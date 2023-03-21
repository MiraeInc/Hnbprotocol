package com.gxenSoft.mall.lgdacom.vo;

import org.apache.commons.lang.StringUtils;

public class PaycoCompletlVO {

	private String resultMessage="";
	
	private	String orderNo = "";                                               
	private String sellerOrderProductReferenceKey = "";                                                   
	
	private	String orderCertifyKey = ""; 											
	private	String totalCancelTaxfreeAmt = "";			
	private String totalCancelTaxableAmt = "";
	
	private	String totalCancelVatAmt = "";  	
	private	String totalCancelPossibleAmt = "";                       
	private	String cancelTotalAmt = "";
	private	String requestMemo = "";
	
	private	String cancelDetailContent = "";
	private	String reserveOrderNo = "";
	private	String sellerOrderReferenceKey = "";
	
	public String getOrderNo() {
		return StringUtils.defaultString(orderNo);
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getSellerOrderProductReferenceKey() {
		return StringUtils.defaultString(sellerOrderProductReferenceKey);
	}
	public void setSellerOrderProductReferenceKey(String sellerOrderProductReferenceKey) {
		this.sellerOrderProductReferenceKey = sellerOrderProductReferenceKey;
	}
	public String getOrderCertifyKey() {
		return StringUtils.defaultString(orderCertifyKey);
	}
	public void setOrderCertifyKey(String orderCertifyKey) {
		this.orderCertifyKey = orderCertifyKey;
	}
	public String getTotalCancelTaxfreeAmt() {
		return StringUtils.defaultString(totalCancelTaxfreeAmt);
	}
	public void setTotalCancelTaxfreeAmt(String totalCancelTaxfreeAmt) {
		this.totalCancelTaxfreeAmt = totalCancelTaxfreeAmt;
	}
	public String getTotalCancelTaxableAmt() {
		return StringUtils.defaultString(totalCancelTaxableAmt);
	}
	public void setTotalCancelTaxableAmt(String totalCancelTaxableAmt) {
		this.totalCancelTaxableAmt = totalCancelTaxableAmt;
	}
	public String getTotalCancelVatAmt() {
		return StringUtils.defaultString(totalCancelVatAmt);
	}
	public void setTotalCancelVatAmt(String totalCancelVatAmt) {
		this.totalCancelVatAmt = totalCancelVatAmt;
	}
	public String getTotalCancelPossibleAmt() {
		return StringUtils.defaultString(totalCancelPossibleAmt);
	}
	public void setTotalCancelPossibleAmt(String totalCancelPossibleAmt) {
		this.totalCancelPossibleAmt = totalCancelPossibleAmt;
	}
	public String getCancelTotalAmt() {
		return StringUtils.defaultString(cancelTotalAmt);
	}
	public void setCancelTotalAmt(String cancelTotalAmt) {
		this.cancelTotalAmt = cancelTotalAmt;
	}
	public String getRequestMemo() {
		return StringUtils.defaultString(requestMemo);
	}
	public void setRequestMemo(String requestMemo) {
		this.requestMemo = requestMemo;
	}
	public String getCancelDetailContent() {
		return StringUtils.defaultString(cancelDetailContent);
	}
	public void setCancelDetailContent(String cancelDetailContent) {
		this.cancelDetailContent = cancelDetailContent;
	}
	public String getReserveOrderNo() {
		return StringUtils.defaultString(reserveOrderNo);
	}
	public void setReserveOrderNo(String reserveOrderNo) {
		this.reserveOrderNo = reserveOrderNo;
	}
	public String getSellerOrderReferenceKey() {
		return StringUtils.defaultString(sellerOrderReferenceKey);
	}
	public void setSellerOrderReferenceKey(String sellerOrderReferenceKey) {
		this.sellerOrderReferenceKey = sellerOrderReferenceKey;
	}
	public String getResultMessage() {
		return StringUtils.defaultString(resultMessage);
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	
	

}
