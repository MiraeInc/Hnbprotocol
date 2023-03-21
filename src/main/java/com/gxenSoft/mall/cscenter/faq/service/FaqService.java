package com.gxenSoft.mall.cscenter.faq.service;

import java.util.List;

import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : FaqService
    * PACKAGE NM : com.gxenSoft.mall.cscenter.faq.service
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 7. 25. 
    * HISTORY :   
    *
    *************************************
    */
public interface FaqService {
	
	/**
	    * @Method : getFaqListCnt
	    * @Date: 2017. 7. 26.
	    * @Author : 임  재  형
	    * @Description	:	FAQ 리스트 총 개수
	   */
	int getFaqListCnt(SearchVO schVO) throws Exception;

	/**
	    * @Method : getFaqList
	    * @Date: 2017. 7. 26.
	    * @Author : 임  재  형
	    * @Description	:	FAQ 리스트
	   */
	List<SqlMap> getFaqList(SearchVO schVO) throws Exception;

	/**
	    * @Method : addFaqReadCnt
	    * @Date: 2017. 8. 17.
	    * @Author : 임  재  형
	    * @Description	:	FAQ 조회수 증가
	   */
	int addFaqReadCnt(int faqIdx) throws Exception;

	/**
	    * @Method : getFaqTop5List
	    * @Date: 2017. 8. 17.
	    * @Author : 임  재  형
	    * @Description	:	FAQ Top5 리스트
	   */
	List<SqlMap> getFaqTop5List(SearchVO schVO) throws Exception;

}
