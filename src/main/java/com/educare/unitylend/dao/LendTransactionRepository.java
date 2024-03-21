package com.educare.unitylend.dao;

import com.educare.unitylend.model.LendTransaction;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LendTransactionRepository {

    @Select("SELECT lend_transaction_id AS lendTransactionId, created_at AS createdAt, last_updated_at AS lastUpdatedAt " +
            "FROM lend_transaction " +
            "WHERE lend_transaction_id = #{lendTransactionId}")
    LendTransaction getLendTransactionById(@Param("lendTransactionId") String lendTransactionId);

    @Select("SELECT lend_transaction_id AS lendTransactionId, created_at AS createdAt, last_updated_at AS lastUpdatedAt " +
            "FROM lend_transaction " +
            "WHERE transaction_id IN (SELECT transaction_id FROM transaction WHERE sender_id = #{userId})")
    List<LendTransaction> getLendTransactionsByUserId(@Param("userId") String userId);

    @Select("SELECT borrow_request_id FROM lend_transaction WHERE lend_transaction_id = #{lendTransactionId}")
    String getBorrowRequestId(@Param("lendTransactionId") String lendTransactionId);

    @Select("SELECT transaction_id FROM lend_transaction WHERE lend_transaction_id = #{lendTransactionId}")
    String getTransactionId(@Param("lendTransactionId") String lendTransactionId);

    @Select("SELECT lend_transaction_id AS lendTransactionId, created_at AS createdAt, last_updated_at AS lastUpdatedAt FROM lend_transaction")
    List<LendTransaction> getAllLendTransactions();
    
    @Insert({"<script>",
            "INSERT INTO lend_transaction (lend_transaction_id, transaction_id, borrow_request_id)",
            "VALUES",
            "<if test='transactionId != null and borrowRequestId != null'>",
            "(uuid_generate_v4(), #{transactionId}, #{borrowRequestId})",
            "</if>",
            "</script>"})
    Integer insertLendTransaction(@Param("transactionId") String transactionId, @Param("borrowRequestId") String borrowRequestId);

    @Select("Select count(*)>0 from lend_transaction where transaction_id in (select transaction_id from transaction where sender_id=#{lenderId}) " +
            "AND borrow_request_id=#{borrowRequestId}")
    Boolean hasLent(@Param("lenderId") String lenderId,@Param("borrowRequestId") String borrowRequestId);
}
