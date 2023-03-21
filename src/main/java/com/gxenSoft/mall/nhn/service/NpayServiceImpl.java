package com.gxenSoft.mall.nhn.service;

import java.security.Security;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gxenSoft.mall.nhn.dao.NpayDAO;
import com.gxenSoft.mall.nhn.vo.NhnChangedProductOrderInfoVO;
import com.gxenSoft.mall.nhn.vo.NhnGetProductOrderInfoListVO;
import com.gxenSoft.message.SpringMessage;
import com.nhncorp.platform.checkout.MallServiceStub;
import com.nhncorp.platform.checkout.base.AccessCredentialsType;
import com.nhncorp.platform.checkout.base.Address;
import com.nhncorp.platform.checkout.base.CancelInfo;
import com.nhncorp.platform.checkout.base.ChangedProductOrderInfo;
import com.nhncorp.platform.checkout.base.Delivery;
import com.nhncorp.platform.checkout.base.ExchangeInfo;
import com.nhncorp.platform.checkout.base.Order;
import com.nhncorp.platform.checkout.base.ProductOrder;
import com.nhncorp.platform.checkout.base.ProductOrderChangeType;
import com.nhncorp.platform.checkout.base.ProductOrderInfo;
import com.nhncorp.platform.checkout.base.ReturnInfo;
import com.nhncorp.platform.checkout.mall.GetChangedProductOrderListRequest;
import com.nhncorp.platform.checkout.mall.GetChangedProductOrderListRequestE;
import com.nhncorp.platform.checkout.mall.GetChangedProductOrderListResponse;
import com.nhncorp.platform.checkout.mall.GetChangedProductOrderListResponseE;
import com.nhncorp.platform.checkout.mall.GetProductOrderInfoListRequest;
import com.nhncorp.platform.checkout.mall.GetProductOrderInfoListRequestE;
import com.nhncorp.platform.checkout.mall.GetProductOrderInfoListResponse;
import com.nhncorp.platform.checkout.mall.GetProductOrderInfoListResponseE;
import com.nhncorp.psinfra.toolkit.SimpleCryptLib;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : NpayServiceImpl
    * PACKAGE NM : com.gxenSoft.mall.nhn.service
    * AUTHOR	 : 강 병 철
    * CREATED DATE  : 2018. 7. 10. 
    * HISTORY :   
    *
    *************************************
    */
@Service("NpayService")
public class NpayServiceImpl implements NpayService {
	
	static final Logger logger = LoggerFactory.getLogger(NpayServiceImpl.class);
	
	private static final String providerName = "Checkout";
	private static final String serviceName = "MallService5";
	//private static final String accessLicense = "010001000046fc0ccbe564fb686a5b291a5fd1721d9956526897789a98a72b1f7983e2584d"; //message-setting에서 불러오면 에러남. //accessLicense입력, PDF파일참조
	//private static final String secretKey = "AQABAADPzW0Z4VW1jrWIcszrE/TfGLCbmf1WEfoHSpoEtcnioA==";  //message-setting에서 불러오면 에러남., PDF파일참조
	
//	final String certiKey = SpringMessage.getMessage("npay.certiKey"); // 

	
	private static String timeStamp;
	//private static String signature;

	
	@Autowired
	private NpayDAO npayDAO;


	/**
	    * @Method : generateSignature
	    * @Date: 2018. 07.10
	    * @Author : 강 병 철
	    * @Description	:	서명 생성
	   */
	public String generateSignature(String operationName)  {
		final String secretKey = SpringMessage.getMessage("npay.api.secretKey");  //"AQABAADPzW0Z4VW1jrWIcszrE/TfGLCbmf1WEfoHSpoEtcnioA=="; //secretKey입력, PDF파일참조
		System.out.println(secretKey);
		timeStamp = SimpleCryptLib.getTimestamp();
		String data = timeStamp + serviceName + operationName;
		System.out.println(data);
		String signature = "";
		try {
			signature = SimpleCryptLib.generateSign(data, secretKey);
			
		} catch (SignatureException e) {
			//서명정보 실패
			System.out.println(e.getMessage());
		}
		return signature;
	}	
	
