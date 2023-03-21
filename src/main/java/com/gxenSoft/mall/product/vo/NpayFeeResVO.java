package com.gxenSoft.mall.product.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : NpayFeeResVO
    * PACKAGE NM : com.gxenSoft.mall.order.vo
    * AUTHOR	 : 강병철
    * CREATED DATE  : 2018. 06. 12. 
    * HISTORY :   Naver pay 추가배송비 응답
    *
    *************************************
    */	
@XmlRootElement(name = "additionalFees")
public class NpayFeeResVO {

	private List<NpayFeeVO> additionalFee;

	public List<NpayFeeVO> getAdditionalFee() {
		return additionalFee;
	}

	public void setAdditionalFee(List<NpayFeeVO> additionalFee) {
		this.additionalFee = additionalFee;
	}
	
    
}

