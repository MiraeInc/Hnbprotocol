package com.gxenSoft.mall.common.web;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;
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

import com.gxenSoft.mall.common.service.CommonService;
import com.gxenSoft.mall.common.vo.CommonVO;
import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.member.service.MemberService;
import com.gxenSoft.mall.member.vo.MemberVO;
import com.gxenSoft.mall.order.vo.OrderVO;
import com.gxenSoft.mall.sns.service.SnsService;
import com.gxenSoft.message.SpringMessage;
import com.gxenSoft.method.ConvertUtil;
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
 * PROGRAM ID  : LoginController
 * PACKAGE NM : com.gatsbyMall.common.web
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 6. 12. 
 * HISTORY :
 
 *************************************
 */
@Controller
public class LoginController extends CommonMethod{
	
	static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	SnsService snsService;
	
	/**
	 * @Method : loginPage
	 * @Date		: 2017. 6. 12.
	 * @Author	:  유  준  철 
	 * @Description	:	로그인 페이지
	 */
	@RequestMapping("/login/loginPage")
	public String loginPage(MemberVO vo, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		boolean isLogin = CommonMethod.isLogin(request);
		String returnPage = "";
		
		if(isLogin == true){
			returnPage = "redirect:/main.do";
		}else{
			UserInfo userInfo = UserInfo.getUserInfo();										
			
			if(ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){								
				String cookieId = "";
				Cookie[] cookie = request.getCookies();
				
				if(cookie !=null){			
					for(int i=0; i<cookie.length; i++){
						if(cookie[i].getName().trim().equals("cookieMemberId")){
							cookieId = cookie[i].getValue();
						}
					}
				}
				
				if(vo.getRefererYn().equals("Y")){
					if(vo.getRefererUrl().isEmpty()){
						vo.setRefererUrl(request.getHeader("referer"));
					}
				}
				
				model.addAttribute("cookieMemberId", cookieId);
				returnPage = PathUtil.getCtx()+"/login/loginPage";
			}else{
				if(vo.getRefererYn().equals("Y")){
					returnPage = "redirect:"+PathUtil.getCtx()+request.getHeader("referer");							//이전 페이지 호출
				}else{
					returnPage = PathUtil.getCtx()+"/main";
				}
			}
			
			session.setAttribute("snsMode","login");
			// SNS 
			String naverAuthUrl = NaverLoginBO.getAuthorizationUrl(session);				// 네이버
			String kakaoAuthUrl = KakaoLoginBO.getAuthorizationUrl(session);				// 카카오
			String facebookAuthUrl = FacebookLoginBO.getAuthorizationUrl(session);		// 페이스북
			String paycoAuthUrl = PaycoLoginBO.getAuthorizationUrl(session);			// 페이코
			String wonderAuthUrl = WonderLoginBO.getAuthorizationUrl(session);			// 원더로그인
			
			model.addAttribute("naverAuthUrl", naverAuthUrl);
			model.addAttribute("kakaoAuthUrl", kakaoAuthUrl);
			model.addAttribute("facebookAuthUrl", facebookAuthUrl);
			model.addAttribute("paycoAuthUrl", paycoAuthUrl);
			model.addAttribute("wonderAuthUrl", wonderAuthUrl);
			model.addAttribute("VO", vo);
		}
		
		return returnPage;
	}
	
