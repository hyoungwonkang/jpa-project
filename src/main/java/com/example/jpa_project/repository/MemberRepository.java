package com.example.jpa_project.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.jpa_project.domain.Member;

import jakarta.persistence.EntityManager;

@Repository
public class MemberRepository {
    
    @Autowired
    private EntityManager em;

    // 회원 등록
    public void save(Member member) {
        em.persist(member);
    }

    // 회원 상세 조회
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findByName(String name) {

        String qlString = "SELECT m FROM Member AS m WHERE m.name = :userName";

        return em.createQuery(qlString, Member.class)
                .setParameter("userName", name)
                .getResultList();
    }

    // 회원 정보 수정
    public void update(Member member) {
        Member foundMember = em.find(Member.class, member.getId());

        if (foundMember == null) {
            throw new IllegalArgumentException(member.getId() + "에 해당하는 회원 정보가 없습니다.");
        }

        foundMember.setName(member.getName()); // 변경 감지 (Dirty Checking)
    }

}
