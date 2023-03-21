package com.gxenSoft.mall.lgdacom.vo;

import org.apache.commons.lang.StringUtils;

public class XPayReqVO {

	//요청 파라미터의 값으로 < , >, +- , --, ; (세미콜론), ‘ (따옴표), ” (이중따옴표) , \ (원화표시) 사용 금지
	
	//공통 (필수요소 *)
	private String CST_PLATFORM=""; 	// *	service 또는 test
	private String CST_MID=""; 				//*	LG U+와 계약시 설정한 상점아이디
	private String LGD_MID=""; 				//*	LG U+와 계약시 설정한 상점아이디
	///private String LGD_MERTKEY;      //*  MertKey
	private String LGD_OID=""; 				//*	주문번호	 상점아이디별로 유일한 값을(유니크하게) 상점에서 생성  영문,숫자, -, _ 만 사용가능(한글금지), 최대 63자
	private String LGD_AMOUNT=""; 		//*	결제금액	☞ "," 가 없는 형태 (예 : 23400)
	private String LGD_BUYER=""; 			//*	구매자명
	private String LGD_PRODUCTINFO=""; 	//*	구매내역
	private String LGD_TIMESTAMP=""; 		//*	타임스탬프(현재시간을 넘겨주세요)	 거래 위변조를 막기위해 사용	 숫자형식으로만 전달, 예) 20090226110637
	private String LGD_HASHDATA=""; 		//*	해쉬데이타	 거래 위변조를 막기 위한 파라미터    샘플페이지 참조(자동생성)
	private String LGD_RETURNURL=""; 		//*	인증결과 응답수신페이지 URL
	private String LGD_WINDOW_TYPE="iframe"; //*	결제창 호출 방식	 iframe
	private String LGD_BUYERID; 			//*	구매자아이디(상품권결제시 필수)
	private String LGD_BUYERIP; 			//*	구매자아이피(상품권결제시 필수)
	private String LGD_BUYERADDRESS; //	구매자주소
	private String LGD_BUYERPHONE; 	//	구매자휴대폰번호
	private String LGD_BUYEREMAIL; 		//	구매자이메일	 결제성공시 해당 이메일로 결제내역 전송
	private String LGD_PRODUCTCODE; 	//	상품코드
	private String LGD_RECEIVER; 			//	수취인
	private String LGD_RECEIVERPHONE; //	수취인전화번호
	private String LGD_DELIVERYINFO; 								//	배송정보
	private String LGD_CUSTOM_PROCESSTYPE = "TWOTR"; 	//*	상점정의 프로세스 타입   (기본값 : TWOTR)
	private String LGD_CUSTOM_SESSIONTIMEOUT;//	상점정의 승인 가능 시간  (기본값 : 10분)
	private String LGD_CUSTOM_USABLEPAY=""; 						//	결제수단,  특정 결제수단만 보이게 할 경우 사용	예) 신용카드, 계좌이체만 사용할 경우SC0010-SC0030
	private String LGD_CUSTOM_SKIN  = "red";     				//	상점정의 스킨 (기본값 : red)
	private String LGD_CUSTOM_CEONAME; 						//	상점정의 대표자명               현금영수증 발급 연동시에만 적용
	private String LGD_CUSTOM_MERTNAME=""; 						//	상점정의 상점명                   현금영수증 발급 연동시에만 적용
	private String LGD_CUSTOM_MERTPHONE=""; 					//	상점정의 상점전화번호           현금영수증 발급 연동시에만 적용
	private String LGD_CUSTOM_BUSINESSNUM; 					//	상점정의 사업자번호             현금영수증 발급 연동시에만 적용
	private String LGD_CUSTOM_LOGO; 								//	상점정의 로고 이미지 URL (로고 설정)	 높이21pix * (폭은 결제창 사이즈에 맞게 정의)
	private String LGD_CUSTOM_CARDPOINTUSEYN=""; 			//	상점정의 신용카드 포인트 사용여부 (기본값 : N) 포인트가맹점이 무조건 포인트를 사용하게 할 때: 'Y'
	private String LGD_TAXFREEAMOUNT=""; 							//	결제금액(LGD_AMOUNT) 중 면세금액
	private String LGD_CLOSEDATE=""; 									//	상점정의 결제마감시간	 형식 : yyyyMMddHHmmss
	private String LGD_ESCROW_USEYN="N"; 							//	에스크로 적용 여부	 Y : 에스크로 적용,  N : 에스크로 미적용
	private String LGD_ENCODING = "UTF-8"; 						//	결제창 호출문자 인코딩방식  (기본값: EUC-KR)	☞ UTF-8 사용시, 최종 결제결과처리 인코딩 방식을 mall.conf 에 설정	☞ UTF-8 사용시, 파라미터 값 “UTF-8”로 셋팅
	private String LGD_ENCODING_RETURNURL= "UTF-8"; 	//	결과수신페이지 호출문자 인코딩방식으로 LGD_RETURNURL에 전달(기본값: EUC-KR)	☞ UTF-8 사용시, 최종 결제결과처리 인코딩 방식을 mall.conf 에 설정	☞ UTF-8 사용시, 파라미터 값 “UTF-8”로 셋팅
	private String LGD_VERSION=""; 										//	사용 모듈 정보	 해당 파라미터를 누락했을 때 서비스 이용이 불가능한 것은 아니지만 어떤 서비스를 사용하는지 판단을 위한 것으로 해당 파라미터는 수정 및 삭제 금지
	private String LGD_OSTYPE_CHECK="P"; 								//	PC 웹브라우저에서 모바일용 결제모듈의 실행 방지	 P: PC 웹브라우저에서만 결제모듈 실행 허용 ☞  “P”,”M” 외의 문자(Null, “” 포함)는 디바이스를 구분하지 않고 모든 웹브라우저에서 결제모듈을 허용합니다. PC용 결제모듈이 모바일에서 실행되지 않도록 예방하려면 이 파라미터의 값을 “P”로 셋팅합니다.
	private String LGD_BACKBTN_YN="N"; 								//	신용카드/계좌이체/가상계좌 등 결제수단을 다시 선택하기 위해 결제창내 “결제수단 선택” 페이지로 이동(“뒤로가기” 버튼) 사용 여부	 Y: 버튼 사용	 N: 버튼 사용하지 않음
    private String LGD_CUSTOM_SWITCHINGTYPE = "IFRAME"; //결제시 페이지전환 방식정의 SUBMIT:값을 세션으로 유지함, IFRAME:값을 IFRAME에 hidden값으로 유지함.
    private String LGD_EASYPAY_ONLY=""; // 페이나우 사용여부 (PAYNOW)
    
