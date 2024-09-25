package com.dev.eCommerce.dto;

import com.dev.eCommerce.entity.Address;
import com.dev.eCommerce.entity.Order;
import com.dev.eCommerce.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private String phoneNumber;
    private String password;
    private String role;

    private List<OrderItemDto> orderItemList;
    private AddressDto address;
}
