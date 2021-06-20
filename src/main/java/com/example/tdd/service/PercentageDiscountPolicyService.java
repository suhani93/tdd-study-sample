package com.example.tdd.service;

import com.example.tdd.interfaces.DiscountPolicy;

public class PercentageDiscountPolicyService implements DiscountPolicy {

    @Override
    public int getDiscountPrice(int finalProductPrice, int percentage) {
        if(finalProductPrice < 10000){
            return finalProductPrice;
        }

        return ((finalProductPrice * (100 - percentage)) /100)  ;
    }
}
