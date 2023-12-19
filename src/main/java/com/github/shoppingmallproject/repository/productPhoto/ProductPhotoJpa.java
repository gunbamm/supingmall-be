package com.github.shoppingmallproject.repository.productPhoto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPhotoJpa extends JpaRepository<ProductPhotoEntity, Integer> {
}
