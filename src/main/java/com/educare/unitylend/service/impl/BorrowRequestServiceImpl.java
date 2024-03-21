package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.BorrowRequestCommunityMapRepository;
import com.educare.unitylend.dao.BorrowRequestRepository;
import com.educare.unitylend.dao.UserCommunityMapRepository;
import com.educare.unitylend.dao.StatusRepository;
import com.educare.unitylend.dao.UserRepository;
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

    @Override
    public List<BorrowRequest> getBorrowRequestForUserId(String userId) throws ServiceException {
        try {
            List<BorrowRequest> borrowRequestList = borrowRequestRepository.getBorrowRequestsByUserId(userId);
            for(BorrowRequest borrowRequest:borrowRequestList){
                User requiredUser = userRepository.getUserForUserId(userId);
                borrowRequest.setBorrower(requiredUser);
                Integer code=borrowRequestRepository.getStatusCodeForReqId(borrowRequest.getBorrowRequestId());
                Status status=statusRepository.getStatusByStatusCode(code);
                borrowRequest.setBorrowStatus(status);
                ObjectMapper objectMapper = new ObjectMapper();
                String communityDetailsJson = requiredUser.getCommunityDetailsJson();
                Map<String, String> communityDetails = objectMapper.readValue(communityDetailsJson, new TypeReference<Map<String, String>>() {
                });
                requiredUser.setCommunityDetails(communityDetails);
                borrowRequest.setCommunityIds(userCommunityMapRepository.getCommunityIdsWithUserId(userId));
            }
            return borrowRequestList;
        } catch (Exception e) {
            log.error("Error encountered during getting requests by user id");
            throw new ServiceException("Error encountered during getting requests by user id", e);
        }
    }


    public BigDecimal calculateMonthlyEMI(BigDecimal principal, BigDecimal monthlyInterestRate, BigDecimal timeInMonths) {

        BigDecimal numerator = principal.multiply(BigDecimal.ONE.add(monthlyInterestRate.multiply(timeInMonths)));
        BigDecimal monthlyEMI = numerator.divide(timeInMonths, 2, BigDecimal.ROUND_HALF_UP);

        return monthlyEMI;
    }

    public boolean isMonthlyEMIAffordable(Integer income, BigDecimal monthlyEMI) {
        BigDecimal halfIncome = BigDecimal.valueOf(income / 2);
        return halfIncome.compareTo(monthlyEMI) >= 0;
    }

    public BigDecimal daysToMonths(Integer days) {
        return BigDecimal.valueOf(days).divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_UP);
    }


    @Override
    public Boolean updateEMIDefaults() throws ServiceException {
        return false;
    }

    @Override
    public Boolean updateBorrowRequestStatus(BorrowRequest borrowRequest, Status status) throws ServiceException {
        try {
            Integer statusCode=status.getStatusCode();
            String requestId=borrowRequest.getBorrowRequestId();
            borrowRequestRepository.updateStatusOfBorrowRequest(statusCode, requestId);
            borrowRequest.setBorrowStatus(status);
            return true;
        } catch (Exception e) {
            log.error("Error while updating borrow request");
            throw new ServiceException("Error while updating borrow request", e);
        }
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
