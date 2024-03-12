package com.example.controller;

import java.util.*;

import com.example.entity.RefreshToken;
import com.example.security.JwtTokenizer;
import com.example.service.RefreshTokenService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Members;
import com.example.model.MembersDto;
import com.example.service.HistoryService;
import com.example.service.MembersService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MemberController {
    @Autowired
    private MembersService membersService;

    @Autowired
    private UserService userService;

    @Autowired
    private HistoryService historyService;
    private final JwtTokenizer jwtTokenizer;
    private final RefreshTokenService refreshTokenService;

    public MemberController(JwtTokenizer jwtTokenizer, RefreshTokenService refreshTokenService) {
        this.jwtTokenizer = jwtTokenizer;
        this.refreshTokenService = refreshTokenService;
    }


    @GetMapping("/join")
    public String join() {
        return "join";
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody MembersDto membersDto) {
        membersService.join(membersDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MembersDto membersDto, HttpServletRequest httpServletRequest, HttpSession session) {

        Optional<Members> members = membersService.findByMemberIdAndMemberPw(membersDto);

        if (!members.isPresent()) {

            return ResponseEntity.badRequest().body("아이디 또는 비밀번호를 다시 입력하세요.");
        }else if(members.get().getMemberOut().equals("Y")) {

            return ResponseEntity.badRequest().body("이미 탈퇴한 회원입니다.");
        }

        List<String> roles = Collections.singletonList(members.get().getRole());
        Integer members_Id = members.get().getId();

        // JWT토큰 생성. jwt 라이브러리를 이용하여 생성
        // MEMBERS_ID, memberId, role 3개 보낼거임
        String accessToken = userService.login(membersDto.getMemberId(),membersDto.getMemberPw(), members_Id);
//        String refreshToken = jwtTokenizer.createRefreshToken(members.get().getMemberId());


        RefreshToken refreshTokenEntity = new RefreshToken();
//        refreshTokenEntity.setValue(refreshToken);
        refreshTokenEntity.setMemberId(members.get().getId());
        refreshTokenService.addRefreshToken(refreshTokenEntity);

        httpServletRequest.getSession().invalidate(); // 기존 세션 파기
        session = httpServletRequest.getSession(true); // 생성
        // 세션에 memberId를 넣어줌
        session.setAttribute("memberId", members.get().getMemberId());
        session.setAttribute("members_Id", members.get().getId());
        session.setMaxInactiveInterval(60 * 30); // 세션 유지 시간 30분으로 설정
        // 세션 추가
        sessionList.put(session.getId(), session);
        // 저장
        historyService.saveLogOnLogin("login", membersDto.getMemberId());

        // 성공시 응답 반환\

        String memberId = members.get().getMemberId();

        MembersDto membersDtoEntity = MembersDto.builder()
                .accessToken(accessToken)
//                .refreshToken(refreshToken)
                .id(members.get().getId())
                .memberId(memberId)
                .role(members.get().getRole())
//                .memberOut(members.get().getMemberOut())
//                .memberPw(members.get().getMemberPw())
//                .createDate(members.get().getCreateDate())
//                .updateDate(members.get().getUpdateDate())
                .build();

//        ResponseEntity<String> token = ResponseEntity.ok().body(userService.login(membersDto.getMemberId(),membersDto.getMemberPw()));
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + token);
//
//        return ResponseEntity.ok().headers(headers).body("Login successful");
//        return ResponseEntity.ok().body(userService.login(membersDto.getMemberId(),membersDto.getMemberPw()));

        return ResponseEntity.ok().body(membersDtoEntity);
    }

    @GetMapping("/logoutReact")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if(session != null) {
            String memberId = session.getAttribute("memberId").toString();
            historyService.saveLogOnLogin("logout", memberId);

            sessionList.remove(session.getId());
            session.invalidate();
        }

        return ResponseEntity.ok().build();
    }

    // 세션 리스트 확인용 코드
    public static Hashtable sessionList = new Hashtable();

    @GetMapping("/session-list")
    @ResponseBody
    public Map<String, String> sessionList() {
        Enumeration elements = sessionList.elements();
        Map<String, String> lists = new HashMap<>();
        while (elements.hasMoreElements()) {
            HttpSession session = (HttpSession) elements.nextElement();
            lists.put(session.getId(), String.valueOf(session.getAttribute("memberId")));
        }
        return lists;
    }

}