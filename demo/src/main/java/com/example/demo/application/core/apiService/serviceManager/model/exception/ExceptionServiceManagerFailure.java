package com.example.demo.application.core.apiService.serviceManager.model.exception;

import org.springframework.http.HttpStatus;

import com.example.demo.application.common.CustomException;

public class ExceptionServiceManagerFailure extends Exception implements CustomException {
    //
	private static final long serialVersionUID = -4087464041101816929L;

	private final int errCode;
	
	private final HttpStatus httpStatus;
	
	public ExceptionServiceManagerFailure() {
		//
    	super(ExceptionCodeService.Service_Manager_Failure.getMessage());
    	errCode = ExceptionCodeService.Service_Manager_Failure.getCode();
    	httpStatus = ExceptionCodeService.Service_Manager_Failure.getHttpStatus();
    }
    
	public ExceptionServiceManagerFailure(Throwable cause) {
		//
		super(ExceptionCodeService.Service_Manager_Failure.getMessage(), cause);
		errCode = ExceptionCodeService.Service_Manager_Failure.getCode();
		httpStatus = ExceptionCodeService.Service_Manager_Failure.getHttpStatus();
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