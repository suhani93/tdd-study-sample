package com.example.tdd.service;

import com.example.tdd.exceptions.DiscountRateException;
import com.example.tdd.interfaces.DiscountPolicy;

public class PercentageDiscountPolicyService implements DiscountPolicy {

    @Override
    public int getDiscountPrice(int finalProductPrice, int percentage) {
        if(percentage < 0){
            throw new DiscountRateException("할인율이 0보다 작을수 없습니다.");
        }

        if(percentage > 100){
            throw new DiscountRateException("할인율이 100보다 클수 없습니다.");
        }

        if(finalProductPrice < 10000){
            return finalProductPrice;
        }

        return ((finalProductPrice * (100 - percentage)) /100)  ;
    }
}
