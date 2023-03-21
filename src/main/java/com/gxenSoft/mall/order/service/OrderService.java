package com.gxenSoft.mall.order.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.gxenSoft.mall.cart.vo.CartVO;
import com.gxenSoft.mall.common.vo.JsonResultVO;
import com.gxenSoft.mall.lgdacom.vo.PaycoApprovalVO;
import com.gxenSoft.mall.lgdacom.vo.PaycoPaymentVO;
import com.gxenSoft.mall.lgdacom.vo.XPayBillKeyVO;
import com.gxenSoft.mall.lgdacom.vo.XPayResultVO;
import com.gxenSoft.mall.order.vo.EscrowResultVO;
import com.gxenSoft.mall.order.vo.KcpNotiReqVO;
import com.gxenSoft.mall.order.vo.OrderVO;
import com.gxenSoft.mall.order.vo.ProcCouponVO;
import com.gxenSoft.mall.order.vo.WonderVO;
import com.gxenSoft.mall.product.vo.NpayWishVO;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.pg.smilepay.vo.SmilepayCancelVO;
import com.gxenSoft.util.pg.smilepay.vo.SmilepayResultVO;

public interface OrderService {

	   /**
	    * @Method : insertCartOrderInfo
	    * @Date: 2017. 7. 4.
	    * @Author :  서 정 길
	    * @Description	:	장바구니 정보 주문 테이블에 저장
	    * 파라미터 : isOneClick : Y : 상품상세 원클릭 구매, N : 그 외 구매
	   */
	public JsonResultVO insertCartOrderInfo(OrderVO vo, String isOneClick, HttpSession session) throws Exception;
	
	   /**
	    * @Method : getGoodsInfo
	    * @Date: 2017. 7. 5.
	    * @Author :  서 정 길
	    * @Description	:	상품 정보 (세트 상품인지 확인)
	   */
	public SqlMap getGoodsInfo(Integer goodsIdx) throws Exception;
	
	   /**
	    * @Method : getOrderGoodsList
	    * @Date: 2017. 7. 6.
	    * @Author :  서 정 길
	    * @Description	:	주문 상품 리스트
	   */
	public List<SqlMap> getOrderGoodsList(OrderVO vo) throws Exception;
	
	   /**
	    * @Method : getOrderInfo
	    * @Date: 2017. 7. 18.
	    * @Author :  서 정 길
	    * @Description	:	주문 마스터 정보
	   */
	public SqlMap getOrderInfo(OrderVO vo) throws Exception;

	   /**
	    * @Method : getAddressList
	    * @Date: 2017. 7. 10.
	    * @Author :  서 정 길
	    * @Description	:	배송지 리스트
	   */
	public List<SqlMap> getAddressList(OrderVO vo) throws Exception;
	
	   /**
	    * @Method : getMemberDetail
	    * @Date: 2017. 7. 10.
	    * @Author :  서 정 길
	    * @Description	:	회원 상세
	   */
	public SqlMap getMemberDetail(Integer memberIdx) throws Exception;
	
	   /**
	    * @Method : getOrderCompleteInfo
	    * @Date: 2017. 7. 13.
	    * @Author :  서 정 길
	    * @Description	:	주문 정보 (주문 완료 페이지)
	   */
	public SqlMap getOrderCompleteInfo(OrderVO vo) throws Exception;
	
	   /**
	    * @Method : getOrderIdx
	    * @Date: 2017. 7. 17.
	    * @Author :  서 정 길
	    * @Description	:	주문 번호 반환
	   */
	public String getOrderIdx(OrderVO vo) throws Exception;

	   /**
	    * @Method : getOrderIdxByOrderCdOnly
	    * @Date: 2017. 7. 20.
	    * @Author :  서 정 길
	    * @Description	:	주문 번호 반환 (주문 코드로만 비교)
	   */
	public String getOrderIdxByOrderCdOnly(String orderCd) throws Exception;

