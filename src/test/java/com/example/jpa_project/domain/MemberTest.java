package com.example.jpa_project.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.*;


@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest // @Transactional 포함됨
public class MemberTest {
    @Autowired
    private EntityManager em;

    @Test
    @Rollback(value = false)
    void testSave() {
        // given
        Team team = new Team();
        team.setName("A팀");
        em.persist(team);

        Member member = new Member();
        member.setName("일길동");
        member.setRoleType(RoleType.ADMIN);
        member.setTeam(team);

        // when
        em.persist(member);
        // em.flush(); // 왜 필요 없는지 확인 필요.
        
        // then
        assertThat(member.getId()).isNotNull();
        assertThat(member.getName()).isEqualTo("일길동");
        assertThat(member.getTeam().getName()).isEqualTo("A팀");
    }

}
