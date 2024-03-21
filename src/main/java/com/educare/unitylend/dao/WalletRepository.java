package com.educare.unitylend.dao;


import com.educare.unitylend.model.Wallet;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Mapper
@Repository
public interface WalletRepository {
    @Select("SELECT wallet_id AS walletId, balance FROM wallet")
    List<Wallet> getAllWallets();

    @Select("SELECT wallet_id AS walletId, balance FROM wallet WHERE user_id=#{userId}")
    Wallet getWalletForUserId(@Param("userId") String userId);

    @Select("SELECT wallet_id AS walletId, balance FROM wallet WHERE wallet_id=#{walletId}")
    Wallet getWalletForWalletId(@Param("walletId") String walletId);

    @Insert("INSERT INTO wallet(wallet_id, user_id, balance) VALUES (uuid_generate_v4(), #{userId}, 0.00)")
    void generateWallet(@Param("userId") String userId);

    @Update({"<script>",
            "UPDATE wallet",
            "SET balance = balance + #{amount}",
            "WHERE wallet_id = #{walletId}",
            "<if test='walletId != null and amount != null'>",
            "</if>",
            "</script>"})
    Boolean addAmount(@Param("walletId") String walletId, @Param("amount") BigDecimal amount);

    @Update({"<script>",
            "UPDATE wallet",
            "SET balance = balance - #{amount}",
            "WHERE wallet_id = #{walletId}",
            "<if test='walletId != null and amount != null'>",
            "</if>",
            "</script>"})
    Boolean deductAmount(@Param("walletId") String walletId, @Param("amount") BigDecimal amount);

    @Select("SELECT balance FROM wallet WHERE user_id = #{userId}")
    BigDecimal getWalletBalance(@Param("userId") String userId);

}