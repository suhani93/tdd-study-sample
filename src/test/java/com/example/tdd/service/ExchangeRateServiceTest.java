package com.example.tdd.service;

import com.example.tdd.entity.Product;
import com.example.tdd.exceptions.ExchangeRateException;
import com.example.tdd.interfaces.DiscountPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ExchangeRateServiceTest {


    @DisplayName("원달러 환율 적용")
    @Nested
    public class WonDollarExchangeRate {
        @Test
        @DisplayName("1달러당 1123.23원 - 소수 셋째자리 올림 테스트")
        void roundWonDollarExchangeRate() {
            int productFinalPrice = getProductFinalPrice();

            DollarExchangeRateService dollarExchangeRateService = new DollarExchangeRateService();
            double exchangeRate = 1123.23;
            double priceWithExchangeRate = dollarExchangeRateService.getPriceWithExchangeRate(productFinalPrice, exchangeRate);

            Assertions.assertEquals(8.82, priceWithExchangeRate,"달러당 1123.23원을 적용해서 8.82달러가 나와야 하는데 8.82달러가 아닙니다.");
        }

        @Test
        @DisplayName("1달러당 1000원")
        void wonDollarExchangeRate() {
            int productFinalPrice = getProductFinalPrice();

            DollarExchangeRateService dollarExchangeRateService = new DollarExchangeRateService();
            int exchangeRate = 1000;
            double priceWithExchangeRate = dollarExchangeRateService.getPriceWithExchangeRate(productFinalPrice, exchangeRate);

            Assertions.assertEquals(8.82, priceWithExchangeRate,"달러당 1000원을 적용해서 9.90달러가 나와야 하는데 9.90달러가 아닙니다.");
        }

        @Test
        @DisplayName("1달러가 음수인 경우")
        void underZeroExchangeRate() {
            int productFinalPrice = getProductFinalPrice();

            DollarExchangeRateService dollarExchangeRateService = new DollarExchangeRateService();
            int exchangeRate = -1;

            Assertions.assertThrows(ExchangeRateException.class , ()->{
                dollarExchangeRateService.getPriceWithExchangeRate(productFinalPrice, exchangeRate);
            },"달러 환율은 음수일 수 없습니다.");

        }

    }

    @DisplayName("원유로 환율 적용")
    @Nested
    public class WonEuroExchangeRate {
        @Test
        @DisplayName("1유로당 1346.90원 - 소수 셋째자리 올림 테스트")
        void roundWonEuroExchangeRate() {
            int productFinalPrice = getProductFinalPrice();

            EuroExchangeRateService euroExchangeRateService = new EuroExchangeRateService();
            double exchangeRate = 1346.90;
            double priceWithExchangeRate = euroExchangeRateService.getPriceWithExchangeRate(productFinalPrice, exchangeRate);

            Assertions.assertEquals(7.35, priceWithExchangeRate,"유로당 1346.90원을 적용해서 7.35달러가 나와야 하는데 7.35달러가 아닙니다.");
        }

        @Test
        @DisplayName("1유로당 1300원")
        void wonEuroExchangeRate() {
            int productFinalPrice = getProductFinalPrice();

            EuroExchangeRateService euroExchangeRateService = new EuroExchangeRateService();
            int exchangeRate = 1300;
            double priceWithExchangeRate = euroExchangeRateService.getPriceWithExchangeRate(productFinalPrice, exchangeRate);

            Assertions.assertEquals(7.62, priceWithExchangeRate,"유로당 1300원을 적용해서 7.62달러가 나와야 하는데 7.62달러가 아닙니다.");
        }


        @Test
        @DisplayName("1유로가 음수인 경우")
        void underZeroExchangeRate() {
            int productFinalPrice = getProductFinalPrice();

            EuroExchangeRateService euroExchangeRateService = new EuroExchangeRateService();
            int exchangeRate = -1;


            Assertions.assertThrows(ExchangeRateException.class , ()->{
                euroExchangeRateService.getPriceWithExchangeRate(productFinalPrice, exchangeRate);
            },"유로 환율은 음수일 수 없습니다.");

        }
    }


    private int getProductFinalPrice() {
        Product product = Product.builder()
                .price(10000)
                .vat(1000)
                .build();
        DiscountPolicy discountPolicy = new PercentageDiscountPolicyService();

        int discountPercentage = 10;
        return discountPolicy.getDiscountPrice(product.getFinalProductPrice(), discountPercentage);
    }
}