package com.paycoreumutyildiz.creditsystem.Service.concretes;

import com.paycoreumutyildiz.creditsystem.Exceptions.CreditQueryException;
import com.paycoreumutyildiz.creditsystem.Exceptions.NotFoundException;
import com.paycoreumutyildiz.creditsystem.Model.Credit;
import com.paycoreumutyildiz.creditsystem.Model.Customer;
import com.paycoreumutyildiz.creditsystem.Repository.CreditRepository;
import com.paycoreumutyildiz.creditsystem.Service.abstracts.CreditService;
import com.paycoreumutyildiz.creditsystem.Service.abstracts.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;
    private final CustomerService customerService;

    @Autowired
    public CreditServiceImpl(CreditRepository creditRepository, CustomerService customerService) {
        this.creditRepository = creditRepository;
        this.customerService = customerService;
    }

    @Override
    public List<Credit> getAllCredits() {
        return creditRepository.findAll();
    }

    @Override
    public Credit getCredit(Long sid) {
        Optional<Credit> credit = creditRepository.findById(sid);
        return credit.orElseThrow(() -> new NotFoundException("Credit"));
    }

    @Override
    public void addCredit(Credit credit) {
        try{
            creditRepository.save(credit);
            log.info("Success adding Credit");
        }catch (Exception exception){
            throw new NotFoundException("Customer with sid:"+credit.getSid());
        }

    }

    @Override
    public Credit updateCredit(Credit credit) {
        getCredit(credit.getSid());
        log.info("Success update");
        return creditRepository.save(credit);
    }

    @Override
    public boolean deleteCredit(Long sid) {
        Optional<Credit> credit = Optional.of(getCredit(sid));
        log.info(credit.get().toString());
        creditRepository.delete(credit.get());
        return true;
    }

    //credit business function for defining and saving customer credit limit and response
    @Override
    public Map<String ,String> queryCredit(Long sid){

        Customer customer = customerService.getCustomer(sid);
        log.info(customer.toString());

        Long customerSalary = customer.getSalary();
        Integer customerCreditScore = customer.getCreditScore();

        final Integer CREDIT_LIMIT_MULTIPLIER = 4;


        if (customerCreditScore < 500){

            return saveCreditQueryAndReturn(sid,"RED",0,customer.getPhoneNumber());

        }else if (customerCreditScore >= 500 && customerCreditScore < 1000 && customerSalary < 5000){

            return saveCreditQueryAndReturn(sid,"ONAY",10000, customer.getPhoneNumber());

        }else if (customerCreditScore >= 500 && customerCreditScore < 1000 && customerSalary >= 5000){

            return saveCreditQueryAndReturn(sid,"ONAY",20000, customer.getPhoneNumber());

        }else if (customerCreditScore >= 1000){

            Integer creditLimit = (int) (customerSalary * CREDIT_LIMIT_MULTIPLIER);

            return saveCreditQueryAndReturn(sid,"ONAY",creditLimit,customer.getPhoneNumber());

        }

        throw new CreditQueryException(sid.toString());
    }

    private Map<String,String> saveCreditQueryAndReturn(Long sid,String creditResult,Integer creditLimit,String phoneNumber){
        Map<String,String> creditInfoMap = new HashMap<>();
        Credit credit;

        addCredit(new Credit(sid,creditResult,creditLimit));
        credit = getCredit(sid);
        log.info(credit.toString());
        creditInfoMap.put("message",credit.getCreditResult());
        creditInfoMap.put("creditLimit",credit.getCreditLimit().toString());
        log.info(phoneNumber + " numaralı telefona sms gönderildi.");
        return creditInfoMap;
    }

}
