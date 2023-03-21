package com.gxenSoft.mall.style.vo;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.gxenSoft.mall.common.vo.CommonVO;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : CounselVO
    * PACKAGE NM : com.gxenSoft.mall.style.vo
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 7. 13. 
    * HISTORY :   
    *
    *************************************
    */
public class CounselVO extends CommonVO {

	// 상담 신청
	private Integer counselIdx;	// 상담 일련번호
	private String qTitle;			// 제목
	private String qContents;		// 내용
	private String hairStyle;		// 모발 길이
	private String hairType;		// 모발 타입
	private String alarmYn;		// 답변 알람 받기 여부
	private String qImg1;   		 //질문이미지명
	private String qImg2;   
	private String qImg3;
	private String qImg4;
	private String qEmail;			// 질문자 이메일
	private Integer brandIdx;		// 브랜드 일련번호

	
	private List<String> qImg; //질문이미지 신규등록 목록
	
	public Integer getCounselIdx() {
		return counselIdx;
	}
	public void setCounselIdx(Integer counselIdx) {
		this.counselIdx = counselIdx;
	}
	public String getqTitle() {
		return StringUtils.defaultString(qTitle);
	}
	public void setqTitle(String qTitle) {
		this.qTitle = qTitle;
	}
	public String getqContents() {
		return StringUtils.defaultString(qContents);
	}
	public void setqContents(String qContents) {
		this.qContents = qContents;
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
	public String getAlarmYn() {
		return StringUtils.defaultString(alarmYn);
	}
	public void setAlarmYn(String alarmYn) {
		this.alarmYn = alarmYn;
	}
	public List<String> getqImg() {
		return qImg;
	}
	public void setqImg(List<String> qImg) {
		this.qImg = qImg;
	}
	public String getqImg1() {
		return StringUtils.defaultString(qImg1);
	}
	public void setqImg1(String qImg1) {
		this.qImg1 = qImg1;
	}
	public String getqImg2() {
		return StringUtils.defaultString(qImg2);
	}
	public void setqImg2(String qImg2) {
		this.qImg2 = qImg2;
	}
	public String getqImg3() {
		return StringUtils.defaultString(qImg3);
	}
	public void setqImg3(String qImg3) {
		this.qImg3 = qImg3;
	}
	public String getqImg4() {
		return StringUtils.defaultString(qImg4);
	}
	public void setqImg4(String qImg4) {
		this.qImg4 = qImg4;
	}
	public String getqEmail() {
		return StringUtils.defaultString(qEmail);
	}
	public void setqEmail(String qEmail) {
		this.qEmail = qEmail;
	}
	public Integer getBrandIdx() {
		return brandIdx;
	}
	public void setBrandIdx(Integer brandIdx) {
		this.brandIdx = brandIdx;
	}
	
}
