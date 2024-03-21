package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.BorrowRequestCommunityMapRepository;
import com.educare.unitylend.dao.BorrowRequestRepository;
import com.educare.unitylend.dao.CommunityRepository;
import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.Status;
import com.educare.unitylend.service.BorrowRequestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Service
public class BorrowRequestServiceImpl implements BorrowRequestService {

    private BorrowRequestRepository borrowRequestRepository;
    private BorrowRequestCommunityMapRepository borrowRequestCommunityMapRepository;
    private CommunityRepository communityRepository;
    @Override
    public List<BorrowRequest> getAllBorrowRequests() throws ServiceException {
        return null;
    }
    /**
     * Validating a borrow request on the basis of the monthly income of the borrower and his monthly emi he has to pay
     *
     * @param borrowRequest The borrow request object containing borrow request details to be created.
     * @return boolean Indicating whether the request is validated based on above parameters or not
     * @throws ServiceException If an error occurs during the validation of borrow request.
     */
    @Override
    public boolean validateBorrowRequest(BorrowRequest borrowRequest) throws ServiceException{
        try{
            Integer tenure = borrowRequest.getReturnPeriodMonth();
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
            List<String> requestedCommunityIds = borrowRequest.getCommunityIds();
            Map<String, String> borrowerCommunities = borrowRequest.getBorrower().getCommunityDetails();
            for (String requestedCommunityId : requestedCommunityIds) {
                String requestedCommunityName = communityRepository.getCommunityName(requestedCommunityId);
                if (!borrowerCommunities.containsValue(requestedCommunityName)) {
                    return false;
                }
            }
            return true;
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
            String borrowerId = borrowRequest.getBorrower().getUserId();
            if(borrowRequestRepository.isRequestPending(borrowerId)) return true;
            return false;
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
            if(borrowRequest.getBorrower().getUserId() == null || borrowRequest.getBorrower().getPassword() == null || borrowRequest.getBorrower().getIncome() == null || borrowRequest.getBorrower().getCommunityDetails() == null ||borrowRequest.getReturnPeriodMonth() == null || borrowRequest.getMonthlyInterestRate() == null || borrowRequest.getRequestedAmount() == null || borrowRequest.getCommunityIds() == null) return true;
            return false;
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
            String passwordProvided = borrowRequest.getBorrower().getPassword();
            String userId = borrowRequest.getBorrower().getUserId();
            if(borrowRequestRepository.checkPassword(passwordProvided, userId)) return true;
            return false;
        }
        catch(Exception e){
            throw new ServiceException("Error checking password: " + e.getMessage(), e);
        }
    }

    @Override
    public List<BorrowRequest> getBorrowRequestForUserId(String userId) throws ServiceException {
        return null;
    }

    @Override
    public Boolean createBorrowRequest(BorrowRequest borrowRequest) throws ServiceException {

        try{
            Boolean flag = borrowRequestRepository.createBorrowRequest(borrowRequest,borrowRequest.getBorrower().getUserId());

            String requestId = borrowRequest.getBorrowRequestId();
            List<String> communityId = borrowRequest.getCommunityIds();

            borrowRequestCommunityMapRepository.createBorrowRequestCommunityMapping(requestId,communityId);
            return flag;
        }
        catch(Exception e){
            throw new ServiceException("Error creating requests in implementation layer: " + e.getMessage(), e);
        }
    }

    @Override
    public List<BorrowRequest> getBorrowRequestForCommunity(String communityId) throws ServiceException {
        return null;
    }

    @Override
    public Boolean updateEMIDefaults() throws ServiceException {
        return false;
    }

    @Override
    public Boolean updateBorrowRequestStatus(Status status) throws ServiceException {
        return false;
    }

    @Override
    public List<BorrowRequest> getBorrowRequestsInCommunityLessThanAmount(BigDecimal maxAmount) throws ServiceException {
        return null;
    }

    @Override
    public List<BorrowRequest> getBorrowRequestsInCommunityGreaterThanAmount(BigDecimal minAmount) throws ServiceException {
        return null;
    }

    @Override
    public List<BorrowRequest> getBorrowRequestsInCommunityInRange(BigDecimal minAmount, BigDecimal maxAmount) throws ServiceException {
        return null;
    }
}
