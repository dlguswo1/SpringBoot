package com.example.repository;

import com.example.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardDao extends JpaRepository<Board, Integer> {

//    @Query("SELECT b FROM Board b LEFT JOIN FETCH b.board_file ORDER BY b.createDate DESC")
//    List<Board> findTop10ByOrderByCreateDateDescWithFiles();

    List<Board> findTop10ByOrderByCreateDateDesc();

    List<Board> findByOrderByCreateDateDesc();

    List<Board> findTop10ByCategoryOrderByCreateDateDesc(String category);
}
