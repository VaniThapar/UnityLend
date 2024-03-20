package com.educare.unitylend.dao;

import com.educare.unitylend.model.BorrowRequest;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BorrowRequestRepository {
    @Insert("INSERT INTO borrow_request (borrow_request_id, borrower_id, return_period_month, monthly_interest_rate, borrow_status, requested_amount, collected_amount, default_fine, default_count, is_defaulted) " +
            "VALUES (uuid_generate_v4(), #{userId}, #{borrowRequest.returnPeriodMonth}, #{borrowRequest.monthlyInterestRate}, 1 , #{borrowRequest.requestedAmount}, 0, COALESCE(#{borrowRequest.defaultFine}, 5), 0, 0)")
    @Options(useGeneratedKeys = true, keyProperty = "borrowRequest.borrowRequestId")
    Boolean createBorrowRequest(@Param("borrowRequest") BorrowRequest borrowRequest, @Param("userId") String userId);

    @Select("SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END " +
            "FROM borrow_request " +
            "WHERE borrower_id = #{userId} AND borrow_status = 1")
    Boolean isRequestPending(@Param("userId") String userId);

    @Select("SELECT CASE WHEN password = #{passwordProvided} THEN TRUE ELSE FALSE END AS password_matched " +
            "FROM user_detail WHERE user_id = #{userId}")
    Boolean checkPassword(@Param("passwordProvided") String passwordProvided, @Param("userId") String userId);


}