package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.*;
import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.Status;
import com.educare.unitylend.model.User;
import com.educare.unitylend.service.BorrowRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Service
public class BorrowRequestServiceImpl implements BorrowRequestService {

    private BorrowRequestRepository borrowRequestRepository;
    private UserRepository userRepository;
    private BorrowRequestCommunityMapRepository borrowRequestCommunityMapRepository;
    private StatusRepository statusRepository;
    private UserCommunityMapRepository userCommunityMapRepository;
    private CommunityRepository communityRepository;

    /**
     * Retrieves all borrow requests.
     *
     * @return List<BorrowRequest> The list of all borrow requests.
     * @throws ServiceException If an error occurs during the process.
     */
    @Override
    public List<BorrowRequest> getAllBorrowRequests() throws ServiceException {
        try {
            List<BorrowRequest> borrowRequestList = borrowRequestRepository.getAllBorrowRequests();
            for(BorrowRequest request : borrowRequestList){
                String borrowerId=borrowRequestRepository.getBorrowerIdUsingRequestId(request.getBorrowRequestId());
                User requiredUser = userRepository.getUserForUserId(borrowerId);
                request.setBorrower(requiredUser);
                ObjectMapper objectMapper = new ObjectMapper();
                String communityDetailsJson = requiredUser.getCommunityDetailsJson();
                Map<String, String> communityDetails = objectMapper.readValue(communityDetailsJson, new TypeReference<Map<String, String>>() {
                });
                requiredUser.setCommunityDetails(communityDetails);
                request.setCommunityIds(userCommunityMapRepository.getCommunityIdsWithUserId(borrowerId));
                Integer code=borrowRequestRepository.getStatusCodeForReqId(request.getBorrowRequestId());
                Status status=statusRepository.getStatusByStatusCode(code);
                request.setBorrowStatus(status);

            }
            return borrowRequestList;
        } catch (Exception e) {
            log.error("Error encountered during getting requests by user id");
            throw new ServiceException("Error encountered during getting requests by user id", e);
        }
    }

    /**
     * Retrieves borrow requests for a given user ID.
     *
     * @param userId The ID of the user for which borrow requests are to be retrieved.
     * @return List<BorrowRequest> The list of borrow requests associated with the user.
     * @throws ServiceException If an error occurs during the process.
     */
    @Override
    public List<BorrowRequest> getBorrowRequestForUserId(String userId) throws ServiceException {
        try {
            if(userId != null) {
                List<BorrowRequest> borrowRequestList = borrowRequestRepository.getBorrowRequestsByUserId(userId);
                for (BorrowRequest borrowRequest : borrowRequestList) {
                    User requiredUser = userRepository.getUserForUserId(userId);
                    borrowRequest.setBorrower(requiredUser);
                    Integer code = borrowRequestRepository.getStatusCodeForReqId(borrowRequest.getBorrowRequestId());
                    Status status = statusRepository.getStatusByStatusCode(code);
                    borrowRequest.setBorrowStatus(status);
                    ObjectMapper objectMapper = new ObjectMapper();
                    String communityDetailsJson = requiredUser.getCommunityDetailsJson();
                    Map<String, String> communityDetails = objectMapper.readValue(communityDetailsJson, new TypeReference<Map<String, String>>() {
                    });
                    requiredUser.setCommunityDetails(communityDetails);
                    borrowRequest.setCommunityIds(userCommunityMapRepository.getCommunityIdsWithUserId(userId));
                }
                return borrowRequestList;
            }
            else{
                log.error("User Id cannot be empty.");
                return Collections.emptyList();
            }
        } catch (Exception e) {
            log.error("Error encountered during getting requests by user id");
            throw new ServiceException("Error encountered during getting requests by user id", e);
        }
    }

    /**
     * Updates the status of a borrow request.
     *
     * @param borrowRequest The borrow request to be updated.
     * @param status The new status to be set for the borrow request.
     * @return Boolean True if the update is successful, false otherwise.
     * @throws ServiceException If an error occurs during the update process.
     */
    @Override
    public Boolean updateBorrowRequestStatus(BorrowRequest borrowRequest, Status status) throws ServiceException {
        try {
            if(borrowRequest != null && status != null) {
                Integer statusCode = status.getStatusCode();
                String requestId = borrowRequest.getBorrowRequestId();
                borrowRequestRepository.updateStatusOfBorrowRequest(statusCode, requestId);
                borrowRequest.setBorrowStatus(status);
                return true;
            }
            else{
                log.error("Cannot update");
                return false;
            }
        } catch (Exception e) {
            log.error("Error while updating borrow request");
            throw new ServiceException("Error while updating borrow request", e);
        }
    }

