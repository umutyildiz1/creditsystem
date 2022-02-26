package com.paycoreumutyildiz.creditsystem.Service.concretes;

import com.paycoreumutyildiz.creditsystem.Exceptions.NotFoundException;
import com.paycoreumutyildiz.creditsystem.Model.Credit;
import com.paycoreumutyildiz.creditsystem.Model.Customer;
import com.paycoreumutyildiz.creditsystem.Repository.CreditRepository;
import com.paycoreumutyildiz.creditsystem.Service.abstracts.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditServiceImplTest {

    @Mock
    private CreditRepository creditRepository;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CreditServiceImpl creditService;

    @Test
    void getAllCredits() {

        Credit firstCredit = new Credit(1L,"RED",0);
        Credit secondCredit = new Credit(2L,"ONAY",10000);

        List<Credit> expectedCredits = new ArrayList<>();
        expectedCredits.add(firstCredit);
        expectedCredits.add(secondCredit);

        when(creditRepository.findAll()).thenReturn(expectedCredits);

        List<Credit> actualCredits = creditService.getAllCredits();

        assertEquals(expectedCredits.size(),actualCredits.size());
        assertEquals(expectedCredits.get(0),actualCredits.get(0));
        assertEquals(expectedCredits.get(1),actualCredits.get(1));
    }

    @Test
    void getCredit_Success() {
        Credit expectedCredit = new Credit(1L,"RED",0);
        Optional<Credit> expectedOptCredit = Optional.of(expectedCredit);

        when(creditRepository.findById(1L)).thenReturn(expectedOptCredit);

        Credit actualCredit = creditService.getCredit(1L);

        assertEquals(expectedCredit,actualCredit);
    }

    @Test
    void getCredit_NotFoundException() {

        when(creditRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,() -> creditService.getCredit(1L));

    }

    @Test
    void addCredit_Success() {
        Credit credit = new Credit(1L,"RED",0);

        when(creditRepository.save(credit)).thenReturn(credit);

        creditService.addCredit(credit);
        verify(creditRepository,times(1)).save(credit);
    }

    @Test
    void addCredit_NotFoundException() {
        Credit credit = new Credit(1L,"RED",0);

        when(creditRepository.save(credit)).thenThrow(RuntimeException.class);

        assertThrows(NotFoundException.class,() -> creditService.addCredit(credit));
    }

    @Test
    void updateCredit() {
        Credit expectedCredit = new Credit(1L,"RED",0);
        Credit inDb = new Credit(1L,"ONAY",10000);

        when(creditRepository.save(expectedCredit)).thenReturn(expectedCredit);
        when(creditRepository.findById(1L)).thenReturn(Optional.of(inDb));

        Credit actualCredit = creditService.updateCredit(expectedCredit);
        assertEquals(expectedCredit,actualCredit);
    }

    @Test
    void deleteCredit() {
        Credit credit = new Credit(1L,"RED",0);
        Optional<Credit> optionalCredit = Optional.of(credit);
        Boolean expected = true;

        when(creditRepository.findById(1L)).thenReturn(optionalCredit);

        Boolean actual = creditService.deleteCredit(1L);
        assertEquals(expected,actual);
    }

    @Test
    void deleteCredit_NotFoundException() {

        when(creditRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,() -> creditService.deleteCredit(1L));


    }

    @Test
    void queryCredit_CreditScore_LessThan500() {
        Customer customer = new Customer
                (1L,"Customer","Customer", 5000L,"12345678912",450,null);
        Credit credit = new Credit(1L,"RED",0);
        Map<String,String> expected = new HashMap<>();
        expected.put("message","RED");
        expected.put("creditLimit","0");

        when(customerService.getCustomer(1L)).thenReturn(customer);
        when(creditRepository.findById(1L)).thenReturn(Optional.of(credit));

        Map<String, String> actual = creditService.queryCredit(1L);
        verify(creditRepository,times(1)).save(credit);
        assertEquals(expected.get("message"),actual.get("message"));
        assertEquals(expected.get("creditLimit"),actual.get("creditLimit"));
    }

    @Test
    void queryCredit_CreditScore_LessThan1000AndBiggerEqual500_CreditLimit10000() {
        Customer customer = new Customer
                (1L,"Customer","Customer", 4999L,"12345678912",501,null);
        Credit credit = new Credit(1L,"ONAY",10000);
        Map<String,String> expected = new HashMap<>();
        expected.put("message","ONAY");
        expected.put("creditLimit","10000");

        when(customerService.getCustomer(1L)).thenReturn(customer);
        when(creditRepository.findById(1L)).thenReturn(Optional.of(credit));

        Map<String, String> actual = creditService.queryCredit(1L);
        verify(creditRepository,times(1)).save(credit);
        assertEquals(expected.get("message"),actual.get("message"));
        assertEquals(expected.get("creditLimit"),actual.get("creditLimit"));
    }

    @Test
    void queryCredit_CreditScore_LessThan1000AndBiggerEqual500_CreditLimit20000() {
        Customer customer = new Customer
                (1L,"Customer","Customer", 5000L,"12345678912",501,null);
        Credit credit = new Credit(1L,"ONAY",20000);
        Map<String,String> expected = new HashMap<>();
        expected.put("message","ONAY");
        expected.put("creditLimit","20000");

        when(customerService.getCustomer(1L)).thenReturn(customer);
        when(creditRepository.findById(1L)).thenReturn(Optional.of(credit));

        Map<String, String> actual = creditService.queryCredit(1L);
        verify(creditRepository,times(1)).save(credit);
        assertEquals(expected.get("message"),actual.get("message"));
        assertEquals(expected.get("creditLimit"),actual.get("creditLimit"));
    }

    @Test
    void queryCredit_CreditScore_BiggerEqualThan1000() {
        Customer customer = new Customer
                (1L,"Customer","Customer", 5000L,"12345678912",1000,null);
        Credit credit = new Credit(1L,"ONAY",20000);
        Map<String,String> expected = new HashMap<>();
        expected.put("message","ONAY");
        expected.put("creditLimit","20000");

        when(customerService.getCustomer(1L)).thenReturn(customer);
        when(creditRepository.findById(1L)).thenReturn(Optional.of(credit));

        Map<String, String> actual = creditService.queryCredit(1L);
        verify(creditRepository,times(1)).save(credit);
        assertEquals(expected.get("message"),actual.get("message"));
        assertEquals(expected.get("creditLimit"),actual.get("creditLimit"));
    }
}