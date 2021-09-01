package com.mycompany.webapp.controller;

import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Controller
@RequestMapping("/ch05")
public class Ch05Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch05Controller.class);
	
	@RequestMapping("/content")
	public String content() {
		logger.info("실행");
		return "ch05/content";
	}
	
	@GetMapping("/getHeaderValue")
	public String getHeaderValue(HttpServletRequest request) {
		logger.info("실행");
		
		logger.info("method: " + request.getMethod());
		logger.info("requestURI: " + request.getRequestURI());
		logger.info("client IP: " + request.getRemoteAddr());
		logger.info("contextPath: " + request.getContextPath());
		
		String userAgent = request.getHeader("User-Agent");
		logger.info("userAgent: " + userAgent);
		if(userAgent.contains("Windows NT")) {
			logger.info("client OS: Windows");
		} else if(userAgent.contains("Macintosh")) {
			logger.info("client OS: macOS");
		}
		
		if(userAgent.contains("Edg")) {
			logger.info("client Browser: Edge");
		} else if(userAgent.contains("Trident")) {
			logger.info("client Browser: IE11");
		} else if(userAgent.contains("Chrome")) {
			logger.info("client Browser: Chrome");
		} else if(userAgent.contains("Safari")) {
			logger.info("client Browser: Safari");
		}
		
		return "redirect:/ch05/content";
	}
	
	@GetMapping("/createCookie")
	public String createCookie(HttpServletResponse response) {
		logger.info("실행");
		
		Cookie cookie = new Cookie("useremail", "emflaxl100@naver.com");
		cookie.setDomain("localhost"); // localhost 면 전송
		cookie.setPath("/"); // localhost/... 이면 모두 전송
		cookie.setMaxAge(30*60); // 이 시간동안에만 전송
		cookie.setHttpOnly(true); // JavaScript에서 못 읽게함
		cookie.setSecure(true); // https://만 전송
		response.addCookie(cookie);
		
		return "redirect:/ch05/content";
	}
	
	@GetMapping("/getCookie1")
	public String getCookie1(
			@CookieValue String useremail, 
			@CookieValue("useremail") String uemail
			) {
		logger.info("실행");
		logger.info("useremail: " + useremail);
		logger.info("uemail: " + uemail);
		
		return "redirect:/ch05/content";
	}
	
	@GetMapping("/getCookie2")
	public String getCookie2(HttpServletRequest request) {
		logger.info("실행");
		
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			String cookieName = cookie.getName();
			String cookieValue = cookie.getValue();
			if (cookieName.equals("useremail")) {
				logger.info(cookieValue);
				break;
			}
		}
		
		return "redirect:/ch05/content";
	}
	
	@GetMapping("/createJsonCookie")
	public String createJsonCookie(HttpServletResponse response) throws Exception{
		logger.info("실행");
		
		// String json = "{\"userid\":\"fall\", \"useremail\":\"fall@company.com\", \"username\":\"김준태\"}";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userid", "fall");
		jsonObject.put("useremail", "fall@company.com");
		jsonObject.put("username", "홍길동");
		String json = jsonObject.toString();
		
		Cookie cookie = new Cookie("user", URLEncoder.encode(json, "UTF-8"));
		response.addCookie(cookie);
		
		return "redirect:/ch05/content";
	}
	
	@GetMapping("/getJsonCookie")
	public String getJsonCookie(@CookieValue String user) {
		logger.info("실행");
		logger.info("user: " + user);
		
		JSONObject jsonObject = new JSONObject(user);
		String userid = jsonObject.getString("userid");
		String useremail = jsonObject.getString("useremail");
		String username = jsonObject.getString("username");
		
		logger.info("userid: " + jsonObject.getString("userid"));
		logger.info("useremail: " + jsonObject.getString("useremail"));
		logger.info("username: " + jsonObject.getString("username"));
		
		return "redirect:/ch05/content";
	}
	
	@GetMapping("/createJwtCookie")
	public String createJwtCookie(HttpServletResponse response) throws Exception {
		logger.info("실행");
		
		String userid = "fall";
		String useremail = "fall@company.com";
		String username = "홍길동";
		
		JwtBuilder builder = Jwts.builder();
		// Header
		builder.setHeaderParam("alg", "HS256"); // 암호화 알고리즘
		builder.setHeaderParam("typ", "JWT");
		builder.setExpiration(new Date(new Date().getTime() + 1000*60*30));
		// Payload
		builder.claim("userid", userid);
		builder.claim("useremail", useremail);
		builder.claim("username", username);
		// Signature
		String secretKey = "abc12345"; // 절대 유출되면 안된다. properties에서 사용하는게 좋다.
		builder.signWith(SignatureAlgorithm.HS256, secretKey.getBytes("UTF-8"));
		String jwt = builder.compact();
		logger.info("jwt: " + jwt);
		
		Cookie cookie = new Cookie("jwt", jwt);
		response.addCookie(cookie);
		
		return "redirect:/ch05/content";
	}
	
	@GetMapping("/getJwtCookie")
	public String getJwtCookie(@CookieValue String jwt) throws Exception{
		logger.info("실행");
		
		logger.info(jwt);
		JwtParser parser = Jwts.parser();
		String secretKey = "abc12345";
		parser.setSigningKey(secretKey.getBytes("UTF-8"));
		Jws<Claims> jws = parser.parseClaimsJws(jwt);
		Claims claims = jws.getBody();
		
		String userid = claims.get("userid", String.class);
		String useremail = claims.get("useremail", String.class);
		String username = claims.get("username", String.class);
		
		logger.info("userid: " + userid);
		logger.info("useremail: " + useremail);
		logger.info("username: " + username);
		
		return "redirect:/ch05/content";
	}
}















