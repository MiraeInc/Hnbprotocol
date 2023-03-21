package com.gxenSoft.mall.nhn.dao;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.gxenSoft.sqlDAO.CommonDefaultDAO;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : NpayDAO
    * PACKAGE NM : com.gxenSoft.mall.nhn.dao
    * AUTHOR	 : 강 병 철
    * CREATED DATE  : 2018. 7. 10. 
    * HISTORY :   
    *
    *************************************
    */
@Repository("npayDAO")
public class NpayDAO extends CommonDefaultDAO{


	/**
	    * @Method : insertNpayOrder
	    * @Date: 2018. 7. 10.
	    * @Author : 강병철
	    * @Description	:	주문테이블
	   */
	public void insertNpayOrder(HashMap<String, Object> param) throws Exception {
		insert("npayDAO.insertNpayOrder", param);
	}

	/**
	    * @Method : updateNpayOrder
	    * @Date: 2018. 7. 10.
	    * @Author : 강병철
	    * @Description	:	주문테이블 수정
	   */
	public void updateNpayOrder(HashMap<String, Object> param) throws Exception {
		insert("npayDAO.updateNpayOrder", param);
	}
	
	
	/**
	    * @Method : cntNpayOrder
	    * @Date: 2018. 7. 10.
	    * @Author : 강병철
	    * @Description	:	주문개수
	   */
	public int cntNpayOrder(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("npayDAO.cntNpayOrder", param);
	}
	
	/**
	    * @Method : insertNpayProductOrder
	    * @Date: 2018. 07.10
	    * @Author : 강 병 철
	    * @Description	:	주문 상품 내역을 저장
	   */
	public void insertNpayProductOrder(HashMap<String, Object> param) throws Exception {
		insert("npayDAO.insertNpayProductOrder", param);
	}
	/**
	    * @Method : updateNpayProductOrder
	    * @Date: 2018. 7. 10.
	    * @Author : 강병철
	    * @Description	:	주문 상품 수정
	   */
	public void updateNpayProductOrder(HashMap<String, Object> param) throws Exception {
		insert("npayDAO.updateNpayProductOrder", param);
	}
	
	/**
	    * @Method : cntNpayOrderProduct
	    * @Date: 2018. 7. 10.
	    * @Author : 강병철
	    * @Description	:	주문상품개수
	   */
	public int cntNpayOrderProduct(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("npayDAO.cntNpayOrderProduct", param);
	}
	
	/**
	    * @Method : insertNpayAddress
	    * @Date: 2018. 07.10
	    * @Author : 강 병 철
	    * @Description	:	주문 주소 저장
	   */
	public void insertNpayAddress(HashMap<String, Object> param) throws Exception {
		insert("npayDAO.insertNpayAddress", param);
	}
	
	/**
	    * @Method : cntNpayAddress
	    * @Date: 2018. 7. 10.
	    * @Author : 강병철
	    * @Description	:	주문 주소 개수
	   */
	public int cntNpayAddress(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("npayDAO.cntNpayAddress", param);
	}

	/**
	    * @Method : insertNpayProductDelivery
	    * @Date: 2018. 07.10
	    * @Author : 강 병 철
	    * @Description	:	상품 배송정보 저장
	   */
	public void insertNpayProductDelivery(HashMap<String, Object> param) throws Exception {
		insert("npayDAO.insertNpayProductDelivery", param);
	}
	
	/**
	    * @Method : updateNpayProductDelivery
	    * @Date: 2018. 07.10
	    * @Author : 강 병 철
	    * @Description	:	상품 배송정보 저장
	   */
	public void updateNpayProductDelivery(HashMap<String, Object> param) throws Exception {
		update("npayDAO.updateNpayProductDelivery", param);
	}
	
	/**
	    * @Method : cntNpayProductDelivery
	    * @Date: 2018. 7. 10.
	    * @Author : 강병철
	    * @Description	:	상품 배송정보 개수
	   */
	public int cntNpayProductDelivery(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("npayDAO.cntNpayProductDelivery", param);
	}
	
