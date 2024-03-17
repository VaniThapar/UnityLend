package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.CommunityRepository;
import com.educare.unitylend.model.Community;
import com.educare.unitylend.service.CommunityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class CommunityServiceImpl implements CommunityService {

    private CommunityRepository communityRepository;
    @Override
    public List<Community> getAllCommunities() throws ServiceException {
        try{
            List<Community>communityList=communityRepository.getAllCommunities();
            log.info("community List:: "+communityList);
            return communityList;
        }
        catch(Exception e){
            log.error("Error encountered in fetching all communities");
            throw new ServiceException("Error encountered in fetching all communities",e);
        }
    }
}
