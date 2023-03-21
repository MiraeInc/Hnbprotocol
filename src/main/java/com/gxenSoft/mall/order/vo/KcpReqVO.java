package com.gxenSoft.mall.order.vo;

import lombok.Getter;
import lombok.Setter;

/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : KcpReqVO
    * PACKAGE NM : com.gxenSoft.mall.order.vo
    * AUTHOR	 : 강 병 철
    * CREATED DATE  : 2018. 07. 16. 
    * HISTORY :   
    *
    *************************************
    */
@Getter
@Setter
public class KcpReqVO {

	//주문요청
	private String req_tx = "pay";			 // 3 	  Y 	요청의 종류를 구분하는 변수 결제요청페이지의 경우 ‘pay’로 설정 
	private String site_name = ""; // 20 	N 	상점이름(영문으로 작성권장) 
	private String site_cd = ""; // 5 	  Y 	상점코드
	private String ordr_idxx = ""; // 40 	Y 	상점 관리 주문번호 (유니크한 값 설정 권장)
	private String pay_method = ""; // 12 	Y 	결제수단코드 ,12자리 숫자로 구성 (사용 	: 1, 사용 안 함 : 0) 신용카드 	: 100000000000 계좌이체:010000000000 	가상계좌:001000000000,포인트:000100000000,휴대폰:000010000000,상품권:000000001000, 신용카드, 계좌이체, 가상계좌를 창에 같이 나타나게 하는 경우의는 ‘111000000000’ 로 설정 	하나의 결제
															//  모바일일경우 (카드 CARD 계좌이체 BANK 가상계좌 VCNT 휴대폰 MOBX 포인트 TPNT 상품권 GIFT )				
	private String good_name = ""; // 30 	Y 	상품명 	
	private int 	 good_mny 		; // 			Y 	주문요청금액 (결제 창으로 전달하는 금액으로 	결제 창에 보여지는 실제 결제금액이 아님) 결제창이 닫힌 후 ordr_chk 값에 	리턴되는 금액정보가 변경되는지 체크 ※ 화폐단위가 ‘USD’ 일 경우, Cent까지 설정 ex ) $10.55 일 경우 콤마를 뺀 1055, $100 일 경우 10000로 설정 결제 금액은 숫자 이외의 문자(콤마 등)는 허용 하지 않습니다 
	private String buyr_name = ""; // 30 	N 	주문자 이름 
	private String buyr_mail = ""; // 50 	N 	주문자 이메일 
	private String buyr_tel1 = ""; // 20 	N 	주문자 전화번호 
	private String buyr_tel2 = ""; // 20 	Y 	주문자 휴대폰번호 
	private String currency = "WON";   // 3 	  Y 	화폐단위 원화 : WON 모바일일경우 "410" / 달러 : USD 달러의 경우 NHN KCP에 별도의 신청(국제 세 미나, 국제 학술대회 등만 신청 가능)해야 사용 가능 
	private String shop_user_id= "" ; 	// 20 	Y 	쇼핑몰회원ID 기관에 따라 RM 조치를 위해 쇼핑몰 관리 ID 를 필수로 요청 휴대폰소액결제 – 40Byte 상품권결제 – 20Byte 
	private String module_type = "01";	// 필수 Y 변경불가
	
