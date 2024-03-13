package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.UserCommunityRepository;
import com.educare.unitylend.service.UserCommunityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class UserCommunityServiceImpl implements UserCommunityService {
    private UserCommunityRepository userCommunityRepository;
    public List<String> getCommunitiesByUserId(String userId) throws ServiceException{
        try {
            List<String> communityNames = userCommunityRepository.findCommunityNamesByUserId(userId);
            //  log.info("communityNames: ", communityNames);
            return communityNames;
        } catch (Exception e) {
            log.error("Error encountered during community fetching operation for user with ID: {}", userId, e);
            throw new ServiceException("Error encountered during community fetch operation", e);
        }
    }
}
