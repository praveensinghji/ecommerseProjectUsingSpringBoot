package com.ecommerce.appEcommerce.controller;

import com.ecommerce.appEcommerce.dto.ProductDTO;
import com.ecommerce.appEcommerce.model.Category;
import com.ecommerce.appEcommerce.model.Product;
import com.ecommerce.appEcommerce.service.CategoryService;
import com.ecommerce.appEcommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {

    private CategoryService categoryService;

    private ProductService productService;

    @Autowired
    public AdminController(CategoryService categoryService,ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }


    @GetMapping("/admin")
    public String adminHome(){
        return "adminHome";
    }

    @GetMapping("/admin/categories")
    public String getCategoriesPage(Model model){
        model.addAttribute("categories",categoryService.getAllCategory());
        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String getCategoriesAdd(Model model){
        Category category = new Category();
        model.addAttribute("category", category);
        return "categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String postCategoriesAdd(@ModelAttribute("category") Category category){
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String getCategoryUpdate(@PathVariable int id, Model model){
        Optional<Category> category1= categoryService.getCategoryById(id);
        if(category1.isPresent()){
            model.addAttribute("category", category1.get());
            return "categoriesAdd";
        }else{
            return "id not found";
        }
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String getDeleteCategory(@PathVariable int id){
        categoryService.removeCategoryById(id);
        return "redirect:/admin/categories";
    }

    /*
        Product Section
    */

    @GetMapping("/admin/products")
    public String getProductsPage(Model model){
        model.addAttribute("products",productService.getAllProduct());
        return "products";
    }

    @GetMapping("/admin/products/add")
    public String getProductsAdd(Model model){
        ProductDTO productDTO = new ProductDTO();
        model.addAttribute("productDTO", productDTO);
        model.addAttribute("categories", categoryService.getAllCategory());
        return "productsAdd";
    }

    @PostMapping("/admin/products/add")
    public String postProductAdd(@ModelAttribute("productDTO") ProductDTO productDTO,
                                 @RequestParam("productImage")MultipartFile file,
                                 @RequestParam("imgName") String imgName) throws IOException {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setWeight(productDTO.getWeight());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
        //get() because return type is Optional
        //Image UUID is not use but we should use this for real project.
        // We are working with direct image Name
        String imageUUID;
        if(!file.isEmpty()){
            imageUUID = file.getOriginalFilename();
            String uplodeDir = uplodeDir =
                    System.getProperty("user.dir") + "/src/main/resources/static/ProductImages";
            Path fileNameAndPath = Paths.get(uplodeDir, imageUUID);
            Files.write(fileNameAndPath,file.getBytes());
        } else {
            imageUUID = imgName;
        }
        product.setImageName(imageUUID);
        productService.addProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getProductUpdate(@PathVariable long id, Model model){
        Product product= productService.getProductById(id).get();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setWeight(product.getWeight());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setImageName(product.getImageName());

        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("productDTO", productDTO);
        return "productsAdd";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProduct(@PathVariable int id){
        productService.removeProductById(id);
        return "redirect:/admin/products";
    }
}
