package com.gxenSoft.mall.cart.vo;

public class CartVO {

	private String cartIdx;						// 선택한 장바구니 일련번호
	private String cartIdxes;					// 선택한 장바구니 일련번호 (삭제,수량변경용)
	private Integer changeGoodsCnt;		// 수량 변경할 상품 갯수
	private Integer memberIdx;			// 회원 일련번호
	private String sessionId;					// 비회원용 세션 ID
	private String goodsIdx;					// 상품 일련번호
	private String goodsIdxes;				// 상품 일련번호 (여러개)
	private Integer goodsCnt;				// 상품 갯수
	private Integer couponIdx;				// 자동 적용 쿠폰 일련번호 (회원일때만 사용)
	private Integer regIdx;					// 작성자 일련번호
	private String regHttpUserAgent;		// 작성자 USER_AGENT
	private String regIp;						// 작성자 IP
	private String regDt;						// 작성일자

	public String getCartIdx() {
		return cartIdx;
	}
	public void setCartIdx(String cartIdx) {
		this.cartIdx = cartIdx;
	}	
	public String getCartIdxes() {
		return cartIdxes;
	}
	public void setCartIdxes(String cartIdxes) {
		this.cartIdxes = cartIdxes;
	}
	public Integer getChangeGoodsCnt() {
		return changeGoodsCnt;
	}
	public void setChangeGoodsCnt(Integer changeGoodsCnt) {
		this.changeGoodsCnt = changeGoodsCnt;
	}
	public Integer getMemberIdx() {
		return memberIdx;
	}
	public void setMemberIdx(Integer memberIdx) {
		this.memberIdx = memberIdx;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getGoodsIdx() {
		return goodsIdx;
	}
	public void setGoodsIdx(String goodsIdx) {
		this.goodsIdx = goodsIdx;
	}
	public String getGoodsIdxes() {
		return goodsIdxes;
	}
	public void setGoodsIdxes(String goodsIdxes) {
		this.goodsIdxes = goodsIdxes;
	}
	public Integer getGoodsCnt() {
		return goodsCnt;
	}
	public void setGoodsCnt(Integer goodsCnt) {
		this.goodsCnt = goodsCnt;
	}
	public Integer getCouponIdx() {
		return couponIdx;
	}
	public void setCouponIdx(Integer couponIdx) {
		this.couponIdx = couponIdx;
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
}
