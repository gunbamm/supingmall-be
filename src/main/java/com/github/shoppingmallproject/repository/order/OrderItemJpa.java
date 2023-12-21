package com.github.shoppingmallproject.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemJpa extends JpaRepository<OrderItemEntity, Integer> {
}
