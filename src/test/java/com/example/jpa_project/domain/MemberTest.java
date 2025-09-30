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
public class MemberTest {

    @Autowired
    private EntityManager em;

    @Test
    @Rollback(false)
    void testSave() {
        // given, when
        Member member = new Member();
        member.setName("Gabriel");
        member.setAddress(new Address("서울", "강남대로", "10001"));

        em.persist(member);

        // then
        assertThat(member.getAddress().getCity()).isEqualTo("서울");
    }
}
