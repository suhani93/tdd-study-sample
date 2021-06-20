package com.example.tdd.entity;

public class Product {
    private int price;
    private int vat;

    protected Product(int price, int vat) {
        this.price = price;
        this.vat = vat;
    }

    public int getFinalProductPrice() {
        return 0;
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
            this.price = price;
            return this;
        }

        public Product.ProductBuilder vat(final int vat) {
            this.vat = vat;
            return this;
        }

        public Product build() {
            return new Product(this.price, this.vat);
        }

        public String toString() {
            return "Product.ProductBuilder(price=" + this.price + ", vat=" + this.vat + ")";
        }
    }
}
