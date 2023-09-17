package com.chamasoft.customer;

public record CustomerLoginRequest(
        String phone,
        String password
) {

}