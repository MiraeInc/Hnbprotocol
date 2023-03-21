package com.gxenSoft.mall.order.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gxenSoft.mall.lgdacom.vo.PaycoApprovalVO;
import com.gxenSoft.mall.lgdacom.vo.PaycoPaymentVO;
import com.gxenSoft.mall.lgdacom.vo.XPayBillKeyVO;
import com.gxenSoft.mall.order.vo.ProcCouponVO;
import com.gxenSoft.sqlDAO.CommonDefaultDAO;
import com.gxenSoft.sqlMap.SqlMap;

@Repository("orderDAO")
public class OrderDAO extends CommonDefaultDAO {

	   /**
	    * @Method : insertOrderMaster
	    * @Date: 2017. 7. 4.
	    * @Author :  서 정 길
	    * @Description	:	주문 마스터 저장
	   */
	public int insertOrderMaster(HashMap<String, Object> param) throws Exception{
		return (Integer)insert("orderDAO.insertOrderMaster", param);
	}

	   /**
	    * @Method : insertOrderDetail
	    * @Date: 2017. 7. 4.
	    * @Author :  서 정 길
	    * @Description	:	주문 상세 저장
	   */
	public int insertOrderDetail(HashMap<String, Object> param) throws Exception{
		return (Integer)insert("orderDAO.insertOrderDetail", param);
	}

	   /**
	    * @Method : insertOrderSubDetail
	    * @Date: 2017. 7. 4.
	    * @Author :  서 정 길
	    * @Description	:	주문 서브 상세 저장
	   */
	public int insertOrderSubDetail(HashMap<String, Object> param) throws Exception{
		return (Integer)insert("orderDAO.insertOrderSubDetail", param);
	}

	   /**
	    * @Method : insertOrderGift
	    * @Date: 2017. 7. 24.
	    * @Author :  서 정 길
	    * @Description	:	사은품 내역 저장
	   */
	public int insertOrderGift(HashMap<String, Object> param) throws Exception{
		return (Integer)insert("orderDAO.insertOrderGift", param);
	}
	
	   /**
	    * @Method : getGoodsInfo
	    * @Date: 2017. 7. 5.
	    * @Author :  서 정 길
	    * @Description	:	상품 정보 (세트 상품인지 확인)
	   */
	public SqlMap getGoodsInfo(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("orderDAO.getGoodsInfo", param);
	}
	
	   /**
	    * @Method : getOrderGoodsList
	    * @Date: 2017. 7. 6.
	    * @Author :  서 정 길
	    * @Description	:	주문 상품 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getOrderGoodsList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("orderDAO.getOrderGoodsList", param);
	}
	
	   /**
	    * @Method : getOrderInfo
	    * @Date: 2017. 7. 18.
	    * @Author :  서 정 길
	    * @Description	:	주문 마스터 정보
	   */
	public SqlMap getOrderInfo(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("orderDAO.getOrderInfo", param);
	}

	   /**
	    * @Method : getOrderIdx
	    * @Date: 2017. 7. 17.
	    * @Author :  서 정 길
	    * @Description	:	주문 번호 반환
	   */
	public String getOrderIdx(HashMap<String, Object> param) throws Exception{
		return (String)selectOne("orderDAO.getOrderIdx", param);
	}

	/**
	 * @Method : getOrderCd
	 * @Date		: 2018. 3. 6.
	 * @Author	:  서  정  길 
	 * @Description	:	주문 번호 반환
	 */
	public String getOrderCd(HashMap<String, Object> param) throws Exception{
		return (String)selectOne("orderDAO.getOrderCd", param);
	}

	   /**
	    * @Method : getAddressList
	    * @Date: 2017. 7. 10.
	    * @Author :  서 정 길
	    * @Description	:	배송지 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getAddressList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("orderDAO.getAddressList", param);
	}

	   /**
	    * @Method : getMemberDetail
	    * @Date: 2017. 7. 10.
	    * @Author :  서 정 길
	    * @Description	:	회원 상세
	   */
	public SqlMap getMemberDetail(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("orderDAO.getMemberDetail", param);
	}
	
