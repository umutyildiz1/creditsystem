package com.paycoreumutyildiz.creditsystem.Service.concretes;

import com.paycoreumutyildiz.creditsystem.Exceptions.NotFoundException;
import com.paycoreumutyildiz.creditsystem.Model.Credit;
import com.paycoreumutyildiz.creditsystem.Model.Customer;
import com.paycoreumutyildiz.creditsystem.Repository.CreditRepository;
import com.paycoreumutyildiz.creditsystem.Service.abstracts.CreditService;
import com.paycoreumutyildiz.creditsystem.Service.abstracts.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;

    @Autowired
    public CreditServiceImpl(CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
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



}
