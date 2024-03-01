package com.educare.unitylend.service;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.User;

import java.util.List;

public interface UserCommunityService {
    List<String> getCommunitiesByUserId(String userId) throws ServiceException;
}
