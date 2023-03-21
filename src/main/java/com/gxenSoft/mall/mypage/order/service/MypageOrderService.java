package com.gxenSoft.mall.mypage.order.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gxenSoft.mall.common.vo.JsonResultVO;
import com.gxenSoft.mall.lgdacom.vo.PaycoApprovalVO;
import com.gxenSoft.mall.lgdacom.vo.PaycoReturnVO;
import com.gxenSoft.mall.lgdacom.vo.XPayResultVO;
import com.gxenSoft.mall.mypage.order.vo.ClaimVO;
import com.gxenSoft.mall.mypage.order.vo.OrderDetailVO;
import com.gxenSoft.mall.order.vo.OrderVO;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;

/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : MypageOrderService
    * PACKAGE NM : com.gxenSoft.mall.mypage.order.service
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 8. 4. 
    * HISTORY :   
    *
    *************************************
    */
public interface MypageOrderService {

	   /**
	    * @Method : getMemberCntInfo
	    * @Date: 2017. 8. 4.
	    * @Author :  서 정 길
	    * @Description	:	쿠폰 갯수, 포인트, 상품 후기 건수, 이 달의 샘플 신청 건수
	   */
	public SqlMap getMemberCntInfo(OrderVO vo) throws Exception;
	
	   /**
	    * @Method : getOrderStatusCnt
	    * @Date: 2017. 8. 4.
	    * @Author :  서 정 길
	    * @Description	:	마이페이지 메인 - 주문 상태별 건수
	   */
	public SqlMap getOrderStatusCnt(OrderVO vo) throws Exception;
	
	   /**
	    * @Method : getOrderListFor1Month
	    * @Date: 2017. 8. 6.
	    * @Author :  서 정 길
	    * @Description	:	마이페이지 메인 - 주문 리스트(최근 1달 5건)
	   */
	public List<SqlMap> getOrderListFor1Month(OrderVO vo) throws Exception;
	
	   /**
	    * @Method : getOrderListFor1MonthCnt
	    * @Date: 2017. 8. 6.
	    * @Author :  서 정 길
	    * @Description	:	마이페이지 메인 - 주문 리스트(최근 1달 5건) 건수
	   */
	public int getOrderListFor1MonthCnt(OrderVO vo) throws Exception;
	
	   /**
	    * @Method : getMyOrderList
	    * @Date: 2017. 8. 6.
	    * @Author :  서 정 길
	    * @Description	:	마이페이지 주문관리 주문 내역 리스트
	   */
	public List<SqlMap> getMyOrderList(OrderVO vo, SearchVO searchVO) throws Exception;
	
	   /**
	    * @Method : getMyOrderListCnt
	    * @Date: 2017. 8. 6.
	    * @Author :  서 정 길
	    * @Description	:	마이페이지 주문관리 주문 내역 리스트 건수
	   */
	public int getMyOrderListCnt(OrderVO vo, SearchVO searchVO) throws Exception;

	   /**
	    * @Method : getMyClaimList
	    * @Date: 2017. 8. 7.
	    * @Author :  서 정 길
	    * @Description	:	주문관리 - 취소, 반품, 교환 리스트
	    * claimType : 클레임 구분 (C : 취소, X : 교환, R : 반품)
	   */
	public List<SqlMap> getMyClaimList(String claimType, OrderVO vo, SearchVO searchVO) throws Exception;

	   /**
	    * @Method : getMyClaimListCnt
	    * @Date: 2017. 8. 7.
	    * @Author :  서 정 길
	    * @Description	:	주문관리 - 취소, 반품, 교환 리스트 건수
	    * claimType : 클레임 구분 (C : 취소, X : 교환, R : 반품)
	   */
	public int getMyClaimListCnt(String claimType, OrderVO vo, SearchVO searchVO) throws Exception;
	
	 /**
	    * @Method : getProductList
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	:	주문상품 정보
	   */
	public String getProductList(SqlMap order) throws Exception ;

	   /**
	    * @Method : getIssueDocumentList
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	:	증명서류발급 목록
	   */
	public List<SqlMap> getIssueDocumentList(OrderVO vo, SearchVO searchVO) throws Exception;
	
	   /**
	    * @Method : getIssueDocumentCnt
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	:	증명서류발급 목록 개수
	   */
	public int getIssueDocumentCnt(OrderVO vo, SearchVO searchVO) throws Exception;
	 /**
	    * @Method : TaxBillReq
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	:	세금계산서 발급 시넝
	   */
	public void TaxBillReq(String ORDER_IDX,String BUSINESS_NO,String COMPANY_NM,String CEO_NM,String ADDR,String UPTAE,String JONGMOK,String DAMDANG_NM,String DAMDANG_EMAIL, String REG_IDX) throws Exception ;

