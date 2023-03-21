package com.gxenSoft.mall.etc.vo;

import org.apache.commons.lang.StringUtils;

   /**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : SearchConditionVO
    * PACKAGE NM : com.gxenSoft.mall.etc.vo
    * AUTHOR	 : 강 병 철
    * CREATED DATE  : 2017. 8. 1. 
    * HISTORY :   
    * 검색 조건 VO
    *************************************
    */	
public class SearchConditionVO {

	private String keyword = "";
	private String hairstyle10;
	private String hairstyle20;
	private String hairstyle30;
	private String hairstyle40;
	private String strong1;   // 강함
	private String strong2;  //중간
	private String strong3;  //약
	private String glossy1; //강
	private String glossy2; //중
	private String glossy3; //약
	private String hashtag;  //hashtag 일련번호 (안씀, 키워드와 통합)
	private String device;  // 디바이스 구분 A,P,M

	//상품 페이징
	private int pageNo = 1;						// 현재 페이지
	private int pageBlock = 16;  				// 표시 개수

	//기획전 페이징
	private int exhPageNo = 1;						// 현재 페이지
	private int exhPageBlock = 2;  				// 표시 개수

	//스타일팁 페이징
	private int tipPageNo = 1;						// 현재 페이지
	private int tipPageBlock = 3;  				// 표시 개수

	//후기 페이징
	private int reviewPageNo = 1;						// 현재 페이지
	private int reviewPageBlock = 4;  				// 표시 개수

	//광고 페이징
	private int adPageNo = 1;						// 현재 페이지
	private int adPageBlock = 2;  				// 표시 개수

	//매거진 페이징
	private int magazPageNo = 1;						// 현재 페이지
	private int magazPageBlock = 3;  				// 표시 개수
	
	public String getKeyword() {
		return StringUtils.defaultString(keyword);
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getHairstyle10() {
		return StringUtils.defaultString(hairstyle10);
	}
	public void setHairstyle10(String hairstyle10) {
		this.hairstyle10 = hairstyle10;
	}
	public String getHairstyle20() {
		return StringUtils.defaultString(hairstyle20);
	}
	public void setHairstyle20(String hairstyle20) {
		this.hairstyle20 = hairstyle20;
	}
	public String getHairstyle30() {
		return StringUtils.defaultString(hairstyle30);
	}
	public void setHairstyle30(String hairstyle30) {
		this.hairstyle30 = hairstyle30;
	}
	public String getHairstyle40() {
		return StringUtils.defaultString(hairstyle40);
	}
	public void setHairstyle40(String hairstyle40) {
		this.hairstyle40 = hairstyle40;
	}
	
	public String getHashtag() {
		return StringUtils.defaultString(hashtag);
	}
	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}


	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageBlock() {
		return pageBlock;
	}
	public void setPageBlock(int pageBlock) {
		this.pageBlock = pageBlock;
	}
	public String getStrong1() {
		return StringUtils.defaultString(strong1);
	}
	public void setStrong1(String strong1) {
		this.strong1 = strong1;
	}
	public String getStrong2() {
		return StringUtils.defaultString(strong2);
	}
	public void setStrong2(String strong2) {
		this.strong2 = strong2;
	}
	public String getStrong3() {
		return StringUtils.defaultString(strong3);
	}
	public void setStrong3(String strong3) {
		this.strong3 = strong3;
	}
	public String getGlossy1() {
		return StringUtils.defaultString(glossy1);
	}
	public void setGlossy1(String glossy1) {
		this.glossy1 = glossy1;
	}
	public String getGlossy2() {
		return StringUtils.defaultString(glossy2);
	}
	public void setGlossy2(String glossy2) {
		this.glossy2 = glossy2;
	}
	public String getGlossy3() {
		return StringUtils.defaultString(glossy3);
	}
	public void setGlossy3(String glossy3) {
		this.glossy3 = glossy3;
	}
	public int getExhPageNo() {
		return exhPageNo;
	}
	public void setExhPageNo(int exhPageNo) {
		this.exhPageNo = exhPageNo;
	}
	public int getExhPageBlock() {
		return exhPageBlock;
	}
	public void setExhPageBlock(int exhPageBlock) {
		this.exhPageBlock = exhPageBlock;
	}
	public String getDevice() {
		return StringUtils.defaultString(device);
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public int getTipPageNo() {
		return tipPageNo;
	}
	public void setTipPageNo(int tipPageNo) {
		this.tipPageNo = tipPageNo;
	}
	public int getTipPageBlock() {
		return tipPageBlock;
	}
	public void setTipPageBlock(int tipPageBlock) {
		this.tipPageBlock = tipPageBlock;
	}
	public int getReviewPageNo() {
		return reviewPageNo;
	}
	public void setReviewPageNo(int reviewPageNo) {
		this.reviewPageNo = reviewPageNo;
	}
	public int getReviewPageBlock() {
		return reviewPageBlock;
	}
	public void setReviewPageBlock(int reviewPageBlock) {
		this.reviewPageBlock = reviewPageBlock;
	}
	public int getAdPageNo() {
		return adPageNo;
	}
	public void setAdPageNo(int adPageNo) {
		this.adPageNo = adPageNo;
	}
	public int getAdPageBlock() {
		return adPageBlock;
	}
	public void setAdPageBlock(int adPageBlock) {
		this.adPageBlock = adPageBlock;
	}
	public int getMagazPageNo() {
		return magazPageNo;
	}
	public void setMagazPageNo(int magazPageNo) {
		this.magazPageNo = magazPageNo;
	}
	public int getMagazPageBlock() {
		return magazPageBlock;
	}
	public void setMagazPageBlock(int magazPageBlock) {
		this.magazPageBlock = magazPageBlock;
	}
}
