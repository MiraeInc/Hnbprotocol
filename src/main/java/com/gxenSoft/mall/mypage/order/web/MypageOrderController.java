package com.gxenSoft.mall.mypage.order.web;

import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gxenSoft.mall.common.vo.JsonResultVO;
import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.common.web.CommonMethod;
import com.gxenSoft.mall.member.service.MemberService;
import com.gxenSoft.mall.mypage.order.service.MypageOrderService;
import com.gxenSoft.mall.mypage.order.vo.ClaimVO;
import com.gxenSoft.mall.order.service.OrderService;
import com.gxenSoft.mall.order.vo.OrderVO;
import com.gxenSoft.message.SpringMessage;
import com.gxenSoft.method.ConvertUtil;
import com.gxenSoft.method.MethodUtil;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.Page;
import com.gxenSoft.util.page.SearchVO;
import com.gxenSoft.util.pathUtil.PathUtil;

   /**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : MypageOrderController
    * PACKAGE NM : com.gxenSoft.mall.mypage.order.web
    * AUTHOR	 : 서 정 길
    * CREATED DATE  : 2017. 8. 3. 
    * HISTORY :   
    *
    *************************************
    */	
@Controller
public class MypageOrderController extends CommonMethod {

	static final Logger logger = LoggerFactory.getLogger(MypageOrderController.class);

	@Autowired
	private MypageOrderService mypageOrderService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private MemberService memberService;


	 /**
	    * @Method : memberBenefit
	    * @Date: 2017. 8. 11.
	    * @Author :  강 병 철
	    * @Description	:	회원혜택안내
	   */
	@RequestMapping(value="/mypage/order/memberBenefit") 
	public String memberBenefit(ClaimVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{

		return PathUtil.getCtx()+"/mypage/memberBenefit";
	}
	
	 /**
	    * @Method : OrderAllCancel
	    * @Date: 2017. 7. 18.
	    * @Author :  강병철
	    * @Description	:	주문 전체 취소 (Mypage)
	   */
	@RequestMapping(value = "/ajax/order/OrderAllCancel", method = RequestMethod.POST) 
	public @ResponseBody JsonResultVO OrderAllCancel(String orderCd, String refundBankCode, String refundAccount, String refundDepositor, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
		JsonResultVO resultMap = new JsonResultVO();
		UserInfo userInfo = UserInfo.getUserInfo();
		OrderVO vo = new OrderVO();
		vo.setOrderCd(orderCd);
		if(session.getAttribute("SS_MEMBER_FLAG") != null){	// 로그인
			if(session.getAttribute("SS_MEMBER_FLAG").equals("Y")){	// 회원 로그인
				vo.setMemberIdx(userInfo.getMemberIdx());
				vo.setMemberId(userInfo.getMemberId());
				vo.setMemberGradeIdx(Integer.parseInt(userInfo.getLevelIdx()));
			}else{	// 비회원 로그인
				vo.setMemberIdx(0);
				vo.setNomemberOrderCd((String)session.getAttribute("SS_NOMEMBER_ORDER_CD"));	// 비회원 로그인 주문 코드
			}
		}else{	// 미로그인
			resultMap.setResult(false);
			resultMap.setMsg("로그인이 필요합니다.");
			return resultMap;
		}
		
		SqlMap orderInfo = mypageOrderService.getOrderInfo(vo, false);			// 주문 마스터 정보, 가상계좌 입금에서 호출 아님 (false)	

		if(orderInfo == null ){
			resultMap.setResult(false);
			resultMap.setMsg("주문 정보가 존재하지 않습니다.");
			return resultMap;
		}
		
		// 주문 접수(입금 대기), 결제 완료일경우에만
		if(!(orderInfo.get("orderStatusCd").equals("100") || orderInfo.get("orderStatusCd").equals("200")) ){
			resultMap.setResult(false);
			resultMap.setMsg("취소 가능한 상태가 아닙니다. 취소할 수 없습니다.");
			return resultMap;
		}
		
		//로그인한 아이피, HTTP_USER_AGENT, memeber idx 저장 (클레임테이블 저장시 사용)
		orderInfo.put("httpUserAgent", request.getHeader("user-agent"));
		InetAddress local = InetAddress.getLocalHost();	// LOCAL IP ADDRESS
		orderInfo.put("regIp", local.getHostAddress());
		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 로그인
			if(Integer.parseInt(userInfo.getMemberIdx().toString()) > 0){	// 회원 로그인
				orderInfo.put("memberIdx", userInfo.getMemberIdx());
			}
		}
		
		/* 결제수단 (공통코드 PAY_TYPE10 : 신용카드, PAY_TYPE15 : 빌키, PAY_TYPE20 : 실시간계좌, PAY_TYPE25 : 가상계좌, PAY_TYPE30 : 휴대폰, PAY_TYPE35 : PAYCO, PAY_TYPE40 : 카카오페이, PAY_TYPE45 : Npay, PAY_TYPE50 : PAYNOW, PAY_TYPE90 : 포인트, PAY_TYPE60 : 원더페이) */
		try {
			if(orderInfo.get("orderStatusCd").equals("100")){		// 입금 전 취소면 PG사 모듈 호출 않고 우리 DB만 취소 처리
				if (mypageOrderService.pointCancelProcess(orderInfo, resultMap) )		// 포인트 결제 취소를 같이 호출
				{
					resultMap.setResult(true);
				}
				else 
				{
					//메시지는 paycoCancelProcess에서 할당한다.
					resultMap.setResult(false);
				}
			}else{
				//lg u+ 결제 (신용카드, 빌키, 실시간계좌, 가상계좌, 휴대폰, 페이나우)
				if (orderInfo.get("payType").equals("PAY_TYPE10") || orderInfo.get("payType").equals("PAY_TYPE15") || orderInfo.get("payType").equals("PAY_TYPE20") || orderInfo.get("payType").equals("PAY_TYPE25")  || orderInfo.get("payType").equals("PAY_TYPE30") || orderInfo.get("payType").equals("PAY_TYPE50"))	{
					
					orderInfo.put("refundBankCode", refundBankCode);
					orderInfo.put("refundAccount", refundAccount);
					orderInfo.put("refundDepositor", refundDepositor);

					SqlMap xpayInfo = mypageOrderService.getXpayResult(vo);
					//LGU+결제
					if (orderInfo.get("pgType").toString().equals("LG")) {
						if (mypageOrderService.lgTelecomCancelProcess(orderInfo, xpayInfo,resultMap) )
						{
							resultMap.setResult(true);
							//todo : sns발송, 메일발송 모듈 추가
						}
						else 
						{
							resultMap.setResult(false);
							//메시지는 lgTelecomCancelProcess에서 저장한다.
						}
					} else {
						if (mypageOrderService.kcpCancelProcess(orderInfo, xpayInfo,resultMap) )
						{
							resultMap.setResult(true);
							//todo : sns발송, 메일발송 모듈 추가
						}
						else 
						{
							resultMap.setResult(false);
							//메시지는 lgTelecomCancelProcess에서 저장한다.
						}
					}
				}
				else if(orderInfo.get("payType").equals("PAY_TYPE35")) {
					//PAYCO
					SqlMap approvalInfo = mypageOrderService.getPaycoApprovalResult(vo);
					if (mypageOrderService.paycoCancelProcess(orderInfo, approvalInfo,resultMap) )
					{
						resultMap.setResult(true);
					}
					else 
					{
						//메시지는 paycoCancelProcess에서 할당한다.
						resultMap.setResult(false);
					}
					
				}
				else if(orderInfo.get("payType").equals("PAY_TYPE60")) {
					//원더페이
					SqlMap approvalInfo = mypageOrderService.getWonderpayResult(vo);
					if (mypageOrderService.wonderCancelProcess(orderInfo, approvalInfo,resultMap) )
					{
						resultMap.setResult(true);
					}
					else 
					{
						//메시지는 wonderCancelProcess에서 할당한다.
						resultMap.setResult(false);
					}
					
				}
				else if(orderInfo.get("payType").equals("PAY_TYPE90")) {
					//포인트 결제
					if (mypageOrderService.pointCancelProcess(orderInfo, resultMap) )
					{
						resultMap.setResult(true);
					}
					else 
					{
						//메시지는 paycoCancelProcess에서 할당한다.
						resultMap.setResult(false);
					}
				}
				else if(orderInfo.get("payType").equals("PAY_TYPE55")) {				//스마일 페이 결제 취소					
					boolean smileFlag = mypageOrderService.smilepayCancelProcess(orderInfo, request ,resultMap);
					resultMap.setResult(smileFlag);
				}
			}
			
			//---- SMS, 메일발송 (에러나더라도 rollback안함)
         	try {
         		if( resultMap.getResult()  ) {
         			orderService.callOrderClaimSmsSendAndEmail("ORD006", orderCd, Integer.parseInt(orderInfo.get("claimIdx").toString()));
         		}
         	}catch(Exception e) {
         		e.printStackTrace();
         	}
         	
		}catch(Exception e){
			e.printStackTrace();
			resultMap.setResult(false);
			//메시지는 lgTelecomCancelProcess에서 할당한다.
			if(resultMap.getMsg() == null || resultMap.getMsg().isEmpty()){
				resultMap.setMsg("주문 취소중 에러가 발생했습니다! (200)");
			}			
		}
		return resultMap;
	}
	
