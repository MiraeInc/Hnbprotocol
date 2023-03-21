package com.gxenSoft.mall.order.vo;

import javax.xml.bind.annotation.XmlRootElement;

/**
    *************************************
    * PROJECT   : GatsbyMall 
    * PROGRAM ID  : NpayVO
    * PACKAGE NM : com.gxenSoft.mall.order.vo
    * AUTHOR	 : 강병철
    * CREATED DATE  : 2018. 06. 12. 
    * HISTORY :    무료배송정책정보
    *
    *************************************
    */	
@XmlRootElement(name = "conditionalFree")
public class NpayShippingConditionalFree {

	private String basePrice = "30000";    									//  Free/basePrice Y 배송비 그룹 내 상품 주문 금액 {(단가 + 옵션가) * 수량)} 합이 기준 금액 이상이면 무료  

	public String getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(String basePrice) {
		this.basePrice = basePrice;
	}

		
}

