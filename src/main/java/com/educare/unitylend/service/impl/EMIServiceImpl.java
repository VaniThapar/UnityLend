package com.educare.unitylend.service.impl;
import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.BorrowRequestRepository;
import com.educare.unitylend.dao.EMIRepository;
import com.educare.unitylend.dao.StatusRepository;
import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.service.BorrowRequestService;
import com.educare.unitylend.service.EMIService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@AllArgsConstructor
@Service
public class EMIServiceImpl implements EMIService {
    private BorrowRequestRepository borrowRequestRepository;
    private StatusRepository statusRepository;
    private EMIRepository emiRepository;

    /**
     * Calculates the monthly EMI (Equated Monthly Installment) amount for a given borrow request.
     *
     * @param borrowRequestId The ID of the borrow request for which the EMI is calculated.
     * @return The calculated monthly EMI amount.
     * @throws ServiceException if an error occurs during the calculation process.
     * @throws IllegalArgumentException if the borrow request ID is null or empty.
     */
    @Override
    public BigDecimal calculateBorrowEMIAmount(String borrowRequestId) throws ServiceException{
        try {
            if (borrowRequestId == null || borrowRequestId.isEmpty()) {
                throw new IllegalArgumentException("Borrow request ID is null or empty");
            }

            BorrowRequest borrowRequest = borrowRequestRepository.getBorrowRequestByRequestId(borrowRequestId);

            Integer returnPeriod = borrowRequest.getReturnPeriodMonths();
            BigDecimal bigDecimalReturnPeriod = new BigDecimal(returnPeriod);
            BigDecimal principalAmount = borrowRequest.getRequestedAmount();
            BigDecimal interestRate = borrowRequest.getMonthlyInterestRate();

            BigDecimal simpleInterest = (principalAmount.multiply(bigDecimalReturnPeriod).multiply(interestRate)).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
            BigDecimal finalMonthlyEMI = (principalAmount.add(simpleInterest)).divide(bigDecimalReturnPeriod, 2, BigDecimal.ROUND_HALF_UP);

            return finalMonthlyEMI;
        }
        catch (Exception e) {
            log.error("Error encountered in calculating borrower EMI ",e);
            throw new ServiceException("Error encountered in calculating borrower EMI",e);
        }
    }

    /**
     * Adds EMI (Equated Monthly Installment) details for a given borrow request.
     *
     * @param returnPeriod The return period in months for the borrow request.
     * @param borrowRequestId The ID of the borrow request for which EMI details are added.
     * @return {@code true} if EMI details are successfully added; {@code false} otherwise.
     * @throws ServiceException if an error occurs during the process of adding EMI details.
     * @throws IllegalArgumentException if the return period or borrow request ID is null or empty.
     */
    @Override
    public Boolean addEMIDetails(Integer returnPeriod, String borrowRequestId) throws ServiceException{
        try {
            if (returnPeriod == null || borrowRequestId == null || borrowRequestId.isEmpty()) {
                throw new IllegalArgumentException("Return period or Borrow request ID is null or empty");
            }

            BigDecimal borrowerMonthlyEMI=calculateBorrowEMIAmount(borrowRequestId);
            Integer statusCode=statusRepository.getStatusCodeByStatusName("Future");

            for (Integer i = 1; i <= returnPeriod; i++) {
                emiRepository.addEMIDetails(borrowerMonthlyEMI, i, borrowRequestId, statusCode);
            }
            return true;
        }
        catch (Exception e){
            log.error("Error encountered in adding EMI details to the EMI table ",e);
            throw new ServiceException("Error encountered in adding EMI details to the EMI table",e);
        }
    }


    /**
     * Calculates the EMI (Equated Monthly Installment) amount to be received by a lender for a given borrow request.
     *
     * @param borrowRequestId The ID of the borrow request for which the EMI amount is calculated.
     * @param lenderId The ID of the lender.
     * @return The calculated EMI amount to be received by the lender.
     * @throws ServiceException if an error occurs during the calculation process.
     * @throws IllegalArgumentException if the borrow request ID or lender ID is null or empty,
     *                                  or if no borrow request is found for the given ID,
     *                                  or if no lent amount is found for the borrow request and lender combination.
     */
    @Override
    public BigDecimal calculateLenderEMIAmount(String borrowRequestId, String lenderId) throws ServiceException{
        try {
            if (borrowRequestId == null || borrowRequestId.isEmpty() || lenderId == null || lenderId.isEmpty()) {
                throw new IllegalArgumentException("Borrow request ID or lender ID is null or empty");
            }

            BorrowRequest borrowRequest = borrowRequestRepository.getBorrowRequestByRequestId(borrowRequestId);
            if (borrowRequest == null) {
                throw new IllegalArgumentException("No borrow request found for ID: " + borrowRequestId);
            }

            BigDecimal finalMonthlyEMI = calculateBorrowEMIAmount(borrowRequestId);
            BigDecimal lentAmount = emiRepository.getLentAmountByBorrowRequestIdAndLenderId(borrowRequestId, lenderId);
            BigDecimal requestedAmount = borrowRequest.getRequestedAmount();

            if (lentAmount == null) {
                throw new IllegalArgumentException("No lent amount found for borrow request ID: " + borrowRequestId + " and lender ID: " + lenderId);
            }
            BigDecimal borrowerEmi=calculateBorrowEMIAmount(borrowRequestId);
            BigDecimal amountToBeReceivedByLender = (lentAmount.divide(requestedAmount)).multiply(borrowerEmi);

            return amountToBeReceivedByLender;
        } catch (Exception e) {
            log.error("Error encountered in adding EMI details to the EMI table ",e);
            throw new ServiceException("Error encountered in adding EMI details to the EMI table ",e);
        }
    }

}