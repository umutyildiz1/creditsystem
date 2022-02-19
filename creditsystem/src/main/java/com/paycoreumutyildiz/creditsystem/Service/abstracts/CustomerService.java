package com.paycoreumutyildiz.creditsystem.Service.abstracts;

import com.paycoreumutyildiz.creditsystem.Model.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerService {

    List<Customer> getAllCustomers();
    Customer getCustomer(Long sid);
    void addCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    boolean deleteCustomer(Long sid);

}