	   /**
	    * @Method : getCouponList
	    * @Date: 2017. 7. 13.
	    * @Author :  서 정 길
	    * @Description	:	적용된 쿠폰 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getCouponList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("orderDAO.getCouponList", param);
	}
	
	   /**
	    * @Method : getOrderCompleteInfo
	    * @Date: 2017. 7. 13.
	    * @Author :  서 정 길
	    * @Description	:	주문 정보 (주문 완료 페이지)
	   */
	public SqlMap getOrderCompleteInfo(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("orderDAO.getOrderCompleteInfo", param);
	}

	   /**
	    * @Method : updateTempOrderMaster
	    * @Date: 2017. 7. 14.
	    * @Author :  서 정 길
	    * @Description	:	주문 페이지 - 주문 마스터 수정
	   */
	public int updateTempOrderMaster(HashMap<String, Object> param) throws Exception{
		return (Integer)update("orderDAO.updateTempOrderMaster", param);
	}
	
	   /**
	    * @Method : updateTempOrderDetail
	    * @Date: 2017. 7. 14.
	    * @Author :  서 정 길
	    * @Description	:	주문 페이지 - 주문 상세 수정
	   */
	public int updateTempOrderDetail(HashMap<String, Object> param) throws Exception{
		return (Integer)update("orderDAO.updateTempOrderDetail", param);
	}

	   /**
	    * @Method : procSpPgCheckBefore
	    * @Date: 2017. 7. 17.
	    * @Author :  서 정 길
	    * @Description	:	결제 전 주문 유효성 체크
	   */
	public SqlMap procSpPgCheckBefore(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("orderDAO.procSpPgCheckBefore", param);
	}

	   /**
	    * @Method : procSpPgCheckAfter
	    * @Date: 2017. 7. 18.
	    * @Author :  서 정 길
	    * @Description	:	결제 후 주문 유효성 체크 & 주문 상태 변경
	   */
	public SqlMap procSpPgCheckAfter(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("orderDAO.procSpPgCheckAfter", param);
	}

	   /**
	    * @Method : procSpPriceDiv
	    * @Date: 2017. 7. 17.
	    * @Author :  서 정 길
	    * @Description	:	주문 디테일 금액 나누어 저장
	   */
	public SqlMap procSpPriceDiv(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("orderDAO.procSpPriceDiv", param);
	}
	
	   /**
	    * @Method : insertBillKey
	    * @Date: 2017. 7. 17.
	    * @Author :  강 병 철
	    * @Description	:	빌키 신규 등록
	   */
	public int insertBillKey(XPayBillKeyVO vo) throws Exception{
		return (Integer)insert("orderDAO.insertBillKey", vo);
	}	
	   /**
	    * @Method : deleteBillKey
	    * @Date: 2017. 7. 17.
	    * @Author :  강 병 철
	    * @Description	:	빌키 삭제
	   */
	public int deleteBillKey(HashMap<String, Object> param) throws Exception{
		return (Integer)delete("orderDAO.deleteBillKey", param);
	}	
	
	   /**
	    * @Method : getBillkeyList
	    * @Date: 2017. 8. 3.
	    * @Author :  서 정 길
	    * @Description	:	빌키 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getBillkeyList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("orderDAO.getBillkeyList", param);
	}

	   /**
	    * @Method : selectMainBillkey
	    * @Date: 2017. 7. 17.
	    * @Author :  강 병 철
	    * @Description	:	대표 빌키 목록 
	   */
	public SqlMap selectMainBillkey(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("orderDAO.selectMainBillkey", param);
	}
	   /**
	    * @Method : updateMainBillkey
	    * @Date: 2017. 7. 17.
	    * @Author :  강 병 철
	    * @Description	:	메인 빌키 수정
	   */
	public int updateMainBillkey(HashMap<String, Object> param) throws Exception{
		return (Integer)update("orderDAO.updateMainBillkey", param);
	}
	   /**
	    * @Method : updateTopMainBillkey
	    * @Date: 2017. 7. 17.
	    * @Author :  강 병 철
	    * @Description	:	최근등록한것을 대표 빌키로 으로 지정
	   */
	public int updateTopMainBillkey(HashMap<String, Object> param) throws Exception{
		return (Integer)update("orderDAO.updateTopMainBillkey", param);
	}
	
