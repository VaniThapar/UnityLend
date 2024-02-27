package com.educare.unitylend.dao;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Mapper
@Repository
public interface UserCommunityRepository {
    @Insert("INSERT INTO usercommunity (userid, communityid) VALUES (#{userid}, #{communityid})")
    void createUserCommunityMapping(@Param("userid") UUID userId, @Param("communityid") UUID communityId);


}
