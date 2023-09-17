package com.chamasoft.wallet;

import com.chamasoft.customer.Customer;
import com.chamasoft.mpesa.*;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/v1/wallet")
public class WalletController {

    private final DepositService depositService;
    private final WithdrawService withdrawService;
    private final WalletService walletService;

    public WalletController(DepositService depositService, WithdrawService withdrawService, WalletService walletService) {
        this.depositService = depositService;
        this.withdrawService = withdrawService;
        this.walletService = walletService;
    }

    @PostMapping(path = "/deposit/mpesa")
    public String mpesaDeposit(
            @RequestBody MpesaDepositRequest mpesaDepositRequest){
        return depositService.makeMpesaDeposit(mpesaDepositRequest);
    }

    @PostMapping(path = "/withdraw/mpesa")
    public String mpesaWithdraw(
            @RequestBody MpesaWithdrawRequest mpesaWithdrawRequest){
        return withdrawService.makeMpesaWithdrawal(mpesaWithdrawRequest);
    }

    @PostMapping(path = "/balance")
    public BigDecimal getCustomerWalletBalance(
            @RequestBody WalletBalanceRequest walletBalanceRequest){
        return walletService.customerWalletBalance(walletBalanceRequest.getPhone());
    }

}
