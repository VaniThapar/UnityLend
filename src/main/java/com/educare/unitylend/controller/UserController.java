package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.User;
import com.educare.unitylend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> getAllUsers() throws ControllerException {
        try {
            List<User> userList = userService.getUsers();
            if (userList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found");
            }
            return ResponseEntity.ok(userList);
        } catch (ServiceException e) {
            log.error("Error encountered in getting the users", e);
            throw new ControllerException("Error encountered in getting the users", e);
        }
    }


    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestBody User user) throws ControllerException{
        // Create the user
        try {
            userService.createUser(user);
            return ResponseEntity.ok("succcesss!!!");
        } catch (ServiceException e) {
            log.error("Error encountered in getting the users");
            throw new ControllerException("Error encountered in getting the users", e);
        }

    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable String userId, @RequestBody User updatedUser) throws ControllerException {
        // Set the userId in the updatedUser object
        updatedUser.setUserid(userId);
        System.out.println(updatedUser);
        // Validate and update the user
        try {
            userService.updateUser(updatedUser);
        } catch (ServiceException e) {
            log.error("Error encountered in getting the users");
            throw new ControllerException("Error encountered in getting the users", e);
        }

        return ResponseEntity.ok("User updated successfully");
    }
    @GetMapping("/{userId}/get-info")
    public ResponseEntity<User> getUserByUserId(@PathVariable String userId) throws ControllerException{
        try {
            if (userId == null || userId.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            User user = userService.getUserByUserId(userId);

            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ServiceException e) {
            // Log the exception or handle it as needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{userId}/inactive")
    public ResponseEntity<String> deactivateUser(@PathVariable String userId) {
        try {
            if (userId == null || userId.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            boolean updated = userService.markUserAsInactive(userId);

            if (updated) {
                return ResponseEntity.ok("User marked as inactive successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ServiceException e) {
            // Log the exception or handle it as needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}


