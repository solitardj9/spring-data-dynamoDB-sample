package com.example.demo.application.core.apiService.categoryManager.model.exception;

import org.springframework.http.HttpStatus;

import com.example.demo.application.common.CustomException;

public class ExceptionCategoryBadRequest extends Exception implements CustomException {
	//
	private static final long serialVersionUID = -2902912149235717570L;

	private final int errCode;
	
	private final HttpStatus httpStatus;
	
	public ExceptionCategoryBadRequest() {
		//
    	super(ExceptionCodeCategory.Category_Bad_Request.getMessage());
    	errCode = ExceptionCodeCategory.Category_Bad_Request.getCode();
    	httpStatus = ExceptionCodeCategory.Category_Bad_Request.getHttpStatus();
    }
    
	public ExceptionCategoryBadRequest(Throwable cause) {
		//
		super(ExceptionCodeCategory.Category_Bad_Request.getMessage(), cause);
		errCode = ExceptionCodeCategory.Category_Bad_Request.getCode();
		httpStatus = ExceptionCodeCategory.Category_Bad_Request.getHttpStatus();
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