package com.chamasoft.customer;

public record CustomerRegistrationRequest(
        String name,
        String phone,
        String password
) {

}
