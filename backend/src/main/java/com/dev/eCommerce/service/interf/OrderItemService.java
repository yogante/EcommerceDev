package com.dev.eCommerce.service.interf;

import com.dev.eCommerce.dto.OrderRequest;
import com.dev.eCommerce.dto.Response;
import com.dev.eCommerce.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


public interface OrderItemService {

    Response placeOrder(OrderRequest orderRequest);

    Response updateOrderItemStatus(Long orderItemId, String status);

    Response filterOrderItems(LocalDateTime startDate, LocalDateTime endDate, OrderStatus status, Long itemId, Pageable pageable);


}
