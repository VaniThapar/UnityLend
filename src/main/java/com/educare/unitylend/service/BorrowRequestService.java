package com.educare.unitylend.service;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.Status;

import java.util.List;

public interface BorrowRequestService {

    //    List<String> getRequestedCommunitiesByUserId(String userId) throws ServiceException;
    List<BorrowRequest> getAllBorrowRequests() throws ServiceException;
    List<BorrowRequest> getBorrowRequestForUserId(String userId) throws ServiceException;
    boolean createBorrowRequest(BorrowRequest borrowRequest) throws ServiceException;

    List<BorrowRequest> getBorrowRequestForCommunity(List <String> communityId ) throws ServiceException;


    boolean updateEMIDefaults() throws ServiceException;

    boolean updateBorrowRequestStatus(Status status) throws ServiceException;

    // boolean raiseBorrowRequestByUserId(String userId, BorrowRequest borrowRequest) throws ServiceException;

}