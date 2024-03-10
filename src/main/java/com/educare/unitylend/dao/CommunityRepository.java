package com.educare.unitylend.dao;

import com.educare.unitylend.model.Community;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository

public interface CommunityRepository {
    String SELECT_COMMUNITIES = "select * from community";

    @Select(SELECT_COMMUNITIES)
    List<Community> getAllCommunities();

    @Select({
            "<script>",
            "SELECT communityname as communityName FROM community",
            "WHERE 1=1",
            "<if test='commonTag != null'>",
            "AND commontag = #{commonTag}",
            "</if>",
            "</script>"
    })
    String getCommunity(@Param("commonTag") String commonTag);


    @Insert({
            "<script>",
            "INSERT INTO community (communityid , communityname , commontag )",
            "VALUES (uuid_generate_v4(),",
            "<if test='community.communityName != null and community.commonTag != null'>",
            "#{community.communityName}, #{community.commonTag}",
            "</if>",
            "<if test='community.communityName == null'>",
            "NULL, #{community.commonTag}",
            "</if>",
            "<if test='community.commonTag == null'>",
            "#{community.communityName}, NULL",
            "</if>",
            "<if test='community.communityName == null and community.commonTag == null'>",
            "NULL, NULL",
            "</if>",
            ")",
            "</script>"
    })
    void createCommunity(@Param("community") Community community);


    @Insert({
            "<script>",
            "INSERT INTO community (communityid, communityname, commontag)",
            "VALUES (uuid_generate_v4(),",
            "<if test='name != null'>",
            "#{name},",
            "</if>",
            "<if test='name == null'>",
            "NULL,",
            "</if>",
            "<if test='tag != null'>",
            "#{tag}",
            "</if>",
            "<if test='tag == null'>",
            "NULL",
            "</if>",
            ")",
            "</script>"
    })
    void createCommunityUsingStrings(@Param("name") String name, @Param("tag") String tag);


    @Select({
            "<script>",
            "SELECT COUNT(*) > 0 FROM community",
            "WHERE 1=1",
            "<if test='commonTag != null'>",
            "AND commontag = #{commonTag}",
            "</if>",
            "</script>"
    })
    boolean existsByCommontag(@Param("commonTag") String commonTag);

    @Select({
            "<script>",
            "SELECT communityname as communityName, commontag as commonTag FROM community",
            "WHERE 1=1",
            "<if test='names != null and !names.isEmpty()'>",
            "AND commontag IN ",
            "<foreach item='name' collection='names' open='(' separator=',' close=')'>",
            "#{name}",
            "</foreach>",
            "</if>",
            "</script>"
    })
    @MapKey("commontag")
    Map<String, String> findCommontagsByNames(@Param("names") List<String> names);

    @Select({
            "<script>",
            "SELECT communityid as communityId FROM community",
            "WHERE 1=1",
            "<if test='name != null'>",
            "AND communityname = #{name}",
            "</if>",
            "</script>"
    })
    String getCommunityIdByName(@Param("name") String name);

}
