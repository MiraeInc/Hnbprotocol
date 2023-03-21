package com.gxenSoft.mall.order.vo;

   /**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : WonderAuthReqVO
    * PACKAGE NM : com.gxenSoft.mall.order.vo
    * AUTHOR	 : 강 병 철
    * CREATED DATE  : 2018. 11. 02. 
    * HISTORY :    원더페이 인증요청 
    *
    *************************************
    */	
public class WonderAuthReqVO {

	private String callUrl = ""; //호출할URL // https://pay-stg.wonder-pay.com ,  https://pay.wonder-pay.com 
	
	private String siteCd = "";  
	private String goodName = "";
	private String goodMny = ""; 
	private String buyrName = "";
	private String buyrMail = ""; 
	private String buyrTel2 = "";  //주문자모바일번호
	private String retUrl = "";
	private String ordrIdxx = "";  //주문번호
	private String ordrInfo = "";  //site_cd + ordr_idxx + good_mny + buyr_mail + buyr_tel2	의 md5 값
	private String buyrTel1  = "";  //주문자전화번호
	
	public String getCallUrl() {
		return callUrl;
	}
	public void setCallUrl(String callUrl) {
		this.callUrl = callUrl;
	}
	public String getSiteCd() {
		return siteCd;
	}
	public void setSiteCd(String siteCd) {
		this.siteCd = siteCd;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public String getGoodMny() {
		return goodMny;
	}
	public void setGoodMny(String goodMny) {
		this.goodMny = goodMny;
	}
	public String getBuyrName() {
		return buyrName;
	}
	public void setBuyrName(String buyrName) {
		this.buyrName = buyrName;
	}
	public String getBuyrMail() {
		return buyrMail;
	}
	public void setBuyrMail(String buyrMail) {
		this.buyrMail = buyrMail;
	}
	public String getBuyrTel2() {
		return buyrTel2;
	}
	public void setBuyrTel2(String buyrTel2) {
		this.buyrTel2 = buyrTel2;
	}
	public String getRetUrl() {
		return retUrl;
	}
	public void setRetUrl(String retUrl) {
		this.retUrl = retUrl;
	}
	public String getOrdrIdxx() {
		return ordrIdxx;
	}
	public void setOrdrIdxx(String ordrIdxx) {
		this.ordrIdxx = ordrIdxx;
	}
	public String getOrdrInfo() {
		return ordrInfo;
	}
	public void setOrdrInfo(String ordrInfo) {
		this.ordrInfo = ordrInfo;
	}
	public String getBuyrTel1() {
		return buyrTel1;
	}
	public void setBuyrTel1(String buyrTel1) {
		this.buyrTel1 = buyrTel1;
	}
		

}
