package com.github.shoppingmallproject.service;

import com.github.shoppingmallproject.repository.product.ProductEntity;
import com.github.shoppingmallproject.repository.product.ProductJPA;
import com.github.shoppingmallproject.repository.userDetails.CustomUserDetails;
import com.github.shoppingmallproject.repository.users.UserEntity;
import com.github.shoppingmallproject.repository.users.UserJpa;
import com.github.shoppingmallproject.service.exceptions.NotFoundException;
import com.github.shoppingmallproject.web.dto.product.SaleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final ProductJPA productJPA;
    private final UserJpa userJpa;

    @Transactional("tm")
    public String regProduct(CustomUserDetails customUserDetails, SaleRequest saleRequest) {
        UserEntity userEntity = userJpa.findByEmail(customUserDetails.getUsername());



        return null;
    }
}
