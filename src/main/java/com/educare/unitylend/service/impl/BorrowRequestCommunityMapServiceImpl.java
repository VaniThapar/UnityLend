package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.*;
import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.Community;
import com.educare.unitylend.model.Status;
import com.educare.unitylend.model.User;
import com.educare.unitylend.service.BorrowRequestCommunityMapService;
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

public class BorrowRequestCommunityMapServiceImpl  implements BorrowRequestCommunityMapService {
    private BorrowRequestCommunityMapRepository borrowRequestCommunityMapRepository;
    private BorrowRequestRepository borrowRequestRepository;
    private UserRepository userRepository;
    private UserCommunityMapRepository userCommunityMapRepository;
    private StatusRepository statusRepository;

    /**
     * Retrieves communities associated with a borrow request.
     *
     * @param requestId The ID of the borrow request for which communities are to be retrieved.
     * @return List<Community> The communities associated with the borrow request.
     * @throws ServiceException If an error occurs during the communities retrieval process.
     */
    @Override
    public List<Community> getCommunitiesByRequestId(String requestId) throws ServiceException {
        try{
            List<Community> communities = borrowRequestCommunityMapRepository.getCommunitiesByRequestId(requestId);
            return communities;
        }
        catch(Exception e){
            log.error("Error encountered while fetching communities in which request with given request id is raised");
            throw new ServiceException("Error encountered while fetching communities in which request with given request id is raised",e);
        }
    }

    /**
     * Retrieves borrow requests associated with a given community ID.
     *
     * @param communityId The ID of the community for which borrow requests are to be retrieved.
     * @return List<BorrowRequest> The borrow requests associated with the given community ID.
     * @throws ServiceException If an error occurs during the retrieval process.
     */
    @Override
    public List<BorrowRequest> getRequestsByCommunityId(String communityId) throws ServiceException {
        try{
            List<BorrowRequest> requests = borrowRequestCommunityMapRepository.getRequestsByCommunityId(communityId);
            for(BorrowRequest request : requests){
                String borrowerId=borrowRequestRepository.getBorrowerIdUsingRequestId(request.getBorrowRequestId());
                User requiredUser = userRepository.getUserForUserId(borrowerId);
                request.setBorrower(requiredUser);
                ObjectMapper objectMapper = new ObjectMapper();
                String communityDetailsJson = requiredUser.getCommunityDetailsJson();
                Map<String, String> communityDetails = objectMapper.readValue(communityDetailsJson, new TypeReference<Map<String, String>>() {
                });
                requiredUser.setCommunityDetails(communityDetails);
                request.setCommunityIds(userCommunityMapRepository.getCommunityIdsWithUserId(borrowerId));
                Integer code=borrowRequestRepository.getStatusCodeForReqId(request.getBorrowRequestId());
                Status status=statusRepository.getStatusByStatusCode(code);
                request.setBorrowStatus(status);
            }
            return requests;
        }
        catch(Exception e){
            log.error("Error encountered while fetching requests by community id");
            throw new ServiceException("Error encountered while fetching requests by community id",e);
        }
    }
}