	/**
	 * @Method : deliveryChangeLayer
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	배송지 변경 레이어
	 */
	@RequestMapping(value="/ajax/order/deliveryChangeLayer", method = RequestMethod.POST) 
	public ModelAndView deliveryChangeLayer(String orderCd, HttpServletRequest request , HttpServletResponse response, HttpSession session)throws Exception{
	//	SqlMap detail = reviewService.getReviewDetail(vo);
	//	detail.put("reviewDesc", MethodUtil.repl(detail.get("reviewDesc").toString()));
		UserInfo userInfo = UserInfo.getUserInfo();
		OrderVO vo = new OrderVO();
		Boolean isnomember = false;
		vo.setOrderCd(orderCd);

		if(session.getAttribute("SS_MEMBER_FLAG") != null){	// 로그인
			if(session.getAttribute("SS_MEMBER_FLAG").equals("Y")){	// 회원 로그인
				vo.setMemberIdx(userInfo.getMemberIdx());
				vo.setMemberId(userInfo.getMemberId());
				vo.setMemberGradeIdx(Integer.parseInt(userInfo.getLevelIdx()));
			}else{	// 비회원 로그인
				vo.setMemberIdx(0);
				vo.setNomemberOrderCd((String)session.getAttribute("SS_NOMEMBER_ORDER_CD"));	// 비회원 로그인 주문 코드
				isnomember = true;
			}
		}else{	// 미로그인
		}
		
		List<SqlMap> addressList = new ArrayList<SqlMap>();
		if (vo.getMemberIdx() != 0) {
			 addressList = orderService.getAddressList(vo);	// 배송지 리스트
		}
		
		SqlMap orderInfo = mypageOrderService.getOrderInfo(vo, false);			// 주문 마스터 정보, 가상계좌 입금에서 호출 아님 (false)
		ModelAndView mav = new ModelAndView();
		mav.addObject("addressList", addressList);
		mav.addObject("isnomember", isnomember);
		mav.addObject("orderInfo", orderInfo);
		
		mav.setViewName(PathUtil.getCtx()+"/mypage/order/deliveryChangeLayer");		
		
		return mav;
	}
	
	/**
	 * @Method : orderDeliveryChange
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	배송지 변경
	 */
	@RequestMapping(value="/ajax/order/orderDeliveryChange", method = RequestMethod.POST) 
	public @ResponseBody JsonResultVO orderDeliveryChange(OrderVO vo, HttpServletRequest request , HttpServletResponse response, HttpSession session)throws Exception{
		JsonResultVO resultMap = new JsonResultVO();
		UserInfo userInfo = UserInfo.getUserInfo();

		if(session.getAttribute("SS_MEMBER_FLAG") != null){	// 로그인
			if(session.getAttribute("SS_MEMBER_FLAG").equals("Y")){	// 회원 로그인
				vo.setMemberIdx(userInfo.getMemberIdx());
				vo.setMemberId(userInfo.getMemberId());
				vo.setMemberGradeIdx(Integer.parseInt(userInfo.getLevelIdx()));
			}else{	// 비회원 로그인
				vo.setMemberIdx(0);
				vo.setNomemberOrderCd((String)session.getAttribute("SS_NOMEMBER_ORDER_CD"));	// 비회원 로그인 주문 코드
			}
		}else{	// 미로그인
			resultMap.setResult(false);
			resultMap.setMsg("로그인이 필요합니다.");
		}
		
		vo.setRegHttpUserAgent(request.getHeader("user-agent"));
		InetAddress local = InetAddress.getLocalHost();	// LOCAL IP ADDRESS
		vo.setRegIp(local.getHostAddress());
		
		try {
			mypageOrderService.setDeliveryChange(vo);	
			resultMap.setResult(true);
		} catch (Exception e) {
			resultMap.setResult(false);
			resultMap.setMsg("배송지 변경중 에러가 발생하였습니다.");
		} 
		
		return resultMap;
	}
	

	 /**
	    * @Method : AllCompletStatus
	    * @Date: 2017. 7. 18.
	    * @Author :  강병철
	    * @Description	:	주문 전체 구매 확정 (Mypage)
	   */
	@RequestMapping(value = "/ajax/order/AllCompletStatus", method = RequestMethod.POST) 
	public @ResponseBody JsonResultVO AllCompletStatus( String orderCd, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
		JsonResultVO resultMap = new JsonResultVO();
		UserInfo userInfo = UserInfo.getUserInfo();
		OrderVO vo = new OrderVO();

		if(session.getAttribute("SS_MEMBER_FLAG") != null){	// 로그인
			if(session.getAttribute("SS_MEMBER_FLAG").equals("Y")){	// 회원 로그인
				vo.setMemberIdx(userInfo.getMemberIdx());
				vo.setMemberId(userInfo.getMemberId());
				vo.setMemberGradeIdx(Integer.parseInt(userInfo.getLevelIdx()));
			}else{	// 비회원 로그인
				vo.setMemberIdx(0);
				vo.setNomemberOrderCd((String)session.getAttribute("SS_NOMEMBER_ORDER_CD"));	// 비회원 로그인 주문 코드
			}
		}else{	// 미로그인
			resultMap.setResult(false);
			resultMap.setMsg("로그인이 필요합니다.");
			return resultMap;
		}

		vo.setOrderCd(orderCd);
		SqlMap orderInfo = mypageOrderService.getOrderInfo(vo, false);			// 주문 마스터 정보, 가상계좌 입금에서 호출 아님 (false)	
		
		if(orderInfo == null || orderInfo.size() == 0){
			resultMap.setResult(false);
			resultMap.setMsg("주문 정보가 존재하지 않습니다!");
			return resultMap;
		}

		vo.setOrderIdx(Integer.parseInt(orderInfo.get("orderIdx").toString()));
		vo.setRegHttpUserAgent(request.getHeader("user-agent"));
		InetAddress local = InetAddress.getLocalHost();	// LOCAL IP ADDRESS
		vo.setRegIp(local.getHostAddress());
				
		try {
			SqlMap xpayInfo = mypageOrderService.getXpayResult(vo);
			mypageOrderService.AllCompletStatus(vo, resultMap);
			resultMap.setResult(true);
		}catch(Exception e){
			e.printStackTrace();
			resultMap.setResult(false);
			if(resultMap.getMsg() == null || resultMap.getMsg().isEmpty()){
				resultMap.setMsg("구매 확정중 에러가 발생했습니다! (200)");
			}			
		}
		return resultMap;
	}
	
