package com.dev.eCommerce.repository;

import com.dev.eCommerce.entity.Address;
import com.dev.eCommerce.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long>,
        JpaSpecificationExecutor<OrderItem> {


}