    /**
     * Retrieves borrow requests in a community with amounts less than a specified value.
     *
     * @param maxAmount    The maximum amount requested in the borrow request.
     * @param communityId  The ID of the community to search within.
     * @return List of BorrowRequest objects meeting the criteria.
     * @throws ServiceException If an error occurs while retrieving borrow requests.
     */
    @Override
    public List<BorrowRequest> getBorrowRequestsInCommunityLessThanAmount(BigDecimal maxAmount, String communityId) throws ServiceException {
        try {
            if(maxAmount!=null && communityId!=null) {
                return borrowRequestRepository.findByRequestedAmountLessThanAndCommunityIdsContaining(maxAmount, communityId);
            }
            else{
                log.error("Cannot fetch requests");
                return Collections.emptyList();
            }
        } catch (Exception e) {
            throw new ServiceException("Error retrieving borrow requests with amount less than " + maxAmount, e);
        }
    }

    /**
     * Retrieves borrow requests in a community with amounts greater than or equal to a specified value.
     *
     * @param minAmount    The minimum amount requested in the borrow request.
     * @param communityId  The ID of the community to search within.
     * @return List of BorrowRequest objects meeting the criteria.
     * @throws ServiceException If an error occurs while retrieving borrow requests.
     */
    @Override
    public List<BorrowRequest> getBorrowRequestsInCommunityGreaterThanAmount(BigDecimal minAmount, String communityId) throws ServiceException {
        try {
            if(minAmount!=null && communityId!=null) {
                return borrowRequestRepository.findByRequestedAmountGreaterThanEqualAndCommunityIdsContaining(minAmount, communityId);
            }
            else{
                log.error("Cannot fetch requests.");
                return Collections.emptyList();
            }
        } catch (Exception e) {
            throw new ServiceException("Error retrieving borrow requests with amount greater than or equal to " + minAmount, e);
        }
    }

    /**
     * Retrieves borrow requests within a specified range of amounts in a community.
     *
     * @param minAmount    The minimum amount requested in the borrow request.
     * @param maxAmount    The maximum amount requested in the borrow request.
     * @param communityId  The ID of the community to search within.
     * @return List of BorrowRequest objects meeting the criteria.
     * @throws ServiceException If an error occurs while retrieving borrow requests.
     */
    @Override
    public List<BorrowRequest> getBorrowRequestsInCommunityInRange(BigDecimal minAmount, BigDecimal maxAmount, String communityId) throws ServiceException {
        try {
            if(minAmount!=null && maxAmount!=null && communityId!=null) {
                return borrowRequestRepository.findByRequestedAmountBetweenAndCommunityIdsContaining(minAmount, maxAmount, communityId);
            }
            else{
                log.error("Cannot fetch requests");
                return Collections.emptyList();
            }
        } catch (Exception e) {
            throw new ServiceException("Error retrieving borrow requests with amount between " + minAmount + " and " + maxAmount, e);
        }
    }

    /**
     * Checking whether the borrow request is valid based on emi-income constraints
     *
     * @param borrowRequest The borrow request object containing borrow request details to be created.
     * @return boolean Indicating whether the borrow request is valid or not
     * @throws ServiceException If an error occurs during the check.
     */
    @Override
    public boolean validateBorrowRequest(BorrowRequest borrowRequest) throws ServiceException{
        try{
            Integer tenure = borrowRequest.getReturnPeriodMonths();
            BigDecimal monthlyIncome = borrowRequest.getBorrower().getIncome();
            BigDecimal borrowedAmount = borrowRequest.getRequestedAmount();
            BigDecimal monthlyEMI = borrowedAmount.divide(BigDecimal.valueOf(tenure));
            BigDecimal limitMoney = monthlyIncome.divide(BigDecimal.valueOf(2));
            boolean isWithinLimit = monthlyEMI.compareTo(limitMoney) <= 0;
            return isWithinLimit;
        }
        catch(Exception e){
            throw new ServiceException("Error validating borrow requests: " + e.getMessage(), e);
        }
    }

