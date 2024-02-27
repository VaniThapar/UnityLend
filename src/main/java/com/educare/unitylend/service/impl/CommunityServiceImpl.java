package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.CommunityRepository;
import com.educare.unitylend.model.Community;
import com.educare.unitylend.service.CommunityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class CommunityServiceImpl implements CommunityService {
    private CommunityRepository communityRepository;

    public List<Community> getCommunities() throws ServiceException {
        try {
            List<Community> communityList = communityRepository.getAllCommunities();
            //  log.info("userList ",userList);
            return communityList;
        } catch (Exception e) {
            log.error("Error encountered during user fetching operation");
            throw new ServiceException("Error encountered during user fetch operation", e);
        }
    }

    @Override
    public String getCommunityName(String communityTag) throws ServiceException {
        try {
            String communityName= communityRepository.getCommunity(communityTag);
            //  log.info("userList ",userList);
            return communityName;
        } catch (Exception e) {
            log.error("Error encountered during user fetching operation");
            throw new ServiceException("Error encountered during user fetch operation", e);
        }
    }

    public void createCommunity(Community newCommunity) throws ServiceException {

        try {
            if (!communityRepository.existsByCommontag(newCommunity.getCommontag())) {

                communityRepository.createCommunity(newCommunity);
            }


        } catch (Exception e) {
            log.error("Error encountered during user creation operation");
            throw new ServiceException("Error encountered during user creation operation", e);
        }
    }



}
