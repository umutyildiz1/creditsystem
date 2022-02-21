package com.paycoreumutyildiz.creditsystem.Service.concretes;

import com.paycoreumutyildiz.creditsystem.Exceptions.NotFoundException;
import com.paycoreumutyildiz.creditsystem.Exceptions.UniquePhoneNumberException;
import com.paycoreumutyildiz.creditsystem.Model.Credit;
import com.paycoreumutyildiz.creditsystem.Model.Customer;
import com.paycoreumutyildiz.creditsystem.Repository.CustomerRepository;
import com.paycoreumutyildiz.creditsystem.Service.abstracts.CreditScoreService;
import com.paycoreumutyildiz.creditsystem.Service.abstracts.CreditService;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CreditScoreService creditScoreService;

    @Mock
    private CreditService creditService;

    @InjectMocks
    private CustomerServiceImpl customerService;


    @Test
    void getAllCustomers() {
        Customer firstCustomer = new Customer
                (1L,"Customer1","Customer1", 5000L,"12345678911",450,null);
        Customer secondCustomer = new Customer
                (1L,"Customer2","Customer2", 5000L,"12345678912",450,null);
        List<Customer> expectedCustomers = new ArrayList<>();
        expectedCustomers.add(firstCustomer);
        expectedCustomers.add(secondCustomer);

        when(customerRepository.findAll()).thenReturn(expectedCustomers);

        List<Customer> customers = customerService.getAllCustomers();
        assertEquals(expectedCustomers.size(),customers.size());
        assertEquals(expectedCustomers.get(0), customers.get(0));
        assertEquals(expectedCustomers.get(1), customers.get(1));

    }

    @Test
    void getCustomer_Success() {
        Customer expectedCustomer = new Customer
                (1L,"Customer","Customer", 5000L,"12345678911",450,null);
        Optional<Customer> expectedOptCustomer = Optional.of(expectedCustomer);

        when(customerRepository.findById(1L)).thenReturn(expectedOptCustomer);

        Customer actualCustomer = customerService.getCustomer(1L);
        assertEquals(expectedCustomer, actualCustomer);

    }

    @Test
    void getCustomer_Not_Found() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> customerService.getCustomer(1L));
    }

    @Test
    void addCustomer_Success() {//CreditScoreServisi inject edip tekrar yaz //exception ayrı test
        Customer expectedCustomer = new Customer
                (1L,"Customer","Customer", 5000L,"12345678912",450,null);
        Customer actualCustomer = new Customer
                (1L,"Customer","Customer", 5000L,"12345678912",0,null);

        when(customerRepository.save(expectedCustomer)).thenReturn(expectedCustomer);
        when(creditScoreService.getRandomCreditScore()).thenReturn(450);
        customerService.addCustomer(actualCustomer);

        verify(customerRepository, times(1)).save(expectedCustomer);
    }

    @Test
    void addCustomer_UniquePhoneNumberException(){
        Customer firstCustomer = new Customer
                (1L,"Customer","Customer", 5000L,"12345678912",450,null);
        Customer secondCustomer = new Customer
                (2L,"Customer","Customer", 5000L,"12345678912",450,null);

        when(customerRepository.save(firstCustomer)).thenReturn(firstCustomer);
        when(customerRepository.save(secondCustomer)).thenThrow(RuntimeException.class);

        customerService.addCustomer(firstCustomer);
        verify(customerRepository,times(0)).save(secondCustomer);
        assertThrows(UniquePhoneNumberException.class,() -> {
            customerService.addCustomer(secondCustomer);
        });
    }

    @Test
    void updateCustomer() {
        Customer expectedCustomer = new Customer
                (1L,"Customer","Customer", 5000L,"12345678912",450,null);

        when(customerRepository.save(expectedCustomer)).thenReturn(expectedCustomer);

        Customer actualCustomer = customerService.updateCustomer(expectedCustomer);
        assertEquals(expectedCustomer,actualCustomer);
    }

    @Test
    void deleteCustomer_Without_Credit_Success() {
        Customer customer = new Customer
                (1L,"Customer","Customer", 5000L,"12345678912",450,null);
        Boolean expectedResult = true;

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(creditService.getCredit(1L)).thenThrow(NotFoundException.class);

        Boolean actualResult = customerService.deleteCustomer(1L);
        assertEquals(expectedResult,actualResult);


    }

    @Test
    void deleteCustomer_With_Credit_Success() {
        Credit credit = new Credit
                (1L,"RED",0);
        Customer customer = new Customer
                (1L,"Customer","Customer", 5000L,"12345678912",450,credit);
        Boolean expectedResult = true;

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(creditService.getCredit(1L)).thenReturn(credit);

        Boolean actualResult = customerService.deleteCustomer(1L);
        assertEquals(expectedResult,actualResult);


    }

    @Test
    void deleteCustomer_NotFoundException(){
        Customer customer = new Customer
                (1L,"Customer","Customer", 5000L,"12345678912",450,null);

        when(customerRepository.findById(1L)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> {
            customerService.deleteCustomer(1L);
        });


    }
}