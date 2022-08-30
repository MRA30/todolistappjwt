package com.todolistapp.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotEmpty(message = "fullname is required")
    private String fullName;
    @NotEmpty(message = " email is required")
    @Email(message = "email not valid")
    private String email;
    @NotEmpty(message = "password is required")
    private String password;
}
