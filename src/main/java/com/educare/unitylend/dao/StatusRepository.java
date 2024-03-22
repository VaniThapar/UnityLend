package com.educare.unitylend.dao;

import com.educare.unitylend.model.Status;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface StatusRepository {
    @Select("SELECT status_code AS statusCode, status_name AS statusName FROM status WHERE status_code = #{statusCode}")
    Status getStatusByStatusCode(@Param("statusCode") Integer statusCode);

    @Select("Select status_code from status where status_name=#{statusName}")
    Integer getStatusCodeByStatusName(@Param("statusName") String statusName);
}
