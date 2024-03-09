package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.BorrowReqRepository;
import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.User;
import com.educare.unitylend.service.BorrowReqService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/all-borrow-requests")
public class BorrowReqController extends BaseController{
    BorrowReqService borrowReqService;
    @GetMapping("/{userId}")
    public List<BorrowRequest> getAllRequests(@PathVariable String userId) throws ControllerException {
        try {
            List<BorrowRequest> borrowRequestList = borrowReqService.getBorrowRequests(userId);
            return borrowRequestList;
        } catch (ServiceException e) {
            log.error("Error encountered in getting the borrow requests");
            throw new ControllerException("Error encountered in getting the borrow requests", e);
        }
    }
    @GetMapping("/{userId}/{communityId}")
    public List<BorrowRequest> getRequestsForUserAndCommunity(
            @PathVariable String userId,
            @PathVariable String communityId
    ) throws ControllerException {
        try {
            List<BorrowRequest> borrowRequestList = borrowReqService.getRequestsForUserAndCommunity(userId, communityId);
            return borrowRequestList;
        } catch (Exception e) {
            log.error("Error encountered in getting the borrow requests for particular community", e);
            throw new ControllerException("Error encountered in getting the borrow requests for particular community", e);
        }
    }

    @GetMapping("/user/{userId}/target-amount/{amount}")
    public List<BorrowRequest> getRequestsForUserAndCommunity(
            @PathVariable String userId,
            @PathVariable double amount
    ) throws ControllerException {
        try {
            List<BorrowRequest> borrowRequestListByAmount = borrowReqService.getRequestsByCommunityAndAmount(userId, amount);
            return borrowRequestListByAmount;
        } catch (Exception e) {
            log.error("Error encountered in getting the borrow requests filtered by amount", e);
            throw new ControllerException("Error encountered in getting the borrow requests filtered by amount", e);
        }
    }

    @GetMapping("/community/{communityId}")
    public List<BorrowRequest> getBorrowRequestByCommunityId(
            @PathVariable String communityId
    ) throws ControllerException {
        try {
            List<BorrowRequest> borrowRequestListByCommunityId = borrowReqService.getBorrowRequestsByCommunityId(communityId);
            return borrowRequestListByCommunityId;
        } catch (Exception e) {
            log.error("Error encountered in getting the borrow requests by community Ids!", e);
            throw new ControllerException("Error encountered in getting the borrow requests by commmunity Ids!", e);
        }
    }

    @GetMapping("/community/{communityId}/target-amount/{amount}")
    public List<BorrowRequest> getRequestsForCommunityByAmount(
            @PathVariable String communityId,
            @PathVariable double amount
    ) throws ControllerException{
        try{
            List<BorrowRequest> borrowRequestListOfCommunityIdByAmount = borrowReqService.getBorrowRequestsOfCommunityByAmount(communityId,amount);
            return borrowRequestListOfCommunityIdByAmount;
        }catch (Exception e){
            log.error("Error encountered in getting the borrow requests of community Ids filtered by amount!", e);
            throw new ControllerException("Error encountered in getting the borrow requests of commmunity Ids filtered by amount!", e);
        }
    }

}
