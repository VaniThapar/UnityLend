package com.educare.unitylend.service;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.BorrowRequestRepository;
import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.Status;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

public interface BorrowRequestService {
    Boolean createBorrowRequest(BorrowRequest borrowRequest) throws ServiceException;
    boolean validateBorrowRequest(BorrowRequest borrowRequest) throws ServiceException;
    boolean isUserPartOfCommunity(BorrowRequest borrowRequest) throws ServiceException;
    boolean isBorrowRequestPending(BorrowRequest borrowRequest) throws ServiceException;
    boolean isAnythingNull(BorrowRequest borrowRequest) throws ServiceException;
    List<BorrowRequest> getBorrowRequestForUserId(String userId) throws ServiceException;
    List<BorrowRequest> getBorrowRequestForCommunity(String communityId) throws ServiceException;
    Boolean updateEMIDefaults() throws ServiceException;
    Boolean updateBorrowRequestStatus(Status status) throws ServiceException;
    List<BorrowRequest> getBorrowRequestsInCommunityLessThanAmount(BigDecimal maxAmount) throws ServiceException;
    List<BorrowRequest> getBorrowRequestsInCommunityGreaterThanAmount(BigDecimal minAmount) throws ServiceException;
    List<BorrowRequest> getBorrowRequestsInCommunityInRange(BigDecimal minAmount, BigDecimal maxAmount) throws ServiceException;
    List<BorrowRequest> getAllBorrowRequests() throws ServiceException;
}