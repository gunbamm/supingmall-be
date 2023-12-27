package com.github.shoppingmallproject.repository.product;
import com.github.shoppingmallproject.repository.productOption.ProductOptionEntity;
import com.github.shoppingmallproject.repository.productPhoto.ProductPhotoEntity;
import com.github.shoppingmallproject.repository.review.ReviewEntity;
import com.github.shoppingmallproject.repository.users.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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


    @Column(name = "product_name", length = 50, nullable = false)
    private String productName;

    @Column(name = "product_price", nullable = false)
    private Integer productPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @Column(name = "rating")
    private Double rating;
    @Column(name = "product_status", nullable = false, columnDefinition = "DEFAULT '판매중' CHECK(product_status IN('판매중', '판매완료'))")
    private String productStatus;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;


    @Column(name = "finish_at")
    private LocalDateTime finishAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @OneToMany(mappedBy = "productEntity")
    private List<ProductOptionEntity> productOptionEntities;

    @OneToMany(mappedBy = "productEntity")
    private List<ProductPhotoEntity> productPhotoEntities;

    @OneToMany(mappedBy = "productEntity")
    private List<ReviewEntity> reviewEntities;


    public enum Category {
        상의("상의"),
        하의("하의"),
        신발("신발");

        private final String value;

        Category(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
