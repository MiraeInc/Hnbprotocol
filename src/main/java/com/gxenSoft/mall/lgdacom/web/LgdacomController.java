package com.gxenSoft.mall.lgdacom.web;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gxenSoft.mall.common.web.CommonMethod;
import com.gxenSoft.mall.lgdacom.vo.XPayBillKeyAuthVO;
import com.gxenSoft.mall.lgdacom.vo.XPayBillKeyVO;
import com.gxenSoft.mall.lgdacom.vo.XPayCyberAccountVO;
import com.gxenSoft.mall.lgdacom.vo.XPayReqVO;
import com.gxenSoft.mall.lgdacom.vo.XPayResultVO;
import com.gxenSoft.message.SpringMessage;
import com.gxenSoft.util.pathUtil.PathUtil;

import lgdacom.XPayClient.XPayClient;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : LgdacomController
 * PACKAGE NM : com.gxenSoft.mall.lgdacom.web
 * AUTHOR	 : 강 병 철
 * CREATED DATE  : 2017. 7. 4. 
 * HISTORY :   
 *
 *************************************
 */	
@Controller
public class LgdacomController extends CommonMethod {

//	@Autowired
//	private CartService lgdacomService;

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
    * @Method : sample
    * @Date: 2017. 7. 4.
    * @Author :   강 병 철
    * @Description	:	lg u+ 테스트
   */
	@RequestMapping(value="/lgdacom/sample") 
	public String sample( HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
		
		return PathUtil.getCtx()+"/lgdacom/sample";
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
		final String LGD_RETURNURL = getCurrentUrl() + SpringMessage.getMessage("xpay.RETURNURL");  // "https://222.112.106.57:8443/w/lgdacom/returnurl.do";
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
		vo.setLGD_CUSTOM_SKIN("red");
		vo.setLGD_OSTYPE_CHECK("P");           //값 P: XPay 실행(PC 결제 모듈): PC용과 모바일용 모듈은 파라미터 및 프로세스가 다르므로 PC용은 PC 웹브라우저에서 실행 필요.
		vo.setLGD_VERSION("JSP_Non-ActiveX_Standard");			// 사용타입 정보(수정 및 삭제 금지): 이 정보를 근거로 어떤 서비스를 사용하는지 판단할 수 있습니다.
		vo.setLGD_WINDOW_TYPE("iframe");
		//vo.setLGD_CUSTOM_SWITCHINGTYPE("IFRAME");
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
    * @Method : payreq
    * @Date: 2017. 7. 4.
    * @Author :   강 병 철
    * @Description	:	lg u+ 결제요청
   */
	@RequestMapping(value="/lgdacom/payreq") 
	public String payreq(XPayReqVO vo,  HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{

		//xpay js url
		//final String XPAYJSURL = SpringMessage.getMessage("xpay.JSURL");  // https://xpay.uplus.co.kr/xpay/js/xpay_crossplatform.js
		final String XPAYJSURL = "https://xpay.uplus.co.kr/xpay/js/xpay_crossplatform.js";
		final String LGD_MERTKEY = SpringMessage.getMessage("xpay.MERTKEY");  //"c7ecf8d164fec7c46d4d049c8700d423";
		//가상계좌 입금시 return URL
		final String LGD_CASNOTEURL = getCurrentUrl() + SpringMessage.getMessage("xpay.CASNOTEURL");  // "https://222.112.106.57:8443/w" + "/lgdacom/casnoteurl.do";

		//빌키등록시 return URL
		final String LGD_BILLKEYREGRETURNURL = getCurrentUrl() + SpringMessage.getMessage("xpay.LGD_BILLKEYREGRETURNURL");  // "https://222.112.106.57:8443/w" + "/lgdacom/casnoteurl.do";
		//빌키 MID
		final String BILLMID = SpringMessage.getMessage("xpay.BILLMID");
		final String BILLMERTKEY = SpringMessage.getMessage("xpay.BILLMERTKEY");

    	//주문번호는 이전페이지에서 넘겨준다.
    	//vo.setLGD_OID("");
    	vo.setLGD_BUYERID("jgseo");
		vo.setLGD_BUYER("서정길");
		vo.setLGD_BUYEREMAIL("jgseo@gxensoft.com");
		vo.setLGD_PRODUCTINFO("가츠비 왁스 50ml 외 1건");
		vo.setLGD_AMOUNT("1350");  //결제금액
		vo.setLGD_BUYERPHONE("01062810537");  //전화번호
				
		//1.초기화
		initXPayReq(vo);
		//2.해시키 세팅
		String HASHDATA = makeHashkey(vo.getLGD_MID(), vo.getLGD_OID(),vo.getLGD_AMOUNT(), vo.getLGD_TIMESTAMP(), LGD_MERTKEY);
		vo.setLGD_HASHDATA(HASHDATA);
		
		//가상계좌 vo
		XPayCyberAccountVO cyberVo = new XPayCyberAccountVO();
		cyberVo.setCST_PLATFORM(vo.getCST_PLATFORM());
		cyberVo.setCST_MID(vo.getCST_MID());
		cyberVo.setLGD_MID(vo.getLGD_MID());
		cyberVo.setLGD_TXNAME("CyberAccount");
		cyberVo.setLGD_METHOD("ASSIGN");
		cyberVo.setLGD_OID(vo.getLGD_OID());
		cyberVo.setLGD_AMOUNT(vo.getLGD_AMOUNT());
		cyberVo.setLGD_CASNOTEURL(LGD_CASNOTEURL);  //무통장입금 결과 수신페이지 URL
		cyberVo.setLGD_MID(vo.getLGD_MID());
		cyberVo.setLGD_BUYERPHONE(vo.getLGD_BUYERPHONE());
		cyberVo.setLGD_BUYER(vo.getLGD_BUYER());
		cyberVo.setLGD_ACCOUNTOWNER(vo.getLGD_BUYER()); //입금자명
		cyberVo.setLGD_BUYERID(vo.getLGD_BUYERID());
		cyberVo.setLGD_BUYEREMAIL(vo.getLGD_BUYEREMAIL());
		cyberVo.setLGD_PRODUCTINFO(vo.getLGD_PRODUCTINFO());
		cyberVo.setLGD_BANKCODE("");   
		cyberVo.setLGD_CASHCARDNUM("");   
		cyberVo.setLGD_CASHRECEIPTUSE("");
		cyberVo.setLGD_ESCROW_USEYN("");
		
		//원클릭결제카드등록

		String LGD_BILLMID    = ("test".equals(vo.getCST_PLATFORM().trim())?"t":"")+BILLMID;  //테스트 아이디는 't'를 제외하고 입력하세요.
		
		XPayBillKeyVO  billkeyRegVo = new XPayBillKeyVO();
		billkeyRegVo.setCST_MID(LGD_BILLMID);
		billkeyRegVo.setLGD_MID(LGD_BILLMID);
		billkeyRegVo.setLGD_RETURNURL(LGD_BILLKEYREGRETURNURL); 
		billkeyRegVo.setCST_PLATFORM(vo.getCST_PLATFORM());
		billkeyRegVo.setLGD_WINDOW_TYPE("iframe");
		
		
		XPayBillKeyAuthVO billkeyAuthVo = new XPayBillKeyAuthVO();
		
		billkeyAuthVo.setCST_PLATFORM(vo.getCST_PLATFORM());
		billkeyAuthVo.setLGD_MID(LGD_BILLMID);
		billkeyAuthVo.setLGD_TXNAME("CardAuth");
		billkeyAuthVo.setLGD_PAN("waGLIXr8GGtC4HEo6nFf54xgjkxLMM1BFZl/Fk3dg+Q=");  //빌링키값
		billkeyAuthVo.setLGD_INSTALL("00");
		billkeyAuthVo.setLGD_OID(vo.getLGD_OID());
		billkeyAuthVo.setLGD_AMOUNT(vo.getLGD_AMOUNT());
		billkeyAuthVo.setLGD_BUYERPHONE(vo.getLGD_BUYERPHONE());
		billkeyAuthVo.setLGD_BUYER(vo.getLGD_BUYER());
		billkeyAuthVo.setLGD_BUYERID(vo.getLGD_BUYERID());
		billkeyAuthVo.setLGD_BUYEREMAIL(vo.getLGD_BUYEREMAIL());
		billkeyAuthVo.setLGD_PRODUCTINFO(vo.getLGD_PRODUCTINFO());
		
		
	    model.addAttribute("reqMap", vo);
	    model.addAttribute("cyberMap", cyberVo);
	    model.addAttribute("billkeyRegMap", billkeyRegVo);
	    model.addAttribute("xpayJsUrl",XPAYJSURL);
	    model.addAttribute("reqMapHiddenParams" , ObjectToHiddenParams(vo));
	    model.addAttribute("cyberMapHiddenParams" , ObjectToHiddenParams(cyberVo));
	    model.addAttribute("billkeyRegMapHiddenParams" , ObjectToHiddenParams(billkeyRegVo));
	    model.addAttribute("billkeyAuthMapHiddenParams" , ObjectToHiddenParams(billkeyAuthVo));
		return PathUtil.getCtx()+"/lgdacom/payreq";
	}


	
   /**
    * @Method : payres
    * @Date: 2017. 7. 4.
    * @Author :   강 병 철
    * @Description	:	lg u+  (신용카드, 실시간, paynow)결제 결과
   */
	@RequestMapping(value="/lgdacom/payres") 
	public String payres(XPayReqVO vo,  HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
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
	        return PathUtil.getCtx()+"/lgdacom/payres";
	   	
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
	    		return PathUtil.getCtx()+"/lgdacom/payres";
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
	         

			 // (5) DB에 인증요청 결과 처리
	         if( "0000".equals( xpay.m_szResCode ) ) {
	         	// 통신상의 문제가 없을시
				// 최종결제요청 결과 성공 DB처리(LGD_RESPCODE 값에 따라 결제가 성공인지, 실패인지 DB처리)
	        	 System.out.println("최종결제요청 성공, DB처리하시기 바랍니다.<br>");
	         	            	
	         	//최종결제요청 결과를 DB처리합니다. (결제성공 또는 실패 모두 DB처리 가능)
				//상점내 DB에 어떠한 이유로 처리를 하지 못한경우 false로 변경해 주세요.
	         	boolean isDBOK = true; 
	         	if( !isDBOK ) {
					 
	         		xpay.Rollback("상점 DB처리 실패로 인하여 Rollback 처리 [TID:" +xpay.Response("LGD_TID",0)+",MID:" + xpay.Response("LGD_MID",0)+",OID:"+xpay.Response("LGD_OID",0)+"]");
	         		
	         		System.out.println( "TX Rollback Response_code = " + xpay.Response("LGD_RESPCODE",0) + "<br>");
	         		System.out.println( "TX Rollback Response_msg = " + xpay.Response("LGD_RESPMSG",0) + "<p>");
	         		
					if( "0000".equals( xpay.m_szResCode ) ) { 
						System.out.println("자동취소가 정상적으로 완료 되었습니다.<br>");
					}else{
						System.out.println("자동취소가 정상적으로 처리되지 않았습니다.<br>");
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
	     
	    model.addAttribute("resultVo", vo);
		// session.setAttribute("PAYREQ_MAP", payReqMap);
	    model.addAttribute("HiddenParams" , ObjectToHiddenParams(vo));

		return PathUtil.getCtx()+"/lgdacom/complet";
	}
		
	  /**
	    * @Method : returnurl
	    * @Date: 2017. 7. 4.
	    * @Author :   강 병 철
	    * @Description	:	lg u+ 결제요청
	   */
	@RequestMapping(value="/lgdacom/returnurl") 
	public String returnurl(XPayReqVO vo,  HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
	
	    model.addAttribute("reqMap", vo);
	    model.addAttribute("HiddenParams" , ObjectToHiddenParams(vo));
		return PathUtil.getCtx()+"/lgdacom/returnurl";
	}
		
	

   /**
    * @Method : cyberpayres
    * @Date: 2017. 7. 4.
    * @Author :   강 병 철
    * @Description	:	lg u+ 가상계좌 결과
   */
	@RequestMapping(value="/lgdacom/cyberpayres") 
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
	        return PathUtil.getCtx()+"/lgdacom/payres";
	   	
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
		    
		    	//금액을 체크하시기 원하는 경우 아래 주석을 풀어서 이용하십시요.
		    	//String DB_AMOUNT = "DB나 세션에서 가져온 금액"; //반드시 위변조가 불가능한 곳(DB나 세션)에서 금액을 가져오십시요.
		    	//xpay.Set("LGD_AMOUNTCHECKYN", "Y");
		    	//xpay.Set("LGD_AMOUNT", DB_AMOUNT);
		    
	    	}catch(Exception e) {
	    		System.out.println("LG유플러스 제공 API를 사용할 수 없습니다. 환경파일 설정을 확인해 주시기 바랍니다. ");
	    		System.out.println(""+e.getMessage());    	
	    		return PathUtil.getCtx()+"/lgdacom/payres";
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
	    	 resultVo.setLGD_PAYER(xpay.Response("LGD_PAYER",0));
	    	 resultVo.setLGD_CASFLAG(xpay.Response("LGD_CASFLAG",0));
	    	 resultVo.setLGD_FINANCECODE(xpay.Response("LGD_FINANCECODE",0));  //003
	    	 resultVo.setLGD_OID(xpay.Response("LGD_OID",0));
	    	 resultVo.setLGD_CASCAMOUNT(xpay.Response("LGD_CASCAMOUNT",0));
	    	 resultVo.setLGD_HASHDATA(xpay.Response("LGD_HASHDATA",0));
	    	 resultVo.setLGD_AMOUNT(xpay.Response("LGD_AMOUNT",0));
	    	 resultVo.setLGD_MID(xpay.Response("LGD_MID",0));
	    	 
	    	 
	    	    System.out.println("가상계좌 발급 요청처리가 완료되었습니다.  <br>");
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
	        	
	         

			 // (5) DB에 인증요청 결과 처리
	         if( "0000".equals( xpay.m_szResCode ) ) {
	         	// 통신상의 문제가 없을시
				// 최종결제요청 결과 성공 DB처리(LGD_RESPCODE 값에 따라 결제가 성공인지, 실패인지 DB처리)
	        	 System.out.println("최종결제요청 성공, DB처리하시기 바랍니다.<br>");
	         	            	
	         	//최종결제요청 결과를 DB처리합니다. (결제성공 또는 실패 모두 DB처리 가능)
				//상점내 DB에 어떠한 이유로 처리를 하지 못한경우 false로 변경해 주세요.
	         	boolean isDBOK = true; 
	         	if( !isDBOK ) {
					 
	         		xpay.Rollback("상점 DB처리 실패로 인하여 Rollback 처리 [TID:" +xpay.Response("LGD_TID",0)+",MID:" + xpay.Response("LGD_MID",0)+",OID:"+xpay.Response("LGD_OID",0)+"]");
	         		
	         		System.out.println( "TX Rollback Response_code = " + xpay.Response("LGD_RESPCODE",0) + "<br>");
	         		System.out.println( "TX Rollback Response_msg = " + xpay.Response("LGD_RESPMSG",0) + "<p>");
	         		
					if( "0000".equals( xpay.m_szResCode ) ) { 
						System.out.println("자동취소가 정상적으로 완료 되었습니다.<br>");
					}else{
						System.out.println("자동취소가 정상적으로 처리되지 않았습니다.<br>");
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
	     
	    model.addAttribute("resultVo", resultVo);
		// session.setAttribute("PAYREQ_MAP", payReqMap);
	    model.addAttribute("HiddenParams" , ObjectToHiddenParams(resultVo));

		return PathUtil.getCtx()+"/lgdacom/complet";
	}
		

   /**
    * @Method : casnoteurl
    * @Date: 2017. 7. 4.
    * @Author :   강 병 철
    * @Description	:	lg u+ 가상계좌, 실시간계좌이체 입금 결과
   */
	@RequestMapping(value="/lgdacom/casnoteurl") 
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
		
		String HASHDATA = makeHashkey(vo.getLGD_MID(), vo.getLGD_OID(),vo.getLGD_AMOUNT(), vo.getLGD_TIMESTAMP(), LGD_MERTKEY);
		System.out.println("vo.getLGD_HASHDATA() = "+vo.getLGD_HASHDATA());
		System.out.println("vo.getLGD_RESPCODE() = "+vo.getLGD_RESPCODE());
		System.out.println("vo.getLGD_CASFLAG() = "+vo.getLGD_CASFLAG());

	    if (vo.getLGD_HASHDATA().trim().equals(HASHDATA)) { //해쉬값 검증이 성공이면
	        if ( ("0000".equals(vo.getLGD_RESPCODE().trim())) ){ //결제가 성공이면
	        	if( "R".equals( vo.getLGD_CASFLAG().trim() ) ) {
	                /*
	                 * 무통장 할당 성공 결과 상점 처리(DB) 부분
	                 * 상점 결과 처리가 정상이면 "OK"
	                 */    
	                //if( 무통장 할당 성공 상점처리결과 성공 ) 
	                	resultMSG = "OK";   
	        		
	        	}else if( "I".equals( vo.getLGD_CASFLAG().trim() ) ) {
	 	            /*
	    	         * 무통장 입금 성공 결과 상점 처리(DB) 부분
	        	     * 상점 결과 처리가 정상이면 "OK"
	            	 */    
	            	//if( 무통장 입금 성공 상점처리결과 성공 ) 
	            		resultMSG = "OK";
	        	}else if( "C".equals( vo.getLGD_CASFLAG().trim() ) ) {
	 	            /*
	    	         * 무통장 입금취소 성공 결과 상점 처리(DB) 부분
	        	     * 상점 결과 처리가 정상이면 "OK"
	            	 */    
	            	//if( 무통장 입금취소 성공 상점처리결과 성공 ) 
	            		resultMSG = "OK";
	        	}
	        } else { //결제가 실패이면
	            /*
	             * 거래실패 결과 상점 처리(DB) 부분
	             * 상점결과 처리가 정상이면 "OK"
	             */  
	           //if( 결제실패 상점처리결과 성공 ) 
	        	   resultMSG = "OK";     
	        }
	    }else { //해쉬값이 검증이 실패이면
	        /*
	         * hashdata검증 실패 로그를 처리하시기 바랍니다. 
	         */      
	        resultMSG = "결제결과 상점 DB처리(LGD_CASNOTEURL) 해쉬값 검증이 실패하였습니다.";     
	    }
	    
	    return resultMSG;
	}

	  /**
	    * @Method : returnurl
	    * @Date: 2017. 7. 4.
	    * @Author :   강 병 철
	    * @Description	:	lg u+ 결제요청
	   */
	@RequestMapping(value="/lgdacom/billkeyRegReturn") 
	public String billkeyRegReturn(XPayBillKeyVO vo,  HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
	
	    model.addAttribute("reqMap", vo);
	    model.addAttribute("HiddenParams" , ObjectToHiddenParams(vo));
		return PathUtil.getCtx()+"/lgdacom/billkeyRegReturn";
	}

	  /**
	    * @Method : CardBillingAuthRes
	    * @Date: 2017. 7. 4.
	    * @Author :   강 병 철
	    * @Description	:	빌키등록
	   */
	@RequestMapping(value="/lgdacom/CardBillingAuthRes") 
	public String CardBillingAuthRes(XPayBillKeyVO vo,  HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{

	    model.addAttribute("resultVo", vo);
		return PathUtil.getCtx()+"/lgdacom/complet";
	
	}


   /**
    * @Method : billkeypayres
    * @Date: 2017. 7. 4.
    * @Author :   강 병 철
    * @Description	:	lg u+ 빌키결제
   */
	@RequestMapping(value="/lgdacom/billkeypayres") 
	public String billkeypayres(XPayBillKeyAuthVO vo,  HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
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
	        return PathUtil.getCtx()+"/lgdacom/complet";
	   	
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
	    		return PathUtil.getCtx()+"/lgdacom/payres";
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
	    	 resultVo.setLGD_PAYER(xpay.Response("LGD_PAYER",0));
	    	 resultVo.setLGD_CASFLAG(xpay.Response("LGD_CASFLAG",0));
	    	 resultVo.setLGD_FINANCECODE(xpay.Response("LGD_FINANCECODE",0));  //003
	    	 resultVo.setLGD_OID(xpay.Response("LGD_OID",0));
	    	 resultVo.setLGD_CASCAMOUNT(xpay.Response("LGD_CASCAMOUNT",0));
	    	 resultVo.setLGD_HASHDATA(xpay.Response("LGD_HASHDATA",0));
	    	 resultVo.setLGD_AMOUNT(xpay.Response("LGD_AMOUNT",0));
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
	        	
	         

			 // (5) DB에 인증요청 결과 처리
	         if( "0000".equals( xpay.m_szResCode ) ) {
	         	// 통신상의 문제가 없을시
				// 최종결제요청 결과 성공 DB처리(LGD_RESPCODE 값에 따라 결제가 성공인지, 실패인지 DB처리)
	        	 System.out.println("최종결제요청 성공, DB처리하시기 바랍니다.<br>");
	         	            	
	         	//최종결제요청 결과를 DB처리합니다. (결제성공 또는 실패 모두 DB처리 가능)
				//상점내 DB에 어떠한 이유로 처리를 하지 못한경우 false로 변경해 주세요.
	         	boolean isDBOK = true; 
	         	if( !isDBOK ) {
					 
	         		xpay.Rollback("상점 DB처리 실패로 인하여 Rollback 처리 [TID:" +xpay.Response("LGD_TID",0)+",MID:" + xpay.Response("LGD_MID",0)+",OID:"+xpay.Response("LGD_OID",0)+"]");
	         		
	         		System.out.println( "TX Rollback Response_code = " + xpay.Response("LGD_RESPCODE",0) + "<br>");
	         		System.out.println( "TX Rollback Response_msg = " + xpay.Response("LGD_RESPMSG",0) + "<p>");
	         		
					if( "0000".equals( xpay.m_szResCode ) ) { 
						System.out.println("자동취소가 정상적으로 완료 되었습니다.<br>");
					}else{
						System.out.println("자동취소가 정상적으로 처리되지 않았습니다.<br>");
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
	     
	    model.addAttribute("resultVo", resultVo);
		// session.setAttribute("PAYREQ_MAP", payReqMap);
	    model.addAttribute("HiddenParams" , ObjectToHiddenParams(resultVo));

		return PathUtil.getCtx()+"/lgdacom/complet";
	}

	
}
