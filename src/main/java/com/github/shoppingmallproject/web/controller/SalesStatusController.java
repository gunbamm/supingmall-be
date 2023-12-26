package com.github.shoppingmallproject.web.controller;

import com.github.shoppingmallproject.repository.userDetails.CustomUserDetails;
import com.github.shoppingmallproject.service.SalesStatusService;
import com.github.shoppingmallproject.web.dto.SalesStatusDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api")
public class SalesStatusController {

    private final SalesStatusService salesStatusService;

    @GetMapping("/account/my-page/sales-status")
    public ResponseEntity<List<SalesStatusDTO>> findAllSalesStatus(@RequestHeader("Token") String token,
                                                                   @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }

        if (!customUserDetails.getAuthorities().contains("ROLE_SELLER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        List<SalesStatusDTO> salesStatus = salesStatusService.findAllSalesStatus();
        return ResponseEntity.ok(salesStatus);
    }
}
