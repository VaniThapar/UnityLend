package com.educare.unitylend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
public class BaseController {

    @GetMapping("")
    public String baseControllerMethod(){
        return "Welcome to the Unity Lend application";
    }
}
