package com.ecommerce.appEcommerce.global;

import com.ecommerce.appEcommerce.model.Product;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {
    public static List<Product> cart;

    static {
        cart = new ArrayList<>();
    }
}
