package com.gxenSoft.mall.product.vo;

import org.apache.commons.lang.StringUtils;

import com.gxenSoft.util.page.SearchVO;

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
public class SchProductVO extends SearchVO {
	
	private String schCateIdx;									// 카데고리 IDX
	private String sch2depthCateIdx;						// 2depth 상위 카테고리 IDX
	private String sch3depthCateIdx;						// 3depth 상위 카테고리 IDX
	private String schCateFlag;							
	private String schOrderGubun;						// 상품 정렬 
	private String schGbn = "";	//카테고리구분 (T : 통합카테고리)
	
	
	public String getSchCateIdx() {
		return StringUtils.defaultString(schCateIdx);
	}
	public void setSchCateIdx(String schCateIdx) {
		this.schCateIdx = schCateIdx;
	}
	public String getSch2depthCateIdx() {
		return StringUtils.defaultString(sch2depthCateIdx);
	}
	public void setSch2depthCateIdx(String sch2depthCateIdx) {
		this.sch2depthCateIdx = sch2depthCateIdx;
	}
	public String getSch3depthCateIdx() {
		return StringUtils.defaultString(sch3depthCateIdx);
	}
	public void setSch3depthCateIdx(String sch3depthCateIdx) {
		this.sch3depthCateIdx = sch3depthCateIdx;
	}
	public String getSchCateFlag() {
		return StringUtils.defaultString(schCateFlag);
	}
	public void setSchCateFlag(String schCateFlag) {
		this.schCateFlag = schCateFlag;
	}
	public String getSchOrderGubun() {
		return StringUtils.defaultString(schOrderGubun);
	}
	public void setSchOrderGubun(String schOrderGubun) {
		this.schOrderGubun = schOrderGubun;
	}
	public String getSchGbn() {
		return schGbn;
	}
	public void setSchGbn(String schGbn) {
		this.schGbn = schGbn;
	}
}
