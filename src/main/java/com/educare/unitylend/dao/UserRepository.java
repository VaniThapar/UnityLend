package com.educare.unitylend.dao;

import com.educare.unitylend.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Mapper
@Repository
public interface UserRepository {

    static String getUserForUserIdQuery(String userId){
        StringBuilder sqlQueryBuilder = new StringBuilder();

        sqlQueryBuilder.append("select userid as userID, password, name,email,dob,income,officename,collegeuniversity,locality from User where 1=1 ");
        if(userId != null){
            sqlQueryBuilder.append(" and userId = %s".formatted(userId));
        }

        return sqlQueryBuilder.toString();
    }

    @Select("select * from tempuser where password='pass123' ")
    List<User> getAllUsers();

    @SelectProvider(type = UserRepository.class, method = "getUserForUserIdQuery")
    User getUserForUserId(@Param("UserId") String userId);
    //  static final List<String> SELECT_USERS = "select password from User";

    @Insert("INSERT INTO tempuser (userid, password, name,email,dob,income,officename,collegeuniversity,locality) VALUES (uuid_generate_v4(), #{password}, #{name}, #{email},#{dob},#{income},#{officename},#{collegeuniversity},#{locality})")
    void createUser(User user);

    @Select("SELECT userid FROM tempuser WHERE email = #{email}")
    UUID settingID(@Param("email") String email);



//    @Update("UPDATE tempuser SET name = #{name}, email =#{email}, locality = #{locality}, officename = #{officename}, income = #{income} WHERE userid = #{userid}")
//    void updateUser(User user);


}
