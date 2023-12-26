package com.github.shoppingmallproject.repository.productOption;

import com.github.shoppingmallproject.web.dto.SalesStatusDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOptionJpa extends JpaRepository<ProductOptionEntity, Integer> {

    @Query("SELECT NEW com.github.shoppingmallproject.web.dto.SalesStatusDTO(" +
            "p.photoUrl, " +
            "p.productName, " +
            "p.productPrice, " +
            "po.stock, " +
            "p.category, " +
            "p.finishAt" +
            ") FROM ProductOptionEntity po " +
            "JOIN po.productEntity p")
    List<SalesStatusDTO> findAllSalesStatus();
}
