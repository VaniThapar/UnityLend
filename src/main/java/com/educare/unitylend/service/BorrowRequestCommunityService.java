package com.educare.unitylend.service;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.Community;

import java.util.List;

public interface BorrowRequestCommunityService {

    List<Community> getCommunitiesByRequestId(String requestId) throws ServiceException;

    List<BorrowRequest> getRequestsByCommunityId(String communityId) throws ServiceException;
}
