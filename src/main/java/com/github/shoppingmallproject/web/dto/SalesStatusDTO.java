package com.github.shoppingmallproject.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SalesStatusDTO {
    private List<SalesStatusItemDTO> salesStatusItems;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SalesStatusItemDTO {
        private String photoUrl;
        private String productName;
        private Integer productPrice;
        private Integer stock;
        private String category;
        private LocalDateTime finishAt;
    }
}
