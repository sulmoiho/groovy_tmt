package com.tomato.jef.exception;

import com.tomato.jef.constants.ScreenKeys;
import com.tomato.jef.constants.MessageKeys;
import com.tomato.jef.log.Log;
import com.tomato.jef.resource.message.Message;

import java.sql.SQLException;


class AppException {
	/**
	 * 발생한 Exception
	 */
	private Exception e;
	
	/**
	 * 에러메시지
	 */
	private String strMessage;
	
	/**
	 * 메인화면
	 */
	private String strScreenKey = ScreenKeys.FRAME;
	
	/**
	 * 에러메시지 출력 Mode (default : ALERT)
	 */
	private String strMessageKey = MessageKeys.ALERT;
	
	/**
	 * 생성자
	 * @param strMessage java.lang.String 에러메시지
	 */
	AppException(String strMessage) {
		init(new Exception(strMessage), null, null, strMessage)
	}
	
	/**
	 * 생성자
	 *
	 * @param e java.lang.Exception 발생한 Exception
	 * @param strMessage java.lang.String 에러메시지
	 */
	AppException(Exception e, String strMessage) {
		init(e, null, null, strMessage)
	}
	
	/**
	 * 생성자
	 *
	 * @param e java.lang.Exception 발생한 Exception
	 * @param strScreenKey java.lang.String 분기될 URL
	 * @param strMessageKey java.lang.String 에러메시지 출력형태
	 * @param strMessage java.lang.String 에러메시지
	 */
	AppException(Exception e, String strScreenKey, String strMessageKey, String strMessage) {
		init(e, strScreenKey, strMessageKey, strMessage)
	}
	
	/**
	 * DB를 통한 메시지를 관리하기 위해 메시지 Argument 추가
	 * @param e
	 * @param strScreenKey
	 * @param strMessageKey
	 * @param msg
	 */
	AppException(Exception e, String strScreenKey, String strMessageKey, Message msg) {
		init(e, strScreenKey, strMessageKey, msg.getMsg())
	}
	
	/**
	 * Exception Trace METHOD
	 *
	 */
	void printStackTrace() {
		if(this.e) e.printStackTrace()
		else Log.info('No Print Stack Trace.')
	}
	
	/**
	 * Exception Trace METHOD
	 *
	 * @param ps java.io.PrintStream 발생한 Exception을 출력한 Stream.
	 */
	void printStackTrace(PrintStream ps) {
		if(this.e) this.e.printStackTrace(ps)
		else Log.info('No Print Stack Trace.')
	}
	
	/**
	 * Exception Trace METHOD
	 *
	 * @param pw java.io.PrintWriter 발생한 Exception을 출력한 Writer
	 */
	void printStackTrace(PrintWriter pw) {
		if(this.e) this.e.printStackTrace(pw)
		else Log.info('No Print Stack Trace.')
	}
	
	/**
	 * Overriding Method
	 *
	 * @return java.lang.Throwable Exception 에러메시지와 발생한 Exception을 출력
	 * @see java.lang.Throwable#fillInStackTrace()
	 */
	Throwable fillInStackTrace() {
		return this.e ? this.e.fillInStackTrace() : super.fillInStackTrace()
	}
	
	
	/**
	 * Exception 발생시 처리되는 METHOD
	 *
	 * @param java.lang.Exception e 발생한 Exception
	 * @param java.lang.String strScreenKey 분기될 URL
	 * @param java.lang.String strMessageKey 에러메시지 출력형태
	 * @param java.lang.String strMessage 에러메시지
	 */
	private void init(Exception e, String strScreenKey, String strMessageKey, String strMessage) {
		this.e = e
		this.strMessage 	= strMessage
		this.strScreenKey 	= strScreenKey
		this.strMessageKey	= strMessageKey
	}
	
	/**
	 * 분기될 URL을 구하는 메소드
	 *
	 * @return java.lang.String 분기될 URL
	 */
	String getScreenKey() {
		return this.strScreenKey
	}
	
	/**
	 * 분기될 URL을 지정하는 메소드
	 *
	 * @param strScreenKey java.lang.String 분기될 URL
	 */
	void setScreenKey(String strScreenKey) {
		this.strScreenKey = strScreenKey
	}
	
	/**
	 * 에러메시지 출력형태를 구하는 메소드
	 *
	 * @return java.lang.String 에러메시지 출력형태
	 * @see com.tomato.jef.util.MessageKeys
	 */
	String getMessageKey() {
		return this.strMessageKey
	}
	
	/**
	 * 에러메시지 출력형태를 지정하는 메소드
	 *
	 * @param strMessageKey java.lang.String 에러메시지 출력형태
	 * @see com.tomato.jef.util.MessageKeys
	 */
	void setMessageKey(String strMessageKey) {
		this.strMessageKey = strMessageKey
	}
	
	/**
	 * 발생한 Exception을 구하는 메소드
	 *
	 * @return java.lang.Exception 발생한 Exception
	 */
	Exception getException() {
		return this.e
	}
	
	/**
	 * 발생한 Exception을 선언해주는 메소드
	 *
	 * @param java.lang.Exception 발생한 Exception
	 */
	void setException(Exception e) {
		this.e = e
	}
	
	/**
	 * 에러메시지를 구하는 메소드
	 *
	 * @return java.lang.String 에러메시지
	 */
	String getMessage() {
		return this.strMessage ? this.strMessage : ''
	}
	
	/**
	 * 에러메시지를 선언해주는 메소드
	 *
	 * @param java.lang.String 에러메시지
	 */
	void setMessage(String strMessage) {
		this.strMessage = strMessage
	}
	
	/**
	 * Overriding Method
	 *
	 * @return java.lang.String Exception 에러메시지와 발생한 Exception의 toString() 메시지를 조합한 출력
	 * @see java.lang.Exception#toString()
	 */
	String toString() {
		return this.e ? this.e.toString() : super.toString()
	}
}