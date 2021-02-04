package com.example.demo.application.core.apiService.apiManager.model.exception;

import org.springframework.http.HttpStatus;

import com.example.demo.application.common.CustomException;

public class ExceptionApiBadRequest extends Exception implements CustomException {
	//
	private static final long serialVersionUID = 7234680985325087168L;

	private final int errCode;
	
	private final HttpStatus httpStatus;
	
	public ExceptionApiBadRequest() {
		//
    	super(ExceptionCodeApi.Api_Bad_Request.getMessage());
    	errCode = ExceptionCodeApi.Api_Bad_Request.getCode();
    	httpStatus = ExceptionCodeApi.Api_Bad_Request.getHttpStatus();
    }
    
	public ExceptionApiBadRequest(Throwable cause) {
		//
		super(ExceptionCodeApi.Api_Bad_Request.getMessage(), cause);
		errCode = ExceptionCodeApi.Api_Bad_Request.getCode();
		httpStatus = ExceptionCodeApi.Api_Bad_Request.getHttpStatus();
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