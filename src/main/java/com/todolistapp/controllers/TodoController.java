package com.todolistapp.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todolistapp.dto.ResponseData;
import com.todolistapp.dto.request.ItemRequest;
import com.todolistapp.dto.request.SearchRequest;
import com.todolistapp.dto.request.TodoRequest;
import com.todolistapp.dto.response.ItemResponse;
import com.todolistapp.dto.response.TodoResponse;
import com.todolistapp.services.ItemService;
import com.todolistapp.services.TodoService;
import com.todolistapp.services.UserService;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    
    @Autowired
    private TodoService todoService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ResponseData<TodoResponse>> createTodo( @Valid @RequestBody TodoRequest todoRequest, Errors errors){
        ResponseData<TodoResponse> responseData = new ResponseData<>();
        if(errors.hasErrors()){
            responseData.setStatus(false);
            responseData.setPayload(null);
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        TodoResponse todoResponse = new TodoResponse();

        todoResponse.setTodo(todoRequest.getTodo());
        todoResponse.setUserId(userService.getId());
        todoResponse.setItems(todoRequest.getItems());
        todoService.createTodo(todoRequest);

        responseData.setStatus(true);
        responseData.setPayload(todoResponse);
        responseData.getMessages().add("Todo Saved");
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ItemResponse> addItem(@PathVariable("id") Long id, @RequestBody ItemRequest itemRequest){
        TodoResponse response = new TodoResponse();
        response = todoService.findById(id);
        if(userService.getId() == response.getUserId()){
            todoService.addItem(itemRequest, id);
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseData<TodoResponse>> updateTodo(@PathVariable("id") long id,@RequestBody Map<String, Object> changes){
        TodoResponse response = todoService.findById(id);
        if(userService.getId() == response.getUserId()){
            ResponseData<TodoResponse> responseData = new ResponseData<>();
            TodoResponse updateTodo = todoService.updateTodo(id, changes);
            responseData.setStatus(true);
            responseData.setPayload(updateTodo);
            responseData.getMessages().add("Todo Updated");
            return ResponseEntity.ok(responseData);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id")Long id){
        TodoResponse response = todoService.findById(id);
        TodoResponse todoresponse = new TodoResponse();
        todoresponse = todoService.findById(id);
        if(userService.getId() == todoresponse.getUserId()){
            if(response != null){
                itemService.deleteAll(response);
                todoService.delete(response.getId());
                return ResponseEntity.ok(null);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PostMapping("/todo/{id}")
    public ResponseEntity<PageImpl<ItemResponse>> findItemByTodoId(@PathVariable("id") Long id, 
                                                                @RequestParam(defaultValue = "0") Integer page, 
                                                                @RequestParam(defaultValue = "10") Integer size,
                                                                @RequestParam(defaultValue = "id") String sortBy){
        TodoResponse todo = todoService.findById(id); 
        if (todo != null) {
            if(todo.getUserId() == userService.getId()){
                PageImpl<ItemResponse> response = todoService.findAllItemByTodo(id, page, size, sortBy);
                return ResponseEntity.ok(response);
            }    
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);                               
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> findAll(){
        List<TodoResponse> response = todoService.findAll(userService.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter")
    public ResponseEntity<ResponseData<PageImpl<TodoResponse>>> findNameContainsTodo(@RequestBody SearchRequest searchRequest,
                                                                                    @RequestParam(defaultValue = "0") Integer page, 
                                                                                    @RequestParam(defaultValue = "10") Integer size,
                                                                                    @RequestParam(defaultValue = "id") String sortBy){
        ResponseData<PageImpl<TodoResponse>> responseData = new ResponseData<>();
        try{
            long userId = userService.getId();
            PageImpl<TodoResponse> response = todoService.searchTodoContainsName(userId, searchRequest, page, size, sortBy);
            responseData.setStatus(true);
            responseData.setPayload(response);
            responseData.getMessages().add("Data Loaded");
            return ResponseEntity.ok(responseData);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
