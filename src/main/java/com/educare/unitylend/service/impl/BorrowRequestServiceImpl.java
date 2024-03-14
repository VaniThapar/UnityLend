package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.Status;
import com.educare.unitylend.service.BorrowRequestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class BorrowRequestServiceImpl implements BorrowRequestService {
    @Override
    public List<BorrowRequest> getAllBorrowRequests() throws ServiceException {
        return null;
    }

    @Override
    public List<BorrowRequest> getBorrowRequestForUserId(String userId) throws ServiceException {
        return null;
    }

    @Override
    public boolean createBorrowRequest(BorrowRequest borrowRequest) throws ServiceException {
        return false;
    }

    @Override
    public List<BorrowRequest> getBorrowRequestForCommunity(List <String> communityId) throws ServiceException {
        return null;
    }


    @Override
    public boolean updateEMIDefaults() throws ServiceException {
        return false;
    }

    @Override
    public boolean updateBorrowRequestStatus(Status status) throws ServiceException {
        return false;
    }

//    private UserRepository userRepository;
//    private BorrowRequestRepository borrowRequestRepository;
//    public List<String> getRequestedCommunitiesByUserId(String userId) throws ServiceException{
//        List<String> communities=borrowRequestRepository.getBorrowRequestByUserId(userId);
//        System.out.println(communities);
//        return communities;
//    }
//
//    public void createBorrowRequest(BorrowRequest borrowRequest) {
//        borrowRequestRepository.createBorrowRequest(borrowRequest);
//    }
//    public boolean raiseBorrowRequestByUserId(String userId, BorrowRequest borrowRequest) throws ServiceException {
//        try {
//            // Assuming returnPeriod is in days
//            BigDecimal monthlyInterestRate = new BigDecimal("50.0").divide(new BigDecimal(12 * 100), 10, RoundingMode.HALF_UP);
//            BigDecimal loanTenureMonths = new BigDecimal(borrowRequest.getReturnPeriod()).multiply(new BigDecimal("30.44"));
//            double pow = Math.pow(1 + monthlyInterestRate.doubleValue(), loanTenureMonths.doubleValue());
//            BigDecimal emiNumerator = borrowRequest.getTargetAmount()
//                    .multiply(new BigDecimal(pow)).multiply(new BigDecimal(50));
//
//
//            BigDecimal emiDenominator = new BigDecimal(pow - 1);
//
//            BigDecimal emi = emiNumerator.divide(emiDenominator, 2, RoundingMode.HALF_UP);
//           // BigDecimal emi =borrowRequest.getTargetAmount().divide(new BigDecimal(borrowRequest.getReturnPeriod()));
//            if (emi.compareTo(new BigDecimal(userRepository.getIncome(userId)).divide(new BigDecimal(2), 2, RoundingMode.HALF_UP)) <0) {
//                return false;
//            }
//            return true;
//
//        } catch (Exception e) {
//            throw new ServiceException("Error in calculating EMI", e);
//        }
//    }



}

