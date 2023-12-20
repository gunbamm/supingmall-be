package com.github.shoppingmallproject.service;

import com.github.shoppingmallproject.repository.product.ProductEntity;
import com.github.shoppingmallproject.repository.product.ProductJoinProductPhoto;
import com.github.shoppingmallproject.repository.product.ProductJpa;
import com.github.shoppingmallproject.repository.productPhoto.ProductPhotoEntity;
import com.github.shoppingmallproject.repository.productPhoto.ProductPhotoJpa;
import com.github.shoppingmallproject.repository.review.ReviewEntity;
import com.github.shoppingmallproject.repository.review.ReviewJpa;
import com.github.shoppingmallproject.service.mappers.ProductMapper;
import com.github.shoppingmallproject.web.dto.findProductDTO.ProductList;
import com.github.shoppingmallproject.web.dto.findProductDTO.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindProductService {

    private final ProductJpa productJpa;
    private final ProductPhotoJpa productPhotoJpa;
    private final ReviewJpa reviewJpa;

    public ProductResponse findProductByCategory(String category, Pageable pageable) {
        List<String> categories = new ArrayList<>();
        if (category.equals("")) {
            categories.add("상의");
            categories.add("하의");
            categories.add("신발");
        } else categories.add(category);
        Integer photoType = 1;
        String productStatus = "판매중";
        Page<ProductJoinProductPhoto> productJoinProductPhotos = productJpa.findAllByCategoryInAndStatusAndProductPhoto(categories, productStatus, pageable);
        List<ProductPhotoEntity> productPhotoEntities = productPhotoJpa.findAllByPhotoType(photoType);
        List<ReviewEntity> reviewEntities = reviewJpa.findAll();
        String code = "SU";
        String message = "Success";

        List<ProductList> productList = productJoinProductPhotos.stream().map(ProductMapper.INSTANCE::productAndProductPhotoToProductList).collect(Collectors.toList());
        ProductResponse productResponse = new ProductResponse(code, message, productList);
        return productResponse;




    }
}
