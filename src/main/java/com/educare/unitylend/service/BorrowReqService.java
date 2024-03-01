package com.educare.unitylend.service;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.User;

import java.util.List;

public interface BorrowReqService {
    List<BorrowRequest> getBorrowRequests(String userId) throws ServiceException;
    List<BorrowRequest> getRequestsForUserAndCommunity(String userId, String communityId) throws ServiceException;
    List<BorrowRequest> getRequestsByCommunityAndAmount(String userId, double amount) throws ServiceException;
}