	   /**
	    * @Method : modifyBillKey
	    * @Date: 2017. 7. 17.
	    * @Author :  강 병 철
	    * @Description	:	빌키 수정
	   */
	public int modifyBillKey(HashMap<String, Object> param) throws Exception{
		return (Integer)update("orderDAO.modifyBillKey", param);
	}
	
	   /**
	    * @Method : updateCasNoteUrlOrderMaster
	    * @Date: 2017. 7. 19.
	    * @Author :  서 정 길
	    * @Description	:	가상계좌 입금 확인시 - 마스터, 디테일 주문 상태 결제 완료로 수정
	   */
	public int updateCasNoteUrlOrderMaster(HashMap<String, Object> param) throws Exception{
		return (Integer)update("orderDAO.updateCasNoteUrlOrderMaster", param);
	}

	   /**
	    * @Method : updateCasNoteUrlOrderSubDetail
	    * @Date: 2017. 8. 10.
	    * @Author :  서 정 길
	    * @Description	:	가상계좌 입금 확인시 - 서브 디테일 주문 상태 결제 완료로 수정
	   */
	public int updateCasNoteUrlOrderSubDetail(HashMap<String, Object> param) throws Exception{
		return (Integer)update("orderDAO.updateCasNoteUrlOrderSubDetail", param);
	}
	
	   /**
	    * @Method : insertXPayResult
	    * @Date: 2017. 7. 21.
	    * @Author :  서 정 길
	    * @Description	:	LG U+ 결제 결과 로그 저장 (성공건만 저장됨)
	   */
	public int insertXPayResult(HashMap<String, Object> param) throws Exception{
		return (Integer)insert("orderDAO.insertXPayResult", param);
	}

	   /**
	    * @Method : insertXPayResultLog
	    * @Date: 2017. 7. 20.
	    * @Author :  서 정 길
	    * @Description	:	LG U+ 결제 결과 로그 저장 (성공/실패 모두 저장됨)
	   */
	public int insertXPayResultLog(HashMap<String, Object> param) throws Exception{
		return (Integer)insert("orderDAO.insertXPayResultLog", param);
	}
	
	   /**
	    * @Method : insertPaycoReserve
	    * @Date: 2017. 7. 20.
	    * @Author :  강 병 철
	    * @Description	:	PAYCO 주문예약 저장
	   */
	public int insertPaycoReserve(HashMap<String, Object> param) throws Exception{
		return (Integer)insert("orderDAO.insertPaycoReserve", param);
	}

   /**
    * @Method : insertPaycoReserve
    * @Date: 2017. 7. 20.
    * @Author :  강 병 철
    * @Description	:	PAYCO 주문예약 저장
   */
	public int insertPaycoApproval(PaycoApprovalVO vo) throws Exception{
		return (Integer)insert("orderDAO.insertPaycoApproval", vo);
	}

   /**
    * @Method : insertPaycoPayment
    * @Date: 2017. 7. 20.
    * @Author :  강 병 철
    * @Description	:	PAYCO 주문예약 상세결제정보 저장
   */
	public int insertPaycoPayment(PaycoPaymentVO vo) throws Exception{
		return (Integer)insert("orderDAO.insertPaycoPayment", vo);
	}

	   /**
	    * @Method : getDefaultAddressList
	    * @Date: 2017. 7. 24.
	    * @Author :  서 정 길
	    * @Description	:	기본 배송지 정보
	   */
	public SqlMap getDefaultAddressInfo(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("orderDAO.getDefaultAddressInfo", param);
	}

