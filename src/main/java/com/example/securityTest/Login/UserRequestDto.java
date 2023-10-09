package com.example.securityTest.Login;


import com.example.securityTest.user.LoginType;
import com.example.securityTest.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
public class UserRequestDto {

    //private Long id;
    private String username;
    private String password;
    private String phone;
    private String email;


    @Builder
    public UserRequestDto(String username, String password, String phone, String email) {
        //this.id = id;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
    }

    public User toEntity(){
        return User.builder()
                .username(username)
                .password(new BCryptPasswordEncoder().encode(password))
                .email(email)
                .role("USER")
                .phone(phone)
                .birth(new Date())
                .enroll_date(LocalDateTime.now())
                .login_type(LoginType.REGULAR)
                .build();
    }
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