	//에스크로 관련
	private String escw_used="N"; //에스크로사용여부
	private String pay_mod="N"; //에스크로 결제처리 모드, 설정 변수 에스크로 결제 처리 : ‘Y’ 일반 결제 처리 : ‘N’ 신청한 조건에 에스크로 결제 처리 : ‘O’ 
	private String deli_term = "02"; //	예상되는 배송 소요 일을 입력 반드시 형식을 2자리(일자)의 숫자로 입력 
	private String  bask_cntx=""; //상품의 개수
	private String rcvr_name = ""; //  N 수취자 이름 
	private String rcvr_tel1 = "";  // N 수취자 전화번호
	private String rcvr_tel2 = ""; //N 수취자 휴대폰번호 
	private String rcvr_mail = ""; // 수취자 이메일 9 
	private String rcvr_zipx = ""; //수취자 우편번호 
	private String rcvr_add1 = ""; // 수취자 우편번호 
	private String rcvr_add2 = ""; // 수취자 상세주소 
	private String good_info = ""; // 장바구니 내 각각의 상품 정보는 RECORD SEPARATOR (chr(30))에 의해 구분 각각의 상품 정보 내 항목들은 UNIT SEPARATOR (chr(31))에 의해 구분 
	
	
	//모바일 파라메터
	private String shop_name	; // 20 	N 	상점이름(영문으로 작성권장) 
	private String response_type;
	private String Ret_URL;              // Y,	NHN KCP 스마트폰 결제 창에서 인증완료 후 인증데이터를 리턴 받을 업체페이지 Ret_URL 뒤의 추가 파라미터는 허용되지 않습 니다. 
	private String ActionResult;		//Y 인증수단(영문 소문자) 인증수단 코드 카드 card 계좌이체 acnt 가상계좌 vcnt 휴대폰 mobx 도서문화상품권 scbl 문화상품권 sccl 해피머니상품권 schm OK 캐쉬백 ocb 복지포인트 tpnt 
	private String approval_key;
	private String PayUrl;        //approval_key.js 를 통해 리턴 받은 결제 창 호 출 주소. 전달 받은 값 그대로 요청 
	private String traceNo;
	
	//신용카드
	private int quotaopt = 12;					// N, 50,000원 이상 거래에 대한 할부 옵션. 기본값은 12개월. 0~12의 값을 설정하면 결제 창에 할부 개월 수가 최대값까 지 표기됨   
	private String kcp_noint  = "N";					//N, 무이자할부 표시기능 “” : 상점관리자 설정에 따름 “Y” : kcp_noint_quota 값에 따라 무이자표시(단, 상점관리자 에 설정이 되어야 함) “N” : 상점관리자 값을 무시하고 일반할부로 처리됨 
	private String kcp_noint_quota = "";			//N, 256, 무이자할부 표시기능이 Y 일 경우 무이자 설정 값을 결제 창에 표기 무이자 설정은 카드사 별로 설정 가능 
	private String used_card_YN = "Y";		// N 결제 요청 시 원하는 신용카드사 확인 해당 변수 값을 Y로 설정 후 used_card 변수 값에 원하는 신용카드사의 코드를 입력하면 입력한 신용카드사만 결제 창에 노출 
	private String used_card = "";				// used_card_YN 변수 값을 Y로 설정한 후 사용하길 원하는 신 용카드사의 코드 입력
	private String fix_inst = "";			// N 결제금액이 50,000원 이상일 경우 결제 창에서 선택 할 수 있는 할부 개월 수를 0~12 의 값 중 하나로 고정 
	
	//가상계좌
	private String wish_vbank_list =""; 				//50 	N 	NHN KCP에서 제공하는 은행 중 가맹점이 원 하는 은행을 선택할 수 있음(5.1.2 은행코드표 참고) 
	private String vcnt_expire_term	= "3" ;                    //Number 2 	N 	가상계좌 유효기간 설정 
	private String vcnt_expire_term_time = "235959"; // 	6 	N 	가상계좌 유효시간 설정. vcnt_expire_term과 부수적으로 설정되는 변수 

	//가상계좌(모바일)
	private String ipgm_date; // 발급된 가상계좌에 입금할 예정일 입력 (포맷 : yymmdd 또는 yyyymmddhhMMss) 예 : 20170111 또는 20170111235959 
	private String used_bank ; // NHN KCP에서 제공하는 은행 중 가맹점이 원하는 은행을 선택 
	
	//휴대폰
	private String hp_apply_yn = "";   //	 	1 	N 	원하는 통신사만 노출시킬 수 있음 변수 값을 Y로 설정한 후 hp_commid 변수의 값에 통신사 코드를 입력하면 결제창에 해당 통신사만 노출됨 
	private String hp_commid = "";	// 	20 	N 	하나의 통신사만 설정 가능	(5.1.3 통신사 코드 참고) 

	//휴대폰 (모바일)
	private String hp_comm_id;			// 	20 	N 	하나의 통신사만 설정 가능	(5.1.3 통신사 코드 참고) 

