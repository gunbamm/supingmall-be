package com.github.shoppingmallproject.repository.review;

import com.github.shoppingmallproject.repository.product.ProductPhoto;
import com.github.shoppingmallproject.repository.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewJpa extends JpaRepository<ReviewEntity, Integer> {

    @Query(
            "SELECT re " +
                    "FROM ReviewEntity re " +
                    "JOIN FETCH re.userEntity ue " +
                    "JOIN FETCH re.productEntity pe " +
                    "JOIN pe.productPhotos pp " +
                    "WHERE re.userEntity = :userId"
    )
    List<ReviewEntity> findByUserEntity(@Param("userId") UserEntity userEntity);


    @Query("SELECT rp " +
            "FROM ReviewEntity r " +
            "JOIN r.productEntity p " +
            "JOIN p.productPhotos rp " +
            "WHERE r.userEntity = :userId")
    List<ProductPhoto> findProductPhotosByReviewId(@Param("userId") UserEntity userEntity);

}
