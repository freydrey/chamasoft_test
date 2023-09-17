package com.chamasoft.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO {
    List<Customer> selectAllCustomers();

    Optional<Customer> selectCustomerById(Integer id);

    Optional<Customer> selectCustomerByPhone(String phone);

    void insertCustomer(Customer customer);

    boolean personWithPhoneExists(String phone);

    Optional<Customer> selectCustomerWithPhonePassword(String phone, String password);

    void deleteCustomerById(Integer id);

    boolean personWithIdExists(Integer id);

    void updateCustomer(Customer update);
}
