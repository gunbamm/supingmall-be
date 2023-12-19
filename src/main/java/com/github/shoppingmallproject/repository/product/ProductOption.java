package com.github.shoppingmallproject.repository.product;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_option")
@Setter
@Getter
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_option_id")
    private Integer productOptionId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity productEntity;

    @Column(name = "color", length = 20, nullable = false)
    private String color;

    @Column(name = "product_size", length = 10, nullable = false)
    private String productSize;

    @Column(name = "stock", nullable = false)
    private Integer stock;

}