	   /**
	    * @Method : mypageMain
	    * @Date: 2017. 8. 3.
	    * @Author :  서 정 길
	    * @Description	:	마이페이지 메인
	   */
	@RequestMapping(value="/mypage/order/main") 
	public String mypageMain(OrderVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
		
		UserInfo userInfo = UserInfo.getUserInfo();

		SqlMap refundAccount = null;	// 환불계좌 정보

		if(session.getAttribute("SS_MEMBER_FLAG") != null){	// 로그인
			if(session.getAttribute("SS_MEMBER_FLAG").equals("Y")){	// 회원 로그인
				vo.setMemberIdx(userInfo.getMemberIdx());
				vo.setMemberId(userInfo.getMemberId());
				vo.setMemberGradeIdx(Integer.parseInt(userInfo.getLevelIdx()));
				
				refundAccount = memberService.getMemberAccount();					// 환불계좌 정보
			}else{	// 비회원 주문조회
				vo.setMemberIdx(0);
				vo.setNomemberOrderCd((String)session.getAttribute("SS_NOMEMBER_ORDER_CD"));	// 비회원 로그인 주문 코드
			}
		}else{	// 미로그인
			MethodUtil.alertMsgUrl(request, response, "로그인 정보가 존재하지 않습니다!", "/main.do");
			return null;
		}
		
		// 주문 상태별 건수
		SqlMap orderStatusCnt = mypageOrderService.getOrderStatusCnt(vo);
		int cnt_100 = 0;	// 주문 접수(입금 대기) 건수
		int cnt_200 = 0;	// 결제 완료 건수
		int cnt_300 = 0;	// 상품 준비중 건수
		int cnt_400 = 0;	// 발송 완료(배송중) 건수
		int cnt_900 = 0;	// 구매 확정 건수
		int cnt_800 = 0;	// 취소 건수
		int cnt_600 = 0;	// 교환 건수
		int cnt_700 = 0;	// 반품 건수
		if(orderStatusCnt != null){
			cnt_100 = Integer.parseInt(orderStatusCnt.get("cnt100").toString());	// 주문 접수(입금 대기) 건수
			cnt_200 = Integer.parseInt(orderStatusCnt.get("cnt200").toString());	// 결제 완료 건수
			cnt_300 = Integer.parseInt(orderStatusCnt.get("cnt300").toString());	// 상품 준비중 건수
			cnt_400 = Integer.parseInt(orderStatusCnt.get("cnt400").toString());	// 발송 완료(배송중) 건수
			cnt_900 = Integer.parseInt(orderStatusCnt.get("cnt900").toString());	// 구매 확정 건수
			cnt_800 = Integer.parseInt(orderStatusCnt.get("cnt800").toString());	// 취소 건수
			cnt_600 = Integer.parseInt(orderStatusCnt.get("cnt600").toString());	// 교환 건수
			cnt_700 = Integer.parseInt(orderStatusCnt.get("cnt700").toString());	// 반품 건수
		}
		model.addAttribute("cnt_100", cnt_100);
		model.addAttribute("cnt_200", cnt_200);
		model.addAttribute("cnt_300", cnt_300);
		model.addAttribute("cnt_400", cnt_400);
		model.addAttribute("cnt_900", cnt_900);
		model.addAttribute("cnt_800", cnt_800);
		model.addAttribute("cnt_600", cnt_600);
		model.addAttribute("cnt_700", cnt_700);
		
		// 주문 리스트(최근 1달 5건)
		List<SqlMap> list = mypageOrderService.getOrderListFor1Month(vo);
		int totalCount = mypageOrderService.getOrderListFor1MonthCnt(vo);
		
		List<SqlMap> exchangeReasonList = getCodeList("EXCHANGE_REASON");	// 교환사유 리스트
		List<SqlMap> returnReasonList = getCodeList("RETURN_REASON");			// 반품사유 리스트
		List<SqlMap> bankCodeList = getCodeList("BANK_CODE");						// 환불은행 리스트		

		final String CST_MID = SpringMessage.getMessage("xpay.MID"); // "mandomkorea"; //상점아이디
		final String CST_PLATFORM = SpringMessage.getMessage("xpay.PLATFORM");
		String LGD_MID    = ("test".equals(CST_PLATFORM.trim())?"t":"")+CST_MID;  //테스트 아이디는 't'를 제외하고 입력하세요.
//		String lgEscrowScriptUrl = "https://pgweb.uplus.co.kr/js/DACOMEscrow.js";	// LG U+ 에스크로 구매확인 창 호출 URL (ActiveX)
		String lgEscrowScriptUrl = "https://pgweb.uplus.co.kr/js/DACOMEscrow_UTF8.js";	// LG U+ 에스크로 구매확인 창 호출 URL (ActiveX)
		if (CST_PLATFORM.equals("test")) {
//			lgEscrowScriptUrl = "https://pgweb.uplus.co.kr:7087/js/DACOMEscrow_UTF8.js";		// 상담원이 알려준 임시 주소
//			lgEscrowScriptUrl = "http://pgweb.uplus.co.kr:7085/js/DACOMEscrow.js";		// 메뉴얼상 주소
		}

		model.addAttribute("list", list);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("exchangeReasonList", exchangeReasonList);
		model.addAttribute("returnReasonList", returnReasonList);
		model.addAttribute("bankCodeList", bankCodeList);
		model.addAttribute("refundAccount", refundAccount);
		model.addAttribute("lgEscrowScriptUrl", lgEscrowScriptUrl);
		model.addAttribute("LGD_MID", LGD_MID);
		
		return PathUtil.getCtx()+"/mypage/mypageMain";
	}

