package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.UserCommunityMapRepository;
import com.educare.unitylend.model.Community;
import com.educare.unitylend.service.UserCommunityMapService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class UserCommunityMapServiceImpl implements UserCommunityMapService {
    private UserCommunityMapRepository userCommunityMapRepository;
    public List<Community> getCommunitiesByUserId(String userId) throws ServiceException {
        try {
            List<Community> communityList = userCommunityMapRepository.findCommunitiesByUserId(userId);
              log.info("communityNames: "+ communityList);
            return communityList;
        } catch (Exception e) {
            log.error("Error encountered during community fetching operation for user with ID: {}", userId, e);
            throw new ServiceException("Error encountered during community fetch operation", e);
        }
    }
}
