package com.gxenSoft.mall.member.vo;

import org.apache.commons.lang.StringUtils;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : MemberVO
 * PACKAGE NM : com.gatsbyMall.member.vo
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 6. 14. 
 * HISTORY :
 
 *************************************
 */
public class MemberVO extends AddressVO {
	
	private String memberNm;										// 이름
	private String gender;												// 성별
	private String phoneNo;											// 휴대폰
	private String birthDate;											// 생년월일
	private String subscribeType;									// 가입경로
	private String subscribe;											// 가입경로 기타 입력
	private String recommender;									// 추천인
	private String recommenderIdx;								// 추천인 일련번호
	private String memberState;										// 회원상태
	private String levelIdx;												// 회원등급 일련번호
	private String joinType;												// 가입구분
	private String marketingYn;										// 마케팅 정보 활용 동의 여부
	private String individualInfoYn;								// 개인정보 제공 여부
	private String smsYn;												// SMS 수신 여부
	private String emailYn;												// 이메일 수신 여부
	private String smsYnOrg;											// SMS 수신 여부 org
	private String emailYnOrg;											// 이메일 수신 여부 org
	private String httpUserAgent;									// USER_AGENT 
	private String device;													// 디바이스
	private String snsCd;													// SNS KEY
	private String snsType;												// SNS 타입
	private String snsTypeNm;										// SNS 타입명
	private String snsMode;											// SNS MODE
	private String snsEmail;												// SNS EMAIL
	private String gradeNm;											// 회원등급명
	private String reason;												// 탈퇴사유
	private String etcReason;											// 탈퇴사유(기타)
	private String memberFlag;										// 회원 로그인/비회원 로그인 여부 (Y : 회원 로그인, N : 비회원 로그인 (주문조회), null : 로그인 하지 않은 비회원)
	private String fromOrderFlag;									// 비회원 구매 여부  
	private String orderGoodsInfoListStr;						// 주문한 장바구니 정보 문자열
	private String sessionId;											// 비회원 세션 ID
	private String compareFlag;										// 이벤트 flag 
	
	// 환불계좌
	private String bankCode;											// 은행코드
	private String account;												// 계좌번호
	private String depositor;											// 예금주
	
	// 비밀번호찾기 (휴대폰, 이메일) 구분
	private String findType;	// 휴대폰, 이메일 구분 (1,2)
	
