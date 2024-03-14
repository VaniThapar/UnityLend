package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.User;
import com.educare.unitylend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {



    ResponseEntity<List<User>> getAllUsers() throws ServiceException {
        return null;
    }

    ResponseEntity<User> updateUser(User user) throws ServiceException {
        return null;
    }

    ResponseEntity<User> createUser(User user) throws ServiceException {
        return null;
    }

    ResponseEntity<User> getUserById(String userId) throws ServiceException {
        return null;
    }

    ResponseEntity<Void> deleteUser(String userId) throws ServiceException {
        return null;
    }
}


