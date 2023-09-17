package com.chamasoft.mpesa;


public record MpesaDepositRequest(
        String phone,
        Integer amount
) {

}
