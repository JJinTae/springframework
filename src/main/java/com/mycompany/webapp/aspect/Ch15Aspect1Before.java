package com.mycompany.webapp.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Ch15Aspect1Before {
	private static final Logger logger = LoggerFactory.getLogger(Ch15Aspect1Before.class);
	
	// Ch15Controller.* 모든 메서드 (..) 모든 매개변수
	@Before("execution(public * com.mycompany.webapp.controller.Ch15Controller.before*(..))")
	public void method() {
		logger.info("실행");
		// ... 
	}
}
