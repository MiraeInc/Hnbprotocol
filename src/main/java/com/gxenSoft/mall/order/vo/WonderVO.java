package com.gxenSoft.mall.order.vo;

   /**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : WonderAuthReqVO
    * PACKAGE NM : com.gxenSoft.mall.order.vo
    * AUTHOR	 : 강 병 철
    * CREATED DATE  : 2018. 11. 02. 
    * HISTORY :    원더페이 DB저장
    *
    *************************************
    */	
public class WonderVO {

	private int order_idx ;  //주문번호 idx
	
	private String res_cd = ""; //응답코드 0000 정상  [0000 : 정상, 0001: 정상(0원결제), 0002: 정상(메일발송실패), 기타 : 실패] 승인결과코드 참조
	private String res_msg = ""; 

	private String ordr_idxx = "";  //주문번호
	private int good_mny = 0;  //결제요청금액
	private String tno = "";  //거래번호  
	private String site_cd = "";
	private String app_time = ""; //결제완료 시각 char yyyyMMddHHMISS (PG 결제 시각) 
	
	private String pay_method = ""; //카드 -> 신용 카드 : PACA	즉시출금 : PABK
  
	private String pg_code = ""; // 승인사 PG 코드 char KCP : K, INISC : I 
	 
	//즉시출금
	private int  bk_mny = 0; // 실 거래 금액 number 실제 거래 금액 (승인 금액) 35000
	private String bank_cd = ""; //은행 코드 char 결제 은행 코드 BK03
	private String cash_receipt_flag = ""; // 현금영수증 발급여부 char Y / N 
	
	//신용카드
	private int  card_mny = 0; // 카드 실결제금액 number 실제 거래 금액 (승인 금액) 	즉시 할인을 제외한 금액 good_mny = card_mny + coupon_mny
	private String card_cd = ""; // 결제 카드사 코드 char 결제한 카드사 코드 CCLG
	private String quota = ""; // 할부 개월 수 number 할부 개월 수 (일시불 : 00) 00
	private String noinf = ""; // 무이자 여부 char Y / N N
	private int coupon_mny  = 0; //쿠폰 적용 금액 number 즉시 할인 쿠폰 적용 금액 
	
	public String getRes_cd() {
		return res_cd;
	}
	public void setRes_cd(String res_cd) {
		this.res_cd = res_cd;
	}
	public String getRes_msg() {
		return res_msg;
	}
	public void setRes_msg(String res_msg) {
		this.res_msg = res_msg;
	}
	public String getOrdr_idxx() {
		return ordr_idxx;
	}
	public void setOrdr_idxx(String ordr_idxx) {
		this.ordr_idxx = ordr_idxx;
	}
	public int getGood_mny() {
		return good_mny;
	}
	public void setGood_mny(int good_mny) {
		this.good_mny = good_mny;
	}
	public String getTno() {
		return tno;
	}
	public void setTno(String tno) {
		this.tno = tno;
	}
	public String getSite_cd() {
		return site_cd;
	}
	public void setSite_cd(String site_cd) {
		this.site_cd = site_cd;
	}
	public String getApp_time() {
		return app_time;
	}
	public void setApp_time(String app_time) {
		this.app_time = app_time;
	}
	public String getPay_method() {
		return pay_method;
	}
	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}
	public String getPg_code() {
		return pg_code;
	}
	public void setPg_code(String pg_code) {
		this.pg_code = pg_code;
	}
	public int getBk_mny() {
		return bk_mny;
	}
	public void setBk_mny(int bk_mny) {
		this.bk_mny = bk_mny;
	}
	public String getBank_cd() {
		return bank_cd;
	}
	public void setBank_cd(String bank_cd) {
		this.bank_cd = bank_cd;
	}
	public String getCash_receipt_flag() {
		return cash_receipt_flag;
	}
	public void setCash_receipt_flag(String cash_receipt_flag) {
		this.cash_receipt_flag = cash_receipt_flag;
	}
	public int getCard_mny() {
		return card_mny;
	}
	public void setCard_mny(int card_mny) {
		this.card_mny = card_mny;
	}
	public String getCard_cd() {
		return card_cd;
	}
	public void setCard_cd(String card_cd) {
		this.card_cd = card_cd;
	}
	public String getQuota() {
		return quota;
	}
	public void setQuota(String quota) {
		this.quota = quota;
	}
	public String getNoinf() {
		return noinf;
	}
	public void setNoinf(String noinf) {
		this.noinf = noinf;
	}
	public int getCoupon_mny() {
		return coupon_mny;
	}
	public void setCoupon_mny(int coupon_mny) {
		this.coupon_mny = coupon_mny;
	}
	public int getOrder_idx() {
		return order_idx;
	}
	public void setOrder_idx(int order_idx) {
		this.order_idx = order_idx;
	}

	

}
