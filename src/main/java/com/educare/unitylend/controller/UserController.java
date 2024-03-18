//package com.educare.unitylend.controller;
//
//import com.educare.unitylend.Exception.ControllerException;
//import com.educare.unitylend.Exception.ControllerException;
//import com.educare.unitylend.model.User;
//import com.educare.unitylend.service.UserService;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
//@Slf4j
//@AllArgsConstructor
//@RestController
//@RequestMapping("/user")
//public class UserController extends BaseController{
//
//    private UserService userService;
//
//    /**
//     * API endpoint for creating a new user.
//     *
//     * @param user The user object containing user details to be created.
//     * @return ResponseEntity<Boolean> Indicating success or failure of the user creation process.
//     * @throws ControllerException If an error occurs during the user creation process.
//     */
//    @PostMapping("/create-user")
//    ResponseEntity<Boolean> createUser(@RequestBody User user) throws ControllerException {
//        return null;
//    }
//
//
//    /**
//     * API endpoint for updating user details.
//     *
//     * @param user The user object containing updated user details.
//     * @return ResponseEntity<Boolean> Indicating success or failure of the user update process.
//     * @throws ControllerException If an error occurs during the user update process.
//     */
//    @PutMapping("/update-user-details")
//    ResponseEntity<Boolean> updateUserDetails(@RequestBody User user) throws ControllerException {
//        return null;
//    }
//
//    /**
//     * API endpoint for retrieving a user by user ID.
//     *
//     * @param userId The ID of the user to retrieve.
//     * @return ResponseEntity<User> The user object corresponding to the provided user ID.
//     * @throws ControllerException If an error occurs during the user retrieval process.
//     */
//    @GetMapping("/get-user-by-user-id/{userId}")
//    ResponseEntity<User> getUserForUserId(String userId) throws ControllerException {
//        return null;
//    }
//
//
//    /**
//     * API endpoint for deleting a user by user ID.
//     *
//     * @param userId The ID of the user to delete.
//     * @return ResponseEntity<Boolean> Indicating success or failure of the user deletion process.
//     * @throws ControllerException If an error occurs during the user deletion process.
//     */
//    @DeleteMapping("/delete-user-by-user-id/{userId}")
//    ResponseEntity<Boolean> deleteUser(String userId) throws ControllerException {
//        return null;
//    }
//
//
//    /*For Administration Purposes*/
//    /**
//     * API endpoint for retrieving all users.
//     *
//     * @return ResponseEntity<List<User>> The list of all users.
//     * @throws ControllerException If an error occurs during the user retrieval process.
//     */
//    @GetMapping("/get-all-users")
//    ResponseEntity<List<User>> getAllUsers() throws ControllerException {
//        return null;
//    }
//}
