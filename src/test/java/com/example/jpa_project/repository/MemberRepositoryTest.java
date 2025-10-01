package com.example.jpa_project.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import com.example.jpa_project.domain.Address;
import com.example.jpa_project.domain.Member;

import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.*;

@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(MemberRepository.class)
@DataJpaTest
// @SpringBootTest
public class MemberRepositoryTest {

    // 엔티티 매니저는 사용 안함.

    @Autowired // 엔티티 사용하니 필요.
    private MemberRepository memberRepository;

    @Test
    void testFindByName() {

    }

    @Test
    void testFindOne() {
        // given
        Long memberId = 1L;
        // when
        Member member = memberRepository.findOne(memberId);

        // then
        assertThat(member).isNotNull();
        assertThat(member.getName()).isEqualTo("Alice");
        log.info("id: {}, name: {}", member.getId(), member.getName());
    }

    @Test
    @Rollback(false)
    void testSave() {
        // given
        // when

        Member member = new Member();
        member.setName("Alice");
        member.setAddress(new Address("서울", "강남대로", "1111"));
        memberRepository.save(member);

        // then
        assertThat(member.getId()).isNotNull();
        assertThat(member.getName()).isEqualTo("Alice");
    }

    @Test
    @Rollback(false)
    void testUpdate() {
        // given
        Member member = new Member();
        member.setId(1L);
        member.setName("Bob");

        // when
        memberRepository.update(member);
        // then
        assertThat(member.getName()).isEqualTo("Bob");
    }
}