	/**
	 * @Method : loginCheck
	 * @Date: 2017. 6. 21.
	 * @Author :  유  준  철
	 * @Description	:	로그인 체크
	 */
	@RequestMapping("/login/loginCheck")
	public void loginCheck(MemberVO vo, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		
		//  flag 값  0 :  아이디 미존재,	  1 : 아이디 OR 비밀번호 불일치,  2 : 6개월 비밀번호 변경,  3 : 휴면계정,  4 : 로그인 성공
		int flag = commonService.loginCheck(vo);					
		
		if(flag <1){
			if(vo.getFromOrderFlag().equals("Y")){	// 바로 구매에서 온 거면
				if(vo.getOrderGoodsInfoListStr().contains("[{")){	// 인코딩이 안 됐으면 (여러번 호출되기 때문에 인코딩은 한번만 해야 함)
					vo.setOrderGoodsInfoListStr(URLEncoder.encode(vo.getOrderGoodsInfoListStr(),"UTF-8"));	// JSON 에 " 가 포함돼서 인코딩시킴
				}
				vo.setFromOrderFlag("Y");
				MethodUtil.alertMsgRedirectPostUrl(request, response, SpringMessage.getMessage("common.login002"), PathUtil.getCtx()+"/login/loginPage.do", vo);
			}else{
				if(vo.getRefererUrl().equals("")){
					MethodUtil.alertMsgUrl(request, response, SpringMessage.getMessage("common.login002"), "/login/loginPage.do");
				}else{
					MethodUtil.alertMsgUrl(request, response, SpringMessage.getMessage("common.login001"), "/login/loginPage.do?refererYn=Y&refererUrl="+URLEncoder.encode(vo.getRefererUrl(),"UTF-8"));
				}
			}
		}else if(flag ==1){
			if(vo.getFromOrderFlag().equals("Y")){	// 바로 구매에서 온 거면
				if(vo.getOrderGoodsInfoListStr().contains("[{")){	// 인코딩이 안 됐으면 (여러번 호출되기 때문에 인코딩은 한번만 해야 함)
					vo.setOrderGoodsInfoListStr(URLEncoder.encode(vo.getOrderGoodsInfoListStr(),"UTF-8"));	// JSON 에 " 가 포함돼서 인코딩시킴
				}
				vo.setFromOrderFlag("Y");
				MethodUtil.alertMsgRedirectPostUrl(request, response, SpringMessage.getMessage("common.login002"), PathUtil.getCtx()+"/login/loginPage.do", vo);
			}else{
				if(vo.getRefererUrl().equals("")){
					MethodUtil.alertMsgUrl(request, response, SpringMessage.getMessage("common.login002"), "/login/loginPage.do");
				}else{
					MethodUtil.alertMsgUrl(request, response, SpringMessage.getMessage("common.login001"), "/login/loginPage.do?refererYn=Y&refererUrl="+URLEncoder.encode(vo.getRefererUrl(),"UTF-8"));
				}
			}
		}else if(flag ==2){
			MethodUtil.redirectUrl(request, response, "/login/changePwdForm.do?memberId="+vo.getMemberId());
		}else if(flag ==3){
			MethodUtil.redirectUrl(request, response, "/login/dormancyForm.do?memberId="+vo.getMemberId());
		}else if(flag ==4){
			if(vo.getFromOrderFlag().equals("Y")){
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("orderGoodsInfoListStr", vo.getOrderGoodsInfoListStr());
				MethodUtil.redirectPostUrl(request, response, PathUtil.getCtx()+"/order/cartOrderProc.do", map);
			}else{
				if(vo.getRefererUrl().equals("")){
					MethodUtil.redirectUrl(request, response, "/main.do");
				}else{
					MethodUtil.redirectRefererUrl(response, vo.getRefererUrl());
				}
			}
		}		
	}	 
	
	/**
	 * @Method : noLoginCheck
	 * @Date: 2017. 6. 22.
	 * @Author :  김  민  수
	 * @Description	:	비회원 로그인 체크
	 * @HISTORY :
	 *
	 */
	@RequestMapping("/login/noLoginCheck")
	public void noLoginCheck(OrderVO vo, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		
		int flag = commonService.noLoginCheck(vo);					//  flag :  0 :  아이디 미존재  1:  주문번호/이름/휴대폰번호 불일치  2: 로그인 성공
		
		if(flag <1){
			MethodUtil.alertMsgUrl(request, response, SpringMessage.getMessage("common.login001"), "/login/loginPage.do?memberFlag=N" );
		}else if(flag ==2){
			MethodUtil.redirectUrl(request, response, "/mypage/order/main.do");
		}
	}
	
