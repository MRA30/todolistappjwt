package com.todolistapp.models.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todolistapp.models.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {

    User findByFullName(String fullName);

    User findByEmail(String email);
    
}
