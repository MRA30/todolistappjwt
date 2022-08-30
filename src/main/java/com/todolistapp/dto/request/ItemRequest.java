package com.todolistapp.dto.request;

import lombok.Data;

@Data
public class ItemRequest {

    private long id;
    private String item;
    private boolean cek;
    private String image;
    
}
