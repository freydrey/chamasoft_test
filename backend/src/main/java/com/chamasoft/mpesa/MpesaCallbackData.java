package com.chamasoft.mpesa;

import com.google.gson.annotations.SerializedName;

public class MpesaCallbackData {
    @SerializedName("TransactionType")
    private String transactionType;

    @SerializedName("TransID")
    private String transactionID;

    @SerializedName("TransTime")
    private String transactionTime;

    @SerializedName("TransAmount")
    private String transactionAmount;

    @SerializedName("BusinessShortCode")
    private String businessShortCode;

    @SerializedName("BillRefNumber")
    private String billReferenceNumber;

    @SerializedName("InvoiceNumber")
    private String invoiceNumber;

    @SerializedName("OrgAccountBalance")
    private String orgAccountBalance;

    @SerializedName("ThirdPartyTransID")
    private String thirdPartyTransactionID;

    @SerializedName("MSISDN")
    private String msisdn;

    @SerializedName("FirstName")
    private String firstName;

    @SerializedName("MiddleName")
    private String middleName;

    @SerializedName("LastName")
    private String lastName;

    @SerializedName("OrgTransactionID")
    private String orgTransactionID;

    @SerializedName("ResultCode")
    private String resultCode;

    @SerializedName("ResultDesc")
    private String resultDesc;

    public String getTransactionType() {
        return transactionType;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public String getBusinessShortCode() {
        return businessShortCode;
    }

    public String getBillReferenceNumber() {
        return billReferenceNumber;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public String getOrgAccountBalance() {
        return orgAccountBalance;
    }

    public String getThirdPartyTransactionID() {
        return thirdPartyTransactionID;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getOrgTransactionID() {
        return orgTransactionID;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }
}
