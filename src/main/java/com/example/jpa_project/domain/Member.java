package com.example.jpa_project.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "member")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Member {
    @Id // Primary Key. 컬럼과 매핑.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본은 AUTO.
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING) // 'ADMIN', 'MEMBER'
    private RoleType roleType;

    // @ManyToOne
    // // 일단은 단방향
    // @JoinColumn(name = "team_id") // foreign key(FK) 컬럼명 지정
    // private Team team; // 데이터베이스 FK로 만들기 위한 레퍼런스 매핑.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    // 연관관계 편의 메서드
    public void setTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    };
}
