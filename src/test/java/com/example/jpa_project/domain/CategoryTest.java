package com.example.jpa_project.domain;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.jpa_project.domain.item.Book;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class CategoryTest {
    @Autowired
    private EntityManager em;
    
    @Test
    @Rollback(false)
    void testSave() {
        // given
        // when
        Category superCategory = new Category();
        superCategory.setName("국내 도서");
        em.persist(superCategory);

        Category subCategory = new Category();
        subCategory.setName("문학");
        superCategory.addCategory(subCategory);
        em.persist(subCategory);

        Category category = new Category();
        category.setName("소설");
        subCategory.addCategory(category);
        em.persist(category);

        // then
        assertThat(superCategory.getId()).isNotNull();
        assertThat(subCategory.getId()).isNotNull();
        assertThat(category.getId()).isNotNull();

        // assertThat(superCategory.getParent().getName()).isEqualTo("국내 도서"); // 부모가 없으므로 NPE 발생
        assertThat(subCategory.getParent().getName()).isEqualTo("국내 도서");
        assertThat(category.getParent().getName()).isEqualTo("문학");
        assertThat(category.getName()).isEqualTo("소설");
    }

    @Test
    @Rollback(false)
    void testSave1() {
        // given
        Category category = em.find(Category.class, 1L);
        log.info("category name : {}", category.getName());

        Book book1 = new Book();
        book1.setName("토지");
        book1.setAuthor("박경리");
        book1.setIsbn("1234");
        book1.setPrice(20000);
        em.persist(book1);

        Book book2 = new Book();
        book2.setName("데미안");
        book2.setAuthor("헤르만 헤세");
        book2.setIsbn("5678");
        book2.setPrice(15000);
        em.persist(book2);

        // when
        category.addItem(book1);
        category.addItem(book2);
        
        // then
        assertThat(category.getItems()).hasSize(2);

        category.getItems().forEach(item -> {
            log.info("item.name: {}", item.getName());
            item.getCategories().forEach(cate -> {
                log.info("category : {}", cate.getName());
            });
        });
    }
}
