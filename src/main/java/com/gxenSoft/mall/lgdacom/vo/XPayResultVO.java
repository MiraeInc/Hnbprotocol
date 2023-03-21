package com.gxenSoft.mall.lgdacom.vo;

import org.apache.commons.lang.StringUtils;

public class XPayResultVO {

	//요청 파라미터의 값으로 < , >, +- , --, ; (세미콜론), ‘ (따옴표), ” (이중따옴표) , \ (원화표시) 사용 금지
	
	//공통
	private String LGD_RESPCODE ="" ;				// 응답코드, '0000' 이면 성공 이외는 실패
    private String LGD_RESPMSG = "";
	private String LGD_OID = "" ;						//주문번호
	private String LGD_MID = "" ; 						//	상점ID
	private String LGD_TID = "" ; 						//	LG유플러스 거래번호
	private String LGD_AMOUNT = "" ;				//	할당금액
	private String LGD_PAYTYPE = "" ;				//	결제수단코드 (SC0040: 가상계좌, SC0030 : 실시간계좌)
	private String LGD_FINANCECODE = "" ;		//	결제기관코드, 입금계좌은행코드
	private String LGD_FINANCENAME = "" ;		//	결제기관명, 입금계좌은행명
	private String LGD_ESCROWYN = "" ;			//	최종 에스크로 적용 여부 Y : 에스크로 적용, N : 에스크로 미적용
	private String LGD_PAYERID = "" ;
	private String LGD_PAYEREMAIL = "" ;			
	private String LGD_PAYERPHONE = "" ;
	private String LGD_PRODUCTINFO = "" ;
	private String LGD_PAYDATE;						//	승인일시
	private String LGD_TIMESTAMP;
	private String LGD_PAYWINDOWTYPE ="";
	private String LGD_BUYER;							// 구매자명
	private String LGD_BUYERID;						// 구매자아이디
	private String LGD_BUYERADDRESS;				// 구매자주소
	private String LGD_BUYERPHONE;				// 구매자휴대폰번호
	private String LGD_BUYEREMAIL;					// 구매자메일
	private String LGD_PRODUCTCODE;				// 상품코드
	private String LGD_RECEIVER;						// 수취인
	private String LGD_RECEIVERPHONE;			// 수취인전화번호
	private String LGD_DELIVERYINFO;				// 배송정보
	
	
    //신용카드,휴대폰,실시간,paynow 공통
    private String LGD_PAYKEY;
	private String LGD_HASHDATA ;					//	해쉬데이타

	//신용카드
	private String LGD_CARDNUM;						//	신용카드번호(일반 가맹점은 *처리됨)
	private String LGD_CARDINSTALLMONTH;		//	신용카드할부개월	  포인트 결제시 할부개월 +60으로 리턴됨	  현대 포인트 결제시 할부개월 +80으로 리턴됨
	private String LGD_CARDNOINTYN;				//	신용카드무이자여부	 1: 무이자,   0 : 일반
	private String LGD_FINANCEAUTHNUM;			//	결제기관승인번호
	private String LGD_AFFILIATECODE;				// 신용카드제휴코드 (ISP만 제공됨)
	private String LGD_CARDGUBUN1;				// 신용카드추가정보1 (0:개인, 1:법인, 9:미확인)
	private String LGD_CARDGUBUN2;				// 신용카드추가정보2 (0:신용, 1:체크, 2:기프트, 9:미확인)
	private String LGD_CARDACQUIRER;				// 신용카드매입사코드
	private String LGD_PCANCELFLAG;				// 신용카드부분취소가능여부 (0: 부분취소불가능, 1: 부분취소가능)
	private String LGD_PCANCELSTR;					// 신용카드부분취소불가능사유	(부분취소가능시는 "0" 으로 리턴)
	private String LGD_DISCOUNTUSEYN;			// 신용카드 즉시할인 여부 (즉시할인여부 0:할인안됨, 1:할인됨)
	private String LGD_DISCOUNTUSEAMOUNT;	// 신용카드 즉시할인 금액 (즉시할인된 금액)
		
