package com.github.shoppingmallproject.repository.productPhoto;

import com.github.shoppingmallproject.repository.product.ProductEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "photoId")
@Entity
@Table(name = "product_photo")
public class ProductPhotoEntity {

    @Id @Column(name = "photo_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer photoId;

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private ProductEntity productEntity;

    @Column(name = "product_photo", nullable = false, length = 255)
    private String productPhoto;

    @Column(name = "photo_type", nullable = false)
    private Boolean photoType;
}
