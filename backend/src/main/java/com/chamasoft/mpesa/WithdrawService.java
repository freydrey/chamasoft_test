package com.chamasoft.mpesa;

import com.chamasoft.wallet.Wallet;
import com.chamasoft.wallet.WalletService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Service
public class WithdrawService {

    private final MpesaWithdrawProperties mpesaWithdrawProperties;
    private final WalletService walletService;

    @Autowired
    public WithdrawService(MpesaWithdrawProperties mpesaWithdrawProperties, WalletService walletService) {
        this.mpesaWithdrawProperties = mpesaWithdrawProperties;
        this.walletService = walletService;
    }

    public String makeMpesaWithdrawal(MpesaWithdrawRequest mpesaWithdrawRequest) {

        String consumerKey = mpesaWithdrawProperties.getDp_consumerkey();
        String consumerSecret = mpesaWithdrawProperties.getDp_consumersecret();
        String initiatorName = mpesaWithdrawProperties.getDp_initiatorname();
        String securityCredential = mpesaWithdrawProperties.getDp_securitycredential();
        String shortcode = mpesaWithdrawProperties.getDp_shortcode();
        String commandId = mpesaWithdrawProperties.getDp_commandid(); // e.g., "SalaryPayment"
        String timeouturl = mpesaWithdrawProperties.getDp_timeouturl();
        String resulturl = mpesaWithdrawProperties.getDp_resulturl();
        String originatorConversationID = String.valueOf(UUID.randomUUID());
        String receiverPhoneNumber = mpesaWithdrawRequest.phone();
        Integer amount = mpesaWithdrawRequest.amount();

        int isSufficientBalance = walletService.customerWalletBalance(receiverPhoneNumber).compareTo(new BigDecimal(amount));
        if(isSufficientBalance == -1) return "Insufficient Balance";

        try {
            // Set the URL for the B2C API endpoint
            URL url = new URL("https://sandbox.safaricom.co.ke/mpesa/b2c/v1/paymentrequest");
            String accessToken = generateAccessToken(consumerKey,consumerSecret);
            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the HTTP request method to POST
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Authorization", "Bearer " + accessToken);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Construct the JSON payload for the B2C request
            String jsonPayload = "{" +
                    "\"OriginatorConversationID\":\"" + originatorConversationID + "\"," +
                    "\"InitiatorName\":\"" + initiatorName + "\"," +
                    "\"SecurityCredential\":\"" + securityCredential + "\"," +
                    "\"CommandID\":\"" + commandId + "\"," +
                    "\"Amount\":\"" + amount + "\"," +
                    "\"PartyA\":\"" + shortcode + "\"," +
                    "\"PartyB\":\"" + receiverPhoneNumber + "\"," +
                    "\"Remarks\":\"Withdrawal\"," +
                    "\"QueueTimeOutURL\":\"" + timeouturl + "\"," +
                    "\"ResultURL\":\"" + resulturl + "\"," +
                    "\"Occasion\":\"Withdraw\"" +
                    "}";

            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(jsonPayload);
            outputStream.flush();
            outputStream.close();

System.out.println(jsonPayload + "" +  accessToken);


            // Get the HTTP response code
            int responseCode = connection.getResponseCode();
             BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response from the input stream

                    //debit customer account
                    Wallet wallet = new Wallet();
                    wallet.setPhone(receiverPhoneNumber);
                    wallet.setAmount(-amount);
                    walletService.customerWalletDebit(wallet);

                    Gson gson = new Gson();
                    WithdrawSuccessResponse withdrawSuccessResponse = gson.fromJson(response.toString(), WithdrawSuccessResponse.class);

                    // You can now access the transaction details from callbackData
                    String resultDesc = withdrawSuccessResponse.getResponseDescription();

                    // Close the connection
                    connection.disconnect();

                    return resultDesc;

            } else {
                // Close the connection
                connection.disconnect();
                Gson gson = new Gson();
                WithdrawFailResponse withdrawFailResponse = gson.fromJson(response.toString(), WithdrawFailResponse.class);

                return withdrawFailResponse.getErrorMessage();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown Error Occured. Please try again";
        }

    }



    // Generate an access token using Safaricom's OAuth API
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

    // Generate the withdrawal request JSON payload
    private static String generateWithdrawalRequest(
            String accessToken, String shortCode, String passKey, String phoneNumber, String amount, String accountReference
    ) {
        // Construct the withdrawal request JSON payload
        String payload = "{"
                + "\"BusinessShortCode\":\"" + shortCode + "\","
                + "\"Password\":\"" + generatePassword(shortCode, passKey) + "\","
                + "\"Timestamp\":\"" + generateTimestamp() + "\","
                + "\"TransactionType\":\"CustomerPayBillOnline\","
                + "\"Amount\":\"" + amount + "\","
                + "\"PartyA\":\"" + phoneNumber + "\","
                + "\"PartyB\":\"" + shortCode + "\","
                + "\"PhoneNumber\":\"" + phoneNumber + "\","
                + "\"CallBackURL\":\"\","
                + "\"AccountReference\":\"" + accountReference + "\","
                + "\"TransactionDesc\":\"Withdrawal\""
                + "}";

        return payload;
    }

    // Generate the password required for the withdrawal request
    private static String generatePassword(String shortCode, String passKey) {
        String timestamp = generateTimestamp();
        String password = shortCode + passKey + timestamp;
        return org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
    }

    // Generate a timestamp in the format YYYYMMDDHHMMSS
    private static String generateTimestamp() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date now = new java.util.Date();
        return sdf.format(now);
    }

    // Send the withdrawal request to Safaricom's M-Pesa API
    private static String sendWithdrawalRequest(String payload, String accessToken) throws Exception {
        URL url = new URL("https://sandbox.safaricom.co.ke/mpesa/b2c/v3/paymentrequest");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + accessToken);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(payload);
        outputStream.flush();
        outputStream.close();

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String responseLine;
            StringBuilder responseContent = new StringBuilder();
            while ((responseLine = reader.readLine()) != null) {
                responseContent.append(responseLine);
            }
            reader.close();
            return responseContent.toString();
        } else {
            throw new RuntimeException("Withdrawal request failed. Response code: " + responseCode);
        }
    }
}
