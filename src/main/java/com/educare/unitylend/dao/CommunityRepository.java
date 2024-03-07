package com.educare.unitylend.dao;

import com.educare.unitylend.model.Community;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

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

//    @Select("SELECT commontag FROM community WHERE commontag = #{commontag}")
//    String findByCommontag(String commontag);

    @Select({
            "<script>",
            "SELECT name, commontag FROM community WHERE name IN ",
            "<foreach item='name' collection='names' open='(' separator=',' close=')'>",
            "#{name}",
            "</foreach>",
            "</script>"
    })
    Map<String, String> findCommontagsByNames(
            @Param("name1") String name1,
            @Param("name2") String name2,
            @Param("name3") String name3
    );



    @Select("SELECT communityid FROM community WHERE communityname = #{name}")
    String getCommunityIdByName(String name);
}
