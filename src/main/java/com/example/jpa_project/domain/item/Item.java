package com.example.jpa_project.domain.item;


import java.util.ArrayList;
import java.util.List;

import com.example.jpa_project.domain.Category;
import com.example.jpa_project.domain.OrderItem;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Inheritance(strategy = InheritanceType.JOINED) // JOINED는 item 테이블의 PK를 album이나 book 테이블의FK로 사용
@DiscriminatorColumn(name = "dtype")
public abstract class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    // 비즈니스 메서드
    // 상품 재고량 증가
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }
    // 상품 재고량 감소
    public void removeStock(int quantity) {
        int restQuantity = this.stockQuantity - quantity;
        if (quantity > restQuantity) {
            throw new RuntimeException("재고가 부족합니다.");
        }
        this.stockQuantity -= quantity;
    }

    public void change(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
