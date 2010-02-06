package com.tomato.jef.util;


import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import com.tomato.jef.constants.ServletKey;

/**
 * Character Set 설정을 하여 사용언어에 맞는 Encoding 문자셋으로 변환시켜주는 class.
 *
 * <P>
 * Java에서 지원하는 Encoding 문자셋 값은
 * <A href="http://java.sun.com/j2se/1.4.1/docs/guide/intl/encoding.doc.html" target="_blank">http://java.sun.com/j2se/1.4.1/docs/guide/intl/encoding.doc.html</A>
 * 에서 확인 가능.
 * </P>
 *
 * <P>
 * @re-version 1.0, 2009.12.05
 * @author sulmoiho
 */
final class EncodingUtil {
	static final String DEFAULT_ENCODING           = ServletKey.EN_CHARTER_SET

	/**
     * Windows Latin-1 (<CODE>Cp1252</CODE>) 인코딩 문자셋 형식으로 처리된
     * <CODE>String</CODE> 변수를 한글 인코딩 문자셋 형식으로 변환시켜주는 메소드.
     *
     * <P>
     * KSC 5601, EUC encoding, Korean (<CODE>EUC_KR</CODE>) 인코딩 문자셋 형식으로 변환됨.
     * </P>
     *
     * <P>
     * @param   strOld  Windows Latin-1 인코딩 문자셋 형식으로 처리된 <CODE>String</CODE> 변수
     * @return  한글 인코딩 문자셋 형식으로 변환된 <CODE>String</CODE> 변수
     */
	static String toKorean(String strOld) {
		return toEncoding(strOld, 'Cp1252', EncodingUtil.DEFAULT_ENCODING)
	}

	/**
     * Windows Latin-1 (<CODE>Cp1252</CODE>) 인코딩 문자셋 형식으로 처리된
     * <CODE>String</CODE> 변수를 한글 인코딩 문자셋 형식으로 변환시켜주는 메소드.
     *
     * <P>
     * Windows Korean (<CODE>MS949</CODE>) 인코딩 문자셋 형식으로 변환됨.
     * </P>
     *
     * <P>
     * @param   strOld  Windows Latin-1 인코딩 문자셋 형식으로 처리된 <CODE>String</CODE> 변수
     * @return  한글 인코딩 문자셋 형식으로 변환된 <CODE>String</CODE> 변수
     */
	static String toMS949(String strOld) {
        return toEncoding(strOld, 'Cp1252', 'MS949')
    }
	
	/**
	 * 한글 인코딩 문자셋 형식으로 처리된 <code>String</code> 변수를 
	 * Windows Latin-1 (<CODE>Cp1252</CODE>) 인코딩 문자셋 형식으로 변환시켜주는 메소드.
	 *
	 * <P>
	 * @param   strOld  한글 인코딩 문자셋 형식으로 처리된 <code>String</code> 변수
	 * @return  Windows Latin-1 인코딩 문자셋 형식으로 변환된 <code>String</code> 변수
	 */
	static String toCp1252(String strOld) {
		return toEncoding(strOld, EncodingUtil.DEFAULT_ENCODING, 'Cp1252')
	}
	
	/**
	 * 처리된 인코딩 문자셋 형식을 다른 문자셋 형식으로 변환시켜주는 메소드.
	 *
	 * <P>
	 * Windows Korean (<CODE>MS949</CODE>) 한글 인코딩 문자셋 형식으로 처리된 <CODE>String</CODE> 변수를
	 * Windows Latin-1 (<CODE>Cp1252</CODE>) 인코딩 문자셋 형식으로 변환하고자 한다면
	 * 두번째 <CODE>String</CODE> 변수값을 &lt;MS949&gt;으로, 세번째 변수값을 &lt;Cp1252&gt;으로 입력
	 * </P>
	 *
	 * <p>
	 * @param   strOld             처리하고자 하는 <CODE>String</CODE> 변수
	 * @param   strOrgEncoding     변환전의 <CODE>String</CODE>에 처리되었던 인코딩 문자셋 값
	 * @param   strTargetEncoding  변환시키고자 하는 인코딩 문자셋 값
	 * @return  변환시킨 인코딩 문자셋 형식의 <CODE>String</CODE> 변수
	 */
	static String toEncoding(String strOld, String strOrgEncoding, String strTargetEncoding) {
		String strNew = null
		
		try {
			strNew = new String(strOld.getBytes(strOrgEncoding), strTargetEncoding)
		} catch (UnsupportedEncodingException usee) {
		} catch (NullPointerException npe) {
		}
		
		return strNew
	}
	
