package com.github.shoppingmallproject.service;

import com.github.shoppingmallproject.repository.product.*;
import com.github.shoppingmallproject.repository.productOption.ProductOptionEntity;
import com.github.shoppingmallproject.repository.productOption.ProductOptionJpa;
import com.github.shoppingmallproject.repository.productPhoto.ProductPhotoEntity;
import com.github.shoppingmallproject.repository.productPhoto.ProductPhotoJpa;
import com.github.shoppingmallproject.repository.userDetails.CustomUserDetails;
import com.github.shoppingmallproject.repository.users.UserEntity;
import com.github.shoppingmallproject.repository.users.UserJpa;
import com.github.shoppingmallproject.service.mappers.ProductMapper;
import com.github.shoppingmallproject.web.dto.product.SaleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final ProductJpa productJPA;
    private final ProductPhotoJpa productPhotoJpa;
    private final ProductOptionJpa productOptionJpa;
    private final UserJpa userJpa;


    @Transactional("tm")
    public String regProduct(CustomUserDetails customUserDetails, SaleRequest saleRequest) {
        UserEntity userEntity = userJpa.findByEmail(customUserDetails.getUsername());
        List<ProductOptionEntity> productOptionEntityList = saleRequest.getOption().stream()
                .map(op->ProductMapper.INSTANCE.OptionDTOToProductOptionEntity(op))
                .toList();
        List<ProductPhotoEntity> productPhotoList = saleRequest.getPhoto().stream()
                .map(ph->ProductMapper.INSTANCE.PhotoDTOToProductPhotoEntity(ph))
                .toList();

        ProductEntity productEntity = ProductMapper.INSTANCE
                .saleRequestToProductEntity(saleRequest, userEntity, productOptionEntityList, productPhotoList);
        ProductEntity savedProduct = productJPA.save(productEntity);
        productOptionEntityList.forEach(o->{
            o.setProductEntity(savedProduct);
            productOptionJpa.save(o);
        });
        productPhotoList.forEach(p->{
            p.setProductEntity(savedProduct);
            productPhotoJpa.save(p);
        });

        return "등록 성공";
    }
}
