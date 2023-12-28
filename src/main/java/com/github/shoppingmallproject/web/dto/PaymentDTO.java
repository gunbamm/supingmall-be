package com.github.shoppingmallproject.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {
    private String photoUrl;
    private String productName;
    private Integer productPrice;
}