	   /**
	    * @Method : myOrderList
	    * @Date: 2017. 8. 6.
	    * @Author :  서 정 길
	    * @Description	:	마이페이지 주문관리 주문 내역
	   */
	@RequestMapping(value="/mypage/order/myOrderList") 
	public String myOrderList(OrderVO vo, SearchVO searchVO, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
		
		UserInfo userInfo = UserInfo.getUserInfo();

		SqlMap refundAccount = null;	// 환불계좌 정보

		if(session.getAttribute("SS_MEMBER_FLAG") != null){	// 로그인
			if(session.getAttribute("SS_MEMBER_FLAG").equals("Y")){	// 회원 로그인
				vo.setMemberIdx(userInfo.getMemberIdx());
				vo.setMemberId(userInfo.getMemberId());
				vo.setMemberGradeIdx(Integer.parseInt(userInfo.getLevelIdx()));
				
				refundAccount = memberService.getMemberAccount();					// 환불계좌 정보
			}else{	// 비회원 주문조회
				vo.setMemberIdx(0);
				vo.setNomemberOrderCd((String)session.getAttribute("SS_NOMEMBER_ORDER_CD"));	// 비회원 로그인 주문 코드
			}
		}else{	// 미로그인
			MethodUtil.alertMsgUrl(request, response, "로그인 정보가 존재하지 않습니다!", "/main.do");
			return null;
		}

		// 주문 상태 유효성 체크
		if(searchVO.getSchStatus() != null &&
			!searchVO.getSchStatus().isEmpty() &&
			!searchVO.getSchStatus().equals("100") &&
			!searchVO.getSchStatus().equals("200") &&
			!searchVO.getSchStatus().equals("300") &&
			!searchVO.getSchStatus().equals("400") &&
			!searchVO.getSchStatus().equals("900")
		){
			MethodUtil.alertMsgBack(request, response, "파라미터가 유효하지 않습니다!");
			return null;
		}
		
		// 주문 상태별 건수
		SqlMap orderStatusCnt = mypageOrderService.getOrderStatusCnt(vo);
		int cnt_All = 0;	// 주문 접수(입금 대기), 결제 완료, 상품 준비중, 발송 완료(배송중), 구매 확정 건수
		int cnt_100 = 0;	// 주문 접수(입금 대기) 건수
		int cnt_200 = 0;	// 결제 완료 건수
		int cnt_300 = 0;	// 상품 준비중 건수
		int cnt_400 = 0;	// 발송 완료(배송중) 건수
		int cnt_900 = 0;	// 구매 확정 건수
		int cnt_800 = 0;	// 취소 건수
		int cnt_600 = 0;	// 교환 건수
		int cnt_700 = 0;	// 반품 건수
		if(orderStatusCnt != null){
			cnt_All = Integer.parseInt(orderStatusCnt.get("cntAll").toString());		// 주문 접수(입금 대기), 결제 완료, 상품 준비중, 발송 완료(배송중), 구매 확정 건수
			cnt_100 = Integer.parseInt(orderStatusCnt.get("cnt100").toString());	// 주문 접수(입금 대기) 건수
			cnt_200 = Integer.parseInt(orderStatusCnt.get("cnt200").toString());	// 결제 완료 건수
			cnt_300 = Integer.parseInt(orderStatusCnt.get("cnt300").toString());	// 상품 준비중 건수
			cnt_400 = Integer.parseInt(orderStatusCnt.get("cnt400").toString());	// 발송 완료(배송중) 건수
			cnt_900 = Integer.parseInt(orderStatusCnt.get("cnt900").toString());	// 구매 확정 건수
			cnt_800 = Integer.parseInt(orderStatusCnt.get("cnt800").toString());	// 취소 건수
			cnt_600 = Integer.parseInt(orderStatusCnt.get("cnt600").toString());	// 교환 건수
			cnt_700 = Integer.parseInt(orderStatusCnt.get("cnt700").toString());	// 반품 건수
		}
		if(searchVO.getSchStatus() != null && !searchVO.getSchStatus().isEmpty()){
			if(searchVO.getSchStatus().equals("100")){
				cnt_All = cnt_100;
			}else if(searchVO.getSchStatus().equals("200")){
				cnt_All = cnt_200;
			}else if(searchVO.getSchStatus().equals("300")){
				cnt_All = cnt_300;
			}else if(searchVO.getSchStatus().equals("400")){
				cnt_All = cnt_400;
			}else if(searchVO.getSchStatus().equals("900")){
				cnt_All = cnt_900;
			}
		}
		model.addAttribute("cnt_All", cnt_All);
		model.addAttribute("cnt_800", cnt_800);
		model.addAttribute("cnt_600", cnt_600);
		model.addAttribute("cnt_700", cnt_700);
		
		// 조회일자 최초 3개월 기본 세팅
		if(searchVO.getSchType().isEmpty()){
			// 3달 전
			Calendar mon = Calendar.getInstance();
			mon.add(Calendar.MONTH , -3);
			searchVO.setSchStartDt(new java.text.SimpleDateFormat("yyyy-MM-dd").format(mon.getTime()));

			// 오늘
			Date today = new Date();         
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			searchVO.setSchEndDt(date.format(today));
		}
		
		// 주문 내역 리스트
		List<SqlMap> list = mypageOrderService.getMyOrderList(vo, searchVO);
		int totalCount = mypageOrderService.getMyOrderListCnt(vo, searchVO);
		
		Page page = new Page(); 
		page.pagingInfo(searchVO, totalCount);

		List<SqlMap> exchangeReasonList = getCodeList("EXCHANGE_REASON");	// 교환사유 리스트
		List<SqlMap> returnReasonList = getCodeList("RETURN_REASON");			// 반품사유 리스트
		List<SqlMap> bankCodeList = getCodeList("BANK_CODE");						// 환불은행 리스트		

		final String CST_MID = SpringMessage.getMessage("xpay.MID"); // "mandomkorea"; //상점아이디
		final String CST_PLATFORM = SpringMessage.getMessage("xpay.PLATFORM");
		String LGD_MID    = ("test".equals(CST_PLATFORM.trim())?"t":"")+CST_MID;  //테스트 아이디는 't'를 제외하고 입력하세요.
//		String lgEscrowScriptUrl = "https://pgweb.uplus.co.kr/js/DACOMEscrow.js";	// LG U+ 에스크로 구매확인 창 호출 URL (ActiveX)
		String lgEscrowScriptUrl = "https://pgweb.uplus.co.kr/js/DACOMEscrow_UTF8.js";	// LG U+ 에스크로 구매확인 창 호출 URL (ActiveX)
		if (CST_PLATFORM.equals("test")) {
//			lgEscrowScriptUrl = "https://pgweb.uplus.co.kr:7087/js/DACOMEscrow.js";		// 상담원이 알려준 임시 주소
//			lgEscrowScriptUrl = "https://pgweb.uplus.co.kr:7087/js/DACOMEscrow_UTF8.js";		// 상담원이 알려준 임시 주소
//			lgEscrowScriptUrl = "http://pgweb.uplus.co.kr:7085/js/DACOMEscrow.js";		// 메뉴얼상 주소
		}
		
		final String kcpSiteCd =  SpringMessage.getMessage("kcp.siteCd");
		
		model.addAttribute("kcpSiteCd", kcpSiteCd);
		model.addAttribute("VO", vo);
		model.addAttribute("SCHVO", searchVO);
		model.addAttribute("list", list);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("page", page);		
		model.addAttribute("exchangeReasonList", exchangeReasonList);
		model.addAttribute("returnReasonList", returnReasonList);
		model.addAttribute("bankCodeList", bankCodeList);
		model.addAttribute("refundAccount", refundAccount);
		model.addAttribute("lgEscrowScriptUrl", lgEscrowScriptUrl);
		model.addAttribute("LGD_MID", LGD_MID);
		model.addAttribute("mySub", "mySub01");
		
		return PathUtil.getCtx()+"/mypage/order/myOrderList";
	}

	   /**
	    * @Method : myClaimList
	    * @Date: 2017. 8. 7.
	    * @Author :  서 정 길
	    * @Description	:	주문관리 - 취소 리스트
	   */
	@RequestMapping(value="/mypage/order/myCancelList") 
	public String myCancelList(OrderVO vo, SearchVO searchVO, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
		
		UserInfo userInfo = UserInfo.getUserInfo();
		searchVO.setPageBlock(10);
		
		if(session.getAttribute("SS_MEMBER_FLAG") != null){	// 로그인
			if(session.getAttribute("SS_MEMBER_FLAG").equals("Y")){	// 회원 로그인
				vo.setMemberIdx(userInfo.getMemberIdx());
				vo.setMemberId(userInfo.getMemberId());
				vo.setMemberGradeIdx(Integer.parseInt(userInfo.getLevelIdx()));
			}else{	// 비회원 로그인
				vo.setMemberIdx(0);
				vo.setNomemberOrderCd((String)session.getAttribute("SS_NOMEMBER_ORDER_CD"));	// 비회원 로그인 주문 코드
			}
		}else{	// 미로그인
			MethodUtil.alertMsgUrl(request, response, "로그인 정보가 존재하지 않습니다!", "/main.do");
			return null;
		}

		
		// 주문 상태별 건수
		SqlMap orderStatusCnt = mypageOrderService.getOrderStatusCnt(vo);
		int cnt_All = 0;	// 주문 접수(입금 대기), 결제 완료, 상품 준비중, 발송 완료(배송중), 구매 확정 건수
		int cnt_800 = 0;	// 취소 건수
		int cnt_600 = 0;	// 교환 건수
		int cnt_700 = 0;	// 반품 건수
		if(orderStatusCnt != null){
			cnt_All = Integer.parseInt(orderStatusCnt.get("cntAll").toString());		// 주문 접수(입금 대기), 결제 완료, 상품 준비중, 발송 완료(배송중), 구매 확정 건수
			cnt_800 = Integer.parseInt(orderStatusCnt.get("cnt800").toString());	// 취소 건수
			cnt_600 = Integer.parseInt(orderStatusCnt.get("cnt600").toString());	// 교환 건수
			cnt_700 = Integer.parseInt(orderStatusCnt.get("cnt700").toString());	// 반품 건수
		}
		model.addAttribute("cnt_All", cnt_All);
		model.addAttribute("cnt_800", cnt_800);
		model.addAttribute("cnt_600", cnt_600);
		model.addAttribute("cnt_700", cnt_700);
		
		// 조회일자 최초 3개월 기본 세팅
		if(searchVO.getSchType().isEmpty()){
			// 3달 전
			Calendar mon = Calendar.getInstance();
			mon.add(Calendar.MONTH , -3);
			searchVO.setSchStartDt(new java.text.SimpleDateFormat("yyyy-MM-dd").format(mon.getTime()));

			// 오늘
			Date today = new Date();         
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			searchVO.setSchEndDt(date.format(today));
		}
		
		// 취소 내역 리스트
		List<SqlMap> list = mypageOrderService.getMyClaimList("C", vo, searchVO);
		int totalCount = mypageOrderService.getMyClaimListCnt("C", vo, searchVO);
		
		for (SqlMap m : list) {
			List<SqlMap> goodsList = mypageOrderService.getClaimDetailList(Integer.parseInt(m.get("claimIdx").toString()));	
			m.put("goodsList", goodsList);
		}
		
		Page page = new Page(); 
		page.pagingInfo(searchVO, totalCount);

		model.addAttribute("VO", vo);
		model.addAttribute("SCHVO", searchVO);
		model.addAttribute("list", list);
		//model.addAttribute("goodsList", goodsList);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("page", page);		
		model.addAttribute("mySub", "mySub02");
		
		return PathUtil.getCtx()+"/mypage/order/myCancelList";
	}
	
