package com.gxenSoft.mall.sns.web;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.google.gson.Gson;
import com.gxenSoft.mall.member.vo.MemberVO;
import com.gxenSoft.mall.sns.api.kakao.KakaoLoginBO;
import com.gxenSoft.mall.sns.api.kakao.OAuthToken;
import com.gxenSoft.mall.sns.service.SnsService;
import com.gxenSoft.message.SpringMessage;
import com.gxenSoft.method.MethodUtil;
import com.gxenSoft.snsApi.FacebookLoginBO;
import com.gxenSoft.snsApi.NaverLoginBO;
import com.gxenSoft.util.pathUtil.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
public class SnsLoginController {
	
	static final Logger logger = LoggerFactory.getLogger(SnsLoginController.class);

	@Autowired
	private SnsService snsService;
	
	/**
	 * @Method : snsLoginCheck
	 * @Date		: 2017. 6. 21.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS 로그인 처리
	 */
	@RequestMapping("/login/snsLoginCheck")
	public void snsLoginCheck(MemberVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session)throws Exception{
		if(vo.getSnsMode().equals("join")){
			if(snsService.snsConnectCheck(vo) > 0){
				MethodUtil.alertMsgUrl(request, response, SpringMessage.getMessage("common.login004"), "/member/joinStep01.do" );
			}else{
				if(snsService.snsReJoinCheck(vo) > 0){					// SNS 재가입 확인
					MethodUtil.alertMsgUrl(request, response, "탈퇴한 회원은 재가입할 수 없습니다.", "/member/joinStep01.do");
				}
			}
		}else if(vo.getSnsMode().equals("mypage")){
			if(snsService.snsJoinCheck(vo) > 0){						// SNS 가입확인 
				MethodUtil.alertMsgUrl(request, response, SpringMessage.getMessage("common.login005"), "/mypage/member/snsConnect.do" );
			}else{
				if(snsService.snsConnectCheck(vo) > 0){
					snsService.snsConnectDelete(vo);			// SNS 연동 삭제
				}else{
					snsService.snsConnectSave(vo);				// SNS 연동 저장
				}
				MethodUtil.redirectUrl(request, response, "/mypage/member/snsConnect.do");
			}
		}else if(vo.getSnsMode().equals("login")){
			if(snsService.snsJoinCheck(vo) > 0){
				// SNS 가입 로그인
				snsService.snsLoginCheck(vo);
			}else{
				if(snsService.snsConnectCheck(vo) > 0){
					// SNS 연동 로그인
					snsService.snsConnectLogin(vo);
				}else{
					if(snsService.snsReJoinCheck(vo) > 0){					// SNS 재가입 확인
						MethodUtil.alertMsgUrl(request, response, "탈퇴한 회원은 재가입할 수 없습니다.", "/login/loginPage.do");
					}
				}
			}
			MethodUtil.redirectUrl(request, response, "/main.do");
		}
	}
	
