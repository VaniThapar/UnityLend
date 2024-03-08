package com.educare.unitylend.dao;

import com.educare.unitylend.model.User;
import com.educare.unitylend.model.Wallet;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Repository;


import java.util.List;


@Mapper
@Repository
public interface WalletRepository {

    static final String SELECT_WALLETS = "select * from wallet";

    static String getWalletInfoQuery(String userId) {
        StringBuilder sqlQueryBuilder = new StringBuilder();

        sqlQueryBuilder.append("SELECT w.walletid, w.userid, w.balance FROM wallet w JOIN tempuser u ON w.userid = u.userid WHERE 1=1");
        if (userId != null) {
            sqlQueryBuilder.append(" AND w.userid = '").append(userId).append("'");
        }

        return sqlQueryBuilder.toString();
    }


    public static String generateWalletInTableQuery(String userId) {
        StringBuilder sqlQueryBuilder = new StringBuilder();

        sqlQueryBuilder.append("INSERT INTO wallet (walletid, balance, userid) VALUES (uuid_generate_v4(), 0, ");
        if (userId != null) {
            sqlQueryBuilder.append("'").append(userId).append("'");
        } else {
            sqlQueryBuilder.append("NULL");
        }
        sqlQueryBuilder.append(")");

        return sqlQueryBuilder.toString();
    }

    public static String getUserIdWithWalletQuery(@Param("ex") Wallet ex) {
        StringBuilder sqlQueryBuilder = new StringBuilder();

        sqlQueryBuilder.append("SELECT u.* FROM tempuser u WHERE u.userid = (SELECT userid FROM wallet WHERE walletid = ");
        if (ex != null && ex.getWalletid() != null) {
            sqlQueryBuilder.append("'").append(ex.getWalletid()).append("'");
        } else {
            sqlQueryBuilder.append("NULL");
        }
        sqlQueryBuilder.append(")");

        return sqlQueryBuilder.toString();
    }

    public static String getWalletByIdQuery(@Param("walletid") String walletid) {
        StringBuilder sqlQueryBuilder = new StringBuilder();

        sqlQueryBuilder.append("SELECT * FROM wallet WHERE walletid = ");
        if (walletid != null) {
            sqlQueryBuilder.append("'").append(walletid).append("'");
        } else {
            sqlQueryBuilder.append("NULL");
        }

        return sqlQueryBuilder.toString();
    }

    public static String updateBalanceQuery(@Param("walletid") String walletid, @Param("balance") Float balance) {
        StringBuilder sqlQueryBuilder = new StringBuilder();

        sqlQueryBuilder.append("UPDATE wallet SET balance = ");
        if (balance != null) {
            sqlQueryBuilder.append(balance);
        } else {
            sqlQueryBuilder.append("NULL");
        }
        sqlQueryBuilder.append(" WHERE walletid = ");
        if (walletid != null) {
            sqlQueryBuilder.append("'").append(walletid).append("'");
        } else {
            sqlQueryBuilder.append("NULL");
        }

        return sqlQueryBuilder.toString();
    }


    @Select(SELECT_WALLETS)
    List<Wallet> getAllWallets();


    @SelectProvider(type = WalletRepository.class, method = "getWalletInfoQuery")
    Wallet getWalletInfo(@Param("userId") String userId);


    @InsertProvider(type = WalletRepository.class, method = "generateWalletInTableQuery")
    void generateWalletInTable(String userId);


    @SelectProvider(type = WalletRepository.class, method = "getUserIdWithWalletQuery")
    User getUserIdWithWallet(@Param("ex") Wallet ex);


    @SelectProvider(type = WalletRepository.class, method = "getWalletByIdQuery")
    Wallet getWalletById(@Param("walletid") String walletid);


    @UpdateProvider(type = WalletRepository.class, method = "updateBalanceQuery")
    void updateBalance(@Param("walletid") String walletid, @Param("balance") Float balance);
}

