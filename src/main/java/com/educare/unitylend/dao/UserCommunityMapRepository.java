package com.educare.unitylend.dao;


import com.educare.unitylend.model.Community;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserCommunityMapRepository {

    @Delete("Delete from user_community_map where user_id=#{userId} AND community_id=#{communityId}")
    void deleteMapping(@Param("userId") String userId,@Param("communityId") String communityId);

    @Insert("Insert into user_community_map (user_id,community_id) values (#{userId},#{communityId})")
    void createMapping(@Param("userId") String userId,@Param("communityId") String communityId);

    @Select("Select community_id as communityId, community_name as communityName, community_tag as communityTag from " +
            "community where community_id in (select community_id from user_community_map where user_id=#{userId})")
    List<Community> findCommunitiesByUserId(@Param("userId") String userId);
}