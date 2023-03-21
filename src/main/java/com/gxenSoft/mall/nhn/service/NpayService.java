package com.gxenSoft.mall.nhn.service;

import java.util.List;

import com.gxenSoft.mall.nhn.vo.NhnChangedProductOrderInfoVO;
import com.gxenSoft.mall.nhn.vo.NhnGetProductOrderInfoListVO;
import com.nhncorp.platform.checkout.base.Address;
import com.nhncorp.platform.checkout.base.CancelInfo;
import com.nhncorp.platform.checkout.base.Delivery;
import com.nhncorp.platform.checkout.base.ExchangeInfo;
import com.nhncorp.platform.checkout.base.Order;
import com.nhncorp.platform.checkout.base.ProductOrder;
import com.nhncorp.platform.checkout.base.ProductOrderChangeType;
import com.nhncorp.platform.checkout.base.ReturnInfo;

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
public interface NpayService {
	
	/**
	    * @Method : generateSignature
	    * @Date: 2018. 07.10
	    * @Author : 강 병 철
	    * @Description	:	서명 생성
	   */
	String generateSignature(String operationName) ;
	
	/**
	    * @Method : GetChangedProductOrderList
	       * @Date: 2018. 07.10
	    * @Author : 강 병 철
	    * @Description	:	변경 상품 주문 내역을 조회
	   */
	String GetChangedProductOrderList(ProductOrderChangeType OrderStatus, String callDate) throws Exception;	
	

	/**
	    * @Method : 3.2.1	GetProductOrderInfoList 
	       * @Date: 2018. 07.10
	    * @Author : 강 병 철
	    * @Description	:	상품 주문 내역을 조회
	   */
	NhnGetProductOrderInfoListVO GetProductOrderInfoList (List<String> orderProductList ) throws Exception ;
	
	/**
	    * @Method : insertNpayOrder
	    * @Date: 2018. 07.10
	    * @Author : 강 병 철
	    * @Description	:	주문 내역을 저장
	   */
	void insertNpayOrder(Order order, byte[]  encryptKey) throws Exception ;
	
	/**
	    * @Method : insertNpayProductOrder
	    * @Date: 2018. 07.10
	    * @Author : 강 병 철
	    * @Description	:	주문 상품 내역을 저장
	   */
	void insertNpayProductOrder(String OrderID, ProductOrder product) throws Exception ;

	/**
	    * @Method : insertNpayAddress
	    * @Date: 2018. 07.10
	    * @Author : 강 병 철
	    * @Description	:	주문 주소 저장
	   */
	void insertNpayAddress(String OrderID, Address vo, byte[]  encryptKey) throws Exception;
	
	/**
	    * @Method : insertNpayProductDelivery
	    * @Date: 2018. 07.10
	    * @Author : 강 병 철
	    * @Description	:	주문 상품별 배송정보
	   */
	void insertNpayProductDelivery(String OrderID,  String ProductOrderID, Delivery vo) throws Exception;
	
	/**
    * @Method : insertNpayCancelInfo
    * @Date: 2018. 07.10
    * @Author : 강 병 철
    * @Description	:	주문 상품별 취소 정보
   */
	void insertNpayCancelInfo(String OrderID,  String ProductOrderID, CancelInfo vo) throws Exception;
	

	/**
    * @Method : insertNpayReturnInfo
    * @Date: 2018. 07.10
    * @Author : 강 병 철
    * @Description	:	주문 상품별 반품 정보
   */
	void insertNpayReturnInfo(String OrderID,  String ProductOrderID, ReturnInfo vo) throws Exception;
	

	/**
    * @Method : insertNpayExchangeInfo
    * @Date: 2018. 07.10
    * @Author : 강 병 철
    * @Description	:	주문 상품별 교환 정보
   */
	void insertNpayExchangeInfo(String OrderID,  String ProductOrderID, ExchangeInfo vo) throws Exception;
	/**
	    * @Method : npayOrderProcess
	    * @Date: 2018. 10. 18
	    * @Author : 강 병 철
	    * @Description	:	주문테이블처리
	   */
		void npayOrderProcess(ProductOrderChangeType OrderStatus, List<NhnChangedProductOrderInfoVO> orderInfoList ) throws Exception;
	/**
    * @Method : npayOrderToMallOrder
    * @Date: 2018. 10. 18
    * @Author : 강 병 철
    * @Description	:	주문테이블로 이동
   */
	void npayOrderToMallOrder(List<NhnChangedProductOrderInfoVO> orderInfoList ) throws Exception;
	
	/**
	    * @Method : npayOrderToClaim
	    * @Date: 2018. 10. 18
	    * @Author : 강 병 철
	    * @Description	:	클레임처리
	   */
	void npayOrderToClaim(ProductOrderChangeType OrderStatus, List<NhnChangedProductOrderInfoVO> orderInfoList ) throws Exception;

}
