package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.Community;
import com.educare.unitylend.service.CommunityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/community")
public class CommunityController extends BaseController {
    @GetMapping
    public void createCommunity() throws ControllerException {
    }

    @GetMapping
    public void deleteCommunity() throws ControllerException {
    }

    @GetMapping
    public void getAllCommunities() throws ControllerException {
    }
}
