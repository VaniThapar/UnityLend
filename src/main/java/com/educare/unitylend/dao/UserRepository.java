package com.educare.unitylend.dao;

import com.educare.unitylend.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserRepository {

    @Select(SELECT_USERS)
    List<User> getAllUsers();

    @SelectProvider(type = UserRepository.class, method = "getUserForUserIdQuery")
    User getUserForUserId(@Param("userId") String userId);

    static final String SELECT_USERS = "select user_id as userID, password, name from user_table";

    static String getUserForUserIdQuery(String userId){
        StringBuilder sqlQueryBuilder = new StringBuilder();

        sqlQueryBuilder.append("select user_id as userID, password, name from user_table where 1=1 ");
        if(userId != null){
            sqlQueryBuilder.append(" and userId = %s".formatted(userId));
        }

        return sqlQueryBuilder.toString();
    }
}
