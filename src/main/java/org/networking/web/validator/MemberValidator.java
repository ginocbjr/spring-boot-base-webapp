package org.networking.web.validator;
import org.networking.entity.Member;
import org.networking.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MemberValidator implements Validator {
	
	@Autowired
    private MemberService memberService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Member.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Member member = (Member) target;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", 
				"", new Object[]{"Username"},"Username is a required field.");
    }
}