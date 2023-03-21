package com.gxenSoft.mall.order.vo;

/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : JsonResultVO
    * PACKAGE NM : com.gxenSoft.mall.common.vo
    * AUTHOR	 : 서 정 길
    * CREATED DATE  : 2017. 6. 27. 
    * HISTORY :   
    *
    *************************************
    */	
public class NpayResultVO {

	private Boolean result;			
	private String msg;
	private String certiKey;  //인증키
	private String merchantNo;  //가맹점번호
	private String orderUrl;  //결제 URL
	private String itemId=""; //찜등록시 사용 (찜한 naver상품코드)
	
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
	public String getCertiKey() {
		return certiKey;
	}
	public void setCertiKey(String certiKey) {
		this.certiKey = certiKey;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getOrderUrl() {
		return orderUrl;
	}
	public void setOrderUrl(String orderUrl) {
		this.orderUrl = orderUrl;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	
}
