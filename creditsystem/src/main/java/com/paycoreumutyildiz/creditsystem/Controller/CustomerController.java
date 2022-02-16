package com.paycoreumutyildiz.creditsystem.Controller;

import com.paycoreumutyildiz.creditsystem.Model.Customer;
import com.paycoreumutyildiz.creditsystem.Service.abstracts.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/customer/")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("all")
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("{sid}")
    public Customer getCustomer(@PathVariable @Min(1) Long sid){
        return customerService.getCustomer(sid);
    }

    @PostMapping("create")
    public void createCustomer(@RequestBody @Valid Customer customer){
        customerService.addCustomer(customer);
    }

    @PutMapping("update")
    public Customer updateCustomer(@RequestBody Customer customer){
        return customerService.updateCustomer(customer);
    }

    @DeleteMapping("delete")
    public boolean deleteBook(@RequestParam @Min(1) Long sid){
        return customerService.deleteCustomer(sid);
    }
}
