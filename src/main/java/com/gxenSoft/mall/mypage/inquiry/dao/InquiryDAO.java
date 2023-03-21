package com.gxenSoft.mall.mypage.inquiry.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gxenSoft.sqlDAO.CommonDefaultDAO;
import com.gxenSoft.sqlMap.SqlMap;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : InquiryDAO
    * PACKAGE NM : com.gxenSoft.mall.mypage.inquiry.dao
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 8. 3. 
    * HISTORY :   
    *
    *************************************
    */
@Repository("inquiryDAO")
public class InquiryDAO extends CommonDefaultDAO{


	/**
	    * @Method : getInquiryListCnt
	    * @Date: 2017. 8. 3.
	    * @Author : 임  재  형
	    * @Description	:	1:1 문의 리스트 총 개수
	   */
	public int getInquiryListCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("inquiryDAO.getInquiryListCnt", param);
	}

	/**
	    * @Method : getInquiryList
	    * @Date: 2017. 8. 3.
	    * @Author : 임  재  형
	    * @Description	:	1:1 문의 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getInquiryList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("inquiryDAO.getInquiryList", param);
	}

	/**
	    * @Method : inquiryDelete
	    * @Date: 2017. 8. 4.
	    * @Author : 임  재  형
	    * @Description	:	1:1 문의 삭제
	   */
	public int inquiryDelete(HashMap<String, Object> param) throws Exception {
		return (Integer)delete("inquiryDAO.inquiryDelete", param);
	}

	/**
	    * @Method : getInquiryDetail
	    * @Date: 2017. 8. 6.
	    * @Author : 임  재  형
	    * @Description	:	1:1 문의 상세
	   */
	public SqlMap getInquiryDetail(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("inquiryDAO.getInquiryDetail", param);
	}

	/**
	    * @Method : inquirySave
	    * @Date: 2017. 8. 6.
	    * @Author : 임  재  형
	    * @Description	:	1:1 문의 작성
	   */
	public int inquirySave(HashMap<String, Object> param) throws Exception {
		return (Integer)insert("inquiryDAO.inquirySave", param);
	}

	/**
	    * @Method : inquiryUpdate
	    * @Date: 2017. 8. 6.
	    * @Author : 임  재  형
	    * @Description	:	1:1 문의 수정
	   */
	public int inquiryUpdate(HashMap<String, Object> param) throws Exception {
		return (Integer)update("inquiryDAO.inquiryUpdate", param);
	}

	/**
	    * @Method : getInquiryGoodsListCnt
	    * @Date: 2017. 8. 7.
	    * @Author : 임  재  형
	    * @Description	:	1:1 상품 검색 리스트 총 개수
	   */
	public int getInquiryGoodsListCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("inquiryDAO.getInquiryGoodsListCnt", param);
	}

	/**
	    * @Method : getInquiryGoodsList
	    * @Date: 2017. 8. 7.
	    * @Author : 임  재  형
	    * @Description	:	1:1 상품 검색 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getInquiryGoodsList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("inquiryDAO.getInquiryGoodsList", param);
	}

	/**
	    * @Method : getInquiryOrderListCnt
	    * @Date: 2017. 8. 8.
	    * @Author : 임  재  형
	    * @Description	:	1:1 주문 검색 리스트 총 개수
	   */
	public int getInquiryOrderListCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("inquiryDAO.getInquiryOrderListCnt", param);
	}

	/**
	    * @Method : getInquiryOrderList
	    * @Date: 2017. 8. 8.
	    * @Author : 임  재  형
	    * @Description	:	1:1 주문 검색 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getInquiryOrderList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("inquiryDAO.getInquiryOrderList", param);
	}

	/**
	    * @Method : getInquiryOrderDetail
	    * @Date: 2017. 8. 28.
	    * @Author : 임  재  형
	    * @Description	:	비회원 주문 정보
	   */
	public SqlMap getInquiryOrderDetail(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("inquiryDAO.getInquiryOrderDetail", param);
	}
	
}
