package org.skypro.skyshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class BasketServiceTest {

    @Mock
    private ProductBasket productBasket;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private BasketService basketService;

    private UUID productId;
    private UUID userId;
    private Product product;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();
        userId = UUID.randomUUID();
        product = new SimpleProduct("Тест-товар", 1000, productId);
    }

    // 1) Добавление несуществующего товара → исключение NoSuchProductException
    @Test
    void addProductToBasket_whenProductDoesNotExist_shouldThrowNoSuchProductException() {

        when(storageService.getProductById(productId))
                .thenThrow(new NoSuchProductException("Продукт не найден"));

        assertThrows(NoSuchProductException.class, () -> {
            basketService.addProductToBasket(productId);
        });

        verify(productBasket, never()).addProduct(any(UUID.class), any(Product.class));
    }

    // 2) Добавление существующего товара → вызов addProduct у ProductBasket
    @Test
    void addProductToBasket_whenProductExists_shouldCallAddProductOnBasket() {

        when(storageService.getProductById(productId)).thenReturn(product);

        basketService.addProductToBasket(productId);

        verify(productBasket, times(1)).addProduct(productId, product);
    }

    // 3) getUserBasket → пустая корзина, если ProductBasket пуст
    @Test
    void getUserBasket_whenBasketIsEmpty_shouldReturnEmptyUserBasket() {

        when(productBasket.getProducts(userId)).thenReturn(Collections.emptyList());

        when(storageService.getProductById(any(UUID.class))).thenReturn(product);

        UserBasket basket = basketService.getUserBasket(userId);

        assertTrue(basket.getItems().isEmpty());
        assertEquals(0.0, basket.getTotal());
    }

    // 4) getUserBasket → корзина с товарами, если ProductBasket не пуст
    @Test
    void getUserBasket_whenBasketHasProducts_shouldReturnUserBasketWithItems() {

        List<Product> productsInBasket = Collections.singletonList(product);
        when(productBasket.getProducts(userId)).thenReturn(productsInBasket);

        when(storageService.getProductById(productId)).thenReturn(product);

        UserBasket basket = basketService.getUserBasket(userId);


        assertEquals(1, basket.getItems().size());


        BasketItem item = basket.getItems().get(0);
        assertEquals(product, item.getProduct());
        assertEquals(1, item.getQuantity());


        assertEquals(1000.0, basket.getTotal());
    }

    // 5) getUserBasket → несколько товаров в корзине
    @Test
    void getUserBasket_withMultipleProducts_shouldCalculateTotalCorrectly() {

        Product product2 = new SimpleProduct("Второй товар", 2000, UUID.randomUUID());
        List<Product> productsInBasket = List.of(product, product2);
        when(productBasket.getProducts(userId)).thenReturn(productsInBasket);

        when(storageService.getProductById(productId)).thenReturn(product);
        when(storageService.getProductById(product2.getId())).thenReturn(product2);

        UserBasket basket = basketService.getUserBasket(userId);

        assertEquals(2, basket.getItems().size());

        assertEquals(3000.0, basket.getTotal());
    }
}