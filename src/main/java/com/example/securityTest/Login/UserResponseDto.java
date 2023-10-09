package com.example.securityTest.Login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.example.securityTest.user.User;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private String username;

    public static UserResponseDto of(User user) {
        return new UserResponseDto(user.getUsername());
    }
}