package com.gxenSoft.mall.mypage.inquiry.vo;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.gxenSoft.mall.common.vo.CommonVO;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : InquiryVO
    * PACKAGE NM : com.gxenSoft.mall.mypage.inquiry.vo
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 8. 3. 
    * HISTORY :   
    *
    *************************************
    */
public class InquiryVO extends CommonVO {

	private Integer inquiryIdx;	// 1:1 문의 일련번호
	private String schType;		// 문의 유형
	private String schStartDt;	// 시작일
	private String schEndDt;		// 종료일
	private String inquiryType;	// 문의 유형
	
	private Integer orderIdx;				// 질문검색 주문번호
	private Integer orderDetailIdx;		// 질문검색 주문 디테일 번호
	private String orderCd;					// 주문검색 주문 코드 (검색-hidden 있을 경우)
	private String orderGoodsNm;			// 주문검색 상품 명 (검색-hidden 있을 경우)
	private String userOrderCd;				// 주문검색 주문 코드 (검색-hidden 없을 경우)
	private String userOrderGoodsNm;	// 주문검색 상품 명 (검색-hidden 없을 경우)
	
	private Integer questnGoodsIdx;		// 상품검색 상품 일련번호
	private String questnGoodsNm;		// 상품검색 상품 명 (검색-hidden 있을 경우)
	private String userQuestnGoodsNm;	// 상품검색 상품 명 (검색-hidden 없을 경우) 
	
	private String nomemberOrderCd;	// 비회원 주문 코드
	private String questnPhone;		// 작성자 핸드폰 번호
	private String questnEmail;		// 작성자 이메일
	private String questnTitle;	// 질문 제목
	private String questnDesc;	// 질문 내용
	private String phoneSendYn;	// 문자 수신 여부
	private String qImg1;	
	private String qImg2;
	private String qImg3;
	private String qImg4;
	
	private List<String> qImg; // 질문이미지 신규등록 목록
	
	
	public Integer getInquiryIdx() {
		return inquiryIdx;
	}
	public void setInquiryIdx(Integer inquiryIdx) {
		this.inquiryIdx = inquiryIdx;
	}
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
	public String getInquiryType() {
		return StringUtils.defaultString(inquiryType);
	}
	public void setInquiryType(String inquiryType) {
		this.inquiryType = inquiryType;
	}
	public Integer getOrderIdx() {
		return orderIdx;
	}
	public void setOrderIdx(Integer orderIdx) {
		this.orderIdx = orderIdx;
	}
	public Integer getOrderDetailIdx() {
		return orderDetailIdx;
	}
	public void setOrderDetailIdx(Integer orderDetailIdx) {
		this.orderDetailIdx = orderDetailIdx;
	}
	public String getOrderCd() {
		return StringUtils.defaultString(orderCd);
	}
	public void setOrderCd(String orderCd) {
		this.orderCd = orderCd;
	}
	public String getOrderGoodsNm() {
		return StringUtils.defaultString(orderGoodsNm);
	}
	public void setOrderGoodsNm(String orderGoodsNm) {
		this.orderGoodsNm = orderGoodsNm;
	}
	public String getUserOrderCd() {
		return StringUtils.defaultString(userOrderCd);
	}
	public void setUserOrderCd(String userOrderCd) {
		this.userOrderCd = userOrderCd;
	}
	public String getUserOrderGoodsNm() {
		return StringUtils.defaultString(userOrderGoodsNm);
	}
	public void setUserOrderGoodsNm(String userOrderGoodsNm) {
		this.userOrderGoodsNm = userOrderGoodsNm;
	}
	public Integer getQuestnGoodsIdx() {
		return questnGoodsIdx;
	}
	public void setQuestnGoodsIdx(Integer questnGoodsIdx) {
		this.questnGoodsIdx = questnGoodsIdx;
	}
	public String getQuestnGoodsNm() {
		return StringUtils.defaultString(questnGoodsNm);
	}
	public void setQuestnGoodsNm(String questnGoodsNm) {
		this.questnGoodsNm = questnGoodsNm;
	}
	public String getUserQuestnGoodsNm() {
		return StringUtils.defaultString(userQuestnGoodsNm);
	}
	public void setUserQuestnGoodsNm(String userQuestnGoodsNm) {
		this.userQuestnGoodsNm = userQuestnGoodsNm;
	}
	public String getNomemberOrderCd() {
		return StringUtils.defaultString(nomemberOrderCd);
	}
	public void setNomemberOrderCd(String nomemberOrderCd) {
		this.nomemberOrderCd = nomemberOrderCd;
	}
	public String getQuestnPhone() {
		return StringUtils.defaultString(questnPhone);
	}
	public void setQuestnPhone(String questnPhone) {
		this.questnPhone = questnPhone;
	}
	public String getQuestnEmail() {
		return StringUtils.defaultString(questnEmail);
	}
	public void setQuestnEmail(String questnEmail) {
		this.questnEmail = questnEmail;
	}
	public String getQuestnTitle() {
		return StringUtils.defaultString(questnTitle);
	}
	public void setQuestnTitle(String questnTitle) {
		this.questnTitle = questnTitle;
	}
	public String getQuestnDesc() {
		return StringUtils.defaultString(questnDesc);
	}
	public void setQuestnDesc(String questnDesc) {
		this.questnDesc = questnDesc;
	}
	public String getPhoneSendYn() {
		return StringUtils.defaultString(phoneSendYn);
	}
	public void setPhoneSendYn(String phoneSendYn) {
		this.phoneSendYn = phoneSendYn;
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
	public List<String> getqImg() {
		return qImg;
	}
	public void setqImg(List<String> qImg) {
		this.qImg = qImg;
	}
	
}
