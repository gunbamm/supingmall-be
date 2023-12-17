package com.github.shoppingmallproject.web.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SignUpRequest {
    private String email;
    private String phoneNumber;
    private String password;
    private String name;
    private String nickName;
    private String address;
    private String imageUrl;
    private String gender;
}
