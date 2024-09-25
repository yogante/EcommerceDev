package com.dev.eCommerce.repository;

import com.dev.eCommerce.entity.Order;
import com.dev.eCommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
