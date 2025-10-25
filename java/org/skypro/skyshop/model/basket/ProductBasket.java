package org.skypro.skyshop.model.basket;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@SessionScope
public class ProductBasket {

    private final Map<UUID, Integer> productsMap = new ConcurrentHashMap<>();

    public void addProduct(UUID id) {
        productsMap.merge(id, 1, Integer::sum);
    }

    public Map<UUID, Integer> getProducts() {
        return Collections.unmodifiableMap(new HashMap<>(productsMap));
    }
}
