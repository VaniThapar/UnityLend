package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.Exception.ServiceException;
import com.educare.unitylend.model.Community;
import com.educare.unitylend.service.CommunityService;
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
    CommunityService communityService;

    @GetMapping("all-communities")
    public ResponseEntity<?> getAllCommunities() throws ControllerException {
        try {
            List<Community> communityList = communityService.getCommunities();

            if (communityList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found");
            }

            return ResponseEntity.ok(communityList);
        } catch (ServiceException e) {
            log.error("Error encountered in getting the communities", e);
            throw new ControllerException("Error encountered in getting the communities", e);
        }
    }

    @GetMapping("get-community-by-tag")
    public ResponseEntity<?> getCommunityWithTag(@RequestParam String commonTag) throws ControllerException {
        try {
            String communityName = communityService.getCommunityName(commonTag);

            if (communityName == null || communityName.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found");
            }

            return ResponseEntity.ok(communityName);
        } catch (ServiceException e) {
            log.error("Error encountered in getting the community by tag", e);
            throw new ControllerException("Error encountered in getting the community by tag", e);
        }
    }

    @PostMapping("/create-community")
    public ResponseEntity<String> createNewCommunity(@RequestBody Community community) throws ControllerException {
        try {
            communityService.createCommunity(community);
            return ResponseEntity.ok("success!!!");
        } catch (ServiceException e) {
            log.error("Error encountered in creating the community", e);
            throw new ControllerException("Error encountered in creating the community", e);
        }
    }
}
