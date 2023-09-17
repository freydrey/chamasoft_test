package com.chamasoft.mpesa;

public record MpesaWithdrawRequest(
        String phone,
        Integer amount
) {

}