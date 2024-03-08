package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.controller.UserCommunityController;
import com.educare.unitylend.dao.CommunityRepository;
import com.educare.unitylend.dao.UserCommunityRepository;
import com.educare.unitylend.dao.UserRepository;
import com.educare.unitylend.model.User;
import com.educare.unitylend.service.UserCommunityService;
import com.educare.unitylend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private CommunityRepository communityRepository;
    private UserCommunityController userCommunityController;
    private UserCommunityRepository userCommunityRepository;
    private UserCommunityService userCommunityService;

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
        System.out.println(newUser);

        System.out.println(newUser);


        try {
            // Add any validation logic if needed before saving to the database

            List<String> tags = new ArrayList<>();
            if (newUser.getOfficename() != null) tags.add(newUser.getOfficename());

            if (newUser.getCollegeuniversity() != null) tags.add(newUser.getCollegeuniversity());

            if (newUser.getLocality() != null) tags.add(newUser.getLocality());

            for (String tag : tags) {

                if (!communityRepository.existsByCommontag(tag)) {
                    log.info("Creating community::", tag);
                    communityRepository.createCommunityUsingStrings(tag, tag);
                }
            }

            userRepository.createUser(newUser);
            newUser.setUserid(userRepository.settingID(newUser.getEmail()));
            newUser.setBorrowingLimit(newUser.getIncome() / 2);
            mapUserToCommunity(newUser.getUserid(), tags);
        } catch (Exception e) {
            log.error("Error encountered during user creation operation");
            throw new ServiceException("Error encountered during user creation operation", e);
        }
    }

    private void mapUserToCommunity(String userId, List<String> tags) {

        for (String tag : tags) {
            userCommunityRepository.createUserCommunityMapping(userId, communityRepository.getCommunityIdByName(tag));
        }
    }

    private void mapUserToCommunity(String userId, String tag) {
        userCommunityRepository.createUserCommunityMapping(userId, communityRepository.getCommunityIdByName(tag));
    }


    public void updateUser(User user, String userId) throws ServiceException {
        List<String> updatedCommunities;
        String[] communityy = new String[3];
        try {
            userCommunityRepository.deletePrevData(userId);
            userRepository.updateUser(user);
            updatedCommunities = userRepository.findCommunitiesByUserId(userId);
            for (String community : updatedCommunities) {
                communityy = community.split(", ");
            }
            for (int i = 0; i < 3; i++) {
                if (!communityRepository.existsByCommontag(communityy[i])) {
                    communityRepository.createCommunityUsingStrings(communityy[i], communityy[i]);
                    mapUserToCommunity(user.getUserid(), communityy[i]);
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
