package com.educare.unitylend.dao;

import com.educare.unitylend.model.BorrowRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Mapper
@Repository
public interface BorrowRequestRepository {

        @Select("SELECT " +
                "borrow_request.borrow_request_id AS borrowRequestId," +
                "borrow_request.return_period_days AS returnPeriodDays," +
                "borrow_request.monthly_interest_rate AS monthlyInterestRate," +
                "borrow_request.borrow_status AS borrowStatus," +
                "borrow_request.requested_amount AS requestedAmount," +
                "borrow_request.collected_amount AS collectedAmount," +
                "borrow_request.is_defaulted AS isDefaulted," +
                "borrow_request.default_fine AS defaultFine," +
                "borrow_request.default_count AS defaultCount," +
                "borrow_request.created_at AS createdAt," +
                "borrow_request.last_modified_at AS lastModifiedAt " +
                "FROM borrow_request INNER JOIN borrow_request_community_map using(borrow_request_id) " +
                "WHERE borrow_request.requested_amount <= #{maxAmount}" +
                " AND " +
                "borrow_request_community_map.community_id = #{communityId}"
                )
        List<BorrowRequest> findByRequestedAmountLessThanAndCommunityIdsContaining(
                @Param("maxAmount") BigDecimal maxAmount,
                @Param("communityId") String communityId
        );

        @Select("SELECT " +
                "borrow_request.borrow_request_id AS borrowRequestId," +
                "borrow_request.return_period_days AS returnPeriodDays," +
                "borrow_request.monthly_interest_rate AS monthlyInterestRate," +
                "borrow_request.borrow_status AS borrowStatus," +
                "borrow_request.requested_amount AS requestedAmount," +
                "borrow_request.collected_amount AS collectedAmount," +
                "borrow_request.is_defaulted AS isDefaulted," +
                "borrow_request.default_fine AS defaultFine," +
                "borrow_request.default_count AS defaultCount," +
                "borrow_request.created_at AS createdAt," +
                "borrow_request.last_modified_at AS lastModifiedAt " +
                "FROM borrow_request INNER JOIN borrow_request_community_map using(borrow_request_id) " +
                "WHERE borrow_request.requested_amount >= #{minAmount}" +
                " AND " +
                "borrow_request_community_map.community_id = #{communityId}"
        )
        List<BorrowRequest> findByRequestedAmountGreaterThanEqualAndCommunityIdsContaining(
                @Param("minAmount") BigDecimal minAmount,
                @Param("communityId") String communityId);

        @Select("SELECT " +
                "borrow_request.borrow_request_id AS borrowRequestId," +
                "borrow_request.return_period_days AS returnPeriodDays," +
                "borrow_request.monthly_interest_rate AS monthlyInterestRate," +
                "borrow_request.borrow_status AS borrowStatus," +
                "borrow_request.requested_amount AS requestedAmount," +
                "borrow_request.collected_amount AS collectedAmount," +
                "borrow_request.is_defaulted AS isDefaulted," +
                "borrow_request.default_fine AS defaultFine," +
                "borrow_request.default_count AS defaultCount," +
                "borrow_request.created_at AS createdAt," +
                "borrow_request.last_modified_at AS lastModifiedAt " +
                "FROM borrow_request INNER JOIN borrow_request_community_map using(borrow_request_id) " +
                "WHERE borrow_request.requested_amount <= #{maxAmount}" +
                " AND " + "borrow_request.requested_amount >= #{minAmount}" +
                " AND " +
                "borrow_request_community_map.community_id = #{communityId}"
        )
        List<BorrowRequest> findByRequestedAmountBetweenAndCommunityIdsContaining(
                @Param("minAmount") BigDecimal minAmount,
                @Param("maxAmount") BigDecimal maxAmount,
                @Param("communityId") String communityId);

}