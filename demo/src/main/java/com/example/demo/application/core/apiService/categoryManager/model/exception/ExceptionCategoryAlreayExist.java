package com.example.demo.application.core.apiService.categoryManager.model.exception;

import org.springframework.http.HttpStatus;

import com.example.demo.application.common.CustomException;

public class ExceptionCategoryAlreayExist extends Exception implements CustomException {
    //
	private static final long serialVersionUID = -1824689827437681695L;

	private final int errCode;
	
	private final HttpStatus httpStatus;
	
	public ExceptionCategoryAlreayExist() {
		//
    	super(ExceptionCodeCategory.Category_Alreay_Exist.getMessage());
    	errCode = ExceptionCodeCategory.Category_Alreay_Exist.getCode();
    	httpStatus = ExceptionCodeCategory.Category_Alreay_Exist.getHttpStatus();
    }
    
	public ExceptionCategoryAlreayExist(Throwable cause) {
		//
		super(ExceptionCodeCategory.Category_Alreay_Exist.getMessage(), cause);
		errCode = ExceptionCodeCategory.Category_Alreay_Exist.getCode();
		httpStatus = ExceptionCodeCategory.Category_Alreay_Exist.getHttpStatus();
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