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
import com.todolistapp.services.ItemService;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseData<ItemResponse>> update(@PathVariable("id") long id, @RequestBody Map<String, Object> changes){
        var updateItem = itemService.updateItem(id, changes);
        ResponseData<ItemResponse> responseData = new ResponseData<>();
        responseData.setStatus(true);
        responseData.setPayload(updateItem);
        responseData.getMessages().add("Item Updated");
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ItemResponse> deleteItem(@PathVariable("id") Long id){
        ItemResponse itemResponse = itemService.findById(id);
        if(itemResponse != null){
            itemService.delete(id);
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    
}