	/**
	 * @Method : naverCallback
	 * @Date		: 2017. 6. 20.
	 * @Author	:  유  준  철 
	 * @Description	: 네이버 로그인 콜백	
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/login/naverCallback")
	public String naverCallback(MemberVO vo, @RequestParam String code, @RequestParam String state, HttpSession session, Model model) throws Exception {
		String snsAuth = (String)session.getAttribute("SS_MY_SNS_AUTH");						// 마이페이지 SNS 계정 인증 세션 값
		String snsReferer = (String)session.getAttribute("SS_SNS_REFERER");					// SNS 이전페이지 URL
		String snsMode = (String) session.getAttribute("snsMode");
		String snsCd = "";
		
		OAuth2AccessToken oauthToken = NaverLoginBO.getAccessToken(session, code, state);
		String apiResult = NaverLoginBO.getUserProfile(oauthToken);
		
		Map<String, Object> resultMap = null;
		Gson gson = new Gson();
		resultMap = gson.fromJson(apiResult, HashMap.class);
		resultMap = (Map<String, Object>) resultMap.get("response");
		
		snsCd = (String) resultMap.get("id");
		
		resultMap.put("snsType", "SNS_TYPE20");
		resultMap.put("snsMode", snsMode);
		resultMap.put("snsCd", snsCd);
		
		vo.setSnsCd(snsCd);
		vo.setSnsMode(snsMode);
		
		int snsJoinCheck = snsService.snsJoinCheck(vo);							// SNS 가입 체크 
		int snsConnectCheck = snsService.snsConnectCheck(vo);			// SNS 연동 체크
		int snsReJoinCheck = snsService.snsReJoinCheck(vo);					// SNS 재가입 체크
		
		// 마이페이지 SNS 계정 인증
		if(snsAuth != null){
			if(snsAuth.equals("Y")){
				model.addAttribute("snsAuth", "Y");
				model.addAttribute("snsReferer", snsReferer);
			}
		}
		
		model.addAttribute("resultMap", resultMap);
		model.addAttribute("snsJoinCheck", snsJoinCheck);
		model.addAttribute("snsConnectCheck", snsConnectCheck);
		model.addAttribute("snsReJoinCheck", snsReJoinCheck);
		
		return PathUtil.getCtx()+"/member/snsCallback";
	}
	
	/**
	 * @Method : facebookCallback
	 * @Date		: 2017. 6. 22.
	 * @Author	:  유  준  철 
	 * @Description	:	페이스북 로그인 콜백
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/login/facebookCallback")
	public String facebookCallback(MemberVO vo, @RequestParam String code, @RequestParam String state, HttpSession session, Model model) throws Exception {
		String snsCd = "";
		
		// ex) snsMode + path + snsAuth + snsReferer =  login@w@Y@http://~~
		String splitState = state;
		String [] splitParam = splitState.split("@", 4);
		String snsMode =  splitParam[0];
		String path =  splitParam[1];
		String snsAuth =  splitParam[2];				// 마이페이지 SNS 계정 인증 값
		String snsReferer =  splitParam[3];			//	SNS 이전페이지 URL
		
		OAuth2AccessToken oauthToken = FacebookLoginBO.getAccessToken(code);
		String apiResult = FacebookLoginBO.getUserProfile(oauthToken);
		
		Map<String, Object> resultMap = null;
		Gson gson = new Gson();
		resultMap = gson.fromJson(apiResult, HashMap.class);
		
		snsCd = (String) resultMap.get("id");
		
		resultMap.put("snsType", "SNS_TYPE30");
		resultMap.put("snsMode", snsMode);
		resultMap.put("snsCd", snsCd);
		
		vo.setSnsCd(snsCd);
		vo.setSnsMode(snsMode);
		
		int snsJoinCheck = snsService.snsJoinCheck(vo);							// SNS 가입 체크 
		int snsConnectCheck = snsService.snsConnectCheck(vo);			// SNS 연동 체크
		
		// 마이페이지 SNS 계정 인증
		if(snsAuth != null){
			if(snsAuth.equals("Y")){
				model.addAttribute("snsAuth", "Y");
				model.addAttribute("snsReferer", snsReferer);
			}
		}
		
		model.addAttribute("resultMap", resultMap);
		model.addAttribute("snsJoinCheck", snsJoinCheck);
		model.addAttribute("snsConnectCheck", snsConnectCheck);
		
		return "/"+path+"/member/snsCallback";
	}
	
	/**
	 * @Method : kakaoCallback
	 * @Date		: 2017. 6. 26.
	 * @Author	:  유  준  철 
	 * @Description	:	카카오 로그인 콜백
	 */
	@RequestMapping(value="/login/kakaoCallback", produces="application/json")
	public String kakaoCallback(MemberVO vo, @RequestParam String code, @RequestParam String state, Model model, HttpSession session) throws Exception {
		// ex) snsMode + path + snsAuth + snsReferer =  login@w@Y@http://~~
		String splitState = state;
		String [] splitParam = splitState.split("@", 4);
		String snsMode =  splitParam[0];
		String path =  splitParam[1];
		String snsAuth =  splitParam[2];				// 마이페이지 SNS 계정 인증 값
		String snsReferer =  splitParam[3];			//	SNS 이전페이지 URL


		OAuthToken oAuthToken = KakaoLoginBO.getOAuthToken(code);
		Map userProfile = KakaoLoginBO.getUserProfile(oAuthToken.getAccessToken());

		Object kakaoAccount = userProfile.get("kakao_account");

		String snsCd = String.valueOf(userProfile.get("id"));
		String email = getEmail(kakaoAccount);
		String nickname = getNickName(kakaoAccount);

		vo.setSnsCd(snsCd);
		vo.setSnsMode(snsMode);

		int snsJoinCheck = snsService.snsJoinCheck(vo);							// SNS 가입 체크
		int snsConnectCheck = snsService.snsConnectCheck(vo);			// SNS 연동 체크
		int snsReJoinCheck = snsService.snsReJoinCheck(vo);					// SNS 재가입 체크

		// 마이페이지 SNS 계정 인증
		if(snsAuth != null){
			if(snsAuth.equals("Y")){
				model.addAttribute("snsAuth", "Y");
				model.addAttribute("snsReferer", snsReferer);
			}
		}
		
		HashMap<String, Object> resultMap = new HashMap<>();  
		resultMap.put("snsCd", snsCd);
		resultMap.put("email", email);
		resultMap.put("name", nickname);
		resultMap.put("snsType", "SNS_TYPE10");
		resultMap.put("snsMode", snsMode);
		
		model.addAttribute("resultMap", resultMap);
		model.addAttribute("snsJoinCheck", snsJoinCheck);
		model.addAttribute("snsConnectCheck", snsConnectCheck);
		model.addAttribute("snsReJoinCheck", snsReJoinCheck);
		
		return "/"+path+"/member/snsCallback";
	}