	   /**
	    * @Method : myReturnList
	    * @Date: 2017. 8. 7.
	    * @Author :  서 정 길
	    * @Description	:	주문관리 - 반품 리스트
	   */
	@RequestMapping(value="/mypage/order/myReturnList") 
	public String myReturnList(OrderVO vo, SearchVO searchVO, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
		
		UserInfo userInfo = UserInfo.getUserInfo();
		searchVO.setPageBlock(10);
		if(session.getAttribute("SS_MEMBER_FLAG") != null){	// 로그인
			if(session.getAttribute("SS_MEMBER_FLAG").equals("Y")){	// 회원 로그인
				vo.setMemberIdx(userInfo.getMemberIdx());
				vo.setMemberId(userInfo.getMemberId());
				vo.setMemberGradeIdx(Integer.parseInt(userInfo.getLevelIdx()));
			}else{	// 비회원 로그인
				vo.setMemberIdx(0);
				vo.setNomemberOrderCd((String)session.getAttribute("SS_NOMEMBER_ORDER_CD"));	// 비회원 로그인 주문 코드
			}
		}else{	// 미로그인
			MethodUtil.alertMsgUrl(request, response, "로그인 정보가 존재하지 않습니다!", "/main.do");
			return null;
		}

		
		// 주문 상태별 건수
		SqlMap orderStatusCnt = mypageOrderService.getOrderStatusCnt(vo);
		int cnt_All = 0;	// 주문 접수(입금 대기), 결제 완료, 상품 준비중, 발송 완료(배송중), 구매 확정 건수
		int cnt_800 = 0;	// 취소 건수
		int cnt_600 = 0;	// 교환 건수
		int cnt_700 = 0;	// 반품 건수
		if(orderStatusCnt != null){
			cnt_All = Integer.parseInt(orderStatusCnt.get("cntAll").toString());		// 주문 접수(입금 대기), 결제 완료, 상품 준비중, 발송 완료(배송중), 구매 확정 건수
			cnt_800 = Integer.parseInt(orderStatusCnt.get("cnt800").toString());	// 취소 건수
			cnt_600 = Integer.parseInt(orderStatusCnt.get("cnt600").toString());	// 교환 건수
			cnt_700 = Integer.parseInt(orderStatusCnt.get("cnt700").toString());	// 반품 건수
		}
		model.addAttribute("cnt_All", cnt_All);
		model.addAttribute("cnt_800", cnt_800);
		model.addAttribute("cnt_600", cnt_600);
		model.addAttribute("cnt_700", cnt_700);
		
		// 조회일자 최초 3개월 기본 세팅
		if(searchVO.getSchType().isEmpty()){
			// 3달 전
			Calendar mon = Calendar.getInstance();
			mon.add(Calendar.MONTH , -3);
			searchVO.setSchStartDt(new java.text.SimpleDateFormat("yyyy-MM-dd").format(mon.getTime()));

			// 오늘
			Date today = new Date();         
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			searchVO.setSchEndDt(date.format(today));
		}
		
		// 반품 내역 리스트
		List<SqlMap> list = mypageOrderService.getMyClaimList("R", vo, searchVO);
		int totalCount = mypageOrderService.getMyClaimListCnt("R", vo, searchVO);
		for (SqlMap m : list) {
			List<SqlMap> goodsList = mypageOrderService.getClaimDetailList(Integer.parseInt(m.get("claimIdx").toString()));	
			m.put("goodsList", goodsList);
		}
		Page page = new Page(); 
		page.pagingInfo(searchVO, totalCount);

		model.addAttribute("VO", vo);
		model.addAttribute("SCHVO", searchVO);
		model.addAttribute("list", list);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("page", page);		
		model.addAttribute("mySub", "mySub03");
		
		return PathUtil.getCtx()+"/mypage/order/myReturnList";
	}
	
	   /**
	    * @Method : myExchangeList
	    * @Date: 2017. 8. 7.
	    * @Author :  서 정 길
	    * @Description	:	주문관리 - 교환 리스트
	   */
	@RequestMapping(value="/mypage/order/myExchangeList") 
	public String myExchangeList(OrderVO vo, SearchVO searchVO, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
		
		UserInfo userInfo = UserInfo.getUserInfo();
		searchVO.setPageBlock(10);
		SqlMap refundAccount = null;	// 환불계좌 정보

		if(session.getAttribute("SS_MEMBER_FLAG") != null){	// 로그인
			if(session.getAttribute("SS_MEMBER_FLAG").equals("Y")){	// 회원 로그인
				vo.setMemberIdx(userInfo.getMemberIdx());
				vo.setMemberId(userInfo.getMemberId());
				vo.setMemberGradeIdx(Integer.parseInt(userInfo.getLevelIdx()));

				refundAccount = memberService.getMemberAccount();					// 환불계좌 정보
			}else{	// 비회원 로그인
				vo.setMemberIdx(0);
				vo.setNomemberOrderCd((String)session.getAttribute("SS_NOMEMBER_ORDER_CD"));	// 비회원 로그인 주문 코드
			}
		}else{	// 미로그인
			MethodUtil.alertMsgUrl(request, response, "로그인 정보가 존재하지 않습니다!", "/main.do");
			return null;
		}


		
		// 주문 상태별 건수
		SqlMap orderStatusCnt = mypageOrderService.getOrderStatusCnt(vo);
		int cnt_All = 0;	// 주문 접수(입금 대기), 결제 완료, 상품 준비중, 발송 완료(배송중), 구매 확정 건수
		int cnt_800 = 0;	// 취소 건수
		int cnt_600 = 0;	// 교환 건수
		int cnt_700 = 0;	// 반품 건수
		if(orderStatusCnt != null){
			cnt_All = Integer.parseInt(orderStatusCnt.get("cntAll").toString());		// 주문 접수(입금 대기), 결제 완료, 상품 준비중, 발송 완료(배송중), 구매 확정 건수
			cnt_800 = Integer.parseInt(orderStatusCnt.get("cnt800").toString());	// 취소 건수
			cnt_600 = Integer.parseInt(orderStatusCnt.get("cnt600").toString());	// 교환 건수
			cnt_700 = Integer.parseInt(orderStatusCnt.get("cnt700").toString());	// 반품 건수
		}
		model.addAttribute("cnt_All", cnt_All);
		model.addAttribute("cnt_800", cnt_800);
		model.addAttribute("cnt_600", cnt_600);
		model.addAttribute("cnt_700", cnt_700);
		
		// 조회일자 최초 3개월 기본 세팅
		if(searchVO.getSchType().isEmpty()){
			// 3달 전
			Calendar mon = Calendar.getInstance();
			mon.add(Calendar.MONTH , -3);
			searchVO.setSchStartDt(new java.text.SimpleDateFormat("yyyy-MM-dd").format(mon.getTime()));

			// 오늘
			Date today = new Date();         
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			searchVO.setSchEndDt(date.format(today));
		}
		
		// 교환 내역 리스트
		List<SqlMap> list = mypageOrderService.getMyClaimList("X", vo, searchVO);
		int totalCount = mypageOrderService.getMyClaimListCnt("X", vo, searchVO);
		for (SqlMap m : list) {
			List<SqlMap> goodsList = mypageOrderService.getClaimDetailList(Integer.parseInt(m.get("claimIdx").toString()));	
			m.put("goodsList", goodsList);
		}
		
		Page page = new Page(); 
		page.pagingInfo(searchVO, totalCount);

		model.addAttribute("VO", vo);
		model.addAttribute("SCHVO", searchVO);
		model.addAttribute("list", list);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("page", page);	
		model.addAttribute("mySub", "mySub04");
		
		return PathUtil.getCtx()+"/mypage/order/myExchangeList";
	}
	
