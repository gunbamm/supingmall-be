package com.github.shoppingmallproject.web.dto.authAccount;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpResponse {
    private String name;
    private String createdAt;
}
