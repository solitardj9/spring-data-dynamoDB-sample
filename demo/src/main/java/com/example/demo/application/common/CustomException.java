package com.example.demo.application.common;

import org.springframework.http.HttpStatus;

public interface CustomException {
	//
	public int getErrCode();
	
	public HttpStatus getHttpStatus();
}