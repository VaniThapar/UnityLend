package com.educare.unitylend.dao;

import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.User;
import org.apache.ibatis.annotations.*;
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


    @Update({"<script>",
            "UPDATE borrow_request",
            "SET collected_amount = collected_amount + #{amount}",
            "WHERE borrow_request_id = #{borrowRequestId}",
            "<if test='borrowRequestId != null and amount != null'>",
            "</if>",
            "</script>"})
    Integer updateCollectedAmount(@Param("borrowRequestId") String borrowRequestId, @Param("amount") BigDecimal amount);


    @Select("SELECT borrow_request_id AS borrowRequestId, return_period_months AS returnPeriodMonths, collected_amount AS collectedAmount, requested_amount AS requestedAmount, " +
            "monthly_interest_rate AS monthlyInterestRate, is_defaulted AS isDefaulted, default_fine AS defaultFine, default_count AS defaultCount, created_at AS createdAt, " +
            "last_modified_at AS lastModifiedAt FROM borrow_request WHERE borrow_request_id = #{borrowRequestId}")
    BorrowRequest getBorrowRequestByRequestId(@Param("borrowRequestId") String borrowRequestId);

    @Select("SELECT borrower_id FROM borrow_request WHERE borrow_request_id = #{borrowRequestId}")
    String getUserIdByRequestId(@Param("borrowRequestId") String borrowRequestId);


    @Insert("INSERT INTO borrow_request (borrow_request_id, borrower_id, return_period_months, monthly_interest_rate, borrow_status, requested_amount, collected_amount, default_fine, default_count, is_defaulted) " +
            "VALUES (uuid_generate_v4(), #{userId}, #{borrowRequest.returnPeriodMonths}, #{borrowRequest.monthlyInterestRate}, 1 , #{borrowRequest.requestedAmount}, 0, COALESCE(#{borrowRequest.defaultFine}, 5), 0, false)")
    @Options(useGeneratedKeys = true, keyProperty = "borrowRequest.borrowRequestId")
    Boolean createBorrowRequest(@Param("borrowRequest") BorrowRequest borrowRequest, @Param("userId") String userId);

    @Select("SELECT COUNT(*) > 0 FROM borrow_request WHERE borrow_request_id = #{borrowRequestId} AND collected_amount + #{amount} <= requested_amount")
    Boolean isLendAmountValid(@Param("borrowRequestId") String borrowRequestId, @Param("amount") BigDecimal amount);



    @Select("SELECT " +
            "borrow_request.borrow_request_id AS borrowRequestId," +
            "borrow_request.return_period_days AS returnPeriodMonths," +
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


    @Select("SELECT borrow_request_id as borrowRequestId,borrower_id as borrower,return_period_months as returnPeriodMonths,collected_amount as collectedAmount,requested_amount as requestedAmount,borrow_status as borrowStatus ,monthly_interest_rate as monthlyInterestRate, is_defaulted as isDefaulted, default_fine as defaultFine, default_count as  defaultCount, created_at as createdAt, last_modified_at as lastModifiedAt FROM borrow_request")
    List<BorrowRequest> getAllBorrowRequests();

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
            "WHERE borrower_id = #{userId}",
            "<if test='userId != null'>",
            "   AND borrower_id = #{userId}",
            "</if>",
            "</script>"
    })
    List<BorrowRequest> getBorrowRequestsByUserId(@Param("userId") String userId);

    @Select({
            "<script>",
            "SELECT borrower_id",
            "FROM borrow_request",
            "WHERE borrow_request_id = #{borrowRequestId}",
            "<if test='borrowRequestId != null'>",
            "   AND borrow_request_id = #{borrowRequestId}",
            "</if>",
            "</script>"
    })
    String getBorrowerIdUsingRequestId(@Param("borrowRequestId") String borrowRequestId);

    @Select({
            "<script>",
            "SELECT borrow_status",
            "FROM borrow_request",
            "WHERE borrow_request_id = #{borrowRequestId}",
            "<if test='borrowRequestId != null'>",
            "   AND borrow_request_id = #{borrowRequestId}",
            "</if>",
            "</script>"
    })
    Integer getStatusCodeForReqId(@Param("borrowRequestId") String borrowRequestId);




    @Select("SELECT CASE WHEN password = #{passwordProvided} THEN TRUE ELSE FALSE END AS password_matched " +
            "FROM user_detail WHERE user_id = #{userId}")
    Boolean checkPassword(@Param("passwordProvided") String passwordProvided, @Param("userId") String userId);


    @Select("SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END " +
            "FROM borrow_request " +
            "WHERE borrower_id = #{userId} AND borrow_status = 1")
    Boolean isRequestPending(@Param("userId") String userId);

    @Update({"<script>",
            "UPDATE borrow_request SET borrow_status = #{code} WHERE borrow_request_id = #{borrowRequestId}",
            "<if test='code != null and borrowRequestId != null'>",
            "</if>",
            "</script>"})
    void updateStatusOfBorrowRequest(@Param("code") Integer code, @Param("borrowRequestId") String borrowRequestId);
}