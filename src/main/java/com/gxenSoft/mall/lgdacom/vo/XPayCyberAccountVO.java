package com.gxenSoft.mall.lgdacom.vo;

import org.apache.commons.lang.StringUtils;

public class XPayCyberAccountVO {

	//요청 파라미터의 값으로 < , >, +- , --, ; (세미콜론), ‘ (따옴표), ” (이중따옴표) , \ (원화표시) 사용 금지
	
	//필수요소 *
	private String CST_PLATFORM="" ;	// *	service 또는 test
	private String CST_MID=""; 				//*	LG U+와 계약시 설정한 상점아이디
	private String LGD_MID=""; 										//*	LG U+와 계약시 설정한 상점아이디
	private String LGD_TXNAME = "CyberAccount"; 				//*	메소드 : CyberAccount
	private String LGD_METHOD = "ASSIGN"; 				//*	호출 메서드 (발급: ASSIGN  변경: CHANGE)
	private String LGD_OID=""; 				//*	주문번호	 상점아이디별로 유일한 값을(유니크하게) 상점에서 생성  영문,숫자, -, _ 만 사용가능(한글금지), 최대 63자
	private String LGD_AMOUNT=""; 		//*	결제금액	☞ "," 가 없는 형태 (예 : 23400)
	private String LGD_BANKCODE=""; 			//*	입금계좌은행코드 (varchar(3))
	private String LGD_ACCOUNTOWNER=""; 			//*	입금자명 (구매자명)
	private String LGD_BUYERPHONE=""; 		//* 구매자핸드폰번호 (필수입력)
	private String LGD_CASNOTEURL=""; 	    	//*	무통장입금 결과 수신페이지    ☞ 입금할 계좌번호 발급 및 입금결과 DB연동을 위한 페이지
	private String LGD_CLOSEDATE = ""; // 입금마감일 20101231000000
	
	private String LGD_ESCROW_USEYN=""; 							//	에스크로 적용 여부	 Y : 에스크로 적용,  N : 에스크로 미적용
	private String LGD_CASHCARDNUM=""; //휴대폰, 현금영수증카드번호,또는 사업자번호
	private String LGD_CASHRECEIPTUSE=""; //	1	현금영수증 발급용도  선택 	0, 1, 2 	0: 미발행, 1: 소득공제, 2: 지출증빙  (결제창에서 현금영수증 발급용도 선택의 디폴트 체크시)
	//선택
	private String LGD_PRODUCTINFO=""; 	//구매상품정보
	private String LGD_BUYER=""; 			   //	구매자
	private String LGD_BUYERID=""; 			//	구매자아이디
	private String LGD_BUYEREMAIL=""; 		//	구매자이메일	 결제성공시 해당 이메일로 결제내역 전송	
	
    // 에스크로 파라미터
    private String LGD_ESCROW_ZIPCODE = "";			// 에스크로배송지우편번호
    private String LGD_ESCROW_ADDRESS1 = "";			// 에스크로배송지주소동까지
    private String LGD_ESCROW_ADDRESS2 = "";			// 에스크로배송지주소상세
    private String LGD_ESCROW_BUYERPHONE = "";	// 에스크로구매자휴대폰번호
	
