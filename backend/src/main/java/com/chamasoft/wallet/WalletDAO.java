package com.chamasoft.wallet;

import java.math.BigDecimal;

public interface WalletDAO {

    BigDecimal customerWalletBalance(String phone);

    void customerWalletCredit(Wallet wallet);

    void customerWalletDebit(Wallet wallet);

}
