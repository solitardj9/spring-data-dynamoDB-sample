package com.example.demo.application.core.apiService.categoryManager.model.exception;

import org.springframework.http.HttpStatus;

import com.example.demo.application.common.CustomException;

public class ExceptionCategoryNotFound extends Exception implements CustomException {
    //
	private static final long serialVersionUID = -2103636100629780441L;

	private final int errCode;
	
	private final HttpStatus httpStatus;
	
	public ExceptionCategoryNotFound() {
		//
    	super(ExceptionCodeCategory.Category_Not_Found.getMessage());
    	errCode = ExceptionCodeCategory.Category_Not_Found.getCode();
    	httpStatus = ExceptionCodeCategory.Category_Not_Found.getHttpStatus();
    }
    
	public ExceptionCategoryNotFound(Throwable cause) {
		//
		super(ExceptionCodeCategory.Category_Not_Found.getMessage(), cause);
		errCode = ExceptionCodeCategory.Category_Not_Found.getCode();
		httpStatus = ExceptionCodeCategory.Category_Not_Found.getHttpStatus();
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