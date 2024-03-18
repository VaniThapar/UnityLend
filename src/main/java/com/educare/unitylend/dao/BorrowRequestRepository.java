package com.educare.unitylend.dao;

import com.educare.unitylend.model.BorrowRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BorrowRequestRepository {
    @Insert("INSERT INTO borrow_request (borrow_request_id, borrower_id, return_period_days, monthly_interest_rate, borrow_status, requested_amount, collected_amount, default_fine, default_count) " +
            "VALUES (uuid_generate_v4(), #{userId}, #{borrowRequest.returnPeriodDays}, #{borrowRequest.monthlyInterestRate}, #{status}, #{borrowRequest.requestedAmount}, #{borrowRequest.collectedAmount}, #{borrowRequest.defaultFine}, #{borrowRequest.defaultCount})")
    @Options(useGeneratedKeys = true, keyProperty = "borrowRequest.borrowRequestId")
    Boolean createBorrowRequest(@Param("borrowRequest") BorrowRequest borrowRequest, @Param("userId") String userId, @Param("status") Integer status);

}