	/**
	    * @Method : GetChangedProductOrderList
	       * @Date: 2018. 07.10
	    * @Author : 강 병 철
	    * @Description	:	변경 상품 주문 내역을 조회
	    * callDate 호출하는 날자 YYYYMMDD 형태
	   */
	public String GetChangedProductOrderList(ProductOrderChangeType OrderStatus, String callDate) throws Exception {
		final String mallID = SpringMessage.getMessage("npay.api.mallID");  //"mandomnpay";
		//private static final String operationName = "GetChangedProductOrderList";

		final String accessLicense = SpringMessage.getMessage("npay.api.accessLicense"); //"010001000046fc0ccbe564fb686a5b291a5fd1721d9956526897789a98a72b1f7983e2584d"; //accessLicense입력, PDF파일참조
		System.out.println(accessLicense);
		Security.addProvider(new BouncyCastleProvider());
		AccessCredentialsType accessCredentialsType = new AccessCredentialsType();
		GetChangedProductOrderListRequestE requestE = new GetChangedProductOrderListRequestE();
		GetChangedProductOrderListRequest req = new GetChangedProductOrderListRequest();
		MallServiceStub stub = new MallServiceStub();
		//서명생성
		String signature = this.generateSignature("GetChangedProductOrderList");
		//인증정보
		accessCredentialsType.setAccessLicense(accessLicense);
		accessCredentialsType.setSignature(signature);
		accessCredentialsType.setTimestamp(timeStamp);
		req.setAccessCredentials(accessCredentialsType);


		//GetChangedProductOrderList 파라메터 설정
		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();
		Calendar cal = Calendar.getInstance();
		 
		//현재 년도, 월, 일
		int year = cal.get ( Calendar.YEAR );
		int month = cal.get ( Calendar.MONTH )  ;
		int day = cal.get ( Calendar.DATE ) ;
	  // 포맷변경 ( 년월일 시분초)
	  SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	  String  today = sdformat.format(cal.getTime());  
	  //System.out.println("to : " + String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day));
	  
		//cal.add(Calendar.DATE, -2);  //하루전
	  
		 today = sdformat.format(cal.getTime());  
		int year2 = cal.get ( Calendar.YEAR );
		int month2 = cal.get ( Calendar.MONTH )  ;
		int day2 = cal.get ( Calendar.DATE ) ;
	  
	  if (!(callDate == null || callDate == "")) {
		  year2 = Integer.parseInt(callDate.substring(0, 4));
		  month2 = Integer.parseInt(callDate.substring(4, 6));
		  month2= month2-1;
		  day2 = Integer.parseInt(callDate.substring(6, 8));
	  }

        System.out.println("from : " + String.valueOf(year2)+"-"+String.valueOf(month2+1)+"-"+String.valueOf(day2));
		  
		from.set(year2, month2, day2, 0, 0, 0);  //최대24시간, 7월6일 데이터 전부 조회   2018, 7-1, 2, 0, 0, 0
//		to.set(year, month, day, 0, 0, 0);  //최대24시간, 7월6일 데이터 전부 조회   2018, 7-1, 2, 0, 0, 0
	//    to.set(2018, 7-1, 2, 24, 0, 0);
		req.setDetailLevel("Full");
		req.setVersion("5.0");
		req.setRequestID("");
		req.setMallID(mallID);
		req.setInquiryTimeFrom(from);
	//	req.setInquiryTimeTo(to);  //생략시 24시간
		req.setLastChangedStatusCode(OrderStatus); //결재완료
		
		requestE.setGetChangedProductOrderListRequest(req);
		System.out.println("   ***************** GetChangedProductOrderList 통신 ****************************   ");
		//GetChangedProductOrderList Response 수신
		GetChangedProductOrderListResponseE responseE = stub.getChangedProductOrderList(requestE);
		GetChangedProductOrderListResponse orderListRes= responseE.getGetChangedProductOrderListResponse();
		//결과 출력
		if("SUCCESS".equals(orderListRes.getResponseType())) {
			System.out.println("   ***************** 결과 : "+orderListRes.getResponseType()+"****************************   ");
			//응답메시지 처리 getxxx
			try {
				System.out.println(orderListRes.getRequestID());
				System.out.println("OrderStatus : " +OrderStatus.getValue());
				System.out.println("data cnt : " +orderListRes.getReturnedDataCount());
				System.out.println(orderListRes.getChangedProductOrderInfoList());
				if (orderListRes.getChangedProductOrderInfoList() != null) {
					this.SaveChangedProductOrderList(OrderStatus, orderListRes);
				}
				return "TRUE";
			} catch (Exception e) {
				return "FALSE";
			}
		} else {
			System.out.println("   ***************** 결과 : "+orderListRes.getResponseType()+"****************************   ");
			System.out.println("Code : " + orderListRes.getError().getCode());
			System.out.println("Message : " + orderListRes.getError().getMessage());
			System.out.println("Detail : " + orderListRes.getError().getDetail());
			return "FALSE";
		}
	}

