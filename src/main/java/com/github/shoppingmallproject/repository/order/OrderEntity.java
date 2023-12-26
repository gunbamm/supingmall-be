package com.github.shoppingmallproject.repository.order;

import com.github.shoppingmallproject.repository.users.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "order_table")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @Column(name = "order_at", nullable = false)
    private LocalDateTime orderAt;

    @Column(name = "order_status", nullable = false, length = 10)
    private String orderStatus;

    @Column(name = "ship", length = 255)
    private String ship;

    @Column(name = "order_request", nullable = false, length = 100, columnDefinition = "varchar(100) default '조심히 부탁드립니다.'")
    private String orderRequest;

    @Column(name = "order_total_price", nullable = false)
    private Integer orderTotalPrice;

    @OneToMany(mappedBy = "orderTable")
    private List<OrderItemEntity> orderItemEntities;

}
