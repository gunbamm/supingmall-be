package com.github.shoppingmallproject.web.controller;

import com.github.shoppingmallproject.repository.product.SaleStatusEntity;
import com.github.shoppingmallproject.service.SalesStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api")
public class SalesStatusController {

    private final SalesStatusService salesStatusService;

    @GetMapping("/admin/my-page/sales-status")
    public ResponseEntity<List<SaleStatusEntity>> findAllSalesStatus() {

        List<SaleStatusEntity> salesStatus = salesStatusService.findAllSalesStatus();
        return ResponseEntity.ok(salesStatus);
    }
}
