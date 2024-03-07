package com.educare.unitylend.dao;

import com.educare.unitylend.model.Community;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Repository

public interface CommunityRepository {
    @Select("select * from community")
    List<Community> getAllCommunities();

    @Select("select communityname from community where commontag = #{commontag}")
    String getCommunity(String commontag);

    @Insert("INSERT INTO community (communityid, communityname, commontag) VALUES (uuid_generate_v4(), #{communityname}, #{commontag})")
    void createCommunity(Community community);

    @Insert("INSERT INTO community (communityid, communityname, commontag) VALUES (uuid_generate_v4(), #{name}, #{tag})")
    void createCommunityUsingStrings(String name, String tag);

    @Select("SELECT COUNT(*) > 0 FROM community WHERE commontag = #{commontag}")
    boolean existsByCommontag(String commontag);


    @Select({
            "<script>",
            "SELECT communityname, commontag FROM community",
            "WHERE commontag IN ",
            "<foreach item='name' collection='names' open='(' separator=',' close=')'>",
            "#{name}",
            "</foreach>",
            "</script>"
    })
    @MapKey("commontag")
    Map<String, String> findCommontagsByNames(@Param("names") List<String> names);


    @Select("SELECT communityid FROM community WHERE communityname = #{name}")
    String getCommunityIdByName(String name);
}
