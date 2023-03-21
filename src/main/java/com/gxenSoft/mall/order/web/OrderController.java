package com.gxenSoft.mall.order.web;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.gson.Gson;
import com.gxenSoft.mall.cart.service.CartService;
import com.gxenSoft.mall.cart.vo.CartVO;
import com.gxenSoft.mall.common.service.CommonService;
import com.gxenSoft.mall.common.vo.JsonResultVO;
import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.common.web.CommonMethod;
import com.gxenSoft.mall.event.entry.service.EntryService;
import com.gxenSoft.mall.lgdacom.vo.PaycoApprovalVO;
import com.gxenSoft.mall.lgdacom.vo.PaycoCompletlVO;
import com.gxenSoft.mall.lgdacom.vo.PaycoPaymentVO;
import com.gxenSoft.mall.lgdacom.vo.PaycoReturnVO;
import com.gxenSoft.mall.lgdacom.vo.PaycoSettingVO;
import com.gxenSoft.mall.lgdacom.vo.XPayBillKeyAuthVO;
import com.gxenSoft.mall.lgdacom.vo.XPayBillKeyVO;
import com.gxenSoft.mall.lgdacom.vo.XPayCyberAccountVO;
import com.gxenSoft.mall.lgdacom.vo.XPayEscrowGoodsVO;
import com.gxenSoft.mall.lgdacom.vo.XPayReqVO;
import com.gxenSoft.mall.lgdacom.vo.XPayResultVO;
import com.gxenSoft.mall.mypage.order.service.MypageOrderService;
import com.gxenSoft.mall.mypage.order.vo.ClaimVO;
import com.gxenSoft.mall.order.service.OrderService;
import com.gxenSoft.mall.order.vo.EscrowResultVO;
import com.gxenSoft.mall.order.vo.KcpNotiReqVO;
import com.gxenSoft.mall.order.vo.KcpReqVO;
import com.gxenSoft.mall.order.vo.NpayResultVO;
import com.gxenSoft.mall.order.vo.OrderGoodsVO;
import com.gxenSoft.mall.order.vo.OrderVO;
import com.gxenSoft.mall.order.vo.ProcCouponResultVO;
import com.gxenSoft.mall.order.vo.ProcCouponVO;
import com.gxenSoft.mall.order.vo.WonderAuthReqVO;
import com.gxenSoft.mall.order.vo.WonderAuthResVO;
import com.gxenSoft.mall.order.vo.WonderVO;
import com.gxenSoft.message.SpringMessage;
import com.gxenSoft.method.ConvertUtil;
import com.gxenSoft.method.MethodUtil;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.pathUtil.PathUtil;
import com.gxenSoft.util.pg.payco.util.PaycoUtil;
import com.gxenSoft.util.pg.smilepay.util.SmilepayUtil;
import com.gxenSoft.util.pg.smilepay.vo.SmilepayCancelVO;
import com.gxenSoft.util.pg.smilepay.vo.SmilepayReqVO;
import com.gxenSoft.util.pg.smilepay.vo.SmilepayResultVO;
import com.kcp.J_PP_CLI_N;

import kr.co.kcp.net.connection.ConnectionKCP;
import kr.co.kcp.net.connection.dto.ParamData;
import kr.co.kcp.net.connection.util.HttpJsonXml;
import kr.co.kcp.net.connection.util.OpenHash;
import lgdacom.XPayClient.XPayClient;

   /**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : OrderController
    * PACKAGE NM : com.gxenSoft.mall.order.web
    * AUTHOR	 : 서 정 길
    * CREATED DATE  : 2017. 7. 4. 
    * HISTORY :   
    *
    *************************************
    */	
@Controller
public class OrderController extends CommonMethod{

	static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private CartService cartService;
	
	@Autowired
	private MypageOrderService mypageOrderService;
	
	@Autowired
	private CommonService commonService;

	@Autowired
	private EntryService entryService;

	   /**
	    * @Method : nvl
	    * @Date: 2017. 8. 27.
	    * @Author :  서 정 길
	    * @Description	:	null 대신 ""로 반환
	   */
	public static String nvl(Object str){
		if(str == null){
			return "";
		}else{
			return String.valueOf(str);
		}
	}	
	
	   /**
	    * @Method : getCurrentUrl
	    * @Date: 2017. 8. 30.
	    * @Author :  서 정 길
	    * @Description	:	현재 URL 반환
	   */
	private String getCurrentUrl() throws Exception{
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		String strCurrentUrl = request.getScheme() + "://" + request.getServerName();
		strCurrentUrl += request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
		strCurrentUrl += request.getContextPath(); 
		
		return strCurrentUrl;
	}
	
	   /**
	    * @Method : ListObjectToHiddenParams
	    * @Date: 2017. 9. 19.
	    * @Author :  서 정 길
	    * @Description	:	List Object를  parameter 값 hidden 형식 return
	   */
	public StringBuffer ListObjectToHiddenParams(List<XPayEscrowGoodsVO> vo) throws Exception{
		StringBuffer bf = new StringBuffer();

		if(vo != null && vo.size() > 0){
			for(int k=0;k<vo.size();k++){

				Gson gson = new Gson();
				String jsonData = gson.toJson(vo.get(k));
			
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonData);
		
				@SuppressWarnings("rawtypes")
				Iterator i = jsonObject.keySet().iterator();
				
				while(i.hasNext()){
					String key = (String)i.next();
					String value = (jsonObject.get(key) == null)? "":jsonObject.get(key).toString();
					bf.append("<input type='hidden' name='"+key+"' id='"+key+"' value='"+value+"' />\n");
				}
			}
		}

		return bf;
	}
	
	   /**
	    * @Method : setInitXPayReq
	    * @Date: 2017. 7. 4.
	    * @Author :   강 병 철
	    * @Description	:	XPayReqVO 초기화
	   */
	private void initXPayReq(XPayReqVO vo) throws Exception {
		final String CST_MID = SpringMessage.getMessage("xpay.MID"); // "mandomkorea"; //상점아이디 
		final String CST_PLATFORM = SpringMessage.getMessage("xpay.PLATFORM"); //"test";// 테스트여부    service 또는 test
		String LGD_RETURNURL = getCurrentUrl() + SpringMessage.getMessage("xpay.RETURNURL");  // "https://222.112.106.57:8443/w" + "/order/returnurl.do";
		if(PathUtil.getDeviceNm().equals("M")){		// 모바일 결제시 m 으로 URL 변경
			LGD_RETURNURL = LGD_RETURNURL.replace("/w/", "/m/");
		}
	/*	
		final String CST_MID =  "mandomkorea"; //상점아이디 
		final String CST_PLATFORM = "test";// 테스트여부    service 또는 test
		final String LGD_MERTKEY = "c7ecf8d164fec7c46d4d049c8700d423";
		
		final String LGD_RETURNURL =  "https://222.112.106.57:8443/w/lgdacom/returnurl.do";
		
		//가상계좌 입금시 return URL
		final String LGD_CASNOTEURL =  "https://222.112.106.57:8443/w/lgdacom/casnoteurl.do";
	*/	
		
		String LGD_MID    = ("test".equals(CST_PLATFORM.trim())?"t":"")+CST_MID;  //테스트 아이디는 't'를 제외하고 입력하세요.
		
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMddHHmmss", Locale.KOREA ); 
		Date currentTime = new Date ( ); 
		String lGD_TIMESTAMP = formatter.format ( currentTime ); 
		
		vo.setCST_PLATFORM(CST_PLATFORM );
		vo.setLGD_MID(LGD_MID);
		vo.setCST_MID(LGD_MID);
		
		if(PathUtil.getDeviceNm().equals("M")){
			// 모바일용
			vo.setLGD_CUSTOM_SKIN("SMART_XPAY2");
			vo.setLGD_OSTYPE_CHECK("M");
			vo.setLGD_VERSION("JSP_Non-ActiveX_SmartXPay");		// 사용타입 정보(수정 및 삭제 금지): 이 정보를 근거로 어떤 서비스를 사용하는지 판단할 수 있습니다.
			vo.setLGD_WINDOW_TYPE("submit");
			vo.setLGD_CUSTOM_SWITCHINGTYPE("SUBMIT");			// 신용카드 카드사 인증 페이지 연동 방식
		}else{
			// PC용
			vo.setLGD_CUSTOM_SKIN("red");
			vo.setLGD_OSTYPE_CHECK("P");           //값 P: XPay 실행(PC 결제 모듈): PC용과 모바일용 모듈은 파라미터 및 프로세스가 다르므로 PC용은 PC 웹브라우저에서 실행 필요.
			vo.setLGD_VERSION("JSP_Non-ActiveX_Standard");			// 사용타입 정보(수정 및 삭제 금지): 이 정보를 근거로 어떤 서비스를 사용하는지 판단할 수 있습니다.
			vo.setLGD_WINDOW_TYPE("iframe");
			//vo.setLGD_CUSTOM_SWITCHINGTYPE("IFRAME");
		}
		
	    vo.setLGD_RETURNURL(LGD_RETURNURL);
	    
		vo.setLGD_CUSTOM_PROCESSTYPE("TWOTR");
		
		vo.setLGD_TIMESTAMP(lGD_TIMESTAMP);    
	}

	   /**
	    * @Method : setHashkey
	    * @Date: 2017. 7. 4.
	    * @Author :   강 병 철
	    * @Description	:	Hashkey 만들기
	   */
		public String makeHashkey(String Mid, String Oid, String Amount, String Timestamp,  String Mertkey) throws Exception{
		
			/*
		     *************************************************
		     * 2. MD5 해쉬암호화 (수정하지 마세요) - BEGIN
		     *
		     * MD5 해쉬암호화는 거래 위변조를 막기위한 방법입니다.
		     *************************************************
		     *
		     * 해쉬 암호화 적용( LGD_MID + LGD_OID + LGD_AMOUNT + LGD_TIMESTAMP + LGD_MERTKEY )
		     * LGD_MID          : 상점아이디
		     * LGD_OID          : 주문번호
		     * LGD_AMOUNT       : 금액
		     * LGD_TIMESTAMP    : 타임스탬프
		     * LGD_MERTKEY      : 상점MertKey (mertkey는 상점관리자 -> 계약정보 -> 상점정보관리에서 확인하실수 있습니다)
		     *
		     * MD5 해쉬데이터 암호화 검증을 위해
		     * LG유플러스에서 발급한 상점키(MertKey)를 환경설정 파일(lgdacom/conf/mall.conf)에 반드시 입력하여 주시기 바랍니다.
		     */
		    StringBuffer sb = new StringBuffer();
		    sb.append(Mid);
		    sb.append(Oid);
		    sb.append(Amount);
		    sb.append(Timestamp);
		    sb.append(Mertkey);
		
		    byte[] bNoti = sb.toString().getBytes();
		    MessageDigest md;
			try {
				md = MessageDigest.getInstance("MD5");
			    byte[] digest = md.digest(bNoti);
		
			    StringBuffer strBuf = new StringBuffer();
			    for (int i=0 ; i < digest.length ; i++) {
			        int c = digest[i] & 0xff;
			        if (c <= 15){
			            strBuf.append("0");
			        }
			        strBuf.append(Integer.toHexString(c));
			    }
			    return strBuf.toString();
			    
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				throw e;
			}
		}

	   /**
	    * @Method : makeHashkey2
	    * @Date: 2017. 7. 27.
	    * @Author :  서 정 길
	    * @Description	:	가상계좌용 해시키 만들기
	   */
	public String makeHashkey2(String Mid, String Oid, String Amount, String respCode, String Timestamp,  String Mertkey) throws Exception{
		StringBuffer sb = new StringBuffer();
	    sb.append(Mid);
	    sb.append(Oid);
	    sb.append(Amount);
	    sb.append(respCode);
	    sb.append(Timestamp);
	    sb.append(Mertkey);

	    byte[] bNoti = sb.toString().getBytes();
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    byte[] digest = md.digest(bNoti);

	    StringBuffer strBuf = new StringBuffer();
	    for (int i=0 ; i < digest.length ; i++) {
	        int c = digest[i] & 0xff;
	        if (c <= 15){
	            strBuf.append("0");
	        }
	        strBuf.append(Integer.toHexString(c));
	    }
	    
	    return strBuf.toString();
	}
		

	/**
	 * @Method : BillkeyList  
	 * @Date		: 2017. 8. 07.
	 * @Author	:  강 병 철
	 * @Description	: Mypage > member의 원클릭 카드 관리
	 */
	@RequestMapping("/mypage/member/billkeyList")
	public String billkeyList(HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session)throws Exception{
		final String XPAYJSURL = SpringMessage.getMessage("xpay.JSURL");  // https://xpay.uplus.co.kr/xpay/js/xpay_crossplatform.js
		UserInfo userInfo = UserInfo.getUserInfo();
		//원클릭결제카드등록
		final String CST_PLATFORM = SpringMessage.getMessage("xpay.PLATFORM"); //"test";// 테스트여부    service 또는 test
		//빌키 MID
		final String BILLMID = SpringMessage.getMessage("xpay.BILLMID");

		String LGD_BILLMID = ("test".equals(CST_PLATFORM.trim())?"t":"")+BILLMID;  //테스트 아이디는 't'를 제외하고 입력하세요.

		//빌키등록시 return URL
		String LGD_BILLKEYREGRETURNURL = getCurrentUrl() + SpringMessage.getMessage("xpay.LGD_BILLKEYREGRETURNURL");  // "https://222.112.106.57:8443/w" + "/order/billkeyRegReturn.do";
		if(PathUtil.getDeviceNm().equals("M")){		// 모바일 결제시 m 으로 URL 변경
			LGD_BILLKEYREGRETURNURL = LGD_BILLKEYREGRETURNURL.replace("/w/", "/m/");
			LGD_BILLKEYREGRETURNURL = LGD_BILLKEYREGRETURNURL.replace("/order/", "/mypage/member/");
		}
		
		XPayBillKeyVO  billkeyRegVo = new XPayBillKeyVO();
		billkeyRegVo.setCST_MID(LGD_BILLMID);
		billkeyRegVo.setLGD_MID(LGD_BILLMID);
		billkeyRegVo.setLGD_RETURNURL(LGD_BILLKEYREGRETURNURL); 
		billkeyRegVo.setCST_PLATFORM(CST_PLATFORM);
		billkeyRegVo.setLGD_WINDOW_TYPE("iframe");	
		billkeyRegVo.setLGD_OSTYPE_CHECK(PathUtil.getDeviceNm());
		if(PathUtil.getDeviceNm().equals("M")){		// 모바일 결제시 스마트폰 신용카드 빌링타입 정보로 세팅
			billkeyRegVo.setLGD_PAYWINDOWTYPE("CardBillingAuth_smartphone");
			billkeyRegVo.setLGD_WINDOW_TYPE("popup");
		}
		
		List<SqlMap> billkeyList = orderService.getBillkeyList(userInfo.getMemberIdx());	// 빌키 리스트
		model.addAttribute("billkeyList", billkeyList);		
		model.addAttribute("xpayJsUrl",XPAYJSURL);
	    model.addAttribute("billkeyRegMap", billkeyRegVo);
	    model.addAttribute("billkeyRegMapHiddenParams" , ObjectToHiddenParams(billkeyRegVo));
		return PathUtil.getCtx()+"/mypage/member/billkeyList";
	}
	
	   /**
	    * @Method : nomemberTerms
	    * @Date: 2017. 8. 18.
	    * @Author :  서 정 길
	    * @Description	:	비회원 구매 이용 약관
	   */
	@RequestMapping(value="/order/nomemberTerms", method = RequestMethod.POST) 
	public String nomemberTerms(OrderVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{

		if(session.getAttribute("SS_MEMBER_FLAG") != null){	// 로그인
			if(session.getAttribute("SS_MEMBER_FLAG").equals("Y")){	// 회원 로그인
				MethodUtil.redirectUrl(request, response, "/cart.do");	// 로그인 된 상태면 장바구니로 보내버림
				return null;
			}else{	// 비회원 주문조회
				
			}
		}else{	// 미로그인

		}
		
		// 장바구니에서 넘어온 주문 정보
		if(vo.getOrderGoodsInfoListStr() == null || vo.getOrderGoodsInfoListStr().isEmpty()){
			// 주문 정보 없음 에러 처리
			MethodUtil.alertMsgUrl(request, response, "주문 정보가 존재하지 않습니다!", "/cart.do");
			return null;
		}
		
		model.addAttribute("VO", vo);

		return PathUtil.getCtx()+"/order/nomemberTerms";
	}

	   /**
	    * @Method : cartOrderProc
	    * @Date: 2017. 8. 3.
	    * @Author :  서 정 길
	    * @Description	:	주문 정보 생성
	   */
	@RequestMapping(value="/order/cartOrderProc", method = RequestMethod.POST) 
	public String cartOrderProc(OrderVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{

		if(session.getAttribute("SS_MEMBER_FLAG") != null){	// 로그인
			if(session.getAttribute("SS_MEMBER_FLAG").equals("Y")){	// 회원 로그인
				UserInfo userInfo = UserInfo.getUserInfo();
				
				vo.setRegIdx(userInfo.getMemberIdx());
				vo.setMemberIdx(userInfo.getMemberIdx());
				vo.setMemberId(userInfo.getMemberId());
				vo.setMemberGradeIdx(Integer.parseInt(userInfo.getLevelIdx()));
				vo.setMemberNm(userInfo.getMemberNm());
				
				SqlMap memberDetail = orderService.getMemberDetail(vo.getMemberIdx());	// 회원 상세
				vo.setSenderEmail((String)memberDetail.get("email"));
				vo.setSenderPhoneNo(((String)memberDetail.get("phoneNo")).replaceAll("-", ""));	// 휴대폰번호에서 - 제거
			}else{	// 비회원 주문조회
				vo.setRegIdx(0);
				
				// 비회원 구매하기 선택한 상태가 아니면 로그인 화면으로 보냄
				if(vo.getNomemberOrderYn() == null || !vo.getNomemberOrderYn().equals("Y")){
					if(vo.getOrderGoodsInfoListStr().contains("[{")){	// 인코딩이 안 됐으면 (여러번 호출되기 때문에 인코딩은 한번만 해야 함)
						vo.setOrderGoodsInfoListStr(URLEncoder.encode(vo.getOrderGoodsInfoListStr(),"UTF-8"));	// JSON 에 " 가 포함돼서 인코딩시킴
					}
					vo.setFromOrderFlag("Y");
					MethodUtil.redirectPostUrl(request, response, PathUtil.getCtx()+"/login/loginPage.do", vo);
					return null;
				}

				// 비회원 주문이고 비회원 구매 이용 약관 동의 정보가 없으면 비회원 구매 이용 약관 페이지로 돌림
				if((vo.getTermsYn() == null || !vo.getTermsYn().equals("Y")) || 
				   (vo.getCollectionAgreementYn() == null || !vo.getCollectionAgreementYn().equals("Y"))){
					if(vo.getOrderGoodsInfoListStr().contains("[{")){	// 인코딩이 안 됐으면 (여러번 호출되기 때문에 인코딩은 한번만 해야 함)
						vo.setOrderGoodsInfoListStr(URLEncoder.encode(vo.getOrderGoodsInfoListStr(),"UTF-8"));	// JSON 에 " 가 포함돼서 인코딩시킴
					}
					MethodUtil.redirectPostUrl(request, response, PathUtil.getCtx()+"/order/nomemberTerms.do", vo);
					return null;
				}
			}
		}else{	// 미로그인
			vo.setRegIdx(0);

			// 비회원 구매하기 선택한 상태가 아니면 로그인 화면으로 보냄
			if(vo.getNomemberOrderYn() == null || !vo.getNomemberOrderYn().equals("Y")){
				if(vo.getOrderGoodsInfoListStr().contains("[{")){	// 인코딩이 안 됐으면 (여러번 호출되기 때문에 인코딩은 한번만 해야 함)
					vo.setOrderGoodsInfoListStr(URLEncoder.encode(vo.getOrderGoodsInfoListStr(),"UTF-8"));	// JSON 에 " 가 포함돼서 인코딩시킴
				}
				vo.setFromOrderFlag("Y");
				MethodUtil.redirectPostUrl(request, response, PathUtil.getCtx()+"/login/loginPage.do?refererYn=Y", vo);
				return null;
			}

			// 비회원 주문이고 비회원 구매 이용 약관 동의 정보가 없으면 비회원 구매 이용 약관 페이지로 돌림
			if((vo.getTermsYn() == null || !vo.getTermsYn().equals("Y")) || 
			   (vo.getCollectionAgreementYn() == null || !vo.getCollectionAgreementYn().equals("Y"))){
				if(vo.getOrderGoodsInfoListStr().contains("[{")){	// 인코딩이 안 됐으면 (여러번 호출되기 때문에 인코딩은 한번만 해야 함)
					vo.setOrderGoodsInfoListStr(URLEncoder.encode(vo.getOrderGoodsInfoListStr(),"UTF-8"));	// JSON 에 " 가 포함돼서 인코딩시킴
				}
				MethodUtil.redirectPostUrl(request, response, PathUtil.getCtx()+"/order/nomemberTerms.do", vo);
				return null;
			}
		}

		vo.setOrderGoodsInfoListStr(URLDecoder.decode(vo.getOrderGoodsInfoListStr(),"UTF-8"));	// JSON 에 " 가 포함돼서 인코딩시킨걸 다시 디코딩

		vo.setRegHttpUserAgent(request.getHeader("user-agent"));	// HTTP_USER_AGENT
		
		InetAddress local = InetAddress.getLocalHost();	// LOCAL IP ADDRESS
		vo.setRegIp(local.getHostAddress());
		
		// 장바구니에서 넘어온 주문 정보
		if(vo.getOrderGoodsInfoListStr() != null && !vo.getOrderGoodsInfoListStr().isEmpty()){
			ObjectMapper mapper = new ObjectMapper();
			List<OrderGoodsVO> jsonObject = mapper.readValue(vo.getOrderGoodsInfoListStr(), new TypeReference<List<OrderGoodsVO>>() {});
			vo.setOrderGoodsInfoList(jsonObject);
			
			JsonResultVO resultMap = new JsonResultVO();
			

			// 네이버 타임보드 이벤트 (2019-11-13 회원 가입자 대상)용 임시 시작-----------------------------------------------------------------
			Calendar calendar1 = Calendar.getInstance();
 		    calendar1.set(Calendar.YEAR, 2019);
 		    calendar1.set(Calendar.MONTH, 10-1);
 		    calendar1.set(Calendar.DAY_OF_MONTH, 30);

			 Calendar calendar2 = Calendar.getInstance();
			 calendar2.set(Calendar.YEAR, 2019);
			 calendar2.set(Calendar.MONTH, 11-1);
			 calendar2.set(Calendar.DAY_OF_MONTH, 13);

			Date now = new Date();
			Calendar nowcalendar = Calendar.getInstance();
			nowcalendar.setTime(now);
			SimpleDateFormat dt = new SimpleDateFormat("Ymd");
			if (nowcalendar.compareTo(calendar2) == 0 ) {
				Boolean isNaverEventGoods = false;
				for(int i=0;i<vo.getOrderGoodsInfoList().size();i++){
					Integer goodsIdx = vo.getOrderGoodsInfoList().get(i).getGoodsIdx();
					Integer goodsCnt = vo.getOrderGoodsInfoList().get(i).getGoodsCnt();

					if(goodsIdx.intValue() == 823){	// 이벤트 대상 상품 포함 여부
						if(goodsCnt.intValue() != 1){	// 주문 수량이 1개가 아니면 문제
							MethodUtil.alertMsgUrl(request, response, "주문 정보가 올바르지 않습니다!", "/main.do");
							return null;
						}
						
						isNaverEventGoods = true;
					}
				}
				
				if(vo.getOrderGoodsInfoList().size() == 1 && isNaverEventGoods){	// 네이버 이벤트 대상이면
					if(vo.getRegIdx() <= 0){	// 회원만 대상
						MethodUtil.alertMsgUrl(request, response, "회원만 구매가능합니다!", "/main.do");
						return null;
					}
					
					SqlMap info = entryService.procSpPgCheckNaverEvent(vo.getRegIdx().toString());	// 구매시 유효성 체크

					if(info == null){
						MethodUtil.alertMsgUrl(request, response, "주문 유효성 확인 중 에러가 발생했습니다!","/main.do");
						return null;
					}else{
						if(!info.get("result").toString().equals("TRUE")){
							MethodUtil.alertMsgUrl(request, response, info.get("result").toString(), "/main.do");
							return null;
						}
					}
				}
			}
			
			// 네이버 타임보드 이벤트 (2019-11-13 회원 가입자 대상)용 임시 끝-----------------------------------------------------------------

			
			// 장바구니 정보 주문 테이블에 저장
			resultMap = orderService.insertCartOrderInfo(vo, "N", session);
			
			if(!resultMap.getResult()){
				// 에러 처리
				MethodUtil.alertMsgUrl(request, response, resultMap.getMsg(), "/cart.do");
				return null;
			}
		}else{
			// 주문 정보 없음 에러 처리
			MethodUtil.alertMsgUrl(request, response, "주문 정보가 존재하지 않습니다!", "/cart.do");
			return null;
		}
		
		return "redirect:/order/cartOrder.do";
	}
	
	
	   /**
	    * @Method : cartOrder
	    * @Date: 2017. 7. 4.
	    * @Author :  서 정 길
	    * @Description	:	주문결제
	   */
	@RequestMapping(value="/order/cartOrder") 
	public String cartOrder(OrderVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{

		// 세션에 주문코드가 없으면 에러, 장바구니로 돌려보냄
		if(session.getAttribute("orderCd") == null || session.getAttribute("orderCd").toString().isEmpty()){
			// 주문 정보 없음 에러 처리
			MethodUtil.alertMsgUrl(request, response, "주문 정보가 존재하지 않습니다!", "/cart.do");
			return null;
		}

		String returnStr = null;
		
		UserInfo userInfo = UserInfo.getUserInfo();
		SqlMap billkeyInfo =  new SqlMap();

		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
			vo.setRegIdx(userInfo.getMemberIdx());
			vo.setMemberIdx(userInfo.getMemberIdx());
			vo.setMemberId(userInfo.getMemberId());
			vo.setMemberGradeIdx(Integer.parseInt(userInfo.getLevelIdx()));
			vo.setMemberNm(userInfo.getMemberNm());
			
			SqlMap memberDetail = orderService.getMemberDetail(vo.getMemberIdx());	// 회원 상세
			vo.setSenderEmail((String)memberDetail.get("email"));
			vo.setSenderPhoneNo(((String)memberDetail.get("phoneNo")).replaceAll("-", ""));	// 휴대폰번호에서 - 제거

			List<SqlMap> addressList = orderService.getAddressList(vo);	// 배송지 리스트
			List<SqlMap> billkeyList = orderService.getBillkeyList(vo.getMemberIdx());	// 빌키 리스트
			billkeyInfo = orderService.selectMainBillkey(vo.getMemberIdx());	// 대표 빌키
			
			model.addAttribute("memberDetail", memberDetail);
			model.addAttribute("addressList", addressList);
			model.addAttribute("billkeyList", billkeyList);
			model.addAttribute("billkeyInfo", billkeyInfo);

			// 회원 포인트
			SqlMap memberInfo = cartService.getMemberPoint(userInfo.getMemberIdx());
			if(memberInfo != null){
				model.addAttribute("point", memberInfo.get("pointPrice"));
			}
			
			CartVO cartVO = new CartVO();			
			cartVO.setRegIdx(userInfo.getMemberIdx());
			cartVO.setMemberIdx(userInfo.getMemberIdx());
			
			int couponCnt = cartService.getMemberCouponCnt(cartVO);
			model.addAttribute("couponCnt", couponCnt);

			returnStr = PathUtil.getCtx()+"/order/orderMember";
		}else{	// 비회원
			vo.setRegIdx(0);
			
			returnStr = PathUtil.getCtx()+"/order/orderNoMember";
		}
		
		vo.setRegHttpUserAgent(request.getHeader("user-agent"));	// HTTP_USER_AGENT
		
		InetAddress local = InetAddress.getLocalHost();	// LOCAL IP ADDRESS
		vo.setRegIp(local.getHostAddress());
		
		vo.setOrderCd(session.getAttribute("orderCd").toString());		// 주문 코드
		
		List<SqlMap> mailList = getCodeList("MAIL_KIND");				// 메일 리스트
		List<SqlMap> orderMemoList = getCodeList("ORDER_MEMO");	// 배송시 요청사항 리스트
		List<SqlMap> bankList = getCodeList("BANK_CODE");				// 은행 리스트
		List<SqlMap> list = orderService.getOrderGoodsList(vo);						// 주문 상품 리스트
		SqlMap orderInfo = orderService.getOrderInfo(vo);								// 주문 마스터 정보(결제전건만)
		SqlMap cardPlan = commonService.getHtmlinfo("HTMLINFO10");															// HTML 정보관리 (무이자 할부 안내)
		SqlMap cardBenefit = commonService.getHtmlinfo("HTMLINFO20");														// HTML 정보관리 (카드할인혜택)

		// 배송비
		double shippingPrice = 2500;	// 일단 2500원으로 초기화			
		if(list != null){
			double orderPriceSum = 0;
			for(int i=0;i<list.size();i++){
				orderPriceSum += Double.parseDouble(list.get(i).get("discountPrice").toString()) * Integer.parseInt(list.get(i).get("orderCnt").toString());
			}
			
			shippingPrice = orderService.getShippingPrice(orderPriceSum);	// 배송비 반환
		}
		

		// 네이버 타임보드 이벤트 (2018-04-23 회원 가입자 대상)면 배송비 0원 처리
		if(list != null && list.size() == 1 && list.get(0).get("goodsIdx") != null && (list.get(0).get("goodsIdx").toString().equals("823") )){
			shippingPrice = 0;
		}

		List<SqlMap> payBanefitBanner = commonService.getMainBannerList("BANNER_PAYBENEFIT"); // 결제혜택배너 리스트
		SqlMap bannerInfo = commonService.getBannerOne("CART_ORDER_BANNER");	// 주문결제 배너
		SqlMap smileBanner = commonService.getBannerOne("SMILEPAY_BANNER");		// 스마일페이 배너
		SqlMap paycoBanner = commonService.getBannerOne("PAYCO_BANNER");			// 페이코 배너
		SqlMap paynowBanner = commonService.getBannerOne("PAYNOW_BANNER");	// 페이나우 배너
		SqlMap wonderpayBanner = commonService.getBannerOne("WONDERPAY_BANNER");	// 원더페이 배너
		
		model.addAttribute("mailList",mailList);
		model.addAttribute("orderMemoList",orderMemoList);
		model.addAttribute("bankList",bankList);
		model.addAttribute("list", list);
		model.addAttribute("orderInfo", orderInfo);
		model.addAttribute("VO", vo);
		model.addAttribute("shippingPrice", shippingPrice);
		model.addAttribute("cardPlan", cardPlan);
		model.addAttribute("cardBenefit", cardBenefit);
		model.addAttribute("payBenefitBanner", payBanefitBanner);
		model.addAttribute("bannerInfo", bannerInfo);
		model.addAttribute("smileBanner", smileBanner);
		model.addAttribute("paycoBanner", paycoBanner);
		model.addAttribute("paynowBanner", paynowBanner);
		model.addAttribute("wonderpayBanner", wonderpayBanner);

		// 사은품 정보
		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
			// 네이버 타임보드 이벤트 (2018-04-23 회원 가입자 대상)면 배송비 0원 처리
			if( !( list != null && list.size() == 1 && list.get(0).get("goodsIdx") != null && (list.get(0).get("goodsIdx").toString().equals("823") )) )
			{

				List<SqlMap> freeGiftList = orderService.getGiftList("Y", vo.getOrderCd());				// 무료 사은품 리스트
				List<SqlMap> priceGiftList = orderService.getGiftList("N", vo.getOrderCd());			// 금액별 사은품 리스트
				SqlMap noGiftSelectList = orderService.getNoGiftSelectList(vo.getOrderCd());			// 사은품 선택 안함 1개			
	
				model.addAttribute("freeGiftList", freeGiftList);
				model.addAttribute("priceGiftList", priceGiftList);
				model.addAttribute("noGiftSelectList", noGiftSelectList);
			}
		}
		
		String orderIdx = orderService.getOrderIdx(vo);	// 주문 번호 반환
		if(orderIdx == null || orderIdx.isEmpty()){
			MethodUtil.alertMsgUrl(request, response, "주문 번호가 존재하지 않습니다!", "/cart.do");
			return null;
		}
		
		// LG U+ 결제 관련
		XPayReqVO xpayVO = new XPayReqVO();
		
		//xpay js url
		final String XPAYJSURL = SpringMessage.getMessage("xpay.JSURL");  // https://xpay.uplus.co.kr/xpay/js/xpay_crossplatform.js
		final String LGD_MERTKEY = SpringMessage.getMessage("xpay.MERTKEY");  //"c7ecf8d164fec7c46d4d049c8700d423";
		//가상계좌 입금시 return URL
		String LGD_CASNOTEURL = getCurrentUrl() + SpringMessage.getMessage("xpay.CASNOTEURL");  // "https://222.112.106.57:8443/w" + "/order/casnoteurl.do";
		if(PathUtil.getDeviceNm().equals("M")){		// 모바일 결제시 m 으로 URL 변경
			LGD_CASNOTEURL = LGD_CASNOTEURL.replace("/w/", "/m/");
		}

		//빌키등록시 return URL
		String LGD_BILLKEYREGRETURNURL = getCurrentUrl() + SpringMessage.getMessage("xpay.LGD_BILLKEYREGRETURNURL");  // "https://222.112.106.57:8443/w" + "/order/billkeyRegReturn.do";
		if(PathUtil.getDeviceNm().equals("M")){		// 모바일 결제시 m 으로 URL 변경
			LGD_BILLKEYREGRETURNURL = LGD_BILLKEYREGRETURNURL.replace("/w/", "/m/");
		}
		//빌키 MID
		final String BILLMID = SpringMessage.getMessage("xpay.BILLMID");
		final String BILLMERTKEY = SpringMessage.getMessage("xpay.BILLMERTKEY");
		
    	xpayVO.setLGD_OID(session.getAttribute("orderCd").toString());	// 주문 코드
    	if(orderInfo == null || orderInfo.get("memberId") == null){
    		xpayVO.setLGD_BUYERID("");
    	}else{
    		xpayVO.setLGD_BUYERID(orderInfo.get("memberId").toString());
    	}
    	if(orderInfo == null || orderInfo.get("senderNm") == null){
    		xpayVO.setLGD_BUYER("");
    	}else{
    		xpayVO.setLGD_BUYER(orderInfo.get("senderNm").toString());
    	}
    	if(orderInfo == null || orderInfo.get("senderEmail") == null){
    		xpayVO.setLGD_BUYEREMAIL("");
    	}else{
    		xpayVO.setLGD_BUYEREMAIL(orderInfo.get("senderEmail").toString());
    	}
    	if(orderInfo == null || orderInfo.get("productInfo") == null){
    		xpayVO.setLGD_PRODUCTINFO("");
    	}else{
    		xpayVO.setLGD_PRODUCTINFO(orderInfo.get("productInfo").toString());
    	}
    	if(orderInfo == null || orderInfo.get("totalPayPrice") == null){
    		xpayVO.setLGD_AMOUNT("");  															//결제금액
    	}else{
    		xpayVO.setLGD_AMOUNT(Integer.toString(Double.valueOf(orderInfo.get("totalPayPrice").toString()).intValue()));  		//결제금액 (정수형으로 보내야 에러가 안 난다)
    	}
    	if(orderInfo == null || orderInfo.get("senderPhoneNo") == null){
    		xpayVO.setLGD_BUYERPHONE("");  													//전화번호
    	}else{
    		xpayVO.setLGD_BUYERPHONE(orderInfo.get("senderPhoneNo").toString());  //전화번호
    	}
		
    	// 에스크로 상품 정보
    	List<XPayEscrowGoodsVO> escrowGoodsList = new ArrayList<>();
    	
    	if(list != null){
    		for(int i=0;i<list.size();i++){
    			XPayEscrowGoodsVO escrowGoodsVO = new XPayEscrowGoodsVO();
    			
    			escrowGoodsVO.setLGD_ESCROW_GOODID(nvl(list.get(i).get("goodsIdx")));			// 에스크로상품번호
    			escrowGoodsVO.setLGD_ESCROW_GOODNAME(nvl(list.get(i).get("goodsNm")));		// 에스크로상품명
    			escrowGoodsVO.setLGD_ESCROW_GOODCODE(nvl(list.get(i).get("goodsCd")));		// 에스크로상품코드
    			escrowGoodsVO.setLGD_ESCROW_UNITPRICE(Integer.toString(Double.valueOf(nvl(list.get(i).get("discountPrice"))).intValue()));	// 에스크로상품금액
    			escrowGoodsVO.setLGD_ESCROW_QUANTITY(nvl(list.get(i).get("orderCnt")));			// 에스크로상품수량
    			   					
    			escrowGoodsList.add(escrowGoodsVO);
    		}
    	}
    	
		//1.초기화
		initXPayReq(xpayVO);
		//2.해시키 세팅
		String HASHDATA = makeHashkey(xpayVO.getLGD_MID(), xpayVO.getLGD_OID(),xpayVO.getLGD_AMOUNT(), xpayVO.getLGD_TIMESTAMP(), LGD_MERTKEY);
		xpayVO.setLGD_HASHDATA(HASHDATA);
		
		long DAY_IN_MS = 1000 * 60 * 60 * 24;
		Date closedate =  new Date(System.currentTimeMillis() + (2 * DAY_IN_MS));	// 가상계좌 입금 만료 2일로 수정
		SimpleDateFormat datefmt = new SimpleDateFormat("yyyyMMdd");
		
		//가상계좌 vo
		XPayCyberAccountVO cyberVo = new XPayCyberAccountVO();
		cyberVo.setCST_PLATFORM(xpayVO.getCST_PLATFORM());
		cyberVo.setCST_MID(xpayVO.getCST_MID());
		cyberVo.setLGD_MID(xpayVO.getLGD_MID());
		cyberVo.setLGD_TXNAME("CyberAccount");
		cyberVo.setLGD_METHOD("ASSIGN");
		cyberVo.setLGD_OID(xpayVO.getLGD_OID());
		cyberVo.setLGD_AMOUNT(xpayVO.getLGD_AMOUNT());
		cyberVo.setLGD_CASNOTEURL(LGD_CASNOTEURL);  //무통장입금 결과 수신페이지 URL
		cyberVo.setLGD_MID(xpayVO.getLGD_MID());
		cyberVo.setLGD_BUYERPHONE(xpayVO.getLGD_BUYERPHONE());
		cyberVo.setLGD_BUYER(xpayVO.getLGD_BUYER());
		cyberVo.setLGD_ACCOUNTOWNER(xpayVO.getLGD_BUYER()); //입금자명
		cyberVo.setLGD_BUYERID(xpayVO.getLGD_BUYERID());
		cyberVo.setLGD_BUYEREMAIL(xpayVO.getLGD_BUYEREMAIL());
		cyberVo.setLGD_PRODUCTINFO(xpayVO.getLGD_PRODUCTINFO());
		cyberVo.setLGD_BANKCODE("");   
		cyberVo.setLGD_CASHCARDNUM("");   
		cyberVo.setLGD_CASHRECEIPTUSE("");
		cyberVo.setLGD_ESCROW_USEYN("");		
		cyberVo.setLGD_CLOSEDATE(datefmt.format(closedate)+"235959");		// 만기일 23시 59분 59초까지   
		

		//원클릭결제카드등록
		String LGD_BILLMID    = ("test".equals(xpayVO.getCST_PLATFORM().trim())?"t":"")+BILLMID;  //테스트 아이디는 't'를 제외하고 입력하세요.
		
		XPayBillKeyVO  billkeyRegVo = new XPayBillKeyVO();
		billkeyRegVo.setCST_MID(LGD_BILLMID);
		billkeyRegVo.setLGD_MID(LGD_BILLMID);
		billkeyRegVo.setLGD_RETURNURL(LGD_BILLKEYREGRETURNURL); 
		billkeyRegVo.setCST_PLATFORM(xpayVO.getCST_PLATFORM());
		billkeyRegVo.setLGD_WINDOW_TYPE("iframe");		
		billkeyRegVo.setLGD_OSTYPE_CHECK(PathUtil.getDeviceNm());
		if(PathUtil.getDeviceNm().equals("M")){		// 모바일 결제시 스마트폰 신용카드 빌링타입 정보로 세팅
			billkeyRegVo.setLGD_PAYWINDOWTYPE("CardBillingAuth_smartphone");
			billkeyRegVo.setLGD_WINDOW_TYPE("popup");
		}

		XPayBillKeyAuthVO billkeyAuthVo = new XPayBillKeyAuthVO();		
		billkeyAuthVo.setCST_PLATFORM(xpayVO.getCST_PLATFORM());
		billkeyAuthVo.setLGD_MID(LGD_BILLMID);
		billkeyAuthVo.setLGD_TXNAME("CardAuth");
		if(billkeyInfo == null || billkeyInfo.size() == 0){
			billkeyAuthVo.setLGD_PAN("");  //빌링키값
		}else{
			billkeyAuthVo.setLGD_PAN(billkeyInfo.get("billkey").toString());  //빌링키값
		}
		billkeyAuthVo.setLGD_INSTALL("00");
		billkeyAuthVo.setLGD_OID(xpayVO.getLGD_OID());
		billkeyAuthVo.setLGD_AMOUNT(xpayVO.getLGD_AMOUNT());
		billkeyAuthVo.setLGD_BUYERPHONE(xpayVO.getLGD_BUYERPHONE());
		billkeyAuthVo.setLGD_BUYER(xpayVO.getLGD_BUYER());
		billkeyAuthVo.setLGD_BUYERID(xpayVO.getLGD_BUYERID());
		billkeyAuthVo.setLGD_BUYEREMAIL(xpayVO.getLGD_BUYEREMAIL());
		billkeyAuthVo.setLGD_PRODUCTINFO(xpayVO.getLGD_PRODUCTINFO());		

		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
			List<ProcCouponVO> cartCouponList = new ArrayList<ProcCouponVO>();
			cartCouponList = orderService.procUsableCouponList(Integer.parseInt(orderIdx), 0, "C"); //장바구니쿠폰 목록
			List<ProcCouponVO> shippingCouponList = new ArrayList<ProcCouponVO>();
			shippingCouponList = orderService.procUsableCouponList(Integer.parseInt(orderIdx), 0, "S"); //배송비쿠폰 목록

			model.addAttribute("cartCouponList",cartCouponList);
			model.addAttribute("shippingCouponList",shippingCouponList);
		}
	    model.addAttribute("reqMap", xpayVO);
	    model.addAttribute("cyberMap", cyberVo);
	    model.addAttribute("billkeyRegMap", billkeyRegVo);
	    model.addAttribute("xpayJsUrl",XPAYJSURL);
	    model.addAttribute("reqMapHiddenParams" , ObjectToHiddenParams(xpayVO));
	    model.addAttribute("cyberMapHiddenParams" , ObjectToHiddenParams(cyberVo));
	    model.addAttribute("billkeyRegMapHiddenParams" , ObjectToHiddenParams(billkeyRegVo));
	    model.addAttribute("billkeyAuthMapHiddenParams" , ObjectToHiddenParams(billkeyAuthVo));
	    model.addAttribute("escrowGoodsHiddenParams" , ListObjectToHiddenParams(escrowGoodsList));
	    
	    // 모바일 결제는 input hidden이 아니라 세션에 보관함
	    if(PathUtil.getDeviceNm().equals("M")){
	    	HashMap payReqMap = new HashMap();
	    	payReqMap.put("CST_PLATFORM"						, xpayVO.getCST_PLATFORM());							// 테스트, 서비스 구분
	    	payReqMap.put("CST_MID"								, xpayVO.getCST_MID() );									// 상점아이디
	    	payReqMap.put("CST_WINDOW_TYPE"				, xpayVO.getLGD_WINDOW_TYPE() );				// 전송방식 구분
	    	payReqMap.put("LGD_MID"								, xpayVO.getLGD_MID() );								// 상점아이디
	    	payReqMap.put("LGD_OID"								, xpayVO.getLGD_OID() );								// 주문번호
	    	payReqMap.put("LGD_BUYER"							, xpayVO.getLGD_BUYER() );								// 구매자
	    	payReqMap.put("LGD_PRODUCTINFO"					, xpayVO.getLGD_PRODUCTINFO() );					// 상품정보
	    	payReqMap.put("LGD_AMOUNT"							, xpayVO.getLGD_AMOUNT() );							// 결제금액
	    	payReqMap.put("LGD_BUYEREMAIL"					, xpayVO.getLGD_BUYEREMAIL() );					// 구매자 이메일
	    	payReqMap.put("LGD_CUSTOM_SKIN"					, xpayVO.getLGD_CUSTOM_SKIN() );					// 결제창 SKIN
	    	payReqMap.put("LGD_CUSTOM_PROCESSTYPE"	, xpayVO.getLGD_CUSTOM_PROCESSTYPE() );		// 트랜잭션 처리방식
	    	payReqMap.put("LGD_TIMESTAMP"						, xpayVO.getLGD_TIMESTAMP() );						// 타임스탬프
	    	payReqMap.put("LGD_HASHDATA"						, xpayVO.getLGD_HASHDATA() );						// MD5 해쉬암호값
	    	payReqMap.put("LGD_RETURNURL"					, xpayVO.getLGD_RETURNURL() );						// 응답수신페이지
	    	payReqMap.put("LGD_VERSION"						, "JSP_Non-ActiveX_SmartXPay");						// 버전정보 (삭제하지 마세요)
	    	payReqMap.put("LGD_CUSTOM_FIRSTPAY"			, xpayVO.getLGD_CUSTOM_USABLEPAY() );			// 디폴트 결제수단
	    	payReqMap.put("LGD_PCVIEWYN"						, xpayVO.getLGD_PCVIEWYN() );						// 휴대폰번호 입력 화면 사용 여부
	    	
	    	payReqMap.put("LGD_CUSTOM_SWITCHINGTYPE", "SUBMIT" );							// 신용카드 카드사 인증 페이지 연동 방식
	    	

	    	//iOS 연동시 필수
	    	payReqMap.put("LGD_MPILOTTEAPPCARDWAPURL"	, xpayVO.getLGD_MPILOTTEAPPCARDWAPURL() );


	    	/*
	    	****************************************************
	    	* 신용카드 ISP(국민/BC)결제에만 적용 - BEGIN 
	    	****************************************************
	    	*/
	    	payReqMap.put("LGD_KVPMISPWAPURL"			, xpayVO.getLGD_KVPMISPWAPURL() );	
	    	payReqMap.put("LGD_KVPMISPCANCELURL"		, xpayVO.getLGD_KVPMISPCANCELURL() );
	    	/*
	    	****************************************************
	    	* 신용카드 ISP(국민/BC)결제에만 적용  - END
	    	****************************************************
	    	*/
	    		
	    	/*
	    	****************************************************
	    	* 계좌이체 결제에만 적용 - BEGIN 
	    	****************************************************
	    	*/
	    	payReqMap.put("LGD_MTRANSFERWAPURL"			, xpayVO.getLGD_MTRANSFERWAPURL() );	
	    	payReqMap.put("LGD_MTRANSFERCANCELURL"		, xpayVO.getLGD_MTRANSFERCANCELURL() );
	    	
	    	/*
	    	****************************************************
	    	* 계좌이체 결제에만 적용  - END
	    	****************************************************
	    	*/
	    	
	    	
	    	/*
	    	****************************************************
	    	* 모바일 OS별 ISP(국민/비씨), 계좌이체 결제 구분 값
	    	****************************************************
	    	1) Web to Web
	    	- 안드로이드: A (디폴트)
	    	- iOS: N
	    	  ** iOS일 경우, 반드시 N으로 값을 수정
	    	2) App to Web(반드시 SmartXPay_AppToWeb_연동가이드를 참조합니다.)
	    	- 안드로이드, iOS: A
	    	*/
	    	payReqMap.put("LGD_KVPMISPAUTOAPPYN"	, xpayVO.getLGD_KVPMISPAUTOAPPYN());					// 신용카드 결제 사용시 필수
	    	payReqMap.put("LGD_MTRANSFERAUTOAPPYN"	, xpayVO.getLGD_MTRANSFERAUTOAPPYN());			// 계좌이체 결제 사용시 필수

	    	// 가상계좌(무통장) 결제연동을 하시는 경우  할당/입금 결과를 통보받기 위해 반드시 LGD_CASNOTEURL 정보를 LG 유플러스에 전송해야 합니다 .
	    	payReqMap.put("LGD_CASNOTEURL"		, LGD_CASNOTEURL );			// 가상계좌 NOTEURL
	    	
	    	// Return URL에서 인증 결과 수신 시 셋팅될 파라미터들
	    	payReqMap.put("LGD_RESPCODE"		, "" );
	    	payReqMap.put("LGD_PAYKEY"			, "" );
	    	payReqMap.put("LGD_RESPMSG"		, "" );

	    	session.setAttribute("PAYREQ_MAP", payReqMap);
	    }
	    
	    /* KCP 결재관련 */
	    KcpReqVO kcpVO = new KcpReqVO();
		final String kcpSiteCd = SpringMessage.getMessage("kcp.siteCd"); 
		final String SiteName =  SpringMessage.getMessage("kcp.siteName");
		final String returnMobileUrl =  SpringMessage.getMessage("kcp.returnMobileUrl");
		
	    kcpVO.setReq_tx("pay");
	    kcpVO.setSite_name(SiteName); //상점명
	    kcpVO.setSite_cd(kcpSiteCd); //상점코드 A834W
	    kcpVO.setOrdr_idxx(session.getAttribute("orderCd").toString()); //상점주문번호
	    kcpVO.setPay_method("111010000000"); //신용카드,계좌이체,가상계좌,포인트,휴대폰,상품권
	    
		if(orderInfo == null || orderInfo.get("productInfo") == null){
			kcpVO.setGood_name("");
    	}else{
    		kcpVO.setGood_name(orderInfo.get("productInfo").toString());
    	}
    	if(orderInfo == null || orderInfo.get("totalPayPrice") == null){
    		kcpVO.setGood_mny(0);  															//결제금액
    	}else{
    		kcpVO.setGood_mny(Double.valueOf(orderInfo.get("totalPayPrice").toString()).intValue());  		//결제금액 (정수형으로 보내야 에러가 안 난다)
    	}
    	if(orderInfo == null || orderInfo.get("memberId") == null){
    		kcpVO.setShop_user_id("");
    	}else{
    		kcpVO.setShop_user_id(orderInfo.get("memberId").toString());
    	}
    	if(orderInfo == null || orderInfo.get("senderNm") == null){
    		kcpVO.setBuyr_name("");
    	}else{
    		kcpVO.setBuyr_name(orderInfo.get("senderNm").toString());
    	}
    	if(orderInfo == null || orderInfo.get("senderEmail") == null){
    		kcpVO.setBuyr_mail("");
    	}else{
    		kcpVO.setBuyr_mail(orderInfo.get("senderEmail").toString());
    	}
    	
    	if(orderInfo == null || orderInfo.get("senderPhoneNo") == null){
    		kcpVO.setBuyr_tel2("");  													//휴대폰
    	}else{
    		kcpVO.setBuyr_tel2(orderInfo.get("senderPhoneNo").toString());  //휴대폰
    	}
    	
    	if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
    		kcpVO.setShop_user_id(userInfo.getMemberId());
    	}
    	else  {
    		kcpVO.setShop_user_id("");
    	}
    	
    	//모바일결제
	    if(PathUtil.getDeviceNm().equals("M")){
	    	kcpVO.setShop_name(SiteName);	
	    	kcpVO.setResponse_type("JSON");
	    	kcpVO.setRet_URL(returnMobileUrl);
	    	kcpVO.setCurrency("410");  //원화
	    	kcpVO.setActionResult("");
	    	kcpVO.setApproval_key("");
	    	kcpVO.setPayUrl("");
	    	kcpVO.setIpgm_date(""); 
	    	kcpVO.setUsed_bank("") ; 
	    	kcpVO.setHp_comm_id("");
	    	kcpVO.setTraceNo("");
	    }

    	// 에스크로 관련
    	kcpVO.setEscw_used("N");
    	kcpVO.setPay_mod("N");
    	kcpVO.setDeli_term("02");
    	kcpVO.setRcvr_name(orderInfo.get("receiverNm") == null ? "" : orderInfo.get("receiverNm").toString() );
    	kcpVO.setRcvr_tel1(orderInfo.get("receiverTelNo") == null ? "" : orderInfo.get("receiverTelNo").toString() );  // N 수취자 전화번호
    	kcpVO.setRcvr_tel2(orderInfo.get("receiverPhoneNo") == null ? "" : orderInfo.get("receiverPhoneNo").toString() ); //N 수취자 휴대폰번호 
    	kcpVO.setRcvr_mail("" ); // 수취자 이메일 9 
    	kcpVO.setRcvr_zipx(orderInfo.get("receiverZipCd") == null ? "" : orderInfo.get("receiverZipCd").toString() ); //수취자 우편번호 
    	kcpVO.setRcvr_add1(orderInfo.get("receiverAddr") == null ? "" : orderInfo.get("receiverAddr").toString() ); // 수취자 주소 
    	kcpVO.setRcvr_add2(orderInfo.get("receiverAddrDetail") == null ? "" : orderInfo.get("receiverAddrDetail").toString() ); // 수취자 상세주소 
    	char char30 = 30;
    	char char31 = 31;
    	String chr30 = String.valueOf(char30);	// ASCII 코드값 30
    	String chr31 = String.valueOf(char31);	// ASCII 코드값 31
    	String good_info = "";
    	if(list != null){
    		kcpVO.setBask_cntx(String.valueOf(list.size())); //상품개수
    		for(int i=0;i<list.size();i++){
    			String goodsOrderCD = session.getAttribute("orderCd").toString()+"_"+nvl(list.get(i).get("goodsCd")); //상품주문번호
   			 	if (i > 0) {
   			 		good_info = good_info +chr30;
   			 	}    			
    			 good_info = good_info + "seq="+String.valueOf(i+1) + chr31 + "ordr_numb="+goodsOrderCD+ chr31 + "good_name=" + nvl(list.get(i).get("goodsNm")) + chr31 + "good_cntx="+ nvl(list.get(i).get("orderCnt"))+ chr31 + "good_amtx="+Integer.toString(Double.valueOf(nvl(list.get(i).get("discountPrice"))).intValue());
    		}
    	}
    	kcpVO.setGood_info(good_info);
    	
    	String g_conf_js_url= SpringMessage.getMessage("kcp.jsPath");
    	model.addAttribute("gConfJsUrl",g_conf_js_url);
	    model.addAttribute("kcpVO" , kcpVO);
	    model.addAttribute("kcpHiddenParams" , ObjectToHiddenParams(kcpVO));
		
    	

	    /*smilePay 인증 관련 초기화*/
		final String smilePayRetPcUrl = SpringMessage.getMessage("smilepay.redirectPcUrl");
		final String smilePayRetMoUrl = SpringMessage.getMessage("smilepay.redirectMobileUrl");
		String smilePayRetUrl = "";
		if(PathUtil.getDeviceNm().equals("M")){	
			smilePayRetUrl = smilePayRetMoUrl;
		}
		else {
			smilePayRetUrl = smilePayRetPcUrl;
		}

	    double totalPayPrice = Double.valueOf(String.valueOf(orderInfo.get("totalPayPrice")));
		HashMap<String, Object> result = SmilepayUtil.initInfo((int)totalPayPrice , String.valueOf(orderInfo.get("memberNm")));
		result.put("returnUrl2", smilePayRetUrl);
		model.addAttribute("smilepay",result);
		
	    /*wonderPay 인증 관련 초기화*/
		final String wonderDomain = SpringMessage.getMessage("wonderpay.domain"); //"https://pay-stg.wonder-pay.com"
		final String wonderAppKey = SpringMessage.getMessage("wonderpay.appKey");   // "4toG23FN0cksHA4HSuzdSRK__";
		final String wonderSiteCd = SpringMessage.getMessage("wonderpay.siteCd"); //  = "A8940";
		final String wonderRetPcUrl = SpringMessage.getMessage("wonderpay.returnPcUrl");
		final String wonderRetMoUrl = SpringMessage.getMessage("wonderpay.returnMoUrl");
		String wonderRetUrl = "";
		WonderAuthReqVO  wonderReqVo = new WonderAuthReqVO();
		if(PathUtil.getDeviceNm().equals("M")){	
			wonderReqVo.setCallUrl(wonderDomain+"/wpay/b2/"+wonderAppKey+"/m/req");  //호출할URL  /wpay/b2/{appKey}/req (PC) , /wpay/b2/{appKey}/m/req (Mobile)
			wonderRetUrl = wonderRetMoUrl;
		}
		else {
			wonderReqVo.setCallUrl(wonderDomain+"/wpay/b2/"+wonderAppKey+"/req");  //호출할URL  /wpay/b2/{appKey}/req (PC) , /wpay/b2/{appKey}/m/req (Mobile)
			wonderRetUrl = wonderRetPcUrl;
		}
		wonderReqVo.setSiteCd(wonderSiteCd);
		wonderReqVo.setGoodName(orderInfo.get("productInfo").toString()); //상품정보
		wonderReqVo.setGoodMny(String.valueOf(kcpVO.getGood_mny()));
		wonderReqVo.setBuyrName(kcpVO.getBuyr_name());
		wonderReqVo.setBuyrMail(kcpVO.getBuyr_mail());
		wonderReqVo.setBuyrTel2(kcpVO.getBuyr_tel2());  //휴대폰번호
		wonderReqVo.setOrdrIdxx(kcpVO.getOrdr_idxx()); //주문번호
		wonderReqVo.setRetUrl(wonderRetUrl);
		wonderReqVo.setOrdrInfo(getMD5Wonder(wonderSiteCd, kcpVO.getOrdr_idxx(), String.valueOf(kcpVO.getGood_mny()), kcpVO.getBuyr_mail(),kcpVO.getBuyr_tel2())); //site_cd + ordr_idxx + good_mny + buyr_mail + buyr_tel2	의 md5 값
		wonderReqVo.setBuyrTel1((orderInfo.get("senderTelNo") == null)? "" : orderInfo.get("senderTelNo").toString());
		 
		model.addAttribute("wonderpay",wonderReqVo);
		
		return returnStr;	
	}
	
	  /**
	    * @Method : wonderOrderInfoReAjax
	    * @Date: 2018. 11. 04
	    * @Author :   강 병 철
	    * @Description	:	원더페이 정보 재조회(주문자정보 및 암호화코드)
	   */
	@RequestMapping(value = "/ajax/order/wonderOrderInfoReAjax", method = RequestMethod.POST) 
	public @ResponseBody WonderAuthReqVO wonderOrderInfoReAjax(OrderVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		final String wonderSiteCd = SpringMessage.getMessage("wonderpay.siteCd");

		WonderAuthReqVO  wonderReqVo = new WonderAuthReqVO();	
		try {
			UserInfo userInfo = UserInfo.getUserInfo();

			if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
				vo.setRegIdx(userInfo.getMemberIdx());
				vo.setMemberIdx(userInfo.getMemberIdx());
				vo.setMemberId(userInfo.getMemberId());
				vo.setMemberGradeIdx(Integer.parseInt(userInfo.getLevelIdx()));
				vo.setMemberNm(userInfo.getMemberNm());
			}else{	// 비회원
				vo.setRegIdx(0);
			}
			SqlMap orderInfo = orderService.getOrderInfo(vo);								// 주문 마스터 정보(결제전건만)
			String buyrName = (orderInfo.get("senderNm") == null) ? "" : orderInfo.get("senderNm").toString();
			String buyrTel2 = (orderInfo.get("senderPhoneNo") == null) ? "" : orderInfo.get("senderPhoneNo").toString();
			String buyrMail = (orderInfo.get("senderMail") == null) ? "" : orderInfo.get("senderMail").toString();
			String goodMny = (orderInfo.get("totalPayPrice") == null) ? "0" : orderInfo.get("totalPayPrice").toString();		
			String payprice = Integer.toString(Double.valueOf(goodMny).intValue()); // double형을 int형으로
			String mdResult = getMD5Wonder(wonderSiteCd, vo.getOrderCd(), payprice, buyrMail,buyrTel2);
	    	
			wonderReqVo.setSiteCd("0000");  //결과로 사용
			wonderReqVo.setBuyrName(buyrName);
			wonderReqVo.setBuyrMail(buyrMail);
			wonderReqVo.setBuyrTel2(buyrTel2);  //휴대폰번호
			wonderReqVo.setOrdrInfo(mdResult);
		} catch (Exception e) {
			wonderReqVo.setSiteCd("9999");  //결과로 사용
			wonderReqVo.setRetUrl(e.getMessage());  //결과메시지로 사용
		}
		
		return wonderReqVo;
	}
	
	  /**
	    * @Method : setMD5Wonder
	    * @Date: 2017. 7. 4.
	    * @Author :   강 병 철
	    * @Description	:	Hashkey 만들기
	    * site_cd + ordr_idxx + good_mny + buyr_mail + buyr_tel2 의 MD5값
	   */
		public String getMD5Wonder(String siteCd, String orderId, String Amount, String buyrMail,  String buyrPhone) throws Exception{
				
		    StringBuffer sb = new StringBuffer();
		    sb.append(siteCd);
		    sb.append(orderId);
		    sb.append(Amount);
		    sb.append(buyrMail);
		    sb.append(buyrPhone);
		
		    byte[] bNoti = sb.toString().getBytes();
		    MessageDigest md;
			try {
				md = MessageDigest.getInstance("MD5");
			    byte[] digest = md.digest(bNoti);
		
			    StringBuffer strBuf = new StringBuffer();
			    for (int i=0 ; i < digest.length ; i++) {
			        int c = digest[i] & 0xff;
			        if (c <= 15){
			            strBuf.append("0");
			        }
			        strBuf.append(Integer.toHexString(c));
			    }
			    return strBuf.toString();
			    
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
	 /**
	    * @Method : wonderReturn
	    * @Date: 2018. 11. 02.
	    * @Author :   강 병 철
	    * @Description	:	원더페이 인증 결과 리턴
	   */
	@RequestMapping(value="/order/wonderReturn") 
	public String wonderReturn( WonderAuthResVO vo,  HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
	    model.addAttribute("reqMap", vo);
	    model.addAttribute("HiddenParams" , ObjectToHiddenParams(vo));
	    
		return PathUtil.getCtx()+"/order/wonderReturn";
	}

	 /**
	    * @Method : smilePayReturn
	    * @Date: 2020. 11. 02.
	    * @Author :   강 병 철
	    * @Description	:	스마일페이 인증 결과 리턴
	   */
	@RequestMapping(value="/order/smilePayReturn") 
	public String smilePayReturn(String message,  HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
		message = message.replaceAll("&quot;", "\"");
		model.addAttribute("message", message);
		return PathUtil.getCtx()+"/order/smilePayReturn";
	}
	
	 /**
	    * @Method : getSSLConnect
	    * @Date: 2018. 11. 02.
	    * @Author :   강 병 철
	    * @Description	:	원더페이 인증 통신
	   */
	@SuppressWarnings("unchecked")
	public String getSSLConnect(String apiUrl, String body) throws Exception{
		
		URL url 			   = new URL(apiUrl); 	// 요청을 보낸 URL
		HttpsURLConnection con = null;
		StringBuffer buf 	   = new StringBuffer();
		String returnStr 	   = "";
	
		System.out.println(body);
		try {
			con = (HttpsURLConnection)url.openConnection();
			
			con.setConnectTimeout(30000);		//서버통신 timeout 설정. 30초
			con.setReadTimeout(30000);			//스트림읽기 timeout 설정.30초
			
			con.setRequestProperty("Content-Type", "application/json");
			con.setDoOutput(true);
		    con.setRequestMethod("POST");
		    con.connect();
		    
		    // 송신할 데이터 전송.
		    DataOutputStream dos = new DataOutputStream(con.getOutputStream());
		    dos.write(body.getBytes());
		    dos.flush();
		    dos.close();
		    int resCode = con.getResponseCode();
		    if (resCode == HttpsURLConnection.HTTP_OK) {
		    	BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				int c;
			    
			    while ((c = br.read()) != -1) {
			    	buf.append((char)c);
			    }
			    returnStr = buf.toString();
			    br.close();
		    } else {
		    	returnStr = "{ \"res_cd\" : 9999, \"req_msg\" : \"Connection Error\" }";
		    }
		    
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.disconnect();
		}
		
		return returnStr;
	}
	
	   /**
	    * @Method : wonderRes
	    * @Date: 2018. 11. 02.
	    * @Author :  강 병 철
	    * @Description	:	원더페이 결제 결과
	   */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/order/wonderRes")
	public void wonderRes(WonderAuthResVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
		final String wonderDomain = SpringMessage.getMessage("wonderpay.domain"); //"https://pay-stg.wonder-pay.com"
		final String wonderAppKey = SpringMessage.getMessage("wonderpay.appKey");   // "4toG23FN0cksHA4HSuzdSRK__";			
		Boolean doCancel    = false;						// 승인 후 오류발생시 결제취소 여부
		Boolean doApproval  = true; 					  	// 요청금액과 결제 금액 일치여부(true : 일치)
		String message = "";
		
		XPayResultVO resultVo = new XPayResultVO();
		resultVo.setLGD_RESPCODE(vo.getRes_cd());
		
		int totalPaymentAmt = 0;
		if(vo.getGood_mny() == null || vo.getGood_mny() == ""){								//총 결제금액
			totalPaymentAmt = 0;
		}else{
			totalPaymentAmt = (int)Float.parseFloat(vo.getGood_mny().toString()); 
		}
		
		/* 결제 인증 성공시 */
		if(vo.getRes_cd().equals("0000"))
		{
			/* 수신된 데이터 중 필요한 정보를 추출하여
			 * 총 결제금액과 요청금액이 일치하는지 확인하고,	
			 * 원더페이 결제 승인 API 호출 여부를 판단한다.
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

			OrderVO ordervo = new OrderVO();
			UserInfo userInfo = UserInfo.getUserInfo();		
			if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
				ordervo.setRegIdx(userInfo.getMemberIdx());
				ordervo.setMemberIdx(userInfo.getMemberIdx());
				ordervo.setMemberId(userInfo.getMemberId());
				ordervo.setMemberGradeIdx(Integer.parseInt(userInfo.getLevelIdx()));
				ordervo.setMemberNm(userInfo.getMemberNm());	
			}else{	// 비회원
				ordervo.setRegIdx(0);
			}
			ordervo.setOrderCd(vo.getOrdr_idxx());
			
			SqlMap orderInfo = orderService.getOrderInfo(ordervo);	// 주문 마스터 정보(결제전건만)
			if (orderInfo == null) {
				doApproval = false;
				resultVo.setLGD_RESPCODE("-1");
				message   = "주문번호("+vo.getOrdr_idxx()+")의 주문정보가 존재하지않습니다.";
			}
			else
			{
				int myDBtotalValue = Double.valueOf(orderInfo.get("totalPayPrice").toString()).intValue();						// 가맹점 DB에서 읽은 주문요청 금액
				if(myDBtotalValue != totalPaymentAmt){		// 주문 요청금액과 인증정보로 넘어온 결제금액 비교
					doApproval = false;
					resultVo.setLGD_RESPCODE("-2");
					message   = "주문금액과 결제금액이 틀립니다.";
				}
			}
			
			/* 주문금액과 결제금액이 일치한다고 가정(doApproval = true) */
			if(doApproval) 
			{
				
				String apiUrl = wonderDomain+"/wpay/api/b2/"+wonderAppKey+"/approval"; // /wpay/api/b2/{appKey}/approval
				
				/* 원더페이 승인요청 API 호출 */
				JSONObject json = new JSONObject();
				
				json.put("ordr_idxx", vo.getOrdr_idxx());
				json.put("enc_code", vo.getEnc_code());
				
				String body = json.toString();
				String callresult = getSSLConnect(apiUrl, body);
						
				System.out.println("********************WonderRes (결제인증) 결과***************************************");
				System.out.println(callresult);
				System.out.println("*************************************************************************************");
				
				// jackson Tree 이용
				Map<String, Object> resultMap1 = null;
				Gson gson = new Gson();
				resultMap1 = gson.fromJson(callresult, HashMap.class);
				
				if(resultMap1.get("res_cd").toString().equals("0000")) {
					// 예시
					try{
						/* 결제승인 후 리턴된 데이터 중 필요한 정보를 추출하여
						 * 가맹점 에서 필요한 작업을 실시합니다.(예 주문서 작성 등..)
						 * 가맹점 DB 저장이 필요합니다.
						 */
						 
						// 승인 후 전달받은 데이터 저장 변수 
						 int orderIdx = Integer.parseInt(orderInfo.get("orderIdx").toString()); ///주문번호
						 
						String res_msg, ordr_idxx, site_cd , pay_method , pg_code;
						String tno = "";
						String app_time; //결제완료시간 yyyyMMddHHMISS 
						String appNo;
						int good_mny;
						
						// 즉시출금 결제일경우
						int bk_mny = 0; // 실 거래 금액 number 실제 거래 금액 (승인 금액) 35000
						String bank_cd=""; // 은행 코드 char 결제 은행 코드 BK03
						String cash_receipt_flag=""; // 현금영수증 발급여부 char Y / N

						//카드결제
						int card_mny = 0; //카드 실결제금액 number 실제 거래 금액 (승인 금액) 즉시 할인을 제외한 금액 good_mny = card_mny + coupon_mny
						String card_cd = ""; //결제 카드사 코드 char 결제한 카드사 코드 CCLG
						String quota=""; // 할부 개월 수 number 할부 개월 수 (일시불 : 00) 00
						String noinf=""; // 무이자 여부 char Y / N N
						int coupon_mny = 0; //coupon_mny 쿠폰 적용 금액

						res_msg = resultMap1.get("res_msg") != null ? resultMap1.get("res_msg").toString() : "";
						
						ordr_idxx = resultMap1.get("ordr_idxx") != null ? resultMap1.get("ordr_idxx").toString() : "";
						site_cd	 = resultMap1.get("site_cd") != null ? resultMap1.get("site_cd").toString() : "";
						pay_method	 = resultMap1.get("pay_method") != null ? resultMap1.get("pay_method").toString() : "";
						pg_code	 = resultMap1.get("pg_code") != null ? resultMap1.get("pg_code").toString() : "";
						tno	 = resultMap1.get("tno") != null ? resultMap1.get("tno").toString() : "";
						app_time	 = resultMap1.get("app_time") != null ? resultMap1.get("app_time").toString() : "";
						appNo	 = resultMap1.get("appNo") != null ? resultMap1.get("appNo").toString() : "";
						good_mny	 = resultMap1.get("good_mny") != null ? Integer.parseInt(resultMap1.get("good_mny").toString()) : 0;
						bk_mny	 = resultMap1.get("bk_mny") != null ? Integer.parseInt(resultMap1.get("bk_mny").toString()) : 0;
						bank_cd	 = resultMap1.get("bank_cd") != null ? resultMap1.get("bank_cd").toString() : "";
						cash_receipt_flag	 = resultMap1.get("cash_receipt_flag") != null ? resultMap1.get("cash_receipt_flag").toString() : "";
						card_mny	 = resultMap1.get("card_mny") != null ? Integer.parseInt(resultMap1.get("card_mny").toString()) : 0;
						card_cd	 = resultMap1.get("card_cd") != null ? resultMap1.get("card_cd").toString() : "";
						quota	 = resultMap1.get("quota") != null ? resultMap1.get("quota").toString() : "";
						noinf	 = resultMap1.get("noinf") != null ? resultMap1.get("noinf").toString() : "";
						coupon_mny	 = resultMap1.get("coupon_mny") != null ? Integer.parseInt(resultMap1.get("coupon_mny").toString()) : 0;

						
						try {
								//결과저장
								WonderVO wVo = new WonderVO();     
								
								wVo.setOrder_idx(orderIdx);  //주문테이블  idx
								wVo.setApp_time(app_time);
								wVo.setBank_cd(bank_cd);
								wVo.setBk_mny(bk_mny);
								wVo.setCard_cd(card_cd);
								wVo.setCard_mny(card_mny);
								wVo.setCash_receipt_flag(cash_receipt_flag);
								wVo.setCoupon_mny(coupon_mny);
								wVo.setGood_mny(good_mny);
								wVo.setNoinf(noinf);
								wVo.setOrdr_idxx(ordr_idxx);
								wVo.setPay_method(pay_method);
								wVo.setPg_code(pg_code);
								wVo.setQuota(quota);
								wVo.setRes_cd(resultMap1.get("res_cd").toString());
								wVo.setRes_msg(res_msg);
								wVo.setSite_cd(site_cd);
								wVo.setTno(tno);

					         	// 결제 후 DB 처리 시작-------------------------------------------------------------------------------------------------
					         	JsonResultVO resultMap = new JsonResultVO();
					         	
				         		resultMap = orderService.dbProcessAfterWonder(wVo);	//Wonder페이 결제 후 DB 처리
				         		
				         		if(!resultMap.getResult()){
									resultVo.setLGD_RESPCODE("-2");
									resultVo.setLGD_RESPMSG(resultMap.getMsg());	// 에러 메시지
									doCancel = true;
				         		}
				         		else {
									resultVo.setLGD_RESPCODE("0000");
									message   = "주문이 정상적으로 완료되었습니다.";	
	
									resultVo.setLGD_BUYER(orderInfo.get("senderNm").toString());
									if(!app_time.equals("")){
										resultVo.setLGD_PAYDATE(wVo.getApp_time().toString() );
									}
									resultVo.setLGD_OID(ordr_idxx);
									resultVo.setLGD_AMOUNT(Double.toString(totalPaymentAmt));
				         		}
					         	// 결제 후 DB 처리 끝-------------------------------------------------------------------------------------------------
								
							} catch (Exception e) {
								e.printStackTrace();
								resultVo.setLGD_RESPCODE("-3");
								message   = "주문서 작성중 데이터처리 오류로 인하여 주문이 취소 되었습니다.";
								doCancel = true;
							}
					
						} catch(Exception e){
							e.printStackTrace();
							resultVo.setLGD_RESPCODE("-4");
							message   = "주문서 작성중 데이터처리 오류로 인하여 주문이 취소 되었습니다.";
							doCancel = true;
						}
					
					//---- SMS, 메일발송 (에러나더라도 rollback안함)
		         	try {
		         		if( !doCancel ) {
		         				orderService.callSPSmsSendAndEmail("ORD002",  resultVo.getLGD_OID());		         			
		         		}
		         	}catch(Exception e) {
		         		e.printStackTrace();
		         	}
						
				} else {
					//승인 API 호출시 에러
					resultVo.setLGD_RESPCODE(resultMap1.get("res_cd").toString());
					message   = resultMap1.get("res_msg").toString();
					
					MethodUtil.alertMsgUrl(request, response, message, "/order/cartOrder.do");	// 잔액 부족등 결제 실패시 결제 페이지로 돌림
					return;
				}
				
			//	doCancel = true;
				
				if(doCancel){
					/*★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
					#  원더페이 결제 승인이 완료되고 아래와 같은 상황이 발생하였을경우 예외 처리가 필요합니다.
					1. 가맹점 DB 처리중 오류 발생시
					2. 통신 장애로 인하여 결과를 리턴받지 못했을 경우
					
					위와 같은 상황이 발생하였을 경우 이미 승인 완료된 주문건에 대하여 주문 취소처리(전체취소)가 필요합니다.
					 - 원더페이 에서는 주문승인(결제완료) 처리 되었으나 가맹점은 해당 주문서가 없는 경우가 발생
					 - 원더페이 에서는 승인 완료된 상태이므로 주문 상세정보 API를 이용해 결제정보를
					   조회하여 취소요청 파라미터에 셋팅합니다.
					★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★*/
					String apiUrl2 = wonderDomain+"/wpay/api/b2/"+wonderAppKey+"/cancel";
					/* 원더페이 승인요청 API 호출 */
					JSONObject json2 = new JSONObject();
					String tno	 = resultMap1.get("tno") != null ? resultMap1.get("tno").toString() : "";
					String site_cd	 = resultMap1.get("site_cd") != null ? resultMap1.get("site_cd").toString() : "";
					String ordr_idxx	 = resultMap1.get("ordr_idxx") != null ? resultMap1.get("ordr_idxx").toString() : "";
					String cancel_desc = "DB저장중에러발생 자동취소";
					String good_mny	 = resultMap1.get("good_mny") != null ? resultMap1.get("good_mny").toString() : "";
					json2.put("site_cd", site_cd);
					json2.put("ordr_idxx", ordr_idxx);
					json2.put("tno", tno);
					json2.put("cancel_desc", cancel_desc);
					json2.put("good_mny", good_mny);
					
					String body2 = json2.toString();
					String callresult2 = getSSLConnect(apiUrl2, body2);

					System.out.println("********************WonderPay 취소 호출 ***************************************");
					System.out.println(callresult2);
					System.out.println("*************************************************************************************");
					
					Map<String, Object> resultMap2 = null;
					Gson gson2 = new Gson();
					resultMap2 = gson2.fromJson(callresult2, HashMap.class);
					
					if(!resultMap2.get("res_cd").toString().equals("0000")) {
					 	resultVo.setLGD_RESPCODE(resultMap2.get("res_cd").toString());
						message   = "원더페이 결제 취소 중 에러가 발생하였습니다.("+resultMap2.get("res_msg").toString()+")";
						System.out.println(message);
					}
				} //cancel end
			} 
		}
		resultVo.setLGD_RESPMSG(message);
		MethodUtil.redirectPostUrl(request, response, PathUtil.getCtx()+"/order/orderComplete.do", resultVo);
		//completvo.setResultMessage(message);
		//MethodUtil.redirectPostUrl(request, response, PathUtil.getCtx()+"/payco/paycoComplete.do", completvo); 
	}	

	
	   /**
	    * @Method : cartOrderAjax
	    * @Date: 2017. 7. 12.
	    * @Author :  서 정 길
	    * @Description	:	장바구니에서 주문하기 눌렀을 때 세션에 담긴 주문코드 삭제 처리
	   */
	@RequestMapping(value = "/ajax/cart/cartOrderAjax", method = RequestMethod.POST) 
	public @ResponseBody JsonResultVO cartOrderAjax(OrderVO vo, HttpServletRequest request, HttpSession session) throws Exception{
		
		JsonResultVO resultMap = new JsonResultVO();

		try{
			session.removeAttribute("orderCd");	// 세션 주문 코드 삭제
			resultMap.setResult(true);
		}catch(Exception e){
			e.printStackTrace();
			resultMap.setResult(false);
			resultMap.setMsg("주문 준비 작업 중 에러가 발생했습니다!");
		}

		return resultMap;
	}

	   /**
	    * @Method : saveOrderInfoAjax
	    * @Date: 2017. 7. 17.
	    * @Author :  서 정 길
	    * @Description	:	주문 정보 DB 저장
	   */
	@RequestMapping(value = "/ajax/order/saveOrderInfoAjax", method = RequestMethod.POST) 
	public @ResponseBody JsonResultVO saveOrderInfoAjax(OrderVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		JsonResultVO resultMap = new JsonResultVO();

		UserInfo userInfo = UserInfo.getUserInfo();
		
		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
			vo.setRegIdx(userInfo.getMemberIdx());
			vo.setMemberIdx(userInfo.getMemberIdx());
			vo.setMemberId(userInfo.getMemberId());
		}else{	// 비회원
			vo.setRegIdx(0);
		}

		vo.setRegHttpUserAgent(request.getHeader("user-agent"));	// HTTP_USER_AGENT
		
		InetAddress local = InetAddress.getLocalHost();	// LOCAL IP ADDRESS
		vo.setRegIp(local.getHostAddress());

		try{
/*
			// 주문자 성명 없을 때
			if(vo.getSenderNm().isEmpty()){
				resultMap.setResult(false);
				resultMap.setMsg("주문자 성명을 확인하세요!");
				return resultMap;
			}

			// 주문자 이메일 없을 때
			if(vo.getSenderEmail().isEmpty()){
				resultMap.setResult(false);
				resultMap.setMsg("주문자 이메일을 확인하세요!");
				return resultMap;
			}

			// 주문자 휴대폰 번호 없을 때
			if(vo.getSenderPhoneNo().isEmpty()){
				resultMap.setResult(false);
				resultMap.setMsg("주문자 휴대폰을 확인하세요!");
				return resultMap;
			}

			if(!vo.getAddressTabId().equals("addressID1")){	// 배송지 목록 탭이 아니면
				if(vo.getAddressTabId() != null){
					if(vo.getAddToAddress() != null && vo.getAddToAddress().equals("Y")){	// 배송지 목록에 추가일때
						// 배송지명 없을 때
						if(vo.getShippingNm() == null || vo.getShippingNm().isEmpty()){
							resultMap.setResult(false);
							resultMap.setMsg("배송지명을 확인하세요!");
							return resultMap;
						}
					}
				}

				// 받으시는 분 성명 없을 때
				if(vo.getReceiverNm().isEmpty()){
					resultMap.setResult(false);
					resultMap.setMsg("받으시는 분 성명을 확인하세요!");
					return resultMap;
				}

				// 받으시는 분 휴대폰 번호 없을 때
				if(vo.getReceiverPhoneNo().isEmpty()){
					resultMap.setResult(false);
					resultMap.setMsg("받으시는 분 휴대폰을 확인하세요!");
					return resultMap;
				}

				// 배송지 주소 우편번호, 주소 없을 때
				if(vo.getReceiverZipCd().isEmpty() || vo.getReceiverAddr().isEmpty() || vo.getReceiverAddrDetail().isEmpty()){
					resultMap.setResult(false);
					resultMap.setMsg("배송지 주소를 확인하세요!");
					return resultMap;
				}
			}
*/
			try{
				orderService.updateOrderMasterInfo(vo);	// 주문 정보 DB 저장
			}catch(Exception e){
				e.printStackTrace();
				resultMap.setResult(false);
				resultMap.setMsg("결제 정보 저장 중 에러가 발생했습니다!");
				return resultMap;
			}
			
			resultMap.setResult(true);
		}catch(Exception e){
			e.printStackTrace();
			resultMap.setResult(false);
			resultMap.setMsg("결제 작업 중 에러가 발생했습니다!");
		}

		return resultMap;
	}

	   /**
	    * @Method : saveOrderInfoValidationAjax
	    * @Date: 2017. 7. 18.
	    * @Author :  서 정 길
	    * @Description	:	주문 정보 DB 저장 & 유효성 체크
	   */
	@RequestMapping(value = "/ajax/order/saveOrderInfoValidationAjax", method = RequestMethod.POST) 
	public @ResponseBody JsonResultVO saveOrderInfoValidationAjax(OrderVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		JsonResultVO resultMap = new JsonResultVO();

		UserInfo userInfo = UserInfo.getUserInfo();
		
		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
			vo.setRegIdx(userInfo.getMemberIdx());
			vo.setMemberIdx(userInfo.getMemberIdx());
			vo.setMemberId(userInfo.getMemberId());
		}else{	// 비회원
			vo.setRegIdx(0);
		}

		vo.setRegHttpUserAgent(request.getHeader("user-agent"));	// HTTP_USER_AGENT
		
		InetAddress local = InetAddress.getLocalHost();	// LOCAL IP ADDRESS
		vo.setRegIp(local.getHostAddress());

		try{
			// 주문자 성명 없을 때
			if(vo.getSenderNm() == null || vo.getSenderNm().isEmpty()){
				resultMap.setResult(false);
				resultMap.setMsg("주문자 성명을 확인하세요!");
				return resultMap;
			}

			// 주문자 이메일 없을 때
			if(vo.getSenderEmail() == null || vo.getSenderEmail().isEmpty()){
				resultMap.setResult(false);
				resultMap.setMsg("주문자 이메일을 확인하세요!");
				return resultMap;
			}

			// 주문자 휴대폰 번호 없을 때
			if(vo.getSenderPhoneNo() == null || vo.getSenderPhoneNo().isEmpty()){
				resultMap.setResult(false);
				resultMap.setMsg("주문자 휴대폰을 확인하세요!");
				return resultMap;
			}

			if(vo.getAddressTabId() == null || vo.getAddressTabId().equals("addressID2")){	// 비회원이거나 회원에서 새로입력일때
				if(vo.getAddressTabId() != null){
					if(vo.getAddToAddress() != null && vo.getAddToAddress().equals("Y")){	// 배송지 목록에 추가일때
						// 배송지명 없을 때
						if(vo.getShippingNm() == null || vo.getShippingNm().isEmpty()){
							resultMap.setResult(false);
							resultMap.setMsg("배송지명을 확인하세요!");
							return resultMap;
						}
					}
				}

				// 받으시는 분 성명 없을 때
				if(vo.getReceiverNm() == null || vo.getReceiverNm().isEmpty()){
					resultMap.setResult(false);
					resultMap.setMsg("받으시는 분 성명을 확인하세요!");
					return resultMap;
				}

				// 받으시는 분 휴대폰 번호 없을 때
				if(vo.getReceiverPhoneNo() == null || vo.getReceiverPhoneNo().isEmpty()){
					resultMap.setResult(false);
					resultMap.setMsg("받으시는 분 휴대폰을 확인하세요!");
					return resultMap;
				}

				// 배송지 주소 우편번호, 주소 없을 때
				if(vo.getReceiverZipCd() == null || vo.getReceiverAddr() == null || vo.getReceiverAddrDetail() == null || vo.getReceiverZipCd().isEmpty() || vo.getReceiverAddr().isEmpty() || vo.getReceiverAddrDetail().isEmpty()){
					resultMap.setResult(false);
					resultMap.setMsg("배송지 주소를 확인하세요!");
					return resultMap;
				}
			}

			// 결제 방식
			if(vo.getSelectPayType() == null || vo.getSelectPayType().isEmpty() || 
				(
				 !vo.getSelectPayType().equals("BILLKEY") &&		// ONE CLICK 원클릭 결제
				 !vo.getSelectPayType().equals("SC0010") &&		// 신용카드
				 !vo.getSelectPayType().equals("SC0030") &&		// 실시간 계좌 이체
				 !vo.getSelectPayType().equals("SC0040") &&		// 가상계좌 입금
				 !vo.getSelectPayType().equals("SC0060") &&		// 휴대폰 결제
				 !vo.getSelectPayType().equals("PAYCO") &&			// PAYCO
				 !vo.getSelectPayType().equals("PAYNOW") &&		// PAYNOW
				 !vo.getSelectPayType().equals("SMILEPAY") &&		// SMILEPAY
				 !vo.getSelectPayType().equals("WONDERPAY") &&		// WONDERPAY
				 !vo.getSelectPayType().equals("POINT")				// 포인트 결제 (결제금액 0원)
				)
			){
				resultMap.setResult(false);
				resultMap.setMsg("결제 방식을 확인하세요!");
				return resultMap;
			}

			// 결제수단 (공통코드 PAY_TYPE10 : 신용카드, PAY_TYPE15 : 빌키, PAY_TYPE20 : 실시간계좌, PAY_TYPE25 : 가상계좌, PAY_TYPE30 : 휴대폰, PAY_TYPE35 : PAYCO, PAY_TYPE40 : 카카오페이, PAY_TYPE45 : Npay, PAY_TYPE50 : PAYNOW, PAY_TYPE90 : 포인트)
			if(vo.getSelectPayType().equals("BILLKEY")){	// ONE CLICK 원클릭 결제
				vo.setPayType("PAY_TYPE15");
			}else if(vo.getSelectPayType().equals("SC0010")){	// 신용카드
				vo.setPayType("PAY_TYPE10");
			}else if(vo.getSelectPayType().equals("SC0030")){	// 실시간 계좌 이체
				vo.setPayType("PAY_TYPE20");
			}else if(vo.getSelectPayType().equals("SC0040")){	// 가상계좌 입금
				vo.setPayType("PAY_TYPE25");
			}else if(vo.getSelectPayType().equals("SC0060")){	// 휴대폰 결제
				vo.setPayType("PAY_TYPE30");
			}else if(vo.getSelectPayType().equals("PAYCO")){	// PAYCO
				vo.setPayType("PAY_TYPE35");
			}else if(vo.getSelectPayType().equals("PAYNOW")){	// PAYNOW
				vo.setPayType("PAY_TYPE50");
			}else if(vo.getSelectPayType().equals("SMILEPAY")){	// SMILEPAY
				vo.setPayType("PAY_TYPE55");
			}else if(vo.getSelectPayType().equals("WONDERPAY")){	// WONDERPAY
				vo.setPayType("PAY_TYPE60");
			}else if(vo.getSelectPayType().equals("POINT")){	// 포인트 결제 (결제금액 0원)
				vo.setPayType("PAY_TYPE90");
			}
			
			try{
				int flag = orderService.updateOrderMasterInfo(vo);	// 주문 정보 DB 저장
				
				if(flag == 0){
					resultMap.setErrorCode("100");
					resultMap.setResult(false);
					resultMap.setMsg("주문 정보가 존재하지 않습니다!");
					return resultMap;
				}
			}catch(Exception e){
				e.printStackTrace();
				resultMap.setResult(false);
				resultMap.setMsg("결제 정보 저장 중 에러가 발생했습니다!");
				return resultMap;
			}
						
			if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원이고
				try{
					if(vo.getUseThisPayTypeToNext() != null && vo.getUseThisPayTypeToNext().equals("Y")){	// 지금 선택하신 결제수단을 다음에도 사용 체크면
						orderService.insertDefaultPayment(vo);	// 기본 결제수단 저장
					}else{
						orderService.deleteDefaultPayment(vo);	// 기본 결제수단 삭제
					}
				}catch(Exception e){
					e.printStackTrace();
					resultMap.setResult(false);
					resultMap.setMsg("기본 결제수단 저장 중 에러가 발생했습니다!");
					return resultMap;
				}
			}
			
			String orderIdx = orderService.getOrderIdx(vo);	// 주문 번호 반환
			if(orderIdx == null || orderIdx.isEmpty()){
				MethodUtil.alertMsgUrl(request, response, "주문 번호가 존재하지 않습니다!", "/cart.do");
				return null;
			}

			SqlMap info = orderService.procSpPgCheckBefore(orderIdx);	// 주문 유효성 체크

			if(info == null){
				resultMap.setResult(false);
				resultMap.setMsg("주문 유효성 확인 중 에러가 발생했습니다!");
				return resultMap;
			}else{
				if(!info.get("result").toString().equals("TRUE")){
					resultMap.setResult(false);
					resultMap.setMsg(info.get("result").toString());
					return resultMap;
				}
			}

			if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
				resultMap = orderService.insertPriceGift(vo);	// 구매 금액별 사은품 저장
				
				if(!resultMap.getResult()){
					return resultMap;
				}
			}

			resultMap.setResult(true);
		}catch(Exception e){
			e.printStackTrace();
			resultMap.setResult(false);
			if(resultMap.getMsg() == null || resultMap.getMsg().isEmpty()){
				resultMap.setMsg("결제 작업 중 에러가 발생했습니다!");
			}			
		}

		return resultMap;
	}

	   /**
	    * @Method : deleteAddressAjax
	    * @Date: 2017. 7. 27.
	    * @Author :  서 정 길
	    * @Description	:	배송지 삭제
	   */
	@RequestMapping(value = "/ajax/order/deleteAddressAjax", method = RequestMethod.POST) 
	public @ResponseBody JsonResultVO deleteAddressAjax(OrderVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		JsonResultVO resultMap = new JsonResultVO();

		UserInfo userInfo = UserInfo.getUserInfo();
				
		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
			vo.setRegIdx(userInfo.getMemberIdx());
			vo.setMemberIdx(userInfo.getMemberIdx());
			vo.setMemberId(userInfo.getMemberId());
		}else{	// 비회원
			resultMap.setResult(false);
			resultMap.setMsg("로그인이 필요합니다.");
			return resultMap;
		}

		try{
			// 삭제할 배송지 일련번호가 없을 때
			if(vo.getModifyAddressIdx() == null || vo.getModifyAddressIdx().toString().isEmpty()){
				resultMap.setResult(false);
				resultMap.setMsg("삭제할 배송지를 선택하세요!");
				return resultMap;
			}

			// 현재 선택된 배송지를 삭제하는 거면 선택된 배송지 null로 저장
			if(vo.getSelectAddressIdx() != null && vo.getModifyAddressIdx() == vo.getSelectAddressIdx()){
				vo.setSelectAddressIdx(null);
			}
			
			try{
				orderService.updateOrderMasterInfo(vo);	// 주문 정보 DB 저장
			}catch(Exception e){
				e.printStackTrace();
				resultMap.setResult(false);
				resultMap.setMsg("결제 정보 저장 중 에러가 발생했습니다!");
				return resultMap;
			}
			
			int flag = orderService.deleteAddress(vo);	// 배송지 삭제

			if(flag < 1){
				resultMap.setResult(false);
				resultMap.setMsg("배송지 삭제 중 에러가 발생했습니다!");
				return resultMap;
			}

			resultMap.setResult(true);
		}catch(Exception e){
			e.printStackTrace();
			resultMap.setResult(false);
			resultMap.setMsg("배송지 삭제 작업 중 에러가 발생했습니다!");
		}

		return resultMap;
	}

	   /**
	    * @Method : saveAddressAjax
	    * @Date: 2017. 7. 27.
	    * @Author :  서 정 길
	    * @Description	:	배송지 저장
	   */
	@RequestMapping(value = "/ajax/order/saveAddressAjax", method = RequestMethod.POST) 
	public @ResponseBody JsonResultVO saveAddressAjax(OrderVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		JsonResultVO resultMap = new JsonResultVO();

		UserInfo userInfo = UserInfo.getUserInfo();
				
		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
			vo.setRegIdx(userInfo.getMemberIdx());
			vo.setMemberIdx(userInfo.getMemberIdx());
			vo.setMemberId(userInfo.getMemberId());
		}else{	// 비회원
			resultMap.setResult(false);
			resultMap.setMsg("로그인이 필요합니다.");
			return resultMap;
		}

		try{
			// 수정할 배송지 일련번호가 없을 때
			if(vo.getModifyAddressIdx() == null || vo.getModifyAddressIdx().toString().isEmpty()){
				resultMap.setResult(false);
				resultMap.setMsg("수정할 배송지를 선택하세요!");
				return resultMap;
			}

			// 수정할 배송지명이 없을 때
			if(vo.getNewShippingNm() == null || vo.getNewShippingNm().toString().isEmpty()){
				resultMap.setResult(false);
				resultMap.setMsg("배송지명을 입력하세요!");
				return resultMap;
			}

			// 수정할 배송지 성명이 없을 때
			if(vo.getNewReceiverNm() == null || vo.getNewReceiverNm().toString().isEmpty()){
				resultMap.setResult(false);
				resultMap.setMsg("성명을 입력하세요!");
				return resultMap;
			}

			// 수정할 배송지 휴대폰번호가 없을 때
			if(vo.getNewReceiverPhoneNo() == null || vo.getNewReceiverPhoneNo().toString().isEmpty()){
				resultMap.setResult(false);
				resultMap.setMsg("휴대폰번호를 입력하세요!");
				return resultMap;
			}

			// 수정할 배송지 우편번호가 없을 때
			if(vo.getNewReceiverZipCd() == null || vo.getNewReceiverZipCd().toString().isEmpty()){
				resultMap.setResult(false);
				resultMap.setMsg("우편번호를 입력하세요!");
				return resultMap;
			}

			// 수정할 배송지 주소가 없을 때
			if(vo.getNewReceiverAddr() == null || vo.getNewReceiverAddr().toString().isEmpty()){
				resultMap.setResult(false);
				resultMap.setMsg("주소를 입력하세요!");
				return resultMap;
			}

			// 수정할 배송지 주소가 없을 때
			if(vo.getNewReceiverAddrDetail() == null || vo.getNewReceiverAddrDetail().toString().isEmpty()){
				resultMap.setResult(false);
				resultMap.setMsg("상세주소를 입력하세요!");
				return resultMap;
			}

			try{
				orderService.updateOrderMasterInfo(vo);	// 주문 정보 DB 저장
			}catch(Exception e){
				e.printStackTrace();
				resultMap.setResult(false);
				resultMap.setMsg("결제 정보 저장 중 에러가 발생했습니다!");
				return resultMap;
			}
			
			int flag = orderService.updateAddress(vo, request);	// 배송지 수정

			if(flag < 1){
				resultMap.setResult(false);
				resultMap.setMsg("배송지 수정 중 에러가 발생했습니다!");
				return resultMap;
			}

			resultMap.setResult(true);
		}catch(Exception e){
			e.printStackTrace();
			resultMap.setResult(false);
			resultMap.setMsg("배송지 수정 작업 중 에러가 발생했습니다!");
		}

		return resultMap;
	}

	   /**
	    * @Method : pointPayment
	    * @Date: 2017. 8. 1.
	    * @Author :  서 정 길
	    * @Description	:	포인트 결제 (결제금액 0원) 처리
	   */
	@RequestMapping(value="/order/pointPayment", method = RequestMethod.POST)
	public String pointPayment(OrderVO vo, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
		
		System.out.println( "===========포인트 결제 처리==================================");
		
		UserInfo userInfo = UserInfo.getUserInfo();
		
		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
			vo.setRegIdx(userInfo.getMemberIdx());
			vo.setMemberIdx(userInfo.getMemberIdx());
			vo.setMemberId(userInfo.getMemberId());
		}else{	// 비회원
			vo.setRegIdx(0);
		}

		XPayResultVO resultVo = new XPayResultVO();
/*
		// 에러 처리
		resultVo.setLGD_RESPCODE("-1");
		resultVo.setLGD_RESPMSG("결제요청을 초기화 하는데 실패하였습니다.");
		MethodUtil.redirectPostUrl(request, response, PathUtil.getCtx()+"/order/orderComplete.do", resultVo);
		return null;
*/		
	         
		// 결제 후 DB 처리 시작-------------------------------------------------------------------------------------------------
		JsonResultVO resultMap = new JsonResultVO();
		
		try{
			session.removeAttribute("orderCd");	// 세션 주문 코드 삭제
			resultMap = orderService.dbProcessAfterPointPayment(vo);	// 포인트 결제 DB 처리
			
			if(!resultMap.getResult()){
				resultVo.setLGD_RESPCODE("-2");
				resultVo.setLGD_RESPMSG(resultMap.getMsg());	// 에러 메시지
			}else{
				resultVo.setLGD_RESPCODE("0000");
				resultVo.setLGD_BUYER(resultMap.getData1());		// 구매자명
				resultVo.setLGD_PAYDATE(resultMap.getData2());	// 결제일자
				resultVo.setLGD_OID(vo.getOrderCd());					// 주문코드
				resultVo.setLGD_AMOUNT(resultMap.getData3());	// 결제금액
				
	         	//---- SMS, 메일발송 (에러나더라도 rollback안함)
	         	try {
     				orderService.callSPSmsSendAndEmail("ORD002",  resultVo.getLGD_OID());
	         	}catch(Exception e) {
	         		e.printStackTrace();
	         	}
			}
		}catch (Exception e) {
			e.printStackTrace();
			resultVo.setLGD_RESPCODE("-2");
			resultVo.setLGD_RESPMSG("결제 후 DB 처리중 에러가 발생했습니다. 다시 결제하여 주시기 바랍니다.");
		}
		// 결제 후 DB 처리 끝-------------------------------------------------------------------------------------------------

		MethodUtil.redirectPostUrl(request, response, PathUtil.getCtx()+"/order/orderComplete.do", resultVo);	     
		return null;
	}

	
	   /**
	    * @Method : orderPayRes
	    * @Date: 2017. 7. 13.
	    * @Author :  서 정 길
	    * @Description	:	lg u+  (신용카드, 실시간, paynow)결제 결과
	   */
	@RequestMapping(value="/order/payres", method = RequestMethod.POST)
	public String orderPayRes(XPayReqVO vo, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{

//		HashMap<String, Object> map = new HashMap<String, Object>();

		System.out.println( "===========payres=================================="); 
		/*
	     * [최종결제요청 페이지(STEP2-2)]
		 *
	     * 매뉴얼 "5.1. XPay 결제 요청 페이지 개발"의 "단계 5. 최종 결제 요청 및 요청 결과 처리" 참조
		 *
	     * LG유플러스으로 부터 내려받은 LGD_PAYKEY(인증Key)를 가지고 최종 결제요청.(파라미터 전달시 POST를 사용하세요)
	     */

		/* ※ 중요
		* 환경설정 파일의 경우 반드시 외부에서 접근이 가능한 경로에 두시면 안됩니다.
		* 해당 환경파일이 외부에 노출이 되는 경우 해킹의 위험이 존재하므로 반드시 외부에서 접근이 불가능한 경로에 두시기 바랍니다. 
		* 예) [Window 계열] C:\inetpub\wwwroot\lgdacom ==> 절대불가(웹 디렉토리)
		*/

		//LG유플러스에서 제공한 환경파일("/conf/lgdacom.conf,/conf/mall.conf") 위치 지정.
		final String XPAYCONFIGPATH =  SpringMessage.getMessage("xpay.CONFIGPATH"); 
		
	    String configPath = XPAYCONFIGPATH;  //LG유플러스에서 제공한 환경파일("\conf\lgdacom.conf,\conf\mall.conf") 위치 지정.

		XPayResultVO resultVo = new XPayResultVO();
	    /*
	     *************************************************
	     * 1.최종결제 요청 - BEGIN
	     *  (단, 최종 금액체크를 원하시는 경우 금액체크 부분 주석을 제거 하시면 됩니다.)
	     *************************************************
	     */
	    

	    //pom.xml에 추가하였음. 
		// (1) XpayClient의 사용을 위한 xpay 객체 생성
	    XPayClient xpay = new XPayClient();

		// (2) Init: XPayClient 초기화(환경설정 파일 로드) 
		// configPath: 설정파일
		// CST_PLATFORM: - test, service 값에 따라 lgdacom.conf의 test_url(test) 또는 url(srvice) 사용
		//				- test, service 값에 따라 테스트용 또는 서비스용 아이디 생성
	   	boolean isInitOK = xpay.Init(configPath, vo.getCST_PLATFORM());   	

	   	if( !isInitOK ) {
	    	//API 초기화 실패 화면처리
	        System.out.println( "결제요청을 초기화 하는데 실패하였습니다.<br>");
	        System.out.println( "LG유플러스에서 제공한 환경파일이 정상적으로 설치 되었는지 확인하시기 바랍니다.<br>");        
	        System.out.println( "mall.conf에는 Mert ID = Mert Key 가 반드시 등록되어 있어야 합니다.<br><br>");
	        System.out.println( "문의전화 LG유플러스 1544-7772<br>");
//	        return PathUtil.getCtx()+"/order/payres";
	        
	        // 에러 처리
	        resultVo.setLGD_RESPCODE("-1");
	        resultVo.setLGD_RESPMSG("결제요청을 초기화 하는데 실패하였습니다.");
	        MethodUtil.redirectPostUrl(request, response, PathUtil.getCtx()+"/order/orderComplete.do", resultVo);
		    return null;
	   	}else{      
	   		try{
	   			
				// (3) Init_TX: 메모리에 mall.conf, lgdacom.conf 할당 및 트랜잭션의 고유한 키 TXID 생성
		    	xpay.Init_TX(vo.getLGD_MID());
		    	xpay.Set("LGD_TXNAME", "PaymentByKey");
		    	xpay.Set("LGD_PAYKEY", vo.getLGD_PAYKEY() );
		    
		    	//금액을 체크하시기 원하는 경우 아래 주석을 풀어서 이용하십시요.
		    	//String DB_AMOUNT = "DB나 세션에서 가져온 금액"; //반드시 위변조가 불가능한 곳(DB나 세션)에서 금액을 가져오십시요.
		    	//xpay.Set("LGD_AMOUNTCHECKYN", "Y");
		    	//xpay.Set("LGD_AMOUNT", DB_AMOUNT);
		    
	    	}catch(Exception e) {
	    		System.out.println("LG유플러스 제공 API를 사용할 수 없습니다. 환경파일 설정을 확인해 주시기 바랍니다. ");
	    		System.out.println(""+e.getMessage());    	
//	    		return PathUtil.getCtx()+"/order/payres";

	    		// 에러 처리
		        resultVo.setLGD_RESPCODE("-1");
		        resultVo.setLGD_RESPMSG("LG유플러스 제공 API를 사용할 수 없습니다.");
		        MethodUtil.redirectPostUrl(request, response, PathUtil.getCtx()+"/order/orderComplete.do", resultVo);
			    return null;
	    	}
	   	}
		/*
		 *************************************************
		 * 1.최종결제 요청(수정하지 마세요) - END
		 *************************************************
		 */

	    /*
	     * 2. 최종결제 요청 결과처리
	     *
	     * 최종 결제요청 결과 리턴 파라미터는 연동메뉴얼을 참고하시기 바랍니다.
	     */
		 // (4) TX: lgdacom.conf에 설정된 URL로 소켓 통신하여 최종 인증요청, 결과값으로 true, false 리턴
	     if ( xpay.TX() ) {

	    	 resultVo.setLGD_RESPCODE(xpay.m_szResCode);
	    	 resultVo.setLGD_RESPMSG(xpay.m_szResMsg);
	    	 
	    	 // 결과 VO
//	    	 resultVo.setLGD_RESPCODE(xpay.Response("LGD_RESPCODE",0));
//	    	 resultVo.setLGD_RESPMSG(xpay.Response("LGD_RESPMSG",0));
	    	 resultVo.setLGD_BUYERPHONE(xpay.Response("LGD_BUYERPHONE",0));
	    	 resultVo.setLGD_PAYDATE(xpay.Response("LGD_PAYDATE",0));
	    	 resultVo.setLGD_DISCOUNTUSEYN(xpay.Response("LGD_DISCOUNTUSEYN",0));
	    	 resultVo.setLGD_RECEIVER(xpay.Response("LGD_RECEIVER",0));
	    	 resultVo.setLGD_BUYEREMAIL(xpay.Response("LGD_BUYEREMAIL",0));
	    	 resultVo.setLGD_DELIVERYINFO(xpay.Response("LGD_DELIVERYINFO",0));
	    	 resultVo.setLGD_PAYTYPE(xpay.Response("LGD_PAYTYPE",0));
	    	 resultVo.setLGD_RECEIVERPHONE(xpay.Response("LGD_RECEIVERPHONE",0));
//	    	 resultVo.setLGD_EXCHANGERATE(xpay.Response("LGD_EXCHANGERATE",0));
//	    	 resultVo.setLGD_IFOS(xpay.Response("LGD_IFOS",0));
	    	 resultVo.setLGD_AFFILIATECODE(xpay.Response("LGD_AFFILIATECODE",0));
	    	 resultVo.setLGD_CARDNUM(xpay.Response("LGD_CARDNUM",0));
//	    	 resultVo.setLGD_2TR_FLAG(xpay.Response("LGD_2TR_FLAG",0));
//	    	 resultVo.setLGD_CARDNOINTEREST_YN(xpay.Response("LGD_CARDNOINTEREST_YN",0));
	    	 resultVo.setLGD_FINANCEAUTHNUM(xpay.Response("LGD_FINANCEAUTHNUM",0));
//	    	 resultVo.setLGD_TRANSAMOUNT(xpay.Response("LGD_TRANSAMOUNT",0));
	    	 resultVo.setLGD_ESCROWYN(xpay.Response("LGD_ESCROWYN",0));
	    	 resultVo.setLGD_FINANCECODE(xpay.Response("LGD_FINANCECODE",0));
	    	 resultVo.setLGD_DISCOUNTUSEAMOUNT(xpay.Response("LGD_DISCOUNTUSEAMOUNT",0));
	    	 resultVo.setLGD_CARDGUBUN1(xpay.Response("LGD_CARDGUBUN1",0));
	    	 resultVo.setLGD_CARDGUBUN2(xpay.Response("LGD_CARDGUBUN2",0));
	    	 resultVo.setLGD_HASHDATA(xpay.Response("LGD_HASHDATA",0));
	    	 resultVo.setLGD_BUYERADDRESS(xpay.Response("LGD_BUYERADDRESS",0));
	    	 resultVo.setLGD_FINANCENAME(xpay.Response("LGD_FINANCENAME",0));
	    	 resultVo.setLGD_CARDNOINTYN(xpay.Response("LGD_CARDNOINTYN",0));
	    	 resultVo.setLGD_CARDACQUIRER(xpay.Response("LGD_CARDACQUIRER",0));
//	    	 resultVo.setLGD_DEVICE(xpay.Response("LGD_DEVICE",0));
	    	 resultVo.setLGD_TIMESTAMP(xpay.Response("LGD_TIMESTAMP",0));
	    	 resultVo.setLGD_PCANCELSTR(xpay.Response("LGD_PCANCELSTR",0));
	    	 resultVo.setLGD_TELNO(xpay.Response("LGD_TELNO", 0));									// 휴대폰 결제 휴대폰번호 (*******0537)
	    	 resultVo.setLGD_BUYER(xpay.Response("LGD_BUYER",0));
	    	 resultVo.setLGD_CARDINSTALLMONTH(xpay.Response("LGD_CARDINSTALLMONTH",0));
//	    	 resultVo.setLGD_BUYERSSN(xpay.Response("LGD_BUYERSSN",0));
	    	 resultVo.setLGD_PRODUCTINFO(xpay.Response("LGD_PRODUCTINFO",0));
//	    	 resultVo.setLGD_VANCODE(xpay.Response("LGD_VANCODE",0));
	    	 resultVo.setLGD_PRODUCTCODE(xpay.Response("LGD_PRODUCTCODE",0));
	    	 resultVo.setLGD_TID(xpay.Response("LGD_TID",0));
	    	 resultVo.setLGD_BUYERID(xpay.Response("LGD_BUYERID",0));
	    	 resultVo.setLGD_OID(xpay.Response("LGD_OID",0));
	    	 resultVo.setLGD_AMOUNT(xpay.Response("LGD_AMOUNT",0));
	    	 resultVo.setLGD_ACCOUNTOWNER(xpay.Response("LGD_ACCOUNTOWNER", 0));		// 실시간계좌이체 계좌주명
	    	 resultVo.setLGD_PCANCELFLAG(xpay.Response("LGD_PCANCELFLAG",0));
	    	 resultVo.setLGD_MID(xpay.Response("LGD_MID",0));

	    	 
	         //1)결제결과 화면처리(성공,실패 결과 처리를 하시기 바랍니다.)
	    	 System.out.println( "결제요청이 완료되었습니다.  <br>");
	    	 System.out.println( "TX 결제요청 통신 응답코드 = " + xpay.m_szResCode + "<br>");					//통신 응답코드("0000" 일 때 통신 성공)
	    	 System.out.println( "TX 결제요청 통신 응답메시지 = " + xpay.m_szResMsg + "<p>");					//통신 응답메시지
	        
	         
	    	 System.out.println("거래번호 : " + xpay.Response("LGD_TID",0) + "<br>");
	    	 System.out.println("상점아이디 : " + xpay.Response("LGD_MID",0) + "<br>");
	    	 System.out.println("상점주문번호 : " + xpay.Response("LGD_OID",0) + "<br>");
	    	 System.out.println("결제금액 : " + xpay.Response("LGD_AMOUNT",0) + "<br>");
	    	 System.out.println("결과코드 : " + xpay.Response("LGD_RESPCODE",0) + "<br>");						//LGD_RESPCODE 가 반드시 "0000" 일때만 결제 성공, 그 외는 모두 실패
	    	 System.out.println("결과메세지 : " + xpay.Response("LGD_RESPMSG",0) + "<p>");
	         
	         for (int i = 0; i < xpay.ResponseNameCount(); i++)
	         {
	        	 System.out.println(xpay.ResponseName(i) + " = ");
	             for (int j = 0; j < xpay.ResponseCount(); j++)
	             {
	            	 System.out.println("\t" + xpay.Response(xpay.ResponseName(i), j) + "<br>");
	             }
	         }
	         System.out.println("<p>");

//	         orderService.insertXPayResultLog(resultVo);	// LG U+ 결제 결과 로그 저장 (성공/실패 모두 저장됨)

	         
			 // (5) DB에 인증요청 결과 처리
	         if( "0000".equals( xpay.m_szResCode ) ) {
	         	// 통신상의 문제가 없을시
				// 최종결제요청 결과 성공 DB처리(LGD_RESPCODE 값에 따라 결제가 성공인지, 실패인지 DB처리)
	        	 System.out.println("최종결제요청 성공, DB처리하시기 바랍니다.<br>");
	         	            	
	         	//최종결제요청 결과를 DB처리합니다. (결제성공 또는 실패 모두 DB처리 가능)
				//상점내 DB에 어떠한 이유로 처리를 하지 못한경우 false로 변경해 주세요.
	         	boolean isDBOK = true; 

	         	// 결제 후 DB 처리 시작-------------------------------------------------------------------------------------------------
	         	JsonResultVO resultMap = new JsonResultVO();
	         	
	         	try{
	         		session.removeAttribute("orderCd");	// 세션 주문 코드 삭제
	         		resultMap = orderService.dbProcessAfterXPay(resultVo);	// LG U+ 결제 후 DB 처리
	         		
	         		if(!resultMap.getResult()){
						resultVo.setLGD_RESPCODE("-2");
						resultVo.setLGD_RESPMSG(resultMap.getMsg());	// 에러 메시지
						isDBOK = false;
	         		}
	         	}catch (Exception e) {
					e.printStackTrace();
					resultVo.setLGD_RESPCODE("-2");
					resultVo.setLGD_RESPMSG("결제 후 DB 처리중 에러가 발생했습니다. 다시 결제하여 주시기 바랍니다.");
					isDBOK = false;
				}
	         	// 결제 후 DB 처리 끝-------------------------------------------------------------------------------------------------

	         	//---- SMS, 메일발송 (에러나더라도 rollback안함)
	         	try {
	         		if( isDBOK ) {
	         			if (resultVo.getLGD_PAYTYPE().equals("SC0040") )  //가상계좌
	         			{
	         				orderService.callSPSmsSendAndEmail("ORD001",  resultVo.getLGD_OID());
	         			}
	         			else
	         			{
	         				orderService.callSPSmsSendAndEmail("ORD002",  resultVo.getLGD_OID());
	         			}
	         		}
	         	}catch(Exception e) {
	         		e.printStackTrace();
	         	}
	         	
	         	if( !isDBOK ) {
					 
	         		xpay.Rollback("상점 DB처리 실패로 인하여 Rollback 처리 [TID:" +xpay.Response("LGD_TID",0)+",MID:" + xpay.Response("LGD_MID",0)+",OID:"+xpay.Response("LGD_OID",0)+"]");
	         		
	         		System.out.println( "TX Rollback Response_code = " + xpay.Response("LGD_RESPCODE",0) + "<br>");
	         		System.out.println( "TX Rollback Response_msg = " + xpay.Response("LGD_RESPMSG",0) + "<p>");
	         		
					if( "0000".equals( xpay.m_szResCode ) ) { 
						System.out.println("자동취소가 정상적으로 완료 되었습니다.<br>");

						resultVo.setLGD_RESPCODE("-3");
						resultVo.setLGD_RESPMSG("상점 DB처리 실패로 인하여 Rollback 처리<br>자동취소가 정상적으로 완료 되었습니다.<br>" + resultVo.getLGD_RESPMSG());
					}else{
						System.out.println("자동취소가 정상적으로 처리되지 않았습니다.<br>");

						resultVo.setLGD_RESPCODE("-3");
						resultVo.setLGD_RESPMSG("상점 DB처리 실패로 인하여 Rollback 처리<br>자동취소가 정상적으로 처리되지 않았습니다.<br>" + resultVo.getLGD_RESPMSG());
					}
	         	}
	         	
	         }else{
				//통신상의 문제 발생(최종결제요청 결과 실패 DB처리)
	        	 System.out.println("최종결제요청 결과 실패, DB처리하시기 바랍니다.<br>");
	         }
	     }else {
	    	 resultVo.setLGD_RESPCODE(xpay.m_szResCode);
	    	 resultVo.setLGD_RESPMSG(xpay.m_szResMsg);

	         //2)API 요청실패 화면처리
	    	 System.out.println( "결제요청이 실패하였습니다.  <br>");
	    	 System.out.println( "TX 결제요청 Response_code = " + xpay.m_szResCode + "<br>");
	    	 System.out.println( "TX 결제요청 Response_msg = " + xpay.m_szResMsg + "<p>");
	         
	     	//최종결제요청 결과 실패 DB처리
	    	 System.out.println("최종결제요청 결과 실패 DB처리하시기 바랍니다.<br>");
	     }
/*
	     model.addAttribute("resultVo", resultVo);
	     // session.setAttribute("PAYREQ_MAP", payReqMap);
	     model.addAttribute("HiddenParams" , ObjectToHiddenParams(vo));

	     return PathUtil.getCtx()+"/lgdacom/complet";

	     redirectAttributes.addAttribute("orderCd", session.getAttribute("orderCd"));

	     return "redirect:/order/orderComplete.do";
*/

	     MethodUtil.redirectPostUrl(request, response, PathUtil.getCtx()+"/order/orderComplete.do", resultVo);	     
	     return null;
	}

	  /**
	    * @Method : returnurl
	    * @Date: 2017. 7. 4.
	    * @Author :   강 병 철
	    * @Description	:	lg u+ 결제요청
	   */
	@RequestMapping(value="/order/returnurl") 
	public String returnurl(XPayReqVO vo,  HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
	
	    model.addAttribute("reqMap", vo);
	    model.addAttribute("HiddenParams" , ObjectToHiddenParams(vo));
		return PathUtil.getCtx()+"/order/returnurl";
	}
	
	
	   /**
	    * @Method : cyberpayres
	    * @Date: 2017. 7. 4.
	    * @Author :   강 병 철
	    * @Description	:	lg u+ 가상계좌 호출&결과
	   */
		@RequestMapping(value="/order/cyberpayres") 
		public String cyberpayres(XPayCyberAccountVO vo,  HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{

			System.out.println( "===========cyberpayres=================================="); 

			//LG유플러스에서 제공한 환경파일("/conf/lgdacom.conf,/conf/mall.conf") 위치 지정.
			final String XPAYCONFIGPATH =  SpringMessage.getMessage("xpay.CONFIGPATH"); 
			String LGD_METHOD = "ASSIGN"; //계좌발급
			
			XPayResultVO resultVo = new XPayResultVO();
		    /*
		     *************************************************
		     * 1.최종결제 요청 - BEGIN
		     *  (단, 최종 금액체크를 원하시는 경우 금액체크 부분 주석을 제거 하시면 됩니다.)
		     *************************************************
		     */

		    //pom.xml에 추가하였음. 
			// (1) XpayClient의 사용을 위한 xpay 객체 생성
		    XPayClient xpay = new XPayClient();

			// (2) Init: XPayClient 초기화(환경설정 파일 로드) 
			// configPath: 설정파일
			// CST_PLATFORM: - test, service 값에 따라 lgdacom.conf의 test_url(test) 또는 url(srvice) 사용
			//				- test, service 값에 따라 테스트용 또는 서비스용 아이디 생성
		   	boolean isInitOK = xpay.Init(XPAYCONFIGPATH, vo.getCST_PLATFORM());   	

		   	if( !isInitOK ) {
		    	//API 초기화 실패 화면처리
		        System.out.println( "결제요청을 초기화 하는데 실패하였습니다.<br>");
		        System.out.println( "LG유플러스에서 제공한 환경파일이 정상적으로 설치 되었는지 확인하시기 바랍니다.<br>");        
		        System.out.println( "mall.conf에는 Mert ID = Mert Key 가 반드시 등록되어 있어야 합니다.<br><br>");
		        System.out.println( "문의전화 LG유플러스 1544-7772<br>");
//		        return PathUtil.getCtx()+"/order/payres";
		   	
		        // 에러 처리
		        resultVo.setLGD_RESPCODE("-1");
		        resultVo.setLGD_RESPMSG("결제요청을 초기화 하는데 실패하였습니다.");
		        MethodUtil.redirectPostUrl(request, response, PathUtil.getCtx()+"/order/orderComplete.do", resultVo);
			    return null;
		   	}else{      
		   		try{
		   			
					// (3) Init_TX: 메모리에 mall.conf, lgdacom.conf 할당 및 트랜잭션의 고유한 키 TXID 생성
			    	xpay.Init_TX(vo.getLGD_MID());
			    	xpay.Set("LGD_TXNAME", "CyberAccount");
			        xpay.Set("LGD_METHOD", LGD_METHOD);
			        xpay.Set("LGD_OID", vo.getLGD_OID());
			        xpay.Set("LGD_AMOUNT", vo.getLGD_AMOUNT());
			        xpay.Set("LGD_PRODUCTINFO", vo.getLGD_PRODUCTINFO());
			        xpay.Set("LGD_BANKCODE", vo.getLGD_BANKCODE());
			        xpay.Set("LGD_CASHRECEIPTUSE", vo.getLGD_CASHRECEIPTUSE());
			        xpay.Set("LGD_CASHCARDNUM", vo.getLGD_CASHCARDNUM());
			        xpay.Set("LGD_CASNOTEURL", vo.getLGD_CASNOTEURL());
			        xpay.Set("LGD_BUYER", vo.getLGD_BUYER());
			        xpay.Set("LGD_ACCOUNTOWNER", vo.getLGD_ACCOUNTOWNER());
			        xpay.Set("LGD_BUYERPHONE", vo.getLGD_BUYERPHONE());
			        xpay.Set("LGD_BUYEREMAIL", vo.getLGD_BUYEREMAIL());
			        xpay.Set("LGD_CLOSEDATE", vo.getLGD_CLOSEDATE());
			        xpay.Set("LGD_ESCROW_USEYN", vo.getLGD_ESCROW_USEYN());
			        
			        xpay.Set("LGD_ESCROW_ZIPCODE", vo.getLGD_ESCROW_ZIPCODE());				// 에스크로배송지우편번호
			        xpay.Set("LGD_ESCROW_ADDRESS1", vo.getLGD_ESCROW_ADDRESS1());			// 에스크로배송지주소동까지
			        xpay.Set("LGD_ESCROW_ADDRESS2", vo.getLGD_ESCROW_ADDRESS2());			// 에스크로배송지주소상세
			        xpay.Set("LGD_ESCROW_BUYERPHONE", vo.getLGD_ESCROW_BUYERPHONE());	// 에스크로구매자휴대폰번호
			    
			        if("Y".equals(vo.getLGD_ESCROW_USEYN())){	// 에스크로 사용일때만 상품 정보 파라미터로 보냄
			    		UserInfo userInfo = UserInfo.getUserInfo();
			    		OrderVO orderVO = new OrderVO();
			    		
			    		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
			    			orderVO.setRegIdx(userInfo.getMemberIdx());
			    			orderVO.setMemberIdx(userInfo.getMemberIdx());
			    		}else{	// 비회원
			    			orderVO.setRegIdx(0);
			    		}
			    		
			    		orderVO.setOrderCd(session.getAttribute("orderCd").toString());		// 주문 코드

			        	List<SqlMap> list = orderService.getOrderGoodsList(orderVO);			// 주문 상품 리스트
			        	
			        	if(list != null){
			        		for(int i=0;i<list.size();i++){
						        xpay.Set("LGD_ESCROW_GOODID", nvl(list.get(i).get("goodsIdx")));			// 에스크로상품번호
						        xpay.Set("LGD_ESCROW_GOODNAME", nvl(list.get(i).get("goodsNm")));		// 에스크로상품명
						        xpay.Set("LGD_ESCROW_GOODCODE", nvl(list.get(i).get("goodsCd")));		// 에스크로상품코드
						        xpay.Set("LGD_ESCROW_UNITPRICE", Integer.toString(Double.valueOf(nvl(list.get(i).get("discountPrice"))).intValue()));	// 에스크로상품금액
						        xpay.Set("LGD_ESCROW_QUANTITY", nvl(list.get(i).get("orderCnt")));			// 에스크로상품수량
			        		}
			        	}
			        }
			        
			    	//금액을 체크하시기 원하는 경우 아래 주석을 풀어서 이용하십시요.
			    	//String DB_AMOUNT = "DB나 세션에서 가져온 금액"; //반드시 위변조가 불가능한 곳(DB나 세션)에서 금액을 가져오십시요.
			    	//xpay.Set("LGD_AMOUNTCHECKYN", "Y");
			    	//xpay.Set("LGD_AMOUNT", DB_AMOUNT);
			    
		    	}catch(Exception e) {
		    		System.out.println("LG유플러스 제공 API를 사용할 수 없습니다. 환경파일 설정을 확인해 주시기 바랍니다. ");
		    		System.out.println(""+e.getMessage());    	
//		    		return PathUtil.getCtx()+"/order/payres";

		    		// 에러 처리
			        resultVo.setLGD_RESPCODE("-1");
			        resultVo.setLGD_RESPMSG("LG유플러스 제공 API를 사용할 수 없습니다.");
			        MethodUtil.redirectPostUrl(request, response, PathUtil.getCtx()+"/order/orderComplete.do", resultVo);
				    return null;
		    	}
		   	}
			/*
			 *************************************************
			 * 1.최종결제 요청(수정하지 마세요) - END
			 *************************************************
			 */

		    
		    /*
		     * 2. 가상계좌 발급/변경 요청 결과처리
		     *
		     * 결과 리턴 파라미터는 연동메뉴얼을 참고하시기 바랍니다.
		     */
			 // (4) TX: lgdacom.conf에 설정된 URL로 소켓 통신하여 최종 인증요청, 결과값으로 true, false 리턴
		     if ( xpay.TX() ) {

		    	//1)가상계좌 발급/변경결과 화면처리(성공,실패 결과 처리를 하시기 바랍니다.)

		    	 resultVo.setLGD_RESPCODE(xpay.m_szResCode);
		    	 resultVo.setLGD_RESPMSG(xpay.m_szResMsg);
		    	 resultVo.setLGD_TID(xpay.Response("LGD_TID",0));
		    	 resultVo.setLGD_CASTAMOUNT(xpay.Response("LGD_CASTAMOUNT",0));
		    	 resultVo.setLGD_FINANCENAME(xpay.Response("LGD_FINANCENAME",0));
		    	 resultVo.setLGD_PAYDATE(xpay.Response("LGD_PAYDATE",0));
		    	 resultVo.setLGD_PAYTYPE(xpay.Response("LGD_PAYTYPE",0));
		    	 resultVo.setLGD_ACCOUNTNUM(xpay.Response("LGD_ACCOUNTNUM",0)); //입금할 계좌번호
		    	 resultVo.setLGD_CASSEQNO(xpay.Response("LGD_CASSEQNO",0));
		    	 resultVo.setLGD_TIMESTAMP(xpay.Response("LGD_TIMESTAMP",0));		    	 
		    	 resultVo.setLGD_PAYER(xpay.Response("LGD_PAYER",0));
//		    	 resultVo.setLGD_BUYERSSN(xpay.Response("LGD_BUYERSSN",0));
		    	 resultVo.setLGD_CASFLAG(xpay.Response("LGD_CASFLAG",0));
		    	 resultVo.setLGD_FINANCECODE(xpay.Response("LGD_FINANCECODE",0));  //003
		    	 resultVo.setLGD_OID(xpay.Response("LGD_OID",0));
		    	 resultVo.setLGD_CASCAMOUNT(xpay.Response("LGD_CASCAMOUNT",0));
		    	 resultVo.setLGD_HASHDATA(xpay.Response("LGD_HASHDATA",0));
		    	 resultVo.setLGD_AMOUNT(xpay.Response("LGD_AMOUNT",0));
		    	 resultVo.setLGD_SAOWNER(xpay.Response("LGD_SAOWNER",0));		    	 
		    	 resultVo.setLGD_MID(xpay.Response("LGD_MID",0));
		    	 resultVo.setLGD_CASHRECEIPTKIND(xpay.Response("LGD_CASHRECEIPTKIND",0));
//		    	 resultVo.setLGD_CASHRECEIPTCODE(xpay.Response("LGD_CASHRECEIPTCODE",0));
		    	 resultVo.setLGD_BUYER(xpay.Response("LGD_PAYER",0));	// 구매자 (가상계좌 결과로 LGD_BUYER가 안 넘어와서 LGD_PAYER로 보냄)
		    	 resultVo.setLGD_CLOSEDATE(vo.getLGD_CLOSEDATE());
		    	 
				System.out.println("가상계좌 발급 요청처리가 완료되었습니다.  <br>");
				System.out.println( "TX Response_code = " + xpay.m_szResCode + "<br>");
				System.out.println( "TX Response_msg = " + xpay.m_szResMsg + "<p>");
				System.out.println( " 만료일 = " + vo.getLGD_CLOSEDATE() + "<p>");
				    
				System.out.println("거래번호 : " + xpay.Response("LGD_TID",0) + "<br>");
				System.out.println("결과코드 : " + xpay.Response("LGD_RESPCODE",0) + "<p>");

	        	for (int i = 0; i < xpay.ResponseNameCount(); i++)
	        	{
	        		System.out.println(xpay.ResponseName(i) + " = ");
	            	for (int j = 0; j < xpay.ResponseCount(); j++)
	            	{
	            		System.out.println("\t" + xpay.Response(xpay.ResponseName(i), j) + "<br>");
	            	}
	        	}
		        	
//	        	orderService.insertXPayResultLog(resultVo);	// LG U+ 결제 결과 로그 저장 (성공/실패 모두 저장됨)

	        	
		        // (5) DB에 인증요청 결과 처리
		        if( "0000".equals( xpay.m_szResCode ) ) {
		         	// 통신상의 문제가 없을시
					// 최종결제요청 결과 성공 DB처리(LGD_RESPCODE 값에 따라 결제가 성공인지, 실패인지 DB처리)
		        	 System.out.println("최종결제요청 성공, DB처리하시기 바랍니다.<br>");
		         	            	
		         	//최종결제요청 결과를 DB처리합니다. (결제성공 또는 실패 모두 DB처리 가능)
					//상점내 DB에 어떠한 이유로 처리를 하지 못한경우 false로 변경해 주세요.
		         	boolean isDBOK = true;
		         	
		         	// 결제 후 DB 처리 시작-------------------------------------------------------------------------------------------------
		         	JsonResultVO resultMap = new JsonResultVO();
		         	
		         	try{
			        	session.removeAttribute("orderCd");	// 세션 주문 코드 삭제
		         		resultMap = orderService.dbProcessAfterXPay(resultVo);	// LG U+ 결제 후 DB 처리
		         		
		         		if(!resultMap.getResult()){
							resultVo.setLGD_RESPCODE("-2");
							resultVo.setLGD_RESPMSG(resultMap.getMsg());	// 에러 메시지
							isDBOK = false;
		         		}
		         	}catch (Exception e) {
						e.printStackTrace();
						resultVo.setLGD_RESPCODE("-2");
						resultVo.setLGD_RESPMSG("결제 후 DB 처리중 에러가 발생했습니다. 다시 결제하여 주시기 바랍니다.");
						isDBOK = false;
					}
		         	// 결제 후 DB 처리 끝-------------------------------------------------------------------------------------------------
		        	try {
		         		if( isDBOK ) {
	         				orderService.callSPSmsSendAndEmail("ORD001",  resultVo.getLGD_OID()); //가상계좌
		         		}
		         	}catch(Exception e) {
		         		e.printStackTrace();
		         	}
		         	if( !isDBOK ) {
						 
		         		xpay.Rollback("상점 DB처리 실패로 인하여 Rollback 처리 [TID:" +xpay.Response("LGD_TID",0)+",MID:" + xpay.Response("LGD_MID",0)+",OID:"+xpay.Response("LGD_OID",0)+"]");
		         		
		         		System.out.println( "TX Rollback Response_code = " + xpay.Response("LGD_RESPCODE",0) + "<br>");
		         		System.out.println( "TX Rollback Response_msg = " + xpay.Response("LGD_RESPMSG",0) + "<p>");
		         		
						if( "0000".equals( xpay.m_szResCode ) ) { 
							System.out.println("자동취소가 정상적으로 완료 되었습니다.<br>");

							resultVo.setLGD_RESPCODE("-3");
							resultVo.setLGD_RESPMSG("상점 DB처리 실패로 인하여 Rollback 처리<br>자동취소가 정상적으로 완료 되었습니다.<br>" + resultVo.getLGD_RESPMSG());
						}else{
							System.out.println("자동취소가 정상적으로 처리되지 않았습니다.<br>");

							resultVo.setLGD_RESPCODE("-3");
							resultVo.setLGD_RESPMSG("상점 DB처리 실패로 인하여 Rollback 처리<br>자동취소가 정상적으로 처리되지 않았습니다.<br>" + resultVo.getLGD_RESPMSG());
						}
		         	}
		         	
		        }else{
					//통신상의 문제 발생(최종결제요청 결과 실패 DB처리)
		        	 System.out.println("최종결제요청 결과 실패, DB처리하시기 바랍니다.<br>");            	
		        }
		     }else {
		    	 resultVo.setLGD_RESPCODE(xpay.m_szResCode);
		    	 resultVo.setLGD_RESPMSG(xpay.m_szResMsg);

		         //2)API 요청실패 화면처리
		    	 System.out.println( "결제요청이 실패하였습니다.  <br>");
		    	 System.out.println( "TX 결제요청 Response_code = " + xpay.m_szResCode + "<br>");
		    	 System.out.println( "TX 결제요청 Response_msg = " + xpay.m_szResMsg + "<p>");
		         
		     	//최종결제요청 결과 실패 DB처리
		    	 System.out.println("최종결제요청 결과 실패 DB처리하시기 바랍니다.<br>");            	            
		     }
/*
		    model.addAttribute("resultVo", resultVo);
			// session.setAttribute("PAYREQ_MAP", payReqMap);
		    model.addAttribute("HiddenParams" , ObjectToHiddenParams(resultVo));

			return PathUtil.getCtx()+"/lgdacom/complet";
			
			redirectAttributes.addAttribute("orderCd", session.getAttribute("orderCd"));

			return "redirect:/order/orderComplete.do";
*/

		     MethodUtil.redirectPostUrl(request, response, PathUtil.getCtx()+"/order/orderComplete.do", resultVo);
		     return null;
		}
			

	   /**
	    * @Method : casnoteurl
	    * @Date: 2017. 7. 4.
	    * @Author :   강 병 철
	    * @Description	:	lg u+ 가상계좌, 실시간계좌이체 입금 결과
	   */
		@RequestMapping(value="/order/casnoteurl") 
		public String casnoteurl(XPayResultVO vo,  HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
			/*
		     * [상점 결제결과처리(DB) 페이지]
		     *
		     * 1) 위변조 방지를 위한 hashdata값 검증은 반드시 적용하셔야 합니다.
		     *
		     */
			String resultMSG = "";   
			System.out.println( "*******************************************************************"); 
			System.out.println( "===========casnoteurl=================================="); 
			System.out.println( "*******************************************************************"); 
			final String LGD_MERTKEY = SpringMessage.getMessage("xpay.MERTKEY");  //"c7ecf8d164fec7c46d4d049c8700d423";
			
//			String HASHDATA = makeHashkey(vo.getLGD_MID(), vo.getLGD_OID(),vo.getLGD_AMOUNT(), vo.getLGD_TIMESTAMP(), LGD_MERTKEY);
			String HASHDATA = makeHashkey2(vo.getLGD_MID(), vo.getLGD_OID(),vo.getLGD_AMOUNT(), vo.getLGD_RESPCODE(), vo.getLGD_TIMESTAMP(), LGD_MERTKEY);
			System.out.println("vo.getLGD_HASHDATA() = "+vo.getLGD_HASHDATA());
			System.out.println("HASHDATA = "+HASHDATA);
			System.out.println("vo.getLGD_RESPCODE() = "+vo.getLGD_RESPCODE());
			System.out.println("vo.getLGD_CASFLAG() = "+vo.getLGD_CASFLAG());
			System.out.println("vo.getLGD_OID() = "+vo.getLGD_OID());

		    if (vo.getLGD_HASHDATA().trim().equals(HASHDATA)) { //해쉬값 검증이 성공이면
		        if ( ("0000".equals(vo.getLGD_RESPCODE().trim())) ){ //결제가 성공이면
		        	if( "R".equals( vo.getLGD_CASFLAG().trim() ) ) {
		                /*
		                 * 무통장 할당 성공 결과 상점 처리(DB) 부분
		                 * 상점 결과 처리가 정상이면 "OK"
		                 */    
		                //if( 무통장 할당 성공 상점처리결과 성공 ) 
		                	resultMSG = "OK";
		                	
		                	System.out.println("=========무통장 할당 성공========= : " + vo.getLGD_OID());
		        		
		        	}else if( "I".equals( vo.getLGD_CASFLAG().trim() ) ) {
		 	            /*
		    	         * 무통장 입금 성공 결과 상점 처리(DB) 부분
		        	     * 상점 결과 처리가 정상이면 "OK"
		            	 */    
		            	//if( 무통장 입금 성공 상점처리결과 성공 ) 
		            		resultMSG = "OK";
		            		
			        		System.out.println("=========무통장 입금 성공========= : " + vo.getLGD_OID());

			        		int flag = orderService.updateCasNoteUrlOrderMaster(vo.getLGD_OID(), vo.getLGD_PAYDATE());	// 가상계좌 입금 확인시 - 주문 상태 결제 완료로 수정 & 결제일자 세팅
			        		
			        		orderService.insertCyberAccountOrderStatusLog(vo.getLGD_OID());	// 가상계좌 입금 완료시 주문 상태 로그 저장
			        		
			        		orderService.callSPSmsSendAndEmail("ORD001ORD002",  vo.getLGD_OID()); 	// 가상계좌 결제 완료 메일전송

		        	}else if( "C".equals( vo.getLGD_CASFLAG().trim() ) ) {
		 	            /*
		    	         * 무통장 입금취소 성공 결과 상점 처리(DB) 부분
		        	     * 상점 결과 처리가 정상이면 "OK"
		            	 */    

		        		System.out.println("=========무통장 입금취소 시작 ========= : " );

		        		JsonResultVO resultMap = new JsonResultVO();
		        		
		        		// 이미 주문 취소가 됐는지 확인
		        		String orderStatusCd = orderService.getOrderStatusCd(vo.getLGD_OID());	// 주문 마스터 상태 코드 반환
		        		
		        		if("890".equals(orderStatusCd)){	// 이미 주문 취소 됐으면 OK 보냄
							resultMap.setResult(true);
		            		resultMSG = "OK";
			        		System.out.println("=========무통장 입금 이미 취소됨========= : " + vo.getLGD_OID());
		        		}else{
			        		OrderVO orderinfovo = new OrderVO();
			        		
			        		orderinfovo.setOrderCd(vo.getLGD_OID());
			        		orderinfovo.setMemberIdx(0);
			        		orderinfovo.setNomemberOrderCd(vo.getLGD_OID());
			        		SqlMap xpayInfo = mypageOrderService.getXpayResult(orderinfovo);
							if (mypageOrderService.lgTelecomCasNotiCancelProcess(vo.getLGD_OID(), xpayInfo,resultMap) )
							{
								resultMap.setResult(true);
			            		resultMSG = "OK";
				        		System.out.println("=========무통장 입금취소 성공========= : " + vo.getLGD_OID());
							}
							else 
							{
								resultMap.setResult(false);
			            		resultMSG = "ERR";
							}
		        		}
		        	}
		        } else { //결제가 실패이면
		            /*
		             * 거래실패 결과 상점 처리(DB) 부분
		             * 상점결과 처리가 정상이면 "OK"
		             */  
		           //if( 결제실패 상점처리결과 성공 ) 
		        	   resultMSG = "OK";
		        	   
		        	   System.out.println("=========무통장 거래실패========= : " + vo.getLGD_OID());
		        }
		    }else { //해쉬값이 검증이 실패이면
		        /*
		         * hashdata검증 실패 로그를 처리하시기 바랍니다. 
		         */      
		        resultMSG = "결제결과 상점 DB처리(LGD_CASNOTEURL) 해쉬값 검증이 실패하였습니다.";
		        System.out.println("결제결과 상점 DB처리(LGD_CASNOTEURL) 해쉬값 검증이 실패하였습니다.");
		    }
		    
		//    return resultMSG;
		    model.addAttribute("resultMSG", resultMSG);
		    return PathUtil.getCtx()+"/order/casnoteurl";
		}

	
	   /**
	    * @Method : orderComplete
	    * @Date: 2017. 7. 4.
	    * @Author :  서 정 길
	    * @Description	:	주문완료
	   */
	@RequestMapping(value="/order/orderComplete") 
	public String orderComplete(XPayResultVO resultVO, OrderVO vo, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{

		String returnStr = PathUtil.getCtx()+"/order/orderComplete";
/*		
		OrderVO vo = new OrderVO();
		
		UserInfo userInfo = UserInfo.getUserInfo();
		
		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
			vo.setRegIdx(userInfo.getMemberIdx());
			vo.setMemberIdx(userInfo.getMemberIdx());
			vo.setMemberId(userInfo.getMemberId());
		}else{	// 비회원
			vo.setRegIdx(0);
		}
		
		vo.setOrderCd(orderCd);
		
		SqlMap orderInfo = orderService.getOrderCompleteInfo(vo);	// 주문 정보
		
		if(orderInfo == null){
			returnStr = null;
			
			
			// 주문 정보 없음 에러 처리
			MethodUtil.alertMsgUrl(request, response, "주문 정보가 존재하지 않습니다!", "/main.do");
			return null;
		}

		model.addAttribute("orderInfo", orderInfo);
*/		
		
		UserInfo userInfo = UserInfo.getUserInfo();
		
		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
			vo.setRegIdx(userInfo.getMemberIdx());
			
			// 회원 포인트
			SqlMap memberInfo = cartService.getMemberPoint(userInfo.getMemberIdx());
			if(memberInfo != null){
				model.addAttribute("point", memberInfo.get("pointPrice"));
			}
			
			CartVO cartVO = new CartVO();			
			cartVO.setRegIdx(userInfo.getMemberIdx());
			cartVO.setMemberIdx(userInfo.getMemberIdx());
			
			int couponCnt = cartService.getMemberCouponCnt(cartVO);
			model.addAttribute("couponCnt", couponCnt);
		}else{	// 비회원
			vo.setRegIdx(0);
		}
		
		vo.setOrderCd(resultVO.getLGD_OID());		// 주문 코드
		
		List<SqlMap> list = orderService.getOrderGoodsList(vo);	// 주문 상품 리스트
		
		SqlMap bannerInfo = commonService.getBannerOne("ORDER_COM_BANNER");		// 주문완료 배너
		
		model.addAttribute("resultVO", resultVO);
		model.addAttribute("list", list);
		model.addAttribute("bannerInfo", bannerInfo);

		return returnStr;
	}
	
	  /**
	    * @Method : billkeyRegReturn
	    * @Date: 2017. 7. 4.
	    * @Author :   강 병 철
	    * @Description	:	lg u+ 결제요청
	   */
	@RequestMapping(value="/order/billkeyRegReturn", method = RequestMethod.POST) 
	public String billkeyRegReturn(XPayBillKeyVO vo,  HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
	    model.addAttribute("reqMap", vo);
	    model.addAttribute("HiddenParams" , ObjectToHiddenParams(vo));
		return PathUtil.getCtx()+"/order/billkeyRegReturn";
	}

	   /**
	    * @Method : billkeyRegReturnMypage
	    * @Date: 2017. 8. 25.
	    * @Author :  서 정 길
	    * @Description	:	lg u+ 결제요청 모바일용 (마이페이지 / 원클릭 결제 카드 관리용)
	   */
	@RequestMapping(value="/mypage/member/billkeyRegReturn", method = RequestMethod.POST) 
	public String billkeyRegReturnMypage(XPayBillKeyVO vo,  HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
	    model.addAttribute("reqMap", vo);
	    model.addAttribute("HiddenParams" , ObjectToHiddenParams(vo));
		return PathUtil.getCtx()+"/mypage/member/billkeyRegReturn";
	}

	   /**
	    * @Method : BillKeyRegAjax
	    * @Date: 2017. 7. 12.
	    * @Author :  강 병철 
	    * @Description	:	빌키 등록
	   */
	@RequestMapping(value = "/ajax/order/BillKeyRegAjax", method = RequestMethod.POST) 
	public @ResponseBody JsonResultVO BillKeyRegAjax(XPayBillKeyVO vo, HttpServletRequest request, HttpSession session) throws Exception{

		UserInfo userInfo = UserInfo.getUserInfo();
		JsonResultVO resultMap = new JsonResultVO();		
		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
			
			vo.setMEMBER_IDX(userInfo.getMemberIdx().toString());
			try{
				orderService.insertBillKey(vo);			
				resultMap.setResult(true);
			}catch(Exception e){
				e.printStackTrace();
				resultMap.setResult(false);
				resultMap.setMsg("저장 중 에러가 발생했습니다!");
			}

		}
		else
		{
			resultMap.setErrorCode("100");
			resultMap.setResult(false);
			resultMap.setMsg("로그인이 필요합니다.");
		}


		return resultMap;
	}
	
	/**
	    * @Method : BillKeyDelAjax
	    * @Date: 2017. 7. 12.
	    * @Author :  강 병철 
	    * @Description	:	빌키 삭제 (mypage에서 호출)
	   */
	@RequestMapping(value = "/ajax/order/BillKeyDelAjax", method = RequestMethod.POST) 
	public @ResponseBody JsonResultVO BillKeyDelAjax(int billkeyIdx, HttpServletRequest request, HttpSession session) throws Exception{
		UserInfo userInfo = UserInfo.getUserInfo();
		JsonResultVO resultMap = new JsonResultVO();		
		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
			
			try{
				orderService.deleteBillKey(billkeyIdx, userInfo.getMemberIdx());			
				resultMap.setResult(true);
			}catch(Exception e){
				e.printStackTrace();
				resultMap.setResult(false);
				resultMap.setMsg("빌키 삭제 중 에러가 발생했습니다!");
			}

		}
		else
		{
			resultMap.setErrorCode("100");
			resultMap.setResult(false);
			resultMap.setMsg("로그인이 필요합니다.");
		}
		return resultMap;
	}
	
	/**
	    * @Method : BillKeyModifyAjax
	    * @Date: 2017. 7. 12.
	    * @Author :  강 병철 
	    * @Description	:	빌키 수정 (mypage에서 호출)
	   */
	@RequestMapping(value = "/ajax/order/BillKeyModifyAjax", method = RequestMethod.POST) 
	public @ResponseBody JsonResultVO BillKeyModifyAjax(int billkeyIdx, String cardNm, String mainYn, HttpServletRequest request, HttpSession session) throws Exception{
		UserInfo userInfo = UserInfo.getUserInfo();
		JsonResultVO resultMap = new JsonResultVO();		
		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
			try{
				orderService.modifyBillKey(billkeyIdx, userInfo.getMemberIdx(), cardNm, mainYn);			
				resultMap.setResult(true);
			}catch(Exception e){
				e.printStackTrace();
				resultMap.setResult(false);
				resultMap.setMsg("빌키 변경 중 에러가 발생했습니다!");
			}
		}
		else
		{
			resultMap.setErrorCode("100");
			resultMap.setResult(false);
			resultMap.setMsg("로그인이 필요합니다.");
		}
		return resultMap;
	}
	
	
   /**
    * @Method : billkeypayres
    * @Date: 2017. 7. 4.
    * @Author :   강 병 철
    * @Description	:	lg u+ 빌키결제
   */
	@RequestMapping(value="/order/billkeyPayRes", method = RequestMethod.POST)
	public String billkeyPayRes(XPayBillKeyAuthVO vo,  HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{

		System.out.println( "===========빌키결제 billkeypayres=================================="); 

		//LG유플러스에서 제공한 환경파일("/conf/lgdacom.conf,/conf/mall.conf") 위치 지정.
		final String XPAYCONFIGPATH =  SpringMessage.getMessage("xpay.CONFIGPATH"); 
		XPayResultVO resultVo = new XPayResultVO();
	    /*
	     *************************************************
	     * 1.최종결제 요청 - BEGIN
	     *  (단, 최종 금액체크를 원하시는 경우 금액체크 부분 주석을 제거 하시면 됩니다.)
	     *************************************************
	     */

	    //pom.xml에 추가하였음. 
		// (1) XpayClient의 사용을 위한 xpay 객체 생성
	    XPayClient xpay = new XPayClient();

		// (2) Init: XPayClient 초기화(환경설정 파일 로드) 
		// configPath: 설정파일
		// CST_PLATFORM: - test, service 값에 따라 lgdacom.conf의 test_url(test) 또는 url(srvice) 사용
		//				- test, service 값에 따라 테스트용 또는 서비스용 아이디 생성
	   	boolean isInitOK = xpay.Init(XPAYCONFIGPATH, vo.getCST_PLATFORM());   	

	   	if( !isInitOK ) {
	    	//API 초기화 실패 화면처리
	        System.out.println( "결제요청을 초기화 하는데 실패하였습니다.<br>");
	        System.out.println( "LG유플러스에서 제공한 환경파일이 정상적으로 설치 되었는지 확인하시기 바랍니다.<br>");        
	        System.out.println( "mall.conf에는 Mert ID = Mert Key 가 반드시 등록되어 있어야 합니다.<br><br>");
	        System.out.println( "문의전화 LG유플러스 1544-7772<br>");
//		        return PathUtil.getCtx()+"/lgdacom/complet";
	   	
	        // 에러 처리
	        resultVo.setLGD_RESPCODE("-1");
	        resultVo.setLGD_RESPMSG("결제요청을 초기화 하는데 실패하였습니다.");
	        MethodUtil.redirectPostUrl(request, response, PathUtil.getCtx()+"/order/orderComplete.do", resultVo);
		    return null;
	   	}else{      
	   		try{
	   			
				// (3) Init_TX: 메모리에 mall.conf, lgdacom.conf 할당 및 트랜잭션의 고유한 키 TXID 생성
		    	xpay.Init_TX(vo.getLGD_MID());
		    	xpay.Set("LGD_TXNAME", "CardAuth");
		        xpay.Set("LGD_OID", vo.getLGD_OID());
		        xpay.Set("LGD_AMOUNT", vo.getLGD_AMOUNT());
		        xpay.Set("LGD_PRODUCTINFO", vo.getLGD_PRODUCTINFO());
		        xpay.Set("LGD_PAN", vo.getLGD_PAN());
		        xpay.Set("LGD_INSTALL", vo.getLGD_INSTALL());
		        xpay.Set("VBV_ECI", vo.getVBV_ECI());
		        xpay.Set("LGD_BUYERPHONE", vo.getLGD_BUYERPHONE());
		        xpay.Set("LGD_BUYER", vo.getLGD_BUYER());
		        xpay.Set("LGD_BUYERID", vo.getLGD_BUYERID());
		        xpay.Set("LGD_BUYERPHONE", vo.getLGD_BUYERPHONE());
		        xpay.Set("LGD_BUYEREMAIL", vo.getLGD_BUYEREMAIL());
		    
		    	//금액을 체크하시기 원하는 경우 아래 주석을 풀어서 이용하십시요.
		    	//String DB_AMOUNT = "DB나 세션에서 가져온 금액"; //반드시 위변조가 불가능한 곳(DB나 세션)에서 금액을 가져오십시요.
		    	//xpay.Set("LGD_AMOUNTCHECKYN", "Y");
		    	//xpay.Set("LGD_AMOUNT", DB_AMOUNT);
		    
	    	}catch(Exception e) {
	    		System.out.println("LG유플러스 제공 API를 사용할 수 없습니다. 환경파일 설정을 확인해 주시기 바랍니다. ");
	    		System.out.println(""+e.getMessage());    	
//		    		return PathUtil.getCtx()+"/lgdacom/payres";

	    		// 에러 처리
		        resultVo.setLGD_RESPCODE("-1");
		        resultVo.setLGD_RESPMSG("LG유플러스 제공 API를 사용할 수 없습니다.");
		        MethodUtil.redirectPostUrl(request, response, PathUtil.getCtx()+"/order/orderComplete.do", resultVo);
			    return null;
	    	}
	   	}
		/*
		 *************************************************
		 * 1.최종결제 요청(수정하지 마세요) - END
		 *************************************************
		 */

	    
	    /*
	     * 2. 가상계좌 발급/변경 요청 결과처리
	     *
	     * 결과 리턴 파라미터는 연동메뉴얼을 참고하시기 바랍니다.
	     */
		 // (4) TX: lgdacom.conf에 설정된 URL로 소켓 통신하여 최종 인증요청, 결과값으로 true, false 리턴
	     if ( xpay.TX() ) {

	    	 //1)가상계좌 발급/변경결과 화면처리(성공,실패 결과 처리를 하시기 바랍니다.)
			
	    	 resultVo.setLGD_RESPCODE(xpay.m_szResCode);
			 resultVo.setLGD_RESPMSG(xpay.m_szResMsg);
			 
	    	 // 결과 VO
//		    	 resultVo.setLGD_RESPCODE(xpay.Response("LGD_RESPCODE",0));
//		    	 resultVo.setLGD_RESPMSG(xpay.Response("LGD_RESPMSG",0));
	    	 resultVo.setLGD_BUYERPHONE(xpay.Response("LGD_BUYERPHONE",0));
	    	 resultVo.setLGD_PAYDATE(xpay.Response("LGD_PAYDATE",0));
	    	 resultVo.setLGD_DISCOUNTUSEYN(xpay.Response("LGD_DISCOUNTUSEYN",0));
	    	 resultVo.setLGD_RECEIVER(xpay.Response("LGD_RECEIVER",0));
	    	 resultVo.setLGD_BUYEREMAIL(xpay.Response("LGD_BUYEREMAIL",0));
	    	 resultVo.setLGD_DELIVERYINFO(xpay.Response("LGD_DELIVERYINFO",0));
	    	 resultVo.setLGD_PAYTYPE(xpay.Response("LGD_PAYTYPE",0));
	    	 resultVo.setLGD_RECEIVERPHONE(xpay.Response("LGD_RECEIVERPHONE",0));
//		    	 resultVo.setLGD_EXCHANGERATE(xpay.Response("LGD_EXCHANGERATE",0));
	    	 resultVo.setLGD_AFFILIATECODE(xpay.Response("LGD_AFFILIATECODE",0));
	    	 resultVo.setLGD_CARDNUM(xpay.Response("LGD_CARDNUM",0));
//		    	 resultVo.setLGD_CARDNOINTEREST_YN(xpay.Response("LGD_CARDNOINTEREST_YN",0));
	    	 resultVo.setLGD_FINANCEAUTHNUM(xpay.Response("LGD_FINANCEAUTHNUM",0));
//		    	 resultVo.setLGD_TRANSAMOUNT(xpay.Response("LGD_TRANSAMOUNT",0));
	    	 resultVo.setLGD_ESCROWYN(xpay.Response("LGD_ESCROWYN",0));
	    	 resultVo.setLGD_FINANCECODE(xpay.Response("LGD_FINANCECODE",0));
	    	 resultVo.setLGD_DISCOUNTUSEAMOUNT(xpay.Response("LGD_DISCOUNTUSEAMOUNT",0));
	    	 resultVo.setLGD_CARDGUBUN1(xpay.Response("LGD_CARDGUBUN1",0));
	    	 resultVo.setLGD_CARDGUBUN2(xpay.Response("LGD_CARDGUBUN2",0));
	    	 resultVo.setLGD_HASHDATA(xpay.Response("LGD_HASHDATA",0));
	    	 resultVo.setLGD_BUYERADDRESS(xpay.Response("LGD_BUYERADDRESS",0));
	    	 resultVo.setLGD_FINANCENAME(xpay.Response("LGD_FINANCENAME",0));
	    	 resultVo.setLGD_CARDNOINTYN(xpay.Response("LGD_CARDNOINTYN",0));
	    	 resultVo.setLGD_CARDACQUIRER(xpay.Response("LGD_CARDACQUIRER",0));
	    	 resultVo.setLGD_TIMESTAMP(xpay.Response("LGD_TIMESTAMP",0));
	    	 resultVo.setLGD_PCANCELSTR(xpay.Response("LGD_PCANCELSTR",0));
	    	 resultVo.setLGD_BUYER(xpay.Response("LGD_BUYER",0));
	    	 resultVo.setLGD_CARDINSTALLMONTH(xpay.Response("LGD_CARDINSTALLMONTH",0));
//		    	 resultVo.setLGD_BUYERSSN(xpay.Response("LGD_BUYERSSN",0));
	    	 resultVo.setLGD_PRODUCTINFO(xpay.Response("LGD_PRODUCTINFO",0));
//		    	 resultVo.setLGD_VANCODE(xpay.Response("LGD_VANCODE",0));
	    	 resultVo.setLGD_PRODUCTCODE(xpay.Response("LGD_PRODUCTCODE",0));
	    	 resultVo.setLGD_TID(xpay.Response("LGD_TID",0));
	    	 resultVo.setLGD_BUYERID(xpay.Response("LGD_BUYERID",0));
	    	 resultVo.setLGD_OID(xpay.Response("LGD_OID",0));
	    	 resultVo.setLGD_AMOUNT(xpay.Response("LGD_AMOUNT",0));
	    	 resultVo.setLGD_PCANCELFLAG(xpay.Response("LGD_PCANCELFLAG",0));
	    	 resultVo.setLGD_MID(xpay.Response("LGD_MID",0));

			 
			 
			 
			 System.out.println("빌키 요청처리가 완료되었습니다.  <br>");
			 System.out.println( "TX Response_code = " + xpay.m_szResCode + "<br>");
			 System.out.println( "TX Response_msg = " + xpay.m_szResMsg + "<p>");
			    
			 System.out.println("거래번호 : " + xpay.Response("LGD_TID",0) + "<br>");
			 System.out.println("결과코드 : " + xpay.Response("LGD_RESPCODE",0) + "<p>");
			
			 for (int i = 0; i < xpay.ResponseNameCount(); i++)
			 {
				 System.out.println(xpay.ResponseName(i) + " = ");
				 for (int j = 0; j < xpay.ResponseCount(); j++)
				 {
					 System.out.println("\t" + xpay.Response(xpay.ResponseName(i), j) + "<br>");
				 }
			 }
	        	
			/// orderService.insertXPayResultLog(resultVo);	// LG U+ 결제 결과 로그 저장 (성공/실패 모두 저장됨)

			 // (5) DB에 인증요청 결과 처리
	         if( "0000".equals( xpay.m_szResCode ) ) {
	         	// 통신상의 문제가 없을시
				// 최종결제요청 결과 성공 DB처리(LGD_RESPCODE 값에 따라 결제가 성공인지, 실패인지 DB처리)
	        	 System.out.println("최종결제요청 성공, DB처리하시기 바랍니다.<br>");
	         	            	
	         	//최종결제요청 결과를 DB처리합니다. (결제성공 또는 실패 모두 DB처리 가능)
				//상점내 DB에 어떠한 이유로 처리를 하지 못한경우 false로 변경해 주세요.
	         	boolean isDBOK = true; 
	         	
	         	// 결제 후 DB 처리 시작-------------------------------------------------------------------------------------------------
	         	JsonResultVO resultMap = new JsonResultVO();
	         	
	         	try{
		   	         session.removeAttribute("orderCd");	// 세션 주문 코드 삭제

	         		resultMap = orderService.dbProcessAfterXPay(resultVo);	// LG U+ 결제 후 DB 처리
	         		
	         		if(!resultMap.getResult()){
						resultVo.setLGD_RESPCODE("-2");
						resultVo.setLGD_RESPMSG(resultMap.getMsg());	// 에러 메시지
						isDBOK = false;
	         		}
	         	}catch (Exception e) {
					e.printStackTrace();
					resultVo.setLGD_RESPCODE("-2");
					resultVo.setLGD_RESPMSG("결제 후 DB 처리중 에러가 발생했습니다. 다시 결제하여 주시기 바랍니다.");
					isDBOK = false;
				}
	         	// 결제 후 DB 처리 끝-------------------------------------------------------------------------------------------------
	         	//---- SMS, 메일발송 (에러나더라도 rollback안함)
	         	try {
	         		if( isDBOK ) {
         				orderService.callSPSmsSendAndEmail("ORD002",  resultVo.getLGD_OID()); //카드결제결과 메일전송
	         		}
	         	}catch(Exception e) {
	         		e.printStackTrace();
	         	}
	         	
	         	if( !isDBOK ) {
					 
	         		xpay.Rollback("상점 DB처리 실패로 인하여 Rollback 처리 [TID:" +xpay.Response("LGD_TID",0)+",MID:" + xpay.Response("LGD_MID",0)+",OID:"+xpay.Response("LGD_OID",0)+"]");
	         		
	         		System.out.println( "TX Rollback Response_code = " + xpay.Response("LGD_RESPCODE",0) + "<br>");
	         		System.out.println( "TX Rollback Response_msg = " + xpay.Response("LGD_RESPMSG",0) + "<p>");
	         		
					if( "0000".equals( xpay.m_szResCode ) ) { 
						System.out.println("자동취소가 정상적으로 완료 되었습니다.<br>");

						resultVo.setLGD_RESPCODE("-3");
						resultVo.setLGD_RESPMSG("상점 DB처리 실패로 인하여 Rollback 처리<br>자동취소가 정상적으로 완료 되었습니다.<br>" + resultVo.getLGD_RESPMSG());
					}else{
						System.out.println("자동취소가 정상적으로 처리되지 않았습니다.<br>");

						resultVo.setLGD_RESPCODE("-3");
						resultVo.setLGD_RESPMSG("상점 DB처리 실패로 인하여 Rollback 처리<br>자동취소가 정상적으로 처리되지 않았습니다.<br>" + resultVo.getLGD_RESPMSG());
					}
	         	}
	         	
	         }else{
				//통신상의 문제 발생(최종결제요청 결과 실패 DB처리)
	        	 System.out.println("최종결제요청 결과 실패, DB처리하시기 바랍니다.<br>");            	
	         }
	     }else {
	    	 resultVo.setLGD_RESPCODE(xpay.m_szResCode);
	    	 resultVo.setLGD_RESPMSG(xpay.m_szResMsg);

	         //2)API 요청실패 화면처리
	    	 System.out.println( "결제요청이 실패하였습니다.  <br>");
	    	 System.out.println( "TX 결제요청 Response_code = " + xpay.m_szResCode + "<br>");
	    	 System.out.println( "TX 결제요청 Response_msg = " + xpay.m_szResMsg + "<p>");
	         
	     	//최종결제요청 결과 실패 DB처리
	    	 System.out.println("최종결제요청 결과 실패 DB처리하시기 바랍니다.<br>");            	            
	     }
	     
	    //model.addAttribute("resultVo", resultVo);
		// session.setAttribute("PAYREQ_MAP", payReqMap);
	    //model.addAttribute("HiddenParams" , ObjectToHiddenParams(resultVo));
/*		     
		    map.put("orderCd", session.getAttribute("orderCd").toString());
		    MethodUtil.redirectPostUrl(request, response, PathUtil.getCtx()+"/order/orderComplete.do", map); 
		    return null;
			//return PathUtil.getCtx()+"/order/orderComplete";
*/

	     MethodUtil.redirectPostUrl(request, response, PathUtil.getCtx()+"/order/orderComplete.do", resultVo);
	     return null;
	}
	
	
	

	   /**
	    * @Method : GoodsBillkeyOrder
	    * @Date: 2017. 7. 19.
	    * @Author :  강 병 철
	    * @Description	:	빌키 주문결제 (상품상세에서 원클릭 결제시)
	   */
	@RequestMapping(value="/order/GoodsBillkeyOrder", method = RequestMethod.POST) 
	public String GoodsBillkeyOrder(OrderVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
		
		UserInfo userInfo = UserInfo.getUserInfo();
		SqlMap billkeyInfo =  new SqlMap();
		if(ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
			// 에러 처리
			MethodUtil.alertMsgBack(request, response, "로그인이 필요합니다.");
			return null;
		}
		
		vo.setRegIdx(userInfo.getMemberIdx());
		vo.setMemberIdx(userInfo.getMemberIdx());
		vo.setMemberId(userInfo.getMemberId());
		vo.setMemberGradeIdx(Integer.parseInt(userInfo.getLevelIdx()));
		vo.setMemberNm(userInfo.getMemberNm());
		
		SqlMap memberDetail = orderService.getMemberDetail(vo.getMemberIdx());	// 회원 상세
		vo.setSenderEmail((String)memberDetail.get("email"));
		vo.setSenderPhoneNo(((String)memberDetail.get("phoneNo")).replaceAll("-", ""));	// 휴대폰번호에서 - 제거

		billkeyInfo = orderService.selectMainBillkey(vo.getMemberIdx());	// 대표 빌키 구하기
		
		if(billkeyInfo == null || billkeyInfo.size() == 0){
			MethodUtil.alertMsgBack(request, response, "빌키가 존재하지 않습니다.");
			return null;
		}
		
		// 기본 배송지 정보
		SqlMap defaultAddressInfo = orderService.getDefaultAddressInfo(vo.getMemberIdx());
		if(defaultAddressInfo == null){
			MethodUtil.alertMsgBack(request, response, "기본 배송지가 존재하지 않습니다.");
			return null;
		}
		
		vo.setRegHttpUserAgent(request.getHeader("user-agent"));	// HTTP_USER_AGENT
		
		InetAddress local = InetAddress.getLocalHost();	// LOCAL IP ADDRESS
		vo.setRegIp(local.getHostAddress());
		
		SqlMap orderInfo = null;
		
		// 넘어온 주문 정보
		if(vo.getOrderGoodsInfoListStr() != null && !vo.getOrderGoodsInfoListStr().isEmpty()){
			ObjectMapper mapper = new ObjectMapper();
			List<OrderGoodsVO> jsonObject = mapper.readValue(vo.getOrderGoodsInfoListStr(), new TypeReference<List<OrderGoodsVO>>() {});
			vo.setOrderGoodsInfoList(jsonObject);
			JsonResultVO resultMap = new JsonResultVO();
			// 넘어온 정보 주문 테이블에 저장
			resultMap = orderService.insertCartOrderInfo(vo, "Y", session);
			
			if(!resultMap.getResult()){
				MethodUtil.alertMsgBack(request, response, resultMap.getMsg());
				//MethodUtil.alertMsgUrl(request, response, resultMap.getMsg(), "/cart.do");
				return null;
			}
			
			vo.setOrderCd(session.getAttribute("orderCd").toString());	
			orderInfo = orderService.getOrderInfo(vo);					// 주문 마스터 정보(결제전건만)
			if(orderInfo == null || orderInfo.isEmpty()){
				MethodUtil.alertMsgBack(request, response, "주문 정보가 존재하지 않습니다!");
				return null;
			}
		}else{
			// 주문 정보 없음 에러 처리
			MethodUtil.alertMsgBack(request, response, "주문 상품 정보가 존재하지 않습니다!");
			return null;
		}
		
		
		final String CST_PLATFORM = SpringMessage.getMessage("xpay.PLATFORM"); //"test";// 테스트여부    service 또는 test
		//빌키 MID
		final String BILLMID = SpringMessage.getMessage("xpay.BILLMID");
		String LGD_BILLMID    = ("test".equals(CST_PLATFORM)?"t":"")+BILLMID;  //테스트 아이디는 't'를 제외하고 입력하세요.
		
		XPayBillKeyAuthVO billkeyAuthVo = new XPayBillKeyAuthVO();
		
		billkeyAuthVo.setCST_PLATFORM(CST_PLATFORM);
		billkeyAuthVo.setLGD_MID(LGD_BILLMID);
		billkeyAuthVo.setLGD_TXNAME("CardAuth");
		billkeyAuthVo.setLGD_PAN(billkeyInfo.get("billkey").toString());  //빌링키값
		billkeyAuthVo.setLGD_INSTALL("00");
		billkeyAuthVo.setLGD_OID(orderInfo.get("orderCd").toString());
		billkeyAuthVo.setLGD_AMOUNT(Integer.toString(Double.valueOf(orderInfo.get("totalPayPrice").toString()).intValue()));
		
		if(orderInfo == null || orderInfo.get("senderPhoneNo") == null){
			billkeyAuthVo.setLGD_BUYERPHONE("");
    	}else{
    		billkeyAuthVo.setLGD_BUYERPHONE(orderInfo.get("senderPhoneNo").toString());
    	}
		
		if(orderInfo == null || orderInfo.get("senderNm") == null){
			billkeyAuthVo.setLGD_BUYER("");
    	}else{
    		billkeyAuthVo.setLGD_BUYER(orderInfo.get("senderNm").toString());
    	}	

		if(orderInfo == null || orderInfo.get("memberId") == null){
			billkeyAuthVo.setLGD_BUYERID("");
    	}else{
    		billkeyAuthVo.setLGD_BUYERID(orderInfo.get("memberId").toString());
    	}
		
		if(orderInfo == null || orderInfo.get("senderEmail") == null){
			billkeyAuthVo.setLGD_BUYEREMAIL("");
    	}else{
    		billkeyAuthVo.setLGD_BUYEREMAIL(orderInfo.get("senderEmail").toString());
    	}

		if(orderInfo == null || orderInfo.get("productInfo") == null){
			billkeyAuthVo.setLGD_PRODUCTINFO("");
    	}else{
    		billkeyAuthVo.setLGD_PRODUCTINFO(orderInfo.get("productInfo").toString());
    	}
	    MethodUtil.redirectPostUrl(request, response, PathUtil.getCtx()+"/order/billkeyPayRes.do", billkeyAuthVo); 
	    return null;
	}
	
	/**-----------------------------------------------------------------------
	 * PAYCO 구매예약처리 페이지
	 *------------------------------------------------------------------------
	 * @Method paycoReserveAjax
	 * @author : 강병철
	 * @since
	 * @version
	 * @Description 
	 * 가맹점에서 전달한 파라미터를 받아 JSON 형태로 PAYCOAPI 와 통신한다.
	 */
	@RequestMapping(value = "/ajax/order/paycoReserveAjax", method = RequestMethod.POST) 
	public @ResponseBody void paycoReserveAjax(OrderVO vo, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
		PaycoSettingVO setting = new PaycoSettingVO();
		PaycoUtil 	 util 	= new PaycoUtil(setting.getServerType()); //CommonUtil

		ObjectMapper mapper = new ObjectMapper(); 		  //jackson json object
		
		UserInfo userInfo = UserInfo.getUserInfo();		
		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
			vo.setRegIdx(userInfo.getMemberIdx());
			vo.setMemberIdx(userInfo.getMemberIdx());
			vo.setMemberId(userInfo.getMemberId());
			vo.setMemberGradeIdx(Integer.parseInt(userInfo.getLevelIdx()));
			vo.setMemberNm(userInfo.getMemberNm());	
		}else{	// 비회원
			vo.setRegIdx(0);
		}
		SqlMap orderInfo = null;
		orderInfo = orderService.getOrderInfo(vo);								// 주문 마스터 정보(결제전건만)
		
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
		productUnitPrice = Double.valueOf(orderInfo.get("totalPayPrice").toString()).intValue();										 //상품 단가 ( 테스트용으로써 79000원으로 설정)
		productUnitPaymentPrice = Double.valueOf(orderInfo.get("totalPayPrice").toString()).intValue();					 		 		 //상품 결제 단가 ( 테스트용으로써 79000원으로 설정)
		productAmt = productUnitPrice * orderQuantity;						 //[필수]상품 결제금액(상품단가 * 수량)
		productPaymentAmt =  productAmt;// productUnitPaymentPrice * orderQuantity + 2500;	 //[필수]상품 결제금액(상품결제단가 * 수량, 배송비 설정시 상품가격에 포함시킴 ex) 2500원)
		iOption = "";											 		 //[선택]옵션(최대 100 자리)
		sortOrdering = orderNumber;											 //[필수]상품노출순서, 10자 이내
		productName = orderInfo.get("productInfo").toString();					 //[필수]상품명, 4000자 이내
		orderConfirmUrl = "";							 					 //[선택]주문완료 후 주문상품을 확인할 수 있는 url, 4000자 이내
		orderConfirmMobileUrl = "";					 						 //[선택]주문완료 후 주문상품을 확인할 수 있는 모바일 url, 1000자 이내
		productImageUrl = "";												 //[선택]이미지URL(배송비 상품이 아닌 경우는 필수), 4000자 이내, productImageUrl에 적힌 이미지를 썸네일해서 PAYCO 주문창에 보여줍니다.
		sellerOrderProductReferenceKey =orderInfo.get("productCode").toString();						 //[필수]가맹점에서 관리하는 상품키, 100자 이내.(외부가맹점에서 관리하는 주문상품 연동 키(sellerOrderProductReferenceKey)는 주문 별로 고유한 key이어야 합니다.)
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
		sellerOrderReferenceKey 	= vo.getOrderCd();						 //[필수]외부가맹점의 주문번호
		sellerOrderReferenceKeyType = "UNIQUE_KEY";								 //[선택]외부가맹점의 주문번호 타입(UNIQUE_KEY : 기본값, DUPLICATE_KEY : 중복가능한 키->외부가맹점의 주문번호가 중복 가능한 경우 사용)
		iCurrency 					= "KRW";									 //[선택]통화(default=KRW)
		totalPaymentAmt 		= totalProductPaymentAmt;			 		 //[필수]총 결재 할 금액
		orderTitle 					= orderInfo.get("productInfo").toString(); //"payco 결제 테스트  (JSP)444";			 	 //[선택]주문 타이틀
		returnUrl 					= setting.getPaycoReturnUrl(); 		 	 //[선택]주문완료 후 Redirect 되는 Url
		if(PathUtil.getDeviceNm().equals("M")){		// 모바일 결제시 m 으로 URL 변경
			returnUrl = returnUrl.replace("/w/", "/m/");
		}
		//[선택]주문완료 후 Redirect 되는 URL과 함께 전달되어야 하는 파라미터(Json 형태의 String)
		returnUrlParam 				= "{\"taxationType\":\""+taxationType+"\",\"totalTaxfreeAmt\":\""+totalTaxfreeAmt+"\",\"totalTaxableAmt\":\""+totalTaxableAmt+"\",\"totalVatAmt\":\""+totalVatAmt+"\"}";
		nonBankbookDepositInformUrl = setting.getPaycoBankReturnUrl();  //[선택]무통장입금완료통보 URL
		if(PathUtil.getDeviceNm().equals("M")){		// 모바일 결제시 m 으로 URL 변경
			nonBankbookDepositInformUrl = nonBankbookDepositInformUrl.replace("/w/", "/m/");
		}
		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
			orderMethod					= "EASYPAY_F";								 //[필수]주문유형   =  간편구매형:CHECKOUT, 간편결제형+회원결제:EASYPAY_F, 간편결제형+비회원:EASYPAY
		} else {
			orderMethod					= "EASYPAY";	
		}	
		if(PathUtil.getDeviceNm().equals("M")){		// 모바일 결제시 MOBILE로 파라미터 전달
			orderChannel 					= "MOBILE";								 //[선택]주문채널 (default : PC/MOBILE)
		}else{
			orderChannel 					= "PC";										 //[선택]주문채널 (default : PC/MOBILE)
		}
		inAppYn 							= "N";										 //[선택]인앱결제 여부(Y/N) (default = N)
		individualCustomNoInputYn 	= "N";										 //[선택]개인통관고유번호 입력 여부 (Y/N) (default = N)
		orderSheetUiType 				= "GRAY";									 //[선택]주문서 UI 타입 선택(선택 가능값 : RED/GRAY)		
		payMode							= "PAY2";									 //[선택]결제모드(PAY1 : 결제인증,승인통합 / PAY2 : 결제인증,승인분리)
																				 				// payMode는 선택값이나 값을 넘기지 않으면 DEFALUT 값은 PAY1 으로 셋팅되어있습니다.
		
		//설정한 주문정보로 Json String 을 작성합니다.		
		HashMap<String,Object> paycoorderInfo = new HashMap<String,Object>();
		paycoorderInfo.put("sellerKey", setting.getSellerKey());										//[필수]가맹점 코드
		paycoorderInfo.put("sellerOrderReferenceKey", sellerOrderReferenceKey); 			//[필수]외부가맹점 주문번호
		paycoorderInfo.put("sellerOrderReferenceKeyType", sellerOrderReferenceKeyType);  //[선택]외부가맹점의 주문번호 타입
		paycoorderInfo.put("currency", iCurrency);										//[선택]통화
		paycoorderInfo.put("totalPaymentAmt", totalPaymentAmt);							//[필수]총 결제금액(면세금액,과세금액,부가세의 합) totalTaxfreeAmt + totalTaxableAmt + totalVatAmt
		paycoorderInfo.put("totalTaxfreeAmt", totalTaxfreeAmt);							//[선택]면세금액(면세상품의 공급가액 합)
		paycoorderInfo.put("totalTaxableAmt", totalTaxableAmt);							//[선택]과세금액(과세상품의 공급가액 합)
		paycoorderInfo.put("totalVatAmt", totalVatAmt);									//[선택]부가세(과세상품의 부가세액 합)
		paycoorderInfo.put("orderTitle", orderTitle); 									//[선택]주문 타이틀
		paycoorderInfo.put("returnUrl", returnUrl);										//[선택]주문완료 후 Redirect 되는 URL
		paycoorderInfo.put("returnUrlParam", returnUrlParam);							//[선택]주문완료 후 Redirect 되는 URL과 함께 전달되어야 하는 파라미터(Json 형태의 String)
		paycoorderInfo.put("nonBankbookDepositInformUrl", nonBankbookDepositInformUrl); 	//[선택]무통장입금완료 통보 URL
		paycoorderInfo.put("orderMethod", orderMethod);									//[필수]주문유형
		paycoorderInfo.put("orderChannel", orderChannel);								//[선택]주문채널
		paycoorderInfo.put("inAppYn", inAppYn);											//[선택]인앱결제 여부
		paycoorderInfo.put("individualCustomNoInputYn", individualCustomNoInputYn);		//[선택]개인통관 고유번호 입력 여부
		paycoorderInfo.put("orderSheetUiType", orderSheetUiType);						//[선택]주문서 UI타입 선택
		paycoorderInfo.put("payMode", payMode);											//[선택]결제모드(PAY1 : 결제인증,승인통합 / PAY2 : 결제인증,승인분리) DEFAULT : PAY1
		paycoorderInfo.put("orderProducts", orderProducts); 								//[필수]주문상품 리스트
		
		
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
		if(PathUtil.getDeviceNm().equals("M")){		// 모바일 결제시 취소 URL 파라미터 세팅
			extraData.put("cancelMobileUrl", SpringMessage.getMessage("server.domain")+PathUtil.getCtx()+"/order/cartOrder.do");	//[선택][모바일 일경우 필수]모바일 결제페이지에서 취소 버튼 클릭시 이동할 URL
		}
																					
		Map<String,Object> viewOptions = new HashMap<String,Object>();
		viewOptions.put("showMobileTopGnbYn", "Y");									//[선택]모바일 상단 GNB 노출여부RKAODWJA 
		viewOptions.put("iframeYn", "Y");													//[선택]Iframe 호출(모바일에서 접근하는경우 iframe 사용시 이값을 "Y"로 보내주셔야 합니다.)
																										// Iframe 사용시 연동가이드 내용중 [Iframe 적용가이드]를 참고하시길 바랍니다.
		extraData.put("viewOptions", viewOptions);									//[선택]화면 UI 옵션
		
		/* 부가정보(bridgeOptions) - Json 형태의 String */
		/* 페이코 로그인시에 bridgeOptions을 세팅해주어야함.*/
		String clientId = SpringMessage.getMessage("payco.clientId");
		String accessToken = (String)session.getAttribute("paycoToken");
		
		if(clientId != null && accessToken != null){
			Map<String,Object> bridgeOptionsData = new HashMap<String,Object>();
			
			bridgeOptionsData.put("clientId", clientId);
			bridgeOptionsData.put("accessToken", accessToken);  //로그인시 발급받은 accessToken 
			extraData.put("bridgeOptions",  bridgeOptionsData);
		}
		paycoorderInfo.put("extraData",  mapper.writeValueAsString(extraData).toString().replaceAll("\"", "\\\""));	//[선택]부가정보 - Json 형태의 String
		
		//주문예약 API 호출 함수
		String result = util.payco_reserve(paycoorderInfo,setting.getLogYn());
		
		JsonNode node = mapper.readTree(result);
		String reserveOrderNo = "";
		String orderSheetUrl = "";
		
		if(node.path("code").toString().equals("0")){
			reserveOrderNo 	= node.path("result").get("reserveOrderNo").textValue();
			orderSheetUrl 		= node.path("result").get("orderSheetUrl").textValue();
			paycoorderInfo.put("reserveOrderNo", reserveOrderNo); 	
			paycoorderInfo.put("orderSheetUrl", orderSheetUrl);

			orderService.insertPaycoReserve(paycoorderInfo); //예약정보저장
			
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
	    * @Method : paycoRes
	    * @Date: 2017. 7. 26.
	    * @Author :  강 병 철
	    * @Description	:	PAYCO 결제 결과
	   */
	@RequestMapping(value = "/order/paycoRes")
	public void paycoRes(PaycoReturnVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
		PaycoSettingVO setting = new PaycoSettingVO();
		ObjectMapper mapper = new ObjectMapper(); 		  //jackson json object
		PaycoUtil 	 util 	= new PaycoUtil(setting.getServerType()); //Co
		
		PaycoCompletlVO completvo = new PaycoCompletlVO();
		PaycoApprovalVO approvalvo = new PaycoApprovalVO();
		
		Boolean doCancel    = false;						// 승인 후 오류발생시 결제취소 여부
		Boolean doApproval  = true; 					  	// 요청금액과 결제 금액 일치여부(true : 일치)

		XPayResultVO resultVo = new XPayResultVO();
		
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

			OrderVO ordervo = new OrderVO();
			UserInfo userInfo = UserInfo.getUserInfo();		
			if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
				ordervo.setRegIdx(userInfo.getMemberIdx());
				ordervo.setMemberIdx(userInfo.getMemberIdx());
				ordervo.setMemberId(userInfo.getMemberId());
				ordervo.setMemberGradeIdx(Integer.parseInt(userInfo.getLevelIdx()));
				ordervo.setMemberNm(userInfo.getMemberNm());	
			}else{	// 비회원
				ordervo.setRegIdx(0);
				ordervo.setSessionId(vo.getSessionId());
			}
			ordervo.setOrderCd(sellerOrderReferenceKey);
			SqlMap orderInfo = orderService.getOrderInfo(ordervo);	// 주문 마스터 정보(결제전건만)
			if (orderInfo == null) {
				doApproval = false;
				resultVo.setLGD_RESPCODE("-1");
				message   = "주문번호("+sellerOrderReferenceKey+")의 주문정보가 존재하지않습니다.";
			}
			else
			{
				int myDBtotalValue = Double.valueOf(orderInfo.get("totalPayPrice").toString()).intValue();						// 가맹점 DB에서 읽은 주문요청 금액
				if(myDBtotalValue != totalPaymentAmt){		// 주문 요청금액과 인증정보로 넘어온 결제금액 비교
					doApproval = false;
					resultVo.setLGD_RESPCODE("-2");
					message   = "주문금액과 결제금액이 틀립니다.";
				}
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
				if(node.path("code").toString().equals("0")) {
					// 예시
					try{
						/* 결제승인 후 리턴된 데이터 중 필요한 정보를 추출하여
						 * 가맹점 에서 필요한 작업을 실시합니다.(예 주문서 작성 등..)
						 * 결제연동시 리턴되는 PAYCO주문번호(orderNo)와 주문인증키(orderCertifyKey)에 대해 
						 * 가맹점 DB 저장이 필요합니다.
						 */
						 
						// 승인 후 전달받은 데이터 저장 변수 
						 Integer orderIdx = Integer.parseInt(orderInfo.get("orderIdx").toString()); ///주문번호
						 
						String memberName, memberEmail, orderChannel, paymentCompletionYn, paymentCompleteYmdt, orderProducts;
						
						Double dtotalPaymentAmt, dtotalOrderAmt, dtotalDeliveryFeeAmt, dtotalRemoteAreaDeliveryFeeAmt 
							,dreceiptPaycoPointAmt, dreceiptPaycoPointServiceAmt,dreceiptPaycoPointTaxfreeAmt
							,dreceiptPaycoPointTaxableAmt	,dreceiptPaycoPointVatAmt;
						
						// 주문완료 페이지 전달용 데이터 변수 
						String  orderNo, orderCertifyKey;
								
						// 결제승인 후 리턴된 데이터 중 필요한 정보를 추출 예시
						orderNo 			  				= node.path("result").get("orderNo") != null ? node.path("result").get("orderNo").textValue() : "";
						orderCertifyKey				= node.path("result").get("orderCertifyKey") != null ? node.path("result").get("orderCertifyKey").textValue() : "";
						
						reserveOrderNo				= node.path("result").get("reserveOrderNo") != null ? node.path("result").get("reserveOrderNo").textValue() : "";
						sellerOrderReferenceKey 	= node.path("result").get("sellerOrderReferenceKey") != null ? node.path("result").get("sellerOrderReferenceKey").textValue() : "";
						
						
						if (node.path("result").get("totalPaymentAmt") == null ) {
							totalPaymentAmt		= 0;
							dtotalPaymentAmt	= 0.0;
						} else {
							totalPaymentAmt		= (int)Float.parseFloat(node.path("result").get("totalPaymentAmt").toString());
							dtotalPaymentAmt 	= Double.valueOf(node.path("result").get("totalPaymentAmt").toString());
						}
						
						if (node.path("result").get("totalOrderAmt") == null ) {
							dtotalOrderAmt = 0.0;
						} else {
							dtotalOrderAmt 	= Double.valueOf(node.path("result").get("totalOrderAmt").toString());
						}

						if (node.path("result").get("totalDeliveryFeeAmt") == null ) {
							dtotalDeliveryFeeAmt = 0.0;
						} else {
							dtotalDeliveryFeeAmt 	= Double.valueOf(node.path("result").get("totalDeliveryFeeAmt").toString());
						}

						if (node.path("result").get("totalRemoteAreaDeliveryFeeAmt") == null ) {
							dtotalRemoteAreaDeliveryFeeAmt = 0.0;
						} else {
							dtotalRemoteAreaDeliveryFeeAmt 	= Double.valueOf(node.path("result").get("totalRemoteAreaDeliveryFeeAmt").toString());
						}

						if (node.path("result").get("receiptPaycoPointAmt") == null ) {
							dreceiptPaycoPointAmt = 0.0;
						} else {
							dreceiptPaycoPointAmt 	= Double.valueOf(node.path("result").get("receiptPaycoPointAmt").toString());
						}
						
						if (node.path("result").get("receiptPaycoPointServiceAmt") == null ) {
							dreceiptPaycoPointServiceAmt = 0.0;
						} else {
							dreceiptPaycoPointServiceAmt 	= Double.valueOf(node.path("result").get("receiptPaycoPointServiceAmt").toString());
						}
						if (node.path("result").get("receiptPaycoPointTaxfreeAmt") == null ) {
							dreceiptPaycoPointTaxfreeAmt = 0.0;
						} else {
							dreceiptPaycoPointTaxfreeAmt 	= Double.valueOf(node.path("result").get("receiptPaycoPointTaxfreeAmt").toString());
						}
						if (node.path("result").get("receiptPaycoPointTaxableAmt") == null) {
							dreceiptPaycoPointTaxableAmt = 0.0;
						} else {
							dreceiptPaycoPointTaxableAmt 	= Double.valueOf(node.path("result").get("receiptPaycoPointTaxableAmt").toString());
						}
						if (node.path("result").get("receiptPaycoPointVatAmt") == null) {
							dreceiptPaycoPointVatAmt = 0.0;
						} else {
							dreceiptPaycoPointVatAmt 	= Double.valueOf(node.path("result").get("receiptPaycoPointVatAmt").toString());
						}
						
						memberName = node.path("result").get("memberName") != null ? node.path("result").get("memberName").textValue() : "";
						memberEmail = node.path("result").get("memberEmail") != null ? node.path("result").get("memberEmail").textValue() : "";
						orderChannel = node.path("result").get("orderChannel") != null ? node.path("result").get("orderChannel").textValue() : "";

						paymentCompletionYn	= node.path("result").get("paymentCompletionYn") != null ? node.path("result").get("paymentCompletionYn").textValue() : "";
						paymentCompleteYmdt= node.path("result").get("paymentCompleteYmdt") != null ? node.path("result").get("paymentCompleteYmdt").textValue() : "";

						orderProducts = node.path("result").get("orderProducts") != null ? node.path("result").get("orderProducts").toString() : "";
																			
						List<PaycoPaymentVO> paymentList = new ArrayList<PaycoPaymentVO>();
						
						// paymentDetails 추출 예시
						ArrayNode paymentDetails_arr = (ArrayNode)node.path("result").get("paymentDetails");
						for(int j = 0; j < paymentDetails_arr.size(); j++){
							PaycoPaymentVO paymentvo = new PaycoPaymentVO();

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

							paymentTradeNo			= paymentDetails_arr.get(j).get("paymentTradeNo") != null ? paymentDetails_arr.get(j).get("paymentTradeNo").textValue() : "";
							paymentMethodCode 	= paymentDetails_arr.get(j).get("paymentMethodCode") != null ? paymentDetails_arr.get(j).get("paymentMethodCode").textValue() : "";
							paymentMethodName 	= paymentDetails_arr.get(j).get("paymentMethodName") != null ? paymentDetails_arr.get(j).get("paymentMethodName").textValue() : "";
							pgAdmissionNo			= paymentDetails_arr.get(j).get("pgAdmissionNo") != null ? paymentDetails_arr.get(j).get("pgAdmissionNo").textValue() : "";
							pgAdmissionYmdt		= paymentDetails_arr.get(j).get("pgAdmissionYmdt") != null ? paymentDetails_arr.get(j).get("pgAdmissionYmdt").textValue() : "";
							easyPaymentYn		    = paymentDetails_arr.get(j).get("easyPaymentYn") != null ? paymentDetails_arr.get(j).get("easyPaymentYn").textValue() : "";
							tradeYmdt		    		= paymentDetails_arr.get(j).get("tradeYmdt") != null ? paymentDetails_arr.get(j).get("tradeYmdt").textValue() : "";
							paymentAmt 				= paymentDetails_arr.get(j).get("paymentAmt") != null ? paymentDetails_arr.get(j).get("paymentAmt").asDouble() : 0;
							
							//신용카드 결제 정보
							if (paymentDetails_arr.get(j).get("cardSettleInfo") != null) {
								cardCompanyName 					= paymentDetails_arr.get(j).get("cardSettleInfo").get("cardCompanyName") != null ? paymentDetails_arr.get(j).get("cardSettleInfo").get("cardCompanyName").textValue() : "";
								cardCompanyCode 					= paymentDetails_arr.get(j).get("cardSettleInfo").get("cardCompanyCode") != null ? paymentDetails_arr.get(j).get("cardSettleInfo").get("cardCompanyCode").textValue() : "";
								cardNo 		 							= paymentDetails_arr.get(j).get("cardSettleInfo").get("cardNo") != null ? paymentDetails_arr.get(j).get("cardSettleInfo").get("cardNo").textValue() : "";		
								cardInstallmentMonthNumber	= paymentDetails_arr.get(j).get("cardSettleInfo").get("cardInstallmentMonthNumber") != null ? paymentDetails_arr.get(j).get("cardSettleInfo").get("cardInstallmentMonthNumber").textValue() : "";		
								cardAdmissionNo 		 			= paymentDetails_arr.get(j).get("cardSettleInfo").get("cardAdmissionNo") != null ? paymentDetails_arr.get(j).get("cardSettleInfo").get("cardAdmissionNo").textValue() : "";		
								cardInterestFreeYn 		 			= paymentDetails_arr.get(j).get("cardSettleInfo").get("cardInterestFreeYn") != null ? paymentDetails_arr.get(j).get("cardSettleInfo").get("cardInterestFreeYn").textValue() : "";		
								corporateCardYn 		 				= paymentDetails_arr.get(j).get("cardSettleInfo").get("corporateCardYn") != null ? paymentDetails_arr.get(j).get("cardSettleInfo").get("corporateCardYn").textValue() : "";		
								partCancelPossibleYn 		 		= paymentDetails_arr.get(j).get("cardSettleInfo").get("partCancelPossibleYn") != null ? paymentDetails_arr.get(j).get("cardSettleInfo").get("partCancelPossibleYn").textValue() : "";		
							}
							
							//핸드폰결제정보
							if (paymentDetails_arr.get(j).get("cellphoneSettleInfo") != null) {
								companyName 	= paymentDetails_arr.get(j).get("cellphoneSettleInfo").get("companyName") != null ? paymentDetails_arr.get(j).get("cellphoneSettleInfo").get("companyName").textValue() : "";
								cellphoneNo		= paymentDetails_arr.get(j).get("cellphoneSettleInfo").get("cellphoneNo") != null ? paymentDetails_arr.get(j).get("cellphoneSettleInfo").get("cellphoneNo").textValue() : "";									
							}
							
							//실시간계좌이체 결제정보
							if (paymentDetails_arr.get(j).get("realtimeAccountTransferSettleInfo") != null) {
								bankName	= paymentDetails_arr.get(j).get("realtimeAccountTransferSettleInfo").get("bankName") != null ? paymentDetails_arr.get(j).get("realtimeAccountTransferSettleInfo").get("bankName").textValue() : "";
								bankCode	= paymentDetails_arr.get(j).get("realtimeAccountTransferSettleInfo").get("bankCode") != null ? paymentDetails_arr.get(j).get("realtimeAccountTransferSettleInfo").get("bankCode").textValue() : "";
							}
							
							//무통장입금결제정보
							if (paymentDetails_arr.get(j).get("nonBankbookSettleInfo") != null) {
								bankName						= paymentDetails_arr.get(j).get("nonBankbookSettleInfo").get("bankName") != null ? paymentDetails_arr.get(j).get("nonBankbookSettleInfo").get("bankName").textValue() : "";
								bankCode						= paymentDetails_arr.get(j).get("nonBankbookSettleInfo").get("bankCode") != null ? paymentDetails_arr.get(j).get("nonBankbookSettleInfo").get("bankCode").textValue() : "";
								accountNo						= paymentDetails_arr.get(j).get("nonBankbookSettleInfo").get("accountNo") != null ? paymentDetails_arr.get(j).get("nonBankbookSettleInfo").get("accountNo").textValue() : "";
								paymentExpirationYmd		= paymentDetails_arr.get(j).get("nonBankbookSettleInfo").get("paymentExpirationYmd") != null ? paymentDetails_arr.get(j).get("nonBankbookSettleInfo").get("paymentExpirationYmd").textValue() : "";

								resultVo.setLGD_PAYTYPE("SC0040");							// 가상계좌 (LG U+의 가상계좌 타입이 SC0040 라서 그걸로 보냄)
								resultVo.setLGD_FINANCENAME(bankName);				// 입금 은행명
								resultVo.setLGD_ACCOUNTNUM(accountNo);				// 입금 계좌번호
								resultVo.setLGD_CLOSEDATE(paymentExpirationYmd);	// 입금마감일 (YYYYMMDDhhddss)인데 PAYCO에선 YYYYMMDD만 반환됨
							}
							
							//쿠폰결제정보
							if (paymentDetails_arr.get(j).get("couponSettleInfo") != null) {
								Double.valueOf(node.path("result").get("receiptPaycoPointTaxableAmt").toString());
								if (paymentDetails_arr.get(j).get("couponSettleInfo").get("discountAmt") != null) {
									discountAmt = String.valueOf(paymentDetails_arr.get(j).get("couponSettleInfo").get("discountAmt").asDouble()); 
								} else {
									discountAmt = "";
								}
								if (paymentDetails_arr.get(j).get("couponSettleInfo").get("discountConditionAmt") != null) {
									discountConditionAmt = String.valueOf(paymentDetails_arr.get(j).get("couponSettleInfo").get("discountConditionAmt").asDouble()); 
								} else {
									discountConditionAmt = "";
								}
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
							//결과저장
				          	
				         	// 결제 후 DB 처리 시작-------------------------------------------------------------------------------------------------
				         	JsonResultVO resultMap = new JsonResultVO();
				         	
			         		resultMap = orderService.dbProcessAfterPayco(sellerOrderReferenceKey, approvalvo);	// Payco 결제 후 DB 처리
			         		
			         		if(!resultMap.getResult()){
								resultVo.setLGD_RESPCODE("-2");
								resultVo.setLGD_RESPMSG(resultMap.getMsg());	// 에러 메시지
								doCancel = true;
			         		}
			         		else {
								resultVo.setLGD_RESPCODE("0000");
								message   = "주문이 정상적으로 완료되었습니다.";	

								resultVo.setLGD_BUYER(orderInfo.get("senderNm").toString());
								if(paymentCompleteYmdt != null && !paymentCompleteYmdt.isEmpty()){
									resultVo.setLGD_PAYDATE(paymentCompleteYmdt);
								}else{
									SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMddHHmmss", Locale.KOREA );
									Date currentTime = new Date();
									resultVo.setLGD_PAYDATE(formatter.format(currentTime));
								}
								resultVo.setLGD_OID(sellerOrderReferenceKey);
								resultVo.setLGD_AMOUNT(Double.toString(totalPaymentAmt));
			         		}
				         	// 결제 후 DB 처리 끝-------------------------------------------------------------------------------------------------
							
						} catch (Exception e) {
							e.printStackTrace();
							resultVo.setLGD_RESPCODE("-3");
							message   = "주문서 작성중 데이터처리 오류로 인하여 주문이 취소 되었습니다.";
							doCancel = true;
						}
					
					}catch(Exception e){
						e.printStackTrace();
						resultVo.setLGD_RESPCODE("-4");
						message   = "주문서 작성중 데이터처리 오류로 인하여 주문이 취소 되었습니다.";
						doCancel = true;
					}
					
					//---- SMS, 메일발송 (에러나더라도 rollback안함)
		         	try {
		         		if( !doCancel ) {
		         			if (resultVo.getLGD_PAYTYPE().equals("SC0040") )  //가상계좌
		         			{
		         				orderService.callSPSmsSendAndEmail("ORD001",  resultVo.getLGD_OID());
		         			}
		         			else
		         			{
		         				orderService.callSPSmsSendAndEmail("ORD002",  resultVo.getLGD_OID());
		         			}
		         		}
		         	}catch(Exception e) {
		         		e.printStackTrace();
		         	}
						
				} else {
					//승인 API 호출시 에러
					resultVo.setLGD_RESPCODE(node.path("code").textValue());
					message   = node.path("message").textValue();
					
					MethodUtil.alertMsgUrl(request, response, message, "/order/cartOrder.do");	// 잔액 부족등 결제 실패시 결제 페이지로 돌림
					return;
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
						resultVo.setLGD_RESPCODE(cancelNode.path("code").textValue());
						message   = "PAYCO 결제 취소 중 에러가 발생하였습니다.("+cancelNode.path("message").textValue()+")";
						System.out.println(message);
					//	returnUrl = "index.do";
					}
				}
			}
		}
		resultVo.setLGD_RESPMSG(message);
		MethodUtil.redirectPostUrl(request, response, PathUtil.getCtx()+"/order/orderComplete.do", resultVo);
		//completvo.setResultMessage(message);
		//MethodUtil.redirectPostUrl(request, response, PathUtil.getCtx()+"/payco/paycoComplete.do", completvo); 
	}	

	  /**
	    * @Method : paycoReturn
	    * @Date: 2017. 7. 4.
	    * @Author :   강 병 철
	    * @Description	:	PAYCO 결제 결과
	   */
	@RequestMapping(value="/order/paycoReturn") 
	public String paycoReturn(PaycoReturnVO vo,  HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
	
	    model.addAttribute("reqMap", vo);
	    model.addAttribute("HiddenParams" , ObjectToHiddenParams(vo));
		return PathUtil.getCtx()+"/order/paycoReturn";
	}
	
	   /**
	    * @Method : paycoBankReturn
	    * @Date: 2017. 8. 9.
	    * @Author :  서 정 길
	    * @Description	:	PAYCO 무통장입금 완료통보수신
		* 무통장입금일 경우 입금완료 통보 URL을 설정하여 수신 받는다.
		* param : response=JSON
		* return : "OK","ERROR"
	   */
	@RequestMapping(value = "/order/paycoBankReturn")
	public void paycoBankReturn(HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
		System.out.println("================Payco 무통장입금 처리 데이터 시작 ==========================");
		java.text.SimpleDateFormat dateformat = new java.text.SimpleDateFormat("yyyyMMdd HH:mm:ss");
		
		ObjectMapper mapper = new ObjectMapper(); //jackson json object
		String strResult 	= "ERROR";				  //반환값
		
		//PAYCO에서 송신하는 주문정보 JSON object
		String strResponse = request.getParameter("response") == null ? "" :(String)request.getParameter("response");
		System.out.println("================Payco 무통장입금 처리 데이터 : " + strResponse);
		
		//POST 값 중 response 값이 없으면 에러를 표시하고 API를 종료합니다.
		if("".equals(strResponse)){
			strResult = "ERROR";
			PrintWriter pw;
			pw = response.getWriter();
			response.setContentType("text/html; charset=utf-8");
			pw.print(strResult);
			pw.flush();
			pw.close();
			
			return;
		}
		
		StringBuffer sb = new StringBuffer();
		
		String sellerOrderReferenceKey 	  	= "";
		String reserveOrderNo 		   	  			= "";
		String orderNo 				   	  			= "";
		String memberName 			   	  		= "";
		int totalOrderAmt 			   	  			= 0;
		int totalDeliveryFeeAmt 	   	  			= 0;
		int totalRemoteAreaDeliveryFeeAmt	= 0;
		
		try{
		    
			strResponse = URLDecoder.decode(strResponse, "UTF-8");
			
			System.out.println("================Payco 무통장입금 처리 데이터 ==========================");
	        System.out.println(strResponse);
	          
	        strResponse = strResponse.replaceAll("&quot;", "\"");
	        strResponse = strResponse.replaceAll("&#039;", "'");
	        strResponse = strResponse.replaceAll("&amp;", "&");
	        strResponse = strResponse.replaceAll("&nbsp;", " ");
			
			Map<String, Object> jObject = mapper.readValue(strResponse, new TypeReference<HashMap<String, Object>>(){});
			

			/*========== 수신 데이터를 사용하여 주문서를 조회합니다.(가맹점 처리) ==========*/
			sellerOrderReferenceKey 	= (String)jObject.get("sellerOrderReferenceKey"); //가맹점에서 발급하는 주문 연동 Key
			reserveOrderNo 				= (String)jObject.get("reserveOrderNo");		  //주문예약번호
			orderNo 							= (String)jObject.get("orderNo");				  //주문번호
			memberName 					= (String)jObject.get("memberName");			  //주문자명
		
			// jackson Tree 이용
			JsonNode node = mapper.readTree(strResponse);

			List<PaycoPaymentVO> paymentList = new ArrayList<PaycoPaymentVO>();
			
			// paymentDetails 추출 예시
			ArrayNode paymentDetails_arr = (ArrayNode)node.get("paymentDetails");
			for(int j = 0; j < paymentDetails_arr.size(); j++){
				
				String paymentTradeNo		= paymentDetails_arr.get(j).get("paymentTradeNo") != null ? paymentDetails_arr.get(j).get("paymentTradeNo").textValue() : "";
				String paymentMethodCode 	= paymentDetails_arr.get(j).get("paymentMethodCode") != null ? paymentDetails_arr.get(j).get("paymentMethodCode").textValue() : "";
				String paymentMethodName = paymentDetails_arr.get(j).get("paymentMethodName") != null ? paymentDetails_arr.get(j).get("paymentMethodName").textValue() : "";
				String pgAdmissionNo			= paymentDetails_arr.get(j).get("pgAdmissionNo") != null ? paymentDetails_arr.get(j).get("pgAdmissionNo").textValue() : "";
				String pgAdmissionYmdt		= paymentDetails_arr.get(j).get("pgAdmissionYmdt") != null ? paymentDetails_arr.get(j).get("pgAdmissionYmdt").textValue() : "";
				String easyPaymentYn		    = paymentDetails_arr.get(j).get("easyPaymentYn") != null ? paymentDetails_arr.get(j).get("easyPaymentYn").textValue() : "";
				String tradeYmdt		    		= paymentDetails_arr.get(j).get("tradeYmdt") != null ? paymentDetails_arr.get(j).get("tradeYmdt").textValue() : "";
				Double paymentAmt 			= paymentDetails_arr.get(j).get("paymentAmt") != null ? paymentDetails_arr.get(j).get("paymentAmt").asDouble() : 0;

				PaycoPaymentVO paymentvo = new PaycoPaymentVO();
				paymentvo.setPaymentTradeNo(paymentTradeNo);
				paymentvo.setPaymentMethodCode(paymentMethodCode);
				paymentvo.setPaymentMethodName(paymentMethodName);
				paymentvo.setPaymentAmt(paymentAmt);
				paymentvo.setTradeYmdt(tradeYmdt);
				paymentvo.setPgAdmissionNo(pgAdmissionNo);
				paymentvo.setPgAdmissionYmdt(pgAdmissionYmdt);
				paymentvo.setEasyPaymentYn(easyPaymentYn);
				
				paymentList.add(paymentvo);
			}
			
			/*========== 지급이 완료 되었다고 받았으면 지급 완료 처리.(가맹점 처리) ==========*/
			String paymentCompletionYn = (String)jObject.get("paymentCompletionYn"); //지급완료 값 ( Y/N )
			if("Y".equals(paymentCompletionYn)){
				
				// 무통장 입금 확인 필드 업데이트(가맹점)
				// .....
				JsonResultVO resultMap = new JsonResultVO();

				try{
					resultMap = orderService.updatePaycoCasNoteUrlOrderMaster(sellerOrderReferenceKey, paymentList);	// PAYCO 가상계좌 입금 확인시 - 주문 상태 결제 완료로 수정 & 결제일자 세팅
					
					if(!resultMap.getResult()){
						strResult = "ERROR";
					}else{
						strResult = "OK";
					}
				}catch (Exception e) {
					e.printStackTrace();
					strResult = "ERROR";
				}

				orderService.insertCyberAccountOrderStatusLog(sellerOrderReferenceKey);	// 가상계좌 입금 완료시 주문 상태 로그 저장
			} else {
				/*
				*지급이 완료 되지 않았다고 받았으면 지급 되지 않았다고 처리
				*단, 지급되지 않은경우 이 API 가 호출되지 않으므로 필요는 없음.
				*혹시 모를 경우를 대비하여 처리
				*/
				strResult = "ERROR";
			}
		   	
		}catch(Exception e){
			e.printStackTrace();
			strResult = "ERROR";
			/*StackTraceElement[] elem = e.getStackTrace();
			for ( int i = 0; i < elem.length; i++ ){
				elem[i].toString();
				sb.append(elem[i].toString() + "\n");
			}*/
		}
		
		/* 상단 처리결과를 payco로 리턴한다.(성공:'OK', 실패:'ERROR') */
		PrintWriter pw;
		pw = response.getWriter();
		response.setContentType("text/html; charset=utf-8");
		pw.print(strResult);
		pw.flush();
		pw.close();
	}

	   /**
	    * @Method : PcodeApplyAjax
	    * @Date: 2017. 7. 18.
	    * @Author :  강병철
	    * @Description	:	프로모션 코드 등록
	   */
	@RequestMapping(value = "/ajax/order/PcodeApplyAjax", method = RequestMethod.POST) 
	public @ResponseBody JsonResultVO PcodeApplyAjax(OrderVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		JsonResultVO resultMap = new JsonResultVO();

		UserInfo userInfo = UserInfo.getUserInfo();
		
		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
			vo.setRegIdx(userInfo.getMemberIdx());
			vo.setMemberIdx(userInfo.getMemberIdx());
			vo.setMemberId(userInfo.getMemberId());
		}else{	// 비회원
			resultMap.setResult(false);
			resultMap.setMsg("로그인이 필요합니다.");
			return resultMap;
		}
		
		try {
			vo.setRegHttpUserAgent(request.getHeader("user-agent"));	// HTTP_USER_AGENT
			
			InetAddress local = InetAddress.getLocalHost();	// LOCAL IP ADDRESS
			vo.setRegIp(local.getHostAddress());

			// 결제수단 (공통코드 PAY_TYPE10 : 신용카드, PAY_TYPE15 : 빌키, PAY_TYPE20 : 실시간계좌, PAY_TYPE25 : 가상계좌, PAY_TYPE30 : 휴대폰, PAY_TYPE35 : PAYCO, PAY_TYPE40 : 카카오페이, PAY_TYPE45 : Npay, PAY_TYPE50 : PAYNOW, PAY_TYPE90 : 포인트)
			if(vo.getSelectPayType().equals("BILLKEY")){	// ONE CLICK 원클릭 결제
				vo.setPayType("PAY_TYPE15");
			}else if(vo.getSelectPayType().equals("SC0010")){	// 신용카드
				vo.setPayType("PAY_TYPE10");
			}else if(vo.getSelectPayType().equals("SC0030")){	// 실시간 계좌 이체
				vo.setPayType("PAY_TYPE20");
			}else if(vo.getSelectPayType().equals("SC0040")){	// 가상계좌 입금
				vo.setPayType("PAY_TYPE25");
			}else if(vo.getSelectPayType().equals("SC0060")){	// 휴대폰 결제
				vo.setPayType("PAY_TYPE30");
			}else if(vo.getSelectPayType().equals("PAYCO")){	// PAYCO
				vo.setPayType("PAY_TYPE35");
			}else if(vo.getSelectPayType().equals("PAYNOW")){	// PAYNOW
				vo.setPayType("PAY_TYPE50");
			}else if(vo.getSelectPayType().equals("SMILEPAY")){	// SMILEPAY
				vo.setPayType("PAY_TYPE55");
			}else if(vo.getSelectPayType().equals("POINT")){	// 포인트 결제 (결제금액 0원)
				vo.setPayType("PAY_TYPE90");
			}
			
			try{
				orderService.updateOrderMasterInfo(vo);	// 주문 정보 DB 저장
			}catch(Exception e){
				e.printStackTrace();
				resultMap.setResult(false);
				resultMap.setMsg("결제 정보 저장 중 에러가 발생했습니다!");
				return resultMap;
			}
			
			String orderIdx = orderService.getOrderIdx(vo);	// 주문 번호 반환
			if(orderIdx == null || orderIdx.isEmpty()){
				MethodUtil.alertMsgUrl(request, response, "주문 번호가 존재하지 않습니다!", "/cart.do");
				return null;
			}

			//프로모션코드 체크
			SqlMap pcodeinfo = orderService.checkPromotionCode(vo.getPromotioncode());
			
			if (pcodeinfo  == null)
			{		
				resultMap.setResult(false);
				resultMap.setMsg("사용 할 수 없는 프로모션코드 입니다.\n프로모션코드를 다시 확인해 주세요.");
			}
			else 
			{
				if (pcodeinfo.get("gubun").toString().equals("END")) {
					resultMap.setResult(false);
					resultMap.setMsg("사용기간이 만료된 프로모션코드 입니다.\n프로모션코드를 다시 확인해 주세요.");				
				}
				else {
					try {
						int discountrate = Double.valueOf(pcodeinfo.get("discountRate").toString()).intValue();
						SqlMap info = orderService.applyPromotionCode(orderIdx, vo.getPromotioncode(), discountrate);	
						resultMap.setResult(true);
						
						if(info == null){
							resultMap.setResult(false);
							resultMap.setMsg("주문 유효성 확인 중 에러가 발생했습니다!");
							return resultMap;
						}else{
							if(!info.get("result").toString().equals("TRUE")){
								resultMap.setResult(false);
								resultMap.setMsg(info.get("result").toString());
								return resultMap;
							}
						}
					}catch(Exception e){
						e.printStackTrace();
						resultMap.setResult(false);
						if(resultMap.getMsg() == null || resultMap.getMsg().isEmpty()){
							resultMap.setMsg("프로모션 코드 적용중 에러가 발생했습니다! (100)");
						}			
					}	
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			resultMap.setResult(false);
			if(resultMap.getMsg() == null || resultMap.getMsg().isEmpty()){
				resultMap.setMsg("에러가 발생했습니다! (200)");
			}			
		}

		return resultMap;
	}
	
	/**
	    * @Method : PcodeApplyRandomAjax
	    * @Date: 2017. 7. 18.
	    * @Author :  강병철
	    * @Description	:	프로모션 랜덤 코드 등록
	   */
	@RequestMapping(value = "/ajax/order/PcodeApplyRandomAjax", method = RequestMethod.POST) 
	public @ResponseBody JsonResultVO PcodeApplyRandomAjax(OrderVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		JsonResultVO resultMap = new JsonResultVO();

		UserInfo userInfo = UserInfo.getUserInfo();
		
		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
			vo.setRegIdx(userInfo.getMemberIdx());
			vo.setMemberIdx(userInfo.getMemberIdx());
			vo.setMemberId(userInfo.getMemberId());
		}else{	// 비회원
			resultMap.setResult(false);
			resultMap.setMsg("로그인이 필요합니다.");
			return resultMap;
		}
		
		try {
			vo.setRegHttpUserAgent(request.getHeader("user-agent"));	// HTTP_USER_AGENT
			
			InetAddress local = InetAddress.getLocalHost();	// LOCAL IP ADDRESS
			vo.setRegIp(local.getHostAddress());

			// 결제수단 (공통코드 PAY_TYPE10 : 신용카드, PAY_TYPE15 : 빌키, PAY_TYPE20 : 실시간계좌, PAY_TYPE25 : 가상계좌, PAY_TYPE30 : 휴대폰, PAY_TYPE35 : PAYCO, PAY_TYPE40 : 카카오페이, PAY_TYPE45 : Npay, PAY_TYPE50 : PAYNOW, PAY_TYPE90 : 포인트)
			if(vo.getSelectPayType().equals("BILLKEY")){	// ONE CLICK 원클릭 결제
				vo.setPayType("PAY_TYPE15");
			}else if(vo.getSelectPayType().equals("SC0010")){	// 신용카드
				vo.setPayType("PAY_TYPE10");
			}else if(vo.getSelectPayType().equals("SC0030")){	// 실시간 계좌 이체
				vo.setPayType("PAY_TYPE20");
			}else if(vo.getSelectPayType().equals("SC0040")){	// 가상계좌 입금
				vo.setPayType("PAY_TYPE25");
			}else if(vo.getSelectPayType().equals("SC0060")){	// 휴대폰 결제
				vo.setPayType("PAY_TYPE30");
			}else if(vo.getSelectPayType().equals("PAYCO")){	// PAYCO
				vo.setPayType("PAY_TYPE35");
			}else if(vo.getSelectPayType().equals("PAYNOW")){	// PAYNOW
				vo.setPayType("PAY_TYPE50");
			}else if(vo.getSelectPayType().equals("SMILEPAY")){	// SMILEPAY
				vo.setPayType("PAY_TYPE55");
			}else if(vo.getSelectPayType().equals("POINT")){	// 포인트 결제 (결제금액 0원)
				vo.setPayType("PAY_TYPE90");
			}
			
			try{
				orderService.updateOrderMasterInfo(vo);	// 주문 정보 DB 저장
			}catch(Exception e){
				e.printStackTrace();
				resultMap.setResult(false);
				resultMap.setMsg("결제 정보 저장 중 에러가 발생했습니다!");
				return resultMap;
			}
			
			String orderIdx = orderService.getOrderIdx(vo);	// 주문 번호 반환
			if(orderIdx == null || orderIdx.isEmpty()){
				MethodUtil.alertMsgUrl(request, response, "주문 번호가 존재하지 않습니다!", "/cart.do");
				return null;
			}
			
			//프로모션코드 체크
			SqlMap pcodeinfo = orderService.checkPromotionCodeRandom(vo.getPromotioncoderandom());
			
			if (pcodeinfo  == null)
			{		
				resultMap.setResult(false);
				resultMap.setMsg("사용 할 수 없는 프로모션코드 입니다.\n프로모션코드를 다시 확인해 주세요.");
			}
			else 
			{
				if (pcodeinfo.get("gubun").toString().equals("END")) {
					resultMap.setResult(false);
					resultMap.setMsg("사용기간이 만료된 프로모션코드 입니다.\n프로모션코드를 다시 확인해 주세요.");				
				}
				else {
					try {
						int discountrate = Double.valueOf(pcodeinfo.get("discountRate").toString()).intValue();
						SqlMap info = orderService.applyPromotionCodeRandom(orderIdx, vo.getPromotioncoderandom(), discountrate, vo.getRandomcodeYn());	
						resultMap.setResult(true);
						
						if(info == null){
							resultMap.setResult(false);
							resultMap.setMsg("주문 유효성 확인 중 에러가 발생했습니다!");
							return resultMap;
						}else{
							if(!info.get("result").toString().equals("TRUE")){
								resultMap.setResult(false);
								resultMap.setMsg(info.get("result").toString());
								return resultMap;
							}
						}
					}catch(Exception e){
						e.printStackTrace();
						resultMap.setResult(false);
						if(resultMap.getMsg() == null || resultMap.getMsg().isEmpty()){
							resultMap.setMsg("프로모션 코드 적용중 에러가 발생했습니다! (100)");
						}			
					}	
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			resultMap.setResult(false);
			if(resultMap.getMsg() == null || resultMap.getMsg().isEmpty()){
				resultMap.setMsg("에러가 발생했습니다! (200)");
			}			
		}

		return resultMap;
	}
	
	 /**
	    * @Method : pointApplyAjax
	    * @Date: 2017. 7. 18.
	    * @Author :  강병철
	    * @Description	:	포인트 사용
	   */
	@RequestMapping(value = "/ajax/order/pointApplyAjax", method = RequestMethod.POST) 
		public @ResponseBody JsonResultVO pointApplyAjax(OrderVO vo, String usepoint, HttpServletRequest request, HttpServletResponse response) throws Exception{
			
			JsonResultVO resultMap = new JsonResultVO();

			UserInfo userInfo = UserInfo.getUserInfo();
			
			if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
				vo.setRegIdx(userInfo.getMemberIdx());
				vo.setMemberIdx(userInfo.getMemberIdx());
				vo.setMemberId(userInfo.getMemberId());
			}else{	// 비회원
				resultMap.setResult(false);
				resultMap.setMsg("로그인이 필요합니다.");
				return resultMap;
			}
			
			try {
				
				vo.setRegHttpUserAgent(request.getHeader("user-agent"));	// HTTP_USER_AGENT
				
				InetAddress local = InetAddress.getLocalHost();	// LOCAL IP ADDRESS
				vo.setRegIp(local.getHostAddress());
	
				// 결제수단 (공통코드 PAY_TYPE10 : 신용카드, PAY_TYPE15 : 빌키, PAY_TYPE20 : 실시간계좌, PAY_TYPE25 : 가상계좌, PAY_TYPE30 : 휴대폰, PAY_TYPE35 : PAYCO, PAY_TYPE40 : 카카오페이, PAY_TYPE45 : Npay, PAY_TYPE50 : PAYNOW, PAY_TYPE90 : 포인트)
				if(vo.getSelectPayType().equals("BILLKEY")){	// ONE CLICK 원클릭 결제
					vo.setPayType("PAY_TYPE15");
				}else if(vo.getSelectPayType().equals("SC0010")){	// 신용카드
					vo.setPayType("PAY_TYPE10");
				}else if(vo.getSelectPayType().equals("SC0030")){	// 실시간 계좌 이체
					vo.setPayType("PAY_TYPE20");
				}else if(vo.getSelectPayType().equals("SC0040")){	// 가상계좌 입금
					vo.setPayType("PAY_TYPE25");
				}else if(vo.getSelectPayType().equals("SC0060")){	// 휴대폰 결제
					vo.setPayType("PAY_TYPE30");
				}else if(vo.getSelectPayType().equals("PAYCO")){	// PAYCO
					vo.setPayType("PAY_TYPE35");
				}else if(vo.getSelectPayType().equals("PAYNOW")){	// PAYNOW
					vo.setPayType("PAY_TYPE50");
				}else if(vo.getSelectPayType().equals("SMILEPAY")){	// SMILEPAY
					vo.setPayType("PAY_TYPE55");
				}else if(vo.getSelectPayType().equals("POINT")){	// 포인트 결제 (결제금액 0원)
					vo.setPayType("PAY_TYPE90");
				}
				
				try{
					orderService.updateOrderMasterInfo(vo);	// 주문 정보 DB 저장
				}catch(Exception e){
					e.printStackTrace();
					resultMap.setResult(false);
					resultMap.setMsg("결제 정보 저장 중 에러가 발생했습니다!");
					return resultMap;
				}
				
				String orderIdx = orderService.getOrderIdx(vo);	// 주문 번호 반환
				if(orderIdx == null || orderIdx.isEmpty()){
					MethodUtil.alertMsgUrl(request, response, "주문 번호가 존재하지 않습니다!", "/cart.do");
					return null;
				}

				try {
					int UsePoint = Integer.parseInt(usepoint);
					SqlMap memberDetail = orderService.getMemberDetail(vo.getMemberIdx());	// 회원 상세
					int myPointPrice = 0;
				///	Double payPrice = vo.getTotalPayPrice();  //결제금액
					
					
					if (memberDetail.get("pointPrice") != null && memberDetail.get("pointPrice").toString() != "") {
						myPointPrice = Integer.parseInt(memberDetail.get("pointPrice").toString());
					}
					else {
						myPointPrice = 0;
					}
					
					if (UsePoint > myPointPrice) {
						resultMap.setResult(false);
						resultMap.setMsg("사용가능한 포인트를 다시 확인해 주세요.<br />(내 보유 포인트 "+String.valueOf(myPointPrice)+"P)");
						return resultMap;
					}

					if (UsePoint < 0) {
						resultMap.setResult(false);
						resultMap.setMsg("포인트는 0보다 큰 수를 입력해 주세요.<br />(내 보유 포인트 "+String.valueOf(myPointPrice)+"P)");
						return resultMap;
					}
					/*
					if (UsePoint >payPrice){
						resultMap.setResult(false);
						resultMap.setMsg(Integer.toString(Double.valueOf(payPrice).intValue())+" 보다 작은 값을 넣어 주세요.<br />(최대 사용가능 결제액 : "+Integer.toString(Double.valueOf(payPrice).intValue())+"P)");
						return resultMap;
					}
					*/
					SqlMap info = orderService.applyUsePoint(orderIdx, UsePoint);	
					resultMap.setResult(true);
					
					if(info == null){
						resultMap.setResult(false);
						resultMap.setMsg("주문 유효성 확인 중 에러가 발생했습니다!");
						return resultMap;
					}else{
						if(!info.get("result").toString().equals("TRUE")){
							resultMap.setResult(false);
							resultMap.setMsg(info.get("result").toString());
							return resultMap;
						}
					}
				}catch(Exception e){
					e.printStackTrace();
					resultMap.setResult(false);
					if(resultMap.getMsg() == null || resultMap.getMsg().isEmpty()){
						resultMap.setMsg("포인트 결제 적용중 에러가 발생했습니다! ( "+e.getMessage()+")");
					}			
				}	
			}catch(Exception e){
				e.printStackTrace();
				resultMap.setResult(false);
				if(resultMap.getMsg() == null || resultMap.getMsg().isEmpty()){
					resultMap.setMsg("포인트 결제 적용중 에러가 발생했습니다! ( "+e.getMessage()+")");
				}			
			}

		return resultMap;
	}
	
	 /**
	    * @Method : getUsableCouponAjax
	    * @Date: 2017. 7. 18.
	    * @Author :  강병철
	    * @Description	:	 사용가능한 장바구니쿠폰, 무료배송쿠폰 목록
	    * gubun : G : 상품쿠폰, C : 장바구니쿠폰, S : 무료배송쿠폰
	   */
	@RequestMapping(value = "/ajax/order/getUsableCouponAjax", method = RequestMethod.POST) 
	public @ResponseBody ProcCouponResultVO getUsableCouponAjax(OrderVO vo, String gubun, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		ProcCouponResultVO resultMap = new ProcCouponResultVO();

		UserInfo userInfo = UserInfo.getUserInfo();
		
		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
			vo.setRegIdx(userInfo.getMemberIdx());
			vo.setMemberIdx(userInfo.getMemberIdx());
			vo.setMemberId(userInfo.getMemberId());
		}else{	// 비회원
			resultMap.setResult(false);
			resultMap.setMsg("로그인이 필요합니다.");
			return resultMap;
		}
		
		try {
			vo.setRegHttpUserAgent(request.getHeader("user-agent"));	// HTTP_USER_AGENT
			
			InetAddress local = InetAddress.getLocalHost();	// LOCAL IP ADDRESS
			vo.setRegIp(local.getHostAddress());

			// 결제수단 (공통코드 PAY_TYPE10 : 신용카드, PAY_TYPE15 : 빌키, PAY_TYPE20 : 실시간계좌, PAY_TYPE25 : 가상계좌, PAY_TYPE30 : 휴대폰, PAY_TYPE35 : PAYCO, PAY_TYPE40 : 카카오페이, PAY_TYPE45 : Npay, PAY_TYPE50 : PAYNOW, PAY_TYPE90 : 포인트)
			if(vo.getSelectPayType().equals("BILLKEY")){	// ONE CLICK 원클릭 결제
				vo.setPayType("PAY_TYPE15");
			}else if(vo.getSelectPayType().equals("SC0010")){	// 신용카드
				vo.setPayType("PAY_TYPE10");
			}else if(vo.getSelectPayType().equals("SC0030")){	// 실시간 계좌 이체
				vo.setPayType("PAY_TYPE20");
			}else if(vo.getSelectPayType().equals("SC0040")){	// 가상계좌 입금
				vo.setPayType("PAY_TYPE25");
			}else if(vo.getSelectPayType().equals("SC0060")){	// 휴대폰 결제
				vo.setPayType("PAY_TYPE30");
			}else if(vo.getSelectPayType().equals("PAYCO")){	// PAYCO
				vo.setPayType("PAY_TYPE35");
			}else if(vo.getSelectPayType().equals("PAYNOW")){	// PAYNOW
				vo.setPayType("PAY_TYPE50");
			}else if(vo.getSelectPayType().equals("SMILEPAY")){	// SMILEPAY
				vo.setPayType("PAY_TYPE55");
			}else if(vo.getSelectPayType().equals("POINT")){	// 포인트 결제 (결제금액 0원)
				vo.setPayType("PAY_TYPE90");
			}
			
			try{
				orderService.updateOrderMasterInfo(vo);	// 주문 정보 DB 저장
			}catch(Exception e){
				e.printStackTrace();
				resultMap.setResult(false);
				resultMap.setMsg("결제 정보 저장 중 에러가 발생했습니다!");
				return resultMap;
			}
			
			String orderIdx = orderService.getOrderIdx(vo);	// 주문 번호 반환
			if(orderIdx == null || orderIdx.isEmpty()){
				MethodUtil.alertMsgUrl(request, response, "주문 번호가 존재하지 않습니다!", "/cart.do");
				return null;
			}

			try {
				//장바구니, 쿠폰 은 orderdetailidx = 0
				List<ProcCouponVO> info = orderService.procUsableCouponList(Integer.parseInt(orderIdx), 0, gubun);	
				resultMap.setCouponList(info);
				resultMap.setResult(true);
			}catch(Exception e){
				e.printStackTrace();
				resultMap.setResult(false);
				resultMap.setMsg("쿠폰 목록을 가져오는 중 에러가 발생했습니다!");
				return resultMap;
			}	
		}catch(Exception e){
			e.printStackTrace();
			resultMap.setResult(false);
			if(resultMap.getMsg() == null || resultMap.getMsg().isEmpty()){
				resultMap.setMsg("쿠폰 목록을 가져오는 중 에러가 발생했습니다!(-100)");
			}			
		}
		return resultMap;
	}

	 /**
	    * @Method : applyShippingCouponAjax
	    * @Date: 2017. 7. 18.
	    * @Author :  강병철
		    * @Description	:	배송 쿠폰 사용
		   */
		@RequestMapping(value = "/ajax/order/applyShippingCouponAjax", method = RequestMethod.POST) 
		public @ResponseBody JsonResultVO applyShippingCouponAjax(OrderVO vo, int couponIdx, HttpServletRequest request, HttpServletResponse response) throws Exception{
			
			JsonResultVO resultMap = new JsonResultVO();

			UserInfo userInfo = UserInfo.getUserInfo();
			
			if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
				vo.setRegIdx(userInfo.getMemberIdx());
				vo.setMemberIdx(userInfo.getMemberIdx());
				vo.setMemberId(userInfo.getMemberId());
			}else{	// 비회원
				resultMap.setResult(false);
				resultMap.setMsg("로그인이 필요합니다.");
				return resultMap;
			}
			
			try {
				vo.setRegHttpUserAgent(request.getHeader("user-agent"));	// HTTP_USER_AGENT
				
				InetAddress local = InetAddress.getLocalHost();	// LOCAL IP ADDRESS
				vo.setRegIp(local.getHostAddress());
	
				// 결제수단 (공통코드 PAY_TYPE10 : 신용카드, PAY_TYPE15 : 빌키, PAY_TYPE20 : 실시간계좌, PAY_TYPE25 : 가상계좌, PAY_TYPE30 : 휴대폰, PAY_TYPE35 : PAYCO, PAY_TYPE40 : 카카오페이, PAY_TYPE45 : Npay, PAY_TYPE50 : PAYNOW, PAY_TYPE90 : 포인트)
				if(vo.getSelectPayType().equals("BILLKEY")){	// ONE CLICK 원클릭 결제
					vo.setPayType("PAY_TYPE15");
				}else if(vo.getSelectPayType().equals("SC0010")){	// 신용카드
					vo.setPayType("PAY_TYPE10");
				}else if(vo.getSelectPayType().equals("SC0030")){	// 실시간 계좌 이체
					vo.setPayType("PAY_TYPE20");
				}else if(vo.getSelectPayType().equals("SC0040")){	// 가상계좌 입금
					vo.setPayType("PAY_TYPE25");
				}else if(vo.getSelectPayType().equals("SC0060")){	// 휴대폰 결제
					vo.setPayType("PAY_TYPE30");
				}else if(vo.getSelectPayType().equals("PAYCO")){	// PAYCO
					vo.setPayType("PAY_TYPE35");
				}else if(vo.getSelectPayType().equals("PAYNOW")){	// PAYNOW
					vo.setPayType("PAY_TYPE50");
				}else if(vo.getSelectPayType().equals("SMILEPAY")){	// SMILEPAY
					vo.setPayType("PAY_TYPE55");
				}else if(vo.getSelectPayType().equals("POINT")){	// 포인트 결제 (결제금액 0원)
					vo.setPayType("PAY_TYPE90");
				}
				
				try{
					orderService.updateOrderMasterInfo(vo);	// 주문 정보 DB 저장
				}catch(Exception e){
					e.printStackTrace();
					resultMap.setResult(false);
					resultMap.setMsg("결제 정보 저장 중 에러가 발생했습니다!");
					return resultMap;
				}
				
				String orderIdx = orderService.getOrderIdx(vo);	// 주문 번호 반환
				if(orderIdx == null || orderIdx.isEmpty()){
					MethodUtil.alertMsgUrl(request, response, "주문 번호가 존재하지 않습니다!", "/cart.do");
					return null;
				}

				try {
					SqlMap info = orderService.applyShippingCoupon(orderIdx, couponIdx);	
					resultMap.setResult(true);
					if(info == null){
						resultMap.setResult(false);
						resultMap.setMsg("주문 유효성 확인 중 에러가 발생했습니다!");
						return resultMap;
					}else{
						if(!info.get("result").toString().equals("TRUE")){
							resultMap.setResult(false);
							resultMap.setMsg(info.get("result").toString());
							return resultMap;
						}
					}
					
					// 무료 사은품 내역 저장
					int flag = orderService.saveFreeGift(orderIdx);	
					/*if(flag <= 0){
						resultMap.setResult(false);
						resultMap.setMsg("사은품 정보 저장 중 에러가 발생했습니다. 다시 주문해 주세요!");
						return resultMap;
					}		*/

				}catch(Exception e){
					e.printStackTrace();
					resultMap.setResult(false);
					if(resultMap.getMsg() == null || resultMap.getMsg().isEmpty()){
						resultMap.setMsg("배송 쿠폰 적용중 에러가 발생했습니다! (100)");
					}			
				}	
			}catch(Exception e){
				e.printStackTrace();
				resultMap.setResult(false);
				if(resultMap.getMsg() == null || resultMap.getMsg().isEmpty()){
					resultMap.setMsg("배송 쿠폰 적용중 에러가 발생했습니다! (200)");
				}			
			}
			return resultMap;
		}
		

		 /**
		    * @Method : applyCartCouponAjax
		    * @Date: 2017. 7. 18.
		    * @Author :  강병철
		    * @Description	:	장바구니 쿠폰 사용
		   */
		@RequestMapping(value = "/ajax/order/applyCartCouponAjax", method = RequestMethod.POST) 
		public @ResponseBody JsonResultVO applyCartCouponAjax(OrderVO vo, int couponIdx, HttpServletRequest request, HttpServletResponse response) throws Exception{
			
			JsonResultVO resultMap = new JsonResultVO();

			UserInfo userInfo = UserInfo.getUserInfo();
			
			if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
				vo.setRegIdx(userInfo.getMemberIdx());
				vo.setMemberIdx(userInfo.getMemberIdx());
				vo.setMemberId(userInfo.getMemberId());
			}else{	// 비회원
				resultMap.setResult(false);
				resultMap.setMsg("로그인이 필요합니다.");
				return resultMap;
			}
			
			try {
				vo.setRegHttpUserAgent(request.getHeader("user-agent"));	// HTTP_USER_AGENT
				
				InetAddress local = InetAddress.getLocalHost();	// LOCAL IP ADDRESS
				vo.setRegIp(local.getHostAddress());
	
				// 결제수단 (공통코드 PAY_TYPE10 : 신용카드, PAY_TYPE15 : 빌키, PAY_TYPE20 : 실시간계좌, PAY_TYPE25 : 가상계좌, PAY_TYPE30 : 휴대폰, PAY_TYPE35 : PAYCO, PAY_TYPE40 : 카카오페이, PAY_TYPE45 : Npay, PAY_TYPE50 : PAYNOW, PAY_TYPE90 : 포인트)
				if(vo.getSelectPayType().equals("BILLKEY")){	// ONE CLICK 원클릭 결제
					vo.setPayType("PAY_TYPE15");
				}else if(vo.getSelectPayType().equals("SC0010")){	// 신용카드
					vo.setPayType("PAY_TYPE10");
				}else if(vo.getSelectPayType().equals("SC0030")){	// 실시간 계좌 이체
					vo.setPayType("PAY_TYPE20");
				}else if(vo.getSelectPayType().equals("SC0040")){	// 가상계좌 입금
					vo.setPayType("PAY_TYPE25");
				}else if(vo.getSelectPayType().equals("SC0060")){	// 휴대폰 결제
					vo.setPayType("PAY_TYPE30");
				}else if(vo.getSelectPayType().equals("PAYCO")){	// PAYCO
					vo.setPayType("PAY_TYPE35");
				}else if(vo.getSelectPayType().equals("PAYNOW")){	// PAYNOW
					vo.setPayType("PAY_TYPE50");
				}else if(vo.getSelectPayType().equals("SMILEPAY")){	// SMILEPAY
					vo.setPayType("PAY_TYPE55");
				}else if(vo.getSelectPayType().equals("POINT")){	// 포인트 결제 (결제금액 0원)
					vo.setPayType("PAY_TYPE90");
				}
				
				try{
					orderService.updateOrderMasterInfo(vo);	// 주문 정보 DB 저장
				}catch(Exception e){
					e.printStackTrace();
					resultMap.setResult(false);
					resultMap.setMsg("결제 정보 저장 중 에러가 발생했습니다!");
					return resultMap;
				}
				
				String orderIdx = orderService.getOrderIdx(vo);	// 주문 번호 반환
				if(orderIdx == null || orderIdx.isEmpty()){
					MethodUtil.alertMsgUrl(request, response, "주문 번호가 존재하지 않습니다!", "/cart.do");
					return null;
				}

				try {
					SqlMap info = orderService.applyCartCoupon(orderIdx, couponIdx);	
					resultMap.setResult(true);
					if(info == null){
						resultMap.setResult(false);
						resultMap.setMsg("주문 유효성 확인 중 에러가 발생했습니다!");
						return resultMap;
					}else{
						if(!info.get("result").toString().equals("TRUE")){
							resultMap.setResult(false);
							resultMap.setMsg(info.get("result").toString());
							return resultMap;
						}
					}
					
					// 무료 사은품 내역 저장
					int flag = orderService.saveFreeGift(orderIdx);	
					/*if(flag <= 0){
						resultMap.setResult(false);
						resultMap.setMsg("사은품 정보 저장 중 에러가 발생했습니다. 다시 주문해 주세요!");
						return resultMap;
					}*/	

				}catch(Exception e){
					e.printStackTrace();
					resultMap.setResult(false);
					if(resultMap.getMsg() == null || resultMap.getMsg().isEmpty()){
						resultMap.setMsg("장바구니 쿠폰 적용중 에러가 발생했습니다! (100)");
					}			
				}	
			}catch(Exception e){
				e.printStackTrace();
				resultMap.setResult(false);
				if(resultMap.getMsg() == null || resultMap.getMsg().isEmpty()){
					resultMap.setMsg("장바구니 쿠폰 적용중 에러가 발생했습니다! (200)");
				}			
			}
			return resultMap;
		}
	
	   /**
	    * @Method : getUsableGoodsCouponAjax
	    * @Date: 2017. 7. 31.
	    * @Author :  서 정 길
	    * @Description	:	사용가능한 상품쿠폰 목록
	    * detailIdx : OrderDetailIdx
	   */
	@RequestMapping(value = "/ajax/order/getUsableGoodsCouponAjax", method = RequestMethod.POST) 
	public @ResponseBody ProcCouponResultVO getUsableGoodsCouponAjax(OrderVO vo, String detailIdx, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		ProcCouponResultVO resultMap = new ProcCouponResultVO();

		UserInfo userInfo = UserInfo.getUserInfo();
		
		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
			vo.setRegIdx(userInfo.getMemberIdx());
			vo.setMemberIdx(userInfo.getMemberIdx());
			vo.setMemberId(userInfo.getMemberId());
		}else{	// 비회원
			resultMap.setResult(false);
			resultMap.setMsg("로그인이 필요합니다.");
			return resultMap;
		}
		
		// 파라미터 체크
		try{
			Integer.parseInt(detailIdx);
		}catch(Exception e){
			resultMap.setResult(false);
			resultMap.setMsg("파라미터가 올바르지 않습니다!");
			return resultMap;
		}
		
		try{
			String orderIdx = orderService.getOrderIdx(vo);	// 주문 번호 반환
			if(orderIdx == null || orderIdx.isEmpty()){
				MethodUtil.alertMsgUrl(request, response, "주문 번호가 존재하지 않습니다!", "/cart.do");
				return null;
			}

			try{
				List<ProcCouponVO> info = orderService.procUsableCouponList(Integer.parseInt(orderIdx), Integer.parseInt(detailIdx), "G");	
				resultMap.setCouponList(info);
				resultMap.setResult(true);
			}catch(Exception e){
				e.printStackTrace();
				resultMap.setResult(false);
				resultMap.setMsg("쿠폰 목록을 가져오는 중 에러가 발생했습니다!");
				return resultMap;
			}	
		}catch(Exception e){
			e.printStackTrace();
			resultMap.setResult(false);
			if(resultMap.getMsg() == null || resultMap.getMsg().isEmpty()){
				resultMap.setMsg("쿠폰 목록을 가져오는 중 에러가 발생했습니다!(-100)");
			}			
		}
		return resultMap;
	}

	   /**
	    * @Method : applyGiftCouponAjax
	    * @Date: 2017. 7. 31.
	    * @Author :  서 정 길
	    * @Description	:	상품 쿠폰 사용
	   */
	@RequestMapping(value = "/ajax/order/applyGiftCouponAjax", method = RequestMethod.POST) 
	public @ResponseBody JsonResultVO applyGiftCouponAjax(OrderVO vo, String couponInfo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		JsonResultVO resultMap = new JsonResultVO();

		UserInfo userInfo = UserInfo.getUserInfo();
		
		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
			vo.setRegIdx(userInfo.getMemberIdx());
			vo.setMemberIdx(userInfo.getMemberIdx());
			vo.setMemberId(userInfo.getMemberId());
		}else{	// 비회원
			resultMap.setResult(false);
			resultMap.setMsg("로그인이 필요합니다.");
			return resultMap;
		}
		
		// 파라미터 체크
		if(couponInfo == null || couponInfo.isEmpty()){		
			resultMap.setResult(false);
			resultMap.setMsg("파라미터가 올바르지 않습니다!");
			return resultMap;
		}

		try {
			String orderIdx = orderService.getOrderIdx(vo);	// 주문 번호 반환
			if(orderIdx == null || orderIdx.isEmpty()){
				MethodUtil.alertMsgUrl(request, response, "주문 번호가 존재하지 않습니다!", "/cart.do");
				return null;
			}

			try {
				SqlMap info = orderService.applyGiftCoupon(orderIdx, couponInfo);	
				resultMap.setResult(true);
				if(info == null){
					resultMap.setResult(false);
					resultMap.setMsg("주문 유효성 확인 중 에러가 발생했습니다!");
					return resultMap;
				}else{
					if(!info.get("result").toString().equals("TRUE")){
						resultMap.setResult(false);
						resultMap.setMsg(info.get("result").toString());
						return resultMap;
					}
				}
				
				// 무료 사은품 내역 저장
				int flag = orderService.saveFreeGift(orderIdx);	
				/*if(flag <= 0){
					resultMap.setResult(false);
					resultMap.setMsg("사은품 정보 저장 중 에러가 발생했습니다. 다시 주문해 주세요!");
					return resultMap;
				}*/		

			}catch(Exception e){
				e.printStackTrace();
				resultMap.setResult(false);
				if(resultMap.getMsg() == null || resultMap.getMsg().isEmpty()){
					resultMap.setMsg("장바구니 쿠폰 적용중 에러가 발생했습니다! (100)");
				}			
			}	
		}catch(Exception e){
			e.printStackTrace();
			resultMap.setResult(false);
			if(resultMap.getMsg() == null || resultMap.getMsg().isEmpty()){
				resultMap.setMsg("장바구니 쿠폰 적용중 에러가 발생했습니다! (200)");
			}			
		}
		
		return resultMap;
	}
	
	   /**
	    * @Method : escrowResult
	    * @Date: 2017. 9. 21.
	    * @Author :  서 정 길
	    * @Description	:	LG U+ 에스크로 처리결과 수신
	   */
	@RequestMapping(value="/order/escrowResult") 
	public String escrowResult(EscrowResultVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{

		String resultMSG = "";   
		System.out.println( "*******************************************************************"); 
		System.out.println( "===========escrowResult================================"); 
		System.out.println( "*******************************************************************"); 

		// ## 이 페이지는 수정하지 마십시요. ##

		// 수정시 html태그나 자바스크립트가 들어가는 경우 동작을 보장할 수 없습니다

		// hash데이타값이 맞는 지 확인 하는 루틴은 LG유플러스에서 받은 데이타가 맞는지 확인하는 것이므로 꼭 사용하셔야 합니다
		// 정상적인 결제 건임에도 불구하고 노티 페이지의 오류나 네트웍 문제 등으로 인한 hash 값의 오류가 발생할 수도 있습니다.
		// 그러므로 hash 오류건에 대해서는 오류 발생시 원인을 파악하여 즉시 수정 및 대처해 주셔야 합니다.
		// 그리고 정상적으로 data를 처리한 경우에도 LG유플러스에서 응답을 받지 못한 경우는 결제결과가 중복해서 나갈 수 있으므로 관련한 처리도 고려되어야 합니다. 

		/*************************    변수선언    ******************************/

			String txtype = "";			// 결과구분(C=수령확인결과, R=구매취소요청, D=구매취소결과, S:택배사배송완료, N=NC처리결과 )
			String mid="";				// 상점아이디 
			String tid="";					// LG유플러스에서 부여한 거래번호
			String oid="";					// 상품 거래 번호
			String ssn = "";				// 구매자주민번호	
			String ip = "";					// 구매자IP
			String mac = "";				// 구매자 mac
			String hashdata = "";		// LG유플러스 인증 암호키
			String productid = "";		// 상품정보키
			String resdate = "";			// 구매확인 일시
/*
			txtype = request.getParameter("txtype");
			mid = request.getParameter("mid");
			tid = request.getParameter("tid");
			oid = request.getParameter("oid");
			ssn = request.getParameter("ssn");	
			ip = request.getParameter("ip");
			mac = request.getParameter("mac");
			hashdata = request.getParameter("hashdata");
			productid = request.getParameter("productid");
			resdate = request.getParameter("resdate");
*/
			txtype = vo.getTxtype();
			mid = vo.getMid();
			tid = vo.getTransactionid();
			oid = vo.getOid();
			ssn = vo.getSsn();
			ip = vo.getIp();
			mac = vo.getMac();
			hashdata = vo.getHashdata();
			productid = vo.getProductid();
			resdate = vo.getResdate();
			
			orderService.insertXPayEscrowResultLog(vo);	// 에스크로 결과 로그 저장
			
		    boolean resp = false;
			String mertkey = "";
				
			mertkey = SpringMessage.getMessage("xpay.MERTKEY");  //"c7ecf8d164fec7c46d4d049c8700d423";
															// 상점관리자 계약정보관리에서 확인 가능

			String hashdata2 = null;
		/*************************************/
		    StringBuffer sb = new StringBuffer();
		    sb.append(mid);
			sb.append(oid);
			sb.append(tid);	
			sb.append(txtype);
			sb.append(productid);
			sb.append(ssn);	
			sb.append(ip);	
			sb.append(mac);
			sb.append(resdate);
			sb.append(mertkey);

		    byte[] bNoti = sb.toString().getBytes();

		    MessageDigest md = MessageDigest.getInstance("MD5");
		    byte[] digest = md.digest(bNoti);

		    StringBuffer strBuf = new StringBuffer();

		    for (int i=0 ; i < digest.length ; i++) {
		        int c = digest[i] & 0xff;
		        if (c <= 15){
		            strBuf.append("0");
		        }
		        strBuf.append(Integer.toHexString(c));
		    }

		    hashdata2 = strBuf.toString();

		/************************ 로그데이타 생성 ****************************/

		    String[] noti = 
			{ 
				txtype,
				mid,
				tid,
				oid,
				ssn,		
				ip,
				mac,
				resdate,		
				hashdata, 
				productid,
				hashdata2 
			};

		    if (hashdata2.trim().equals(hashdata)) 
			{										//해쉬값 검증이 성공이면
		        resp = write_success(noti, request, response);
		    } 
			else 
			{										//해쉬값이 검증이 실패이면
		        write_hasherr(noti);
		    }

		    if(resp)
			{										//결과연동이 성공이면
		    	resultMSG = "OK";
		        System.out.println("OK");
		    }
			else
			{										//결과 연동이 실패이면
				resultMSG = "FAIL"+noti;
		        System.out.println("FAIL"+noti);
		    }		
		
	    model.addAttribute("resultMSG", resultMSG);
	    return PathUtil.getCtx()+"/order/casnoteurl";
	}
	
	
	boolean write_success(String noti[], HttpServletRequest request, HttpServletResponse response) throws Exception
	{
	    //구매여부에 관한 log남기게 됩니다. log path수정 및 db처리루틴이 추가하여 주십시요.
		String escrowLogPath = SpringMessage.getMessage("xpay.ESCROW_LOG");	// C:/project/gxensoft/workspace/GatsbyMall/src/main/java/com/gxenSoft/util/pg/lgdacom/log
	    write_log(escrowLogPath + "/escrow_write_success.log", noti);
	    
	    // 결과구분(C=수령확인결과, R=구매취소요청, D=구매취소결과, S:택배사배송완료, N=NC처리결과 )
	    if("C".equals(noti[0]) || "N".equals(noti[0])){	// 구매확인(수령확인결과, NC 처리결과)이면 전체구매확정 처리	    	
	    	JsonResultVO resultMap = new JsonResultVO();
			OrderVO vo = new OrderVO();

			vo.setOrderCd(noti[3]);	// 주문 코드
			SqlMap orderInfo = null;
			orderInfo = mypageOrderService.getOrderInfo(vo, true);	// 주문 마스터 정보, 주문 코드로만 조회 (true)
			
			if(orderInfo == null || orderInfo.size() == 0){
				System.out.println("write_success("+noti[0]+") : " + noti[3] + " : 주문 정보가 존재하지 않습니다!");
				return false;
			}

			// LG U+에서 정보를 여러번 보낼 수 있어서 이미 처리된 거면 SKIP
			String orderStatusCd = nvl(orderInfo.get("orderStatusCd"));	// 현재 주문 상태 코드 
			if("900".equals(orderStatusCd)){
				return true;
			}			

			if("Y".equals(nvl(orderInfo.get("memberOrderYn")))){	// 회원 주문
				vo.setMemberIdx(Integer.valueOf(nvl(orderInfo.get("memberIdx"))));
				vo.setMemberId(nvl(orderInfo.get("memberId")));
			}else{	// 비회원 주문
				vo.setMemberIdx(0);
				vo.setNomemberOrderCd(noti[3]);	// 비회원 로그인 주문 코드
			}

			vo.setOrderIdx(Integer.parseInt(orderInfo.get("orderIdx").toString()));
			vo.setRegHttpUserAgent(request.getHeader("user-agent"));
			InetAddress local = InetAddress.getLocalHost();	// LOCAL IP ADDRESS
			vo.setRegIp(local.getHostAddress());
					
			try {
				mypageOrderService.AllCompletStatus(vo, resultMap);
			}catch(Exception e){
				e.printStackTrace();
				if(resultMap.getMsg() == null || resultMap.getMsg().isEmpty()){
					System.out.println("write_success : " + noti[3] + " 구매 확정중 에러가 발생했습니다! (300)");
				}
				return false;
			}
			
			return true;
	    }else if("R".equals(noti[0])){	// 구매취소요청이면 반품신청 처리
	    	ClaimVO claimVO = new ClaimVO();
	    	claimVO.setOrderCd(noti[3]);	// 주문 코드
	    	
			OrderVO vo = new OrderVO();

			vo.setOrderCd(noti[3]);	// 주문 코드
			SqlMap orderInfo = null;
			orderInfo = mypageOrderService.getOrderInfo(vo, true);	// 주문 마스터 정보, 주문 코드로만 조회 (true)
			
			if(orderInfo == null || orderInfo.size() == 0){
				System.out.println("write_success("+noti[0]+") : " + noti[3] + " : 주문 정보가 존재하지 않습니다!");
				return false;
			}

			// LG U+에서 정보를 여러번 보낼 수 있어서 이미 처리된 거면 SKIP
			String orderStatusCd = nvl(orderInfo.get("orderStatusCd"));	// 현재 주문 상태 코드 
			if("700".equals(orderStatusCd) || "750".equals(orderStatusCd) || "770".equals(orderStatusCd) || "790".equals(orderStatusCd)){
				return true;
			}
			
			if("Y".equals(nvl(orderInfo.get("memberOrderYn")))){	// 회원 주문
				claimVO.setMemberIdx(Integer.valueOf(nvl(orderInfo.get("memberIdx"))));
				claimVO.setRegIdx(Integer.valueOf(nvl(orderInfo.get("memberIdx"))));
			}else{	// 비회원 주문
				claimVO.setMemberIdx(0);
				claimVO.setRegIdx(0);
				claimVO.setNomemberOrderCd(noti[3]);	// 비회원 로그인 주문 코드
			}
			
			try{
				List<SqlMap> detaillist = mypageOrderService.getOrderDetailList(vo, true);	// 주문 상세 리스트, 주문 코드로만 조회 (true)
				String detailIdxStr = "";
				for(SqlMap detail : detaillist) {
					if(!detailIdxStr.isEmpty()){
						detailIdxStr += ",";
					}
					detailIdxStr += nvl(detail.get("orderDetailIdx"));
				}
				
				claimVO.setOrderDetailIdxes(detailIdxStr.split(",", -1));
				
				claimVO.setRegHttpUserAgent(request.getHeader("user-agent"));	// HTTP_USER_AGENT
				
				InetAddress local = InetAddress.getLocalHost();	// LOCAL IP ADDRESS
				claimVO.setRegIp(local.getHostAddress());

				// 선택한 상품의 주문 상태 유효성 체크
				String[] orderStatusCds = new String[]{"400","500","670","690"};	// 발송 완료, 배송 완료, 교환 불가, 교환 완료만 반품 신청 가능
/*
				JsonResultVO crMap = mypageOrderService.getOrderDetailValidStatusCnt(claimVO, orderStatusCds);
				
				if(!crMap.getResult()){
					MethodUtil.alertMsgBack(request, response, crMap.getMsg());
					return;
				}else{
					if(crMap.getResultCnt() < claimVO.getOrderDetailIdxes().length){
						MethodUtil.alertMsgBack(request, response, "유효하지 않은 주문상태의 상품이 포함되어 있습니다! 다시 확인해 주시기 바랍니다.");
						return;
					}
				}
*/
				claimVO.setBeforeOrderStatusCds(orderStatusCds);
				
				claimVO.setReasonCd("RETURN_REASON90");		// 반품 사유 기타
				claimVO.setReason("에스크로 구매 취소 요청");
				
				JsonResultVO resultMap = new JsonResultVO();
				
				resultMap = mypageOrderService.requestReturnOrder(claimVO);		// 반품 신청 처리
				
				if(resultMap.getResult()){
					//---- SMS, 메일발송 (에러나더라도 rollback안함)
		         	try {
		         		if( resultMap.getResult()  ) {
		         			orderService.callOrderClaimSmsSendAndEmail("ORD008", claimVO.getOrderCd(), claimVO.getClaimIdx());
		         		}
		         	}catch(Exception e) {
		         		System.out.println("write_success("+noti[0]+") : " + noti[3] + " : SMS, 이메일 발송중 에러");
		         		e.printStackTrace();
		         		return false;
		         	}
		         	
		         	System.out.println("write_success("+noti[0]+") : " + noti[3] + " : SMS, 이메일 발송 완료");
				}else{
					System.out.println("write_success("+noti[0]+") : " + noti[3] + " : 	반품 신청 처리중 에러 "+resultMap.getMsg());
					return false;
				}
			}catch (Exception e) {
				System.out.println("write_success("+noti[0]+") : " + noti[3] + " : 	구매 취소 처리중 에러 ");
				e.printStackTrace();
				return false;
			}
			
			return true;
	    }else if("S".equals(noti[0])){	// 택배사 배송완료면 배송완료일 업데이트
			try {
				orderService.updateOrderDetailDeliveredDt(noti[3], noti[7]);
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("write_success : " + noti[3] + ", " + noti[7] + " 배송완료일 업데이트중 에러가 발생했습니다!");
				return false;
			}
			
			return true;
	    }
	    
		return true;
	}

	boolean write_failure(String noti[]) throws IOException
	{
	    //구매여부에 관한 log남기게 됩니다. log path수정 및 db처리루틴이 추가하여 주십시요.
		String escrowLogPath = SpringMessage.getMessage("xpay.ESCROW_LOG");	// C:/project/gxensoft/workspace/GatsbyMall/src/main/java/com/gxenSoft/util/pg/lgdacom/log
	    write_log(escrowLogPath + "/escrow_write_failure.log", noti);
	    System.out.println("write_failure("+noti[0]+") : " + noti[3] + " : 에러!");
	    return true;
	}

	boolean write_hasherr(String noti[]) throws IOException
	{
	    //구매여부에 관한 log남기게 됩니다. log path수정 및 db처리루틴이 추가하여 주십시요.
		String escrowLogPath = SpringMessage.getMessage("xpay.ESCROW_LOG");	// C:/project/gxensoft/workspace/GatsbyMall/src/main/java/com/gxenSoft/util/pg/lgdacom/log
	    write_log(escrowLogPath + "/escrow_write_hasherr.log", noti);
	    System.out.println("write_hasherr("+noti[0]+") : " + noti[3] + " : 해시값 검증 실패!");
	    return true;
	}

	void write_log(String file, String noti[]) throws IOException
	{

	    StringBuffer strBuf = new StringBuffer();

		strBuf.append("작성일시 : "				+ getSysDate() +"\r\n");
	    strBuf.append("결과구분 : "				+ noti[0] +		"\r\n");
	    strBuf.append("상점ID : "				+ noti[1] +		"\r\n");
	    strBuf.append("LG유플러스 거래번호 : "	+ noti[2] +		"\r\n");
		strBuf.append("상점주문번호 : "			+ noti[3] +		"\r\n");
	    strBuf.append("작업자 주민번호 : "		+ noti[4] +		"\r\n");    
	    strBuf.append("작업자 PC의 IP주소 : "	+ noti[5] +		"\r\n");
	    strBuf.append("작업자 PC의 MAC주소 : "	+ noti[6] +		"\r\n");
		strBuf.append("처리일시 : "				+ noti[7] +		"\r\n");
		strBuf.append("LG유플러스 인증키 : "	+ noti[8] +		"\r\n");
		strBuf.append("상품ID : "				+ noti[9] +		"\r\n");
		strBuf.append("자체  인증키 : "			+ noti[10] +	"\r\n");


//	    byte b[] = strBuf.toString().getBytes("EUC-KR");
	    byte b[] = strBuf.toString().getBytes("UTF-8");
	    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file, true));
	    stream.write(b);
	    stream.close();
	}

	    public static String getSysDate(){
	        Calendar     m_objCal  = Calendar.getInstance();
	        StringBuffer m_objDate = new StringBuffer();

	        m_objDate.append(m_objCal.get(m_objCal.YEAR));
	        if(m_objCal.get(m_objCal.MONTH)<9)
	            m_objDate.append(0);
	        m_objDate.append(m_objCal.get(m_objCal.MONTH)+1);
	        if(m_objCal.get(m_objCal.DATE)<10)
	            m_objDate.append(0);
	        m_objDate.append(m_objCal.get(m_objCal.DATE));
	        if(m_objCal.get(m_objCal.HOUR)<10)
	            m_objDate.append(0);
	        m_objDate.append(m_objCal.get(m_objCal.HOUR));
	        if(m_objCal.get(m_objCal.MINUTE)<10)
	            m_objDate.append(0);
	        m_objDate.append(m_objCal.get(m_objCal.MINUTE));
	        if(m_objCal.get(m_objCal.SECOND)<10)
	            m_objDate.append(0);
	        m_objDate.append(m_objCal.get(m_objCal.SECOND));

	        return m_objDate.toString();
	    }	
	    
    /**
     * @Method : smilepayTxnidAjax
     * @Date: 2017. 11. 25.
     * @Author :  김  민  수
     * @Description	:	인증 프로세스 txnID 반환	
     * @HISTORY :
     *
     */
    @RequestMapping(value = "/ajax/order/smilepayTxnidAjax", method = RequestMethod.POST) 
	public @ResponseBody SqlMap smilepayTxnidAjax(SmilepayReqVO smilepayReqVO , HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
    	
    	SqlMap resultMap = new SqlMap();
    	resultMap.put("result", SmilepayUtil.getTxnid(smilepayReqVO));
    	return resultMap;
    }
    
    /**
     * @Method : smilepayRes
     * @Date: 2017. 11. 26.
     * @Author :  김  민  수
     * @Description	:	결제 버튼 클릭 후 콜백 정보
     * @HISTORY :
     *
     */
    @RequestMapping(value="/order/smilepayRes" , method = RequestMethod.POST)
    public String smilepayRes(HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
    	XPayResultVO resultVo = new XPayResultVO();
    	
		//smile pay 결제 완료 정보
    	SmilepayResultVO result  = SmilepayUtil.getSmilepayResult(request);
    	session.removeAttribute("orderCd");	// 세션 주문 코드 삭제
    	
    	if(result.getResultCode().equals("3001")){						//결제 성공        		
    		JsonResultVO resultMap = new JsonResultVO();
    		try{
    			
    			boolean isDBOK = true; 
    			
    			resultMap = orderService.dbProcessAfterSmilepay(result);
    			
    			
        		if(!resultMap.getResult()){
    				resultVo.setLGD_RESPCODE("-2");
    				resultVo.setLGD_RESPMSG("결제정보 DB 작업중 오류가 발생하였습니다.\n 관리자에게 문의바랍니다.");	// 에러 메시지
    				isDBOK = false;
    				// smile pay 결제 취소 처리    				
    				SmilepayCancelVO smilepayCancelVO = new SmilepayCancelVO();
            		smilepayCancelVO.setAutoFlag("N");
            		smilepayCancelVO.setCancelMsg("결제 강제취소");
            		smilepayCancelVO.setPartialCancelCode("0");		//전체 취소
            		smilepayCancelVO.setTid(result.getTid());
            		smilepayCancelVO.setCancelAmt(result.getAmt());
            		SmilepayUtil.smilepayCancel(smilepayCancelVO, request);
    			}else{
    				resultVo.setLGD_RESPCODE("0000");
    				resultVo.setLGD_BUYER(result.getBuyerName());		// 구매자명
    				String authDate = "20"+result.getAuthDate().substring(0, 6);
    				resultVo.setLGD_PAYDATE(authDate);		// 결제일자
    				resultVo.setLGD_OID(result.getOrderCd());				// 주문코드
    				resultVo.setLGD_AMOUNT(result.getAmt());			// 결제금액			
    			}

        		// 문자 메일 발송
        		try {
	         		if( isDBOK ) {
	         			orderService.callSPSmsSendAndEmail("ORD002",  resultVo.getLGD_OID());
	         		}
	         	}catch(Exception e) {
	         		e.printStackTrace();
	         	}
	         	
    		}catch(Exception e){
    			e.printStackTrace();
				// smile pay 결제 취소 처리
    			SmilepayCancelVO smilepayCancelVO = new SmilepayCancelVO();
        		smilepayCancelVO.setAutoFlag("N");
        		smilepayCancelVO.setCancelMsg("결제 강제취소");
        		smilepayCancelVO.setPartialCancelCode("0");		//전체 취소
        		smilepayCancelVO.setTid(result.getTid());
        		smilepayCancelVO.setCancelAmt(result.getAmt());
        		SmilepayUtil.smilepayCancel(smilepayCancelVO, request);
        		resultVo.setLGD_RESPCODE("-2");
				resultVo.setLGD_RESPMSG("결제정보 DB 작업중 오류가 발생하였습니다.\n 관리자에게 문의바랍니다.");	// 에러 메시지
    		}
    	}else{													//결재 실패
			resultVo.setLGD_RESPCODE("-2");
			resultVo.setLGD_RESPMSG("결제모듈에서 오류가 발생하였습니다.\n 다시 결제하여 주시기 바랍니다.");
    	}
    	
    	
    	MethodUtil.redirectPostUrl(request, response, PathUtil.getCtx()+"/order/orderComplete.do", resultVo);	     
		return null;    	
    }
    
    /**
     * @Method : smilepaycancel
     * @Date: 2017. 12. 10.
     * @Author :  김  민  수
     * @Description	:	스마일 페이 결제 취소 요청 페이지 테스트 (추후 삭제 해야함)
     * @HISTORY :
     *
     */
    @RequestMapping(value="/order/smilepaycancel" )
    public String smilepaycancel(SmilepayCancelVO smilepayCancelVO ,HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
    	
    	return PathUtil.getCtx()+"/order/smilepayCancelTest";
    }
    
    
    
    /**
     * @Method : smilepaycancelTest
     * @Date: 2017. 12. 10.
     * @Author :  김  민  수
     * @Description	:	스마일 페이 결제 취소 완료 페이지 (추후 삭제 해야함)
     * @HISTORY :
     *
     */
    @RequestMapping(value="/order/smilepaycancelTest" , method = RequestMethod.POST)
    public String smilepaycancelTest(SmilepayCancelVO smilepayCancelVO ,HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
    	
    	SmilepayCancelVO result = SmilepayUtil.smilepayCancel(smilepayCancelVO, request);
    	
    	int flag = orderService.saveSamilPayCancelResult(result);
    	
    	model.addAttribute("flag" , flag);
    	return PathUtil.getCtx()+"/order/smilepayCancelOk";
    }

    
    private static String f_get_parm( String val )
    {
      if ( val == null ) val = "";
      return  val;
    }
    
    /**
     * @Method : kcpPayRes
     * @Date: 2018. 07. 16.
     * @Author :  강병 철
     * @Description	:	KCP 결제 버튼 클릭 후 콜백 정보
     * @HISTORY :
     *
     */
    @RequestMapping(value="/order/kcpPayRes" , method = RequestMethod.POST)
    public String kcpPayRes( KcpReqVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{

		XPayResultVO resultVo = new XPayResultVO();
/*
		// 에러 처리
		resultVo.setLGD_RESPCODE("-1");
		resultVo.setLGD_RESPMSG("결제요청을 초기화 하는데 실패하였습니다.");
		MethodUtil.redirectPostUrl(request, response, PathUtil.getCtx()+"/order/orderComplete.do", resultVo);
		return null;
*/		
	         
//		HashMap<String, Object> map = new HashMap<String, Object>();
		//LG유플러스에서 제공한 환경파일("/conf/lgdacom.conf,/conf/mall.conf") 위치 지정.
		final String g_conf_gw_url =  SpringMessage.getMessage("kcp.gwUrl"); 
		final String g_conf_gw_port =  SpringMessage.getMessage("kcp.gwPort");
		final int g_conf_tx_mode =  0;
		final String g_conf_log_dir =  SpringMessage.getMessage("kcp.logPath");
		final String g_conf_site_cd =  SpringMessage.getMessage("kcp.siteCd");
		final String g_conf_site_key =  SpringMessage.getMessage("kcp.siteKey");
		final String g_conf_log_level =  SpringMessage.getMessage("kcp.logLevel");
		
		System.out.println( "=========== kcpPayRes =================================="); 
		/* ============================================================================== */
	    /* =   02. 지불 요청 정보 설정                                                  = */
	    /* = -------------------------------------------------------------------------- = */
	    String req_tx         = f_get_parm( vo.getReq_tx()); // 요청 종류
	    String tran_cd        = f_get_parm( vo.getTran_cd()); // 처리 종류
	    /* = -------------------------------------------------------------------------- = */
	    String cust_ip        = f_get_parm( request.getRemoteAddr() ); // 요청 IP
	    String ordr_idxx      = f_get_parm( vo.getOrdr_idxx()); // 쇼핑몰 주문번호
	    String good_name      = f_get_parm( vo.getGood_name()); // 상품명
	    /* = -------------------------------------------------------------------------- = */
	    String res_cd         = "";                                                     // 응답코드
	    String res_msg        = "";                                                     // 응답 메세지
	    String tno            = f_get_parm( vo.getTno()); // KCP 거래 고유 번호
	    String escw_yn  = ""; // f_get_parm( vo.getEscw_used());  //에스크로결제여부
	    /* = -------------------------------------------------------------------------- = */
	    String buyr_name      = f_get_parm( vo.getBuyr_name()); // 주문자명
	    String buyr_tel1      = f_get_parm(vo.getBuyr_tel1()); // 주문자 전화번호
	    String buyr_tel2      = f_get_parm( vo.getBuyr_tel2() ); // 주문자 핸드폰 번호
	    String buyr_mail      = f_get_parm( vo.getBuyr_mail() ); // 주문자 E-mail 주소
	    /* = -------------------------------------------------------------------------- = */
	    String use_pay_method = f_get_parm( vo.getUse_pay_method() ); // 결제 방법
	    String bSucc          = "";                                                     // 업체 DB 처리 성공 여부
	    /* = -------------------------------------------------------------------------- = */
	    String app_time       = "";                                                     // 승인시간 (모든 결제 수단 공통)
	    String amount         = "";                                                     // KCP 실제 거래금액         
	    String total_amount   = "0";                                               // 복합결제시 총 거래금액
	    String coupon_mny     = "";                                               // 쿠폰금액
	    /* = -------------------------------------------------------------------------- = */
	    String card_cd        = "";                                                     // 신용카드 코드
	    String card_name      = "";                                                     // 신용카드 명
	    String card_no			= "";			//결제 건의 카드번호 카드번호 16자리 중 3번째구간은 마스킹 
	    String app_no         = "";                                                     // 신용카드 승인번호
	    String noinf          = "";                                                     // 신용카드 무이자 여부
	    String quota          = "";                                                     // 신용카드 할부개월
	    String partcanc_yn    = "";                                                     // 부분취소 가능유무
	    String card_bin_type_01 = "";                                                   // 카드구분1
	    String card_bin_type_02 = "";                                                   // 카드구분2
	    String card_mny       = "0";                                                     // 카드결제금액
	    
	    String isp_issuer_cd = "";  //N 	ISP 계열 카드 발급 사 코드 	    BC96 : 케이뱅크카드	    KM90 : 카카오뱅크카드 
	    String isp_issuer_nm  = ""; //ISP 계열 카드 발급 사 명 카카오뱅크 케이뱅크의경우 BC카드로 리턴 됨 
	    
	    /* = -------------------------------------------------------------------------- = */
	    String bank_name      = "";                                                     // 은행명
	    String bank_code      = "";                                                     // 은행코드
	    String bk_mny         = "0";                                                     // 계좌이체결제금액
	    /* = -------------------------------------------------------------------------- = */
	    String bankname       = "";                                                     // 입금 은행명
	    String depositor      = "";                                                     // 입금 계좌 예금주 성명
	    String account        = "";                                                     // 입금 계좌 번호
	    String va_date        = "";                                                     // 가상계좌 입금마감시간
	    /* = -------------------------------------------------------------------------- = */
	    String pnt_issue      = "";                                                     // 결제 포인트사 코드
	    String pnt_amount     = "0";                                                     // 적립금액 or 사용금액
	    String pnt_app_time   = "";                                                     // 승인시간
	    String pnt_app_no     = "";                                                     // 승인번호
	    String add_pnt        = "";                                                     // 발생 포인트
	    String use_pnt        = "";                                                     // 사용가능 포인트
	    String rsv_pnt        = "";                                                     // 총 누적 포인트
	    /* = -------------------------------------------------------------------------- = */
	    String commid         = "";                                                     // 통신사코드
	    String mobile_no      = "";                                                     // 휴대폰번호
	    /* = -------------------------------------------------------------------------- = */
	    String shop_user_id   = f_get_parm( vo.getShop_user_id()); // 가맹점 고객 아이디
	    String tk_van_code    = "";                                                     // 발급사코드
	    String tk_app_no      = "";                                                     // 승인번호
	    /* = -------------------------------------------------------------------------- = */
	    String cash_yn        = f_get_parm( vo.getCash_yn()); // 현금 영수증 등록 여부
	    String cash_authno    = "";                                                     // 현금 영수증 승인 번호
	    String cash_tr_code   = f_get_parm( vo.getCash_tr_code() ); // 현금 영수증 발행 구분
	    String cash_id_info   = f_get_parm( vo.getCash_id_info()); // 현금 영수증 등록 번호
	    String cash_no        = "";                                                     // 현금 영수증 거래 번호    
	    
	    String paytype=""; // 결제수단 수단(SC0010:신용카드, SC0030:실시간계좌,  SC0040: 가상계좌, SC0060 : 휴대폰)
	    /* ============================================================================== */
	    /* =   02. 지불 요청 정보 설정 END
	    /* ============================================================================== */
	    
	    /* ============================================================================== */
	    /* =   03. 인스턴스 생성 및 초기화(변경 불가)                                   = */
	    /* = -------------------------------------------------------------------------- = */
	    /* =       결제에 필요한 인스턴스를 생성하고 초기화 합니다.                     = */
	    /* = -------------------------------------------------------------------------- = */
	    J_PP_CLI_N c_PayPlus = new J_PP_CLI_N();

	    c_PayPlus.mf_init( "", g_conf_gw_url, g_conf_gw_port, g_conf_tx_mode, g_conf_log_dir );
	    c_PayPlus.mf_init_set();   

	    /* ============================================================================== */
	    /* =   03. 인스턴스 생성 및 초기화 END                                          = */
	    /* ============================================================================== */
	    /* ============================================================================== */
	    /* =   04. 처리 요청 정보 설정                                                  = */
	    /* = -------------------------------------------------------------------------- = */
	    /* = -------------------------------------------------------------------------- = */
	    /* =   04-1. 승인 요청 정보 설정                                                = */
	    /* = -------------------------------------------------------------------------- = */
	    if ( vo.getReq_tx().equals( "pay" ) )
	    {
			System.out.println( "=========== kcp  04. 처리 요청 정보 설정 mf_set_enc_data =================================="); 
	            c_PayPlus.mf_set_enc_data( f_get_parm( vo.getEnc_data() ),
	                                       f_get_parm( vo.getEnc_info()) );

	            /* 1 원은 실제로 업체에서 결제하셔야 될 원 금액을 넣어주셔야 합니다. 결제금액 유효성 검증 */
	            int ordr_data_set_no;
	            ordr_data_set_no = c_PayPlus.mf_add_set( "ordr_data" );
	            // 실결제금액
	            c_PayPlus.mf_set_us( ordr_data_set_no, "ordr_mony", Integer.toString(vo.getGood_mny()) );
	    }
	    /* = -------------------------------------------------------------------------- = */
	    /* =   04. 처리 요청 정보 설정 END                                              = */
	    /* = ========================================================================== = */


	    /* = ========================================================================== = */
	    /* =   05. 실행                                                                 = */
	    /* = -------------------------------------------------------------------------- = */
	    if ( tran_cd.length() > 0 )
	    {
	    	System.out.println( "=========== kcp 05. 실행   mf_do_tx =================================="); 
	        c_PayPlus.mf_do_tx( g_conf_site_cd, g_conf_site_key, tran_cd, "", ordr_idxx, g_conf_log_level, "0" );
	    }
	    else
	    {
	        c_PayPlus.m_res_cd  = "9562";
	        c_PayPlus.m_res_msg = "연동 오류|tran_cd값이 설정되지 않았습니다.";
	    }
	     
        res_cd  = c_PayPlus.m_res_cd;  // 결과 코드
        res_msg = c_PayPlus.m_res_msg; // 결과 메시지
        
        
        System.out.println( "=========== res_cd="+res_cd);
        System.out.println( "=========== res_msg="+res_msg);
        resultVo.setLGD_RESPCODE(res_cd);
		resultVo.setLGD_RESPMSG(res_msg);
        
	    /* = -------------------------------------------------------------------------- = */
	    /* =   05. 실행 END                                                             = */
	    /* ============================================================================== */


	    /* ============================================================================== */
	    /* =   06. 승인 결과 값 추출                                                    = */
	    /* = -------------------------------------------------------------------------- = */
        JsonResultVO resultMap = new JsonResultVO();

		
	    if ( req_tx.equals( "pay" ) )
	    {
	        if ( res_cd.equals( "0000" ) )
	        {
	            tno       = c_PayPlus.mf_get_res( "tno"); // KCP 거래 고유 번호
	            amount    = c_PayPlus.mf_get_res( "amount"    ); // KCP 실제 거래 금액
	            pnt_issue = c_PayPlus.mf_get_res( "pnt_issue" ); // 결제 포인트사 코드
	            coupon_mny = c_PayPlus.mf_get_res( "coupon_mny" ); // 쿠폰금액
	            
	            
	            resultVo.setLGD_OID(ordr_idxx); //주문번호
	    		resultVo.setLGD_MID(g_conf_site_cd); //상점id
	    		resultVo.setLGD_TID(tno); //거래번호
	    		resultVo.setLGD_AMOUNT(amount); //할당금액
	    		
	    		resultVo.setLGD_PAYERID(shop_user_id);
	    		resultVo.setLGD_PAYEREMAIL(buyr_mail);
	    		resultVo.setLGD_PAYERPHONE(buyr_tel2);
	    		resultVo.setLGD_PRODUCTINFO(good_name);
	      		resultVo.setLGD_BUYER(buyr_name);
	    		resultVo.setLGD_BUYERID(shop_user_id);
	    		resultVo.setLGD_BUYERPHONE(buyr_tel2);
	    		resultVo.setLGD_BUYEREMAIL(buyr_mail);
	    /* = -------------------------------------------------------------------------- = */
	    /* =   06-1. 신용카드 승인 결과 처리                                            = */
	    /* = -------------------------------------------------------------------------- = */
	            if ( use_pay_method.equals( "100000000000" ) )
	            {
	            	paytype = "SC0010";
	                card_cd   = c_PayPlus.mf_get_res( "card_cd"   ); // 카드사 코드
	                card_name = c_PayPlus.mf_get_res( "card_name" ); // 카드사 명
	                card_no= c_PayPlus.mf_get_res( "card_no" ); // 카드번호
	                app_time  = c_PayPlus.mf_get_res( "app_time"  ); // 승인시간
	                app_no    = c_PayPlus.mf_get_res( "app_no"    ); // 승인번호
	                noinf     = c_PayPlus.mf_get_res( "noinf"     ); // 무이자 여부
	                quota     = c_PayPlus.mf_get_res( "quota"     ); // 할부 개월 수
	                partcanc_yn = c_PayPlus.mf_get_res( "partcanc_yn"     ); // 부분취소 가능유무
	                card_bin_type_01 = c_PayPlus.mf_get_res( "card_bin_type_01" ); // 카드구분1
	                card_bin_type_02 = c_PayPlus.mf_get_res( "card_bin_type_02" ); // 카드구분2
	                isp_issuer_cd =  c_PayPlus.mf_get_res( "isp_issuer_cd" ); 
	                isp_issuer_nm=  c_PayPlus.mf_get_res( "isp_issuer_nm" );
	                card_mny = c_PayPlus.mf_get_res( "card_mny" ); // 카드결제금액

	                /* = -------------------------------------------------------------- = */
	                /* =   06-1.1. 복합결제(포인트+신용카드) 승인 결과 처리             = */
	                /* = -------------------------------------------------------------- = */
	                if ( pnt_issue.equals( "SCSK" ) || pnt_issue.equals( "SCWB" ) )
	                {
	                    pnt_amount   = c_PayPlus.mf_get_res( "pnt_amount"   ); // 적립금액 or 사용금액
	                    pnt_app_time = c_PayPlus.mf_get_res( "pnt_app_time" ); // 승인시간
	                    pnt_app_no   = c_PayPlus.mf_get_res( "pnt_app_no"   ); // 승인번호
	                    add_pnt      = c_PayPlus.mf_get_res( "add_pnt"      ); // 발생 포인트
	                    use_pnt      = c_PayPlus.mf_get_res( "use_pnt"      ); // 사용가능 포인트
	                    rsv_pnt      = c_PayPlus.mf_get_res( "rsv_pnt"      ); // 총 누적 포인트
	                    total_amount = Integer.toString(Integer.parseInt(amount) + Integer.parseInt(pnt_amount));                    // 복합결제시 총 거래금액
	                    
	                    resultVo.setPNT_AMOUNT(pnt_amount);
	                    resultVo.setPNT_APP_TIME(pnt_app_time);
	                    resultVo.setPNT_APP_NO(pnt_app_no);
	                    resultVo.setADD_PNT(add_pnt);
	                    resultVo.setUSE_PNT(use_pnt);
	                    resultVo.setRSV_PNT(rsv_pnt);
	                    resultVo.setLGD_AMOUNT(total_amount); //할당금액
	                }
	                
	        		resultVo.setLGD_PAYDATE(app_time); //승인일시
		    		resultVo.setLGD_TIMESTAMP(app_time);
		    		
	                resultVo.setLGD_FINANCECODE(card_cd); //결제기관코드, 입금계좌은행코드
	        		resultVo.setLGD_FINANCENAME(card_name); //결제기관명
	        		//신용카드
	        		resultVo.setLGD_CARDNUM(card_no);
	        		resultVo.setLGD_CARDINSTALLMONTH(quota); // 할부개월 
	        		resultVo.setLGD_CARDNOINTYN(noinf); //1무이자,1일반
	        		resultVo.setLGD_FINANCEAUTHNUM(app_no); // 승인기관
	        		resultVo.setLGD_AFFILIATECODE(isp_issuer_cd ); //신용카드제휴코드ISP
	        		resultVo.setLGD_CARDGUBUN1(card_bin_type_01); //추가정보 0:개인, 1:법인,9미확인
	        		resultVo.setLGD_CARDGUBUN2(card_bin_type_02); //추가정보 0:신용,1:체크,2:기프트,9미확인
	        		resultVo.setLGD_CARDACQUIRER(isp_issuer_nm);  //신용카드매입사코드
	        		resultVo.setLGD_PCANCELFLAG(partcanc_yn); //부분취소가능여부(0:불가능, 1:부분취소가능)
	        		resultVo.setLGD_PCANCELSTR(""); //부분취소불가능사유 (가능시 0)
	            }

	    /* = -------------------------------------------------------------------------- = */
	    /* =   06-2. 계좌이체 승인 결과 처리                                            = */
	    /* = -------------------------------------------------------------------------- = */
	            if ( use_pay_method.equals("010000000000") )
	            {
	            	paytype = "SC0030";
	                app_time  = c_PayPlus.mf_get_res( "app_time"  ); // 승인시간
	                bank_name = c_PayPlus.mf_get_res( "bank_name" ); // 은행명
	                bank_code = c_PayPlus.mf_get_res( "bank_code" ); // 은행코드
	                bk_mny    = c_PayPlus.mf_get_res( "bk_mny"    ); // 계좌이체결제금액
	                
	                resultVo.setLGD_FINANCECODE(bank_code); //결제기관코드, 입금계좌은행코드
	        		resultVo.setLGD_FINANCENAME(bank_name); //결제기관명
	        		resultVo.setLGD_PAYDATE(app_time); //승인일시
		    		resultVo.setLGD_TIMESTAMP(app_time);
		    		
	        		//resultVo.setLGD_ACCOUNTOWNER(); //계좌주명
	            }

	    /* = -------------------------------------------------------------------------- = */
	    /* =   06-3. 가상계좌 승인 결과 처리                                            = */
	    /* = -------------------------------------------------------------------------- = */
	            if ( use_pay_method.equals( "001000000000" ) )
	            {
	            	paytype = "SC0040";
	                bankname  = c_PayPlus.mf_get_res( "bankname"  ); // 입금할 은행 이름
	                bank_code  = c_PayPlus.mf_get_res( "bankcode"  ); // 입금할 은행 이름
	                depositor = c_PayPlus.mf_get_res( "depositor" ); // 입금할 계좌 예금주
	                account   = c_PayPlus.mf_get_res( "account"   ); // 입금할 계좌 번호
	                va_date   = c_PayPlus.mf_get_res( "va_date"   ); // 가상계좌 입금마감시간
	                app_time  = c_PayPlus.mf_get_res( "app_time"  ); // 승인시간
	        		
	        		//가상계좌(무통장)
	                resultVo.setLGD_FINANCECODE(bank_code); //결제기관코드, 입금계좌은행코드
	        		resultVo.setLGD_FINANCENAME(bankname); //결제기관명
	        		resultVo.setLGD_ACCOUNTNUM(account); //입금계좌번호
	        		resultVo.setLGD_CASFLAG("R"); // 가상계좌 플레그(R:계좌발급, I:입금, C:취소)
	        		resultVo.setLGD_CASCAMOUNT("0"); //현입금금액
	        		resultVo.setLGD_CASTAMOUNT("0");//누적입금금액
	        		resultVo.setLGD_CASSEQNO("000"); // 가상계좌일련번호(과오납불가의경우 "000")
	        		resultVo.setLGD_PAYER(buyr_name); //가상계좌입금자명
	        		resultVo.setLGD_SAOWNER(depositor); // 가상계좌입금계좌주명(디폴트 상점명)
	        		resultVo.setLGD_CLOSEDATE(va_date);	// 입금마감일 (YYYYMMDDhhddss)

	        		
	        		resultVo.setLGD_PAYDATE(app_time); //승인일시
		    		resultVo.setLGD_TIMESTAMP(app_time);
	            }

	    /* = -------------------------------------------------------------------------- = */
	    /* =   06-4. 포인트 승인 결과 처리                                              = */
	    /* = -------------------------------------------------------------------------- = */
	            if ( use_pay_method.equals( "000100000000" ) )
	            {
	            	paytype = "SC1000";
	                pnt_amount   = c_PayPlus.mf_get_res( "pnt_amount"   ); // 적립금액 or 사용금액
	                pnt_app_time = c_PayPlus.mf_get_res( "pnt_app_time" ); // 승인시간
	                pnt_app_no   = c_PayPlus.mf_get_res( "pnt_app_no"   ); // 승인번호
	                add_pnt      = c_PayPlus.mf_get_res( "add_pnt"      ); // 발생 포인트
	                use_pnt      = c_PayPlus.mf_get_res( "use_pnt"      ); // 사용가능 포인트
	                rsv_pnt      = c_PayPlus.mf_get_res( "rsv_pnt"      ); // 총 누적 포인트
	                
                    resultVo.setPNT_AMOUNT(pnt_amount);
                    resultVo.setPNT_APP_TIME(pnt_app_time);
                    resultVo.setPNT_APP_NO(pnt_app_no);
                    resultVo.setADD_PNT(add_pnt);
                    resultVo.setUSE_PNT(use_pnt);
                    resultVo.setRSV_PNT(rsv_pnt);
	        		
	            }

	    /* = -------------------------------------------------------------------------- = */
	    /* =   06-5. 휴대폰 승인 결과 처리                                              = */
	    /* = -------------------------------------------------------------------------- = */
	            if ( use_pay_method.equals( "000010000000" ) )
	            {
	            	paytype = "SC0060";
	                app_time = c_PayPlus.mf_get_res( "hp_app_time" ); // 승인 시간
	                commid   = c_PayPlus.mf_get_res( "commid"      ); // 통신사 코드
	                mobile_no= c_PayPlus.mf_get_res( "mobile_no"   ); // 휴대폰 번호

	        		resultVo.setLGD_PAYDATE(app_time); //승인일시
		    		resultVo.setLGD_TIMESTAMP(app_time);
		    		resultVo.setCOMMID(commid);
		    		resultVo.setMOBILE_NO(mobile_no);
	            }

	    /* = -------------------------------------------------------------------------- = */
	    /* =   06-6. 상품권 승인 결과 처리                                              = */
	    /* = -------------------------------------------------------------------------- = */
	            if ( use_pay_method.equals( "000000001000" ) )
	            {
	            	paytype = "SC2000";
	                app_time    = c_PayPlus.mf_get_res( "tk_app_time" ); // 승인 시간
	                tk_van_code = c_PayPlus.mf_get_res( "tk_van_code" ); // 발급사 코드
	                tk_app_no   = c_PayPlus.mf_get_res( "tk_app_no"   ); // 승인 번호
	            }

	    /* = -------------------------------------------------------------------------- = */
	    /* =   06-7. 현금영수증 승인 결과 처리                                          = */
	    /* = -------------------------------------------------------------------------- = */
	            cash_authno = c_PayPlus.mf_get_res( "cash_authno" ); // 현금영수증 승인번호
	            cash_no     = c_PayPlus.mf_get_res( "cash_no"     ); // 현금영수증 거래번호            

	            escw_yn  = c_PayPlus.mf_get_res( "escw_yn" ); // 에스크로 여부
	    /*--------------------------------------------------------------------------------------*/
	    	    
        		resultVo.setLGD_CASHRECEIPTNUM(cash_authno); //현금영수증승인번호(현금영수증건이 아니거나 실패인경우 0)
        		resultVo.setCASH_NO(cash_no);
        		resultVo.setLGD_CASHRECEIPTSELFYN(cash_yn); //현금영수증자진발급제유무(Y자진발급제적용, 그외:미적용)
        		resultVo.setLGD_CASHRECEIPTKIND(cash_tr_code); //현금영수증종류(0:소득공제용, 1:지출증비용)	 
        		resultVo.setLGD_ESCROWYN(escw_yn);// 에스크로적용YN
        		
        		
	            resultVo.setLGD_PAYTYPE(paytype); // 결제수단 수단(SC0010:신용카드, SC0030:실시간계좌,  SC0040: 가상계좌, SC0060 : 휴대폰)
	            resultVo.setPG_TYPE("KCP");//PG사 구분
	        }
	    }
	    /* = -------------------------------------------------------------------------- = */
	    /* =   06. 승인 결과 처리 END                                                   = */
	    /* ============================================================================== */


	    /* = ========================================================================== = */
	    /* =   07. 승인 및 실패 결과 DB 처리                                            = */
	    /* = -------------------------------------------------------------------------- = */
	    /* =      결과를 업체 자체적으로 DB 처리 작업하시는 부분입니다.                 = */
	    /* = -------------------------------------------------------------------------- = */
		 boolean isDBOK=true;
		

		 
	    if ( req_tx.equals( "pay" ) )
	    {

	    /* = -------------------------------------------------------------------------- = */
	    /* =   07-1. 승인 결과 DB 처리(res_cd == "0000")                                = */
	    /* = -------------------------------------------------------------------------- = */
	    /* =        각 결제수단을 구분하시어 DB 처리를 하시기 바랍니다.                 = */
	    /* = -------------------------------------------------------------------------- = */
	    	 if ( res_cd.equals( "0000" ) )
	         {
			     try {

			         System.out.println( "=========== 07-1. 승인 결과 DB 처리==============================");
		         		session.removeAttribute("orderCd");	// 세션 주문 코드 삭제
		         		resultMap = orderService.dbProcessAfterKCP(resultVo);	// KCP결제 후 DB 처리
		         		
		         		if(!resultMap.getResult()){
							resultVo.setLGD_RESPCODE("-2");
							resultVo.setLGD_RESPMSG(resultMap.getMsg());	// 에러 메시지
							isDBOK = false;
		         		}
		         	}catch (Exception e) {
						e.printStackTrace();
						resultVo.setLGD_RESPCODE("-2");
						resultVo.setLGD_RESPMSG("결제 후 DB 처리중 에러가 발생했습니다. 다시 결제하여 주시기 바랍니다.");
						isDBOK = false;
					}
		         	// 결제 후 DB 처리 끝-------------------------------------------------------------------------------------------------
		
		         	//---- SMS, 메일발송 (에러나더라도 rollback안함)
		         	try {
		         		if( isDBOK ) {
		         			if (resultVo.getLGD_PAYTYPE().equals("SC0040") )  //가상계좌
		         			{
		         				orderService.callSPSmsSendAndEmail("ORD001",  vo.getOrdr_idxx());
		         			}
		         			else
		         			{
		         				orderService.callSPSmsSendAndEmail("ORD002",  vo.getOrdr_idxx());
		         			}
		         		}
		         	}catch(Exception e) {
		         		e.printStackTrace();
		         	}
	        }

	        /* = -------------------------------------------------------------------------- = */
	        /* =   07-2. 승인 실패 DB 처리(res_cd != "0000")                                = */
	        /* = -------------------------------------------------------------------------- = */
	        if( !"0000".equals ( res_cd ) )
	        {
	        	
	        }
	    }
	    /* = -------------------------------------------------------------------------- = */
	    /* =   07. 승인 및 실패 결과 DB 처리 END                                        = */
	    /* = ========================================================================== = */

	    /* = ========================================================================== = */
	    /* =   08. 승인 결과 DB 처리 실패시 : 자동취소                                  = */
	    /* = -------------------------------------------------------------------------- = */
	    /* =      승인 결과를 DB 작업 하는 과정에서 정상적으로 승인된 건에 대해         = */
	    /* =      DB 작업을 실패하여 DB update 가 완료되지 않은 경우, 자동으로          = */
	    /* =      승인 취소 요청을 하는 프로세스가 구성되어 있습니다.                   = */
	    /* =                                                                            = */
	    /* =      DB 작업이 실패 한 경우, bSucc 라는 변수(String)의 값을 "false"        = */
	    /* =      로 설정해 주시기 바랍니다. (DB 작업 성공의 경우에는 "false" 이외의    = */
	    /* =      값을 설정하시면 됩니다.)                                              = */
	    /* = -------------------------------------------------------------------------- = */

	    // 승인 결과 DB 처리 에러시 bSucc값을 false로 설정하여 거래건을 취소 요청
	    bSucc = ""; 

	    if (req_tx.equals("pay") )
	    {
	        if (res_cd.equals("0000") )
	        {
	            if (!isDBOK)
	            {
	                int mod_data_set_no;

	                c_PayPlus.mf_init_set();

	                tran_cd = "00200000";
	                /* ============================================================================== */
	                /* =   07-1.자동취소시 에스크로 거래인 경우                                     = */
	                /* = -------------------------------------------------------------------------- = */
                    String bSucc_mod_type; // 즉시 취소 시 사용하는 mod_type

                    if ( escw_yn.equals("Y") && use_pay_method.equals("001000000000") )
                    {
                        bSucc_mod_type = "STE5"; // 에스크로 가상계좌 건의 경우 가상계좌 발급취소(STE5)
                    }
                    else if ( escw_yn.equals("Y") )
                    {
                        bSucc_mod_type = "STE2"; // 에스크로 가상계좌 이외 건은 즉시취소(STE2)
                    }
                    else
                    {
                        bSucc_mod_type = "STSC"; // 에스크로 거래 건이 아닌 경우(일반건)(STSC)
                    }
	                /* = ---------------------------------------------------------------------------- = */
	                /* =   07-1. 자동취소시 에스크로 거래인 경우 처리 END                             = */
	                /* = ============================================================================== */

	                mod_data_set_no = c_PayPlus.mf_add_set( "mod_data" );

	                c_PayPlus.mf_set_us( mod_data_set_no, "tno",      tno      ); // KCP 원거래 거래번호
	                c_PayPlus.mf_set_us( mod_data_set_no, "mod_type", bSucc_mod_type ); // 원거래 변경 요청 종류
	                c_PayPlus.mf_set_us( mod_data_set_no, "mod_ip",   cust_ip  ); // 변경 요청자 IP
	                c_PayPlus.mf_set_us( mod_data_set_no, "mod_desc", "가맹점 결과 DB처리 오류 - 가맹점에서 취소 요청"  ); // 변경 사유
	                System.out.println("=========가맹점 결과 DB처리 오류 - 가맹점에서 취소 요청 ========= kcp원거래번호: "+tno );
	                c_PayPlus.mf_do_tx( g_conf_site_cd, g_conf_site_key, tran_cd, "", ordr_idxx, g_conf_log_level, "0" );
	        		
	                res_cd  = c_PayPlus.m_res_cd;                                 // 결과 코드
	                res_msg = c_PayPlus.m_res_msg;                                // 결과 메시지
	                
	            }
	        }
	    }
	        // End of [res_cd = "0000"]
	    /* = -------------------------------------------------------------------------- = */
	    /* =   08. 승인 결과 DB 처리 END                                                = */
	    /* = ========================================================================== = */
	   

	     MethodUtil.redirectPostUrl(request, response, PathUtil.getCtx()+"/order/orderComplete.do", resultVo);	     
	     return null;
    }
    /**
	    * @Method : kcpOrderApprovalAjax
	    * @Date: 2018.07.20
	    * @Author : 강 병 철
	    * @Description	:	 kcp 모바일 결재전에 주문등록
	   */
	@RequestMapping(value = "/ajax/order/kcpOrderApprovalAjax", method = RequestMethod.POST) 
	public @ResponseBody String kcpOrderApprovalAjax(KcpReqVO vo, HttpServletRequest request, HttpSession session) throws Exception{
		
		 // #스마트폰 SOAP 통신 설정 : 실서버일경우true
		final Boolean confServer = Boolean.valueOf(SpringMessage.getMessage("kcp.confServer"));
		final String g_conf_log_dir =  SpringMessage.getMessage("kcp.logPath");

	    ConnectionKCP suc = new ConnectionKCP();  // KCP 인증 정보를 저장하기 위한 Object                     ( 통신의 기본이 되는 객체 ) - 필수
	    OpenHash      oh  = new OpenHash();       // KCP 와 통신시 데이터 위변조를 확인 하기 위한 Hash Object ( 업체와 KCP 간의 통신시 데이터 위변조를 확인 하기 위해 필요. 미설정시 통신 구간만 라이브러리에서 자체적으로 hash 처리 )
	    HttpJsonXml   hjx = new HttpJsonXml();    // 응답값 get value 형식으로 가져올수 있는 Object           ( Java 또는 JSP 내에서 데이터를 파싱할때 필요 - XML 또는 JSON )
	    ParamData     pd  = new ParamData();      // 파라메타 값을 세팅할수 있는 bean Object                  ( String, HashMap 등으로 대체 가능 )
	    
	    String siteCode      =  vo.getSite_cd();
	    String orderID       = vo.getOrdr_idxx();
	    String paymentAmount = String.valueOf(vo.getGood_mny());
	    String paymentMethod = vo.getPay_method();
	    String productName   = vo.getGood_name();
	    String response_type = vo.getResponse_type();
	    String Ret_URL       = vo.getResponse_type();
	    String escrow        = vo.getEscw_used();

	    pd.setGood_mny( paymentAmount );
	    pd.setGood_name( productName );
	    pd.setOrdr_idxx( orderID );
	    pd.setPay_method( paymentMethod );
	    pd.setRet_URL( Ret_URL );
	    pd.setSite_cd( siteCode );
	    pd.setEscw_used( escrow );
	    
	    if( response_type.equals( "JSON" ) || response_type.equals( "XML" ) )
	    {
	        pd.setResponse_type( response_type );
	    }
	    
	    String result = suc.kcpPaymentSmartPhone( request, confServer, pd, g_conf_log_dir );
	    System.out.println("================================================================");
		System.out.println("result="+result);
		System.out.println("================================================================");
    

		return result;
	}
    
	  /**
     * @Method : kcpPayMoRes
     * @Date: 2018. 07. 16.
     * @Author :  강병 철
     * @Description	:	KCP 결제 버튼 클릭 후 콜백 정보
     * @HISTORY :
     *
     */
    @RequestMapping(value="/order/kcpPayMoRes" , method = RequestMethod.POST)
    public String kcpPayMoRes( KcpReqVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
	    model.addAttribute("reqMap", vo);
	    model.addAttribute("HiddenParams" , ObjectToHiddenParams(vo));
		return PathUtil.getCtx()+"/order/kcpPayMoRes";
    }
    

   /**
    * @Method : casnoteurl
    * @Date: 2018. 07. 23.
    * @Author :   강 병 철
    * @Description	:	KCP 가상계좌, 실시간계좌이체 입금 결과 
   */
	@RequestMapping(value="/order/kcpPayNoti") 
	public String kcpPayNoti(KcpNotiReqVO  vo,  HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
	
		String resultMSG  = "0000";  
		JsonResultVO resultMap = new JsonResultVO();
		System.out.println( "*******************************************************************"); 
		System.out.println( "=========== kcpPayNoti =================================="); 
		System.out.println( "*******************************************************************"); 
        //로그 저장 
		orderService.insertKcpNotiReqVO(vo);
		/* ============================================================================== */
	    /* =   02. 공통 통보 데이터 받기                                                = */
	    /* = -------------------------------------------------------------------------- = */
	    String site_cd          = f_get_parm( vo.getSite_cd() );    // 사이트 코드
	    String tno                = f_get_parm( vo.getTno());    // KCP 거래번호
	    String order_cd      = f_get_parm(  vo.getOrder_no()  );    // 주문번호
	    String tx_cd            = f_get_parm( vo.getTx_cd());    // 업무처리 구분 코드(	가상계좌 입금 통보 – TX00,	구매확인 통보 – TX02,배송시작 통보 – TX03,	정산보류 통보 – TX04,	즉시취소 통보 – TX05,	취소 통보 – TX06,	발급계좌해지 통보 – TX07)
	    String tx_tm            = f_get_parm( vo.getTx_tm() );    // 업무처리 완료 시간
	    /* = -------------------------------------------------------------------------- = */
	    String ipgm_name        = "";                                       // 주문자명
	    String remitter         = "";                                                          // 입금자명
	    
	    String ipgm_mnyx        = "";                                                          // 입금금액
	    String bank_code        = "";                                                          // 은행코드
	    String account          = "";                                                          // 가상계좌 입금계좌번호
	    String op_cd            = "";                                                          // 처리구분 코드
	    String noti_id          = "";                                                          // 통보 아이디
	    /* = -------------------------------------------------------------------------- = */
	    String refund_nm        = "";                                                          // 환불계좌주명
	    String refund_mny       = "";                                                          // 환불금액
	    /* = -------------------------------------------------------------------------- = */
	    String st_cd            = "";                                                          // 구매확인 코드
	    String can_msg          = "";                                                          // 구매취소 사유
	    /* = -------------------------------------------------------------------------- = */
	    String waybill_no       = "";                                                          // 운송장 번호
	    String waybill_corp     = "";                                                          // 택배 업체명
	    /* = -------------------------------------------------------------------------- = */
	    String cash_a_no        = "";                                                          // 현금영수증 승인번호
	    String cash_a_dt        = "";                                                          // 현금영수증 승인시간
		String cash_no          = "";                                                          // 현금영수증 거래번호
			
		 /* = -------------------------------------------------------------------------- = */
	    /* =   02-1. 가상계좌 입금 통보 데이터 받기                                     = */
	    /* = -------------------------------------------------------------------------- = */
	    if ( tx_cd.equals("TX00") )
	    {
	        ipgm_name = f_get_parm( vo.getIpgm_name());           // 주문자명
	        remitter  = f_get_parm( vo.getRemitter());           // 입금자명
	        ipgm_mnyx = f_get_parm( vo.getIpgm_mnyx());           // 입금 금액
	        bank_code = f_get_parm( vo.getBank_code() );           // 은행코드
	        account   = f_get_parm( vo.getAccount());           // 가상계좌 입금계좌번호
	        op_cd     = f_get_parm(vo.getOp_cd() );           // 가상계좌 입금 처리 결과 구분 코드 ,가상계좌는 입금처리 이외에도 은행 공동망을 통한취소가 이루어 질 수 있음.  해당 변수를 통해서 가상계좌 상태 구분을 반 드시 해주시기 바랍니다. ‘13’을 제외한 모든 건 은 입금 건으로 처리 하시기 바랍니다. op_cd = ‘13’ 은 입금이 잘못 된 경우로 가맹점 에 취소 노티가 나갑니다. 9 
	        noti_id   = f_get_parm( vo.getNoti_id() );           // 통보 아이디
	        cash_a_no = f_get_parm( vo.getCash_a_no() );           // 현금영수증 승인번호
	        cash_a_dt = f_get_parm( vo.getCash_a_dt() );           // 현금영수증 승인시간
			cash_no   = f_get_parm( vo.getCash_no() );           // 현금영수증 거래번호
	    }
	    /* = -------------------------------------------------------------------------- = */
	    /* =   02-2. 가상계좌 환불 통보 데이터 받기                                      = */
	    /* = -------------------------------------------------------------------------- = */
	    else if ( tx_cd.equals("TX01") )
	    {
	        refund_nm  = f_get_parm( vo.getRefund_nm() );         // 환불계좌주명
	        refund_mny = f_get_parm( vo.getRefund_mny());         // 환불금액
	        bank_code  = f_get_parm( vo.getBank_code());         // 은행코드
	    }
	    /* = -------------------------------------------------------------------------- = */
	    /* =   02-3. 구매확인/구매취소 통보 데이터 받기                                 = */
	    /* = -------------------------------------------------------------------------- = */
	    else if ( tx_cd.equals("TX02") )
	    {
	        st_cd = f_get_parm( vo.getSt_cd() );                   // 구매확인 코드

	        if ( st_cd.equals("N") )                                                 // 구매확인 상태가 구매취소인 경우
	        {
	            can_msg = f_get_parm(vo.getCan_msg());           // 구매취소 사유
	        }
	    }
	    /* = -------------------------------------------------------------------------- = */
	    /* =   02-4. 배송시작 통보 데이터 받기                                          = */
	    /* = -------------------------------------------------------------------------- = */
	    else if ( tx_cd.equals("TX03") )
	    {
	        waybill_no   = f_get_parm( vo.getWaybill_no());     // 운송장 번호
	        waybill_corp = f_get_parm( vo.getWaybill_corp());     // 택배 업체명
	    }
	    /* ============================================================================== */


	    /* ============================================================================== */
	    /* =   03. 공통 통보 결과를 업체 자체적으로 DB 처리 작업하시는 부분입니다.      = */
	    /* = -------------------------------------------------------------------------- = */
	    /* =   통보 결과를 DB 작업 하는 과정에서 정상적으로 통보된 건에 대해 DB 작업을  = */
	    /* =   실패하여 DB update 가 완료되지 않은 경우, 결과를 재통보 받을 수 있는     = */
	    /* =   프로세스가 구성되어 있습니다. 소스에서 result 라는 Form 값을 생성 하신   = */
	    /* =   후, DB 작업이 성공 한 경우, result 의 값을 "0000" 로 세팅해 주시고,      = */
	    /* =   DB 작업이 실패 한 경우, result 의 값을 "0000" 이외의 값으로 세팅해 주시  = */
	    /* =   기 바랍니다. result 값이 "0000" 이 아닌 경우에는 재통보를 받게 됩니다.   = */
	    /* = -------------------------------------------------------------------------- = */

	    /* = -------------------------------------------------------------------------- = */
	    /* =   03-1. 가상계좌 입금 통보 데이터 DB 처리 작업 부분                        = */
	    /* = -------------------------------------------------------------------------- = */
	    if ( tx_cd.equals("TX00") )
	    {
	    	resultMSG = "0000";
    		if (!op_cd.equals("13")) {
	    		System.out.println("=========무통장 입금 성공========= : " + order_cd);
	
	    		int flag = orderService.updateCasNoteUrlOrderMaster(order_cd, tx_tm);	// 가상계좌 입금 확인시 - 주문 상태 결제 완료로 수정 & 결제일자 세팅
	    		if (flag > 0) {
		    		orderService.insertCyberAccountOrderStatusLog(order_cd);	// 가상계좌 입금 완료시 주문 상태 로그 저장
		    		orderService.callSPSmsSendAndEmail("ORD001ORD002",  order_cd); 	// 가상계좌 결제 완료 메일전송
	    		}
    		}
	    }

	    /* = -------------------------------------------------------------------------- = */
	    /* =   03-2. 가상계좌 환불 통보 데이터 DB 처리 작업 부분                        = */
	    /* = -------------------------------------------------------------------------- = */
	    else if ( tx_cd.equals("TX01") )
	    {
	    }
	    /* = -------------------------------------------------------------------------- = */
	    /* =   03-3. 에스크로 구매확인/구매취소 통보 데이터 DB 처리 작업 부분                    = */
	    /* = -------------------------------------------------------------------------- = */
	    else if ( tx_cd.equals("TX02") )
	    {
	    	   if ( st_cd.equals("N") )              //구매취소                                   // 구매확인 상태가 구매취소인 경우
		        {
		           // can_msg = f_get_parm(vo.getCan_msg());           // 구매취소 사유
	    		   ClaimVO claimVO = new ClaimVO();
		   	    	claimVO.setOrderCd(order_cd);	// 주문 코드
		   	    	
		   			OrderVO ordervo = new OrderVO();
	
		   			ordervo.setOrderCd(order_cd);	// 주문 코드
		   			SqlMap orderInfo = null;
		   			orderInfo = mypageOrderService.getOrderInfo(ordervo, true);	// 주문 마스터 정보, 주문 코드로만 조회 (true)
		   			
		   			if(orderInfo == null || orderInfo.size() == 0){
		   				System.out.println(order_cd + " : 주문 정보가 존재하지 않습니다!");
		   				resultMSG = "2110";
		   			}
		   			else
		   			{
			   			// 정보를 여러번 보낼 수 있어서 이미 처리된 거면 SKIP
			   			String orderStatusCd = nvl(orderInfo.get("orderStatusCd"));	// 현재 주문 상태 코드 
			   			if("700".equals(orderStatusCd) || "750".equals(orderStatusCd) || "770".equals(orderStatusCd) || "790".equals(orderStatusCd)){
			   				resultMSG = "0000";
			   			}
			   			else
			   			{
				   			if("Y".equals(nvl(orderInfo.get("memberOrderYn")))){	// 회원 주문
				   				claimVO.setMemberIdx(Integer.valueOf(nvl(orderInfo.get("memberIdx"))));
				   				claimVO.setRegIdx(Integer.valueOf(nvl(orderInfo.get("memberIdx"))));
				   			}else{	// 비회원 주문
				   				claimVO.setMemberIdx(0);
				   				claimVO.setRegIdx(0);
				   				claimVO.setNomemberOrderCd(order_cd);	// 비회원 로그인 주문 코드
				   			}
				   			
				   			try{
				   				List<SqlMap> detaillist = mypageOrderService.getOrderDetailList(ordervo, true);	// 주문 상세 리스트, 주문 코드로만 조회 (true)
				   				String detailIdxStr = "";
				   				for(SqlMap detail : detaillist) {
				   					if(!detailIdxStr.isEmpty()){
				   						detailIdxStr += ",";
				   					}
				   					detailIdxStr += nvl(detail.get("orderDetailIdx"));
				   				}
				   				
				   				claimVO.setOrderDetailIdxes(detailIdxStr.split(",", -1));
				   				
				   				claimVO.setRegHttpUserAgent(request.getHeader("user-agent"));	// HTTP_USER_AGENT
				   				
				   				InetAddress local = InetAddress.getLocalHost();	// LOCAL IP ADDRESS
				   				claimVO.setRegIp(local.getHostAddress());
			
				   				// 선택한 상품의 주문 상태 유효성 체크
				   				String[] orderStatusCds = new String[]{"400","500","670","690"};	// 발송 완료, 배송 완료, 교환 불가, 교환 완료만 반품 신청 가능
				   				claimVO.setBeforeOrderStatusCds(orderStatusCds);
				   				
				   				claimVO.setReasonCd("RETURN_REASON90");		// 반품 사유 기타
				   				claimVO.setReason("에스크로 구매 취소 요청("+can_msg+")");
				   				
				   				resultMap = mypageOrderService.requestReturnOrder(claimVO);		// 반품 신청 처리
				   				
				   				if(resultMap.getResult()){
				   					//---- SMS, 메일발송 (에러나더라도 rollback안함)
				   		         	try {
				   		         		if( resultMap.getResult()  ) {
				   		         			orderService.callOrderClaimSmsSendAndEmail("ORD008", claimVO.getOrderCd(), claimVO.getClaimIdx());
				   		         		}
				   		         	}catch(Exception e) {
				   		         		e.printStackTrace();
				   		         	}
				   		         	resultMSG = "0000";
				   				}else{
				   					System.out.println(order_cd+ " : 	반품 신청 처리중 에러 "+resultMap.getMsg());
				   					resultMSG = "2010";
				   				}
				   			}catch (Exception e) {
				   				System.out.println(order_cd+ " : 	구매 취소 처리중 에러 ");
				   				e.printStackTrace();
				   				resultMSG = "2020";
				   			}
			   			}
		   			}
		        }
	    	   else //구매확인 Y, 시스템구매확인 S
	    	   {
		   			OrderVO ordervo = new OrderVO();
		   			ordervo.setOrderCd(order_cd);	// 주문 코드
		   			SqlMap orderInfo = null;
		   			orderInfo = mypageOrderService.getOrderInfo(ordervo, true);	// 주문 마스터 정보, 주문 코드로만 조회 (true)
		   			
		   			if(orderInfo == null || orderInfo.size() == 0){
		   				System.out.println(order_cd+" : 주문 정보가 존재하지 않습니다!");
		   				resultMSG = "1110";
		   			}
		   			else {
			   			String orderStatusCd = nvl(orderInfo.get("orderStatusCd"));	// 현재 주문 상태 코드 
			   			if("900".equals(orderStatusCd)){
			   				resultMSG = "0000";
			   			}			
			   			else {
				   			if("Y".equals(nvl(orderInfo.get("memberOrderYn")))){	// 회원 주문
				   				ordervo.setMemberIdx(Integer.valueOf(nvl(orderInfo.get("memberIdx"))));
				   				ordervo.setMemberId(nvl(orderInfo.get("memberId")));
				   			}else{	// 비회원 주문
				   				ordervo.setMemberIdx(0);
				   				ordervo.setNomemberOrderCd(order_cd);	// 비회원 로그인 주문 코드
				   			}
			
				   			ordervo.setOrderIdx(Integer.parseInt(orderInfo.get("orderIdx").toString()));
				   			ordervo.setRegHttpUserAgent(request.getHeader("user-agent"));
				   			InetAddress local = InetAddress.getLocalHost();	// LOCAL IP ADDRESS
				   			ordervo.setRegIp(local.getHostAddress());
				   					
				   			try {
				   				mypageOrderService.AllCompletStatus(ordervo, resultMap);
				   				resultMSG = "0000";
				   			}catch(Exception e){
				   				e.printStackTrace();
				   				if(resultMap.getMsg() == null || resultMap.getMsg().isEmpty()){
				   					System.out.println(order_cd + " : 구매 확정중 에러가 발생했습니다! (300)");
				   				}
				   				resultMSG = "1120";
				   			}
			   			}
		   			}
	    	   }
	    }

	    /* = -------------------------------------------------------------------------- = */
	    /* =   03-4. 배송시작 통보 데이터 DB 처리 작업 부분                             = */
	    /* = -------------------------------------------------------------------------- = */
	    else if ( tx_cd.equals("TX03") )
	    {
	    	
	    }

	    /* = -------------------------------------------------------------------------- = */
	    /* =   03-5. 정산보류 통보 데이터 DB 처리 작업 부분                             = */
	    /* = -------------------------------------------------------------------------- = */
	    else if ( tx_cd.equals("TX04") )
	    {
	    }

	    /* = -------------------------------------------------------------------------- = */
	    /* =   03-6. 즉시취소, 취소, 발급계좌해지  통보 데이터 DB 처리 작업 부분                             = */
	    /* = -------------------------------------------------------------------------- = */
	    else if ( tx_cd.equals("TX05")  || tx_cd.equals("TX06") || tx_cd.equals("TX07"))
	    {
	    	 /*
	         * 무통장 입금취소 성공 결과 상점 처리(DB) 부분
    	     * 상점 결과 처리가 정상이면 "OK"
        	 */    
    		System.out.println("=========가상계좌 입금취소 시작 ========= : " );
    		// 이미 주문 취소가 됐는지 확인
    		String orderStatusCd = orderService.getOrderStatusCd(order_cd);	// 주문 마스터 상태 코드 반환
    		
    		if("890".equals(orderStatusCd) || "790".equals(orderStatusCd)){	// 이미 주문 취소 됐으면 OK 보냄
				resultMap.setResult(true);
        		resultMSG = "0000";
        		System.out.println("=========무통장 입금 이미 취소됨========= : " + order_cd);
    		}else{
        		OrderVO orderinfovo = new OrderVO();
        		orderinfovo.setOrderCd(order_cd);
        		orderinfovo.setMemberIdx(0);
        		orderinfovo.setNomemberOrderCd(order_cd);
        		SqlMap xpayInfo = mypageOrderService.getXpayResult(orderinfovo);
				if (mypageOrderService.lgTelecomCasNotiCancelProcess(order_cd, xpayInfo,resultMap) )  //취소DB처리
				{
					resultMap.setResult(true);
            		resultMSG = "0000";
	        		System.out.println("=========무통장 입금취소 성공========= : " + order_cd);
				}
				else 
				{
					resultMap.setResult(false);
            		resultMSG = "1111";
	        		System.out.println("=========DB취소처리중에러========= : " + order_cd);
				}
    		}
	    }

	    /* = -------------------------------------------------------------------------- = */
	    /* =   03-9. 모바일계좌이체 통보 데이터 DB 처리 작업 부분                       = */
	    /* = -------------------------------------------------------------------------- = */
	    else if ( tx_cd.equals("TX08") )
	    {
	    }
	    /* ============================================================================== */

	    
	//    return resultMSG;
	    model.addAttribute("resultMSG", resultMSG);
	    return PathUtil.getCtx()+"/order/kcpPayNoti";
	}
    
    /**
	    * @Method : Naver Pay 주문하기버튼 클릭
	    * @Date: 2017. 6. 27.
	    * @Author :  서 정 길
	    * @Description	:	주문담기
	    * callgubun : 호출한페이지(productview, cart) 
	   */
	@RequestMapping(value = "/ajax/order/nPayOrderRegister", method = RequestMethod.POST) 
	public @ResponseBody NpayResultVO nPayOrderRegister(@RequestBody List<CartVO> vo,  HttpServletRequest request) throws Exception{
		NpayResultVO resultMap = new NpayResultVO();
		String callUrl = "";
		String result = orderService.nPayOrderRegister(vo, request);
		 String[] resultArr =  result.split(":");
		 if (resultArr.length == 0) {
			 resultMap.setResult(false);
			 resultMap.setMsg(result);
		 }
		 else {
			 if (resultArr[0].equals("SUCCESS")) {
				 resultMap.setResult(true);
				 resultMap.setMsg("");
				 resultMap.setCertiKey(resultArr[1]);
				 resultMap.setMerchantNo(resultArr[2]);			
				 //모바일
				 if (PathUtil.getCtx().toUpperCase().equals("/M")) {
					callUrl = SpringMessage.getMessage("npay.returnMoOrderUrl"); 
				 	resultMap.setOrderUrl(callUrl+"/"+resultArr[1]+"/"+resultArr[2]); //https://test-m.pay.naver.com/o/customer/buy
				 }
				 else {
					callUrl = SpringMessage.getMessage("npay.returnPcOrderUrl"); 
				 	resultMap.setOrderUrl(callUrl+"/"+resultArr[1]+"/"+resultArr[2]); //https://test-order.pay.naver.com/customer/buy
				 }
				 
				// SUCCESS:KpXp9FIwTx-9DU-BLemplQ:1200142980
			 }
			 else {
				 resultMap.setResult(false);
				 resultMap.setMsg(resultArr[1]);
			 }
			
		 }
		return resultMap;
	}
	

    /**
	    * @Method : Naver Pay 찜버튼 클릭
	    * @Date: 2017. 6. 27.
	    * @Author :  강 병 철
	    * @Description	:	npay 찜하기
	   */
	@RequestMapping(value = "/order/nPayWishRegister") 
	public String nPayWishRegister(CartVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{

		final String merchantId = SpringMessage.getMessage("npay.merchantId"); 
		final String returnWishPopup = SpringMessage.getMessage("npay.returnWishPopup"); 
		final String returnWishMobilePopup = SpringMessage.getMessage("npay.returnWishMobilePopup"); 
		
		NpayResultVO resultMap = new NpayResultVO();
		String result = orderService.nPayWishRegister(vo.getGoodsIdx());
		 if (result.isEmpty() || result.equals("nodata")) {
			 MethodUtil.alertMsgBack(request, response, "선택하신 상품은 판매중인 상품이 아닙니다.");
			return null;
		 }
		 else {
			 resultMap.setResult(true);
			 resultMap.setItemId(result);
			 resultMap.setMerchantNo(merchantId);
			 if (PathUtil.getCtx().toUpperCase().equals("/M")) {
				 resultMap.setOrderUrl(returnWishMobilePopup);
			 }
			 else {
				 resultMap.setOrderUrl(returnWishPopup);
			 }
		 }

	    model.addAttribute("resultMap", resultMap);
	    return PathUtil.getCtx()+"/order/nPayWish";
	}

}



