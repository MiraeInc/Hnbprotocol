package com.gxenSoft.mall.member.vo;

import org.apache.commons.lang.StringUtils;

import com.gxenSoft.mall.common.vo.CommonVO;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : AddressVO
 * PACKAGE NM : com.gxenSoft.mall.member.vo
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 8. 8. 
 * HISTORY :
 
 *************************************
 */
public class AddressVO extends CommonVO {
	
	private int addressIdx = 0;							// 주소 일련번호
	private String shippingNm;							// 배송지명
	private String receiverNm;  							// 받는사람 이름
	private String zipCd;										// 우편번호 (신)
	private String oldZipCd;								// 우편번호 (구)
	private String addrDetail;								// 상세주소
	private String addr;										// 도로명 주소
	private String oldAddr;									// 지번주소
	private String telNo;										// 전화번호
	private String defaultYn;								// 기본 배송지 여부
	private String regHttpUserAgent;					// 작성자 user_agent
	private String regIp;										// 작성자 IP
	private String editHttpUserAgent;				// 수정자 user_agent
	private String editIp;										// 수정자 IP
	
	public int getAddressIdx() {
		return addressIdx;
	}
	public void setAddressIdx(int addressIdx) {
		this.addressIdx = addressIdx;
	}
	public String getShippingNm() {
		return StringUtils.defaultString(shippingNm);
	}
	public void setShippingNm(String shippingNm) {
		this.shippingNm = shippingNm;
	}
	public String getReceiverNm() {
		return StringUtils.defaultString(receiverNm);
	}
	public void setReceiverNm(String receiverNm) {
		this.receiverNm = receiverNm;
	}
	public String getZipCd() {
		return StringUtils.defaultString(zipCd);
	}
	public void setZipCd(String zipCd) {
		this.zipCd = zipCd;
	}
	public String getOldZipCd() {
		return StringUtils.defaultString(oldZipCd);
	}
	public void setOldZipCd(String oldZipCd) {
		this.oldZipCd = oldZipCd;
	}
	public String getAddrDetail() {
		return StringUtils.defaultString(addrDetail);
	}
	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}
	public String getAddr() {
		return StringUtils.defaultString(addr);
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getOldAddr() {
		return StringUtils.defaultString(oldAddr);
	}
	public void setOldAddr(String oldAddr) {
		this.oldAddr = oldAddr;
	}
	public String getTelNo() {
		return StringUtils.defaultString(telNo);
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getDefaultYn() {
		return StringUtils.defaultString(defaultYn);
	}
	public void setDefaultYn(String defaultYn) {
		this.defaultYn = defaultYn;
	}
	public String getRegHttpUserAgent() {
		return StringUtils.defaultString(regHttpUserAgent);
	}
	public void setRegHttpUserAgent(String regHttpUserAgent) {
		this.regHttpUserAgent = regHttpUserAgent;
	}
	public String getRegIp() {
		return StringUtils.defaultString(regIp);
	}
	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}
	public String getEditHttpUserAgent() {
		return StringUtils.defaultString(editHttpUserAgent);
	}
	public void setEditHttpUserAgent(String editHttpUserAgent) {
		this.editHttpUserAgent = editHttpUserAgent;
	}
	public String getEditIp() {
		return StringUtils.defaultString(editIp);
	}
	public void setEditIp(String editIp) {
		this.editIp = editIp;
	}
}
