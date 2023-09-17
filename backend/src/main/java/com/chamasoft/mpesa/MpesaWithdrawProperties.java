package com.chamasoft.mpesa;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("mpesa.settings.withdraw")
public class MpesaWithdrawProperties {

    private String dp_consumerkey;
    private String dp_consumersecret;
    private String dp_securitycredential;
    private String dp_shortcode;
    private String dp_initiatorname;
    private String dp_commandid;
    private String dp_timeouturl;
    private String dp_resulturl;

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

    public String getDp_shortcode() {
        return dp_shortcode;
    }

    public void setDp_shortcode(String dp_shortcode) {
        this.dp_shortcode = dp_shortcode;
    }

    public String getDp_securitycredential() {
        return dp_securitycredential;
    }

    public void setDp_securitycredential(String dp_securitycredential) {
        this.dp_securitycredential = dp_securitycredential;
    }

    public String getDp_initiatorname() {
        return dp_initiatorname;
    }

    public void setDp_initiatorname(String dp_initiatorname) {
        this.dp_initiatorname = dp_initiatorname;
    }

    public String getDp_commandid() {
        return dp_commandid;
    }

    public void setDp_commandid(String dp_commandid) {
        this.dp_commandid = dp_commandid;
    }

    public String getDp_timeouturl() {
        return dp_timeouturl;
    }

    public void setDp_timeouturl(String dp_timeouturl) {
        this.dp_timeouturl = dp_timeouturl;
    }

    public String getDp_resulturl() {
        return dp_resulturl;
    }

    public void setDp_resulturl(String dp_resulturl) {
        this.dp_resulturl = dp_resulturl;
    }
}
