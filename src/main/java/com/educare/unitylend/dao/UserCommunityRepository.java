package com.educare.unitylend.dao;


import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserCommunityRepository {
    @Insert("INSERT INTO usercommunity (userid, communityid) VALUES (#{userid}, #{communityid})")
    void createUserCommunityMapping(@Param("userid") String userId, @Param("communityid") String communityId);


    @Select("SELECT c.CommunityName FROM Community c JOIN UserCommunity uc ON c.CommunityId = uc.CommunityId WHERE uc.UserId = #{userId}")
    List<String> findCommunityNamesByUserId(@Param("userId") String userId);

    @Delete("DELETE FROM usercommunityrepository WHERE userid = #{userId}")
    void deletePrevData(@Param("userId") String userId);

}
