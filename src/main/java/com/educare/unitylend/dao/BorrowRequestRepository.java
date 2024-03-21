package com.educare.unitylend.dao;

import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Mapper
@Repository
public interface BorrowRequestRepository {

    @Select("SELECT borrow_request_id as borrowRequestId,borrower_id as borrower,return_period_days as returnPeriodDays,collected_amount as collectedAmount,requested_amount as requestedAmount,borrow_status as borrowStatus ,monthly_interest_rate as monthlyInterestRate, is_defaulted as isDefaulted, default_fine as defaultFine, default_count as  defaultCount, created_at as createdAt, last_modified_at as lastModifiedAt FROM borrow_request")
    List<BorrowRequest> getAllBorrowRequests();

    @Select("SELECT borrow_request_id as borrowRequestId,borrower_id as borrower,return_period_days as returnPeriodDays,collected_amount as collectedAmount,requested_amount as requestedAmount,borrow_status as borrowStatus ,monthly_interest_rate as monthlyInterestRate, is_defaulted as isDefaulted, default_fine as defaultFine, default_count as  defaultCount, created_at as createdAt, last_modified_at as lastModifiedAt FROM borrow_request where borrower_id=#{userId}")
    List<BorrowRequest> getBorrowRequestsByUserId(@Param("userId")String userId);

    @Select("SELECT borrower_id from borrow_request where borrow_request_id=#{borrowRequestId}")
    String getBorrowerIdUsingRequestId(@Param("borrowRequestId") String borrowRequestId);

    @Select("SELECT borrow_status from borrow_request where borrow_request_id=#{borrowRequestId}")
    Integer getStatusCodeForReqId(@Param("borrowRequestId") String borrowRequestId);

    @Select("SELECT borrow_request_id AS borrowRequestId, return_period_days AS returnPeriodDays, collected_amount AS collectedAmount, requested_amount AS requestedAmount, " +
            "monthly_interest_rate AS monthlyInterestRate, is_defaulted AS isDefaulted, default_fine AS defaultFine, default_count AS defaultCount, created_at AS createdAt, " +
            "last_modified_at AS lastModifiedAt FROM borrow_request WHERE borrow_request_id = #{requestId}")
    BorrowRequest getBorrowRequestByRequestId(@Param("requestId") String requestId);

    @Select("SELECT borrower_id FROM borrow_request WHERE borrow_request_id = #{requestId}")
    String getUserIdByRequestId(@Param("requestId") String requestId);

    @Update("UPDATE borrow_request SET collected_amount = collected_amount + #{amount} WHERE borrow_request_id = #{borrowRequestId}")
    Integer updateCollectedAmount(@Param("borrowRequestId") String borrowRequestId, @Param("amount") BigDecimal amount);

    @Select("SELECT COUNT(*) > 0 FROM borrow_request WHERE borrow_request_id = #{borrowRequestId} AND collected_amount + #{amount} <= requested_amount")
    Boolean isLendAmountValid(@Param("borrowRequestId") String borrowRequestId, @Param("amount") BigDecimal amount);

    @Update("UPDATE borrow_request SET borrow_status=#{code} where borrow_request_id=#{borrowRequestId}")
    void updateStatusOfBorrowRequest(Integer code, @Param("borrowRequestId") String borrowRequestId);

}