	/**
     * Unicode 1.2 문자셋 형식으로 인코딩된 <CODE>String</CODE> 변수를
     * Unicode 2.0 인코딩 문자셋 형식으로 변환시켜주는 메소드.
     *
     * <P>
     * Oracle 7.3의 경우 문자셋이 Unicode 1.2 형식으로 DB에 저장되므로,
     * <CODE>SELECT</CODE>해서 사용할 경우 Unicode 2.0 인코딩 문자셋 형식으로 변환해주어야 함.
     * </P>
     *
     * @param   strOld  Unicode 1.2 인코딩 문자셋 형식으로 처리된 <CODE>String</CODE> 변수
     * @return  Unicode 2.0 인코딩 문자셋 형식으로 바꾼 <CODE>String</CODE> 변수
     */
    static String toUnicode20(String strOld) {
        def strbfNew

        try {
            strbfNew = new StringBuffer()
            StringCharacterIterator itOld = new StringCharacterIterator(strOld)

            for (char chOld = itOld.first(); chOld != CharacterIterator.DONE; chOld = itOld.next()) {
                Character.UnicodeBlock unicode = Character.UnicodeBlock.of(chOld)

                if (unicode == null) {
                    byte[] bytNew = new byte[2]
                    String strTemp = null

                    if (chOld < 13312 && chOld > 19967) {
                    	strbfNew << chOld
                    } else if (chOld >= 15662) {
                    	strTemp = toCp1252(String.valueOf(chOld))

                    	strbfNew << strTemp.charAt(0)
	                } else {
	                    bytNew[0] = (byte)((chOld - 13312) / 94 + 176)
	                    bytNew[1] = (byte)((chOld - 13312) % 94 + 161)
	                    strTemp = new String(bytNew, EncodingUtil.DEFAULT_ENCODING).trim()
	
	                    if (strTemp == '') {
	                        strbfNew << chOld
	                    } else {
	                        char chNew = strTemp.charAt(0)
	
	                        switch (chNew) {
	                        case 36:
	                            chNew = chOld
	                            break
	                        case 35:
	                            chNew = chOld
	                            break
	                    }
	
	                    strbfNew << chNew
                    }
                }
            } else {
        	    strbfNew << chOld
            }
        }

        } catch (UnsupportedEncodingException usee) {
        } catch (NullPointerException npe) {
        }

        return strbfNew?.toString()
    }


    /**
     * Unicode 2.0 문자셋 형식으로 인코딩된 <CODE>String</CODE> 변수를
     * Unicode 1.2 인코딩 문자셋 형식으로 변환시켜주는 메소드.
     *
     * <P>
     * Oracle 7.3의 경우 문자셋이 Unicode 1.2 형식으로 DB에 저장되므로,
     * <CODE>INSERT, UPDATE</CODE>해서 사용할 경우 Unicode 1.2 인코딩 문자셋 형식으로 변환해주어야 함.
     * </P>
     *
     * @param   strOld  Unicode 2.0 인코딩 문자셋 형식으로 처리된 <CODE>String</CODE> 변수
     * @return  Unicode 1.2 인코딩 문자셋 형식으로 바꾼 <CODE>String</CODE> 변수
     */
    public static String toUnicode12(String strOld) {
        def strbfNew

        try {
            strbfNew = new StringBuffer()

            for (i in 0..< strOld.length()) {
            char chOld = strOld.charAt(i)

            if ((chOld < 44032) && (chOld > 55203)) {
                strbfNew << chOld
            } else {
                byte[] bytNew
                char chNew

                bytNew = String.valueOf(chOld).getBytes(EncodingUtil.DEFAULT_ENCODING)

                if (bytNew.length == 2) {
                    chNew = (char)(13312 + ((bytNew[0] & 255) - 176) * 94 + (bytNew[1] & 255) - 161)
                } else {
                    chNew = chOld
                }

                strbfNew << chNew
            }
        }

        } catch (UnsupportedEncodingException usee) {
        } catch (NullPointerException npe) {
        }

        return strbfNew?.toString()
    }
}
