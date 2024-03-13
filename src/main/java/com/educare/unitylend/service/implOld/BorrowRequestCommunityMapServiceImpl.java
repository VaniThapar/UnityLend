package com.educare.unitylend.service.implOld;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.BorrowRequestCommunityMapRepository;
import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.Community;
import com.educare.unitylend.service.BorrowRequestCommunityMapService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@AllArgsConstructor
@Service

public class BorrowRequestCommunityMapServiceImpl implements BorrowRequestCommunityMapService {

    BorrowRequestCommunityMapRepository borrowRequestCommunityMapRepository;
    @Override
    public List<Community> getCommunitiesByRequestId(String requestId) throws ServiceException {
        try{
            List<Community>communities = borrowRequestCommunityMapRepository.getCommunitiesByRequestId(requestId);
            return communities;
        }
        catch(Exception e){
            log.error("Error encountered while fetching communities in which request with given request id is raised");
            throw new ServiceException("Error encountered while fetching communities in which request with given request id is raised",e);
        }
    }

    @Override
    public List<BorrowRequest> getRequestsByCommunityId(String communityId) throws ServiceException {
        try{
            List<BorrowRequest>requests = borrowRequestCommunityMapRepository.getRequestsByCommunityId(communityId);
            return requests;
        }
        catch(Exception e){
            log.error("Error encountered while fetching requests by community id");
            throw new ServiceException("Error encountered while fetching requests by community id",e);
        }
    }
}
