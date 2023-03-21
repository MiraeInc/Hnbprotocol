package com.gxenSoft.mall.lgdacom.vo;

import java.util.List;

import org.apache.commons.lang.StringUtils;

public class PaycoApprovalVO {
	private Integer 	approvalIdx;
	private Integer 	orderIdx;
	private String 	sellerKey="";
	private String 	reserveOrderNo="";
	private String 	orderNo="";
	private String 	sellerOrderReferenceKey="";
	private String 	orderCertifyKey="";
	private String 	memberName="";
	private String 	memberEmail="";
	private String 	orderChannel="";
	private Double 	totalOrderAmt;
	private Double 	totalDeliveryFeeAmt;
	private Double 	totalRemoteAreaDeliveryFeeAmt;
	private Double 	totalPaymentAmt;
	private Double 	receiptPaycoPointAmt;
	private Double 	receiptPaycoPointTaxfreeAmt;
	private Double 	receiptPaycoPointTaxableAmt;
	private Double 	receiptPaycoPointVatAmt;
	private Double 	receiptPaycoPointServiceAmt;
	private String 	paymentCompletionYn="";
	private String 	paymentCompleteYmdt="";
	private String 	orderProducts="";
	
	private List<PaycoPaymentVO> paymentDetails ;
	
	public Integer getApprovalIdx() {
		return approvalIdx;
	}
	public void setApprovalIdx(Integer approvalIdx) {
		this.approvalIdx = approvalIdx;
	}
	public Integer getOrderIdx() {
		return orderIdx;
	}
	public void setOrderIdx(Integer orderIdx) {
		this.orderIdx = orderIdx;
	}
	public String getSellerKey() {
		return StringUtils.defaultString(sellerKey);
	}
	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}
	public String getReserveOrderNo() {
		return StringUtils.defaultString(reserveOrderNo);
	}
	public void setReserveOrderNo(String reserveOrderNo) {
		this.reserveOrderNo = reserveOrderNo;
	}
	public String getOrderNo() {
		return StringUtils.defaultString(orderNo);
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getSellerOrderReferenceKey() {
		return StringUtils.defaultString(sellerOrderReferenceKey);
	}
	public void setSellerOrderReferenceKey(String sellerOrderReferenceKey) {
		this.sellerOrderReferenceKey = sellerOrderReferenceKey;
	}
	public String getOrderCertifyKey() {
		return StringUtils.defaultString(orderCertifyKey);
	}
	public void setOrderCertifyKey(String orderCertifyKey) {
		this.orderCertifyKey = orderCertifyKey;
	}
	public String getMemberName() {
		return StringUtils.defaultString(memberName);
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberEmail() {
		return StringUtils.defaultString(memberEmail);
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	public String getOrderChannel() {
		return StringUtils.defaultString(orderChannel);
	}
	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}
	public Double getTotalOrderAmt() {
		return totalOrderAmt;
	}
	public void setTotalOrderAmt(Double totalOrderAmt) {
		this.totalOrderAmt = totalOrderAmt;
	}
	public Double getTotalDeliveryFeeAmt() {
		return totalDeliveryFeeAmt;
	}
	public void setTotalDeliveryFeeAmt(Double totalDeliveryFeeAmt) {
		this.totalDeliveryFeeAmt = totalDeliveryFeeAmt;
	}
	public Double getTotalRemoteAreaDeliveryFeeAmt() {
		return totalRemoteAreaDeliveryFeeAmt;
	}
	public void setTotalRemoteAreaDeliveryFeeAmt(Double totalRemoteAreaDeliveryFeeAmt) {
		this.totalRemoteAreaDeliveryFeeAmt = totalRemoteAreaDeliveryFeeAmt;
	}
	public Double getTotalPaymentAmt() {
		return totalPaymentAmt;
	}
	public void setTotalPaymentAmt(Double totalPaymentAmt) {
		this.totalPaymentAmt = totalPaymentAmt;
	}
	public Double getReceiptPaycoPointAmt() {
		return receiptPaycoPointAmt;
	}
	public void setReceiptPaycoPointAmt(Double receiptPaycoPointAmt) {
		this.receiptPaycoPointAmt = receiptPaycoPointAmt;
	}
	public Double getReceiptPaycoPointTaxfreeAmt() {
		return receiptPaycoPointTaxfreeAmt;
	}
	public void setReceiptPaycoPointTaxfreeAmt(Double receiptPaycoPointTaxfreeAmt) {
		this.receiptPaycoPointTaxfreeAmt = receiptPaycoPointTaxfreeAmt;
	}
	public Double getReceiptPaycoPointTaxableAmt() {
		return receiptPaycoPointTaxableAmt;
	}
	public void setReceiptPaycoPointTaxableAmt(Double receiptPaycoPointTaxableAmt) {
		this.receiptPaycoPointTaxableAmt = receiptPaycoPointTaxableAmt;
	}
	public Double getReceiptPaycoPointVatAmt() {
		return receiptPaycoPointVatAmt;
	}
	public void setReceiptPaycoPointVatAmt(Double receiptPaycoPointVatAmt) {
		this.receiptPaycoPointVatAmt = receiptPaycoPointVatAmt;
	}
	public Double getReceiptPaycoPointServiceAmt() {
		return receiptPaycoPointServiceAmt;
	}
	public void setReceiptPaycoPointServiceAmt(Double receiptPaycoPointServiceAmt) {
		this.receiptPaycoPointServiceAmt = receiptPaycoPointServiceAmt;
	}
	public String getPaymentCompletionYn() {
		return StringUtils.defaultString(paymentCompletionYn);
	}
	public void setPaymentCompletionYn(String paymentCompletionYn) {
		this.paymentCompletionYn = paymentCompletionYn;
	}
	public String getPaymentCompleteYmdt() {
		return StringUtils.defaultString(paymentCompleteYmdt);
	}
	public void setPaymentCompleteYmdt(String paymentCompleteYmdt) {
		this.paymentCompleteYmdt = paymentCompleteYmdt;
	}
	public String getOrderProducts() {
		return StringUtils.defaultString(orderProducts);
	}
	public void setOrderProducts(String orderProducts) {
		this.orderProducts = orderProducts;
	}
	public List<PaycoPaymentVO> getPaymentDetails() {
		return paymentDetails;
	}
	public void setPaymentDetails(List<PaycoPaymentVO> paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

}
