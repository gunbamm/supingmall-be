package com.github.shoppingmallproject.repository.payment;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PaymentJpa extends JpaRepository<PaymentEntity, Integer> {

    @Transactional
    @Modifying
    @Query("INSERT INTO PaymentEntity(p.name, p.ship, p.orderRequest, p.orderTotalPrice, p.productPrice, p.orderPrice, p.photoUrl, p.productName, p.productPrice) " +
            "VALUES (:name, :ship, :orderRequest, :orderTotalPrice, :productPrice, :orderPrice, :photoUrl, :productName, :productPrice)")
    void makePayment(@Param("name") String name,
                     @Param("ship") String ship,
                     @Param("orderRequest") String orderRequest,
                     @Param("orderTotalPrice") int orderTotalPrice,
                     @Param("productPrice") int productPrice,
                     @Param("orderPrice") int orderPrice,
                     @Param("photoUrl") String photoUrl,
                     @Param("productName") String productName);
}

