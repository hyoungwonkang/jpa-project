package com.example.jpa_project.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
// @Table(name = "team") // 기본으로 테이블이 만들어짐
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Team {
    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;

    // @OneToMany(fetch = FetchType.LAZY) // 멤버 사용하고 싶을때 레이지로딩
    // @JoinColumn(name = "team_id") // foreign key(FK) 컬럼명 지정
    // private List<Member> members = new ArrayList<>();

    // public void addMember(Member member) {
    //     this.members.add(member);
    // }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    public void addMember(Member member) {
        this.members.add(member);
    }
}
