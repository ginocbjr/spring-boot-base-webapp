package org.networking.web.controller;

import javax.validation.Valid;

import org.networking.entity.Member;
import org.networking.entity.Product;
import org.networking.service.MemberService;
import org.networking.service.ProductService;
import org.networking.web.validator.MemberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/member")
public class MemberController extends BaseController<Product> {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberValidator memberValidator;
	
	@RequestMapping(method = {RequestMethod.GET})
	public String view(Model model) {
		model.addAttribute("memberList", memberService.findAll());
        return "member-list";
	}
	
	@RequestMapping("/member-create")
	public String memberCreatePage(Member member, Model model) {
		model.addAttribute("memberList", memberService.findAll());
		return "member-add";
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(memberValidator);
    }

	@RequestMapping("/member-edit/{id}")
    public String memberEditPage(@PathVariable Long id) {
		return "member-edit";
    }

    @RequestMapping(value="/member-create", method=RequestMethod.POST)
    public String createMember(@Valid Member member, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
        	model.addAttribute("memberList", memberService.findAll());
        	return "member-add";
        } else {
        	memberService.create(member);
        	model.addAttribute("memberCreate", "success");
        	return "member-add";
        }
    }

	@RequestMapping(value="/search", method =RequestMethod.GET)
	public @ResponseBody Map<String, Object> memberList(@RequestParam(value="key") String key){
		Map<String, Object> result = new HashMap<>();
		List<Member> members = memberService.findByLastnameOrFirstnameLike(key);
		result.put("results", members);
		result.put("length", members.size());
		return result;
	}
}