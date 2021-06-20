package com.example.tdd.entity;

import com.example.tdd.exceptions.DiscountRateException;
import com.example.tdd.exceptions.NegativePriceValueException;
import com.example.tdd.interfaces.DiscountPolicy;
import com.example.tdd.service.FlatDiscountPolicyService;
import com.example.tdd.service.PercentageDiscountPolicyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;



class ProductTest {

    @Test
    @DisplayName("상품 최종 가격 테스트")
    void getFinalProductPrice(){
        Product product = Product.builder()
                .price(1000)
                .vat(100)
                .build();

        int finalProductPrice = product.getFinalProductPrice();
        Assertions.assertEquals(1100,finalProductPrice,"1100원을 예상했는데 1100원이 아닙니다.");
    }

    @Test
    @DisplayName("상품 가격세팅시 음수값이 들어가지나에 대한 테스트")
    void checkNegativePriceValue(){
        Assertions.assertThrows(NegativePriceValueException.class , ()->{
            Product.builder()
                    .price(-1100)
                    .vat(100)
                    .build();
        },"상품의 가격(price)가 음수인데 NegativePriceValueException이 발생하지 않았습니다.");

        Assertions.assertThrows(NegativePriceValueException.class , ()->{
            Product.builder()
                    .price(1100)
                    .vat(-100)
                    .build();
        },"상품의 부가세(vat)가 음수인데 NegativePriceValueException이 발생하지 않았습니다.");

    }


    @DisplayName("정액 상품 할인 정책 테스트")
    @Nested
    public class flatDiscountPolicy{
        @Test
        @DisplayName("상품이 1000원 이상인 경우 할인된 상품 가격 테스트")
        void overOneThousandWon(){
            Product product = Product.builder()
                    .price(1000)
                    .vat(100)
                    .build();
            DiscountPolicy discountPolicy = new FlatDiscountPolicyService();
            int discountPrice = 100;
            int productFinalPrice = discountPolicy.getDiscountPrice(product.getFinalProductPrice(), discountPrice);
            Assertions.assertEquals(1000,productFinalPrice, "1000원 이상 상품이라 정액 할인 100원 할인해서 1000원 이여야 하는데 1000원이 아닙니다.");
        }

        @Test
        @DisplayName("상품이 1000원 이하인 경우 할인된 상품 가격 테스트 - 550원인 경우")
        void underOneThousandWon(){
            Product product = Product.builder()
                    .price(500)
                    .vat(50)
                    .build();
            DiscountPolicy discountPolicy = new FlatDiscountPolicyService();
            int discountPrice = 100;
            int productFinalPrice = discountPolicy.getDiscountPrice(product.getFinalProductPrice(), discountPrice);

            Assertions.assertEquals(550,productFinalPrice, "1000원 이하 상품이라 정액 할인 안해서 550원이 정상가격인데 550원이 아닙니다.");
        }

   }

    @DisplayName("정액 상품 할인가 예외 상황 테스트")
    @Nested
    public class DiscountRateExceptionInFlatDiscountPolicy{
        @Test
        @DisplayName("할인 금액이 상품 가격보다 많은 경우")
        void discountRateHigherThenPrice(){

            Product product = Product.builder()
                    .price(1000)
                    .vat(100)
                    .build();
            DiscountPolicy discountPolicy = new FlatDiscountPolicyService();
            int discountPrice = 2000;

            Assertions.assertThrows(DiscountRateException.class,
                    ()->{
                        discountPolicy.getDiscountPrice(product.getFinalProductPrice(), discountPrice);
                    },
            "할인 금액이 상품 가격보다 많습니다.");

        }
    }

    @DisplayName("정률 상품 할인 정책 가격 테스트")
    @Nested
    public class percentageDiscountPolicy {

        @Test
        @DisplayName("상품이 10000원 이상인 경우 할인된 상품 가격 테스트")
        void overTenThousandWon() {
            Product product = Product.builder()
                    .price(10000)
                    .vat(1000)
                    .build();
            DiscountPolicy discountPolicy = new PercentageDiscountPolicyService();

            int discountPercentage = 10;
            int productFinalPrice = discountPolicy.getDiscountPrice(product.getFinalProductPrice(), discountPercentage);
            Assertions.assertEquals(9900, productFinalPrice, "상품이 만원 이상이라 정률 할인 10% 할인해서 9900원 이여야 하는데 9900원이 아닙니다.");
        }


        @Test
        @DisplayName("상품이 10000원 이하인 경우 할인된 상품 가격 테스트")
        void underTenThousandWon() {
            Product product = Product.builder()
                    .price(1000)
                    .vat(100)
                    .build();
            DiscountPolicy discountPolicy = new PercentageDiscountPolicyService();

            int discountPercentage = 10;
            int productFinalPrice = discountPolicy.getDiscountPrice(product.getFinalProductPrice(), discountPercentage);
            Assertions.assertEquals(9900, productFinalPrice, "상품이 만원 이하라 정률 할인 안해서 1100원 이여야 하는데 1100원이 아닙니다.");
        }

    }


    @DisplayName("정률 상품 할인률 예외 상황 테스트")
    @Nested
    public class DiscountRateErrorInPercentageDiscountPolicy {
        @Test
        @DisplayName("할인 퍼센트가 0퍼센트 미만 경우")
        void underZeroPercentDiscount() {
            Product product = Product.builder()
                    .price(10000)
                    .vat(1000)
                    .build();
            DiscountPolicy discountPolicy = new PercentageDiscountPolicyService();

            int discountPercentage = 0;

            Assertions.assertThrows(DiscountRateException.class,
                    ()->{
                        discountPolicy.getDiscountPrice(product.getFinalProductPrice(), discountPercentage);
                    },
                    "할인 퍼센트가 0퍼센트 미만인데 DiscountRateException이 발생하지 않았습니다."
            );
        }

        @Test
        @DisplayName("할인 퍼센트가 100퍼센트 이상인 경우")
        void overOneHundredPercentDiscount() {
            Product product = Product.builder()
                    .price(10000)
                    .vat(1000)
                    .build();
            DiscountPolicy discountPolicy = new PercentageDiscountPolicyService();

            int discountPercentage = 101;
            Assertions.assertThrows(DiscountRateException.class,
                    ()->{
                        discountPolicy.getDiscountPrice(product.getFinalProductPrice(), discountPercentage);
                    },
                    "할인 퍼센트가 100퍼센트 이상인데 DiscountRateException이 발생하지 않았습니다."
            );
        }
    }


}