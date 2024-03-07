package com.example.service;

import java.util.Optional;

import com.example.security.JwtTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.entity.Members;
import com.example.model.MembersDto;
import com.example.repository.MembersDao;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MembersService {
    @Value("${jwt.secretKey}")
    private String secretKey;

    private Long expired = 1000 * 60 * 60L;

    private final MembersDao membersDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public MembersService(MembersDao membersDao, BCryptPasswordEncoder bCryptPasswordEncoder, JwtTokenizer jwtTokenizer) {
        this.membersDao = membersDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    // 메서드 사용대 데이터 삽입
    public void join(MembersDto membersDto) {
        Members members = new Members();
        //System.out.println("service :" + membersDto);
        members.setRole("ROLE_USER");
        members.setMemberId(membersDto.getMemberId());
        //System.out.println("service :" + membersDto.getMemberPw());
        String rawPassword = membersDto.getMemberPw();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        //String password = bCryptPasswordEncoder.encode(membersDto.getMemberPw());
        members.setMemberPw(encPassword);
        members.setMemberOut("N");

        System.out.println("service :" + membersDto);
        membersDao.save(members);
    }

    public Optional<Members> findByMemberIdAndMemberPw(MembersDto membersDto) {
        String memberId = membersDto.getMemberId();
        String memberPw = membersDto.getMemberPw();

        Optional<Members> members = membersDao.findByMemberId(memberId);

        if (members.isPresent()) {
            String encodePw = members.get().getMemberPw();
            boolean test = bCryptPasswordEncoder.matches(memberPw, encodePw);
            if(test) {
                members = membersDao.findByMemberIdAndMemberPw(memberId, encodePw);
                return members;
            }
        }
        return Optional.empty(); // null을 리턴할 경우 nullPointException 에러를 일으킬 수 있음
    }

//    public Optional<Members> findTwoIds(MembersDto membersDto) {
//        String memberId = membersDto.getMemberId();
//        Integer members_Id = membersDto.getId();
//
//        Optional<Members> members = membersDao.findByMemberIdAndId(memberId, members_Id);
//        return Optional.empty();
//    }

//    public String login(String memberId, String password) {
//        // 인증과정
//
//        return JwtTokenizer.createToken(memberId, secretKey, expired);
//    }


}
