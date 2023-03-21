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
@XmlRootElement(name = "interface")
public class NpayOrderInterfaceVO {

 
	private String cpaInflowCode = "";			//  네이버쇼핑/사이트/키워드 등을 통해서 가맹점으로 유입될때 NaPm 파라미터에 의해 CPAValidator 쿠키가 생성됩니다. 쿠키에서 CPAValidator 에 해당하는 값을 전달
	private String naverInflowCode  = "";		//NaPm 파라미터에 의해 NA_CO 쿠키가 생성되고, 쿠키에서 NA_CO 에 해당하는 값을 전달
	private String saClickId   = "";		//검색서비스 관련 파라미터에 의해서 가맹점유입시 NVADID 쿠키가 생성되고, 쿠키에서 NVADID에 해당하는 값을 전달
	public String getCpaInflowCode() {
		return cpaInflowCode;
	}
	public void setCpaInflowCode(String cpaInflowCode) {
		this.cpaInflowCode = cpaInflowCode;
	}
	public String getNaverInflowCode() {
		return naverInflowCode;
	}
	public void setNaverInflowCode(String naverInflowCode) {
		this.naverInflowCode = naverInflowCode;
	}
	public String getSaClickId() {
		return saClickId;
	}
	public void setSaClickId(String saClickId) {
		this.saClickId = saClickId;
	}

}

