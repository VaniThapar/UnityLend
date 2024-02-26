package com.educare.unitylend.dao;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserCommunityRepository {
    @Insert("INSERT INTO usercommunity (userid, communityid) VALUES (#{userid}, #{communityid})")
    void createUserCommunityMapping(@Param("userid") String userId, @Param("communityid") String communityId);


}
