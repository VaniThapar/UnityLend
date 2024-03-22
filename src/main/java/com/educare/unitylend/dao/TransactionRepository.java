package com.educare.unitylend.dao;

import com.educare.unitylend.model.Transaction;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Mapper
@Repository
public interface TransactionRepository {

    @Select("SELECT transaction_id AS transactionId, amount, transaction_time AS transactionTime, last_updated_time AS lastUpdatedTime " +
            "FROM transaction WHERE transaction_id = #{transactionId}")
    Transaction getTransactionByTransactionId(@Param("transactionId") String transactionId);

    @Select("SELECT sender_id FROM transaction WHERE transaction_id = #{transactionId}")
    String getSenderIdByTransactionId(@Param("transactionId") String transactionId);

    @Select("SELECT receiver_id FROM transaction WHERE transaction_id = #{transactionId}")
    String getReceiverIdByTransactionId(@Param("transactionId") String transactionId);

    @Select("SELECT transaction_status FROM transaction WHERE transaction_id = #{transactionId}")
    Integer getStatusCodeByTransactionId(@Param("transactionId") String transactionId);

    @Insert({"<script>",
            "INSERT INTO transaction (transaction_id, receiver_id, sender_id, amount, transaction_status, last_updated_time, transaction_time)",
            "VALUES",
            "<if test='senderId != null and receiverId != null and amount != null'>",
            "(uuid_generate_v4(), #{receiverId}, #{senderId}, #{amount},",
            "(SELECT status_code FROM status WHERE status_name = 'Completed'), NOW(), NOW())",
            "</if>",
            "</script>"})
    int insertTransaction(@Param("senderId") String senderId, @Param("receiverId") String receiverId, @Param("amount") BigDecimal amount);

    @Select("SELECT transaction_id FROM transaction WHERE transaction_time = (SELECT MAX(transaction_time) FROM transaction)")
    String getLatestTransactionId();

    @Select("SELECT amount FROM transaction WHERE transaction_id IN (SELECT transaction_id FROM lend_transaction WHERE " +
            "borrow_request_id = #{borrowRequestId}) AND sender_id = #{lenderId}")
    BigDecimal getLentAmountByLenderAndRequestId(@Param("lenderId") String lenderId, @Param("borrowRequestId") String borrowRequestId);

    @Select("SELECT transaction_id AS transactionId, amount, transaction_time AS transactionTime, last_updated_time AS lastUpdatedTime " +
            "FROM transaction WHERE sender_id = #{senderId}")
    List<Transaction> getDebitTransactionsForUser(@Param("senderId") String senderId);

    @Select("SELECT transaction_id AS transactionId, amount, transaction_time AS transactionTime, last_updated_time AS lastUpdatedTime " +
            "FROM transaction WHERE sender_id = #{senderId} AND receiver_id=#{receiverId}")
    List<Transaction> getTransactions(@Param("senderId") String senderId, @Param("receiverId") String receiverId);

    @Select("SELECT transaction_id AS transactionId, amount, transaction_time AS transactionTime, last_updated_time AS lastUpdatedTime " +
            "FROM transaction WHERE sender_id = #{senderId} AND DATE(transaction_time) between #{startDate} and #{endDate}")
    List<Transaction> getDebitTransactionByDateRangeForUser(@Param("senderId") String senderId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Select("SELECT transaction_id AS transactionId, amount, transaction_time AS transactionTime, last_updated_time AS lastUpdatedTime " +
            "FROM transaction")
    List<Transaction> getAllTransactions();

    @Select("select sender_id from transaction where transaction_id in (Select transaction_id from lend_transaction " +
            "where borrow_request_id=#{borrowRequestId})")
    List<String> getLenderIdForRequestId(@Param("borrowRequestId") String borrowRequestId);
}
