package com.github.shoppingmallproject.service;

import com.github.shoppingmallproject.config.security.JwtTokenConfig;
import com.github.shoppingmallproject.repository.userRoles.Roles;
import com.github.shoppingmallproject.repository.userRoles.RolesJpa;
import com.github.shoppingmallproject.repository.userRoles.UserRoles;
import com.github.shoppingmallproject.repository.userRoles.UserRolesJpa;
import com.github.shoppingmallproject.repository.users.UserEntity;
import com.github.shoppingmallproject.repository.users.UserJpa;
import com.github.shoppingmallproject.service.exceptions.NotAcceptException;
import com.github.shoppingmallproject.service.exceptions.NotFoundException;
import com.github.shoppingmallproject.service.mappers.UserMapper;
import com.github.shoppingmallproject.web.dto.LoginRequest;
import com.github.shoppingmallproject.web.dto.SignUpRequest;
import com.github.shoppingmallproject.web.dto.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SignUpLoginService {
    private final UserJpa userJpa;
    private final RolesJpa rolesJpa;
    private final UserRolesJpa userRolesJpa;
    private final JwtTokenConfig jwtTokenConfig;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Transactional(transactionManager = "tm")
    public String signUp(SignUpRequest signUpRequest) {
        String email = signUpRequest.getEmail();
        String phoneNumber = signUpRequest.getPhoneNumber();

        if(!email.matches(".+@.+\\..+")){
           throw new NotAcceptException("이메일을 정확히 입력해주세요.");
        } else if ((!phoneNumber.matches("01\\d+")&&phoneNumber.length()==11)) {
            throw new NotAcceptException("핸드폰 번호를 확인해주세요.");
        }

        if(userJpa.existsByEmail(signUpRequest.getEmail())){
            return "이미 입력하신 \""+signUpRequest.getEmail()+"\" 이메일로 가입된 계정이 있습니다.";
        }
        if(userJpa.existsByPhoneNumber(signUpRequest.getPhoneNumber())) {
            return "이미 입력하신 \"" + signUpRequest.getPhoneNumber() + "\" 핸드폰 번호로 가입된 계정이 있습니다.";
        }

        String password = signUpRequest.getPassword();
        signUpRequest.setPassword(passwordEncoder.encode(password));

        Roles roles = rolesJpa.findByName("ROLE_USER");

        UserEntity userEntity = UserMapper.INSTANCE.signUpRequestToUserEntity(signUpRequest);
        userJpa.save(userEntity);

        userRolesJpa.save(UserRoles.builder()
                        .userEntity(userEntity)
                        .roles(roles)
                .build());

        SignUpResponse signUpResponse = UserMapper.INSTANCE.userEntityToSignUpResponse(userEntity);

        return signUpResponse.getName()+"님 회원 가입이 완료 되었습니다.\n"+
                "가입 날짜 : "+signUpResponse.getCreatedAt();
    }

    public List<String> login(LoginRequest loginRequest){
        String emailOrPhoneNumber = loginRequest.getEmailOrPhoneNumber();
        UserEntity userEntity;

        if(emailOrPhoneNumber.matches("01\\d+")&&emailOrPhoneNumber.length()==11){
            userEntity = userJpa.findByPhoneNumberJoin(emailOrPhoneNumber).orElseThrow(()->
                    new NotFoundException(
                            String.format("해당 핸드폰번호 \"%s\"의 계정을 찾을 수 없습니다.", emailOrPhoneNumber)
                    ));
        } else if (emailOrPhoneNumber.matches(".+@.+\\..+")) {
            userEntity = userJpa.findByEmailJoin(emailOrPhoneNumber).orElseThrow(()->
                    new NotFoundException(
                            String.format("해당 이메일 \"%s\"의 계정을 찾을 수 없습니다.", emailOrPhoneNumber)
                    ));
        }else throw new NotAcceptException("입력하신 이메일 또는 핸드폰 번호가 잘못입력되었습니다.");

        try{
            String email = userEntity.getEmail();
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            List<String> roles = userEntity.getUserRoles().stream()
                    .map(u->u.getRoles()).map(r->r.getName()).toList();

            return Arrays.asList(jwtTokenConfig.createToken(email, roles), userEntity.getName());
//        }catch (InternalAuthenticationServiceException e){
//            throw new NotFoundException(String.format("해당 이메일 또는 핸드폰번호 \"%s\"의 계정을 찾을 수 없습니다.", emailOrPhoneNumber));
        }catch (BadCredentialsException e){
            throw new NotAcceptException("비밀번호가 틀립니다.");
        }
    }

    @Transactional(transactionManager = "tm")
    public String setSuperUser(String email) {
        UserEntity userEntity = userJpa.findByEmailJoin(email)
                .orElseThrow(()->new NotFoundException("이메일 \""+email+"\"의 계정을 찾을 수 없습니다."));
        List<Roles> userRoles = userEntity.getUserRoles().stream()
                .map(ur->ur.getRoles()).toList();
        Roles roles = rolesJpa.findByName("ROLE_SUPERUSER");

        if(userRoles.contains(roles)) return "이미 "+roles.getName()+"의 권한을 갖고 있습니다.";


        userRolesJpa.save(UserRoles.builder()
                        .roles(roles)
                        .userEntity(userEntity)
                .build());

        return "\""+userEntity.getEmail()+"\"의 계정의 권한에 "
                +roles.getName()
                +"가 추가 되었습니다.";
    }
}
