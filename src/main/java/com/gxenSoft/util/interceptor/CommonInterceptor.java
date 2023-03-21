package com.gxenSoft.util.interceptor;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gxenSoft.mall.cart.service.CartService;
import com.gxenSoft.mall.cart.vo.CartVO;
import com.gxenSoft.mall.common.service.CommonService;
import com.gxenSoft.mall.common.web.CommonMethod;
import com.gxenSoft.mall.cscenter.notice.service.NoticeService;
import com.gxenSoft.mall.etc.service.EtcService;
import com.gxenSoft.mall.event.timeSale.dao.TimeSaleDAO;
import com.gxenSoft.mall.member.vo.MemberVO;
import com.gxenSoft.mall.mypage.order.service.MypageOrderService;
import com.gxenSoft.mall.order.vo.OrderVO;
import com.gxenSoft.method.PathUtil;
import com.gxenSoft.sqlMap.SqlMap;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : FrontInterceptor
 * PACKAGE NM : com.gatsbyMall.util.interceptor
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 6. 12. 
 * HISTORY :
 
 *************************************
 */
public class CommonInterceptor extends HandlerInterceptorAdapter {
	
	static final Logger logger = LoggerFactory.getLogger(CommonInterceptor.class);
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private CartService cartService;

	@Autowired
	private EtcService etcService;

	@Autowired
	private TimeSaleDAO timeSaleDAO;
	
	@Autowired
	private MypageOrderService mypageOrderService;
	
	@Autowired
	private NoticeService noticeService;
	
