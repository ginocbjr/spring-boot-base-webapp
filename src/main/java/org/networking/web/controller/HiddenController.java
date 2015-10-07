package org.networking.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

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
}