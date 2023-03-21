package com.gxenSoft.mall.lgdacom.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.gxenSoft.mall.common.web.CommonMethod;
import com.gxenSoft.mall.lgdacom.vo.PaycoApprovalVO;
import com.gxenSoft.mall.lgdacom.vo.PaycoCompletlVO;
import com.gxenSoft.mall.lgdacom.vo.PaycoPaymentVO;
import com.gxenSoft.mall.lgdacom.vo.PaycoReturnVO;
import com.gxenSoft.mall.lgdacom.vo.PaycoSettingVO;
import com.gxenSoft.mall.order.service.OrderService;
import com.gxenSoft.method.MethodUtil;
import com.gxenSoft.util.pathUtil.PathUtil;
import com.gxenSoft.util.pg.payco.util.PaycoUtil;


/**
 *************************************
 * PROJECT   : PaycoController
 * PROGRAM ID  : LgdacomController
 * PACKAGE NM : com.gxenSoft.mall.lgdacom.web
 * AUTHOR	 : 강 병 철
 * CREATED DATE  : 2017. 7. 4. 
 * HISTORY :   
 *
 *************************************
 */	
@Controller
public class PaycoController extends CommonMethod {

	@Autowired
	private OrderService orderService;

	/**
    * @Method : IsMobile
    * @Date: 2017. 7. 4.
    * @Author :   강 병 철
    * @Description	:	모바일여부 체크
   */
	public String IsMobile(HttpServletRequest request) {
		String isMobile;
		String userAgent = request.getHeader("user-agent");
		boolean mobile1 = userAgent.matches(".*(iPhone|iPod|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mni|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson).*");
		boolean mobile2 = userAgent.matches(".*(LG|SAMSUNG|Samsung).*");
		if(mobile1||mobile2){
			isMobile = "true";
		}else{
			isMobile = "false";
		}
		return isMobile;
	}
	
	/**
    * @Method : index
    * @Date: 2017. 7. 4.
    * @Author :   강 병 철
    * @Description	:	payco 테스트
   */
	@RequestMapping(value="/payco/index") 
	public String index( HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
		PaycoSettingVO setting = new PaycoSettingVO();
		model.addAttribute("IsMobile",IsMobile(request));
		model.addAttribute("settingVo", setting);
		return PathUtil.getCtx()+"/payco/index";
	}
	
