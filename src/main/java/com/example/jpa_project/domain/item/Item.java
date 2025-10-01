package com.example.jpa_project.domain.item;


import java.util.ArrayList;
import java.util.List;

import com.example.jpa_project.domain.OrderItem;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();
}
