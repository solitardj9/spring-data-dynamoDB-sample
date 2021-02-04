package com.example.demo.application.core.apiService.apiManager.model.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionCodeApi {
    //
	Api_Bad_Request(400, "ApiBadRequest.", HttpStatus.BAD_REQUEST),
	Api_Not_Found(404, "ApiNotFound.", HttpStatus.NOT_FOUND),
	Api_Alreay_Exist(409, "ApiAlreayExist.", HttpStatus.CONFLICT),
	Api_Manager_Failure(500, "ApiManagerFailure.", HttpStatus.INTERNAL_SERVER_ERROR)
    ;
 
    private Integer code;
    private String message;
    private HttpStatus httpStatus;
 
    ExceptionCodeApi(Integer code, String msg, HttpStatus httpStatus) {
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