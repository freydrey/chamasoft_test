package com.chamasoft.mpesa;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("mpesa.settings.deposit")
public class MpesaDepositProperties {

    private String dp_consumerkey;
    private String dp_consumersecret;
    private String dp_passkey;
    private String dp_shortcode;
    private String dp_callbackurl;

    // Getters and setters for the properties

    public String getDp_consumerkey() {
        return dp_consumerkey;
    }

    public void setDp_consumerkey(String dp_consumerkey) {
        this.dp_consumerkey = dp_consumerkey;
    }

    public String getDp_consumersecret() {
        return dp_consumersecret;
    }

    public void setDp_consumersecret(String dp_consumersecret) {
        this.dp_consumersecret = dp_consumersecret;
    }

    public String getDp_passkey() {
        return dp_passkey;
    }

    public void setDp_passkey(String dp_passkey) {
        this.dp_passkey = dp_passkey;
    }

    public String getDp_shortcode() {
        return dp_shortcode;
    }

    public void setDp_shortcode(String dp_shortcode) {
        this.dp_shortcode = dp_shortcode;
    }

    public String getDp_callbackurl() {
        return dp_callbackurl;
    }

    public void setDp_callbackurl(String dp_callbackurl) {
        this.dp_callbackurl = dp_callbackurl;
    }
}