	/**
	 * @Method : getOrderCd
	 * @Date		: 2018. 3. 6.
	 * @Author	:  서  정  길 
	 * @Description	:	주문 코드 반환
	 */
	public String getOrderCd(String orderIdx) throws Exception;

	   /**
	    * @Method : procSpPgCheckBefore
	    * @Date: 2017. 7. 17.
	    * @Author :  서 정 길
	    * @Description	:	결제 전 주문 유효성 체크
	   */
	public SqlMap procSpPgCheckBefore(String orderIdx) throws Exception;

	   /**
	    * @Method : procSpPgCheckAfter
	    * @Date: 2017. 7. 18.
	    * @Author :  서 정 길
	    * @Description	:	결제 후 주문 유효성 체크 & 주문 상태 변경
	   */
	public SqlMap procSpPgCheckAfter(String orderIdx) throws Exception;

	   /**
	    * @Method : procSpPriceDiv
	    * @Date: 2017. 7. 17.
	    * @Author :  서 정 길
	    * @Description	:	주문 디테일 금액 나누어 저장
	   */
	public SqlMap procSpPriceDiv(String orderIdx) throws Exception;
	
	   /**
	    * @Method : updateOrderMasterInfo
	    * @Date: 2017. 7. 17.
	    * @Author :  서 정 길
	    * @Description	:	주문 정보 DB 수정
	   */
	public int updateOrderMasterInfo(OrderVO vo) throws Exception;
	

	   /**
	    * @Method : insertBillKey
	    * @Date: 2017. 7. 17.
	    * @Author :  강 병 철
	    * @Description	:	빌키 신규 등록
	   */
	public int insertBillKey(XPayBillKeyVO vo) throws Exception;

	/**
	    * @Method : deleteBillKey
	    * @Date: 2017. 7. 17.
	    * @Author :  강 병 철
	    * @Description	:	빌키 삭제
	   */
	public int deleteBillKey(int billkeyIdx, int memberIdx) throws Exception;
	
	/**
	    * @Method : modifyBillKey
	    * @Date: 2017. 7. 17.
	    * @Author :  강 병 철
	    * @Description	:	빌키 삭제
	   */
	int modifyBillKey(int billkeyIdx, int memberIdx, String cardNm, String mainYn) throws Exception ;

	/**
	    * @Method : getBillkeyList
	    * @Date: 2017. 8. 3.
	    * @Author :  서 정 길
	    * @Description	:	빌키 리스트
	   */
	public List<SqlMap> getBillkeyList(Integer memberIdx) throws Exception;
	
	   /**
	    * @Method : selectMainBillkey
	    * @Date: 2017. 7. 17.
	    * @Author :  강 병 철
	    * @Description	:	대표 빌키 목록 
	   */
	public SqlMap selectMainBillkey(Integer memberIdx) throws Exception;
	
	   /**
	    * @Method : updateCasNoteUrlOrderMaster
	    * @Date: 2017. 7. 19.
	    * @Author :  서 정 길
	    * @Description	:	가상계좌 입금 확인시 - 마스터, 디테일 주문 상태 결제 완료로 수정
	   */
	public int updateCasNoteUrlOrderMaster(String orderCd, String payDt) throws Exception;
	
	   /**
	    * @Method : insertXPayResult
	    * @Date: 2017. 7. 21.
	    * @Author :  서 정 길
	    * @Description	:	LG U+ 결제 결과 로그 저장 (성공건만 저장됨)
	   */
	public int insertXPayResult(XPayResultVO vo) throws Exception;
	
	   /**
	    * @Method : insertXPayResultLog
	    * @Date: 2017. 7. 20.
	    * @Author :  서 정 길
	    * @Description	:	LG U+ 결제 결과 로그 저장 (성공/실패 모두 저장됨)
	   */
	public int insertXPayResultLog(XPayResultVO vo) throws Exception;
	
