package com.example.jpa_project.domain;


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

        assertThat(superCategory.getParent().getName()).isEqualTo("국내 도서");
        assertThat(subCategory.getParent().getName()).isEqualTo("문학");
        assertThat(category.getName()).isEqualTo("소설");
    }
}
