package com.gxenSoft.mall.order.vo;

import java.util.List;

/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : ProcCouponResultVO
    * PACKAGE NM : com.gxenSoft.mall.order.vo
    * AUTHOR	 : 강 병 철
    * CREATED DATE  : 2017. 7. 27. 
    * HISTORY :   
    *  사용 가능한 쿠폰 목록 
    *************************************
    */	
public class ProcCouponResultVO {

	private Boolean result;			
	private String msg;
	private String errorCode;
	
	private List<ProcCouponVO> couponList = null;

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public List<ProcCouponVO> getCouponList() {
		return couponList;
	}

	public void setCouponList(List<ProcCouponVO> couponList) {
		this.couponList = couponList;
	}
	
}
