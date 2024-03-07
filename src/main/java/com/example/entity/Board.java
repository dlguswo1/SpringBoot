package com.example.entity;

import com.example.model.BoardDto;
import com.example.model.MembersDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"members"})
@EntityListeners(AuditingEntityListener.class)
@Table(name = "board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Integer id;
    @Column(name = "MEMBERS_ID", nullable = false)
    private Integer members_Id; //fk연결
    @Column(name = "memberId", nullable = false)
    private String memberId; //fk연결
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @CreatedDate
    @Column(name = "createDate")
    private Timestamp createDate = Timestamp.valueOf(LocalDateTime.now());
    @LastModifiedDate
    @Column(nullable = true, insertable = false)
    private Timestamp updateDate;
    @Column(nullable = false)
    private String deleteYN; //삭제 여부
    @Column(nullable = false)
    private int fileAttached; // 1 or 0

    // BoardFile Entity와의 관계 아까 이 친구는 @MantToOne였으니
    // mappedBy : 어떤 것과 매칭 할거냐
    // cascade = CascadeType.REMOVE, orphanRemoval = true : onDeleteCasCade : 부모가 삭제 되면 자식도 같이 삭제
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<BoardFile> boardFileList = new ArrayList<>();


    // 입력한 정보를 받아와서 Entity에 받아올 때
    // 파일이 없을 떄
    public static Board convertToEntity(BoardDto boardDto) {
        return Board.builder()
                .id(boardDto.getId())
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .deleteYN("N")
                .category(boardDto.getCategory())
                .members_Id(boardDto.getMembers_Id())
                .memberId(boardDto.getMemberId())
                .createDate(boardDto.getCreateDate())
                .updateDate(boardDto.getUpdateDate())
                .fileAttached(0)
                .build();
    }

    public static Board toSaveEntity(BoardDto boardDto) {
        Board board = new Board();
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        board.setDeleteYN("N");
        board.setCategory(boardDto.getCategory());
        board.setMembers_Id(boardDto.getMembers_Id());
        board.setMemberId(boardDto.getMemberId());
        board.setCreateDate(boardDto.getCreateDate());
        board.setUpdateDate(boardDto.getUpdateDate());
        board.setFileAttached(0);
        return board;
    }

    // 업데이틀 하는 메서드 작성 메서드랑 동일함
    // 파일이 없을 때
    public static Board convertToEntityUpdate(BoardDto boardDto) {
            Board board = new Board();
            board.setId(boardDto.getId());
            board.setTitle(boardDto.getTitle());
            board.setContent(boardDto.getContent());
            board.setDeleteYN("N");
            board.setCategory(boardDto.getCategory());
            board.setMembers_Id(boardDto.getMembers_Id());
            board.setMemberId(boardDto.getMemberId());
            board.setCreateDate(boardDto.getCreateDate());
            board.setUpdateDate(boardDto.getUpdateDate());
            board.setFileAttached(0);
        return board;
    }

    // 파일이 있을 경우 저장 메서드
    public static Board convertToFileEntity(BoardDto boardDto) {
        return Board.builder()
                .id(boardDto.getId())
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .deleteYN("N")
                .category(boardDto.getCategory())
                .members_Id(boardDto.getMembers_Id())
                .memberId(boardDto.getMemberId())
                .createDate(boardDto.getCreateDate())
                .updateDate(boardDto.getUpdateDate())
                .fileAttached(1) // 파일이 있으니까 1
                .build();
    }

    public static Board convertToFileUpdateEntity(BoardDto boardDto) {
        return Board.builder()
                .id(boardDto.getId())
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .deleteYN("N")
                .category(boardDto.getCategory())
                .members_Id(boardDto.getMembers_Id())
                .memberId(boardDto.getMemberId())
                .createDate(boardDto.getCreateDate())
                .updateDate(boardDto.getUpdateDate())
                .fileAttached(1) // 파일이 있으니까 1
                .build();
    }
}