	private String getNickName(Object kakaoAccount) {
		return String.valueOf(
				((LinkedHashMap) ((LinkedHashMap) kakaoAccount).getOrDefault("profile", null))
						.get("nickname")
		);
	}

	private String getEmail(Object kakaoAccount) {
		return String.valueOf(((LinkedHashMap) kakaoAccount).getOrDefault("email", null));
	}

	/**
	 * @Method : paycoCallback
	 * @Date		: 2017. 10. 31.
	 * @Author	:  임  재  형
	 * @Description	: 페이코 로그인 콜백	
	 */
	/*
	@RequestMapping(value="/login/paycoCallback", produces="application/json")
	public String paycoCallback(MemberVO vo, @RequestParam String code, @RequestParam String state, HttpSession session, Model model) throws Exception {
		String oauthToken = PaycoLoginBO.getAccessToken(code);
		if(oauthToken != null){
			session.setAttribute("paycoToken", oauthToken);
		}
		// ex) snsMode + path + snsAuth + snsReferer =  login@w@Y@http://~~
		String splitState = state;
		String [] splitParam = splitState.split("@", 4);
		String snsMode =  splitParam[0];
		String path =  splitParam[1];
		String snsAuth =  splitParam[2];				// 마이페이지 SNS 계정 인증 값
		String snsReferer =  splitParam[3];			//	SNS 이전페이지 URL
		JsonNode apiResult = PaycoLoginBO.getUserProfile(oauthToken);
		
		String idNo = apiResult.get("memberProfile").get("idNo").getTextValue(); // 회원 UUID
		String id = apiResult.get("memberProfile").get("id").getTextValue(); // 이메일 아이디 (값이 없으면 모바일 아이디만 있는 경우)
		String mobileId = apiResult.get("memberProfile").get("mobileId").getTextValue(); // 모바일 아이디
		String maskingId = apiResult.get("memberProfile").get("maskingId").getTextValue(); // 마스킹 된 이메일 아이디
		String maskingMobileId = apiResult.get("memberProfile").get("maskingMobileId").getTextValue(); // 마스킹 된 모바일 아이디
		String name = apiResult.get("memberProfile").get("name").getTextValue(); // 이름
		String sexCode = apiResult.get("memberProfile").get("sexCode").getTextValue(); // 성별 (F:여자, M:남자)
		String birthdayMMdd = apiResult.get("memberProfile").get("birthdayMMdd").getTextValue(); // 생일(MMDD)
		String ageGroup = apiResult.get("memberProfile").get("ageGroup").getTextValue(); // 연령대 (0-10세 미만, 10, 20, 30, ....)
		
		vo.setSnsCd(idNo);
		vo.setSnsMode(snsMode);
		vo.setGender(sexCode);
		
		int snsJoinCheck = snsService.snsJoinCheck(vo);							// SNS 가입 체크 
		int snsConnectCheck = snsService.snsConnectCheck(vo);			// SNS 연동 체크
		int snsReJoinCheck = snsService.snsReJoinCheck(vo);					// SNS 재가입 체크
		
		// 마이페이지 SNS 계정 인증
		if(snsAuth != null){
			if(snsAuth.equals("Y")){
				model.addAttribute("snsAuth", "Y");
				model.addAttribute("snsReferer", snsReferer);
			}
		}
		
		HashMap<String, Object> resultMap = new HashMap<>();  
		resultMap.put("snsCd", idNo);
		resultMap.put("email", id);
		resultMap.put("name", name);
		resultMap.put("gender", sexCode);
		resultMap.put("snsType", "SNS_TYPE40");
		resultMap.put("snsMode", snsMode);
		
		model.addAttribute("resultMap", resultMap);
		model.addAttribute("snsJoinCheck", snsJoinCheck);
		model.addAttribute("snsConnectCheck", snsConnectCheck);
		model.addAttribute("snsReJoinCheck", snsReJoinCheck);
		
		return "/"+path+"/member/snsCallback";
	}
	*/

