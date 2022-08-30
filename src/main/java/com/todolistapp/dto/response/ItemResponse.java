package com.todolistapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemResponse {

    private long id;
    private String item;
    private boolean cek;
    private String image;
}
