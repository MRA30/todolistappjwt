package com.todolistapp.services;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.todolistapp.dto.response.ItemResponse;
import com.todolistapp.dto.response.TodoResponse;
import com.todolistapp.models.entity.Item;
import com.todolistapp.models.entity.Todo;



@Service
public class ImageService {
    // private final String pathSting = FileSystems.getDefault().getPath("").toAbsolutePath().toString();
    // private final String pathFolder = "//src//main//resources//images";
    private final String pathSting = "C:\\Users\\munif\\Documents\\java\\todolistappjwt\\src\\main\\resources\\images";

    @Autowired
    private ItemService itemService;

    @Autowired
    private TodoService todoService;
    
    public void saveImageItem(MultipartFile file, long id) {
        try {
            String filename = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.SSS").format(new Date()) + "-" + file.getOriginalFilename();
          file.transferTo(new File(pathSting+File.separator+filename));
          ItemResponse itemResponse = itemService.findById(id);
          Item item = new Item(
            itemResponse.getId(),
            itemResponse.getItem(),
            itemResponse.isCek(),
            itemResponse.getImage()
        );
        item.setImage(filename);
        itemService.save(item);
          
        } catch (Exception e) {
          throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
      }

      public void saveImageTodo(MultipartFile file, long id) {
        try {
            String filename = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(new Date()) + "-" + file.getOriginalFilename();
          file.transferTo(new File(pathSting+File.separator+filename));
          TodoResponse response = todoService.findById(id);
          Todo todo = new Todo(
            response.getId(),
            response.getTodo(),
            response.getUserId(),
            response.getItems(),
            response.getImage()
          );
          todo.setImage(filename);
          todoService.save(todo);
        } catch (Exception e) {
          throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
      }
}
