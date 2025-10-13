package com.example.jpa_project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpa_project.domain.Address;
import com.example.jpa_project.domain.Delivery;
import com.example.jpa_project.domain.Member;
import com.example.jpa_project.domain.Order;
import com.example.jpa_project.domain.OrderItem;
import com.example.jpa_project.domain.item.Item;
import com.example.jpa_project.dto.OrderRequestDto;
import com.example.jpa_project.repository.ItemRepository;
import com.example.jpa_project.repository.MemberRepository;
import com.example.jpa_project.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    // 주문 취소
    @Transactional(readOnly = false)
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancelOrder();
    }

    // 주문 생성
    @Transactional(readOnly = false)
    public Long order(OrderRequestDto orderRequestDto) {
        
        Member member = memberRepository.findOne(orderRequestDto.getMemberId());

        Delivery delivery = new Delivery();
        delivery.setAddress(new Address(orderRequestDto.getDelivery().getStreet(),
        orderRequestDto.getDelivery().getCity(),
        orderRequestDto.getDelivery().getZipcode()));

        List<OrderItem> orderItems = new ArrayList<>();

        orderRequestDto.getOrderItems().forEach(orderItem -> {
            Long itemId = orderItem.getItemId();
            Item item = itemRepository.findOne(itemId);
            int orderPrice = orderItem.getOrderPrice();
            int count = orderItem.getCount();

            orderItems.add(OrderItem.createOrderItem(item, orderPrice, count));

        });

        Order order = Order.createOrder(member, delivery, orderItems);

        orderRepository.save(order);

        return order.getId();
    }
}
