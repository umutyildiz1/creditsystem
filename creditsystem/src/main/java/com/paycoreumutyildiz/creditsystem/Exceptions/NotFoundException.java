package com.paycoreumutyildiz.creditsystem.Exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String s) {
        super(s + " not found...!");
    }
}

