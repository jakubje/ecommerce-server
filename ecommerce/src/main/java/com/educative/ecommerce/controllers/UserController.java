package com.educative.ecommerce.controllers;

import com.educative.ecommerce.dto.users.SignUpResponseDto;
import com.educative.ecommerce.dto.users.SignupDto;
import com.educative.ecommerce.exceptions.CustomException;
import com.educative.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public SignUpResponseDto SignUp(@RequestBody SignupDto signupDto) throws CustomException{
        return userService.signUp(signupDto);
    }
}