	//신용카드
	private String LGD_INSTALLRANGE; 			//	표시할부개월수 	 구분자는 반드시 ‘:’ 을 사용	 반드시 아래 기본값과 동일한 포맷을 사용하십시오.	// 기본값: 0:2:3:4:5:6:7:8:9:10:11:12 잘못된 설정 예시: 0:02:03:04:05:06:07:08:09:10:11:12 
	private String LGD_NOINTINF; 				//	특정카드/특정개월무이자 셋팅 카드-개월수 : 개월수, 카드-개월수 형식으로 전달	예) 국민 3,6개월, 삼성 3-6개월 무이자 적용시,   11-3:6,51-3:4:5:6 
    private String LGD_USABLECARD=""; 			//	사용가능카드사
    private String LGD_LANGUAGE; 				//*	해외발행 카드로 결제 시 반드시 입력	 영문설정시 LGD_LANGUAGE(value= EN)	 중문설정시 LGD_LANGUAGE(value= ZH)	 일문설정시 LGD_LANGUAGE(value= JA)

    //계좌이체 	
    private String LGD_CASHRECEIPTYN="" ; 		//	현금영수증 사용 여부 (기본값 : Y) Y : 현금영수증 사용함   N : 현금영수증 사용안함    	
    private String LGD_USABLEBANK="";     		//	사용가능 은행 
    private String LGD_ACTIVEXYN	="N"; 	    		//ActiveX 사용 여부 N: NonActiveX 사용(반드시 “N”으로 값을 넘길 것) ☞ N 외의 값(파라미터를 넘기지 않을 경우도 포함): ActiveX 환경에서 계좌이체 결제 진행(IE)
    
