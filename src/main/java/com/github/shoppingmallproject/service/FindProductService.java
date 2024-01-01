package com.github.shoppingmallproject.service;

import com.github.shoppingmallproject.repository.product.ProductEntity;
import com.github.shoppingmallproject.repository.product.ProductJoinPhotoAndReview;
import com.github.shoppingmallproject.repository.product.ProductJpa;
import com.github.shoppingmallproject.repository.productPhoto.ProductPhotoJpa;
import com.github.shoppingmallproject.repository.review.ReviewJpa;
import com.github.shoppingmallproject.service.exceptions.NotFoundException;
import com.github.shoppingmallproject.web.dto.findProductDTO.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@ToString
@Slf4j
public class FindProductService {

    private final ProductJpa productJpa;
    private final ProductPhotoJpa productPhotoJpa;
    private final ReviewJpa reviewJpa;


    public ProductResponse findProductByCategory(String category, Pageable pageable) {
        // category arg 에러 예외 처리
        List<String> categories = new ArrayList<>();
        categories.add("상의");
        categories.add("하의");
        categories.add("신발");
        categories.add("전체");
        if (!categories.contains(category)) throw new NotFoundException("NFC", "Not Found Category", category);

        Page<ProductJoinPhotoAndReview> productJoinProductPhotos;
        String productStatus = "판매중";
        String code = "SU";
        String message = "Success";

        if (category.equals("전체")) {
            productJoinProductPhotos = productJpa.findAllByStatusAndPhotoType(productStatus, pageable);
        } else {
            productJoinProductPhotos = productJpa.findAllByCategoryInAndStatusAndPhotoType(ProductEntity.Category.valueOf(category), productStatus, pageable);
        }
        if (productJoinProductPhotos.isEmpty()) throw new NotFoundException("NFP", "Not Found Product in the Page", String.valueOf(pageable.getPageNumber()));
        log.info(productJoinProductPhotos.toString());

        return new ProductResponse(code, message, productJoinProductPhotos);
    }

    public ProductResponse findProductByKeyword(String keyword, Pageable pageable) {
        String productStatus = "판매중";
        String code = "SU";
        String message = "Success";

        Page<ProductJoinPhotoAndReview> productJoinProductPhotos = productJpa.findByKeywordByStatusAndPhotoType(productStatus, keyword, pageable);

        if (productJoinProductPhotos.isEmpty()) throw new NotFoundException("NFP", "Not Found Product in the Page", String.valueOf(pageable.getPageNumber()));

        return new ProductResponse(code, message, productJoinProductPhotos);
    }
}
