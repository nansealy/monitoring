package com.sahno.service;

import com.sahno.model.entity.business.User;

public interface UserService {
    boolean existsWith(String email, String password);

    void create(User user);
}
