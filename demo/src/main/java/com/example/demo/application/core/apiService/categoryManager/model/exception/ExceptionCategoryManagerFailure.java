package com.example.demo.application.core.apiService.categoryManager.model.exception;

import org.springframework.http.HttpStatus;

import com.example.demo.application.common.CustomException;

public class ExceptionCategoryManagerFailure extends Exception implements CustomException {
    //
	private static final long serialVersionUID = 3266762849303110066L;

	private final int errCode;
	
	private final HttpStatus httpStatus;
	
	public ExceptionCategoryManagerFailure() {
		//
    	super(ExceptionCodeCategory.Category_Manager_Failure.getMessage());
    	errCode = ExceptionCodeCategory.Category_Manager_Failure.getCode();
    	httpStatus = ExceptionCodeCategory.Category_Manager_Failure.getHttpStatus();
    }
    
	public ExceptionCategoryManagerFailure(Throwable cause) {
		//
		super(ExceptionCodeCategory.Category_Manager_Failure.getMessage(), cause);
		errCode = ExceptionCodeCategory.Category_Manager_Failure.getCode();
		httpStatus = ExceptionCodeCategory.Category_Manager_Failure.getHttpStatus();
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