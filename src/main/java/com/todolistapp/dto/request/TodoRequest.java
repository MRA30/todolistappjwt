package com.todolistapp.dto.request;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.todolistapp.models.entity.Item;

import lombok.Data;

@Data
public class TodoRequest {

    private long id;
    @NotEmpty(message = "todo is required")
    private String todo;
    @NotEmpty(message = "userId is required")
    private long userId;
    private List<Item> items;
    private String image;
    
}
