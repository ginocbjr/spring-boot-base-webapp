package org.networking.web.controller;

import org.networking.entity.Member;
import org.networking.entity.Product;
import org.networking.entity.SalesOrder;
import org.networking.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/order")
public class OrderController extends BaseController<Product> {

    @Autowired
    private MemberService memberService;

	@RequestMapping(method = {RequestMethod.GET})
	public String view() {
        return "admin-order-list";
	}

    @RequestMapping(value="/add-order", method=RequestMethod.POST)
    public String createProduct(@Valid SalesOrder salesOrder, BindingResult bindingResult, Model model) {
        return "add-order";
    }

    @RequestMapping(value="/memberList", method =RequestMethod.GET)
    public List<Member> memberList(@RequestParam(value="key") String key){
        return memberService.findByLastnameOrFirstnameLike(key);
    }
}