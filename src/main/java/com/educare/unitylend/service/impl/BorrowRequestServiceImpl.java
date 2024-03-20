package com.educare.unitylend.service.impl;

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

    @Override
    public boolean validateBorrowRequest(BorrowRequest borrowRequest){
        Integer tenure = borrowRequest.getReturnPeriodDays();
        BigDecimal monthlyIncome = borrowRequest.getBorrower().getIncome();
        BigDecimal borrowedAmount = borrowRequest.getRequestedAmount();
        BigDecimal monthlyEMI = borrowedAmount.divide(BigDecimal.valueOf(tenure));
        BigDecimal limitMoney = monthlyIncome.divide(BigDecimal.valueOf(2));
        boolean isWithinLimit = monthlyEMI.compareTo(limitMoney) <= 0;
        return isWithinLimit;
    }

    @Override
    public boolean isUserPartOfCommunity(BorrowRequest borrowRequest){
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
    @Override
    public boolean isBorrowRequestPending(BorrowRequest borrowRequest){
        String borrowerId = borrowRequest.getBorrower().getUserId();
        if(borrowRequestRepository.isRequestPending(borrowerId)) return true;
        return false;
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
