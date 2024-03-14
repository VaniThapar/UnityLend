package com.educare.unitylend.service;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.User;

import java.util.List;

public interface UserService {


    List<User> getAllUsers() throws ServiceException;

    boolean updateUserDetails(User user) throws ServiceException;
    //   boolean markUserAsInactive(String userId) throws ServiceException;
    boolean createUser(User user) throws ServiceException;

    User getUserByUserId(String userId) throws ServiceException;

    boolean deleteUser(String userId) throws ServiceException;
}
