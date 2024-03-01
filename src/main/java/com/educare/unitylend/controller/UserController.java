package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.model.User;
import com.educare.unitylend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    UserService userService;

    /**
     * @return List of all the available {@link User}
     * @throws ControllerException : Exception to be thrown from controller in case of any exception
     */
    @GetMapping("all-users")
    public List<User> getAllUsers() throws ControllerException {
        try {
            List<User> userList = userService.getUsers();
            return userList;
        } catch (Exception e) {
            log.error("Error encountered in getting the users");
            throw new ControllerException("Error encountered in getting the users", e);
        }
    }

    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestBody User user) throws ControllerException {
        // Create the user
        try {
            userService.createUser(user);
            return ResponseEntity.ok("succcesss!!!");
        } catch (Exception e) {
            log.error("Error encountered in getting the users");
            throw new ControllerException("Error encountered in getting the users", e);
        }

    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable String userId, @RequestBody User updatedUser) throws ControllerException {
        // Set the userId in the updatedUser object
        updatedUser.setUserid(userId);

        // Validate and update the user
        try {
            userService.updateUser(updatedUser);
        } catch (Exception e) {
            log.error("Error encountered in getting the users");
            throw new ControllerException("Error encountered in getting the users", e);
        }

        return ResponseEntity.ok("User updated successfully");
    }

}


