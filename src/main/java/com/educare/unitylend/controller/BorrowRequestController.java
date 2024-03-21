package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.StatusRepository;
import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.Status;
import com.educare.unitylend.service.BorrowRequestCommunityMapService;
import com.educare.unitylend.service.BorrowRequestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/borrow-request")
public class BorrowRequestController extends BaseController {

    private BorrowRequestService borrowRequestService;
    private BorrowRequestCommunityMapService borrowRequestCommunityMapService;
    private StatusRepository statusRepository;

    /**
     * API endpoint for retrieving borrow requests for a specific user ID.
     *
     * @param userId The ID of the user for whom borrow requests are to be retrieved.
     * @return ResponseEntity<List < BorrowRequest>> The list of borrow requests initiated by the specified user.
     * @throws ControllerException If an error occurs during the borrow request retrieval process.
     */
    @GetMapping("/get-borrow-request-by-user-id/{userId}")
    ResponseEntity<List<BorrowRequest>> getBorrowRequestForUserId(@PathVariable String userId) throws ControllerException {
        try {
            // Checking if the user ID is null or empty
            if (userId == null || userId.isEmpty()) {
                log.error("User id cannot be null");
                // Returning a bad request response if the user ID is null or empty
                return ResponseEntity.badRequest().body(null);
            }

            // Retrieving borrow requests initiated by the specified user
            List<BorrowRequest> borrowRequestList = borrowRequestService.getBorrowRequestForUserId(userId);
            // Returning a response entity with the list of borrow requests
            return ResponseEntity.ok(borrowRequestList);
        } catch (ServiceException e) {
            // Logging an error message if an exception occurs during the process
            log.error("Error encountered in fetching the borrow requests by user id", e);
            // Throwing a ControllerException with an error message if an exception occurs
            throw new ControllerException("Error encountered in fetching the borrow requests by user id", e);
        }
    }

    /**
     * API endpoint for retrieving borrow requests for a specific community.
     *
     * @param communityId The ID of the community for which borrow requests are to be retrieved.
     * @return ResponseEntity<List < BorrowRequest>> The borrow request associated with the specified community.
     * @throws ControllerException If an error occurs during the borrow request retrieval process.
     */
    @GetMapping("/get-borrow-request-by-community-id/{communityId}")
    ResponseEntity<List<BorrowRequest>> getBorrowRequestForCommunity(@PathVariable String communityId) throws ControllerException {
        try {
            // Checking if the community ID is null or empty
            if (communityId == null || communityId.isEmpty()) {
                log.error("Commmunity id cannot be null");
                // Returning a bad request response if the community ID is null or empty
                return ResponseEntity.badRequest().body(null);
            }

            // Retrieving borrow requests associated with the specified community ID
            List<BorrowRequest> borrowRequestList = borrowRequestCommunityMapService.getRequestsByCommunityId(communityId);

            // Checking if no borrow requests are found for the given community
            if (borrowRequestList == null || borrowRequestList.isEmpty()) {
                log.error("No borrow requests found for given community");
                // Returning a bad request response if no borrow requests are found
                return ResponseEntity.badRequest().body(null);
            }

            // Returning a response entity with the list of borrow requests
            return ResponseEntity.ok(borrowRequestList);
        } catch (ServiceException e) {
            // Logging an error message if an exception occurs during the process
            log.error("Error encountered in fetching borrow requests in a community", e);
            // Throwing a ControllerException with an error message if an exception occurs
            throw new ControllerException("Error encountered in fetching borrow requests in a community", e);
        }
    }


