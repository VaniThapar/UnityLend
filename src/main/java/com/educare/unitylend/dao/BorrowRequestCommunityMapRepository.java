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

        @Select({
                "<script>",
                "SELECT c.community_id as communityId,",
                "       c.community_name as communityName,",
                "       c.community_tag as communityTag,",
                "       c.community_detail as communityDetail",
                "FROM borrow_request_community_map brcm",
                "JOIN community c ON brcm.community_id = c.community_id",
                "WHERE 1=1",
                "<if test='requestId != null'>",
                "   AND brcm.borrow_request_id = #{requestId}",
                "</if>",
                "</script>"
        })
        List<Community> getCommunitiesByRequestId(@Param("requestId") String requestId);

        @Select({
                "<script>",
                "SELECT borrow_request_id as borrowRequestId,",
                "       borrower_id as borrower,",
                "       return_period_months as returnPeriodMonths,",
                "       collected_amount as collectedAmount,",
                "       requested_amount as requestedAmount,",
                "       borrow_status as borrowStatus ,",
                "       monthly_interest_rate as monthlyInterestRate,",
                "       is_defaulted as isDefaulted,",
                "       default_fine as defaultFine,",
                "       default_count as defaultCount,",
                "       created_at as createdAt,",
                "       last_modified_at as lastModifiedAt",
                "FROM borrow_request",
                "WHERE borrow_request_id IN (",
                "       SELECT borrow_request_id",
                "       FROM borrow_request_community_map",
                "       WHERE community_id = #{communityId}",
                ")",
                "<if test='communityId != null'>",
                "   AND borrow_request_id IN (",
                "       SELECT borrow_request_id",
                "       FROM borrow_request_community_map",
                "       WHERE community_id = #{communityId}",
                "   )",
                "</if>",
                "</script>"
        })
        List<BorrowRequest> getRequestsByCommunityId(@Param("communityId") String communityId);

        @Insert({
                "<script>",
                "<if test='requestId != null and communityIds != null'>",
                "INSERT INTO borrow_request_community_map (borrow_request_id, community_id) VALUES ",
                "<foreach item='communityId' collection='communityIds' separator=','>",
                "(#{requestId}, #{communityId})",
                "</foreach>",
                "</if>",
                "</script>"
        })
        void createBorrowRequestCommunityMapping(
                @Param("requestId") String requestId,
                @Param("communityIds") List<String> communityIds
        );
}
