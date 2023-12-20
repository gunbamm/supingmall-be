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

    @Id @Column(name = "product_photo_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ProductPhotoId;

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private ProductEntity productEntity;

    @Column(name = "photo_url", nullable = false, length = 255)
    private String photoUrl;

    @Column(name = "photo_type", nullable = false)
    private Integer photoType;
}
