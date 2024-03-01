package com.educare.unitylend.service;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.Community;
import com.educare.unitylend.model.User;

import java.util.List;

public interface CommunityService {
    List<Community> getCommunities() throws ServiceException;
    String getCommunityName(String communityTag) throws ServiceException;
    void createCommunity(Community community) throws ServiceException;

}
