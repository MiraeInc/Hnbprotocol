package com.gxenSoft.mall.brand.vo;

import org.apache.commons.lang.StringUtils;

import com.gxenSoft.util.page.SearchVO;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : BrandVO
 * PACKAGE NM : com.gxenSoft.mall.brand.vo
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 7. 28. 
 * HISTORY :
 
 *************************************
 */
public class BrandVO extends SearchVO {
	
	private int magazineIdx = 0;		// 매거진 일련번호
	private String deviceGubun;		// 디바이스 구분
	private Integer brandIdx;			// 브랜드 일련번호

	public int getMagazineIdx() {
		return magazineIdx;
	}
	public void setMagazineIdx(int magazineIdx) {
		this.magazineIdx = magazineIdx;
	}
	public String getDeviceGubun() {
		return StringUtils.defaultString(deviceGubun);
	}
	public void setDeviceGubun(String deviceGubun) {
		this.deviceGubun = deviceGubun;
	}
	public Integer getBrandIdx() {
		return brandIdx;
	}
	public void setBrandIdx(Integer brandIdx) {
		this.brandIdx = brandIdx;
	}
}
