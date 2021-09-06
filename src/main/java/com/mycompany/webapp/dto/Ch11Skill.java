package com.mycompany.webapp.dto;

import lombok.Data;

@Data
public class Ch11Skill {
	private int code;
	private String label;
	
	// Source - Generate Constructor fields
	public Ch11Skill() {
		
	}
	
	public Ch11Skill(int code, String label) {
		super();
		this.code = code;
		this.label = label;
	}
}
