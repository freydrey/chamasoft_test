package com.chamasoft.mpesa;

import com.chamasoft.wallet.Wallet;
import com.chamasoft.wallet.WalletService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

@Service
public class DepositService {

    private final MpesaDepositProperties mpesaDepositProperties;
    private final WalletService walletService;

    @Autowired
    public DepositService(MpesaDepositProperties mpesaDepositProperties, WalletService walletService) {
        this.mpesaDepositProperties = mpesaDepositProperties;
        this.walletService = walletService;
    }

    public String makeMpesaDeposit(MpesaDepositRequest mpesaDepositRequest) {
        try {
System.out.println("params are " + mpesaDepositRequest.amount() + " " + mpesaDepositRequest.phone());
            // Safaricom Daraja API credentials
            String consumerKey = mpesaDepositProperties.getDp_consumerkey();
            String consumerSecret = mpesaDepositProperties.getDp_consumersecret();
            String passKey = mpesaDepositProperties.getDp_passkey();
            String shortCode = mpesaDepositProperties.getDp_shortcode();
            String callbackUrl = mpesaDepositProperties.getDp_callbackurl();
            String phoneNumber = mpesaDepositRequest.phone();
            Integer amount = mpesaDepositRequest.amount();

            // Generate an access token
            String accessToken = generateAccessToken(consumerKey, consumerSecret);

            // Create the STK push request payload
            String stkPushRequest = generateSTKPushRequest(
                    accessToken, shortCode, passKey, phoneNumber, amount, callbackUrl
            );

            // Send the STK push request
            String response = sendSTKPushRequest(stkPushRequest,accessToken,phoneNumber,amount);

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return "Exception: " + e.getMessage();
        }
    }

    // Generate an access token using Safaricom's OAuth API
    private static String generateAccessToken(String consumerKey, String consumerSecret) throws Exception {
        String authString = consumerKey + ":" + consumerSecret;
        String encodedAuthString = java.util.Base64.getEncoder().encodeToString(authString.getBytes());

        System.out.println(encodedAuthString);

        URL url = new URL("https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Basic " + encodedAuthString);

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String responseLine;
            StringBuilder responseContent = new StringBuilder();
            while ((responseLine = reader.readLine()) != null) {
                responseContent.append(responseLine);
            }
            reader.close();
            Gson gson = new Gson();
            AccessTokenObject accessTokenObject = gson.fromJson(responseContent.toString(), AccessTokenObject.class);
            return accessTokenObject.getAccess_token();
        } else {
            throw new RuntimeException("Failed to generate access token. Response code: " + responseCode);
        }
    }

    // Generate the STK push request JSON payload
    private static String generateSTKPushRequest(
            String accessToken, String shortCode, String passKey, String phoneNumber, Integer amount, String callbackUrl
    ) {
        // Construct the STK push request JSON payload
        String payload = "{"
                + "\"BusinessShortCode\":\"" + shortCode + "\","
                + "\"Password\":\"" + generatePassword(shortCode, passKey) + "\","
                + "\"Timestamp\":\"" + generateTimestamp() + "\","
                + "\"TransactionType\":\"CustomerPayBillOnline\","
                + "\"Amount\":\"" + amount + "\","
                + "\"PartyA\":\"" + phoneNumber + "\","
                + "\"PartyB\":\"" + shortCode + "\","
                + "\"PhoneNumber\":\"" + phoneNumber + "\","
                + "\"CallBackURL\":\"" + callbackUrl + "\","
                + "\"AccountReference\":\"Test\","
                + "\"TransactionDesc\":\"Test Payment\""
                + "}";

        return payload;
    }

    // Generate the password required for the STK push request
    private static String generatePassword(String shortCode, String passKey) {
        String timestamp = generateTimestamp();
        String password = shortCode + passKey + timestamp;
        // Encode the data to Base64
        byte[] encodedData = Base64.getEncoder().encode(password.getBytes());

        // Convert the byte array to a Base64 string
        String base64String = new String(encodedData);

        return base64String;
    }

    // Generate a timestamp in the format YYYYMMDDHHMMSS
    private static String generateTimestamp() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date now = new java.util.Date();
        return sdf.format(now);
    }

    // Send the STK push request to Safaricom's M-Pesa API
    private String sendSTKPushRequest(String payload, String accessToken, String phone, Integer amount) throws Exception {
        URL url = new URL("https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + accessToken);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(payload);
        outputStream.flush();
        outputStream.close();

        System.out.println(payload + " " + accessToken);

        int responseCode = connection.getResponseCode();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String responseLine;
        StringBuilder responseContent = new StringBuilder();
        while ((responseLine = reader.readLine()) != null) {
            responseContent.append(responseLine);
        }
        reader.close();

        if (responseCode == 200) {

            //credit customer account
            Wallet wallet = new Wallet();
            wallet.setPhone(phone);
            wallet.setAmount(amount);
            walletService.customerWalletCredit(wallet);

            Gson gson = new Gson();
            DepositSuccessResponse depositSuccessResponse = gson.fromJson(responseContent.toString(), DepositSuccessResponse.class);
            return depositSuccessResponse.getCustomerMessage();

        } else {
            Gson gson = new Gson();
            DepositErrorResponse depositErrorResponse = gson.fromJson(responseContent.toString(), DepositErrorResponse.class);

            return depositErrorResponse.getResponseDesc();
            //throw new RuntimeException("STK push request failed. Response code: " + responseCode + payload + accessToken);
        }
    }
}

