package com.example.repository;

import com.example.entity.Board;
import com.example.entity.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardCommentDao extends JpaRepository<BoardComment, Integer> {
    // select * from board_comment where board_id=? order by desc;
    List<BoardComment> findAllByBoardOrderByIdDesc(Board board);

    @Query("SELECT bc FROM BoardComment bc WHERE bc.board.id = :board_id ORDER BY bc.createdTime DESC")
    List<BoardComment> findAllByBoardIdOrderByCreatedTimeDesc(Integer board_id);
}
