package com.mycompany.webapp.exception;


public class Ch10SoldOutException extends RuntimeException{
	public Ch10SoldOutException() {
		super("품절");
	}
	
	public Ch10SoldOutException(String message) {
		super(message); // 상황마다 다르게 메세지를 주고싶을 때
	}
}
