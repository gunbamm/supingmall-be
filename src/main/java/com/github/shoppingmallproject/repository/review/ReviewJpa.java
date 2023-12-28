package com.github.shoppingmallproject.repository.review;

import com.github.shoppingmallproject.repository.product.ProductEntity;
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
                    "JOIN FETCH pe.productPhotoEntities pp " +
                    "WHERE re.userEntity = :userId"
    )
    List<ReviewEntity> findByUserEntity(@Param("userId") UserEntity userEntity);


    @Query(
            "SELECT re " +
                    "FROM ReviewEntity re " +
                    "JOIN FETCH re.productEntity pe " +
                    "WHERE re.productEntity = :productId"
    )
    List<ReviewEntity> findByProductId(@Param("productId") ProductEntity productEntity);


}
