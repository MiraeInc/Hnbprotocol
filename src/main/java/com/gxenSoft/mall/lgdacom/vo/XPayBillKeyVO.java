package com.gxenSoft.mall.lgdacom.vo;

import org.apache.commons.lang.StringUtils;

public class XPayBillKeyVO {
	private String LGD_MID = "" ; 			//	상점ID
	private String CST_MID = "" ; 		
	private String LGD_ENCODING_RETURNURL = "UTF-8" ;		
	private String LGD_ENCODING = "UTF-8" ;			
	private String LGD_RETURNURL = "" ;			
	private String LGD_PAYWINDOWTYPE = "CardBillingAuth" ;	
	private String LGD_OSTYPE_CHECK = "P" ;
	private String LGD_WINDOW_TYPE = "iframe" ;			//	popup
	private String CST_PLATFORM = "" ;
	private String LGD_VERSION = "JSP_Non-ActiveX_CardBilling";			//	승인일시
	private String LGD_ACTIVEXYN = "N";
	//결과
	private String LGD_RESPCODE = "";
	private String LGD_RESPMSG = "";
	private String LGD_BILLKEY = "";			   	  
	private String LGD_PAYTYPE = "";			   	   
	private String LGD_PAYDATE = "";			   	   	
	private String LGD_FINANCECODE = "";				    	   	
	private String LGD_FINANCENAME = "";		   	   	
	private String LGD_CARDINSTALLMONTH = "";			   	   	
	private String LGD_EXPMON = "";				  	
	private String LGD_EXPYEAR = "";	   	   	
	private String LGD_BUYERSSN = "";		 //생년월일	   	   	
	private String LGD_CARDNUM = "";	//카드번호	   	   	
	private String LGD_CARDNOINTYN = "";	  	
	private String LGD_TID = "";	//승인번호	
	private String LGD_VANCODE = "";	
	
	private String CARD_NM = "";		//카드별칭
	private String MAIN_YN = "";  //대표카드여부 
	private String MEMBER_IDX = "";  //회원번호 
	 