    // 모바일 신용카드 ISP 앱용
    private String LGD_KVPMISPAUTOAPPYN="N";		// ISP 결제 처리방식 (A: ISP 처리(안드로이드), N: ISP 동기 결제처리(iOS Web-to-Web)) ☞ “연동스펙_결제창2.0.xls” 첨부파일 참조, “ISP 신용카드, 계좌이체 결제에 필요한 파라미터” 참조
    private String LGD_KVPMISPWAPURL="";				// ISP 승인완료 화면처리 URL (ISP 인증 신용카드 사용시 필수) ☞ “ISP 신용카드, 계좌이체 결제에 필요한 파라미터” 참조
    private String LGD_KVPMISPCANCELURL="";			// ISP 결제취소 결과화면 URL (ISP 인증 신용카드 사용시 필수) ☞ “ISP 신용카드, 계좌이체 결제에 필요한 파라미터” 참조
    
    // 모바일 신용카드 롯데앱카드 (IOS 필수)
    private String LGD_MPILOTTEAPPCARDWAPURL="";	// 롯데앱카드 결제 이후 고객사 앱 호출 스키마 설정 ☞IOS 환경의 고객사 APP에서 결제시 필수 ☞iOS의 경우 결제 이후 고객사 앱으로 자동리턴이 되지 않으므로 이 파라미터를 적용
    
    // 모바일 계좌이체 앱용
    private String LGD_MTRANSFERAUTOAPPYN="N";	// 계좌이체 결제 처리방식(계좌이체 사용시 필수) (A: 계좌이체 처리(안드로이드), N: 계좌이체 처리(iOS Web-to-Web)) ☞ “연동스펙_결제창2.0.xls” 첨부파일 참조, “ISP 신용카드, 계좌이체 결제에 필요한 파라미터” 참조
    private String LGD_MTRANSFERWAPURL="";			// 계좌이체 승인완료 화면처리 URL(계좌이체 사용시 필수) ☞ “ISP 신용카드, 계좌이체 결제에 필요한 파라미터” 참조
    private String LGD_MTRANSFERCANCELURL="";		// 계좌이체 결제실패(오류) 결과화면 URL(계좌이체 사용시 필수) ☞ “ISP 신용카드, 계좌이체 결제에 필요한 파라미터” 참조    		

    // 모바일 페이나우
    private String LGD_PCVIEWYN="";						// "휴대폰번호 입력” 화면 사용 여부, 유심칩이 없는 단말기에서 휴대폰번호를 입력하는 화면 제공, 예) 태블릿 (PC용 결제에서 페이나우 선택시 출력되는 화면과 동일), (휴대폰 번호 입력 -> 다음버튼 클릭 -> 유심칩이 있는 휴대폰에 설치된 페이나우로 결제 가능)
    
    /*Return URL에서 인증 결과 수신 시 셋팅될 파라미터 입니다.*/
    private String LGD_RESPCODE = "";
    private String LGD_RESPMSG = "";
    private String LGD_PAYKEY = "";

