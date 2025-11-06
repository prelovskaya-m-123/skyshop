package org.skypro.skyshop.model.product;

import java.util.UUID;

public class DiscountedProduct extends Product {
    private int basePrice;
    private int discountInWholePercentages;

    public DiscountedProduct(String name, int basePrice, int discountInWholePercentages, UUID id) {
        super(name, id);
        if (basePrice <= 0){
            throw new IllegalArgumentException("Базовая цена продукта должна быть строго больше 0");
        }
        if (discountInWholePercentages < 0 || discountInWholePercentages > 100){
            throw new IllegalArgumentException ("Размер скидки должен быть в диапазоне от 0 до 100 включительно");
        }
        this.basePrice = basePrice;
        this.discountInWholePercentages = discountInWholePercentages;
    }


    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public int getDiscountInWholePercentages() {
        return discountInWholePercentages;
    }


    public void setDiscountInWholePercentages(int discountInWholePercentages) {
        this.discountInWholePercentages = discountInWholePercentages;
    }


    @Override
    public int getPrice() {
        double discountedPrice = basePrice * (1 - (discountInWholePercentages / 100.0));
        return (int) Math.round(discountedPrice);
    }


    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String getFormattedInfo() {
        return "<" + getName() + ">" + ":" + "<" + getPrice() + ">" + "(" + "<" + discountInWholePercentages + ">" + "%" + ")";
    }

}

