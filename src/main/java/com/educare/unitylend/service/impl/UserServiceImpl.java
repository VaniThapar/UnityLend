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

    /**
     * Creates a new user with the provided details.
     *
     * @param user The user object containing the details of the user to be created.
     * @return True if the user creation is successful, false otherwise.
     * @throws ServiceException If an error occurs during user creation.
     */
    @Override
    public Boolean createUser(User user) throws ServiceException {
        try {
            Map<String, String> communityDetails = user.getCommunityDetails();
            String communityDetailsJson = objectMapper.writeValueAsString(communityDetails);
            user.setCommunityDetailsJson(communityDetailsJson);

            int rowsAffected = userRepository.createUser(user);
            String userId=user.getUserId();
            handleCommunityCreationForUser(communityDetails, userId);
            handleWalletCreation(userId);

            return rowsAffected > 0;
        } catch (JsonProcessingException e) {
            log.error("Error encountered in user creation");
            throw new ServiceException("Error encountered in user creation", e);
        }
    }


    /**
     * Updates the details of an existing user.
     *
     * @param user The user object containing the updated details.
     * @return True if the user details are successfully updated, false otherwise.
     * @throws ServiceException If an error occurs during user details update.
     */
    @Override
    public Boolean updateUserDetails(User user) throws ServiceException {
        try {
            String userId = user.getUserId();
            User formerUser=userRepository.getUserForUserId(userId);
            String formerCommunityDetailsJson=formerUser.getCommunityDetailsJson();

            Map<String, String> formerCommunityDetails = objectMapper.readValue(formerCommunityDetailsJson, new TypeReference<Map<String, String>>() {
            });
            Map<String, String> updatedCommunityDetails = user.getCommunityDetails();
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
            log.error("Error encountered in updating user");
            throw new ServiceException("Error encountered in updating user", e);
        }
    }


    /**
     * Maps a user to a community.
     *
     * @param userId The ID of the user to be mapped.
     * @param communityId The ID of the community to which the user will be mapped.
     */
    void mapUserToCommunity(String userId, String communityId) {
        try {
            userCommunityMapRepository.createMapping(userId, communityId);
        } catch (Exception e) {
            log.error("Error in mapping user to community");
        }
    }


    /**
     * Handles the creation of a wallet for a user.
     *
     * @param userId The ID of the user for whom the wallet will be generated.
     */
    void handleWalletCreation(String userId){
        try{
            walletRepository.generateWallet(userId);
            log.info("Wallet for user generated successfully");
        }
        catch (Exception e){
            log.error("Error encountered in wallet generation for user");
        }
    }


    /**
     * Handles the creation of communities for a user based on the provided community details.
     * If a community does not exist, it will be created.
     *
     * @param communityDetails A map containing community tags as keys and community names as values.
     * @param userId The ID of the user for whom communities will be created.
     */
    void handleCommunityCreationForUser(Map<String, String> communityDetails, String userId) {
        for (String communityTag : communityDetails.keySet()) {
            String communityName = communityDetails.get(communityTag);
            Boolean existsCommunity = communityRepository.existsCommunity(communityTag, communityName);
            if (!existsCommunity) {
                communityRepository.createCommunity(communityTag, communityName);
            }
            Community community = communityRepository.findByCommunityTagAndCommunityName(communityTag, communityName);
            String communityId=community.getCommunityId();
            mapUserToCommunity(userId, communityId);
        }
    }


    /**
     * Retrieves the user details for the given user ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The user object corresponding to the provided user ID.
     * @throws ServiceException If an error occurs while retrieving the user details.
     */
    @Override
    public User getUserForUserId(String userId) throws ServiceException {
        try {
            User requiredUser = userRepository.getUserForUserId(userId);
            if(requiredUser==null){
                throw new Exception("User id is null");
            }
            String communityDetailsJson = requiredUser.getCommunityDetailsJson();
            Map<String, String> communityDetails = objectMapper.readValue(communityDetailsJson, new TypeReference<Map<String, String>>() {
            });
            requiredUser.setCommunityDetails(communityDetails);

            return requiredUser;
        } catch (JsonProcessingException e) {
            log.error("Error encountered in getting user by user id");
            throw new ServiceException("Error encountered in getting user by user id", e);
        }
        catch(Exception e){
            log.error("Error encountered in getting user by user id");
            throw new ServiceException("Error encountered in getting user by user id", e);

        }
    }


    /**
     * Deletes the user corresponding to the provided user ID.
     *
     * @param userId The ID of the user to be deleted.
     * @return True if the user was successfully deleted, otherwise false.
     * @throws ServiceException If an error occurs while deleting the user.
     */
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


    /**
     * Retrieves a list of all users.
     *
     * @return A list containing all users.
     * @throws ServiceException If an error occurs while retrieving the users.
     */
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

    /**
     * Retrieves a list of all users along with their community details.
     *
     * @return A list containing all users with their community details.
     * @throws ServiceException If an error occurs while retrieving the users.
     */
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
