package com.todolistapp.dto.response;

import com.todolistapp.models.entity.Image;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemResponse {

    private long id;
    private String item;
    private boolean cek;
    private Long todoId;
    private Image image;
}
