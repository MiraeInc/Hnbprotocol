package com.gxenSoft.mall.order.vo;

public class OrderGoodsVO {

	private Integer goodsIdx;				// 상품 일련번호
	private Integer goodsCnt;				// 상품 갯수
	private Integer couponIdx;				// 자동 적용 쿠폰 일련번호
	
	private String setFlag;					// 세트상품 구분 (Y : 세트 상품, N : 일반상품)
	private Double discountPrice;			// 할인가
	
	public Integer getGoodsIdx() {
		return goodsIdx;
	}
	public void setGoodsIdx(Integer goodsIdx) {
		this.goodsIdx = goodsIdx;
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
	public String getSetFlag() {
		return setFlag;
	}
	public void setSetFlag(String setFlag) {
		this.setFlag = setFlag;
	}
	public Double getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}	
}
