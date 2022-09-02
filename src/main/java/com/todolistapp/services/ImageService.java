package com.todolistapp.services;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.todolistapp.Constans;
import com.todolistapp.dto.response.ItemResponse;
import com.todolistapp.dto.response.TodoResponse;
import com.todolistapp.models.entity.Image;
import com.todolistapp.models.entity.Item;
import com.todolistapp.models.entity.Todo;
import com.todolistapp.models.repos.ImageRepo;



@Service
public class ImageService {

    @Autowired
    private ImageRepo imageRepo;

    @Autowired
    private ItemService itemService;

    @Autowired
    private TodoService todoService;
    
    public void saveImageItem(MultipartFile file, long idItem) {
        try {
          String currentData = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.SSS").format(new Date());
          String filename = "Item" + "_" + idItem + "_" + currentData;
          ItemResponse response = itemService.findById(idItem);
          file.transferTo(new File(Constans.userDirectory + File.separator + filename + ".jpg"));
          Image image = new Image();
          image.setImage(filename);
          imageRepo.save(image);
          
          Item item = new Item();
          item.setId(response.getId());
          item.setItem(response.getItem());
          item.setCek(response.isCek());
          item.setTodoId(response.getTodoId());
          item.setImage(image);
          itemService.save(item);
          
        } catch (Exception e) {
          throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
      }

      public void saveImageTodo(MultipartFile file, long idTodo){

      try {
        String currentData = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.SSS").format(new Date());
        String filename = "Todo" + "_" + idTodo + "_" + currentData;
        TodoResponse response = todoService.findById(idTodo);
        file.transferTo(new File(Constans.userDirectory + File.separator + filename + ".jpg"));
        Image image = new Image();
        image.setImage(filename);
        imageRepo.save(image);
        
        Todo todo = new Todo();
        todo.setId(response.getId());
        todo.setTodo(response.getTodo());
        todo.setUserId(response.getUserId());
        todo.setItems(response.getItems());
        todo.setImage(image);
        todoService.save(todo);
        
      } catch (Exception e) {
        throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
      }
      }
}
