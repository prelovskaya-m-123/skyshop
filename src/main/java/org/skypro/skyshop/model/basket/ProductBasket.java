package org.skypro.skyshop.model.basket;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import org.skypro.skyshop.model.product.Product;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@SessionScope
public class ProductBasket {

    private final Map<UUID, List<Product>> userBaskets = new ConcurrentHashMap<>();

    public boolean containsProduct(UUID userId, UUID productId) {
        return userBaskets.getOrDefault(userId, Collections.emptyList())
                .stream()
                .anyMatch(p -> p.getId().equals(productId));
    }

    public void addProduct(UUID userId, Product product) {
        userBaskets.computeIfAbsent(userId, k -> new ArrayList<>()).add(product);
    }

    public List<Product> getProducts(UUID userId) {
        return Collections.unmodifiableList(
                userBaskets.getOrDefault(userId, Collections.emptyList())
        );
    }
}