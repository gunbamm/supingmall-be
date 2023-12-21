package com.github.shoppingmallproject.repository.order;


import com.github.shoppingmallproject.repository.product.ProductOption;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "order_item")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Integer orderItemId;

    @ManyToOne
    @JoinColumn(name = "product_option_id", nullable = false)
    private ProductOption productOption;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity orderTable;

    @Column(name = "item_amount", nullable = false)
    private Integer itemAmount;

    @Column(name = "order_price", nullable = false)
    private Integer orderPrice;


}
