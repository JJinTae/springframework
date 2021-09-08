package com.mycompany.webapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service // 기본생성자가 없으면 주입이되는 생성자를 우선적으로 객체를 생성한다.
public class Ch13Service6 {
	private static final Logger logger = LoggerFactory.getLogger(Ch13Service6.class);
	
	private int prop1;
	private double prop2;
	private boolean prop3;
	
	@Value("${service.prop4}")
	private String prop4;

	public Ch13Service6(
			@Value("${service.prop1}") int prop1, 
			@Value("${service.prop2}") double prop2) {
		logger.info("실행");
		logger.info("prop1: " + prop1);
		logger.info("prop2: " + prop2);
		this.prop1 = prop1;
		this.prop2 = prop2;
	}
	
	@Value("${service.prop3}")
	public void setProp4(boolean prop3) {
		logger.info("실행");
		logger.info("prop3: " + prop3);
		this.prop3 = prop3;
	}
}
