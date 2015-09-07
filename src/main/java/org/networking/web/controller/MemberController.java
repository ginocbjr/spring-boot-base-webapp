package org.networking.web.controller;

import javax.validation.Valid;

import org.networking.entity.Member;
import org.networking.service.MemberService;
import org.networking.web.validator.MemberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MemberController  {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberValidator memberValidator;
	
	@RequestMapping("/members")
    String members() {
        return "member-list";
    }
	
	@RequestMapping(value="/member-create", method=RequestMethod.GET)
	public String index(Member member) {
		return "member-add";
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(memberValidator);
    }

    /*@RequestMapping(value="/members/view", method=RequestMethod.GET)
    public String showForm(Member member) {
        return "member-list";	
    }*/

    @RequestMapping(value="/member-create", method=RequestMethod.POST)
    public String createMember(@Valid Member member, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
        	return "member-add";
        } else {
        	memberService.create(member);
        	model.addAttribute("result", "success");
        	return "redirect:/members";
        }
    }
}