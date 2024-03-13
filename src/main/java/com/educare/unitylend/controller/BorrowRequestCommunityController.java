package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.Community;
import com.educare.unitylend.service.BorrowRequestCommunityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/borrowrequestcommunity")
public class BorrowRequestCommunityController {

    BorrowRequestCommunityService borrowRequestCommunityService;

    @GetMapping("/get-communities/{requestId}")
    public ResponseEntity<List<?>> getCommunitiesByRequestId(@PathVariable String requestId) throws ControllerException {
        //Getting all communities for a given request
        try {
            if (requestId == null || requestId.isEmpty()) {
                return ResponseEntity.badRequest().body(List.of("Request ID cannot be null or empty"));
            }

            List<Community> communityList = borrowRequestCommunityService.getCommunitiesByRequestId(requestId);

            if (communityList == null || communityList.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(communityList);
        } catch (Exception e) {
            log.error("Error encountered in getting the communities with request ID: {}", requestId, e);
            throw new ControllerException("Error encountered in getting the communities with request ID", e);
        }
    }

    @GetMapping("/get-requests/{communityId}")
    public ResponseEntity<List<?>> getRequestsByCommunityId(@PathVariable String communityId) throws ControllerException {
        try {
            if (communityId == null || communityId.isEmpty()) {
                return ResponseEntity.badRequest().body(List.of("Community ID cannot be null or empty"));
            }

            List<BorrowRequest> requests = borrowRequestCommunityService.getRequestsByCommunityId(communityId);

            if (requests == null || requests.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(requests);
        } catch (Exception e) {
            log.error("Error encountered in getting the requests for community with ID: {}", communityId, e);
            throw new ControllerException("Error encountered in getting the requests for community with given ID", e);
        }
    }

}
