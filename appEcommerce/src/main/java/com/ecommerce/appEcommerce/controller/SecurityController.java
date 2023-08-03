package com.ecommerce.appEcommerce.controller;

import com.ecommerce.appEcommerce.global.GlobalData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping("/login")
    public String showMyLoginForm(){
        GlobalData.cart.clear();
        return "login";
    }

    @GetMapping("/logout")
    public String showPageWithLogout(){
        return "login";
    }

}