	   /**
	    * @Method : getShippingPrice
	    * @Date: 2017. 7. 24.
	    * @Author :  서 정 길
	    * @Description	:	배송비 반환
	   */
	public double getShippingPrice(HashMap<String, Object> param) throws Exception{
		return (double)selectOne("orderDAO.getShippingPrice", param);
	}

	   /**
	    * @Method : getRandomPriceGiftInfo
	    * @Date: 2017. 7. 24.
	    * @Author :  서 정 길
	    * @Description	:	구매금액별 사은품 랜덤하게 1개 반환
	   */
	public SqlMap getRandomPriceGiftInfo(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("orderDAO.getRandomPriceGiftInfo", param);
	}
	
	   /**
	    * @Method : deleteOrderGift
	    * @Date: 2017. 7. 25.
	    * @Author :  서 정 길
	    * @Description	:	구매금액별 사은품 삭제
	   */
	public int deleteOrderGift(HashMap<String, Object> param) throws Exception{
		return (Integer)delete("orderDAO.deleteOrderGift", param);
	}
	
	   /**
	    * @Method : insertSelectedOrderGift
	    * @Date: 2017. 7. 25.
	    * @Author :  서 정 길
	    * @Description	:	선택한 구매금액별 사은품 내역 저장
	   */
	public int insertSelectedOrderGift(HashMap<String, Object> param) throws Exception{
		return (Integer)insert("orderDAO.insertSelectedOrderGift", param);
	}
	
	   /**
	    * @Method : updateAllAddressToNotDefaultYn
	    * @Date: 2017. 7. 25.
	    * @Author :  서 정 길
	    * @Description	:	배송지 모두 기본 배송지 아님으로 수정
	   */
	public int updateAllAddressToNotDefaultYn(HashMap<String, Object> param) throws Exception{
		return (Integer)update("orderDAO.updateAllAddressToNotDefaultYn", param);
	}
	
	   /**
	    * @Method : insertAddress
	    * @Date: 2017. 7. 25.
	    * @Author :  서 정 길
	    * @Description	:	배송지 목록에 저장
	   */
	public int insertAddress(HashMap<String, Object> param) throws Exception{
		return (Integer)insert("orderDAO.insertAddress", param);
	}
	
	   /**
	    * @Method : deleteAddress
	    * @Date: 2017. 7. 27.
	    * @Author :  서 정 길
	    * @Description	:	배송지 삭제
	   */
	public int deleteAddress(HashMap<String, Object> param) throws Exception{
		return (Integer)delete("orderDAO.deleteAddress", param);
	}
	
	   /**
	    * @Method : updateAddress
	    * @Date: 2017. 7. 27.
	    * @Author :  서 정 길
	    * @Description	:	배송지 수정
	   */
	public int updateAddress(HashMap<String, Object> param) throws Exception{
		return (Integer)update("orderDAO.updateAddress", param);
	}
	
	/**
	    * @Method : selectPromotionCodeRandom
	    * @Date: 2017. 7. 27.
		    * @Author :  강 병 철
	    * @Description	:	프로모션코드랜덤 체크
	   */
		public SqlMap selectPromotionCodeRandom(HashMap<String, Object> param) throws Exception{
			return(SqlMap)selectOne("orderDAO.selectPromotionCodeRandom", param);
		}
	
   /**
    * @Method : selectPromotionCode
    * @Date: 2017. 7. 27.
	    * @Author :  강 병 철
    * @Description	:	프로모션코드 체크
   */
	public SqlMap selectPromotionCode(HashMap<String, Object> param) throws Exception{
		return(SqlMap)selectOne("orderDAO.selectPromotionCode", param);
	}

   /**
    * @Method : updatePromotionCode
    * @Date: 2017. 7. 27.
	    * @Author :  강 병 철
    * @Description	:	프로모션코드 저장
   */
	public int updatePromotionCode(HashMap<String, Object> param) throws Exception{
		return (Integer)update("orderDAO.updatePromotionCode", param);
	}


