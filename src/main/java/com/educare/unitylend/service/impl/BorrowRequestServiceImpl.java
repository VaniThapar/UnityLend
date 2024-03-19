package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.BorrowRequestRepository;
import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.Status;
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
        BorrowRequestRepository borrowRequestRepository;
    @Override
    public List<BorrowRequest> getAllBorrowRequests() throws ServiceException {
        return null;
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
    public List<BorrowRequest> getBorrowRequestsInCommunityLessThanAmount(BigDecimal maxAmount, String communityId) throws ServiceException {
        try {


            return borrowRequestRepository.findByRequestedAmountLessThanAndCommunityIdsContaining(maxAmount, communityId);
        } catch (Exception e) {
            throw new ServiceException("Error retrieving borrow requests with amount less than " + maxAmount, e);
        }
    }

    @Override
    public List<BorrowRequest> getBorrowRequestsInCommunityGreaterThanAmount(BigDecimal minAmount, String communityId) throws ServiceException {
        try {
            return borrowRequestRepository.findByRequestedAmountGreaterThanEqualAndCommunityIdsContaining(minAmount, communityId);
        } catch (Exception e) {
            throw new ServiceException("Error retrieving borrow requests with amount greater than or equal to " + minAmount, e);
        }
    }

    @Override
    public List<BorrowRequest> getBorrowRequestsInCommunityInRange(BigDecimal minAmount, BigDecimal maxAmount, String communityId) throws ServiceException {
        try {
            return borrowRequestRepository.findByRequestedAmountBetweenAndCommunityIdsContaining(minAmount, maxAmount, communityId);
        } catch (Exception e) {
            throw new ServiceException("Error retrieving borrow requests with amount between " + minAmount + " and " + maxAmount, e);
        }
    }

}
