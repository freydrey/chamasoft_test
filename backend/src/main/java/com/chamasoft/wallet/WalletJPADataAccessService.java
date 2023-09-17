package com.chamasoft.wallet;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;


@Repository("wJpa")
public class WalletJPADataAccessService implements WalletDAO {

    private final WalletRepository walletRepository;

    public WalletJPADataAccessService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public BigDecimal customerWalletBalance(String phone) {
        return walletRepository.getWalletBalanceByPhone(phone);
    }

    @Override
    public void customerWalletCredit(Wallet wallet) {
        walletRepository.save(wallet);
    }

    @Override
    public void customerWalletDebit(Wallet wallet) {
        walletRepository.save(wallet);
    }


}
