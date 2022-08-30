package com.todolistapp.dto.response;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private long id;
    private String fullName;
    @Email
    private String email;
    private String JwtToken;
    
}
