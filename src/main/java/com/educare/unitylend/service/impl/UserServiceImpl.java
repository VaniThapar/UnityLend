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
       List<String> matchingCommontags = findMatchingCommontags(newUser);
        try {
            // Add any validation logic if needed before saving to the database
            for (String tag : matchingCommontags) {
                if (!communityRepository.existsByCommontag(tag)) {

                    communityRepository.createCommunityUsingStrings(tag, tag);
                    }

                }

                userRepository.createUser(newUser);
            newUser.setUserid(userRepository.settingID(newUser.getEmail()));
            //  log.info("addeddd!!!!", newUser.getIncome());
                newUser.setBorrowingLimit(newUser.getIncome() / 2);

            //    log.info("addeddd!!!!", newUser.getBorrowingLimit());

            String collegeUni = newUser.getCollegeuniversity();
            String office = newUser.getOfficename();
            String locality = newUser.getLocality();
            if (collegeUni != null) {
                userCommunityRepository.createUserCommunityMapping(newUser.getUserid(), communityRepository.getCommunityIdByName(collegeUni));
            }
            if (office != null) {
                userCommunityRepository.createUserCommunityMapping(newUser.getUserid(), communityRepository.getCommunityIdByName(office));
            }
            if (locality != null) {
                userCommunityRepository.createUserCommunityMapping(newUser.getUserid(), communityRepository.getCommunityIdByName(locality));
            }


        } catch (Exception e) {
            log.error("Error encountered during user creation operation");
            throw new ServiceException("Error encountered during user creation operation", e);
        }
    }
    private List<String> findMatchingCommontags(User user) {
        List<String> matchingCommontags = new ArrayList<>();
        String officenameCommontag = null;
        String collegeuniversityCommontag = null;
        String localityCommontag = null;

// Check if officename, collegeuniversity, or locality exists in the Community table as commontag
        if (user.getOfficename() != null || user.getCollegeuniversity() != null || user.getLocality() != null) {
            Map<String, String> commontags = communityRepository.findCommontagsByNames(
                    user.getOfficename(), user.getCollegeuniversity(), user.getLocality());

            officenameCommontag = commontags.get(user.getOfficename());
            collegeuniversityCommontag = commontags.get(user.getCollegeuniversity());
            localityCommontag = commontags.get(user.getLocality());
        }


// Add non-null commontags to the list
        if (officenameCommontag == null && user.getOfficename() != null) {
            matchingCommontags.add(user.getOfficename());
        }
        if (collegeuniversityCommontag == null && user.getCollegeuniversity() != null) {
            matchingCommontags.add(user.getCollegeuniversity());
        }
        if (localityCommontag == null && user.getLocality() != null) {
            matchingCommontags.add(user.getLocality());
        }


        return matchingCommontags;
    }

    public void updateUser(User user, String userId) throws ServiceException {
        List<String> updatedCommunities;
        try {
            userCommunityRepository.deletePrevData(userId);

            userRepository.updateUser(user);
            updatedCommunities = userCommunityService.getCommunitiesByUserId(userId);
            for(String community:updatedCommunities){
                if (!communityRepository.existsByCommontag(community)) {

                    communityRepository.createCommunityUsingStrings(community,community);
                }
            }



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
