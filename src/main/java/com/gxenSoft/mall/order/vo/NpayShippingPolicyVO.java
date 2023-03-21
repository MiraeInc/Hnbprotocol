package com.gxenSoft.mall.order.vo;

import javax.xml.bind.annotation.XmlRootElement;

/**
    *************************************
    * PROJECT   : GatsbyMall 
    * PROGRAM ID  : NpayVO
    * PACKAGE NM : com.gxenSoft.mall.order.vo
    * AUTHOR	 : 강병철
    * CREATED DATE  : 2018. 06. 12. 
    * HISTORY :    배송정책정보
    *
    *************************************
    */	
@XmlRootElement(name = "shippingPolicy")
public class NpayShippingPolicyVO {

	private String groupId = "";    									// 필수(N) , 배송비묵음그룹ID  . 배송비 묶음 그룹 ID 가 동일한 상품의 배송비 금 액이 다른 경우에는 배송비 금액이 가장 작은 상품의 정책을 적용한다.	이 값을 입력하지 않으면 상품별로 배송비를 개별 청구한다. 
	private String method = "DELIVERY";	                    // N,  배송 방법. 기본값은 ‘택배·소포·등기’   택배·소포·등기: DELIVERY  퀵 서비스: QUICK_SVC  직접 전달: DIRECT_DELIVERY  방문 수령: VISIT_RECEIPT  배송 없음: NOTHING  
	private String feeType = "CONDITIONAL_FREE";					                     // Y,배송비 유형  무료: FREE  유료: CHARGE  조건부무료: CONDITIONAL_FREE  수량별부과: CHARGE_BY_QUANTITY	 
	private int feePrice  = 0;								         	//  Y 기본 배송비. 0 이상 200,000 이하만 입력 가능.	배송비 유형이 ‘무료’이거나 배송비 결제 방법이 ‘착불’인 경우에만 ‘0’ 을 입력할 수 있다(배송비 결제 방법이 ‘착불’이고 배송비 액수를 미리 산정할 수 없는 경우 ‘0’ 입력). 그 외에는 모두 0 보다 큰 값을 입력해 야 한다.
	private String feePayType ="FREE";                      // Y 배송비 결제 방법.   무료: FREE  선불: PREPAYED  착불: CASH_ON_DELIVERY 배송비 유형이 ‘무료’이면 배송비 결제 방법에 ‘FREE‘를 입력한 다.
	
	private NpayShippingConditionalFree conditionalFree ; // 무료조건
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public int getFeePrice() {
		return feePrice;
	}
	public void setFeePrice(int feePrice) {
		this.feePrice = feePrice;
	}
	public String getFeePayType() {
		return feePayType;
	}
	public void setFeePayType(String feePayType) {
		this.feePayType = feePayType;
	}
	public NpayShippingConditionalFree getConditionalFree() {
		return conditionalFree;
	}
	public void setConditionalFree(NpayShippingConditionalFree conditionalFree) {
		this.conditionalFree = conditionalFree;
	}
   
		
}

