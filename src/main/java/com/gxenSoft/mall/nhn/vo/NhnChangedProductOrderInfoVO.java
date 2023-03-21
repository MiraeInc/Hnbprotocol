package com.gxenSoft.mall.nhn.vo;

import java.util.List;


/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : NhnChangedProductOrderInfoVO
 * PACKAGE NM : com.gxenSoft.mall.nhn.vo
 * AUTHOR	 : 강 병 철
 * CREATED DATE  : 2018. 7. 10. 
 * HISTORY :
 
 *************************************
 */
public class NhnChangedProductOrderInfoVO  {
	
	private String OrderID;								
	private List<String> ProductOrderIDList ;  			
	
	public String getOrderID() {
		return OrderID;
	}
	public void setOrderID(String orderID) {
		OrderID = orderID;
	}
	public List<String> getProductOrderIDList() {
		return ProductOrderIDList;
	}
	public void setProductOrderIDList(List<String> productOrderIDList) {
		ProductOrderIDList = productOrderIDList;
	}
}
