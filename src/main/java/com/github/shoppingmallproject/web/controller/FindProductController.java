package com.github.shoppingmallproject.web.controller;

import com.github.shoppingmallproject.service.FindProductService;
import com.github.shoppingmallproject.web.dto.findProductDTO.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api")
public class FindProductController {

    private final FindProductService findProductService;

    @GetMapping("/product")
    public ProductResponse findProductByCategory(
            @RequestParam("category") String category,
            @RequestParam("page") String page
    ) {
        return findProductService.findProductByCategory(category, page);
    }
}
