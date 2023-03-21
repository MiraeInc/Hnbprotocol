package com.gxenSoft.mall.cscenter.faq.dao;


import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gxenSoft.sqlDAO.CommonDefaultDAO;
import com.gxenSoft.sqlMap.SqlMap;

/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : FaqDAO
    * PACKAGE NM : com.gxenSoft.mall.cscenter.faq.dao
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 7. 25. 
    * HISTORY :   
    *
    *************************************
    */
@Repository("faqDAO")
public class FaqDAO extends CommonDefaultDAO{
	
	/**
	    * @Method : getFaqListCnt
	    * @Date: 2017. 7. 26.
	    * @Author : 임  재  형
	    * @Description	:	FAQ 리스트 총 개수
	   */
	public int getFaqListCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("faqDAO.getFaqListCnt", param);
	}

	/**
	    * @Method : getFaqList
	    * @Date: 2017. 7. 26.
	    * @Author : 임  재  형
	    * @Description	:	FAQ 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getFaqList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("faqDAO.getFaqList", param);
	}

	/**
	    * @Method : addFaqReadCnt
	    * @Date: 2017. 8. 17.
	    * @Author : 임  재  형
	    * @Description	:	FAQ 조회수 증가
	   */
	public int addFaqReadCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)update("faqDAO.addFaqReadCnt", param);
	}

	/**
	    * @Method : getFaqTop5List
	    * @Date: 2017. 8. 17.
	    * @Author : 임  재  형
	    * @Description	:	Faq Top5 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getFaqTop5List(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("faqDAO.getFaqTop5List", param);
	}

}
