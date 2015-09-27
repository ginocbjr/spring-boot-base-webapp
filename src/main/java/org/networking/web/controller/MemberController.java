package org.networking.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.networking.entity.Account;
import org.networking.entity.Member;
import org.networking.service.AccountPointsService;
import org.networking.service.AccountService;
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
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AccountPointsService accountPointsService;
	
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
    
    // Add new accounts
    @RequestMapping(value = "/addAccounts", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addAccounts(@RequestParam String username, @RequestParam Integer numAccounts) {
		Map<String, Object> model = new HashMap<>();
		
		if(username != null && numAccounts != null) {
			Member member = memberService.findMemberByUsername(username).get(0);
			member.setNumOfAccounts(member.getNumOfAccounts() + numAccounts);
			if(numAccounts != null && numAccounts >= 1) {
				for(int i = 1; i <= numAccounts; i++) {
					Account account = new Account();
					account.setCreateDate(new Date());
					account.setUpdateDate(new Date());
					account.setDateActivated(new Date());
					account.setMember(member);
					account.setTotalPoints(0d);
					if(i == 1) {
						Account firstAcct = member.getAccounts().get(0);
						if(firstAcct.getIsNext()) {
							firstAcct.setIsNext(false);
							account.setIsNext(true);
							accountService.save(firstAcct);
						} else {
							account.setIsNext(false);
						}
					} else {
						account.setIsNext(false);
					}
					accountService.save(account);
				}
			}
			
			
			if(member.getReferrer() != null && member.getReferrer().getId() != 1) {
				//Add points to referrer
				accountPointsService.createForReferral(member, numAccounts);
			}
			
			memberService.save(member);
			
			model.put("success", true);
		} else {
			model.put("success", false);
		}
		return model;
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