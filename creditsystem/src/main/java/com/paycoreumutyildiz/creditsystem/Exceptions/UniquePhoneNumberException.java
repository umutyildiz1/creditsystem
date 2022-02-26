package com.paycoreumutyildiz.creditsystem.Exceptions;

public class UniquePhoneNumberException extends RuntimeException {
    public UniquePhoneNumberException(String s) {
        super(s + " telefon numarası başka bir kullanıcıya ait!");
    }
}
