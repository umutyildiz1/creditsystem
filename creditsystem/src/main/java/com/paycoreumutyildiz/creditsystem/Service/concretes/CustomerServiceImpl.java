package com.paycoreumutyildiz.creditsystem.Service.concretes;

import com.paycoreumutyildiz.creditsystem.Exceptions.NotFoundException;
import com.paycoreumutyildiz.creditsystem.Exceptions.UniquePhoneNumberException;
import com.paycoreumutyildiz.creditsystem.Model.Credit;
import com.paycoreumutyildiz.creditsystem.Model.Customer;
import com.paycoreumutyildiz.creditsystem.Repository.CustomerRepository;
import com.paycoreumutyildiz.creditsystem.Service.abstracts.CreditScoreService;
import com.paycoreumutyildiz.creditsystem.Service.abstracts.CreditService;
import com.paycoreumutyildiz.creditsystem.Service.abstracts.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CreditService creditService;
    private final CreditScoreService creditScoreService;

    private final Integer UPPER_BOUND = 3;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CreditService creditService, CreditScoreService creditScoreService) {
        this.customerRepository = customerRepository;
        this.creditService = creditService;
        this.creditScoreService = creditScoreService;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomer(Long sid) {
        Optional<Customer> customer = customerRepository.findById(sid);
        return customer.orElseThrow(() -> new NotFoundException("Customer"));
    }

    @Override
    public void addCustomer(Customer customer) {
        try{
            customer.setCreditScore(creditScoreService.getRandomCreditScore());
            customerRepository.save(customer);
        }catch (Exception exception){
            throw new UniquePhoneNumberException(customer.getPhoneNumber());
        }

    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override //Delete Customer with Credit
    public boolean deleteCustomer(Long sid) {
        Optional<Customer> customer = Optional.of(getCustomer(sid));
        if(customer.isPresent()){
            Optional<Credit> credit;
            try{
                 credit= Optional.of(creditService.getCredit(sid));
                if(credit.isPresent()){
                    creditService.deleteCredit(sid);
                }
            }catch(NotFoundException e){//log
                 }

            customerRepository.delete(customer.get());
            return true;
        }
        return false;
    }
}
