package com.example.service;

import com.example.entity.Board;
import com.example.entity.BoardFile;
import com.example.model.BoardDto;
import com.example.repository.BoardDao;
import com.example.repository.BoardFileDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.model.BoardDto.convertToDto;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    @Autowired
    private BoardDao boardDao;

    @Autowired
    private BoardFileDao boardFileDao;

    public List<BoardDto> getTop10Board() {

        List<Board> top10Boards = boardDao.findTop10ByOrderByCreateDateDesc();

        return top10Boards.stream().map(board -> {
            BoardDto dto = convertToDto(board);

            // BoardFile 정보 가져오기
            List<BoardFile> boardFiles = board.getBoardFileList();
            if (!boardFiles.isEmpty()) {
                BoardFile firstFile = boardFiles.get(0);
                dto.setOriginFileName(firstFile.getOriginFileName());
                dto.setStoredFileName(firstFile.getStoredFileName());
            }

            return dto;
        }).collect(Collectors.toList());
    }

    public List<BoardDto> getTop10ForCategoryBoard() {
        List<BoardDto> boardDtoList = new ArrayList<>();

        List<Board> korList = boardDao.findTop10ByCategoryOrderByCreateDateDesc("korean");
        for (Board board : korList) {
            boardDtoList.add(BoardDto.convertToDto2(board));
        }

        List<Board> engList = boardDao.findTop10ByCategoryOrderByCreateDateDesc("english");
        for (Board board : engList) {
            boardDtoList.add(BoardDto.convertToDto2(board));
        }

        List<Board> mathList = boardDao.findTop10ByCategoryOrderByCreateDateDesc("math");
        for (Board board : mathList) {
            boardDtoList.add(BoardDto.convertToDto2(board));
        }

        List<Board> sciList = boardDao.findTop10ByCategoryOrderByCreateDateDesc("science");
        for (Board board : sciList) {
            boardDtoList.add(BoardDto.convertToDto2(board));
        }

        return boardDtoList;
    }

    @Transactional
    public List<BoardDto> getAllBoard() {
        List<Board> allBoards = boardDao.findByOrderByCreateDateDesc();

        return allBoards.stream().map(board -> {
            BoardDto dto = convertToDto(board);

            // BoardFile 정보 가져오기
            List<BoardFile> boardFiles = board.getBoardFileList();
            if (!boardFiles.isEmpty()) {
                BoardFile firstFile = boardFiles.get(0);
                dto.setOriginFileName(firstFile.getOriginFileName());
                dto.setStoredFileName(firstFile.getStoredFileName());
            }

            return dto;
        }).collect(Collectors.toList());
    }

    // builder 이용해 데이터 삽입
    public BoardDto getBoardById(Integer id) {
        Optional<Board> boardEntity = boardDao.findById(id);

        if(!boardEntity.isPresent()) {
            return null;
        }

        Board board = boardEntity.get();
        BoardDto boardDto = convertToDto(board);

        List<String> fileNames  = board.getBoardFileList().stream()
                .map(BoardFile::getStoredFileName)
                .collect(Collectors.toList());
        boardDto.setStoredFileName(fileNames.toString());

        return boardDto;

    }

    @Transactional
    public void writeSave(BoardDto boardDto) throws IOException {
        // 파일 첨부 여부에 따라 로직 분리
        if(boardDto.getBoardFile() == null || boardDto.getBoardFile().isEmpty()) {
            // 첨부 파일 없음
            Board board = Board.toSaveEntity(boardDto);
            boardDao.save(board);
            // 그대로 진행
        }
        else {
            // 첨부파일 있음
            /*
                1. DTO에 담긴 파일을 꺼냄
                2. 파일의 이름 가져옴
                3. 서버 저장용 이름으로 수정
                4. 저장경로 설정
                5. 해당 경로에 파일 저장
                6. board_table에 해당 데이터 save처리
                7. board_file_table에 해당 데이터 save처리
            */

            MultipartFile boardFile = boardDto.getBoardFile(); // 1.
            String originFileName = boardFile.getOriginalFilename(); // 2.
            String storedFileName = System.currentTimeMillis() + "_" + originFileName; // 3.
            String savePath = "C:/spring_img/" + storedFileName; // 4.
            boardFile.transferTo(new File(savePath)); // 5.
            Board board = Board.convertToFileEntity(boardDto);

            // .getId() : 부모 자식관계를 맺어놓고 자식 테이블에서는 부모가
            // 어떤 번호인지 반드시 필요 > 부모의 게시글에 대한 pk값이 필요
            // 단순히 pk값으로 쓰지는 않고 자식 entity 기준으로 Board entity 선언
            // 참조 관계를 맺었을 때 pk값 말고 entity 값을 전달해줘야하는 특징
            Integer saveId = boardDao.save(board).getId();
            // Optional 생략
            // 부모 entity자체를 자식 entity객채에 저장할 때
            // 부모 entity자체가 전달되어야함 그래서 다시 가져옴 Board boardEntity
            Board boardEntity = boardDao.findById(saveId).get();

            BoardFile boardFileEntity = BoardFile.toBoardFileEntity(boardEntity, originFileName, storedFileName);
            boardFileDao.save(boardFileEntity);
        }
    }

    @Transactional
    public void updateSave(BoardDto boardDtoUpdate) throws IOException {
        if (boardDtoUpdate.getBoardFile() == null || boardDtoUpdate.getBoardFile().isEmpty()) {
            // 첨부 파일 없음
            Board board = Board.convertToEntityUpdate(boardDtoUpdate);
            boardDao.save(board);
            // 그대로 진행
        }else {

            // 첨부파일 있음
            /*
                1. DTO에 담긴 파일을 꺼냄
                2. 파일의 이름 가져옴
                3. 서버 저장용 이름으로 수정
                4. 저장경로 설정
                5. 해당 경로에 파일 저장
                6. board_table에 해당 데이터 save처리
                7. board_file_table에 해당 데이터 save처리
            */

            // 기존 이미지 삭제


            MultipartFile boardFile = boardDtoUpdate.getBoardFile(); // 1.
            String originFileName = boardFile.getOriginalFilename(); // 2.
            String storedFileName = System.currentTimeMillis() + "_" + originFileName; // 3.
//            String savePath = "C:/spring_img/" + storedFileName; // 4.
            String savePath = "/usr/project/java/spring_img/" + storedFileName; // 4.
            boardFile.transferTo(new File(savePath)); // 5.
            Board board = Board.convertToFileUpdateEntity(boardDtoUpdate);

            // .getId() : 부모 자식관계를 맺어놓고 자식 테이블에서는 부모가
            // 어떤 번호인지 반드시 필요 > 부모의 게시글에 대한 pk값이 필요
            // 단순히 pk값으로 쓰지는 않고 자식 entity 기준으로 Board entity 선언
            // 참조 관계를 맺었을 때 pk값 말고 entity 값을 전달해줘야하는 특징
            Integer saveId = boardDao.save(board).getId();
            // Optional 생략
            // 부모 entity자체를 자식 entity객채에 저장할 때
            // 부모 entity자체가 전달되어야함 그래서 다시 가져옴 Board boardEntity
            Board boardEntity = boardDao.findById(saveId).get();

            BoardFile boardFileEntity = BoardFile.toBoardFileEntity(boardEntity, originFileName, storedFileName);
            boardFileDao.save(boardFileEntity);
        }

    }

    public void boardDelete(Integer id) {
        boardDao.deleteById(id);
    }
}
