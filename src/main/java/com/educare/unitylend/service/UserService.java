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

    /**
     * update the user infomation {@link User}
     *
     * @throws ServiceException : Throws if any exception occurs
     */
    void updateUser(User user,String userId) throws ServiceException;
    boolean markUserAsInactive(String userId) throws ServiceException;

    /**
     * create the user {@link User}
     * @throws ServiceException : Throws if any exception occurs
     */
    void createUser(User user) throws ServiceException;

    /**
     * @return the User using userId
     * @throws ServiceException : Throws if any exception occurs
     */
    User getUserByUserId(String userId) throws ServiceException;
}
