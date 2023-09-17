package com.chamasoft.mpesa;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/callback")
public class CallbackController {

    private final DepositService depositService;
    private final WithdrawService withdrawService;

    public CallbackController(DepositService depositService, WithdrawService withdrawService) {
        this.depositService = depositService;
        this.withdrawService = withdrawService;
    }

    @PostMapping("/deposit")
    public String callbackDeposit(
            @RequestBody String callbackData){

        // Parse the JSON data from the request body
        Gson gson = new Gson();
        MpesaCallbackData mpesaCallbackData = gson.fromJson(callbackData, MpesaCallbackData.class);

        // You can now access the transaction details from callbackData
        /*String merchantRequestID = mpesaCallbackData.getMerchantRequestID();
        String checkoutRequestID = mpesaCallbackData.getCheckoutRequestID();
        String resultCode = mpesaCallbackData.getResultCode();
        String resultDesc = mpesaCallbackData.getResultDesc();*/
        System.out.println("M-Pesa Callback Data:\n" + callbackData);

        return "";

    }

    @PostMapping("/withdraw")
    public String callbackWithdraw(
            @RequestBody String callbackData){

        // Parse the JSON data from the request body
        Gson gson = new Gson();
        MpesaCallbackData mpesaCallbackData = gson.fromJson(callbackData, MpesaCallbackData.class);

        // You can now access the transaction details from callbackData
        /*String merchantRequestID = mpesaCallbackData.getMerchantRequestID();
        String checkoutRequestID = mpesaCallbackData.getCheckoutRequestID();
        String resultCode = mpesaCallbackData.getResultCode();
        String resultDesc = mpesaCallbackData.getResultDesc();*/
        System.out.println("M-Pesa Callback Data:\n" + callbackData);

        return "";
    }


}
