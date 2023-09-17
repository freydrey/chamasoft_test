package com.chamasoft.mpesa;

import com.google.gson.annotations.SerializedName;

public class WithdrawCallbackData {

    @SerializedName("ConversationID")
    private String conversationID;

    @SerializedName("OriginatorConversationID")
    private String originatorConversationID;

    @SerializedName("ResponseCode")
    private String responseCode;

    @SerializedName("ResponseDescription")
    private String responseDescription;

    public String getConversationID() {
        return conversationID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public String getOriginatorConversationID() {
        return originatorConversationID;
    }

    public void setOriginatorConversationID(String originatorConversationID) {
        this.originatorConversationID = originatorConversationID;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDescription() {
        return responseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }
}