	/**
	    * @Method : lgTelecomCashReceiptProcess
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	: LGU+ 현금영수증 신청
	    * 
	   */
	public Boolean lgTelecomCashReceiptProcess( SqlMap orderInfo, String CASH_RECEIPT_GUBUN, String CASH_RECEIPT_NO, SqlMap xpayInfo, JsonResultVO resultMap) throws Exception;
	
	/*
    * @Method : getXpayResult
    * @Date: 2017. 8. 7.
    * @Author :  강 병 철
    * @Description	: LGU+ 결제 정보
   */
	public SqlMap getXpayResult(OrderVO vo) throws Exception ;

	   /**
	    * @Method : getPaycoApprovalResult
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	: PAYCO 결제 정보
	   */
	public SqlMap getPaycoApprovalResult(OrderVO vo) throws Exception ;

	/**
	    * @Method : cancleAllDBModule
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	: 전체 취소 DB처리
	    * 
	   */
	public SqlMap cancleAllDBModule(SqlMap orderInfo, String reason) throws Exception;

	/**
	    * @Method : lgTelecomCasNotiCancelProcess
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	: LGU+주문 취소 (가상계좌 연동 URL로 취소 호출시 )
	    * 
	   */
	public Boolean lgTelecomCasNotiCancelProcess( String orderCd, SqlMap xpayInfo, JsonResultVO resultMap) throws Exception;
	
	/**
	    * @Method : lgTelecomCancel
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	: LGU+ 취소 실행
	   */
	public Boolean lgTelecomCancelProcess(SqlMap orderInfo,SqlMap xpayInfo, JsonResultVO resultMap) throws Exception;

	/**
	    * @Method : paycoCancelProcess
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	:PAYCO 취소
	   */
	public Boolean paycoCancelProcess(SqlMap orderInfo, SqlMap approvalInfo, JsonResultVO resultMap) throws Exception ;
	
	/**
	    * @Method : pointCancelProcess
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	:0원 취소
	   */
	public Boolean pointCancelProcess(SqlMap orderInfo,  JsonResultVO resultMap) throws Exception ;

	/**
	    * @Method : xPayCancel
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	: LGU+ 취소 모듈
	   */
	public String xPayCancel(XPayResultVO vo, String payType) throws Exception ;

	/**
	    * @Method : paycoCancel
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	: PAYCO 결제 주문 취소
	   */
	public PaycoReturnVO paycoCancel(PaycoApprovalVO vo) throws Exception ;

   /**
    * @Method : insertOrderStatusLog
    * @Date: 2017. 8. 7.
    * @Author :  강병철
    * @Description	:	주문상세 로그 저장
   */
	public void insertOrderStatusLog(OrderVO vo, int orderDetailIdx, String statusCd , String reason) throws Exception;
	
	/**
	    * @Method : setDeliveryChange
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	: 배송지정보수정
	   */
	public void setDeliveryChange(OrderVO vo) throws Exception ;
	

	/**
    * @Method : AllCompletStatus
    * @Date: 2017. 8. 7.
    * @Author :  강병철
    * @Description	:	전체 구매확정
   */
	public void AllCompletStatus( OrderVO vo,JsonResultVO resultMap) throws Exception;
	
	/**
	    * @Method : getOrderInfo
	    * @Date: 2017. 8. 7.
	    * @Author :  서 정 길
	    * @Description	:	주문 상세 - 주문 마스터 정보
	    * isCasNote : 가상계좌 입금완료에서 호출시는 회원/비회원 여부 상관없이 주문 코드로만 조회하기 위해
	   */
	public SqlMap getOrderInfo(OrderVO vo, Boolean isCasNote) throws Exception;

	   /**
	    * @Method : getOrderDetailList
	    * @Date: 2017. 8. 7.
	    * @Author :  서 정 길
	    * @Description	:	주문 상세 - 주문 상세 리스트
	    * isCasNote : 가상계좌 입금완료에서 호출시는 회원/비회원 여부 상관없이 주문 코드로만 조회하기 위해
	   */
	public List<SqlMap> getOrderDetailList(OrderVO vo, Boolean isCasNote) throws Exception;

	   /**
	    * @Method : getOrderGiftList
	    * @Date: 2017. 8. 9.
	    * @Author :  서 정 길
	    * @Description	:	주문 상세 - 사은품 리스트
	    * freeYn : 무료구분	Y : 무료사은품, N:구간사은품
	   */
	public List<SqlMap> getOrderGiftList(OrderVO vo, String freeYn) throws Exception;
	
