package com.gxenSoft.mall.event.entry.vo;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.gxenSoft.mall.common.vo.CommonVO;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : CreatorVO
 * PACKAGE NM : com.gxenSoft.gatsbyMall.event.entry.vo
 * AUTHOR	 : 임  재  형
 * CREATED DATE  : 2019. 6. 17. 
 * HISTORY :
 
 *************************************
 */
public class CreatorVO extends CommonVO {
	
	private Integer creatorIdx;	// 크리에이터 일련번호
	private Integer eventIdx;		// 이벤트 일련번호
	private String memberNm;	// 이름,별명,닉네임
	private String age;				// 나이
	private String phoneNo;		// 연락처
	private String area;				// 지역
	private String introduce;		// 자기소개
	private String moment;		// 계기
	private String snsUrl;			// SNS 주소
	private CommonsMultipartFile videoNm;		// 동영상 파일
	private String videoUrl;		// 동영상 주소
	private String message;		// 메세지
	private String privacyYn;		// 개인정보동의 여부
	
	public Integer getCreatorIdx() {
		return creatorIdx;
	}
	public void setCreatorIdx(Integer creatorIdx) {
		this.creatorIdx = creatorIdx;
	}
	public Integer getEventIdx() {
		return eventIdx;
	}
	public void setEventIdx(Integer eventIdx) {
		this.eventIdx = eventIdx;
	}
	public String getMemberNm() {
		return StringUtils.defaultString(memberNm);
	}
	public void setMemberNm(String memberNm) {
		this.memberNm = memberNm;
	}
	public String getAge() {
		return StringUtils.defaultString(age);
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getPhoneNo() {
		return StringUtils.defaultString(phoneNo);
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getArea() {
		return StringUtils.defaultString(area);
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getIntroduce() {
		return StringUtils.defaultString(introduce);
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getMoment() {
		return StringUtils.defaultString(moment);
	}
	public void setMoment(String moment) {
		this.moment = moment;
	}
	public String getSnsUrl() {
		return StringUtils.defaultString(snsUrl);
	}
	public void setSnsUrl(String snsUrl) {
		this.snsUrl = snsUrl;
	}
	public CommonsMultipartFile getVideoNm() {
		return videoNm;
	}
	public void setVideoNm(CommonsMultipartFile videoNm) {
		this.videoNm = videoNm;
	}
	public String getVideoUrl() {
		return StringUtils.defaultString(videoUrl);
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public String getMessage() {
		return StringUtils.defaultString(message);
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPrivacyYn() {
		return StringUtils.defaultString(privacyYn);
	}
	public void setPrivacyYn(String privacyYn) {
		this.privacyYn = privacyYn;
	}
	
}
