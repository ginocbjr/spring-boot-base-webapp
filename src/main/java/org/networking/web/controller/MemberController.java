package org.networking.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/member")
public class MemberController extends BaseController<Member> {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberValidator memberValidator;
	
	@RequestMapping(method = {RequestMethod.GET})
	public String view(Model model) {
		model.addAttribute("memberList", memberService.findAll());
        return "member-list";
	}
	
	@RequestMapping("/create")
	public String memberCreatePage(Member member, Model model) {
		model.addAttribute("memberList", memberService.findAll());
		return "member-add";
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(memberValidator);
    }
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model){
	    model.addAttribute("member", memberService.load(id));
	    model.addAttribute("memberList", memberService.findAll());
	    return "member-add";
	}

    @RequestMapping(value="/create", method=RequestMethod.POST)
    public String createMember(@Valid Member member, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
        	model.addAttribute("memberList", memberService.findAll());
        } else {
        	memberService.create(member);
        	model.addAttribute("memberCreate", "success");
        }
        
        return "member-add";
        /*if(member.isNew()) {
    		return "member-add";
    	} else {
    		return "redirect:/admin/member/edit/" + member.getId();
    	}*/
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