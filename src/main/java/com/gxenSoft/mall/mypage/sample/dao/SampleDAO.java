package com.gxenSoft.mall.mypage.sample.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gxenSoft.sqlDAO.CommonDefaultDAO;
import com.gxenSoft.sqlMap.SqlMap;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : SampleDAO
    * PACKAGE NM : com.gxenSoft.mall.mypage.sample.dao
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 8. 2. 
    * HISTORY :   
    *
    *************************************
    */
@Repository("sampleDAO")
public class SampleDAO extends CommonDefaultDAO{

	/**
	    * @Method : getSampleListCnt
	    * @Date: 2017. 8. 2.
	    * @Author : 임  재  형
	    * @Description	:	샘플 리스트 총 개수
	   */
	public int getSampleListCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("sampleDAO.getSampleListCnt", param);
	}

	/**
	    * @Method : getSampleList
	    * @Date: 2017. 8. 2.
	    * @Author : 임  재  형
	    * @Description	:	샘플 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getSampleList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("sampleDAO.getSampleList", param);
	}

	/**
	    * @Method : getSampleReplyList
	    * @Date: 2017. 8. 2.
	    * @Author : 임  재  형
	    * @Description	:	샘플 댓글 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getSampleReplyList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("sampleDAO.getSampleReplyList", param);
	}

}