	public String getLGD_MID() {
		return StringUtils.defaultString(LGD_MID);
	}
	public void setLGD_MID(String lGD_MID) {
		LGD_MID = lGD_MID;
	}
	public String getCST_MID() {
		return StringUtils.defaultString(CST_MID);
	}
	public void setCST_MID(String cST_MID) {
		CST_MID = cST_MID;
	}
	public String getLGD_ENCODING_RETURNURL() {
		return StringUtils.defaultString(LGD_ENCODING_RETURNURL);
	}
	public void setLGD_ENCODING_RETURNURL(String lGD_ENCODING_RETURNURL) {
		LGD_ENCODING_RETURNURL = lGD_ENCODING_RETURNURL;
	}
	public String getLGD_ENCODING() {
		return StringUtils.defaultString(LGD_ENCODING);
	}
	public void setLGD_ENCODING(String lGD_ENCODING) {
		LGD_ENCODING = lGD_ENCODING;
	}
	public String getLGD_RETURNURL() {
		return StringUtils.defaultString(LGD_RETURNURL);
	}
	public void setLGD_RETURNURL(String lGD_RETURNURL) {
		LGD_RETURNURL = lGD_RETURNURL;
	}
	public String getLGD_PAYWINDOWTYPE() {
		return StringUtils.defaultString(LGD_PAYWINDOWTYPE);
	}
	public void setLGD_PAYWINDOWTYPE(String lGD_PAYWINDOWTYPE) {
		LGD_PAYWINDOWTYPE = lGD_PAYWINDOWTYPE;
	}
	public String getLGD_OSTYPE_CHECK() {
		return StringUtils.defaultString(LGD_OSTYPE_CHECK);
	}
	public void setLGD_OSTYPE_CHECK(String lGD_OSTYPE_CHECK) {
		LGD_OSTYPE_CHECK = lGD_OSTYPE_CHECK;
	}
	public String getLGD_WINDOW_TYPE() {
		return StringUtils.defaultString(LGD_WINDOW_TYPE);
	}
	public void setLGD_WINDOW_TYPE(String lGD_WINDOW_TYPE) {
		LGD_WINDOW_TYPE = lGD_WINDOW_TYPE;
	}
	public String getCST_PLATFORM() {
		return StringUtils.defaultString(CST_PLATFORM);
	}
	public void setCST_PLATFORM(String cST_PLATFORM) {
		CST_PLATFORM = cST_PLATFORM;
	}
	public String getLGD_VERSION() {
		return StringUtils.defaultString(LGD_VERSION);
	}
	public void setLGD_VERSION(String lGD_VERSION) {
		LGD_VERSION = lGD_VERSION;
	}
	public String getLGD_ACTIVEXYN() {
		return StringUtils.defaultString(LGD_ACTIVEXYN);
	}
	public void setLGD_ACTIVEXYN(String lGD_ACTIVEXYN) {
		LGD_ACTIVEXYN = lGD_ACTIVEXYN;
	}
	public String getLGD_RESPCODE() {
		return StringUtils.defaultString(LGD_RESPCODE);
	}
	public void setLGD_RESPCODE(String lGD_RESPCODE) {
		LGD_RESPCODE = lGD_RESPCODE;
	}
	public String getLGD_RESPMSG() {
		return StringUtils.defaultString(LGD_RESPMSG);
	}
	public void setLGD_RESPMSG(String lGD_RESPMSG) {
		LGD_RESPMSG = lGD_RESPMSG;
	}
	public String getLGD_BILLKEY() {
		return StringUtils.defaultString(LGD_BILLKEY);
	}
	public void setLGD_BILLKEY(String lGD_BILLKEY) {
		LGD_BILLKEY = lGD_BILLKEY;
	}
	public String getLGD_PAYTYPE() {
		return StringUtils.defaultString(LGD_PAYTYPE);
	}
	public void setLGD_PAYTYPE(String lGD_PAYTYPE) {
		LGD_PAYTYPE = lGD_PAYTYPE;
	}
	public String getLGD_PAYDATE() {
		return StringUtils.defaultString(LGD_PAYDATE);
	}
	public void setLGD_PAYDATE(String lGD_PAYDATE) {
		LGD_PAYDATE = lGD_PAYDATE;
	}
	public String getLGD_FINANCECODE() {
		return StringUtils.defaultString(LGD_FINANCECODE);
	}
	public void setLGD_FINANCECODE(String lGD_FINANCECODE) {
		LGD_FINANCECODE = lGD_FINANCECODE;
	}
	public String getLGD_FINANCENAME() {
		return StringUtils.defaultString(LGD_FINANCENAME);
	}
	public void setLGD_FINANCENAME(String lGD_FINANCENAME) {
		LGD_FINANCENAME = lGD_FINANCENAME;
	}
	public String getCARD_NM() {
		return StringUtils.defaultString(CARD_NM);
	}
	public void setCARD_NM(String cARD_NM) {
		CARD_NM = cARD_NM;
	}
	public String getMAIN_YN() {
		return StringUtils.defaultString(MAIN_YN);
	}
	public void setMAIN_YN(String mAIN_YN) {
		MAIN_YN = mAIN_YN;
	}
	public String getLGD_CARDINSTALLMONTH() {
		return StringUtils.defaultString(LGD_CARDINSTALLMONTH);
	}
	public void setLGD_CARDINSTALLMONTH(String lGD_CARDINSTALLMONTH) {
		LGD_CARDINSTALLMONTH = lGD_CARDINSTALLMONTH;
	}
	public String getLGD_EXPMON() {
		return StringUtils.defaultString(LGD_EXPMON);
	}
	public void setLGD_EXPMON(String lGD_EXPMON) {
		LGD_EXPMON = lGD_EXPMON;
	}
	public String getLGD_EXPYEAR() {
		return StringUtils.defaultString(LGD_EXPYEAR);
	}
	public void setLGD_EXPYEAR(String lGD_EXPYEAR) {
		LGD_EXPYEAR = lGD_EXPYEAR;
	}
	public String getLGD_BUYERSSN() {
		return StringUtils.defaultString(LGD_BUYERSSN);
	}
	public void setLGD_BUYERSSN(String lGD_BUYERSSN) {
		LGD_BUYERSSN = lGD_BUYERSSN;
	}
	public String getLGD_CARDNUM() {
		return StringUtils.defaultString(LGD_CARDNUM);
	}
	public void setLGD_CARDNUM(String lGD_CARDNUM) {
		LGD_CARDNUM = lGD_CARDNUM;
	}
	public String getLGD_CARDNOINTYN() {
		return StringUtils.defaultString(LGD_CARDNOINTYN);
	}
	public void setLGD_CARDNOINTYN(String lGD_CARDNOINTYN) {
		LGD_CARDNOINTYN = lGD_CARDNOINTYN;
	}
	public String getLGD_TID() {
		return StringUtils.defaultString(LGD_TID);
	}
	public void setLGD_TID(String lGD_TID) {
		LGD_TID = lGD_TID;
	}
	public String getLGD_VANCODE() {
		return StringUtils.defaultString(LGD_VANCODE);
	}
	public void setLGD_VANCODE(String lGD_VANCODE) {
		LGD_VANCODE = lGD_VANCODE;
	}
	public String getMEMBER_IDX() {
		return StringUtils.defaultString(MEMBER_IDX);
	}
	public void setMEMBER_IDX(String mEMBER_IDX) {
		MEMBER_IDX = mEMBER_IDX;
	}

}
