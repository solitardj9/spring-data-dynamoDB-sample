package com.example.demo.application.core.apiService.apiManager.model.exception;

import org.springframework.http.HttpStatus;

import com.example.demo.application.common.CustomException;

public class ExceptionApiNotFound extends Exception implements CustomException {
    //
	private static final long serialVersionUID = 2913391455166538141L;

	private final int errCode;
	
	private final HttpStatus httpStatus;
	
	public ExceptionApiNotFound() {
		//
    	super(ExceptionCodeApi.Api_Not_Found.getMessage());
    	errCode = ExceptionCodeApi.Api_Not_Found.getCode();
    	httpStatus = ExceptionCodeApi.Api_Not_Found.getHttpStatus();
    }
    
	public ExceptionApiNotFound(Throwable cause) {
		//
		super(ExceptionCodeApi.Api_Not_Found.getMessage(), cause);
		errCode = ExceptionCodeApi.Api_Not_Found.getCode();
		httpStatus = ExceptionCodeApi.Api_Not_Found.getHttpStatus();
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