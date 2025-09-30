package com.example.jpa_project.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    @Column(name = "member_id")
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}
