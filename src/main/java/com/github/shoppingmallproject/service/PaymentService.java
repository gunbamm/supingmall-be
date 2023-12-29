//package com.github.shoppingmallproject.service;
//
//import com.github.shoppingmallproject.repository.payment.PaymentJpa;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class PaymentService {
//
//    private final PaymentJpa paymentJpa;
//
//    @Autowired
//    public PaymentService(PaymentJpa paymentJpa) {
//        this.paymentJpa = paymentJpa;
//    }
//
//    public void makePayment(String name, String ship, String orderRequest, int orderTotalPrice,
//                            int productPrice, int orderPrice, String photoUrl, String productName) {
//        paymentJpa.makePayment(name, ship, orderRequest, orderTotalPrice, productPrice, orderPrice, photoUrl, productName);
//    }
//}
