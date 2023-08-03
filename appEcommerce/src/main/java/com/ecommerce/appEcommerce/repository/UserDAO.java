package com.ecommerce.appEcommerce.repository;

import com.ecommerce.appEcommerce.model.User;

public interface UserDAO {
    User findByUserName(String theUserName);

    void save(User theUser);
}
