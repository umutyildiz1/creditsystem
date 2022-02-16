package com.paycoreumutyildiz.creditsystem.Service.abstracts;

import com.paycoreumutyildiz.creditsystem.Model.Credit;
import com.paycoreumutyildiz.creditsystem.Model.Customer;

import java.util.List;
import java.util.Optional;

public interface CreditService {

    List<Credit> getAllCredits();
    Credit getCredit(Long sid);
    void addCredit(Credit credit);
    Credit updateCredit(Credit credit);
    boolean deleteCredit(Long sid);
}
