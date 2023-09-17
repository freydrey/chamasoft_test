package com.chamasoft.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository
        extends JpaRepository<Customer, Integer> {

    boolean existsCustomerByPhone(String phone);
    boolean existsCustomerById(Integer id);
    Optional<Customer> findByPhoneAndPassword(String phone, String password);
    Optional<Customer> findByPhone(String phone);



}
