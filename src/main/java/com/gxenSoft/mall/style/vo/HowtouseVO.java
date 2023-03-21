package com.gxenSoft.mall.style.vo;

import org.apache.commons.lang.StringUtils;

import com.gxenSoft.mall.common.vo.CommonVO;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : StyleTipVO
    * PACKAGE NM : com.gxenSoft.mall.style.vo
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 7. 14. 
    * HISTORY :   
    *
    *************************************
    */
public class HowtouseVO extends CommonVO {

	private Integer howtouseIdx;	// 상품 사용법 일련번호
	private String title;					// 제목
	private Integer brandIdx;			// 브랜드 일련번호
	
	
	public Integer getHowtouseIdx() {
		return howtouseIdx;
	}
	public void setHowtouseIdx(Integer howtouseIdx) {
		this.howtouseIdx = howtouseIdx;
	}
	public String getTitle() {
		return StringUtils.defaultString(title);
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getBrandIdx() {
		return brandIdx;
	}
	public void setBrandIdx(Integer brandIdx) {
		this.brandIdx = brandIdx;
	}
	
}
