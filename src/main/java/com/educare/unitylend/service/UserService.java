package com.educare.unitylend.service;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.User;

import java.util.List;

public interface UserService {

    /**
     * @return the list of all available {@link User}
     * @throws ServiceException : Throws if any exception occurs
     */
    List<User> getUsers() throws ServiceException;
    //void updateUser(User user) throws ServiceException;

    void createUser(User user) throws ServiceException;
    /**
     * @param userId : Uniquely identifies a user
     * @return Object of {@link User} for the given parameter
     * @throws ServiceException :Throws if any exception occurs
     */
    User getUserByUserId(String userId) throws ServiceException;
}