	//계좌이체
	private String LGD_CASHRECEIPTNUM;			// 현금영수증 승인번호 현금영수증 건이 아니거나 실패인경우 "0"
	private String LGD_CASHRECEIPTSELFYN;		// 현금영수증자진발급제유무	 Y: 자진발급제 적용,   그외 : 미적용
	private String LGD_CASHRECEIPTKIND;			// 현금영수증 종류	 0: 소득공제용,   1: 지출증빙용
	private String LGD_ACCOUNTOWNER;			// 계좌주명

	//가상계좌(무통장)
	private String LGD_ACCOUNTNUM	;				// 입금할 계좌번호
	private String LGD_CASFLAG;						// 가상계좌 플래그 (R: 계좌발급I: 입금C: 취소)
	private String LGD_CASCAMOUNT;				// 현입금금액
	private String LGD_CASTAMOUNT;				// 누적입금금액
	private String LGD_CASSEQNO;					// 가상계좌일련번호 (과오납불가의 경우 “000”) 
	private String LGD_PAYER = "" ;					// 가상계좌입금자명
	private String LGD_SAOWNER;						// 가상계좌 입금계좌주명 (상점명이 디폴트로 리턴)
	private String LGD_CLOSEDATE;					// 입금마감일 (YYYYMMDDhhddss)
	
	// 휴대폰
	private String LGD_TELNO;							// 결제휴대폰번호	
	
	// 가산계좌(무통장) 환불용
    private String LGD_RFBANKCODE;					//환불계좌 은행코드 (가상계좌환불은 필수)
    private String LGD_RFACCOUNTNUM;			//환불계좌 번호 (가상계좌환불은 필수)
    private String LGD_RFCUSTOMERNAME;			//환불계좌 예금주 (가상계좌환불은 필수)
    private String LGD_RFPHONE;						//요청자 연락처 (가상계좌환불은 필수)
	
