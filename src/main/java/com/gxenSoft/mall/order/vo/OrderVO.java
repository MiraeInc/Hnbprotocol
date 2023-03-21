
package com.gxenSoft.mall.order.vo;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.HtmlUtils;

public class OrderVO {

	private Integer orderIdx;							// 주문 마스터 일련번호 (100000 번부터 시작)
	private String orderCd;								// 주문코드 (YYYYMMDDHHNN + [P|M|A] + 랜덤값5자리 (P : PC, M : MOBILE, A : APP))
	private String memberOrderYn;					// 	MEMBER_ORDER_YN (회원/비회원 구분	Y : 회원주문, N : 비회원주문)
	private Integer memberIdx;						// 회원 일련번호
	private String memberId;							// 회원 ID
	private Integer memberGradeIdx;				// 회원등급 일련번호
	private String memberNm;							// 회원명(주문자명) (비회원일때는 주문자명)
	private String sessionId;								// 비회원용 세션 ID
	private String nonmemberPw;						// 비밀번호 (비회원일때만 사용됨)
	private String device;									// 디바이스 (P : PC, M : MOBILE, A : APP)
	private String orderDt;								// 주문일자
	private String orderStatusCd;						// 주문상태코드 (000 : 주문 전, 100 : 주문 접수(입금 대기), 200 : 결제 완료, 300 : 상품 준비중, 400 : 발송 완료(배송중), 500 : 배송 완료, 600 : 교환 신청, 650 : 교환 처리중, 670 : 교환 불가, 690 : 교환 완료, 700 : 반품 신청, 750 : 반품 처리중, 770 : 반품 불가, 790 : 반품 완료, 800 : 취소 신청, 890 : 주문 취소, 900 : 구매 확정)
	private String payDt;									// 결제일자 (PG사 결제성공일시)
	private String payType;								// 결제수단 (공통코드 PAY_TYPE10 : 신용카드, PAY_TYPE15 : 빌키, PAY_TYPE20 : 실시간계좌, PAY_TYPE25 : 가상계좌, PAY_TYPE30 : 휴대폰, PAY_TYPE35 : PAYCO, PAY_TYPE40 : 카카오페이, PAY_TYPE45 : Npay, PAY_TYPE50 : PAYNOW, PAY_TYPE90 : 포인트)
	private String billkeyIdx;								// 빌키 일련번호 (원클릭 결제시 결제된 빌키 일련번호)
	private String bankCode;								// 입금 은행코드 (공통코드 무통장(가상계좌)일때 사용)
	private String account;								// 입금 계좌번호 (무통장(가상계좌)일때 사용)
	private String depositor;								// 입금 예금주명 (무통장(가상계좌)일때 사용)
	private String escrowYn;								// 에스크로 여부 (Y : 에스크로 사용, N : 에스크로 사용 안 함)
	private String cashReceiptGubun;					// 현금영수증 여부 (1 : 개인, 2 : 사업자, 0 : 미신청)
	private String cashReceiptNo;						// 현금영수증 번호 (CASH_RECEIPT_GUBUN가 N이 아닐때만 사용, 개인은 휴대폰번호, 사업자는 사업자등록번호)
	private Double totalOrderPrice;					// 총 주문금액 (총 상품금액)
	private Integer freeShippingCouponIdx;		// 무료배송 쿠폰 일련번호
	private Double freeShippingCouponPrice;		// 무료배송 쿠폰 할인 금액
	private Double totalGiftCouponPrice;				// 총 상품 쿠폰 할인 금액
	private Double totalCartCouponPrice;			// 총 장바구니 쿠폰 할인 금액
	private Integer cartCouponIdx;					// 장바구니 쿠폰 일련번호 (결제 페이지에서 임시 적용된 장바구니 쿠폰 일련번호)
	private String promotioncode;						//사용 프로모션코드
	private String promotioncoderandom;				//사용 프로모션코드 (랜덤)
	private String randomcodeYn;						// 랜덤프로모션코드 여부 (Y:랜덤, NULL:일반)
	private Double totalPromotioncodePrice = 0.0;		// 총 프로모션 코드 할인 금액
	private Double totalCouponPrice= 0.0;					// 총 쿠폰/프로모션코드 할인 금액 (무료배송 쿠폰 할인 금액 + 총 상품 쿠폰 할인금액 + 총 장바구니 쿠폰 할인금액 + 총 프로모션 코드 할인금액)
	private Double shippingPrice;						// 배송비 (무료배송쿠폰이 적용되면 0)
	private Double totalPointPrice= 0.0;						// 포인트 결제 금액
	private Double totalPrePointPrice;				// 선포인트 결제 금액
	private Double totalPayPrice;						// 결제금액 (실제 PG사 결제금액)
	private Double totalSavePoint;						// 적립 예정 포인트 (원래 적립 예정 포인트 - 선포인트 결제 금액)
	private String senderNm;								// 보내는 사람 이름 (비회원 주문시 필수)
	private String senderAddr;							// 보내는 사람 주소 (도로명주소)
	private String senderAddrDetail;					// 보내는 사람 상세주소 (도로명, 지번 공통)
	private String senderZipCd;							// 보내는 사람 우편번호 (신우편번호)
	private String senderOldAddr;						// 보내는 사람 주소 (지번주소)
	private String senderOldZipCd;						// 보내는 사람 (구)우편번호 ((-) 포함)
	private String senderTelNo;							// 보내는 사람 전화번호	
	private String senderPhoneNo;						// 보내는 사람 휴대폰번호 (비회원 주문시 필수)
	private String senderEmail;							// 보내는 사람 이메일
	private String shippingNm;							// 주소록명 (새로입력시 입력한 배송지명)
	private String receiverNm;							// 받는 사람 이름
	private String receiverAddr;							// 받는 사람 주소 (도로명주소)
	private String receiverAddrDetail;					// 받는 사람 상세주소 (도로명, 지번 공통)
	private String receiverZipCd;						// 받는 사람 우편번호 (신우편번호)
	private String receiverOldAddr;						// 받는 사람 주소 (지번주소)
	private String receiverOldZipCd;					// 받는 사람 (구)우편번호 ((-) 포함)
	private String receiverTelNo;						// 받는 사람 전화번호
	private String receiverPhoneNo;					// 받는 사람 휴대폰번호
	private String orderMemo;							// 배송시 요청사항	
	private String giftPackingYn;						// 선물포장 서비스 (Y : 선물 포장, N : 선물 포장 안 함)
	private String termsYn;								// 비회원 구매 이용약관 동의 여부 (비회원일때만 사용됨 Y : 동의, N : 미동의)
	private String collectionAgreementYn;			// 비회원 구매 개인정보 수집 동의 (비회원일때만 사용됨 Y : 동의, N : 미동의)
	private String consignmentAgreementYn;		// 비회원 구매 개인정보 취급 위탁 동의 (비회원일때만 사용됨 Y : 동의, N : 미동의)
	private Integer regIdx;								// 작성자 일련번호
	private String regHttpUserAgent;					// 작성자 USER_AGENT
	private String regIp;									// 작성자 IP
	private String regDt;									// 작성일자
	
