package com.github.shoppingmallproject.service.authAccount;

import com.github.shoppingmallproject.repository.cart.CartEntity;
import com.github.shoppingmallproject.repository.cart.CartJpa;
import com.github.shoppingmallproject.repository.order.OrderEntity;
import com.github.shoppingmallproject.repository.orderItem.OrderItemEntity;
import com.github.shoppingmallproject.repository.order.OrderJpa;
import com.github.shoppingmallproject.repository.product.ProductJpa;
import com.github.shoppingmallproject.repository.productOption.ProductOptionEntity;
import com.github.shoppingmallproject.repository.productOption.ProductOptionJpa;
import com.github.shoppingmallproject.repository.userDetails.CustomUserDetails;
import com.github.shoppingmallproject.repository.userRoles.Roles;
import com.github.shoppingmallproject.repository.userRoles.RolesJpa;
import com.github.shoppingmallproject.repository.userRoles.UserRoles;
import com.github.shoppingmallproject.repository.userRoles.UserRolesJpa;
import com.github.shoppingmallproject.repository.users.UserEntity;
import com.github.shoppingmallproject.repository.users.UserJpa;
import com.github.shoppingmallproject.service.exceptions.CustomBindException;
import com.github.shoppingmallproject.service.exceptions.DuplicateKeyException;
import com.github.shoppingmallproject.service.exceptions.NotFoundException;
import com.github.shoppingmallproject.service.mappers.OrderMapper;
import com.github.shoppingmallproject.service.mappers.UserMapper;
import com.github.shoppingmallproject.web.dto.authAccount.AccountDTO;
import com.github.shoppingmallproject.web.dto.product.CartAndTotalQuantityResponse;
import com.github.shoppingmallproject.web.dto.product.OrderResponse;
import com.github.shoppingmallproject.web.dto.product.ProductOptionResponse;
import com.github.shoppingmallproject.web.dto.product.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.FeatureDescriptor;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserJpa userJpa;
    private final UserRolesJpa userRolesJpa;
    private final RolesJpa rolesJpa;
    private final PasswordEncoder passwordEncoder;
    private final ProductOptionJpa productOptionJpa;
    private final CartJpa cartJpa;
    private final ProductJpa productJpa;
    private final OrderJpa orderJpa;


    //탈퇴한지 7일 이상된 계정 정보 자동 삭제 (하루에 한번씩 로직 실행됨)
    @Transactional(transactionManager = "tm")
    public void cleanupOldWithdrawnUser() {
        List<UserEntity> userEntities = userJpa.findAll();
        List<UserEntity> oldWithdrawnUser = userEntities.stream().filter(ue->ue.getDeletionDate()!=null)
                .filter(ue-> ChronoUnit.DAYS.between(ue.getDeletionDate(), LocalDateTime.now())>=7).toList();
        if (oldWithdrawnUser.isEmpty()) return;
        userJpa.deleteAll(oldWithdrawnUser);
    }


    public AccountDTO getMyInfo(CustomUserDetails customUserDetails) {
        UserEntity userEntity = userJpa.findByEmailJoin(customUserDetails.getUsername())
                .orElseThrow(()->new NotFoundException("NFE","계정을 찾을 수 없습니다.",customUserDetails.getUsername()));
        List<String> roles = userEntity.getUserRoles().stream()
                .map(ur->ur.getRoles()).map(r->r.getName()).toList();
        return UserMapper.INSTANCE.userEntityToAccountDTO(userEntity,roles);
    }

    @Transactional(transactionManager = "tm")
    public AccountDTO patchMyInfo(CustomUserDetails customUserDetails, AccountDTO accountDTO) {
        String loggedEmail = customUserDetails.getUsername();
        String email = accountDTO.getEmail();
        String phoneNumber = accountDTO.getPhoneNumber();
        String nickName = accountDTO.getNickName();

        if(!email.matches(".+@.+\\..+")){
            throw new CustomBindException("CBE","이메일을 정확히 입력해주세요.",email);
        } else if (!phoneNumber.matches("01\\d{9}")) {
            throw new CustomBindException("CBE","핸드폰 번호를 확인해주세요.", phoneNumber);
        } else if (nickName.matches("01\\d{9}")){
            throw new CustomBindException("CBE","핸드폰 번호를 닉네임으로 사용할수 없습니다.",nickName);
        }

        if(userJpa.existsByEmailAndEmailNot(email, loggedEmail)){
            throw new DuplicateKeyException(email, "이메일로");
        }else if(userJpa.existsByPhoneNumberAndEmailNot(email, loggedEmail)) {
            throw new DuplicateKeyException(phoneNumber, "핸드폰 번호로");
        }else if(userJpa.existsByNickNameAndEmailNot(email, loggedEmail)){
            throw new DuplicateKeyException(nickName, "닉네임으로");
        }


        UserEntity existingUser = userJpa.findByEmail(customUserDetails.getUsername());
//        List<Roles> roles = accountDTO.getUserRoles().stream()
//                .map(rn->rolesJpa.findByName(rn)).toList();//롤변경시 필요
        UserEntity updateUser = UserMapper.INSTANCE.AccountDTOToUserEntity(accountDTO);

        if(accountDTO.getPassword()==null) throw new CustomBindException("현재 비밀번호를 입력해 주세요.");


        if(passwordEncoder.matches(accountDTO.getPassword(), customUserDetails.getPassword())){
            if (accountDTO.getNewPassword()!=null||accountDTO.getNewPasswordConfirm()!=null) {
                if(accountDTO.getNewPassword()==null||accountDTO.getNewPasswordConfirm()==null) throw new CustomBindException("CBE"
                        ,"비밀번호 변경을 원하시면 새로운 비밀번호와, 비밀번호 확인 둘다 입력해주세요.","new_password : "+accountDTO.getNewPassword()+", new_password_confirm : "+accountDTO.getNewPasswordConfirm());
                else if (accountDTO.getPassword().equals(accountDTO.getNewPassword())) throw  new CustomBindException("CBE","현재 비밀번호와 변경하려는 비밀번호가 같습니다.","new_password : "+accountDTO.getNewPassword());
                else if(accountDTO.getNewPassword().equals(accountDTO.getNewPasswordConfirm())){
                    if(!accountDTO.getNewPassword().matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]+$")
                            ||!(accountDTO.getNewPassword().length()>=8&&accountDTO.getNewPassword().length()<=20)
                    ){
                        throw new CustomBindException("CBE","비밀번호는 8자 이상 20자 이하 숫자와 영문자 조합 이어야 합니다.",accountDTO.getNewPassword());
                    }

                    updateUser.setPassword(passwordEncoder.encode(accountDTO.getNewPassword()));
                }
            }else updateUser.setPassword(passwordEncoder.encode(accountDTO.getPassword()));

            BeanUtils.copyProperties(updateUser, existingUser, getNullPropertyNames(updateUser));
        }else throw new CustomBindException("CBE","현재 비밀번호와 입력하신 비밀번호가 틀립니다.",accountDTO.getPassword());

        List<String> roles = existingUser.getUserRoles().stream()
                .map(ur->ur.getRoles()).map(r->r.getName()).toList();
        return UserMapper.INSTANCE.userEntityToAccountDTO(existingUser, roles);
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        return Stream.of(src.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(pn->src.getPropertyValue(pn) == null)
                .toArray(String[]::new);
    }




    //회원 탈퇴
    @Transactional(transactionManager = "tm")
    public String withdrawal(CustomUserDetails customUserDetails) {
        UserEntity userEntity = userJpa.findById(customUserDetails.getUserId()).orElseThrow(
                ()->new NotFoundException("계정을 찾을 수 없습니다. 다시 로그인 해주세요.")
        );
        if(userEntity.getStatus().equals("delete")){
            throw new DuplicateKeyException("이미 탈퇴처리된 회원 입니다.");
        }
        userEntity.setStatus("delete");
        userEntity.setDeletionDate(LocalDateTime.now());

        return userEntity.getEmail()+" 계정 회원 탈퇴 완료";
    }


    //유저권한 추가 (안쓸듯)
    @Transactional(transactionManager = "tm")
    public String setSuperUser(String email) {
        UserEntity userEntity = userJpa.findByEmailJoin(email)
                .orElseThrow(()->new NotFoundException("이메일",email));
        List<Roles> userRoles = userEntity.getUserRoles().stream()
                .map(ur->ur.getRoles()).toList();
        Roles roles = rolesJpa.findByName("ROLE_SUPERUSER");

        if(userRoles.contains(roles)) throw new DuplicateKeyException("이미 "+roles.getName()+"의 권한을 갖고 있습니다.");

        userRolesJpa.save(UserRoles.builder()
                .roles(roles)
                .userEntity(userEntity)
                .build());

        return "\""+userEntity.getEmail()+"\"의 계정의 권한에 "
                +roles.getName()
                +"가 추가 되었습니다.";
    }

