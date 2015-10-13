package org.networking.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.networking.entity.Account;
import org.networking.entity.Member;
import org.networking.repository.AccountRepository;
import org.networking.repository.MemberRepository;
import org.networking.service.AccountPointsService;
import org.networking.service.AccountService;
import org.networking.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/*memberService.saveEarningsHistoryByDate(new Date());
*/

/**
 * Used for manually running Cron for (1) Distributing Daily Group Points & (2) Saving Weekly Earnings
 * @author sony
 *
 */
@Controller
@RequestMapping("/hidden")
public class HiddenController {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private AccountPointsService accountPointsService;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@RequestMapping(method = {RequestMethod.GET})
	public String view() {
        return "hidden";
	}
	
	@RequestMapping(value = "/distributeGroupPoints", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> distributeGroupPoints(@RequestParam String date) {
		Map<String, Object> model = new HashMap<>();
		try {
			accountService.distributeGroupPoints(dateFormat.parse(date));
		} catch (ParseException e) {
			model.put("success", false);
			return model;
		}
		model.put("success", true);
		return model;
	}
	
	@RequestMapping(value = "/saveEarningsHistoryByDate", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveEarningsHistoryByDate(@RequestParam String date) {
		Map<String, Object> model = new HashMap<>();
		try {
			memberService.saveEarningsHistoryByDate(dateFormat.parse(date));
		} catch (ParseException e) {
			model.put("success", false);
			return model;
		}
		model.put("success", true);
		return model;
	}
	
	@RequestMapping(value = "/createAccounts", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createAccounts() {
		Map<String, Object> model = new HashMap<>();
		
		List<Member> members = memberService.findAll();
		
		for(Member member : members) {
			// Add accounts
			Integer accounts = member.getNumOfAccounts();

				if(accounts != null && accounts >= 1) {
					for(int i = 1; i <= accounts; i++) {
						Account account = new Account();
						account.setCreateDate(member.getDateJoined());
						account.setUpdateDate(member.getDateJoined());
						account.setDateActivated(member.getDateJoined());
						account.setMember(member);
						account.setTotalPoints(0d);
						if(i == 1) {
							account.setIsNext(true);
						} else {
							account.setIsNext(false);
						}
						// For group points
						if(i==1 && memberRepository.count() == 1) {
							account.setIsNextForGroup(Boolean.TRUE);
						}
						accountRepository.save(account);
					}
				}
				
				
				if(member.getReferrer() != null && member.getReferrer().getId() != 1) {
					//Add points to referrer
					accountPointsService.createForReferral(member, accounts, member.getDateJoined());
				}
		}
		
		
		
		model.put("success", true);
		return model;
	}
}