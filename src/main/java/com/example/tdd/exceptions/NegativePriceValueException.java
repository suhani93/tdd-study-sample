package com.example.tdd.exceptions;

public class NegativePriceValueException extends RuntimeException{

    public NegativePriceValueException(String message) {
        super(message);
    }
}
