package com.educare.unitylend.dao;

import com.educare.unitylend.model.EMI;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Mapper
@Repository
public interface EMIRepository {
    @Select("SELECT emi_id AS emiId, emi_no AS emiNo, emi_amount AS emiAmount, emi_date AS emiDate, " +
            "created_date AS createdDate, modified_date AS modifiedDate " +
            "FROM borrow_request_emi " +
            "WHERE borrow_request_id = #{borrowRequestId} AND emi_status IN " +
            "(SELECT status_code FROM status WHERE status_name ='Future')")
    List<EMI> getScheduledEMIDetailsForBorrowRequest(@Param("borrowRequestId") String borrowRequestId);


    @Select("SELECT emi_id AS emiId, emi_no AS emiNo, emi_amount AS emiAmount, emi_date AS emiDate, " +
            "created_date AS createdDate, modified_date AS modifiedDate " +
            "FROM borrow_request_emi " +
            "WHERE borrow_request_id = #{borrowRequestId} AND emi_status IN " +
            "(SELECT status_code FROM status WHERE status_name ='Completed')")
    List<EMI> getPaidEMIDetailsForBorrowRequest(@Param("borrowRequestId") String borrowRequestId);

    @Select("SELECT emi_id AS emiId, emi_no AS emiNo, emi_amount AS emiAmount, emi_date AS emiDate, " +
            "created_date AS createdDate, modified_date AS modifiedDate " +
            "FROM borrow_request_emi " +
            "WHERE borrow_request_id = #{borrowRequestId} AND emi_status IN " +
            "(SELECT status_code FROM status WHERE status_name ='Defaulted')")
    List<EMI> getDefaultEMIs(@Param("borrowRequestId") String borrowRequestId);

    @Update("UPDATE borrow_request_emi " +
            "SET emi_status = (SELECT status_code FROM status WHERE status_name = 'Defaulted'), " +
            "emi_amount = emi_amount * (1 + (#{defaultFine}/100)) " +
            "WHERE borrow_request_id = #{borrowRequestId} " +
            "AND emi_status = (SELECT status_code FROM status WHERE status_name = 'Future') " +
            "AND emi_date < CURRENT_DATE")
    void updateOverdueEMIsToDefaultedStatus(@Param("borrowRequestId") String borrowRequestId, @Param("defaultFine") BigDecimal defaultFine);

    @Insert({"<script>",
            "INSERT INTO borrow_request_emi (emi_id, emi_amount, emi_date, borrow_request_id, emi_status, emi_no)",
            "VALUES",
            "<if test='finalMonthlyEMI != null and monthIndex != null and borrowRequestId != null and status != null'>",
            "(uuid_generate_v4(), #{finalMonthlyEMI}, CURRENT_DATE + INTERVAL '${monthIndex} MONTH', #{borrowRequestId}, #{status}, #{monthIndex})",
            "</if>",
            "</script>"})
    void addEMIDetails(@Param("finalMonthlyEMI") BigDecimal finalMonthlyEMI,
                       @Param("monthIndex") Integer monthIndex,
                       @Param("borrowRequestId") String borrowRequestId,
                       @Param("status") Integer status);


    @Select("SELECT amount FROM transaction WHERE transaction_id IN (SELECT transaction_id FROM lend_transaction WHERE " +
            "borrow_request_id = #{borrowRequestId}) AND sender_id = #{lenderId}")
    BigDecimal getLentAmountByBorrowRequestIdAndLenderId(@Param("borrowRequestId") String borrowRequestId, @Param("lenderId") String lenderId);

    @Select("SELECT emi_id AS emiId, emi_no AS emiNo, emi_amount AS emiAmount, emi_date AS emiDate, " +
            "created_date AS createdDate, modified_date AS modifiedDate " +
            "FROM borrow_request_emi " +
            "WHERE borrow_request_id = #{borrowRequestId} AND emi_status NOT IN (SELECT status_code FROM status WHERE status_name = 'Paid') " +
            "LIMIT 1")
    EMI getNextScheduledEMI(@Param("borrowRequestId") String borrowRequestId);

    @Select("SELECT COUNT(*) > 0 FROM borrow_request_emi " +
            "WHERE emi_id = #{emiId} AND emi_status = (SELECT status_code FROM status WHERE status_name = 'Defaulted')")
    Boolean isDefaultedEMI(@Param("emiId") String emiId);

    @Update({"<script>",
            "UPDATE borrow_request_emi",
            "SET emi_status = #{statusCode}, modified_date = NOW()",
            "WHERE emi_id = #{emiId}",
            "<if test='emiId != null and statusCode != null'>",
            "</if>",
            "</script>"})
    void updateEMIStatus(@Param("emiId") String emiId, @Param("statusCode") Integer statusCode);

}
