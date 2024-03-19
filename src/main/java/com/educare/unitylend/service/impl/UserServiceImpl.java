package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.CommunityRepository;
import com.educare.unitylend.dao.UserCommunityMapRepository;
import com.educare.unitylend.dao.UserRepository;
import com.educare.unitylend.dao.WalletRepository;
import com.educare.unitylend.model.Community;
import com.educare.unitylend.model.User;
import com.educare.unitylend.model.Wallet;
import com.educare.unitylend.service.UserService;
import com.educare.unitylend.service.WalletService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ObjectMapper objectMapper;
    private UserCommunityMapRepository userCommunityMapRepository;
    private CommunityRepository communityRepository;
    private WalletRepository walletRepository;
    @Override
    public Boolean createUser(User user) throws ServiceException {
        try {
            Map<String, String> communityDetails = user.getCommunityDetails();
            log.info("communityDetails:: "+communityDetails);


            String communityDetailsJson = objectMapper.writeValueAsString(communityDetails);
            user.setCommunityDetailsJson(communityDetailsJson);

            log.info("communityDetailsJson:: "+communityDetailsJson);
            int rowsAffected = userRepository.createUser(user);
            String userId=user.getUserId();

            log.info("userId:: "+userId);
            log.info("count:: "+rowsAffected);

            handleCommunityCreationForUser(communityDetails, userId);
            handleWalletCreation(userId);


            return rowsAffected > 0;
        } catch (JsonProcessingException e) {
            log.error("Error encountered in user creation");
            throw new ServiceException("Error encountered in user creation", e);
        }
    }

    @Override
    public Boolean updateUserDetails(User user) throws ServiceException {
        try {
            String userId = user.getUserId();
            log.info("user id:: "+userId);
            User formerUser=userRepository.getUserForUserId(userId);
            log.info("user:: "+formerUser);
            String formerCommunityDetailsJson=formerUser.getCommunityDetailsJson();


            Map<String, String> formerCommunityDetails = objectMapper.readValue(formerCommunityDetailsJson, new TypeReference<Map<String, String>>() {
            });
            log.info("former community details:: "+formerCommunityDetails);

            Map<String, String> updatedCommunityDetails = user.getCommunityDetails();
            log.info("updated community details:: "+updatedCommunityDetails);

            String communityDetailsJson = objectMapper.writeValueAsString(updatedCommunityDetails);
            user.setCommunityDetailsJson(communityDetailsJson);

            int rowsAffected = userRepository.updateUserDetails(user);

            for (String communityTag : formerCommunityDetails.keySet()) {
                String communityName = formerCommunityDetails.get(communityTag);
                String communityId = communityRepository.findByCommunityTagAndCommunityName(communityTag, communityName).getCommunityId();
                userCommunityMapRepository.deleteMapping(userId, communityId);
            }
            handleCommunityCreationForUser(updatedCommunityDetails,userId);
            return rowsAffected > 0;

        } catch (Exception e) {
            log.error("Error encountered in user updation");
            throw new ServiceException("Error encountered in user updation", e);
        }
    }

    void mapUserToCommunity(String userId, String communityId) {
        try {
            userCommunityMapRepository.createMapping(userId, communityId);
        } catch (Exception e) {
            log.error("Error in mapping user to community");
        }
    }

    void handleWalletCreation(String userId){
        try{
            walletRepository.generateWallet(userId);
            log.info("Wallet for user generated successfully");
        }
        catch (Exception e){
            log.error("Error encountered in wallet generation for user");
        }
    }

    void handleCommunityCreationForUser(Map<String, String> communityDetails, String userId) {
        for (String communityTag : communityDetails.keySet()) {
            String communityName = communityDetails.get(communityTag);
            Boolean existsCommunity = communityRepository.existsCommunity(communityTag, communityName);
            if (!existsCommunity) {
                communityRepository.createCommunity(communityTag, communityName);
            }
            Community community = communityRepository.findByCommunityTagAndCommunityName(communityTag, communityName);
            log.info("community:: "+community);
            String communityId=community.getCommunityId();
            log.info("community id:: "+communityId);
            mapUserToCommunity(userId, communityId);
        }
    }

    @Override
    public User getUserForUserId(String userId) throws ServiceException {
        try {
            User requiredUser = userRepository.getUserForUserId(userId);
            String communityDetailsJson = requiredUser.getCommunityDetailsJson();
            Map<String, String> communityDetails = objectMapper.readValue(communityDetailsJson, new TypeReference<Map<String, String>>() {
            });
            requiredUser.setCommunityDetails(communityDetails);

            return requiredUser;
        } catch (JsonProcessingException e) {
            log.error("Error encountered in getting user by user id");
            throw new ServiceException("Error encountered in getting user by user id", e);
        }
    }

    @Override
    public Boolean deleteUser(String userId) throws ServiceException {
        try {
            int rowsAffected = userRepository.deleteUser(userId);
            return rowsAffected > 0;
        } catch (Exception e) {
            log.error("Error encountered in deleting user");
            throw new ServiceException("Error encountered in deleting user", e);
        }
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
        try {
            List<User> userList = userRepository.getAllUsers();

            for (User user : userList) {
                String communityDetailsJson = user.getCommunityDetailsJson();
                Map<String, String> communityDetails = objectMapper.readValue(communityDetailsJson, new TypeReference<Map<String, String>>() {
                });
                user.setCommunityDetails(communityDetails);
            }
            return userList;
        } catch (JsonProcessingException e) {
            log.error("Error in processing json");
            throw new ServiceException("Error in processing json", e);
        }
    }

    @Override
    public User getUserByWalletId(String walletId) throws ServiceException {
        try{
            User requiredUser=userRepository.getUserByWalletId(walletId);
            return requiredUser;
        }
        catch (Exception e){
            log.error("Error in getting user by wallet id");
            throw new ServiceException("Error in getting user by wallet id",e);
        }
    }
}
