package com.example.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.example.entity.Board;
import com.example.entity.BoardFile;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto {
    private Integer id;
    private Integer members_Id;
    private String memberId;
    private String category;
    private String title;
    private String content;
    private Timestamp createDate;
    private Timestamp updateDate;
    private String deleteYN;
    private List<MultipartFile> boardFile; // Controller 파일 담는 용도
    private List<String> originFileName; // 원본 파일 이름
    private List<String> storedFileName; // 서버 저장용 파일 이름
    private int fileAttached; // 파일 첨부 여부

    public static BoardDto convertToDto(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .deleteYN(board.getDeleteYN())
                .category(board.getCategory())
                .members_Id(board.getMembers_Id())
                .memberId(board.getMemberId())
                .createDate(board.getCreateDate())
                .updateDate(board.getUpdateDate())
                .fileAttached(board.getFileAttached())
                .build();
    }

    public static BoardDto convertToDto2(Board board) {
        BoardDto boardDto = new BoardDto();
        boardDto.setId(board.getId());
        boardDto.setTitle(board.getTitle());
        boardDto.setContent(board.getContent());
        boardDto.setDeleteYN(board.getDeleteYN());
        boardDto.setCategory(board.getCategory());
        boardDto.setMembers_Id(board.getMembers_Id());
        boardDto.setMemberId(board.getMemberId());
        boardDto.setCreateDate(board.getCreateDate());
        boardDto.setUpdateDate(board.getUpdateDate());
        if(board.getFileAttached() == 0) {
            boardDto.setFileAttached(board.getFileAttached()); // 0일 경우
        }
        else {
            List<String> originFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();
            boardDto.setFileAttached(board.getFileAttached()); // 1일 경우
            //파일 이름을 가져가야함
            // originalFileName, storedFileName : board_file 테이블에 들어있음
            // jpa 특성으로 board와 board_file이 연관되어 있기 떄문에
            // 부모 entity가 자식 entity를 직접적으로 접근할 수 있다.
            // get()으로 인덱스 번호에 접근 파일이 1개이니까 get(0), 여러개 일 경우 반복문
            for (BoardFile boardFile : board.getBoardFileList()) {
//                boardDto.setOriginFileName(board.getBoardFileList().get(0).getOriginFileName());
//                boardDto.setStoredFileName(board.getBoardFileList().get(0).getStoredFileName());
                originFileNameList.add(boardFile.getOriginFileName());
                storedFileNameList.add(boardFile.getStoredFileName());
            }
            boardDto.setOriginFileName(originFileNameList);
            boardDto.setStoredFileName(storedFileNameList);
        }

        return boardDto;
    }
}
