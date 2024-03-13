package com.educare.unitylend.dao;

import com.educare.unitylend.model.BorrowRequest;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BorrowRequestRepository {

    @Insert("INSERT INTO borrowrequest(requestid, returnperiod,timestamp,requestedamount, monthlyinterestrate) VALUES (uuid_generate_v4(),#{returnPeriod},NOW(),#{requestedAmount},#{monthlyInterestRate})")
    @Options(useGeneratedKeys = true, keyProperty = "requestId")
    void createBorrowRequest(BorrowRequest borrowRequest);

    @Select("SELECT requestid as requestId,returnperiod as returnPeriod, collectedamount as collectedAmount,requestedamount as requestedAmount, monthlyinterestrate as monthlyInterestRate FROM borrowrequest")
    List<BorrowRequest> getAllBorrowRequests();

    @Select("SELECT requestid as requestId,returnperiod as returnPeriod, collectedamount as collectedAmount,requestedamount as requestedAmount, monthlyinterestrate as monthlyInterestRate FROM borrowrequest where borrowerid=#{userId}")
    List<BorrowRequest> getBorrowRequestsByUserId(String userId);
}