package org.networking.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Gino on 8/28/2015.
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    String index() {
        return "index";
    }
    
    @RequestMapping(value = {"/login", "/logout"})
    String login() {
        return "login";
    }
    
    @RequestMapping("/contact")
    String homeContact() {
        return "contact";
    }
    
    @RequestMapping("/infinite-business-solutions")
    String homeIbs() {
        return "ibs";
    }
    
    @RequestMapping("/products")
    String product() {
        return "product";
    }
    
    @RequestMapping("/product-1")
    String product1() {
        return "product-1";
    }
    
    @RequestMapping("/product-2")
    String product2() {
        return "product-2";
    }
    
    @RequestMapping("/product-3")
    String product3() {
        return "product-3";
    }
    
    @RequestMapping("/members")
    String members() {
        return "member-list";
    }
    
    @RequestMapping("/order")
    String order() {
        return "admin-order";
    }
    
    @RequestMapping("/admin-products")
    String adminProducts() {
        return "admin-product-list";
    }
}
