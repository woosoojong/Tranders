package com.example.marketback.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNo;

    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String region;

    private String address;

    private Float temperature;

    //@Enumerated(EnumType.STRING)
    //private MemberRole managerAuth;

    private Boolean bossAuth;

    //------
    private String roles;

    @CreationTimestamp
    private Timestamp createDate;

    public List<String> getRoleList() {
        if(this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public final Member memberSetting(Member member){
        member.setTemperature(36.5F);
        member.setBossAuth(Boolean.FALSE);

        return member;
    }


}