package com.educare.unitylend.service;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.BorrowRequest;

import java.util.List;

public interface BorrowRequestService {

    List<String> getRequestedCommunitiesByUserId(String userId) throws ServiceException;

    boolean raiseBorrowRequestByUserId(String userId, BorrowRequest borrowRequest) throws ServiceException;

    void createBorrowRequest(BorrowRequest borrowRequest) throws ServiceException;
}