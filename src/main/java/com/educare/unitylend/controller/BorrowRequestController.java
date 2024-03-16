package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.BorrowRequest;
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

    /**
     * API endpoint for creating a new borrow request.
     *
     * @param borrowRequest The borrow request object containing borrow request details to be created.
     * @return ResponseEntity<Boolean> Indicating success or failure of the borrow request creation process.
     * @throws ControllerException If an error occurs during the borrow request creation process.
     */
    @PostMapping("/create-borrow-request")
    ResponseEntity<Boolean> createBorrowRequest(@RequestBody BorrowRequest borrowRequest) throws ControllerException {
        return null;
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
        return null;
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
        return null;
    }


    /**
     * API endpoint for retrieving borrow requests in a community within an amount range.
     *
     * @param minAmount   The minimum amount of the borrow requests.
     * @param maxAmount   The maximum amount of the borrow requests.
     * @param lessThan    Flag indicating if the amount should be less than maxAmount.
     * @param greaterThan Flag indicating if the amount should be greater than minAmount.
     * @return ResponseEntity<List<BorrowRequest>> The list of borrow requests in the community within the specified amount range.
     * @throws ControllerException If an error occurs during the borrow request retrieval process.
     */
    @GetMapping("/get-borrow-request-in-community-by-amount")
    ResponseEntity<List<BorrowRequest>> getBorrowRequestsInCommunityInAmountRange(
            @RequestParam(value = "minAmount", required = false) BigDecimal minAmount,
            @RequestParam(value = "maxAmount", required = false) BigDecimal maxAmount,
            @RequestParam(value = "lessThan", required = false, defaultValue = "false") boolean lessThan,
            @RequestParam(value = "greaterThan", required = false, defaultValue = "true") boolean greaterThan
    ) throws ControllerException {
        try{
            List<BorrowRequest> borrowRequests = new ArrayList<>();

            if (minAmount != null && maxAmount != null) {
                if (minAmount.compareTo(maxAmount) >= 0) {
                    log.error("minAmount is greater than maxAmount which is not permissible");
                    return ResponseEntity.badRequest().body(borrowRequests);
                }
            }

            else if(minAmount==null && maxAmount==null){
                log.error("Both minAmount and maxAmount are empty");
                return ResponseEntity.badRequest().body(borrowRequests);
            }

            if(lessThan && greaterThan){
                return ResponseEntity.badRequest().body(borrowRequests);
            }
            else if(lessThan){
                borrowRequests=borrowRequestService.getBorrowRequestsInCommunityLessThanAmount(maxAmount);
            }
            else if(greaterThan){
                borrowRequests=borrowRequestService.getBorrowRequestsInCommunityGreaterThanAmount(minAmount);
            }
            else{
                borrowRequests=borrowRequestService.getBorrowRequestsInCommunityInRange(minAmount,maxAmount);
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
     * @return ResponseEntity<List<BorrowRequest>> The list of borrow requests with interest rates greater than the specified rate.
     * @throws ControllerException If an error occurs during the borrow request retrieval process.
     */
    @GetMapping("/get-borrow-request-in-community-by-interest/{interest}")
    ResponseEntity<List<BorrowRequest>>getBorrowRequestsInCommunityByInterestRateGreaterThan(@PathVariable BigDecimal interest) throws ControllerException{
        return null;
    }


    /* For Administration Purposes */
    /**
     * API endpoint for retrieving all borrow requests.
     *
     * @return ResponseEntity<List<BorrowRequest>> The list of all borrow requests.
     * @throws ControllerException If an error occurs during the borrow request retrieval process.
     */
    @GetMapping("/get-all-borrow-requests")
    ResponseEntity<List<BorrowRequest>> getAllBorrowRequests() throws ControllerException {
        return null;
    }
}