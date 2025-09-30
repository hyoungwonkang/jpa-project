package com.example.jpa_project.domain.item;
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
}
