package com.educare.unitylend.service;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.Community;

import java.util.List;

/**
 * Interface for managing the User community mappings within the system
 */
public interface UserCommunityMapService {
    List<Community> getCommunitiesByUserId(String userId) throws ServiceException;
}
