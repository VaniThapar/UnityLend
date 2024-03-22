package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.dao.CommunityRepository;
import com.educare.unitylend.model.Community;
import com.educare.unitylend.service.CommunityService;
import com.educare.unitylend.service.UserCommunityMapService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/community")
public class CommunityController extends BaseController {

    private CommunityService communityService;
    private UserCommunityMapService userCommunityMapService;


    /**
     * API endpoint for retrieving the communities associated with a specific user.
     *
     * @param userId The unique identifier of the user.
     * @return ResponseEntity containing a list of Community objects if communities are found
     * @throws ControllerException if an error occurs during the retrieval process.
     */
    @GetMapping("/get-communities-by-user-id/{userId}")
    ResponseEntity<List<Community>> getCommunitiesByUserId(@PathVariable(required = true) String userId) throws ControllerException {
        try {

            List<Community> communityList = userCommunityMapService.getCommunitiesByUserId(userId);
            if (communityList.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(communityList);
        } catch (ServiceException e) {
            log.error("Error encountered in getting communities by user id");
            throw new ControllerException("Error encountered in getting communities by user id", e);
        }
    }




    /*For Administration Purpose*/

    /**
     * API endpoint for retrieving all communities.
     *
     * @return ResponseEntity<List < Community>> The list of all communities.
     * @throws ControllerException If an error occurs during the community retrieval process.
     */
    @GetMapping("/get-all-communities")
    public ResponseEntity<List<Community>> getAllCommunities() throws ControllerException {
        try {
            List<Community> communityList = communityService.getAllCommunities();
            log.info("community list:: " + communityList);

            if (communityList.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(communityList);
        } catch (ServiceException e) {
            log.error("Error encountered in getting all communities");
            throw new ControllerException("Error encountered in getting all communities", e);
        }
    }


}
