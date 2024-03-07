package com.example.entity;

import java.sql.Timestamp;

import com.example.model.MembersDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Table(name="members")
@AllArgsConstructor
public class Members {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "MEMBERS_ID") // 컬럼명을 새로 만들어 준다.
    private Integer id;
    @Column(nullable=false)
    private String role;
    @Column(nullable=false)
    private String memberId;
    @Column(nullable=false)
    private String memberPw;

    @CreatedDate
    @Column(nullable=false)
    private Timestamp createDate;
    @LastModifiedDate
    @Column(nullable = true, insertable = false)
    private Timestamp updateDate;
    @Column(nullable=false)
    private String memberOut;

    public static Members convertToMembersEntity(MembersDto membersDto) {
        return Members.builder()
                .id(membersDto.getId())
                .memberId(membersDto.getMemberId())
                .build();
    }

}