	private String goodsIdx;								// 상품 일련번호
	private Integer goodsCnt;							// 상품 갯수
	
	private List<OrderGoodsVO> orderGoodsInfoList;	// 주문한 장바구니 정보
	private String orderGoodsInfoListStr;					// 주문한 장바구니 정보 문자열
	
	// 상태 유지 관련
	private String sameAsOrderInfo;					// 회원정보와 동일인지 새로입력인지 (Y : 회원정보와 동일, N : 새로입력) (비회원은 null)
	private String addressTabId;						// 선택한 배송지 탭 ID (addressID1 : 배송지 목록 탭, addressID2 : 새로 입력 탭) (비회원은 null)
	private Integer selectAddressIdx;				// 선택한 배송지 목록 일련번호 (비회원은 null)
	private String sameOrderInfo;						// 주문자 정보와 상품을 받는 분이 같습니다
	private String addToAddress;						// 배송지 목록에 추가 (Y) (비회원은 null)
	private String setAsDefaultAddress;				// 기본 배송지로 설정 (Y) (비회원은 null)
	private String orderMemoVal = "";					// 선택한 배송시 요청사항 SELECTBOX value
	private String giftTabId;								// 선택한 사은품 탭 ID (allGift : 무료 사은품 탭, over4Gift...) (비회원은 null)
	private Integer selectPriceGiftIdx;				// 선택한 구매금액별 사은품 일련번호 (비회원은 null)
	private String selectPayType = "BILLKEY";		// 선택한 결제 수단 (BILLKEY : 원클릭 결제, SC0010 : 신용카드)
	private String selectBillkeyVal = "";				// 선택한 빌키 SELECTBOX value
	private String selectCardCode;						// 선택한 카드 코드
	private String etcCardVal = "";						// 선택한 기타 카드 SELECTBOX value
	private String escrowYn1Val;						// 실시간 계좌 이체시 선택한 에스크로 value
	private String escrowYn2Val;						// 가상계좌 입금시 선택한 에스크로 value
	private String useThisPayTypeToNext;			// 지금 선택하신 결제수단을 다음에도 사용
	private String agreement;							// 위 상품의 판매조건을 명확히 확인하였으며, 구매 진행에 동의합니다. (전자상거래법 제2조 8항)
	
