package com.example.tdd.service;

import com.example.tdd.interfaces.DiscountPolicy;

public class FlatDiscountPolicyService implements DiscountPolicy {

    @Override
    public int getDiscountPrice(int finalProductPrice, int amount){
        return 0;
    };
}