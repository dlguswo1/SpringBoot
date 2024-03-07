package com.example.utll;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class JwtTokenizerTest {

    @Test
    public void createToken() throws Exception{
        String email = "guswo6431@naver.com";
        List<String> roles = List.of("ROLE_USER");
        Integer id = 1;
//        Claims claims = dd;
    }


    // Jwt를 생성하는 부분
//    String JwtToken = Jwts.builder() // builder는 JwtBuilder를 반환
//            .setClaims() // claims가 추가된 빌더를 리턴
//            .setIssuedAt()
//            .setExpiration()
//            .signWith()
//            .compact()
}
