package com.educare.unitylend.dao;

import com.educare.unitylend.model.Community;
import com.educare.unitylend.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

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

    @Select("SELECT commontag FROM community WHERE commontag = #{commontag}")
    String findByCommontag(String commontag);

    @Select("SELECT communityid FROM community WHERE communityname = #{name}")
    UUID getCommunityIdByName(String name);
}
