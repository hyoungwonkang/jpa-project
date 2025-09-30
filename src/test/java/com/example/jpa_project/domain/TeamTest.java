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
public class TeamTest {
    @Autowired
    private EntityManager em;

    @Test
    @Rollback(false)
    void testSave() {
        // given
        // 쿼리문 insert
        Member member1 = new Member();
        member1.setName("Alice");
        member1.setRoleType(RoleType.ADMIN);
        em.persist(member1);

        // when
        Team team = new Team();
        team.setName("운영팀");
        team.addMember(member1);

        em.persist(team);

        // then
        assertThat(team.getId()).isNotNull();
        assertThat(team.getMembers().get(0).getTeam()).isNull();

    
        // Member member2 = new Member();
        // member2.setName("Bob");
        // member2.setRoleType(RoleType.MEMBER);
        // em.persist(member2);
    
        // // when
        // Team team = new Team();
        // team.setName("기획팀");
        // // team.addMember(member1);
        // // team.addMember(member2);

        // em.persist(team);

        // // then
        // assertThat(team.getId()).isNotNull();
        // // assertThat(team.getMembers().size()).isEqualTo(2);
        // assertThat(team.getMembers()).hasSize(2);
    }
}
