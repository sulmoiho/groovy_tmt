package com.tomato.jef.log;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tomato.jef.jndi.JNDIHelper;

/**
 * <p>
 * 시스템 사용자 LOG를 출력하는 Class.
 * </p>
 * <code>common.properties</code>파일에 정의된 <code>debug_mode</code>에 따라 <code>info</code> Level Log의 Write여부를 결정한다.<br>
 * log 파일의 생성경로는 <code>System Properties</code>의 <code>user.dir</code>하위의 <code>LOGS</code> Directory에 생성된다.<br>
 * Log는 File과 Console에 출력된다.
 */
class Log {
	/**
	 * common.properties의 query_debug Property가 true로 설정되면 활성화 되는 로그<br>
	 * 본로그는 쿼리수행시 쿼리의 수행시간을 체크하여, common.properties의 query_time_out에 설정된<br>
	 * milisecond 단위의 시간을 초과할경우 로그를 발생시킨다.
	 */
	private final static String QUERY_DEBUG_LOGGER    = "com.tomato.query.debug";
	private final static String PROCESS_DEBUG_LOGGER  = "com.tomato.process.debug";
	private final static String DBMS_DEBUG_LOGGER     = "com.tomato.dems.debug";
	private static long QUERY_TIMEOUT 		= 3000L;
	private static long PROCESS_TIMEOUT = 5000L;
	
	private final String LOG_FILE_NAME 				= "tomato_jef.log";
	private final String QUERY_LOG_FILE_NAME 		= "tomato_jef_query_debug.log";
	private final String PROCESS_LOG_FILE_NAME		= "tomato_process_debug.log";
	private final String DBMS_ERRO_LOG_FILE_NAME	= "tomato_dbms_error_debug.log";
	
	private static Logger nomalLogger 		= null;
	private static Logger queryLogger 		= null;
	private static Logger processLogger 	= null;
	private static Logger dbmsErrorLogger 	= null;

	/**
	 * Logger Instance
     */
	private static Log log = new Log();

	/**
	 * Constructor Method
	 */
	private Log() {
//		InitializingLogger()
	}
	
	static {
		InitializingLogger()
	}
	
