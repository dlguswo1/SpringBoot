package com.example.entity;

import com.example.model.BoardCommentDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "board_comment")
public class BoardComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String commentWriter;

    @Column
    private String commentContent;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdTime;

    /* Board:Comment = 1:B */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public static BoardComment toSaveEntity(BoardCommentDto boardCommentDto, Board board) {
        BoardComment boardComment = new BoardComment();
        boardComment.setCommentWriter(boardCommentDto.getCommentWriter());
        boardComment.setCommentContent(boardCommentDto.getCommentContents());
        boardComment.setBoard(board);
        return boardComment;
    }
}
