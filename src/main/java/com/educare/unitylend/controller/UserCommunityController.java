package com.educare.unitylend.controller;

import com.educare.unitylend.Exception.ControllerException;
import com.educare.unitylend.service.UserCommunityService;
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
@RequestMapping("/usercommunity")
public class UserCommunityController {

private UserCommunityService usercommunityService;
    @GetMapping("/{userId}")
    public ResponseEntity<List<String>> getAllCommunities(@PathVariable String userId) throws ControllerException {
        try {
            List<String> communityList = usercommunityService.getCommunitiesByUserId(userId);
            return ResponseEntity.ok(communityList);
        } catch (Exception e) {
            log.error("Error encountered in getting the communities for user with ID: {}", userId, e);
            throw new ControllerException("Error encountered in getting the communities", e);
        }
    }



}
