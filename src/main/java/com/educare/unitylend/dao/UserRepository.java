package com.educare.unitylend.dao;

import com.educare.unitylend.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserRepository {

    @Insert({"<script>",
            "INSERT INTO user_detail (user_id",
            "<if test='password != null'>, password</if>",
            "<if test='name != null'>, name</if>",
            "<if test='email != null'>, email</if>",
            "<if test='contactNo != null'>, contact_no</if>",
            "<if test='dob != null'>, dob</if>",
            "<if test='income != null'>, income</if>",
            "<if test='communityDetailsJson != null'>, community_details</if>",
            ") VALUES (uuid_generate_v4()",
            "<if test='password != null'>, #{password}</if>",
            "<if test='name != null'>, #{name}</if>",
            "<if test='email != null'>, #{email}</if>",
            "<if test='contactNo != null'>, #{contactNo}</if>",
            "<if test='dob != null'>, #{dob}</if>",
            "<if test='income != null'>, #{income}</if>",
            "<if test='communityDetailsJson != null'>, #{communityDetailsJson}::json</if>",
            ")",
            "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    int createUser(User user);

    @Update({"<script>",
            "UPDATE user_detail SET",
            "<if test='password != null'> password=#{password},</if>",
            "<if test='name != null'> name=#{name},</if>",
            "<if test='email != null'> email=#{email},</if>",
            "<if test='contactNo != null'> contact_no=#{contactNo},</if>",
            "<if test='dob != null'> dob=#{dob},</if>",
            "<if test='income != null'> income=#{income},</if>",
            "<if test='communityDetailsJson != null'> community_details=#{communityDetailsJson}::json </if>",
            "WHERE user_id=#{userId}",
            "</script>"})
    int updateUserDetails(User user);


    @Select("SELECT user_id AS userId, password, name, email, contact_no AS contactNo, dob, income, " +
            "community_details AS communityDetailsJson FROM user_detail WHERE user_id=#{userId}")
    User getUserForUserId(@Param("userId") String userId);


    @Delete("DELETE FROM user_detail WHERE user_id=#{userId}")
    int deleteUser(@Param("userId") String userId);

    @Select("SELECT user_id AS userId, password, name, email, contact_no AS contactNo, dob, income, " +
            "community_details AS communityDetailsJson FROM user_detail")
    List<User> getAllUsers();


    @Select("SELECT user_id AS userId, password, name, email, contact_no AS contactNo, dob, income, " +
            "community_details AS communityDetailsJson FROM user_detail WHERE user_id = " +
            "(SELECT user_id FROM wallet WHERE wallet_id=#{walletId})")
    User getUserByWalletId(@Param("walletId") String walletId);
}