package com.gxenSoft.util.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gxenSoft.mall.common.web.CommonMethod;
import com.gxenSoft.message.SpringMessage;


/**
 *************************************
 * PROGRAM ID  : UrlRewriteFilter
 * PACKAGE NM : com.gxenSoft.util.filter
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2018. 4. 18. 
 * HISTORY :
 *
 *************************************
 **/
public class UrlRewriteFilter implements Filter {
	static final Logger logger = LoggerFactory.getLogger(UrlRewriteFilter.class);
	
	@Override
    public void init(FilterConfig config) throws ServletException {
    }
	
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
    	HttpServletRequest req = (HttpServletRequest) request;
    	HttpServletResponse res = (HttpServletResponse) response;
    	 
    	String uri = req.getRequestURI();
    	String getDomain = req.getServerName();
    	String getParam = req.getQueryString();
    	String webDomain = SpringMessage.getMessage("server.domain");
    	String mobileDomain = SpringMessage.getMessage("server.mDomain");
    	
    	String [] splitParam = getDomain.split("\\.");
		String domainType =  splitParam[0];
		String device = CommonMethod.isDevice(req);
		String mobilePathUrl = "";
		
		if(getDomain.contains("gatsby")){
			String domain = "";
    		
    		if(domainType.equals("m")){
    			domain = mobileDomain;
    		}else{
    			domain = webDomain;
    		}
    		
    		domain = domain+ uri;
    		if (getParam != null){	
	    		if (!getParam.isEmpty()) {
	    			domain = domain+ "?" + getParam;
	    		}
    		}
    		String site = new String(domain);
			res.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
			res.setHeader("Location", site);
			chain.doFilter(req, res);
		}else{
			String contextPath = req.getContextPath();

			if(uri.contains("kakaoCallback")){
				chain.doFilter(request, response);
			}else{
				//if(device.equals("m") && !domainType.equals("m")){
				if(device.equals("m") && !contextPath.toUpperCase().equals("/M")){
					mobilePathUrl = mobileDomain+uri.replace("/w/", "/m/");
					
					if (getParam != null){	
						if (!getParam.isEmpty()) {
							mobilePathUrl = mobilePathUrl+ "?" + getParam;
						}
					}
					
					String site = new String(mobilePathUrl);
					res.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
					res.setHeader("Location", site);
					chain.doFilter(req, res);
					
				}else{
					chain.doFilter(request, response);
				}
			}
		}
    }
    
	@Override
	public void destroy() {
	}
}
