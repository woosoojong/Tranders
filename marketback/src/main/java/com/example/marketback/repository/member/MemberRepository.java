package com.example.marketback.repository.member;

import com.example.marketback.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.id = :id")
    Member findByMemberName(String id);

    @Query("select m from Member m where m.id = :id")
    Optional<Member> checkDubById(String id);

    @Query("select m from Member m where m.name = :name and m.phoneNumber = :phoneNumber")
    Optional<Member> checkMember(String name, String phoneNumber);

    @Query("select m from Member m where m.id = :id and m.name = :name and m.phoneNumber = :phoneNumber")
    Optional<Member> findPwById(String id, String name, String phoneNumber);

    @Query("select m from Member m where m.id = :email")
    Member findByEmail(String email);

    @Query("select m from Member m where m.id = :id")
    Optional<Member> findByMember(String email);
}