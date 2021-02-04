package com.example.demo.application.core.apiService.serviceManager.model.exception;

import org.springframework.http.HttpStatus;

import com.example.demo.application.common.CustomException;

public class ExceptionServiceNotFound extends Exception implements CustomException {
    //
	private static final long serialVersionUID = 5107116655698868550L;

	private final int errCode;
	
	private final HttpStatus httpStatus;
	
	public ExceptionServiceNotFound() {
		//
    	super(ExceptionCodeService.Service_Not_Found.getMessage());
    	errCode = ExceptionCodeService.Service_Not_Found.getCode();
    	httpStatus = ExceptionCodeService.Service_Not_Found.getHttpStatus();
    }
    
	public ExceptionServiceNotFound(Throwable cause) {
		//
		super(ExceptionCodeService.Service_Not_Found.getMessage(), cause);
		errCode = ExceptionCodeService.Service_Not_Found.getCode();
		httpStatus = ExceptionCodeService.Service_Not_Found.getHttpStatus();
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