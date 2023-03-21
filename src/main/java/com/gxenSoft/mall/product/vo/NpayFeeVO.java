package com.gxenSoft.mall.product.vo;

import javax.xml.bind.annotation.XmlRootElement;

/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : NpayFeeVO
    * PACKAGE NM : com.gxenSoft.mall.order.vo
    * AUTHOR	 : 강병철
    * CREATED DATE  : 2018. 06. 12. 
    * HISTORY :   Naver pay 추가배송비 응답
    *
    *************************************
    */	
@XmlRootElement(name = "additionalFee")
public class NpayFeeVO {

	private String id;         //상품 ID Y  상품 번호 30 자 
	private Integer surprice = 0;  // 추가배송비금액 N 0 추가배송비 
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getSurprice() {
		return surprice;
	}
	public void setSurprice(Integer surprice) {
		this.surprice = surprice;
	}
	
    
}

