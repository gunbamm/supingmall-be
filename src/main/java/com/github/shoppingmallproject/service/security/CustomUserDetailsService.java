package com.github.shoppingmallproject.service.security;

import com.github.shoppingmallproject.repository.userDetails.CustomUserDetails;
import com.github.shoppingmallproject.repository.users.UserEntity;
import com.github.shoppingmallproject.repository.users.UserJpa;
import com.github.shoppingmallproject.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Primary
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserJpa userJpa;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userJpa.findByEmailJoin(email).orElseThrow(() ->
                new NotFoundException("NFE", "Not Found Email", email));

        return CustomUserDetails.builder()
                .userId(userEntity.getUserId())
                .email(userEntity.getEmail())
                .address(userEntity.getAddress())
                .password(userEntity.getPassword())
                .phoneNumber(userEntity.getPhoneNumber())
                .nickName(userEntity.getNickName())

                .authorities(userEntity.getUserRoles()
                        .stream().map(ur->ur.getRoles())
                        .map(r->r.getName())
                        .collect(Collectors.toList()))
                .build();

    }

}