	//현금영수증 옵션
	private String disp_tax_yn ="";    //1 	N 	계좌이체, 가상계좌를 이용한 현금 결제 시 결 제 금액이 1원 이상인 경우, 결제 창에 현금영 수증 등록여부를 보여줌, 현금영수증 자동등록을 원할 경우 해당 변수를 Y 로 설정. 가상계좌의 경우 입금 완료 후 현금 영수증 등록됨 
																	//Y : 소득공제용, 지출증빙용 노출,	N : 현금영수증 숨기기,	R : 소득공제용만 노출,	E : 지출증빙용만 노출
	//옵션 변수
	private String site_logo 	= "";    // 	256 	N 	결제 창 왼쪽 상단에 가맹점 사이트의 로고를 띄움. 업체의 로고가 있는 URL을 정확히 입력 해야 하며 해당 변수 생략 시에는 로고가 뜨지 않고 site_name 값이 표시됨 로고 파일은 GIF, JPG 파일만 지원 최대 사이즈 : 150 X 50 미만 이미지 파일을 150 X 50 이상으로 설정 시 site_name 값이 표시됨 ※ site_logo 설정 시 결제 창 호출이 느려질 수 있음 
	private String eng_flag 	= "";   // 	1 	N 	결제 창 한글/영문 변환 ,신용카드, 계좌이체, 가상계좌, 휴대폰소액결제 에 적용 
	private int skin_indx 	=	1;			// 	N 	결제 창 스킨 변경. 1~11까지 설정 가능 
	private String good_expr = "0";		// 	18 	Y 	상품 제공기간 "","0" : 일반결제 "1":제공기간 ※ 기본 설정은 일반결제 
	private String good_cd 	= "";	 	//20 	N 	상품코드 : 주문상품명으로 구분이 어려운 경우 상품군을 따로 구분하여 처리할 수 있는 옵션 기능 
	private String tax_flag 	= "";	// 	4 	N 	복합 과세 구문 TG01 : 과세 TG02 : 비과세 TG03 : 복합과세 ※ 표 아래 추가 안내사항 확인 
	private int comm_tax_mny 	; //	12 	N 	과세 승인금액 (공급가액) 과세 금액에 해당하는 공급가액 설정,  과세 금액 = good_mny / 1.1 
	private int comm_free_mny;  //	12 	N 	비과세 승인금액,  비과세 금액에 해당하는 공급가액 설정,  비과세 금액 = good_mny – 과세금액 – 부가가 치세 
	private int comm_vat_mny; 	  // 	12 	N 	부가가치세,     부가가치세는 과세금액 공금가액의 10% 부가가치세 = good_mny – 과세금액 
	
	//결제요청정보(리턴)
	private String  enc_data	= "" ; //전달받은 인증결과 
	private String  enc_info	= "" ; //전달받은 인증결과 
	private String  tran_cd	= ""  ; //전달받은 상태코드 
	private String  ordr_chk	= "" ; //인증완료 후 결제 창으로부터 전달 받는 주문 번호와 주문금액 .	
	                                                 //주문페이지에서 결제 창으로 전달한 ordr_idxx, good_mny 값이 그대로 리턴 되기 때문에 NHN KCP로 결제요청 전, 리턴 받은 해당 값에 대해 업체 측에서 다시 한번 검증 하고 결제요 청 하시기 바랍니다. ※ PC버전에만 적용 
    private String use_pay_method	= "" ; //Y 	고객이 선택한 결제 수단 
    private String cash_yn	= "";  //현금영수증 선택여부 
    private String cash_tr_code	= "" ; 	 //	Y 	현금영수증 선택 시 식별코드   소득공제용(개인) : 0 / 지출증빙용(기업) : 1 
    private String cash_id_info	= ""  ; // N 현금영수증 등록요청 시 입력한 등록 정보 
   
    private String res_cd	= "" ; //결과코드 정상 승인이 이루어졌을 경우 ‘0000’ 값 리턴 
    private String res_msg	= "" ; //결과 메시지 
    private String res_en_msg	= "" ; //결과 메시지 영문
    private String tno	= "" ; //NHN KCP 거래 고유번호 
    private String amount	= "" ; //총 결제 금액이 리턴
    private String escw_yn	= "" ; //에스크로 결제여부 
   