	   /**
	    * @Method : procUsableCouponList
	    * @Date: 2017. 7. 17.
	    * @Author :  강 병 철
	    * @Description	:	사용 가능한 쿠폰 목록
	   */
	@SuppressWarnings("unchecked")
	public List<ProcCouponVO> procUsableCouponList(HashMap<String, Object> param) throws Exception{
		return (List<ProcCouponVO>)selectList("orderDAO.procUsableCouponList", param);
	}	
	   /**
	    * @Method : procCouponUse
	    * @Date: 2017. 7. 17.
	    * @Author :  강 병 철
	    * @Description	:	쿠폰적용
		 * 파라미터 설명
			P_ORDER_IDX			INT					주문 마스터 일련번호
			P_ORDER_DETAIL_IDX	INT					주문 디테일 일련번호	--> 상품쿠폰일때만 설정하면 된다. 다른쿠폰일때에는 0으로 설정.
			P_COUPON_GUBUN		CHAR(1)				G : 상품쿠폰, C : 장바구니쿠폰, S : 무료배송쿠폰
			P_COUPON_IDX		INT					쿠폰마스터 테이블의 IDX값.  (자동발급쿠폰일때에만 의미를 갖는다)   /  기존에 적용된 쿠폰을 제거할때는 0으로 설정한다
			P_COUPON_MEMBER_IDX	INT					회원별 쿠폰발급테이블의 IDX값.  (회원이 이미 다운로드 한 쿠폰을 사용했을때 의미를 갖는다)   /  기존에 적용된 쿠폰을 제거할때는 0으로 설정한다
		 * */
	public SqlMap procCouponUse(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("orderDAO.procCouponUse", param);
	}
	
	/**
    * @Method : updatePointPrice
    * @Date: 2017. 7. 27.
	    * @Author :  강 병 철
    * @Description	:	포인트 사용액 update
   */
	public int updatePointPrice(HashMap<String, Object> param) throws Exception{
		return (Integer)update("orderDAO.updatePointPrice", param);
	}

	   /**
	    * @Method : deleteDefaultPayment
	    * @Date: 2017. 8. 2.
	    * @Author :  서 정 길
	    * @Description	:	기본 결제수단 삭제
	   */
	public int deleteDefaultPayment(HashMap<String, Object> param) throws Exception{
		return (Integer)delete("orderDAO.deleteDefaultPayment", param);
	}
	
	   /**
	    * @Method : insertDefaultPayment
	    * @Date: 2017. 8. 2.
	    * @Author :  서 정 길
	    * @Description	:	기본 결제수단 저장
	   */
	public int insertDefaultPayment(HashMap<String, Object> param) throws Exception{
		return (Integer)insert("orderDAO.insertDefaultPayment", param);
	}

	   /**
	    * @Method : updatePaycoNonBankResult
	    * @Date: 2017. 8. 9.
	    * @Author :  서 정 길
	    * @Description	:	PAYCO 무통장 결제 결과 적용
	   */
	public int updatePaycoNonBankResult(HashMap<String, Object> param) throws Exception{
		return (Integer)update("orderDAO.updatePaycoNonBankResult", param);
	}

	   /**
	    * @Method : updateOrderMasterCyberAccountInfo
	    * @Date: 2017. 8. 10.
	    * @Author :  서 정 길
	    * @Description	:	가상계좌 결제 - 주문 마스터 가상계좌 정보 수정
	   */
	public int updateOrderMasterCyberAccountInfo(HashMap<String, Object> param) throws Exception{
		return (Integer)update("orderDAO.updateOrderMasterCyberAccountInfo", param);
	}

	   /**
	    * @Method : insertCyberAccountOrderStatusLog
	    * @Date: 2017. 8. 10.
	    * @Author :  서 정 길
	    * @Description	:	가상계좌 입금 완료시 주문 상태 로그 저장
	   */
	public int insertCyberAccountOrderStatusLog(HashMap<String, Object> param) throws Exception{
		return (Integer)insert("orderDAO.insertCyberAccountOrderStatusLog",param);
	}
	
