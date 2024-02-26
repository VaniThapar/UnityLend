package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.controller.UserCommunityController;
import com.educare.unitylend.dao.CommunityRepository;
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
    private UserCommunityController userCommunityController;

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
                for(String str:matchingCommontags){
                    if(!communityRepository.existsByCommontag(str)){

                        communityRepository.createCommunityUsingStrings(str,str);
                    }
                }

                userRepository.createUser(newUser);
                log.info("addeddd!!!!", newUser.getIncome());
                newUser.setBorrowingLimit(newUser.getIncome() / 2);

                log.info("addeddd!!!!", newUser.getBorrowingLimit());
            //    userCommunityController.createUserCommunityMapping(newUser.getUserid(),);

        } catch (Exception e) {
            log.error("Error encountered during user creation operation");
            throw new ServiceException("Error encountered during user creation operation", e);
        }
    }
    private List<String> findMatchingCommontags(User user) {
        List<String> matchingCommontags = new ArrayList<>();
        String officenameCommontag=null;
        String collegeuniversityCommontag=null;
        String localityCommontag=null;

        // Check if officename, collegeuniversity, or locality exists in the Community table as commontag
        if(user.getOfficename()!=null)
        officenameCommontag = communityRepository.findByCommontag(user.getOfficename());
        if(user.getCollegeuniversity()!=null)
        collegeuniversityCommontag = communityRepository.findByCommontag(user.getCollegeuniversity());
        if(user.getLocality()!=null)
        localityCommontag = communityRepository.findByCommontag(user.getLocality());

        // Add non-null commontags to the list
        if (officenameCommontag == null && user.getOfficename()!=null ) {
            matchingCommontags.add(user.getOfficename());
        }
        if (collegeuniversityCommontag == null && user.getCollegeuniversity()!=null) {
            matchingCommontags.add(user.getCollegeuniversity());
        }
        if (localityCommontag == null && user.getLocality()!=null) {
            matchingCommontags.add(user.getLocality());
        }

        return matchingCommontags;
    }

    public void updateUser(User user) throws ServiceException  {
        // Your logic to validate and perform additional checks before updating
        try {
            userRepository.updateUser(user);
        }
        catch (Exception e) {
            log.error("Error encountered during user fetching operation");
            throw new ServiceException("Error encountered during user fetch operation", e);
        }
    }

}
