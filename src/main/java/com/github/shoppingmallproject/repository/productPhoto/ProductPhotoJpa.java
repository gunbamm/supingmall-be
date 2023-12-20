package com.github.shoppingmallproject.repository.productPhoto;

import com.github.shoppingmallproject.repository.product.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPhotoJpa extends JpaRepository<ProductPhotoEntity, Integer> {
    List<ProductPhotoEntity> findAllByPhotoType(Integer photoType);
}
