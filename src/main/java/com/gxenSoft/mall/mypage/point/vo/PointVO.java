package com.gxenSoft.mall.mypage.point.vo;

import org.apache.commons.lang.StringUtils;

import com.gxenSoft.mall.common.vo.CommonVO;


public class PointVO extends CommonVO {

	private String schType;		// 포인트 유형
	private String schStartDt;	// 시작일
	private String schEndDt;		// 종료일
	
	
	public String getSchType() {
		return StringUtils.defaultString(schType);
	}
	public void setSchType(String schType) {
		this.schType = schType;
	}
	public String getSchStartDt() {
		return StringUtils.defaultString(schStartDt);
	}
	public void setSchStartDt(String schStartDt) {
		this.schStartDt = schStartDt;
	}
	public String getSchEndDt() {
		return StringUtils.defaultString(schEndDt);
	}
	public void setSchEndDt(String schEndDt) {
		this.schEndDt = schEndDt;
	}
	
}
