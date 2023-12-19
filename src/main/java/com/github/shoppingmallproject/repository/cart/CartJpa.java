package com.github.shoppingmallproject.repository.cart;

import com.github.shoppingmallproject.repository.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartJpa extends JpaRepository<CartEntity, Integer> {
    List<CartEntity> findAllByUser(UserEntity userEntity);

    @Query(
            "SELECT DISTINCT ce " +
                    "FROM CartEntity ce " +
                    "JOIN FETCH ce.productOption po " +
                    "JOIN FETCH po.productEntity pe " +
                    "JOIN FETCH pe.productPhotos " +
                    "WHERE ce.user = :userEntity"
    )
    List<CartEntity> findByUserJoin(UserEntity userEntity);
}
