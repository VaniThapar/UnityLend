package com.educare.unitylend.service;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.EMI;
import com.educare.unitylend.model.Status;

import java.util.List;

/**
 * Interface for managing EMI Schedules within the system
 */

public interface PaymentScheduleService {
    public List<EMI> getScheduledEMIDetailsForBorrowRequest(String borrowRequestId) throws ServiceException;

    public List<EMI> getScheduledEMIDetailsForLender(String lenderId, String borrowRequestId) throws ServiceException;

    public List<EMI> getPaidEMIDetailsForBorrowRequest(String borrowRequestId) throws ServiceException;

    public List<EMI> getPaidEMIDetailsForLender(String lenderId, String borrowRequestId) throws ServiceException;

    public List<EMI> getDefaultEMIDetailsForBorrowRequest(String borrowRequestId) throws ServiceException;

    public List<EMI>  getDefaultEMIDetailsForLender(String lenderId, String borrowRequestId) throws ServiceException;

    public void updateOverdueEMIsToDefaultedStatus(String borrowRequestId) throws ServiceException;

    public EMI getNextScheduledEMI(String borrowRequestId) throws ServiceException;

    public Boolean isDefaulted(EMI emi) throws ServiceException;

    public void updateEMIStatus(EMI nextScheduledEMI, Status status) throws ServiceException;
}
