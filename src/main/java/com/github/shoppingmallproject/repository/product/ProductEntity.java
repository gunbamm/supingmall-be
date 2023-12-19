package com.github.shoppingmallproject.repository.product;

import com.github.shoppingmallproject.repository.productPhoto.ProductPhotoEntity;
import com.github.shoppingmallproject.repository.users.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "productId")
@Entity
@Table(name = "product")
public class ProductEntity {

    @Id @Column(name = "product_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity userEntity;

    @Column(name = "product_name", length = 50, nullable = false)
    private String productName;

    @Column(name = "product_price", nullable = false)
    private Integer productPrice;

    @Column(name = "category", nullable = false, columnDefinition = "CHECK(category IN('상의', '하의', '신발'))")
    private String category;

    @Column(name = "product_status", nullable = false, columnDefinition = "DEFAULT '판매중' CHECK(product_status IN('판매중', '판매완료'))")
    private String productStatus;

    @Column(name = "create_at", nullable = false)
    private String createAt;

    @Column(name = "finish_at")
    private String finishAt;

    @OneToMany(mappedBy = "productEntity")
    private List<ProductPhotoEntity> productPhotoEntities;
}
