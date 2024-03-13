package com.educare.unitylend.dao;


import com.educare.unitylend.model.User;
import com.educare.unitylend.model.Wallet;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


import java.util.List;


@Mapper
@Repository
public interface WalletRepository {

    @Select("select * from wallet")
    List<Wallet> getAllWallets();


    @Select("SELECT w.walletid, w.userid, w.balance FROM wallet w JOIN tempuser u ON w.userid = u.userid WHERE w.userid = #{userId}")
    Wallet getWalletInfo(@Param("userId") String userId);


    @Insert("INSERT INTO wallet (walletid, balance, userid) VALUES (uuid_generate_v4(), 0, #{userId})")
    void generateWalletInTable(String userId);


    @Select("SELECT u.* FROM tempuser u WHERE u.userid = (SELECT userid FROM wallet WHERE walletid = #{ex.walletid})")
    User getUserIdWithWallet(@Param("ex") Wallet ex);


    @Select("SELECT * FROM wallet WHERE walletid = #{walletid}")
    Wallet getWalletById(@Param("walletid") String walletid);


    @Update("UPDATE wallet SET balance = #{balance} WHERE walletid = #{walletid}")
    void updateBalance(@Param("walletid") String walletid, @Param("balance") Float balance);
}
