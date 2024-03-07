package com.example.security;

import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtTokenizer {

//    @Value("${jwt.secretKey}")
//    private final String accessSecret;
//    @Value("${jwt.refreshKey}")
//    private final String refreshSecret;


//    public final static Long ACCESS_TOKEN_EXPIRE_COUNT = 30 * 60 * 1000L; // 30 minutes
//    public final static Long REFRESH_TOKEN_EXPIRE_COUNT = 7 * 24 * 60 * 60 * 1000L; // 7 days

//    public JwtTokenizer(String accessSecret, String refreshSecret) {
//        this.accessSecret = accessSecret;
//        this.refreshSecret = refreshSecret;
//    }

//    /**
//     * AccessToken 생성
//     */
//    public String createAccessToken(String memberId) {
//        return createToken(memberId, accessSecret, ACCESS_TOKEN_EXPIRE_COUNT);
//    }
//
//    /**
//     * RefreshToken 생성
//     */
//    public String createRefreshToken(String memberId) {
//        return createToken(memberId, refreshSecret, REFRESH_TOKEN_EXPIRE_COUNT);
//    }


    // 토큰 생성 메서드
    public static String createToken(String memberId, String secretKey,
                               Long expire) {
        Claims claims = Jwts.claims();

//        claims.put("roles", roles);
//        claims.put("members_Id", id);
        claims.put("memberId", memberId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public static boolean isExpired(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    public static String getMemberId (String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("memberId", String.class);
    }

}