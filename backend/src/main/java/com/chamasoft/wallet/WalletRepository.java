package com.chamasoft.wallet;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;


public interface WalletRepository
        extends JpaRepository<Wallet, Integer> {


    @Query("SELECT sum(u.amount) FROM Wallet u WHERE u.phone = ?1")
    BigDecimal getWalletBalanceByPhone(String phone);

}
