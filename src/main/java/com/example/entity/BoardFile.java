package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "board_file")
public class BoardFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String originFileName;

    @Column
    private String storedFileName;

    // 보드파일 기준에서 n : 1 관계
    // FetchType.EAGER : 조회할 때 부모자식 다 가져옴
    // FetchType.LAZY : 필요한 상황에 호출해 쓸 수 있음
    // 부모 Entity타입으로 적어줘야함 > 실제로 id 값만 들어감
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    // 변환작업
    // 부모 entity객체를 넘겨줘야함 id 값이 아니라
    public static BoardFile toBoardFileEntity(Board board, String originFileName, String storedFileName) {
        BoardFile boardFile = new BoardFile();
        boardFile.setOriginFileName(originFileName);
        boardFile.setStoredFileName(storedFileName);
        boardFile.setBoard(board);
        return boardFile;
    }
}