	/**
	 * @Method : wonderLoginCallback
	 * @Date		: 2018. 10. 31.
	 * @Author	:  강 병 철
	 * @Description	: 원더로그인 콜백	
	 */
	/*
	@RequestMapping(value="/login/wonderLoginCallback", produces="application/json")
	public String wonderLoginCallback(MemberVO vo, @RequestParam String code, @RequestParam String state, HttpSession session, Model model) throws Exception {
		
		String snsAuth = (String)session.getAttribute("SS_MY_SNS_AUTH");						// 마이페이지 SNS 계정 인증 세션 값
		String snsReferer = (String)session.getAttribute("SS_SNS_REFERER");					// SNS 이전페이지 URL
		String snsMode = (String) session.getAttribute("snsMode");
		String snsCd = "";

		String oauthToken = WonderLoginBO.getAccessToken(code);
		JsonNode apiResult = WonderLoginBO.getUserProfile(oauthToken);
		
		String idNo = apiResult.get("mid").getTextValue(); // 회원 UUID
		String id = apiResult.get("email").getTextValue(); // 이메일
		String name = apiResult.get("name").getTextValue(); // 이름
		String gender = apiResult.get("gender").getTextValue(); // 성별(1:남, 2:여, 0:선택안함)
		String birth = apiResult.get("birth").getTextValue(); // 생년월일
		String mobile = apiResult.get("mobile").getTextValue();
		
		String sexCode = ""; //(F:여자, M:남자)
		if (gender.equals("1") ) {
			sexCode = "M";
		} else if (gender.equals("2") ) {
			sexCode = "F";
		}
		
		vo.setSnsCd(idNo);
		vo.setSnsMode(snsMode);
		vo.setGender(sexCode);
		
		int snsJoinCheck = snsService.snsJoinCheck(vo);							// SNS 가입 체크 
		int snsConnectCheck = snsService.snsConnectCheck(vo);			// SNS 연동 체크
		int snsReJoinCheck = snsService.snsReJoinCheck(vo);					// SNS 재가입 체크
		
		// 마이페이지 SNS 계정 인증
		if(snsAuth != null){
			if(snsAuth.equals("Y")){
				model.addAttribute("snsAuth", "Y");
				model.addAttribute("snsReferer", snsReferer);
			}
		}
		
		HashMap<String, Object> resultMap = new HashMap<>();  
		resultMap.put("snsCd", idNo);
		resultMap.put("email", id);
		resultMap.put("name", name);
		resultMap.put("gender", sexCode);
		resultMap.put("snsType", "SNS_TYPE50");
		resultMap.put("snsMode", snsMode);

		model.addAttribute("resultMap", resultMap);
		model.addAttribute("snsJoinCheck", snsJoinCheck);
		model.addAttribute("snsConnectCheck", snsConnectCheck);
		model.addAttribute("snsReJoinCheck", snsReJoinCheck);

		return PathUtil.getCtx()+"/member/snsCallback";
	}*/
}
