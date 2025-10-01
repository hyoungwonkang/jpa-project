package com.example.jpa_project.domain.item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ItemTest {
    @Autowired
    private EntityManager em;

    @Test
    @Rollback(false)
    void testSave() {
        // given, when
        Book book = new Book();
        book.setName("인간 실격");
        book.setPrice(10000);
        book.setIsbn("1111");
        book.setAuthor("다자이 오사무");
        em.persist(book);

        // Album album = new Album();
        // album.setName("Dive Baby, Dive");
        // album.setPrice(15000);
        // album.setArtist("누구지");
        // em.persist(album);

        // then
        assertThat(book.getId()).isNotNull();
        assertThat(book.getName()).isEqualTo("인간 실격");
        // assertThat(album.getId()).isNotNull();
        // assertThat(album.getName()).isEqualTo("Dive Baby, Dive");
    }

    @Test
    @Rollback(false)
    void testRemoveStock() {
        // given
        Album album = new Album();
        album.setName("Dive Baby Dive");
        album.setPrice(15000);
        album.setArtist("Glen Check");
        album.setStockQuantity(100);
        em.persist(album);

        // when
        Album found = em.find(Album.class, album.getId());
        found.removeStock(1);

        // then
        assertThat(found.getName()).isEqualTo("Dive Baby Dive");
        assertThat(found.getStockQuantity()).isEqualTo(99);
    }

    @Test
    @Rollback(false)
    void testRemoveStock1() {
        // given
        Album album = new Album();
        album.setName("Dive Baby Dive");
        album.setPrice(15000);
        album.setArtist("Glen Check");
        album.setStockQuantity(100);
        em.persist(album);

         Album found = em.find(Album.class, album.getId());

        // when
        assertThatThrownBy(() -> {
            found.removeStock(200);
        }).isInstanceOf(RuntimeException.class)
            .hasMessageContaining("재고가 부족합니다");
    }

    @Test
    @Rollback(false)
    void testUpdate() {
        // given
        // 수정하기 전 조회먼저 필요
        Book book = em.find(Book.class, 4L);

        // when
        book.change("채식주의자", 12000, 1000, "한강", "2222");

        // then
        assertThat(book.getName()).isEqualTo("채식주의자");
        assertThat(book.getPrice()).isEqualTo(12000);
        assertThat(book.getStockQuantity()).isEqualTo(1000);
        assertThat(book.getAuthor()).isEqualTo("한강");
        assertThat(book.getIsbn()).isEqualTo("2222");
    }
}
