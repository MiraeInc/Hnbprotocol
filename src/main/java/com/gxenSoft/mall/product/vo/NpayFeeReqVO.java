package com.gxenSoft.mall.product.vo;

import java.util.List;

/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : NpayFeeReqVO
    * PACKAGE NM : com.gxenSoft.mall.order.vo
    * AUTHOR	 : 강병철
    * CREATED DATE  : 2018. 06. 12. 
    * HISTORY :   Naver pay 추가 배송비 요청
    *
    *************************************
    */	
public class NpayFeeReqVO {

	private List<String> productId;			// Y 조회하려는 상품 번호, 상품 번호는 여러 개를 설정할 수 있고 product[0]=XXX&productId[1]=XX…. 형식으로 복수 건 추가 가능
	private String zipcode;						// Y, 배송지 우편번호. 예)463050  
	private String address1;					// Y, 배송지 기본 주소. 예) 경기도 성남시 분당구 서현동	한글값은 UTF-8 로 디코딩하여 base64 url encoding 방식으로 전달 함 
	
	public List<String> getProductId() {
		return productId;
	}
	public void setProductId(List<String> productId) {
		this.productId = productId;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
}

