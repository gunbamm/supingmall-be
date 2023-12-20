package com.github.shoppingmallproject.repository.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductJpa extends JpaRepository<ProductEntity, Integer> {
    @Query(
            "SELECT p, t.photoUrl FROM ProductEntity p " +
                    "JOIN p.productPhotoEntities t " +
                    "WHERE t.photoType = 1 AND p.category IN ?1 AND p.productStatus = ?2"
    )
    Page<ProductJoinProductPhoto> findAllByCategoryInAndStatusAndProductPhoto(List<String> category, String productStatus, Pageable pageable);
}
