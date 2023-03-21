package com.gxenSoft.mall.mypage.order.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gxenSoft.sqlDAO.CommonDefaultDAO;
import com.gxenSoft.sqlMap.SqlMap;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : MypageOrderDAO
    * PACKAGE NM : com.gxenSoft.mall.mypage.order.dao
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 8. 4. 
    * HISTORY :   
    *
    *************************************
    */
@Repository("mypageOrderDAO")
public class MypageOrderDAO extends CommonDefaultDAO{

	
	   /**
	    * @Method : getMemberCntInfo
	    * @Date: 2017. 8. 4.
	    * @Author :  서 정 길
	    * @Description	:	쿠폰 갯수, 포인트, 상품 후기 건수, 이 달의 샘플 신청 건수
	   */
	public SqlMap getMemberCntInfo(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("mypageOrderDAO.getMemberCntInfo", param);
	}
	
	   /**
	    * @Method : getOrderStatusCnt
	    * @Date: 2017. 8. 4.
	    * @Author :  서 정 길
	    * @Description	:	마이페이지 메인 - 주문 상태별 건수
	   */
	public SqlMap getOrderStatusCnt(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("mypageOrderDAO.getOrderStatusCnt", param);
	}
	
	   /**
	    * @Method : getOrderListFor1Month
	    * @Date: 2017. 8. 6.
	    * @Author :  서 정 길
	    * @Description	:	마이페이지 메인 - 주문 리스트(최근 1달 5건)
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getOrderListFor1Month(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("mypageOrderDAO.getOrderListFor1Month", param);
	}
	
	   /**
	    * @Method : getOrderListFor1MonthCnt
	    * @Date: 2017. 8. 6.
	    * @Author :  서 정 길
	    * @Description	:	마이페이지 메인 - 주문 리스트(최근 1달 5건) 건수
	   */
	public int getOrderListFor1MonthCnt(HashMap<String, Object> param) throws Exception{
		return (Integer)selectListTotCnt("mypageOrderDAO.getOrderListFor1MonthCnt", param);
	}
	   /**
	    * @Method : getXpayResult
	    * @Date: 2017. 8. 4.
	    * @Author :  강 병 철
	    * @Description	:	마이페이지 메인 - 주문 LGU+ 결제 결과 정보
	   */
	public SqlMap getXpayResult(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("mypageOrderDAO.getXpayResult", param);
	}
	   /**
	    * @Method : getPaycoApprovalResult
	    * @Date: 2017. 8. 4.
	    * @Author :  강 병 철
	    * @Description	:	마이페이지 메인 - 주문 Payco 결제 결과 정보
	   */
	public SqlMap getPaycoApprovalResult(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("mypageOrderDAO.getPaycoApprovalResult", param);
	}
	
	/**
	    * @Method : insertClaimMaster
	    * @Date: 2017. 7. 4.
	    * @Author :  강 병 철
	    * @Description	:	클레임 마스터 저장
	   */
	public int insertClaimMaster(HashMap<String, Object> param) throws Exception{
		return (Integer)insert("mypageOrderDAO.insertClaimMaster", param);
	}
	/**
	    * @Method : insertClaimDetailAllCancel
	    * @Date: 2017. 7. 4.
	    * @Author :  강 병 철
	    * @Description	:	클레임 디테일 저장
	   */
	public int insertClaimDetailAllCancel(HashMap<String, Object> param) throws Exception{
		return (Integer)insert("mypageOrderDAO.insertClaimDetailAllCancel", param);
	}
	
	
	   /**
	    * @Method : procSpOrderCancelRefund
	    * @Date: 2017. 7. 17.
	    * @Author :  강 병 철
	    * @Description	:	주문 취소/반품 DB 처리 모듈
	   */
	public SqlMap procSpOrderCancelRefund(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("mypageOrderDAO.procSpOrderCancelRefund", param);
	}
	
	/**
	    * @Method : updateOrderMasterDelivery
	    * @Date: 2017. 7. 4.
	    * @Author :  강 병 철
	    * @Description	:	배송지정보수정
	   */
	public int updateOrderMasterDelivery(HashMap<String, Object> param) throws Exception{
		return (Integer)update("mypageOrderDAO.updateOrderMasterDelivery", param);
	}