    //KCP관련 추가
    private String PG_TYPE=""; //pg타입, LG/KCP
    private String CASH_NO = ""; //현금영수증 거래번호
    private String NOINF_TYPE=""; 
    private String CARD_MNY=""; 
    private String PAYCO_POINT_MNY=""; 
    private String PNT_AMOUNT=""; 
    private String PNT_ISSUE=""; 
    private String PNT_APP_NO=""; 
    private String PNT_APP_TIME=""; 
    private String PNT_RECEIPT_GUBN=""; 
    private String ADD_PNT=""; 
    private String USE_PNT=""; 
    private String RSV_PNT=""; 
    private String VAN_CD=""; 
    private String VAN_ID=""; 
    private String COMMID=""; 
    private String MOBILE_NO=""; 
    
    
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
	public String getLGD_OID() {
		return StringUtils.defaultString(LGD_OID);
	}
	public void setLGD_OID(String lGD_OID) {
		LGD_OID = lGD_OID;
	}
	public String getLGD_MID() {
		return StringUtils.defaultString(LGD_MID);
	}
	public void setLGD_MID(String lGD_MID) {
		LGD_MID = lGD_MID;
	}
	public String getLGD_TID() {
		return StringUtils.defaultString(LGD_TID);
	}
	public void setLGD_TID(String lGD_TID) {
		LGD_TID = lGD_TID;
	}
	public String getLGD_AMOUNT() {
		return StringUtils.defaultString(LGD_AMOUNT);
	}
	public void setLGD_AMOUNT(String lGD_AMOUNT) {
		LGD_AMOUNT = lGD_AMOUNT;
	}
	public String getLGD_PAYTYPE() {
		return StringUtils.defaultString(LGD_PAYTYPE);
	}
	public void setLGD_PAYTYPE(String lGD_PAYTYPE) {
		LGD_PAYTYPE = lGD_PAYTYPE;
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
	public String getLGD_ESCROWYN() {
		return StringUtils.defaultString(LGD_ESCROWYN);
	}
	public void setLGD_ESCROWYN(String lGD_ESCROWYN) {
		LGD_ESCROWYN = lGD_ESCROWYN;
	}
	public String getLGD_PAYER() {
		return StringUtils.defaultString(LGD_PAYER);
	}
	public void setLGD_PAYER(String lGD_PAYER) {
		LGD_PAYER = lGD_PAYER;
	}
	public String getLGD_PAYERID() {
		return StringUtils.defaultString(LGD_PAYERID);
	}
	public void setLGD_PAYERID(String lGD_PAYERID) {
		LGD_PAYERID = lGD_PAYERID;
	}
	public String getLGD_PAYEREMAIL() {
		return StringUtils.defaultString(LGD_PAYEREMAIL);
	}
	public void setLGD_PAYEREMAIL(String lGD_PAYEREMAIL) {
		LGD_PAYEREMAIL = lGD_PAYEREMAIL;
	}
	public String getLGD_PAYERPHONE() {
		return StringUtils.defaultString(LGD_PAYERPHONE);
	}
	public void setLGD_PAYERPHONE(String lGD_PAYERPHONE) {
		LGD_PAYERPHONE = lGD_PAYERPHONE;
	}
	public String getLGD_PRODUCTINFO() {
		return StringUtils.defaultString(LGD_PRODUCTINFO);
	}
	public void setLGD_PRODUCTINFO(String lGD_PRODUCTINFO) {
		LGD_PRODUCTINFO = lGD_PRODUCTINFO;
	}
	public String getLGD_PAYDATE() {
		return StringUtils.defaultString(LGD_PAYDATE);
	}
	public void setLGD_PAYDATE(String lGD_PAYDATE) {
		LGD_PAYDATE = lGD_PAYDATE;
	}
	public String getLGD_PAYKEY() {
		return StringUtils.defaultString(LGD_PAYKEY);
	}
	public void setLGD_PAYKEY(String lGD_PAYKEY) {
		LGD_PAYKEY = lGD_PAYKEY;
	}
	public String getLGD_HASHDATA() {
		return StringUtils.defaultString(LGD_HASHDATA);
	}
	public void setLGD_HASHDATA(String lGD_HASHDATA) {
		LGD_HASHDATA = lGD_HASHDATA;
	}
	public String getLGD_CARDNUM() {
		return StringUtils.defaultString(LGD_CARDNUM);
	}
	public void setLGD_CARDNUM(String lGD_CARDNUM) {
		LGD_CARDNUM = lGD_CARDNUM;
	}
	public String getLGD_CARDINSTALLMONTH() {
		return StringUtils.defaultString(LGD_CARDINSTALLMONTH);
	}
	public void setLGD_CARDINSTALLMONTH(String lGD_CARDINSTALLMONTH) {
		LGD_CARDINSTALLMONTH = lGD_CARDINSTALLMONTH;
	}
	public String getLGD_CARDNOINTYN() {
		return StringUtils.defaultString(LGD_CARDNOINTYN);
	}
	public void setLGD_CARDNOINTYN(String lGD_CARDNOINTYN) {
		LGD_CARDNOINTYN = lGD_CARDNOINTYN;
	}
	public String getLGD_FINANCEAUTHNUM() {
		return StringUtils.defaultString(LGD_FINANCEAUTHNUM);
	}
	public void setLGD_FINANCEAUTHNUM(String lGD_FINANCEAUTHNUM) {
		LGD_FINANCEAUTHNUM = lGD_FINANCEAUTHNUM;
	}
	public String getLGD_CASHRECEIPTNUM() {
		return StringUtils.defaultString(LGD_CASHRECEIPTNUM);
	}
	public void setLGD_CASHRECEIPTNUM(String lGD_CASHRECEIPTNUM) {
		LGD_CASHRECEIPTNUM = lGD_CASHRECEIPTNUM;
	}
	public String getLGD_CASHRECEIPTSELFYN() {
		return StringUtils.defaultString(LGD_CASHRECEIPTSELFYN);
	}
	public void setLGD_CASHRECEIPTSELFYN(String lGD_CASHRECEIPTSELFYN) {
		LGD_CASHRECEIPTSELFYN = lGD_CASHRECEIPTSELFYN;
	}
	public String getLGD_CASHRECEIPTKIND() {
		return StringUtils.defaultString(LGD_CASHRECEIPTKIND);
	}
	public void setLGD_CASHRECEIPTKIND(String lGD_CASHRECEIPTKIND) {
		LGD_CASHRECEIPTKIND = lGD_CASHRECEIPTKIND;
	}
	public String getLGD_ACCOUNTNUM() {
		return StringUtils.defaultString(LGD_ACCOUNTNUM);
	}
	public void setLGD_ACCOUNTNUM(String lGD_ACCOUNTNUM) {
		LGD_ACCOUNTNUM = lGD_ACCOUNTNUM;
	}
	public String getLGD_CASFLAG() {
		return StringUtils.defaultString(LGD_CASFLAG);
	}
	public void setLGD_CASFLAG(String lGD_CASFLAG) {
		LGD_CASFLAG = lGD_CASFLAG;
	}
	public String getLGD_CASCAMOUNT() {
		return StringUtils.defaultString(LGD_CASCAMOUNT);
	}
	public void setLGD_CASCAMOUNT(String lGD_CASCAMOUNT) {
		LGD_CASCAMOUNT = lGD_CASCAMOUNT;
	}
	public String getLGD_CASTAMOUNT() {
		return StringUtils.defaultString(LGD_CASTAMOUNT);
	}
	public void setLGD_CASTAMOUNT(String lGD_CASTAMOUNT) {
		LGD_CASTAMOUNT = lGD_CASTAMOUNT;
	}
	public String getLGD_CASSEQNO() {
		return StringUtils.defaultString(LGD_CASSEQNO);
	}
	public void setLGD_CASSEQNO(String lGD_CASSEQNO) {
		LGD_CASSEQNO = lGD_CASSEQNO;
	}
	public String getLGD_TIMESTAMP() {
		return StringUtils.defaultString(LGD_TIMESTAMP);
	}
	public void setLGD_TIMESTAMP(String lGD_TIMESTAMP) {
		LGD_TIMESTAMP = lGD_TIMESTAMP;
	}
	public String getLGD_PAYWINDOWTYPE() {
		return StringUtils.defaultString(LGD_PAYWINDOWTYPE);
	}
	public void setLGD_PAYWINDOWTYPE(String lGD_PAYWINDOWTYPE) {
		LGD_PAYWINDOWTYPE = lGD_PAYWINDOWTYPE;
	}
	public String getLGD_BUYER() {
		return StringUtils.defaultString(LGD_BUYER);
	}
	public void setLGD_BUYER(String lGD_BUYER) {
		LGD_BUYER = lGD_BUYER;
	}
	public String getLGD_BUYERID() {
		return (LGD_BUYERID);
	}
	public void setLGD_BUYERID(String lGD_BUYERID) {
		LGD_BUYERID = lGD_BUYERID;
	}
	public String getLGD_BUYERADDRESS() {
		return StringUtils.defaultString(LGD_BUYERADDRESS);
	}
	public void setLGD_BUYERADDRESS(String lGD_BUYERADDRESS) {
		LGD_BUYERADDRESS = lGD_BUYERADDRESS;
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
	public String getLGD_PRODUCTCODE() {
		return StringUtils.defaultString(LGD_PRODUCTCODE);
	}
	public void setLGD_PRODUCTCODE(String lGD_PRODUCTCODE) {
		LGD_PRODUCTCODE = lGD_PRODUCTCODE;
	}
	public String getLGD_RECEIVER() {
		return StringUtils.defaultString(LGD_RECEIVER);
	}
	public void setLGD_RECEIVER(String lGD_RECEIVER) {
		LGD_RECEIVER = lGD_RECEIVER;
	}
	public String getLGD_RECEIVERPHONE() {
		return StringUtils.defaultString(LGD_RECEIVERPHONE);
	}
	public void setLGD_RECEIVERPHONE(String lGD_RECEIVERPHONE) {
		LGD_RECEIVERPHONE = lGD_RECEIVERPHONE;
	}
	public String getLGD_DELIVERYINFO() {
		return StringUtils.defaultString(LGD_DELIVERYINFO);
	}
	public void setLGD_DELIVERYINFO(String lGD_DELIVERYINFO) {
		LGD_DELIVERYINFO = lGD_DELIVERYINFO;
	}
	public String getLGD_AFFILIATECODE() {
		return StringUtils.defaultString(LGD_AFFILIATECODE);
	}
	public void setLGD_AFFILIATECODE(String lGD_AFFILIATECODE) {
		LGD_AFFILIATECODE = lGD_AFFILIATECODE;
	}
	public String getLGD_CARDGUBUN1() {
		return StringUtils.defaultString(LGD_CARDGUBUN1);
	}
	public void setLGD_CARDGUBUN1(String lGD_CARDGUBUN1) {
		LGD_CARDGUBUN1 = lGD_CARDGUBUN1;
	}
	public String getLGD_CARDGUBUN2() {
		return StringUtils.defaultString(LGD_CARDGUBUN2);
	}
	public void setLGD_CARDGUBUN2(String lGD_CARDGUBUN2) {
		LGD_CARDGUBUN2 = lGD_CARDGUBUN2;
	}
	public String getLGD_CARDACQUIRER() {
		return StringUtils.defaultString(LGD_CARDACQUIRER);
	}
	public void setLGD_CARDACQUIRER(String lGD_CARDACQUIRER) {
		LGD_CARDACQUIRER = lGD_CARDACQUIRER;
	}
	public String getLGD_PCANCELFLAG() {
		return StringUtils.defaultString(LGD_PCANCELFLAG);
	}
	public void setLGD_PCANCELFLAG(String lGD_PCANCELFLAG) {
		LGD_PCANCELFLAG = lGD_PCANCELFLAG;
	}
	public String getLGD_PCANCELSTR() {
		return StringUtils.defaultString(LGD_PCANCELSTR);
	}
	public void setLGD_PCANCELSTR(String lGD_PCANCELSTR) {
		LGD_PCANCELSTR = lGD_PCANCELSTR;
	}
	public String getLGD_DISCOUNTUSEYN() {
		return StringUtils.defaultString(LGD_DISCOUNTUSEYN);
	}
	public void setLGD_DISCOUNTUSEYN(String lGD_DISCOUNTUSEYN) {
		LGD_DISCOUNTUSEYN = lGD_DISCOUNTUSEYN;
	}
	public String getLGD_DISCOUNTUSEAMOUNT() {
		return StringUtils.defaultString(LGD_DISCOUNTUSEAMOUNT);
	}
	public void setLGD_DISCOUNTUSEAMOUNT(String lGD_DISCOUNTUSEAMOUNT) {
		LGD_DISCOUNTUSEAMOUNT = lGD_DISCOUNTUSEAMOUNT;
	}
	public String getLGD_ACCOUNTOWNER() {
		return StringUtils.defaultString(LGD_ACCOUNTOWNER);
	}
	public void setLGD_ACCOUNTOWNER(String lGD_ACCOUNTOWNER) {
		LGD_ACCOUNTOWNER = lGD_ACCOUNTOWNER;
	}
	public String getLGD_SAOWNER() {
		return StringUtils.defaultString(LGD_SAOWNER);
	}
	public void setLGD_SAOWNER(String lGD_SAOWNER) {
		LGD_SAOWNER = lGD_SAOWNER;
	}
	public String getLGD_CLOSEDATE() {
		return StringUtils.defaultString(LGD_CLOSEDATE);
	}
	public void setLGD_CLOSEDATE(String lGD_CLOSEDATE) {
		LGD_CLOSEDATE = lGD_CLOSEDATE;
	}
	public String getLGD_TELNO() {
		return StringUtils.defaultString(LGD_TELNO);
	}
	public void setLGD_TELNO(String lGD_TELNO) {
		LGD_TELNO = lGD_TELNO;
	}
	public String getLGD_RFBANKCODE() {
		return LGD_RFBANKCODE;
	}
	public void setLGD_RFBANKCODE(String lGD_RFBANKCODE) {
		LGD_RFBANKCODE = lGD_RFBANKCODE;
	}
	public String getLGD_RFACCOUNTNUM() {
		return LGD_RFACCOUNTNUM;
	}
	public void setLGD_RFACCOUNTNUM(String lGD_RFACCOUNTNUM) {
		LGD_RFACCOUNTNUM = lGD_RFACCOUNTNUM;
	}
	public String getLGD_RFCUSTOMERNAME() {
		return LGD_RFCUSTOMERNAME;
	}
	public void setLGD_RFCUSTOMERNAME(String lGD_RFCUSTOMERNAME) {
		LGD_RFCUSTOMERNAME = lGD_RFCUSTOMERNAME;
	}
	public String getLGD_RFPHONE() {
		return LGD_RFPHONE;
	}
	public void setLGD_RFPHONE(String lGD_RFPHONE) {
		LGD_RFPHONE = lGD_RFPHONE;
	}
	public String getPG_TYPE() {
		return PG_TYPE;
	}
	public void setPG_TYPE(String pG_TYPE) {
		PG_TYPE = pG_TYPE;
	}
	public String getNOINF_TYPE() {
		return NOINF_TYPE;
	}
	public void setNOINF_TYPE(String nOINF_TYPE) {
		NOINF_TYPE = nOINF_TYPE;
	}
	public String getCARD_MNY() {
		return CARD_MNY;
	}
	public void setCARD_MNY(String cARD_MNY) {
		CARD_MNY = cARD_MNY;
	}
	public String getPAYCO_POINT_MNY() {
		return PAYCO_POINT_MNY;
	}
	public void setPAYCO_POINT_MNY(String pAYCO_POINT_MNY) {
		PAYCO_POINT_MNY = pAYCO_POINT_MNY;
	}
	public String getPNT_AMOUNT() {
		return PNT_AMOUNT;
	}
	public void setPNT_AMOUNT(String pNT_AMOUNT) {
		PNT_AMOUNT = pNT_AMOUNT;
	}
	public String getPNT_ISSUE() {
		return PNT_ISSUE;
	}
	public void setPNT_ISSUE(String pNT_ISSUE) {
		PNT_ISSUE = pNT_ISSUE;
	}
	public String getPNT_APP_NO() {
		return PNT_APP_NO;
	}
	public void setPNT_APP_NO(String pNT_APP_NO) {
		PNT_APP_NO = pNT_APP_NO;
	}
	public String getPNT_APP_TIME() {
		return PNT_APP_TIME;
	}
	public void setPNT_APP_TIME(String pNT_APP_TIME) {
		PNT_APP_TIME = pNT_APP_TIME;
	}
	public String getPNT_RECEIPT_GUBN() {
		return PNT_RECEIPT_GUBN;
	}
	public void setPNT_RECEIPT_GUBN(String pNT_RECEIPT_GUBN) {
		PNT_RECEIPT_GUBN = pNT_RECEIPT_GUBN;
	}
	public String getADD_PNT() {
		return ADD_PNT;
	}
	public void setADD_PNT(String aDD_PNT) {
		ADD_PNT = aDD_PNT;
	}
	public String getUSE_PNT() {
		return USE_PNT;
	}
	public void setUSE_PNT(String uSE_PNT) {
		USE_PNT = uSE_PNT;
	}
	public String getRSV_PNT() {
		return RSV_PNT;
	}
	public void setRSV_PNT(String rSV_PNT) {
		RSV_PNT = rSV_PNT;
	}
	public String getVAN_CD() {
		return VAN_CD;
	}
	public void setVAN_CD(String vAN_CD) {
		VAN_CD = vAN_CD;
	}
	public String getVAN_ID() {
		return VAN_ID;
	}
	public void setVAN_ID(String vAN_ID) {
		VAN_ID = vAN_ID;
	}
	public String getCOMMID() {
		return COMMID;
	}
	public void setCOMMID(String cOMMID) {
		COMMID = cOMMID;
	}
	public String getMOBILE_NO() {
		return MOBILE_NO;
	}
	public void setMOBILE_NO(String mOBILE_NO) {
		MOBILE_NO = mOBILE_NO;
	}
	public String getCASH_NO() {
		return CASH_NO;
	}
	public void setCASH_NO(String cASH_NO) {
		CASH_NO = cASH_NO;
	}
}
