package com.gxenSoft.mall.mypage.point.service;

import java.util.List;

import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : PointService
    * PACKAGE NM : com.gxenSoft.mall.mypage.point.service
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 8. 4. 
    * HISTORY :   
    *
    *************************************
    */
public interface PointService {

	/**
	    * @Method : getPointListCnt
	    * @Date: 2017. 8. 4.
	    * @Author : 임  재  형
	    * @Description	:	포인트 리스트 총 개수
	   */
	int getPointListCnt(SearchVO schVO) throws Exception;
	
	/**
	    * @Method : getPointList
	    * @Date: 2017. 8. 4.
	    * @Author : 임  재  형
	    * @Description	:	포인트 리스트
	   */
	List<SqlMap> getPointList(SearchVO schVO) throws Exception;

	/**
	    * @Method : getSumPoint
	    * @Date: 2017. 8. 4.
	    * @Author : 임  재  형
	    * @Description	:	페이지 적용 포인트 합
	   */
	int getSumPoint(SearchVO schVO, int totalCount) throws Exception;

	/**
	    * @Method : getTotalPoint
	    * @Date: 2017. 8. 4.
	    * @Author : 임  재  형
	    * @Description	:	보유 포인트
	   */
	int getTotalPoint() throws Exception;

	/**
	    * @Method : getSpPointDeduct
	    * @Date: 2017. 8. 4.
	    * @Author : 임  재  형
	    * @Description	:	포인트 조회 프로시져
	   */
	SqlMap getSpPointDeduct(String nextMonth) throws Exception;	
	
	/**
	    * @Method : getUsableCouponCnt
	    * @Date: 2017. 8. 4.
	    * @Author :  강 병 철
	    * @Description	:	사용가능한 리스트 총 개수
	   */
	int getUsableCouponCnt(SearchVO schVO) throws Exception;

	/**
	    * @Method : getUsableCouponList
	    * @Date: 2017. 8. 4.
	    * @Author :  강 병 철
	    * @Description	:	사용가능한 리스트 총 개수
	   */
	List<SqlMap> getUsableCouponList(SearchVO schVO) throws Exception;

	/**
	    * @Method : getSevenExpireCouponCnt
	    * @Date: 2017. 8. 4.
	    * @Author :  강 병 철
	    * @Description	:	7일내 만료 리스트 총 개수
	   */
	int getSevenExpireCouponCnt(SearchVO schVO) throws Exception;

	/**
	    * @Method : getSevenExpireCouponList
	    * @Date: 2017. 8. 4.
	    * @Author :  강 병 철
	    * @Description	:	7일이내 만료 리스트
	   */
	List<SqlMap> getSevenExpireCouponList(SearchVO schVO) throws Exception;

	/**
	    * @Method : getExpireCouponCnt
	    * @Date: 2017. 8. 4.
	    * @Author :  강 병 철
	    * @Description	:	만료 총 개수
	   */
	int getExpireCouponCnt(SearchVO schVO) throws Exception;

	/**
	    * @Method : getExpireCouponList
	    * @Date: 2017. 8. 4.
	    * @Author :  강 병 철
	    * @Description	:	만료 총 개수
	   */
	List<SqlMap> getExpireCouponList(SearchVO schVO) throws Exception;
}
