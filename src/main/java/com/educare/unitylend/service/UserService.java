package com.educare.unitylend.service;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.User;

import java.util.List;

/**
 * Interface for managing the Users in the system
 */
public interface UserService {
    Boolean createUser(User user) throws ServiceException;

    Boolean updateUserDetails(User user) throws ServiceException;

    User getUserForUserId(String userId) throws ServiceException;

    Boolean deleteUser(String userId) throws ServiceException;

    List<User> getAllUsers() throws ServiceException;

    User getUserByWalletId(String walletId) throws ServiceException;
}
