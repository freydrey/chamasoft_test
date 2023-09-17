package com.chamasoft.mpesa;

public class DepositErrorResponse {

    String responseId;
    String responseCod;
    String responseDesc;

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public String getResponseCod() {
        return responseCod;
    }

    public void setResponseCod(String responseCod) {
        this.responseCod = responseCod;
    }

    public String getResponseDesc() {
        return responseDesc;
    }

    public void setResponseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
    }
}
