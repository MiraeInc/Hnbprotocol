package com.gxenSoft.mall.lgdacom.vo;

import org.apache.commons.lang.StringUtils;

public class PaycoPaymentVO {
	private Integer paymentIdx;
	private Integer approvalIdx;
	private Integer 	orderIdx;
	private String paymentTradeNo;
	private String paymentMethodCode;
	private String paymentMethodName;
	private Double paymentAmt;
	private String tradeYmdt;
	private String pgAdmissionNo;
	private String pgAdmissionYmdt;
	private String easyPaymentYn;
	private String cardCompanyName;
	private String cardCompanyCode;
	private String cardNo;
	private String cardInstallmentMonthNumber;
	private String cardAdmissionNo;
	private String cardInterestFreeYn;
	private String corporateCardYn;
	private String partCancelPossibleYn;
	private String companyName;
	private String cellphoneNo;
	private String bankName;
	private String bankCode;
	private String accountNo;
	private String paymentExpirationYmd;
	private String discountAmt;
	private String discountConditionAmt;
	
	public Integer getPaymentIdx() {
		return paymentIdx;
	}
	public void setPaymentIdx(Integer paymentIdx) {
		this.paymentIdx = paymentIdx;
	}
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
	public String getPaymentTradeNo() {
		return StringUtils.defaultString(paymentTradeNo);
	}
	public void setPaymentTradeNo(String paymentTradeNo) {
		this.paymentTradeNo = paymentTradeNo;
	}
	public String getPaymentMethodCode() {
		return StringUtils.defaultString(paymentMethodCode);
	}
	public void setPaymentMethodCode(String paymentMethodCode) {
		this.paymentMethodCode = paymentMethodCode;
	}
	public String getPaymentMethodName() {
		return StringUtils.defaultString(paymentMethodName);
	}
	public void setPaymentMethodName(String paymentMethodName) {
		this.paymentMethodName = paymentMethodName;
	}
	public Double getPaymentAmt() {
		return paymentAmt;
	}
	public void setPaymentAmt(Double paymentAmt) {
		this.paymentAmt = paymentAmt;
	}
	public String getTradeYmdt() {
		return StringUtils.defaultString(tradeYmdt);
	}
	public void setTradeYmdt(String tradeYmdt) {
		this.tradeYmdt = tradeYmdt;
	}
	public String getPgAdmissionNo() {
		return StringUtils.defaultString(pgAdmissionNo);
	}
	public void setPgAdmissionNo(String pgAdmissionNo) {
		this.pgAdmissionNo = pgAdmissionNo;
	}
	public String getPgAdmissionYmdt() {
		return StringUtils.defaultString(pgAdmissionYmdt);
	}
	public void setPgAdmissionYmdt(String pgAdmissionYmdt) {
		this.pgAdmissionYmdt = pgAdmissionYmdt;
	}
	public String getEasyPaymentYn() {
		return StringUtils.defaultString(easyPaymentYn);
	}
	public void setEasyPaymentYn(String easyPaymentYn) {
		this.easyPaymentYn = easyPaymentYn;
	}
	public String getCardCompanyName() {
		return StringUtils.defaultString(cardCompanyName);
	}
	public void setCardCompanyName(String cardCompanyName) {
		this.cardCompanyName = cardCompanyName;
	}
	public String getCardCompanyCode() {
		return StringUtils.defaultString(cardCompanyCode);
	}
	public void setCardCompanyCode(String cardCompanyCode) {
		this.cardCompanyCode = cardCompanyCode;
	}
	public String getCardNo() {
		return StringUtils.defaultString(cardNo);
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardInstallmentMonthNumber() {
		return StringUtils.defaultString(cardInstallmentMonthNumber);
	}
	public void setCardInstallmentMonthNumber(String cardInstallmentMonthNumber) {
		this.cardInstallmentMonthNumber = cardInstallmentMonthNumber;
	}
	public String getCardAdmissionNo() {
		return StringUtils.defaultString(cardAdmissionNo);
	}
	public void setCardAdmissionNo(String cardAdmissionNo) {
		this.cardAdmissionNo = cardAdmissionNo;
	}
	public String getCardInterestFreeYn() {
		return StringUtils.defaultString(cardInterestFreeYn);
	}
	public void setCardInterestFreeYn(String cardInterestFreeYn) {
		this.cardInterestFreeYn = cardInterestFreeYn;
	}
	public String getCorporateCardYn() {
		return StringUtils.defaultString(corporateCardYn);
	}
	public void setCorporateCardYn(String corporateCardYn) {
		this.corporateCardYn = corporateCardYn;
	}
	public String getPartCancelPossibleYn() {
		return StringUtils.defaultString(partCancelPossibleYn);
	}
	public void setPartCancelPossibleYn(String partCancelPossibleYn) {
		this.partCancelPossibleYn = partCancelPossibleYn;
	}
	public String getCompanyName() {
		return StringUtils.defaultString(companyName);
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCellphoneNo() {
		return StringUtils.defaultString(cellphoneNo);
	}
	public void setCellphoneNo(String cellphoneNo) {
		this.cellphoneNo = cellphoneNo;
	}
	public String getBankName() {
		return StringUtils.defaultString(bankName);
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankCode() {
		return StringUtils.defaultString(bankCode);
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getAccountNo() {
		return StringUtils.defaultString(accountNo);
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getPaymentExpirationYmd() {
		return StringUtils.defaultString(paymentExpirationYmd);
	}
	public void setPaymentExpirationYmd(String paymentExpirationYmd) {
		this.paymentExpirationYmd = paymentExpirationYmd;
	}
	public String getDiscountAmt() {
		return discountAmt;
	}
	public void setDiscountAmt(String discountAmt) {
		this.discountAmt = discountAmt;
	}
	public String getDiscountConditionAmt() {
		return discountConditionAmt;
	}
	public void setDiscountConditionAmt(String discountConditionAmt) {
		this.discountConditionAmt = discountConditionAmt;
	}

}
