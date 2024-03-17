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


    @Insert("Insert into community(community_id,community_tag,community_name) values (uuid_generate_v4(),#{communityTag},#{communityName})")
    void createCommunity(@Param("communityTag")String communityTag,@Param("communityName")String communityName);

    @Select("Select community_id as communityId, community_name as communityName, community_tag as communityTag " +
            "from community " +
            "where community_tag = #{communityTag} AND community_name = #{communityName}")
    Community findByCommunityTagAndCommunityName(@Param("communityTag")String communityTag,@Param("communityName")String communityName);

    @Select("Select community_id as communityId,community_name as communityName, community_tag as communityTag from community")
    List<Community> getAllCommunities();
}
