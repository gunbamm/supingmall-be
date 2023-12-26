package com.github.shoppingmallproject.repository.cart;


import com.github.shoppingmallproject.repository.product.ProductOption;
import com.github.shoppingmallproject.repository.users.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Integer cartId;

    @ManyToOne
    @JoinColumn(name = "production_option_id", nullable = false)
    private ProductOption productOption;

    @Column(name = "cart_amount", nullable = false)
    private Integer cartAmount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

}
