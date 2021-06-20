package com.example.tdd.entity;

import com.example.tdd.exceptions.DiscountRateException;
import com.example.tdd.exceptions.NegativePriceValueException;

public class Product {
    private int price;
    private int vat;

    protected Product(int price, int vat) {
        this.price = price;
        this.vat = vat;
    }

    public int getFinalProductPrice() {
        return price + vat;
    }


    public static Product.ProductBuilder builder() {
        return new Product.ProductBuilder();
    }

    public static class ProductBuilder {
        private int price;
        private int vat;

        ProductBuilder() {
        }

        public Product.ProductBuilder price(final int price) {
            if(isNegativePriceValue(price)){
                throw new NegativePriceValueException("가격에 음수값은 올 수 없습니다.");
            }
            this.price = price;
            return this;
        }

        public Product.ProductBuilder vat(final int vat) {
            if(isNegativePriceValue(vat)){
                throw new NegativePriceValueException("부가세에 음수값은 올 수 없습니다.");
            }
            this.vat = vat;
            return this;
        }

        public Product build() {
            return new Product(this.price, this.vat);
        }

        public String toString() {
            return "Product.ProductBuilder(price=" + this.price + ", vat=" + this.vat + ")";
        }

        /**
         * 설정 값이 음수인지 체크하는 메소드
         * @param value
         * @return
         */
        private boolean isNegativePriceValue(int value){
            return 0 > value;
        }
    }

}
