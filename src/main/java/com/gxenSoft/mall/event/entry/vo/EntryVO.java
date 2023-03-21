package com.gxenSoft.mall.event.entry.vo;

import org.apache.commons.lang.StringUtils;

import com.gxenSoft.mall.common.vo.CommonVO;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : EntryVO
 * PACKAGE NM : com.gxenSoft.mall.event.entry.vo
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 8. 22. 
 * HISTORY :
 
 *************************************
 */
public class EntryVO extends CommonVO {
	
	private int entryInfoIdx=0;								// 일련번호
	private String entryNo;										// 응모번호
	private String entryNm;										// 신청자명
	private String telNo;											// 전화번호
	private String birthDate;									// 생년월일
	private String zipCd;											// 우편번호 (신)
	private String addr;											// 도로명 주소
	private String oldZipCd;									// 우편번호 (구)
	private String oldAddr;										// 지번주소
	private String addrDetail;									// 상세주소
	private String gender;										// 성별
	private String purchaseType;							// 왁스 구입 타입
	
	public int getEntryInfoIdx() {
		return entryInfoIdx;
	}
	public void setEntryInfoIdx(int entryInfoIdx) {
		this.entryInfoIdx = entryInfoIdx;
	}
	public String getEntryNo() {
		return StringUtils.defaultString(entryNo);
	}
	public void setEntryNo(String entryNo) {
		this.entryNo = entryNo;
	}
	public String getEntryNm() {
		return StringUtils.defaultString(entryNm);
	}
	public void setEntryNm(String entryNm) {
		this.entryNm = entryNm;
	}
	public String getTelNo() {
		return StringUtils.defaultString(telNo);
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getBirthDate() {
		return StringUtils.defaultString(birthDate);
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getZipCd() {
		return StringUtils.defaultString(zipCd);
	}
	public void setZipCd(String zipCd) {
		this.zipCd = zipCd;
	}
	public String getAddr() {
		return StringUtils.defaultString(addr);
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getOldZipCd() {
		return StringUtils.defaultString(oldZipCd);
	}
	public void setOldZipCd(String oldZipCd) {
		this.oldZipCd = oldZipCd;
	}
	public String getOldAddr() {
		return StringUtils.defaultString(oldAddr);
	}
	public void setOldAddr(String oldAddr) {
		this.oldAddr = oldAddr;
	}
	public String getAddrDetail() {
		return StringUtils.defaultString(addrDetail);
	}
	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}
	public String getGender() {
		return StringUtils.defaultString(gender);
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPurchaseType() {
		return StringUtils.defaultString(purchaseType);
	}
	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}
}
