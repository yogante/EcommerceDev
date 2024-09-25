package com.dev.eCommerce.service.interf;

import com.dev.eCommerce.dto.LoginRequest;
import com.dev.eCommerce.dto.Response;
import com.dev.eCommerce.dto.UserDto;
import com.dev.eCommerce.entity.User;

public interface UserService {
    Response registerUser(UserDto registrationRequest);

    Response loginUser(LoginRequest loginRequest);

    Response getAllUsers();

    User getLoginUser();

    Response getUserInfoAndOrderHistory();


}
