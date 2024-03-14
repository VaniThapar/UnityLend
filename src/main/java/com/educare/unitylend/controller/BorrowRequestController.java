package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.Status;
import com.educare.unitylend.service.BorrowRequestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class BorrowRequestController extends BaseController{

    ResponseEntity<List<BorrowRequest>> getAllBorrowRequests() throws ServiceException {
        return null;
    }

    ResponseEntity<List<BorrowRequest>> getBorrowRequestForUserId(String userId) throws ServiceException {
        return null;
    }

    ResponseEntity<Void> createBorrowRequest(@RequestBody BorrowRequest borrowRequest) throws ServiceException {
        return null;
    }

    ResponseEntity<List<BorrowRequest>> getBorrowRequestForCommunity(@RequestParam List<String> communityId) throws ServiceException {
        return null;
    }

    ResponseEntity<Void> updateEMIDefaults() throws ServiceException {
        return null;
    }

    ResponseEntity<Void> updateBorrowRequestStatus(@RequestBody Status status) throws ServiceException {
        return null;
    }
}



