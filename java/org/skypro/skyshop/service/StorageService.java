package org.skypro.skyshop.service;

import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class StorageService {

    private final Map<UUID, Product> availableProducts;

    private final Map<UUID, Article> articlesStorage;

    public StorageService() {
        this.availableProducts = new ConcurrentHashMap<>();
        this.articlesStorage = new ConcurrentHashMap<>();

        fillWithTestData();
    }

    private void fillWithTestData() {
        UUID productId1 = UUID.randomUUID();
        availableProducts.put(productId1,
                new SimpleProduct("Школьный рюкзак Brauberg Premium", 5200, productId1));

        UUID productId2 = UUID.randomUUID();
        availableProducts.put(productId2,
                new SimpleProduct("Мешок для сменной обуви", 290, productId2));

        UUID productId3 = UUID.randomUUID();
        availableProducts.put(productId3,
                new SimpleProduct("Смартфон Realme Note 50", 5320 , productId3));

        UUID productId4 = UUID.randomUUID();
        availableProducts.put(productId4,
                new SimpleProduct("Пауэрбанк Xiaomi Mi Power Bank 3", 2490, productId4));

        UUID productId5 = UUID.randomUUID();
        availableProducts.put(productId5,
                new SimpleProduct("Школьный костюм для мальчика", 3700, productId5));


        UUID articleId1 = UUID.randomUUID();
        articlesStorage.put(articleId1,
                new Article("Как выбрать рюкзак для школьника", "При выборе школьного рюкзака обратите внимание на вес, наличие ортопедической спинки с мягкой подкладкой, широкие лямки с возможностью регулировки", articleId1));

        UUID articleId2 = UUID.randomUUID();
        articlesStorage.put(articleId2,
                new Article("Пауэрбанк для школьника", "При выборе пауэрбанка обращайте внимание на ёмкость (10000-20000 мАч), разъёмы (наличие USB-A и USB-C), скорость зарядки", articleId2));

        UUID articleId3 = UUID.randomUUID();
        articlesStorage.put(articleId3,
                new Article("Как выбрать смартфон для первоклассника", "Рейтинг топ-15 недорогих и мощных телефонов для детей в 2025 году", articleId3));

        UUID articleId4 = UUID.randomUUID();
        articlesStorage.put(articleId4,
                new Article("Школьный костюм", "При выборе школьной формы важно учитывать несколько ключевых критериев: размер и посадка, качество материалов, удобство и функциональность", articleId4));

        UUID articleId5 = UUID.randomUUID();
        articlesStorage.put(articleId5,
                new Article("Школьный рюкзак", "Топ-20 лучших школьных рюкзаков 2025 года", articleId5));

        UUID articleId6 = UUID.randomUUID();
        articlesStorage.put(articleId6, new Article("", "", articleId6));

        UUID articleId7 = UUID.randomUUID();
        articlesStorage.put(articleId7, new Article("А", "А", articleId7));
    }

    public Product getProductById(UUID id) {
        if (!availableProducts.containsKey(id)) {
            throw new NoSuchProductException("Продукт с ID " + id + " не найден");
        }
        return availableProducts.get(id);
    }

    public Collection<Product> getAllProducts() {
        return availableProducts.values();
    }

    public Collection<Article> getAllArticles() {
        return articlesStorage.values();
    }

    public Collection<Searchable> getAllSearchable() {

        List<Searchable> allSearchable = new ArrayList<>();
        allSearchable.addAll(availableProducts.values());
        allSearchable.addAll(articlesStorage.values());
        return allSearchable;
    }
}