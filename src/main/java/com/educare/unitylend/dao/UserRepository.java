package com.educare.unitylend.dao;

import com.educare.unitylend.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserRepository {

    String SELECT_USERS = "select * from tempuser";

    @Select(SELECT_USERS)
    List<User> getAllUsers();

    @Insert({
            "<script>",
            "INSERT INTO tempuser (userid, password, name, email, dob, income, officename, collegeuniversity, locality)",
            "VALUES (",
            "CAST(uuid_generate_v4() AS VARCHAR),",
            "<if test='password != null'>#{password},</if>",
            "<if test='name != null'>#{name},</if>",
            "<if test='email != null'>#{email},</if>",
            "#{dob},",
            "<if test='income != null'>#{income},</if>",
            "#{officeName},",
            "#{collegeUniversity},",
            "#{locality}",
            ")",
            "</script>"
    })
    void createUser(User user);

    @Select({
            "<script>",
            "SELECT * FROM tempuser",
            "WHERE 1=1",
            "<if test='userid != null'>",
            "AND userid = #{userId}",
            "</if>",
            "</script>"
    })
    User getUserForUserId(String userId);


    @Select({
            "<script>",
            "SELECT userid FROM tempuser",
            "WHERE 1=1",
            "<if test='email != null'>",
            "AND email = #{email}",
            "</if>",
            "</script>"
    })
    String settingID(@Param("email") String email);


    @Update({
            "<script>",
            "UPDATE tempuser SET",
            "<if test='name != null'> name = #{name},</if>",
            "<if test='password != null'> password = #{password},</if>",
            "<if test='email != null'> email = #{email},</if>",
            "<if test='income != null'> income = #{income},</if>",
            "<if test='userId != null'> userid = #{userId},</if>",
            "<if test='locality != null'> locality = #{locality},</if>",
            "<if test='collegeUniversity != null'> collegeuniversity = #{collegeUniversity},</if>",
            "<if test='officeName != null'> officename  = #{officeName},</if>",
            " isactive = #{isActive}",
            " WHERE userid = #{userId}",
            "</script>"
    })
    void updateUser(User user);


    @Update({
            "<script>",
            "UPDATE tempuser SET",
            "isActive = #{isActive}",
            "<if test='userId != null'>WHERE userid = #{userId}</if>",
            "</script>"
    })
    void inactivatingUser(User user);

    @Select("SELECT CONCAT(officename, ', ', collegeuniversity, ', ', locality) AS community FROM tempuser WHERE userid = #{userId}")
    List<String> findCommunitiesByUserId(String userId);


}