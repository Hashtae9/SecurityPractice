package com.example.securityTest.Config;

import com.example.securityTest.jwt.JwtAccessDeniedHandler;
import com.example.securityTest.jwt.JwtAuthenticationEntryPoint;
import com.example.securityTest.jwt.TokenProvider;
import com.example.securityTest.user.CustomUserDetailsService;
import com.example.securityTest.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
@Slf4j
@EnableWebSecurity
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private final CustomUserDetailsService customUserDetailsService;

    //    @Autowired
//    private final UserService userService;

//
    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) //csrf끄기
                .cors(withDefaults()) // cors설정
                // 토큰을 활용하면 세션이 필요 없으므로 STATELESS로 설정하여 Session을 사용하지 않는다.
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(authorize -> authorize
                                .frameOptions(withDefaults()))
                .exceptionHandling((exception) -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler))
                .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/registerUser", "/*", "/auth/**", "/api/**", "/**").permitAll()
                .requestMatchers("/user/user").hasRole("USER")
                .requestMatchers("/admin/admin").hasRole("ADMIN")
                .anyRequest().authenticated())
                // form 기반의 로그인에 대해 비활성화 한다.
                .formLogin(Customizer.withDefaults())
                .apply(new JwtSecurityConfig(tokenProvider));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authentication -> {
            // 사용자 인증을 직접 처리하는 로직을 여기에 작성
            // 사용자 정보를 customUserDetailsService를 사용하여 가져오고, 비밀번호 검증을 직접 수행
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(authentication.getName());
            if (!getPasswordEncoder().matches(authentication.getCredentials().toString(), userDetails.getPassword())) {
                throw new BadCredentialsException("Bad credentials");
            }
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        };
    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth, CustomUserDetailsService customUserDetailsService) throws Exception {
//        auth.userDetailsService(customUserDetailsService)
//                .passwordEncoder(new BCryptPasswordEncoder());
//        return auth.build();
//    }

//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http, CustomUserDetailsService customUserDetailsService) throws Exception {
//
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(customUserDetailsService)
//                .passwordEncoder(new BCryptPasswordEncoder()).and().build();
//    }

//    @Bean
//    public AuthenticationManager configure(AuthenticationManagerBuilder auth) throws Exception {
//        return auth
//                .userDetailsService(customUserDetailsService)
//                .passwordEncoder(getPasswordEncoder()).and().build();
//    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}