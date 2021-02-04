package com.example.demo.application.core.apiService.apiManager.model.exception;

import org.springframework.http.HttpStatus;

import com.example.demo.application.common.CustomException;

public class ExceptionApiManagerFailure extends Exception implements CustomException {
    //
	private static final long serialVersionUID = -5866546680299272557L;

	private final int errCode;
	
	private final HttpStatus httpStatus;
	
	public ExceptionApiManagerFailure() {
		//
    	super(ExceptionCodeApi.Api_Manager_Failure.getMessage());
    	errCode = ExceptionCodeApi.Api_Manager_Failure.getCode();
    	httpStatus = ExceptionCodeApi.Api_Manager_Failure.getHttpStatus();
    }
    
	public ExceptionApiManagerFailure(Throwable cause) {
		//
		super(ExceptionCodeApi.Api_Manager_Failure.getMessage(), cause);
		errCode = ExceptionCodeApi.Api_Manager_Failure.getCode();
		httpStatus = ExceptionCodeApi.Api_Manager_Failure.getHttpStatus();
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