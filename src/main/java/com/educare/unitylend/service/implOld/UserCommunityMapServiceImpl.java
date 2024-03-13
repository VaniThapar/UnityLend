package com.educare.unitylend.service.implOld;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.UserCommunityMapRepository;
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

    public List<String> getCommunitiesByUserId(String userId) throws ServiceException{
        try {
            List<String> communityNames = userCommunityMapRepository.findCommunityNamesByUserId(userId);
            return communityNames;
        } catch (Exception e) {
            log.error("Error encountered while fetching community by user id: {}", userId, e);
            throw new ServiceException("Error encountered while fetching community by user id", e);
        }
    }
}
