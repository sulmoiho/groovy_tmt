package com.tomato.jef.util;

import java.text.NumberFormat;
import java.util.Locale;

final class NumFormat {
	
	//Locale.KOREA의 숫자형식
	private static NumberFormat nf = NumberFormat.getInstance(Locale.KOREA)
	
	/**
	 * <pre>
	 * Constructor
	 * 객체의 인스턴스가 허용되지 않음
	 * </pre>
	 */
	private NumFormat(){}

	/**
	 * <pre>
	 * int형 아규먼트를 화폐단위 형식의 String형으로 변환하는 Static Method
	 * </pre>
	 * @param intValue int형 value
	 * @return java.lang.String 화폐단위 형식의 문자열
	 * @see java.text.NumberFormat#format(double)
	 * @see java.lang.String#valueOf(int)
	 * @see java.lang.Double#parseDouble(String)
	 * @see java.lang.Exception
     */
	static String format(int intValue) {
		try {
			double dblValue = new Integer(intValue).doubleValue()
			return nf.format(dblValue)
		} catch (Exception e) {
			return String.valueOf(intValue)
		}
	}
	
	/**
	 * <pre>
	 * String형 아규먼트를 화폐단위 형식의 String형으로 변환하는 Static Method
	 * </pre>
	 * @param strValue java.lang.String String형 value
	 * @return java.lang.String 화폐단위 형식의 문자열
	 * @see java.text.NumberFormat#format(double)
	 * @see java.lang.Double#parseDouble(String)
	 * @see java.lang.Exception
	 */
	static String format(String strValue) {
		try {
			double dblValue = Double.parseDouble(strValue)
			return nf.format(dblValue)
		} catch (Exception e) {
			return strValue
		}
	}
	
	/**
	 * <pre>
	 * double형 아규먼트를 화폐단위 형식의 String형으로 변환하는 Static Method
	 * </pre>
	 * @param dblValue double형 value
	 * @return java.lang.String 화폐단위 형식의 문자열
	 * @see java.text.NumberFormat#format(double)
	 * @see java.lang.String#valueOf(int)
	 * @see java.lang.Exception
	 */
	static String format(double dblValue) {
		try {
			return nf.format(dblValue)
		} catch (Exception e) {
			return String.valueOf(dblValue)
		}
	}
	
	/**
	 * <pre>
	 * float형 아규먼트를 화폐단위 형식의 String형으로 변환하는 Static Method
	 * </pre>
	 * @param fltValue float형 value
	 * @return java.lang.String 화폐단위 형식의 문자열
	 * @see java.text.NumberFormat#format(double)
	 * @see java.lang.String#valueOf(int)
	 * @see java.lang.Float#doubleValue()
	 * @see java.lang.Exception
	 */
	static String format(float fltValue) {
		try {
			double dblValue = new Float(fltValue).doubleValue()
			return nf.format(dblValue)
		} catch (Exception e) {
			return String.valueOf(fltValue)
		}
	}
	
	/**
	 * <pre>
	 * long형 아규먼트를 화폐단위 형식의 String형으로 변환하는 Static Method
	 * </pre>
	 * @param lngValue long형 value
	 * @return java.lang.String 화폐단위 형식의 문자열
	 * @see java.text.NumberFormat#format(double)
	 * @see java.lang.String#valueOf(int)
	 * @see java.lang.Long#doubleValue()
	 * @see java.lang.Exception
	 */
	static String format(long lngValue) {
		try {
			double dblValue = new Long(lngValue).doubleValue()
			return nf.format(dblValue)
		} catch (Exception e) {
			return String.valueOf(lngValue)
		}
	}

	public static void main(String[] args){
		println NumFormat.format(1000.0f)
	}
}