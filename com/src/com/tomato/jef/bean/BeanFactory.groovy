package com.tomato.jef.bean;

import java.util.Map;

import com.tomato.jef.dao.DAO;
import com.tomato.jef.log.Log;
import com.tomato.jef.exception.AppException;
/**
 * Business Logic을 수행하는 각 Java Instance의 Singletone을 보장하는 Factory Class.
 * @author TomatoSystem
 * @since TmtFrameWork Version 2.0 (jsp/xbuilder)
 * @ re-version 209.12.13 sulmoiho
 */
final class BeanFactory {
	private static BeanFactory factory = new BeanFactory();
	private Map beanMap = null;
	
	/**
	 * 생성자
	 */
	private BeanFactory() {
		beanMap = [:]
	}
	
	/**
	 * BeanFactory 인스턴스를 반환하는 싱글톤 메소드
	 */
	static BeanFactory getInstance() {
		return factory
	}
	
	/**
	 * DAO Bean 객체를 반환한다.
	 * @param className java.lang.String 클래스명
	 */
	static DAO getDAO(String className) {
		def dao = getBean(className)
		return (DAO)dao
	}
	
	/**
	 * DAO Bean 객체를 반환한다.
	 * @param daoClass java.lang.Class 클래스
	 */
	static DAO getDAO(Class daoClass) {
		def dao = getBean(daoClass)
		return (DAO)dao
	}
	
	/**
	 * 빈 객체를 반환한다.
	 * @param className java.lang.String 클래스명
	 */
	static Object getBean(String className) {
		try {
			return BeanFactory.getBean(Class.forName(className))
		} catch(ClassNotFoundException e) {
			Log.error(e)
			return null
		}
	}
	
	/**
	 * 빈 객체를 반환한다.
	 * @param daoClass java.lang.Class 클래스
	 */
	static Object getBean(Class daoClass) {
		return getInstance().getBeanObject(daoClass)
	}
	
	/**
	 * 빈객체 저장소에 등록된 빈객체를 반환하는 메소드
	 */
	private synchronized Object getBeanObject(final Class beanClass) {
		if(beanClass == null) return null
		
		def className = beanClass?.name

		def bean = beanMap.get(className)
		if(bean == null) bean = createBean(beanClass, className)
		
		return bean
	}
	
	/**
	 * 빈 객체 생성 메소드
	 */
	private Object createBean(final Class beanClass, final String className) {
		def bean = null
		try {
			bean = beanClass.newInstance()
			
			beanMap.remove(className)
			beanMap.put(className, bean)
		} catch(InstantiationException e) {
			Log.error(e)
		} catch(IllegalAccessException e) {
			Log.error(e)
		}
		
		return bean
	}
	
	/**
	 * 빈 객체 타입을 반환한다.
	 */
	private static Class getBeanType(String className) {
		def obj = null
		try {
			obj = BeanFactory.getBean(Class.forName(className))
		} catch(ClassNotFoundException e) {
			Log.error(e)
		}
		
		return obj?.class
	}
	
	public static void main(String[] args) {
		def factory = BeanFactory.factory
		println BeanFactory.getDAO(null)
	}
}