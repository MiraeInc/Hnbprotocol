package com.gxenSoft.util.pg.smilepay.vo;

import org.apache.commons.lang.StringUtils;

/**
   *************************************
   * PROJECT   : GatsbyMall
   * PROGRAM ID  : SmilepayResultVO
   * PACKAGE NM : com.gxenSoft.util.pg.smilepay.vo
   * AUTHOR	 : 김 민 수
   * CREATED DATE  : 2017. 11. 26. 
   * HISTORY :   
   *
   *************************************
 **/	
public class SmilepayResultVO {
	private String orderCd;					// 주문마스터 테이블 주문 코드
	private String resultCode;	          	// 결과코드 (정상 :3001 , 그 외 에러)
	private String resultMsg;             	// 결과메시지
	private String errorCD;               		// 원천사 오류코드 (참조용)
	private String errorMsg;              		// 원천사 오류 메세지 (참조용)
	private String authDate;              	// 승인일시 YYMMDDHH24mmss
	private String authCode;             	// 승인번호
	private String buyerName;             	// 구매자명
	private String goodsName;             	// 상품명
	private String payMethod;             	// 결제수단
	private String mid;                   		// 가맹점ID
	private String tid;                   		// 거래ID
	private String moid;                  		// 주문번호
	private String amt;                   		// 금액
	private String cardCode;              	// 카드발급사 코드
	private String acquCardCode;         	// 카드매입사 코드
	private String cardName;              	// 결제카드사명
	private String cardQuota;             	// 할부개월수 ex) 00:일시불,02:2개월
	private String cardInterest;          	// 무이자여부 (0:일반, 1:무이자)
	private String cardCl;                		// 체크카드여부 (0:일반, 1:체크카드)
	private String cardBin;               		// 카드BIN번호
	private String cardPoint;             		// 카드사포인트사용여부 (0:미사용, 1:포인트사용, 2:세이브포인트사용)
	private String ccPartCl;              		// 부분취소 가능 여부 (0:부분취소불가, 1:부분취소가능)
	private String promotionCcPartCl;    	// 프로모션 부분취소 가능 여부 (null:프로모션미적용, N:부분취소불가, Y:부분취소가능)
	private String vanCode;               	// VAN 코드 (00:자체응답, 04:KOVAN, 05:SMARTRO)
	private String fnNo;                  		// 가맹점번호 (중계가맹점일 경우)
	private String cardNo;                		// 마스킹된 카드 번호
	private String promotionCd;          	// 프로모션 코드
	private String discountAmt;           	// 프로모션 할인 금액
	private String possiBin;              		// 선할인제휴카드 BIN
	private String blockBin;              		// 특정제한카드 BIN
	private String paySuccess;	          	// 결제 성공 여부
	
	
	
	public String getOrderCd() {
		return StringUtils.defaultString(orderCd);
	}
	public void setOrderCd(String orderCd) {
		this.orderCd = orderCd;
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
	public String getErrorCD() {
		return StringUtils.defaultString(errorCD);
	}
	public void setErrorCD(String errorCD) {
		this.errorCD = errorCD;
	}
	public String getErrorMsg() {
		return StringUtils.defaultString(errorMsg);
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getAuthDate() {
		return StringUtils.defaultString(authDate);
	}
	public void setAuthDate(String authDate) {
		this.authDate = authDate;
	}
	public String getAuthCode() {
		return StringUtils.defaultString(authCode);
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getBuyerName() {
		return StringUtils.defaultString(buyerName);
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getGoodsName() {
		return StringUtils.defaultString(goodsName);
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
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
	public String getMoid() {
		return StringUtils.defaultString(moid);
	}
	public void setMoid(String moid) {
		this.moid = moid;
	}
	public String getAmt() {
		return StringUtils.defaultString(amt);
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getCardCode() {
		return StringUtils.defaultString(cardCode);
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public String getAcquCardCode() {
		return StringUtils.defaultString(acquCardCode);
	}
	public void setAcquCardCode(String acquCardCode) {
		this.acquCardCode = acquCardCode;
	}
	public String getCardName() {
		return StringUtils.defaultString(cardName);
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getCardQuota() {
		return StringUtils.defaultString(cardQuota);
	}
	public void setCardQuota(String cardQuota) {
		this.cardQuota = cardQuota;
	}
	public String getCardInterest() {
		return StringUtils.defaultString(cardInterest);
	}
	public void setCardInterest(String cardInterest) {
		this.cardInterest = cardInterest;
	}
	public String getCardCl() {
		return StringUtils.defaultString(cardCl);
	}
	public void setCardCl(String cardCl) {
		this.cardCl = cardCl;
	}
	public String getCardBin() {
		return StringUtils.defaultString(cardBin);
	}
	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}
	public String getCardPoint() {
		return StringUtils.defaultString(cardPoint);
	}
	public void setCardPoint(String cardPoint) {
		this.cardPoint = cardPoint;
	}
	public String getCcPartCl() {
		return StringUtils.defaultString(ccPartCl);
	}
	public void setCcPartCl(String ccPartCl) {
		this.ccPartCl = ccPartCl;
	}
	public String getPromotionCcPartCl() {
		return StringUtils.defaultString(promotionCcPartCl);
	}
	public void setPromotionCcPartCl(String promotionCcPartCl) {
		this.promotionCcPartCl = promotionCcPartCl;
	}
	public String getVanCode() {
		return StringUtils.defaultString(vanCode);
	}
	public void setVanCode(String vanCode) {
		this.vanCode = vanCode;
	}
	public String getFnNo() {
		return StringUtils.defaultString(fnNo);
	}
	public void setFnNo(String fnNo) {
		this.fnNo = fnNo;
	}
	public String getCardNo() {
		return StringUtils.defaultString(cardNo);
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getPromotionCd() {
		return StringUtils.defaultString(promotionCd);
	}
	public void setPromotionCd(String promotionCd) {
		this.promotionCd = promotionCd;
	}
	public String getDiscountAmt() {
		return StringUtils.defaultString(discountAmt);
	}
	public void setDiscountAmt(String discountAmt) {
		this.discountAmt = discountAmt;
	}
	public String getPossiBin() {
		return StringUtils.defaultString(possiBin);
	}
	public void setPossiBin(String possiBin) {
		this.possiBin = possiBin;
	}
	public String getBlockBin() {
		return StringUtils.defaultString(blockBin);
	}
	public void setBlockBin(String blockBin) {
		this.blockBin = blockBin;
	}
	public String getPaySuccess() {
		return StringUtils.defaultString(paySuccess);
	}
	public void setPaySuccess(String paySuccess) {
		this.paySuccess = paySuccess;
	}

	
}
