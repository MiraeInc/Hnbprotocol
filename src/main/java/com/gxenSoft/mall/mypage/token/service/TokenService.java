package com.gxenSoft.mall.mypage.token.service;

import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;

import java.util.List;

public interface TokenService {

	int getPointListCnt(SearchVO schVO) throws Exception;

	List<SqlMap> getPointList(SearchVO schVO) throws Exception;

	int getSumPoint(SearchVO schVO, int totalCount) throws Exception;

	int getTotalPoint() throws Exception;

	SqlMap getSpPointDeduct(String nextMonth) throws Exception;	

	int getUsableCouponCnt(SearchVO schVO) throws Exception;

	List<SqlMap> getUsableCouponList(SearchVO schVO) throws Exception;

	int getSevenExpireCouponCnt(SearchVO schVO) throws Exception;

	List<SqlMap> getSevenExpireCouponList(SearchVO schVO) throws Exception;

	int getExpireCouponCnt(SearchVO schVO) throws Exception;

	List<SqlMap> getExpireCouponList(SearchVO schVO) throws Exception;
}
