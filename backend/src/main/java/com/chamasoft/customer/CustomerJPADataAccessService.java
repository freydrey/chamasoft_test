package com.chamasoft.customer;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("Jpa")
public class CustomerJPADataAccessService implements CustomerDAO{

    private final CustomerRepository customerRepository;

    public CustomerJPADataAccessService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return customerRepository.findById(id);
    }

    @Override
    public Optional<Customer> selectCustomerByPhone(String phone) {
        return customerRepository.findByPhone(phone);
    }

    @Override
    public void insertCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public boolean personWithPhoneExists(String phone) {
        return customerRepository.existsCustomerByPhone(phone);
    }

    @Override
    public Optional<Customer> selectCustomerWithPhonePassword(String phone, String password) {
        return customerRepository.findByPhoneAndPassword(phone,password);
    }

    @Override
    public void deleteCustomerById(Integer id) {
        customerRepository.deleteById(id);
    }

    @Override
    public boolean personWithIdExists(Integer id) {
        return customerRepository.existsCustomerById(id);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }
}
