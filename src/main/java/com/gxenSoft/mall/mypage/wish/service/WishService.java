package com.gxenSoft.mall.mypage.wish.service;

import java.util.List;

import com.gxenSoft.mall.mypage.wish.vo.WishVO;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : WishService
    * PACKAGE NM : com.gxenSoft.mall.mypage.wish.service
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 8. 5. 
    * HISTORY :   
    *
    *************************************
    */
public interface WishService {

	/**
	    * @Method : getWishListCnt
	    * @Date: 2017. 8. 5.
	    * @Author : 임  재  형
	    * @Description	:	위시 리스트 총 개수
	   */
	int getWishListCnt(SearchVO schVO) throws Exception;

	/**
	    * @Method : getWishList
	    * @Date: 2017. 8. 5.
	    * @Author : 임  재  형
	    * @Description	:	위시 리스트
	   */
	List<SqlMap> getWishList(SearchVO schVO) throws Exception;

	/**
	    * @Method : wishDelete
	    * @Date: 2017. 8. 5.
	    * @Author : 임  재  형
	    * @Description	:	위시 삭제 (하트)
	   */
	int wishDelete(WishVO vo) throws Exception;

	/**
	    * @Method : wishDeleteAjax
	    * @Date: 2017. 8. 5.
	    * @Author : 임  재  형
	    * @Description	:	위시 삭제 (체크 박스)
	   */
	int wishDeleteAjax(WishVO vo) throws Exception;

}