		   /**
	    * @Method : dbProcessAfterXPay
	    * @Date: 2017. 7. 21.
	    * @Author :  서 정 길
	    * @Description	:	LG U+ 결제 후 DB 처리
	   */
	public JsonResultVO dbProcessAfterXPay(XPayResultVO resultVo) throws Exception;

   /**
    * @Method : insertPaycoReserve
    * @Date: 2017. 6. 15.
    * @Author :  강 병 철
    * @Description	:	PAYCO 주문예약 저장
   */
	public void insertPaycoReserve(HashMap<String, Object> map) throws Exception;
	
	/**
    * @Method : insertPaycoApproval
    * @Date: 2017. 6. 15.
    * @Author :  강 병 철
    * @Description	:	PAYCO 주문 결제정보 저장
   */	
	public void insertPaycoApprovalPayment(PaycoApprovalVO vo) throws Exception ;
	   
	/**
	    * @Method : dbProcessAfterPayco
	    * @Date: 2017. 7. 21.
	    * @Author :  강 병 철
	    * @Description	:	Payco 결제 후 DB 처리
	   */
	public JsonResultVO dbProcessAfterPayco(String orderCd, PaycoApprovalVO appVo) throws Exception ;
	
	   /**
	    * @Method : getDefaultAddressInfo
	    * @Date: 2017. 7. 24.
	    * @Author :  서 정 길
	    * @Description	:	기본 배송지 정보
	   */
	public SqlMap getDefaultAddressInfo(Integer memberIdx) throws Exception;
	
	   /**
	    * @Method : getShippingPrice
	    * @Date: 2017. 7. 24.
	    * @Author :  서 정 길
	    * @Description	:	배송비 반환
	   */
	public double getShippingPrice(Double orderPriceSum) throws Exception;
	
	   /**
	    * @Method : getRandomPriceGiftInfo
	    * @Date: 2017. 7. 24.
	    * @Author :  서 정 길
	    * @Description	:	구매금액별 사은품 랜덤하게 1개 반환
	   */
	public SqlMap getRandomPriceGiftInfo(String orderCd) throws Exception;
	
	   /**
	    * @Method : insertPriceGift
	    * @Date: 2017. 7. 25.
	    * @Author :  서 정 길
	    * @Description	:	구매 금액별 사은품 저장
	   */
	public JsonResultVO insertPriceGift(OrderVO vo) throws Exception;
	
	   /**
	    * @Method : insertAddress
	    * @Date: 2017. 7. 27.
	    * @Author :  서 정 길
	    * @Description	:	배송지 목록에 저장 처리
	   */
	public int insertAddress(String orderCd, HttpServletRequest request) throws Exception;
	
	   /**
	    * @Method : deleteAddress
	    * @Date: 2017. 7. 27.
	    * @Author :  서 정 길
	    * @Description	:	배송지 삭제
	   */
	public int deleteAddress(OrderVO vo) throws Exception;
	
	   /**
	    * @Method : updateAddress
	    * @Date: 2017. 7. 27.
	    * @Author :  서 정 길
	    * @Description	:	배송지 수정
	   */
	public int updateAddress(OrderVO vo, HttpServletRequest request) throws Exception;
	 
	/**
	    * @Method : checkPromotionCode
	    * @Date: 2017. 7. 27.
	    * @Author :  강 병 철
	    * @Description	:	프로모션코드 체크
	   */
	public SqlMap checkPromotionCode(String promotioncode) throws Exception;
	
	/**
	    * @Method : checkPromotionCodeRandom
	    * @Date: 2017. 7. 27.
	    * @Author :  강 병 철
	    * @Description	:	프로모션코드랜덤 체크
	   */
	public SqlMap checkPromotionCodeRandom(String promotioncoderandom) throws Exception;
	
