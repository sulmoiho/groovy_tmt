package com.tomato.jef.util;

import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;

final class DateUtil {
	/**
	 * DB에 저장된 날짜의 저장 TYPE  <code>'20050218'</code>연월일
	 */
	final static String YYYYMMDD     = 'yyyyMMdd'   //DEFAULT TYPE
	
	/**
	 * DB에 저장된 날짜의 저장 TYPE  <code>'200502'</code>연월
	 */
	final static String YYYYMM       = 'yyyyMM'
	
	/**
	 * DB에 저장된 날짜의 저장 TYPE  <code>'200502'</code>월일
	 */
	final static String MMDD         ='MMdd'
	
	/**
	 * Default Mask Type <code>2005-02-18</code>
	 */
	final static String DEFAULT_MASK = 'yyyy-MM-dd'

	
	/**
	 * <pre>
	 * Constructor
	 * 객체의 인스턴스가 허용되지 않음
	 * </pre>
	 */
	private DateUtil() {}
	
	/**
	 * 오늘 날짜를 <CODE>&quot;yyyy-MM-dd&quot;<CODE> 형태로 만들어주는 메소드.
	 */
	static String getToday(){
		return DateUtil.getToday('yyyy-MM-dd')
	}
	
	/**
	 * 오늘 날짜를 주어진 날짜 포맷에 맞게 만들어주는 메소드.
	 */
	static String getToday(String mask) {
		return new Date().format(mask)
	}
	
	/**
	 * 날자 문자열을 java.util.Date 객체로 변환하여 리턴한다.<br>
	 * 변환중 Exception 발생시 null 을 리턴한다.
	 * @return 변환된 java.util.Date
	 */
	static Date parseDate(String dateSrc, String dateFormat){
		if(!dateSrc || dateSrc == '') return null

		def format = new SimpleDateFormat(dateFormat)
		try {
			return format.parse( dateSrc )
		} catch(ParseException pe) {
			pe.printStackTrace()	
			return null
		}
	}
	
	/**
	 * DEFAULT 문자 TYPE <code>yyyyMMdd</code> 이고 DEFAULT MASK TYPE이 <code>yyyy-MM-dd</code>일경우 <br/>
	 * 변환할 문자만 Parameter로 넘겨 호출
	 * @param strSource <code>String</code>
	 * @return  strSource의 strMask의 형태로 변환후의 날짜 TEXT <br/>
	 *  문자의 TYPE이나 MASK가 부적절할경우 원본 strSource를 리턴한다.
	 */
	static String dateFormat(String strSource) {
		return dateFormat(strSource, YYYYMMDD, DEFAULT_MASK)
	}
	
	/**
	 * DEFAULT 문자 TYPE <code>yyyyMMdd</code> 이고 USER MASK일때 변환할 문자와 변환하고자 하는 MASK를 Parameter로 넘겨 호출
	 * @param strSource <code>String</code> 날짜에 해당하는 문자열
	 * @param strMask <code>String</code> 변환하고자하는 Format
	 * @return strSource의 strMask의 형태로 변환후의 날짜 TEXT <br/>
	 *  문자의 TYPE이나 MASK가 부적절할경우 원본 strSource를 리턴한다.
	 */
	static String dateFormat(String strSource, String strMask) {
		return dateFormat(strSource, YYYYMMDD, strMask)
	}
	
	/**
	 * DEFAULT 문자 TYPE <code>yyyyMMdd</code> 이고 USER MASK일때 변환할 날짜 객체와 변환하고자 하는 MASK를 Parameter로 넘겨 호출
	 * @param date <code>Date</code> 해당 날짜 객체
	 * @param strMask <code>String</code> 변환하고자하는 Format
	 * @return date의 strMask의 형태로 변환후의 날짜 TEXT <br/>
	 *  문자의 TYPE이나 MASK가 부적절할경우 원본 strSource를 리턴한다.
	 */
	static String dateFormat(Date date, String strMask) {
		return dateFormat(date, YYYYMMDD, strMask)
	}
	
