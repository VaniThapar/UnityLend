package com.educare.unitylend.service;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.Community;

import java.util.List;

public interface BorrowRequestCommunityMapService {
    public List<BorrowRequest> getRequestsByCommunityId(String communityId) throws ServiceException;
    public List<Community> getCommunitiesByRequestId(String requestId) throws ServiceException;
}
