package com.github.shoppingmallproject.repository.product;

import jakarta.persistence.*;

@Entity
@Table(name = "product_photo")
public class ProductPhoto {

    @Id
    @Column(name = "product_photo_id")
    private Long productPhotoId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity productEntity;

    @Column(name = "photo_url", length = 255, nullable = false)
    private String photoUrl;

    @Column(name = "photo_type", nullable = false)
    private boolean photoType;


}
