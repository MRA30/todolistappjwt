package com.todolistapp;

import java.nio.file.FileSystems;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class Constans {

    public final static String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    
    public final static String userDirectory = FileSystems.getDefault().getPath("").toAbsolutePath().toString() + "\\src\\main\\resources\\images";
    // public final static String userDirectory = "C:\\Users\\munif\\Documents\\java\\todolistappjwt\\src\\main\\resources\\images";
}
