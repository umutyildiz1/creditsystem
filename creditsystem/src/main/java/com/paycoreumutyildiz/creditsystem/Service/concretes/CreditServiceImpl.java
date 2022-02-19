package com.paycoreumutyildiz.creditsystem.Service.concretes;

import com.paycoreumutyildiz.creditsystem.Exceptions.CreditQueryException;
import com.paycoreumutyildiz.creditsystem.Exceptions.NotFoundException;
import com.paycoreumutyildiz.creditsystem.Model.Credit;
import com.paycoreumutyildiz.creditsystem.Model.Customer;
import com.paycoreumutyildiz.creditsystem.Repository.CreditRepository;
import com.paycoreumutyildiz.creditsystem.Repository.CustomerRepository;
import com.paycoreumutyildiz.creditsystem.Service.abstracts.CreditService;
import com.paycoreumutyildiz.creditsystem.Service.abstracts.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
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
        }catch (Exception exception){
            throw new NotFoundException("Customer with sid:"+credit.getSid());
        }

    }

    @Override
    public Credit updateCredit(Credit credit) {
        return creditRepository.save(credit);
    }

    @Override
    public boolean deleteCredit(Long sid) {
        Optional<Credit> credit = Optional.of(getCredit(sid));
        if(credit.isPresent()){
            creditRepository.delete(credit.get());
            return true;
        }
        return false;
    }

    //credit business function for defining and saving customer credit limit and response
    @Override
    public Map<String ,String> queryCredit(Long sid){//loglarda info sms bilgisi

        Customer customer = customerService.getCustomer(sid);
        Long customerSalary = customer.getSalary();
        Integer customerCreditScore = customer.getCreditScore();
        Map<String, String> creditInfoMap = new HashMap<>();
        final Integer CREDIT_LIMIT_MULTIPLIER = 4;
        Credit credit;

        if (customerCreditScore < 500){

            addCredit(new Credit(sid,"RED",0));
            credit = getCredit(sid);
            creditInfoMap.put("message",credit.getCreditResult());
            creditInfoMap.put("creditLimit",credit.getCreditLimit().toString());
            return creditInfoMap;

        }else if (customerCreditScore >= 500 && customerCreditScore < 1000 && customerSalary < 5000){

            addCredit(new Credit(sid,"ONAY",10000));
            credit = getCredit(sid);
            creditInfoMap.put("message",credit.getCreditResult());
            creditInfoMap.put("creditLimit",credit.getCreditLimit().toString());
            return creditInfoMap;

        }else if (customerCreditScore >= 500 && customerCreditScore < 1000 && customerSalary >= 5000){

            addCredit(new Credit(sid,"ONAY",20000));
            credit = getCredit(sid);
            creditInfoMap.put("message",credit.getCreditResult());
            creditInfoMap.put("creditLimit",credit.getCreditLimit().toString());
            return creditInfoMap;

        }else if (customerCreditScore >= 1000){

            Integer creditLimit = (int) (customerSalary * CREDIT_LIMIT_MULTIPLIER);
            addCredit(new Credit(sid,"ONAY", creditLimit));
            credit = getCredit(sid);
            creditInfoMap.put("message", credit.getCreditResult());
            creditInfoMap.put("creditLimit", credit.getCreditLimit().toString());
            return creditInfoMap;

        }

        throw new CreditQueryException(sid.toString());
    }


}