	public String getCST_PLATFORM() {
		return StringUtils.defaultString(CST_PLATFORM);
	}
	public void setCST_PLATFORM(String cST_PLATFORM) {
		CST_PLATFORM = cST_PLATFORM;
	}
	public String getCST_MID() {
		return StringUtils.defaultString(CST_MID);
	}
	public void setCST_MID(String cST_MID) {
		CST_MID = cST_MID;
	}
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
	public String getLGD_METHOD() {
		return StringUtils.defaultString(LGD_METHOD);
	}
	public void setLGD_METHOD(String lGD_METHOD) {
		LGD_METHOD = lGD_METHOD;
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
	public String getLGD_BANKCODE() {
		return StringUtils.defaultString(LGD_BANKCODE);
	}
	public void setLGD_BANKCODE(String lGD_BANKCODE) {
		LGD_BANKCODE = lGD_BANKCODE;
	}
	public String getLGD_ACCOUNTOWNER() {
		return StringUtils.defaultString(LGD_ACCOUNTOWNER);
	}
	public void setLGD_ACCOUNTOWNER(String lGD_ACCOUNTOWNER) {
		LGD_ACCOUNTOWNER = lGD_ACCOUNTOWNER;
	}
	public String getLGD_BUYERPHONE() {
		return StringUtils.defaultString(LGD_BUYERPHONE);
	}
	public void setLGD_BUYERPHONE(String lGD_BUYERPHONE) {
		LGD_BUYERPHONE = lGD_BUYERPHONE;
	}
	public String getLGD_CASNOTEURL() {
		return StringUtils.defaultString(LGD_CASNOTEURL);
	}
	public void setLGD_CASNOTEURL(String lGD_CASNOTEURL) {
		LGD_CASNOTEURL = lGD_CASNOTEURL;
	}
	public String getLGD_ESCROW_USEYN() {
		return StringUtils.defaultString(LGD_ESCROW_USEYN);
	}
	public void setLGD_ESCROW_USEYN(String lGD_ESCROW_USEYN) {
		LGD_ESCROW_USEYN = lGD_ESCROW_USEYN;
	}
	public String getLGD_CASHCARDNUM() {
		return StringUtils.defaultString(LGD_CASHCARDNUM);
	}
	public void setLGD_CASHCARDNUM(String lGD_CASHCARDNUM) {
		LGD_CASHCARDNUM = lGD_CASHCARDNUM;
	}
	public String getLGD_CASHRECEIPTUSE() {
		return StringUtils.defaultString(LGD_CASHRECEIPTUSE);
	}
	public void setLGD_CASHRECEIPTUSE(String lGD_CASHRECEIPTUSE) {
		LGD_CASHRECEIPTUSE = lGD_CASHRECEIPTUSE;
	}
	public String getLGD_PRODUCTINFO() {
		return StringUtils.defaultString(LGD_PRODUCTINFO);
	}
	public void setLGD_PRODUCTINFO(String lGD_PRODUCTINFO) {
		LGD_PRODUCTINFO = lGD_PRODUCTINFO;
	}
	public String getLGD_BUYER() {
		return StringUtils.defaultString(LGD_BUYER);
	}
	public void setLGD_BUYER(String lGD_BUYER) {
		LGD_BUYER = lGD_BUYER;
	}
	public String getLGD_BUYERID() {
		return StringUtils.defaultString(LGD_BUYERID);
	}
	public void setLGD_BUYERID(String lGD_BUYERID) {
		LGD_BUYERID = lGD_BUYERID;
	}
	public String getLGD_BUYEREMAIL() {
		return StringUtils.defaultString(LGD_BUYEREMAIL);
	}
	public void setLGD_BUYEREMAIL(String lGD_BUYEREMAIL) {
		LGD_BUYEREMAIL = lGD_BUYEREMAIL;
	}
	public String getLGD_CLOSEDATE() {
		return StringUtils.defaultString(LGD_CLOSEDATE);
	}
	public void setLGD_CLOSEDATE(String lGD_CLOSEDATE) {
		LGD_CLOSEDATE = lGD_CLOSEDATE;
	}
	public String getLGD_ESCROW_ZIPCODE() {
		return LGD_ESCROW_ZIPCODE;
	}
	public void setLGD_ESCROW_ZIPCODE(String lGD_ESCROW_ZIPCODE) {
		LGD_ESCROW_ZIPCODE = lGD_ESCROW_ZIPCODE;
	}
	public String getLGD_ESCROW_ADDRESS1() {
		return LGD_ESCROW_ADDRESS1;
	}
	public void setLGD_ESCROW_ADDRESS1(String lGD_ESCROW_ADDRESS1) {
		LGD_ESCROW_ADDRESS1 = lGD_ESCROW_ADDRESS1;
	}
	public String getLGD_ESCROW_ADDRESS2() {
		return LGD_ESCROW_ADDRESS2;
	}
	public void setLGD_ESCROW_ADDRESS2(String lGD_ESCROW_ADDRESS2) {
		LGD_ESCROW_ADDRESS2 = lGD_ESCROW_ADDRESS2;
	}
	public String getLGD_ESCROW_BUYERPHONE() {
		return LGD_ESCROW_BUYERPHONE;
	}
	public void setLGD_ESCROW_BUYERPHONE(String lGD_ESCROW_BUYERPHONE) {
		LGD_ESCROW_BUYERPHONE = lGD_ESCROW_BUYERPHONE;
	}

}