	/**
	    * @Method : applyPromotionCode
	    * @Date: 2017. 7. 27.
	    * @Author :  강 병 철
	    * @Description	:	프로모션코드 적용
	   */
	public SqlMap applyPromotionCode(String orderIdx, String promotioncode, int discountRate) throws Exception;
	
	/**
	    * @Method : applyPromotionCode
	    * @Date: 2017. 7. 27.
	    * @Author :  강 병 철
	    * @Description	:	랜덤프로모션코드 적용
	   */
	public SqlMap applyPromotionCodeRandom(String orderIdx, String promotioncoderandom, int discountRate, String randomcodeYn) throws Exception;
	

	   /**
	    * @Method : procUsableCouponList
	    * @Date: 2017. 7. 17.
	    * @Author :  강 병 철
	    * @Description	:	사용 가능한 쿠폰 목록
	    * P_ORDER_IDX			INT					주문 마스터 일련번호
			P_ORDER_DETAIL_IDX	INT					주문 디테일 일련번호	 상품쿠폰일때만 설정하면 된다. 다른쿠폰일때에는 0으로 설정.
			P_COUPON_GUBUN		CHAR(1)				G : 상품쿠폰, C : 장바구니쿠폰, S : 무료배송쿠폰
	   */
	public List<ProcCouponVO> procUsableCouponList(int orderIdx, int orderdetailIdx, String couponGubun) throws Exception;
	
	/**
	    * @Method : applyShippingCoupon
	    * @Date: 2017. 7. 27.
	    * @Author :  강 병 철
	    * @Description	:	배송쿠폰적용
	   */
	public SqlMap applyShippingCoupon(String orderIdx, int couponIdx) throws Exception;


	/**
	    * @Method : applyCartCoupon
	    * @Date: 2017. 7. 27.
	    * @Author :  강 병 철
	    * @Description	:	장바구니쿠폰적용
	   */
	public SqlMap applyCartCoupon(String orderIdx, int couponIdx) throws Exception;	
	   /**
	    * @Method : applyGiftCoupon
	    * @Date: 2017. 7. 31.
	    * @Author :  서 정 길
	    * @Description	:	상품쿠폰적용
	   */
	public SqlMap applyGiftCoupon(String orderIdx, String couponInfo) throws Exception;

	
	/**
	    * @Method : applyUsePoint
	    * @Date: 2017. 7. 27.
	    * @Author :  강 병 철
	    * @Description	:	포인트 결제 적용
	   */
	public SqlMap applyUsePoint(String orderIdx, int usePoint) throws Exception;

	   /**
	    * @Method : dbProcessAfterPointPayment
	    * @Date: 2017. 8. 1.
	    * @Author :  서 정 길
	    * @Description	:	포인트 결제 DB 처리
	   */
	public JsonResultVO dbProcessAfterPointPayment(OrderVO vo) throws Exception;

	   /**
	    * @Method : deleteDefaultPayment
	    * @Date: 2017. 8. 2.
	    * @Author :  서 정 길
	    * @Description	:	기본 결제수단 삭제
	   */
	public int deleteDefaultPayment(OrderVO vo) throws Exception;
	
	   /**
	    * @Method : insertDefaultPayment
	    * @Date: 2017. 8. 2.
	    * @Author :  서 정 길
	    * @Description	:	기본 결제수단 저장
	   */
	public int insertDefaultPayment(OrderVO vo) throws Exception;

	   /**
	    * @Method : updatePaycoCasNoteUrlOrderMaster
	    * @Date: 2017. 8. 9.
	    * @Author :  서 정 길
	    * @Description	:	PAYCO 가상계좌 입금 확인시 - 주문 상태 결제 완료로 수정등 처리
	   */
	public JsonResultVO updatePaycoCasNoteUrlOrderMaster(String orderCd, List<PaycoPaymentVO> paymentList) throws Exception;

