package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.service.BorrowRequestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/borrow-request")
public class BorrowRequestController extends BaseController{
    BorrowRequestService borrowRequestService;
    @GetMapping("/{userId}")
    public ResponseEntity<List<String>> getAllCommunities(@PathVariable String userId) throws ControllerException {
        try {
            if (userId == null || userId.isEmpty()) {
                return ResponseEntity.badRequest().body(List.of("User ID cannot be null or empty"));
            }

            List<String> communityList = borrowRequestService.getRequestedCommunitiesByUserId(userId);

            if (communityList == null || communityList.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(communityList);
        } catch (Exception e) {
            log.error("Error encountered in getting the communities for user with ID: {}", userId, e);
            throw new ControllerException("Error encountered in getting the communities", e);
        }
    }

    @PostMapping("/request")
    public ResponseEntity<String> raiseBorrowRequest(@RequestBody BorrowRequest borrowRequest) throws ControllerException {
        try {
            String userId = borrowRequest.getBorrowerid();

            if (userId == null || userId.isEmpty()) {
                return ResponseEntity.badRequest().body("User ID cannot be null or empty");
            }

            boolean isBorrowRequestSuccessful = borrowRequestService.raiseBorrowRequestForUserid(userId,borrowRequest);

            if (isBorrowRequestSuccessful) {
                return ResponseEntity.ok("Raised Borrow Request");
            } else {
                return ResponseEntity.ok("Cannot make borrow request as your EMI is greater than monthly salary");
            }
        } catch (Exception e) {
            log.error("Error encountered in raising borrow request for user with ID: {}", borrowRequest.getBorrower(), e);
            throw new ControllerException("Error encountered in getting the communities", e);
        }
    }




}