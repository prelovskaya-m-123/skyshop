package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import model.article.Article;
import model.product.Product;
import service.StorageService;
import java.util.Collection;

@RestController
public class ShopController {

    // Поле для хранения сервиса
    private final StorageService storageService;

    // Внедрение зависимости через конструктор
    @Autowired
    public ShopController(StorageService storageService) {
        this.storageService = storageService;
    }

    // Метод для получения всех продуктов
    @GetMapping("/products")
    public Collection<Product> getAllProducts() {
        return storageService.getAllProducts();
    }

    // Метод для получения всех статей
    @GetMapping("/articles")
    public Collection<Article> getAllArticles() {
        return storageService.getAllArticles();
    }
}
