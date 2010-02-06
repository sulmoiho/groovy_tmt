package com.tomato.jef.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tomato.jef.jndi.JNDIHelper;

final class CookieUtil {
	/**
	 * <pre>
	 * Constructor
	 * 객체의 인스턴스가 허용되지 않음
	 * </pre>
	 */
	private CookieUtil(){}
	
	static Cookie setCookie(HttpServletRequest request, HttpServletResponse response, String strName, String strValue) {
		def cookieDomain = JNDIHelper.getString('cookie_domain')
		def cookiePath   = JNDIHelper.getString('cookie_path')
		
		Cookie cookie = new Cookie(strName, strValue)
		cookie.domain = cookieDomain
		cookie.path = cookiePath
		
		response.addCookie(cookie)
		
		return cookie
	}
	
	static String getCookie(HttpServletRequest request, HttpServletResponse response, String strName) {
		Cookie[] cookies = request.getCookies()
		if (cookies) {
			return cookies.find { it.getName() == strName }.getValue()
		}else{
			return null
		}
	}
	
	static List getCookieNames(HttpServletRequest request, HttpServletResponse response) {
		def nameList = []

		Cookie[] cookies = request.getCookies()
		if (cookies) {
			cookies.each { nameList.add(it.getName()) }
		}
		
		return nameList
	}
	
	static void removeCookies(HttpServletRequest request, HttpServletResponse response, String strName) {
		Cookie[] cookies = request.getCookies()
		
		if (cookies) {
			def cookieDomain = JNDIHelper.getString('cookie_domain')
			def cookiePath   = JNDIHelper.getString('cookie_path')

			def findedCookies = cookies.findAll { it.getName() == strName }
			
			Cookie cookie = null
			findedCookies.each {
				cookie = new Cookie(it.getName(), '')
				cookie.domain = cookieDomain
				cookie.path = cookiePath
				cookie.MaxAge = 0
				
				response.addCookie(cookie)
			}
		}
	}
	
	static void removeAllCookies(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies()
		
		if (cookies) {
			def cookieDomain = JNDIHelper.getString('cookie_domain')
			def cookiePath   = JNDIHelper.getString('cookie_path')
			
			Cookie cookie = null
			cookies.each { 
				cookie = new Cookie(it.getName(), '')
				cookie.domain = cookieDomain
				cookie.path = cookiePath
				cookie.MaxAge = 0
				
				response.addCookie(cookie)
			}
		}
	}
	
	public static void main(String[] args){
		println CookieUtil.getCookieNames(null, null)
	}
}