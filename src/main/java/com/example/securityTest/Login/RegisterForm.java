package com.example.securityTest.Login;

import com.example.securityTest.user.LoginType;
import com.example.securityTest.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class RegisterForm {
//    private Long id;
    private String username;
    private String password;


    @Builder
    public RegisterForm(String username, String password) {
//        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User toEntity(){
        return User.builder()
                //.id(id)
                .username(username)
//                .password(password)
                .password(new BCryptPasswordEncoder().encode(password))
                .role("USER")
                .email("")
                .phone("0000")
                .birth(new Date())
                .enroll_date(LocalDateTime.now())
                .login_type(LoginType.KAKAO)
                .build();
    }

}
