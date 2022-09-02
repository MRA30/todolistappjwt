package com.todolistapp.dto.response;

import java.util.List;

import com.todolistapp.models.entity.Image;
import com.todolistapp.models.entity.Item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoExportResponse {
    private long id;
    private String todo;
    private String username;
    private List<Item> items;
    private Image image;
}
