package com.mycompany.webapp.dto;

import lombok.Data;

@Data
public class Ch11City {
	private int code;
	private String label;
	
	// Source - Generate Constructor fields
	public Ch11City() {
		
	}
	
	public Ch11City(int code, String label) {
		super();
		this.code = code;
		this.label = label;
	}
}
