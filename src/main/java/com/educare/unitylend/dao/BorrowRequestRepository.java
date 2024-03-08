package com.educare.unitylend.dao;

import com.educare.unitylend.model.BorrowRequest;
import org.apache.ibatis.annotations.Insert;
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
            "WHERE Borrow_Request.BorrowerId = #{userId}")
    List<String> getBorrowRequestByUserId(@Param("userId") String userId);

    @Insert("INSERT INTO Borrow_Request (RequestID, BorrowerID, CommunityID, ReturnPeriod, Status, TargetAmount) " +
            "VALUES (uuid_generate_v4(), #{borrowRequest.borrowerId}, #{borrowRequest.community.communityid}, CAST (#{borrowRequest.returnPeriod} AS INTEGER), #{borrowRequest.status}, #{borrowRequest.targetAmount})")
    void createBorrowRequest(@Param("borrowRequest") BorrowRequest borrowRequest);

}
