package org.networking.web.controller;

import org.networking.entity.Product;
import org.networking.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin/members-earnings")
public class MembersEarningsController {

	@Autowired
	private MemberService memberService;
	
	@RequestMapping(method = {RequestMethod.GET})
	public String view() {
        return "admin-members-earnings";
	}

	@RequestMapping(value = "/members")
	public @ResponseBody Map<String, Object> findMembersWithUnclaimedEarnings() {
		Map<String, Object> model = new HashMap<>();
		model.put("members", memberService.findMemberEarningsByDate(new Date()));
		return model;
	}


}