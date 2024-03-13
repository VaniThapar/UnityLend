package com.educare.unitylend.dao;

import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.Community;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BorrowRequestCommunityRepository {

    @Select("select communityid as communityId,communityname as communityName,commontag from community where communityid in (select communityid from borrowrequestcommunity where requestid=#{requestId})")
    List<Community> getCommunitiesByRequestId(@Param("requestId") String requestId);

    @Select("SELECT requestid as requestId,returnperiod as returnPeriod, collectedamount as collectedAmount,requestedamount as requestedAmount, monthlyinterestrate as monthlyInterestRate, timestamp as timestamp from borrowrequest where requestid in (select requestid from borrowrequestcommunity where communityid=#{communityId})")
    List<BorrowRequest> getRequestsByCommunityId(@Param("communityId") String communityId);

    @Insert("insert into borrowrequestcommunity(requestid ,communityid) values (#{requestId},#{communityId})")
    void createBorrowRequestCommunityMapping(@Param("requestId") String requestId, @Param("communityId") String communityId);
}
