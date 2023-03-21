package com.gxenSoft.mall.member.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gxenSoft.mall.sns.api.kakao.KakaoLoginBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gxenSoft.mall.common.service.CommonService;
import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.common.web.CommonMethod;
import com.gxenSoft.mall.member.service.MemberService;
import com.gxenSoft.mall.member.vo.MemberVO;
import com.gxenSoft.mall.product.service.ProductService;
import com.gxenSoft.mall.product.vo.ProductVO;
import com.gxenSoft.mall.sns.service.SnsService;
import com.gxenSoft.message.SpringMessage;
import com.gxenSoft.method.ConvertUtil;
import com.gxenSoft.method.DateUtil;
import com.gxenSoft.method.MethodUtil;
import com.gxenSoft.snsApi.FacebookLoginBO;
import com.gxenSoft.snsApi.NaverLoginBO;
import com.gxenSoft.snsApi.PaycoLoginBO;
import com.gxenSoft.snsApi.WonderLoginBO;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.pathUtil.PathUtil;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : MemberController
 * PACKAGE NM : com.gxenSoft.mall.member.web
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 6. 16. 
 * HISTORY :
 
 *************************************
 */
@Controller
public class MemberController extends CommonMethod{
	
	static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private SnsService snsService;
	
	@Autowired
	private ProductService productService;
	
	/**
	 * @Method : joinStep01
	 * @Date		: 2017. 6. 16.
	 * @Author	:  유  준  철 
	 * @Description	:	회원혜택 
	 */
	@RequestMapping("/member/joinStep01")
	public String joinStep01(MemberVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session)throws Exception{
		// SNS 
		session.setAttribute("snsMode", "join");
		
		String naverAuthUrl = NaverLoginBO.getAuthorizationUrl(session);							// 네이버
		String kakaoAuthUrl = KakaoLoginBO.getAuthorizationUrl(session);							// 카카오
		String facebookAuthUrl = FacebookLoginBO.getAuthorizationUrl(session);				// 페이스북
		String paycoAuthUrl = PaycoLoginBO.getAuthorizationUrl(session);							// 페이코
		String wonderAuthUrl = WonderLoginBO.getAuthorizationUrl(session);							// 원더로그인
		
		model.addAttribute("naverAuthUrl", naverAuthUrl);
		model.addAttribute("kakaoAuthUrl", kakaoAuthUrl);
		model.addAttribute("facebookAuthUrl", facebookAuthUrl);
		model.addAttribute("paycoAuthUrl", paycoAuthUrl);
		model.addAttribute("wonderAuthUrl", wonderAuthUrl);
		
		return PathUtil.getCtx()+"/member/joinStep01";
	}
	
	/**
	 * @Method : joinStep02
	 * @Date		: 2017. 6. 16.
	 * @Author	:  유  준  철 
	 * @Description	:	약관 동의
	 */
	@RequestMapping("/member/joinStep02")
	public String joinStep02(MemberVO vo, HttpServletRequest request, HttpServletResponse response, Model model)throws Exception{
		List<SqlMap> termsGubun = getCodeList("TERMS_GUBUN");												// 약관구분 리스트
		List<SqlMap> termsList = memberService.getTermsList();															// 약관/개인정보 리스트
		
		model.addAttribute("termsGubun",termsGubun);
		model.addAttribute("termsList",termsList);
		
		return PathUtil.getCtx()+"/member/joinStep02";
	}
	
	/**
	 * @Method : joinForm
	 * @Date		: 2017. 6. 16.
	 * @Author	:  유  준  철 
	 * @Description	:	회원가입 폼
	 */
	@RequestMapping("/member/joinForm")
	public String joinForm(MemberVO vo, HttpServletRequest request, HttpServletResponse response, Model model)throws Exception{
		List<SqlMap> mailList = getCodeList("MAIL_KIND");												// 메일 리스트
		List<SqlMap> subscribeList = getCodeList("SUBSCRIBE_TYPE");								// 가입경로 리스트
		
		model.addAttribute("mailList",mailList);
		model.addAttribute("subscribeList",subscribeList);
		model.addAttribute("VO",vo);
		
		return PathUtil.getCtx()+"/member/joinForm";
	}
	