	/**
	 * @Method : preHandle
	 * @Date		: 2017. 6. 12.
	 * @Author	:  유  준  철 
	 * @Description	:	Controller 호출전	
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		String memberFlag = (String)session.getAttribute("SS_MEMBER_FLAG");
		String memberId = (String)session.getAttribute("SS_MEMBER_ID");
		Integer memberIdx = (Integer)session.getAttribute("SS_MEMBER_IDX");
		String memberNm = (String)session.getAttribute("SS_MEMBER_NM");
		String levelIdx = String.valueOf(session.getAttribute("SS_LEVEL_IDX"));
		String memberLoginType = (String)session.getAttribute("SS_MEMBER_LOGIN_TYPE");
		
		if(memberFlag != null && !memberFlag.isEmpty()){
			OrderVO orderVO = new OrderVO();
			if(memberFlag.equals("Y")){													//회원
				MemberVO vo = new MemberVO();
				vo.setMemberId(memberId);
				vo.setMemberNm(memberNm);
				vo.setLevelIdx(levelIdx);
				vo.setMemberLoginType(memberLoginType);
				
				SqlMap userInfo = commonService.getUserInfo(vo);				
				userInfo.put("memberFlag", "Y");	// 회원으로 로그인
				
				if(memberLoginType != null && !memberLoginType.isEmpty()){			
					if(memberLoginType.equals("MEMBER")){
						userInfo.put("memberLoginType", "MEMBER");				// 자체 회원
					}else{
						userInfo.put("memberLoginType", "SNS");							// SNS 회원
					}
				}
				
				request.setAttribute("USERINFO", userInfo);
				
				//쿠폰목록
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("MEMBERIDX", memberIdx);
				List<SqlMap> wingCouponList = commonService.getWingCouponList(map); 
				request.setAttribute("wingCouponList", wingCouponList);
				
				// 회원일때만 여기서 처리 비회원은 로컬스토리지 때문에 ajax 처리
				// Header 장바구니
				CartVO cartVO = new CartVO();
				cartVO.setMemberIdx(memberIdx);
				cartVO.setRegIdx(memberIdx);
				List<SqlMap> cartList = cartService.getCartList(cartVO);	// 장바구니 리스트
				request.setAttribute("headerCartList", cartList);
				
				// WING - 최근 구매한 상품 1개
				orderVO.setMemberIdx(memberIdx);
				SqlMap lastestOrderedGoods = commonService.getLastestOrderedGoods(orderVO);	// 최근 구매한 상품 1개
				request.setAttribute("lastestOrderedGoods", lastestOrderedGoods);
				
				// PC, 모바일 header 공통 쿠폰, 포인트 정보
				SqlMap memberCntInfo = mypageOrderService.getMemberCntInfo(orderVO); // 쿠폰 갯수, 포인트
				request.setAttribute("mypageCouponCnt", memberCntInfo.get("couponCnt"));
				request.setAttribute("mypagePointM", memberCntInfo.get("point"));

			}else if(memberFlag.equals("N")){										//비회원
				orderVO.setMemberIdx(0);
				orderVO.setNomemberOrderCd((String)session.getAttribute("SS_NOMEMBER_ORDER_CD"));	// 비회원 로그인 주문 코드
				
				SqlMap userInfo = new SqlMap();
				userInfo.put("memberFlag", "N");	// 비회원 주문조회로 로그인
				
				request.setAttribute("USERINFO", userInfo);
			}
			
			// 모바일 header 주문건수 정보
			if("m".equals(CommonMethod.isDevice(request))){
				// 주문 상태별 건수
				SqlMap orderStatusCnt = mypageOrderService.getOrderStatusCnt(orderVO);
				int orderCntM = 0;	// 주문 접수(입금 대기) 건수 + 결제 완료 건수 + 상품 준비중 건수 + 발송 완료(배송중) 건수
				if(orderStatusCnt != null){
					orderCntM = Integer.parseInt(orderStatusCnt.get("cnt100").toString())		// 주문 접수(입금 대기) 건수 
									+ Integer.parseInt(orderStatusCnt.get("cnt200").toString())		// + 결제 완료 건수 
									+ Integer.parseInt(orderStatusCnt.get("cnt300").toString())		// + 상품 준비중 건수 
									+ Integer.parseInt(orderStatusCnt.get("cnt400").toString());	// + 발송 완료(배송중) 건수
				}
				request.setAttribute("orderCntM", orderCntM);
			}
		}
		
		// GNB 카테고리
		List<SqlMap> categoryList = commonService.getCategoryList();
		List<SqlMap> category3DepthList = commonService.getCategory3DepthList();
		
		//통합 카테고리
		List<SqlMap> totalCateList = commonService.getTotalCateList();
		
		
		// 배너 TOP 
		SqlMap bannerTop = commonService.eventBanner("BANNER_GUBUN_TOP");
		// 배너 WING 리스트
		List<SqlMap> bannerWing = commonService.getBannerWing("BANNER_GUBUN_WING");
		// 타임세일 여부
		int timeSaleCnt = timeSaleDAO.getTimeSaleListCnt();
		
		// 추천 해시태그 목록 // 
		List<SqlMap> mainHashList = etcService.getMainHashtagList(); //추천 해시태그 목록 (총 10개)
		
		SqlMap bannerHeader = commonService.getBannerHeader("BANNER_HEADER"); // 띠배너
		// GNB 해시태그 리스트
		List<SqlMap> gnbHashList = etcService.getGnbHashList();	// GNB 해시태그 리스트 
		List<SqlMap> footerNoticeList = commonService.getNoticeList();	// PC 푸터 공지사항 리스트
		SqlMap floatPopup = commonService.getFloatPopup("POPUP_GUBUN40"); // PC 플로팅 팝업
		
		List<SqlMap> headerRollingBannerList = commonService.getMainBannerList("HEADERROLLING_BANNER"); //헤더 롤링배너 
		
		if("m".equals(PathUtil.getDevice())){
			SqlMap footerNotice = noticeService.getFooterNotice(); // 모바일 푸터 공지사항
			
			request.setAttribute("footerNotice", footerNotice);
		}
		
		request.setAttribute("mainHashList", mainHashList);
		request.setAttribute("categoryList", categoryList);
		request.setAttribute("category3DepthList", category3DepthList);
		request.setAttribute("totalCateList", totalCateList);
		request.setAttribute("bannerTop", bannerTop);
		request.setAttribute("bannerWing", bannerWing);
		request.setAttribute("timeSaleCnt", timeSaleCnt);
		request.setAttribute("bannerHeader", bannerHeader);
		request.setAttribute("gnbHashList", gnbHashList);
		request.setAttribute("footerNoticeList", footerNoticeList);
		request.setAttribute("floatPopup", floatPopup);
		
		request.setAttribute("headerRollingBannerList", headerRollingBannerList);
		
		// 로그인 여부
		request.setAttribute("IS_LOGIN", CommonMethod.isLogin(request));
		
		// 디바이스 체크
		request.setAttribute("DEVICE", CommonMethod.isDevice(request));
		
		return true;
	}

}
