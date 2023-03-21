package com.gxenSoft.util.pathUtil;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
 
/**
   *************************************
   * PROJECT   : GatsbyMall
   * PROGRAM ID  : PathUtil
   * PACKAGE NM : com.gxenSoft.util.pathUtil
   * AUTHOR	 : 김 민 수
   * CREATED DATE  : 2017. 6. 20. 
   * HISTORY :   
   *
   *************************************
 **/	
public class PathUtil {

	/**
	 * @Method : getCtx
	 * @Date: 2017. 6. 20.
	 * @Author :  김  민  수
	 * @Description	:	Context Path 반환
	 * @HISTORY :
	 *
	 */
	public static String getCtx()throws Exception{
		String contextPath = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getContextPath();
		return contextPath;
	}
	
	/**
	 * @Method : getDomain
	 * @Date: 2017. 6. 20.
	 * @Author :  김  민  수
	 * @Description	:	도메인 반환	
	 * @HISTORY :
	 *
	 */
	public static String getDomain()throws Exception{
		String domain = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getServerName();
		return domain;
	}
	
	/**
	 * @Method : getCurrentUrl
	 * @Date: 2017. 6. 21.
	 * @Author :  김  민  수
	 * @Description	:	현재 URL 반환
	 * @HISTORY :
	 *
	 */
	public static String getCurrentUrl()throws Exception{
		StringBuffer currentUrl = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getRequestURL();
		return currentUrl.toString();
	}
	
	
	/**
	 * @Method : getRefer
	 * @Date: 2017. 6. 21.
	 * @Author :  김  민  수
	 * @Description	:	이전 페이지 이동 시 사용
	 * @HISTORY :
	 *
	 */
	public static String getRefer()throws Exception{
		String referer = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getHeader("REFERER");
		return referer;
	}
	
	/**
	 * @Method : getDevice
	 * @Date		: 2017. 8. 11.
	 * @Author	:  유  준  철 
	 * @Description	:	디바이스 Context Path ex) w,m
	 */
	public static String getDevice()throws Exception{
		String contextPath = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getContextPath().replaceAll("/", "");
		return contextPath;
	}
	
	   /**
	    * @Method : getDeviceNm
	    * @Date: 2017. 8. 24.
	    * @Author :  서 정 길
	    * @Description	:	디바이스명 반환 ex) M : 모바일, P : PC등 모바일 외
	   */
	public static String getDeviceNm() throws Exception{
		String contextPath = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getContextPath();
		if(contextPath.equals("/m")){
			return "M";
		}else{
			return "P";
		}
	}

}
