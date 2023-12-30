package com.github.shoppingmallproject.repository.orderItem;


import com.github.shoppingmallproject.repository.order.OrderEntity;
import com.github.shoppingmallproject.repository.productOption.ProductOptionEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_item")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Integer orderItemId;

    @ManyToOne
    @JoinColumn(name = "product_option_id", nullable = false)
    private ProductOptionEntity productOptionEntity;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity orderTable;

    @Column(name = "item_amount", nullable = false)
    private Integer itemAmount;

    @Column(name = "order_price", nullable = false)
    private Integer orderPrice;


}
