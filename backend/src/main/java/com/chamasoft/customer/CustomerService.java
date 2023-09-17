package com.chamasoft.customer;


import com.chamasoft.PassEncryption;
import com.chamasoft.customer.exception.DuplicateResourceException;
import com.chamasoft.customer.exception.RequestValidationException;
import com.chamasoft.customer.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerService(@Qualifier("Jpa") CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public List<Customer> getAllCustomers(){
        return customerDAO.selectAllCustomers();
    }

    public Customer getCustomerById(Integer id){
        return customerDAO.selectCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] NOT found"
                        .formatted(id)));
    }

    public Customer getCustomerByPhonePassword(CustomerLoginRequest customerLoginRequest){
        Optional<Customer> customer = customerDAO.selectCustomerByPhone(customerLoginRequest.phone());
        if(customer.isPresent()){
            if(!PassEncryption.checkPassword(customerLoginRequest.password(),customer.get().getPassword())){
                new ResourceNotFoundException(
                        "Incorrect username or password");
            }else{
                return customer.get();
            }
        }else{
            new ResourceNotFoundException(
                    "Incorrect username or password");
        }
        return null;
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest){
        //check if email exits
        System.out.println("boolean phone exists count = " + customerDAO.personWithPhoneExists(customerRegistrationRequest.phone()));
        if(customerDAO.personWithPhoneExists(customerRegistrationRequest.phone())){
            throw new DuplicateResourceException("Phone already taken");
        }
        //add
        Customer customer = new Customer(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.phone(),
                PassEncryption.getSecuredPassword(customerRegistrationRequest.password()));
        customerDAO.insertCustomer(customer);
    }

    public void deleteCustomerById(Integer id){
        //check if email exits
        if(!customerDAO.personWithIdExists(id)){
           throw new ResourceNotFoundException("Customer with id [%s] not found " .formatted(id));
        }
        //add
        customerDAO.deleteCustomerById(id);
    }

    public void updateCustomer(Integer customerId, CustomerUpdateRequest customerUpdateRequest){

        Customer customer = getCustomerById(customerId);

        boolean changes = false;
        //System.out.println("customer name " + customer.getName() + "-" + customerUpdateRequest.name()
            //    .equals(customer.getName()));

        if(customerUpdateRequest.name() != null && !customerUpdateRequest.name()
                .equals(customer.getName()) ){
            customer.setName(customerUpdateRequest.name());
            changes = true;
        }

        if(customerUpdateRequest.email() != null && !customerUpdateRequest.email()
                .equals(customer.getPhone()) ){
            //check if email exits
            if(customerDAO.personWithPhoneExists(customerUpdateRequest.email())){
                throw new DuplicateResourceException("Email already taken");
            }

            customer.setPhone(customerUpdateRequest.email());
            changes = true;
        }


        if(!changes){
            throw new RequestValidationException("no data changes found");
        }

        customerDAO.updateCustomer(customer);

    }
}
