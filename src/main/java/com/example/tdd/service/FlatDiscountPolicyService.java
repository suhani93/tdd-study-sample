package com.example.tdd.service;

import com.example.tdd.exceptions.DiscountRateException;
import com.example.tdd.interfaces.DiscountPolicy;

public class FlatDiscountPolicyService implements DiscountPolicy {

    @Override
    public int getDiscountPrice(int finalProductPrice, int discountPrice){
        if(finalProductPrice <= 1000 ){
            return finalProductPrice;
        }

        if(finalProductPrice < discountPrice){
            throw new DiscountRateException("상품의 최종가보다 할인 금액이 클 수 없습니다.");
        }

        return finalProductPrice - discountPrice;
    };
}
