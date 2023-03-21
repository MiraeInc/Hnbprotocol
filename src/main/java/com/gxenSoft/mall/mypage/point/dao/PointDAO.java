package com.gxenSoft.mall.mypage.point.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gxenSoft.sqlDAO.CommonDefaultDAO;
import com.gxenSoft.sqlMap.SqlMap;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : PointDAO
    * PACKAGE NM : com.gxenSoft.mall.mypage.point.dao
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 8. 4. 
    * HISTORY :   
    *
    *************************************
    */
@Repository("pointDAO")
public class PointDAO extends CommonDefaultDAO{

	/**
	    * @Method : getPointListCnt
	    * @Date: 2017. 8. 4.
	    * @Author : 임  재  형
	    * @Description	:	포인트 리스트 총 개수
	   */
	public int getPointListCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("pointDAO.getPointListCnt", param);
	}
	
	/**
	    * @Method : getPointList
	    * @Date: 2017. 8. 4.
	    * @Author : 임  재  형
	    * @Description	:	포인트 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getPointList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("pointDAO.getPointList", param);
	}

	/**
	    * @Method : getSumPoint
	    * @Date: 2017. 8. 4.
	    * @Author : 임  재  형
	    * @Description	:	페이지 적용 포인트 합
	   */
	public int getSumPoint(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("pointDAO.getSumPoint", param);
	}

	/**
	    * @Method : getTotalPoint
	    * @Date: 2017. 8. 4.
	    * @Author : 임  재  형
	    * @Description	:	보유 포인트
	   */
	public int getTotalPoint(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("pointDAO.getTotalPoint", param);
	}

	/**
	    * @Method : getSpPointDeduct
	    * @Date: 2017. 8. 4.
	    * @Author : 임  재  형
	    * @Description	:	포인트 조회 프로시져
	   */
	public SqlMap getSpPointDeduct(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("pointDAO.getSpPointDeduct", param);
	}

	/**
	    * @Method : getUsableCouponCnt
	    * @Date: 2017. 8. 4.
	    * @Author :  강 병 철
	    * @Description	:	사용가능한 리스트 총 개수
	   */
	public int getUsableCouponCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("pointDAO.getUsableCouponCnt", param);
	}
	
	/**
	    * @Method : getUsableCouponList
	    * @Date: 2017. 8. 4.
	    * @Author :  강 병 철
	    * @Description	:	사용가능한 리스트 총 개수
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getUsableCouponList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("pointDAO.getUsableCouponList", param);
	}


	/**
	    * @Method : getSevenExpireCouponCnt
	    * @Date: 2017. 8. 4.
	    * @Author :  강 병 철
	    * @Description	:	7일내 만료 리스트 총 개수
	   */
	public int getSevenExpireCouponCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("pointDAO.getSevenExpireCouponCnt", param);
	}
	
	/**
	    * @Method : getSevenExpireCouponList
	    * @Date: 2017. 8. 4.
	    * @Author :  강 병 철
	    * @Description	:	7일이내 만료 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getSevenExpireCouponList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("pointDAO.getSevenExpireCouponList", param);
	}

	/**
	    * @Method : getExpireCouponCnt
	    * @Date: 2017. 8. 4.
	    * @Author :  강 병 철
	    * @Description	:	만료 총 개수
	   */
	public int getExpireCouponCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("pointDAO.getExpireCouponCnt", param);
	}
	
	/**
	    * @Method : getExpireCouponList
	    * @Date: 2017. 8. 4.
	    * @Author :  강 병 철
	    * @Description	:	만료 총 개수
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getExpireCouponList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("pointDAO.getExpireCouponList", param);
	}

	
}
