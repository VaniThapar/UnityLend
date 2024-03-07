package com.educare.unitylend.dao;

import com.educare.unitylend.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserRepository {

    @Select("select * from tempuser")
    List<User> getAllUsers();

    @Insert("INSERT INTO tempuser (userid, password, name,email,dob,income,officename,collegeuniversity,locality) VALUES (CAST(uuid_generate_v4() AS VARCHAR), #{password}, #{name}, #{email},#{dob},#{income},#{officename},#{collegeuniversity},#{locality})")
    void createUser(User user);

    @Select("SELECT * FROM tempuser WHERE userid = #{userid}")
    User getUserForUserId(String userId);

    @Select("SELECT userid FROM tempuser WHERE email = #{email}")
    String settingID(@Param("email") String email);


    @Update("UPDATE tempuser SET name = #{name}, password = #{password}, email = #{email}, locality = #{locality}, officename = #{officename}, collegeuniversity = #{collegeuniversity}, income = #{income}, isActive = #{isActive} WHERE userid = #{userid}")
    void updateUser(User user);

    @Update("UPDATE tempuser SET  isActive = #{isActive} WHERE userid = #{userid}")
    void inactivatingUser(User user);


    @Select("SELECT CONCAT(officename, ', ', collegeuniversity, ', ', locality) AS community FROM tempuser WHERE userid = #{userId}")
    List<String> findCommunitiesByUserId(String userId);


}
