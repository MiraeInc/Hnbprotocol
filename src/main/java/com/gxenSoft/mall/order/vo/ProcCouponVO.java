package com.gxenSoft.mall.order.vo;

/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : ProcCouponVO
    * PACKAGE NM : com.gxenSoft.mall.order.vo
    * AUTHOR	 : 강 병 철
    * CREATED DATE  : 2017. 7. 27. 
    * HISTORY :   
    *  사용 가능한 쿠폰 목록 
    *************************************
    */	
public class ProcCouponVO {
	private int COUPON_IDX;
	private int COUPON_MEMBER_IDX;
	private String COUPON_NM;
	private String DISCOUNT_TYPE; //할인종류 (A:정액, R:정율)
	private int DISCOUNT;  // 할인액(률)
	private int DISCOUNT_PRICE;
	private String USE_END_DT;
	
	public int getCOUPON_IDX() {
		return COUPON_IDX;
	}
	public void setCOUPON_IDX(int cOUPON_IDX) {
		COUPON_IDX = cOUPON_IDX;
	}
	public int getCOUPON_MEMBER_IDX() {
		return COUPON_MEMBER_IDX;
	}
	public void setCOUPON_MEMBER_IDX(int cOUPON_MEMBER_IDX) {
		COUPON_MEMBER_IDX = cOUPON_MEMBER_IDX;
	}
	public String getCOUPON_NM() {
		return COUPON_NM;
	}
	public void setCOUPON_NM(String cOUPON_NM) {
		COUPON_NM = cOUPON_NM;
	}
	public String getDISCOUNT_TYPE() {
		return DISCOUNT_TYPE;
	}
	public void setDISCOUNT_TYPE(String dISCOUNT_TYPE) {
		DISCOUNT_TYPE = dISCOUNT_TYPE;
	}
	public int getDISCOUNT() {
		return DISCOUNT;
	}
	public void setDISCOUNT(int dISCOUNT) {
		DISCOUNT = dISCOUNT;
	}
	public int getDISCOUNT_PRICE() {
		return DISCOUNT_PRICE;
	}
	public void setDISCOUNT_PRICE(int dISCOUNT_PRICE) {
		DISCOUNT_PRICE = dISCOUNT_PRICE;
	}
	public String getUSE_END_DT() {
		return USE_END_DT;
	}
	public void setUSE_END_DT(String uSE_END_DT) {
		USE_END_DT = uSE_END_DT;
	}
	
}
