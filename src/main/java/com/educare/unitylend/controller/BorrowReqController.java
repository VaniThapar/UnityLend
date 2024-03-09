package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.service.BorrowReqService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/all-borrow-requests")
public class BorrowReqController extends BaseController{
    BorrowReqService borrowReqService;
    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllRequests(@PathVariable String userId) {
        try {
            List<BorrowRequest> borrowRequestList = borrowReqService.getBorrowRequests(userId);
            if (borrowRequestList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No borrow requests found for user: " + userId);
            } else {
                return ResponseEntity.ok(borrowRequestList);
            }
        } catch (ServiceException e) {
            log.error("Error encountered in validating the user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error encountered in validating the user");
        }
    }
    @GetMapping("/{userId}/{communityId}")
    public ResponseEntity<?> getRequestsForUserAndCommunity(
            @PathVariable String userId,
            @PathVariable String communityId
    ) {
        try {
            List<BorrowRequest> borrowRequestList = borrowReqService.getRequestsForUserAndCommunity(userId, communityId);
            if (borrowRequestList.isEmpty()) {
                // Check if the user is not a part of that community
                boolean isUserPartOfCommunity = borrowReqService.isUserPartOfCommunity(userId, communityId);
                if (!isUserPartOfCommunity) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User is not a part of the community.");
                }

                // Check if no pending borrow requests are there
                boolean hasPendingRequests = borrowReqService.hasPendingRequests(userId);
                if (!hasPendingRequests) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No pending borrow requests found for the user.");
                }

                // Check if the user is not a part of any community
                boolean isUserPartOfAnyCommunity = borrowReqService.isUserPartOfAnyCommunity(userId);
                if (!isUserPartOfAnyCommunity) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User is not a part of any community.");
                }
            }
            return ResponseEntity.ok(borrowRequestList);
        } catch (Exception e) {
            log.error("Error encountered in getting the borrow requests for particular community", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error encountered in getting the borrow requests for particular community");
        }
    }

    @GetMapping("/user/{userId}/target-amount/{amount}")
    public List<BorrowRequest> getRequestsForUserAndCommunity(
            @PathVariable String userId,
            @PathVariable double amount
    ) throws ControllerException {
        try {
            List<BorrowRequest> borrowRequestListByAmount = borrowReqService.getRequestsByCommunityAndAmount(userId, amount);
            return borrowRequestListByAmount;
        } catch (Exception e) {
            log.error("Error encountered in getting the borrow requests filtered by amount", e);
            throw new ControllerException("Error encountered in getting the borrow requests filtered by amount", e);
        }
    }

    @GetMapping("/community/{communityId}")
    public List<BorrowRequest> getBorrowRequestByCommunityId(
            @PathVariable String communityId
    ) throws ControllerException {
        try {
            List<BorrowRequest> borrowRequestListByCommunityId = borrowReqService.getBorrowRequestsByCommunityId(communityId);
            return borrowRequestListByCommunityId;
        } catch (Exception e) {
            log.error("Error encountered in getting the borrow requests by community Ids!", e);
            throw new ControllerException("Error encountered in getting the borrow requests by commmunity Ids!", e);
        }
    }

    @GetMapping("/community/{communityId}/target-amount/{amount}")
    public List<BorrowRequest> getRequestsForCommunityByAmount(
            @PathVariable String communityId,
            @PathVariable double amount
    ) throws ControllerException{
        try{
            List<BorrowRequest> borrowRequestListOfCommunityIdByAmount = borrowReqService.getBorrowRequestsOfCommunityByAmount(communityId,amount);
            return borrowRequestListOfCommunityIdByAmount;
        }catch (Exception e){
            log.error("Error encountered in getting the borrow requests of community Ids filtered by amount!", e);
            throw new ControllerException("Error encountered in getting the borrow requests of commmunity Ids filtered by amount!", e);
        }
    }

}
