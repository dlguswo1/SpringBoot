package com.example.security;



import com.example.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
// jwt 인증, 권한 부여
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Value("${jwt.secretKey}")
    private final String secretKey;


    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response,
                                    jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {

        String head = "Bearer ";
        // 막는기능

        // 토큰을 꺼내기
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization : {}", authorization);

        // 토큰이 없을 경우
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.error("authorization : is null or wrong");
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰에서 이름 꺼내기
        String realToken = authorization.split(" ")[1];
        // userName 토큰에서 꺼내기
        String token = JwtTokenizer.getMemberId(realToken, secretKey);
        log.info("memberId : {}", token);

        // 만료되었는지 여부
        if (JwtTokenizer.isExpired(realToken, secretKey)) {
            log.error("token is expired");
            filterChain.doFilter(request, response);
            return;
        }

        Integer tokenId = JwtTokenizer.getMembersId(realToken, secretKey);

        if (tokenId == null) {
            log.error("token's members_Id is null");
            filterChain.doFilter(request, response);
            return;
        }

        List<SimpleGrantedAuthority> authorities;
        if (tokenId == 1) {
            log.info("token members_Id : {}", tokenId);
            authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        else {
            log.info("token members_Id : {}", tokenId);
            authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }

        // 권한 부여
        UsernamePasswordAuthenticationToken authenticationToken =
                // 나중에 db에서 role을 꺼내오자
                // 지금은 하드코딩으로 rule = user 넣어놨음
                new UsernamePasswordAuthenticationToken(token, null, authorities);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
    public Long parserToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.get("members_id", String.class));
    }
}