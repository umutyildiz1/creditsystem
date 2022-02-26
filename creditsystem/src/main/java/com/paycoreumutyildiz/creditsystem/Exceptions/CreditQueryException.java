package com.paycoreumutyildiz.creditsystem.Exceptions;

public class CreditQueryException extends RuntimeException{

    public CreditQueryException(String s) {
        super(s + " TC numaralı müşteri kredi sorgusu başarılı olamadı.");
    }
}
