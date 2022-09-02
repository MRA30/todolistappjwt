package com.todolistapp.models.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todolistapp.models.entity.Image;

public interface ImageRepo extends JpaRepository<Image, Long>{

    Image save(String filename);
    
    Image findByImage(String filename);
}
