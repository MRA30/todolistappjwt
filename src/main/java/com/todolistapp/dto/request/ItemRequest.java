package com.todolistapp.dto.request;

import com.todolistapp.models.entity.Image;

import lombok.Data;

@Data
public class ItemRequest {

    private Long id;
    private String item;
    private boolean cek;
    private Long todoId;
    private Image image;
    
}
