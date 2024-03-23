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

    private UserService userService;

    /**
     * API endpoint for creating a new user.
     *
     * @param user The user object containing user details to be created.
     * @return ResponseEntity<Boolean> Indicating success or failure of the user creation process.
     * @throws ControllerException If an error occurs during the user creation process.
     */
    @PostMapping("/create-user")
    ResponseEntity<String> createUser(@RequestBody User user) throws ControllerException {
        try {

            if (user == null) {
                return ResponseEntity.badRequest().body("User is null");
            }
            Boolean isCreated = userService.createUser(user);
            if (!isCreated) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User could not be created");
            }
            return ResponseEntity.ok("User created successfully");
        } catch (ServiceException e) {
            log.error("Error encountered in user creation");
            throw new ControllerException("Error encountered in user creation", e);
        }
    }


    /**
     * API endpoint for updating user details.
     *
     * @param user The user object containing updated user details.
     * @return ResponseEntity<Boolean> Indicating success or failure of the user update process.
     * @throws ControllerException If an error occurs during the user update process.
     */
    @PutMapping("/update-user-details")
    ResponseEntity<String> updateUserDetails(@RequestBody User user) throws ControllerException {

        try {
            if (user == null) {
                ResponseEntity.badRequest().body("User is null");
            }
            Boolean isUpdated = userService.updateUserDetails(user);
            if (!isUpdated) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User could not be updated");
            }
            return ResponseEntity.ok("User updated successfully");
        } catch (ServiceException e) {
            log.error("Error encountered in updating user");
            throw new ControllerException("Error encountered in updating user", e);
        }


    }

    /**
     * API endpoint for retrieving a user by user ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return ResponseEntity<User> The user object corresponding to the provided user ID.
     * @throws ControllerException If an error occurs during the user retrieval process.
     */
    @GetMapping("/get-user-by-user-id/{userId}")
    ResponseEntity<User> getUserForUserId(@PathVariable(required = true) String userId) throws ControllerException {
       try{
           User requiredUser=userService.getUserForUserId(userId);
           return ResponseEntity.ok(requiredUser);
       }
       catch (ServiceException e){
           log.error("Error encountered in getting user by user id");
           throw new ControllerException("Error encountered in getting user by user id",e);
       }
    }


    /**
     * API endpoint for deleting a user by user ID.
     *
     * @param userId The ID of the user to delete.
     * @return ResponseEntity<Boolean> Indicating success or failure of the user deletion process.
     * @throws ControllerException If an error occurs during the user deletion process.
     */
    @DeleteMapping("/delete-user-by-user-id/{userId}")
    ResponseEntity<String> deleteUser(@PathVariable(required = true) String userId) throws ControllerException {
        try{
            Boolean isDeleted=userService.deleteUser(userId);
            if(!isDeleted){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User could not be deleted");
            }
            return ResponseEntity.ok("User deleted successfully");
        }
        catch(ServiceException e){
            log.error("Error encountered in user deletion");
            throw new ControllerException("Error encountered in user deletion",e);
        }
    }


    /*For Administration Purposes*/

    /**
     * API endpoint for retrieving all users.
     *
     * @return ResponseEntity<List < User>> The list of all users.
     * @throws ControllerException If an error occurs during the user retrieval process.
     */
    @GetMapping("/get-all-users")
    ResponseEntity<List<User>> getAllUsers() throws ControllerException {
        try{
            List<User>userList=userService.getAllUsers();
            if(userList.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(userList);
        }
        catch(ServiceException e){
            log.error("Error encountered in getting all users");
            throw new ControllerException("Error encountered in getting all users",e);
        }
    }
}
