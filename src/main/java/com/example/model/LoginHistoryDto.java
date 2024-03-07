package com.example.model;

import java.sql.Timestamp;
import java.util.Date;

import com.example.entity.LoginHistory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginHistoryDto {
    private Integer id;
    private String memberId;
    private Timestamp createDate;
    private Date expiredDate;

    public static LoginHistoryDto fromEntity(LoginHistory loginHistory) {
        return LoginHistoryDto.builder()
                .id(loginHistory.getId())
                .memberId(loginHistory.getMemberId())
                .createDate(loginHistory.getCreateDate())
                .expiredDate(loginHistory.getExpiredDate())
                .build();
    }
}