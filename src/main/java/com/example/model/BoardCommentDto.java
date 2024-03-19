package com.example.model;

import com.example.entity.BoardComment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BoardCommentDto {
    private Integer id;
    private String commentWriter;
    private String commentContents;
    private Integer boardId;
    private LocalDateTime commentSaveTime;

    //
    public static BoardCommentDto toCommentDto(BoardComment boardComment, Integer boardId) {
        BoardCommentDto boardCommentDto = new BoardCommentDto();
        boardCommentDto.setId(boardComment.getId());
        boardCommentDto.setCommentWriter(boardComment.getCommentWriter());
        boardCommentDto.setCommentContents(boardComment.getCommentContent());
        boardCommentDto.setCommentSaveTime(boardComment.getCreatedTime());
        // service에 있는 메서드에 @Transactional
//        boardCommentDto.setBoardId(boardComment.getBoard().getId());
        boardCommentDto.setBoardId(boardId);
        return boardCommentDto;
    }
}