	/**
	    * @Method : callSPSmsSendAndEmail
	    * @Date: 2017. 8. 10.
	    * @Author :  강 병 철
	    * @Description	:	주문 SMS / 이메일 발송 
	    * Gubun ORD001 = 가상계좌, ORD002 = 그외 결제
	   */
	public void callSPSmsSendAndEmail(HashMap<String, Object> param) throws Exception{
		selectOne("orderDAO.callSPSmsSendAndEmail",param);
	}

	   /**
	    * @Method : getOrderStatusCd
	    * @Date: 2017. 9. 4.
	    * @Author :  서 정 길
	    * @Description	:	주문 마스터 상태 코드 반환
	   */
	public String getOrderStatusCd(HashMap<String, Object> param) throws Exception{
		return (String)selectOne("orderDAO.getOrderStatusCd", param);
	}
	
	   /**
	    * @Method : insertXPayEscrowResultLog
	    * @Date: 2017. 9. 28.
	    * @Author :  서 정 길
	    * @Description	:	LG U+ 에스크로 결과 로그 저장
	   */
	public int insertXPayEscrowResultLog(HashMap<String, Object> param) throws Exception{
		return (Integer)insert("orderDAO.insertXPayEscrowResultLog", param);
	}

	   /**
	    * @Method : updateOrderDetailDeliveredDt
	    * @Date: 2017. 9. 29.
	    * @Author :  서 정 길
	    * @Description	:	에스크로 택배사배송완료 수신 - 배송완료일 저장
	   */
	public int updateOrderDetailDeliveredDt(HashMap<String, Object> param) throws Exception{
		return (Integer)update("orderDAO.updateOrderDetailDeliveredDt", param);
	}
	
	/**
	 * @Method : saveSmilePayResult
	 * @Date: 2017. 12. 8.
	 * @Author :  김  민  수
	 * @Description	:	스마일 페이 주문 결과 저장
	 * @HISTORY :
	 *
	 */
	public int saveSmilePayResult(HashMap<String, Object> param)throws Exception{
		return (Integer)insert("orderDAO.saveSmilePayResult", param);
	}

	/**
	 * @Method : saveSamilPayCancelResult
	 * @Date: 2017. 12. 8.
	 * @Author :  김  민  수
	 * @Description	:	스마일 페이 주문취소 결과 저장	
	 * @HISTORY :
	 *
	 */
	public int saveSamilPayCancelResult(HashMap<String, Object> param)throws Exception{
		return (Integer)insert("orderDAO.saveSamilPayCancelResult", param);
	}
	
	/**
	 * @Method : getGiftList
	 * @Date		: 2018. 2. 22.
	 * @Author	:  서  정  길 
	 * @Description	:	주문 페이지 구매금액별 사은품 리스트 (무료사은품은 cartDAO.getGiftList 를 사용함)
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getGiftList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("orderDAO.getGiftList", param);
	}

	/**
	 * @Method : getNoGiftSelectList
	 * @Date		: 2018. 3. 27.
	 * @Author	:  서  정  길 
	 * @Description	:	주문 페이지 사은품 선택 안함 1개
	 */
	public SqlMap getNoGiftSelectList(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("orderDAO.getNoGiftSelectList", param);
	}
	/**
	* @Method : insertKcpNotiReqVO
	* @Date: 2018.07.17
	* @Author :  강 병 철
	* @Description	: kcp 가상계좌 입금 / 에스크로 연동 로그 저장
	*
	*/
	public int insertKcpNotiReqVO(HashMap<String, Object> param)throws Exception{
		return (Integer)insert("orderDAO.insertKcpNotiReqVO", param);
	}
	/**
	 * @Method : saveWonderPayResult
	 * @Date: 2018. 11.02
	 * @Author :  강 병 철
	 * @Description	:	원더페이 주문 결과 저장
	 * @HISTORY :
	 *
	 */
	public int saveWonderPayResult(HashMap<String, Object> param)throws Exception{
		return (Integer)insert("orderDAO.saveWonderPayResult", param);
	}

}