	/**-----------------------------------------------------------------------
	 * 구매예약처리 페이지
	 *------------------------------------------------------------------------
	 * @Method paycoReserveAjax
	 * @author : 강병철
	 * @since
	 * @version
	 * @Description 
	 * 가맹점에서 전달한 파라미터를 받아 JSON 형태로 페이코API 와 통신한다.
	 */
	@RequestMapping(value = "/ajax/payco/paycoReserveAjax", method = RequestMethod.POST)
	public @ResponseBody void paycoReserveAjax(String customerOrderNumber, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
		PaycoSettingVO setting = new PaycoSettingVO();
		PaycoUtil 	 util 	= new PaycoUtil(setting.getServerType()); //CommonUtil

		ObjectMapper mapper = new ObjectMapper(); 		  //jackson json object
		/*======== 상품정보 변수 선언 ========*/
		int orderNumber, orderQuantity, productUnitPrice, productUnitPaymentPrice, productAmt; 
		int productPaymentAmt, totalProductPaymentAmt, sortOrdering;
		int totalTaxfreeAmt, totalTaxableAmt, totalVatAmt, unitTaxfreeAmt, unitTaxableAmt, unitVatAmt;
		String iOption;
		String productName;
		String orderConfirmUrl;
		String orderConfirmMobileUrl;
		String productImageUrl;
		String sellerOrderProductReferenceKey;
		String taxationType;
		/*=====================================*/
		
		/*======== 변수 초기화 ========*/
		totalProductPaymentAmt = 0;	//주문 상품이 여러개일 경우 상품들의 총 금액을 저장할 변수
		orderNumber 	= 0;			//주문 상품이 여러개일 경우 순번을 매길 변수
		unitTaxfreeAmt  = 0;		//개별상품단위 면세금액
		unitTaxableAmt  = 0;		//개별상품단위 공급가액
		unitVatAmt      = 0;			//개별상품단위 부가세액
		totalTaxfreeAmt = 0;		//총 면세 금액
		totalTaxableAmt = 0;		//총 과세 공급가액
		totalVatAmt 	= 0;			//총 과세 부가세액
		/*=============================*/
		
		/*==== 상품정보 값 입력 ====*/
		orderNumber = orderNumber + 1; 										 // 상품에 순번을 정하기 위해 값을 증가합니다.
		orderQuantity = 1;													 //[필수]주문수량 (배송비 상품인 경우 1로 세팅)
		productUnitPrice = 79000;											 //상품 단가 ( 테스트용으로써 79000원으로 설정)
		productUnitPaymentPrice = 79000;					 		 		 //상품 결제 단가 ( 테스트용으로써 79000원으로 설정)
		productAmt = productUnitPrice * orderQuantity;						 //[필수]상품 결제금액(상품단가 * 수량)
		productPaymentAmt = productUnitPaymentPrice * orderQuantity + 2500;	 //[필수]상품 결제금액(상품결제단가 * 수량, 배송비 설정시 상품가격에 포함시킴 ex) 2500원)
		iOption = "";											 		 //[선택]옵션(최대 100 자리)
		sortOrdering = orderNumber;											 //[필수]상품노출순서, 10자 이내
		productName = "[GLOBE] TILT KIDS (NAVY/ORANGE)";					 //[필수]상품명, 4000자 이내
		orderConfirmUrl = "";							 					 //[선택]주문완료 후 주문상품을 확인할 수 있는 url, 4000자 이내
		orderConfirmMobileUrl = "";					 						 //[선택]주문완료 후 주문상품을 확인할 수 있는 모바일 url, 1000자 이내
		productImageUrl = "";												 //[선택]이미지URL(배송비 상품이 아닌 경우는 필수), 4000자 이내, productImageUrl에 적힌 이미지를 썸네일해서 PAYCO 주문창에 보여줍니다.
		sellerOrderProductReferenceKey = "ITEM_100001";						 //[필수]가맹점에서 관리하는 상품키, 100자 이내.(외부가맹점에서 관리하는 주문상품 연동 키(sellerOrderProductReferenceKey)는 주문 별로 고유한 key이어야 합니다.)
																			 // 단 주문당 1건에 대한 상품을 보내는 경우는 연동키가 1개이므로 주문별 고유값을 고려하실 필요 없습니다.
		taxationType = "TAXATION";											 //[선택]과세타입(기본값 : 과세). DUTYFREE :면세, COMBINE : 결합상품, TAXATION : 과세
		
		
		//totalTaxfreeAmt(면세상품 총액) / totalTaxableAmt(과세상품 총액) / totalVatAmt(부가세 총액) => 일부 필요한 가맹점을위한 예제입니다.
		//과세상품일 경우
		if(taxationType.equals("TAXATION")){ 
			unitTaxfreeAmt = 0;
			unitTaxableAmt = Math.round((float)(productPaymentAmt / 1.1));
			unitVatAmt	   = Math.round((float)((productPaymentAmt / 1.1) * 0.1));
			
			if(unitTaxableAmt + unitVatAmt != productPaymentAmt){
				unitTaxableAmt = productPaymentAmt - unitVatAmt;
			}
		//면세상품일 경우
		}else if(taxationType.equals("DUTYFREE")){
			unitTaxfreeAmt = productPaymentAmt;
			unitTaxableAmt = 0;
			unitVatAmt	   = 0;
		//복합상품일 경우
		}else if(taxationType.equals("COMBINE")){
			unitTaxfreeAmt = 1000;
			unitTaxableAmt = Math.round((float)((productPaymentAmt - unitTaxfreeAmt) / 1.1));
			unitVatAmt	   = Math.round((float)(((productPaymentAmt - unitTaxfreeAmt) / 1.1) * 0.1));
			
			if(unitTaxableAmt + unitVatAmt != productPaymentAmt - unitTaxfreeAmt){
				unitTaxableAmt = (productPaymentAmt - unitTaxfreeAmt) - unitVatAmt;
			}
				
		}
		
		totalTaxfreeAmt = totalTaxfreeAmt + unitTaxfreeAmt;
		totalTaxableAmt = totalTaxableAmt + unitTaxableAmt;
		totalVatAmt		= totalVatAmt + unitVatAmt;
		
		//주문정보를 구성하기 위한 상품들 누적 결제 금액(상품결제금액)  
		totalProductPaymentAmt = totalProductPaymentAmt + productPaymentAmt; // 주문상품 총 금액
		
		
		//상품값으로 읽은 변수들로 Json String 을 작성합니다.
		List<Map<String,Object>> orderProducts = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> productInfo = new HashMap<String,Object>(); 
		productInfo.put("cpId", setting.getCpId());														//[필수]상점ID
		productInfo.put("productId", setting.getProductId());											//[필수]상품ID
		productInfo.put("productAmt", productAmt);											//[필수]상품금액(상품단가 * 수량)
		productInfo.put("productPaymentAmt", productPaymentAmt);							//[필수]상품결제금액(상품결제단가 * 수량)
		productInfo.put("orderQuantity", orderQuantity);									//[필수]주문수량(배송비 상품인 경우 1로 셋팅)
		productInfo.put("option", iOption);													//[선택]상품 옵션
		productInfo.put("sortOrdering", sortOrdering);										//[필수]상품 노출순서
		productInfo.put("productName", productName);										//[필수]상품명
		productInfo.put("orderConfirmUrl", orderConfirmUrl);								//[선택]주문완료 후 주문상품을 확인할 수 있는 URL
		productInfo.put("orderConfirmMobileUrl", orderConfirmMobileUrl); 					//[선택]주문완료 후 주문상품을 확인할 수 있는 모바일 URL
		productInfo.put("productImageUrl", productImageUrl);								//[선택]이미지 URL(배송비 상품이 아닌 경우는 필수)
		productInfo.put("sellerOrderProductReferenceKey", sellerOrderProductReferenceKey);	//[필수]외부가맹점에서 관리하는 주문상품 연동 키
		productInfo.put("taxationType", taxationType);										//[선택]과세타입(면세상품 : DUTYFREE, 과세상품 : TAXATION (기본), 결합상품 : COMBINE)
		orderProducts.add(productInfo);
		/*=====================================================================================================*/	
				
		/*======== 주문정보 변수 선언 ========*/
		int totalOrderAmt, totalPaymentAmt; 
		String sellerOrderReferenceKey;
		String sellerOrderReferenceKeyType;
		String iCurrency;
		String orderSheetUiType;
		String orderTitle;
		String orderMethod;
		String returnUrl;
		String returnUrlParam;
		String nonBankbookDepositInformUrl;
		String orderChannel;
		String inAppYn;
		String individualCustomNoInputYn;
		String payMode;
		/*=====================================*/
				
		/*==== 주문정보 값 입력(가맹점 수정 가능 부분) ========================================================*/		
		sellerOrderReferenceKey 	= customerOrderNumber;						 //[필수]외부가맹점의 주문번호
		sellerOrderReferenceKeyType = "UNIQUE_KEY";								 //[선택]외부가맹점의 주문번호 타입(UNIQUE_KEY : 기본값, DUPLICATE_KEY : 중복가능한 키->외부가맹점의 주문번호가 중복 가능한 경우 사용)
		iCurrency 					= "KRW";									 //[선택]통화(default=KRW)
		totalPaymentAmt 			= totalProductPaymentAmt;			 		 //[필수]총 결재 할 금액
		orderTitle 					= "payco 결제 테스트  (JSP)444";			 	 //[선택]주문 타이틀
		returnUrl 					= setting.getPaycoReturnUrl(); 		 	 //[선택]주문완료 후 Redirect 되는 Url
		//[선택]주문완료 후 Redirect 되는 URL과 함께 전달되어야 하는 파라미터(Json 형태의 String)
		returnUrlParam 				= "{\"taxationType\":\""+taxationType+"\",\"totalTaxfreeAmt\":\""+totalTaxfreeAmt+"\",\"totalTaxableAmt\":\""+totalTaxableAmt+"\",\"totalVatAmt\":\""+totalVatAmt+"\"}";
		nonBankbookDepositInformUrl = setting.getPaycoBankReturnUrl();  //[선택]무통장입금완료통보 URL
		orderMethod					= "EASYPAY_F";								 //[필수]주문유형   =  간편구매형:CHECKOUT, 간편결제형+회원결제:EASYPAY_F, 간편결제형+비회원:EASYPAY
		orderChannel 				= "PC";										 //[선택]주문채널 (default : PC/MOBILE)
		inAppYn 					= "N";										 //[선택]인앱결제 여부(Y/N) (default = N)
		individualCustomNoInputYn 	= "N";										 //[선택]개인통관고유번호 입력 여부 (Y/N) (default = N)
		orderSheetUiType 			= "GRAY";									 //[선택]주문서 UI 타입 선택(선택 가능값 : RED/GRAY)		
		payMode						= "PAY2";									 //[선택]결제모드(PAY1 : 결제인증,승인통합 / PAY2 : 결제인증,승인분리)
																				 // payMode는 선택값이나 값을 넘기지 않으면 DEFALUT 값은 PAY1 으로 셋팅되어있습니다.
		
		//설정한 주문정보로 Json String 을 작성합니다.		
		HashMap<String,Object> orderInfo = new HashMap<String,Object>();
		orderInfo.put("sellerKey", setting.getSellerKey());										//[필수]가맹점 코드
		orderInfo.put("sellerOrderReferenceKey", sellerOrderReferenceKey); 			//[필수]외부가맹점 주문번호
		orderInfo.put("sellerOrderReferenceKeyType", sellerOrderReferenceKeyType);  //[선택]외부가맹점의 주문번호 타입
		orderInfo.put("currency", iCurrency);										//[선택]통화
		orderInfo.put("totalPaymentAmt", totalPaymentAmt);							//[필수]총 결제금액(면세금액,과세금액,부가세의 합) totalTaxfreeAmt + totalTaxableAmt + totalVatAmt
		orderInfo.put("totalTaxfreeAmt", totalTaxfreeAmt);							//[선택]면세금액(면세상품의 공급가액 합)
		orderInfo.put("totalTaxableAmt", totalTaxableAmt);							//[선택]과세금액(과세상품의 공급가액 합)
		orderInfo.put("totalVatAmt", totalVatAmt);									//[선택]부가세(과세상품의 부가세액 합)
		orderInfo.put("orderTitle", orderTitle); 									//[선택]주문 타이틀
		orderInfo.put("returnUrl", returnUrl);										//[선택]주문완료 후 Redirect 되는 URL
		orderInfo.put("returnUrlParam", returnUrlParam);							//[선택]주문완료 후 Redirect 되는 URL과 함께 전달되어야 하는 파라미터(Json 형태의 String)
		orderInfo.put("nonBankbookDepositInformUrl", nonBankbookDepositInformUrl); 	//[선택]무통장입금완료 통보 URL
		orderInfo.put("orderMethod", orderMethod);									//[필수]주문유형
		orderInfo.put("orderChannel", orderChannel);								//[선택]주문채널
		orderInfo.put("inAppYn", inAppYn);											//[선택]인앱결제 여부
		orderInfo.put("individualCustomNoInputYn", individualCustomNoInputYn);		//[선택]개인통관 고유번호 입력 여부
		orderInfo.put("orderSheetUiType", orderSheetUiType);						//[선택]주문서 UI타입 선택
		orderInfo.put("payMode", payMode);											//[선택]결제모드(PAY1 : 결제인증,승인통합 / PAY2 : 결제인증,승인분리) DEFAULT : PAY1
		orderInfo.put("orderProducts", orderProducts); 								//[필수]주문상품 리스트
		
		
		/* 부가정보(extraData) - Json 형태의 String */
		Map<String,Object> extraData = new HashMap<String,Object>(); 
		//해당주문예약건 만료처리일자(해당일 이후에는 결제불가)
		//extraData.put("payExpiryYmdt", "20161231180000");							//[선택]해당주문예약건 만료처리일자(14자리로 맞춰주세요)
																					//가맹점 상황에따라 필요한경우가 아니라면 해당 파라미터는 삭제하여 주세요.
		//가상계좌만료일시(YYYYMMDD or YYYYMMDDHHmmss형태로 넣는다)
		//extraData.put("virtualAccountExpiryYmd", "20161231180000");				//[선택]가상계좌만료일시(Default 3일, YYYYMMDD일경우 그날 24시까지)
																					//가맹점 에서 무통장입금을 사용하지 않으시면 해당 파라미터는 삭제하여 주세요.
		//appUrl
		//extraData.put("appUrl", "testapp://open");								//[IOS필수]IOS 인앱 결제시 ISP 모바일 등의 앱에서 결제를 처리한 뒤 복귀할 앱 URL
																					//AOS(안드로이드) 에서는 필수사항이 아니므로 삭제하여 주세요.
		
		//모바일 결제페이지에서 취소 버튼 클릭시 이동할 URL (결제창 이전 URL 등) 
		//1순위 : 주문예약 > extraData > cancelMobileUrl 값이 있을시 => cancelMobileUrl 이동
		//2순위 : 주문예약시 전달받은 returnUrl 이동 + 실패코드(오류코드:2222)
		//3순위 : 가맹점 URL로 이동(가맹점등록시 받은 사이트URL)
		//4순위 : 이전 페이지로 이동 => history.Back();
		//extraData.put("cancelMobileUrl", "http://www.payco.com");					//[선택][모바일 일경우 필수]모바일 결제페이지에서 취소 버튼 클릭시 이동할 URL
																					
		Map<String,Object> viewOptions = new HashMap<String,Object>();
		viewOptions.put("showMobileTopGnbYn", "Y");									//[선택]모바일 상단 GNB 노출여부RKAODWJA 
		viewOptions.put("iframeYn", "Y");													//[선택]Iframe 호출(모바일에서 접근하는경우 iframe 사용시 이값을 "Y"로 보내주셔야 합니다.)
																										// Iframe 사용시 연동가이드 내용중 [Iframe 적용가이드]를 참고하시길 바랍니다.
		extraData.put("viewOptions", viewOptions);									//[선택]화면 UI 옵션
																					
		orderInfo.put("extraData",  mapper.writeValueAsString(extraData).toString().replaceAll("\"", "\\\""));	//[선택]부가정보 - Json 형태의 String
			
		//주문예약 API 호출 함수
		String result = util.payco_reserve(orderInfo,setting.getLogYn());
		
		JsonNode node = mapper.readTree(result);
		String reserveOrderNo = "";
		String orderSheetUrl = "";
		System.out.println(result);
		if(node.path("code").toString().equals("0")){
			reserveOrderNo 	= node.path("result").get("reserveOrderNo").textValue();
			orderSheetUrl 		= node.path("result").get("orderSheetUrl").textValue();
			orderInfo.put("reserveOrderNo", reserveOrderNo); 	
			orderInfo.put("orderSheetUrl", orderSheetUrl);

			orderService.insertPaycoReserve(orderInfo);
			
		}

		
		try {
		    PrintWriter pw;
		    pw = response.getWriter();
		    response.setContentType("application/json; charset=utf-8");
		    pw.print(result);
		    pw.flush();
		    pw.close();
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	  /**
	    * @Method : paycoReturn
	    * @Date: 2017. 7. 4.
	    * @Author :   강 병 철
	    * @Description	:	lg u+ 결제요청
	   */
	@RequestMapping(value="/payco/paycoReturn") 
	public String paycoReturn(PaycoReturnVO vo,  HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
	
	    model.addAttribute("reqMap", vo);
	    model.addAttribute("HiddenParams" , ObjectToHiddenParams(vo));
		return PathUtil.getCtx()+"/payco/paycoReturn";
	}
	
	
	@RequestMapping(value = "/payco/paycoRes")
	public void paycoRes(PaycoReturnVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
		PaycoSettingVO setting = new PaycoSettingVO();
		ObjectMapper mapper = new ObjectMapper(); 		  //jackson json object
		PaycoUtil 	 util 	= new PaycoUtil(setting.getServerType()); //Co
		
		PaycoCompletlVO completvo = new PaycoCompletlVO();
		PaycoApprovalVO approvalvo = new PaycoApprovalVO();
		
		Boolean doCancel    = false;						// 승인 후 오류발생시 결제취소 여부
		Boolean doApproval  = true; 					  	// 요청금액과 결제 금액 일치여부(true : 일치)
		
		/* 인증 데이타 변수선언 */
		String reserveOrderNo 	   	   = vo.getReserveOrderNo();				//주문예약번호
		String sellerOrderReferenceKey = vo.getSellerOrderReferenceKey();	//가맹점주문연동키
		String paymentCertifyToken 	   = vo.getPaymentCertifyToken();		//결제인증토큰(결제승인시필요)
		
		int totalPaymentAmt = 0;
		if(vo.getTotalPaymentAmt() == null || vo.getTotalPaymentAmt() == ""){								//총 결제금액
			totalPaymentAmt = 0;
		}else{
			totalPaymentAmt = (int)Float.parseFloat(vo.getTotalPaymentAmt().toString()); 
		}
		
		String code      	   	           = vo.getCode();						//결과코드(성공 : 0)
		String message				   = vo.getMessage();					//결과 메시지
		
		/* 주문예약시 전달한 returnUrlParam */
		///	String taxationType = vo.getTaxationType();
		///	String taxfreeAmt 	= vo.getTotalTaxfreeAmt();
		///	String taxableAmt 	= vo.getTotalTaxableAmt();
		///	String vatAmt 	  	= vo.getTotalVatAmt();
		
		/* 결제 인증 성공시 */
		if(code.equals("0"))
		{ 
			/* 수신된 데이터 중 필요한 정보를 추출하여
			 * 총 결제금액과 요청금액이 일치하는지 확인하고,	
			 * 결제요청 상품의 재고파악을 실행하여 
			 * PAYCO 결제 승인 API 호출 여부를 판단한다.
			 */
			/*----------------------------------------------------------------
			.. 가맹점 처리 부분
			..
			-----------------------------------------------------------------*/
			
		  /*★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
			★★★★★★★★★★                                                      ★★★★★★★★★
			★★★★★★★★★★                    중요 사항                         ★★★★★★★★★
			★★★★★★★★★★                                                      ★★★★★★★★★
			★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
			
			 총 금액 결제된 금액을 주문금액과 비교.(반드시 필요한 검증 부분.)
			 주문금액을 변조하여 결제를 시도 했는지 확인함.(반드시 DB에서 읽어야 함.)
			 금액이 변조되었으면 반드시 승인을 취소해야 함.
			 
			★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★*/
			int myDBtotalValue = 81500;						// 가맹점 DB에서 읽은 주문요청 금액
			if(myDBtotalValue != totalPaymentAmt){		// 주문 요청금액과 인증정보로 넘어온 결제금액 비교
				doApproval = false;
				message   = "주문금액과 결제금액이 틀립니다.";
			}
			
			/* 주문금액과 결제금액이 일치한다고 가정(doApproval = true) */
			if(doApproval){
				HashMap<String,Object> sendMap = new HashMap<String,Object>(); 
				sendMap.put("sellerKey", setting.getSellerKey());
				sendMap.put("reserveOrderNo", reserveOrderNo);
				sendMap.put("sellerOrderReferenceKey", sellerOrderReferenceKey);
				sendMap.put("paymentCertifyToken", paymentCertifyToken);
				sendMap.put("totalPaymentAmt", totalPaymentAmt);
				
				/* payco 결제승인 API 호출 */
				String result = util.payco_approval(sendMap,setting.getLogYn());
				
				System.out.println("********************PaycoRes (payco_approval) 결과***************************************");
				System.out.println(result);
				System.out.println("*************************************************************************************");
				
				// jackson Tree 이용
				JsonNode node = mapper.readTree(result);
				if(node.path("code").toString().equals("0")){
					// 예시
					try{
						/* 결제승인 후 리턴된 데이터 중 필요한 정보를 추출하여
						 * 가맹점 에서 필요한 작업을 실시합니다.(예 주문서 작성 등..)
						 * 결제연동시 리턴되는 PAYCO주문번호(orderNo)와 주문인증키(orderCertifyKey)에 대해 
						 * 가맹점 DB 저장이 필요합니다.
						 */
						 
						// 승인 후 전달받은 데이터 저장 변수 
						 Integer orderIdx = 300; ///주문번호
						 
						String memberName, memberEmail, orderChannel, paymentCompletionYn, paymentCompleteYmdt, orderProducts;
						
						Double dtotalPaymentAmt, dtotalOrderAmt, dtotalDeliveryFeeAmt, dtotalRemoteAreaDeliveryFeeAmt 
							,dreceiptPaycoPointAmt, dreceiptPaycoPointServiceAmt,dreceiptPaycoPointTaxfreeAmt
							,dreceiptPaycoPointTaxableAmt	,dreceiptPaycoPointVatAmt;
						
						// 주문완료 페이지 전달용 데이터 변수 
						String  orderNo, orderCertifyKey, totalCancelPossibleAmt, requestMemo, cancelTotalAmt, 
								cancelDetailContent, productAmt, cancelPaymentAmount;
								
						// 결제승인 후 리턴된 데이터 중 필요한 정보를 추출 예시
						orderNo 			  			= node.path("result").get("orderNo").textValue();
						orderCertifyKey			= node.path("result").get("orderCertifyKey").textValue();
						
						reserveOrderNo			= node.path("result").get("reserveOrderNo").textValue();
						sellerOrderReferenceKey = node.path("result").get("sellerOrderReferenceKey").textValue();
						
						
						if (node.path("result").get("totalPaymentAmt") == null ) {
							totalPaymentAmt			= 0;
							dtotalPaymentAmt = 0.0;
						} else {
							totalPaymentAmt			= (int)Float.parseFloat(node.path("result").get("totalPaymentAmt").toString());
							dtotalPaymentAmt 	=  Double.valueOf(node.path("result").get("totalPaymentAmt").toString());
						}
						
						if (node.path("result").get("totalOrderAmt") == null ) {
							dtotalOrderAmt = 0.0;
						} else {
							dtotalOrderAmt 	=  Double.valueOf(node.path("result").get("totalOrderAmt").toString());
						}

						if (node.path("result").get("totalDeliveryFeeAmt") == null ) {
							dtotalDeliveryFeeAmt = 0.0;
						} else {
							dtotalDeliveryFeeAmt 	=  Double.valueOf(node.path("result").get("totalDeliveryFeeAmt").toString());
						}

						if (node.path("result").get("totalRemoteAreaDeliveryFeeAmt") == null ) {
							dtotalRemoteAreaDeliveryFeeAmt = 0.0;
						} else {
							dtotalRemoteAreaDeliveryFeeAmt 	=  Double.valueOf(node.path("result").get("totalRemoteAreaDeliveryFeeAmt").toString());
						}

						if (node.path("result").get("receiptPaycoPointAmt") == null ) {
							dreceiptPaycoPointAmt = 0.0;
						} else {
							dreceiptPaycoPointAmt 	=  Double.valueOf(node.path("result").get("receiptPaycoPointAmt").toString());
						}
						
						if (node.path("result").get("receiptPaycoPointServiceAmt") == null ) {
							dreceiptPaycoPointServiceAmt = 0.0;
						} else {
							dreceiptPaycoPointServiceAmt 	=  Double.valueOf(node.path("result").get("receiptPaycoPointServiceAmt").toString());
						}
						if (node.path("result").get("receiptPaycoPointTaxfreeAmt") == null ) {
							dreceiptPaycoPointTaxfreeAmt = 0.0;
						} else {
							dreceiptPaycoPointTaxfreeAmt 	=  Double.valueOf(node.path("result").get("receiptPaycoPointTaxfreeAmt").toString());
						}
						if (node.path("result").get("receiptPaycoPointTaxableAmt") == null) {
							dreceiptPaycoPointTaxableAmt = 0.0;
						} else {
							dreceiptPaycoPointTaxableAmt 	=  Double.valueOf(node.path("result").get("receiptPaycoPointTaxableAmt").toString());
						}
						if (node.path("result").get("receiptPaycoPointVatAmt") == null) {
							dreceiptPaycoPointVatAmt = 0.0;
						} else {
							dreceiptPaycoPointVatAmt 	=  Double.valueOf(node.path("result").get("receiptPaycoPointVatAmt").toString());
						}
						
						memberName = node.path("result").get("memberName").textValue();
						memberEmail = node.path("result").get("memberEmail").textValue();
						orderChannel = node.path("result").get("orderChannel").textValue();

						paymentCompletionYn= node.path("result").get("paymentCompletionYn").textValue();
						paymentCompleteYmdt= node.path("result").get("paymentCompleteYmdt").textValue();

						orderProducts = node.path("result").get("orderProducts").toString();
						
						/*
						// orderProducts 추출 예시
						ArrayNode orderProducts_arr = (ArrayNode)node.path("result").get("orderProducts");
						for(int i = 0; i < orderProducts_arr.size(); i++){
							orderProductNo 				   = orderProducts_arr.get(i).get("orderProductNo").textValue();
				///			sellerOrderProductReferenceKey = orderProducts_arr.get(i).get("sellerOrderProductReferenceKey").textValue();
							productPaymentAmt			   = orderProducts_arr.get(i).get("productPaymentAmt").toString();
							// .. 등등...
						}
						*/
												
						String paymentTradeNo, paymentMethodCode, paymentMethodName, 
						        pgAdmissionNo, pgAdmissionYmdt,easyPaymentYn,tradeYmdt, 
						        cardCompanyName = "",
         			            cardCompanyCode = "",
								cardNo = "",
								cardInstallmentMonthNumber = "",
								cardAdmissionNo = "",
								cardInterestFreeYn = "",
								corporateCardYn = "",
								partCancelPossibleYn = "",
								companyName = "",
								cellphoneNo = "",
								bankName = "",
								bankCode = "",
								accountNo = "",
								paymentExpirationYmd = "",
								discountAmt = "",
								discountConditionAmt = "";

						Double paymentAmt;
						
						List<PaycoPaymentVO> paymentList = new ArrayList<PaycoPaymentVO>();
						
						// paymentDetails 추출 예시
						ArrayNode paymentDetails_arr = (ArrayNode)node.path("result").get("paymentDetails");
						for(int j = 0; j < paymentDetails_arr.size(); j++){
							PaycoPaymentVO paymentvo = new PaycoPaymentVO();

							paymentTradeNo		= paymentDetails_arr.get(j).get("paymentTradeNo").textValue();
							paymentMethodCode 	= paymentDetails_arr.get(j).get("paymentMethodCode").textValue();
							paymentMethodName 	= paymentDetails_arr.get(j).get("paymentMethodName").textValue();
							pgAdmissionNo		    = paymentDetails_arr.get(j).get("pgAdmissionNo").textValue();
							pgAdmissionYmdt		    = paymentDetails_arr.get(j).get("pgAdmissionYmdt").textValue();
							easyPaymentYn		    = paymentDetails_arr.get(j).get("easyPaymentYn").textValue();
							tradeYmdt		    = paymentDetails_arr.get(j).get("tradeYmdt").textValue();
							paymentAmt = paymentDetails_arr.get(j).get("paymentAmt").asDouble();
							
							//신용카드 결제 정보
							if (paymentDetails_arr.get(j).get("cardSettleInfo") != null) {
								cardCompanyName = paymentDetails_arr.get(j).get("cardSettleInfo").get("cardCompanyName").textValue();
								cardCompanyCode = paymentDetails_arr.get(j).get("cardSettleInfo").get("cardCompanyCode").textValue();
								cardNo 		 = paymentDetails_arr.get(j).get("cardSettleInfo").get("cardNo").textValue();		
								cardInstallmentMonthNumber 		 = paymentDetails_arr.get(j).get("cardSettleInfo").get("cardInstallmentMonthNumber").textValue();		
								cardAdmissionNo 		 = paymentDetails_arr.get(j).get("cardSettleInfo").get("cardAdmissionNo").textValue();		
								cardInterestFreeYn 		 = paymentDetails_arr.get(j).get("cardSettleInfo").get("cardInterestFreeYn").textValue();		
								corporateCardYn 		 = paymentDetails_arr.get(j).get("cardSettleInfo").get("corporateCardYn").textValue();		
								partCancelPossibleYn 		 = paymentDetails_arr.get(j).get("cardSettleInfo").get("partCancelPossibleYn").textValue();		
							}
							
							//핸드폰결제정보
							if (paymentDetails_arr.get(j).get("cellphoneSettleInfo") != null) {
								companyName = paymentDetails_arr.get(j).get("cellphoneSettleInfo").get("companyName").textValue();
								cellphoneNo  = paymentDetails_arr.get(j).get("cellphoneSettleInfo").get("cellphoneNo").textValue();
								
							}
							//실시간계좌이체 결제정보
							if (paymentDetails_arr.get(j).get("realtimeAccountTransferSettleInfo") != null) {
								bankName = paymentDetails_arr.get(j).get("realtimeAccountTransferSettleInfo").get("bankName").textValue();
								bankCode = paymentDetails_arr.get(j).get("realtimeAccountTransferSettleInfo").get("bankCode").textValue();
							}
							
							//무통장입금결제정보
							if (paymentDetails_arr.get(j).get("nonBankbookSettleInfo") != null) {
								bankName = paymentDetails_arr.get(j).get("nonBankbookSettleInfo").get("bankName").textValue();
								bankCode = paymentDetails_arr.get(j).get("nonBankbookSettleInfo").get("bankCode").textValue();
								accountNo  = paymentDetails_arr.get(j).get("nonBankbookSettleInfo").get("accountNo").textValue();
								paymentExpirationYmd  = paymentDetails_arr.get(j).get("nonBankbookSettleInfo").get("paymentExpirationYmd").textValue();
							}
							
							//쿠폰결제정보
							if (paymentDetails_arr.get(j).get("couponSettleInfo") != null) {
								discountAmt = paymentDetails_arr.get(j).get("couponSettleInfo").get("discountAmt").textValue();
								discountConditionAmt = paymentDetails_arr.get(j).get("couponSettleInfo").get("discountConditionAmt").textValue();
							}
							
							paymentvo.setPaymentTradeNo(paymentTradeNo);
							paymentvo.setPaymentMethodCode(paymentMethodCode);
							paymentvo.setPaymentMethodName(paymentMethodName);
							paymentvo.setPaymentAmt(paymentAmt);
							paymentvo.setTradeYmdt(tradeYmdt);
							paymentvo.setPgAdmissionNo(pgAdmissionNo);
							paymentvo.setPgAdmissionYmdt(pgAdmissionYmdt);
							paymentvo.setEasyPaymentYn(easyPaymentYn);
							paymentvo.setCardCompanyName(cardCompanyName);
							paymentvo.setCardCompanyCode(cardCompanyCode);
							paymentvo.setCardNo(cardNo);
							paymentvo.setCardInstallmentMonthNumber(cardInstallmentMonthNumber);
							paymentvo.setCardAdmissionNo(cardAdmissionNo);
							paymentvo.setCardInterestFreeYn(cardInterestFreeYn);
							paymentvo.setCorporateCardYn(corporateCardYn);
							paymentvo.setPartCancelPossibleYn(partCancelPossibleYn);
							paymentvo.setCompanyName(companyName);
							paymentvo.setCellphoneNo(cellphoneNo);
							paymentvo.setBankName(bankName);
							paymentvo.setBankCode(bankCode);
							paymentvo.setAccountNo(accountNo);
							paymentvo.setPaymentExpirationYmd(paymentExpirationYmd);
							paymentvo.setDiscountAmt(discountAmt);
							paymentvo.setDiscountConditionAmt(discountConditionAmt);
							
							paymentList.add(paymentvo);
						}

						approvalvo.setOrderIdx(orderIdx);
						approvalvo.setSellerKey(setting.getSellerKey());
						approvalvo.setReserveOrderNo(reserveOrderNo);
						approvalvo.setOrderNo(orderNo);
						approvalvo.setSellerOrderReferenceKey(sellerOrderReferenceKey);
						approvalvo.setOrderCertifyKey(orderCertifyKey);
						approvalvo.setMemberName(memberName);
						approvalvo.setMemberEmail(memberEmail);
						approvalvo.setOrderChannel(orderChannel);
						approvalvo.setTotalOrderAmt(dtotalOrderAmt);
						approvalvo.setTotalDeliveryFeeAmt(dtotalDeliveryFeeAmt);
						approvalvo.setTotalRemoteAreaDeliveryFeeAmt(dtotalRemoteAreaDeliveryFeeAmt);
						approvalvo.setTotalPaymentAmt(dtotalPaymentAmt);
						approvalvo.setReceiptPaycoPointAmt(dreceiptPaycoPointAmt);
						approvalvo.setReceiptPaycoPointServiceAmt(dreceiptPaycoPointServiceAmt);
						approvalvo.setReceiptPaycoPointTaxableAmt(dreceiptPaycoPointTaxableAmt);
						approvalvo.setReceiptPaycoPointTaxfreeAmt(dreceiptPaycoPointTaxfreeAmt);
						approvalvo.setReceiptPaycoPointVatAmt(dreceiptPaycoPointVatAmt);
						approvalvo.setPaymentCompletionYn(paymentCompletionYn);
						approvalvo.setPaymentCompleteYmdt(paymentCompleteYmdt);
						approvalvo.setOrderProducts(orderProducts);
						
						approvalvo.setPaymentDetails(paymentList);
												
						completvo.setOrderNo(orderNo);
///						completvo.setSellerOrderProductReferenceKey(sellerOrderProductReferenceKey);
						completvo.setOrderCertifyKey(orderCertifyKey);
///						completvo.setTotalCancelTaxfreeAmt(taxfreeAmt);
///						completvo.setTotalCancelTaxableAmt(taxableAmt);
///						completvo.setTotalCancelVatAmt(vatAmt);
						completvo.setTotalCancelPossibleAmt(Integer.toString(totalPaymentAmt));
						completvo.setCancelTotalAmt(Integer.toString(totalPaymentAmt));
						completvo.setRequestMemo("결제취소 테스트입니다.");
						completvo.setCancelDetailContent("결제취소 테스트입니다.");

						completvo.setReserveOrderNo(reserveOrderNo);
						completvo.setSellerOrderReferenceKey(sellerOrderReferenceKey);

						// complete 페이지로 보내는 정보(샘플용) - 가맹점에서는 DB로 데이터 처리하시기 바랍니다. 
/*						String paramStr = "?orderNo="+orderNo+"&sellerOrderProductReferenceKey="+sellerOrderProductReferenceKey+"&orderCertifyKey="+orderCertifyKey+
										  "&totalCancelTaxfreeAmt="+taxfreeAmt+"&totalCancelTaxableAmt="+taxableAmt+"&totalCancelVatAmt="+vatAmt+
										  "&totalCancelPossibleAmt="+totalPaymentAmt+"&cancelTotalAmt="+totalPaymentAmt+"&requestMemo=결제취소 테스트입니다."+
										  "&cancelDetailContent=결제취소 테스트입니다."+"&reserveOrderNo="+reserveOrderNo+"&sellerOrderReferenceKey="+sellerOrderReferenceKey;
*/						
						try {
							orderService.insertPaycoApprovalPayment(approvalvo);
							
							message   = "주문이 정상적으로 완료되었습니다.";							
						} catch (Exception e) {
							e.printStackTrace();
							message   = "주문서 작성중 데이터처리 오류로 인하여 주문이 취소 되었습니다.";
							doCancel = true;
						}
					
					}catch(Exception e){
						e.printStackTrace();
						message   = "주문서 작성중 데이터처리 오류로 인하여 주문이 취소 되었습니다.";
						doCancel = true;
					}
						
				}else{
					message   = node.path("message").textValue();
					
				}
				
			//	doCancel = true;
				
				if(doCancel){
					/*★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
					#  PAYCO 결제 승인이 완료되고 아래와 같은 상황이 발생하였을경우 예외 처리가 필요합니다.
					1. 가맹점 DB 처리중 오류 발생시
					2. 통신 장애로 인하여 결과를 리턴받지 못했을 경우
					
					위와 같은 상황이 발생하였을 경우 이미 승인 완료된 주문건에 대하여 주문 취소처리(전체취소)가 필요합니다.
					 - PAYCO에서는 주문승인(결제완료) 처리 되었으나 가맹점은 해당 주문서가 없는 경우가 발생
					 - PAYCO에서는 승인 완료된 상태이므로 주문 상세정보 API를 이용해 결제정보를
					   조회하여 취소요청 파라미터에 셋팅합니다.
					★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★*/
					
					// 결제상세 조회 필수값 셋팅 
					Map<String, Object> verifyPaymentMap = new HashMap<String, Object>();
					verifyPaymentMap.put("sellerKey", setting.getSellerKey());
					verifyPaymentMap.put("reserveOrderNo", reserveOrderNo);						// 주문예약번호
					verifyPaymentMap.put("sellerOrderReferenceKey", sellerOrderReferenceKey);	// 가맹점주문연동키
					
					// 결제상세 조회 API 호출
					String verifyPaymentResult = util.payco_verifyPayment(verifyPaymentMap, setting.getLogYn());
					
					// jackson Tree 이용
					JsonNode verifyPayment_node = mapper.readTree(verifyPaymentResult);
					
					String cancel_orderNo		  = verifyPayment_node.path("result").get("orderNo").textValue(); 
					String cancel_orderCertifyKey = verifyPayment_node.path("result").get("orderCertifyKey").textValue();
					String cancel_cancelTotalAmt  = verifyPayment_node.path("result").get("totalPaymentAmt").toString();
					
					/* 설정한 주문취소 정보로 Json String 을 작성합니다. */
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("sellerKey", setting.getSellerKey());										//[필수]가맹점 코드
					param.put("orderNo", cancel_orderNo);									//[필수]주문번호
					param.put("orderCertifyKey", cancel_orderCertifyKey);					//[필수]주문인증 key
					param.put("cancelTotalAmt", Integer.parseInt(cancel_cancelTotalAmt));  	//[필수]취소할 총 금액(전체취소, 부분취소 전부다)
					
					/* 주문 결제 취소 API 호출 */
					String cancelResult = util.payco_cancel(param,setting.getLogYn(),"Y");
					
					JsonNode cancelNode = mapper.readTree(cancelResult);
					
					if(!cancelNode.path("code").toString().equals("0")){
						message   = "페이코결제취소 중 에러가 발생하였습니다.";
						System.out.println(message);
					//	returnUrl = "index.do";
					}
				}
			}
		}
		completvo.setResultMessage(message);
		 MethodUtil.redirectPostUrl(request, response, PathUtil.getCtx()+"/payco/paycoComplete.do", completvo); 
	}	
	

	
	@RequestMapping(value = "/payco/paycoComplete")
	public String paycoComplete(PaycoCompletlVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
		model.addAttribute("vo", vo);
		return PathUtil.getCtx()+"/payco/paycoComplete";
	}
	   
}
