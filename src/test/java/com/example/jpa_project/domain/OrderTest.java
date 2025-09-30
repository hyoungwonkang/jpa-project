package com.example.jpa_project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class OrderTest {
    @Autowired
    private EntityManager em;

    @Test
    @Rollback(false)
    void testSave() {
        // given
        // 회원정보등록
        Member member1 = new Member();
        member1.setName("Frank");
        em.persist(member1);

        // when
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.ORDER);
        order.setMember(member1);
        em.persist(order);

        // then
        assertThat(order.getId()).isNotNull();
        assertThat(order.getMember()).isNotNull();
        assertThat(order.getMember().getName()).isEqualTo("Frank");
        assertThat(member1.getOrders()).hasSize(1);;
    }
}
