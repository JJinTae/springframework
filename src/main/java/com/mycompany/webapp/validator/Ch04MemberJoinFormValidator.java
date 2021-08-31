package com.mycompany.webapp.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mycompany.webapp.dto.Ch04Member;

public class Ch04MemberJoinFormValidator implements Validator{
	private static final  Logger logger = LoggerFactory.getLogger(Ch04MemberJoinFormValidator.class);

	@Override
	public boolean supports(Class<?> clazz) { 
		// TODO Auto-generated method stub
		// 유효성을 검사할 수 있는 class일 때 true 없을 때 false를 리턴한다.
		logger.info("실행");
		boolean check = Ch04Member.class.isAssignableFrom(clazz);
		return check;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Ch04Member member = (Ch04Member) target;
		
		// mid 검사
		if(member.getMid() == null || member.getMid().trim().equals("")) {
			errors.rejectValue("mid", "errors.mid.required");
		} else {
			if (member.getMid().length() < 4) {
				errors.rejectValue("mid", "errors.mid.minlength", new Object[] {4}, "");
			}
		}
		
		// mpassword 검사
		if(member.getMpassword() == null || member.getMpassword().trim().equals("")) {
			errors.rejectValue("mpassword", "errors.mpassword.required");
		} else {
			if (member.getMpassword().length() < 8) {
				errors.rejectValue("mpassword", "errors.mpassword.minlength", new Object[] {8}, "");
			}
		}
		
		// memail 검사
		if(member.getMemail() == null || member.getMemail().trim().equals("")) {
			errors.rejectValue("memail", "errors.memail.required");
		} else {
			String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
			Pattern pattern = Pattern.compile(regex); // regex를 해석해서 pattern 객체를 만들겠다.
			Matcher matcher = pattern.matcher(member.getMemail());
			if(!matcher.matches()) {
				errors.rejectValue("memail", "errors.memail.invalid");
			}
		}
		
		// mtel 검사
		if(member.getMtel() == null || member.getMtel().trim().equals("")) {
			errors.rejectValue("mtel", "errors.mtel.required");
		} else {
			String regex = "^\\d{3}-\\d{3,4}-\\d{4}$";
			Pattern pattern = Pattern.compile(regex); // regex를 해석해서 pattern 객체를 만들겠다.
			Matcher matcher = pattern.matcher(member.getMtel());
			if(!matcher.matches()) {
				errors.rejectValue("mtel", "errors.mtel.invalid");
			}
		}
	}
}
