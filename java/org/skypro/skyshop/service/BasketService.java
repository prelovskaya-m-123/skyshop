package org.skypro.skyshop.service;

import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BasketService {

    private final ProductBasket productBasket;
    private final StorageService storageService;

    public BasketService(ProductBasket productBasket, StorageService storageService) {
        this.productBasket = productBasket;
        this.storageService = storageService;
    }

    public void addProductToBasket(UUID productId) {
        Optional<Product> product = storageService.getProductById(productId);

        if (!product.isPresent()) {
            throw new IllegalArgumentException("Продукт с ID " + productId + " не найден");
        }

        productBasket.addProduct(productId);
    }

    public UserBasket getUserBasket() {
        Map<UUID, Integer> productsMap = productBasket.getProducts();

        List<BasketItem> basketItems = productsMap.entrySet().stream()
                .map(entry -> {
                    UUID productId = entry.getKey();
                    int quantity = entry.getValue();

                    Optional<Product> productOptional = storageService.getProductById(productId);

                    if (!productOptional.isPresent()) {
                        throw new IllegalStateException("Продукт с ID " + productId + " был удален из хранилища");
                    }

                    return new BasketItem(productOptional.get(), quantity);
                })
                .collect(Collectors.toList());

        return new UserBasket(basketItems);
    }
}