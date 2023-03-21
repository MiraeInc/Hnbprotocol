package com.gxenSoft.mall.mypage.inquiry.service;

import java.util.List;

import com.gxenSoft.mall.mypage.inquiry.vo.InquiryVO;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;

/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : InquiryService
    * PACKAGE NM : com.gxenSoft.mall.mypage.inquiry.service
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 8. 3. 
    * HISTORY :   
    *
    *************************************
    */
public interface InquiryService {
	
	/**
	    * @Method : getInquiryListCnt
	    * @Date: 2017. 8. 3.
	    * @Author : 임  재  형
	    * @Description	:	1:1 문의 리스트 총 개수
	   */
	int getInquiryListCnt(InquiryVO vo, SearchVO schVO) throws Exception;

	/**
	    * @Method : getInquiryList
	    * @Date: 2017. 8. 3.
	    * @Author : 임  재  형
	    * @Description	:	1:1 문의 리스트
	   */
	List<SqlMap> getInquiryList(InquiryVO vo, SearchVO schVO) throws Exception;

	/**
	    * @Method : inquiryDelete
	    * @Date: 2017. 8. 4.
	    * @Author : 임  재  형
	    * @Description	:	1:1 문의 삭제
	   */
	int inquiryDelete(InquiryVO vo) throws Exception;

	/**
	    * @Method : getInquiryDetail
	    * @Date: 2017. 8. 6.
	    * @Author : 임  재  형
	    * @Description	:	1:1 문의 상세
	   */
	SqlMap getInquiryDetail(InquiryVO vo) throws Exception;

	/**
	    * @Method : inquirySave
	    * @Date: 2017. 8. 6.
	    * @Author : 임  재  형
	    * @Description	:	1:1 문의 작성, 수정
	   */
	int inquirySave(InquiryVO vo) throws Exception;

	/**
	    * @Method : getInquiryGoodsListCnt
	    * @Date: 2017. 8. 7.
	    * @Author : 임  재  형
	    * @Description	:	1:1 상품 검색 리스트 총 개수
	   */
	int getInquiryGoodsListCnt(InquiryVO vo, SearchVO schVO) throws Exception;

	/**
	    * @Method : getInquiryGoodsList
	    * @Date: 2017. 8. 7.
	    * @Author : 임  재  형
	    * @Description	:	1:1 상품 검색 리스트
	   */
	List<SqlMap> getInquiryGoodsList(InquiryVO vo, SearchVO schVO) throws Exception;

	/**
	    * @Method : getInquiryOrderListCnt
	    * @Date: 2017. 8. 8.
	    * @Author : 임  재  형
	    * @Description	:	1:1 주문 검색 리스트 총 개수
	   */
	int getInquiryOrderListCnt(InquiryVO vo, SearchVO schVO) throws Exception;

	/**
	    * @Method : getInquiryOrderList
	    * @Date: 2017. 8. 8.
	    * @Author : 임  재  형
	    * @Description	:	1:1 주문 검색 리스트
	   */
	List<SqlMap> getInquiryOrderList(InquiryVO vo, SearchVO schVO) throws Exception;

	/**
	    * @Method : getInquiryOrderDetail
	    * @Date: 2017. 8. 28.
	    * @Author : 임  재  형
	    * @Description	:	비회원 주문 정보
	   */
	SqlMap getInquiryOrderDetail(String orderCd) throws Exception;
	
}