	/**
	    * @throws SignatureException 
	 * @Method : 3.2.1	GetProductOrderInfoList 
	       * @Date: 2018. 07.10
	    * @Author : 강 병 철
	    * @Description	:	변경 상품 주문 내역을 조회
	   */
	public NhnGetProductOrderInfoListVO GetProductOrderInfoList (List<String> orderProductList) throws Exception {
		final String secretKey = SpringMessage.getMessage("npay.api.secretKey");  
		System.out.println(secretKey);
		final String accessLicense = SpringMessage.getMessage("npay.api.accessLicense");
		System.out.println(accessLicense);
		NhnGetProductOrderInfoListVO result = new NhnGetProductOrderInfoListVO();
		Security.addProvider(new BouncyCastleProvider());
		AccessCredentialsType accessCredentialsType = new AccessCredentialsType();
		GetProductOrderInfoListRequestE requestE = new GetProductOrderInfoListRequestE();
		GetProductOrderInfoListRequest req = new GetProductOrderInfoListRequest();
		MallServiceStub stub = new MallServiceStub();
		//서명생성
		String signature = this.generateSignature("GetProductOrderInfoList");
		//인증정보
		accessCredentialsType.setAccessLicense(accessLicense);
		accessCredentialsType.setSignature(signature);
		accessCredentialsType.setTimestamp(timeStamp);
		req.setAccessCredentials(accessCredentialsType);
		//파라메터 설정
		req.setDetailLevel("Full");
		req.setVersion("5.0");
		req.setRequestID("");

		System.out.println("signature : "+signature);
		System.out.println("timeStamp : "+timeStamp);
		
		req.setProductOrderIDList(orderProductList.toArray(new String[0]));
		
		requestE.setGetProductOrderInfoListRequest(req);
		System.out.println("   ***************** GetProductOrderInfoList 통신 ****************************   ");
		
		//GetChangedProductOrderList Response 수신
		GetProductOrderInfoListResponseE responseE = stub.getProductOrderInfoList(requestE);
		GetProductOrderInfoListResponse orderListRes= responseE.getGetProductOrderInfoListResponse();

		System.out.println("orderListRes.getResponseType() : "+orderListRes.getResponseType());
		//결과 출력
		if("SUCCESS".equals(orderListRes.getResponseType())) {
			System.out.println(orderListRes.getRequestID());
			System.out.println(orderListRes.getReturnedDataCount());
			System.out.println(orderListRes.getProductOrderInfoList());

			byte[] encryptKey = SimpleCryptLib.generateKey(timeStamp, secretKey);
			result.setOrderRes(orderListRes);
			result.setEncryptKey(encryptKey);
		} else {
			System.out.println("Code : " + orderListRes.getError().getCode());
			System.out.println("Message : " + orderListRes.getError().getMessage());
			System.out.println("Detail : " + orderListRes.getError().getDetail());
		}
		
		return result;
	}
	
	
	/**
    * @Method : SaveChangedProductOrderList
    * @Date: 2018. 07.10
    * @Author : 강 병 철
    * @Description	:	변경 상품 주문 내역을 저장
   */

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public void SaveChangedProductOrderList(ProductOrderChangeType OrderStatus, GetChangedProductOrderListResponse orderListRes) throws Exception {
		
		List<NhnChangedProductOrderInfoVO> orderInfoList = new ArrayList<>();
		
		//changed product 정보를 주문번호, 주문상품번호리스트 형태로 넣는다.
		for ( ChangedProductOrderInfo item : orderListRes.getChangedProductOrderInfoList()) {
			NhnChangedProductOrderInfoVO findvo= orderInfoList.stream().filter(o -> o.getOrderID().equals(item.getOrderID())).findFirst().orElse(null);
			if (findvo == null) {
				NhnChangedProductOrderInfoVO orderInfo = new NhnChangedProductOrderInfoVO();
				orderInfo.setOrderID(item.getOrderID());
				if (orderInfo.getProductOrderIDList() == null) {
					orderInfo.setProductOrderIDList(new ArrayList<String>());
				}
				orderInfo.getProductOrderIDList().add(item.getProductOrderID());
				orderInfoList.add(orderInfo);
			}
			else {
				//findvo.getProductOrderIDList().add(item.getProductOrderID());
				orderInfoList.stream().filter(o -> o.getOrderID().equals(item.getOrderID())).findFirst().orElse(null).getProductOrderIDList().add(item.getProductOrderID());
			}
		}
		
		//주문정보별 저장
		for (NhnChangedProductOrderInfoVO  item : orderInfoList) {
			NhnGetProductOrderInfoListVO orderRes = this.GetProductOrderInfoList(item.getProductOrderIDList()); // ******* 상품정보 api 호출
			ProductOrderInfo[]  productorderInfos = orderRes.getOrderRes().getProductOrderInfoList();
			int cnt = 0;
			for (ProductOrderInfo  productorderInfo : productorderInfos) {
				if (cnt == 0) {
					this.insertNpayOrder(productorderInfo.getOrder(), orderRes.getEncryptKey()); //주문정보
					this.insertNpayAddress(productorderInfo.getOrder().getOrderID(), productorderInfo.getProductOrder().getShippingAddress(), orderRes.getEncryptKey()); //주소 저장
				}
				
				this.insertNpayProductOrder(item.getOrderID(),productorderInfo.getProductOrder()); //상품정보저장
				
				if (productorderInfo.getDelivery() != null) {
					this.insertNpayProductDelivery(item.getOrderID(), productorderInfo.getProductOrder().getProductOrderID(), productorderInfo.getDelivery()); //배송정보저장
				}
				
				if (productorderInfo.getCancelInfo() != null) {
					this.insertNpayCancelInfo(item.getOrderID(), productorderInfo.getProductOrder().getProductOrderID(), productorderInfo.getCancelInfo()); //취소정보저장
				}
				
				if (productorderInfo.getReturnInfo() != null) {
					this.insertNpayReturnInfo(item.getOrderID(), productorderInfo.getProductOrder().getProductOrderID(), productorderInfo.getReturnInfo()); //반품정보저장
				}
				
				if (productorderInfo.getExchangeInfo() != null) {
					this.insertNpayExchangeInfo(item.getOrderID(), productorderInfo.getProductOrder().getProductOrderID(), productorderInfo.getExchangeInfo()); //교환정보저장
				}
				cnt++;
			}
		}
			
		//npay 테이블에서 몰주문테이블로 옮기기
		this.npayOrderProcess(OrderStatus,  orderInfoList );
	}
	
	/**
    * @Method : insertNpayOrder
    * @Date: 2018. 07.10
    * @Author : 강 병 철
    * @Description	:	주문 내역을 저장
   */
	public void insertNpayOrder(Order order, byte[]  encryptKey) throws Exception {
		Security.addProvider(new BouncyCastleProvider()); 
		HashMap<String, Object> map = new HashMap<>();
		
		String ordererId = new String(SimpleCryptLib.decrypt(encryptKey, order.getOrdererID()), "UTF-8");
		String ordererNm = new String(SimpleCryptLib.decrypt(encryptKey, order.getOrdererName()), "UTF-8");
		String ordererTel1 = new String(SimpleCryptLib.decrypt(encryptKey, order.getOrdererTel1()), "UTF-8");
		Date orderDate = order.getOrderDate().getTime(); 	
	   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   
		map.clear();
		map.put("ORDER_ID", order.getOrderID());
		map.put("ORDER_DATE",  ( order.getOrderDate() == null)? "" : dateFormat.format(orderDate));		
		map.put("ORDERER_ID", ordererId);
		map.put("ORDERER_NAME", ordererNm);  
		map.put("ORDERER_TEL1", ordererTel1);
		
		map.put("PAYMENT_DATE",  ( order.getPaymentDate() == null)? "" : dateFormat.format(order.getPaymentDate().getTime()));
		map.put("PAYMENT_MEANS", order.getPaymentMeans().toString());	
		map.put("PAYMENT_DUE_DATE", ( order.getPaymentDueDate() == null)? "" : dateFormat.format(order.getPaymentDueDate().getTime()) );
		map.put("PAYMENT_NUMBER", order.getPaymentNumber());
	
		map.put("ORDER_DISCOUNT_AMOUNT", order.getOrderDiscountAmount());
		map.put("GENERAL_PAYMENT_AMOUNT", order.getGeneralPaymentAmount());
		map.put("NAVER_MILEAGE_PAYMENT_AMOUNT", order.getNaverMileagePaymentAmount());
		map.put("CHARGE_AMOUNT_PAYMENT_AMOUNT", order.getChargeAmountPaymentAmount());
		map.put("CHECKOUT_ACCUMULATION_PAYMENT_AMOUNT", order.getCheckoutAccumulationPaymentAmount());
		map.put("IS_DELIVERY_MEMO_PARTICULAR_INPUT", order.getIsDeliveryMemoParticularInput());
		map.put("ORDER_TYPE",  order.getOrderType());
		map.put("PAY_LOCATION_TYPE", order.getPayLocationType());
		map.put("PAYMENT_CORE_TYPE", order.getPaymentCoreType());
		
		if (npayDAO.cntNpayOrder(map)  == 0) {
			npayDAO.insertNpayOrder(map);
		}
		else {
			npayDAO.updateNpayOrder(map);
		}
	}
	
