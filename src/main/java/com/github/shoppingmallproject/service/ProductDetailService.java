package com.github.shoppingmallproject.service;

import com.github.shoppingmallproject.repository.product.ProductEntity;
import com.github.shoppingmallproject.repository.product.ProductJpa;
import com.github.shoppingmallproject.repository.productOption.ProductOptionEntity;
import com.github.shoppingmallproject.repository.productOption.ProductOptionJpa;
import com.github.shoppingmallproject.repository.productPhoto.ProductPhotoEntity;
import com.github.shoppingmallproject.repository.review.ReviewEntity;
import com.github.shoppingmallproject.repository.review.ReviewJpa;
import com.github.shoppingmallproject.service.exceptions.NotFoundException;
import com.github.shoppingmallproject.service.mappers.ProductMapper;
import com.github.shoppingmallproject.web.dto.product.OptionDTO;
import com.github.shoppingmallproject.web.dto.product.PhotoDTO;
import com.github.shoppingmallproject.web.dto.product.ProductDetailResponse;
import com.github.shoppingmallproject.web.dto.product.ReviewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDetailService {

    private final ProductJpa productJpa;
//    private final ProductOptionJpa productOptionJpa;
//    private final ReviewJpa reviewJpa;

    public ProductDetailResponse getProductDetails(Integer productId) {

        ProductEntity productEntity = productJpa.findById(productId)
                .orElseThrow( () -> new NotFoundException("상품을 찾을 수 없습니다."));

        ProductDetailResponse productDetailResponse = ProductMapper.INSTANCE.productEntityToProductDetailResponse(productEntity);
        List<ProductPhotoEntity> productPhotoEntities = productEntity.getProductPhotoEntities();
        List<ProductOptionEntity> productOptionEntities = productEntity.getProductOptionEntities();
        List<OptionDTO> optionDTOS = productOptionEntities.stream()
                .map(po -> {
                    return OptionDTO.builder()
                            .productOptionId(po.getProductOptionId())
                            .color(po.getColor())
                            .stock(po.getStock())
                            .productSize(po.getProductSize())
                            .build();
                })
                .toList();
        List<PhotoDTO> photoDTOS = productPhotoEntities.stream().map(pp -> {
            return PhotoDTO.builder()
                    .productPhotoId(pp.getProductPhotoId())
                    .photoUrl(pp.getPhotoUrl())
                    .photoType(pp.getPhotoType())
                    .build();
        })
                .toList();
        productDetailResponse.setProductPhoto(photoDTOS);
        productDetailResponse.setProductDetailList(optionDTOS);

        List<ReviewEntity> reviewEntities = productEntity.getReviewEntities();
        List<ReviewDTO> reviewDTOS = reviewEntities.stream()
                .map(re -> {
                    return ReviewDTO.builder()
                            .reviewId(re.getReviewId())
                            .userId(re.getUserEntity().getUserId())
                            .productId(re.getProductEntity().getProductId())
                            .score(re.getScore())
                            .nickName(re.getUserEntity().getNickName())
                            .reviewContents(re.getReviewContents())
                            .createAt(re.getCreateAt())
                            .score(re.getScore())
                            .build();
                })
                .toList();
        productDetailResponse.setProductReview(reviewDTOS);
        productDetailResponse.setScoreAvg(productEntity.getRating());

        return productDetailResponse;
    }

}
