package com.example.security1.model;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity //JPA가 관리한다!
@Data
public class User {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String email;
    private String role; //role.user
    //private Timestamp loginDate; // 로그인할때마다 날짜를 넣어둬서 휴먼계정
    @CreationTimestamp
    private Timestamp createDate;

    public User() { //JPA 기본생성자 필수
    }
}
