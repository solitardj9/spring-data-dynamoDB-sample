package com.example.demo.application.core.apiService.categoryManager.model.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionCodeCategory {
    //
	Category_Bad_Request(400, "CategoryBadRequest.", HttpStatus.BAD_REQUEST),
	Category_Not_Found(404, "CategoryNotFound.", HttpStatus.NOT_FOUND),
	Category_Alreay_Exist(409, "CategoryAlreayExist.", HttpStatus.CONFLICT),
	Category_Manager_Failure(500, "CategoryManagerFailure.", HttpStatus.INTERNAL_SERVER_ERROR)
    ;
 
    private Integer code;
    private String message;
    private HttpStatus httpStatus;
 
    ExceptionCodeCategory(Integer code, String msg, HttpStatus httpStatus) {
        this.code = code;
        this.message = msg;
        this.httpStatus = httpStatus;
    }
    
    public Integer getCode() {
        return this.code;
    }
    
    public String getMessage() {
        return this.message;
    }

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}