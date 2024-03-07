package com.example.repository;

import com.example.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembersDao extends JpaRepository<Members, Integer>{

    Optional<Members> findByMemberIdAndMemberPw(String memberId, String memberPw);

    Optional<Members> findByMemberIdAndMemberPwAndRole(String memberId, String memberPw, String role);

    // 멤버 아이디가 존재하는지
    boolean existsByMemberId(String memberId);

    // 닉네임이 존재하는지
//    boolean existsByNickName(String nickName);

    // 로그인
    Optional<Members> findByMemberId(String memberId);

}
