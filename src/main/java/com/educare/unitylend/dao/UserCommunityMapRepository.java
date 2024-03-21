package com.educare.unitylend.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserCommunityMapRepository {

@Select("SELECT community_id from user_community_map where user_id=#{userId}")
    List<String> getCommunityIdsWithUserId(@Param("userId") String userId);
}