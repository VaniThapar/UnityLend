package com.educare.unitylend.service.impl;

import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.BorrowReqRepository;
import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.User;
import com.educare.unitylend.service.BorrowReqService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@AllArgsConstructor
@Service
public class BorrowServiceImpl implements BorrowReqService {
    private BorrowReqRepository borrowReqRepository;
    @Override
    public List<BorrowRequest> getBorrowRequests(String userId) throws ServiceException {
        try {
            List<BorrowRequest> borrowRequestList = borrowReqRepository.getAllRequests(userId);
            return borrowRequestList;
        } catch (Exception e) {
            log.error("Error encountered during user fetch operation");
            throw new ServiceException("Error encountered during user fetch operation", e);
        }
    }

    @Override
    public List<BorrowRequest> getRequestsForUserAndCommunity(String userId, String communityId) throws ServiceException {
        try {
            List<BorrowRequest> borrowRequestListForComm = borrowReqRepository.getAllRequestsForUserAndCommunity(userId,communityId);
            return borrowRequestListForComm;
        } catch (Exception e) {
            log.error("Error encountered during Borrow request for community fetch operation");
            throw new ServiceException("Error encountered during Borrow request for community fetch operation", e);
        }
    }

    @Override
    public List<BorrowRequest> getRequestsByCommunityAndAmount(String userId, double amount) throws ServiceException {
        try {
            List<BorrowRequest> borrowRequestListByAmount = borrowReqRepository.getAllRequestsByCommunityAndAmount(userId,amount);
            return borrowRequestListByAmount;
        } catch (Exception e) {
            log.error("Error encountered during Borrow request filtered by amount fetch operation");
            throw new ServiceException("Error encountered during Borrow request filtered by amount fetch operation", e);
        }
    }
}