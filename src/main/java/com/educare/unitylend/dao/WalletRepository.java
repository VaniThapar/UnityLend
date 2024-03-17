package com.educare.unitylend.dao;


import com.educare.unitylend.model.Wallet;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Mapper
@Repository
public interface WalletRepository {

    @Select("Select wallet_id as walletId,balance from wallet")
    List<Wallet> getAllWallets();

    @Select("Select wallet_id as walletId,balance from wallet where user_id=#{userId}")
    Wallet getWalletForUserId(@Param("userId")String userId);

    @Select("Select wallet_id as walletId,balance from wallet where wallet_id=#{walletId}")
    Wallet getWalletForWalletId(@Param("walletId")String walletId);

    @Insert("Insert into wallet(wallet_id,user_id,balance) values(uuid_generate_v4(),#{userId},0.00)")
    Wallet generateWallet(@Param("userId")String userId);

    @Update("Update wallet set balance=balance+#{amount} where wallet_id=#{walletId}")
    Boolean addAmount(@Param("walletId")String walletId,@Param("amount") BigDecimal amount);

    @Update("Update wallet set balance=balance-#{amount} where wallet_id=#{walletId}")
    Boolean deductAmount(@Param("walletId")String walletId,@Param("amount") BigDecimal amount);

    @Select("Select balance from wallet where user_id=#{userId}")
    BigDecimal getWalletBalance(@Param("userId")String userId);
}