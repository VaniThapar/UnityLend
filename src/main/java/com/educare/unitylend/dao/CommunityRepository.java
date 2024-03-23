package com.educare.unitylend.dao;

import com.educare.unitylend.model.Community;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommunityRepository {

    @Select("Select count(*)>0 from community where community_tag=#{communityTag} AND community_name=#{communityName}")
    Boolean existsCommunity(@Param("communityTag")String communityTag,@Param("communityName")String communityName);

    @Insert({"<script>",
            "INSERT INTO community (community_id, community_tag, community_name)",
            "VALUES",
            "<if test='communityTag != null and communityName != null'>",
            "(uuid_generate_v4(), #{communityTag}, #{communityName})",
            "</if>",
            "</script>"})
    void createCommunity(@Param("communityTag") String communityTag, @Param("communityName") String communityName);

    @Select({
            "<script>",
            "SELECT community_id as communityId, community_name as communityName, community_tag as communityTag ",
            "FROM community WHERE 1=1",
            "<if test='communityTag != null'>",
            "   AND community_tag = #{communityTag}",
            "</if>",
            "<if test='communityName != null'>",
            "   AND community_name = #{communityName}",
            "</if>",
            "</script>"
    })
    Community findByCommunityTagAndCommunityName(@Param("communityTag") String communityTag, @Param("communityName") String communityName);

    @Select("Select community_id as communityId,community_name as communityName, community_tag as communityTag from community")
    List<Community> getAllCommunities();

    @Select("SELECT community_name FROM community WHERE community_id = #{communityId}")
    String getCommunity(@Param("communityId") String communityId);
}
