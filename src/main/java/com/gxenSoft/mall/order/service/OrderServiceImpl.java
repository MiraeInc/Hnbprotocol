package com.gxenSoft.mall.order.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import com.gxenSoft.mall.cart.service.CartService;
import com.gxenSoft.mall.cart.vo.CartVO;
import com.gxenSoft.mall.common.vo.JsonResultVO;
import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.lgdacom.vo.PaycoApprovalVO;
import com.gxenSoft.mall.lgdacom.vo.PaycoPaymentVO;
import com.gxenSoft.mall.lgdacom.vo.XPayBillKeyVO;
import com.gxenSoft.mall.lgdacom.vo.XPayResultVO;
import com.gxenSoft.mall.mypage.order.service.MypageOrderService;
import com.gxenSoft.mall.order.dao.OrderDAO;
import com.gxenSoft.mall.order.vo.EscrowResultVO;
import com.gxenSoft.mall.order.vo.KcpNotiReqVO;
import com.gxenSoft.mall.order.vo.NpayOrderInterfaceVO;
import com.gxenSoft.mall.order.vo.NpayOrderVO;
import com.gxenSoft.mall.order.vo.NpayProductSingleVO;
import com.gxenSoft.mall.order.vo.NpayProductVO;
import com.gxenSoft.mall.order.vo.NpayShippingConditionalFree;
import com.gxenSoft.mall.order.vo.NpayShippingPolicyVO;
import com.gxenSoft.mall.order.vo.OrderVO;
import com.gxenSoft.mall.order.vo.ProcCouponVO;
import com.gxenSoft.mall.order.vo.WonderVO;
import com.gxenSoft.mall.product.service.ProductService;
import com.gxenSoft.mall.product.vo.NpayWishVO;
import com.gxenSoft.message.SpringMessage;
import com.gxenSoft.method.ConvertUtil;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.mailUtil.MailUtil;
import com.gxenSoft.util.pathUtil.PathUtil;
import com.gxenSoft.util.pg.smilepay.vo.SmilepayCancelVO;
import com.gxenSoft.util.pg.smilepay.vo.SmilepayResultVO;


