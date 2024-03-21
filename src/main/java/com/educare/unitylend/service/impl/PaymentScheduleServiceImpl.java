package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.BorrowRequestRepository;
import com.educare.unitylend.dao.EMIRepository;
import com.educare.unitylend.dao.TransactionRepository;
import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.EMI;
import com.educare.unitylend.model.Status;
import com.educare.unitylend.service.EMIService;
import com.educare.unitylend.service.PaymentScheduleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class PaymentScheduleServiceImpl implements PaymentScheduleService {

    private EMIRepository emiRepository;
    private TransactionRepository transactionRepository;
    private EMIService emiService;
    private BorrowRequestRepository borrowRequestRepository;

    /**
     * Retrieves the scheduled EMI (Equated Monthly Installment) details for a given borrow request.
     *
     * @param borrowRequestId The ID of the borrow request for which to retrieve scheduled EMI details.
     * @return A list of scheduled EMI objects associated with the borrow request.
     * @throws ServiceException if an error occurs during the retrieval process.
     * @throws IllegalArgumentException if the borrow request ID is null or empty.
     */
    @Override
    public List<EMI> getScheduledEMIDetailsForBorrowRequest(String borrowRequestId) throws ServiceException {
        try {
            if (borrowRequestId == null || borrowRequestId.isEmpty()) {
                throw new IllegalArgumentException("Borrow request ID is null or empty");
            }
            updateOverdueEMIsToDefaultedStatus(borrowRequestId);
            List<EMI> scheduledEMIs = emiRepository.getScheduledEMIDetailsForBorrowRequest(borrowRequestId);
            return scheduledEMIs;
        } catch (Exception e) {
            log.error("Error encountered in fetching the scheduled EMI details using Borrow request Id ",e);
            throw new ServiceException("Error encountered in fetching the scheduled EMI details using Borrow request Id", e);
        }
    }


    /**
     * Retrieves the scheduled EMI (Equated Monthly Installment) details for a given lender and borrow request.
     *
     * @param lenderId The ID of the lender for which to retrieve scheduled EMI details.
     * @param borrowRequestId The ID of the borrow request for which to retrieve scheduled EMI details.
     * @return A list of scheduled EMI objects associated with the lender and borrow request.
     * @throws ServiceException if an error occurs during the retrieval process.
     * @throws IllegalArgumentException if the lender ID or borrow request ID is null or empty.
     */
    @Override
    public List<EMI> getScheduledEMIDetailsForLender(String lenderId, String borrowRequestId) throws ServiceException {
        try {
            if (lenderId == null || lenderId.isEmpty() || borrowRequestId == null || borrowRequestId.isEmpty()) {
                throw new IllegalArgumentException("Lender ID or Borrow request ID is null or empty");
            }

            updateOverdueEMIsToDefaultedStatus(borrowRequestId);

            List<EMI> scheduledEMIs = emiRepository.getScheduledEMIDetailsForBorrowRequest(borrowRequestId);

            for (EMI scheduledEMI : scheduledEMIs) {
                BigDecimal lenderEMI = emiService.calculateLenderEMIAmount(borrowRequestId, lenderId);
                scheduledEMI.setEmiAmount(lenderEMI);
            }
            return scheduledEMIs;
        } catch (Exception e) {
            log.error("Error encountered in fetching the scheduled EMI details using Lender Id ",e);
            throw new ServiceException("Error encountered in fetching the scheduled EMI details using Lender Id", e);
        }
    }

    /**
     * Retrieves the paid EMI (Equated Monthly Installment) details for a given borrow request.
     *
     * @param borrowRequestId The ID of the borrow request for which to retrieve paid EMI details.
     * @return A list of paid EMI objects associated with the borrow request.
     * @throws ServiceException if an error occurs during the retrieval process.
     * @throws IllegalArgumentException if the borrow request ID is null or empty.
     */
    @Override
    public List<EMI> getPaidEMIDetailsForBorrowRequest(String borrowRequestId) throws ServiceException {
        try {
            if (borrowRequestId == null || borrowRequestId.isEmpty()) {
                throw new IllegalArgumentException("Borrow request ID is null or empty");
            }

            updateOverdueEMIsToDefaultedStatus(borrowRequestId);
            List<EMI> scheduledEMIs = emiRepository.getPaidEMIDetailsForBorrowRequest(borrowRequestId);

            return scheduledEMIs;
        } catch (Exception e) {
            log.error("Error encountered in fetching the actual EMI details for Borrow request Id ",e);
            throw new ServiceException("Error encountered in fetching the actual EMI details Borrow request Id", e);
        }
    }


    /**
     * Retrieves the paid EMI (Equated Monthly Installment) details for a given lender and borrow request.
     *
     * @param lenderId The ID of the lender for which to retrieve paid EMI details.
     * @param borrowRequestId The ID of the borrow request for which to retrieve paid EMI details.
     * @return A list of paid EMI objects associated with the lender and borrow request.
     * @throws ServiceException if an error occurs during the retrieval process.
     * @throws IllegalArgumentException if the lender ID or borrow request ID is null or empty.
     */
    @Override
    public List<EMI> getPaidEMIDetailsForLender(String lenderId, String borrowRequestId) throws ServiceException {
        try {
            if (lenderId == null || lenderId.isEmpty() || borrowRequestId == null || borrowRequestId.isEmpty()) {
                throw new IllegalArgumentException("Lender ID or Borrow request ID is null or empty");
            }
            updateOverdueEMIsToDefaultedStatus(borrowRequestId);
            List<EMI> scheduledEMIs = emiRepository.getPaidEMIDetailsForBorrowRequest(borrowRequestId);
            BigDecimal lentAmount = transactionRepository.getLentAmountByLenderAndRequestId(lenderId, borrowRequestId);
            for (EMI scheduledEMI : scheduledEMIs) {
                BigDecimal lenderEMI = emiService.calculateLenderEMIAmount(borrowRequestId, lenderId);
                scheduledEMI.setEmiAmount(lenderEMI);
            }
            return scheduledEMIs;
        } catch (Exception e) {
            log.error("Error encountered in fetching the actual EMI details using Lender Id ",e);
            throw new ServiceException("Error encountered in fetching the actual EMI details using Lender Id", e);
        }
    }


    /**
     * Retrieves the defaulted EMI (Equated Monthly Installment) details for a given borrow request.
     *
     * @param borrowRequestId The ID of the borrow request for which to retrieve defaulted EMI details.
     * @return A list of defaulted EMI objects associated with the borrow request.
     * @throws ServiceException if an error occurs during the retrieval process.
     * @throws IllegalArgumentException if the borrow request ID is null or empty.
     */
    @Override
    public List<EMI> getDefaultEMIDetailsForBorrowRequest(String borrowRequestId) throws ServiceException {
        try {
            if (borrowRequestId == null || borrowRequestId.isEmpty()) {
                throw new IllegalArgumentException("Borrow request ID is null or empty");
            }
            updateOverdueEMIsToDefaultedStatus(borrowRequestId);
            List<EMI> defaultEMIs = emiRepository.getDefaultEMIs(borrowRequestId);

            return defaultEMIs;
        } catch (Exception e) {
            log.error("Error encountered in fetching Default EMIs ",e);
            throw new ServiceException("Error encountered in fetching Default EMIs", e);
        }
    }


    /**
     * Retrieves the defaulted EMI (Equated Monthly Installment) details for a given lender and borrow request.
     *
     * @param lenderId The ID of the lender for which to retrieve defaulted EMI details.
     * @param borrowRequestId The ID of the borrow request for which to retrieve defaulted EMI details.
     * @return A list of defaulted EMI objects associated with the lender and borrow request.
     * @throws ServiceException if an error occurs during the retrieval process.
     * @throws IllegalArgumentException if the borrow request ID is null or empty.
     */
    @Override
    public List<EMI> getDefaultEMIDetailsForLender(String lenderId, String borrowRequestId) throws ServiceException {
        try {
            if (borrowRequestId == null || borrowRequestId.isEmpty()) {
                throw new IllegalArgumentException("Borrow request ID is null or empty");
            }
            updateOverdueEMIsToDefaultedStatus(borrowRequestId);
            List<EMI> defaultEMIs = emiRepository.getDefaultEMIs(borrowRequestId);

            for (EMI defaultEMI : defaultEMIs) {
                BigDecimal lenderEMI = emiService.calculateLenderEMIAmount(borrowRequestId, lenderId);
                defaultEMI.setEmiAmount(lenderEMI);
            }

            return defaultEMIs;
        } catch (Exception e) {
            log.error("Error encountered in fetching Default EMIs ",e);
            throw new ServiceException("Error encountered in fetching Default EMIs", e);
        }
    }


    /**
     * Updates overdue EMIs (Equated Monthly Installments) to defaulted status for a given borrow request.
     *
     * @param borrowRequestId The ID of the borrow request for which to update overdue EMIs.
     * @throws ServiceException if an error occurs during the update process.
     * @throws IllegalArgumentException if the borrow request ID is null or empty.
     */
    @Override
    public void updateOverdueEMIsToDefaultedStatus(String borrowRequestId) throws ServiceException {
        try {
            if (borrowRequestId == null || borrowRequestId.isEmpty()) {
                throw new IllegalArgumentException("Borrow request ID is null or empty");
            }

            BorrowRequest borrowRequest=borrowRequestRepository.getBorrowRequestByRequestId(borrowRequestId);
            BigDecimal defaultFine=borrowRequest.getDefaultFine();

            emiRepository.updateOverdueEMIsToDefaultedStatus(borrowRequestId,defaultFine);
        } catch (Exception e) {
            log.error("Error encountered in updating over due EMIS to Defaulted status ",e);
            throw new ServiceException("Error encountered in updating over due EMIS to Defaulted status", e);
        }
    }


    /**
     * Retrieves the next scheduled EMI (Equated Monthly Installment) for a given borrow request.
     *
     * @param borrowRequestId The ID of the borrow request for which to retrieve the next scheduled EMI.
     * @return The next scheduled EMI object associated with the borrow request.
     * @throws ServiceException if an error occurs during the retrieval process.
     * @throws IllegalArgumentException if the borrow request ID is null or empty.
     */
    @Override
    public EMI getNextScheduledEMI(String borrowRequestId) throws ServiceException {
        try {
            if (borrowRequestId == null || borrowRequestId.isEmpty()) {
                throw new IllegalArgumentException("Borrow request ID is null or empty");
            }

            updateOverdueEMIsToDefaultedStatus(borrowRequestId);

            return emiRepository.getNextScheduledEMI(borrowRequestId);

        } catch (Exception e) {
            log.error("Error encountered in getting next scheduled EMI ",e);
            throw new ServiceException("Error encountered in getting next scheduled EMI", e);
        }
    }


    /**
     * Checks if an EMI (Equated Monthly Installment) is defaulted.
     *
     * @param emi The EMI object to check for default status.
     * @return true if the EMI is defaulted, false otherwise.
     * @throws ServiceException if an error occurs during the check process.
     * @throws IllegalArgumentException if the EMI object is null.
     */
    @Override
    public Boolean isDefaulted(EMI emi) throws ServiceException {
        try {
            if (emi == null) {
                throw new IllegalArgumentException("EMI is null");
            }
            String emiId = emi.getEmiId();
            Boolean isDefaulted = emiRepository.isDefaultedEMI(emiId);

            return isDefaulted;
        } catch (Exception e) {
            log.error("Error encountered while checking if EMI is defaulted ",e);
            throw new ServiceException("Error encountered while checking if EMI is defaulted", e);
        }
    }


    /**
     * Updates the status of an EMI (Equated Monthly Installment).
     *
     * @param nextScheduledEMI The next scheduled EMI for which to update the status.
     * @param status The status to set for the EMI.
     * @throws ServiceException if an error occurs during the update process.
     * @throws IllegalArgumentException if the nextScheduledEMI or status is null.
     */
    @Override
    public void updateEMIStatus(EMI nextScheduledEMI, Status status) throws ServiceException{
        try {
            if (nextScheduledEMI == null || status==null) {
                throw new IllegalArgumentException("nextScheduledEMI or status is null");
            }

            String emiId=nextScheduledEMI.getEmiId();
            Integer statusCode=status.getStatusCode();

            emiRepository.updateEMIStatus(emiId,statusCode);
        }
        catch (Exception e){
            log.error("Error encountered in updating EMI status ",e);
            throw new ServiceException("Error encountered in updating EMI status",e);
        }
    }
}
