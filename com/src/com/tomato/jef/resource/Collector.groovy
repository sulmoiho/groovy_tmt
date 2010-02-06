package com.tomato.jef.resource;


import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;

import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;

import java.io.FileInputStream;
import java.io.InputStream;

import com.tomato.jef.log.Log;

/**
 * <pre>
 * 사용된 자원을 회수하는 Utility Class
 * java.sql.Result, java.sql.Statement, java.sql.Connection,
 * javax.ejb.EJBObject, javax.ejb.EJBLocalObject 객체를 회수한다.
 * </pre>
 *
 * @see java.io.Serializable
 */
final class Collector {
	/**
	 * <pre>
	 * Constructor
	 * 객체의 instance를 허용하지 않음
	 * </pre>
	 */
	private Collector() {}

	/**
	 * <pre>
	 * java.sql.ResultSet, java.sql.Statement, java.sql.Connection을 회수하는 메소드
	 * </pre>
	 *
	 * @param rs java.sql.ResultSet
	 * @param preStmt java.sql.Statement
	 * @param conn java.sql.Connection
	 * @see java.sql.ResultSet
	 * @see java.sql.Statement
	 * @see java.sql.Connection
	 * @see java.sql.ResultSet#close()
	 * @see java.sql.Statement#close()
	 * @see java.sql.Connection#close()
	 * @see java.lang.Exception
	 * @see com.tomato.jef.log.Log#error(Exception)
	 */
	static void release(ResultSet rs, Statement stmt, Connection conn) {
		try {
			rs?.close()
			rs = null
		} catch (Exception e) {
			Log.error(e)
		}
		
		try {
			stmt?.close()
			stmt = null
		} catch (Exception e) {
			Log.error(e)
		}
		
		try {
			conn?.close()
			conn = null
		} catch (Exception e) {
			Log.error(e)
		}
	}
	
	/**
	 * <pre>
	 * java.sql.ResultSet, java.sql.PreparedStatement, java.sql.Connection을 회수하는 메소드
	 * </pre>
	 *
	 * @param rs java.sql.ResultSet
	 * @param pstmt java.sql.Statement
	 * @param conn java.sql.Connection
	 * @see java.sql.ResultSet
	 * @see java.sql.PreparedStatement
	 * @see java.sql.Connection
	 * @see java.sql.ResultSet#close()
	 * @see java.sql.Statement#close()
	 * @see java.sql.Connection#close()
	 * @see java.lang.Exception
	 * @see com.tomato.jef.log.Log#error(Exception)
	 */
	static void release(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		try {
			rs?.close()
			rs = null
		} catch (Exception e) {
			Log.error(e)
		}
		
		try {
			pstmt?.close()
			pstmt = null
		} catch (Exception e) {
			Log.error(e)
		}
		
		try {
			conn?.close()
			conn = null
		} catch (Exception e) {
			Log.error(e)
		}
	}
	
	/**
	 * <pre>
	 * java.sql.Connection을 회수하는 메소드
	 * </pre>
	 *
	 * @param conn java.sql.Connection
	 *
	 * @see java.sql.Connection
	 */
	static void release(Connection conn) {
		release(null, null, conn)
	}
	
	/**
    * <pre>
    * java.sql.Statement을 회수하는 메소드
    * </pre>
    *
    * @param stmt java.sql.Statement
    *
    * @see java.sql.Statement
    */
	static void release(Statement stmt) {
		release(null, stmt, null)
	}
	
	/**
	 * <pre>
	 * java.sql.PreparedStatement을 회수하는 메소드
	 * </pre>
	 *
	 * @param pstmt java.sql.PreparedStatement
	 *
	 * @see java.sql.PreparedStatement
	 */
	static void release(PreparedStatement pstmt) {
		release(null, pstmt, null)
	}
	
	/**
	 * <pre>
	 * java.sql.ResultSet을 회수하는 메소드
	 * </pre>
	 *
	 * @param rs java.sql.ResultSet
	 * @see java.sql.ResultSet
	 */
	static void release(ResultSet rs) {
		release(rs, null, null)
	}
	
	/**
	 * <pre>
	 * java.io.InputStream을 회수하는 메소드
	 * </pre>
	 *
	 * @param ins java.io.InputStream
	 * @see java.io.InputStream
	 */
	static void release(InputStream ins) {
		try {
			ins?.close()
		} catch (Exception e) {
			Log.error(e)
		}
	}
	
	/**
	 * <pre>
	 * java.io.OutputStream을 회수하는 메소드
	 * </pre>
	 *
	 * @param out java.io.OutputStream
	 * @see java.io.OutputStream
	 */
	static void release(OutputStream out) {
		try {
			out?.close()
		} catch (Exception e) {
			Log.error(e)
		}
	}
	
	/**
	 * <pre>
	 * java.io.Reader을 회수하는 메소드
	 * </pre>
	 *
	 * @param reader java.io.Reader
	 * @see java.io.Reader
	 */
	static void release(Reader reader) {
		try {
			reader?.close()
		} catch (Exception e) {
			Log.error(e)
		}
	}
	
	/**
	 * <pre>
	 * java.io.Writer을 회수하는 메소드
	 * </pre>
	 *
	 * @param writer java.io.Writer
	 * @see java.io.Writer
	 */
	static void release(Writer writer) {
		try {
			writer?.close()
		} catch (Exception e) {
			Log.error(e)
		}
	}
	
	/**
	 * <pre>
	 * javax.naming.Context을 회수하는 메소드
	 * </pre>
	 *
	 * @param ctx javax.naming.Context
	 * @see javax.naming.Context
	 */
	public static void release(Context ctx) {
		try {
			ctx?.close()
		} catch (Exception e) {
			Log.error(e)
		}
	}
	
	/**
	 * <pre>
	 * javax.ejb.EJBLocalObject를 회수하는 메소드
	 * </pre>
	 *
	 * @param obj javax.ejb.EJBLocalObject
	 *
	 * @see java.lang.Exception
	 * @see javax.ejb.EJBLocalObject#remove()
	 * @see com.tomato.jef.log.Log#error(Exception)
	 */
	static void release(EJBLocalObject obj) {
		try {
			obj?.remove()
			obj = null
		} catch (Exception e) {
			Log.error(e)
		}
	}
	
	/**
	 * <pre>
	 * javax.ejb.EJBObject를 회수하는 메소드
	 * </pre>
	 *
	 * @param obj javax.ejb.EJBObject
	 *
	 * @see java.lang.Exception
	 * @see javax.ejb.EJBObject#remove()
	 * @see com.tomato.jef.log.Log#error(Exception)
	 */
	static void release(EJBObject obj) {
		try {
			obj?.remove()
			obj = null
		} catch (Exception e) {
			Log.error(e)
		}
	}
	
	public static void main(String[] args) {

	}
}
