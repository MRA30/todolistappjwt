package com.todolistapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {
    
    private long id;
    private String fullName;
    private String email;
    private String password;
    
}
