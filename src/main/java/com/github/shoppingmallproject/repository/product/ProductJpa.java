package com.github.shoppingmallproject.repository.product;

import com.github.shoppingmallproject.repository.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductJpa extends JpaRepository<ProductEntity, Integer> {
    @Query("SELECT new com.github.shoppingmallproject.repository.product.ProductJoinPhotoAndReview(p.productId, p.userEntity.userId, p.productName, p.productPrice, p.category, p.createAt, t.photoUrl, COUNT(DISTINCT r.reviewId), AVG(r.score)) " +
            "FROM ProductEntity p " +
            "LEFT JOIN p.productPhotoEntities t " +
            "LEFT JOIN p.reviewEntities r " +
            "WHERE t.photoType = true AND p.productStatus = ?1 "+
            "GROUP BY p.productId " +
            "ORDER BY p.productId ASC "
    )
    Page<ProductJoinPhotoAndReview> findAllByStatusAndPhotoType(String productStatus, Pageable pageable);

    @Query("SELECT new com.github.shoppingmallproject.repository.product.ProductJoinPhotoAndReview(p.productId, p.userEntity.userId, p.productName, p.productPrice, p.category, p.createAt, t.photoUrl, COUNT(DISTINCT r.reviewId), AVG(r.score)) " +
            "FROM ProductEntity p " +
            "LEFT JOIN p.productPhotoEntities t " +
            "LEFT JOIN p.reviewEntities r " +
            "WHERE t.photoType = true AND p.category = ?1 AND p.productStatus = ?2 "+
            "GROUP BY p.productId " +
            "ORDER BY p.productId ASC "
    )

    Page<ProductJoinPhotoAndReview> findAllByCategoryInAndStatusAndPhotoType(ProductEntity.Category category, String productStatus, Pageable pageable);

    @Query("SELECT new com.github.shoppingmallproject.repository.product.ProductJoinPhotoAndReview(p.productId, p.userEntity.userId, p.productName, p.productPrice, p.category, p.createAt, t.photoUrl, COUNT(DISTINCT r.reviewId), AVG(r.score)) " +
            "FROM ProductEntity p " +
            "LEFT JOIN p.productPhotoEntities t " +
            "LEFT JOIN p.reviewEntities r " +
            "WHERE t.photoType = true AND p.productStatus = ?1 AND p.productName LIKE %?2%  "+
            "GROUP BY p.productId " +
            "ORDER BY p.productId ASC "
    )
    Page<ProductJoinPhotoAndReview> findByKeywordByStatusAndPhotoType(String productStatus, String keyword, Pageable pageable);
    
}
