package com.github.shoppingmallproject.service;

import com.github.shoppingmallproject.repository.product.SaleStatusEntity;
import com.github.shoppingmallproject.repository.productOption.ProductOptionJpa;
import com.github.shoppingmallproject.web.dto.SalesStatusDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesStatusService {

    private final ProductOptionJpa productOptionJpa;

    public SalesStatusService(ProductOptionJpa productOptionJpaRepository) {
        this.productOptionJpa = productOptionJpaRepository;
    }

    public List<SaleStatusEntity> findAllSalesStatus() {
        return productOptionJpa.findAllSalesStatus();
    }
}