	/**
	 * @Method : duplicateCheckMemberIdAjax
	 * @Date		: 2017. 6. 19.
	 * @Author	:  유  준  철 
	 * @Description	:	아이디 중복 체크
	 */
	@RequestMapping("/ajax/member/duplicateCheckMemberIdAjax") 
	public @ResponseBody int duplicateCheckMemberIdAjax(MemberVO vo, HttpServletRequest request, HttpServletResponse response)throws Exception{
		int flag = memberService.duplicateCheckMemberId(vo);
		return flag;
	}
	
	/**
	 * @Method : duplicateCheckEmailAjax
	 * @Date		: 2017. 6. 19.
	 * @Author	:  유  준  철 
	 * @Description	:	이메일 중복 체크
	 */
	@RequestMapping("/ajax/member/duplicateCheckEmailAjax") 
	public @ResponseBody int duplicateCheckEmailAjax(MemberVO vo, HttpServletRequest request, HttpServletResponse response)throws Exception{
		int flag = memberService.duplicateCheckEmail(vo);
		return flag;
	}
	
	/**
	 * @Method : duplicateCheckRecommenderAjax
	 * @Date		: 2017. 6. 20.
	 * @Author	:  유  준  철 
	 * @Description	:	추천인 중복 체크
	 */
	@RequestMapping("/ajax/member/duplicateCheckRecommenderAjax") 
	public @ResponseBody int duplicateCheckRecommenderAjax(MemberVO vo, HttpServletRequest request, HttpServletResponse response)throws Exception{
		int flag = memberService.duplicateCheckRecommender(vo);
		return flag;
	}
	
	/**
	 * @Method : memberSave
	 * @Date		: 2017. 6. 20.
	 * @Author	:  유  준  철 
	 * @Description	:	회원가입 저장
	 */
	@RequestMapping("/member/memberSave") 
	public String memberSave(MemberVO vo, HttpServletRequest request, HttpServletResponse response, Model model)throws Exception{
		vo.setHttpUserAgent(request.getHeader("user-agent"));	
		
		memberService.memberSave(vo);
		
		return "redirect:/member/joinComplete.do?memberId=" + vo.getMemberId();
	}
	
	/**
	 * @Method : joinComplete
	 * @Date		: 2017. 6. 20.
	 * @Author	:  유  준  철 
	 * @Description	:	회원가입 완료
	 */
	@RequestMapping("/member/joinComplete")
	public String joinComplete(MemberVO vo, ProductVO productVO, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		SqlMap detail = memberService.joinCompleteDetail(vo);
		List<SqlMap> list = productService.getBestProductList(productVO);
		List<SqlMap> productSubImgList = productService.getProductSubImgList();
		
		String memberId = (String) detail.get("memberId");
		String memberPwd = (String) detail.get("memberPw");
		String joinType = (String) detail.get("joinType");
		
		vo.setSnsCd(memberId);
		vo.setSnsMode("login");
		
		if(joinType.equals("0")){						// 회원가입
			vo.setMemberId(memberId);
			vo.setMemberPwd(memberPwd);
			vo.setJoinType(joinType);
			commonService.loginCheck(vo);
		}else{														// SNS가입
			snsService.snsLoginCheck(vo);			
		}
		
		String currentDate = DateUtil.getCurrentDate("yyyy-MM-dd");		
		SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
        Date nowDate = null;
        Date startDate = null;
        Date endDate = null;
        
		nowDate = format.parse(currentDate);											// 현재 날짜
		//startDate = format.parse("2019-11-13 00:00");							// 시작 날짜
		//endDate = format.parse("2019-11-13 23:59");								// 종료 날짜
		startDate = format.parse("2019-11-13" );							// 시작 날짜
		endDate = format.parse("2019-11-13");								// 종료 날짜
        boolean compare = nowDate.equals(startDate);
        int startCompare = nowDate.compareTo(startDate);
        int endCompare = nowDate.compareTo(endDate);
        
        String compareVal="";
        
        if(compare){
        	compareVal = "Y";
        }else if (startCompare > 0 && endCompare < 1){
        	compareVal = "Y";
        }else{
        	compareVal = "N";
        }
        
        vo.setCompareFlag(compareVal);
		model.addAttribute("detail", detail);
		model.addAttribute("list", list);
		model.addAttribute("productSubImgList", productSubImgList);
		model.addAttribute("VO", vo);
		
		return PathUtil.getCtx()+"/member/joinComplete";
	}
	
