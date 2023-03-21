package com.gxenSoft.mall.style.vo;


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
public class TipVO extends CommonVO {

	private Integer tipIdx;		// 팁 일련번호
	private Integer brandIdx;	// 브랜드 일련번호
	
	
	public Integer getTipIdx() {
		return tipIdx;
	}
	public void setTipIdx(Integer tipIdx) {
		this.tipIdx = tipIdx;
	}
	public Integer getBrandIdx() {
		return brandIdx;
	}
	public void setBrandIdx(Integer brandIdx) {
		this.brandIdx = brandIdx;
	}
	
}