	/**
    * @Method : insertNpayProductOrder
    * @Date: 2018. 07.10
    * @Author : 강 병 철
    * @Description	:	주문 상품 내역을 저장
   */
	public void insertNpayProductOrder(String OrderID, ProductOrder product) throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		map.clear();
		map.put("ORDER_ID", OrderID);
		map.put("PRODUCT_ORDER_ID", product.getProductOrderID());
		map.put("PRODUCT_INFO_API_VERSION", product.getProductInfoApiVersion().toString() );
		map.put("PRODUCT_ORDER_STATUS", product.getProductOrderStatus().toString() );
		map.put("CLAIM_TYPE", ( product.getClaimType() == null)? "" :product.getClaimType().toString() );
		map.put("CLAIM_STATUS", ( product.getClaimStatus() == null)? "" :product.getClaimStatus().toString() );
		map.put("PRODUCT_ID", product.getProductID() );
		map.put("PRODUCT_NAME", product.getProductName() );
		map.put("PRODUCT_CLASS", ( product.getProductClass() == null)? "" :product.getProductClass() );
		map.put("PRODUCT_OPTION", ( product.getProductOption() == null)? "" :product.getProductOption() );
		map.put("OPTION_MANAGE_CODE", ( product.getOptionManageCode() == null)? "" :product.getOptionManageCode() );
		map.put("QUANTITY", product.getQuantity() );
		map.put("UNIT_PRICE", product.getUnitPrice() );
		map.put("OPTION_PRICE", product.getOptionPrice() );
		map.put("OPTION_CODE", ( product.getOptionCode() == null)? "" :product.getOptionCode() );
		map.put("TOTAL_PRODUCT_AMOUNT", product.getTotalProductAmount() );
		map.put("PRODUCT_DISCOUNT_AMOUNT", product.getProductDiscountAmount() );
		map.put("FIRST_SELLER_BURDEN_DISCOUNT_AMOUNT", product.getFirstSellerBurdenDiscountAmount() );
		map.put("TOTAL_PAYMENT_AMOUNT", product.getTotalPaymentAmount() );
		map.put("SELLING_CODE", product.getSellingCode() );
		map.put("SELLER_PRODUCT_CODE", product.getSellerProductCode() );
		map.put("MALL_MANAGE_CODE", product.getMallManageCode() );
		map.put("ORDER_EXTRA_DATA", ( product.getOrderExtraData() == null)? "" :product.getOrderExtraData() );
		map.put("MALL_ID", product.getMallID() );
		map.put("EXPECTED_DELIVERY_METHOD",( product.getExpectedDeliveryMethod() == null)? "" : product.getExpectedDeliveryMethod().toString() );
		map.put("PACKAGE_NUMBER", ( product.getPackageNumber() == null)? "" :  product.getPackageNumber() );
		map.put("SHIPPING_FEE_TYPE", product.getShippingFeeType() );
		map.put("DELIVERY_POLICY_TYPE", ( product.getDeliveryPolicyType() == null)? "" : product.getDeliveryPolicyType() );
		map.put("DELIVERY_FEE_AMOUNT", product.getDeliveryFeeAmount() );
		map.put("SECTION_DELIVERY_FEE", product.getSectionDeliveryFee() );
		map.put("DELIVERY_DISCOUNT_AMOUNT", product.getDeliveryDiscountAmount() );
		map.put("SHIPPING_MEMO", ( product.getShippingMemo() == null)? "" :product.getShippingMemo() );
		map.put("SHIPPING_DUE_DATE",  ( product.getShippingDueDate() == null)? "" : dateFormat.format(product.getShippingDueDate().getTime()) );
		map.put("PLACE_ORDER_DATE",  ( product.getPlaceOrderDate() == null)? "" : dateFormat.format(product.getPlaceOrderDate().getTime()) );
		map.put("PLACE_ORDER_RELEASE_DATE",  ( product.getPlaceOrderReleaseDate() == null)? "" : dateFormat.format(product.getPlaceOrderReleaseDate().getTime()) );
		map.put("DECISION_DATE",  ( product.getDecisionDate() == null)? "" : dateFormat.format(product.getDecisionDate().getTime()) );
		map.put("FREE_GIFT", ( product.getFreeGift() == null)? "" : product.getFreeGift() );
		map.put("MALL_MEMBER_ID", ( product.getMallMemberID() == null)? "" : product.getMallMemberID() );
		map.put("PLACE_ORDER_STATUS",  ( product.getPlaceOrderStatus() == null)? "" : product.getPlaceOrderStatus().toString() );
		map.put("DELAYED_DISPATCH_REASON", ( product.getDelayedDispatchReason() == null)? "" :  product.getDelayedDispatchReason().toString() );
		map.put("DELAYED_DISPATCH_DETAILED_REASON",  ( product.getDelayedDispatchDetailedReason() == null)? "" : product.getDelayedDispatchDetailedReason() );
		map.put("SELLER_BURDEN_DISCOUNT_AMOUNT",   product.getSellerBurdenDiscountAmount() );
		map.put("PAYMENT_COMMISSION",   product.getPaymentCommission() );
		map.put("EXPECTED_SETTLEMENT_AMOUNT",   product.getExpectedSettlementAmount() );

		
		if (npayDAO.cntNpayOrderProduct(map)  == 0) {
			npayDAO.insertNpayProductOrder(map);
		}
		else {
			npayDAO.updateNpayProductOrder(map);
		}
	}
	

	/**
    * @Method : insertNpayAddress
    * @Date: 2018. 07.10
    * @Author : 강 병 철
    * @Description	:	주문 주소 저장
   */
	public void insertNpayAddress(String OrderID, Address vo, byte[]  encryptKey) throws Exception {
		Security.addProvider(new BouncyCastleProvider()); 
		HashMap<String, Object> map = new HashMap<>();
		map.put("ORDER_ID", OrderID);
		
		if (npayDAO.cntNpayAddress(map)  == 0) {
			String baseAddress = (vo.getBaseAddress() == null ) ? "" : new String(SimpleCryptLib.decrypt(encryptKey, vo.getBaseAddress()), "UTF-8");
			String detailAddress = ( vo.getDetailedAddress() == null) ? "" :  new String(SimpleCryptLib.decrypt(encryptKey, vo.getDetailedAddress() ), "UTF-8");
			String tel1 =  ( vo.getTel1() == null) ? "" :  new String(SimpleCryptLib.decrypt(encryptKey, vo.getTel1() ), "UTF-8");
			String tel2 =  ( vo.getTel2() == null) ? "" :  new String(SimpleCryptLib.decrypt(encryptKey, vo.getTel2() ), "UTF-8");
			String name =  ( vo.getName() == null) ? "" :  new String(SimpleCryptLib.decrypt(encryptKey, vo.getName() ), "UTF-8");
			
			map.clear();
			map.put("ORDER_ID", OrderID);
			map.put("ADDRESS_TYPE", vo.getAddressType().toString());
			map.put("ZIP_CODE", vo.getZipCode());
			map.put("BASE_ADDRESS", baseAddress);
			map.put("DETAILED_ADDRESS",  detailAddress );
			map.put("CITY", vo.getCity());
			map.put("STATE", vo.getState());
			map.put("COUNTRY", vo.getCountry() );
			map.put("TEL1", tel1);
			map.put("TEL2", tel2 );
			map.put("NAME", name );

			npayDAO.insertNpayAddress(map);
		}
	}

	/**
    * @Method : insertNpayProductDelivery
    * @Date: 2018. 07.10
    * @Author : 강 병 철
    * @Description	:	주문 상품별 배송정보
   */
	public void insertNpayProductDelivery(String OrderID,  String ProductOrderID, Delivery vo) throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		map.clear();
		map.put("ORDER_ID", OrderID);
		map.put("PRODUCT_ORDER_ID", ProductOrderID);
		map.put("DELIVERY_STATUS",  ( vo.getDeliveryStatus() == null)? "" : vo.getDeliveryStatus());
		map.put("DELIVERY_METHOD",  ( vo.getDeliveryMethod() == null)? "" : vo.getDeliveryMethod().toString() );        
		map.put("DELIVERY_COMPANY",  ( vo.getDeliveryCompany() == null)? "" : vo.getDeliveryCompany());
		map.put("TRACKING_NUMBER",  ( vo.getTrackingNumber() == null)? "" :  vo.getTrackingNumber());
		map.put("SEND_DATE", ( vo.getSendDate() == null)? "" : dateFormat.format(vo.getSendDate().getTime()) );     
		map.put("PICKUP_DATE", ( vo.getPickupDate() == null)? "" : dateFormat.format(vo.getPickupDate().getTime()) );     
		map.put("DELIVERED_DATE",( vo.getDeliveredDate() == null)? "" : dateFormat.format(vo.getPickupDate().getTime()) );    
		map.put("IS_WRONG_TRACKING_NUMBER",vo.getIsWrongTrackingNumber() );    
		map.put("WRONG_TRACKING_NUMBER_REGISTERED_DATE", ( vo.getWrongTrackingNumberRegisteredDate() == null)? "" : dateFormat.format(vo.getWrongTrackingNumberRegisteredDate().getTime()) );  
		map.put("WRONG_TRACKING_NUMBER_TYPE",  ( vo.getWrongTrackingNumberType() == null)? "" : vo.getWrongTrackingNumberType() );        

		if (npayDAO.cntNpayProductDelivery(map)  == 0) {		
			npayDAO.insertNpayProductDelivery(map);
		}
		else {
			npayDAO.updateNpayProductDelivery(map);
		}
	}
	
	/**
    * @Method : insertNpayCancelInfo
    * @Date: 2018. 07.10
    * @Author : 강 병 철
    * @Description	:	주문 상품별 취소 정보
   */
	public void insertNpayCancelInfo(String OrderID,  String ProductOrderID, CancelInfo vo) throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		map.clear();
		map.put("ORDER_ID", OrderID);
		map.put("PRODUCT_ORDER_ID", ProductOrderID);
		map.put("CLAIM_STATUS",  ( vo.getClaimStatus() == null)? "" : vo.getClaimStatus().toString());
		map.put("CLAIM_REQUEST_DATE",  ( vo.getClaimRequestDate() == null)? "" :dateFormat.format(vo.getClaimRequestDate().getTime()) );     
		map.put("REQUEST_CHANNEL",  ( vo.getRequestChannel() == null)? "" : vo.getRequestChannel());
		map.put("CANCEL_REASON",  ( vo.getCancelReason() == null)? "" :  vo.getCancelReason().toString());
		map.put("CANCEL_DETAILED_REASON", ( vo.getCancelDetailedReason() == null)? "" : vo.getCancelDetailedReason());     
		map.put("CANCEL_COMPLETED_DATE", ( vo.getCancelCompletedDate() == null)? "" : dateFormat.format(vo.getCancelCompletedDate().getTime()) );     
		map.put("CANCEL_APPROVAL_DATE",( vo.getCancelApprovalDate() == null)? "" : dateFormat.format(vo.getCancelApprovalDate().getTime()) );    
		map.put("HOLDBACK_STATUS",   ( vo.getHoldbackStatus() == null)? "" : vo.getHoldbackStatus().toString());   
		map.put("HOLDBACK_REASON", ( vo.getHoldbackReason() == null)? "" : vo.getHoldbackReason().toString());   ;  
		map.put("HOLDBACK_DETAILED_REASON",  ( vo.getHoldbackDetailedReason() == null)? "" : vo.getHoldbackDetailedReason());
		map.put("ETC_FEE_DEMAND_AMOUNT",  vo.getEtcFeeDemandAmount());
		map.put("ETC_FEE_PAY_METHOD",  ( vo.getEtcFeePayMethod() == null)? "" : vo.getEtcFeePayMethod());
		map.put("ETC_FEE_PAY_MEANS",  ( vo.getEtcFeePayMeans() == null)? "" : vo.getEtcFeePayMeans());
		map.put("REFUND_EXPECTED_DATE",  ( vo.getRefundExpectedDate() == null) ? ""  : dateFormat.format(vo.getRefundExpectedDate().getTime()) );     
		map.put("REFUND_STANDBY_STATUS",  ( vo.getRefundStandbyStatus() == null)? "" : vo.getRefundStandbyStatus());
		map.put("REFUND_STANDBY_REASON",  ( vo.getRefundStandbyReason() == null)? "" : vo.getRefundStandbyReason());
		map.put("REFUND_REQUEST_DATE",  ( vo.getRefundRequestDate() == null)? "" : dateFormat.format(vo.getRefundRequestDate().getTime()) );    
		

		if (npayDAO.cntNpayCancelInfo(map)  == 0) {		
			npayDAO.insertNpayCancelInfo(map);
		}
		else {
			npayDAO.updateNpayCancelInfo(map);
		}
	}
	
	/**
    * @Method : insertNpayCancelInfo
    * @Date: 2018. 07.10
    * @Author : 강 병 철
    * @Description	:	주문 상품별 반품 정보
   */
	public void insertNpayReturnInfo(String OrderID,  String ProductOrderID, ReturnInfo vo) throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		map.clear();
		map.put("ORDER_ID", OrderID);
		map.put("PRODUCT_ORDER_ID", ProductOrderID);
		map.put("CLAIM_STATUS",  ( vo.getClaimStatus() == null)? "" : vo.getClaimStatus().toString());
		map.put("CLAIM_REQUEST_DATE",  ( vo.getClaimRequestDate() == null)? "" : dateFormat.format(vo.getClaimRequestDate().getTime()) );     
		map.put("REQUEST_CHANNEL",  ( vo.getRequestChannel() == null)? "" : vo.getRequestChannel());
		map.put("RETURN_REASON",  ( vo.getReturnReason() == null)? "" :  vo.getReturnReason().toString());
		map.put("RETURN_DETAILED_REASON", ( vo.getReturnDetailedReason() == null)? "" : vo.getReturnDetailedReason());     

		map.put("HOLDBACK_STATUS",   ( vo.getHoldbackStatus() == null)? "" : vo.getHoldbackStatus().toString());   
		map.put("HOLDBACK_REASON", ( vo.getHoldbackReason() == null)? "" : vo.getHoldbackReason().toString());   
		map.put("HOLDBACK_DETAILED_REASON",  ( vo.getHoldbackDetailedReason() == null)? "" : vo.getHoldbackDetailedReason());
		map.put("HOLDBACK_CONFIG_DATE",  ( vo.getHoldbackConfigDate() == null)? "": dateFormat.format(vo.getHoldbackConfigDate().getTime()) );     
		map.put("HOLDBACK_CONFIGURER",  ( vo.getHoldbackConfigurer() == null)? "" : vo.getHoldbackConfigurer());
		map.put("HOLDBACK_RELEASE_DATE",  ( vo.getHoldbackReleaseDate() == null)? "" : dateFormat.format(vo.getHoldbackReleaseDate().getTime()) );     
		map.put("HOLDBACK_RELEASER",  ( vo.getHoldbackReleaser() == null)? "" : vo.getHoldbackReleaser());
		
		map.put("COLLECT_STATUS",  ( vo.getCollectStatus() == null)? "" : vo.getCollectStatus());
		map.put("COLLECT_DELIVERY_METHOD",  ( vo.getCollectDeliveryMethod() == null)? "" : vo.getCollectDeliveryMethod().toString());
		map.put("COLLECT_DELIVERY_COMPANY",  ( vo.getCollectDeliveryCompany() == null)? "" : vo.getCollectDeliveryCompany());
		map.put("COLLECT_TRACKING_NUMBER",  ( vo.getCollectTrackingNumber() == null)? "" : vo.getCollectTrackingNumber());
		map.put("COLLECT_COMPLETED_DATE", ( vo.getCollectCompletedDate() == null)? "" : dateFormat.format(vo.getCollectCompletedDate().getTime()) );     
		
		map.put("CLAIM_DELIVERY_FEE_DEMAND_AMOUNT", vo.getClaimDeliveryFeeDemandAmount());
		map.put("CLAIM_DELIVERY_FEE_PAY_METHOD",  ( vo.getClaimDeliveryFeePayMethod() == null)? "" : vo.getClaimDeliveryFeePayMethod());
		map.put("CLAIM_DELIVERY_FEE_PAY_MEANS",  ( vo.getClaimDeliveryFeePayMeans() == null)? "" : vo.getClaimDeliveryFeePayMeans());
		
		map.put("ETC_FEE_DEMAND_AMOUNT",  vo.getEtcFeeDemandAmount());
		map.put("ETC_FEE_PAY_METHOD",  ( vo.getEtcFeePayMethod() == null)? "" : vo.getEtcFeePayMethod());
		map.put("ETC_FEE_PAY_MEANS",  ( vo.getEtcFeePayMeans() == null)? "" : vo.getEtcFeePayMeans());

		map.put("REFUND_EXPECTED_DATE",  ( vo.getRefundExpectedDate() == null) ? ""  : dateFormat.format(vo.getRefundExpectedDate().getTime()) );     
		map.put("REFUND_STANDBY_STATUS",  ( vo.getRefundStandbyStatus() == null)? "" : vo.getRefundStandbyStatus());
		map.put("REFUND_STANDBY_REASON",  ( vo.getRefundStandbyReason() == null)? "" : vo.getRefundStandbyReason());
		map.put("REFUND_REQUEST_DATE",  ( vo.getRefundRequestDate() == null)? "" : dateFormat.format(vo.getRefundRequestDate().getTime()) );    
		map.put("RETURN_COMPLETED_DATE", ( vo.getReturnCompletedDate() == null)? "" : dateFormat.format(vo.getReturnCompletedDate().getTime()) );     
		
		map.put("CLAIM_DELIVERY_FEE_DISCOUNT_AMOUNT", vo.getClaimDeliveryFeeDiscountAmount());
		map.put("ETC_FEE_DISCOUNT_AMOUNT", vo.getEtcFeeDiscountAmount());

		if (npayDAO.cntNpayReturnInfo(map)  == 0) {		
			npayDAO.insertNpayReturnInfo(map);
		}
		else {
			npayDAO.updateNpayReturnInfo(map);
		}
	}
	
	/**
    * @Method : insertNpayExchangeInfo
    * @Date: 2018. 07.10
    * @Author : 강 병 철
    * @Description	:	주문 상품별 교환 정보
   */
	public	void insertNpayExchangeInfo(String OrderID,  String ProductOrderID, ExchangeInfo vo) throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		map.clear();
		map.put("ORDER_ID", OrderID);
		map.put("PRODUCT_ORDER_ID", ProductOrderID);
		map.put("CLAIM_STATUS",  ( vo.getClaimStatus() == null)? "" : vo.getClaimStatus().toString());
		map.put("CLAIM_REQUEST_DATE",  ( vo.getClaimRequestDate() == null)? "" :dateFormat.format(vo.getClaimRequestDate().getTime()) );     
		map.put("REQUEST_CHANNEL",  ( vo.getRequestChannel() == null)? "" : vo.getRequestChannel());
		map.put("EXCHANGE_REASON",  ( vo.getExchangeReason() == null)? "" :  vo.getExchangeReason().toString());
		map.put("EXCHANGE_DETAILED_REASON", ( vo.getExchangeDetailedReason() == null)? "" : vo.getExchangeDetailedReason());     
	
		map.put("HOLDBACK_STATUS",   ( vo.getHoldbackStatus() == null)? "" : vo.getHoldbackStatus().toString());   
		map.put("HOLDBACK_REASON", ( vo.getHoldbackReason() == null)? "" : vo.getHoldbackReason().toString());   
		map.put("HOLDBACK_DETAILED_REASON",  ( vo.getHoldbackDetailedReason() == null)? "" : vo.getHoldbackDetailedReason());
		map.put("HOLDBACK_CONFIG_DATE",  ( vo.getHoldbackConfigDate() == null)? "": dateFormat.format(vo.getHoldbackConfigDate().getTime()) );     
		map.put("HOLDBACK_CONFIGURER",  ( vo.getHoldbackConfigurer() == null)? "" : vo.getHoldbackConfigurer());
		map.put("HOLDBACK_RELEASE_DATE",  ( vo.getHoldbackReleaseDate() == null)? "" : dateFormat.format(vo.getHoldbackReleaseDate().getTime()) );     
		map.put("HOLDBACK_RELEASER",  ( vo.getHoldbackReleaser() == null)? "" : vo.getHoldbackReleaser());

		map.put("COLLECT_STATUS",  ( vo.getCollectStatus() == null)? "" : vo.getCollectStatus());
		map.put("COLLECT_DELIVERY_METHOD",  ( vo.getCollectDeliveryMethod() == null)? "" : vo.getCollectDeliveryMethod().toString());
		map.put("COLLECT_DELIVERY_COMPANY",  ( vo.getCollectDeliveryCompany() == null)? "" : vo.getCollectDeliveryCompany());
		map.put("COLLECT_TRACKING_NUMBER",  ( vo.getCollectTrackingNumber() == null)? "" : vo.getCollectTrackingNumber());
		map.put("COLLECT_COMPLETED_DATE", ( vo.getCollectCompletedDate() == null)? "" : dateFormat.format(vo.getCollectCompletedDate().getTime()) );     
		
		map.put("RE_DELIVERY_STATUS",  ( vo.getReDeliveryStatus() == null)? "" : vo.getReDeliveryStatus());
		map.put("RE_DELIVERY_METHOD",  ( vo.getReDeliveryMethod() == null)? "" : vo.getReDeliveryMethod().toString());
		map.put("RE_DELIVERY_COMPANY",  ( vo.getReDeliveryCompany() == null)? "" : vo.getReDeliveryCompany());
		map.put("RE_DELIVERY_TRACKING_NUMBER",  ( vo.getReDeliveryTrackingNumber() == null)? "" : vo.getReDeliveryTrackingNumber());
		map.put("RE_DELIVERY_OPERATION_DATE", ( vo.getReDeliveryOperationDate() == null)? "" : dateFormat.format(vo.getReDeliveryOperationDate().getTime()) );     
		
		map.put("CLAIM_DELIVERY_FEE_DEMAND_AMOUNT", vo.getClaimDeliveryFeeDemandAmount());
		map.put("CLAIM_DELIVERY_FEE_PRODUCT_ORDER_IDS",  ( vo.getClaimDeliveryFeeProductOrderIds() == null)? "" : vo.getClaimDeliveryFeeProductOrderIds());
		map.put("CLAIM_DELIVERY_FEE_PAY_METHOD",  ( vo.getClaimDeliveryFeePayMethod() == null)? "" : vo.getClaimDeliveryFeePayMethod());
		map.put("CLAIM_DELIVERY_FEE_PAY_MEANS",  ( vo.getClaimDeliveryFeePayMeans() == null)? "" : vo.getClaimDeliveryFeePayMeans());
		
		map.put("ETC_FEE_DEMAND_AMOUNT",  vo.getEtcFeeDemandAmount());
		map.put("ETC_FEE_PAY_METHOD",  ( vo.getEtcFeePayMethod() == null)? "" : vo.getEtcFeePayMethod());
		map.put("ETC_FEE_PAY_MEANS",  ( vo.getEtcFeePayMeans() == null)? "" : vo.getEtcFeePayMeans());
		map.put("CLAIM_DELIVERY_FEE_DISCOUNT_AMOUNT", vo.getClaimDeliveryFeeDiscountAmount());

		if (npayDAO.cntNpayExchangeInfo(map)  == 0) {		
			npayDAO.insertNpayExchangeInfo(map);
		}
		else {
			npayDAO.updateNpayExchangeInfo(map);
		}
	}
	
	/**
	    * @Method : npayOrderProcess
	    * @Date: 2018. 10. 18
	    * @Author : 강 병 철
	    * @Description	:	주문테이블로 이동
	    * CANCEL_REQUESTED 취소 요청,	RETURN_REQUESTED 반품 요청,	EXCHANGE_REQUESTED 교환 요청,	EXCHANGE_REDELIVERY_READY 교환 재배송 준비, 	HOLDBACK_REQUESTED 구매 확정 보류 요청,	CANCELED 취소, 	RETURNED 반품, 	EXCHANGED 교환
	 */
	public void npayOrderProcess(ProductOrderChangeType OrderStatus, List<NhnChangedProductOrderInfoVO> orderInfoList ) throws Exception{
		String orderStatus = OrderStatus.getValue().toUpperCase();
        		
		this.npayOrderToMallOrder(orderInfoList);
		
		
		switch (orderStatus) {
		case "PAY_WAITING":   //입금처리
			break;
		case "PAYED":    //결제완료
			break;
		case "DISPATCHED":    //발송처리
			break;
		case "CANCEL_REQUESTED":
			this.npayOrderToClaim(OrderStatus, orderInfoList);
			break;
		case "RETURN_REQUESTED":
			this.npayOrderToClaim(OrderStatus, orderInfoList);
			break;
		case "EXCHANGE_REQUESTED":
			this.npayOrderToClaim(OrderStatus, orderInfoList);
			break;
		case "HOLDBACK_REQUESTED":
			break;	
		case "CANCELED":
			this.npayOrderToClaim(OrderStatus, orderInfoList);
			break;	
		case "RETURNED":
			this.npayOrderToClaim(OrderStatus, orderInfoList);
			break;	
		case "EXCHANGE":
			this.npayOrderToClaim(OrderStatus, orderInfoList);
			break;	
		}
	}
	
	/**
	    * @Method : npayOrderToMallOrder
	    * @Date: 2018. 10. 18
	    * @Author : 강 병 철
	    * @Description	:	주문테이블로 이동
	 */
	public void npayOrderToMallOrder(List<NhnChangedProductOrderInfoVO> orderInfoList ) throws Exception {		
		
		HashMap<String, Object> map = new HashMap<String, Object>();

		for (NhnChangedProductOrderInfoVO  item : orderInfoList) {
			String orderid = item.getOrderID();
			map.put("ORDER_ID", orderid);
			npayDAO.callSpNpayOrderPayed(map);
		}
	}
	/**
	    * @Method : npayOrderToClaim
	    * @Date: 2018. 10. 18
	    * @Author : 강 병 철
	    * @Description	:	npay 클레임처리
	 */
	public void npayOrderToClaim(ProductOrderChangeType OrderStatus, List<NhnChangedProductOrderInfoVO> orderInfoList ) throws Exception{
		HashMap<String, Object> map = new HashMap<String, Object>();
		for (NhnChangedProductOrderInfoVO  item : orderInfoList) {
			String orderid = item.getOrderID();
			map.put("CLAIM_TYPE", OrderStatus.getValue().toUpperCase());
			map.put("ORDER_ID", orderid);
			npayDAO.callSpNpayClaim(map);
		}
	}
	
	
}