	/**
	 * @Method : joinSnsForm
	 * @Date		: 2017. 6. 21.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS가입 폼
	 */
	@RequestMapping("/member/joinSnsForm")
	public String joinSnsForm(MemberVO vo, HttpServletRequest request, HttpServletResponse response, Model model)throws Exception{
		List<SqlMap> mailList = getCodeList("MAIL_KIND");												// 메일 리스트
		List<SqlMap> termsGubun = getCodeList("TERMS_GUBUN");								// 약관구분 리스트
		List<SqlMap> termsList = memberService.getTermsList();											// 약관/개인정보 리스트
		
		model.addAttribute("mailList", mailList);
		model.addAttribute("termsGubun",termsGubun);
		model.addAttribute("termsList",termsList);
		model.addAttribute("VO", vo);
		
		return PathUtil.getCtx()+"/member/joinSnsForm";
	}
	
	//========================================================== 마이페이지 ==========================================================
	
	/**
	 * @Method : pwdCheckForm
	 * @Date		: 2017. 8. 6.
	 * @Author	:  유  준  철 
	 * @Description	:	회원정보 비밀번호 재확인 페이지
	 */
	@RequestMapping("/mypage/pwdCheckForm")
	public String pwdCheckForm(MemberVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session)throws Exception{
		UserInfo userInfo = UserInfo.getUserInfo();
		
		if(userInfo.getMemberLoginType().equals("SNS")){
			session.setAttribute("SS_MY_SNS_AUTH", "Y");																		// SNS 계정 인증 세션
			session.setAttribute("SS_SNS_REFERER", request.getHeader("referer"));								// SNS 인증 하기전  이전페이지 세션 저장
			
			String snsAuthUrl = "";
			vo.setSnsCd(userInfo.getMemberId()); 
			
			SqlMap snsDetail = snsService.getSnsMemberInfo(vo);
			String snsType = (String) snsDetail.get("snsType");
			
			if(snsType.equals("SNS_TYPE10")){
				snsAuthUrl = KakaoLoginBO.getAuthorizationUrl(session);								// 카카오
			}else if(snsType.equals("SNS_TYPE20")){
				snsAuthUrl = NaverLoginBO.getAuthorizationUrl(session);								// 네이버
			}else if(snsType.equals("SNS_TYPE30")){
				snsAuthUrl = FacebookLoginBO.getAuthorizationUrl(session);						// 페이스북
			}else if(snsType.equals("SNS_TYPE40")){
				snsAuthUrl = PaycoLoginBO.getAuthorizationUrl(session);								// 페이코
			}
			model.addAttribute("snsAuthUrl", snsAuthUrl);
		}
		
		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){
			vo.setMemberIdx(userInfo.getMemberIdx());
		}
		
