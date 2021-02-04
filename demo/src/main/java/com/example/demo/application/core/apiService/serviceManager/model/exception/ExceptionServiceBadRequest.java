package com.example.demo.application.core.apiService.serviceManager.model.exception;

import org.springframework.http.HttpStatus;

import com.example.demo.application.common.CustomException;

public class ExceptionServiceBadRequest extends Exception implements CustomException {
	//
	private static final long serialVersionUID = 52080287614727069L;

	private final int errCode;
	
	private final HttpStatus httpStatus;
	
	public ExceptionServiceBadRequest() {
		//
    	super(ExceptionCodeService.Service_Bad_Request.getMessage());
    	errCode = ExceptionCodeService.Service_Bad_Request.getCode();
    	httpStatus = ExceptionCodeService.Service_Bad_Request.getHttpStatus();
    }
    
	public ExceptionServiceBadRequest(Throwable cause) {
		//
		super(ExceptionCodeService.Service_Bad_Request.getMessage(), cause);
		errCode = ExceptionCodeService.Service_Bad_Request.getCode();
		httpStatus = ExceptionCodeService.Service_Bad_Request.getHttpStatus();
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