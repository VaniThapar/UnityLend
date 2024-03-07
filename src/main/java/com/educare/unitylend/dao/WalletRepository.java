package com.educare.unitylend.dao;

import com.educare.unitylend.model.User;
import com.educare.unitylend.model.Wallet;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WalletRepository {



        @Select("SELECT * FROM wallet WHERE userid = #{userId}")
        Wallet getWalletInfo(@Param("userId") String userId);

        @Insert("INSERT INTO wallet (walletid, balance, userid) VALUES (uuid_generate_v4(), #{balance}, #{user.getUserid()})")
        void generateWallet(Wallet wallet);

}
