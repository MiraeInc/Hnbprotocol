package com.gxenSoft.util.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.mypage.order.service.MypageOrderService;
import com.gxenSoft.mall.order.vo.OrderVO;
import com.gxenSoft.message.SpringMessage;
import com.gxenSoft.method.MethodUtil;
import com.gxenSoft.sqlMap.SqlMap;
 

/**
   *************************************
   * PROJECT   : GatsbyMall
   * PROGRAM ID  : DefaultInterceptor
   * PACKAGE NM : com.gxenSoft.util.interceptor
   * AUTHOR	 : 김 민 수
   * CREATED DATE  : 2017. 6. 20. 
   * HISTORY :   
   *
   *************************************
 **/	
public class DefaultInterceptor extends HandlerInterceptorAdapter {
	
	static final Logger logger = LoggerFactory.getLogger(DefaultInterceptor.class);
	
	@Autowired
	private MypageOrderService mypageOrderService;
		
	/**
	 * @Method : preHandle
	 * @Date		: 2017. 7. 26.
	 * @Author	:  유  준  철 
	 * @Description	:	Controller 호출전 (PC)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		Integer memberIdx = (Integer)session.getAttribute("SS_MEMBER_IDX");											// 회원 일련번호
		String memberId = (String)session.getAttribute("SS_MEMBER_ID");													// 회원 아이디
		String myMemberFlag = (String)session.getAttribute("SS_MY_MEMBER_FLAG");							// 마이페이지 비밀번호 체크  
		String memberFlag = (String)session.getAttribute("SS_MEMBER_FLAG");										// 회원 Y, 비회원 N
		
		if(memberFlag != null){	
			if(memberFlag.equals("Y")){											// 회원
				OrderVO orderVO = new OrderVO();
				orderVO.setMemberIdx(memberIdx);
				// 마이페이지 헤더
				SqlMap memberCntInfo = mypageOrderService.getMemberCntInfo(orderVO); // 쿠폰 갯수, 포인트, 상품 후기 건수, 이 달의 샘플 신청 건수
				request.setAttribute("mypageCouponCnt", memberCntInfo.get("couponCnt"));
				request.setAttribute("mypagePoint", memberCntInfo.get("point"));
				request.setAttribute("mypageReviewCnt", memberCntInfo.get("reviewCnt"));
				request.setAttribute("mypageSampleCnt", memberCntInfo.get("sampleCnt"));
				request.setAttribute("sumOrderPrice", memberCntInfo.get("sumOrderPrice"));
				
				int nextLevelPrice = 0;
				if (UserInfo.getUserInfo().getLevelIdx().equals("1")) {
					nextLevelPrice = 30000 - Double.valueOf(memberCntInfo.get("sumOrderPrice").toString()).intValue();
				} else if (UserInfo.getUserInfo().getLevelIdx().equals("2")) {
					nextLevelPrice = 50000 - Double.valueOf(memberCntInfo.get("sumOrderPrice").toString()).intValue();
				}else if (UserInfo.getUserInfo().getLevelIdx().equals("3")) {
					nextLevelPrice = 50000 - Double.valueOf(memberCntInfo.get("sumOrderPrice").toString()).intValue();
				}
				
				if (nextLevelPrice < 0) {
					nextLevelPrice = 0;
				}					

				request.setAttribute("sumOrderPrice", Double.valueOf(memberCntInfo.get("sumOrderPrice").toString()).intValue());
				request.setAttribute("nextLevelPrice", nextLevelPrice);
				request.setAttribute("startYear", memberCntInfo.get("startYear"));
				request.setAttribute("startMonth", memberCntInfo.get("startMonth"));
				request.setAttribute("endYear", memberCntInfo.get("endYear"));
				request.setAttribute("endMonth", memberCntInfo.get("endMonth"));
				request.setAttribute("currentMonth", memberCntInfo.get("currentMonth"));
				
				// 마이페이지 자체회원, SNS 회원 체크
				String path = request.getRequestURI().substring(request.getContextPath().length());
				String [] splitParam = path.split("/");
				String memberPath =  splitParam[2];
				String memberShippingPath = "";
				
				if (!memberPath.equals("pwdCheckForm.do") && !memberPath.equals("pwdCheck.do")){
					memberShippingPath =  splitParam[3];
				}
				
				if(memberId !=null && !memberId.isEmpty()){
					if(memberShippingPath.equals("memberShipping.do")){
						return true;
					}
					 
					if(memberPath.equals("member")){
						if(myMemberFlag !=null && !myMemberFlag.isEmpty()){
							return true;
						}else{
							MethodUtil.redirectUrl(request, response, "/mypage/pwdCheckForm.do");
						}
					}else{
						return true;
					}
				}
			}else{		// 비회원
				String mypagePath = request.getRequestURI().substring(request.getContextPath().length());
				String [] splitParam = mypagePath.split("/");
				String noMemberPath =  splitParam[2];
				
				if(!noMemberPath.equals("order") && !noMemberPath.equals("inquiry")){			// 비회원시 주문관리, 1:1문의만 접근
					MethodUtil.alertMsgUrl(request, response, SpringMessage.getMessage("common.util011"), "/login/loginPage.do");
					return false;
				}
			}
		}else{
			if(request.getRequestURI().contains("mypage")){	 // 이전 페이지가 마이페이지 일 경우엔 refererYn=Y
				MethodUtil.alertMsgUrl(request, response, SpringMessage.getMessage("common.util011"), "/login/loginPage.do?refererYn=Y");
			}else{
				MethodUtil.alertMsgUrl(request, response, SpringMessage.getMessage("common.util011"), "/login/loginPage.do");
			}
			
			return false;
		}
		return true;
	}
}