	// 배송지 수정/삭제
	private Integer modifyAddressIdx;				// 수정/삭제할 배송지 목록 일련번호
	private String newShippingNm;						// 수정할 배송지명
	private String newReceiverNm;						// 수정할 받는 사람 이름
	private String newReceiverAddr;					// 수정할 받는 사람 주소 (도로명주소)
	private String newReceiverAddrDetail;			// 수정할 받는 사람 상세주소 (도로명, 지번 공통)
	private String newReceiverZipCd;					// 수정할 받는 사람 우편번호 (신우편번호)
	private String newReceiverOldAddr;				// 수정할 받는 사람 주소 (지번주소)
	private String newReceiverOldZipCd;				// 수정할 받는 사람 (구)우편번호 ((-) 포함)
	private String newReceiverTelNo;					// 수정할 받는 사람 전화번호
	private String newReceiverPhoneNo;				// 수정할 받는 사람 휴대폰번호
	private String newSetAsDefaultAddress;		// 수정할 기본 배송지로 설정 (Y) (비회원은 null)
	
	// 비회원 주문 조회용
	private String nomemberOrderCd;					// 주문코드 (YYYYMMDDHHNN + [P|M|A] + 랜덤값5자리 (P : PC, M : MOBILE, A : APP))
	
	// 비회원 구매
	private String nomemberOrderYn;					// 비회원 구매 선택 여부 (Y : 비회원 구매 버튼 클릭한 상태)
	private String fromOrderFlag;						// 비회원 구매 선택 안 하고 왔을때 로그인 페이지로 보낼 파라미터 (Y)
	
