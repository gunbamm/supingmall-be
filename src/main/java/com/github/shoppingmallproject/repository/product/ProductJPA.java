package com.github.shoppingmallproject.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJPA extends JpaRepository<ProductEntity, Integer> {

}
