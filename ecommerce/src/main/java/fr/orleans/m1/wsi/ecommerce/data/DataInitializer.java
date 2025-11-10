package fr.orleans.m1.wsi.ecommerce.data;

import fr.orleans.m1.wsi.ecommerce.models.Product;
import fr.orleans.m1.wsi.ecommerce.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0){
            log.info("initialized product...");
            createProduct("Smartphone XZ Pro", "Dernier modèle avec écran OLED 6.5 pouces", 799.99, "Electronics", 50);
            createProduct("Laptop Ultra 15", "Processeur i7, 16GB RAM, 512GB SSD", 1299.99, "Electronics", 30);
            createProduct("Casque Audio Premium", "Réduction de bruit active, autonomie 30h", 249.99, "Audio", 75);
            createProduct("Montre Connectée", "Suivi fitness et santé, GPS intégré", 299.99, "Wearables", 100);
            createProduct("Tablette GraphicPro", "Parfaite pour le design, écran 12 pouces", 599.99, "Electronics", 40);
            createProduct("Enceinte Bluetooth", "Son 360° immersif, résistante à l'eau", 89.99, "Audio", 120);
            createProduct("Appareil Photo Pro", "24MP, 4K video, objectif interchangeable", 1499.99, "Electronics", 20);
            createProduct("Drone Caméra 4K", "Stabilisation 3 axes, portée 5km", 899.99, "Electronics", 15);

            log.info("Product initialized successfully");
        }
    }

    private void createProduct(String name, String desc, Double price, String category, Integer stock) {
        Product p = new Product();
        p.setName(name);
        p.setDescription(desc);
        p.setPrice(price);
        p.setCategory(category);
        p.setStock(stock);
        p.setActive(true);
        p.setImage("https://via.placeholder.com/400x300?text=" + name.replace(" ", "+"));
        productRepository.save(p);
    }
}
