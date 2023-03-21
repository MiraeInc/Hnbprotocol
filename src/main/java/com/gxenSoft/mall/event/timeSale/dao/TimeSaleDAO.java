package com.gxenSoft.mall.event.timeSale.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gxenSoft.sqlDAO.CommonDefaultDAO;
import com.gxenSoft.sqlMap.SqlMap;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : TimeSaleDAO
 * PACKAGE NM : com.gxenSoft.mall.event.timeSale.dao
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 7. 20. 
 * HISTORY :
 
 *************************************
 */
@Repository("timeSaleDAO")
public class TimeSaleDAO extends CommonDefaultDAO{
	
	/**
	 * @Method : getTimeSaleDetail
	 * @Date		: 2017. 7. 20.
	 * @Author	:  유  준  철 
	 * @Description	:	타임 세일 상세
	 */
	public SqlMap getTimeSaleDetail(HashMap<String, Object> param)throws Exception{
		return (SqlMap)selectOne("timeSaleDAO.getTimeSaleDetail", param);
	}
	
	/**
	 * @Method : getTimeSaleProductList
	 * @Date		: 2017. 7. 20.
	 * @Author	:  유  준  철 
	 * @Description	:	타임 세일 진행 예정 상품 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getTimeSaleProductList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("timeSaleDAO.getTimeSaleProductList", param);
	}
	
	/**
	 * @Method : getTimeSaleListCnt
	 * @Date		: 2017. 7. 27.
	 * @Author	:  유  준  철 
	 * @Description	:	타임세일 진행 건수
	 */
	public int getTimeSaleListCnt()throws Exception{
		return (Integer)selectListTotCnt("timeSaleDAO.getTimeSaleListCnt", null);
	}
}
