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

    /**
     * Retrieves a list of communities associated with a specific user.
     *
     * @param userId The ID of the user.
     * @return A list of communities associated with the user.
     * @throws ServiceException If an error occurs while fetching the communities.
     * @throws IllegalArgumentException If the provided user ID is null or empty.
     */
    public List<Community> getCommunitiesByUserId(String userId) throws ServiceException {
        try {
            if (userId == null || userId.isEmpty()) {
                throw new IllegalArgumentException("User id is null or empty");
            }
            List<Community> communityList = userCommunityMapRepository.findCommunitiesByUserId(userId);
            return communityList;
        } catch (Exception e) {
            log.error("Error encountered during community fetching operation for user with ID: {}", userId, e);
            throw new ServiceException("Error encountered during community fetch operation", e);
        }
    }
}
