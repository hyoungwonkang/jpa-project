package com.example.jpa_project.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "order_date")
    private LocalDateTime orderDate;
    
    @Enumerated(EnumType.STRING) // "CANCEL", "ORDER"
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderItem> orderItems = new ArrayList<>();

    // 연관 관계 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }
    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    // 연관 관계 메서드(OrderItem 관련)
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    // 주문 생성
    // (스태틱) 팩토리 메서드 : 복잡한 것은 생성자보다 팩토리 메서드가 나음(이펙티브 자바 책 아이템1). 복잡한 로직과 연관관계메서드 같이 사용하기에.
    public static Order createOrder(Member member, Delivery delivery, List<OrderItem> orderItems) {

        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        // orderItems.forEach(orderItem -> {
        //     order.addOrderItem(orderItem);
        // });
        orderItems.forEach(order::addOrderItem);

        return order;
    }

    // 주문 취소
    public void cancelOrder() {
        if (delivery.getStatus() == DeliveryStatus.COMPLETED) {
            throw new IllegalStateException("이미 배송완료된 상품은 주문 취소가 불가능합니다"); 
        }

        orderItems.forEach(OrderItem::cancel);

        this.setStatus(OrderStatus.CANCEL);
    }

}
