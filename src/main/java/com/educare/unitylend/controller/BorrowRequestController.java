package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.*;
import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.service.BorrowRequestCommunityMapService;
import com.educare.unitylend.service.BorrowRequestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/borrow-request")
public class BorrowRequestController extends BaseController {

    private BorrowRequestService borrowRequestService;
    private BorrowRequestCommunityMapService borrowRequestCommunityMapService;

    /**
     * API endpoint for creating a new borrow request.
     *
     * @param borrowRequest The borrow request object containing borrow request details to be created.
     * Required field to create a borrow request: {"borrower": {userId, password, income, communityDetails}, returnPeriodMonths, monthlyInterestRate, requestedAmount, communityIds}
     * @return ResponseEntity<?> Indicating success or failure of the borrow request creation process.
     * @throws ControllerException If an error occurs during the borrow request creation process.
     */
    @PostMapping("/create-borrow-request")
    ResponseEntity<?> createBorrowRequest(@RequestBody BorrowRequest borrowRequest) throws ControllerException{
        try {
            String userId = borrowRequest.getBorrower().getUserId();
            if (userId == null || userId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Id cannot be empty.");
            }
            if(borrowRequestService.isAnythingNull(borrowRequest)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("One of the required fields is empty!");
            }
            if(!borrowRequestService.isPasswordCorrect(borrowRequest)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password incorrect");
            }
            if(borrowRequestService.isBorrowRequestPending(borrowRequest)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pending borrow requests found. Complete previous borrow requests to raise a new one!");
            }
            boolean isBorrowRequestValid = borrowRequestService.validateBorrowRequest(borrowRequest);
            boolean UserPartOfCommunity = borrowRequestService.isUserPartOfCommunity(borrowRequest);
            if (isBorrowRequestValid && UserPartOfCommunity) {
                borrowRequestService.createBorrowRequest(borrowRequest);
                return ResponseEntity.ok(true);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Either you are not a part of the community or you do not satisfy the income-emi constraint.");
            }
        } catch (Exception e) {
            log.error("Error encountered in raising borrow request for user with ID: {}", borrowRequest.getBorrower(), e);
            throw new ControllerException("Error encountered in raising the borrow requests", e);
        }
    }

    /**
     * API endpoint for retrieving borrow requests for a specific user ID.
     *
     * @param userId The ID of the user for whom borrow requests are to be retrieved.
     * @return ResponseEntity<List<BorrowRequest>> The list of borrow requests initiated by the specified user.
     * @throws ControllerException If an error occurs during the borrow request retrieval process.
     */
    @GetMapping("/get-borrow-request-by-user-id/{userId}")
    ResponseEntity<List<BorrowRequest>> getBorrowRequestForUserId(@PathVariable String userId) throws ControllerException {
        try {
            if (userId == null || userId.isEmpty()) {
                log.error("User id cannot be null");
                return ResponseEntity.badRequest().body(null);
            }
            List<BorrowRequest> borrowRequestList = borrowRequestService.getBorrowRequestForUserId(userId);
            return ResponseEntity.ok(borrowRequestList);
        } catch (ServiceException e) {
            log.error("Error encountered in fetching the borrow requests by user id", e);
            throw new ControllerException("Error encountered in fetching the borrow requests by user id", e);
        }
    }

    /**
     * API endpoint for retrieving borrow requests for a specific community.
     *
     * @param communityId The ID of the community for which borrow requests are to be retrieved.
     * @return ResponseEntity<List<BorrowRequest>> The borrow request associated with the specified community.
     * @throws ControllerException If an error occurs during the borrow request retrieval process.
     */
    @GetMapping("/get-borrow-request-by-community-id/{communityId}")
    ResponseEntity<List<BorrowRequest>> getBorrowRequestForCommunity(@PathVariable String communityId) throws ControllerException {
        try {
            if (communityId == null || communityId.isEmpty()) {
                log.error("Community id cannot be null");
                return ResponseEntity.badRequest().body(null);
            }
            List<BorrowRequest> borrowRequestList = borrowRequestCommunityMapService.getRequestsByCommunityId(communityId);
            if (borrowRequestList == null || borrowRequestList.isEmpty()) {
                log.error("No borrow requests found for given community");
                return ResponseEntity.badRequest().body(null);
            }
            return ResponseEntity.ok(borrowRequestList);
        } catch (ServiceException e) {
            log.error("Error encountered in fetching borrow requests in a community", e);
            throw new ControllerException("Error encountered in fetching borrow requests in a community", e);
        }
    }