    // 에스크로 파라미터
    private String LGD_ESCROW_ZIPCODE = "";			// 에스크로배송지우편번호
    private String LGD_ESCROW_ADDRESS1 = "";			// 에스크로배송지주소동까지
    private String LGD_ESCROW_ADDRESS2 = "";			// 에스크로배송지주소상세
    private String LGD_ESCROW_BUYERPHONE = "";	// 에스크로구매자휴대폰번호
//    private List<XPayEscrowGoodsVO> escrowGoodsList;	// 상품정보가 복수개일 때 해당 필드를 중복해서 사용 (5개의 에스크로 필드를 반드시 한쌍으로 적용)
    
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
	public String getLGD_TIMESTAMP() {
		return StringUtils.defaultString(LGD_TIMESTAMP);
	}
	public void setLGD_TIMESTAMP(String lGD_TIMESTAMP) {
		LGD_TIMESTAMP = lGD_TIMESTAMP;
	}
	public String getLGD_HASHDATA() {
		return StringUtils.defaultString(LGD_HASHDATA);
	}
	public void setLGD_HASHDATA(String lGD_HASHDATA) {
		LGD_HASHDATA = lGD_HASHDATA;
	}
	public String getLGD_RETURNURL() {
		return StringUtils.defaultString(LGD_RETURNURL);
	}
	public void setLGD_RETURNURL(String lGD_RETURNURL) {
		LGD_RETURNURL = lGD_RETURNURL;
	}
	public String getLGD_WINDOW_TYPE() {
		return StringUtils.defaultString(LGD_WINDOW_TYPE);
	}
	public void setLGD_WINDOW_TYPE(String lGD_WINDOW_TYPE) {
		LGD_WINDOW_TYPE = lGD_WINDOW_TYPE;
	}
	public String getLGD_BUYERID() {
		return StringUtils.defaultString(LGD_BUYERID);
	}
	public void setLGD_BUYERID(String lGD_BUYERID) {
		LGD_BUYERID = lGD_BUYERID;
	}
	public String getLGD_BUYERIP() {
		return StringUtils.defaultString(LGD_BUYERIP);
	}
	public void setLGD_BUYERIP(String lGD_BUYERIP) {
		LGD_BUYERIP = lGD_BUYERIP;
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
	public String getLGD_CUSTOM_PROCESSTYPE() {
		return StringUtils.defaultString(LGD_CUSTOM_PROCESSTYPE);
	}
	public void setLGD_CUSTOM_PROCESSTYPE(String lGD_CUSTOM_PROCESSTYPE) {
		LGD_CUSTOM_PROCESSTYPE = lGD_CUSTOM_PROCESSTYPE;
	}
	public String getLGD_CUSTOM_SESSIONTIMEOUT() {
		return StringUtils.defaultString(LGD_CUSTOM_SESSIONTIMEOUT);
	}
	public void setLGD_CUSTOM_SESSIONTIMEOUT(String lGD_CUSTOM_SESSIONTIMEOUT) {
		LGD_CUSTOM_SESSIONTIMEOUT = lGD_CUSTOM_SESSIONTIMEOUT;
	}
	public String getLGD_CUSTOM_USABLEPAY() {
		return StringUtils.defaultString(LGD_CUSTOM_USABLEPAY);
	}
	public void setLGD_CUSTOM_USABLEPAY(String lGD_CUSTOM_USABLEPAY) {
		LGD_CUSTOM_USABLEPAY = lGD_CUSTOM_USABLEPAY;
	}
	public String getLGD_CUSTOM_SKIN() {
		return StringUtils.defaultString(LGD_CUSTOM_SKIN);
	}
	public void setLGD_CUSTOM_SKIN(String lGD_CUSTOM_SKIN) {
		LGD_CUSTOM_SKIN = lGD_CUSTOM_SKIN;
	}
	public String getLGD_CUSTOM_CEONAME() {
		return StringUtils.defaultString(LGD_CUSTOM_CEONAME);
	}
	public void setLGD_CUSTOM_CEONAME(String lGD_CUSTOM_CEONAME) {
		LGD_CUSTOM_CEONAME = lGD_CUSTOM_CEONAME;
	}
	public String getLGD_CUSTOM_MERTNAME() {
		return StringUtils.defaultString(LGD_CUSTOM_MERTNAME);
	}
	public void setLGD_CUSTOM_MERTNAME(String lGD_CUSTOM_MERTNAME) {
		LGD_CUSTOM_MERTNAME = lGD_CUSTOM_MERTNAME;
	}
	public String getLGD_CUSTOM_MERTPHONE() {
		return StringUtils.defaultString(LGD_CUSTOM_MERTPHONE);
	}
	public void setLGD_CUSTOM_MERTPHONE(String lGD_CUSTOM_MERTPHONE) {
		LGD_CUSTOM_MERTPHONE = lGD_CUSTOM_MERTPHONE;
	}
	public String getLGD_CUSTOM_BUSINESSNUM() {
		return StringUtils.defaultString(LGD_CUSTOM_BUSINESSNUM);
	}
	public void setLGD_CUSTOM_BUSINESSNUM(String lGD_CUSTOM_BUSINESSNUM) {
		LGD_CUSTOM_BUSINESSNUM = lGD_CUSTOM_BUSINESSNUM;
	}
	public String getLGD_CUSTOM_LOGO() {
		return StringUtils.defaultString(LGD_CUSTOM_LOGO);
	}
	public void setLGD_CUSTOM_LOGO(String lGD_CUSTOM_LOGO) {
		LGD_CUSTOM_LOGO = lGD_CUSTOM_LOGO;
	}
	public String getLGD_CUSTOM_CARDPOINTUSEYN() {
		return StringUtils.defaultString(LGD_CUSTOM_CARDPOINTUSEYN);
	}
	public void setLGD_CUSTOM_CARDPOINTUSEYN(String lGD_CUSTOM_CARDPOINTUSEYN) {
		LGD_CUSTOM_CARDPOINTUSEYN = lGD_CUSTOM_CARDPOINTUSEYN;
	}
	public String getLGD_TAXFREEAMOUNT() {
		return StringUtils.defaultString(LGD_TAXFREEAMOUNT);
	}
	public void setLGD_TAXFREEAMOUNT(String lGD_TAXFREEAMOUNT) {
		LGD_TAXFREEAMOUNT = lGD_TAXFREEAMOUNT;
	}
	public String getLGD_CLOSEDATE() {
		return StringUtils.defaultString(LGD_CLOSEDATE);
	}
	public void setLGD_CLOSEDATE(String lGD_CLOSEDATE) {
		LGD_CLOSEDATE = lGD_CLOSEDATE;
	}
	public String getLGD_ESCROW_USEYN() {
		return StringUtils.defaultString(LGD_ESCROW_USEYN);
	}
	public void setLGD_ESCROW_USEYN(String lGD_ESCROW_USEYN) {
		LGD_ESCROW_USEYN = lGD_ESCROW_USEYN;
	}
	public String getLGD_ENCODING() {
		return StringUtils.defaultString(LGD_ENCODING);
	}
	public void setLGD_ENCODING(String lGD_ENCODING) {
		LGD_ENCODING = lGD_ENCODING;
	}
	public String getLGD_ENCODING_RETURNURL() {
		return StringUtils.defaultString(LGD_ENCODING_RETURNURL);
	}
	public void setLGD_ENCODING_RETURNURL(String lGD_ENCODING_RETURNURL) {
		LGD_ENCODING_RETURNURL = lGD_ENCODING_RETURNURL;
	}
	public String getLGD_VERSION() {
		return StringUtils.defaultString(LGD_VERSION);
	}
	public void setLGD_VERSION(String lGD_VERSION) {
		LGD_VERSION = lGD_VERSION;
	}
	public String getLGD_OSTYPE_CHECK() {
		return StringUtils.defaultString(LGD_OSTYPE_CHECK);
	}
	public void setLGD_OSTYPE_CHECK(String lGD_OSTYPE_CHECK) {
		LGD_OSTYPE_CHECK = lGD_OSTYPE_CHECK;
	}
	public String getLGD_BACKBTN_YN() {
		return StringUtils.defaultString(LGD_BACKBTN_YN);
	}
	public void setLGD_BACKBTN_YN(String lGD_BACKBTN_YN) {
		LGD_BACKBTN_YN = lGD_BACKBTN_YN;
	}
	public String getLGD_INSTALLRANGE() {
		return StringUtils.defaultString(LGD_INSTALLRANGE);
	}
	public void setLGD_INSTALLRANGE(String lGD_INSTALLRANGE) {
		LGD_INSTALLRANGE = lGD_INSTALLRANGE;
	}
	public String getLGD_NOINTINF() {
		return StringUtils.defaultString(LGD_NOINTINF);
	}
	public void setLGD_NOINTINF(String lGD_NOINTINF) {
		LGD_NOINTINF = lGD_NOINTINF;
	}
	public String getLGD_USABLECARD() {
		return StringUtils.defaultString(LGD_USABLECARD);
	}
	public void setLGD_USABLECARD(String lGD_USABLECARD) {
		LGD_USABLECARD = lGD_USABLECARD;
	}
	public String getLGD_LANGUAGE() {
		return StringUtils.defaultString(LGD_LANGUAGE);
	}
	public void setLGD_LANGUAGE(String lGD_LANGUAGE) {
		LGD_LANGUAGE = lGD_LANGUAGE;
	}
	public String getLGD_CASHRECEIPTYN() {
		return StringUtils.defaultString(LGD_CASHRECEIPTYN);
	}
	public void setLGD_CASHRECEIPTYN(String lGD_CASHRECEIPTYN) {
		LGD_CASHRECEIPTYN = lGD_CASHRECEIPTYN;
	}
	public String getLGD_USABLEBANK() {
		return StringUtils.defaultString(LGD_USABLEBANK);
	}
	public void setLGD_USABLEBANK(String lGD_USABLEBANK) {
		LGD_USABLEBANK = lGD_USABLEBANK;
	}
	public String getLGD_ACTIVEXYN() {
		return StringUtils.defaultString(LGD_ACTIVEXYN);
	}
	public void setLGD_ACTIVEXYN(String lGD_ACTIVEXYN) {
		LGD_ACTIVEXYN = lGD_ACTIVEXYN;
	}
	public String getLGD_KVPMISPAUTOAPPYN() {
		return LGD_KVPMISPAUTOAPPYN;
	}
	public void setLGD_KVPMISPAUTOAPPYN(String lGD_KVPMISPAUTOAPPYN) {
		LGD_KVPMISPAUTOAPPYN = lGD_KVPMISPAUTOAPPYN;
	}
	public String getLGD_KVPMISPWAPURL() {
		return LGD_KVPMISPWAPURL;
	}
	public void setLGD_KVPMISPWAPURL(String lGD_KVPMISPWAPURL) {
		LGD_KVPMISPWAPURL = lGD_KVPMISPWAPURL;
	}
	public String getLGD_KVPMISPCANCELURL() {
		return LGD_KVPMISPCANCELURL;
	}
	public void setLGD_KVPMISPCANCELURL(String lGD_KVPMISPCANCELURL) {
		LGD_KVPMISPCANCELURL = lGD_KVPMISPCANCELURL;
	}
	public String getLGD_MPILOTTEAPPCARDWAPURL() {
		return LGD_MPILOTTEAPPCARDWAPURL;
	}
	public void setLGD_MPILOTTEAPPCARDWAPURL(String lGD_MPILOTTEAPPCARDWAPURL) {
		LGD_MPILOTTEAPPCARDWAPURL = lGD_MPILOTTEAPPCARDWAPURL;
	}
	public String getLGD_MTRANSFERAUTOAPPYN() {
		return LGD_MTRANSFERAUTOAPPYN;
	}
	public void setLGD_MTRANSFERAUTOAPPYN(String lGD_MTRANSFERAUTOAPPYN) {
		LGD_MTRANSFERAUTOAPPYN = lGD_MTRANSFERAUTOAPPYN;
	}
	public String getLGD_MTRANSFERWAPURL() {
		return LGD_MTRANSFERWAPURL;
	}
	public void setLGD_MTRANSFERWAPURL(String lGD_MTRANSFERWAPURL) {
		LGD_MTRANSFERWAPURL = lGD_MTRANSFERWAPURL;
	}
	public String getLGD_MTRANSFERCANCELURL() {
		return LGD_MTRANSFERCANCELURL;
	}
	public void setLGD_MTRANSFERCANCELURL(String lGD_MTRANSFERCANCELURL) {
		LGD_MTRANSFERCANCELURL = lGD_MTRANSFERCANCELURL;
	}
	public String getLGD_PCVIEWYN() {
		return LGD_PCVIEWYN;
	}
	public void setLGD_PCVIEWYN(String lGD_PCVIEWYN) {
		LGD_PCVIEWYN = lGD_PCVIEWYN;
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
	public String getLGD_PAYKEY() {
		return StringUtils.defaultString(LGD_PAYKEY);
	}
	public void setLGD_PAYKEY(String lGD_PAYKEY) {
		LGD_PAYKEY = lGD_PAYKEY;
	}
	public String getLGD_CUSTOM_SWITCHINGTYPE() {
		return StringUtils.defaultString(LGD_CUSTOM_SWITCHINGTYPE);
	}
	public void setLGD_CUSTOM_SWITCHINGTYPE(String lGD_CUSTOM_SWITCHINGTYPE) {
		LGD_CUSTOM_SWITCHINGTYPE = lGD_CUSTOM_SWITCHINGTYPE;
	}
	public String getLGD_EASYPAY_ONLY() {
		return StringUtils.defaultString(LGD_EASYPAY_ONLY);
	}
	public void setLGD_EASYPAY_ONLY(String lGD_EASYPAY_ONLY) {
		LGD_EASYPAY_ONLY = lGD_EASYPAY_ONLY;
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
/*
	public List<XPayEscrowGoodsVO> getEscrowGoodsList() {
		return escrowGoodsList;
	}
	public void setEscrowGoodsList(List<XPayEscrowGoodsVO> escrowGoodsList) {
		this.escrowGoodsList = escrowGoodsList;
	}
*/
}
