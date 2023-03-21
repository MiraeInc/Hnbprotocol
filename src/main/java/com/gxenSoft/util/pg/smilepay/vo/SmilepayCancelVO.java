package com.gxenSoft.util.pg.smilepay.vo;

import org.apache.commons.lang.StringUtils;

/**
   *************************************
   * PROJECT   : GatsbyMall
   * PROGRAM ID  : SmilepayCancelVO
   * PACKAGE NM : com.gxenSoft.util.pg.smilepay.vo
   * AUTHOR	 : 김 민 수
   * CREATED DATE  : 2017. 12. 8. 
   * HISTORY :   
   *
   *************************************
 **/	
public class SmilepayCancelVO {
	
	private String orderCd;				// 주문 마스터 테이블 주문코드값
	private String autoFlag;				// 수동 주문 취소 구분값
	
	//취소 요청 파라미터
	private String supplyAmt;			//공급가액			
	private String goodsVat;				//부가세
	private String serviceAmt;			//봉사료
	private String taxationAmt;			//과세금액
	private String cancelMsg;			//취소사유
	private String partialCancelCode;	//부분취소여부
	private String cancelIP;				//취소 요청자 IP
	private String cancelNo;				//취소번호
	private String checkRemainAmt;	//부분취소 가능잔액
	private String preCancelCode; 		//취소종류
		
	//취소 후 응답 파라미터
	private String resultCode;		//결과코드 (정상 :2001(취소성공), 그 외 에러)
	private String resultMsg;			//결과 메세지
	private String errorCd;			//원천사 오류코드 참조용
	private String errorMsg;			//원천사 오류 메세지 참조용
	private String encryptData;		// 해쉬값
	private String cancelAmt;		//취소금액
	private String cancelDate;		//취소일
	private String cancelTime;		//취소시간
	private String cancelNum;		//취소번호
	private String payMethod;		//취소수단
	private String mid;					//MID 가맹점ID
	private String tid;					//TID 거래ID
	private String authDate;			//취소거래시간
	private String stateCd;			//거래상태코드
	private String vanCode;			//van코드
	private String ediDate;
	
	
	public String getAutoFlag() {
		return StringUtils.defaultString(autoFlag);
	}
	public void setAutoFlag(String autoFlag) {
		this.autoFlag = autoFlag;
	}
	public String getOrderCd() {
		return StringUtils.defaultString(orderCd);
	}
	public void setOrderCd(String orderCd) {
		this.orderCd = orderCd;
	}
	public String getEdiDate() {
		return StringUtils.defaultString(ediDate);
	}
	public void setEdiDate(String ediDate) {
		this.ediDate = ediDate;
	}
	public String getSupplyAmt() {
		return StringUtils.defaultString(supplyAmt);
	}
	public void setSupplyAmt(String supplyAmt) {
		this.supplyAmt = supplyAmt;
	}
	public String getGoodsVat() {
		return StringUtils.defaultString(goodsVat);
	}
	public void setGoodsVat(String goodsVat) {
		this.goodsVat = goodsVat;
	}
	public String getServiceAmt() {
		return StringUtils.defaultString(serviceAmt);
	}
	public void setServiceAmt(String serviceAmt) {
		this.serviceAmt = serviceAmt;
	}
	public String getTaxationAmt() {
		return StringUtils.defaultString(taxationAmt);
	}
	public void setTaxationAmt(String taxationAmt) {
		this.taxationAmt = taxationAmt;
	}
	public String getCancelMsg() {
		return StringUtils.defaultString(cancelMsg);
	}
	public void setCancelMsg(String cancelMsg) {
		this.cancelMsg = cancelMsg;
	}
	public String getPartialCancelCode() {
		return StringUtils.defaultString(partialCancelCode);
	}
	public void setPartialCancelCode(String partialCancelCode) {
		this.partialCancelCode = partialCancelCode;
	}
	public String getCancelIP() {
		return StringUtils.defaultString(cancelIP);
	}
	public void setCancelIP(String cancelIP) {
		this.cancelIP = cancelIP;
	}
	public String getCancelNo() {
		return StringUtils.defaultString(cancelNo);
	}
	public void setCancelNo(String cancelNo) {
		this.cancelNo = cancelNo;
	}
	public String getCheckRemainAmt() {
		return StringUtils.defaultString(checkRemainAmt);
	}
	public void setCheckRemainAmt(String checkRemainAmt) {
		this.checkRemainAmt = checkRemainAmt;
	}
	public String getPreCancelCode() {
		return StringUtils.defaultString(preCancelCode);
	}
	public void setPreCancelCode(String preCancelCode) {
		this.preCancelCode = preCancelCode;
	}
	public String getResultCode() {
		return StringUtils.defaultString(resultCode);
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMsg() {
		return StringUtils.defaultString(resultMsg);
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public String getErrorCd() {
		return StringUtils.defaultString(errorCd);
	}
	public void setErrorCd(String errorCd) {
		this.errorCd = errorCd;
	}
	public String getErrorMsg() {
		return StringUtils.defaultString(errorMsg);
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getEncryptData() {
		return StringUtils.defaultString(encryptData);
	}
	public void setEncryptData(String encryptData) {
		this.encryptData = encryptData;
	}
	public String getCancelAmt() {
		return StringUtils.defaultString(cancelAmt);
	}
	public void setCancelAmt(String cancelAmt) {
		this.cancelAmt = cancelAmt;
	}
	public String getCancelDate() {
		return StringUtils.defaultString(cancelDate);
	}
	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}
	public String getCancelTime() {
		return StringUtils.defaultString(cancelTime);
	}
	public void setCancelTime(String cancelTime) {
		this.cancelTime = cancelTime;
	}
	public String getCancelNum() {
		return StringUtils.defaultString(cancelNum);
	}
	public void setCancelNum(String cancelNum) {
		this.cancelNum = cancelNum;
	}
	public String getPayMethod() {
		return StringUtils.defaultString(payMethod);
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public String getMid() {
		return StringUtils.defaultString(mid);
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getTid() {
		return StringUtils.defaultString(tid);
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getAuthDate() {
		return StringUtils.defaultString(authDate);
	}
	public void setAuthDate(String authDate) {
		this.authDate = authDate;
	}
	public String getStateCd() {
		return StringUtils.defaultString(stateCd);
	}
	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
	}
	public String getVanCode() {
		return StringUtils.defaultString(vanCode);
	}
	public void setVanCode(String vanCode) {
		this.vanCode = vanCode;
	}
}
