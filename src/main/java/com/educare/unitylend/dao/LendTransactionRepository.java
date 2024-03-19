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


    @Select("Select lend_transaction_id as lendTransactionId,created_at as createdAt,last_updated_at as lastUpdatedAt from lend_transaction " +
            "where lend_transaction_id = #{lendTransactionId}")
    LendTransaction getLendTransactionById(@Param("lendTransactionId") String lendTransactionId);

    @Select("Select lend_transaction_id as lendTransactionId,created_at as createdAt,last_updated_at as lastUpdatedAt from lend_transaction " +
            "where transaction_id in (select transaction_id from transaction where sender_id=#{userId})")
    List<LendTransaction> getLendTransactionsByUserId(@Param("userId") String userId);


//    @Select("Select lend_transaction as lendTransactionId,created_at as createdAt,last_updated_at as lastUpdatedAt from lend_transaction " +
//            "where transaction_id in (select transaction_id from transaction where sender_id=#{payerId} AND receiver_id=#{payeeId})")
//    List<LendTransaction> getLendTransactionsBetweenPayerAndPayee(@Param("payerId") String payerId, @Param("payeeId") String payeeId);

    @Select("Select borrow_request_id from lend_transaction where lend_transaction_id=#{lendTransactionId}")
    String getBorrowRequestId(@Param("lendTransactionId") String lendTransactionId);

    @Select("Select transaction_id from lend_transaction where lend_transaction_id=#{lendTransactionId}")
    String getTransactionId(@Param("lendTransactionId") String lendTransactionId);

    @Select("Select lend_transaction_id as lendTransactionId,created_at as createdAt,last_updated_at as lastUpdatedAt from lend_transaction")
    List<LendTransaction> getAllLendTransactions();

    @Insert("Insert into lend_transaction(lend_transaction_id,transaction_id,borrow_request_id,created_at,last_updated_at)" +
            " values (uuid_generate_v4(),#{transactionId},#{borrowRequestId},NOW(),NOW())")
    Integer insertLendTransaction(@Param("transactionId") String transactionId,@Param("borrowRequestId") String borrowRequestId);
}
