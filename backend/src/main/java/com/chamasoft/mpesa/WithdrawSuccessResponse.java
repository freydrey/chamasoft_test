package com.chamasoft.mpesa;

public class WithdrawSuccessResponse {

    String ConversationID;
    String OriginatorConversationID;
    String ResponseCode;
    String ResponseDescription;


    public String getConversationID() {
        return ConversationID;
    }

    public void setConversationID(String conversationID) {
        ConversationID = conversationID;
    }

    public String getOriginatorConversationID() {
        return OriginatorConversationID;
    }

    public void setOriginatorConversationID(String originatorConversationID) {
        OriginatorConversationID = originatorConversationID;
    }

    public String getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(String responseCode) {
        ResponseCode = responseCode;
    }

    public String getResponseDescription() {
        return ResponseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        ResponseDescription = responseDescription;
    }
}