    /**
     * Checking whether the borrower is a part of the community he wants to send the borrow request to
     *
     * @param borrowRequest The borrow request object containing borrow request details to be created.
     * @return boolean Indicating whether the borrower is a part of the community he wants to send borrow request to or not
     * @throws ServiceException If an error occurs during the check.
     */
    @Override
    public boolean isUserPartOfCommunity(BorrowRequest borrowRequest) throws ServiceException{
        try{
            if(borrowRequest!=null) {
                List<String> requestedCommunityIds = borrowRequest.getCommunityIds();
                Map<String, String> borrowerCommunities = borrowRequest.getBorrower().getCommunityDetails();
                for (String requestedCommunityId : requestedCommunityIds) {
                    String requestedCommunityName = communityRepository.getCommunity(requestedCommunityId);
                    if (!borrowerCommunities.containsValue(requestedCommunityName)) {
                        return false;
                    }
                }
                return true;
            }
            else{
                log.error("Cannot add");
                return false;
            }
        }
        catch(Exception e){
            throw new ServiceException("Error verifying borrower's communities: " + e.getMessage(), e);
        }
    }

    /**
     * Checking whether the borrower has any pending borrow request or not. If yes, he cannot raise a new request
     *
     * @param borrowRequest The borrow request object containing borrow request details to be created.
     * @return boolean Indicating whether the borrower has a previous pending request or not
     * @throws ServiceException If an error occurs during the check.
     */
    @Override
    public boolean isBorrowRequestPending(BorrowRequest borrowRequest) throws ServiceException{
        try{
            if(borrowRequest!=null) {
                String borrowerId = borrowRequest.getBorrower().getUserId();
                if (borrowRequestRepository.isRequestPending(borrowerId)) return true;
                return false;
            }
            else{
                log.error("Error in execution");
                return false;
            }
        }
        catch(Exception e){
            throw new ServiceException("Error checking pending requests: " + e.getMessage(), e);
        }
    }

    /**
     * Checking whether the borrower has sent a null field which is required while adding the borrow request to the database
     *
     * @param borrowRequest The borrow request object containing borrow request details to be created.
     * @return boolean Indicating whether the borrower has any null required fields
     * @throws ServiceException If an error occurs during the check.
     */
    @Override
    public boolean isAnythingNull(BorrowRequest borrowRequest) throws ServiceException{
        try{
            if(borrowRequest!=null) {
                if (borrowRequest.getBorrower().getUserId() == null || borrowRequest.getBorrower().getPassword() == null || borrowRequest.getBorrower().getIncome() == null || borrowRequest.getBorrower().getCommunityDetails() == null || borrowRequest.getReturnPeriodMonths() == null || borrowRequest.getMonthlyInterestRate() == null || borrowRequest.getRequestedAmount() == null || borrowRequest.getCommunityIds() == null || borrowRequest.getCommunityIds().isEmpty())
                    return true;
                return false;
            }
            else{
                log.error("Error in execution");
                return false;
            }
        }
        catch(Exception e){
            throw new ServiceException("Error checking null conditions: " + e.getMessage(), e);
        }
    }

    /**
     * Checking whether the borrower has entered the correct password
     * @param borrowRequest The borrow request object containing borrow request details to be created.
     * @return boolean Indicating whether the entered password is correct or not
     * @throws ServiceException If an error occurs during the check.
     */
    @Override
    public boolean isPasswordCorrect(BorrowRequest borrowRequest) throws ServiceException{
        try{
            if(borrowRequest!=null) {
                String passwordProvided = borrowRequest.getBorrower().getPassword();
                String userId = borrowRequest.getBorrower().getUserId();
                if (borrowRequestRepository.checkPassword(passwordProvided, userId)) return true;
                return false;
            }
            else{
                log.error("Error in execution");
                return false;
            }
        }
        catch(Exception e){
            throw new ServiceException("Error checking password: " + e.getMessage(), e);
        }
    }

    /**
     * Creating a borrow-request and add it to the database
     * @param borrowRequest The borrow request object containing borrow request details to be created.
     * @return boolean Indicating whether the borrow request was successfully added to the database or not in the dao layer
     * @throws ServiceException If an error occurs during the addition of request to the table.
     */
    @Override
    public Boolean createBorrowRequest(BorrowRequest borrowRequest) throws ServiceException {
        try{
            if(borrowRequest!=null) {
                Boolean flag = borrowRequestRepository.createBorrowRequest(borrowRequest, borrowRequest.getBorrower().getUserId());
                String requestId = borrowRequest.getBorrowRequestId();
                List<String> communityId = borrowRequest.getCommunityIds();
                borrowRequestCommunityMapRepository.createBorrowRequestCommunityMapping(requestId, communityId);
                return flag;
            }
            else{
                log.error("Error in execution");
                return false;
            }
        }
        catch(Exception e){
            throw new ServiceException("Error creating requests in implementation layer: " + e.getMessage(), e);
        }
    }

}
