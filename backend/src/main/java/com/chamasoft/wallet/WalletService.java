package com.chamasoft.wallet;

import com.chamasoft.customer.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WalletService {

    private final WalletDAO walletDAO;

    public WalletService(@Qualifier("wJpa") WalletDAO walletDAO) {
        this.walletDAO = walletDAO;
    }

    public BigDecimal customerWalletBalance(String phone){
        System.out.println("phone "+ phone);
        return walletDAO.customerWalletBalance(phone);
    }

    public void customerWalletCredit(Wallet wallet){
         walletDAO.customerWalletCredit(wallet);
    }

    public void customerWalletDebit(Wallet wallet){
         walletDAO.customerWalletCredit(wallet);
    }

}
