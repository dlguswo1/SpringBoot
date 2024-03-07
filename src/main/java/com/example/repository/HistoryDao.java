package com.example.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.LoginHistory;

public interface HistoryDao extends JpaRepository<LoginHistory, Integer>{
    List<LoginHistory> findLoginHistoriesByMemberIdOrderByCreateDateDesc(String memberID);

    //로그아웃시 해당 회원 ID를 가진 로그인 히스토리 중 가장 최근의 것을 찾아서 반환
    LoginHistory findTopByMemberIdOrderByCreateDateDesc(String memberId);
}