@Service("orderService")
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDAO orderDAO;

	@Autowired
	private CartService cartService;
	
	@Autowired
	private MypageOrderService mypageOrderService;
	
	@Autowired
	private ProductService productService;
	/**
	 * @Method : randomNumber
	 * @Date		: 2016. 7. 1.
	 * @Author	:  김  민  수
	 * @Description	:  랜덤 숫자 임의 생성 함수	
	 */
	public String randomNumber(int length) {  
		int index = 0;  
		char[] charSet = new char[] {  
				'0','1','2','3','4','5','6','7','8','9'  
				,'A','B','C','D','E','F','G','H','I','J','K','L','M'  
				,'N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};  
         
		StringBuffer sb = new StringBuffer();  
		for (int i=0; i<length; i++) {  
			index =  (int) (charSet.length * Math.random());  
			sb.append(charSet[index]);  
		}           
		
		return sb.toString();
	}
	
	   /**
	    * @Method : insertCartOrderInfo
	    * @Date: 2017. 7. 4.
	    * @Author :  서 정 길
	    * @Description	:	장바구니 정보 주문 테이블에 저장
	    * 파라미터 : isOneClick : Y : 상품상세 원클릭 구매, N : 그 외 구매 
	   */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public JsonResultVO insertCartOrderInfo(OrderVO vo, String isOneClick, HttpSession session) throws Exception{
		
		JsonResultVO resultMap = new JsonResultVO();
		
		int flag = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();

		try{
			// 주문코드 구함
			long time = System.currentTimeMillis();
			SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String orderCd = dayTime.format(new Date(time));
			
			if(PathUtil.getCtx().equals("/w")){
				orderCd += "P";
			}else if(PathUtil.getCtx().equals("/m")){
				orderCd += "M";
			}else{
				orderCd += "X";
			}
			
			orderCd += this.randomNumber(2);	// 주문코드
			
			session.setAttribute("orderCd", orderCd);	// 세션에 주문코드 저장
			
			// 주문 금액 합
			double orderPriceSum = 0;
			
			// 총 주문 금액 계산하고 세트상품 여부 미리 vo에 세팅
			for(int i=0;i<vo.getOrderGoodsInfoList().size();i++){
				Integer goodsIdx = vo.getOrderGoodsInfoList().get(i).getGoodsIdx();
				Integer goodsCnt = vo.getOrderGoodsInfoList().get(i).getGoodsCnt();

				// 상품 정보 (세트 상품인지 여부 확인, 할인가)
				SqlMap detail = this.getGoodsInfo(goodsIdx);
				vo.getOrderGoodsInfoList().get(i).setSetFlag(detail.get("setFlag").toString());		// 세트상품 미리 세팅 (OrderDetail 넣을때 쓰게) // 세트상품 구분 (Y : 세트 상품, N : 일반상품)
				vo.getOrderGoodsInfoList().get(i).setDiscountPrice(Double.valueOf(detail.get("discountPrice").toString()));	// 할인가
				Double discountPrice = Double.valueOf(detail.get("discountPrice").toString());	// 할인가
				
				orderPriceSum += discountPrice * goodsCnt;	// 총 주문금액 계산
			}

			// 배송비
			double shippingPrice = this.getShippingPrice(orderPriceSum);	// 배송비 반환

			// 네이버 타임보드 이벤트 (2018-04-23 회원 가입자 대상)면 배송비 0원 처리
			if(vo.getOrderGoodsInfoList().size() == 1 && vo.getOrderGoodsInfoList().get(0).getGoodsIdx() != null &&vo.getOrderGoodsInfoList().get(0).getGoodsIdx().intValue() == 823){
				shippingPrice = 0;
			}

			// 주문 마스터 저장
			map.clear();
			map.put("ORDERCD", orderCd);											// 주문코드 YYYYMMDDHHNNSSFFF + [P|M|A] + 랜덤값2자리 (P : PC, M : MOBILE, A : APP)
			if(vo.getRegIdx() > 0){	// 회원
				map.put("MEMBERORDERYN", "Y");								// 회원/비회원 구분 Y : 회원주문, N : 비회원주문
				map.put("MEMBERIDX", vo.getMemberIdx());					// 회원 일련번호
				map.put("MEMBERID", vo.getMemberId());						// 회원 ID
				map.put("MEMBERGRADEIDX", vo.getMemberGradeIdx());	// 회원등급 일련번호 TM_MEMBER_GRADE 일련번호
				map.put("MEMBERNM", vo.getMemberNm());					// 회원명(주문자명) 비회원일때는 주문자명
				map.put("SESSIONID", null);											// 비회원 세션 ID
				
				// 원클릭 결제시 결제수단, 대표 빌키 일련번호 저장
				if(isOneClick.equals("Y")){
					map.put("PAYTYPE", "PAY_TYPE15");							// 결제수단 (공통코드 PAY_TYPE10 : 신용카드, PAY_TYPE15 : 빌키, PAY_TYPE20 : 실시간계좌, PAY_TYPE25 : 가상계좌, PAY_TYPE30 : 휴대폰, PAY_TYPE35 : PAYCO, PAY_TYPE40 : 카카오페이, PAY_TYPE45 : Npay, PAY_TYPE50 : PAYNOW, PAY_TYPE90 : 포인트)
					
					SqlMap billkeyInfo = this.selectMainBillkey(vo.getMemberIdx());		// 대표 빌키 구하기
					map.put("BILLKEYIDX", (BigInteger)billkeyInfo.get("billkeyIdx"));		// 빌키 일련번호 (원클릭 결제시 결제된 빌키 일련번호)
				}else{
					map.put("PAYTYPE", null);										// 결제수단 (공통코드 PAY_TYPE10 : 신용카드, PAY_TYPE15 : 빌키, PAY_TYPE20 : 실시간계좌, PAY_TYPE25 : 가상계좌, PAY_TYPE30 : 휴대폰, PAY_TYPE35 : PAYCO, PAY_TYPE40 : 카카오페이, PAY_TYPE45 : Npay, PAY_TYPE50 : PAYNOW, PAY_TYPE90 : 포인트)
					map.put("BILLKEYIDX", null);									// 빌키 일련번호 (원클릭 결제시 결제된 빌키 일련번호)
				}
				
				// 기본 배송지 정보
				SqlMap defaultAddressInfo = this.getDefaultAddressInfo(vo.getMemberIdx());
				if(defaultAddressInfo != null && isOneClick.equals("Y")){
					map.put("SHIPPINGNM", (String)defaultAddressInfo.get("shippingNm"));					// 주소록명
					map.put("RECEIVERNM", (String)defaultAddressInfo.get("receiverNm"));					// 받는 사람 이름	
					map.put("RECEIVERADDR", (String)defaultAddressInfo.get("addr"));						// 받는 사람 주소	도로명주소
					map.put("RECEIVERADDRDETAIL", (String)defaultAddressInfo.get("addrDetail"));		// 받는 사람 상세주소	도로명", (String)defaultAddressInfo.get("receiverNm")); 지번 공통
					map.put("RECEIVERZIPCD", (String)defaultAddressInfo.get("zipCd"));						// 받는 사람 우편번호	신우편번호
					map.put("RECEIVEROLDADDR", (String)defaultAddressInfo.get("oldAddr"));				// 받는 사람 주소	지번주소
					map.put("RECEIVEROLDZIPCD", (String)defaultAddressInfo.get("oldZipCd"));			// 받는 사람 (구)우편번호	(-) 포함
					map.put("RECEIVERTELNO", (String)defaultAddressInfo.get("telNo"));					// 받는 사람 전화번호
					map.put("RECEIVERPHONENO", (String)defaultAddressInfo.get("phoneNo"));			// 받는 사람 휴대폰번호
					map.put("SELECTADDRESSIDX", String.valueOf(defaultAddressInfo.get("addressIdx")));		// 선택한 배송지 목록 일련번호
				}else{
					map.put("SHIPPINGNM", null);									// 주소록명
					map.put("RECEIVERNM", null);									// 받는 사람 이름	
					map.put("RECEIVERADDR", null);								// 받는 사람 주소	도로명주소
					map.put("RECEIVERADDRDETAIL", null);						// 받는 사람 상세주소	도로명", null); 지번 공통
					map.put("RECEIVERZIPCD", null);								// 받는 사람 우편번호	신우편번호
					map.put("RECEIVEROLDADDR", null);							// 받는 사람 주소	지번주소
					map.put("RECEIVEROLDZIPCD", null);							// 받는 사람 (구)우편번호	(-) 포함
					map.put("RECEIVERTELNO", null);								// 받는 사람 전화번호
					map.put("RECEIVERPHONENO", null);							// 받는 사람 휴대폰번호
					map.put("SELECTADDRESSIDX", null);							// 선택한 배송지 목록 일련번호
				}
			}else{							// 비회원
				map.put("MEMBERORDERYN", "N");								// 회원/비회원 구분 Y : 회원주문, N : 비회원주문
				map.put("MEMBERIDX", null);										// 회원 일련번호
				map.put("MEMBERID", null);											// 회원 ID
				map.put("MEMBERGRADEIDX", null);								// 회원등급 일련번호 TM_MEMBER_GRADE 일련번호
				map.put("MEMBERNM", null);											// 회원명(주문자명) 비회원일때는 주문자명
				map.put("SESSIONID", vo.getSessionId());						// 비회원 세션 ID
				map.put("SHIPPINGNM", null);										// 주소록명
				map.put("RECEIVERNM", null);										// 받는 사람 이름	
				map.put("RECEIVERADDR", null);									// 받는 사람 주소	도로명주소
				map.put("RECEIVERADDRDETAIL", null);							// 받는 사람 상세주소	도로명", null); 지번 공통
				map.put("RECEIVERZIPCD", null);									// 받는 사람 우편번호	신우편번호
				map.put("RECEIVEROLDADDR", null);								// 받는 사람 주소	지번주소
				map.put("RECEIVEROLDZIPCD", null);								// 받는 사람 (구)우편번호	(-) 포함
				map.put("RECEIVERTELNO", null);									// 받는 사람 전화번호
				map.put("RECEIVERPHONENO", null);								// 받는 사람 휴대폰번호
				map.put("SELECTADDRESSIDX", null);								// 선택한 배송지 목록 일련번호
			}
			map.put("NONMEMBERPW", null);										// 비밀번호 비회원일때만 사용됨
			map.put("DEVICE", PathUtil.getDeviceNm());						// 디바이스 (P : PC, M : MOBILE, A : APP)
			map.put("ORDERSTATUSCD", "000");									// 주문상태코드 (000 : 주문 전, 100 : 주문 접수(입금 대기), 200 : 결제 완료, 300 : 상품 준비중, 400 : 발송 완료(배송중), 500 : 배송 완료, 600 : 교환 신청, 650 : 교환 처리중, 670 : 교환 불가, 690 : 교환 완료, 700 : 반품 신청, 750 : 반품 처리중, 770 : 반품 불가, 790 : 반품 완료, 800 : 취소 신청, 890 : 주문 취소, 900 : 구매 확정)
			map.put("TOTALORDERPRICE", orderPriceSum);					// 총 주문금액
			map.put("SHIPPINGPRICE", shippingPrice);							// 배송비
			map.put("SENDERPHONENO", vo.getSenderPhoneNo());		// 보내는 사람 휴대폰번호 (비회원 주문시 필수)
			map.put("SENDEREMAIL", vo.getSenderEmail());					// 회원명(주문자명) 비회원일때는 주문자명
			map.put("ADFROM", session.getAttribute("adfrom"));			// 광고 경로 TAG
			map.put("REGIDX", vo.getRegIdx());
			map.put("REGHTTPUSERAGENT", vo.getRegHttpUserAgent());
			map.put("REGIP", vo.getRegIp());
	
			flag = orderDAO.insertOrderMaster(map);
	
			if(flag <= 0){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
				resultMap.setResult(false);
				resultMap.setMsg("주문 정보 저장 중 에러가 발생했습니다. 다시 주문해 주세요!");
				return resultMap;
			}
			
			Integer orderIdx = Integer.parseInt(map.get("ORDER_IDX").toString());	// 주문 마스터 일련번호
			
			for(int i=0;i<vo.getOrderGoodsInfoList().size();i++){
				Integer goodsIdx = vo.getOrderGoodsInfoList().get(i).getGoodsIdx();
				Integer goodsCnt = vo.getOrderGoodsInfoList().get(i).getGoodsCnt();
				Integer couponIdx = vo.getOrderGoodsInfoList().get(i).getCouponIdx();

				String setFlag = vo.getOrderGoodsInfoList().get(i).getSetFlag();	// 세트상품 구분 (Y : 세트 상품, N : 일반상품)
				Double discountPrice = vo.getOrderGoodsInfoList().get(i).getDiscountPrice();	// 할인가
				double couponPrice = 0;	// 상품 쿠폰 할인 금액
				
				// 상품 자동발급 상품 쿠폰 리스트(회원일때만)
				if(vo.getRegIdx() > 0){	// 회원
					List<SqlMap> couponList = cartService.getAutoIssueGoodsCouponList(goodsIdx.toString());	// 상품 자동발급 상품 쿠폰 리스트
					if(couponList != null && couponList.size() > 0){
						// 쿠폰 정보가 넘어왔을때만 쿠폰 유효성 확인
						if(couponIdx != null){
							// 쿠폰 정보가 다르면 에러							
							if(couponIdx.intValue() != Integer.valueOf(couponList.get(0).get("couponIdx").toString()).intValue()){
								TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
								resultMap.setResult(false);
								resultMap.setMsg("쿠폰 정보가 올바르지 않습니다. 다시 주문해 주세요!");
								return resultMap;
							}
						}
						
						couponIdx = Integer.valueOf(couponList.get(0).get("couponIdx").toString());
						String discountType = (String)couponList.get(0).get("discountType");									// 할인종류 (R : 정률할인, A : 정액할인)
						Double discount = Double.valueOf(couponList.get(0).get("discount").toString());				// 할인액(률) (DISCOUNT_RATE(할인종류)가 R(정률)이면 할인률, A(정액)이면 할인액)
						Double maxDiscount = null;
						if(couponList.get(0).get("maxDiscount") != null){
							maxDiscount = Double.valueOf(couponList.get(0).get("maxDiscount").toString());			// 최대할인금액 (DISCOUNT_RATE(할인종류)가 R(정률)일때만 사용됨)
						}
						String orderAmtLimitYn = (String)couponList.get(0).get("orderAmtLimitYn");						// 적용주문금액 제한여부 (Y : 제한적용, N : 제한없음)
						Double minOrderAmt = null;
						if(couponList.get(0).get("minOrderAmt") != null){
							minOrderAmt = Double.valueOf(couponList.get(0).get("minOrderAmt").toString());	// 최소주문금액 (ORDER_AMT_LIMIT(적용주문금액)가 Y(제한적용)일때만 사용됨)
						}
						
						// 쿠폰 적용 조건 체크
						if(orderAmtLimitYn.equals("N")){
							if(discountType.equals("R")){	// 정률 할인
								couponPrice = Math.floor(discountPrice * discount / 100) * goodsCnt;	// 절사
								if(maxDiscount != null && maxDiscount > 0){	// 최대할인금액 제한이 있고 최대할인금액보다 많이 할인되면 최대할인금액까지만 할인
									if(couponPrice > maxDiscount){
										couponPrice = maxDiscount;
									}
								}
							}else{	// 정액 할인
								couponPrice = discount ;// (discountPrice * goodsCnt) - discount;
							}
						}else{
							if(minOrderAmt > orderPriceSum){	// 최소주문금액보다 적으면 쿠폰 적용 취소
								couponIdx = null;
								couponPrice = 0;										
							}else{
								if(discountType.equals("R")){	// 정률 할인
									couponPrice = Math.floor(discountPrice * discount / 100) * goodsCnt;		// 절사
									if(maxDiscount != null && maxDiscount > 0){	// 최대할인금액 제한이 있고 최대할인금액보다 많이 할인되면 최대할인금액까지만 할인
										if(couponPrice > maxDiscount){
											couponPrice = maxDiscount;
										}
									}
								}else{	// 정액 할인
									couponPrice = discount ;// (discountPrice * goodsCnt) - discount;
								}
							}
						}
					}
				}else{							// 비회원
					couponIdx = null;
				}
	
				// 주문 상세 저장
				map.clear();
				map.put("ORDERIDX", orderIdx);					// 주문 마스터 일련번호 (TO_ORDER 일련번호)
				map.put("ORDERCD", orderCd);						// 주문코드
				map.put("ORDERSTATUSCD", "000");				// 주문상태코드 (000 : 주문 전, 100 : 주문 접수(입금 대기), 200 : 결제 완료, 300 : 상품 준비중, 400 : 발송 완료(배송중), 500 : 배송 완료, 600 : 교환 신청, 650 : 교환 처리중, 670 : 교환 불가, 690 : 교환 완료, 700 : 반품 신청, 750 : 반품 처리중, 770 : 반품 불가, 790 : 반품 완료, 800 : 취소 신청, 890 : 주문 취소, 900 : 구매 확정) 
				map.put("GOODSIDX", goodsIdx);					// 상품 일련번호 (TP_GOODS 일련번호)
				map.put("ORDERCNT", goodsCnt);					// 상품 개수
				map.put("GIFTCOUPONIDX", couponIdx);		// 상품 쿠폰 일련번호 (결제 페이지에서 임시 적용된 상품 쿠폰 일련번호)
				map.put("GIFTCOUPONPRICE", couponPrice);	// 상품 쿠폰 할인 금액 (상품 쿠폰 여러 개 적용됐으면 금액 합)	
				map.put("CARTCOUPONPRICE", 0);				// 장바구니 쿠폰 할인 금액 (비율로 나눠서 저장)
				map.put("PROMOTIONCODEPRICE", 0);			// 프로모션 코드 할인 금액 (비율로 나눠서 저장)
				map.put("POINTPRICE", 0);							// 포인트 결제 금액 (비율로 나눠서 저장)
				map.put("PREPOINTPRICE", 0);						// 선포인트 결제 금액 (비율로 나눠서 저장)
				map.put("PAYPRICE", 0);								// 결제금액 (실제 PG사 결제금액, 비율로 나눠서 저장)
				map.put("SAVEPOINT", 0);							// 적립 예정 포인트
				map.put("CATEIDX", null);							// 카테고리 일련번호 (카테고리별 가격이 다른 쇼핑몰에서 사용, TP_CATEGORY 일련번호)
				map.put("PARTNERIDX", null);						// 파트너 데이타 일련번호 (네이버EP용, TO_PARTNER_DATA 일련번호)
				map.put("REGIDX", vo.getRegIdx());
				map.put("REGHTTPUSERAGENT", vo.getRegHttpUserAgent());
				map.put("REGIP", vo.getRegIp());
				
				flag = orderDAO.insertOrderDetail(map);
				
				if(flag <= 0){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
					resultMap.setResult(false);
					resultMap.setMsg("상품 정보 저장 중 에러가 발생했습니다. 다시 주문해 주세요!");
					return resultMap;
				}
	
				Integer orderDetailIdx = Integer.parseInt(map.get("ORDER_DETAIL_IDX").toString());	// 주문 디테일 일련번호
				
				// 세트 상품이면 서브 디테일 테이블에 저장
				if(setFlag.equals("Y")){
					// 주문 서브 상세 저장
					map.clear();
					map.put("ORDERDETAILIDX", orderDetailIdx);		// 주문 디테일 일련번호
					map.put("ORDERIDX", orderIdx);						// 주문 마스터 일련번호 (TO_ORDER 일련번호)
					map.put("ORDERCD", orderCd);							// 주문코드
					map.put("ORDERSTATUSCD", "000");					// 주문상태코드 (000 : 주문 전, 100 : 주문 접수(입금 대기), 200 : 결제 완료, 300 : 상품 준비중, 400 : 발송 완료(배송중), 500 : 배송 완료, 600 : 교환 신청, 650 : 교환 처리중, 670 : 교환 불가, 690 : 교환 완료, 700 : 반품 신청, 750 : 반품 처리중, 770 : 반품 불가, 790 : 반품 완료, 800 : 취소 신청, 890 : 주문 취소, 900 : 구매 확정) 
					map.put("GOODSIDX", goodsIdx);						// 상품 일련번호 (TP_GOODS 일련번호)
					map.put("ORDERCNT", goodsCnt);						// 상품 개수
					map.put("GIFTCOUPONIDX", couponIdx);			// 상품 쿠폰 일련번호 (결제 페이지에서 임시 적용된 상품 쿠폰 일련번호)	// TODO 일단 주문 상세랑 같게 저장했음
					map.put("GIFTCOUPONPRICE", couponPrice);		// 상품 쿠폰 할인 금액 (상품 쿠폰 여러 개 적용됐으면 금액 합)				// TODO 일단 주문 상세랑 같게 저장했음
					map.put("CARTCOUPONPRICE", 0);					// 장바구니 쿠폰 할인 금액 (비율로 나눠서 저장)
					map.put("PROMOTIONCODEPRICE", 0);				// 프로모션 코드 할인 금액 (비율로 나눠서 저장)
					map.put("POINTPRICE", 0);								// 포인트 결제 금액 (비율로 나눠서 저장)
					map.put("PREPOINTPRICE", 0);							// 선포인트 결제 금액 (비율로 나눠서 저장)
					map.put("PAYPRICE", 0);									// 결제금액 (실제 PG사 결제금액, 비율로 나눠서 저장)
					map.put("SAVEPOINT", 0);								// 적립 예정 포인트
					map.put("CATEIDX", null);								// 카테고리 일련번호 (카테고리별 가격이 다른 쇼핑몰에서 사용, TP_CATEGORY 일련번호)
					map.put("PARTNERIDX", null);							// 파트너 데이타 일련번호 (네이버EP용, TO_PARTNER_DATA 일련번호)
					map.put("REGIDX", vo.getRegIdx());
					map.put("REGHTTPUSERAGENT", vo.getRegHttpUserAgent());
					map.put("REGIP", vo.getRegIp());
					
					flag = orderDAO.insertOrderSubDetail(map);
					
					if(flag <= 0){
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
						resultMap.setResult(false);
						resultMap.setMsg("세트 상품 정보 저장 중 에러가 발생했습니다. 다시 주문해 주세요!");
						return resultMap;
					}
				}
			}
			
			SqlMap info = this.procSpPriceDiv(orderIdx.toString());	// 주문 디테일 금액 나누어 저장

			if(info == null){
				resultMap.setResult(false);
				resultMap.setMsg("주문 유효성 확인 중 에러가 발생했습니다!");
				return resultMap;
			}else{
				if(!info.get("result").toString().equals("TRUE")){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
					resultMap.setResult(false);
					resultMap.setMsg(info.get("result").toString());
					return resultMap;
				}
			}			
/*
			// 무료 사은품 내역 저장(회원일때만)
			if(vo.getRegIdx() > 0){
				List<SqlMap> freeGiftList = this.getGiftList("Y", orderCd);				// 무료 사은품 리스트
				
				if(freeGiftList != null){
					for(int i=0;i<freeGiftList.size();i++){
						// 주문 상세 저장
						map.clear();
						map.put("ORDERIDX", orderIdx);																// 주문 마스터 일련번호 (TO_ORDER 일련번호)
						map.put("ORDERCD", orderCd);																	// 주문코드
						map.put("GIFTIDX", String.valueOf(freeGiftList.get(i).get("giftIdx")));			// 사은품 일련번호 (TP_GIFT 일련번호)
						map.put("GIFTNM", (String)freeGiftList.get(i).get("giftNm"));						// 사은품명
						map.put("GIFTIMG", (String)freeGiftList.get(i).get("giftImg"));						// 사은품 이미지
						map.put("GIFTREALIMG", (String)freeGiftList.get(i).get("giftRealImg"));			// 사은품 실제 이미지
						map.put("GIFTFLAG", (String)freeGiftList.get(i).get("giftFlag"));						// 사은품 구분 (P : 금액별 G : 상품별(상품별사용안함))
						map.put("TERMIDX", String.valueOf(freeGiftList.get(i).get("termIdx")));			// 사은품구간일련번호 (TP_GIFT_TERM 일련번호)
						map.put("BRANDIDX", String.valueOf(freeGiftList.get(i).get("brandIdx")));		// 브랜드 (0이면 전체)
						map.put("FREEYN", (String)freeGiftList.get(i).get("freeYn"));							// 무료구분 (Y : 무료사은품, N:구간사은품)
						map.put("TERMNM", (String)freeGiftList.get(i).get("termNm"));						// 구간명
						if(freeGiftList.get(i).get("priceStart") == null){
							map.put("PRICESTART", null);	// 구간시작금액 (구간사은품인경우 1이상)
						}else{
							map.put("PRICESTART", String.valueOf(freeGiftList.get(i).get("priceStart")));	// 구간시작금액 (구간사은품인경우 1이상)
						}
						if(freeGiftList.get(i).get("priceEnd") == null){
							map.put("PRICEEND", null);		// 구간끝금액
						}else{
							map.put("PRICEEND", String.valueOf(freeGiftList.get(i).get("priceEnd")));		// 구간끝금액
						}
						map.put("GIFTSTARTDATE", (String)freeGiftList.get(i).get("giftStartDate"));	// 사은품 증정 시작일자
						map.put("GIFTENDDATE", (String)freeGiftList.get(i).get("giftEndDate"));			// 사은품 증정 종료일자
						map.put("SELYN", (String)freeGiftList.get(i).get("selYn"));							// 선택여부 (Y : 선택사은품, N : 브랜드, 금액 해당되면 모두증정)
						map.put("GIFTCNT", (String)freeGiftList.get(i).get("giftCnt"));  //사은품 개수"
						
						flag = orderDAO.insertOrderGift(map);	// 사은품 내역 저장
						
						if(flag <= 0){
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
							resultMap.setResult(false);
							resultMap.setMsg("사은품 정보 저장 중 에러가 발생했습니다. 다시 주문해 주세요!");
							return resultMap;
						}					
					}
				}
			}
*/
			// 원클릭 결제시 금액별 사은품 랜덤 1개 저장
			if(isOneClick.equals("Y")){
				// 구매금액별 사은품 내역 저장
				SqlMap priceGiftList = this.getRandomPriceGiftInfo(orderCd);	// 구매금액별 사은품 랜덤하게 1개 반환
				if(priceGiftList != null){
					// 주문 상세 저장
					map.clear();
					map.put("ORDERIDX", orderIdx);														// 주문 마스터 일련번호 (TO_ORDER 일련번호)
					map.put("ORDERCD", orderCd);															// 주문코드
					map.put("GIFTIDX", String.valueOf(priceGiftList.get("giftIdx")));			// 사은품 일련번호 (TP_GIFT 일련번호)
					map.put("GIFTNM", (String)priceGiftList.get("giftNm"));						// 사은품명
					map.put("GIFTIMG", (String)priceGiftList.get("giftImg"));						// 사은품 이미지
					map.put("GIFTREALIMG", (String)priceGiftList.get("giftRealImg"));			// 사은품 실제 이미지
					map.put("GIFTFLAG", (String)priceGiftList.get("giftFlag"));					// 사은품 구분 (P : 금액별 G : 상품별(상품별사용안함))
					map.put("TERMIDX", String.valueOf(priceGiftList.get("termIdx")));			// 사은품구간일련번호 (TP_GIFT_TERM 일련번호)
					map.put("FREEYN", (String)priceGiftList.get("freeYn"));						// 무료구분 (Y : 무료사은품, N:구간사은품)
					map.put("BRANDIDX", String.valueOf(priceGiftList.get("brandIdx")));		// 브랜드 (0이면 전체)
					map.put("TERMNM", (String)priceGiftList.get("termNm"));						// 구간명
					if(priceGiftList.get("priceStart") == null){
						map.put("PRICESTART", null);	// 구간시작금액 (구간사은품인경우 1이상)
					}else{
						map.put("PRICESTART", String.valueOf(priceGiftList.get("priceStart")));	// 구간시작금액 (구간사은품인경우 1이상)
					}
					if(priceGiftList.get("priceEnd") == null){
						map.put("PRICEEND", null);		// 구간끝금액
					}else{
						map.put("PRICEEND", String.valueOf(priceGiftList.get("priceEnd")));		// 구간끝금액
					}
					map.put("GIFTSTARTDATE", (String)priceGiftList.get("giftStartDate"));	// 사은품 증정 시작일자
					map.put("GIFTENDDATE", (String)priceGiftList.get("giftEndDate"));		// 사은품 증정 종료일자
					map.put("SELYN", (String)priceGiftList.get("selYn"));							// 선택여부 (Y : 선택사은품, N : 브랜드, 금액 해당되면 모두증정)
					map.put("GIFTCNT", (priceGiftList.get("giftCnt") == null ? 1 : Integer.parseInt(priceGiftList.get("giftCnt").toString())));  //사은품 개수"
					flag = orderDAO.insertOrderGift(map);	// 사은품 내역 저장
					
					if(flag <= 0){
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
						resultMap.setResult(false);
						resultMap.setMsg("사은품 정보 저장 중 에러가 발생했습니다. 다시 주문해 주세요!");
						return resultMap;
					}					
				}
			}
			
			resultMap.setResult(true);
		}catch(Exception e){
			e.printStackTrace();
			resultMap.setResult(false);
			resultMap.setMsg("주문 정보 작성 중 에러가 발생했습니다. 다시 주문해 주세요!");
			throw e;
		}
		
		return resultMap;
	}

	   /**
	    * @Method : getGoodsInfo
	    * @Date: 2017. 7. 5.
	    * @Author :  서 정 길
	    * @Description	:	상품 정보 (세트 상품인지 확인)
	   */
	@Override
	public SqlMap getGoodsInfo(Integer goodsIdx) throws Exception {
		SqlMap info = new SqlMap();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			map.put("GOODSIDX", goodsIdx);	// 상품 일련번호
		
			info = orderDAO.getGoodsInfo(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}

		return info;
	}

	   /**
	    * @Method : getOrderGoodsList
	    * @Date: 2017. 7. 6.
	    * @Author :  서 정 길
	    * @Description	:	주문 상품 리스트
	   */
	@Override
	public List<SqlMap> getOrderGoodsList(OrderVO vo) throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();

		try{
			map.put("ORDERCD", vo.getOrderCd());		// 주문 코드
//			map.put("DEVICE", PathUtil.getDeviceNm());		// 디바이스 (P : PC, M : MOBILE, A : APP)

			if(vo.getRegIdx() > 0){	// 회원
				map.put("MEMBERIDX", vo.getMemberIdx());	// 회원 일련번호
//				map.put("SESSIONID", null);							// 비회원 세션 ID
//				map.put("MEMBERGRADE", UserInfo.getUserInfo().getLevelIdx());		// 회원 등급 코드
			}else{							// 비회원
				map.put("MEMBERIDX", null);						// 회원 일련번호
//				map.put("SESSIONID", vo.getSessionId());		// 비회원 세션 ID
			}

			list = orderDAO.getOrderGoodsList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	   /**
	    * @Method : getOrderInfo
	    * @Date: 2017. 7. 18.
	    * @Author :  서 정 길
	    * @Description	:	주문 마스터 정보(결제전건만)
	   */
	@Override
	public SqlMap getOrderInfo(OrderVO vo) throws Exception {
		SqlMap info = new SqlMap();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			map.put("ORDERCD", vo.getOrderCd());		// 주문 코드
			map.put("ORDERSTATUSCD", "000");			// 주문 전 건만(000)
		
			if(vo.getRegIdx() > 0){	// 회원
				map.put("MEMBERIDX", vo.getMemberIdx());	// 회원 일련번호
				map.put("SESSIONID", null);							// 비회원 세션 ID
			}else{							// 비회원
				map.put("MEMBERIDX", null);						// 회원 일련번호
				map.put("SESSIONID", vo.getSessionId());		// 비회원 세션 ID
			}

			info = orderDAO.getOrderInfo(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}

		return info;
	}
	
	   /**
	    * @Method : getAddressList
	    * @Date: 2017. 7. 13.
	    * @Author :  서 정 길
	    * @Description	:	배송지 리스트
	   */
	@Override
	public List<SqlMap> getAddressList(OrderVO vo) throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();

		try{
			map.put("MEMBERIDX", vo.getMemberIdx());	// 회원 일련번호

			list = orderDAO.getAddressList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	   /**
	    * @Method : getMemberDetail
	    * @Date: 2017. 7. 13.
	    * @Author :  서 정 길
	    * @Description	:	회원 상세
	   */
	@Override
	public SqlMap getMemberDetail(Integer memberIdx) throws Exception {
		SqlMap info = new SqlMap();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			map.put("MEMBERIDX", memberIdx);	// 회원 일련번호
		
			info = orderDAO.getMemberDetail(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}

		return info;
	}
	
	   /**
	    * @Method : getOrderCompleteInfo
	    * @Date: 2017. 7. 13.
	    * @Author :  서 정 길
	    * @Description	:	주문 정보 (주문 완료 페이지)
	   */
	@Override
	public SqlMap getOrderCompleteInfo(OrderVO vo) throws Exception {
		SqlMap info = new SqlMap();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			map.put("ORDERCD", vo.getOrderCd());	// 주문 코드

			if(vo.getRegIdx() > 0){	// 회원
				map.put("MEMBERIDX", vo.getMemberIdx());	// 회원 일련번호
				map.put("SESSIONID", null);							// 비회원 세션 ID
			}else{							// 비회원
				map.put("MEMBERIDX", null);						// 회원 일련번호
				map.put("SESSIONID", vo.getSessionId());		// 비회원 세션 ID
			}

			info = orderDAO.getOrderCompleteInfo(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}

		return info;
	}
	
	   /**
	    * @Method : getOrderIdx
	    * @Date: 2017. 7. 17.
	    * @Author :  서 정 길
	    * @Description	:	주문 번호 반환
	   */
	@Override	
	public String getOrderIdx(OrderVO vo) throws Exception{
		String orderIdx = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			map.put("ORDERCD", vo.getOrderCd());	// 주문 코드

			if(vo.getRegIdx() > 0){	// 회원
				map.put("MEMBERIDX", vo.getMemberIdx());	// 회원 일련번호
				map.put("SESSIONID", null);							// 비회원 세션 ID
			}else{							// 비회원
				map.put("MEMBERIDX", null);						// 회원 일련번호
				map.put("SESSIONID", vo.getSessionId());		// 비회원 세션 ID
			}

			orderIdx = orderDAO.getOrderIdx(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}

		return orderIdx;
	}

	   /**
	    * @Method : getOrderIdxByOrderCdOnly
	    * @Date: 2017. 7. 20.
	    * @Author :  서 정 길
	    * @Description	:	주문 번호 반환 (주문 코드로만 비교)
	   */
	@Override	
	public String getOrderIdxByOrderCdOnly(String orderCd) throws Exception{
		String orderIdx = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			map.put("ORDERCD", orderCd);	// 주문 코드

			orderIdx = orderDAO.getOrderIdx(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}

		return orderIdx;
	}

	/**
	 * @Method : getOrderCd
	 * @Date		: 2018. 3. 6.
	 * @Author	:  서  정  길 
	 * @Description	:	주문 코드 반환
	 */
	@Override	
	public String getOrderCd(String orderIdx) throws Exception{
		String orderCd = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			map.put("ORDERIDX", orderIdx);	// 주문 번호

			orderCd = orderDAO.getOrderCd(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}

		return orderCd;
	}

	   /**
	    * @Method : procSpPgCheckBefore
	    * @Date: 2017. 7. 17.
	    * @Author :  서 정 길
	    * @Description	:	결제 전 주문 유효성 체크
	   */
	@Override
	public SqlMap procSpPgCheckBefore(String orderIdx) throws Exception {
		SqlMap info = new SqlMap();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			map.put("ORDERIDX", orderIdx);			// 주문 번호

			info = orderDAO.procSpPgCheckBefore(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}

		return info;
	}

	   /**
	    * @Method : procSpPgCheckAfter
	    * @Date: 2017. 7. 18.
	    * @Author :  서 정 길
	    * @Description	:	결제 후 주문 유효성 체크 & 주문 상태 변경
	   */
	@Override
	public SqlMap procSpPgCheckAfter(String orderIdx) throws Exception {
		SqlMap info = new SqlMap();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			map.put("ORDERIDX", orderIdx);			// 주문 번호

			info = orderDAO.procSpPgCheckAfter(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}

		return info;
	}


	   /**
	    * @Method : procSpPriceDiv
	    * @Date: 2017. 7. 17.
	    * @Author :  서 정 길
	    * @Description	:	주문 디테일 금액 나누어 저장
	   */
	@Override
	public SqlMap procSpPriceDiv(String orderIdx) throws Exception {
		SqlMap info = new SqlMap();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			map.put("ORDERIDX", orderIdx);			// 주문 번호

			info = orderDAO.procSpPriceDiv(map);	// 주문 디테일 금액 나누어 저장
			
			  int  flag = saveFreeGift(orderIdx);
/*
			if(flag < 0){
				info.clear();
				info.put("result", "사은품 정보 저장 중 에러가 발생했습니다. 다시 주문해 주세요!");
			}
*/
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}

		return info;
	}

	   /**
	    * @Method : updateOrderMasterInfo
	    * @Date: 2017. 7. 17.
	    * @Author :  서 정 길
	    * @Description	:	주문 정보 DB 저장
	   */
	@Override
	public int updateOrderMasterInfo(OrderVO vo) throws Exception {
		int flag = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.clear();
		map.put("ORDERCD", vo.getOrderCd());				// 주문코드 YYYYMMDDHHNNSSFFF + [P|M|A] + 랜덤값2자리 (P : PC, M : MOBILE, A : APP)
		if(vo.getRegIdx() > 0){	// 회원
			map.put("MEMBERIDX", vo.getMemberIdx());	// 회원 일련번호
			map.put("SESSIONID", null);							// 비회원 세션 ID
		}else{							// 비회원
			map.put("MEMBERIDX", null);						// 회원 일련번호
			map.put("SESSIONID", vo.getSessionId());		// 비회원 세션 ID
		}
		
		map.put("DEVICE", PathUtil.getDeviceNm());		// 디바이스 (P : PC, M : MOBILE, A : APP)
		
		map.put("SENDERNM", vo.getSenderNm());									// 주문자명
		map.put("SENDEREMAIL", vo.getSenderEmail());							// 주문자 이메일
		map.put("SENDERPHONENO", vo.getSenderPhoneNo());				// 주문자 휴대폰번호

		map.put("SHIPPINGNM", vo.getShippingNm());								// 주소록명 (새로입력시 입력한 배송지명)
		map.put("RECEIVERNM", vo.getReceiverNm());								// 수령자명
		map.put("RECEIVERPHONENO", vo.getReceiverPhoneNo());			// 수령자 휴대폰번호
		map.put("RECEIVERTELNO", vo.getReceiverTelNo());						// 수령자 일반전화번호
		map.put("RECEIVERZIPCD", vo.getReceiverZipCd());						// 배송지 우편번호
		map.put("RECEIVERADDR", vo.getReceiverAddr());						// 배송지 도로명 주소
		map.put("RECEIVERADDRDETAIL", vo.getReceiverAddrDetail());		// 배송지 상세 주소
		map.put("RECEIVEROLDZIPCD", vo.getReceiverOldZipCd());			// 배송지 (구)우편번호
		map.put("RECEIVEROLDADDR", vo.getReceiverOldAddr());				// 배송지 지번주소

		map.put("ORDERMEMO", vo.getOrderMemo());								// 배송시 요청사항 직접 입력 내용

		map.put("PAYTYPE", vo.getPayType());										// 결제수단 (공통코드 PAY_TYPE10 : 신용카드, PAY_TYPE15 : 빌키, PAY_TYPE20 : 실시간계좌, PAY_TYPE25 : 가상계좌, PAY_TYPE30 : 휴대폰, PAY_TYPE35 : PAYCO, PAY_TYPE40 : 카카오페이, PAY_TYPE45 : Npay, PAY_TYPE50 : PAYNOW,  PAY_TYPE55: SMILEPAY  PAY_TYPE90 : 포인트)

		if(vo.getPayType() != null && vo.getPayType().equals("PAY_TYPE15")){		// 빌키
			map.put("BILLKEYIDX", vo.getBillkeyIdx());								// 빌키 일련번호 (원클릭 결제시 결제된 빌키 일련번호)
		}else{
			map.put("BILLKEYIDX", null);												// 빌키 일련번호 (원클릭 결제시 결제된 빌키 일련번호)
		}

		if(vo.getPayType() != null && (vo.getPayType().equals("PAY_TYPE20") || vo.getPayType().equals("PAY_TYPE25"))){		// 실시간계좌나 가상계좌
			if(vo.getPayType().equals("PAY_TYPE20")){					// 실시간계좌
				map.put("ESCROWYN", vo.getEscrowYn1Val());						// 에스크로 여부 (Y : 에스크로 사용, N : 에스크로 사용 안 함)
			}else{
				map.put("BANKCODE", null);													// 입금 은행코드 (무통장(가상계좌)일때 사용)
				map.put("CASHRECEIPTGUBUN", null);									// 현금영수증 여부 (0 : 미신청, 1 : 개인, 2 : 사업자)
				map.put("CASHRECEIPTNO", null);											// 현금영수증 번호 (CASH_RECEIPT_GUBUN가 '0'이 아닐때만 사용, 개인은 휴대폰번호, 사업자는 사업자등록번호)
			}
			
			if(vo.getPayType().equals("PAY_TYPE25")){					// 가상계좌
				map.put("BANKCODE", vo.getBankCode());								// 입금 은행코드 (무통장(가상계좌)일때 사용)
				map.put("ESCROWYN", vo.getEscrowYn2Val());						// 에스크로 여부 (Y : 에스크로 사용, N : 에스크로 사용 안 함)
				map.put("CASHRECEIPTGUBUN", vo.getCashReceiptGubun());	// 현금영수증 여부 (0 : 미신청, 1 : 개인, 2 : 사업자)
				map.put("CASHRECEIPTNO", vo.getCashReceiptNo());				// 현금영수증 번호 (CASH_RECEIPT_GUBUN가 '0'이 아닐때만 사용, 개인은 휴대폰번호, 사업자는 사업자등록번호)
			}
		}else{
			map.put("BANKCODE", null);														// 입금 은행코드 (무통장(가상계좌)일때 사용)
			map.put("ESCROWYN", null);													// 에스크로 여부 (Y : 에스크로 사용, N : 에스크로 사용 안 함)
			map.put("CASHRECEIPTGUBUN", null);										// 현금영수증 여부 (0 : 미신청, 1 : 개인, 2 : 사업자)
			map.put("CASHRECEIPTNO", null);												// 현금영수증 번호 (CASH_RECEIPT_GUBUN가 '0'이 아닐때만 사용, 개인은 휴대폰번호, 사업자는 사업자등록번호)
		}
		
		// 상태 유지용 필드 시작
		map.put("SAMEASORDERINFO", vo.getSameAsOrderInfo());					// 회원정보와 동일인지 새로입력인지 (상태유지용 필드, Y : 회원정보와 동일, N : 새로입력)
		map.put("ADDRESSTABID", vo.getAddressTabId());								// 선택한 배송지 탭 ID (상태유지용 필드, addressID1 : 배송지 목록 탭, addressID2 : 새로 입력 탭)
		map.put("SELECTADDRESSIDX", vo.getSelectAddressIdx());					// 선택한 배송지 목록 일련번호 (상태유지용 필드)
		map.put("SAMEORDERINFO", vo.getSameOrderInfo());							// 주문자 정보와 상품을 받는 분이 같습니다 (상태유지용 필드)
		map.put("ADDTOADDRESS", vo.getAddToAddress());								// 배송지 목록에 추가 (상태유지용 필드)
		map.put("SETASDEFAULTADDRESS", vo.getSetAsDefaultAddress());			// 기본 배송지로 설정 (상태유지용 필드)
		map.put("ORDERMEMOVAL", vo.getOrderMemoVal());							// 선택한 배송시 요청사항 SELECTBOX value (상태유지용 필드)
		map.put("GIFTTABID", vo.getGiftTabId());											// 선택한 사은품 탭 ID (상태유지용 필드, allGift : 무료 사은품 탭, over4Gift…)
		map.put("SELECTPRICEGIFTIDX", vo.getSelectPriceGiftIdx());					// 선택한 구매금액별 사은품 일련번호 (상태유지용 필드)
		map.put("SELECTPAYTYPE", vo.getSelectPayType());								// 선택한 결제 수단 (상태유지용 필드, BILLKEY : 원클릭 결제, SC0010 : 신용카드)
		map.put("SELECTBILLKEYVAL", vo.getSelectBillkeyVal());						// 선택한 빌키 SELECTBOX value (상태유지용 필드)
		map.put("SELECTCARDCODE", vo.getSelectCardCode());						// 선택한 카드 코드 (상태유지용 필드)
		map.put("ETCCARDVAL", vo.getEtcCardVal());										// 선택한 기타 카드 SELECTBOX value (상태유지용 필드)
		map.put("ESCROWYN1VAL", vo.getEscrowYn1Val());								// 실시간 계좌 이체시 선택한 에스크로 value (상태유지용 필드)
		map.put("ESCROWYN2VAL", vo.getEscrowYn2Val());								// 가상계좌 입금시 선택한 에스크로 value (상태유지용 필드)
		map.put("USETHISPAYTYPETONEXT", vo.getUseThisPayTypeToNext());		// 지금 선택하신 결제수단을 다음에도 사용 (상태유지용 필드)
		map.put("AGREEMENT", vo.getAgreement());										// 위 상품의 판매조건을 명확히 확인하였으며, 구매 진행에 동의합니다. (상태유지용 필드)
		// 상태 유지용 필드 끝
		
		map.put("EDITIDX", vo.getRegIdx());
		map.put("EDITHTTPUSERAGENT", vo.getRegHttpUserAgent());
		map.put("EDITIP", vo.getRegIp());

		flag = orderDAO.updateTempOrderMaster(map);
		
		return flag;
	}
	

	   /**
	    * @Method : insertBillKey
	    * @Date: 2017. 7. 17.
	    * @Author :  강 병 철
	    * @Description	:	빌키 신규 등록
	   */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public int insertBillKey(XPayBillKeyVO vo) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("MEMBERIDX", vo.getMEMBER_IDX());	// 회원 일련번호

		if (vo.getMAIN_YN().equals("Y")) {
			orderDAO.updateMainBillkey(map);
		}
		else {
			SqlMap info = new SqlMap();
			info = orderDAO.selectMainBillkey(map);
			if (info == null || info.size() == 0) {
				vo.setMAIN_YN("Y");
			}
		}
		flag = orderDAO.insertBillKey(vo);
		return flag;
	}
	
	/**
	    * @Method : deleteBillKey
	    * @Date: 2017. 7. 17.
	    * @Author :  강 병 철
	    * @Description	:	빌키 삭제
	   */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public int deleteBillKey(int billkeyIdx, int memberIdx) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("MEMBERIDX", memberIdx);	// 회원 일련번호
		map.put("BILLKEYIDX", billkeyIdx);	// 회원 일련번호

		flag = orderDAO.deleteBillKey(map);
		
		SqlMap info = new SqlMap();
		info = orderDAO.selectMainBillkey(map);
		if (info == null || info.size() == 0) {
			orderDAO.updateTopMainBillkey(map);
		}
		
		return flag;
	}
	
	/**
	    * @Method : modifyBillKey
	    * @Date: 2017. 7. 17.
	    * @Author :  강 병 철
	    * @Description	:	빌키 삭제
	   */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public int modifyBillKey(int billkeyIdx, int memberIdx, String cardNm, String mainYn) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("MEMBERIDX", memberIdx);	
		map.put("BILLKEYIDX", billkeyIdx);	
		map.put("CARDNM", cardNm);	
		map.put("MAINYN", mainYn);	

		if (mainYn.equals("Y")) {
			orderDAO.updateMainBillkey(map);  //대표카드 N으로 update
		}
		
		flag = orderDAO.modifyBillKey(map);
		
		SqlMap info = new SqlMap();
		info = orderDAO.selectMainBillkey(map);
		if (info == null || info.size() == 0) {
			orderDAO.updateTopMainBillkey(map);
		}
		
		return flag;
	}
	
	   /**
	    * @Method : selectMainBillkey
	    * @Date: 2017. 7. 17.
	    * @Author :  강 병 철
	    * @Description	:	대표 빌키 목록 
	   */
	@Override
	public SqlMap selectMainBillkey(Integer memberIdx) throws Exception {
		SqlMap info = new SqlMap();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			map.put("MEMBERIDX", memberIdx);	// 회원 일련번호
		
			info = orderDAO.selectMainBillkey(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return info;
	}

	   /**
	    * @Method : getBillkeyList
	    * @Date: 2017. 8. 3.
	    * @Author :  서 정 길
	    * @Description	:	빌키 리스트
	   */
	@Override
	public List<SqlMap> getBillkeyList(Integer memberIdx) throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();

		try{
			map.put("MEMBERIDX", memberIdx);	// 회원 일련번호

			list = orderDAO.getBillkeyList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	   /**
	    * @Method : updateCasNoteUrlOrderMaster
	    * @Date: 2017. 7. 19.
	    * @Author :  서 정 길
	    * @Description	:	가상계좌 입금 확인시 - 마스터, 디테일 주문 상태 결제 완료로 수정
	   */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public int updateCasNoteUrlOrderMaster(String orderCd, String payDt) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();

		// 마스터, 디테일 주문 상태 결제 완료로 수정
		map.put("ORDERCD", orderCd);		// 주문 코드
		map.put("PAYDT", payDt);

		flag = orderDAO.updateCasNoteUrlOrderMaster(map);	// 마스터, 디테일 주문 상태 결제 완료로 수정
		
		if(flag < 1){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			System.out.println("마스터, 디테일 주문 상태 결제 완료로 수정된 건이 없습니다! : " + orderCd);
			return flag;
		}

		// 서브 디테일 주문 상태 결제 완료로 수정
		map.clear();
		map.put("ORDERCD", orderCd);		// 주문 코드

		orderDAO.updateCasNoteUrlOrderSubDetail(map);	// 서브 디테일 주문 상태 결제 완료로 수정 (서브 디테일은 없을수도 있으니 결과 안 받음)

		return flag;
	}

	   /**
	    * @Method : insertXPayResult
	    * @Date: 2017. 7. 21.
	    * @Author :  서 정 길
	    * @Description	:	LG U+ 결제 결과 로그 저장 (성공건만 저장됨)
	   */
	@Override
	public int insertXPayResult(XPayResultVO vo) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("LGD_OID", vo.getLGD_OID());
		map.put("LGD_RESPCODE", vo.getLGD_RESPCODE());
		map.put("LGD_RESPMSG", vo.getLGD_RESPMSG());
		map.put("LGD_MID", vo.getLGD_MID());
		map.put("LGD_AMOUNT", vo.getLGD_AMOUNT());
		map.put("LGD_TID", vo.getLGD_TID());
		map.put("LGD_PAYTYPE", vo.getLGD_PAYTYPE());
		map.put("LGD_PAYDATE", vo.getLGD_PAYDATE());
		map.put("LGD_HASHDATA", vo.getLGD_HASHDATA());
		map.put("LGD_TIMESTAMP", vo.getLGD_TIMESTAMP());
		map.put("LGD_BUYER", vo.getLGD_BUYER());
		map.put("LGD_PRODUCTINFO", vo.getLGD_PRODUCTINFO());
		map.put("LGD_BUYERID", vo.getLGD_BUYERID());
		map.put("LGD_BUYERADDRESS", vo.getLGD_BUYERADDRESS());
		map.put("LGD_BUYERPHONE", vo.getLGD_BUYERPHONE());
		map.put("LGD_BUYEREMAIL", vo.getLGD_BUYEREMAIL());
		map.put("LGD_PRODUCTCODE", vo.getLGD_PRODUCTCODE());
		map.put("LGD_RECEIVER", vo.getLGD_RECEIVER());
		map.put("LGD_RECEIVERPHONE", vo.getLGD_RECEIVERPHONE());
		map.put("LGD_DELIVERYINFO", vo.getLGD_DELIVERYINFO());
		map.put("LGD_FINANCECODE", vo.getLGD_FINANCECODE());
		map.put("LGD_FINANCENAME", vo.getLGD_FINANCENAME());
		map.put("LGD_FINANCEAUTHNUM", vo.getLGD_FINANCEAUTHNUM());
		map.put("LGD_ESCROWYN", vo.getLGD_ESCROWYN());
		map.put("LGD_CASHRECEIPTNUM", vo.getLGD_CASHRECEIPTNUM());
		map.put("LGD_CASHRECEIPTSELFYN", vo.getLGD_CASHRECEIPTSELFYN());
		map.put("LGD_CASHRECEIPTKIND", vo.getLGD_CASHRECEIPTKIND());
		map.put("LGD_CARDNUM", vo.getLGD_CARDNUM());
		map.put("LGD_CARDINSTALLMONTH", vo.getLGD_CARDINSTALLMONTH());
		map.put("LGD_CARDNOINTYN", vo.getLGD_CARDNOINTYN());
		map.put("LGD_AFFILIATECODE", vo.getLGD_AFFILIATECODE());
		map.put("LGD_CARDGUBUN1", vo.getLGD_CARDGUBUN1());
		map.put("LGD_CARDGUBUN2", vo.getLGD_CARDGUBUN2());
		map.put("LGD_CARDACQUIRER", vo.getLGD_CARDACQUIRER());
		map.put("LGD_PCANCELFLAG", vo.getLGD_PCANCELFLAG());
		map.put("LGD_PCANCELSTR", vo.getLGD_PCANCELSTR());
		map.put("LGD_DISCOUNTUSEYN", vo.getLGD_DISCOUNTUSEYN());
		map.put("LGD_DISCOUNTUSEAMOUNT", vo.getLGD_DISCOUNTUSEAMOUNT());
		map.put("LGD_ACCOUNTNUM", vo.getLGD_ACCOUNTNUM());
		map.put("LGD_ACCOUNTOWNER", vo.getLGD_ACCOUNTOWNER());
		map.put("LGD_PAYER", vo.getLGD_PAYER());
		map.put("LGD_CASTAMOUNT", vo.getLGD_CASTAMOUNT());
		map.put("LGD_CASCAMOUNT", vo.getLGD_CASCAMOUNT());
		map.put("LGD_CASFLAG", vo.getLGD_CASFLAG());
		map.put("LGD_CASSEQNO", vo.getLGD_CASSEQNO());
		map.put("LGD_SAOWNER", vo.getLGD_SAOWNER());
		map.put("LGD_TELNO", vo.getLGD_TELNO());
		map.put("LGD_PAYKEY", vo.getLGD_PAYKEY());
		
		
		map.put("PG_TYPE", vo.getPG_TYPE());
		map.put("NOINF_TYPE", vo.getNOINF_TYPE());
		map.put("CARD_MNY", vo.getCARD_MNY());
		map.put("PAYCO_POINT_MNY", vo.getPAYCO_POINT_MNY());
		map.put("PNT_AMOUNT", vo.getPNT_AMOUNT());
		map.put("PNT_ISSUE", vo.getPNT_ISSUE());
		map.put("PNT_APP_NO", vo.getPNT_APP_NO());
		map.put("PNT_APP_TIME", vo.getPNT_APP_TIME());
		map.put("PNT_RECEIPT_GUBN", vo.getPNT_RECEIPT_GUBN());
		map.put("ADD_PNT", vo.getADD_PNT());
		map.put("USE_PNT", vo.getUSE_PNT());
		map.put("RSV_PNT", vo.getRSV_PNT());
		map.put("VAN_CD", vo.getVAN_CD());
		map.put("VAN_ID", vo.getVAN_ID());
		map.put("COMMID", vo.getCOMMID());
		map.put("MOBILE_NO", vo.getMOBILE_NO());
		
//			map.put("LGD_TRANSAMOUNT", vo.getLGD_TRANSAMOUNT());
//			map.put("LGD_EXCHANGERATE", vo.getLGD_EXCHANGERATE());
//			map.put("LGD_BUYERSSN", vo.getLGD_BUYERSSN());
//			map.put("LGD_CARDNOINTEREST_YN", vo.getLGD_CARDNOINTEREST_YN());
//			map.put("LGD_VANCODE", vo.getLGD_VANCODE());
		
		flag = orderDAO.insertXPayResult(map);
		
		return flag;
	}

	   /**
	    * @Method : insertXPayResultLog
	    * @Date: 2017. 7. 20.
	    * @Author :  서 정 길
	    * @Description	:	LG U+ 결제 결과 로그 저장 (성공/실패 모두 저장됨)
	   */
	@Override
	public int insertXPayResultLog(XPayResultVO vo) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();

		try{
			map.put("LGD_OID", vo.getLGD_OID());
			map.put("LGD_RESPCODE", vo.getLGD_RESPCODE());
			map.put("LGD_RESPMSG", vo.getLGD_RESPMSG());
			map.put("LGD_MID", vo.getLGD_MID());
			map.put("LGD_AMOUNT", vo.getLGD_AMOUNT());
			map.put("LGD_TID", vo.getLGD_TID());
			map.put("LGD_PAYTYPE", vo.getLGD_PAYTYPE());
			map.put("LGD_PAYDATE", vo.getLGD_PAYDATE());
			map.put("LGD_HASHDATA", vo.getLGD_HASHDATA());
			map.put("LGD_TIMESTAMP", vo.getLGD_TIMESTAMP());
			map.put("LGD_BUYER", vo.getLGD_BUYER());
			map.put("LGD_PRODUCTINFO", vo.getLGD_PRODUCTINFO());
			map.put("LGD_BUYERID", vo.getLGD_BUYERID());
			map.put("LGD_BUYERADDRESS", vo.getLGD_BUYERADDRESS());
			map.put("LGD_BUYERPHONE", vo.getLGD_BUYERPHONE());
			map.put("LGD_BUYEREMAIL", vo.getLGD_BUYEREMAIL());
			map.put("LGD_PRODUCTCODE", vo.getLGD_PRODUCTCODE());
			map.put("LGD_RECEIVER", vo.getLGD_RECEIVER());
			map.put("LGD_RECEIVERPHONE", vo.getLGD_RECEIVERPHONE());
			map.put("LGD_DELIVERYINFO", vo.getLGD_DELIVERYINFO());
			map.put("LGD_FINANCECODE", vo.getLGD_FINANCECODE());
			map.put("LGD_FINANCENAME", vo.getLGD_FINANCENAME());
			map.put("LGD_FINANCEAUTHNUM", vo.getLGD_FINANCEAUTHNUM());
			map.put("LGD_ESCROWYN", vo.getLGD_ESCROWYN());
			map.put("LGD_CASHRECEIPTNUM", vo.getLGD_CASHRECEIPTNUM());
			map.put("LGD_CASHRECEIPTSELFYN", vo.getLGD_CASHRECEIPTSELFYN());
			map.put("LGD_CASHRECEIPTKIND", vo.getLGD_CASHRECEIPTKIND());
			map.put("LGD_CARDNUM", vo.getLGD_CARDNUM());
			map.put("LGD_CARDINSTALLMONTH", vo.getLGD_CARDINSTALLMONTH());
			map.put("LGD_CARDNOINTYN", vo.getLGD_CARDNOINTYN());
			map.put("LGD_AFFILIATECODE", vo.getLGD_AFFILIATECODE());
			map.put("LGD_CARDGUBUN1", vo.getLGD_CARDGUBUN1());
			map.put("LGD_CARDGUBUN2", vo.getLGD_CARDGUBUN2());
			map.put("LGD_CARDACQUIRER", vo.getLGD_CARDACQUIRER());
			map.put("LGD_PCANCELFLAG", vo.getLGD_PCANCELFLAG());
			map.put("LGD_PCANCELSTR", vo.getLGD_PCANCELSTR());
			map.put("LGD_DISCOUNTUSEYN", vo.getLGD_DISCOUNTUSEYN());
			map.put("LGD_DISCOUNTUSEAMOUNT", vo.getLGD_DISCOUNTUSEAMOUNT());
			map.put("LGD_ACCOUNTNUM", vo.getLGD_ACCOUNTNUM());
			map.put("LGD_ACCOUNTOWNER", vo.getLGD_ACCOUNTOWNER());
			map.put("LGD_PAYER", vo.getLGD_PAYER());
			map.put("LGD_CASTAMOUNT", vo.getLGD_CASTAMOUNT());
			map.put("LGD_CASCAMOUNT", vo.getLGD_CASCAMOUNT());
			map.put("LGD_CASFLAG", vo.getLGD_CASFLAG());
			map.put("LGD_CASSEQNO", vo.getLGD_CASSEQNO());
			map.put("LGD_SAOWNER", vo.getLGD_SAOWNER());
			map.put("LGD_TELNO", vo.getLGD_TELNO());
			map.put("LGD_PAYKEY", vo.getLGD_PAYKEY());
//			map.put("LGD_TRANSAMOUNT", vo.getLGD_TRANSAMOUNT());
//			map.put("LGD_EXCHANGERATE", vo.getLGD_EXCHANGERATE());
//			map.put("LGD_BUYERSSN", vo.getLGD_BUYERSSN());
//			map.put("LGD_CARDNOINTEREST_YN", vo.getLGD_CARDNOINTEREST_YN());
//			map.put("LGD_VANCODE", vo.getLGD_VANCODE());
			
			flag = orderDAO.insertXPayResultLog(map);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return flag;
	}

	   /**
	    * @Method : dbProcessAfterXPay
	    * @Date: 2017. 7. 21.
	    * @Author :  서 정 길
	    * @Description	:	LG U+ 결제 후 DB 처리
	   */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public JsonResultVO dbProcessAfterXPay(XPayResultVO resultVo) throws Exception {

		JsonResultVO resultMap = new JsonResultVO();

		int flag = 0;
		
		String orderCd = resultVo.getLGD_OID();	// 주문 코드
		
		// 가상계좌 결제 - 주문 마스터 가상계좌 정보 수정
		if(resultVo.getLGD_PAYTYPE().equals("SC0040")){	// 가상계좌일때만 실행
			flag = this.updateOrderMasterCyberAccountInfo(orderCd, "XPAY", resultVo.getLGD_FINANCECODE(), resultVo.getLGD_ACCOUNTNUM(), resultVo.getLGD_PAYER(), resultVo.getLGD_CLOSEDATE());		
			
			if(flag < 1){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
				resultMap.setResult(false);
				resultMap.setMsg("가상계좌 정보 수정중 에러가 발생했습니다. 다시 결제하여 주시기 바랍니다.");
				return resultMap;
			}
		}

		// 주문 번호 반환
		String orderIdx = this.getOrderIdxByOrderCdOnly(orderCd);
		if(orderIdx == null || orderIdx.isEmpty()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			resultMap.setMsg("주문 번호가 존재하지 않습니다!");
			return resultMap;
		}

		SqlMap info = this.procSpPgCheckAfter(orderIdx);	// 주문 유효성 체크 & 주문 상태 변경

		if(info == null){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			System.out.println("결제 후 주문 유효성 확인 중 에러가 발생했습니다! : " + orderCd);
			resultMap.setResult(false);
			resultMap.setMsg("결제 후 주문 유효성 확인 중 에러가 발생했습니다! 다시 결제하여 주시기 바랍니다.");
			return resultMap;
		}else{
			if(!info.get("result").toString().equals("TRUE")){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
				System.out.println("결제 후 유효성 확인 결과 : " + orderCd + " : "+ info.get("result").toString());
				resultMap.setResult(false);
				resultMap.setMsg("결제 후 유효성 확인 결과 : " + info.get("result").toString());
				return resultMap;
			}
		}

		flag = this.insertXPayResult(resultVo);	// LG U+ 결제 결과 저장 (성공건만 저장됨)
		
		if(flag < 1){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			resultMap.setMsg("결제 결과 저장중 에러가 발생했습니다. 다시 결제하여 주시기 바랍니다.");
			return resultMap;
		}
		
		resultMap.setResult(true);
		return resultMap;
	}
	
   /**
    * @Method : insertPaycoReserve
    * @Date: 2017. 6. 15.
    * @Author :  강 병 철
    * @Description	:	PAYCO 주문예약 저장
   */
	public void insertPaycoReserve(HashMap<String, Object> map) throws Exception {
		orderDAO.insertPaycoReserve(map);
	}

	/**
    * @Method : insertPaycoApproval
    * @Date: 2017. 6. 15.
    * @Author :  강 병 철
    * @Description	:	PAYCO 주문 결제정보 저장
   */
	public void insertPaycoApprovalPayment(PaycoApprovalVO vo) throws Exception {
		orderDAO.insertPaycoApproval(vo);
		for (PaycoPaymentVO item : vo.getPaymentDetails()) {
			item.setApprovalIdx(vo.getApprovalIdx());
			orderDAO.insertPaycoPayment(item);
		}
	}

	   /**
	    * @Method : dbProcessAfterPayco
	    * @Date: 2017. 7. 21.
	    * @Author :  강 병 철
	    * @Description	:	Payco 결제 후 DB 처리
	   */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public JsonResultVO dbProcessAfterPayco(String orderCd, PaycoApprovalVO appVo) throws Exception {

		JsonResultVO resultMap = new JsonResultVO();

		// 가상계좌 결제 - 주문 마스터 가상계좌 정보 수정
		if(appVo.getPaymentCompletionYn().equals("N")){	// 가상계좌일때만 실행
			String paymentMethodCode = "";	// 결제 수단 (02 : 무통장입금(가상계좌))
			String bankCode = "";
			String accountNo = "";
			String paymentExpirationYmd = "";
			String payer = "";
			
			for (PaycoPaymentVO item : appVo.getPaymentDetails()) {
				if(item.getPaymentMethodCode().equals("02")){
					paymentMethodCode = item.getPaymentMethodCode();
					bankCode = item.getBankCode();
					accountNo = item.getAccountNo();
					paymentExpirationYmd = item.getPaymentExpirationYmd();
					break;
				}
			}
			
			if(paymentMethodCode.equals("02")){	// 가상계좌일때만 실행
				// 주문 정보 구함
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.clear();
				map.put("ORDERCD", orderCd);		// 주문 코드

				SqlMap info = orderDAO.getOrderInfo(map);
				
				int flag = this.updateOrderMasterCyberAccountInfo(orderCd, "PAYCO", bankCode, accountNo, info.get("senderNm").toString(), paymentExpirationYmd);		// 가상계좌 결제 - 주문 마스터 가상계좌 정보 수정		
				
				if(flag < 1){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
					resultMap.setResult(false);
					resultMap.setMsg("가상계좌 정보 수정중 에러가 발생했습니다. 다시 결제하여 주시기 바랍니다.");
					return resultMap;
				}
			}
		}

		//Payco 결제 정보 저장
		this.insertPaycoApprovalPayment(appVo);

		String orderIdx = this.getOrderIdxByOrderCdOnly(orderCd);	// 주문 번호 반환
		if(orderIdx == null || orderIdx.isEmpty()){
			resultMap.setResult(false);
			resultMap.setMsg("주문 번호가 존재하지 않습니다!");
			return resultMap;
		}

		SqlMap info = this.procSpPgCheckAfter(orderIdx);	// 주문 유효성 체크 & 주문 상태 변경

		if(info == null){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			System.out.println("결제 후 주문 유효성 확인 중 에러가 발생했습니다! : " + orderCd);
			resultMap.setResult(false);
			resultMap.setMsg("결제 후 주문 유효성 확인 중 에러가 발생했습니다! 다시 결제하여 주시기 바랍니다.");
			return resultMap;
		}else{
			if(!info.get("result").toString().equals("TRUE")){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
				System.out.println("결제 후 유효성 확인 결과 : " + orderCd + " : "+ info.get("result").toString());
				resultMap.setResult(false);
				resultMap.setMsg("결제 후 유효성 확인 결과 : " + info.get("result").toString());
				return resultMap;
			}
		}
		
		resultMap.setResult(true);
		return resultMap;
	}
	
	   /**
	    * @Method : getDefaultAddressInfo
	    * @Date: 2017. 7. 24.
	    * @Author :  서 정 길
	    * @Description	:	기본 배송지 정보
	   */
	@Override
	public SqlMap getDefaultAddressInfo(Integer memberIdx) throws Exception {
		SqlMap info = null;
		HashMap<String, Object> map = new HashMap<String, Object>();

		try{
			map.put("MEMBERIDX", memberIdx);	// 회원 일련번호

			info = orderDAO.getDefaultAddressInfo(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return info;
	}

	   /**
	    * @Method : getShippingPrice
	    * @Date: 2017. 7. 24.
	    * @Author :  서 정 길
	    * @Description	:	배송비 반환
	   */
	@Override
	public double getShippingPrice(Double orderPriceSum) throws Exception{
		double shippingPrice = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			map.put("ORDERPRICESUM", orderPriceSum);	// 총 구매금액
			
			shippingPrice = orderDAO.getShippingPrice(map);
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return shippingPrice;
	}
	
	   /**
	    * @Method : getRandomPriceGiftInfo
	    * @Date: 2017. 7. 24.
	    * @Author :  서 정 길
	    * @Description	:	구매금액별 사은품 랜덤하게 1개 반환
	   */
	@Override
	public SqlMap getRandomPriceGiftInfo(String orderCd) throws Exception {
		SqlMap giftInfo = null;
		HashMap<String, Object> map = new HashMap<String, Object>();

		try{
			map.put("ORDERCD", orderCd);		// 주문코드

			giftInfo = orderDAO.getRandomPriceGiftInfo(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return giftInfo;
	}

	   /**
	    * @Method : insertPriceGift
	    * @Date: 2017. 7. 25.
	    * @Author :  서 정 길
	    * @Description	:	구매 금액별 사은품 저장
	   */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public JsonResultVO insertPriceGift(OrderVO vo) throws Exception {
		
		JsonResultVO resultMap = new JsonResultVO();
		
		int flag = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();

		try{
			// 주문 정보 구함
			SqlMap orderInfo = this.getOrderInfo(vo);					// 주문 마스터 정보(결제전건만)
			if(orderInfo == null || orderInfo.isEmpty()){
				resultMap.setResult(false);
				resultMap.setMsg("주문 정보가 존재하지 않습니다!");
				return resultMap;
			}			
			
			// 구매 금액별 사은품 삭제
			map.clear();
			map.put("ORDERCD", vo.getOrderCd());	// 주문코드			
			
			boolean isevent = false;
			
			//네이버 타임보드 이벤트 (2019-11-13 회원 가입자 대상)면 사은품 안준다.
			List<SqlMap> detaillist = orderDAO.getOrderGoodsList(map);
			for (SqlMap item : detaillist) {
				Integer goodsidx = (item.get("goodsIdx") == null ? 0 : Integer.parseInt(item.get("goodsIdx").toString()));
				if (goodsidx == 823) {
					isevent = true;
					break;
				}
			}
			
			if (!isevent) 
			{			
				// 구매 금액별 사은품 삭제
				map.put("SELYN", "Y");							// 선택사은품 여부 (Y : 선택사은품, N : 브랜드, 금액 해당되면 모두증정)
				flag = orderDAO.deleteOrderGift(map);	// 구매 금액별 사은품 삭제
							
				if(vo.getSelectPriceGiftIdx() == null){	// 구매금액별 사은품 선택하지 않았을시 금액별 사은품 랜덤 1개 저장
					// 구매금액별 사은품 내역 저장
					SqlMap priceGiftList = this.getRandomPriceGiftInfo(vo.getOrderCd());	// 구매금액별 사은품 랜덤하게 1개 반환
					if(priceGiftList != null){
						map.clear();
						map.put("ORDERIDX", String.valueOf(orderInfo.get("orderIdx")));			// 주문 마스터 일련번호 (TO_ORDER 일련번호)
						map.put("ORDERCD", vo.getOrderCd());												// 주문코드
						map.put("GIFTIDX", String.valueOf(priceGiftList.get("giftIdx")));			// 사은품 일련번호 (TP_GIFT 일련번호)
						map.put("GIFTNM", (String)priceGiftList.get("giftNm"));						// 사은품명
						map.put("GIFTIMG", (String)priceGiftList.get("giftImg"));						// 사은품 이미지
						map.put("GIFTREALIMG", (String)priceGiftList.get("giftRealImg"));			// 사은품 실제 이미지
						map.put("GIFTFLAG", (String)priceGiftList.get("giftFlag"));					// 사은품 구분 (P : 금액별 G : 상품별(상품별사용안함))
						map.put("TERMIDX", String.valueOf(priceGiftList.get("termIdx")));			// 사은품구간일련번호 (TP_GIFT_TERM 일련번호)
						map.put("FREEYN", (String)priceGiftList.get("freeYn"));						// 무료구분 (Y : 무료사은품, N:구간사은품)
						map.put("BRANDIDX", String.valueOf(priceGiftList.get("brandIdx")));		// 브랜드 (0이면 전체)
						map.put("TERMNM", (String)priceGiftList.get("termNm"));						// 구간명
						if(priceGiftList.get("priceStart") == null){
							map.put("PRICESTART", null);	// 구간시작금액 (구간사은품인경우 1이상)
						}else{
							map.put("PRICESTART", String.valueOf(priceGiftList.get("priceStart")));	// 구간시작금액 (구간사은품인경우 1이상)
						}
						if(priceGiftList.get("priceEnd") == null){
							map.put("PRICEEND", null);		// 구간끝금액
						}else{
							map.put("PRICEEND", String.valueOf(priceGiftList.get("priceEnd")));		// 구간끝금액
						}
						map.put("GIFTSTARTDATE", (String)priceGiftList.get("giftStartDate"));	// 사은품 증정 시작일자
						map.put("GIFTENDDATE", (String)priceGiftList.get("giftEndDate"));		// 사은품 증정 종료일자
						map.put("SELYN", (String)priceGiftList.get("selYn"));							// 선택여부 (Y : 선택사은품, N : 브랜드, 금액 해당되면 모두증정)
						map.put("GIFTCNT", (priceGiftList.get("giftCnt") == null ? 1 : Integer.parseInt(priceGiftList.get("giftCnt").toString())));  //사은품 개수"
						
						flag = orderDAO.insertOrderGift(map);	// 사은품 내역 저장
						
						if(flag <= 0){
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
							resultMap.setResult(false);
							resultMap.setMsg("구매 금액별 사은품 저장 중 에러가 발생했습니다. 다시 주문해 주세요!");
							return resultMap;
						}
					}
				}else{	// 구매금액별 사은품 선택했으면 선택한 사은품 저장
					map.clear();
					map.put("ORDERIDX", String.valueOf(orderInfo.get("orderIdx")));			// 주문 마스터 일련번호
					map.put("ORDERCD", vo.getOrderCd());												// 주문코드
					map.put("GIFTIDX", vo.getSelectPriceGiftIdx());									// 선택한 사은품 일련번호
	
					flag = orderDAO.insertSelectedOrderGift(map);	// 선택한 사은품 저장
	
					if(flag <= 0){
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
						resultMap.setResult(false);
						resultMap.setMsg("구매 금액별 사은품 저장 중 에러가 발생했습니다. 다시 주문해 주세요!");
						return resultMap;
					}
				}
			}
			
			resultMap.setResult(true);
		}catch(Exception e){
			e.printStackTrace();
			resultMap.setResult(false);
			resultMap.setMsg("구매 금액별 사은품 저장 중 에러가 발생했습니다. 다시 주문해 주세요!");
			throw e;
		}
		
		return resultMap;
	}

	   /**
	    * @Method : insertAddress
	    * @Date: 2017. 7. 25.
	    * @Author :  서 정 길
	    * @Description	:	배송지 목록에 저장 처리
	   */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public int insertAddress(String orderCd, HttpServletRequest request) throws Exception {
		int flag = 0;		
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		// 주문 정보 구함
		map.clear();
		map.put("ORDERCD", orderCd);		// 주문 코드

		SqlMap info = orderDAO.getOrderInfo(map);
		
		if(info != null){
			if(info.get("memberIdx") != null){					// 회원 주문이고
				if(info.get("addressTabId") != null && info.get("addressTabId").toString().equals("addressID2")){					// 새로입력 폼으로 주문했으면
					if(info.get("addToAddress") != null && info.get("addToAddress").toString().equals("Y")){							// 배송지 목록에 추가가 체크돼 있으면
						
						// 기본 배송지로 설정이 체크돼 있으면 기존 배송지 중 기본배송지 모두 해제
						if(info.get("setAsDefaultAddress") != null && info.get("setAsDefaultAddress").toString().equals("Y")){
							// 기존 기본 배송지 모두 해제
							map.clear();
							map.put("MEMBERIDX", String.valueOf(info.get("memberIdx")));		// 회원 일련번호

							flag = orderDAO.updateAllAddressToNotDefaultYn(map);
						}
						
						// 배송지 테이블에 저장
						map.clear();
						map.put("MEMBERIDX", String.valueOf(info.get("memberIdx")));		// 회원 일련번호
						map.put("SHIPPINGNM", (String)info.get("shippingNm"));					// 주소록명
						map.put("RECEIVERNM", (String)info.get("receiverNm"));					// 받는 사람 이름
						map.put("ADDR", (String)info.get("receiverAddr"));							// 도로명주소
						map.put("ADDRDETAIL", (String)info.get("receiverAddrDetail"));			// 상세주소 (도로명, 지번 공통)
						map.put("ZIPCD", (String)info.get("receiverZipCd"));						// 우편번호 (신우편번호)
						map.put("OLDADDR", (String)info.get("receiverOldAddr"));				// 지번주소
						map.put("OLDZIPCD", (String)info.get("receiverZipCd"));					// (구)우편번호 ((-) 포함)
						map.put("TELNO", (String)info.get("receiverTelNo"));						// 전화번호
						map.put("PHONENO", (String)info.get("receiverPhoneNo"));				// 핸드폰번호
						if(info.get("setAsDefaultAddress") != null && info.get("setAsDefaultAddress").toString().equals("Y")){	// 기본 배송지로 설정 체크됐으면
							map.put("DEFAULTYN", "Y");														// 기본 배송지 여부 (Y : 기본 배송지, N : 기본 배송지 아님)
						}else{
							map.put("DEFAULTYN", "N");														// 기본 배송지 여부 (Y : 기본 배송지, N : 기본 배송지 아님)
						}
						map.put("REGIDX", String.valueOf(info.get("memberIdx")));				// 작성자 일련번호
						map.put("REGHTTPUSERAGENT", request.getHeader("user-agent"));	// 작성자 USER_AGENT
						InetAddress local = InetAddress.getLocalHost();							// LOCAL IP ADDRESS
						map.put("REGIP", local.getHostAddress());										// 작성자 IP

						flag = orderDAO.insertAddress(map);
					}
				}
			}
		}

		return flag;
	}

	   /**
	    * @Method : deleteAddress
	    * @Date: 2017. 7. 27.
	    * @Author :  서 정 길
	    * @Description	:	배송지 삭제
	   */
	@Override
	public int deleteAddress(OrderVO vo) throws Exception {
		int flag = 0;		
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.clear();
		map.put("MEMBERIDX", vo.getMemberIdx());				// 회원 일련번호
		map.put("ADDRESSIDX", vo.getModifyAddressIdx());	// 삭제할 배송지 일련번호

		flag = orderDAO.deleteAddress(map);

		return flag;
	}
	
	   /**
	    * @Method : updateAddress
	    * @Date: 2017. 7. 27.
	    * @Author :  서 정 길
	    * @Description	:	배송지 수정
	   */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public int updateAddress(OrderVO vo, HttpServletRequest request) throws Exception {
		int flag = 0;		
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		// 기본 배송지로 설정이 체크돼 있으면 기존 배송지 중 기본배송지 모두 해제
		if(vo.getNewSetAsDefaultAddress() != null && vo.getNewSetAsDefaultAddress().toString().equals("Y")){
			// 기존 기본 배송지 모두 해제
			map.clear();
			map.put("MEMBERIDX", vo.getMemberIdx());		// 회원 일련번호

			flag = orderDAO.updateAllAddressToNotDefaultYn(map);
		}
		
		// 배송지 수정
		map.clear();
		map.put("ADDRESSIDX", vo.getModifyAddressIdx());				// 수정할 배송지 일련번호
		map.put("MEMBERIDX", vo.getMemberIdx());						// 회원 일련번호
		map.put("SHIPPINGNM", vo.getNewShippingNm());				// 주소록명
		map.put("RECEIVERNM", vo.getNewReceiverNm());				// 받는 사람 이름
		map.put("ADDR", vo.getNewReceiverAddr());						// 도로명주소
		map.put("ADDRDETAIL", vo.getNewReceiverAddrDetail());		// 상세주소 (도로명, 지번 공통)
		map.put("ZIPCD", vo.getNewReceiverZipCd());						// 우편번호 (신우편번호)
		map.put("OLDADDR", vo.getNewReceiverOldAddr());				// 지번주소
		map.put("OLDZIPCD", vo.getNewReceiverOldZipCd());			// (구)우편번호 ((-) 포함)
		map.put("TELNO", vo.getNewReceiverTelNo());					// 전화번호
		map.put("PHONENO", vo.getNewReceiverPhoneNo());			// 핸드폰번호
		if(vo.getNewSetAsDefaultAddress() != null && vo.getNewSetAsDefaultAddress().toString().equals("Y")){	// 기본 배송지로 설정 체크됐으면
			map.put("DEFAULTYN", "Y");											// 기본 배송지 여부 (Y : 기본 배송지, N : 기본 배송지 아님)
		}else{
			map.put("DEFAULTYN", "N");											// 기본 배송지 여부 (Y : 기본 배송지, N : 기본 배송지 아님)
		}
		map.put("EDITIDX", vo.getMemberIdx());							// 수정자 일련번호
		map.put("EDITHTTPUSERAGENT", request.getHeader("user-agent"));	// 작성자 USER_AGENT
		InetAddress local = InetAddress.getLocalHost();							// LOCAL IP ADDRESS
		map.put("EDITIP", local.getHostAddress());										// 작성자 IP

		flag = orderDAO.updateAddress(map);

		return flag;
	}
	
	/**
	    * @Method : checkPromotionCode
	    * @Date: 2017. 7. 27.
	    * @Author :  강 병 철
	    * @Description	:	프로모션코드 체크
	   */
	public SqlMap checkPromotionCode(String promotioncode) throws Exception {
		SqlMap info = new SqlMap();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("PROMOTIONCODE", promotioncode);
		//초기화
		if (StringUtils.isEmpty(promotioncode)) {
			info.put("gubun", "ING");
			info.put("discountRate", "0");		
		}
		else {
			info = orderDAO.selectPromotionCode(map);
		}
		return info;
	}
	
	/**
	    * @Method : checkPromotionCodeRandom
	    * @Date: 2017. 7. 27.
	    * @Author :  강 병 철
	    * @Description	:	프로모션코드랜덤 체크
	   */
	public SqlMap checkPromotionCodeRandom(String promotioncoderandom) throws Exception {
		SqlMap info = new SqlMap();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("PROMOTIONCODERANDOM", promotioncoderandom);
		//초기화
		if (StringUtils.isEmpty(promotioncoderandom)) {
			info.put("gubun", "ING");
			info.put("discountRate", "0");		
		}
		else {
			info = orderDAO.selectPromotionCodeRandom(map);
		}
		return info;
	}
	
	/**
	    * @Method : applyPromotionCode
	    * @Date: 2017. 7. 27.
	    * @Author :  강 병 철
	    * @Description	:	프로모션코드 적용
	   */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public SqlMap applyPromotionCode(String orderIdx, String promotioncode, int discountRate) throws Exception {
		SqlMap info = new SqlMap();

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.clear();
		map.put("ORDER_IDX", orderIdx);				
		map.put("PROMOTIONCODE", promotioncode);		
		map.put("DISCOUNTRATE", discountRate);					
		orderDAO.updatePromotionCode(map);
    	info = this.procSpPriceDiv(orderIdx);	
		return info;
	}
	
	/**
	    * @Method : applyPromotionCodeRandom
	    * @Date: 2017. 7. 27.
	    * @Author :  강 병 철
	    * @Description	:	랜덤프로모션코드 적용
	   */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public SqlMap applyPromotionCodeRandom(String orderIdx, String promotioncoderandom, int discountRate, String randomcodeYn) throws Exception {
		SqlMap info = new SqlMap();

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.clear();
		map.put("ORDER_IDX", orderIdx);				
		map.put("PROMOTIONCODE", promotioncoderandom);		
		map.put("DISCOUNTRATE", discountRate);
		map.put("RANDOMCODEYN", randomcodeYn);
		orderDAO.updatePromotionCode(map);
 	info = this.procSpPriceDiv(orderIdx);	
		return info;
	}
	
	 /**
	    * @Method : procUsableCouponList
	    * @Date: 2017. 7. 17.
	    * @Author :  강 병 철
	    * @Description	:	사용 가능한 쿠폰 목록
	    *  input
	    *  P_ORDER_IDX			INT					주문 마스터 일련번호
			P_ORDER_DETAIL_IDX	INT					주문 디테일 일련번호	 상품쿠폰일때만 설정하면 된다. 다른쿠폰일때에는 0으로 설정.
			P_COUPON_GUBUN		CHAR(1)				G : 상품쿠폰, C : 장바구니쿠폰, S : 무료배송쿠폰
			
			output : COUPON_IDX,COUPON_MEMBER_IDX,COUPON_NM,DISCOUNT_TYPE,DISCOUNT,DISCOUNT_PRICE,USE_END_DT
	   */
	public List<ProcCouponVO> procUsableCouponList(int orderIdx, int orderdetailIdx, String couponGubun) throws Exception {
		List<ProcCouponVO> info = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.clear();
		map.put("P_ORDER_IDX", orderIdx);				
		map.put("P_ORDER_DETAIL_IDX", orderdetailIdx);		
		map.put("P_COUPON_GUBUN", couponGubun);					
		info = orderDAO.procUsableCouponList(map);
		return info;
	}

	/**
	    * @Method : applyShippingCoupon
	    * @Date: 2017. 7. 27.
	    * @Author :  강 병 철
	    * @Description	:	배송쿠폰적용
	   */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public SqlMap applyShippingCoupon(String orderIdx, int couponIdx) throws Exception {
		SqlMap couponinfo = new SqlMap();
		int coupon_idx = 0;
		int coupon_member_idx = 0;
		
		if (couponIdx > 0) {
			coupon_member_idx = couponIdx;
			coupon_idx = 0;
		}
		else {
			//자동발급쿠폰인경우
			coupon_member_idx = 0;
			coupon_idx = couponIdx*-1;
		}
		/*
		 * 파라미터 설명
			P_ORDER_IDX			INT					주문 마스터 일련번호
			P_ORDER_DETAIL_IDX	INT					주문 디테일 일련번호	--> 상품쿠폰일때만 설정하면 된다. 다른쿠폰일때에는 0으로 설정.
			P_COUPON_GUBUN		CHAR(1)				G : 상품쿠폰, C : 장바구니쿠폰, S : 무료배송쿠폰
			P_COUPON_IDX		INT					쿠폰마스터 테이블의 IDX값.  (자동발급쿠폰일때에만 의미를 갖는다)   /  기존에 적용된 쿠폰을 제거할때는 0으로 설정한다
			P_COUPON_MEMBER_IDX	INT					회원별 쿠폰발급테이블의 IDX값.  (회원이 이미 다운로드 한 쿠폰을 사용했을때 의미를 갖는다)   /  기존에 적용된 쿠폰을 제거할때는 0으로 설정한다
		 * */
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.clear();
		map.put("P_ORDER_IDX", orderIdx);				
		map.put("P_ORDER_DETAIL_IDX", 0);		
		map.put("P_COUPON_GUBUN", "S");		
		map.put("P_COUPON_IDX", coupon_idx);	
		map.put("P_COUPON_MEMBER_IDX", coupon_member_idx);	
		
		couponinfo = orderDAO.procCouponUse(map);
    	if(!couponinfo.get("result").toString().equals("TRUE")){
    		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
		}

		return couponinfo;
	}

	/**
	    * @Method : applyCartCoupon
	    * @Date: 2017. 7. 27.
	    * @Author :  강 병 철
	    * @Description	:	장바구니쿠폰적용
	   */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public SqlMap applyCartCoupon(String orderIdx, int couponIdx) throws Exception {
		SqlMap couponinfo = new SqlMap();

		int coupon_idx = 0;
		int coupon_member_idx = 0;
		
		if (couponIdx > 0) {
			coupon_member_idx = couponIdx;
			coupon_idx = 0;
		}
		else {
			//자동발급쿠폰인경우
			coupon_member_idx = 0;
			coupon_idx = couponIdx*-1;
		}
		/*
		 * 파라미터 설명
			P_ORDER_IDX			INT					주문 마스터 일련번호
			P_ORDER_DETAIL_IDX	INT					주문 디테일 일련번호	--> 상품쿠폰일때만 설정하면 된다. 다른쿠폰일때에는 0으로 설정.
			P_COUPON_GUBUN		CHAR(1)				G : 상품쿠폰, C : 장바구니쿠폰, S : 무료배송쿠폰
			P_COUPON_IDX		INT					쿠폰마스터 테이블의 IDX값.  (자동발급쿠폰일때에만 의미를 갖는다)   /  기존에 적용된 쿠폰을 제거할때는 0으로 설정한다
			P_COUPON_MEMBER_IDX	INT					회원별 쿠폰발급테이블의 IDX값.  (회원이 이미 다운로드 한 쿠폰을 사용했을때 의미를 갖는다)   /  기존에 적용된 쿠폰을 제거할때는 0으로 설정한다
		 * */
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.clear();
		map.put("P_ORDER_IDX", orderIdx);				
		map.put("P_ORDER_DETAIL_IDX", 0);		
		map.put("P_COUPON_GUBUN", "C");		
		map.put("P_COUPON_IDX", coupon_idx);	
		map.put("P_COUPON_MEMBER_IDX", coupon_member_idx);	
		
		couponinfo = orderDAO.procCouponUse(map);
    	if(!couponinfo.get("result").toString().equals("TRUE")){
    		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백			
		}
    	
    	return couponinfo;
	}
	
	/**
	    * @Method : applyUsePoint
	    * @Date: 2017. 7. 27.
	    * @Author :  강 병 철
	    * @Description	:	포인트 결제 적용
	   */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public SqlMap applyUsePoint(String orderIdx, int usePoint) throws Exception {
		SqlMap info = new SqlMap();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.clear();
		map.put("ORDER_IDX", orderIdx);				
		map.put("TOTAL_POINT_PRICE", usePoint);		
		orderDAO.updatePointPrice(map);
		info = this.procSpPriceDiv(orderIdx);	
		return info;
	}

	   /**
	    * @Method : applyGiftCoupon
	    * @Date: 2017. 7. 31.
	    * @Author :  서 정 길
	    * @Description	:	상품쿠폰적용
	   */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public SqlMap applyGiftCoupon(String orderIdx, String couponInfo) throws Exception {
		SqlMap couponinfo = new SqlMap();
		
		String[] arrCouponInfo = couponInfo.split(":;:",-1);
		if(arrCouponInfo.length == 0){
			return null;
		}
		
		for(int i=0;i<arrCouponInfo.length;i++){
			String[] arrCoupon = arrCouponInfo[i].split(",",-1);
			
			if(arrCoupon[0] == null || arrCoupon[0].isEmpty()){	// orderDetailIdx 는 ''이 아니어야 함
				return null;
			}
			
			int orderDetailIdx = 0;
			int giftCouponIdx = 0;
			
			// 숫자형 체크
			try{
				orderDetailIdx = Integer.parseInt(arrCoupon[0]);
				if(arrCoupon[1] == null || arrCoupon[1].isEmpty()){		// 쿠폰이 없으면 0
					giftCouponIdx = 0;
				}else{
					giftCouponIdx = Integer.parseInt(arrCoupon[1]);
				}
			}catch(Exception e){
				return null;
			}			
			
			int coupon_idx = 0;
			int coupon_member_idx = 0;
			
			if(giftCouponIdx > 0){
				coupon_member_idx = giftCouponIdx;
				coupon_idx = 0;
			}else{
				//자동발급쿠폰인경우
				coupon_member_idx = 0;
				coupon_idx = giftCouponIdx*-1;
			}
			
			/*
			 * 파라미터 설명
				P_ORDER_IDX			INT					주문 마스터 일련번호
				P_ORDER_DETAIL_IDX	INT					주문 디테일 일련번호	--> 상품쿠폰일때만 설정하면 된다. 다른쿠폰일때에는 0으로 설정.
				P_COUPON_GUBUN		CHAR(1)				G : 상품쿠폰, C : 장바구니쿠폰, S : 무료배송쿠폰
				P_COUPON_IDX		INT					쿠폰마스터 테이블의 IDX값.  (자동발급쿠폰일때에만 의미를 갖는다)   /  기존에 적용된 쿠폰을 제거할때는 0으로 설정한다
				P_COUPON_MEMBER_IDX	INT					회원별 쿠폰발급테이블의 IDX값.  (회원이 이미 다운로드 한 쿠폰을 사용했을때 의미를 갖는다)   /  기존에 적용된 쿠폰을 제거할때는 0으로 설정한다
			 * */
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.clear();
			map.put("P_ORDER_IDX", orderIdx);
			map.put("P_ORDER_DETAIL_IDX", orderDetailIdx);
			map.put("P_COUPON_GUBUN", "G");
			map.put("P_COUPON_IDX", coupon_idx);
			map.put("P_COUPON_MEMBER_IDX", coupon_member_idx);
			
			couponinfo = orderDAO.procCouponUse(map);
			
	    	if(!couponinfo.get("result").toString().equals("TRUE")){
	    		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
				return couponinfo;
			}
		}

		return couponinfo;
	}

	   /**
	    * @Method : dbProcessAfterPointPayment
	    * @Date: 2017. 8. 1.
	    * @Author :  서 정 길
	    * @Description	:	포인트 결제 DB 처리
	   */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public JsonResultVO dbProcessAfterPointPayment(OrderVO vo) throws Exception {

		JsonResultVO resultMap = new JsonResultVO();

		String orderCd = vo.getOrderCd();	// 주문 코드
		String orderIdx = this.getOrderIdxByOrderCdOnly(orderCd);	// 주문 번호 반환
		if(orderIdx == null || orderIdx.isEmpty()){
			resultMap.setResult(false);
			resultMap.setMsg("주문 번호가 존재하지 않습니다!");
			return resultMap;
		}

		SqlMap info = this.procSpPgCheckAfter(orderIdx);	// 주문 유효성 체크 & 주문 상태 변경

		if(info == null){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			System.out.println("결제 후 주문 유효성 확인 중 에러가 발생했습니다! : " + orderCd);
			resultMap.setResult(false);
			resultMap.setMsg("결제 후 주문 유효성 확인 중 에러가 발생했습니다! 다시 결제하여 주시기 바랍니다.");
			return resultMap;
		}else{
			if(!info.get("result").toString().equals("TRUE")){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
				System.out.println("결제 후 유효성 확인 결과 : " + orderCd + " : "+ info.get("result").toString());
				resultMap.setResult(false);
				resultMap.setMsg("결제 후 유효성 확인 결과 : " + info.get("result").toString());
				return resultMap;
			}
		}

		SqlMap orderInfo = this.getOrderCompleteInfo(vo);	// 주문 정보
		
		if(orderInfo == null){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			System.out.println("결제 후 주문 정보 확인 중 에러가 발생했습니다! : " + orderCd);
			resultMap.setResult(false);
			resultMap.setMsg("결제 후 주문 정보 확인 중 에러가 발생했습니다! 다시 결제하여 주시기 바랍니다.");
			return resultMap;
		}

		resultMap.setData1(orderInfo.get("senderNm").toString());		// 구매자명
		resultMap.setData2(orderInfo.get("payDt").toString());				// 결제일자
		resultMap.setData3(orderInfo.get("totalPayPrice").toString());	// 결제일자
		
		resultMap.setResult(true);
		return resultMap;
	}

	   /**
	    * @Method : deleteDefaultPayment
	    * @Date: 2017. 8. 2.
	    * @Author :  서 정 길
	    * @Description	:	기본 결제수단 삭제
	   */
	@Override
	public int deleteDefaultPayment(OrderVO vo) throws Exception {
		int flag = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.clear();
		map.put("MEMBERIDX", vo.getMemberIdx());	// 회원 일련번호
		
		flag = orderDAO.deleteDefaultPayment(map);	// 기본 결제수단 삭제

		return flag;
	}

	   /**
	    * @Method : insertDefaultPayment
	    * @Date: 2017. 8. 2.
	    * @Author :  서 정 길
	    * @Description	:	기본 결제수단 저장
	   */
	@Override
	public int insertDefaultPayment(OrderVO vo) throws Exception {
		int flag = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.clear();
		map.put("MEMBERIDX", vo.getMemberIdx());	// 회원 일련번호
		map.put("DEVICE", PathUtil.getDeviceNm());		// 디바이스 (P : PC, M : MOBILE, A : APP)
		map.put("SELECTPAYTYPE", vo.getSelectPayType());								// 선택한 결제 수단 (상태유지용 필드, BILLKEY : 원클릭 결제, SC0010 : 신용카드)
		map.put("SELECTBILLKEYVAL", vo.getSelectBillkeyVal());						// 선택한 빌키 SELECTBOX value (상태유지용 필드)
		map.put("SELECTCARDCODE", vo.getSelectCardCode());						// 선택한 카드 코드 (상태유지용 필드)
		map.put("ETCCARDVAL", vo.getEtcCardVal());										// 선택한 기타 카드 SELECTBOX value (상태유지용 필드)
		map.put("ESCROWYN1VAL", vo.getEscrowYn1Val());								// 실시간 계좌 이체시 선택한 에스크로 value (상태유지용 필드)
		map.put("ESCROWYN2VAL", vo.getEscrowYn2Val());								// 가상계좌 입금시 선택한 에스크로 value (상태유지용 필드)

		if(vo.getPayType() != null && (vo.getPayType().equals("PAY_TYPE20") || vo.getPayType().equals("PAY_TYPE25"))){		// 실시간계좌나 가상계좌
			if(vo.getPayType().equals("PAY_TYPE20")){					// 실시간계좌
				map.put("ESCROWYN", vo.getEscrowYn1Val());						// 에스크로 여부 (Y : 에스크로 사용, N : 에스크로 사용 안 함)
			}else{
				map.put("BANKCODE", null);													// 입금 은행코드 (무통장(가상계좌)일때 사용)
				map.put("CASHRECEIPTGUBUN", null);									// 현금영수증 여부 (0 : 미신청, 1 : 개인, 2 : 사업자)
				map.put("CASHRECEIPTNO", null);											// 현금영수증 번호 (CASH_RECEIPT_GUBUN가 '0'이 아닐때만 사용, 개인은 휴대폰번호, 사업자는 사업자등록번호)
			}
			
			if(vo.getPayType().equals("PAY_TYPE25")){					// 가상계좌
				map.put("BANKCODE", vo.getBankCode());								// 입금 은행코드 (무통장(가상계좌)일때 사용)
				map.put("ESCROWYN", vo.getEscrowYn2Val());						// 에스크로 여부 (Y : 에스크로 사용, N : 에스크로 사용 안 함)
				map.put("CASHRECEIPTGUBUN", vo.getCashReceiptGubun());	// 현금영수증 여부 (0 : 미신청, 1 : 개인, 2 : 사업자)
				map.put("CASHRECEIPTNO", vo.getCashReceiptNo());				// 현금영수증 번호 (CASH_RECEIPT_GUBUN가 '0'이 아닐때만 사용, 개인은 휴대폰번호, 사업자는 사업자등록번호)
			}
		}else{
			map.put("BANKCODE", null);														// 입금 은행코드 (무통장(가상계좌)일때 사용)
			map.put("ESCROWYN", null);													// 에스크로 여부 (Y : 에스크로 사용, N : 에스크로 사용 안 함)
			map.put("CASHRECEIPTGUBUN", null);										// 현금영수증 여부 (0 : 미신청, 1 : 개인, 2 : 사업자)
			map.put("CASHRECEIPTNO", null);												// 현금영수증 번호 (CASH_RECEIPT_GUBUN가 '0'이 아닐때만 사용, 개인은 휴대폰번호, 사업자는 사업자등록번호)
		}
		
		map.put("REGIDX", vo.getRegIdx());
		map.put("REGHTTPUSERAGENT", vo.getRegHttpUserAgent());
		map.put("REGIP", vo.getRegIp());
		
		flag = orderDAO.insertDefaultPayment(map);	// 기본 결제수단 저장
		
		return flag;
	}

	   /**
	    * @Method : updatePaycoCasNoteUrlOrderMaster
	    * @Date: 2017. 8. 9.
	    * @Author :  서 정 길
	    * @Description	:	PAYCO 가상계좌 입금 확인시 - 주문 상태 결제 완료로 수정 등 처리
	   */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public JsonResultVO updatePaycoCasNoteUrlOrderMaster(String orderCd, List<PaycoPaymentVO> paymentList) throws Exception {

		JsonResultVO resultMap = new JsonResultVO();
		
		int flag = 0;
		
		// 무통장 일때의 날짜 뽑음
		String payDt = "";
		
		for(int i=0;i<paymentList.size();i++){
			if(paymentList.get(i).getPaymentMethodCode().equals("02")){	// 무통장
				payDt = paymentList.get(i).getTradeYmdt();
				break;
			}
		}

		if(payDt.isEmpty()){
			System.out.println("무통장 결제일시가 없습니다! : " + orderCd);
			resultMap.setResult(false);
			resultMap.setMsg("무통장 결제일시가 없습니다!");
			return resultMap;
		}		
		
		HashMap<String, Object> map = new HashMap<String, Object>();

		// 주문 상태 결제 완료로 수정, 결제일자 저장
		map.put("ORDERCD", orderCd);		// 주문 코드
		map.put("PAYDT", payDt);
		
		flag = orderDAO.updateCasNoteUrlOrderMaster(map);	// 마스터, 디테일 주문 상태 결제 완료로 수정, 결제일자 저장

		if(flag < 1){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			System.out.println("주문 상태 결제 완료로 수정된 건이 없습니다! : " + orderCd);
			resultMap.setResult(false);
			resultMap.setMsg("주문 상태 결제 완료로 수정된 건이 없습니다!");
			return resultMap;
		}
		
		// 서브 디테일 주문 상태 결제 완료로 수정
		map.clear();
		map.put("ORDERCD", orderCd);		// 주문 코드

		orderDAO.updateCasNoteUrlOrderSubDetail(map);	// 서브 디테일 주문 상태 결제 완료로 수정 (서브 디테일은 없을수도 있으니 결과 안 받음)

		// PAYCO 결제 결과 테이블 업데이트 (TO_PAYCO_APPROVAL, TO_PAYCO_APPROVAL_PAYMENT)
		for(int i=0;i<paymentList.size();i++){
			map.clear();
			map.put("ORDERCD", orderCd);																		// 주문 코드
			map.put("paymentMethodCode", paymentList.get(i).getPaymentMethodCode());	// 결제수단코드
			map.put("paymentCompletionYn", "Y");															// 결제완료여부 ( Y/N )
			map.put("paymentCompleteYmdt", payDt);														// 결제완료일자 ( yyyyMMddHHmmss )
			map.put("paymentTradeNo", paymentList.get(i).getPaymentTradeNo());				// 결제번호 ( ex)201409191000001142 )
			map.put("tradeYmdt", paymentList.get(i).getTradeYmdt());								// 결제일시 ( yyyyMMddHHmmss )
			map.put("pgAdmissionNo", paymentList.get(i).getPgAdmissionNo());					// 원천사승인번호
			map.put("pgAdmissionYmdt", paymentList.get(i).getPgAdmissionYmdt());			// 원천사승인일시

			flag = orderDAO.updatePaycoNonBankResult(map);	// PAYCO 무통장 결제 결과 적용
			
			if(flag < 1){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
				System.out.println("PAYCO 결제 결과 테이블 수정된 건이 없습니다! : " + orderCd + ", " + paymentList.get(i).getPaymentMethodCode());
				resultMap.setResult(false);
				resultMap.setMsg("PAYCO 결제 결과 테이블 수정된 건이 없습니다!");
				return resultMap;
			}
		}		
		
		resultMap.setResult(true);
		return resultMap;
	}

	   /**
	    * @Method : updateOrderMasterCyberAccountInfo
	    * @Date: 2017. 8. 10.
	    * @Author :  서 정 길
	    * @Description	:	가상계좌 결제 - 주문 마스터 가상계좌 정보 수정
	    * orderCd : 주문코드
	    * type : LG U+, PAYCO 구분 (XPAY : LG U+, PAYCO : PAYCO, KCP)
	    * bankcode : 은행코드
	    * account : 계좌번호
	    * depositor : 예금주명
	    * depositDeadlineDt : 입금 기한
	   */
	@Override
	public int updateOrderMasterCyberAccountInfo(String orderCd, String type, String bankCode, String account, String depositor, String depositDeadlineDt) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("ORDERCD", orderCd);			// 주문 코드
		map.put("TYPE", type);					// LG U+, PAYCO 구분 (XPAY,PAYCO, KCP)
		map.put("BANKCODE", bankCode);		// 은행코드
		map.put("ACCOUNT", account);			// 계좌번호
		map.put("DEPOSITOR", depositor);	// 예금주명
		if(depositDeadlineDt == null || depositDeadlineDt.isEmpty()){
			map.put("DEPOSITDEADLINEDT", null);	// 입금 기한
		}else{
			map.put("DEPOSITDEADLINEDT", depositDeadlineDt);	// 입금 기한
		}
		
		flag = orderDAO.updateOrderMasterCyberAccountInfo(map);
		
		return flag;
	}

	   /**
	    * @Method : insertCyberAccountOrderStatusLog
	    * @Date: 2017. 8. 10.
	    * @Author :  서 정 길
	    * @Description	:	가상계좌 입금 완료시 주문 상태 로그 저장
	   */
	@Override
	public int insertCyberAccountOrderStatusLog(String orderCd) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();

		try{
			map.put("ORDERCD", orderCd);		// 주문 코드

			flag = orderDAO.insertCyberAccountOrderStatusLog(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return flag;	
	}

	/**
	    * @Method : callSPSmsSendAndEmail
	    * @Date: 2017. 8. 10.
	    * @Author :  강 병 철
	    * @Description	:	주문 SMS / 이메일 발송 
	    * Gubun ORD001 = 가상계좌, ORD002 = 그외 결제, ORD001ORD002 = 가상계좌 입금완료
	   */
	public void callSPSmsSendAndEmail(String Gubun,  String orderCd) throws Exception {
		try{
			OrderVO vo = new OrderVO();
			
			if (UserInfo.getUserInfo().getMemberId().isEmpty()) {
				vo.setMemberIdx(0);
				vo.setNomemberOrderCd(orderCd); //비회원으로 조회하면 orderCd로만 조회됨.				
			}
			else {
				vo.setMemberIdx(UserInfo.getUserInfo().getMemberIdx());
				vo.setOrderCd(orderCd); //비회원으로 조회하면 orderCd로만 조회됨.
			}
			
			SqlMap orderInfo = mypageOrderService.getOrderInfo(vo, true);			// 주문 마스터 정보, 가상계좌 입금에서 호출(true)				
			List<SqlMap> list = mypageOrderService.getOrderDetailList(vo, true);	// 주문 상세 리스트, 가상계좌 입금에서 호출(true)
	
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			//SMS발송
			if(Gubun.equals("ORD001ORD002")){	// 가상계좌 입금완료에서 호출이면 알림톡은 주문 완료(ORD002)를 발송
				map.put("TEMPLATE_CD", "ORD002");
			}else{
				map.put("TEMPLATE_CD", Gubun);
			}
			map.put("ORDER_IDX", orderInfo.get("orderIdx"));			// 주문 번호
			try {
				orderDAO.callSPSmsSendAndEmail(map);				
			} catch (Exception e) {
			}
			
			//이메일발송
			if (Gubun.equals("ORD001")) { 
				MailUtil.sendOrderBankCompletEmail(orderInfo, list); //가상계좌 주문 완료
			} else if(Gubun.equals("ORD001ORD002")) {	// 가상계좌 입금완료에서 호출이면 메일은
				MailUtil.sendOrderBankDepositCompletEmail(orderInfo, list); //가상계좌 입금 완료
			} else {
				MailUtil.sendOrderCardCompletEmail(orderInfo, list);
			}
		}catch(Exception e){
		}
	}
	
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
	public void callOrderClaimSmsSendAndEmail(String Gubun, String orderCd,  int ClaimIdx) throws Exception {
		try{
			OrderVO vo = new OrderVO();
			
			if (UserInfo.getUserInfo().getMemberId().isEmpty()) {
				vo.setMemberIdx(0);
				vo.setNomemberOrderCd(orderCd); //비회원으로 조회하면 orderCd로만 조회됨.				
			}
			else {
				vo.setMemberIdx(UserInfo.getUserInfo().getMemberIdx());
				vo.setOrderCd(orderCd); //비회원으로 조회하면 orderCd로만 조회됨.
			}
			
			SqlMap orderInfo = mypageOrderService.getOrderInfo(vo, false);			// 주문 마스터 정보, 가상계좌 입금에서 호출 아님 (false)	
			List<SqlMap> list = mypageOrderService.getClaimDetailList(ClaimIdx);	// 주문 상세 리스트
	
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			//SMS발송
			map.put("TEMPLATE_CD", Gubun);		// 주문 코드
			map.put("ORDER_IDX", ClaimIdx); //클레임번호를 넘겨줌.
			try {
				orderDAO.callSPSmsSendAndEmail(map);				
			} catch (Exception e) {
			}
			
			//이메일발송 (취소,교환,반품)
			if (Gubun.equals("ORD004") || Gubun.equals("ORD005") || Gubun.equals("ORD006") ) {
				MailUtil.sendOrderCancelEmail(orderInfo, list); //취소
			} else if (Gubun.equals("ORD007")){
				MailUtil.sendOrderChangeEmail(orderInfo, list);  //교환
			} else if (Gubun.equals("ORD008")){
				MailUtil.sendOrderReturnEmail(orderInfo, list);  //반품
			}
		}catch(Exception e){
		}
	}
	
	   /**
	    * @Method : getCasNoteOrderInfo
	    * @Date: 2017. 9. 4.
	    * @Author :  서 정 길
	    * @Description	:	주문 마스터 상태 코드 반환
	   */
	@Override
	public String getOrderStatusCd(String orderCd) throws Exception {
		String orderStatusCd = "";
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			map.put("ORDERCD", orderCd);		// 주문 코드
		
			orderStatusCd = orderDAO.getOrderStatusCd(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}

		return orderStatusCd;
	}

	   /**
	    * @Method : insertXPayEscrowResultLog
	    * @Date: 2017. 9. 28.
	    * @Author :  서 정 길
	    * @Description	:	LG U+ 에스크로 결과 로그 저장
	   */
	@Override
	public int insertXPayEscrowResultLog(EscrowResultVO vo) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();

		try{
			map.put("mid", vo.getMid());
			map.put("oid", vo.getOid());
			map.put("tid", vo.getTid());
			map.put("txtype", vo.getTxtype());
			map.put("productid", vo.getProductid());
			map.put("ssn", vo.getSsn());
			map.put("ip", vo.getIp());
			map.put("mac", vo.getMac());
			map.put("resdate", vo.getResdate());
			map.put("hashdata", vo.getHashdata());
			
			flag = orderDAO.insertXPayEscrowResultLog(map);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return flag;
	}

	   /**
	    * @Method : updateOrderDetailDeliveredDt
	    * @Date: 2017. 9. 29.
	    * @Author :  서 정 길
	    * @Description	:	에스크로 택배사배송완료 수신 - 배송완료일 저장
	   */
	@Override
	public int updateOrderDetailDeliveredDt(String orderCd, String deliveredDt) throws Exception {
		int flag = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.clear();
		map.put("ORDERCD", orderCd);				// 주문코드
		map.put("DELIVEREDDT", deliveredDt);	// 배송완료일

		flag = orderDAO.updateOrderDetailDeliveredDt(map);
		
		return flag;
	}

	/**
	 * @Method : saveSmilePayResult
	 * @Date: 2017. 12. 8.
	 * @Author :  김  민  수
	 * @Description	:	스마일 페이 처리 결과 저장 (결제요청)
	 * @HISTORY :
	 *
	 */
	public int saveSmilePayResult(SmilepayResultVO resultVO)throws Exception{
		int flag = 1;
		try{
			HashMap<String, Object> map = new HashMap<>();
			map.put("ORDERCD", resultVO.getOrderCd());
			map.put("RESULTCODE", resultVO.getResultCode());
			map.put("RESULTMSG", resultVO.getResultMsg());			
			map.put("ERRORCD", resultVO.getErrorCD());			
			map.put("ERRORMSG", resultVO.getErrorMsg());			
			map.put("TID", resultVO.getTid());			
			map.put("MOID", resultVO.getMoid());			
			map.put("MID", resultVO.getMid());			
			map.put("PAYMETHOD", resultVO.getPayMethod());			
			map.put("AMT", resultVO.getAmt());			
			map.put("DISCOUNTAMT", resultVO.getDiscountAmt());
			map.put("AUTHDATE", resultVO.getAuthDate());			
			map.put("AUTHCODE", resultVO.getAuthCode());			
			map.put("CARDCODE", resultVO.getCardCode());			
			map.put("ACQUCARDCODE", resultVO.getAcquCardCode());			
			map.put("CARDNAME", resultVO.getCardName());			
			map.put("CARDQUOTA", resultVO.getCardQuota());						
			map.put("CARDINTEREST", resultVO.getCardInterest());			
			map.put("CARDCL", resultVO.getCardCl());			
			map.put("CARDBIN", resultVO.getCardBin());			
			map.put("CARDPOINT", resultVO.getCardPoint());									
			map.put("VANCODE", resultVO.getVanCode());			
			map.put("FNNO", resultVO.getFnNo());			
			map.put("CARDNO", resultVO.getCardNo());
			
			flag = orderDAO.saveSmilePayResult(map);
			
		}catch(Exception e){
			e.printStackTrace();
			flag = 0;
		}
		return flag;
	}
	
	/**
	 * @Method : saveSamilPayCancelResult
	 * @Date: 2017. 12. 8.
	 * @Author :  김  민  수
	 * @Description	:	스마일페이 결제취소 처리 결과 저장(성공/실패)	
	 * @HISTORY :
	 *
	 */
	public int saveSamilPayCancelResult(SmilepayCancelVO resultVO)throws Exception{
		int flag = 1;
		
		try{
			HashMap<String, Object> map = new HashMap<>();
			map.put("ORDERCD", resultVO.getOrderCd());
			map.put("RESULTCODE", resultVO.getResultCode());
			map.put("RESULTMSG", resultVO.getResultMsg());
			map.put("ERRORCD", resultVO.getErrorCd());
			map.put("ERRORMSG", resultVO.getErrorMsg());
			map.put("CANCELAMT", resultVO.getCancelAmt());
			map.put("CANCELDATE", resultVO.getCancelDate());
			map.put("CANCELTIME", resultVO.getCancelTime());
			map.put("PAYMETHOD", resultVO.getPayMethod());
			map.put("MID", resultVO.getMid());
			map.put("TID", resultVO.getTid());
			map.put("STATECD", resultVO.getStateCd());
			map.put("CANCELNUM", resultVO.getCancelNum());
			map.put("VANCODE", resultVO.getVanCode());
			
			orderDAO.saveSamilPayCancelResult(map);
			
		}catch(Exception e){
			e.printStackTrace();
			flag = 0;
		}
		
		return flag;
	}
	
	/**
	 * @Method : dbProcessAfterSmilepay
	 * @Date: 2017. 12. 10.
	 * @Author :  김  민  수
	 * @Description	:	스마일 페이 결제 후 DB 처리 
	 * @HISTORY :
	 *
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public JsonResultVO dbProcessAfterSmilepay(SmilepayResultVO smilepayResultVO)throws Exception{
		JsonResultVO resultMap = new JsonResultVO();
		String orderCd = smilepayResultVO.getOrderCd();
		String orderIdx = this.getOrderIdxByOrderCdOnly(smilepayResultVO.getOrderCd());
		if(orderIdx == null || orderIdx.isEmpty()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			resultMap.setMsg("주문 번호가 존재하지 않습니다!");
			return resultMap;
		}
		SqlMap info = this.procSpPgCheckAfter(orderIdx);	// 주문 유효성 체크 & 주문 상태 변경

		if(info == null){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			System.out.println("결제 후 주문 유효성 확인 중 에러가 발생했습니다! : " + orderCd);
			resultMap.setResult(false);
			resultMap.setMsg("결제 후 주문 유효성 확인 중 에러가 발생했습니다! 다시 결제하여 주시기 바랍니다.");
			return resultMap;
		}else{
			if(!info.get("result").toString().equals("TRUE")){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
				System.out.println("결제 후 유효성 확인 결과 : " + orderCd + " : "+ info.get("result").toString());
				resultMap.setResult(false);
				resultMap.setMsg("결제 후 유효성 확인 결과 : " + info.get("result").toString());
				return resultMap;
			}
		}
		
		int flag = this.saveSmilePayResult(smilepayResultVO);									//스마일 페이 결제 결과 저장
		if(flag <1){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			resultMap.setMsg("결제 결과 저장중 에러가 발생했습니다. 다시 결제하여 주시기 바랍니다.");
			return resultMap;
		}	
		resultMap.setResult(true);
			
		return resultMap;
	}

	/**
	 * @Method : getGiftList
	 * @Date		: 2018. 2. 22.
	 * @Author	:  서  정  길 
	 * @Description	:	주문 페이지 구매금액별 사은품 리스트
	 */
	@Override
	public List<SqlMap> getGiftList(String freeYn, String orderCd) throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("FREEYN", freeYn);			// 무료 구분 (Y : 무료사은품, N:구간사은품)
		map.put("ORDERCD", orderCd);		// 주문코드

		try{
			list = orderDAO.getGiftList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	/**
	 * @Method : getNoGiftSelectList
	 * @Date		: 2018. 3. 27.
	 * @Author	:  서  정  길 
	 * @Description	:	주문 페이지 사은품 선택 안함 1개
	 */
	@Override
	public SqlMap getNoGiftSelectList(String orderCd) throws Exception {
		SqlMap list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("ORDERCD", orderCd);		// 주문코드

		try{
			list = orderDAO.getNoGiftSelectList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}
	
	/**
	 * @Method : saveFreeGift
	 * @Date		: 2018. 3. 6.
	 * @Author	:  서  정  길 
	 * @Description	:	무료 사은품 내역 저장
	 */
	@Override
	public int saveFreeGift(String orderIdx) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int flag = 0;
		
		// 무료 사은품 내역 저장(회원일때만)
		UserInfo userInfo = UserInfo.getUserInfo();
		
		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원일때만			
			String orderCd = this.getOrderCd(orderIdx);	// 주문 코드 반환
			
			// 무료 금액별 사은품 삭제
			map.clear();
			map.put("ORDERCD", orderCd);	// 주문코드			
			map.put("SELYN", "N");						// 선택사은품 여부 (Y : 선택사은품, N : 브랜드, 금액 해당되면 모두증정)
			flag = orderDAO.deleteOrderGift(map);	// 무료 금액별 사은품 삭제

			boolean isevent = false;
			
			//네이버 타임보드 이벤트 (2019-11-13 회원 가입자 대상)면 사은품 안준다.
			List<SqlMap> detaillist = orderDAO.getOrderGoodsList(map);
			for (SqlMap item : detaillist) {
				Integer goodsidx = (item.get("goodsIdx") == null ? 0 : Integer.parseInt(item.get("goodsIdx").toString()));
				if (goodsidx == 823) {
					isevent = true;
					break;
				}
			}
			
			// 무료 사은품 내역 저장
			if (!isevent) {			
				List<SqlMap> freeGiftList = this.getGiftList("Y", orderCd);				// 무료 사은품 리스트
				
				if(freeGiftList != null){
					for(int i=0;i<freeGiftList.size();i++){
						// 주문 상세 저장
						map.clear();
						map.put("ORDERIDX", orderIdx);																// 주문 마스터 일련번호 (TO_ORDER 일련번호)
						map.put("ORDERCD", orderCd);																	// 주문코드
						map.put("GIFTIDX", String.valueOf(freeGiftList.get(i).get("giftIdx")));			// 사은품 일련번호 (TP_GIFT 일련번호)
						map.put("GIFTNM", (String)freeGiftList.get(i).get("giftNm"));						// 사은품명
						map.put("GIFTIMG", (String)freeGiftList.get(i).get("giftImg"));						// 사은품 이미지
						map.put("GIFTREALIMG", (String)freeGiftList.get(i).get("giftRealImg"));			// 사은품 실제 이미지
						map.put("GIFTFLAG", (String)freeGiftList.get(i).get("giftFlag"));						// 사은품 구분 (P : 금액별 G : 상품별(상품별사용안함))
						map.put("TERMIDX", String.valueOf(freeGiftList.get(i).get("termIdx")));			// 사은품구간일련번호 (TP_GIFT_TERM 일련번호)
						map.put("BRANDIDX", String.valueOf(freeGiftList.get(i).get("brandIdx")));		// 브랜드 (0이면 전체)
						map.put("FREEYN", (String)freeGiftList.get(i).get("freeYn"));							// 무료구분 (Y : 무료사은품, N:구간사은품)
						map.put("TERMNM", (String)freeGiftList.get(i).get("termNm"));						// 구간명
						if(freeGiftList.get(i).get("priceStart") == null){
							map.put("PRICESTART", null);	// 구간시작금액 (구간사은품인경우 1이상)
						}else{
							map.put("PRICESTART", String.valueOf(freeGiftList.get(i).get("priceStart")));	// 구간시작금액 (구간사은품인경우 1이상)
						}
						if(freeGiftList.get(i).get("priceEnd") == null){
							map.put("PRICEEND", null);		// 구간끝금액
						}else{
							map.put("PRICEEND", String.valueOf(freeGiftList.get(i).get("priceEnd")));		// 구간끝금액
						}
						map.put("GIFTSTARTDATE", (String)freeGiftList.get(i).get("giftStartDate"));	// 사은품 증정 시작일자
						map.put("GIFTENDDATE", (String)freeGiftList.get(i).get("giftEndDate"));			// 사은품 증정 종료일자
						map.put("SELYN", (String)freeGiftList.get(i).get("selYn"));							// 선택여부 (Y : 선택사은품, N : 브랜드, 금액 해당되면 모두증정)
						map.put("GIFTCNT", (freeGiftList.get(i).get("giftCnt") == null ? 1 : Integer.parseInt(freeGiftList.get(i).get("giftCnt").toString())));  //사은품 개수"
								
						flag = orderDAO.insertOrderGift(map);	// 사은품 내역 저장
						
						if(flag <= 0){
							return flag;
						}					
					}
				}
			}
		}
		
		return flag;
	}
	
   /**
    * @Method : dbProcessAfterKCP
    * @Date: 2018.07.17
    * @Author :  강 병 철
    * @Description	:	KCP 결제 후 DB 처리
   */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public JsonResultVO dbProcessAfterKCP(XPayResultVO resultVo) throws Exception {

		JsonResultVO resultMap = new JsonResultVO();

		int flag = 0;
		
		String orderCd = resultVo.getLGD_OID();	// 주문 코드
		
		// 가상계좌 결제 - 주문 마스터 가상계좌 정보 수정
		if(resultVo.getLGD_PAYTYPE().equals("SC0040")){	// 가상계좌일때만 실행
			flag = this.updateOrderMasterCyberAccountInfo(orderCd, "KCP", resultVo.getLGD_FINANCECODE(), resultVo.getLGD_ACCOUNTNUM(), resultVo.getLGD_PAYER(), resultVo.getLGD_CLOSEDATE());		
			
			if(flag < 1){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
				resultMap.setResult(false);
				resultMap.setMsg("가상계좌 정보 수정중 에러가 발생했습니다. 다시 결제하여 주시기 바랍니다.");
				return resultMap;
			}
		}

		// 주문 번호 반환
		String orderIdx = this.getOrderIdxByOrderCdOnly(orderCd);
		if(orderIdx == null || orderIdx.isEmpty()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			resultMap.setMsg("주문 번호가 존재하지 않습니다!");
			return resultMap;
		}

		SqlMap info = this.procSpPgCheckAfter(orderIdx);	// 주문 유효성 체크 & 주문 상태 변경

		if(info == null){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			System.out.println("결제 후 주문 유효성 확인 중 에러가 발생했습니다! : " + orderCd);
			resultMap.setResult(false);
			resultMap.setMsg("결제 후 주문 유효성 확인 중 에러가 발생했습니다! 다시 결제하여 주시기 바랍니다.");
			return resultMap;
		}else{
			if(!info.get("result").toString().equals("TRUE")){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
				System.out.println("결제 후 유효성 확인 결과 : " + orderCd + " : "+ info.get("result").toString());
				resultMap.setResult(false);
				resultMap.setMsg("결제 후 유효성 확인 결과 : " + info.get("result").toString());
				return resultMap;
			}
		}

		flag = this.insertXPayResult(resultVo);	// KCP 결제 결과 저장 (성공건만 저장됨) - LGU+테이블 같이 사용
		
		if(flag < 1){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			resultMap.setMsg("결제 결과 저장중 에러가 발생했습니다. 다시 결제하여 주시기 바랍니다.");
			return resultMap;
		}
		
		resultMap.setResult(true);
		return resultMap;
	}


   /**
    * @Method : insertKcpNotiReqVO
    * @Date: 2018.07.17
    * @Author :  강 병 철
    * @Description	: kcp 가상계좌 입금 / 에스크로 연동 로그 저장
   */
	@Override
	public void insertKcpNotiReqVO(KcpNotiReqVO vo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.clear();		
		map.put("TX_CD", vo.getTx_cd());	
		map.put("TX_TM", vo.getTx_tm());	
		map.put("SITE_CD", vo.getSite_cd());	
		map.put("TNO", vo.getTno());	
		map.put("ORDER_NO", vo.getOrder_no());	
		map.put("RESULT", vo.getResult());	
		map.put("IPGM_NAME", vo.getIpgm_name());	
		map.put("IPGM_MNYX", vo.getIpgm_mnyx());	
		map.put("TOTL_MNYX", vo.getTotl_mnyx());	
		map.put("IPGM_TIME", vo.getIpgm_time());	
		map.put("BANK_CODE", vo.getBank_code());	
		map.put("ACCOUNT", vo.getAccount());	
		map.put("NOTI_ID", vo.getNoti_id());	
		map.put("OP_CD", vo.getOp_cd());		
		map.put("REMITTER", vo.getRemitter());		
		map.put("CASH_A_NO", vo.getCash_a_no());		
		map.put("CASH_A_DT", vo.getCash_a_dt());		
		map.put("CASH_NO", vo.getCash_no());		
		map.put("REFUND_NM", vo.getRefund_nm());		
		map.put("REFUND_MNY", vo.getRefund_mny());		
		map.put("ST_CD", vo.getSt_cd());		
		map.put("CAN_MSG", vo.getCan_msg());		
		map.put("WAYBILL_NO", vo.getWaybill_no());		
		map.put("WAYBILL_CORP", vo.getWaybill_corp());		
		orderDAO.insertKcpNotiReqVO(map);	// 사은품 내역 저장
	}
	
	/**
	 * @Method : nPayOrderRegister
	 * @Date		: 2018. 06. 12
	 * @Author	:  강병철
	 * @Description	:	nPay 주문 등록
	 */
	@Override
	public String nPayOrderRegister(List<CartVO> vo, HttpServletRequest request) throws Exception {
		final String callUrl = SpringMessage.getMessage("npay.orderRegisterUrl"); //  "https://test-api.pay.naver.com/o/customer/api/order/v20/register";
		final String merchantId = SpringMessage.getMessage("npay.merchantId");  //mandomnpay 
		final String certiKey = SpringMessage.getMessage("npay.certiKey"); // 
		String domain = SpringMessage.getMessage("server.domain");
		String mdomain = SpringMessage.getMessage("server.mDomain");
		String uploadIMGPath = SpringMessage.getMessage("server.imgDomain");
		String siteDomain = "";

		String referrer = request.getHeader("referer");
		System.out.println(referrer);
		
		if (PathUtil.getCtx().toUpperCase().equals("/M")) {
			 siteDomain = mdomain;
		 }
		 else {
			 siteDomain = domain;
		 }
		
		NpayOrderVO order = new NpayOrderVO();
		order.setMerchantId(merchantId); 
		order.setCertiKey(certiKey);

		List<NpayProductVO> products = new   ArrayList<>();
		
		int i = 0;
		Integer totalPrice = 0;
		
		for (CartVO item : vo) {
			String goodsIdx = item.getGoodsIdx();
			Integer goodsCnt = item.getGoodsCnt();
			SqlMap info = productService.getProductSalesDetail(goodsIdx);
			if (info != null ) {
					
				Integer price = (int)Double.parseDouble(info.get("discountPrice").toString()); //판매가
				
				if (i == 0) {
					if (referrer.indexOf("cart") > -1) {
						order.setBackUrl(String.format("%s%s/cart.do", siteDomain, PathUtil.getCtx()));
					}
					else {
						//상품상세로 이동
						order.setBackUrl(String.format("%s%s/product/productView.do?goodsCd=%s", siteDomain, PathUtil.getCtx(),info.get("goodsCd").toString()));		
					}
				}
				
				NpayProductSingleVO single = new NpayProductSingleVO();
				single.setQuantity(goodsCnt);  // 주문개수
	
				NpayProductVO product = new NpayProductVO();
				product.setId(info.get("goodsCd").toString());
				product.setMerchantProductId(info.get("goodsCd").toString());
				product.setEcMallProductId(info.get("goodsCd").toString());
				product.setName(info.get("goodsNm").toString());
				product.setTaxType("TAX");
				product.setBasePrice(price);
				totalPrice += price*goodsCnt; //전체결제금액
				product.setInfoUrl(String.format("%s%s/product/productView.do?goodsCd=%s", siteDomain, PathUtil.getCtx(),info.get("goodsCd").toString()));
				
				String imageUrl = "";
				String imageUrlStr = info.get("mainFile").toString();
				if (!imageUrlStr.isEmpty()) {
					String[] str = imageUrlStr.split("\\.");
					if (str.length > 1) {
						imageUrl = String.format("%s/goods/%s/%s_S.%s", uploadIMGPath,info.get("goodsIdx").toString(),str[0], str[1]);
					}
				}
				product.setImageUrl(imageUrl);
	
				product.setSingle(single);
				products.add(product);
				i++;
			}
		}
		
		order.setProduct(products);
		String cpaInflowCode = "";
		String naverInflowCode = "";
		String saClickId = "";
		Cookie[] cookie = request.getCookies();
		
		if(cookie !=null){			
			for(int k=0; k<cookie.length; k++){
				if(cookie[k].getName().trim().equals("CPAValidator")){
					cpaInflowCode = cookie[k].getValue();
				}
				if(cookie[k].getName().trim().equals("NA_CO")){
					naverInflowCode = cookie[k].getValue();
				}
				if(cookie[k].getName().trim().equals("NVADID")){
					saClickId = cookie[k].getValue();
				}
			}
		}

		NpayOrderInterfaceVO naverInterface = new NpayOrderInterfaceVO();
		naverInterface.setCpaInflowCode(cpaInflowCode);
		naverInterface.setNaverInflowCode(naverInflowCode);
		naverInterface.setSaClickId(saClickId);
		
		order.setInterFace(naverInterface);
		
		//배송비계산
		for (NpayProductVO pd : order.getProduct()) {
			NpayShippingPolicyVO shipping = new NpayShippingPolicyVO();
			
			//30000원이상이면 		
			NpayShippingConditionalFree conditionFree = new NpayShippingConditionalFree();

			shipping.setGroupId("100");
			shipping.setMethod("DELIVERY");
			shipping.setFeePayType("PREPAYED"); //선불				
			shipping.setFeeType("CONDITIONAL_FREE"); //조건부무료
			shipping.setFeePrice(2500);
			
			conditionFree.setBasePrice("30000");
			shipping.setConditionalFree(conditionFree);

			/*shipping.setGroupId("000");
			shipping.setMethod("DELIVERY");
			shipping.setFeePayType("FREE"); 			
			shipping.setFeeType("FREE"); //무료
			shipping.setFeePrice(0);	*/
			
			pd.setShippingPolicy(shipping);
		}

			
        JAXBContext jaxbContext = JAXBContext.newInstance(NpayOrderVO.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(order, sw);
        String xmlString = sw.toString();

     //   String result = this.getSSLConnection(callUrl, xmlString);
		String result = this.callHttpXmlClient(callUrl, xmlString);
		return result;
	}
	
	public String callHttpXmlClient(String apiUrl, String xmlData) throws Exception{
		String accessToken = "";
		StringEntity xmlEntity = new StringEntity(xmlData, "UTF-8"); 
		System.out.println(xmlData);
	    final HttpClient client = HttpClientBuilder.create().build();

	    final HttpPost post = new HttpPost(apiUrl);
	    post.setHeader("Content-Type", "application/xml; charset=utf-8");
	   // post.setHeader("Accept-Encoding", "UTF-8");

	    BufferedReader rd = null;
	    InputStreamReader isr = null;
	    
	    try {
			post.setEntity(xmlEntity );
		     
	      final HttpResponse response = client.execute(post);

	      isr = new InputStreamReader(response.getEntity().getContent());
	      rd = new BufferedReader(isr);

	      final StringBuffer buffer = new StringBuffer();
	      String line;
	      while ((line = rd.readLine()) != null) {
	        buffer.append(line);
	      }
	      
	 	 accessToken =buffer.toString();
	        
	    } catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	    } catch (ClientProtocolException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    } finally {
	        // clear resources
	        if (rd != null) {
	            try {
	                rd.close();
	            } catch(Exception ignore) {
	            }
	        }
	        if (isr != null) {
	            try {
	                isr.close();
	            } catch(Exception ignore) {
	            }
	         }
	    }
	    System.out.println(accessToken);
		return accessToken;
	}
	

	/**
	 * @Method : nPayWishRegister
	 * @Date		: 2018. 06. 12
	 * @Author	:  강병철
	 * @Description	:	nPay 찜 등록
	 */
	@Override
	public String nPayWishRegister(String goodsIdx) throws Exception {
 		final String callUrl = SpringMessage.getMessage("npay.returnWishUrl");
		final String merchantId = SpringMessage.getMessage("npay.merchantId");  //mandomnpay 
		final String certiKey = SpringMessage.getMessage("npay.certiKey"); // 
		final String epmallPId = SpringMessage.getMessage("npay.epmallPId"); //naver EP id
		String result = "";
		String domain = SpringMessage.getMessage("server.domain");
		String uploadIMGPath = SpringMessage.getMessage("server.imgDomain");

		NpayWishVO wish = new NpayWishVO();
		wish.setSHOP_ID(merchantId);
		wish.setCERTI_KEY(certiKey);
	    wish.setEC_MALL_PID(epmallPId);
		
	//	String goodsIdx = vo.getGoodsIdx();
		
		SqlMap info = productService.getProductSalesDetail(goodsIdx);
		if (info == null ) {
			return "nodata";
		}
		else {
			wish.setITEM_ID(info.get("goodsCd").toString());
			wish.setITEM_NAME(info.get("goodsNm").toString());
			wish.setITEM_DESC(info.get("shortInfo").toString());;
			wish.setITEM_UPRICE((int)Double.parseDouble((info.get("discountPrice").toString())));

			String imageUrl = "";
			String thumbImageUrl = "";
			String imageUrlStr = info.get("mainFile").toString();
			if (!imageUrlStr.isEmpty()) {
				String[] str = imageUrlStr.split("\\.");
				if (str.length > 1) {
					imageUrl = String.format("%s/goods/%s/%s_B.%s", uploadIMGPath,info.get("goodsIdx").toString(),str[0], str[1]);
					thumbImageUrl = String.format("%s/goods/%s/%s_S.%s", uploadIMGPath,info.get("goodsIdx").toString(),str[0], str[1]);
				}
			}
			
			String goodsUrl = String.format("%s%s/product/productView.do?goodsCd=%s", domain, "/w",info.get("goodsCd").toString()); //찜URL은 웹용으로 등록
			wish.setITEM_IMAGE(imageUrl);
			wish.setITEM_THUMB(thumbImageUrl);
			wish.setITEM_URL(goodsUrl);
		}
		result = this.callHttpClient(callUrl, wish);
		return result;
	}

	public String callHttpClient(String apiUrl, NpayWishVO data ) throws Exception{
		
	 	final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
	    postParams.add(new BasicNameValuePair("SHOP_ID", data.getSHOP_ID().toString()));
	    postParams.add(new BasicNameValuePair("CERTI_KEY", data.getCERTI_KEY().toString()));
	    postParams.add(new BasicNameValuePair("EC_MALL_PID", data.getEC_MALL_PID().toString()));
	    postParams.add(new BasicNameValuePair("ITEM_ID", data.getITEM_ID().toString()));
	    postParams.add(new BasicNameValuePair("ITEM_NAME", data.getITEM_NAME().toString()));
	    postParams.add(new BasicNameValuePair("ITEM_DESC", data.getITEM_DESC().toString()));
	    postParams.add(new BasicNameValuePair("ITEM_UPRICE", data.getITEM_UPRICE().toString()));
	    postParams.add(new BasicNameValuePair("ITEM_IMAGE", data.getITEM_IMAGE().toString()));
	    postParams.add(new BasicNameValuePair("ITEM_THUMB", data.getITEM_THUMB().toString()));
	    postParams.add(new BasicNameValuePair("ITEM_URL", data.getITEM_URL().toString()));
		    
		String accessToken = "";
	    final HttpClient client = HttpClientBuilder.create().build();

	    final HttpPost post = new HttpPost(apiUrl);
	    post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	   // post.setHeader("Accept-Encoding", "UTF-8");

	    BufferedReader rd = null;
	    InputStreamReader isr = null;
	    
	    try {
	    	
          post.setEntity(new UrlEncodedFormEntity(postParams,"UTF-8"));
 
	      final HttpResponse response = client.execute(post);

	      isr = new InputStreamReader(response.getEntity().getContent());
	      rd = new BufferedReader(isr);

	      final StringBuffer buffer = new StringBuffer();
	      String line;
	      while ((line = rd.readLine()) != null) {
	        buffer.append(line);
	      }
	      
	 	 accessToken =buffer.toString();
	        
	    } catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	    } catch (ClientProtocolException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    } finally {
	        // clear resources
	        if (rd != null) {
	            try {
	                rd.close();
	            } catch(Exception ignore) {
	            }
	        }
	        if (isr != null) {
	            try {
	                isr.close();
	            } catch(Exception ignore) {
	            }
	         }
	    }
	    System.out.println(accessToken);
		return accessToken;
	}
	

	/**
	 * @Method : dbProcessAfterWonder
	 * @Date: 2018.11.02
	 * @Author :  강 병 철
	 * @Description	:	원더페이 결제 후 DB 처리 
	 * @HISTORY :
	 *
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public JsonResultVO dbProcessAfterWonder(WonderVO vo)throws Exception{
		JsonResultVO resultMap = new JsonResultVO();
		String orderCd = vo.getOrdr_idxx().toString();
		String orderIdx = this.getOrderIdxByOrderCdOnly(orderCd);
		if(orderIdx == null || orderIdx.isEmpty()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			resultMap.setMsg("주문 번호가 존재하지 않습니다!");
			return resultMap;
		}
		SqlMap info = this.procSpPgCheckAfter(orderIdx);	// 주문 유효성 체크 & 주문 상태 변경

		if(info == null){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			System.out.println("결제 후 주문 유효성 확인 중 에러가 발생했습니다! : " + orderCd);
			resultMap.setResult(false);
			resultMap.setMsg("결제 후 주문 유효성 확인 중 에러가 발생했습니다! 다시 결제하여 주시기 바랍니다.");
			return resultMap;
		}else{
			if(!info.get("result").toString().equals("TRUE")){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
				System.out.println("결제 후 유효성 확인 결과 : " + orderCd + " : "+ info.get("result").toString());
				resultMap.setResult(false);
				resultMap.setMsg("결제 후 유효성 확인 결과 : " + info.get("result").toString());
				return resultMap;
			}
		}
		
		int flag = this.saveWonderPayResult(vo);									//원더 페이 결제 결과 저장
		if(flag <1){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			resultMap.setMsg("결제 결과 저장중 에러가 발생했습니다. 다시 결제하여 주시기 바랍니다.");
			return resultMap;
		}	
		resultMap.setResult(true);
			
		return resultMap;
	}

	/**
	 * @Method : saveWonderPayResult
	 * @Date: 2018.11.02
	 * @Author :  강 병 철
	 * @Description	:	원더페이 처리 결과 저장 (결제요청)
	 * @HISTORY :
	 *
	 */
	public int saveWonderPayResult(WonderVO resultVO)throws Exception{
		int flag = 0;
		HashMap<String, Object> map = new HashMap<>();
		  
		map.put("ORDERCD", resultVO.getOrdr_idxx());
		map.put("ORDERIDX", resultVO.getOrder_idx());
		map.put("RESCD", resultVO.getRes_cd());			
		map.put("REQMSG", resultVO.getRes_msg());			
		map.put("GOODMNY", resultVO.getGood_mny());			
		map.put("TID", resultVO.getTno());			
		map.put("SITECD", resultVO.getSite_cd());			
		map.put("APPTIME", resultVO.getApp_time());			
		map.put("PAYMETHOD", resultVO.getPay_method());			
		map.put("PGCODE", resultVO.getPg_code());			
		map.put("BKMNY", resultVO.getBk_mny());
		map.put("BANKCD", resultVO.getBank_cd());			
		map.put("CASHRECEIPTFLAG", resultVO.getCash_receipt_flag());			
		map.put("CARDMNY", resultVO.getCard_mny());			
		map.put("CARDCD", resultVO.getCard_cd());			
		map.put("QUOTA", resultVO.getQuota());			
		map.put("NOINF", resultVO.getNoinf());						
		map.put("COUPONMNY", resultVO.getCoupon_mny());			
		
		flag = orderDAO.saveWonderPayResult(map);
			
		return flag;
	}
	
				
}
