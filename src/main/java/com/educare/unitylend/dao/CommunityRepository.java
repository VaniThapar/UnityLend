package com.educare.unitylend.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CommunityRepository {
    @Select("SELECT community_name FROM community WHERE community_id = #{communityId}")
    String getCommunity(@Param("communityId") String communityId);
}
