package com.tomato.jef.exception;

/**
 * DataSet에서 요청한 Type으로 형변환할 수 없을때 발생하는 Exception.
 *
 * <P>
 * @version 0.9, 2003-10-23
 * @author  yb2u
 */
class DataTypeException extends Exception {
	static final long serialVersionUID = -4845115573787736320L;
	
	/**
     * DataSet에서 요청한 Type으로 형변환할 수 없을때 발생하는 Exception에 대한 생성자.
     *
     * <P>
     * @see  java.lang.Exception
     */
    DataTypeException() {
      super('Wrong instance type')
    }
}
