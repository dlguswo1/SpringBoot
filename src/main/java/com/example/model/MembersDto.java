package com.example.model;

import java.sql.Timestamp;
import com.example.entity.Members;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembersDto {
    private Integer id;
    private String role;
    private String memberId;
    private String memberPw;
    private Timestamp createDate;
    private Timestamp updateDate;
    private String memberOut;
    private String accessToken;
    private String refreshToken;

    public static MembersDto convertToDto(Members members) {
        return MembersDto.builder()
                .id(members.getId())
                .role(members.getRole())
                .memberId(members.getMemberId())
                .memberPw(members.getMemberPw())
                .memberOut(members.getMemberOut())
                .createDate(members.getCreateDate())
                .updateDate(members.getUpdateDate())
                .build();
    }
}
