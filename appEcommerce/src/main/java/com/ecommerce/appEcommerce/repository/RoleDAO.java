package com.ecommerce.appEcommerce.repository;


import com.ecommerce.appEcommerce.model.Role;

public interface RoleDAO  {
    public Role findRoleByName(String theRoleName);
}
