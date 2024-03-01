package com.educare.unitylend.dao;

import com.educare.unitylend.model.User;
import com.educare.unitylend.model.Wallet;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WalletRepository {


        @Select("SELECT w.walletid, w.userid, w.balance FROM wallet w JOIN tempuser u ON w.userid = u.userid WHERE w.userid = #{userId}")
        Wallet getWalletInfo(@Param("userId") String userId);

        @Insert("INSERT INTO wallet (walletid, balance, userid) VALUES (uuid_generate_v4(), 0, #{userId})")
        void generateWalletInTable(String userId);

        @Select("SELECT u.* FROM tempuser u WHERE u.userid = (SELECT userid FROM wallet WHERE walletid = #{ex.walletid})")
        User getUserIdWithWallet(@Param("ex") Wallet ex);


}
