package com.github.shoppingmallproject.repository.productOption;

import com.github.shoppingmallproject.repository.product.ProductEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "productOptionId")
@Table(name = "product_option")
public class ProductOptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_option_id")
    private Integer productOptionId;

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private ProductEntity productEntity;

    @Column(name = "color", length = 255)
    private String color;

    @Column(name = "product_size", length = 255)
    private String productSize;

    @Column(name = "stock", nullable = false)
    private Integer stock;
}

