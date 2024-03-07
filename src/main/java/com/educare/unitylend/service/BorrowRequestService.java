package com.educare.unitylend.service;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.BorrowRequest;

import java.util.List;

public interface BorrowRequestService {

    List<String> getRequestedCommunitiesByUserId(String userId) throws ServiceException;

    boolean raiseBorrowRequestForUserid(String userId, BorrowRequest borrowRequest) throws ServiceException;
}