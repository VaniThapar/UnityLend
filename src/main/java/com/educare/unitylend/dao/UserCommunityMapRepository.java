package com.educare.unitylend.dao;


import com.educare.unitylend.model.Community;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserCommunityMapRepository {

    @Delete("DELETE FROM user_community_map WHERE user_id = #{userId} AND community_id = #{communityId}")
    void deleteMapping(@Param("userId") String userId, @Param("communityId") String communityId);

    @Insert({"<script>",
            "INSERT INTO user_community_map (user_id, community_id)",
            "VALUES",
            "<if test='userId != null and communityId != null'>",
            "(#{userId}, #{communityId})",
            "</if>",
            "</script>"})
    void createMapping(@Param("userId") String userId, @Param("communityId") String communityId);


    @Select("SELECT community_id AS communityId, community_name AS communityName, community_tag AS communityTag " +
            "FROM community " +
            "WHERE community_id IN (SELECT community_id FROM user_community_map WHERE user_id = #{userId})")
    List<Community> findCommunitiesByUserId(@Param("userId") String userId);


    @Select("SELECT community_id from user_community_map where user_id=#{userId}")
    List<String> getCommunityIdsWithUserId(@Param("userId") String userId);

}