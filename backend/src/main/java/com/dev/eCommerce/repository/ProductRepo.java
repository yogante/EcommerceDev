package com.dev.eCommerce.repository;

import com.dev.eCommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findByCategoryId(Long categoryId);

    // ToDo Name　検索かからない。
    List<Product> findByNameOrDescriptionContaining(String name, String description);

}
