package com.gxenSoft.mall.mypage.sample.service;

import java.util.List;

import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : SampleService
    * PACKAGE NM : com.gxenSoft.mall.mypage.sample.service
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 8. 2. 
    * HISTORY :   
    *
    *************************************
    */
public interface SampleService {

	/**
	    * @Method : getSampleListCnt
	    * @Date: 2017. 8. 2.
	    * @Author : 임  재  형
	    * @Description	:	샘플 리스트 총 개수
	   */
	int getSampleListCnt(SearchVO schVO) throws Exception;

	/**
	    * @Method : getSampleList
	    * @Date: 2017. 8. 2.
	    * @Author : 임  재  형
	    * @Description	:	샘플 리스트
	   */
	List<SqlMap> getSampleList(SearchVO schVO) throws Exception;

	/**
	    * @Method : getSampleReplyList
	    * @Date: 2017. 8. 2.
	    * @Author : 임  재  형
	    * @Description	:	샘플 댓글 리스트
	   */
	List<SqlMap> getSampleReplyList(SearchVO schVO) throws Exception;

}