	/**
	 * 로그 초기화 
	 */
	private void InitializingLogger() {
		def strDebugMode  = JNDIHelper.getString('debug_mode')
		def strQueryDebug = JNDIHelper.getString('query_debug') 
		def strLogFilePath = null
		
		//debug mode 설정, debug 모드일때는 DEBUG Level 부터
        //아닐경우 WARNNING Level 부터 로깅
		if(strDebugMode && (strDebugMode.toUpperCase() == 'Y' || strDebugMode.toUpperCase() == 'TRUE' )){
			strDebugMode = 'DEBUG'
		}else{
			strDebugMode = 'WARN'
		}

		//로그 파일 위치
		if(System.properties.'os.name'?.contains('WIN')){
			strLogFilePath = JNDIHelper.getString('log_dir_win')
		}else{
			strLogFilePath = JNDIHelper.getString('log_dir_unix')
		}

		//로그 파일을 저장할 디렉토리를 생성한다.
		File logFile = new File(strLogFilePath)
		if(logFile.exists() == false) logFile.mkdirs()

		//Define Log4J Logger Instance
		Properties prop = new Properties()
		//root logger
		prop.setProperty('log4j.rootLogger', "${strDebugMode}, ConsoleLog, FileLog")
		//ConsoleLogger
		prop.setProperty('log4j.appender.ConsoleLog', 'org.apache.log4j.ConsoleAppender')
		prop.setProperty('log4j.appender.ConsoleLog.layout', 'org.apache.log4j.PatternLayout')
		prop.setProperty('log4j.appender.ConsoleLog.layout.ConversionPattern', "<%d{yyyy-MM-dd HH:mm:ss.SSS}><%-5p><%m>%n")
		//FileLogger
		prop.setProperty('log4j.appender.FileLog'            , 'org.apache.log4j.DailyRollingFileAppender')
		prop.setProperty('log4j.appender.FileLog.layout'     , 'org.apache.log4j.PatternLayout')
		prop.setProperty('log4j.appender.FileLog.File'       , "${strLogFilePath}${LOG_FILE_NAME}")
		prop.setProperty('log4j.appender.FileLog.DatePattern', "'.'yyyy-MM-dd");
		prop.setProperty('log4j.appender.FileLog.BufferSize' , '8192')
		prop.setProperty('log4j.appender.FileLog.Append'     , 'true')
		prop.setProperty('log4j.appender.FileLog.layout.ConversionPattern', "<%d{yyyy-MM-dd HH:mm:ss.SSS}><%-5p><%m>%n")

		//Query logger
		if(strQueryDebug && (strQueryDebug.toUpperCase() == 'Y' || strQueryDebug.toUpperCase() == 'TRUE')){
			prop.setProperty("log4j.logger.${QUERY_DEBUG_LOGGER}"	, 'DEBUG, QueryLog')
			prop.setProperty('log4j.appender.QueryLog'				, 'org.apache.log4j.DailyRollingFileAppender')
			prop.setProperty('log4j.appender.QueryLog.Threshold'	, 'DEBUG')
			prop.setProperty('log4j.appender.QueryLog.layout'		, 'org.apache.log4j.PatternLayout');
			prop.setProperty('log4j.appender.QueryLog.File'			, "${strLogFilePath}${QUERY_LOG_FILE_NAME}")
			prop.setProperty('log4j.appender.QueryLog.DatePattern'	, "'.'yyyy-MM-dd")
			prop.setProperty('log4j.appender.QueryLog.BufferSize'	, '8192')
			prop.setProperty('log4j.appender.QueryLog.Append'		, 'true')
			prop.setProperty('log4j.appender.QueryLog.layout.ConversionPattern"', "<%d{yyyy-MM-dd HH:mm:ss.SSS}><%-5p><%m>%n")
			
			def strQueryTimeOut = JNDIHelper.getString('query_time_out')
			try {
				QUERY_TIMEOUT = Long.parseLong(strQueryTimeOut)
			} catch(NumberFormatException nfe) {
				QUERY_TIMEOUT = 3000L
			} 
		}

		//Process debug logger
		prop.setProperty("log4j.logger.${PROCESS_DEBUG_LOGGER}" , 'DEBUG, ProcessLog' )
		prop.setProperty('log4j.appender.ProcessLog'			, 'org.apache.log4j.DailyRollingFileAppender')
		prop.setProperty('log4j.appender.ProcessLog.Threshold'	, 'DEBUG')
		prop.setProperty('log4j.appender.ProcessLog.layout'		, 'org.apache.log4j.PatternLayout')
		prop.setProperty('log4j.appender.ProcessLog.File'		, "${strLogFilePath}${PROCESS_LOG_FILE_NAME}")
		prop.setProperty('log4j.appender.ProcessLog.DatePattern', "'.'yyyy-MM-dd")
		prop.setProperty('log4j.appender.ProcessLog.BufferSize'	, '8192')
		prop.setProperty('log4j.appender.ProcessLog.Append'		, 'true')
		prop.setProperty('log4j.appender.ProcessLog.layout.ConversionPattern', "<%d{yyyy-MM-dd HH:mm:ss.SSS}><%-5p><%m>%n")

		//DBMS Error logger
		prop.setProperty("log4j.logger.${DBMS_DEBUG_LOGGER}"		, 'DEBUG, DbmsDebugLog')
		prop.setProperty('log4j.appender.DbmsDebugLog'				, 'org.apache.log4j.DailyRollingFileAppender')
		prop.setProperty('log4j.appender.DbmsDebugLog.Threshold'	, 'DEBUG')
		prop.setProperty('log4j.appender.DbmsDebugLog.layout'		, 'org.apache.log4j.PatternLayout')
		prop.setProperty('log4j.appender.DbmsDebugLog.File'			, "${strLogFilePath}${DBMS_ERRO_LOG_FILE_NAME}")
		prop.setProperty('log4j.appender.DbmsDebugLog.DatePattern'	, "'.'yyyy-MM-dd")
		prop.setProperty('log4j.appender.DbmsDebugLog.BufferSize'	, '8192')
		prop.setProperty('log4j.appender.DbmsDebugLog.Append'		, 'true')
		prop.setProperty('log4j.appender.DbmsDebugLog.layout.ConversionPattern', "<%d{yyyy-MM-dd HH:mm:ss.SSS}><%-5p><%m>%n")

		//log4j 설정
		PropertyConfigurator.configure(prop)

		nomalLogger     = Logger.getRootLogger()
		processLogger   = Logger.getLogger(PROCESS_DEBUG_LOGGER)
		dbmsErrorLogger = Logger.getLogger(DBMS_DEBUG_LOGGER)

		if(strQueryDebug && (strQueryDebug.toUpperCase() == 'Y' || strQueryDebug.toUpperCase() == 'TRUE')){
			queryLogger = Logger.getLogger(QUERY_DEBUG_LOGGER)
		}
	}

	/**
	 * Info log
	 * @param msg Info Log Message
	 */
	static void info(String msg) {
		try {
			nomalLogger.info(msg) 
		} catch(Throwable t) {
			t.printStackTrace()
		}
	}
	
