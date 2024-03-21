package com.example.controller;

import com.example.model.BoardCommentDto;
import com.example.model.BoardDto;
import com.example.security.JwtTokenizer;
import com.example.service.BoardService;

import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BoardController {
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Autowired
    private BoardService boardService;

    @GetMapping("/main1")
    public List<BoardDto> main1() {
        return boardService.getTop10Board();
    }

    @GetMapping("/main2")
    public List<BoardDto> main2() {
        return boardService.getTop10ForCategoryBoard();
    }

    @GetMapping("/main3")
    public List<BoardDto> main3() {
        return boardService.getAllBoard();
    }

    @GetMapping("/board/{id}")
    public List<BoardCommentDto> board(@PathVariable(name = "id") Integer id) {
        return boardService.findAll(id);
    }

    @GetMapping("/boardEdit/{id}")
    public BoardDto boardEdit(@PathVariable(name = "id") Integer id) {
        return boardService.getBoardById(id);
    }

    //글 작성
    @PostMapping("/boardWrite")
    public ResponseEntity<?> boardWrite(@ModelAttribute BoardDto boardDto, HttpSession session, jakarta.servlet.http.HttpServletRequest request, Authentication authentication) throws IOException {
        // 세션에서 memberId와 MEMBERS_ID 가져오기
//        Integer members_Id = (Integer) session.getAttribute("members_Id");
//        String memberId = (String) session.getAttribute("memberId");
//        String memberId = authentication.getName();

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        String realtoken = token.split(" ")[1];

        Integer members_Id = JwtTokenizer.getMembersId(realtoken, secretKey);
        String memberId = JwtTokenizer.getMemberId(realtoken, secretKey);

        boardDto.setMembers_Id(members_Id);
        boardDto.setMemberId(memberId);

        boardService.writeSave(boardDto);
//        return ResponseEntity.ok("/main1");
        return ResponseEntity.ok().body(authentication.getName() + "님 게시글 등록 완료. ㅇㅇ");
    }

    // 글 수정
    @PostMapping("/boardEdit/{id}")
    public ResponseEntity<?> boardEdit(@PathVariable Integer id, @ModelAttribute BoardDto boardDto, Authentication authentication) throws IOException {

        BoardDto boardDtoUpdate = boardService.getBoardById(id);
        boardDtoUpdate.setId(boardDto.getId());
        boardDtoUpdate.setCategory(boardDto.getCategory());
        boardDtoUpdate.setTitle(boardDto.getTitle());
        boardDtoUpdate.setContent(boardDto.getContent());
        boardDtoUpdate.setUpdateDate(boardDtoUpdate.getUpdateDate());

        boardService.updateSave(boardDtoUpdate, id);
        return ResponseEntity.ok().body(authentication.getName() + "님 게시글 수정 완료");
    }

    @GetMapping("/boardDelete/{id}")
    public String boardDelete(@PathVariable Integer id) {
        boardService.boardDelete(id);
        return "ok";
    }

    @GetMapping("/commentDelete/{id}")
    public String commentDelete(@PathVariable Integer id) {
        boardService.commentDelete(id);
        return "ok";
    }

    @GetMapping("/test1")
    public String test() {
        return "test! test! test!";
    }

//    @GetMapping("/admintest")
//    public ResponseEntity<String> adminTest() {
//        return ResponseEntity.ok().body("adminTest 완료");
//    }

//    @GetMapping("/admin")
//    public String adminTest() {
//        return "adminTest 완료";
//    }

    @PostMapping("/comments")
    public ResponseEntity comments(@RequestBody BoardCommentDto boardCommentDto) {
        System.out.println("commemt : " + boardCommentDto);
        Integer saveResult = boardService.saveComments(boardCommentDto);

        if (saveResult != null) {
            // 작성 성공 하고 save 하고 끝내면 안됨
            // 작성하고 댓글 목록을 가져와서 리턴
            // 댓글 목록 : 해당 게시글의 댓글 전체 board_id 기준
            List<BoardCommentDto> boardCommentDtoList =  boardService.findAll(boardCommentDto.getBoardId());
            return new ResponseEntity<>(boardCommentDtoList, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String filename) throws IOException {
        // 파일 경로 설정
        String directory = "c:/spring_img";
        Path file = Paths.get(directory, filename);

        // 파일을 byte 배열로 읽어옴
        byte[] data = Files.readAllBytes(file);

        // 파일 다운로드를 위한 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", filename);
        headers.setContentLength(data.length);

        // ResponseEntity를 통해 파일과 헤더를 클라이언트에 전송
        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }

}
