package org.networking.web.controller;

import org.networking.entity.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/admin/settings")
public class SettingsController  extends BaseController<Product> {
	
	@RequestMapping(method = {RequestMethod.GET})
	public String view() {
        return "admin-settings";
	}

    /*@RequestMapping(value="/add-product", method=RequestMethod.POST)
    public String createProduct(@Valid Product product, BindingResult bindingResult, Model model) {
        return "add-product";
    }

    @RequestMapping(value = "/search")
    public @ResponseBody Map<String, Object> search(@RequestParam(value = "key") String search) {
        Map<String, Object> map = new HashMap<>();
        List<Product> results = ((ProductService)baseService).findByNameLike(search);
        map.put("results", ((ProductService)baseService).findByNameLike(search));
        map.put("length", results.size());
        return map;
    }*/
}