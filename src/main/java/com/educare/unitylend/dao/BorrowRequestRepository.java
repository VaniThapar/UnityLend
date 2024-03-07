package com.educare.unitylend.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BorrowRequestRepository {

    @Select("SELECT Community.CommunityName " +
            "FROM Borrow_Request " +
            "JOIN Community ON Borrow_Request.CommunityID = Community.CommunityId " +
            "WHERE Borrow_Request.BorrowerID = #{userId}")
    List<String> getBorrowRequestesByUserInCommunities(@Param("userId") String userId);


}
