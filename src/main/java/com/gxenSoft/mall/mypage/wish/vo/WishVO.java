package com.gxenSoft.mall.mypage.wish.vo;

import org.apache.commons.lang.StringUtils;

import com.gxenSoft.mall.common.vo.CommonVO;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : WishVO
    * PACKAGE NM : com.gxenSoft.mall.mypage.wish.vo
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 8. 5. 
    * HISTORY :   
    *
    *************************************
    */
public class WishVO extends CommonVO {

	private Integer wishIdx;			// 위시 일련번호
	private Integer memberIdx;	// 회원 일련번호
	private String wishListIdx;			// 위시 일련번호 리스트
	
	
	public Integer getWishIdx() {
		return wishIdx;
	}
	public void setWishIdx(Integer wishIdx) {
		this.wishIdx = wishIdx;
	}
	public Integer getMemberIdx() {
		return memberIdx;
	}
	public void setMemberIdx(Integer memberIdx) {
		this.memberIdx = memberIdx;
	}
	public String getWishListIdx() {
		return StringUtils.defaultString(wishListIdx);
	}
	public void setWishListIdx(String wishListIdx) {
		this.wishListIdx = wishListIdx;
	}
	
}
