package com.gxenSoft.mall.mypage.wish.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gxenSoft.sqlDAO.CommonDefaultDAO;
import com.gxenSoft.sqlMap.SqlMap;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : WishDAO
    * PACKAGE NM : com.gxenSoft.mall.mypage.wish.dao
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 8. 5. 
    * HISTORY :   
    *
    *************************************
    */
@Repository("wishDAO")
public class WishDAO extends CommonDefaultDAO{

	/**
	    * @Method : getWishListCnt
	    * @Date: 2017. 8. 5.
	    * @Author : 임  재  형
	    * @Description	:	위시 리스트 총 개수
	   */
	public int getWishListCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("wishDAO.getWishListCnt", param);
	}

	/**
	    * @Method : getWishList
	    * @Date: 2017. 8. 5.
	    * @Author : 임  재  형
	    * @Description	:	위시 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getWishList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("wishDAO.getWishList", param);
	}

	/**
	    * @Method : wishDelete
	    * @Date: 2017. 8. 5.
	    * @Author : 임  재  형
	    * @Description	:	위시 삭제
	   */
	public int wishDelete(HashMap<String, Object> param) throws Exception {
		return (Integer)delete("wishDAO.wishDelete", param);
	}
	
}