	   /**
	    * @Method : issueDocumentList
	    * @Date: 2017. 8. 7.
	    * @Author :  서 정 길
	    * @Description	:	주문관리 - 증빙 서류 발급
	   */
	@RequestMapping(value="/mypage/order/issueDocumentList") 
	public String issueDocumentList(OrderVO vo, SearchVO searchVO, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
		
		UserInfo userInfo = UserInfo.getUserInfo();

		if(session.getAttribute("SS_MEMBER_FLAG") != null){	// 로그인
			if(session.getAttribute("SS_MEMBER_FLAG").equals("Y")){	// 회원 로그인
				vo.setMemberIdx(userInfo.getMemberIdx());
				vo.setMemberId(userInfo.getMemberId());
				vo.setMemberGradeIdx(Integer.parseInt(userInfo.getLevelIdx()));
			}else{	// 비회원 로그인
				vo.setMemberIdx(0);
				vo.setNomemberOrderCd((String)session.getAttribute("SS_NOMEMBER_ORDER_CD"));	// 비회원 로그인 주문 코드
			}
		}else{	// 미로그인
			MethodUtil.alertMsgUrl(request, response, "로그인 정보가 존재하지 않습니다!", "/main.do");
			return null;
		}

		// 주문 상태별 건수
		SqlMap orderStatusCnt = mypageOrderService.getOrderStatusCnt(vo);
		int cnt_All = 0;	// 주문 접수(입금 대기), 결제 완료, 상품 준비중, 발송 완료(배송중), 구매 확정 건수
		int cnt_800 = 0;	// 취소 건수
		int cnt_600 = 0;	// 교환 건수
		int cnt_700 = 0;	// 반품 건수
		if(orderStatusCnt != null){
			cnt_All = Integer.parseInt(orderStatusCnt.get("cntAll").toString());		// 주문 접수(입금 대기), 결제 완료, 상품 준비중, 발송 완료(배송중), 구매 확정 건수
			cnt_800 = Integer.parseInt(orderStatusCnt.get("cnt800").toString());	// 취소 건수
			cnt_600 = Integer.parseInt(orderStatusCnt.get("cnt600").toString());	// 교환 건수
			cnt_700 = Integer.parseInt(orderStatusCnt.get("cnt700").toString());	// 반품 건수
		}
		model.addAttribute("cnt_All", cnt_All);
		model.addAttribute("cnt_800", cnt_800);
		model.addAttribute("cnt_600", cnt_600);
		model.addAttribute("cnt_700", cnt_700);
		
		// 조회일자 최초 3개월 기본 세팅
		if(searchVO.getSchType().isEmpty()){
			// 3달 전
			Calendar mon = Calendar.getInstance();
			mon.add(Calendar.MONTH , -3);
			searchVO.setSchStartDt(new java.text.SimpleDateFormat("yyyy-MM-dd").format(mon.getTime()));

			// 오늘
			Date today = new Date();         
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			searchVO.setSchEndDt(date.format(today));
		}

		List<SqlMap> list = mypageOrderService.getIssueDocumentList( vo, searchVO);
		for (SqlMap m : list) {
			String productList = mypageOrderService.getProductList(m);
			m.put("productList", productList);
			
			//모바일일경우 
			vo.setOrderCd(m.get("orderCd").toString());
			List<SqlMap> mobileProductList = mypageOrderService.getOrderDetailList(vo, false);	// 주문 상세 리스트, 가상계좌 입금에서 호출 아님 (false)
			m.put("mobileProductList", mobileProductList);
		}
		
		int totalCount = mypageOrderService.getIssueDocumentCnt(vo, searchVO);
		
		Page page = new Page(); 
		page.pagingInfo(searchVO, totalCount);

		model.addAttribute("VO", vo);
		model.addAttribute("SCHVO", searchVO);
		model.addAttribute("list", list);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("page", page);		
		model.addAttribute("mySub", "mySub05");
		
		return PathUtil.getCtx()+"/mypage/order/issueDocumentList";
	}
	

	 /**
	    * @Method : CashReceiptReq
	    * @Date: 2017. 7. 18.
	    * @Author :  강병철
	    * @Description	:	현금영수증 신청
	   */
	@RequestMapping(value = "/ajax/order/CashReceiptReq", method = RequestMethod.POST) 
	public @ResponseBody JsonResultVO CashReceiptReq( String ORDER_CD,String CASH_RECEIPT_GUBUN, String CASH_RECEIPT_NO,  HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
		JsonResultVO resultMap = new JsonResultVO();
		UserInfo userInfo = UserInfo.getUserInfo();
		OrderVO vo = new OrderVO();
		vo.setOrderCd(ORDER_CD);
		if(session.getAttribute("SS_MEMBER_FLAG") != null){	// 로그인
			if(session.getAttribute("SS_MEMBER_FLAG").equals("Y")){	// 회원 로그인
				vo.setMemberIdx(userInfo.getMemberIdx());
				vo.setMemberId(userInfo.getMemberId());
				vo.setMemberGradeIdx(Integer.parseInt(userInfo.getLevelIdx()));
			}else{	// 비회원 로그인
				vo.setMemberIdx(0);
				vo.setNomemberOrderCd((String)session.getAttribute("SS_NOMEMBER_ORDER_CD"));	// 비회원 로그인 주문 코드
			}
		}else{	// 미로그인
			resultMap.setResult(false);
			resultMap.setMsg("로그인이 필요합니다.");
			return resultMap;
		}
		
		SqlMap orderInfo = mypageOrderService.getOrderInfo(vo, false);			// 주문 마스터 정보, 가상계좌 입금에서 호출 아님 (false)	

		if(orderInfo == null ){
			resultMap.setResult(false);
			resultMap.setMsg("주문 정보가 존재하지 않습니다.");
			return resultMap;
		}
		
		//구매 확정인경우에만
		/* 일단 주석 처리
		if(!(orderInfo.get("orderStatusCd").equals("900")) ){
			resultMap.setResult(false);
			resultMap.setMsg("현금영수증 신청 가능한 상태가 아닙니다. 구매 확정일경우에만 신청 가능합니다.");
			return resultMap;
		}
		*/
		
		//실시간계좌이체, 가상계좌, PAYCO 인경우에만  || orderInfo.get("payType").equals("PAY_TYPE35")
		if(!(orderInfo.get("payType").equals("PAY_TYPE20") || orderInfo.get("payType").equals("PAY_TYPE25")) ){
			resultMap.setResult(false);
			resultMap.setMsg("가상계좌,실시간계좌입금 일 경우에만 신청 가능합니다.");
			return resultMap;
		}

		//로그인한 아이피, HTTP_USER_AGENT, memeber idx 저장 (클레임테이블 저장시 사용)
		orderInfo.put("httpUserAgent", request.getHeader("user-agent"));
		InetAddress local = InetAddress.getLocalHost();	// LOCAL IP ADDRESS
		orderInfo.put("regIp", local.getHostAddress());
		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 로그인
			if(Integer.parseInt(userInfo.getMemberIdx().toString()) > 0){	// 회원 로그인
				orderInfo.put("memberIdx", userInfo.getMemberIdx());
			}
		}
		
		/* 결제수단 (공통코드 PAY_TYPE10 : 신용카드, PAY_TYPE15 : 빌키, PAY_TYPE20 : 실시간계좌, PAY_TYPE25 : 가상계좌, PAY_TYPE30 : 휴대폰, PAY_TYPE35 : PAYCO, PAY_TYPE40 : 카카오페이, PAY_TYPE45 : Npay, PAY_TYPE50 : PAYNOW, PAY_TYPE90 : 포인트) */
		try {
			//lg u+ 결제 (실시간계좌, 가상계좌
			if (orderInfo.get("payType").equals("PAY_TYPE20") || orderInfo.get("payType").equals("PAY_TYPE25") )	{
				
				SqlMap xpayInfo = mypageOrderService.getXpayResult(vo);
				if (mypageOrderService.lgTelecomCashReceiptProcess(orderInfo, CASH_RECEIPT_GUBUN, CASH_RECEIPT_NO,xpayInfo,resultMap) )
				{
					resultMap.setResult(true);
				}
				else 
				{
					resultMap.setResult(false);
					//메시지는 lgTelecomCancelProcess에서 저장한다.
				}
			}
			else if(orderInfo.get("payType").equals("PAY_TYPE35")) {
				//PAYCO 결제결과
				SqlMap approvalInfo = mypageOrderService.getPaycoApprovalResult(vo);
				
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			resultMap.setResult(false);
			//메시지는 lgTelecomCashReceiptProcess에서 할당한다.
			if(resultMap.getMsg() == null || resultMap.getMsg().isEmpty()){
				resultMap.setMsg("현금영수증 발급중 에러가 발생했습니다! (200)");
			}			
		}
		return resultMap;
	}
	