//    @Transactional(transactionManager = "tm")
//    public String cartAddItem(String optionId,String quantity, CustomUserDetails customUserDetails) {
//        UserEntity userEntity = userJpa.findByEmail(customUserDetails.getUsername());
//        ProductOptionEntity productOptionEntity = productOptionJpa.getReferenceById(Integer.valueOf(optionId));
//        CartEntity cartEntity = CartEntity.builder()
//                .productOptionEntity(productOptionEntity)
//                .user(userEntity)
//                .cartAmount(Integer.valueOf(quantity))
//                .build();
//        cartJpa.save(cartEntity);
//        return "장바구니에 상품 담음";
//    }


    public CartAndTotalQuantityResponse getCartItem(CustomUserDetails customUserDetails) {
        UserEntity userEntity = userJpa.findByEmail(customUserDetails.getUsername());
        List<CartEntity> cartEntities = cartJpa.findByUserJoin(userEntity);
        if(cartEntities.isEmpty()) return null;
        Map<Integer, List<CartEntity>> cartEntityMap = new HashMap<>();
        for (CartEntity cartEntity : cartEntities) {
            Integer productId = cartEntity.getProductOptionEntity().getProductEntity().getProductId();
            List<CartEntity> productList = cartEntityMap.getOrDefault(productId, new ArrayList<>());
            productList.add(cartEntity);
            cartEntityMap.put(productId, productList);
        }

        List<List<CartEntity>> groupedCartEntities = new ArrayList<>(cartEntityMap.values());
        List<ProductResponse> productResponseList = new ArrayList<>();

        Integer totalQuantity = 0;
        Integer inCartTotalPrice = 0;
        for (List<CartEntity> group : groupedCartEntities) {
            List<ProductOptionResponse> productOptionResponseList = new ArrayList<>();
            for(CartEntity groupedCart: group){
                totalQuantity = totalQuantity + groupedCart.getCartAmount();

                ProductOptionResponse productOptionResponse = ProductOptionResponse.builder()
                        .optionId(groupedCart.getProductOptionEntity().getProductOptionId())
                        .color(groupedCart.getProductOptionEntity().getColor())
                        .size(groupedCart.getProductOptionEntity().getProductSize())
                        .quantity(groupedCart.getCartAmount())
                        .price(groupedCart.getProductOptionEntity().getProductEntity().getProductPrice())
                        .build();
                productOptionResponseList.add(productOptionResponse);
            }

            Integer totalPrice = productOptionResponseList.stream()
                    .mapToInt(por -> por.getPrice() * por.getQuantity())
                    .sum();;
            inCartTotalPrice = inCartTotalPrice + totalPrice;

            ProductResponse productResponse = ProductResponse.builder()
                    .productId(group.get(0).getProductOptionEntity().getProductEntity().getProductId())
                    .productName(group.get(0).getProductOptionEntity().getProductEntity().getProductName())
                    .productImg(group.get(0).getProductOptionEntity().getProductEntity()
                            .getProductPhotoEntities().stream()
                            .filter(pp-> pp.getPhotoType())
                            .map(pp-> pp.getPhotoUrl()).toList())
                    .productOptionResponse(productOptionResponseList)
                    .productTotalPrice(totalPrice)
                    .build();
            productResponseList.add(productResponse);
        }
        CartAndTotalQuantityResponse cartAndTotalQuantityResponse = new CartAndTotalQuantityResponse();
        cartAndTotalQuantityResponse.setProductResponseList(productResponseList);
        cartAndTotalQuantityResponse.setTotalQuantity(totalQuantity);
        cartAndTotalQuantityResponse.setInCartTotalPrice(inCartTotalPrice);

        return cartAndTotalQuantityResponse;
//        List<String> thumbnailList = cartEntities.stream()
//                .map(ce->ce.getProductOption()).map(po->po.getProductEntity())
//                .map(pe->pe.getProductPhotos())
//                .flatMap(ppl->ppl.stream()
//                        .filter(pp->pp.isPhotoType())
//                        .map(pp->pp.getPhotoUrl()))
//                .toList(); //⬆️플랫맵 사용 예시

        //⬇️상품별로 그룹화 시키지않고 옵션별상품 하나하나 다 반환할때⬇️
//        List<ProductResponse> productResponseList = cartEntities.stream()
//                .map(cartEntity -> ProductResponse.builder()
//                    .color(cartEntity.getProductOption().getColor())
//                    .size(cartEntity.getProductOption().getProductSize())
//                    .optionId(cartEntity.getProductOption().getProductOptionId())
//                    .productId(cartEntity.getProductOption().getProductEntity().getProductId())
//                    .productName(cartEntity.getProductOption().getProductEntity().getProductName())
//                    .quantity(cartEntity.getCartAmount())
//                    .productImg(cartEntity.getProductOption().getProductEntity()
//                            .getProductPhotos().stream()
//                            .filter(pp->pp.isPhotoType())
//                            .map(pp->pp.getPhotoUrl())
//                            .toList()
//                    )
//                    .build())
//                .toList();
    }

    public List<OrderResponse> getMyOrder(CustomUserDetails customUserDetails) {
        UserEntity userEntity = userJpa.findByEmail(customUserDetails.getUsername());

        List<OrderEntity> orderEntities = orderJpa.findAllByUserEntityJoin(userEntity);
        if (orderEntities.isEmpty()) return null;


        List<OrderResponse> orderResponses = new ArrayList<>();
        for (OrderEntity order : orderEntities) {
            OrderResponse makingRes = OrderMapper.INSTANCE.orderEntityToOrderResponse(order);

            List<ProductResponse> productResponseList = new ArrayList<>();

            List<OrderItemEntity> orderItemList = order.getOrderItemEntities();
            Map<Integer, List<OrderItemEntity>> orderItemEntityMap = new HashMap<>();
            for(OrderItemEntity orderTest : orderItemList){
                Integer productId = orderTest.getProductOptionEntity().getProductEntity().getProductId();

                List<OrderItemEntity> orderItemEntityList = orderItemEntityMap.getOrDefault(productId, new ArrayList<>());

                orderItemEntityList.add(orderTest);
                orderItemEntityMap.put(productId, orderItemEntityList);
            }
            List<List<OrderItemEntity>> groupedOrderItem = new ArrayList<>(orderItemEntityMap.values());
            Integer orderTotalPrice = 0;
            for(List<OrderItemEntity> gor:groupedOrderItem){
                List<ProductOptionResponse> productOptionResponses = new ArrayList<>();
                Integer productTotalPrice = 0;
                for(OrderItemEntity oie:gor){
                    ProductOptionResponse productOptionResponse = ProductOptionResponse.builder()
                            .optionId(oie.getProductOptionEntity().getProductOptionId())
                            .color(oie.getProductOptionEntity().getColor())
                            .size(oie.getProductOptionEntity().getProductSize())
                            .price(oie.getOrderPrice())
                            .quantity(oie.getItemAmount())
                            .build();

                    productOptionResponses.add(productOptionResponse);
                    productTotalPrice = productTotalPrice+oie.getOrderPrice()*oie.getItemAmount();
                }

                ProductResponse productResponse = ProductResponse.builder()
                        .productId(gor.get(0).getProductOptionEntity().getProductEntity().getProductId())
                        .productName(gor.get(0).getProductOptionEntity().getProductEntity().getProductName())
                        .productOptionResponse(productOptionResponses)
                        .productImg(gor.get(0).getProductOptionEntity().getProductEntity().getProductPhotoEntities()
                                .stream().filter(imgEntityList->imgEntityList.getPhotoType())
                                .map(imgEntityList->imgEntityList.getPhotoUrl()).toList())
                        .productTotalPrice(productTotalPrice)
                        .build();
                productResponseList.add(productResponse);
                orderTotalPrice = orderTotalPrice+productTotalPrice;
            }

            makingRes.setProductResponseList(productResponseList);
            orderResponses.add(makingRes);
        }
        return orderResponses;
    }
}
