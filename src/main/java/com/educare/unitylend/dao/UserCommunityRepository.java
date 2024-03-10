package com.educare.unitylend.dao;


import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserCommunityRepository {
    @Select({
            "<script>",
            "SELECT c.CommunityName FROM Community c",
            "JOIN UserCommunity uc ON c.CommunityId = uc.CommunityId",
            "WHERE 1=1",
            "<if test='userId != null'>",
            "AND uc.UserId = #{userId}",
            "</if>",
            "</script>"
    })
    List<String> findCommunityNamesByUserId(@Param("userId") String userId);

    @Insert({
            "<script>",
            "INSERT INTO usercommunity (userid, communityid)",
            "VALUES",
            "(",
            "<if test='userId != null'>",
            "#{userId}, #{communityId}",
            "</if>",
            ")",
            "</script>"
    })
    void createUserCommunityMapping(
            @Param("userId") String userId,
            @Param("communityId") String communityId
    );


    @Delete({
            "<script>",
            "DELETE FROM usercommunity",
            "WHERE 1=1",
            "<if test='userId != null'>",
            "AND userid = #{userId}",
            "</if>",
            "</script>"
    })
    void deletePrevData(@Param("userId") String userId);


}