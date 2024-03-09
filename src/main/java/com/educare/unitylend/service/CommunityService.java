package com.educare.unitylend.service;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.Community;

import java.util.List;

public interface CommunityService {
    /**
     * @return the list of all available {@link Community}
     * @throws ServiceException : Throws if any exception occurs
     */
    List<Community> getCommunities() throws ServiceException;

    /**
     * @return the Communtiy name of available {@link Community} based on common tag
     * @throws ServiceException : Throws if any exception occurs
     */
    String getCommunityName(String communityTag) throws ServiceException;

    /**
     * create the community {@link Community}
     *
     * @throws ServiceException : Throws if any exception occurs
     */
    void createCommunity(Community community) throws ServiceException;

}
