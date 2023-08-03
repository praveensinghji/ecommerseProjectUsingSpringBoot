package com.ecommerce.appEcommerce.controller;

import com.ecommerce.appEcommerce.model.User;
import com.ecommerce.appEcommerce.service.UserService;
import com.ecommerce.appEcommerce.user.WebUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.logging.Logger;

@Controller
public class LoginController {
   private Logger logger = Logger.getLogger(getClass().getName());
   private UserService userService;

   @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/register")
    public String showMyRegisterPage(Model model){
       model.addAttribute("user", new WebUser());
       return "register";
    }

    @PostMapping("/register")
    public String processingRegisterForm(@Valid @ModelAttribute("user") WebUser theWebUser,
                                         BindingResult theBindingResult,
                                         HttpSession session, Model model){
       String userName = theWebUser.getUserName();
       logger.info("Processing registration form for: " + userName);

       //form Validation
        if(theBindingResult.hasErrors()){
            return "register";
        }

        //Check the database if user is already exists
        User existing = userService.findByUserName(userName);
        if(existing != null){
            model.addAttribute("user", new WebUser());
            model.addAttribute("registrationError","User name is already exists.");

            logger.warning("User name is already exists.");
            return "register";
        }

        //create user account and store in the database
        userService.save(theWebUser);
        logger.info("Successfully created user: " + userName);

        //place user in the Web http session for later use
        session.setAttribute("user", theWebUser);

        return "registration-confirmation";
    }
}
