package com.educare.unitylend.dao;

import com.educare.unitylend.model.BorrowRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Mapper
@Repository
public interface BorrowRequestRepository {

    @Select("SELECT borrow_request_id as borrowRequestId,return_period_days as returnPeriodDays, collected_amount as collectedAmount,requested_amount as requestedAmount, monthly_interest_rate as monthlyInterestRate, " +
            "is_defaulted as isDefaulted, default_fine as defaultFine, default_count as defaultCount,created_at " +
            "as createdAt, last_modified_at as lastModifiedAt FROM borrow_request where borrow_request_id=#{requestId}")
    BorrowRequest getBorrowRequestByRequestId(@Param("requestId") String requestId);

    @Select("Select borrower_id from borrow_request where borrow_request_id=#{requestId}")
    String getUserIdByRequestId(@Param("requestId") String requestId);

    @Update("Update borrow_request set collected_amount=collected_amount+#{amount} where borrow_request_id=#{borrowRequestId}")
    Integer updateCollectedAmount(@Param("borrowRequestId") String borrowRequestId, @Param("amount") BigDecimal amount);

    @Select("Select count(*)>0 from borrow_request where borrow_request_id=#{borrowRequestId} AND " +
            "collected_amount+#{amount}<=requested_amount")
    Boolean isLendAmountValid(@Param("borrowRequestId") String borrowRequestId, @Param("amount") BigDecimal amount);
}