package com.gxenSoft.mall.event.exhibition.vo;

import com.gxenSoft.mall.common.vo.CommonVO;


public class ExhibitionVO extends CommonVO {

	private Integer exhibitionIdx;	// 기획전 일련번호
	private Integer brandIdx;			// 브랜드 일련번호 [1:갸스비, 3:비페스타, 4:루시도엘, 6:마마버터, 7:덴탈프로, 8:찰리]
	
	
	public Integer getExhibitionIdx() {
		return exhibitionIdx;
	}

	public void setExhibitionIdx(Integer exhibitionIdx) {
		this.exhibitionIdx = exhibitionIdx;
	}

	public Integer getBrandIdx() {
		return brandIdx;
	}

	public void setBrandIdx(Integer brandIdx) {
		this.brandIdx = brandIdx;
	}
	
}
