package org.networking.web.controller;

import java.security.Principal;

import org.networking.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/my-earnings")
public class DashboardController {
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(method = {RequestMethod.GET})
	public String view(Model model, Principal principal) {
		if(principal != null) {
			model.addAttribute("pointsSummary", memberService.findAccountPointsByMember(principal.getName()));
		}
        return "my-earnings";
	}
    
}