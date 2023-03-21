package com.gxenSoft.mall.lgdacom.vo;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gxenSoft.message.SpringMessage;
import com.gxenSoft.util.pathUtil.PathUtil;

public class PaycoSettingVO {

	/*
	
	String sellerKey 			= "S0FSJE";       	// 가맹점 코드 - 파트너센터에서 알려주는 값으로, 초기 연동 시 PAYCO에서 쇼핑몰에 값을 전달한다.
	String cpId 				= "PARTNERTEST";	// 상점ID
	String productId 			= "PROD_EASY";		// 상품ID
	String deliveryId 		= "DELIVERY_PROD";	// 배송비상품ID
	String deliveryReferenceKey = "DV0001";			// 가맹점에서 관리하는 배송비상품 연동 키
	String serverType 		= "DEV";			// 서버유형. DEV:개발, REAL:운영
	String logYn 				= "Y";				// 로그Y/N


	//도메인명 or 서버IP  
	String domainName = "http://222.112.106.57:8443/w/payco";   */

	private	String sellerKey 			= "";       		// 가맹점 코드 - 파트너센터에서 알려주는 값으로, 초기 연동 시 PAYCO에서 쇼핑몰에 값을 전달한다.
	private	String cpId 				= "";		   		 // 상점ID
	private	String productId 			= "";				// 상품ID
	private	String deliveryId 		= "";				// 배송비상품ID
	private	String deliveryReferenceKey = "";			// 가맹점에서 관리하는 배송비상품 연동 키
	private	String serverType 		= "DEV";			// 서버유형. DEV:개발, REAL:운영
	private	String logYn 				= "Y";				// 로그Y/N
	private String domainName = ""; // 도메인명
	private String paycoReturnUrl = ""; //결제결과 return url
	private String paycoBankReturnUrl = ""; // 무통장 입금결과 return url
	
	   /**
	    * @Method : getCurrentUrl
	    * @Date: 2017. 8. 30.
	    * @Author :  서 정 길
	    * @Description	:	현재 URL 반환
	   */
	private String getCurrentUrl() throws Exception{
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		String strCurrentUrl = request.getScheme() + "://" + request.getServerName();
		strCurrentUrl += (request.getServerPort() == 80 || request.getServerPort() == 443) ? "" : ":" + request.getServerPort();
		strCurrentUrl += request.getContextPath(); 
		
		return strCurrentUrl;
	}

	//생성자
	public PaycoSettingVO () throws Exception {
		this.sellerKey = SpringMessage.getMessage("payco.sellerKey"); 
		this.cpId = SpringMessage.getMessage("payco.cpId");
		this.productId = SpringMessage.getMessage("payco.productId");
		this.serverType = SpringMessage.getMessage("payco.serverType");
		this.logYn = SpringMessage.getMessage("payco.logYn");
		this.paycoReturnUrl = getCurrentUrl() + SpringMessage.getMessage("payco.paycoReturnUrl");
		//this.paycoBankReturnUrl = getCurrentUrl() + SpringMessage.getMessage("payco.paycoBankReturnUrl");
		//this.paycoBankReturnUrl = "https://www.mandom.co.kr/w" + SpringMessage.getMessage("payco.paycoBankReturnUrl");		
		//this.paycoBankReturnUrl = "http://58.229.240.102/w" + SpringMessage.getMessage("payco.paycoBankReturnUrl");
		//*** 408에러 발생하여 was 를 직접 호출하게 변경함.
		this.paycoBankReturnUrl = "http://110.10.147.238:8080/MandomMall" + SpringMessage.getMessage("payco.paycoBankReturnUrl");
		
		//http://110.10.147.238:8080/MandomMall/order/paycoBankReturn.do
			
		try {
			this.domainName = PathUtil.getCtx()+"/payco/";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getSellerKey() {
		return StringUtils.defaultString(sellerKey);
	}

	public String getCpId() {
		return StringUtils.defaultString(cpId);
	}

	public String getProductId() {
		return StringUtils.defaultString(productId);
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getDeliveryId() {
		return StringUtils.defaultString(deliveryId);
	}
	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}
	public String getDeliveryReferenceKey() {
		return StringUtils.defaultString(deliveryReferenceKey);
	}
	public void setDeliveryReferenceKey(String deliveryReferenceKey) {
		this.deliveryReferenceKey = deliveryReferenceKey;
	}
	public String getServerType() {
		return StringUtils.defaultString(serverType);
	}
	public String getLogYn() {
		return StringUtils.defaultString(logYn);
	}

	public String getDomainName() {
		return StringUtils.defaultString(domainName);
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getPaycoReturnUrl() {
		return StringUtils.defaultString(paycoReturnUrl);
	}

	public void setPaycoReturnUrl(String paycoReturnUrl) {
		this.paycoReturnUrl = paycoReturnUrl;
	}

	public String getPaycoBankReturnUrl() {
		return StringUtils.defaultString(paycoBankReturnUrl);
	}

	public void setPaycoBankReturnUrl(String paycoBankReturnUrl) {
		this.paycoBankReturnUrl = paycoBankReturnUrl;
	}

	
	

}
