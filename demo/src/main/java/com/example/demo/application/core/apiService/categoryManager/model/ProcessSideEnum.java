package com.example.demo.application.core.apiService.categoryManager.model;

public enum ProcessSideEnum {
	
	INSIDE("inside"),
	OUTSIDE("outside")
	;
	
	private String sideType;
	
	ProcessSideEnum(String sideType) {
		this.sideType = sideType;
	}
	
	public String getSideType() {
		return sideType;
	}
	
	@Override
	public String toString() {
		return sideType;
	}
}