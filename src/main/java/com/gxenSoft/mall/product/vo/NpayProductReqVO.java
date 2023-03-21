package com.gxenSoft.mall.product.vo;

/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : NpayVO
    * PACKAGE NM : com.gxenSoft.mall.order.vo
    * AUTHOR	 : 강병철
    * CREATED DATE  : 2018. 06. 12. 
    * HISTORY :   Naver pay 상품정보 요청 
    *
    *************************************
    */	
public class NpayProductReqVO {

	private String id = "";												// 필수(Y) 조회하려는 상품 번호. 상품 번호는 여러 개를 설정할 수 있고 product[0][id]=XXX&product[1][id]=XX… 형식으로 복수 건 추가 가 능. 
	private String ecmallproductId = null;					    // N,    조회하려는 해당 가맹점 상품 번호. 여러 개를 설정할 수 있고, 상품 번호가 없을 경우 해당 항목이 필수 값이 된다. product[0][ecmallproductId]=XXX&product[1][ecmallproductId]=XXX.. 형식으로 복수 건 추가 가능.  
	private String optionManageCodes  = null;				// N,  상품 조합 옵션 관리 코드. product[순번][id]에 종속돼 있는 상품 조 합 옵션 관리 코드를 콤마(,)로 구분하여 넣을 수 있다. 
	private String supplementIds = null;						// N,  추가 구성 상품 코드. product[순번][id]에 종속돼 있는 추가 구성 상 품 코드를 콤마(,)로 구분하여 넣을 수 있다. 

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEcmallproductId() {
		return ecmallproductId;
	}
	public void setEcmallproductId(String ecmallproductId) {
		this.ecmallproductId = ecmallproductId;
	}
	public String getOptionManageCodes() {
		return optionManageCodes;
	}
	public void setOptionManageCodes(String optionManageCodes) {
		this.optionManageCodes = optionManageCodes;
	}
	public String getSupplementIds() {
		return supplementIds;
	}
	public void setSupplementIds(String supplementIds) {
		this.supplementIds = supplementIds;
	}

}

