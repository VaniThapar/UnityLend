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


    @Select({
            "<script>",
            "SELECT borrow_request_id AS borrowRequestId, return_period_months AS returnPeriodMonths, collected_amount AS collectedAmount, requested_amount AS requestedAmount, ",
            "       monthly_interest_rate AS monthlyInterestRate, is_defaulted AS isDefaulted, default_fine AS defaultFine, default_count AS defaultCount, created_at AS createdAt, ",
            "       last_modified_at AS lastModifiedAt FROM borrow_request WHERE 1=1",
            "<if test='requestId != null'>",
            "       AND borrow_request_id = #{requestId}",
            "</if>",
            "</script>"
    })
    BorrowRequest getBorrowRequestByRequestId(@Param("requestId") String requestId);


    @Select({
            "<script>",
            "SELECT borrower_id FROM borrow_request WHERE 1=1",
            "<if test='requestId != null'>",
            "   AND borrow_request_id = #{requestId}",
            "</if>",
            "</script>"
    })
    String getUserIdByRequestId(@Param("requestId") String requestId);

    @Update({"<script>",
            "UPDATE borrow_request",
            "SET collected_amount = collected_amount + #{amount}",
            "WHERE borrow_request_id = #{borrowRequestId}",
            "<if test='borrowRequestId != null and amount != null'>",
            "</if>",
            "</script>"})
    Integer updateCollectedAmount(@Param("borrowRequestId") String borrowRequestId, @Param("amount") BigDecimal amount);

    @Select("SELECT COUNT(*) > 0 FROM borrow_request WHERE borrow_request_id = #{borrowRequestId} AND collected_amount + #{amount} <= requested_amount")
    Boolean isLendAmountValid(@Param("borrowRequestId") String borrowRequestId, @Param("amount") BigDecimal amount);

    @Update({"<script>",
            "UPDATE borrow_request SET borrow_status = #{code} WHERE borrow_request_id = #{borrowRequestId}",
            "<if test='code != null and borrowRequestId != null'>",
            "</if>",
            "</script>"})
    void updateStatusOfBorrowRequest(@Param("code") Integer code, @Param("borrowRequestId") String borrowRequestId);


}