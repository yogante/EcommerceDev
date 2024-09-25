package com.dev.eCommerce.service.interf;

import com.dev.eCommerce.dto.AddressDto;
import com.dev.eCommerce.dto.Response;

public interface AddressService {
    Response saveAndUpdateAddress(AddressDto addressDto);

}