	public String getMemberNm() {
		return StringUtils.defaultString(memberNm);
	}
	public void setMemberNm(String memberNm) {
		this.memberNm = memberNm;
	}
	public String getGender() {
		return StringUtils.defaultString(gender);
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhoneNo() {
		return StringUtils.defaultString(phoneNo);
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getBirthDate() {
		return StringUtils.defaultString(birthDate);
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getSubscribeType() {
		return StringUtils.defaultString(subscribeType);
	}
	public void setSubscribeType(String subscribeType) {
		this.subscribeType = subscribeType;
	}
	public String getSubscribe() {
		return StringUtils.defaultString(subscribe);
	}
	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}
	public String getRecommender() {
		return StringUtils.defaultString(recommender);
	}
	public void setRecommender(String recommender) {
		this.recommender = recommender;
	}
	public String getRecommenderIdx() {
		return recommenderIdx;
	}
	public void setRecommenderIdx(String recommenderIdx) {
		this.recommenderIdx = recommenderIdx;
	}
	public String getLevelIdx() {
		return StringUtils.defaultString(levelIdx);
	}
	public void setLevelIdx(String levelIdx) {
		this.levelIdx = levelIdx;
	}
	public String getJoinType() {
		return StringUtils.defaultString(joinType);
	}
	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}
	public String getMemberState() {
		return StringUtils.defaultString(memberState);
	}
	public void setMemberState(String memberState) {
		this.memberState = memberState;
	}
	public String getMarketingYn() {
		return StringUtils.defaultString(marketingYn);
	}
	public void setMarketingYn(String marketingYn) {
		this.marketingYn = marketingYn;
	}
	public String getIndividualInfoYn() {
		return StringUtils.defaultString(individualInfoYn);
	}
	public void setIndividualInfoYn(String individualInfoYn) {
		this.individualInfoYn = individualInfoYn;
	}
	public String getSmsYn() {
		return StringUtils.defaultString(smsYn);
	}
	public void setSmsYn(String smsYn) {
		this.smsYn = smsYn;
	}
	public String getEmailYn() {
		return StringUtils.defaultString(emailYn);
	}
	public void setEmailYn(String emailYn) {
		this.emailYn = emailYn;
	}
	public String getSmsYnOrg() {
		return smsYnOrg;
	}
	public void setSmsYnOrg(String smsYnOrg) {
		this.smsYnOrg = StringUtils.defaultString(smsYnOrg);
	}
	public String getEmailYnOrg() {
		return emailYnOrg;
	}
	public void setEmailYnOrg(String emailYnOrg) {
		this.emailYnOrg = StringUtils.defaultString(emailYnOrg);
	}
	public String getHttpUserAgent() {
		return StringUtils.defaultString(httpUserAgent);
	}
	public void setHttpUserAgent(String httpUserAgent) {
		this.httpUserAgent = httpUserAgent;
	}
	public String getDevice() {
		return StringUtils.defaultString(device);
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getSnsCd() {
		return StringUtils.defaultString(snsCd);
	}
	public void setSnsCd(String snsCd) {
		this.snsCd = snsCd;
	}
	public String getSnsType() {
		return StringUtils.defaultString(snsType);
	}
	public void setSnsType(String snsType) {
		this.snsType = snsType;
	}
	public String getSnsTypeNm() {
		return StringUtils.defaultString(snsTypeNm);
	}
	public void setSnsTypeNm(String snsTypeNm) {
		this.snsTypeNm = snsTypeNm;
	}
	public String getSnsMode() {
		return StringUtils.defaultString(snsMode);
	}
	public void setSnsMode(String snsMode) {
		this.snsMode = snsMode;
	}
	public String getSnsEmail() {
		return StringUtils.defaultString(snsEmail);
	}
	public void setSnsEmail(String snsEmail) {
		this.snsEmail = snsEmail;
	}
	public String getGradeNm() {
		return gradeNm;
	}
	public void setGradeNm(String gradeNm) {
		this.gradeNm = gradeNm;
	}
	public String getReason() {
		return StringUtils.defaultString(reason);
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getEtcReason() {
		return StringUtils.defaultString(etcReason);
	}
	public void setEtcReason(String etcReason) {
		this.etcReason = etcReason;
	}
	public String getMemberFlag() {
		return memberFlag;
	}
	public void setMemberFlag(String memberFlag) {
		this.memberFlag = memberFlag;
	}
	public String getFromOrderFlag() {
		return StringUtils.defaultString(fromOrderFlag);
	}
	public void setFromOrderFlag(String fromOrderFlag) {
		this.fromOrderFlag = fromOrderFlag;
	}
	public String getOrderGoodsInfoListStr() {
		return StringUtils.defaultString(orderGoodsInfoListStr);
	}
	public void setOrderGoodsInfoListStr(String orderGoodsInfoListStr) {
		this.orderGoodsInfoListStr = orderGoodsInfoListStr;
	}
	public String getCompareFlag() {
		return compareFlag;
	}
	public void setCompareFlag(String compareFlag) {
		this.compareFlag = compareFlag;
	}
	public String getSessionId() {
		return StringUtils.defaultString(sessionId);
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getBankCode() {
		return StringUtils.defaultString(bankCode);
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getAccount() {
		return StringUtils.defaultString(account);
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getDepositor() {
		return StringUtils.defaultString(depositor);
	}
	public void setDepositor(String depositor) {
		this.depositor = depositor;
	}
	public String getFindType() {
		return StringUtils.defaultString(findType);
	}
	public void setFindType(String findType) {
		this.findType = findType;
	}
}
