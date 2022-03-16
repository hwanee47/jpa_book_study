package com.study.jpa.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "MEMBER")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    public Member(String name){
        this.name = name;
    }

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    // 연관관계 매핑 (연관관계주인)
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public void setTeam(Team team){

        // 기존 연관관계 제거
        if(this.team != null){
            this.team.getMembers().remove(this);
        }

        this.team = team;
        team.getMembers().add(this);
    }

    @Column(name = "NAME")
    private String name;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STREET")
    private String street;

    @Column(name = "ZIPCODE")
    private String zipCode;

}
