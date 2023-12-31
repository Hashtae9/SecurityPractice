package com.example.securityTest.user;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// DB에 연동되게끔 하는 곳
@Slf4j
@AllArgsConstructor
@Service("userDetailsService")  // 빈 등록하기
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        log.info("loginform = {}", username);
        log.info("user = {}", user);
        if(user == null){
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }
        return new UserDetailImpl(user);

    }

}