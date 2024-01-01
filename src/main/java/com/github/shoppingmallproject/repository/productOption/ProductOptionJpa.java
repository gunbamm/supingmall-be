package com.github.shoppingmallproject.repository.productOption;

import com.github.shoppingmallproject.repository.product.SaleStatusEntity;
import com.github.shoppingmallproject.web.dto.SalesStatusDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductOptionJpa extends JpaRepository<ProductOptionEntity, Integer> {

    @Query("SELECT new com.github.shoppingmallproject.repository.product.SaleStatusEntity" +
            "(p.productOptionId, pp.photoUrl, p.productEntity.productName, p.color, p.productSize, p.productEntity.productPrice, p.stock, p.productEntity.category, p.productEntity.finishAt) " +
            "FROM ProductOptionEntity p " +
            "JOIN p.productEntity.productPhotoEntities pp " +
            "WHERE pp.photoType = true " +
            "GROUP BY p.productOptionId " +
            "ORDER BY p.productOptionId ASC ")
    List<SaleStatusEntity> findAllSalesStatus();

//    @Query("SELECT poe " +
//            "FROM ProductOptionEntity poe " +
//            "JOIN FETCH poe.productEntity pe " +
//            "JOIN FETCH ")
//    ProductOptionEntity findByIdFetchJoin(Integer id);

}
