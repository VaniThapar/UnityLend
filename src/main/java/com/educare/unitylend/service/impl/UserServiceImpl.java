package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.CommunityRepository;
import com.educare.unitylend.dao.UserCommunityRepository;
import com.educare.unitylend.dao.UserRepository;
import com.educare.unitylend.model.User;
import com.educare.unitylend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private CommunityRepository communityRepository;
    private UserCommunityRepository userCommunityRepository;

    @Override
    public List<User> getUsers() throws ServiceException {
        try {
            List<User> userList = userRepository.getAllUsers();
            log.info("userList ", userList);
            return userList;
        } catch (Exception e) {
            log.error("Error encountered during user fetching operation");
            throw new ServiceException("Error encountered during user fetch operation", e);
        }
    }


    @Override
    public User getUserByUserId(String userId) throws ServiceException {
        try {
            User user = userRepository.getUserForUserId(userId);
            return user;
        } catch (Exception e) {
            log.error("Error encountered during user fetch operation");
            throw new ServiceException("Error encountered during user fetch operation", e);
        }
    }


    public void createUser(User newUser) throws ServiceException {

        try {
            // Add any validation logic if needed before saving to the database

            List<String> commonTags = new ArrayList<>();
            if (newUser.getOfficeName() != null) commonTags.add(newUser.getOfficeName());

            if (newUser.getCollegeuniversity() != null) commonTags.add(newUser.getCollegeuniversity());

            if (newUser.getLocality() != null) commonTags.add(newUser.getLocality());

            for (String commonTag : commonTags) {
                if (!communityRepository.existsByCommontag(commonTag)) {

                    log.info("Creating community::", commonTag);
                    communityRepository.createCommunityUsingStrings(commonTag, commonTag);
                }
            }

            userRepository.createUser(newUser);
            newUser.setUserId(userRepository.settingID(newUser.getEmail()));
            newUser.setBorrowingLimit(newUser.getIncome() / 2);
            mapUserToCommunity(newUser.getUserId(), commonTags);
        } catch (Exception e) {
            log.error("Error encountered during user creation operation");
            throw new ServiceException("Error encountered during user creation operation", e);
        }
    }

    private void mapUserToCommunity(String userId, List<String> commonTags) {

        for (String commonTag : commonTags) {
            userCommunityRepository.createUserCommunityMapping(userId, communityRepository.getCommunityIdByName(commonTag));
        }
    }

    private void mapUserToCommunity(String userId, String commonTag) {
        userCommunityRepository.createUserCommunityMapping(userId, communityRepository.getCommunityIdByName(commonTag));
    }


    public void updateUser(User user, String userId) throws ServiceException {
        List<String> updatedCommunities;
        String[] communityName = new String[3];
        try {
            userCommunityRepository.deletePrevData(userId);
            userRepository.updateUser(user);
            updatedCommunities = userRepository.findCommunitiesByUserId(userId);
            for (String community : updatedCommunities) {
                communityName = community.split(", ");
            }
            for (int i = 0; i < 3; i++) {
                if (!communityRepository.existsByCommontag(communityName[i])) {
                    communityRepository.createCommunityUsingStrings(communityName[i], communityName[i]);
                    mapUserToCommunity(user.getUserId(), communityName[i]);
                }
            }
            user.setBorrowingLimit(user.getIncome() / 2);


        } catch (Exception e) {
            log.error("Error encountered during user fetching operation");
            throw new ServiceException("Error encountered during user fetch operation", e);
        }
    }

    public boolean markUserAsInactive(String userId) throws ServiceException {
        try {
            User user = userRepository.getUserForUserId(userId);
            if (user != null) {
                user.setActive(false);
                userRepository.inactivatingUser(user);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new ServiceException("Error marking user as inactive", e);
        }
    }

}
