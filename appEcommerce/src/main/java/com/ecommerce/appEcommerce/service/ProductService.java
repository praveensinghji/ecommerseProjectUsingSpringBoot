package com.ecommerce.appEcommerce.service;

import com.ecommerce.appEcommerce.model.Category;
import com.ecommerce.appEcommerce.model.Product;
import com.ecommerce.appEcommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> getAllProduct(){
        List<Product> products = productRepository.findAll();
        return products;
    }

    public void addProduct(Product product){
        productRepository.save(product);
    }

    public void removeProductById(long id){
        productRepository.deleteById(id);
    }

    public Optional<Product> getProductById(long id){
        return productRepository.findById(id);
    }

    public List<Product> getAllProductsByCategory(int id){
        return productRepository.findAllByCategory_Id(id);
    }
}
