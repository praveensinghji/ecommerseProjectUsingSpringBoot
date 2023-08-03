package com.ecommerce.appEcommerce.service;

import com.ecommerce.appEcommerce.model.User;
import com.ecommerce.appEcommerce.user.WebUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public User findByUserName(String userName);

    void save (WebUser webUser);
}
