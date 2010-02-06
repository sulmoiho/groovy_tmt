package com.tomato.jef.exception;

/**
 * 비지니스 로직 수행중 사용자에게 알림을 위한 Exception.
 * Log에는 전달할 메시지만 남게 된다.
 * @author TomatoSystem
 * @version TmtFramework Version 2.0
 * @re-version 2009.12.08 sulmoiho
 */
class BusinessException extends RuntimeException {
	static final long serialVersionUID = -4299367215439316181L;
	
	BusinessException(String msg){
		super(msg)
	}
	
	BusinessException(String msg, Throwable cause) {
		super(msg, cause)
	}
	
	String getMessage(){
		return super.getMessage()
	}
	
	Throwable getCause(){
		return super.getCause()
	}
}
