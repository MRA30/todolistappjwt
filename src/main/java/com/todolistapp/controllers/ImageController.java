package com.todolistapp.controllers;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.todolistapp.Constans;
import com.todolistapp.dto.response.ItemResponse;
import com.todolistapp.dto.response.TodoResponse;
import com.todolistapp.services.ImageService;
import com.todolistapp.services.ItemService;
import com.todolistapp.services.TodoService;
import com.todolistapp.services.UserService;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private TodoService todoService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;
    

    @PostMapping("/item/{idItem}")
    public ResponseEntity<?> uploadFileItem(@PathVariable("idItem")long idItem, @RequestParam("file") MultipartFile file) {
      Long id = userService.getId();
      ItemResponse itemResponse = itemService.findById(idItem);
      TodoResponse response = todoService.findById(itemResponse.getTodoId());
      if(response.getUserId() == id){
        try {
          imageService.saveImageItem(file, idItem);
          return ResponseEntity.status(HttpStatus.OK).body(String.format("image %s successfully uploaded" , file.getOriginalFilename()));
        } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
      }
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        
      }

      @PostMapping("/todo/{idTodo}")
      public ResponseEntity<?> uploadFileTodo(@PathVariable("idTodo") long idTodo, @RequestParam("file") MultipartFile file) {
        Long id = userService.getId();
        TodoResponse response = todoService.findById(idTodo);
      if(response.getUserId() == id){
        try {
          imageService.saveImageTodo(file, idTodo);
          return ResponseEntity.status(HttpStatus.OK).body(String.format("image %s successfully uploaded" , file.getOriginalFilename()));
        } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
      }
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
      }

      @GetMapping("donwload/{image}")
      public void getImage(@PathVariable("image") String image, HttpServletResponse response
      ) throws IOException {
  
          var imgFile = new ClassPathResource(Constans.baseUrl + File.separator + Constans.userDirectory + File.separator + image);
          System.out.println(imgFile.getInputStream());
  
          response.setContentType(MediaType.IMAGE_JPEG_VALUE);
          StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
      }

      // @GetMapping("/download/{fileName:.+}")
      // public ResponseEntity<?> downloadFileFromLocal(@PathVariable String fileName) {
      //   Path path = Paths.get(pathString + File.separator + fileName);
      //   Resource resource = null;
      //   try {
      //     resource = new UrlResource(path.toUri());
      //   } catch (MalformedURLException e) {
      //     e.printStackTrace();
      //   }
      //   return ResponseEntity.ok()
      //       .contentType(MediaType.IMAGE_JPEG)
      //       .body(resource);
      // }
}
