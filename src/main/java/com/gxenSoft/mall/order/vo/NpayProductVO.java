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
@XmlRootElement(name = "product")
public class NpayProductVO {

	private String id = "";													// 필수(Y) 상품번호(최대30자) 영문숫자,(!+-/=_)만사용, 공백허용안함
	private String merchantProductId = null;					// N,  판매자 상품 번호. 최대 100 자. 상품 번호 외에 가맹점 내부적으로 상 품 단위 관리 코드가 별도로 필요한 경우에 입력한다.	영문자 및 숫자, 일부 특수문자(!+-/=_|)만 사용할 수 있다. 공백은 사용 할 수 없다. 
	private String ecMallProductId = null;					        // N, 지식쇼핑 EP의 mall_pid. 지식쇼핑 가맹점이면 지식쇼핑 EP 의 mall_pid 와 동일한 값을 입력해야 한다. 
	private String name  = "";											// Y, 상품명. 최대100자
	private int  basePrice ;											// Y, 1개의 가격, 1이상값 
	private String taxType = "TAX";									// N, 상품의 과세종류. 입력이 없으면 과세. 네이버 결제 시만 적용됨 (과세: TAX , 면세 TAX_FREE, 영세 ZERO_TAX)
	private String infoUrl = "";											// Y, 상품상세페이지 URL
	private String imageUrl="";										//Y, 상품원본이미지URL
	private String giftName= null;										//N, 사은품명 최대 200자
    private NpayProductSingleVO single=null;	                            // 	N, 단일 상품 주문 수량. 단일 상품(옵션이없는상품일경우)이면 필수. 1 이상만 입력 가능.	옵션 상품이면 옵션 상품 주문 수량에 주문 수량을 입력한다. 자세한 내용은 "표 3-3 구매 정보 연동 - option 상세 내용"을 참조한다.
    private NpayShippingPolicyVO shippingPolicy;   //Y, 배송비정책정보.  상품마다 입력해야 한다. 본상품의 하위 옵션 및 추 가 상품에는 모두 본상품 배송비 정책 정보가 동일하게 적용된다
    
    //상품정보요청시 사용하는 정보
    private Integer stockQuantity=null;                             // N, 재고수량 - 상품정보요청에서 사용 (주문시에는 사용X) (0:품절로 구매불가, 1이상 : 주문수량비교, 입력하지않으면 주문시 재고를 확인하지 않는다.) 옵션 상품이고 옵션 관리 코드가 존재하면 본상품 재고 수량은 값을 입력해도 사용하지 않는다. 
    private String status = null;									// N, 본상품의 거래 상태, 기본값은 판매중 (ON_SALE : 판매중, SOLD_OUT : 품절, NOT_SALE : 구매불가)
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMerchantProductId() {
		return merchantProductId;
	}
	public void setMerchantProductId(String merchantProductId) {
		this.merchantProductId = merchantProductId;
	}
	public String getEcMallProductId() {
		return ecMallProductId;
	}
	public void setEcMallProductId(String ecMallProductId) {
		this.ecMallProductId = ecMallProductId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(int basePrice) {
		this.basePrice = basePrice;
	}
	public String getTaxType() {
		return taxType;
	}
	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}
	public String getInfoUrl() {
		return infoUrl;
	}
	public void setInfoUrl(String infoUrl) {
		this.infoUrl = infoUrl;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getGiftName() {
		return giftName;
	}
	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
	public NpayProductSingleVO getSingle() {
		return single;
	}
	public void setSingle(NpayProductSingleVO single) {
		this.single = single;
	}
	public NpayShippingPolicyVO getShippingPolicy() {
		return shippingPolicy;
	}
	public void setShippingPolicy(NpayShippingPolicyVO shippingPolicy) {
		this.shippingPolicy = shippingPolicy;
	}
	public Integer getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
		
}