	/**
	    * @Method : updateOrderDetailStatusTo900
	    * @Date: 2017. 7. 4.
	    * @Author :  강 병 철
	    * @Description	:	주문상세 구매확정으로 변경
	   */
	public int updateOrderDetailStatusTo900(HashMap<String, Object> param) throws Exception{
		return (Integer)update("mypageOrderDAO.updateOrderDetailStatusTo900", param);
	}
	
	
	   /**
	    * @Method : getMyOrderList
	    * @Date: 2017. 8. 6.
	    * @Author :  서 정 길
	    * @Description	:	마이페이지 주문관리 주문 내역 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getMyOrderList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("mypageOrderDAO.getMyOrderList", param);
	}

	   /**
	    * @Method : getIssueDocumentCnt
	    * @Date: 2017. 8. 6.
	    * @Author :  강 병 철
	    * @Description	:	마이페이지 주문관리 증빙서류발급
	   */
	public int getIssueDocumentCnt(HashMap<String, Object> param) throws Exception{
		return (Integer)selectListTotCnt("mypageOrderDAO.getIssueDocumentCnt", param);
	}
	
	   /**
	    * @Method : getIssueDocumentList
	    * @Date: 2017. 8. 6.
	    * @Author :  강 병 철
	    * @Description	:	마이페이지 주문관리 증빙서류발급
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getIssueDocumentList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("mypageOrderDAO.getIssueDocumentList", param);
	}
	  /**
	    * @Method : getProductList
	    * @Date: 2017. 8. 6.
	    * @Author :  강 병 철
	    * @Description	:	마이페이지 주문관리 증빙서류발급 의 주문상품정보
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getProductList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("mypageOrderDAO.getProductList", param);
	}
	   /**
	    * @Method : getMyOrderListCnt
	    * @Date: 2017. 8. 6.
	    * @Author :  서 정 길
	    * @Description	:	마이페이지 주문관리 주문 내역 리스트 건수
	   */
	public int getMyOrderListCnt(HashMap<String, Object> param) throws Exception{
		return (Integer)selectListTotCnt("mypageOrderDAO.getMyOrderListCnt", param);
	}
	
	
	
	   /**
	    * @Method : getOrderDetailInfo
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	:	주문 상세 - 주문 상품 정보
	   */
	public SqlMap getOrderDetailInfo(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("mypageOrderDAO.getOrderDetailInfo", param);
	}
	
	/**
	    * @Method : updateCashBillReq
	    * @Date: 2017. 7. 4.
	    * @Author :  강 병 철
	    * @Description	:	현금영수증 발급 신청
	   */
	public int updateCashBillReq(HashMap<String, Object> param) throws Exception{
		return (Integer)update("mypageOrderDAO.updateCashBillReq", param);
	}
	/**
	    * @Method : insertTaxBillReq
	    * @Date: 2017. 7. 4.
	    * @Author :  강 병 철
	    * @Description	:	세금계산서 발급 신청
	   */
	public int insertTaxBillReq(HashMap<String, Object> param) throws Exception{
		return (Integer)insert("mypageOrderDAO.insertTaxBillReq", param);
	}
	   /**
	    * @Method : getMyClaimList
	    * @Date: 2017. 8. 7.
	    * @Author :  서 정 길
	    * @Description	:	주문관리 - 취소, 반품, 교환 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getMyClaimList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("mypageOrderDAO.getMyClaimList", param);
	}

	   /**
	    * @Method : getMyClaimListCnt
	    * @Date: 2017. 8. 7.
	    * @Author :  서 정 길
	    * @Description	:	주문관리 - 취소, 반품, 교환 리스트 건수
	   */
	public int getMyClaimListCnt(HashMap<String, Object> param) throws Exception{
		return (Integer)selectListTotCnt("mypageOrderDAO.getMyClaimListCnt", param);
	}

	   /**
	    * @Method : getOrderInfo
	    * @Date: 2017. 8. 7.
	    * @Author :  서 정 길
	    * @Description	:	주문 상세 - 주문 마스터 정보
	   */
	public SqlMap getOrderInfo(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("mypageOrderDAO.getOrderInfo", param);
	}
	