		vo.setRefererUrl(request.getHeader("referer"));
		model.addAttribute("VO", vo);
		return PathUtil.getCtx()+"/mypage/pwdCheckForm";
	}
	
	/**
	 * @Method : pwdCheck
	 * @Date		: 2017. 8. 6.
	 * @Author	:  유  준  철 
	 * @Description	:	회원정보 비밀번호 처리
	 */
	@RequestMapping("/mypage/pwdCheck")
	public void pwdCheck(MemberVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session)throws Exception{
		session.setAttribute("SS_MY_MEMBER_FLAG", "Y");								// 비밀번호 세션 추가
		MethodUtil.redirectRefererUrl(response, vo.getRefererUrl());
	}
	
	/**
	 * @Method : snsConnect
	 * @Date		: 2017. 8. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS 계정 연결 관리
	 */
	@RequestMapping("/mypage/member/snsConnect")
	public String snsConnect(MemberVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session)throws Exception{
		UserInfo userInfo = UserInfo.getUserInfo();
		
		if(userInfo.getMemberLoginType().equals("SNS")){
			MethodUtil.alertMsgUrl(request, response, SpringMessage.getMessage("common.login001"), "/main.do" );
			return null;
		}else{
			String naverAuthUrl = NaverLoginBO.getAuthorizationUrl(session);								// 네이버
			String kakaoAuthUrl = KakaoLoginBO.getAuthorizationUrl(session);							// 카카오
			String facebookAuthUrl = FacebookLoginBO.getAuthorizationUrl(session);					// 페이스북
			String paycoAuthUrl = PaycoLoginBO.getAuthorizationUrl(session);								// 페이코
			
			vo.setSnsType("SNS_TYPE10");		// 카카오
			SqlMap snsKakaoInfo = snsService.getSnsInfo(vo);
			vo.setSnsType("SNS_TYPE20");		// 네이버
			SqlMap snsNaverInfo = snsService.getSnsInfo(vo);
			vo.setSnsType("SNS_TYPE30");		// 페이스북
			SqlMap snsFacebookInfo = snsService.getSnsInfo(vo);
			vo.setSnsType("SNS_TYPE40");		// 페이코
			SqlMap snsPaycoInfo = snsService.getSnsInfo(vo);
			
			session.setAttribute("snsMode", "mypage");
			model.addAttribute("naverAuthUrl", naverAuthUrl);
			model.addAttribute("kakaoAuthUrl", kakaoAuthUrl);
			model.addAttribute("facebookAuthUrl", facebookAuthUrl);
			model.addAttribute("paycoAuthUrl", paycoAuthUrl);
			model.addAttribute("snsKakaoInfo", snsKakaoInfo);
			model.addAttribute("snsNaverInfo", snsNaverInfo);
			model.addAttribute("snsFacebookInfo", snsFacebookInfo);
			model.addAttribute("snsPaycoInfo", snsPaycoInfo);
			model.addAttribute("VO", vo);
			
		}
		
		return PathUtil.getCtx()+"/mypage/member/snsConnect";
	}
	
	/**
	 * @Method : memberInfo
	 * @Date		: 2017. 8. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	회원정보 관리 페이지
	 */
	@RequestMapping("/mypage/member/memberInfo")
	public String memberInfo(MemberVO vo, HttpServletRequest request, HttpServletResponse response, Model model)throws Exception{
		String returnUrl = "";
		UserInfo userInfo = UserInfo.getUserInfo();
		vo.setMemberId(userInfo.getMemberId());
		
		SqlMap detail = memberService.getMemberDetail(vo);
		String phoneNo = (String) detail.get("phoneNo");
		detail.put("PHONE_NO", phoneNo.replaceAll("-", ""));
		
		if(userInfo.getMemberLoginType().equals("MEMBER")){
			List<SqlMap> mailList = getCodeList("MAIL_KIND");												// 메일 리스트
			model.addAttribute("mailList", mailList);
			returnUrl = PathUtil.getCtx()+"/mypage/member/memberInfo";
			
		}else{
			returnUrl = PathUtil.getCtx()+"/mypage/member/memberSnsInfo";
		}
		
		model.addAttribute("detail", detail);
		
		return returnUrl;
	}
	
	/**
	 * @Method : memberModify
	 * @Date		: 2017. 8. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	회원정보 수정
	 */
	@RequestMapping("/mypage/member/memberModify") 
	public void memberModify(MemberVO vo, HttpServletRequest request, HttpServletResponse response, Model model)throws Exception{
		memberService.memberModify(vo);
		
		MethodUtil.alertMsgUrl(request, response, "정보가 수정 되었습니다.", "/mypage/order/main.do");
	}
	
	/**
	 * @Method : memberAccount
	 * @Date		: 2017. 8. 7.
	 * @Author	:  유  준  철 
	 * @Description	:	환불계좌 관리 페이지
	 */
	@RequestMapping("/mypage/member/memberAccount")
	public String memberAccount(MemberVO vo, HttpServletRequest request, HttpServletResponse response, Model model)throws Exception{
		List<SqlMap> bankList = getCodeList("BANK_CODE");						// 은행 리스트
		SqlMap detail = memberService.getMemberAccount();				// 환불계좌 정보
		
		model.addAttribute("bankList", bankList);
		model.addAttribute("detail", detail);
		model.addAttribute("VO", vo);
		
		return PathUtil.getCtx()+"/mypage/member/memberAccount";
	}
	
	/**
	 * @Method : memberAccountSaveAjax
	 * @Date		: 2017. 8. 7.
	 * @Author	:  유  준  철 
	 * @Description	:	환불계좌 저장
	 */
	@RequestMapping("/ajax/mypage/member/memberAccountSaveAjax") 
	public @ResponseBody int memberAccountSaveAjax(MemberVO vo, HttpServletRequest request, HttpServletResponse response)throws Exception{
		int flag = memberService.memberAccountSave(vo);
		return flag;
	}
	
	/**
	 * @Method : memberAccountDeleteAjax
	 * @Date		: 2017. 8. 7.
	 * @Author	:  유  준  철 
	 * @Description	:	환불계좌 삭제
	 */
	@RequestMapping("/ajax/mypage/member/memberAccountDeleteAjax") 
	public @ResponseBody int memberAccountDeleteAjax(MemberVO vo, HttpServletRequest request, HttpServletResponse response)throws Exception{
		int flag = memberService.memberAccountDelete(vo);
		return flag;
	}
	
	/**
	 * @Method : memberWithdraw
	 * @Date		: 2017. 8. 6.
	 * @Author	:  유  준  철 
	 * @Description	:	회원 탈퇴 폼
	 */
	@RequestMapping("/mypage/member/memberWithdraw")
	public String memberWithdraw(MemberVO vo, HttpServletRequest request, HttpServletResponse response, Model model)throws Exception{
		SqlMap memberInfo = memberService.getMemberInfo(vo);									// 보유포인트, 진행중인 주문 건
		List<SqlMap> withdrawList = getCodeList("WITHDRAW_REASON");						// 탈퇴 사유 리스트
		
		model.addAttribute("memberInfo", memberInfo);
		model.addAttribute("withdrawList", withdrawList);
		
		return PathUtil.getCtx()+"/mypage/member/memberWithdraw";
	}
	
	/**
	 * @Method : memberWithdrawSaveAjax
	 * @Date		: 2017. 8. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	회원 탈퇴 저장
	 */
	@RequestMapping("/ajax/mypage/member/memberWithdrawSaveAjax") 
	public @ResponseBody int memberWithdrawSaveAjax(MemberVO vo, HttpServletRequest request, HttpServletResponse response, HttpSession session)throws Exception{
		int flag = memberService.memberWithdrawSave(vo);
		session.invalidate();		// session 종료		
		return flag;
	}
	
	/**
	 * @Method : memberShipping
	 * @Date		: 2017. 8. 7.
	 * @Author	:  유  준  철 
	 * @Description	:	배송지 관리 페이지
	 */
	@RequestMapping("/mypage/member/memberShipping")
	public String memberShipping(MemberVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session)throws Exception{
		List<SqlMap> shippingList = memberService.getMemberShippingList(vo);
		
		model.addAttribute("list", shippingList);
		
		return PathUtil.getCtx()+"/mypage/member/memberShipping";
	}
	
	/**
	 * @Method : memberShippingLayerAjax
	 * @Date		: 2017. 8. 8.
	 * @Author	:  유  준  철 
	 * @Description	:	배송지 레이어
	 */
	@RequestMapping("/ajax/mypage/member/memberShippingLayerAjax") 
	public ModelAndView memberShippingLayerAjax(MemberVO vo, HttpServletRequest request , HttpServletResponse response)throws Exception{
		ModelAndView mav = new ModelAndView();
		
		if(vo.getStatusFlag().equals("U")){
			SqlMap detail = memberService.getShippingDetail(vo);
			mav.addObject("detail", detail);
		}
		
		mav.addObject("VO", vo);
		mav.setViewName(PathUtil.getCtx()+"/mypage/member/memberShippingLayer");		
		
		return mav;
	}
	
	/**
	 * @Method : memberShippingSaveAjax
	 * @Date		: 2017. 8. 7.
	 * @Author	:  유  준  철 
	 * @Description	:	배송지 저장/수정/삭제
	 */
	@RequestMapping("/ajax/mypage/member/memberShippingSaveAjax") 
	public @ResponseBody int memberShippingSaveAjax(MemberVO vo, HttpServletRequest request, HttpServletResponse response, HttpSession session)throws Exception{
		if(vo.getStatusFlag().equals("I")){
			vo.setRegHttpUserAgent(request.getHeader("user-agent"));	
			vo.setRegIp(MethodUtil.clientIP(request));
		}else if(vo.getStatusFlag().equals("U")){
			vo.setEditHttpUserAgent(request.getHeader("user-agent"));	
			vo.setEditIp(MethodUtil.clientIP(request));
		}
		
		int flag = memberService.memberShippingSave(vo);
		
		return flag;
	}
	
	/**
	 * @Method : shippingDefalutUpdateAjax
	 * @Date		: 2017. 8. 8.
	 * @Author	:  유  준  철 
	 * @Description	:	기본 배송지 설정
	 */
	@RequestMapping("/ajax/mypage/member/shippingDefalutUpdateAjax") 
	public @ResponseBody int shippingDefalutUpdateAjax(MemberVO vo, HttpServletRequest request, HttpServletResponse response, HttpSession session)throws Exception{
		vo.setEditHttpUserAgent(request.getHeader("user-agent"));	
		vo.setEditIp(MethodUtil.clientIP(request));
		
		int flag = memberService.shippingDefalutUpdate(vo);
		
		return flag;
	}
}
