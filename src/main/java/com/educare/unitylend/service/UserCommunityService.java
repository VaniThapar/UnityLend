package com.educare.unitylend.service;

import com.educare.unitylend.Exception.ServiceException;

import java.util.List;

public interface UserCommunityService {
    /**
     * @return the list of all available user and community mappings
     * @throws ServiceException : Throws if any exception occurs
     */
    List<String> getCommunitiesByUserId(String userId) throws ServiceException;
}
