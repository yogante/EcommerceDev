package com.dev.eCommerce.controller;

import com.dev.eCommerce.dto.OrderRequest;
import com.dev.eCommerce.dto.Response;
import com.dev.eCommerce.enums.OrderStatus;
import com.dev.eCommerce.service.interf.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemService orderItemService;

    @PostMapping("/create")
    public ResponseEntity<Response> placeOrder(@RequestBody OrderRequest orderRequest){
        return ResponseEntity.ok(orderItemService.placeOrder(orderRequest));
    }

    @PutMapping("/update-item-status/{orderItemId}")
    public ResponseEntity<Response> updateOrderItemStatus(@PathVariable Long orderItemId, @RequestParam String status){
        return ResponseEntity.ok(orderItemService.updateOrderItemStatus(orderItemId, status));
    }


    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('ADMIN')")
    public  ResponseEntity<Response> filterOrderItems(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long itemId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1000") int size
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        OrderStatus orderStatus = status !=null ? OrderStatus.valueOf(status.toUpperCase()) : null;

        return ResponseEntity.ok(orderItemService.filterOrderItems(orderStatus, startDate, endDate, itemId, pageable));
    }


}
