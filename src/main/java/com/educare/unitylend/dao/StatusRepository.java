package com.educare.unitylend.dao;

import com.educare.unitylend.model.Status;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface StatusRepository {
    @Select("SELECT status_code as statusCode, status_name as statusName from status where status_code=#{statusCode}")
    Status getStatusByStatusCode(@Param("statusCode") Integer statusCode);
}
