package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.BorrowRequestCommunityMapRepository;
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

    private BorrowRequestRepository borrowRequestRepository;
    private BorrowRequestCommunityMapRepository borrowRequestCommunityMapRepository;
    @Override
    public List<BorrowRequest> getAllBorrowRequests() throws ServiceException {
        return null;
    }

    @Override
    public boolean validateBorrowRequest(BorrowRequest borrowRequest){
        return true;
    }

    @Override
    public List<BorrowRequest> getBorrowRequestForUserId(String userId) throws ServiceException {
        return null;
    }

    @Override
    public Boolean createBorrowRequest(BorrowRequest borrowRequest) throws ServiceException {
        Boolean flag = borrowRequestRepository.createBorrowRequest(borrowRequest,borrowRequest.getBorrower().getUserId(),borrowRequest.getBorrowStatus().getStatusCode());

        String requestId = borrowRequest.getBorrowRequestId();
        System.out.println(requestId);
        List<String> communityId = borrowRequest.getCommunityIds();

        borrowRequestCommunityMapRepository.createBorrowRequestCommunityMapping(requestId,communityId);
        return flag;
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
