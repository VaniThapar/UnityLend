package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.CommunityRepository;
import com.educare.unitylend.model.Community;
import com.educare.unitylend.service.CommunityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class CommunityServiceImpl implements CommunityService {

    @Override
    public boolean createCommunity() throws ServiceException {
        return false;
    }

    @Override
    public boolean deleteCommunity(String communityId) throws ServiceException {
        return false;
    }

    @Override
    public List<Community> getAllCommunities() throws ServiceException {
        return null;
    }
}
