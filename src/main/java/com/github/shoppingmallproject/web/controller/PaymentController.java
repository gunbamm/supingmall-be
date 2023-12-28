package com.github.shoppingmallproject.web.controller;

import com.github.shoppingmallproject.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/make-payment")
    public ResponseEntity<String> makePayment(@RequestParam String name,
                                              @RequestParam String ship,
                                              @RequestParam String orderRequest,
                                              @RequestParam int orderTotalPrice,
                                              @RequestParam int productPrice,
                                              @RequestParam int orderPrice,
                                              @RequestParam String photoUrl,
                                              @RequestParam String productName) {
        try {
            paymentService.makePayment(name, ship, orderRequest, orderTotalPrice, productPrice, orderPrice, photoUrl, productName);
            return ResponseEntity.ok("Payment successful.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment failed.");
        }
    }
}
