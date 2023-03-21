package com.gxenSoft.mall.common.vo;

import org.apache.commons.lang.StringUtils;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : CommonVO
 * PACKAGE NM : com.gatsbyMall.common.vo
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 6. 14. 
 * HISTORY :
 
 *************************************
 */
public class CommonVO {
	
	private String statusFlag;										// I : insert U: update D:delete
	private String refererYn = "N";								//이전 페이지 호출여부
	private String refererUrl;
	private String gnbBrand;										// GNB 브랜드
	
	// user session정보
	private Integer memberIdx;
	private String memberId;
	private String email;
	private String memberPwd;
	private String saveMemberIdFlag;						// 아이디 저장여부
	private String memberLoginType;						   
	private String mandomSessionId;						// 비회원 로컬스토리지 세션 ID
	
	//공통 DB 컬럼
	private int regIdx;					// 작성자 일련번호
	private String regDt;				// 작성일자
	private int editIdx;					// 수정자 일련번호
	private String editDt;				// 수정일자
	private String visitIp;				// 방문 IP
	private String visitDt;				// 방문일자
	
	private String srcPathUrl;						// 유입경로 파라미터 URL
	private String srcPathSessionId;			// 유입경로 세션ID
	
	public String getStatusFlag() {
		return StringUtils.defaultString(statusFlag);
	}
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}
	public String getRefererYn() {
		return StringUtils.defaultString(refererYn);
	}
	public void setRefererYn(String refererYn) {
		this.refererYn = refererYn;
	}
	public String getRefererUrl() {
		return StringUtils.defaultString(refererUrl);
	}
	public void setRefererUrl(String refererUrl) {
		this.refererUrl = refererUrl;
	}
	public String getGnbBrand() {
		return gnbBrand;
	}
	public void setGnbBrand(String gnbBrand) {
		this.gnbBrand = gnbBrand;
	}
	public Integer getMemberIdx() {
		return memberIdx;
	}
	public void setMemberIdx(Integer memberIdx) {
		this.memberIdx = memberIdx;
	}
	public String getMemberId() {
		return StringUtils.defaultString(memberId);
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getEmail() {
		return StringUtils.defaultString(email);
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMemberPwd() {
		return StringUtils.defaultString(memberPwd);
	}
	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd;
	}
	public String getSaveMemberIdFlag() {
		return StringUtils.defaultString(saveMemberIdFlag);
	}
	public void setSaveMemberIdFlag(String saveMemberIdFlag) {
		this.saveMemberIdFlag = saveMemberIdFlag;
	}
	public String getMemberLoginType() {
		return StringUtils.defaultString(memberLoginType);
	}
	public void setMemberLoginType(String memberLoginType) {
		this.memberLoginType = memberLoginType;
	}
	public String getMandomSessionId() {
		return StringUtils.defaultString(mandomSessionId);
	}
	public void setMandomSessionId(String mandomSessionId) {
		this.mandomSessionId = mandomSessionId;
	}
	public int getRegIdx() {
		return regIdx;
	}
	public void setRegIdx(int regIdx) {
		this.regIdx = regIdx;
	}
	public String getRegDt() {
		return StringUtils.defaultString(regDt);
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public int getEditIdx() {
		return editIdx;
	}
	public void setEditIdx(int editIdx) {
		this.editIdx = editIdx;
	}
	public String getEditDt() {
		return StringUtils.defaultString(editDt);
	}
	public void setEditDt(String editDt) {
		this.editDt = editDt;
	}
	public String getVisitIp() {
		return StringUtils.defaultString(visitIp);
	}
	public void setVisitIp(String visitIp) {
		this.visitIp = visitIp;
	}
	public String getVisitDt() {
		return StringUtils.defaultString(visitDt);
	}
	public void setVisitDt(String visitDt) {
		this.visitDt = visitDt;
	}
	public String getSrcPathUrl() {
		return srcPathUrl;
	}
	public void setSrcPathUrl(String srcPathUrl) {
		this.srcPathUrl = srcPathUrl;
	}
	public String getSrcPathSessionId() {
		return srcPathSessionId;
	}
	public void setSrcPathSessionId(String srcPathSessionId) {
		this.srcPathSessionId = srcPathSessionId;
	}
}
