package com.educare.unitylend.service.implOld;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.BorrowRequestCommunityMapRepository;
import com.educare.unitylend.dao.BorrowRequestRepository;
import com.educare.unitylend.dao.UserRepository;
import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.Community;
import com.educare.unitylend.model.User;
import com.educare.unitylend.service.BorrowRequestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class BorrowRequestServiceImpl implements BorrowRequestService {
    private UserRepository userRepository;
    private BorrowRequestRepository borrowRequestRepository;
    private BorrowRequestCommunityMapRepository borrowRequestCommunityRepository;

    @Override
    public List<Community> getCommunitiesForWhichBorrowRequestRaisedByUser(String userId) throws ServiceException {

        try {
            List<BorrowRequest>borrowRequestListByUser=borrowRequestRepository.getBorrowRequestsByUserId(userId);
            List<Community>communities=new ArrayList<>();

            for(BorrowRequest request:borrowRequestListByUser){
                List<Community>communityList=borrowRequestCommunityRepository.getCommunitiesByRequestId(request.getRequestId());
                for(Community community:communityList){
                    if(!communities.contains(community)){
                        communities.add(community);
                    }
                }
            }
            return communities;
        } catch (Exception e) {
            log.error("Error while getting communities where given user has raised request");
            throw new ServiceException("Error while getting communities where given user has raised request", e);
        }
    }

    @Override
    public void createBorrowRequest(BorrowRequest borrowRequest) throws ServiceException {

        try {
            borrowRequestRepository.createBorrowRequest(borrowRequest);

            List<String> communityIds = borrowRequest.getCommunityIds();
            String requestId = borrowRequest.getRequestId();

            mapBorrowRequestToCommunity(requestId, communityIds);
        } catch (Exception e) {
            log.error("Error while creating borrow request");
            throw new ServiceException("Error while creating borrow request", e);
        }

    }

    @Override
    public void mapBorrowRequestToCommunity(String requestId, List<String> CommunityIds) {
        for (String communityId : CommunityIds) {
            borrowRequestCommunityRepository.createBorrowRequestCommunityMapping(requestId, communityId);
        }
    }

    @Override
    public void mapBorrowRequestToCommunity(String requestId, String communityId) {
        borrowRequestCommunityRepository.createBorrowRequestCommunityMapping(requestId, communityId);
    }

    @Override
    public boolean validateBorrowRequest(BorrowRequest borrowRequest) throws ServiceException {

        try {
            String borrowerId=borrowRequest.getBorrower().getUserId();
            User borrower = userRepository.getUserForUserId(borrowerId);
            Integer returnPeriod = borrowRequest.getReturnPeriod();
            BigDecimal returnPeriodInMonths = daysToMonths(returnPeriod);
            BigDecimal requestedAmount = borrowRequest.getRequestedAmount();
            BigDecimal monthlyInterestRate = borrowRequest.getMonthlyInterestRate();

            BigDecimal monthlyEMI = calculateMonthlyEMI(requestedAmount, monthlyInterestRate, returnPeriodInMonths);
            Integer income = borrower.getIncome();

            return isMonthlyEMIAffordable(income, monthlyEMI);
        } catch (Exception e) {
            log.error("Error while validating the borrow request");
            throw new ServiceException("Error while validating the borrow request", e);
        }

    }

    @Override
    public List<BorrowRequest> getAllBorrowRequests() throws ServiceException{
        try{
            return borrowRequestRepository.getAllBorrowRequests();
        }
        catch (Exception e){
            log.error("Error encountered while getting all borrow requests");
            throw new ServiceException("Error encountered while getting all borrow requests",e);
        }
    }

    @Override
    public List<BorrowRequest> getBorrowRequestsByUserId(String userId) throws ServiceException{
        try {
            List<BorrowRequest> borrowRequestList = borrowRequestRepository.getBorrowRequestsByUserId(userId);
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

}