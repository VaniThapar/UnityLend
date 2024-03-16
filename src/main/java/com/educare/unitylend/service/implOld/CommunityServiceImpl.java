//package com.educare.unitylend.service.implOld;
//
//import com.educare.unitylend.Exception.ServiceException;
//import com.educare.unitylend.dao.CommunityRepository;
//import com.educare.unitylend.model.Community;
//import com.educare.unitylend.service.CommunityService;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Slf4j
//@AllArgsConstructor
//@Service
//public class CommunityServiceImpl implements CommunityService {
//    private CommunityRepository communityRepository;
//
//    public List<Community> getCommunities() throws ServiceException {
//        try {
//            List<Community> communityList = communityRepository.getAllCommunities();
//            return communityList;
//        } catch (Exception e) {
//            log.error("Error encountered during community fetching operation");
//            throw new ServiceException("Error encountered during community fetch operation", e);
//        }
//    }
//
//    @Override
//    public String getCommunityName(String communityTag) throws ServiceException {
//        try {
//            String communityName= communityRepository.getCommunity(communityTag);
//            return communityName;
//        } catch (Exception e) {
//            log.error("Error encountered during getting community name");
//            throw new ServiceException("Error encountered during getting community name", e);
//        }
//    }
//
//    public void createCommunity(Community newCommunity) throws ServiceException {
//        try {
//            if (!communityRepository.existsByCommontag(newCommunity.getCommonTag())) {
//                communityRepository.createCommunity(newCommunity);
//                newCommunity.setCommunityId(communityRepository.getCommunityIdByName(newCommunity.getCommonTag()));
//            }
//        } catch (Exception e) {
//            log.error("Error encountered during user creation operation");
//            throw new ServiceException("Error encountered during user creation operation", e);
//        }
//    }
//
//
//
//}
