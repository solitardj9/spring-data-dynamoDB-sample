package com.example.demo.application.core.apiService.serviceManager.model.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionCodeService {
    //
	Service_Bad_Request(400, "ServiceBadRequest.", HttpStatus.BAD_REQUEST),
	Service_Not_Found(404, "ServiceNotFound.", HttpStatus.NOT_FOUND),
	Service_Alreay_Exist(409, "ServiceAlreayExist.", HttpStatus.CONFLICT),
	Service_Manager_Failure(500, "ServiceManagerFailure.", HttpStatus.INTERNAL_SERVER_ERROR)
    ;
 
    private Integer code;
    private String message;
    private HttpStatus httpStatus;
 
    ExceptionCodeService(Integer code, String msg, HttpStatus httpStatus) {
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