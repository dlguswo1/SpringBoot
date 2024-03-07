package com.example.controller;

import com.example.model.BoardDto;
import com.example.service.BoardService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class BoardController {

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

    @GetMapping("/boardEdit/{id}")
    public BoardDto boardEdit(@PathVariable(name = "id") Integer id) {
        return boardService.getBoardById(id);
    }

    //글 작성
    @PostMapping("/boardWrite")
    public ResponseEntity<?> boardWrite(@ModelAttribute BoardDto boardDto, HttpSession session, Authentication authentication) throws IOException {
        // 세션에서 memberId와 MEMBERS_ID 가져오기
        Integer members_Id = (Integer) session.getAttribute("members_Id");
        String memberId = (String) session.getAttribute("memberId");

        boardDto.setMembers_Id(members_Id);
        boardDto.setMemberId(memberId);

        boardService.writeSave(boardDto);
//        return ResponseEntity.ok("/main1");
        return ResponseEntity.ok().body(authentication.getName() + "님 게시글 등록 완료");
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

        boardService.updateSave(boardDtoUpdate);
        return ResponseEntity.ok().body(authentication.getName() + "님 게시글 수정 완료");
    }

    @GetMapping("/boardDelete/{id}")
    public String boardDelete(@PathVariable Integer id) {
        boardService.boardDelete(id);
        return "ok";
    }

}
