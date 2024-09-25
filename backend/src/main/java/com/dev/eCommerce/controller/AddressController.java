package com.dev.eCommerce.controller;


import com.dev.eCommerce.dto.AddressDto;
import com.dev.eCommerce.dto.Response;
import com.dev.eCommerce.service.interf.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping("/save")
    private ResponseEntity<Response> saveAndUpdateAddress(@RequestBody AddressDto addressDto){
        return ResponseEntity.ok( addressService.saveAndUpdateAddress(addressDto));
    }


}
