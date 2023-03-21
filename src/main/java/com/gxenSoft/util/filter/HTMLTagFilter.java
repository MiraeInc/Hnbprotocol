package com.gxenSoft.util.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 *************************************
 * PROGRAM ID  	: HTMLTagFilter
 * PACKAGE NM 	: com.gxenSoft.util.filter
 * CREATED DATE	: 2020.01.16
 * HISTORY 		: Cross site scripting 보안 관련 이슈로 인해 추가 
 *************************************
**/
public class HTMLTagFilter implements Filter{

	private FilterConfig config;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		chain.doFilter(new HTMLTagFilterRequestWrapper((HttpServletRequest)request), response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}
}
