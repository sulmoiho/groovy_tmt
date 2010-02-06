package com.tomato.jef.jndi;

import com.tomato.jef.resource.Collector;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.tomato.jef.constants.ServletKey;
import javax.naming.NamingException;
import java.util.ResourceBundle;
import java.util.MissingResourceException;


class JNDIHelper {
	
	private static ResourceBundle bundle;
	
	static {
		try {
			bundle = ResourceBundle.getBundle('common', Locale.KOREA)
		} catch(Exception e) {
			e.printStackTrace()
		}
	}
	/**
	 * <pre>
	 * Constructor
	 * static 메소드를 담고 있으며 객체의 instance를 허용하지 않음
	 * </pre>
	 */
	private JNDIHelper() {}
	
	/**
	 * <pre>
	 * JNDI Context를 반환하는 static method
	 * </pre>
	 *
	 * @return javax.naming.Context
	 *
	 * @see javax.naming.NamingException
	 * @see javax.naming.Context
	 *
	 * @exception javax.naming.InitialContext
	 */
	static Context getInitialContext() throws NamingException {
		return new InitialContext()
	}
	
	/**
	 * <pre>
	 * JNDI Context에서 객체의 instance를 찾아오는 메소드<BR>
	 * 객체의 형이 javax.ejb.EJBHome 인 경우에는 Remote Interface를 구현한 객체의 instance를 반환함.
	 * </pre>
	 *
	 * @param strJNDIName java.lang.String JNDI Context에 Bind된 JNDI 이름
	 * @return java.lang.Object 객체의 인스턴스
	 */
	static Object lookup(String strJNDIName) {
		Context ctx = null
		def obj = null
		
		try {
			obj = getResourceProperty(strJNDIName)
		} catch (Exception e) {
			try {
				ctx = getInitialContext()
				obj = ctx.lookup(strJNDIName)
			} catch (Exception ex) {
				//skip
			} finally {
				if(ctx) ctx.close()
			}
		}
		
		return obj
	}
	
	/**
	 * <pre>
	 * 리소스 번들로부터 속성값을 반환한다.
	 * </pre>
	 * 
	 * @param name 리소스 키
	 * @return Object 해당 키의 리소스 값
	 */
	static Object getResourceProperty(String name) {
		try {
			return bundle.getObject(name)
		} catch (MissingResourceException mre) {
			//skip
			return null
		} catch (Exception e) {
			//skip
			return null
		}
	}
	
	/**
	 * <pre>
	 * JNDI Context에서 객체의 instance를 java.lang.String으로 return하는 메소드<BR>
	 * </pre>
	 * @param strJNDIName java.lang.String JNDI Context에 Bind된 JNDI 이름
	 * @return java.lang.String 객체의 인스턴스명
	 */
	static String getString(String strJNDIName) {
		def obj = lookup(strJNDIName)
		
		if(obj && obj instanceof String){
			try {
				return new String(((String)obj).getBytes('UTF-8'), ServletKey.EN_CHARTER_SET )
			} catch(Exception e) {
				e.printStackTrace()
				return null
			}
		}else{
			return null
		}
	}
	
	/**
	 * <pre>
	 * JNDI Context에 바인딩 된 객체를 새로운 instance를 생성하여 기존의 바인딩된 <BR>
	 * Object를 덮어쓰기 하는 메소드<BR>
	 * </pre>
	 * @param strJNDIName java.lang.String JNDI Context에 Bind된 JNDI 이름
	 * @param objJNDIObject java.lang.Object 객체의 인스턴스
	 *
	 * @see javax.naming.Context
	 * @see javax.naming.Context#lookup(java.lang.String)
	 * @see javax.naming.Context#rebind(java.lang.String, java.lang.Object)
	 * @see java.lang.Object
	 * @see java.lang.Class
	 *
	 * @exception java.lang.Exception
	 */
	static void bind(String strJNDIName, Object objJNDIObject) {
		Context ctx = null
		
		try {
			ctx = getInitialContext()
			ctx.rebind(strJNDIName, objJNDIObject)
		} catch (Exception e) {
			e.printStackTrace()
		} finally {
			Collector.release(ctx)
		}
	}
	
	/**
	 * <pre>
	 * JNDI Context을 close하는 메소드
	 * </pre>
	 */
	static void destroyContext() {
		bind('blnPropLoaded', false)
	}
}