	   /**
	    * @Method : getOrderDetailList
	    * @Date: 2017. 8. 7.
	    * @Author :  서 정 길
	    * @Description	:	주문 상세 - 주문 상세 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getOrderDetailList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("mypageOrderDAO.getOrderDetailList", param);
	}

	   /**
	    * @Method : getOrderGiftList
	    * @Date: 2017. 8. 9.
	    * @Author :  서 정 길
	    * @Description	:	주문 상세 - 사은품 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getOrderGiftList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("mypageOrderDAO.getOrderGiftList", param);
	}

	   /**
	    * @Method : getPaycoResultList
	    * @Date: 2017. 8. 8.
	    * @Author :  서 정 길
	    * @Description	:	주문 상세 - PAYCO 결제 결과 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getPaycoResultList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("mypageOrderDAO.getPaycoResultList", param);
	}
	
	   /**
	    * @Method : getOrderDetailValidStatusCnt
	    * @Date: 2017. 8. 11.
	    * @Author :  서 정 길
	    * @Description	:	주문 상세 - 선택한 상품들 중 해당 상태에 맞는 상품 건 수
	   */
	public int getOrderDetailValidStatusCnt(HashMap<String, Object> param) throws Exception{
		return (Integer)selectListTotCnt("mypageOrderDAO.getOrderDetailValidStatusCnt", param);
	}	

	   /**
	    * @Method : updateOrderStatusCdAsDetailMinOrderStatusCd
	    * @Date: 2017. 8. 11.
	    * @Author :  서 정 길
	    * @Description	:	주문 상세의 주문 상태 코드들 최소값으로 주문 마스터의 주문 상태 코드 변경
	   */
	public int updateOrderStatusCdAsDetailMinOrderStatusCd(HashMap<String, Object> param) throws Exception{
		return (Integer)update("mypageOrderDAO.updateOrderStatusCdAsDetailMinOrderStatusCd", param);
	}

	   /**
	    * @Method : updateDetailOrderStatusCd
	    * @Date: 2017. 8. 11.
	    * @Author :  서 정 길
	    * @Description	:	주문 디테일 주문상태 변경
	   */
	public int updateDetailOrderStatusCd(HashMap<String, Object> param) throws Exception{
		return (Integer)update("mypageOrderDAO.updateDetailOrderStatusCd", param);
	}

	   /**
	    * @Method : insertOrderStatusLog
	    * @Date: 2017. 8. 11.
	    * @Author :  서 정 길
	    * @Description	:	주문 상세 로그 저장
	   */
	public int insertOrderStatusLog(HashMap<String, Object> param) throws Exception{
		return (Integer)insert("mypageOrderDAO.insertOrderStatusLog", param);
	}

	   /**
	    * @Method : insertClaimDetail
	    * @Date: 2017. 8. 11.
	    * @Author :  서 정 길
	    * @Description	:	클레임 디테일 등록
	   */
	public int insertClaimDetail(HashMap<String, Object> param) throws Exception{
		return (Integer)insert("mypageOrderDAO.insertClaimDetail", param);
	}

	   /**
	    * @Method : insertClaimStatusLog
	    * @Date: 2017. 8. 11.
	    * @Author :  서 정 길
	    * @Description	:	클레임 상세 로그 저장
	   */
	public int insertClaimStatusLog(HashMap<String, Object> param) throws Exception{
		return (Integer)insert("mypageOrderDAO.insertClaimStatusLog", param);
	}

	   /**
	    * @Method : updateClaimFile
	    * @Date: 2017. 8. 11.
	    * @Author :  서 정 길
	    * @Description	:	클레임 파일 업데이트
	   */
	public int updateClaimFile(HashMap<String, Object> param) throws Exception{
		return (Integer)update("mypageOrderDAO.updateClaimFile", param);
	}
	
