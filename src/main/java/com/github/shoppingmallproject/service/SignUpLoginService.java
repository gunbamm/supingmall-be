package com.github.shoppingmallproject.service;

import com.github.shoppingmallproject.repository.user_roles.Roles;
import com.github.shoppingmallproject.repository.user_roles.RolesJpa;
import com.github.shoppingmallproject.repository.user_roles.UserRoles;
import com.github.shoppingmallproject.repository.user_roles.UserRolesJpa;
import com.github.shoppingmallproject.repository.users.UserEntity;
import com.github.shoppingmallproject.repository.users.UserJpa;
import com.github.shoppingmallproject.service.mappers.UserMapper;
import com.github.shoppingmallproject.web.dto.SignUpRequest;
import com.github.shoppingmallproject.web.dto.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SignUpLoginService {
    private final UserJpa userJpa;
    private final RolesJpa rolesJpa;
    private final UserRolesJpa userRolesJpa;

    private final PasswordEncoder passwordEncoder;
    public String signUp(SignUpRequest signUpRequest) {
        List<Roles> rolesList = rolesJpa.findAll();

        if(userJpa.existsByEmail(signUpRequest.getEmail())){
            return "이미 입력하신 \""+signUpRequest.getEmail()+"\" 이메일이 있습니다.";
        }
        String password = signUpRequest.getPassword();
        signUpRequest.setPassword(passwordEncoder.encode(password));

        Roles roles = rolesJpa.findByName("ROLE_USER");

        UserEntity userEntity = UserMapper.INSTANCE.UserDtoToUserEntity(signUpRequest);
        userJpa.save(userEntity);

        userRolesJpa.save(UserRoles.builder()
                        .userEntity(userEntity)
                        .roles(roles)
                .build());

        SignUpResponse signUpResponse = UserMapper.INSTANCE.UserEntityToUserDto(userEntity);

        return signUpResponse.getName()+signUpResponse.getCreatedAt();

    }

}