	/**
	 * @Method : findIdPwd
	 * @Date: 2017. 6. 21.
	 * @Author :  김  민  수
	 * @Description	:	아이디 비밀번호 찾기
	 * @HISTORY :
	 *
	 */
	@RequestMapping("/login/findIdPwd")
	public String findIdPwd(MemberVO vo, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		
		List<SqlMap> mailList = getCodeList("MAIL_KIND");				//메일 리스트
		
		model.addAttribute("VO", vo);
		model.addAttribute("mailList", mailList);
		return PathUtil.getCtx()+"/login/findIdPwd";
	}
	
	
	/**
	 * @Method : goodsCdCheckAjax
	 * @Date: 2017. 6. 22.
	 * @Author :  김  민  수
	 * @Description	:	아이디 찾기 결과
	 * @HISTORY :
	 *
	 */
	@RequestMapping("/ajax/login/findMemberId")
	public @ResponseBody SqlMap findMemberIdAjax(MemberVO vo ,  HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{
		
		SqlMap info = commonService.findMemberId(vo);
		
		return info;
	}
	
	/**
	 * @Method : findMemberPwdAjax
	 * @Date: 2017. 6. 22.
	 * @Author :  김  민  수
	 * @Description	:	임시 비밀번호발송 결과
	 * @HISTORY :
	 *
	 */
	@RequestMapping("/ajax/login/findMemberPwd")
	public @ResponseBody SqlMap findMemberPwdAjax(MemberVO vo ,  HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{
		
		SqlMap info = commonService.findMemberPwd(vo);
		
		return info;
	}
	
	/**
	 * @Method : logout
	 * @Date		: 2017. 6. 14.
	 * @Author	:  유  준  철 
	 * @Description	:	로그아웃
	 */
	@RequestMapping("/logout")
	public void  logout(CommonVO vo,HttpServletRequest request, HttpServletResponse response, HttpSession session)throws Exception{
		session.invalidate();		// session 종료		
		MethodUtil.alertMsgUrl(request, response, SpringMessage.getMessage("common.util010"), "/main.do" );
	}
	
	/**
	 * @Method : dormancyForm
	 * @Date		: 2017. 6. 26.
	 * @Author	:  유  준  철 
	 * @Description	:	휴면 계정 
	 */
	@RequestMapping("/login/dormancyForm")
	public String dormancyForm(MemberVO vo, HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{
		List<SqlMap> termsGubun = getCodeList("TERMS_GUBUN");												// 약관구분 리스트
		List<SqlMap> termsList = memberService.getTermsList();															// 약관/개인정보 리스트
		SqlMap detail = memberService.getMemberDetail(vo);
		
		model.addAttribute("termsList",termsList);
		model.addAttribute("termsGubun",termsGubun);
		model.addAttribute("detail", detail);
		
		return PathUtil.getCtx()+"/login/dormancyForm";
	}
	
	/**
	 * @Method : changePwdForm
	 * @Date		: 2017. 6. 26.
	 * @Author	:  유  준  철 
	 * @Description	:	6개월 비밀번호 변경
	 */
	@RequestMapping("/login/changePwdForm")
	public String changePwdForm(MemberVO vo, HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{
		
		SqlMap detail = memberService.getMemberDetail(vo);
		
		model.addAttribute("detail", detail);
		
		return PathUtil.getCtx()+"/login/changePwdForm";
	}
	
	/**
	 * @Method : dormancySaveAjax
	 * @Date		: 2017. 6. 26.
	 * @Author	:  유  준  철 
	 * @Description	:	휴면 해제 
	 */
	@RequestMapping("/ajax/login/dormancySaveAjax")
	public @ResponseBody int dormancySaveAjax(MemberVO vo, HttpServletRequest request , HttpServletResponse response)throws Exception{
		int flag = 0;
		
		try {
			 flag = commonService.dormancySave(vo);
			 
			 if(flag > 0){
				 if(vo.getJoinType().equals("0")){			// 일반 회원 로그인
					 commonService.loginCheck(vo);						
				 }else{
					 vo.setSnsMode("login");
					 snsService.snsLoginCheck(vo);
				 }
			 }
		} catch (Exception e) {
			response.sendError(1000);
		}
		
		return flag;
	}
	
	/**
	 * @Method : nextChangePwdAjax
	 * @Date		: 2017. 6. 27.
	 * @Author	:  유  준  철 
	 * @Description	:	비밀번호 다음에 변경
	 */
	@RequestMapping("/ajax/login/nextChangePwdAjax")
	public @ResponseBody int nextChangePwdAjax(MemberVO vo, HttpServletRequest request , HttpServletResponse response)throws Exception{
		int flag = 0;
		
		try {
			 flag = commonService.nextChangePwd(vo);
			 
			 if(flag > 0){
				 commonService.loginCheck(vo);			
			 }
			 
		} catch (Exception e) {
			response.sendError(1000);
		}
		
		return flag;
	}
	
	/**
	 * @Method : checkPwdAjax
	 * @Date		: 2017. 6. 27.
	 * @Author	:  유  준  철 
	 * @Description	:	비밀번호 체크
	 */
	@RequestMapping("/ajax/login/checkPwdAjax")
	public @ResponseBody int checkPwdAjax(MemberVO vo, HttpServletRequest request , HttpServletResponse response)throws Exception{
		int flag = 0;
		
		try {
			flag = commonService.checkPwd(vo);			
		} catch (Exception e) {
			response.sendError(1000);
		}
		
		return flag;
	}
	
	/**
	 * @Method : changePwdAjax
	 * @Date		: 2017. 6. 27.
	 * @Author	:  유  준  철 
	 * @Description	:	비밀번호 변경
	 */
	@RequestMapping("/ajax/login/changePwdAjax")
	public @ResponseBody int changePwdAjax(MemberVO vo, HttpServletRequest request , HttpServletResponse response)throws Exception{
		int flag = 0;
		
		try {
			flag = commonService.changePwd(vo);			
		} catch (Exception e) {
			response.sendError(1000);
		}
		
		return flag;
	}
}
