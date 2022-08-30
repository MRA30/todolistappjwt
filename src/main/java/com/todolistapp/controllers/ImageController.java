package com.todolistapp.controllers;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.todolistapp.dto.response.TodoResponse;
import com.todolistapp.services.ImageService;
import com.todolistapp.services.TodoService;
import com.todolistapp.services.UserService;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    @Autowired
    private TodoService todoService;

    private final String pathString = "C:\\Users\\munif\\Documents\\java\\todolistappjwt\\src\\main\\resources\\images";
    
    
    @PostMapping("/item/{id}")
    public ResponseEntity<?> uploadFileItem(@PathVariable("id") long id, @RequestParam("image") MultipartFile file) {
        try {

          imageService.saveImageItem(file, id);
          return ResponseEntity.status(HttpStatus.OK).body(String.format("image %s successfully uploaded" , file.getOriginalFilename()));
        } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
      }

      @PostMapping("/todo/{id}")
      public ResponseEntity<?> uploadFileTodo(@PathVariable("id") long id, @RequestParam("image") MultipartFile file) {
        try {
          TodoResponse response = todoService.findById(id);
          if(response.getUserId() == userService.getId()){
          imageService.saveImageTodo(file, id);
          return ResponseEntity.status(HttpStatus.OK).body(String.format("image %s successfully uploaded" , file.getOriginalFilename()));
          }
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
      }

      @GetMapping("/download/{fileName:.+}")
      public ResponseEntity<?> downloadFileFromLocal(@PathVariable String fileName) {
        Path path = Paths.get(pathString + File.separator + fileName);
        Resource resource = null;
        try {
          resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
          e.printStackTrace();
        }
        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_JPEG)
            .body(resource);
      }
}