	/**
	    * @Method : insertNpayCancelInfo
	    * @Date: 2018. 07.10
	    * @Author : 강 병 철
	    * @Description	:	상품 취소정보 저장
	   */
	public void insertNpayCancelInfo(HashMap<String, Object> param) throws Exception {
		insert("npayDAO.insertNpayCancelInfo", param);
	}
	
	/**
	    * @Method : updateNpayCancelInfo
	    * @Date: 2018. 07.10
	    * @Author : 강 병 철
	    * @Description	:	상품 취소정보 저장
	   */
	public void updateNpayCancelInfo(HashMap<String, Object> param) throws Exception {
		update("npayDAO.updateNpayCancelInfo", param);
	}
	
	/**
	    * @Method : cntNpayCancelInfo
	    * @Date: 2018. 7. 10.
	    * @Author : 강병철
	    * @Description	:	상품 취소정보 개수
	   */
	public int cntNpayCancelInfo(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("npayDAO.cntNpayCancelInfo", param);
	}

	/**
	    * @Method : insertNpayReturnInfo
	    * @Date: 2018. 07.10
	    * @Author : 강 병 철
	    * @Description	:	상품 반품정보 저장
	   */
	public void insertNpayReturnInfo(HashMap<String, Object> param) throws Exception {
		insert("npayDAO.insertNpayReturnInfo", param);
	}
	
	/**
	    * @Method : updateNpayReturnInfo
	    * @Date: 2018. 07.10
	    * @Author : 강 병 철
	    * @Description	:	상품 반품정보 저장
	   */
	public void updateNpayReturnInfo(HashMap<String, Object> param) throws Exception {
		update("npayDAO.updateNpayReturnInfo", param);
	}
	
	/**
	    * @Method : cntNpayReturnInfo
	    * @Date: 2018. 7. 10.
	    * @Author : 강병철
	    * @Description	:	상품  반품정보 개수
	   */
	public int cntNpayReturnInfo(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("npayDAO.cntNpayReturnInfo", param);
	}


	/**
	    * @Method : insertNpayExchangeInfo
	    * @Date: 2018. 07.10
	    * @Author : 강 병 철
	    * @Description	:	상품 교환정보 저장
	   */
	public void insertNpayExchangeInfo(HashMap<String, Object> param) throws Exception {
		insert("npayDAO.insertNpayExchangeInfo", param);
	}
	
	/**
	    * @Method : updateNpayExchangeInfo
	    * @Date: 2018. 07.10
	    * @Author : 강 병 철
	    * @Description	:	상품 교환정보 저장
	   */
	public void updateNpayExchangeInfo(HashMap<String, Object> param) throws Exception {
		update("npayDAO.updateNpayExchangeInfo", param);
	}
	
	/**
	    * @Method : cntNpayExchangeInfo
	    * @Date: 2018. 7. 10.
	    * @Author : 강병철
	    * @Description	:	상품  교환정보 개수
	   */
	public int cntNpayExchangeInfo(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("npayDAO.cntNpayExchangeInfo", param);
	}
	
	  /**
	    * @Method : callSpNpayOrderPayed
	    * @Date: 2018.10.18
	    * @Author :  강병철
	    * @Description	:	주문저장프로시저호출
	   */
	public int callSpNpayOrderPayed(HashMap<String, Object> param) throws Exception{
		return (Integer)update("npayDAO.callSpNpayOrderPayed", param);
	}
	  /**
	    * @Method : callSpNpayClaim
	    * @Date: 2018.10.18
	    * @Author :  강병철
	    * @Description	:	클레임 테이블 저장
	   */
	public int callSpNpayClaim(HashMap<String, Object> param) throws Exception{
		return (Integer)update("npayDAO.callSpNpayClaim", param);
	}

	
}
