package com.example.jpa_project.domain;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.jpa_project.domain.item.Album;
import com.example.jpa_project.domain.item.Book;

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
        Member member = new Member();
        member.setName("Drake");
        em.persist(member);

        Delivery delivery = new Delivery();
        delivery.setStatus(DeliveryStatus.READY);

        // 상품 목록
        Book book = new Book();
        book.setAuthor("다자이 오사무");
        book.setName("인간실격");
        book.setIsbn("1111");
        book.setPrice(10000);
        em.persist(book);

        Album album = new Album();
        album.setName("꽃갈피");
        album.setArtist("아이유");
        em.persist(album);

        // when
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setItem(book);
        orderItem1.setCount(1);
        orderItem1.setOrderPrice(30000);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setItem(album);
        orderItem2.setCount(5);
        orderItem2.setOrderPrice(20000);

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.ORDER);
        order.setMember(member);
        order.setDelivery(delivery);
        em.persist(order);

        // then
        assertThat(order.getId()).isNotNull();
        assertThat(order.getMember()).isNotNull();
        assertThat(order.getDelivery().getId()).isNotNull();
        assertThat(order.getOrderItems()).hasSize(2);
    }
}