	 /**
	    * @Method : TaxBillReq
	    * @Date: 2017. 7. 18.
	    * @Author :  강병철
	    * @Description	:	세금계산서 발급 신청
	   */
	@RequestMapping(value = "/ajax/order/TaxBillReq", method = RequestMethod.POST) 
	public @ResponseBody JsonResultVO TaxBillReq( String ORDER_IDX,String BUSINESS_NO,String COMPANY_NM,String CEO_NM,String ADDR,String UPTAE,String JONGMOK,String DAMDANG_NM,String DAMDANG_EMAIL, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
		JsonResultVO resultMap = new JsonResultVO();
		UserInfo userInfo = UserInfo.getUserInfo();
		OrderVO vo = new OrderVO();
		if(session.getAttribute("SS_MEMBER_FLAG") != null){	// 로그인
			if(session.getAttribute("SS_MEMBER_FLAG").equals("Y")){	// 회원 로그인
				vo.setMemberIdx(userInfo.getMemberIdx());
				vo.setMemberId(userInfo.getMemberId());
				vo.setMemberGradeIdx(Integer.parseInt(userInfo.getLevelIdx()));
			}else{	// 비회원 로그인
				vo.setMemberIdx(0);
				vo.setNomemberOrderCd((String)session.getAttribute("SS_NOMEMBER_ORDER_CD"));	// 비회원 로그인 주문 코드
			}
		}else{	// 미로그인
			resultMap.setResult(false);
			resultMap.setMsg("로그인이 필요합니다.");
			return resultMap;
		}
		
		try {
			mypageOrderService.TaxBillReq( ORDER_IDX, BUSINESS_NO, COMPANY_NM, CEO_NM, ADDR, UPTAE, JONGMOK, DAMDANG_NM, DAMDANG_EMAIL, vo.getMemberIdx().toString());	
			resultMap.setResult(true);
		} catch (Exception e) {
			resultMap.setResult(false);
			resultMap.setMsg("세금계산서 발급 접수중 에러가 발생하였습니다.");
			return resultMap;
		}
		
		
		return resultMap;
	}
	
	
	public static String MD5Encode(String str) {
	    String MD5 = "";
	    try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        md.update(str.getBytes());
	        byte byteData[] = md.digest();
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        MD5 = sb.toString();
	 
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	        MD5 = null;
	    }
	    return MD5;
	}


		
	   /**
	    * @Method : orderDetail
	    * @Date: 2017. 8. 7.
	    * @Author :  서 정 길
	    * @Description	:	마이페이지 주문관리 주문 상세
	   */
	@RequestMapping(value="/mypage/order/orderDetail") 
	public String orderDetail(OrderVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
		
		UserInfo userInfo = UserInfo.getUserInfo();
		
		SqlMap refundAccount = null;	// 환불계좌 정보

		if(session.getAttribute("SS_MEMBER_FLAG") != null){	// 로그인
			if(session.getAttribute("SS_MEMBER_FLAG").equals("Y")){	// 회원 로그인
				vo.setMemberIdx(userInfo.getMemberIdx());
				vo.setMemberId(userInfo.getMemberId());
				vo.setMemberGradeIdx(Integer.parseInt(userInfo.getLevelIdx()));
				
				refundAccount = memberService.getMemberAccount();					// 환불계좌 정보
			}else{	// 비회원 로그인
				vo.setMemberIdx(0);
				vo.setNomemberOrderCd((String)session.getAttribute("SS_NOMEMBER_ORDER_CD"));	// 비회원 로그인 주문 코드
			}
		}else{	// 미로그인
			MethodUtil.alertMsgUrl(request, response, "로그인 정보가 존재하지 않습니다!", "/main.do");
			return null;
		}
		
		if(vo.getOrderCd() == null || vo.getOrderCd().isEmpty()){
			MethodUtil.alertMsgBack(request, response, "주문 정보가 존재하지 않습니다!");
			return null;
		}
		
		SqlMap orderInfo = mypageOrderService.getOrderInfo(vo, false);			// 주문 마스터 정보, 가상계좌 입금에서 호출 아님 (false)				
		List<SqlMap> list = mypageOrderService.getOrderDetailList(vo, false);	// 주문 상세 리스트, 가상계좌 입금에서 호출 아님 (false)

		if(orderInfo == null || orderInfo.size() == 0 || list == null || list.size() == 0){
			MethodUtil.alertMsgBack(request, response, "주문 정보가 존재하지 않습니다!");
			return null;
		}
		
		List<SqlMap> freeGiftList = mypageOrderService.getOrderGiftList(vo, "Y");		// 무료 사은품 리스트
		List<SqlMap> priceGiftList = mypageOrderService.getOrderGiftList(vo, "N");	// 금액별 사은품 리스트
		
		final String CST_PLATFORM = SpringMessage.getMessage("xpay.PLATFORM"); 
		final String LGD_MERTKEY = SpringMessage.getMessage("xpay.MERTKEY");  //"c7ecf8d164fec7c46d4d049c8700d423";
		final String BILLMERTKEY = SpringMessage.getMessage("xpay.BILLMERTKEY");  //"c7ecf8d164fec7c46d4d049c8700d423";

		String paycoUrl = "https://bill.payco.com";
		String lgScriptUrl = "https://pgweb.uplus.co.kr/WEB_SERVER/js/receipt_link.js";
//		String lgEscrowScriptUrl = "https://pgweb.uplus.co.kr/js/DACOMEscrow.js";	// LG U+ 에스크로 구매확인 창 호출 URL (ActiveX)
		String lgEscrowScriptUrl = "https://pgweb.uplus.co.kr/js/DACOMEscrow_UTF8.js";	// LG U+ 에스크로 구매확인 창 호출 URL (ActiveX)
		if (CST_PLATFORM.equals("test")) {
			paycoUrl = "https://alpha-bill.payco.com"; //영수증 URL
	//		lgScriptUrl = "http://pgweb.uplus.co.kr:7085/WEB_SERVER/js/receipt_link.js";
//			lgEscrowScriptUrl = "https://pgweb.uplus.co.kr:7087/js/DACOMEscrow.js";		// 상담원이 알려준 임시 주소
//			lgEscrowScriptUrl = "https://pgweb.uplus.co.kr:7087/js/DACOMEscrow_UTF8.js";		// 상담원이 알려준 임시 주소
//			lgEscrowScriptUrl = "http://pgweb.uplus.co.kr:7085/js/DACOMEscrow.js";		// 메뉴얼상 주소
		}
		String authData = "";
		
		// PAYCO 결제면 PAYCO 결제 결과 리스트 구함 (복합 결제라서)
		if (orderInfo.get("payType") != null) {
			if(orderInfo.get("payType").toString().equals("PAY_TYPE35")){
				List<SqlMap> paycoList = mypageOrderService.getPaycoResultList(vo);	// PAYCO 결제 결과 리스트
				model.addAttribute("paycoList", paycoList);
			}
			else if(orderInfo.get("payType").toString().equals("PAY_TYPE90")){
				//포인트결제
			}else if (orderInfo.get("payType").toString().equals("PAY_TYPE55")){
				//smilepay 결제
				SqlMap smilepayInfo = mypageOrderService.getSmilepayResult(vo);	// 스마일페이 결제 결과 리스트
				model.addAttribute("smilepayInfo", smilepayInfo);
			}else if (orderInfo.get("payType").toString().equals("PAY_TYPE60")){
				//원더페이 결제 
				SqlMap wonderpayInfo = mypageOrderService.getWonderpayResult(vo);	// 원더페이 결제 결과 리스트
				model.addAttribute("wonderpayInfo", wonderpayInfo);
			}
			else
			{
				if(orderInfo.get("pgType") != null) {
					// LGU+일경우
					if (orderInfo.get("pgType").toString().equals("LG")) {
						//ⓐ 인증문자열 생성: LGD_MID + LGD_TID + MertKey
				     	//ⓑ md5로 인증문자열 암호화(authdata): md5(인증문자열)
						authData = orderInfo.get("lgdMid").toString()+orderInfo.get("lgdTid").toString()+(orderInfo.get("payType").toString().equals("PAY_TYPE15") ? BILLMERTKEY : LGD_MERTKEY);
						authData  = MD5Encode(authData);
					}
				}
			}
		}

		List<SqlMap> exchangeReasonList = getCodeList("EXCHANGE_REASON");	// 교환사유 리스트
		List<SqlMap> returnReasonList = getCodeList("RETURN_REASON");			// 반품사유 리스트
		List<SqlMap> bankCodeList = getCodeList("BANK_CODE");						// 환불은행 리스트		
		

		final String kcpSiteCd =  SpringMessage.getMessage("kcp.siteCd");
		
		model.addAttribute("kcpSiteCd", kcpSiteCd);
		
		model.addAttribute("orderInfo", orderInfo);
		model.addAttribute("list", list);		
		model.addAttribute("freeGiftList", freeGiftList);
		model.addAttribute("priceGiftList", priceGiftList);
		model.addAttribute("paycoUrl", paycoUrl);
		model.addAttribute("lgScriptUrl", lgScriptUrl);
		model.addAttribute("lgEscrowScriptUrl", lgEscrowScriptUrl);
		model.addAttribute("authData", authData);
		model.addAttribute("exchangeReasonList", exchangeReasonList);
		model.addAttribute("returnReasonList", returnReasonList);
		model.addAttribute("bankCodeList", bankCodeList);
		model.addAttribute("refundAccount", refundAccount);
		model.addAttribute("VO", vo);
		
		return PathUtil.getCtx()+"/mypage/order/orderDetail";
	}

	   /**
	    * @Method : requestExchangeOrder
	    * @Date: 2017. 8. 11.
	    * @Author :  서 정 길
	    * @Description	:	교환 신청
	   */
	@RequestMapping(value="/mypage/order/requestExchangeOrder") 
	public void requestExchangeOrder(ClaimVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
		
		UserInfo userInfo = UserInfo.getUserInfo();

		if(session.getAttribute("SS_MEMBER_FLAG") != null){	// 로그인
			if(session.getAttribute("SS_MEMBER_FLAG").equals("Y")){	// 회원 로그인
				vo.setMemberIdx(userInfo.getMemberIdx());
				vo.setRegIdx(userInfo.getMemberIdx());
			}else{	// 비회원 로그인
				vo.setMemberIdx(0);
				vo.setRegIdx(0);
				vo.setNomemberOrderCd((String)session.getAttribute("SS_NOMEMBER_ORDER_CD"));	// 비회원 로그인 주문 코드
			}
		}else{	// 미로그인
			MethodUtil.alertMsgUrl(request, response, "로그인 정보가 존재하지 않습니다!", "/main.do");
			return;
		}
		
		try{
			if(vo.getOrderCd() == null || vo.getOrderCd().isEmpty() || vo.getOrderDetailIdxesStr() == null || vo.getOrderDetailIdxesStr().isEmpty()){
				MethodUtil.alertMsgBack(request, response, "파라미터가 정확하지 않습니다!");
				return;
			}
			
			vo.setOrderDetailIdxes(vo.getOrderDetailIdxesStr().split(",", -1));
			
			vo.setRegHttpUserAgent(request.getHeader("user-agent"));	// HTTP_USER_AGENT
			
			InetAddress local = InetAddress.getLocalHost();	// LOCAL IP ADDRESS
			vo.setRegIp(local.getHostAddress());

			// 선택한 상품의 주문 상태 유효성 체크
			String[] orderStatusCds = new String[]{"400","500","690"};	// 발송 완료, 배송 완료, 교환 완료만 교환 신청 가능
			
			JsonResultVO crMap = mypageOrderService.getOrderDetailValidStatusCnt(vo, orderStatusCds);
			
			if(!crMap.getResult()){
				MethodUtil.alertMsgBack(request, response, crMap.getMsg());
				return;
			}else{
				if(crMap.getResultCnt() < vo.getOrderDetailIdxes().length){
					MethodUtil.alertMsgBack(request, response, "유효하지 않은 주문상태의 상품이 포함되어 있습니다! 다시 확인해 주시기 바랍니다.");
					return;
				}
			}
			
			vo.setBeforeOrderStatusCds(orderStatusCds);
		
			JsonResultVO resultMap = new JsonResultVO();
			
			resultMap = mypageOrderService.requestExchangeOrder(vo);		// 교환 신청 처리
			
			
			if(resultMap.getResult()){
				//---- SMS, 메일발송 (에러나더라도 rollback안함)
	         	try {
	         		if( resultMap.getResult()  ) {
	         			orderService.callOrderClaimSmsSendAndEmail("ORD007", vo.getOrderCd(), vo.getClaimIdx());
	         		}
	         	}catch(Exception e) {
	         		e.printStackTrace();
	         	}
	         	
				MethodUtil.alertMsgUrl(request, response, "신청이 완료되었습니다. 감사합니다.", vo.getReturnUrl());
			}else{
				MethodUtil.alertMsgBack(request, response, resultMap.getMsg());
			}
		}catch (Exception e) {
			e.printStackTrace();
			MethodUtil.alertMsgBack(request, response, "에러가 발생했습니다. ");
		}
	}
	
	   /**
	    * @Method : requestReturnOrder
	    * @Date: 2017. 8. 11.
	    * @Author :  서 정 길
	    * @Description	:	반품 신청
	   */
	@RequestMapping(value="/mypage/order/requestReturnOrder") 
	public void requestReturnOrder(ClaimVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
		
		UserInfo userInfo = UserInfo.getUserInfo();

		if(session.getAttribute("SS_MEMBER_FLAG") != null){	// 로그인
			if(session.getAttribute("SS_MEMBER_FLAG").equals("Y")){	// 회원 로그인
				vo.setMemberIdx(userInfo.getMemberIdx());
				vo.setRegIdx(userInfo.getMemberIdx());
			}else{	// 비회원 로그인
				vo.setMemberIdx(0);
				vo.setRegIdx(0);
				vo.setNomemberOrderCd((String)session.getAttribute("SS_NOMEMBER_ORDER_CD"));	// 비회원 로그인 주문 코드
			}
		}else{	// 미로그인
			MethodUtil.alertMsgUrl(request, response, "로그인 정보가 존재하지 않습니다!", "/main.do");
			return;
		}
		
		try{
			if(vo.getOrderCd() == null || vo.getOrderCd().isEmpty() || vo.getOrderDetailIdxesStr() == null || vo.getOrderDetailIdxesStr().isEmpty()){
				MethodUtil.alertMsgBack(request, response, "파라미터가 정확하지 않습니다!");
				return;
			}
			
			/*if(vo.getRefundBankCode() == null || vo.getRefundBankCode().trim().isEmpty()){
				MethodUtil.alertMsgBack(request, response, "환불 계좌 은행을 선택하세요!");
				return;
			}

			if(vo.getRefundDepositor() == null || vo.getRefundDepositor().trim().isEmpty()){
				MethodUtil.alertMsgBack(request, response, "환불 계좌 예금주를 입력해 주세요!");
				return;
			}

			if(vo.getRefundAccount() == null || vo.getRefundAccount().trim().isEmpty()){
				MethodUtil.alertMsgBack(request, response, "환불 계좌번호를 입력해 주세요!");
				return;
			}*/

			vo.setOrderDetailIdxes(vo.getOrderDetailIdxesStr().split(",", -1));
			
			vo.setRegHttpUserAgent(request.getHeader("user-agent"));	// HTTP_USER_AGENT
			
			InetAddress local = InetAddress.getLocalHost();	// LOCAL IP ADDRESS
			vo.setRegIp(local.getHostAddress());

			// 선택한 상품의 주문 상태 유효성 체크
			String[] orderStatusCds = new String[]{"400","500","670","690"};	// 발송 완료, 배송 완료, 교환 불가, 교환 완료만 반품 신청 가능
			
			JsonResultVO crMap = mypageOrderService.getOrderDetailValidStatusCnt(vo, orderStatusCds);
			
			if(!crMap.getResult()){
				MethodUtil.alertMsgBack(request, response, crMap.getMsg());
				return;
			}else{
				if(crMap.getResultCnt() < vo.getOrderDetailIdxes().length){
					MethodUtil.alertMsgBack(request, response, "유효하지 않은 주문상태의 상품이 포함되어 있습니다! 다시 확인해 주시기 바랍니다.");
					return;
				}
			}
			
			vo.setBeforeOrderStatusCds(orderStatusCds);
		
			JsonResultVO resultMap = new JsonResultVO();
			
			resultMap = mypageOrderService.requestReturnOrder(vo);		// 반품 신청 처리
			
			if(resultMap.getResult()){
				//---- SMS, 메일발송 (에러나더라도 rollback안함)
	         	try {
	         		if( resultMap.getResult()  ) {
	         			orderService.callOrderClaimSmsSendAndEmail("ORD008", vo.getOrderCd(), vo.getClaimIdx());
	         		}
	         	}catch(Exception e) {
	         		e.printStackTrace();
	         	}
	         	
				MethodUtil.alertMsgUrl(request, response, "신청이 완료되었습니다. 감사합니다.", vo.getReturnUrl());
			}else{
				MethodUtil.alertMsgBack(request, response, resultMap.getMsg());
			}
		}catch (Exception e) {
			e.printStackTrace();
			MethodUtil.alertMsgBack(request, response, "에러가 발생했습니다. ");
		}
	}
	
	
}
