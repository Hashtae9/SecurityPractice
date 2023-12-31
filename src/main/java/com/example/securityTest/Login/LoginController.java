package com.example.securityTest.Login;

import com.example.securityTest.user.UserRepository;
import com.example.securityTest.user.UserService;
import com.example.securityTest.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    @Autowired
    private final UserService userService;
    private final UserRepository userRepository;


    @GetMapping("/registerUser")
    public String viewRegister() {
        return "ok";
    }

    @PostMapping("/reacttest")
    public String viewtest() {
        return "ok";
    }

    @GetMapping("/logingettest")
    public String logingettest(Authentication authentication) {
        String userName = authentication.getName();
        return userName;
    }
    @GetMapping("/user/user")
    public String userTest() {
        return "user ok";
    }

    @GetMapping("/admin/admin")
    public String adminTest() {
        return "admin ok";
    }

    @GetMapping("/logintest")
    public String viewLogin() {
        User user = userRepository.findByUsername("test");
        log.info("loginform = {}", user);
        return "login ok";
    }

    @PostMapping("/registerUser")
    public String createUser(@ModelAttribute RegisterForm form, BindingResult result){
        if(result.hasErrors()){
            return "error";
        }
        User existingUser = userRepository.findByUsername(form.getUsername());
        if (existingUser != null) {
            String errorMessage = "Id already exist.";
            return errorMessage;
        }
        log.info("register = {}", form);
        userService.createUser(form);

        return "success";
    }

}
