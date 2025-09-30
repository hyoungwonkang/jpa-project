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
        // given
        Team team = new Team();
        team.setName("경호팀");
        em.persist(team);

        Profile profile = new Profile();
        profile.setBio("자기소개입니다.");

        // when
        Member member = new Member();
        member.setName("Irene");
        member.setRoleType(RoleType.MEMBER);
        member.setTeam(team);
        member.setProfile(profile); // Profile 테스트
        em.persist(member);

        // then
        assertThat(member.getId()).isNotNull();
        assertThat(member.getTeam().getId()).isNotNull();
        assertThat(member.getTeam().getName()).isEqualTo("경호팀");
        assertThat(member.getProfile()).isNotNull();
    }

    @Test
    @Rollback(false)
    void testUpdate() {
        // given
        Team team = em.find(Team.class, 1L);
        log.info("team.id : {}", team.getId());
        log.info("team.name : {}", team.getName());

        // when
        Member member = em.find(Member.class, 1L);
        team.setName("홍보팀"); // 더티 체킹. 스냅샷과 다르면 update 쿼리문 실행
        member.setTeam(team);

        // then
        assertThat(member.getTeam().getName()).isEqualTo("홍보팀");
    }
}
