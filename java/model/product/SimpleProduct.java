package model.product;

import java.util.UUID;

public class SimpleProduct extends Product {

    private int price;

    public SimpleProduct(String name, int price, UUID id) {
        super(name, id);
        if (price <= 0){
            throw new IllegalArgumentException("Цена продукта должна быть строго больше 0");
        }
        this.price = price;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public String getFormattedInfo() {
        return "<" + getName() + ">" + ":" + "<" + price + ">";
    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    @Override
    public UUID getId() {
        return super.getId();
    }
}
