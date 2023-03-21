package com.gxenSoft.mall.order.vo;

   /**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : WonderAuthReqVO
    * PACKAGE NM : com.gxenSoft.mall.order.vo
    * AUTHOR	 : 강 병 철
    * CREATED DATE  : 2018. 11. 02. 
    * HISTORY :    원더페이 인증 결과
    *
    *************************************
    */	
public class WonderAuthResVO {

	private String res_cd = ""; //응답코드 0000 정상
	private String res_msg = ""; 
	private String pay_type = ""; //카드 -> card,	즉시출금 -> acnt
	
	private String site_cd = "";  
	private String good_name = "";
	private String good_mny = ""; 
	private String buyr_name = "";
	private String buyr_mail = ""; 
	private String buyr_tel2 = "";  //주문자모바일번호
	private String ordr_idxx = "";  //주문번호
	private String coupon_use_yn = ""; //Y/N
	private String coupon_mny = "";  //죽시할인 금액 
	
	private String cash_receipt_flag = "";  //현금영수증 발행 여부 Y/N
	private String enc_code  = ""; //암호코드
	
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
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getSite_cd() {
		return site_cd;
	}
	public void setSite_cd(String site_cd) {
		this.site_cd = site_cd;
	}
	public String getGood_name() {
		return good_name;
	}
	public void setGood_name(String good_name) {
		this.good_name = good_name;
	}
	public String getGood_mny() {
		return good_mny;
	}
	public void setGood_mny(String good_mny) {
		this.good_mny = good_mny;
	}
	public String getBuyr_name() {
		return buyr_name;
	}
	public void setBuyr_name(String buyr_name) {
		this.buyr_name = buyr_name;
	}
	public String getBuyr_mail() {
		return buyr_mail;
	}
	public void setBuyr_mail(String buyr_mail) {
		this.buyr_mail = buyr_mail;
	}
	public String getBuyr_tel2() {
		return buyr_tel2;
	}
	public void setBuyr_tel2(String buyr_tel2) {
		this.buyr_tel2 = buyr_tel2;
	}
	public String getOrdr_idxx() {
		return ordr_idxx;
	}
	public void setOrdr_idxx(String ordr_idxx) {
		this.ordr_idxx = ordr_idxx;
	}
	public String getCoupon_use_yn() {
		return coupon_use_yn;
	}
	public void setCoupon_use_yn(String coupon_use_yn) {
		this.coupon_use_yn = coupon_use_yn;
	}
	public String getCoupon_mny() {
		return coupon_mny;
	}
	public void setCoupon_mny(String coupon_mny) {
		this.coupon_mny = coupon_mny;
	}
	public String getCash_receipt_flag() {
		return cash_receipt_flag;
	}
	public void setCash_receipt_flag(String cash_receipt_flag) {
		this.cash_receipt_flag = cash_receipt_flag;
	}
	public String getEnc_code() {
		return enc_code;
	}
	public void setEnc_code(String enc_code) {
		this.enc_code = enc_code;
	}
	

}
