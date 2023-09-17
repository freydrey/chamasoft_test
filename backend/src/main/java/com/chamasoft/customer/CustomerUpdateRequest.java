package com.chamasoft.customer;

public record CustomerUpdateRequest(
        String name,
        String email,
        Integer age

) {

}
