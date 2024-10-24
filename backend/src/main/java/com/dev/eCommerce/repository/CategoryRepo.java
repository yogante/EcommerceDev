package com.dev.eCommerce.repository;

import com.dev.eCommerce.entity.Address;
import com.dev.eCommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
}