	   /**
	    * @Method : insertRefundAccount
	    * @Date: 2017. 8. 11.
	    * @Author :  서 정 길
	    * @Description	:	환불 계좌 저장
	   */
	public int insertRefundAccount(HashMap<String, Object> param) throws Exception{
		return (Integer)insert("mypageOrderDAO.insertRefundAccount", param);
	}
	   /**
	    * @Method : getClaimDetailList
	    * @Date: 2017. 8. 8.
	    * @Author :  강병철
	    * @Description	:	claim 주문 상품 목록
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getClaimDetailList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("mypageOrderDAO.getClaimDetailList", param);
	}
	/**
	    * @Method : orderInsertPointHistory
	    * @Date: 2017. 8. 8.
	    * @Author : 강병철
	    * @Description	:	구매확정시 주문 포인트 history추가
	   */
	public int orderInsertPointHistory(HashMap<String, Object> param) throws Exception {
		return (Integer)insert("mypageOrderDAO.orderInsertPointHistory", param);
	}
	
	/**
	    * @Method : memberPointUpdate
	    * @Date: 2017. 8. 8.
	    * @Author : 강병철
	    * @Description	:	회원 보유 포인트 변경
	   */
	public int memberPointUpdate(HashMap<String, Object> param) throws Exception {
		return (Integer)update("mypageOrderDAO.memberPointUpdate", param);
	}
	
	   /**
	    * @Method : getOrderSavePoint
	    * @Date: 2017. 8. 11.
	    * @Author :  강 병 철
	    * @Description	:	적립포인트 구하기
	   */
	public int getOrderSavePoint(HashMap<String, Object> param) throws Exception{
		return (Integer)selectListTotCnt("mypageOrderDAO.getOrderSavePoint", param);
	}	

	/**
	 * @Method : getRecommendIdx
	 * @Date		: 2018. 3. 2.
	 * @Author	:  서  정  길 
	 * @Description	:	회원가입시 추천인 반환
	 */
	public Integer getRecommendIdx(HashMap<String, Object> param) throws Exception{
		return (Integer)selectOne("mypageOrderDAO.getRecommendIdx", param);
	}	

	/**
	 * @Method : updateRecommendIdxNull
	 * @Date		: 2018. 3. 2.
	 * @Author	:  서  정  길 
	 * @Description	:	회원 추천인 NULL로 수정
	 */
	public int updateRecommendIdxNull(HashMap<String, Object> param) throws Exception {
		return (Integer)update("mypageOrderDAO.updateRecommendIdxNull", param);
	}

	   /**
	    * @Method : getOrderMasterInfo
	    * @Date: 2017. 8. 7.
	    * @Author :  서 정 길
	    * @Description	:	주문 상세 - 주문 마스터 정보
	   */
	public SqlMap getOrderMasterInfo(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("mypageOrderDAO.getOrderMasterInfo", param);
	}
	
	/**
	    * @Method : updateOrderGiftStatus
	    * @Date: 2017. 8. 8.
	    * @Author : 강병철
	    * @Description	:	사은품 상태 변경
	   */
	public int updateOrderGiftStatus(HashMap<String, Object> param) throws Exception {
		return (Integer)update("mypageOrderDAO.updateOrderGiftStatus", param);
	}
	
	   /**
	    * @Method : updateClaimSumDetail
	    * @Date: 2017. 7. 17.
	    * @Author :  강 병 철
	    * @Description	:	클레임 마스터 테이블 합계 update
	   */
	public int updateClaimSumDetail(HashMap<String, Object> param) throws Exception{
		return (Integer)update("mypageOrderDAO.updateClaimSumDetail", param);
	}

	/**
	 * @Method : getSmilepayResult
	 * @Date: 2017. 12. 17.
	 * @Author :  김  민  수
	 * @Description	:	스마일 페이 결제결과
	 * @HISTORY :
	 *
	 */
	public SqlMap getSmilepayResult(HashMap<String, Object> param)throws Exception{
		return (SqlMap)selectOne("mypageOrderDAO.getSmilepayResult", param);
	}
	

	/**
	 * @Method : getWonderpayResult
	 * @Date: 2018.11.02
	 * @Author :  강 병 철
	 * @Description	:	원더페이 결제결과
	 * @HISTORY :
	 *
	 */
	public SqlMap getWonderpayResult(HashMap<String, Object> param)throws Exception{
		return (SqlMap)selectOne("mypageOrderDAO.getWonderpayResult", param);
	}

	
}
	
