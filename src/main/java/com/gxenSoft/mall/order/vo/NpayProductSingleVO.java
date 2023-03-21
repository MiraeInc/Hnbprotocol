package com.gxenSoft.mall.order.vo;

import javax.xml.bind.annotation.XmlRootElement;

/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : NpayVO
    * PACKAGE NM : com.gxenSoft.mall.order.vo
    * AUTHOR	 : 강병철
    * CREATED DATE  : 2018. 06. 12. 
    * HISTORY :   
    *
    *************************************
    */	
@XmlRootElement(name = "single")
public class NpayProductSingleVO {

	private Integer quantity ;													// N, 단일 상품 주문 수량. 단일 상품(옵션이없는상품일경우)이면 필수. 1 이상만 입력 가능.	옵션 상품이면 옵션 상품 주문 수량에 주문 수량을 입력한다. 자세한 내용은 "표 3-3 구매 정보 연동 - option 상세 내용"을 참조한다.
    

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
		
}