	   /**
	    * @Method : updateOrderMasterCyberAccountInfo
	    * @Date: 2017. 8. 10.
	    * @Author :  서 정 길
	    * @Description	:	가상계좌 결제 - 주문 마스터 가상계좌 정보 수정
	    * orderCd : 주문코드
	    * type : LG U+, PAYCO 구분 (XPAY : LG U+, PAYCO : PAYCO)
	    * bankcode : 은행코드
	    * account : 계좌번호
	    * depositor : 예금주명
	    * depositDeadlineDt : 입금 기한
	   */
	public int updateOrderMasterCyberAccountInfo(String orderCd, String type, String bankCode, String account, String depositor, String depositDeadlineDt) throws Exception;
	
	   /**
	    * @Method : insertCyberAccountOrderStatusLog
	    * @Date: 2017. 8. 10.
	    * @Author :  서 정 길
	    * @Description	:	가상계좌 입금 완료시 주문 상태 로그 저장
	   */
	public int insertCyberAccountOrderStatusLog(String orderCd) throws Exception;
	   
	/**
	    * @Method : callSPSmsSendAndEmail
	    * @Date: 2017. 8. 10.
	    * @Author :  강 병 철
	    * @Description	:	주문 SMS / 이메일 발송 
	    * Gubun ORD001 = 가상계좌, ORD002 = 그외 결제
	   */
	public void callSPSmsSendAndEmail(String Gubun,  String orderCd) throws Exception;
	
	/**
	    * @Method : callOrderClaimSmsSendAndEmail
	    * @Date: 2017. 8. 10.
	    * @Author :  강 병 철
	    * @Description	:	주문 SMS / 이메일 발송 
	    * Gubun 값 
	    * CALL SP_SMS_SEND( 'ORD004', 0 , 1, '', '', '' );		-- 주문 취소_품절로 인한 취소  ( CLAIM_IDX를 파라미터로 전달 )
			CALL SP_SMS_SEND( 'ORD005', 0 , 1, '', '', '' );		-- 주문 취소_미입금으로 인한 취소  ( CLAIM_IDX를 파라미터로 전달 )
			CALL SP_SMS_SEND( 'ORD006', 0 , 1, '', '', '' );		-- 주문취소처리완료  ( CLAIM_IDX를 파라미터로 전달 )
			CALL SP_SMS_SEND( 'ORD007', 0 , 1, '', '', '' );		-- 교환 신청 완료  ( CLAIM_IDX를 파라미터로 전달 )
			CALL SP_SMS_SEND( 'ORD008', 0 , 1, '', '', '' );		-- 반품 신청 완료  ( CLAIM_IDX를 파라미터로 전달 )

	   */
	public void callOrderClaimSmsSendAndEmail(String Gubun,  String orderCd, int ClaimIdx) throws Exception ;
	
	   /**
	    * @Method : getCasNoteOrderInfo
	    * @Date: 2017. 9. 4.
	    * @Author :  서 정 길
	    * @Description	:	주문 마스터 상태 코드
	   */
	public String getOrderStatusCd(String orderCd) throws Exception;

	   /**
	    * @Method : insertXPayEscrowResultLog
	    * @Date: 2017. 9. 28.
	    * @Author :  서 정 길
	    * @Description	:	LG U+ 에스크로 결과 로그 저장
	   */
	public int insertXPayEscrowResultLog(EscrowResultVO vo) throws Exception;
	
	   /**
	    * @Method : updateOrderDetailDeliveredDt
	    * @Date: 2017. 9. 29.
	    * @Author :  서 정 길
	    * @Description	:	에스크로 택배사배송완료 수신 - 배송완료일 저장
	   */
	public int updateOrderDetailDeliveredDt(String orderCd, String deliveredDt) throws Exception;
	
	/**
	 * @Method : saveSmilePayResult
	 * @Date: 2017. 12. 8.
	 * @Author :  김  민  수
	 * @Description	:	스마일 페이 결제 처리 결과 저장(성공/실패)	
	 * @HISTORY :
	 *
	 */
	public int saveSmilePayResult(SmilepayResultVO smilepayResultVO)throws Exception;
	
