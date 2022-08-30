package com.todolistapp.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.todolistapp.dto.request.ItemRequest;
import com.todolistapp.dto.request.SearchRequest;
import com.todolistapp.dto.request.TodoRequest;
import com.todolistapp.dto.response.ItemResponse;
import com.todolistapp.dto.response.TodoResponse;
import com.todolistapp.models.entity.Item;
import com.todolistapp.models.entity.Todo;
import com.todolistapp.models.repos.ItemRepo;
import com.todolistapp.models.repos.TodoRepo;
import static org.springframework.data.jpa.domain.Specification.*;
import static com.todolistapp.models.specification.TodoSpecification.*;

@Service
@Transactional
public class TodoService {

    @Autowired
    private TodoRepo todoRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired 
    private UserService userService;

    public Todo createTodo(TodoRequest todoRequest){
        Todo todo = new Todo();
        todo.setTodo(todoRequest.getTodo());
        todo.setUserId(userService.getId());
        return todoRepo.save(todo);
    }

    public Todo save(Todo todo){
        return todoRepo.save(todo);
    }

    public TodoResponse findById(long id){
        Optional<Todo> todo = todoRepo.findById(id);
        if(todo.isPresent()){
            TodoResponse todoResponse = new TodoResponse();
            todoResponse.setId(todo.get().getId());
            todoResponse.setTodo(todo.get().getTodo());
            todoResponse.setUserId(todo.get().getUserId());
            todoResponse.setItems(todo.get().getItems());
            return todoResponse;
        }
        return null;
    }

    public List<TodoResponse> findAll(Long userId){

        List<Todo> todos = todoRepo.findTodoByUserId(userId);
        return todos.stream().map(todo -> new TodoResponse(
                todo.getId(),
                todo.getTodo(),
                todo.getUserId(),
                todo.getItems(),
                todo.getImage()))
                .collect(Collectors.toList());
    }

    public void delete(long id){
        todoRepo.deleteById(id);
    }

    public void addItem(ItemRequest itemRequest, long id){
        Optional<Todo> todo = todoRepo.findById(id);
        Item item = new Item();
        item.setItem(itemRequest.getItem());
        item.setCek(itemRequest.isCek());
        todo.ifPresent(value -> value.getItems().add(item));

        TodoRequest todoRequest = new TodoRequest();
        todoRequest.setId(id);
        todoRequest.setItems(todo.get().getItems());
    }

    public TodoResponse updateTodo(long id, Map<String, Object> changes){
        TodoResponse todoResponse = findById(id);

        Todo todo = new Todo(
            todoResponse.getId(),
            todoResponse.getTodo(),
            todoResponse.getUserId(),
            todoResponse.getItems(),
            todoResponse.getImage()
        ); 
        changes.forEach(
                (change, value) -> {
                    switch (change){
                        case "todo":  todo.setTodo((String) value); break;
                        case "image": todo.setImage((String) value); break;
                    }
                }
        );
        todoRepo.save(todo);
        TodoResponse response = new TodoResponse(
            todo.getId(),
            todo.getTodo(),
            todo.getUserId(),
            todo.getItems(),
            todo.getImage()
        );        

        return response;
    }

    public PageImpl<ItemResponse> findAllItemByTodo(Long todoId, Integer page, Integer size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Item> listItem = itemRepo.findItemByTodo(todoId, pageable);
        int totalElements = (int) listItem.getTotalElements();
        // return listItem.stream().map(item -> new ItemResponse(
        //     item.getId(),
        //     item.getItem(),
        //     item.isCek(),
        //     item.getImage()
        // )).collect(Collectors.toList());
        return new PageImpl<ItemResponse>(listItem.getContent()
                    .stream().map(item -> new ItemResponse(item.getId(),
                                                            item.getItem(),
                                                            item.isCek(), 
                                                            item.getImage()))
                                                            .collect(Collectors.toList()), pageable, totalElements);
    }

    public PageImpl<TodoResponse> searchTodoContainsName(long userId, SearchRequest searchRequest, Integer page, Integer size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Todo> listTodo = todoRepo.findAll(where(filterByUserId(userId)).and(where(searchTodoName(searchRequest.getSearchKey()))), pageable);
        int totalElements = (int) listTodo.getTotalElements();
        // return listTodo.stream().map(todo -> new TodoResponse(
        //     todo.getId(),
        //     todo.getTodo(),
        //     todo.getUserId(),
        //     todo.getItems(),
        //     todo.getImage()
        // )).collect(Collectors.toList());
        return new PageImpl<TodoResponse>(listTodo.getContent()
                    .stream().map(todo -> new TodoResponse(todo.getId(),
                                                            todo.getTodo(),
                                                            todo.getUserId(),
                                                            todo.getItems(),
                                                            todo.getImage()))
                                                            .collect(Collectors.toList()), pageable, totalElements);
    }

}
