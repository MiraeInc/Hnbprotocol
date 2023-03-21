package com.gxenSoft.mall.product.vo;
/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : NpayWishVO
    * PACKAGE NM : com.gxenSoft.mall.order.vo
    * AUTHOR	 : 강병철
    * CREATED DATE  : 2018. 06. 12. 
    * HISTORY :   Naver pay 찜 
    *
    *************************************
    */	
public class NpayWishVO {

	private String SHOP_ID ;   		// Y 상점 ID. 네이버페이에 가입 승인될 때 정해진다. 
	private String CERTI_KEY;   	// Y 인증키. 네이버페이에 가입 승인될 때 정해진다. 
	private String ITEM_ID;   			// Y 상품 ID 
	private String EC_MALL_PID;   // N 지식쇼핑 EP 의 mall_pid. 지식쇼핑 가맹점이면 지식쇼핑 EP 의 mall_pid 와 동일한 값을 입력해야 한다. 
	private String ITEM_NAME;   	// Y 상품 이름 
	private String ITEM_DESC;   	// Y 상품 설명 
	private Integer ITEM_UPRICE;   // Y 개별 상품 단가. 0 보다 커야 한다. 
	private String ITEM_IMAGE;   	// Y 상품 사진 URL. 
	private String ITEM_THUMB;   	// Y 상품 썸네일 사진 URL.
	private String ITEM_URL;   		// Y 상품 정보 URL. 
	
	public String getSHOP_ID() {
		return SHOP_ID;
	}
	public void setSHOP_ID(String sHOP_ID) {
		SHOP_ID = sHOP_ID;
	}
	public String getCERTI_KEY() {
		return CERTI_KEY;
	}
	public void setCERTI_KEY(String cERTI_KEY) {
		CERTI_KEY = cERTI_KEY;
	}
	public String getITEM_ID() {
		return ITEM_ID;
	}
	public void setITEM_ID(String iTEM_ID) {
		ITEM_ID = iTEM_ID;
	}
	public String getEC_MALL_PID() {
		return EC_MALL_PID;
	}
	public void setEC_MALL_PID(String eC_MALL_PID) {
		EC_MALL_PID = eC_MALL_PID;
	}
	public String getITEM_NAME() {
		return ITEM_NAME;
	}
	public void setITEM_NAME(String iTEM_NAME) {
		ITEM_NAME = iTEM_NAME;
	}
	public String getITEM_DESC() {
		return ITEM_DESC;
	}
	public void setITEM_DESC(String iTEM_DESC) {
		ITEM_DESC = iTEM_DESC;
	}
	public Integer getITEM_UPRICE() {
		return ITEM_UPRICE;
	}
	public void setITEM_UPRICE(Integer iTEM_UPRICE) {
		ITEM_UPRICE = iTEM_UPRICE;
	}
	public String getITEM_IMAGE() {
		return ITEM_IMAGE;
	}
	public void setITEM_IMAGE(String iTEM_IMAGE) {
		ITEM_IMAGE = iTEM_IMAGE;
	}
	public String getITEM_URL() {
		return ITEM_URL;
	}
	public void setITEM_URL(String iTEM_URL) {
		ITEM_URL = iTEM_URL;
	}
	public String getITEM_THUMB() {
		return ITEM_THUMB;
	}
	public void setITEM_THUMB(String iTEM_THUMB) {
		ITEM_THUMB = iTEM_THUMB;
	}
    
}

