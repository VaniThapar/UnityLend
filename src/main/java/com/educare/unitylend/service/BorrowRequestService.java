package com.educare.unitylend.service;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.Community;

import java.util.List;

public interface BorrowRequestService {

    List<Community> getCommunitiesForWhichBorrowRequestRaisedByUser(String userId) throws ServiceException;

    boolean validateBorrowRequest(BorrowRequest borrowRequest) throws ServiceException;

    void createBorrowRequest(BorrowRequest borrowRequest) throws ServiceException;

    void mapBorrowRequestToCommunity(String requestId, List<String>CommunityIds);

    void mapBorrowRequestToCommunity(String requestId, String communityId);

    List<BorrowRequest> getAllBorrowRequests() throws ServiceException;

    List<BorrowRequest> getBorrowRequestsByUserId(String userId) throws ServiceException;
}