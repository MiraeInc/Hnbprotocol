package com.gxenSoft.mall.mypage.order.vo;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class ClaimVO {

	private Integer claimIdx;								// 클레임 마스터 일련번호
	private Integer orderIdx;								// 주문 마스터 일련번호 (TO_ORDER 일련번호)
	private String orderCd;									// 주문코드 (YYYYMMDDHHNN + [P|M|A] + 랜덤값5자리 (P : PC, M : MOBILE, A : APP))
	private String claimType;									// 클레임 구분	(C : 취소, X : 교환, R : 반품)
	private String device;										// 디바이스 (P : PC, M : MOBILE, A : APP)
	private Double restPayPrice;							// 잔여 결제금액 (최초 결제금액에서 매회 클레임 환불 금액을 빼고 남아있는 결제금액)
	private Double totalRefundCharge;					// 총 환불수수료
	private Double refundSubtraction;						// 환불차감금액
	private Double freeShippingCouponRefundPrice;	// 무료배송 쿠폰 환불 금액
	private Double totalGiftCouponRefundPrice;		// 총 상품 쿠폰 환불 금액
	private Double totalCartCouponRefundPrice;		// 총 장바구니 쿠폰 환불 금액
	private Double totalCouponRefundPrice;			// 총 쿠폰 환불 금액 (무료배송 쿠폰 환불 금액 + 총 상품 쿠폰 환불 금액 + 총 장바구니 쿠폰 환불 금액)
	private Double shippingRefundPrice;					// 배송비 환불 금액 (무료배송쿠폰이 적용되면 0)
	private Double totalPointRefundPrice;				// 포인트 결제 환불 금액
	private Double totalPrePointRefundPrice;			// 선포인트 결제 환불 금액
	private Double totalRefundPrice;						// 총 환불금액
	private Double totalRefundPoint;						// 총 환불 포인트
	private String refundMethod;							// 환불방식 (P : PG사 결제취소, A : 현금환불)
	private String refundBankCode;						// 환불 은행코드
	private String refundAccount;							// 환불 계좌번호
	private String refundDepositor;						// 환불 예금주명
	private String reasonCd;									// 사유코드 (공통코드)
	private String reason;										// 사유 상세
	private CommonsMultipartFile imgFile;				// 이미지 파일
	private Integer reqMemberIdx;						// 신청자 일련번호
	private String reqHttpUserAgent;						// 신청자 USER_AGENT
	private String reqIp;										// 신청자 IP
	private String reqDt;										// 신청일자
	private String pgCancelState;							// PG 승인취소 상태
	private String pgCancelFailCd;							// PG 승인취소 실패코드
	private String pgCancelFailMsg;						// PG 승인취소 실패메세지
	private String pgCancelDt;								// PG 승인취소일자
	private String pgCancelTransNo;						// PG 승인취소 거래번호
	private Double pgCancelPrice;							// PG 승인취소 신청금액
	private Double pgCancelBalance;						// PG 승인취소 잔여금액
	private String refundState;								// 환불 상태 (E : 처리 완료)
	private Integer refundMemberIdx;					// 환불 처리자 일련번호
	private String refundHttpUserAgent;					// 환불 처리자 USER_AGENT
	private String refundIp;									// 환불 처리자 IP
	private String refundDt;									// 환불 처리일자
	private Double refundTaxSupplyPrice;				// 환불 과세 공급가액
	private Double refundTaxFreeSupplyPrice;			// 환불 면세 공급가액
	private Double refundTaxVat;							// 환불 부가가치세
	private Integer regIdx;									// 작성자 일련번호
	private String regHttpUserAgent;						// 작성자 USER_AGENT
	private String regIp;										// 작성자 IP
	private String regDt;										// 작성일자
	private Integer editIdx;									// 수정자 일련번호
	private String editHttpUserAgent;						// 수정자 USER_AGENT
	private String editIp;										// 수정자 IP
	private String editDt;										// 수정일자

	private String saveRefundAccount;					// 환불 계좌 저장 여부 (Y : 저장, N : 저장 안 함)
	private Integer memberIdx;							// 회원 일련번호	

	// 비회원 주문 조회용
	private String nomemberOrderCd;						// 주문코드 (YYYYMMDDHHNN + [P|M|A] + 랜덤값5자리 (P : PC, M : MOBILE, A : APP))

	private String[] orderDetailIdxes;						// 선택한 주문 디테일 일련번호
	private String orderDetailIdxesStr;					// 선택한 주문 디테일 일련번호 JSON 문자열

	private String[] beforeOrderStatusCds;				// 이전 주문상태코드 배열

	private String returnUrl;									// 교환, 반품 신청 완료 후 돌아갈 URL (0 : 마이페이지 메인, 1 : 주문관리 리스트, 2 : 주문상세)
	
	public Integer getClaimIdx() {
		return claimIdx;
	}
	public void setClaimIdx(Integer claimIdx) {
		this.claimIdx = claimIdx;
	}
	public Integer getOrderIdx() {
		return orderIdx;
	}
	public void setOrderIdx(Integer orderIdx) {
		this.orderIdx = orderIdx;
	}
	public String getOrderCd() {
		return orderCd;
	}
	public void setOrderCd(String orderCd) {
		this.orderCd = orderCd;
	}
		public String getClaimType() {
		return claimType;
	}
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public Double getRestPayPrice() {
		return restPayPrice;
	}
	public void setRestPayPrice(Double restPayPrice) {
		this.restPayPrice = restPayPrice;
	}
	public Double getTotalRefundCharge() {
		return totalRefundCharge;
	}
	public void setTotalRefundCharge(Double totalRefundCharge) {
		this.totalRefundCharge = totalRefundCharge;
	}
	public Double getRefundSubtraction() {
		return refundSubtraction;
	}
	public void setRefundSubtraction(Double refundSubtraction) {
		this.refundSubtraction = refundSubtraction;
	}
	public Double getFreeShippingCouponRefundPrice() {
		return freeShippingCouponRefundPrice;
	}
	public void setFreeShippingCouponRefundPrice(Double freeShippingCouponRefundPrice) {
		this.freeShippingCouponRefundPrice = freeShippingCouponRefundPrice;
	}
	public Double getTotalGiftCouponRefundPrice() {
		return totalGiftCouponRefundPrice;
	}
	public void setTotalGiftCouponRefundPrice(Double totalGiftCouponRefundPrice) {
		this.totalGiftCouponRefundPrice = totalGiftCouponRefundPrice;
	}
	public Double getTotalCartCouponRefundPrice() {
		return totalCartCouponRefundPrice;
	}
	public void setTotalCartCouponRefundPrice(Double totalCartCouponRefundPrice) {
		this.totalCartCouponRefundPrice = totalCartCouponRefundPrice;
	}
	public Double getTotalCouponRefundPrice() {
		return totalCouponRefundPrice;
	}
	public void setTotalCouponRefundPrice(Double totalCouponRefundPrice) {
		this.totalCouponRefundPrice = totalCouponRefundPrice;
	}
	public Double getShippingRefundPrice() {
		return shippingRefundPrice;
	}
	public void setShippingRefundPrice(Double shippingRefundPrice) {
		this.shippingRefundPrice = shippingRefundPrice;
	}
	public Double getTotalPointRefundPrice() {
		return totalPointRefundPrice;
	}
	public void setTotalPointRefundPrice(Double totalPointRefundPrice) {
		this.totalPointRefundPrice = totalPointRefundPrice;
	}
	public Double getTotalPrePointRefundPrice() {
		return totalPrePointRefundPrice;
	}
	public void setTotalPrePointRefundPrice(Double totalPrePointRefundPrice) {
		this.totalPrePointRefundPrice = totalPrePointRefundPrice;
	}
	public Double getTotalRefundPrice() {
		return totalRefundPrice;
	}
	public void setTotalRefundPrice(Double totalRefundPrice) {
		this.totalRefundPrice = totalRefundPrice;
	}
	public Double getTotalRefundPoint() {
		return totalRefundPoint;
	}
	public void setTotalRefundPoint(Double totalRefundPoint) {
		this.totalRefundPoint = totalRefundPoint;
	}
	public String getRefundMethod() {
		return refundMethod;
	}
	public void setRefundMethod(String refundMethod) {
		this.refundMethod = refundMethod;
	}
	public String getRefundBankCode() {
		return refundBankCode;
	}
	public void setRefundBankCode(String refundBankCode) {
		this.refundBankCode = refundBankCode;
	}
	public String getRefundAccount() {
		return refundAccount;
	}
	public void setRefundAccount(String refundAccount) {
		this.refundAccount = refundAccount;
	}
	public String getRefundDepositor() {
		return refundDepositor;
	}
	public void setRefundDepositor(String refundDepositor) {
		this.refundDepositor = refundDepositor;
	}
	public String getReasonCd() {
		return reasonCd;
	}
	public void setReasonCd(String reasonCd) {
		this.reasonCd = reasonCd;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public CommonsMultipartFile getImgFile() {
		return imgFile;
	}
	public void setImgFile(CommonsMultipartFile imgFile) {
		this.imgFile = imgFile;
	}
	public Integer getReqMemberIdx() {
		return reqMemberIdx;
	}
	public void setReqMemberIdx(Integer reqMemberIdx) {
		this.reqMemberIdx = reqMemberIdx;
	}
	public String getReqHttpUserAgent() {
		return reqHttpUserAgent;
	}
	public void setReqHttpUserAgent(String reqHttpUserAgent) {
		this.reqHttpUserAgent = reqHttpUserAgent;
	}
	public String getReqIp() {
		return reqIp;
	}
	public void setReqIp(String reqIp) {
		this.reqIp = reqIp;
	}
	public String getReqDt() {
		return reqDt;
	}
	public void setReqDt(String reqDt) {
		this.reqDt = reqDt;
	}
	public String getPgCancelState() {
		return pgCancelState;
	}
	public void setPgCancelState(String pgCancelState) {
		this.pgCancelState = pgCancelState;
	}
	public String getPgCancelFailCd() {
		return pgCancelFailCd;
	}
	public void setPgCancelFailCd(String pgCancelFailCd) {
		this.pgCancelFailCd = pgCancelFailCd;
	}
	public String getPgCancelFailMsg() {
		return pgCancelFailMsg;
	}
	public void setPgCancelFailMsg(String pgCancelFailMsg) {
		this.pgCancelFailMsg = pgCancelFailMsg;
	}
	public String getPgCancelDt() {
		return pgCancelDt;
	}
	public void setPgCancelDt(String pgCancelDt) {
		this.pgCancelDt = pgCancelDt;
	}
	public String getPgCancelTransNo() {
		return pgCancelTransNo;
	}
	public void setPgCancelTransNo(String pgCancelTransNo) {
		this.pgCancelTransNo = pgCancelTransNo;
	}
	public Double getPgCancelPrice() {
		return pgCancelPrice;
	}
	public void setPgCancelPrice(Double pgCancelPrice) {
		this.pgCancelPrice = pgCancelPrice;
	}
	public Double getPgCancelBalance() {
		return pgCancelBalance;
	}
	public void setPgCancelBalance(Double pgCancelBalance) {
		this.pgCancelBalance = pgCancelBalance;
	}
	public String getRefundState() {
		return refundState;
	}
	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}
	public Integer getRefundMemberIdx() {
		return refundMemberIdx;
	}
	public void setRefundMemberIdx(Integer refundMemberIdx) {
		this.refundMemberIdx = refundMemberIdx;
	}
	public String getRefundHttpUserAgent() {
		return refundHttpUserAgent;
	}
	public void setRefundHttpUserAgent(String refundHttpUserAgent) {
		this.refundHttpUserAgent = refundHttpUserAgent;
	}
	public String getRefundIp() {
		return refundIp;
	}
	public void setRefundIp(String refundIp) {
		this.refundIp = refundIp;
	}
	public String getRefundDt() {
		return refundDt;
	}
	public void setRefundDt(String refundDt) {
		this.refundDt = refundDt;
	}
	public Double getRefundTaxSupplyPrice() {
		return refundTaxSupplyPrice;
	}
	public void setRefundTaxSupplyPrice(Double refundTaxSupplyPrice) {
		this.refundTaxSupplyPrice = refundTaxSupplyPrice;
	}
	public Double getRefundTaxFreeSupplyPrice() {
		return refundTaxFreeSupplyPrice;
	}
	public void setRefundTaxFreeSupplyPrice(Double refundTaxFreeSupplyPrice) {
		this.refundTaxFreeSupplyPrice = refundTaxFreeSupplyPrice;
	}
	public Double getRefundTaxVat() {
		return refundTaxVat;
	}
	public void setRefundTaxVat(Double refundTaxVat) {
		this.refundTaxVat = refundTaxVat;
	}
	public Integer getRegIdx() {
		return regIdx;
	}
	public void setRegIdx(Integer regIdx) {
		this.regIdx = regIdx;
	}
	public String getRegHttpUserAgent() {
		return regHttpUserAgent;
	}
	public void setRegHttpUserAgent(String regHttpUserAgent) {
		this.regHttpUserAgent = regHttpUserAgent;
	}
	public String getRegIp() {
		return regIp;
	}
	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public Integer getEditIdx() {
		return editIdx;
	}
	public void setEditIdx(Integer editIdx) {
		this.editIdx = editIdx;
	}
	public String getEditHttpUserAgent() {
		return editHttpUserAgent;
	}
	public void setEditHttpUserAgent(String editHttpUserAgent) {
		this.editHttpUserAgent = editHttpUserAgent;
	}
	public String getEditIp() {
		return editIp;
	}
	public void setEditIp(String editIp) {
		this.editIp = editIp;
	}
	public String getEditDt() {
		return editDt;
	}
	public void setEditDt(String editDt) {
		this.editDt = editDt;
	}
	public String getSaveRefundAccount() {
		return saveRefundAccount;
	}
	public void setSaveRefundAccount(String saveRefundAccount) {
		this.saveRefundAccount = saveRefundAccount;
	}
	public Integer getMemberIdx() {
		return memberIdx;
	}
	public void setMemberIdx(Integer memberIdx) {
		this.memberIdx = memberIdx;
	}
	public String getNomemberOrderCd() {
		return nomemberOrderCd;
	}
	public void setNomemberOrderCd(String nomemberOrderCd) {
		this.nomemberOrderCd = nomemberOrderCd;
	}
	public String[] getOrderDetailIdxes() {
		return orderDetailIdxes;
	}
	public void setOrderDetailIdxes(String[] orderDetailIdxes) {
		this.orderDetailIdxes = orderDetailIdxes;
	}
	public String getOrderDetailIdxesStr() {
		return orderDetailIdxesStr;
	}
	public void setOrderDetailIdxesStr(String orderDetailIdxesStr) {
		this.orderDetailIdxesStr = orderDetailIdxesStr;
	}
	public String[] getBeforeOrderStatusCds() {
		return beforeOrderStatusCds;
	}
	public void setBeforeOrderStatusCds(String[] beforeOrderStatusCds) {
		this.beforeOrderStatusCds = beforeOrderStatusCds;
	}
	public String getReturnUrl() {
		switch(this.returnUrl){	// 교환, 반품 신청 완료 후 돌아갈 URL (0 : 마이페이지 메인, 1 : 주문관리 리스트, 2 : 주문상세)
		case "0" : return "/mypage/order/main.do";
		case "1" : return "/mypage/order/myOrderList.do";
		case "2" : return "/mypage/order/orderDetail.do?orderCd="+this.getOrderCd();
		default : return "";
		}		
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
}
