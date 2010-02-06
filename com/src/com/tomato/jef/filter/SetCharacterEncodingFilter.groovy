package com.tomato.jef.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.tomato.jef.constants.ServletKey;

class SetCharacterEncodingFilter implements Filter{
	/**
	 * The filter configuration object we are associated with.  If this value
	 * is null, this filter instance is not currently configured.
	 */
	protected FilterConfig filterConfig = null;
	
	private static final String ENCODING_HEADER_NAME = 'encoding';
	
	/**
	 * Force a character encoding specified by encoding given at web configuration 
	 */
	protected boolean force = false;
	
	/**
	 * Should a character encoding specified from web configuration file(web.xml)
	 */
	protected String encoding = ServletKey.EN_CHARTER_SET;
	
	/** 
	 * Take this filter out of service.
	 */
	public void destroy() {
		this.filterConfig = null
	}
	
	/**
	 * Select and set (if specified) the character encoding to be used to
	 * interpret request parameters for this request.
	 *
	 * @param request The servlet request we are processing
	 * @param response The servlet response we are creating
	 * @param chain The filter chain we are processing
	 * @exception IOException if an input/output error occurs
	 * @exception ServletException if a servlet error occurs
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//Conditionally select and set the character encoding to be used
		//인코딩 설정이 강제된 경우에는 web.xml에 설정된 인코딩 또는 디폴트 인코딩으로 모든 요청을 인코딩 처리한다.
		if (this.force) {
			request.setCharacterEncoding(this.encoding)
		//인코딩 설정이 강제되지 않은 경우에는 클라이언트 요청 헤더의 인코딩 또는 디폴트 인코딩으로 요청을 인코딩 처리한다.	
		}else{
			def clientEncode = getClientEncoding(request)
			request.setCharacterEncoding(clientEncode ? clientEncode : this.encoding)
		}
		
		// Pass control on to the next filter
		chain.doFilter(request, response)
	}
	
	/**
	 * Place this filter into service.
	 *
	 * @param filterConfig The filter configuration object
	 */
	public void init(FilterConfig config) throws ServletException {
		this.filterConfig = config
		
		//클라이언트단의 요청 인코딩을 무시하고 서버단의 인코딩 설정으로 모든 요청에 대한 인코딩을 강제할지 여부 확인
		def value = config.getInitParameter('force')
		if(value && value.toLowerCase() == 'true'){
			this.force = true
			
			value = config.getInitParameter('encoding')
			if(value){
				this.encoding = value.toUpperCase()
			}
		}
	}

	/**
	 * 클라이언트에서 넘어온 HTTP 요청들의 헤더 내에 설정된 인코딩 정보를 반환한다.
	 */
	private String getClientEncoding(ServletRequest request){
		def clientEncodeing = null
		
		if(request instanceof HttpServletRequest){
			clientEncodeing = ((HttpServletRequest)request).headers[this.ENCODING_HEADER_NAME]
		}
		
		return clientEncodeing
	}
}