	/**
	 * @Method : saveSamilPayCancelResult
	 * @Date: 2017. 12. 8.
	 * @Author :  김  민  수
	 * @Description	:	스마일페이 결제취소 처리 결과 저장(성공/실패)	
	 * @HISTORY :
	 *
	 */
	public int saveSamilPayCancelResult(SmilepayCancelVO SmilepayCancelVO)throws Exception;
	
	/**
	 * @Method : dbProcessAfterSmilepay
	 * @Date: 2017. 12. 10.
	 * @Author :  김  민  수
	 * @Description	:	스마일 페이 결제 후 DB 처리 
	 * @HISTORY :
	 *
	 */
	public JsonResultVO dbProcessAfterSmilepay(SmilepayResultVO smilepayResultVO)throws Exception;
	
	/**
	 * @Method : getGiftList
	 * @Date		: 2018. 2. 22.
	 * @Author	:  서  정  길 
	 * @Description	:	주문 페이지 구매금액별 사은품 리스트
	 */
	public List<SqlMap> getGiftList(String freeYn, String orderCd) throws Exception;

	/**
	 * @Method : getNoGiftSelectList
	 * @Date		: 2018. 3. 27.
	 * @Author	:  서  정  길 
	 * @Description	:	주문 페이지 사은품 선택 안함 1개
	 */
	public SqlMap getNoGiftSelectList(String orderCd) throws Exception;

	/**
	 * @Method : saveFreeGift
	 * @Date		: 2018. 3. 6.
	 * @Author	:  서  정  길 
	 * @Description	:	무료 사은품 내역 저장
	 */
	public int saveFreeGift(String orderIdx) throws Exception;
	
	/**
	 * @Method : nPayOrderRegister
	 * @Date		: 2018. 06. 12
	 * @Author	:  강병철
	 * @Description	:	nPay 주문 등록
	 */
	public String nPayOrderRegister(List<CartVO> vo, HttpServletRequest request) throws Exception ;
	
	public String callHttpXmlClient(String apiUrl, String xmlData) throws Exception;
	

	/**
	 * @Method : nPayWishRegister
	 * @Date		: 2018. 06. 12
	 * @Author	:  강병철
	 * @Description	:	nPay 찜 등록
	 */
	
	   /**
	    * @Method : dbProcessAfterKCP
	    * @Date: 2018.07.17
	    * @Author :  강 병 철
	    * @Description	:	KCP 결제 후 DB 처리
	   */
	public JsonResultVO dbProcessAfterKCP(XPayResultVO resultVo) throws Exception ;
	
	 /**
     * @Method : insertKcpNotiReqVO
    * @Date: 2018.07.17
    * @Author :  강 병 철
    * @Description	: kcp 가상계좌 입금 / 에스크로 연동 로그 저장
   */
	public void insertKcpNotiReqVO(KcpNotiReqVO vo) throws Exception;
	
	public String nPayWishRegister(String goodsIdx) throws Exception ;
	public String callHttpClient(String apiUrl, NpayWishVO data ) throws Exception;


	/**
	 * @Method : dbProcessAfterWonder
	 * @Date: 2018.11.02
	 * @Author :  강 병 철
	 * @Description	:	원더페이 결제 후 DB 처리 
	 * @HISTORY :
	 *
	 */
	public JsonResultVO dbProcessAfterWonder(WonderVO vo)throws Exception;
	/**
	 * @Method : saveWonderPayResult
	 * @Date: 2018.11.02
	 * @Author :  강 병 철
	 * @Description	:	원더페이 처리 결과 저장 (결제요청)
	 * @HISTORY :
	 *
	 */
	public int saveWonderPayResult(WonderVO resultVO)throws Exception;
}
