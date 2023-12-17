package com.github.shoppingmallproject.web.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccountDTO {
    String name;
    String gender;
    String email;
    String nickName;
    String password;
    String phoneNumber;
    String imageUrl;
    String address;
    String createdAt;
    List<String> userRoles;
}