    //신용카드
	private String card_cd = ""; // 	 	N 	결제 건의 발급 사 코드 (5.1.1 승인 및 매입 카드사 코드 표 참고) 
	private String card_name 				= "";  //String 	32 	N 	결제 건의 발급 사 명 
	private String card_no 				  = "";  //Number 	16 	N 	결제 건의 카드번호 카드번호 16자리 중 3번째구간은 마스킹 
	private String app_no 					= "";  //String 	8 	N 	결제 건의 	승인번호 
	private String app_time 				= "";  //Number 	14 	N 	결제 건의 	결제(승인) 시간 
	private String noinf 						= "";  //String 	1 	N 	결제 건의 	무이자 여부 
	private String noinf_type 			= "";  //String 	4 	N 	noinf = Y 일 때 (무이자 결제인 경우) 카드사 이벤트 무이자인 경우 : CARD 상점 부담 무이자인 경우 : SHOP 
	private String quota 						= "";  //String 	2 	N 	결제 	건의 	할부 기간 
	private String card_mny 				= "";  //Number 	12 	N 	결제 	건의 	총 결제금액 중 신용카드 결제금액 ,만약 	총 결제금액(amount) 10000 원 중 쿠폰할인 2000 원 받았다면 card_mny=8000,    *페이코 포인트, 쿠폰 100% 결제 시 card_mny=0 으로 리턴될 수 있으니 반드시  총 결제 금액 처리는 amount 금액으로 체크하 시기 바랍니다. 
	private String coupon_mny 			= "";  //Number 	12 	N 	결제 건의 쿠폰 할인, 페이코 포인트 사용 금액 결제 건의 쿠폰 할인 금액 또는 페이코 포인트 사용 금액이 리턴됩니다. 만약 총 결제금액(amount) 10000 원 중 쿠폰 할인을 2000 원 받 았다면 coupon_mny=2000 이 됩니다. 
	private String partcanc_yn 			= "";  //String 	1 	N 	결제 	건의 부분취소 가능 유무 
	private String card_bin_type_01 = "";  //	Number 	1 	N 	결제 	건의 카드 구분 	정보, 개인 	: 0 / 법인 : 1 	
	private String card_bin_type_02 = "";  //	Number 	1 	N 	결제 	건의 카드 구분 	정보 ,일반 	: 0 / 체크 : 1 	
	private String isp_issuer_cd 		= "";  //String 	4 	N 	ISP 계열 카드 발급 사 코드,BC96 : 케이뱅크카드,KM90 : 카카오뱅크카드 
	private String isp_issuer_nm	 	= "";  //String 	32 	N 	ISP 계열 카드 발급 사 명 카카오뱅크 케이뱅크의경우 BC카드로 리턴 됨 
	private String payco_point_mny 	= "";  //Number 	12 	N 	결제 건의 페이코 포인트 사용 금액 

	//계좌이체  //가상계좌
	private String bank_code 	 = "";   //String 	4 	N 	결제 건의 은행코드 (5.1.2 은행코드표 참고) ※ 테스트코드로 계좌이체 테스트 시 금융결제 원과 협의된 은행 코드로 리턴 
	private String bank_name 	 = "";   //String 	20 	N 	결제 건의 은행 명 ※ 테스트코드로 계좌이체 테스트 시 금융결제 원과 협의된 은행 명으로 리턴 
	 
	private String cash_authno = "";   //Number 	9 	N 	NHN KCP 결제 창에서 현금영수증 등록 요청 한 결제 건의 현금영수증 승인번호 
	private String cash_no 		 = "";   //Number 	14 	N 	NHN KCP 결제 창에서 현금영수증 등록 요청 한 결제 건의 현금영수증 거래번호 
	private String bk_mny 		 = "";   //Number 	12 	N 	결제 건의 계좌이체 결제 금액 

	private String account 		 = "";   //String 	20 	N 	가상계좌 번호 
	private String va_date 		 = "";   //Number 	14 	N 	가상계좌 입금마감일 
   
	// 휴대폰
	private String van_cd = ""; 		//String 	4 	N 	결제 건의 결제 사 코드 	(5.1.3 통신사 코드 참고) 
	private String van_id = ""; 		//String 	4 	N 	결제 건의 	실물/컨텐츠 구분 
	private String commid  = "";		//String 	3 	N 	결제 건의 	통신사 코드 
	private String mobile_no  = "";	//Number 	11 	N 	결제 건의 	휴대폰 번호 

}