	private String orderMenu;						// 마이페이지 주문관리 메뉴값
	
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
	public String getMemberOrderYn() {
		return memberOrderYn;
	}
	public void setMemberOrderYn(String memberOrderYn) {
		this.memberOrderYn = memberOrderYn;
	}
	public Integer getMemberIdx() {
		return memberIdx;
	}
	public void setMemberIdx(Integer memberIdx) {
		this.memberIdx = memberIdx;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public Integer getMemberGradeIdx() {
		return memberGradeIdx;
	}
	public void setMemberGradeIdx(Integer memberGradeIdx) {
		this.memberGradeIdx = memberGradeIdx;
	}
	public String getMemberNm() {
		return memberNm;
	}
	public void setMemberNm(String memberNm) {
		this.memberNm = memberNm;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getNonmemberPw() {
		return nonmemberPw;
	}
	public void setNonmemberPw(String nonmemberPw) {
		this.nonmemberPw = nonmemberPw;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getOrderDt() {
		return orderDt;
	}
	public void setOrderDt(String orderDt) {
		this.orderDt = orderDt;
	}
	public String getOrderStatusCd() {
		return orderStatusCd;
	}
	public void setOrderStatusCd(String orderStatusCd) {
		this.orderStatusCd = orderStatusCd;
	}
	public String getPayDt() {
		return payDt;
	}
	public void setPayDt(String payDt) {
		this.payDt = payDt;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getBillkeyIdx() {
		return billkeyIdx;
	}
	public void setBillkeyIdx(String billkeyIdx) {
		this.billkeyIdx = billkeyIdx;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getDepositor() {
		return depositor;
	}
	public void setDepositor(String depositor) {
		this.depositor = depositor;
	}
	public String getEscrowYn() {
		return escrowYn;
	}
	public void setEscrowYn(String escrowYn) {
		this.escrowYn = escrowYn;
	}
	public String getCashReceiptGubun() {
		return cashReceiptGubun;
	}
	public void setCashReceiptGubun(String cashReceiptGubun) {
		this.cashReceiptGubun = cashReceiptGubun;
	}
	public String getCashReceiptNo() {
		return cashReceiptNo;
	}
	public void setCashReceiptNo(String cashReceiptNo) {
		this.cashReceiptNo = cashReceiptNo;
	}
	public Double getTotalOrderPrice() {
		return totalOrderPrice;
	}
	public void setTotalOrderPrice(Double totalOrderPrice) {
		this.totalOrderPrice = totalOrderPrice;
	}
	public Double getFreeShippingCouponPrice() {
		return freeShippingCouponPrice;
	}
	public void setFreeShippingCouponPrice(Double freeShippingCouponPrice) {
		this.freeShippingCouponPrice = freeShippingCouponPrice;
	}
	public Double getTotalGiftCouponPrice() {
		return totalGiftCouponPrice;
	}
	public void setTotalGiftCouponPrice(Double totalGiftCouponPrice) {
		this.totalGiftCouponPrice = totalGiftCouponPrice;
	}
	public Double getTotalCartCouponPrice() {
		return totalCartCouponPrice;
	}
	public void setTotalCartCouponPrice(Double totalCartCouponPrice) {
		this.totalCartCouponPrice = totalCartCouponPrice;
	}
	public Integer getCartCouponIdx() {
		return cartCouponIdx;
	}
	public void setCartCouponIdx(Integer cartCouponIdx) {
		this.cartCouponIdx = cartCouponIdx;
	}
	public Double getTotalPromotioncodePrice() {
		return totalPromotioncodePrice;
	}
	public void setTotalPromotioncodePrice(Double totalPromotioncodePrice) {
		this.totalPromotioncodePrice = totalPromotioncodePrice;
	}
	public Double getTotalCouponPrice() {
		return totalCouponPrice;
	}
	public void setTotalCouponPrice(Double totalCouponPrice) {
		this.totalCouponPrice = totalCouponPrice;
	}
	public Double getShippingPrice() {
		return shippingPrice;
	}
	public void setShippingPrice(Double shippingPrice) {
		this.shippingPrice = shippingPrice;
	}
	public Double getTotalPointPrice() {
		return totalPointPrice;
	}
	public void setTotalPointPrice(Double totalPointPrice) {
		this.totalPointPrice = totalPointPrice;
	}
	public Double getTotalPrePointPrice() {
		return totalPrePointPrice;
	}
	public void setTotalPrePointPrice(Double totalPrePointPrice) {
		this.totalPrePointPrice = totalPrePointPrice;
	}
	public Double getTotalPayPrice() {
		return totalPayPrice;
	}
	public void setTotalPayPrice(Double totalPayPrice) {
		this.totalPayPrice = totalPayPrice;
	}
	public Double getTotalSavePoint() {
		return totalSavePoint;
	}
	public void setTotalSavePoint(Double totalSavePoint) {
		this.totalSavePoint = totalSavePoint;
	}
	public String getSenderNm() {
		return senderNm;
	}
	public void setSenderNm(String senderNm) {
		this.senderNm = senderNm;
	}
	public String getSenderAddr() {
		return senderAddr;
	}
	public void setSenderAddr(String senderAddr) {
		this.senderAddr = senderAddr;
	}
	public String getSenderAddrDetail() {
		return senderAddrDetail;
	}
	public void setSenderAddrDetail(String senderAddrDetail) {
		this.senderAddrDetail = senderAddrDetail;
	}
	public String getSenderZipCd() {
		return senderZipCd;
	}
	public void setSenderZipCd(String senderZipCd) {
		this.senderZipCd = senderZipCd;
	}
	public String getSenderOldAddr() {
		return senderOldAddr;
	}
	public void setSenderOldAddr(String senderOldAddr) {
		this.senderOldAddr = senderOldAddr;
	}
	public String getSenderOldZipCd() {
		return senderOldZipCd;
	}
	public void setSenderOldZipCd(String senderOldZipCd) {
		this.senderOldZipCd = senderOldZipCd;
	}
	public String getSenderTelNo() {
		return senderTelNo;
	}
	public void setSenderTelNo(String senderTelNo) {
		this.senderTelNo = senderTelNo;
	}
	public String getSenderPhoneNo() {
		return senderPhoneNo;
	}
	public void setSenderPhoneNo(String senderPhoneNo) {
		this.senderPhoneNo = senderPhoneNo;
	}
	public String getSenderEmail() {
		return senderEmail;
	}
	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}
	public String getShippingNm() {
		return shippingNm;
	}
	public void setShippingNm(String shippingNm) {
		this.shippingNm = shippingNm;
	}
	public String getReceiverNm() {
		return receiverNm;
	}
	public void setReceiverNm(String receiverNm) {
		this.receiverNm = receiverNm;
	}
	public String getReceiverAddr() {
		return receiverAddr;
	}
	public void setReceiverAddr(String receiverAddr) {
		this.receiverAddr = receiverAddr;
	}
	public String getReceiverAddrDetail() {
		return receiverAddrDetail;
	}
	public void setReceiverAddrDetail(String receiverAddrDetail) {
		this.receiverAddrDetail = receiverAddrDetail;
	}
	public String getReceiverZipCd() {
		return receiverZipCd;
	}
	public void setReceiverZipCd(String receiverZipCd) {
		this.receiverZipCd = receiverZipCd;
	}
	public String getReceiverOldAddr() {
		return receiverOldAddr;
	}
	public void setReceiverOldAddr(String receiverOldAddr) {
		this.receiverOldAddr = receiverOldAddr;
	}
	public String getReceiverOldZipCd() {
		return receiverOldZipCd;
	}
	public void setReceiverOldZipCd(String receiverOldZipCd) {
		this.receiverOldZipCd = receiverOldZipCd;
	}
	public String getReceiverTelNo() {
		return receiverTelNo;
	}
	public void setReceiverTelNo(String receiverTelNo) {
		this.receiverTelNo = receiverTelNo;
	}
	public String getReceiverPhoneNo() {
		return receiverPhoneNo;
	}
	public void setReceiverPhoneNo(String receiverPhoneNo) {
		this.receiverPhoneNo = receiverPhoneNo;
	}
	public String getOrderMemo() {
		return orderMemo;
	}
	public void setOrderMemo(String orderMemo) {
		this.orderMemo = orderMemo;
	}
	public String getGiftPackingYn() {
		return giftPackingYn;
	}
	public void setGiftPackingYn(String giftPackingYn) {
		this.giftPackingYn = giftPackingYn;
	}
	public String getTermsYn() {
		return termsYn;
	}
	public void setTermsYn(String termsYn) {
		this.termsYn = termsYn;
	}
	public String getCollectionAgreementYn() {
		return collectionAgreementYn;
	}
	public void setCollectionAgreementYn(String collectionAgreementYn) {
		this.collectionAgreementYn = collectionAgreementYn;
	}
	public String getConsignmentAgreementYn() {
		return consignmentAgreementYn;
	}
	public void setConsignmentAgreementYn(String consignmentAgreementYn) {
		this.consignmentAgreementYn = consignmentAgreementYn;
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
	public String getGoodsIdx() {
		return goodsIdx;
	}
	public void setGoodsIdx(String goodsIdx) {
		this.goodsIdx = goodsIdx;
	}
	public Integer getGoodsCnt() {
		return goodsCnt;
	}
	public void setGoodsCnt(Integer goodsCnt) {
		this.goodsCnt = goodsCnt;
	}
	public List<OrderGoodsVO> getOrderGoodsInfoList() {
		return orderGoodsInfoList;
	}
	public void setOrderGoodsInfoList(List<OrderGoodsVO> orderGoodsInfoList) {
		this.orderGoodsInfoList = orderGoodsInfoList;
	}
	public String getOrderGoodsInfoListStr() {
		return orderGoodsInfoListStr;
	}
	public void setOrderGoodsInfoListStr(String orderGoodsInfoListStr) {
		this.orderGoodsInfoListStr = HtmlUtils.htmlUnescape(orderGoodsInfoListStr) ;
	}
	public String getSameAsOrderInfo() {
		return sameAsOrderInfo;
	}
	public void setSameAsOrderInfo(String sameAsOrderInfo) {
		this.sameAsOrderInfo = sameAsOrderInfo;
	}
	public String getAddressTabId() {
		return addressTabId;
	}
	public void setAddressTabId(String addressTabId) {
		this.addressTabId = addressTabId;
	}
	public Integer getSelectAddressIdx() {
		return selectAddressIdx;
	}
	public void setSelectAddressIdx(Integer selectAddressIdx) {
		this.selectAddressIdx = selectAddressIdx;
	}
	public String getSameOrderInfo() {
		return sameOrderInfo;
	}
	public void setSameOrderInfo(String sameOrderInfo) {
		this.sameOrderInfo = sameOrderInfo;
	}
	public String getAddToAddress() {
		return addToAddress;
	}
	public void setAddToAddress(String addToAddress) {
		this.addToAddress = addToAddress;
	}
	public String getSetAsDefaultAddress() {
		return setAsDefaultAddress;
	}
	public void setSetAsDefaultAddress(String setAsDefaultAddress) {
		this.setAsDefaultAddress = setAsDefaultAddress;
	}
	public String getOrderMemoVal() {
		return orderMemoVal;
	}
	public void setOrderMemoVal(String orderMemoVal) {
		this.orderMemoVal = orderMemoVal;
	}
	public String getGiftTabId() {
		return giftTabId;
	}
	public void setGiftTabId(String giftTabId) {
		this.giftTabId = giftTabId;
	}
	public Integer getSelectPriceGiftIdx() {
		return selectPriceGiftIdx;
	}
	public void setSelectPriceGiftIdx(Integer selectPriceGiftIdx) {
		this.selectPriceGiftIdx = selectPriceGiftIdx;
	}
	public String getSelectPayType() {
		return selectPayType;
	}
	public void setSelectPayType(String selectPayType) {
		this.selectPayType = selectPayType;
	}
	public String getSelectBillkeyVal() {
		return selectBillkeyVal;
	}
	public void setSelectBillkeyVal(String selectBillkeyVal) {
		this.selectBillkeyVal = selectBillkeyVal;
	}
	public String getSelectCardCode() {
		return selectCardCode;
	}
	public void setSelectCardCode(String selectCardCode) {
		this.selectCardCode = selectCardCode;
	}
	public String getEtcCardVal() {
		return etcCardVal;
	}
	public void setEtcCardVal(String etcCardVal) {
		this.etcCardVal = etcCardVal;
	}
	public String getEscrowYn1Val() {
		return escrowYn1Val;
	}
	public void setEscrowYn1Val(String escrowYn1Val) {
		this.escrowYn1Val = escrowYn1Val;
	}
	public String getEscrowYn2Val() {
		return escrowYn2Val;
	}
	public void setEscrowYn2Val(String escrowYn2Val) {
		this.escrowYn2Val = escrowYn2Val;
	}
	public String getUseThisPayTypeToNext() {
		return useThisPayTypeToNext;
	}
	public void setUseThisPayTypeToNext(String useThisPayTypeToNext) {
		this.useThisPayTypeToNext = useThisPayTypeToNext;
	}
	public String getAgreement() {
		return agreement;
	}
	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}
	public Integer getModifyAddressIdx() {
		return modifyAddressIdx;
	}
	public void setModifyAddressIdx(Integer modifyAddressIdx) {
		this.modifyAddressIdx = modifyAddressIdx;
	}
	public String getNewShippingNm() {
		return newShippingNm;
	}
	public void setNewShippingNm(String newShippingNm) {
		this.newShippingNm = newShippingNm;
	}
	public String getNewReceiverNm() {
		return newReceiverNm;
	}
	public void setNewReceiverNm(String newReceiverNm) {
		this.newReceiverNm = newReceiverNm;
	}
	public String getNewReceiverAddr() {
		return newReceiverAddr;
	}
	public void setNewReceiverAddr(String newReceiverAddr) {
		this.newReceiverAddr = newReceiverAddr;
	}
	public String getNewReceiverAddrDetail() {
		return newReceiverAddrDetail;
	}
	public void setNewReceiverAddrDetail(String newReceiverAddrDetail) {
		this.newReceiverAddrDetail = newReceiverAddrDetail;
	}
	public String getNewReceiverZipCd() {
		return newReceiverZipCd;
	}
	public void setNewReceiverZipCd(String newReceiverZipCd) {
		this.newReceiverZipCd = newReceiverZipCd;
	}
	public String getNewReceiverOldAddr() {
		return newReceiverOldAddr;
	}
	public void setNewReceiverOldAddr(String newReceiverOldAddr) {
		this.newReceiverOldAddr = newReceiverOldAddr;
	}
	public String getNewReceiverOldZipCd() {
		return newReceiverOldZipCd;
	}
	public void setNewReceiverOldZipCd(String newReceiverOldZipCd) {
		this.newReceiverOldZipCd = newReceiverOldZipCd;
	}
	public String getNewReceiverTelNo() {
		return newReceiverTelNo;
	}
	public void setNewReceiverTelNo(String newReceiverTelNo) {
		this.newReceiverTelNo = newReceiverTelNo;
	}
	public String getNewReceiverPhoneNo() {
		return newReceiverPhoneNo;
	}
	public void setNewReceiverPhoneNo(String newReceiverPhoneNo) {
		this.newReceiverPhoneNo = newReceiverPhoneNo;
	}
	public String getNewSetAsDefaultAddress() {
		return newSetAsDefaultAddress;
	}
	public void setNewSetAsDefaultAddress(String newSetAsDefaultAddress) {
		this.newSetAsDefaultAddress = newSetAsDefaultAddress;
	}
	public Integer getFreeShippingCouponIdx() {
		return freeShippingCouponIdx;
	}
	public void setFreeShippingCouponIdx(Integer freeShippingCouponIdx) {
		this.freeShippingCouponIdx = freeShippingCouponIdx;
	}
	public String getPromotioncode() {
		return promotioncode;
	}
	public void setPromotioncode(String promotioncode) {
		this.promotioncode = promotioncode;
	}
	public String getNomemberOrderCd() {
		return nomemberOrderCd;
	}
	public void setNomemberOrderCd(String nomemberOrderCd) {
		this.nomemberOrderCd = nomemberOrderCd;
	}
	public String getNomemberOrderYn() {
		return nomemberOrderYn;
	}
	public void setNomemberOrderYn(String nomemberOrderYn) {
		this.nomemberOrderYn = nomemberOrderYn;
	}
	public String getFromOrderFlag() {
		return fromOrderFlag;
	}
	public void setFromOrderFlag(String fromOrderFlag) {
		this.fromOrderFlag = fromOrderFlag;
	}
	public String getOrderMenu() {
		return StringUtils.defaultString(orderMenu);
	}
	public void setOrderMenu(String orderMenu) {
		this.orderMenu = orderMenu;
	}
	public String getPromotioncoderandom() {
		return StringUtils.defaultString(promotioncoderandom);
	}
	public void setPromotioncoderandom(String promotioncoderandom) {
		this.promotioncoderandom = promotioncoderandom;
	}
	public String getRandomcodeYn() {
		return randomcodeYn;
	}
	public void setRandomcodeYn(String randomcodeYn) {
		this.randomcodeYn = randomcodeYn;
	}
}
