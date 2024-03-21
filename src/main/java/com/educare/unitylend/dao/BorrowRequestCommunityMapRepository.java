package com.educare.unitylend.dao;


import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.Community;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper
@Repository
public interface BorrowRequestCommunityMapRepository {

        @Select("SELECT c.community_id as communityId, c.community_name as communityName, c.community_tag as communityTag, c.community_detail as communityDetail\n" +
                "FROM borrow_request_community_map brcm\n" +
                "JOIN community c ON brcm.community_id = c.community_id\n" +
                "WHERE brcm.borrow_request_id = #{requestId}")
        List<Community> getCommunitiesByRequestId(@Param("requestId") String requestId);

        @Select("SELECT borrow_request_id as borrowRequestId,borrower_id as borrower,return_period_days as returnPeriodDays,collected_amount as collectedAmount,requested_amount as requestedAmount,borrow_status as borrowStatus ,monthly_interest_rate as monthlyInterestRate, is_defaulted as isDefaulted, default_fine as defaultFine, default_count as  defaultCount, created_at as createdAt, last_modified_at as lastModifiedAt from borrow_request where borrow_request_id in (select borrow_request_id from borrow_request_community_map where community_id=#{communityId})")
        List<BorrowRequest> getRequestsByCommunityId(@Param("communityId") String communityId);



}