    /**
     * API endpoint for retrieving borrow requests in a community within an amount range.
     *@PathVariable String communityId
     * @param minAmount   The minimum amount of the borrow requests.
     * @param maxAmount   The maximum amount of the borrow requests.
     * @param lessThan    Flag indicating if the amount should be less than maxAmount.
     * @param greaterThan Flag indicating if the amount should be greater than minAmount.
     * @return ResponseEntity<List<BorrowRequest>> The list of borrow requests in the community within the specified amount range.
     * @throws ControllerException If an error occurs during the borrow request retrieval process.
     */
    @PostMapping("/min/{communityId}")
    ResponseEntity<List<BorrowRequest>> getBorrowRequestsInCommunityInAmountRange(
            @RequestParam(value = "minAmount", required = false) BigDecimal minAmount,
            @RequestParam(value = "maxAmount", required = true) BigDecimal maxAmount,
            @RequestParam(value = "lessThan", required = false, defaultValue = "false") boolean lessThan,
            @RequestParam(value = "greaterThan", required = false, defaultValue = "true") boolean greaterThan,
            @PathVariable String communityId
    ) throws ControllerException {
        try{
            log.info("minAmount: {}", minAmount);
            log.info("maxAmount: {}", maxAmount);
            log.info("lessThan: {}", lessThan);
            log.info("greaterThan: {}", greaterThan);
            log.info("communityId: {}", communityId);
            List<BorrowRequest> borrowRequests = new ArrayList<>();
            if (minAmount != null && maxAmount != null) {
                if (minAmount.compareTo(maxAmount) > 0) {
                    log.error("minAmount is greater than maxAmount which is not permissible");
                    return ResponseEntity.badRequest().body(borrowRequests);
                }
            }
            else if(minAmount==null && maxAmount==null){
                log.error("Both minAmount and maxAmount are empty");
                return ResponseEntity.badRequest().body(borrowRequests);
            }
            if(lessThan && greaterThan){
                log.error("Inaccurate Constrains");
                return ResponseEntity.badRequest().body(borrowRequests);
            }
            else if(lessThan){
                borrowRequests=borrowRequestService.getBorrowRequestsInCommunityLessThanAmount(maxAmount, communityId);
            }
            else if(greaterThan){
                borrowRequests=borrowRequestService.getBorrowRequestsInCommunityGreaterThanAmount(minAmount, communityId);
            }
            else{
                borrowRequests=borrowRequestService.getBorrowRequestsInCommunityInRange(minAmount,maxAmount, communityId);
            }
            return ResponseEntity.ok(borrowRequests);
        }
        catch(ServiceException e){
            log.error("Error in filtering borrow requests based on amount");
            throw new ControllerException("Error in filtering borrow requests based on amount",e);
        }
    }


    /**
     * API endpoint for retrieving borrow requests within a community based on interest rate.
     *
     * @param interest The interest rate used to filter borrow requests.
     * @return ResponseEntity<List < BorrowRequest>> The list of borrow requests with interest rates greater than the specified rate.
     * @throws ControllerException If an error occurs during the borrow request retrieval process.
     */
    @GetMapping("/get-borrow-request-in-community-by-interest/{communityId}/{interest}")
    ResponseEntity<List<BorrowRequest>> getBorrowRequestsInCommunityByInterestRateGreaterThan(@PathVariable String communityId, @PathVariable BigDecimal interest) throws ControllerException {
        try {
            if (communityId == null || communityId.isEmpty() || interest == null) {
                log.error("Community ID or interest rate cannot be null");
                return ResponseEntity.badRequest().body(null);
            }
            List<BorrowRequest> borrowRequestList = borrowRequestCommunityMapService.getRequestsByCommunityId(communityId);
            if (borrowRequestList == null || borrowRequestList.isEmpty()) {
                log.error("No borrow requests found for given community and interest.");
                return ResponseEntity.badRequest().body(null);
            }
            List<BorrowRequest> requiredRequestList = new ArrayList<>();
            for (BorrowRequest request : borrowRequestList) {
                if (request.getMonthlyInterestRate().compareTo(interest) > 0) {
                    requiredRequestList.add(request);
                }
            }
            return ResponseEntity.ok(requiredRequestList);
        } catch (ServiceException e) {
            log.error("Error encountered in fetching borrow requests for the given community and interest", e);
            throw new ControllerException("Error encountered in fetching borrow requests for the given community and interest", e);
        }
    }

    /**
     * API endpoint for retrieving all borrow requests.
     *
     * @return ResponseEntity<List<BorrowRequest>> The list of all borrow requests.
     * @throws ControllerException If an error occurs during the borrow request retrieval process.
     */
    @GetMapping("/get-all-borrow-requests")
    ResponseEntity<List<BorrowRequest>> getAllBorrowRequests() throws ControllerException {
        try {
            List<BorrowRequest> borrowRequestList = borrowRequestService.getAllBorrowRequests();
            if (borrowRequestList == null || borrowRequestList.isEmpty()) {
                log.error("No borrow requests created yet");
                return ResponseEntity.badRequest().body(null);
            }
            return ResponseEntity.ok(borrowRequestList);
        } catch (Exception e) {
            log.error("Error encountered in getting all borrow requests", e);
            throw new ControllerException("Error encountered in getting all borrow requests", e);
        }
    }

}