package com.gxenSoft.mall.order.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
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
@XmlRootElement(name = "order")
public class NpayOrderVO {

	private String merchantId = "";					// 상점ID (네이버제공)
	private String certiKey = "";					// 인증키 (네이버제공)
	private List<NpayProductVO> product = null;					// 주문상품
	
	private NpayOrderInterfaceVO interFace = null;					
	private String backUrl = "";								// 네이버페이 주문서 페이지에서 [이전페이지]를 클릭했을 때 이동하는 페이지 URL. 상점 메인 화면으로 돌아가거나, 주문을 따로 저장했다면 주문서 페이지로 돌아가게 할 수 있다.
	
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getCertiKey() {
		return certiKey;
	}
	public void setCertiKey(String certiKey) {
		this.certiKey = certiKey;
	}
	public List<NpayProductVO> getProduct() {
		return product;
	}
	public void setProduct(List<NpayProductVO> product) {
		this.product = product;
	}
	public String getBackUrl() {
		return backUrl;
	}
	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}
	@XmlElement(name = "interface")
	public NpayOrderInterfaceVO getInterFace() {
		return interFace;
	}
	public void setInterFace(NpayOrderInterfaceVO interFace) {
		this.interFace = interFace;
	}
	
}

