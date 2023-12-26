package com.github.shoppingmallproject.service;

import com.github.shoppingmallproject.repository.product.*;
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
    private final ProductPhotoSihuJpa productPhotoSihuJpa;
    private final ProductOptionJpa productOptionJpa;

    private final UserJpa userJpa;


    @Transactional("tm")
    public String regProduct(CustomUserDetails customUserDetails, SaleRequest saleRequest) {
        UserEntity userEntity = userJpa.findByEmail(customUserDetails.getUsername());
        List<ProductOption> productOptionList = saleRequest.getOption().stream()
                .map(op->ProductMapper.INSTANCE.OptionDTOToProductOption(op))
                .toList();
        List<ProductPhoto> productPhotoList = saleRequest.getPhoto().stream()
                .map(ph->ProductMapper.INSTANCE.PhotoDTOToProductPhoto(ph))
                .toList();

        ProductEntity productEntity = ProductMapper.INSTANCE
                .saleRequestToProductEntity(saleRequest, userEntity, productOptionList, productPhotoList);
        ProductEntity savedProduct = productJPA.save(productEntity);
        productOptionList.forEach(o->{
            o.setProductEntity(savedProduct);
            productOptionJpa.save(o);
        });
        productPhotoList.forEach(p->{
            p.setProductEntity(savedProduct);
            productPhotoSihuJpa.save(p);
        });

        return "등록 성공";
    }
}
