package com.github.shoppingmallproject.repository.productPhoto;

import com.github.shoppingmallproject.repository.product.ProductEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_photo")
@Getter
@Setter
public class ProductPhotoEntity {

    @Id
    @Column(name = "product_photo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productPhotoId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity productEntity;

    @Column(name = "photo_url", length = 255, nullable = false)
    private String photoUrl;

    @Column(name = "photo_type", nullable = false)
    private Boolean photoType;
}
