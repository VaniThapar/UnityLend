package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.Community;
import com.educare.unitylend.service.BorrowRequestCommunityService;
import com.educare.unitylend.service.BorrowRequestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/borrow-request")
public class BorrowRequestController extends BaseController{
    BorrowRequestService borrowRequestService;
    BorrowRequestCommunityService borrowRequestCommunityService;

    @GetMapping("get-requests-by-user/{userId}")
    public ResponseEntity<?> getBorrowRequestsByUserId(@PathVariable String userId) throws ControllerException{
        try {
            if(userId==null || userId.isEmpty()){
                return ResponseEntity.badRequest().body(List.of("User ID cannot be null or empty"));
            }
            List<BorrowRequest> borrowRequestList = borrowRequestService.getBorrowRequestsByUserId(userId);
            return ResponseEntity.ok(borrowRequestList);
        } catch (ServiceException e) {
            log.error("Error encountered in fetching the borrow requests by user id", e);
            throw new ControllerException("Error encountered in fetching the borrow requests by user id",e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error encountered in validating the user");
        }
    }

    @GetMapping("/get-communities-for-user/{userId}")
    public ResponseEntity<?> getCommunitiesForUser(@PathVariable String userId) throws ControllerException {
        try {
            if (userId == null || userId.isEmpty()) {
                return ResponseEntity.badRequest().body(List.of("User ID cannot be null or empty"));
            }

            List<Community> communityList = borrowRequestService.getCommunitiesForWhichBorrowRequestRaisedByUser(userId);

            if (communityList == null || communityList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No communities found where given user has raised a request");
            }

            return ResponseEntity.ok(communityList);
        } catch (Exception e) {
            log.error("Error encountered in getting the communities for user with ID: {}", userId, e);
            throw new ControllerException("Error encountered in getting the communities", e);
        }
    }

    @PostMapping("/raise-borrow-request")
    public ResponseEntity<String> raiseBorrowRequest(@RequestBody BorrowRequest borrowRequest) throws ControllerException {
        try {
            String userId = borrowRequest.getBorrower().getUserId();

            if (userId == null || userId.isEmpty()) {
                return ResponseEntity.badRequest().body("User ID cannot be null or empty");
            }

            boolean isBorrowRequestValid = borrowRequestService.validateBorrowRequest(borrowRequest);

            if (isBorrowRequestValid) {
                borrowRequestService.createBorrowRequest(borrowRequest);
                return ResponseEntity.ok("Raised Borrow Request");
            } else {
                return ResponseEntity.ok("Cannot make borrow request as your Monthly EMI is greater than half of monthly salary");
            }
        } catch (Exception e) {
            log.error("Error encountered in raising borrow request for user with ID: {}", borrowRequest.getBorrower(), e);
            throw new ControllerException("Error encountered in getting the communities", e);
        }
    }


    @GetMapping("/get-requests-by-community/{communityId}")
    public ResponseEntity<?> getBorrowRequestsByCommunityId(@PathVariable String communityId) throws ControllerException{
        try {
            if (communityId == null || communityId.isEmpty()) {
                return ResponseEntity.badRequest().body(List.of("Community ID cannot be null or empty"));
            }

            List<BorrowRequest> borrowRequestList = borrowRequestCommunityService.getRequestsByCommunityId(communityId);

            if (borrowRequestList == null || borrowRequestList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No borrow requests found for given community");
            }
            return ResponseEntity.ok(borrowRequestList);
        } catch (ServiceException e) {
            log.error("Error encountered in fetching borrow requests in a community");
            throw new ControllerException("Error encountered in fetching borrow requests in a community",e);
        }

//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error encountered in validating the community");

    }


    @GetMapping("/get-requests-by-amount/{communityId}/{amount}")
    ResponseEntity<?>getBorrowRequestsByAmount(@PathVariable String communityId, @PathVariable BigDecimal amount) throws ControllerException{
        try {
            if (communityId == null || communityId.isEmpty()) {
                return ResponseEntity.badRequest().body(List.of("Community ID cannot be null or empty"));
            }

            List<BorrowRequest> borrowRequestList = borrowRequestCommunityService.getRequestsByCommunityId(communityId);

            if (borrowRequestList == null || borrowRequestList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No borrow requests found for given community and amount");
            }

            List<BorrowRequest> requiredRequestList=new ArrayList<>();
            for(BorrowRequest request:borrowRequestList){
                if (request.getRequestedAmount().compareTo(amount) >= 0) {
                    requiredRequestList.add(request);
                }
            }

            log.info("requiredRequestList is "+ requiredRequestList);
            return ResponseEntity.ok(requiredRequestList);
        }
        catch(ServiceException e){
            log.error("Error encountered in fetching borrow requests for the given community and amount");
            throw new ControllerException("Error encountered in fetching borrow requests for the given community and amount",e);
        }
    }

    @GetMapping("/get-requests-by-interest/{communityId}/{interest}")
    ResponseEntity<?>getBorrowRequestsByInterest(@PathVariable String communityId, @PathVariable BigDecimal interest) throws ControllerException{
        try {
            if (communityId == null || communityId.isEmpty()) {
                return ResponseEntity.badRequest().body(List.of("Community ID cannot be null or empty"));
            }
            List<BorrowRequest> borrowRequestList = borrowRequestCommunityService.getRequestsByCommunityId(communityId);

            if (borrowRequestList == null || borrowRequestList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No borrow requests found for given community and amount");
            }

            List<BorrowRequest> requiredRequestList=new ArrayList<>();
            for(BorrowRequest request:borrowRequestList){
                if(request.getMonthlyInterestRate().compareTo(interest)<=0){
                    requiredRequestList.add(request);
                }
            }
            return ResponseEntity.ok(requiredRequestList);
        }
        catch(ServiceException e){
            log.error("Error encountered in fetching borrow requests for the given community and interest");
            throw new ControllerException("Error encountered in fetching borrow requests for the given community and interest",e);
        }
    }

    /*For Administration Purpose*/
    @GetMapping("/get-all-borrow-requests")
    public ResponseEntity<?> getAllBorrowRequests() throws ControllerException {
        try {
            List<BorrowRequest>borrowRequestList=borrowRequestService.getAllBorrowRequests();

            if (borrowRequestList == null || borrowRequestList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No borrow requests created yet");

            }

            return ResponseEntity.ok(borrowRequestList);
        } catch (Exception e) {
            log.error("Error encountered in getting all borrow requests", e);
            throw new ControllerException("Error encountered in getting all borrow requests", e);
        }
    }









}