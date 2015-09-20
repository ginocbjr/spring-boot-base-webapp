package org.networking.web.controller;

import javax.validation.Valid;

import org.networking.entity.Member;
import org.networking.entity.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/admin/product")
public class ProductController  extends BaseController<Product> {
	
	@RequestMapping(method = {RequestMethod.GET})
	public String view() {
        return "admin-product-list";
	}

    @RequestMapping(value="/add-product", method=RequestMethod.POST)
    public String createProduct(@Valid Product product, BindingResult bindingResult, Model model) {
        return "add-product";
    }
}