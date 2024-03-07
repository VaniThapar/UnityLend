package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.UserCommunityRepository;
import com.educare.unitylend.dao.UserRepository;
import com.educare.unitylend.model.User;
import com.educare.unitylend.service.UserCommunityService;
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
public class UserController extends BaseController{

    UserService userService;
    private UserCommunityService usercommunityService;
    private UserRepository userRepository;
    private UserCommunityRepository userCommunityRepository;
    @GetMapping("all-users")
    public ResponseEntity<?> getAllUsers() throws ControllerException {
        //Getting all the users
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
    public ResponseEntity<String> createUser(@RequestBody User user) throws ControllerException {
        //Creating a new user with the user object
        try {
            if (user == null || user.getUserid() == null || user.getUserid().isEmpty()) {
                return ResponseEntity.badRequest().body("User ID cannot be null or empty");
            }

            userService.createUser(user);
            return ResponseEntity.ok("success!!!");
        } catch (ServiceException e) {
            log.error("Error encountered in creating the user", e);
            throw new ControllerException("Error encountered in creating the user", e);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable String userId, @RequestBody User updatedUser) throws ControllerException {
        //List<String> prevCommunities;

        //Updating the user

        try {
            if (userId == null || userId.isEmpty() || updatedUser == null) {
                return ResponseEntity.badRequest().body("User ID and updated user cannot be null or empty");
            }
            updatedUser.setUserid(userId);
        //    System.out.println(updatedUser);
            userService.updateUser(updatedUser, userId);
            return ResponseEntity.ok("User updated successfully");
        }
        catch (ServiceException e) {
            log.error("Error encountered in updating the user", e);
            throw new ControllerException("Error encountered in updating the user", e);
        } catch (Exception e) {
            log.error("Error encountered in getting the communities for user with ID: {}", userId, e);
            throw new ControllerException("Error encountered in getting the communities", e);
        }
    }
    @GetMapping("/{userId}/get-info")
    public ResponseEntity<User> getUserByUserId(@PathVariable String userId) throws ControllerException {
        //Getting user information for a given user by its userId
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
            log.error("Error encountered in getting user information by ID: {}", userId, e);
            throw new ControllerException("Error encountered in getting user information", e);
        }
    }

    @PutMapping("/{userId}/inactive")
    public ResponseEntity<String> deactivateUser(@PathVariable String userId) throws ControllerException{
        //Deactivating the user
        try {
            if (userId == null || userId.isEmpty()) {
                return ResponseEntity.badRequest().body("User ID cannot be null or empty");
            }

            boolean updated = userService.markUserAsInactive(userId);

            if (updated) {
                return ResponseEntity.ok("User marked as inactive successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ServiceException e) {
            log.error("Error encountered in deactivating user with ID: {}", userId, e);
            throw new ControllerException("Error encountered in deactivating user", e);
        }
    }

}
