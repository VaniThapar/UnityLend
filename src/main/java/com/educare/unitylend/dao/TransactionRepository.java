package com.educare.unitylend.dao;

import com.educare.unitylend.model.Transaction;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Mapper
@Repository
public interface TransactionRepository {

    @Select("Select transaction_id as transactionId,amount,transaction_time as transactionTime, last_updated_time as lastUpdatedTime " +
            "from transaction where transaction_id=#{transactionId}")
    Transaction getTransactionByTransactionId(@Param("transactionId") String transactionId);

    @Select("Select sender_id from transaction where transaction_id=#{transactionId}")
    String getSenderIdByTransactionId(@Param("transactionId") String transactionId);


    @Select("select receiver_id from transaction where transaction_id=#{transactionId}")
    String getReceiverIdByTransactionId(@Param("transactionId") String transactionId);

    @Select("Select transaction_status from transaction where transaction_id=#{transactionId}")
    Integer getStatusCodeByTransactionId(@Param("transactionId") String transactionId);

    @Insert("Insert into transaction(transaction_id,receiver_id,sender_id,amount,transaction_status,last_updated_time," +
            "transaction_time) values (uuid_generate_v4(),#{receiverId},#{senderId},#{amount},3,NOW(),NOW())")
    int insertTransaction(@Param("senderId") String senderId, @Param("receiverId") String receiverId, @Param("amount") BigDecimal amount);

    @Select("Select transaction_id from transaction where transaction_time = (select max(transaction_time) from transaction)")
    String getLatestTransactionId();
}
