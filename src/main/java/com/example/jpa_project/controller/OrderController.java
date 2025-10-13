package com.example.jpa_project.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jpa_project.dto.OrderRequestDto;
import com.example.jpa_project.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // 주문등록 요청
    @PostMapping(value = "/orders")
    public ResponseEntity<Map<String, Long>> postOrder(@RequestBody OrderRequestDto orderRequestDto) {
        Long id = orderService.order(orderRequestDto);

        return ResponseEntity.ok().body(Map.of("orderId", id));
    }

    // 주문 취소 요청
    @GetMapping(value = "/orders/{id}")
    public ResponseEntity<String> cancelOrder(@PathVariable(value = "id") Long orderId) {
        orderService.cancelOrder(orderId);

        return ResponseEntity.ok().body("주문이 취소되었습니다.");
    }
    
}