	/**
	 * DB에 CHAR, VARCHAR2 Type으로 들어간 Date를 MASK에 따라 알맞은 Format으로 변환
	 * @param dateSource 변환할 Source 날짜 문자열 또는 Date 객체
	 * @param strType 문자의 TYPE
	 * @param strMask 변환후의 MASK
	 * @return dateSource strMask의 형태로 변환후의 날짜 TEXT <br/>
	 *  문자의 TYPE이나 MASK가 부적절할경우 원본 dateSource를 리턴한다.
	 * @see java.text.SimpleDateFormat
	 */
	static String dateFormat(def dateSource, String strType, String strMask) {
		if(!dateSource || dateSource.toString() == '') return ''

		try {
			if(dateSource instanceof Date){
				return dateSource.format(strMask)
			}else if(dateSource instanceof String){
				return (new SimpleDateFormat(strType).parse(dateSource)).format(strMask)
			}else{
				return dateSource
			}
		} catch(Exception e){
			return dateSource
		}
	}
	
	/**
	 * 주어진 일자에서 Gap 만큼의 이전 또는 이후의 날짜를  MASK에 따라 알맞은 포맷으로 변환하여 반환한다.
	 * @param strDate 변환할 Source 날짜 문자열
	 * @param gap 해당 날짜의 이전 또는 이후 날짜간의 차이(음수면 이전날짜, 양수면 이후날짜)
	 * @param strMask 변환후의 MASK
	 * @return strDate에서 차이만큼 이전 또는 이후 날짜에 대해 strMask의 형태로 변환후의 날짜 TEXT <br/>
	 *  문자의 TYPE이나 MASK가 부적절할경우 원본 strDate를 리턴한다.
	 * @see java.text.SimpleDateFormat
	 */
	static String getDate(String strDate, int gap, String strMask) {
		if(!strDate || strDate == '') return ''
		
		try {
			return new SimpleDateFormat(strMask).parse(strDate).plus(gap).format(strMask)
		} catch(Exception e){
			return strDate
		}
	}
	
	/**
	 * toDate 날짜부터 fromDate 날짜 사이의 날 수를 계산하여 반환한다.
	 * @param fromDate 시작날짜 문자열
	 * @param toDate 종료날짜 문자열
	 * @param strMask 문자열 변환을 위한 MASK
	 * @return toDate 날짜부터 fromDate 날짜 사이의 날 수 toDate가 fromDate보다 크면 양의 정수를, 반대로 작으면 음의 정수를 반환한다.<br/>
	 * @exception 인자의 날짜 문자열이나 MASK가 부적절할경우 원본 Exception을 발생한다.
	 * @see java.text.SimpleDateFormat
	 */
	static int betweenDate(String fromDate, String toDate, String strMask) {
		try {
			return new SimpleDateFormat(strMask).parse(toDate).minus( new SimpleDateFormat(strMask).parse(fromDate))
		} catch(Exception e){
			e.printStackTrace()
		}
	}
	
	
	/**
	 * 해당월의 마지막 일자 반환한다.
	 * @param date 날짜 문자열
	 * @param strMask 문자열 변환을 위한 MASK
	 * @return last_date 해당월의 마지막 일자를 주어진 마스크 형태의 포맷으로 반환한다.<br/>
	 * @exception 인자의 날짜 문자열이나 MASK가 부적절할경우 원본 Exception을 발생한다.
	 * @see java.text.SimpleDateFormat
	 */
	static String getLastDayOfMonth(String date, String strMask){
		if(!date || date.toString() == '') return ''
		
		Date dt = new SimpleDateFormat(strMask).parse(date)

		def cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA)
		cal.set(dt.getYear(), dt.getMonth(), 1)

		dt.setDate(cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH))

		return "${dt.format(strMask)}"
	}
	
	public static void main(String[] args){
		println DateUtil.getLastDayOfMonth( '2009/04/10', 'yyyy/MM/dd')
		
	}
}
