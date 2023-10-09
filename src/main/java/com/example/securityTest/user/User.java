package com.example.securityTest.user;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Entity //jpa entity 선언
@Getter @Setter
@Table(name="user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class User {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;
    @Column
    private String role;
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    @Column
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime enroll_date;

    @Column
    @Enumerated(EnumType.STRING) //enum의 String 값 자체가 db에 저장
    private LoginType login_type;

    @Builder
    public User(String password, String username, String phone, String email, Date birth,
                LocalDateTime enroll_date, LoginType login_type, String role) {
        //this.id = id;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.birth = birth;
        this.enroll_date = enroll_date;
        this.login_type = login_type;
        this.role = role;
    }

    public void updatePassword(String password){
        this.password = password;
    }
}
