package com.example.service;

import com.example.entity.Members;
import com.example.model.MembersDto;
import com.example.repository.MembersDao;
import com.example.security.JwtTokenizer;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Optional;

@Service
@NoArgsConstructor(force = true)
public class UserService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    private Long expired = 1000 * 60 * 60L;

    public String login(String memberId, String memberPw, Integer id) {

        return JwtTokenizer.createToken(memberId, id, secretKey, expired);
    }


}
