package com.gxenSoft.mall.event.timeSale.service;

import java.util.List;

import com.gxenSoft.sqlMap.SqlMap;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : TimeSaleService
 * PACKAGE NM : com.gxenSoft.mall.event.timeSale.service
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 7. 20. 
 * HISTORY :
 
 *************************************
 */
public interface TimeSaleService {
	
	/**
	 * @Method : getTimeSaleDetail
	 * @Date		: 2017. 7. 20.
	 * @Author	:  유  준  철 
	 * @Description	:	타임 세일 상세
	 */
	public SqlMap getTimeSaleDetail(Integer mainGubun)throws Exception;
	
	/**
	 * @Method : getTimeSaleProductList
	 * @Date		: 2017. 7. 20.
	 * @Author	:  유  준  철 
	 * @Description	:	타임 세일 진행 예정 상품 리스트
	 */
	public List<SqlMap> getTimeSaleProductList(String idx) throws Exception;

}
