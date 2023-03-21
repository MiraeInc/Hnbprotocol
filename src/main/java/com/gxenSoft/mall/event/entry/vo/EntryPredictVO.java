package com.gxenSoft.mall.event.entry.vo;

import org.apache.commons.lang.StringUtils;

import com.gxenSoft.mall.common.vo.CommonVO;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : EntryPredictVO
 * PACKAGE NM : com.gxenSoft.gatsbyMall.event.entry.vo
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2018. 1. 22. 
 * HISTORY :
 
 *************************************
 */
public class EntryPredictVO extends CommonVO {
	
	private int predictInfoIdx=0;								// 일련번호
	private String cp;												// 코드페이지
	private String pyear;											// 태어난 년도
	private String pmonth;										// 태어난 월
	private String pday;											// 태어난 일
	private String phour;											// 태어난 시
	private String pminute;										// 태어난 분
	private String pleap;											// 태어난 해
	private String pgender;										// 성별
	private String device;											// 디바이스
	
	public int getPredictInfoIdx() {
		return predictInfoIdx;
	}
	public void setPredictInfoIdx(int predictInfoIdx) {
		this.predictInfoIdx = predictInfoIdx;
	}
	public String getCp() {
		return StringUtils.defaultString(cp);
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
	public String getPyear() {
		return StringUtils.defaultString(pyear);
	}
	public void setPyear(String pyear) {
		this.pyear = pyear;
	}
	public String getPmonth() {
		return StringUtils.defaultString(pmonth);
	}
	public void setPmonth(String pmonth) {
		this.pmonth = pmonth;
	}
	public String getPday() {
		return StringUtils.defaultString(pday);
	}
	public void setPday(String pday) {
		this.pday = pday;
	}
	public String getPhour() {
		return StringUtils.defaultString(phour);
	}
	public void setPhour(String phour) {
		this.phour = phour;
	}
	public String getPminute() {
		return StringUtils.defaultString(pminute);
	}
	public void setPminute(String pminute) {
		this.pminute = pminute;
	}
	public String getPleap() {
		return StringUtils.defaultString(pleap);
	}
	public void setPleap(String pleap) {
		this.pleap = pleap;
	}
	public String getPgender() {
		return StringUtils.defaultString(pgender);
	}
	public void setPgender(String pgender) {
		this.pgender = pgender;
	}
	public String getDevice() {
		return StringUtils.defaultString(device);
	}
	public void setDevice(String device) {
		this.device = device;
	}
}
