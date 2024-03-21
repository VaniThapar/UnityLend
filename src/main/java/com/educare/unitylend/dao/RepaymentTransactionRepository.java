package com.educare.unitylend.dao;

import com.educare.unitylend.model.RepaymentTransaction;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RepaymentTransactionRepository {

    @Insert({"<script>",
            "INSERT INTO repayment_transaction (repayment_transaction_id, transaction_id, borrow_request_id)",
            "VALUES",
            "<if test='transactionId != null and borrowRequestId != null'>",
            "(uuid_generate_v4(), #{transactionId}, #{borrowRequestId})",
            "</if>",
            "</script>"})
    Integer insertRepaymentTransaction(@Param("transactionId") String transactionId, @Param("borrowRequestId") String borrowRequestId);

    @Select("SELECT repayment_transaction_id AS repaymentTransactionId, created_at AS createdAt, last_updated_at AS lastUpdatedAt " +
            "FROM repayment_transaction " +
            "WHERE repayment_transaction_id = #{repaymentTransactionId}")
    RepaymentTransaction getRepaymentTransactionById(@Param("repaymentTransactionId") String repaymentTransactionId);

    @Select("SELECT repayment_transaction_id AS repaymentTransactionId, created_at AS createdAt, last_updated_at AS lastUpdatedAt FROM repayment_transaction")
    List<RepaymentTransaction> getAllRepaymentTransactions();

    @Select("SELECT repayment_transaction_id AS repaymentTransactionId, created_at AS createdAt, last_updated_at AS lastUpdatedAt " +
            "FROM repayment_transaction " +
            "WHERE transaction_id IN (SELECT transaction_id FROM transaction WHERE sender_id = #{userId})")
    List<RepaymentTransaction> getRepaymentTransactionsByUserId(@Param("userId") String userId);

    @Select("SELECT borrow_request_id FROM repayment_transaction WHERE repayment_transaction_id = #{repaymentTransactionId}")
    String getBorrowRequestId(@Param("repaymentTransactionId") String repaymentTransactionId);

    @Select("SELECT transaction_id FROM repayment_transaction WHERE repayment_transaction_id = #{repaymentTransactionId}")
    String getTransactionId(@Param("repaymentTransactionId") String repaymentTransactionId);

}

