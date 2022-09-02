package com.todolistapp.services;

import java.security.Principal;
import java.util.ArrayList;

import javax.security.auth.message.AuthException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.todolistapp.dto.request.RegisterRequest;
import com.todolistapp.dto.response.RegisterResponse;
import com.todolistapp.models.entity.User;
import com.todolistapp.models.repos.UserRepo;
import com.todolistapp.util.JwtUtil;

@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder bcryptPasswordEncoder;

    private Principal principal(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public User getUser(){
        return userRepo.findByEmail(principal().getName());
    }

    public Long getId(){
        return userRepo.findByEmail(principal().getName()).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if(user == null){
            return (UserDetails) new UsernameNotFoundException(
            String.format("User with email '%s' not found",email));
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    public RegisterResponse registerUser(RegisterRequest registerRequest) throws AuthException{

        if(userRepo.findByEmail(registerRequest.getEmail()) != null){
            throw new AuthException(String.format("User with email '%s' already exists", 
            registerRequest.getEmail()));
        }
 
        String emailLowerCase = registerRequest.getEmail().toLowerCase();
        String encodePassword = bcryptPasswordEncoder.encode(registerRequest.getPassword());
        registerRequest.setPassword(encodePassword);
        registerRequest.setEmail(emailLowerCase);

        User user = new User();
        user.setFullName(registerRequest.getFullName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());

        User newuser = userRepo.save(user);

        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setId(newuser.getId());
        registerResponse.setFullName(newuser.getFullName());
        registerResponse.setEmail(newuser.getEmail());

        return registerResponse;
    }

    public String login(String email){
        User user = userRepo.findByEmail(email);
        return jwtUtil.generateToken(user);
    }
    
}
