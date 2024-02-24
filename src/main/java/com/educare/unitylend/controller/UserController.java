package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.User;
import com.educare.unitylend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    UserService userService;

    /**
     *
     * @return List of all the available {@link User}
     * @throws ControllerException : Exception to be thrown from controller in case of any exception
     */
    @GetMapping("all-users")
    public List<User> getAllUsers() throws ControllerException {
        try {
            List<User> userList = userService.getUsers();
            return userList;
        } catch (ServiceException e) {
            log.error("Error encountered in getting the users");
            throw new ControllerException("Error encountered in getting the users", e);
        }
    }
}
