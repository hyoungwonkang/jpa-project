package com.example.jpa_project.repository;

import org.springframework.stereotype.Repository;

import com.example.jpa_project.domain.item.Item;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    // 상품 조회
    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }
}
