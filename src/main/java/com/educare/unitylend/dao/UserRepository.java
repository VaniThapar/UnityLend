package com.educare.unitylend.dao;

import com.educare.unitylend.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserRepository {

    @Insert("Insert into user_detail(user_id,password,name,email,contact_no,dob,income,community_details)" +
            "values(uuid_generate_v4(), #{password}, #{name}, #{email}, #{contactNo}, #{dob}, #{income}, #{communityDetailsJson}::json)")
    @Options(useGeneratedKeys = true,keyProperty = "userId", keyColumn = "user_id")
    int createUser(User user);


    @Update("Update user_detail set password=#{password},name=#{name},email=#{email},contact_no=#{contactNo},dob=#{dob}," +
            "income=#{income},community_details=#{communityDetailsJson}::json where user_id=#{userId}")
    int updateUserDetails(User user);

    @Select("Select user_id as userId,password,name,email,contact_no as contactNo,dob,income,community_details as communityDetailsJson " +
            "from user_detail where user_id=#{userId}")
    User getUserForUserId(@Param("userId") String userId);


    @Delete("Delete from user_detail where user_id=#{userId}")
    int deleteUser(@Param("userId") String userId);

    @Select("Select user_id as userId,password,name,email,contact_no as contactNo,dob,income,community_details as communityDetailsJson from user_detail")
    List<User> getAllUsers();


    @Select("Select user_id as userId,password,name,email,contact_no as contactNo,dob,income,community_details as communityDetailsJson " +
            "from user_detail where user_id = (select user_id from wallet where wallet_id=#{walletId})")
    User getUserByWalletId(@Param("walletId") String walletId);


}