package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.Community;
import com.educare.unitylend.service.BorrowRequestCommunityMapService;
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
@RequestMapping("/borrow-request-community-map")
public class BorrowRequestCommunityMapController {

    BorrowRequestCommunityMapService borrowRequestCommunityMapService;

    /**
     * API endpoint for retrieving communities associated with a borrow request.
     *
     * @param BorrowRequestId The ID of the borrow request for which communities are to be retrieved.
     * @return ResponseEntity<List<Community>> Indicating the communities for a borrow request.
     * @throws ControllerException If an error occurs during the communities retrieval process.
     */
    @GetMapping("/get-communities/{BorrowRequestId}")
    public ResponseEntity<List<Community>> getCommunitiesByRequestId(@PathVariable(required = true) String BorrowRequestId) throws ControllerException {
        try {
            List<Community> communityList = borrowRequestCommunityMapService.getCommunitiesByRequestId(BorrowRequestId);
            if (communityList == null || communityList.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(communityList);
        } catch (Exception e) {
            log.error("Error encountered in getting the communities with request ID: {}", BorrowRequestId, e);
            throw new ControllerException("Error encountered in getting the communities with request ID", e);
        }
    }

    /**
     * API endpoint for retrieving borrow requests associated with a community.
     *
     * @param communityId The ID of the community for which borrow requests are to be retrieved.
     * @return ResponseEntity<List<BorrowRequest>> Indicating the list of borrow requests for a community ID.
     * @throws ControllerException If an error occurs during the borrow requests retrieval process.
     */
    @GetMapping("/get-requests/{communityId}")
    public ResponseEntity<List<BorrowRequest>> getBorrowRequestsForCommunity(@PathVariable(required = true) String communityId) throws ControllerException {
        try {
            List<BorrowRequest> borrowRequestList = borrowRequestCommunityMapService.getRequestsByCommunityId(communityId);
            if (borrowRequestList == null || borrowRequestList.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(borrowRequestList);
        } catch (Exception e) {
            log.error("Error encountered in getting the requests for community with ID: {}", communityId, e);
            throw new ControllerException("Error encountered in getting the requests for community with given ID", e);
        }
    }

}
