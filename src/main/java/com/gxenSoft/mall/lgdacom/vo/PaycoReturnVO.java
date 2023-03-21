package com.gxenSoft.mall.lgdacom.vo;

import org.apache.commons.lang.StringUtils;

public class PaycoReturnVO {
	//https://222.112.106.57:8443/w/payco/paycoReturn.do?code=0&reserveOrderNo=201707202004552992&sellerOrderReferenceKey=TEST2257686654&mainPgCode=31&totalPaymentAmt=81500&totalRemoteAreaDeliveryFeeAmt=0&discountAmt=2000&pointAmt=0&paymentCertifyToken=Eh3BHs_Eo77l_Uzq7KKs1jv3numK3LR_GN3xgCI3p4ch7iA6S%2FOxUZa9X3jSTvQd&taxationType=TAXATION&totalTaxfreeAmt=0&totalTaxableAmt=74091&totalVatAmt=7409&bid=R5P2333SI4AFEBB416EN7QCCI

	private	String code = "";                                                        //결과코드(성공 : 0)
	private String message = "";                                                   //결과메시지
	
	/* 인증 데이타 변수선언 */
	private	String reserveOrderNo = ""; 											//주문예약번호
	private	String sellerOrderReferenceKey = ""; 								//가맹점주문연동키
	private	String paymentCertifyToken = "";								    //결제인증토큰(결제승인시필요)  Eh3BHs_Eo77l_Uzq7KKs1jv3numK3LR_GN3xgCI3p4ch7iA6S%2FOxUZa9X3jSTvQd
	private	String totalPaymentAmt = "";                                      //총 결제금액
	
	private String sessionId = "";
	
	public String getCode() {
		return StringUtils.defaultString(code);
	}
	public void setCode(String code) {
		this.code = code;
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
	public String getMessage() {
		return StringUtils.defaultString(message);
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPaymentCertifyToken() {
		return StringUtils.defaultString(paymentCertifyToken);
	}
	public void setPaymentCertifyToken(String paymentCertifyToken) {
		this.paymentCertifyToken = paymentCertifyToken;
	}
	public String getTotalPaymentAmt() {
		return StringUtils.defaultString(totalPaymentAmt);
	}
	public void setTotalPaymentAmt(String totalPaymentAmt) {
		this.totalPaymentAmt = totalPaymentAmt;
	}
	public String getSessionId() {
		return StringUtils.defaultString(sessionId);
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	

}