	/**
	 * Debug log
	 * @param msg Debug Log Message
	 */
	static void debug(String msg) {
		try {
			nomalLogger.debug(msg)
		} catch (Throwable t) {
			t.printStackTrace()
		}
	}
	
	/**
	 * Warning log
	 * @param msg Log Message
	 */
	static void warning(String msg) {
		try {
			nomalLogger.warn(msg)
		} catch (Throwable t) {
			t.printStackTrace()
		}
	}
	
	/**
	 * Error log
	 * @param msg Log Message
	 */
	static void error(String msg) {
		try {
			nomalLogger.error(msg)
			processLogger.error(msg)
		} catch (Throwable t) {
			t.printStackTrace()
		}
	}
	
	/**
	 * Exception을 인자로 error Log Write 
	 * @param e <code>Exception</code> throw 된 Exception
	 * @see #error(String, Exception)
	 */
	static void error(Exception e) {
		error(e?.getMessage(), e)
	}
	
	/**
	 * <p>
	 * Error Log Writer.
	 * </p>
	 * 던져진 Exception의 root Exception까지 Exception Stack의 모든 내용을 출력한다. 
	 * @param msg <code>Sring</code> Message
	 * @param e <code>Exception</code> throwed Exception
	 */
	static void error(String msg, Exception e) {
		if(msg) error(msg)
		
		if(e){
			error(getStackTrace(e))

			Throwable throwable
			while(true){
				throwable = e.getCause()
				if(throwable == null) break
				error(getStackTrace(throwable))
			}
		}
	}
	
	/**
	 * Info Log Message and <code>HttpServletRequest</code>의 dataCatch and Log Write
	 * 
	 * @param request <code>HttpServletRequest</code>
	 * @param msg <code>String</code> Message문자열
	 * @see #dataCatch(String, HttpServletRequest)
	 */
	static void info(HttpServletRequest request, String msg) {
		info(dataCatch(request, msg))
	}
	
	/**
	 * Debug Log Writer
	 * @param request <code>HttpServletRequest</code>
	 * @param msg <code>String</code> Log Message
	 * @see #debug(String)
	 */
	static void debug(HttpServletRequest request, String msg) {
		debug(dataCatch(request, msg))
	}
	
	/**
	 * Warning Log Writer
	 * @param request <code>HttpServletRequest</code>
	 * @param msg <code>String</code> Log Message
	 * @see #warning(String)
	 */
	static void warning(HttpServletRequest request, String msg) {
		warning(dataCatch(request, msg))
	}
	
	/**
	 * Error Log Writer
	 * @param request <code>HttpServletRequest</code>
	 * @param msg <code>String</code> Log Message
	 * @see #error(String)
	 */
	static void error(HttpServletRequest request, String msg) {
		error(dataCatch(request, msg))
	}
	
	/**
	 * PreparedStatment에서 Sql Query문장과 바인드 변수를 받아 Log 작성
	 * @param strSql PreparedStatement Sql Query 문장
	 * @param param Bind Valiable
	 * @see #info(String)
	 */
	static void info(String strSql, Object[] param) {
		info(getFullQuery(strSql, param))
	}
	
	/**
	 * Query 디버깅을 위한 로그
	 * @param startTime
	 * @param endTime
	 * @param strSql
	 * @param param
	 */
	static void debug(long startTime, long endTime, String strSql, Object[] param) {
		try {
			if(queryLogger == null) return
			if(endTime - startTime < QUERY_TIMEOUT) return
		
			def msgbf = new StringBuffer(getFullQuery(strSql, param))
			msgbf << '< ' << getSpentTime(startTime, endTime) << ' >'
		
			queryLogger.debug(msgbf)
		} catch (Throwable t) {
			t.printStackTrace()
		}
	}
	
	/**
	 * Process 디버깅을 위한 로그
	 * @param startTime
	 * @param endTime
	 * @param strProcess
	 */
	static void debugProcess(long startTime, long endTime, String strProcess ) {
		try {
			if(processLogger == null) return
			if(endTime - startTime < PROCESS_TIMEOUT) return
			
			def msgbf = new StringBuffer(50)
			msgbf << '< ' << strProcess << ' >'
			msgbf << '< ' << getSpentTime(startTime, endTime) << ' process Time(sec) >'
			
			processLogger.debug(msgbf)
		} catch (Throwable t) {
			t.printStackTrace()
		}
	}
	
