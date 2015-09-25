package org.networking.web.controller;

import org.networking.entity.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/admin/members-earnings")
public class MembersEarningsController  extends BaseController<Product> {
	
	@RequestMapping(method = {RequestMethod.GET})
	public String view() {
        return "admin-members-earnings";
	}
}