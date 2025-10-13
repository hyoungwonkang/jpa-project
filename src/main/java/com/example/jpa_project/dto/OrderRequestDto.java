package com.example.jpa_project.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    
    private Long memberId;
    private Address delivery;
    private List<OrderItemRequest> orderItems;
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItemRequest {
        private Long itemId;
        private int orderPrice;
        private int count;
        
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Address {
        private String city;
        private String street;
        private String zipcode; 
        
    }
}