    /**
     * API endpoint for retrieving borrow requests in a community within an amount range.
     *
     * @param minAmount   The minimum amount of the borrow requests.
     * @param maxAmount   The maximum amount of the borrow requests.
     * @param lessThan    Flag indicating if the amount should be less than maxAmount.
     * @param greaterThan Flag indicating if the amount should be greater than minAmount.
     * @return ResponseEntity<List < BorrowRequest>> The list of borrow requests in the community within the specified amount range.
     * @throws ControllerException If an error occurs during the borrow request retrieval process.
     */
    @GetMapping("/get-borrow-request-in-community-by-amount")
    ResponseEntity<List<BorrowRequest>> getBorrowRequestsInCommunityInAmountRange(
            @RequestParam(value = "minAmount", required = false) BigDecimal minAmount,
            @RequestParam(value = "maxAmount", required = false) BigDecimal maxAmount,
            @RequestParam(value = "lessThan", required = false, defaultValue = "false") boolean lessThan,
            @RequestParam(value = "greaterThan", required = false, defaultValue = "true") boolean greaterThan
    ) throws ControllerException {
        try {
            List<BorrowRequest> borrowRequests = new ArrayList<>();

            if (minAmount != null && maxAmount != null) {
                if (minAmount.compareTo(maxAmount) >= 0) {
                    log.error("minAmount is greater than maxAmount which is not permissible");
                    return ResponseEntity.badRequest().body(borrowRequests);
                }
            } else if (minAmount == null && maxAmount == null) {
                log.error("Both minAmount and maxAmount are empty");
                return ResponseEntity.badRequest().body(borrowRequests);
            }

            if (lessThan && greaterThan) {
                return ResponseEntity.badRequest().body(borrowRequests);
            } else if (lessThan) {
                borrowRequests = borrowRequestService.getBorrowRequestsInCommunityLessThanAmount(maxAmount);
            } else if (greaterThan) {
                borrowRequests = borrowRequestService.getBorrowRequestsInCommunityGreaterThanAmount(minAmount);
            } else {
                borrowRequests = borrowRequestService.getBorrowRequestsInCommunityInRange(minAmount, maxAmount);
            }

            return ResponseEntity.ok(borrowRequests);
        } catch (ServiceException e) {
            log.error("Error in filtering borrow requests based on amount");
            throw new ControllerException("Error in filtering borrow requests based on amount", e);
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
            // Checking if community ID or interest rate is provided
            if (communityId == null || communityId.isEmpty() || interest == null) {
                log.error("Community ID or interest rate cannot be null");
                // Returning a bad request response if community ID or interest rate is not provided
                return ResponseEntity.badRequest().body(null);
            }

            // Retrieving borrow requests associated with the specified community ID
            List<BorrowRequest> borrowRequestList = borrowRequestCommunityMapService.getRequestsByCommunityId(communityId);

            // Checking if no borrow requests are found for the given community
            if (borrowRequestList == null || borrowRequestList.isEmpty()) {
                log.error("No borrow requests found for given community and amount");
                // Returning a bad request response if no borrow requests are found
                return ResponseEntity.badRequest().body(null);
            }

            List<BorrowRequest> requiredRequestList = new ArrayList<>();
            // Filtering borrow requests with interest rates greater than the specified rate
            for (BorrowRequest request : borrowRequestList) {
                System.out.println(request.getMonthlyInterestRate());
                if (request.getMonthlyInterestRate().compareTo(interest) <= 0) {
                    requiredRequestList.add(request);
                }
            }
            // Returning a response entity with the filtered list of borrow requests
            return ResponseEntity.ok(requiredRequestList);
        } catch (ServiceException e) {
            // Logging an error message if an exception occurs during the process
            log.error("Error encountered in fetching borrow requests for the given community and interest", e);
            // Throwing a ControllerException with an error message if an exception occurs
            throw new ControllerException("Error encountered in fetching borrow requests for the given community and interest", e);
        }
    }


    /**
     * API endpoint for retrieving all borrow requests.
     *
     * @return ResponseEntity<List < BorrowRequest>> The list of all borrow requests.
     * @throws ControllerException If an error occurs during the borrow request retrieval process.
     */
    @GetMapping("/get-all-borrow-requests")
    ResponseEntity<List<BorrowRequest>> getAllBorrowRequests() throws ControllerException {
        try {
            // Retrieving all borrow requests
            List<BorrowRequest> borrowRequestList = borrowRequestService.getAllBorrowRequests();

            // Checking if no borrow requests are found
            if (borrowRequestList == null || borrowRequestList.isEmpty()) {
                log.error("No borrow requests created yet");
                // Returning a bad request response if no borrow requests are found
                return ResponseEntity.badRequest().body(null);
            }

            // Returning a response entity with the list of all borrow requests
            return ResponseEntity.ok(borrowRequestList);
        } catch (Exception e) {
            // Logging an error message if an exception occurs during the process
            log.error("Error encountered in getting all borrow requests", e);
            // Throwing a ControllerException with an error message if an exception occurs
            throw new ControllerException("Error encountered in getting all borrow requests", e);
        }
    }
}