	/**
	 * SQL 질의 오류 디버깅을 위한 로그
	 * @param strErrMessage 오류 메세지
	 * @param intErrCode DB 오류코드
	 * @param strSql SQL 질의문
	 * @param param SQL 질의시에 넘어온 파라메터 Values
	 */
	static void dbErr( String strErrMessage , int intErrCode , String strSql, Object[] param){
		try {
			if(dbmsErrorLogger == null) return
			
			def msgbf = new StringBuffer()
			msgbf << "DBMS ERROR CODE :: ${intErrCode}"
			msgbf << "DBMS ERROR MSG  :: ${strErrMessage}"
			msgbf << getFullQuery(strSql, param)
			
			dbmsErrorLogger.info( msgbf.toString(), new Throwable())
		} catch (Throwable t) {
			t.printStackTrace()
		}
	}
	
	/**
	 * 쿼리 응답 시간 계산
	 */
	private static String getSpentTime(long startTime, long endTime) {
		double spentTime = ((double)(endTime - startTime)) / 1000D;

		return "${Double.toString(spentTime)} Sec"
	}
	
	private static String getStackTrace(Throwable throwable) {
		StringBuffer traceDesc;
		traceDesc = new StringBuffer();
		traceDesc.append(throwable.toString()).append("\n");
		
		StackTraceElement stackTrace[] = throwable.getStackTrace();
		if(stackTrace != null && stackTrace.length > 0) {
			for(int i = 0; i < stackTrace.length; i++) {
				traceDesc.append("\t at ").append(stackTrace[i].getClassName()).append(".").append(stackTrace[i].getMethodName()).append("(");
				if(stackTrace[i].getFileName() == null) {
					if(stackTrace[i].isNativeMethod()) traceDesc.append("Native)\n");
					else traceDesc.append("UnKnown)\n");
				}
				else traceDesc.append(stackTrace[i].getFileName()).append(":").append(stackTrace[i].getLineNumber()).append(")").append("\n");
			}
		}
		return traceDesc.toString();
	}
	
	/**
	 * SQL 질의문에 넘겨진 파라메터들을 셋팅하여 완성된 질의문을 생성하여 반환한다.
	 * @param strSql SQL 질의문
	 * @param param SQL 질의시에 넘어온 파라메터 Values
	 */
	private static String getFullQuery(String strSql, Object[] param) {
		//PreparentStatement SQL이 아니면 바로 리턴한다.
		if(strSql.indexOf('?') == -1) return strSql
		
		def sqlbf = new StringBuffer(strSql)

		try {
			if(param){
				def dateFormat = 'yyyy-MM-dd HH:mm:ss'
				
				String strParam = null
				def objIndex = 0
				param.each {
					if(it instanceof String){
						strParam = "'${it.toString()}'"
					}else if(it instanceof Date || it instanceof Timestamp || it instanceof Time){
						strParam = "'${new SimpleDateFormat(dateFormat).format((Date)it)}'"
					}else{
						strParam = it.toString()
					}
					
					objIndex = sqlbf.indexOf('?')
					sqlbf.delete(objIndex, objIndex + 1)
					sqlbf.insert(objIndex, strParam)
				}
			}
		} catch (Throwable t){
			t.printStackTrace()
		}
		
		return sqlbf.toString()
	}

	/**
	 * <code>HttpServletRequest</code>에 담긴 Attribute를 문자열로 반환
	 * @param request <code>HttpServletRequest</code>
	 * @param msg <code>String</code> 추가 메시지
	 * @return Message String
     */
	private static String dataCatch(HttpServletRequest request, String msg) {
		try {
			def msgbf = new StringBuffer()
			
			if(msg) msgbf << msg << '\n'
			
			//Request Header 의 값들
			msgbf << '\n# REQUEST HEADER LIST \n'
			request.headerNames.each { name ->
				msgbf <<	'Name : ' << name
				msgbf << '\tValue : ' << request.getHeader(name) << '\n'
			}
			//Request Parameter 의 값들
			msgbf << '\n# REQUEST PARAMETER LIST \n'
			request.parameterMap.each { key, value ->
				msgbf << 'Name : ' << key
				msgbf << '\tValue : ' << value << '\n'
			}
			//Request Attribute 의 값들
			msgbf << '\n# REQUEST ATTRIBUTE LIST \n'
			request.attributeNames.each { name ->
				msgbf << 'Name : ' << name
				msgbf << '\tValue : ' << request.getAttribute(name) << '\n'
			}
			//Session 의 값들
			HttpSession session = request.getSession(false)
			if(session){
				msgbf << '\n# SESSION ATTRIBUTE LIST \n'
				session.getAttributeNames().each { name ->
					if(name == '_AUTH_SESSION_MANAGER') return
					if(name == 'S_USER_INFO') return
					
					msgbf << 'Name : ' << name
					msgbf << '\tValue : ' << session.getAttribute(name) << '\n'
				}
			}

			return msgbf.toString()
		} catch (Throwable t) {
			return msg
		}
	}

	public static void main(String[] args){
		String aa = 'WI_ASS'
		println aa.contains('WIN')
	}
}