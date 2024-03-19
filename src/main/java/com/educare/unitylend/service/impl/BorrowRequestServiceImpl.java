package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.BorrowRequestRepository;
import com.educare.unitylend.dao.UserRepository;
import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.Status;
import com.educare.unitylend.model.User;
import com.educare.unitylend.service.BorrowRequestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class BorrowRequestServiceImpl implements BorrowRequestService {

    private BorrowRequestRepository borrowRequestRepository;
    private UserRepository userRepository;

    @Override
    public List<BorrowRequest> getAllBorrowRequests() throws ServiceException {
        return null;
    }

    @Override
    public BorrowRequest getBorrowRequestByRequestId(String borrowRequestId) throws ServiceException {
        try {
            BorrowRequest borrowRequest = borrowRequestRepository.getBorrowRequestByRequestId(borrowRequestId);

            String userId = borrowRequestRepository.getUserIdByRequestId(borrowRequestId);
            log.info("borr req:: " + borrowRequest);
            log.info("user id:: " + userId);
            User borrower = userRepository.getUserForUserId(userId);
            borrowRequest.setBorrower(borrower);
            return borrowRequest;
        } catch (Exception e) {
            log.error("Error encountered in fetching borrow request by id");
            throw new ServiceException("Error encountered in fetching borrow request by id", e);
        }
    }

    @Override
    public Boolean updateCollectedAmount(String borrowRequestId, BigDecimal amount) throws ServiceException{
        try {
           Integer rowsAffected=borrowRequestRepository.updateCollectedAmount(borrowRequestId,amount);
           return rowsAffected>0;
        } catch (Exception e) {
            log.error("Error encountered in updating collected amount in given borrow request");
            throw new ServiceException("Error encountered in updating collected amount in given borrow request", e);
        }
    }

    @Override
    public Boolean isLendAmountValid(String borrowRequestId, BigDecimal amount) throws ServiceException {
        try{
            Boolean isLendPossible=borrowRequestRepository.isLendAmountValid(borrowRequestId,amount);
            return isLendPossible;
        }
        catch (Exception e){
            log.info("Error encountered while checking the possibility of lend operation");
            throw new ServiceException("Error encountered while checking the possibility of lend operation",e);
        }
    }

    @Override
    public List<BorrowRequest> getBorrowRequestForUserId(String userId) throws ServiceException {
        return null;
    }

    @Override
    public Boolean createBorrowRequest(BorrowRequest borrowRequest) throws ServiceException {
        return false;
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
