package com.github.shoppingmallproject.repository.productOption;

import com.github.shoppingmallproject.repository.product.SaleStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductOptionJpa extends JpaRepository<ProductOptionEntity, Integer> {

    @Query("SELECT new com.github.shoppingmallproject.repository.product.SaleStatusEntity" +
            "(pp.photoUrl, p.productEntity.productName, p.productEntity.productPrice, p.stock, p.productEntity.category, p.productEntity.finishAt) " +
            "FROM ProductOptionEntity p " +
            "JOIN p.productEntity.productPhotoEntities pp ")
    List<SaleStatusEntity> findAllSalesStatus();

}
