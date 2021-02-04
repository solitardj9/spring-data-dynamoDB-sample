package com.example.demo.application.core.apiService.apiManager.model.exception;

import org.springframework.http.HttpStatus;

import com.example.demo.application.common.CustomException;

public class ExceptionApiAlreayExist extends Exception implements CustomException {
    //
	private static final long serialVersionUID = 8381376260969902645L;

	private final int errCode;
	
	private final HttpStatus httpStatus;
	
	public ExceptionApiAlreayExist() {
		//
    	super(ExceptionCodeApi.Api_Alreay_Exist.getMessage());
    	errCode = ExceptionCodeApi.Api_Alreay_Exist.getCode();
    	httpStatus = ExceptionCodeApi.Api_Alreay_Exist.getHttpStatus();
    }
    
	public ExceptionApiAlreayExist(Throwable cause) {
		//
		super(ExceptionCodeApi.Api_Alreay_Exist.getMessage(), cause);
		errCode = ExceptionCodeApi.Api_Alreay_Exist.getCode();
		httpStatus = ExceptionCodeApi.Api_Alreay_Exist.getHttpStatus();
	}
	
	public int getErrCode() {
		//
		return errCode;
    }
	
	public HttpStatus getHttpStatus() {
		//
		return httpStatus;
    }
}