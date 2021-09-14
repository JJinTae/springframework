package com.mycompany.webapp.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.Ch14MemberDao;
import com.mycompany.webapp.dto.Ch14Member;

@Service
public class Ch14MemberService {

	private static final Logger logger = LoggerFactory.getLogger(Ch14MemberService.class);

	public enum JoinResult {
		SUCCESS, FAIL, DUPLICATED
	}
	public enum LoginResult {
		SUCCESS, FAIL, FAIL_MID, FAIL_MPASSWORD;
	}

	@Resource
	private Ch14MemberDao memberDao;

	// 회원 가입을 처리하는 비지니스 메소드(로직)
	public JoinResult join(Ch14Member member) {
		try {
			// 이미 가입된 아이디인지 확인
			Ch14Member dbMember = memberDao.selectByMid(member.getMid()); // SELECT * FROM member WHERE mid=?
			// DB에 회원 정보를 저장
			if (dbMember == null) {
				memberDao.insert(member);
				return JoinResult.SUCCESS;
			} else {
				return JoinResult.DUPLICATED;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JoinResult.FAIL;
		}
	}
	
	// 로그인을 처리하는 비즈니스 메소드(로직)
	public LoginResult login(Ch14Member member) {
		try {
			// 기존에 있는 아이디인지 확인
			Ch14Member dbMember = memberDao.selectByMid(member.getMid());
			if(dbMember != null) {
				if(dbMember.getMpassword().equals(member.getMpassword())) {
					// 아이디와 패스워드 일치
					return LoginResult.SUCCESS;
				} else { 
					// 아이디는 일치하나 패스워드 다름.
					return LoginResult.FAIL_MPASSWORD;
				}
			} else { 
				// 로그인 아이디 일치 하지 않음.
				return LoginResult.FAIL_MID;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return LoginResult.FAIL;
		}
	}
}