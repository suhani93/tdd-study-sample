package com.example.tdd.exceptions;

public class DiscountRateException extends RuntimeException{
    public DiscountRateException(String message) {
        super(message);
    }
}
