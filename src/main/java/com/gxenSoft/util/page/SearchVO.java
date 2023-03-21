package com.gxenSoft.util.page;

import org.apache.commons.lang.StringUtils;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : SearchVO
 * PACKAGE NM : com.gxenSoft.util.page
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 7. 11. 
 * HISTORY :
 
 *************************************
 */
public class SearchVO {
	
	// 검색 공통 변수
	private String schType = "";
	private String schSubType = "";
	private String schKeyword = "";
	private String schYear = "";
	private String schStartDt = "";
	private String schEndDt = "";
	private String schUseYn = "";
	private String schValue = "";
	private String schStatus = "";
	private String schWriter = "";
	private String schApprover = "";
	private String schOrderText = "";  
	private String schStartCnt = "";
	private String schEndCnt = "";
	private String schCheck = "";
	
	private String schMode = "";  // 검색모드 여부 ("search" 이면 검색) kkang 추가
	
	//페이징 공통 변수
	private int pageNo = 1;						// 현재 페이지
	private int pageSize = 5;  					// 페이지 수
	private int pageBlock = 12;				// 게시글 수
	
	public String getSchStartCnt() {
		return StringUtils.defaultString(schStartCnt);
	}
	public void setSchStartCnt(String schStartCnt) {
		this.schStartCnt = schStartCnt;
	}
	public String getSchEndCnt() {
		return StringUtils.defaultString(schEndCnt);
	}
	public void setSchEndCnt(String schEndCnt) {
		this.schEndCnt = schEndCnt;
	}
	public String getSchOrderText() {
		return StringUtils.defaultString(schOrderText);
	}
	public void setSchOrderText(String schOrderText) {
		this.schOrderText = schOrderText;
	}
	public String getSchType() {
		return StringUtils.defaultString(schType);
	}
	public void setSchType(String schType) {
		this.schType = schType;
	}
	public String getSchSubType() {
		return StringUtils.defaultString(schSubType);
	}
	public void setSchSubType(String schSubType) {
		this.schSubType = schSubType;
	}
	public String getSchKeyword() {
		return StringUtils.defaultString(schKeyword);
	}
	public void setSchKeyword(String schKeyword) {
		this.schKeyword = schKeyword;
	}
	public String getSchYear() {
		return StringUtils.defaultString(schYear);
	}
	public void setSchYear(String schYear) {
		this.schYear = schYear;
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
	public String getSchUseYn() {
		return StringUtils.defaultString(schUseYn);
	}
	public void setSchUseYn(String schUseYn) {
		this.schUseYn = schUseYn;
	}
	public String getSchValue() {
		return StringUtils.defaultString(schValue);
	}
	public void setSchValue(String schValue) {
		this.schValue = schValue;
	}
	public String getSchStatus() {
		return StringUtils.defaultString(schStatus);
	}
	public void setSchStatus(String schStatus) {
		this.schStatus = schStatus;
	}
	public String getSchWriter() {
		return StringUtils.defaultString(schWriter);
	}
	public void setSchWriter(String schWriter) {
		this.schWriter = schWriter;
	}
	public String getSchApprover() {
		return StringUtils.defaultString(schApprover);
	}
	public void setSchApprover(String schApprover) {
		this.schApprover = schApprover;
	}
	public String getSchMode() {
		return StringUtils.defaultString(schMode);
	}
	public void setSchMode(String schMode) {
		this.schMode = schMode;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageBlock() {
		return pageBlock;
	}
	public void setPageBlock(int pageBlock) {
		this.pageBlock = pageBlock;
	}
	public String getSchCheck() {
		return StringUtils.defaultString(schCheck);
	}
	public void setSchCheck(String schCheck) {
		this.schCheck = schCheck;
	}
}
