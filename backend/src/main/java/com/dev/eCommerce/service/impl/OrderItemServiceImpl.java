package com.dev.eCommerce.service.impl;

import com.dev.eCommerce.dto.OrderItemDto;
import com.dev.eCommerce.dto.OrderRequest;
import com.dev.eCommerce.dto.Response;
import com.dev.eCommerce.entity.Order;
import com.dev.eCommerce.entity.OrderItem;
import com.dev.eCommerce.entity.Product;
import com.dev.eCommerce.entity.User;
import com.dev.eCommerce.enums.OrderStatus;
import com.dev.eCommerce.exception.NotFoundException;
import com.dev.eCommerce.mapper.EntityDtoMapper;
import com.dev.eCommerce.repository.OrderItemRepo;
import com.dev.eCommerce.repository.OrderRepo;
import com.dev.eCommerce.repository.ProductRepo;
import com.dev.eCommerce.service.interf.OrderItemService;
import com.dev.eCommerce.service.interf.UserService;
import com.dev.eCommerce.specification.OrderItemSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;
    private final ProductRepo productRepo;
    private final UserService userService;
    private final EntityDtoMapper entityDtoMapper;

    @Override
    public Response placeOrder(OrderRequest orderRequest) {
        User user = userService.getLoginUser();

        // map orderRequest item to order entities
        List< OrderItem> orderItems = orderRequest.getItems().stream().map(orderItemRequest -> {
            Product product =productRepo.findById(orderItemRequest.getProductId())
                    .orElseThrow(()-> new NotFoundException("Product Not found."));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemRequest.getQuantity());
            orderItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(orderItemRequest.getQuantity())));
            orderItem.setStatus(OrderStatus.PENDING);
            orderItem.setUser(user);
            return orderItem;

        }).collect(Collectors.toList());

        // calculate the total price
        BigDecimal totalPrice = orderRequest.getTotalPrice() != null && orderRequest.getTotalPrice().compareTo(BigDecimal.ZERO) > 0
                ? orderRequest.getTotalPrice()
                : orderItems.stream().map(OrderItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        // create order entity
        Order order = new Order();

        order.setOrderItemList(orderItems);
        order.setTotalPrice(totalPrice);

        //set the order reference in each orderItem
        orderItems.forEach(orderItem -> orderItem.setOrder(order));

        orderRepo.save(order);

        return Response.builder()
                .status(200)
                .message("Order was successfully placed.")
                .build();
    }

    @Override
    public Response updateOrderItemStatus(Long orderItemId, String status) {
        OrderItem orderItem = orderItemRepo.findById(orderItemId)
                .orElseThrow(()-> new NotFoundException("Order Item not found."));

        orderItem.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        orderItemRepo.save(orderItem);

        return  Response.builder()
                .status(200)
                .message("Order status updated successfully.")
                .build();

    }

    @Override
    public Response filterOrderItems( LocalDateTime startDate, LocalDateTime endDate, OrderStatus status, Long itemId, Pageable pageable) {
        Specification<OrderItem> spec = Specification.where(OrderItemSpecification.hasStatus(status))
                .and(OrderItemSpecification.createdBetween(startDate,endDate))
                .and(OrderItemSpecification.hasItemId(itemId));

        Page<OrderItem> orderItemPage = orderItemRepo.findAll(spec, pageable);

        if (orderItemPage.isEmpty()) {
            throw new NotFoundException("No order found.");
        }
        List<OrderItemDto> orderItemDtos = orderItemPage.getContent().stream()
                .map(entityDtoMapper::mapOrderItemToDtoPlusProductAndUser)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .orderItemList(orderItemDtos)
                .totalPage(orderItemPage.getTotalPages())
                .totalElement(orderItemPage.getTotalElements())
                .build();
    }
}
