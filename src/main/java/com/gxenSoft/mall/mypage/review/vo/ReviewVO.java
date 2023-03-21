package com.gxenSoft.mall.mypage.review.vo;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.gxenSoft.mall.common.vo.CommonVO;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : ReviewVO
 * PACKAGE NM : com.gxenSoft.mall.mypage.review.vo
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 7. 26. 
 * HISTORY :
 
 *************************************
 */
public class ReviewVO extends CommonVO {
	
	private int reviewIdx = 0;		// 리뷰 일련번호
	private Integer orderDetailIdx;// 주문 디테일 일련번호
	private String goodsIdx;			// 상품 일련번호	
	private String goodsCd;			// 상품 코드
	private Integer winnerIdx;		// 당첨자 일련번호
	private String rating;				// 별점
	private String hairStyle;			// 헤어 길이
	private String hairType;			// 헤어 타입
	private String title;					// 제목
	private String reviewDesc;		// 내용
	private String img1;				// 이미지1 [이 필드로 일반/포토 후기 판단 및 포인트 차감 여부 판단]
	private String img2;				// 이미지2
	private String img3;				// 이미지3
	private String reviewPoint;		// 리뷰 포인트
	private String layerType;			// 레이어 타입 ( 샘플,리뷰:review  /  상품:goods  /  주문:order )
	private String chkReview;        //구매확정시 리뷰 저장여부 Y (layerType = order일경우에만)
	
	private List<String> qImg; // 이미지 리스트
	
	private String reviewType;		// 상품 리뷰 TYPE
	

	public int getReviewIdx() {
		return reviewIdx;
	}
	public void setReviewIdx(int reviewIdx) {
		this.reviewIdx = reviewIdx;
	}
	public Integer getOrderDetailIdx() {
		return orderDetailIdx;
	}
	public void setOrderDetailIdx(Integer orderDetailIdx) {
		this.orderDetailIdx = orderDetailIdx;
	}
	public String getGoodsIdx() {
		return StringUtils.defaultString(goodsIdx);
	}
	public void setGoodsIdx(String goodsIdx) {
		this.goodsIdx = goodsIdx;
	}
	public String getGoodsCd() {
		return StringUtils.defaultString(goodsCd);
	}
	public void setGoodsCd(String goodsCd) {
		this.goodsCd = goodsCd;
	}
	public Integer getWinnerIdx() {
		return winnerIdx;
	}
	public void setWinnerIdx(Integer winnerIdx) {
		this.winnerIdx = winnerIdx;
	}
	public String getRating() {
		return StringUtils.defaultString(rating);
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getHairStyle() {
		return StringUtils.defaultString(hairStyle);
	}
	public void setHairStyle(String hairStyle) {
		this.hairStyle = hairStyle;
	}
	public String getHairType() {
		return StringUtils.defaultString(hairType);
	}
	public void setHairType(String hairType) {
		this.hairType = hairType;
	}
	public String getTitle() {
		return StringUtils.defaultString(title);
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getReviewDesc() {
		return StringUtils.defaultString(reviewDesc);
	}
	public void setReviewDesc(String reviewDesc) {
		this.reviewDesc = reviewDesc;
	}
	public String getImg1() {
		return StringUtils.defaultString(img1);
	}
	public void setImg1(String img1) {
		this.img1 = img1;
	}
	public String getImg2() {
		return StringUtils.defaultString(img2);
	}
	public void setImg2(String img2) {
		this.img2 = img2;
	}
	public String getImg3() {
		return StringUtils.defaultString(img3);
	}
	public void setImg3(String img3) {
		this.img3 = img3;
	}
	public List<String> getqImg() {
		return qImg;
	}
	public void setqImg(List<String> qImg) {
		this.qImg = qImg;
	}
	public String getReviewType() {
		return StringUtils.defaultString(reviewType);
	}
	public void setReviewType(String reviewType) {
		this.reviewType = reviewType;
	}
	public String getReviewPoint() {
		return StringUtils.defaultString(reviewPoint);
	}
	public void setReviewPoint(String reviewPoint) {
		this.reviewPoint = reviewPoint;
	}
	public String getLayerType() {
		return layerType;
	}
	public void setLayerType(String layerType) {
		this.layerType = layerType;
	}
	public String getChkReview() {
		return StringUtils.defaultString(chkReview);
	}
	public void setChkReview(String chkReview) {
		this.chkReview = chkReview;
	}
}
