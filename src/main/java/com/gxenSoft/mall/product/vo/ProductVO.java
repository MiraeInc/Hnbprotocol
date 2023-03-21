package com.gxenSoft.mall.product.vo;

import org.apache.commons.lang.StringUtils;

import com.gxenSoft.mall.common.vo.CommonVO;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : ProductVO
 * PACKAGE NM : com.gxenSoft.mall.product.vo
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 6. 29. 
 * HISTORY :
 
 *************************************
 */
public class ProductVO extends CommonVO {
	
	private String goodsIdx;							// 상품 일련번호
	private String goodsCd;							// 상품 코드
	private String goodsNm;							// 상품명
	private String choiceCateIdx;					// 상품 카테고리 일련번호
	private String brandIdx;							// 브랜드 일련번호
	private String setFlag;								// 세트여부
	private String srcPath;								// 유입 경로
	private String srcInfo;								// 유입 경로 기타 정보
	private String adfrom;								// 광고 경로 TAG
	private String compareYn;						// 비교하기 flag
	private String reviewYn;							// 리뷰 클릭으로 넘어 왔는지 여부
	
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
	public String getGoodsNm() {
		return StringUtils.defaultString(goodsNm);
	}
	public void setGoodsNm(String goodsNm) {
		this.goodsNm = goodsNm;
	}
	public String getChoiceCateIdx() {
		return choiceCateIdx;
	}
	public void setChoiceCateIdx(String choiceCateIdx) {
		this.choiceCateIdx = choiceCateIdx;
	}
	public String getBrandIdx() {
		return brandIdx;
	}
	public void setBrandIdx(String brandIdx) {
		this.brandIdx = brandIdx;
	}
	public String getSetFlag() {
		return StringUtils.defaultString(setFlag);
	}
	public void setSetFlag(String setFlag) {
		this.setFlag = setFlag;
	}
	public String getSrcPath() {
		return srcPath;
	}
	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}
	public String getSrcInfo() {
		return srcInfo;
	}
	public void setSrcInfo(String srcInfo) {
		this.srcInfo = srcInfo;
	}
	public String getAdfrom() {
		return adfrom;
	}
	public void setAdfrom(String adfrom) {
		this.adfrom = adfrom;
	}
	public String getCompareYn() {
		return compareYn;
	}
	public void setCompareYn(String compareYn) {
		this.compareYn = compareYn;
	}
	public String getReviewYn() {
		return reviewYn;
	}
	public void setReviewYn(String reviewYn) {
		this.reviewYn = reviewYn;
	}
}
