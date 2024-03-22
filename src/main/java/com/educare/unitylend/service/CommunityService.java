package com.educare.unitylend.service;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.Community;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Interface for managing communities within the interface
 */

public interface CommunityService {

    List<Community> getAllCommunities() throws ServiceException;
}
