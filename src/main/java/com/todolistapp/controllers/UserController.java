package com.todolistapp.controllers;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todolistapp.dto.ResponseData;
import com.todolistapp.dto.request.LoginRequest;
import com.todolistapp.dto.request.RegisterRequest;
import com.todolistapp.dto.response.RegisterResponse;
import com.todolistapp.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<ResponseData<RegisterResponse>> register(@Valid @RequestBody RegisterRequest registerRequest, Errors errors) throws AuthException{
        ResponseData<RegisterResponse> responseData = new ResponseData<>();
        if(errors.hasErrors()){
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        
        RegisterResponse registerResponse = userService.registerUser(registerRequest);
            responseData.setStatus(true);
            responseData.setPayload(registerResponse);
            responseData.getMessages().add("User registered successfully!");

            return ResponseEntity.ok(responseData);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest LoginRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(LoginRequest.getEmail(), LoginRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
        return userService.login(LoginRequest.getEmail());
    }

}
