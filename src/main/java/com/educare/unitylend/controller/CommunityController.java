package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.model.Community;
import com.educare.unitylend.model.User;
import com.educare.unitylend.service.CommunityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public List<Community> getAllCommunities() throws ControllerException {

        try {
            List<Community> communityList = communityService.getCommunities();
            return communityList;
        } catch (Exception e) {
            log.error("Error encountered in getting the users");
            throw new ControllerException("Error encountered in getting the users", e);
        }
    }
    @GetMapping("get-community-by-tag")
    public String getCommunityWithTag(@RequestParam String commonTag) throws ControllerException {
        try {
            String communityName = communityService.getCommunityName(commonTag);
            return communityName;
        } catch (Exception e) {
            log.error("Error encountered in getting the community by tag");
            throw new ControllerException("Error encountered in getting the community by tag", e);
        }
    }


    @PostMapping("/create-community")
    public ResponseEntity<String> createNewCommunity(@RequestBody Community community) throws ControllerException {
        try {
            communityService.createCommunity(community);
            return ResponseEntity.ok("succcesss!!!");
        } catch (Exception e) {
            log.error("Error encountered in getting the users");
            throw new ControllerException("Error encountered in getting the users", e);
        }

    }

}
