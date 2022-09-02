package com.todolistapp.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todolistapp.dto.ResponseData;
import com.todolistapp.dto.response.ItemResponse;
import com.todolistapp.dto.response.TodoResponse;
import com.todolistapp.services.ItemService;
import com.todolistapp.services.TodoService;
import com.todolistapp.services.UserService;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private TodoService todoService;

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseData<ItemResponse>> update(@PathVariable("id") long id, @RequestBody Map<String, Object> changes){
        ItemResponse itemResponse = itemService.findById(id);
        ResponseData<ItemResponse> responseData = new ResponseData<>();
        TodoResponse response = todoService.findById(itemResponse.getTodoId());
        if(userService.getId() == response.getUserId()){
            try{
                var updateItem = itemService.updateItem(id, changes);  
                responseData.setStatus(true);
                responseData.setPayload(updateItem);
                responseData.getMessages().add("Item Updated");
                return ResponseEntity.ok(responseData);
            }catch(Exception e){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ItemResponse> deleteItem(@PathVariable("id") Long id){
        ItemResponse itemResponse = itemService.findById(id);
        TodoResponse response = todoService.findById(itemResponse.getTodoId());
        if(userService.getId() == response.getUserId()){
            try{
                itemService.delete(id);
                return ResponseEntity.ok(null);
            }catch(Exception e){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }        
    }
    
}
