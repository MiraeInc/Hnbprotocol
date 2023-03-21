package com.gxenSoft.mall.product.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.gxenSoft.mall.order.vo.NpayProductVO;

/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : NpayVO
    * PACKAGE NM : com.gxenSoft.mall.order.vo
    * AUTHOR	 : 강병철
    * CREATED DATE  : 2018. 06. 12. 
    * HISTORY :   Naver pay 상품정보 응답
    *
    *************************************
    */	
@XmlRootElement(name = "products")
public class NpayResVO {

	private List<NpayProductVO> product;
	
	public List<NpayProductVO> getProduct() {
		return product;
	}
	public void setProduct(List<NpayProductVO> product) {
		this.product = product;
	}
    
}

