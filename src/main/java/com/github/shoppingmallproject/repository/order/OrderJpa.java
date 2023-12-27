package com.github.shoppingmallproject.repository.order;

import com.github.shoppingmallproject.repository.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderJpa extends JpaRepository<OrderEntity, Integer> {

    @Query(
            "SELECT oe " +
                    "FROM OrderEntity oe " +
                    "JOIN FETCH oe.orderItemEntities oie " +
                    "JOIN FETCH oie.productOptionEntity " +
                    "WHERE oe.userEntity = :userEntity "
    )
    List<OrderEntity> findAllByUserEntityJoin(UserEntity userEntity);
}
