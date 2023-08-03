package com.ecommerce.appEcommerce.repository;

import com.ecommerce.appEcommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
