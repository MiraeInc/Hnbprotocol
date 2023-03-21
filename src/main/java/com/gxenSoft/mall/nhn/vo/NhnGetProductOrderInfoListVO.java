package com.gxenSoft.mall.nhn.vo;

import com.nhncorp.platform.checkout.mall.GetProductOrderInfoListResponse;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : NhnErrorTypeVO
 * PACKAGE NM :  com.gxenSoft.mall.nhn.vo;
 * AUTHOR	 : 강병철
 * CREATED DATE  : 2018. 7. 8. 
 * HISTORY :
 
 *************************************
 */
public class NhnGetProductOrderInfoListVO  {
	
	private GetProductOrderInfoListResponse orderRes ;					
	private byte[] encryptKey = null;
	
	public GetProductOrderInfoListResponse getOrderRes() {
		return orderRes;
	}
	public void setOrderRes(GetProductOrderInfoListResponse orderRes) {
		this.orderRes = orderRes;
	}
	public byte[] getEncryptKey() {
		return encryptKey;
	}
	public void setEncryptKey(byte[] encryptKey) {
		this.encryptKey = encryptKey;
	} 

}