	   /**
	    * @Method : getPaycoResultList
	    * @Date: 2017. 8. 8.
	    * @Author :  서 정 길
	    * @Description	:	주문 상세 - PAYCO 결제 결과 리스트
	   */
	public List<SqlMap> getPaycoResultList(OrderVO vo) throws Exception;

	   /**
	    * @Method : getOrderDetailValidStatusCnt
	    * @Date: 2017. 8. 11.
	    * @Author :  서 정 길
	    * @Description	:	주문 상세 - 선택한 상품들 중 해당 상태에 맞는 상품 건 수
	   */
	public JsonResultVO getOrderDetailValidStatusCnt(ClaimVO vo, String[] orderStatusCds) throws Exception;

	   /**
	    * @Method : updateOrderStatusCdAsDetailMinOrderStatusCd
	    * @Date: 2017. 8. 11.
	    * @Author :  서 정 길
	    * @Description	:	주문 상세의 주문 상태 코드들 최소값으로 주문 마스터의 주문 상태 코드 변경
	   */
	public int updateOrderStatusCdAsDetailMinOrderStatusCd(String orderIdx) throws Exception;
	
	   /**
	    * @Method : requestExchangeOrder
	    * @Date: 2017. 8. 11.
	    * @Author :  서 정 길
	    * @Description	:	교환 신청
	   */
	public JsonResultVO requestExchangeOrder(ClaimVO vo) throws Exception;

	   /**
	    * @Method : requestReturnOrder
	    * @Date: 2017. 8. 11.
	    * @Author :  서 정 길
	    * @Description	:	반품 신청
	   */
	public JsonResultVO requestReturnOrder(ClaimVO vo) throws Exception;

	   /**
	    * @Method : insertOrderStatusLog
	    * @Date: 2017. 8. 11.
	    * @Author :  서 정 길
	    * @Description	:	주문 상세 로그 저장
	   */
	public int insertOrderStatusLog(OrderDetailVO vo) throws Exception;

	   /**
	    * @Method : getClaimDetailList
	    * @Date: 2017. 8. 7.
	    * @Author :  강병철
	    * @Description	:	Claim 상품 정보
	   */
	public List<SqlMap> getClaimDetailList(int claimIdx) throws Exception;

	/**
	 * @Method : smilepayCancelProcess
	 * @Date: 2017. 12. 10.
	 * @Author :  김  민  수
	 * @Description	:	스마일 페이 결제 취소 처리 
	 * @HISTORY :
	 *
	 */
	public Boolean smilepayCancelProcess(SqlMap orderInfo , HttpServletRequest request , JsonResultVO resultMap) throws Exception ;
	
	
	/**
	 * @Method : getSmilepayResult
	 * @Date: 2017. 12. 17.
	 * @Author :  김  민  수
	 * @Description	:	스마일 페이 결제	
	 * @HISTORY :
	 *
	 */
	public SqlMap getSmilepayResult(OrderVO vo)throws Exception;


	/**
	    * @Method : kcpCancelProcess
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	: KCP 주문 취소
	    * 
	   */
	public Boolean kcpCancelProcess( SqlMap orderInfo, SqlMap xpayInfo, JsonResultVO resultMap) throws Exception;
	

	/**
	    * @Method : kcpPgCancel
	    * @Date: 2018. 7. 18.
	    * @Author :  강 병 철
	    * @Description	: KCP 결제 주문 취소
	   */
	public String kcpPgCancel(XPayResultVO vo, String regIp, String payType) throws Exception;


	/**
	    * @Method : kcpPgStep2
	    * @Date: 2018. 7. 18.
	    * @Author :  강 병 철
	    * @Description	: KCP 정산보류
	   */
	public String kcpPgStep3(XPayResultVO vo, String regIp, String reason) throws Exception;
	

	/**
	 * @Method : getWonderpayResult
	 * @Date: 2018.11.02
	 * @Author :  강 병 철
	 * @Description	:	원더페이 결제 결과
	 * @HISTORY :
	 *
	 */
	public SqlMap getWonderpayResult(OrderVO vo) throws Exception;
	
	/**
	    * @Method : getSSLConnect
	    * @Date: 2018. 11. 02.
	    * @Author :   강 병 철
	    * @Description	:	원더페이 인증 통신
	   */
	public String getSSLConnect(String apiUrl, String body) throws Exception;
	
	/**
	    * @Method : wonderCancelProcess
	    * @Date: 2018. 11. 04.
	    * @Author :  강 병 철
	    * @Description	: 원더페이 주문 취소
	   */
	public Boolean wonderCancelProcess(SqlMap orderInfo, SqlMap approvalInfo, JsonResultVO resultMap) throws Exception;
	
}
