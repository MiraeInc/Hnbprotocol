package com.gxenSoft.mall.lgdacom.vo;

import org.apache.commons.lang.StringUtils;

public class XPayBillKeyAuthVO {
	private String LGD_MID = "" ; 			//	상점ID
	private String LGD_TXNAME = "CardAuth" ; 			//	메소드 ID
	private String CST_PLATFORM = "";	
	private String VBV_ECI = "010" ;		//*결제방식 ( 010 : Key-in,  020: Swipe)	☞ 빌링결제시, 010 (key-in 방식) 으로 설정
	private String LGD_PAN=""; 				//*	빌링키
	private String LGD_INSTALL="00"; 				//*	할부개월
	private String LGD_OID=""; 				//*	주문번호	 상점아이디별로 유일한 값을(유니크하게) 상점에서 생성  영문,숫자, -, _ 만 사용가능(한글금지), 최대 63자
	private String LGD_AMOUNT=""; 		//*	결제금액	☞ "," 가 없는 형태 (예 : 23400)
	private String LGD_BUYER=""; 			//*	구매자명
	private String LGD_PRODUCTINFO=""; 	//*	구매내역
	private String LGD_BUYERID=""; 			//	구매자ID
	private String LGD_BUYERPHONE=""; 			//	구매자핸드폰(-빼고)
	private String LGD_BUYEREMAIL = "" ;
	
	public String getLGD_MID() {
		return StringUtils.defaultString(LGD_MID);
	}
	public void setLGD_MID(String lGD_MID) {
		LGD_MID = lGD_MID;
	}
	public String getLGD_TXNAME() {
		return StringUtils.defaultString(LGD_TXNAME);
	}
	public void setLGD_TXNAME(String lGD_TXNAME) {
		LGD_TXNAME = lGD_TXNAME;
	}
	public String getVBV_ECI() {
		return StringUtils.defaultString(VBV_ECI);
	}
	public void setVBV_ECI(String vBV_ECI) {
		VBV_ECI = vBV_ECI;
	}
	public String getLGD_PAN() {
		return StringUtils.defaultString(LGD_PAN);
	}
	public void setLGD_PAN(String lGD_PAN) {
		LGD_PAN = lGD_PAN;
	}
	public String getLGD_INSTALL() {
		return StringUtils.defaultString(LGD_INSTALL);
	}
	public void setLGD_INSTALL(String lGD_INSTALL) {
		LGD_INSTALL = lGD_INSTALL;
	}
	public String getLGD_OID() {
		return StringUtils.defaultString(LGD_OID);
	}
	public void setLGD_OID(String lGD_OID) {
		LGD_OID = lGD_OID;
	}
	public String getLGD_AMOUNT() {
		return StringUtils.defaultString(LGD_AMOUNT);
	}
	public void setLGD_AMOUNT(String lGD_AMOUNT) {
		LGD_AMOUNT = lGD_AMOUNT;
	}
	public String getLGD_BUYER() {
		return StringUtils.defaultString(LGD_BUYER);
	}
	public void setLGD_BUYER(String lGD_BUYER) {
		LGD_BUYER = lGD_BUYER;
	}
	public String getLGD_PRODUCTINFO() {
		return StringUtils.defaultString(LGD_PRODUCTINFO);
	}
	public void setLGD_PRODUCTINFO(String lGD_PRODUCTINFO) {
		LGD_PRODUCTINFO = lGD_PRODUCTINFO;
	}
	public String getLGD_BUYERID() {
		return StringUtils.defaultString(LGD_BUYERID);
	}
	public void setLGD_BUYERID(String lGD_BUYERID) {
		LGD_BUYERID = lGD_BUYERID;
	}
	public String getLGD_BUYERPHONE() {
		return StringUtils.defaultString(LGD_BUYERPHONE);
	}
	public void setLGD_BUYERPHONE(String lGD_BUYERPHONE) {
		LGD_BUYERPHONE = lGD_BUYERPHONE;
	}
	public String getLGD_BUYEREMAIL() {
		return StringUtils.defaultString(LGD_BUYEREMAIL);
	}
	public void setLGD_BUYEREMAIL(String lGD_BUYEREMAIL) {
		LGD_BUYEREMAIL = lGD_BUYEREMAIL;
	}
	public String getCST_PLATFORM() {
		return StringUtils.defaultString(CST_PLATFORM);
	}
	public void setCST_PLATFORM(String cST_PLATFORM) {
		CST_PLATFORM = cST_PLATFORM;
	}		
	
	

}
