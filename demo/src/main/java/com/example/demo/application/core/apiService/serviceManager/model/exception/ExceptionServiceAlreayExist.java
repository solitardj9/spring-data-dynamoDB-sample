package com.example.demo.application.core.apiService.serviceManager.model.exception;

import org.springframework.http.HttpStatus;

import com.example.demo.application.common.CustomException;

public class ExceptionServiceAlreayExist extends Exception implements CustomException {
    //
	private static final long serialVersionUID = 2263895877760667549L;

	private final int errCode;
	
	private final HttpStatus httpStatus;
	
	public ExceptionServiceAlreayExist() {
		//
    	super(ExceptionCodeService.Service_Alreay_Exist.getMessage());
    	errCode = ExceptionCodeService.Service_Alreay_Exist.getCode();
    	httpStatus = ExceptionCodeService.Service_Alreay_Exist.getHttpStatus();
    }
    
	public ExceptionServiceAlreayExist(Throwable cause) {
		//
		super(ExceptionCodeService.Service_Alreay_Exist.getMessage(), cause);
		errCode = ExceptionCodeService.Service_Alreay_Exist.getCode();
		httpStatus = ExceptionCodeService.Service_Alreay_Exist.getHttpStatus();
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