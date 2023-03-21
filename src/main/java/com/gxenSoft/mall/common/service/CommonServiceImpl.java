package com.gxenSoft.mall.common.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gxenSoft.fileUtil.FileVO;
import com.gxenSoft.mall.common.dao.CommonDAO;
import com.gxenSoft.mall.common.vo.CommonVO;
import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.member.dao.MemberDAO;
import com.gxenSoft.mall.member.vo.MemberVO;
import com.gxenSoft.mall.order.vo.OrderVO;
import com.gxenSoft.mall.product.vo.SchProductVO;
import com.gxenSoft.method.MethodUtil;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.mailUtil.MailUtil;
import com.gxenSoft.util.page.SearchVO;
import com.gxenSoft.util.pathUtil.PathUtil;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : CommonServiceImpl
 * PACKAGE NM : com.gatsbyMall.common.service
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 6. 14. 
 * HISTORY :
 
 *************************************
 */ 
@Service("commonService")
public class CommonServiceImpl implements CommonService {
	
	static final Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);
	
	@Autowired
	private CommonDAO commonDAO;
	@Autowired
	private MemberDAO memberDAO;
	
	/**
	 * @Method : loginCheck
	 * @Date		: 2017. 6. 14.
	 * @Author	:  유  준  철 
	 * @Description	:	로그인 체크
	 */
	public int loginCheck(MemberVO vo)throws Exception{
		int flag = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> cartMap = new HashMap<String, Object>();
		map.put("MEMBERID", vo.getMemberId());
		map.put("MEMBERPWD", vo.getMemberPwd());
		map.put("JOINTYPE", vo.getJoinType());			
		
		try{
			
			SqlMap checkInfo = commonDAO.loginCheck(map);												//로그인 가능 여부 체크
			int userCnt = NumberUtils.toInt(String.valueOf(checkInfo.get("userCnt")));
			String validYn =String.valueOf(checkInfo.get("validYn"));
			String dormancyYn =String.valueOf(checkInfo.get("dormancyYn"));
			String changePwdYn =String.valueOf(checkInfo.get("changePwdYn"));
			
			if(userCnt <1 ){
				flag = 0;   // 회원 정보 없는 경우
			}else{
				if(validYn.equals("N")){
					flag = 1;   // 회원 아이디/비밀번호 불일치
				}else{
					if(dormancyYn.equals("Y")){ 
						flag = 3;   // 휴면 회원 전환
					}else{
						if(changePwdYn.equals("Y")){ 
							flag = 2;   // 비밀번호 변경
						}else{
							flag = 4; //로그인 성공
							
							SqlMap memberInfo = commonDAO.getMemberInfo(map);
							HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
							HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
							HttpSession session = request.getSession(false);
							
							String memberIdx = String.valueOf(memberInfo.get("memberIdx"));
							
							session.removeAttribute("SS_NOMEMBER_ORDER_CD");				// 비회원 ORDER_CD 초기화
							
							// session 정보
							session.setAttribute("SS_MEMBER_IDX", Integer.parseInt((String)memberIdx));
							session.setAttribute("SS_MEMBER_ID", memberInfo.get("memberId"));
							session.setAttribute("SS_MEMBER_NM", memberInfo.get("memberNm"));
							session.setAttribute("SS_LEVEL_IDX", memberInfo.get("levelIdx"));
							session.setAttribute("SS_MEMBER_FLAG", "Y");
							session.setAttribute("SS_MEMBER_LOGIN_TYPE", "MEMBER");
							
							String device = ""; 
							device =  PathUtil.getCtx().replace("/", "");
							if(device.equals("w")){
								device = "P";
							}else{
								device = "M";
							}
							
							map.put("IP", MethodUtil.clientIP(request));				
							map.put("MEMBERIDX", memberIdx);	
							map.put("DEVICE",device);
							map.put("SRCPATHSESSIONID",session.getId());
							
							commonDAO.memberLogInsert(map);							// 로그인 이력
							commonDAO.memberInfoUpdate(map);						// 회원정보 업데이트 (방문수, 방문IP, 방문일자)
							commonDAO.goodsViewUpdate(map);						// 상품 뷰 테이블 업데이트 (유입경로)
							
							// 장바구니 회원 비회원 통합 
							cartMap.put("SESSIONID", vo.getMandomSessionId());								// 비회원 세션 ID
							cartMap.put("MEMBERIDX", Integer.parseInt((String)memberIdx));			// 회원 일련번호
							
							// 장바구니 업데이트 리스트
							List<SqlMap> cartUpdateList = commonDAO.getCartUpdateList(cartMap);
							
							if(cartUpdateList != null && cartUpdateList.size() > 0){
								for(int i=0; i < cartUpdateList.size(); i++){
									cartMap.put("CARTIDX", cartUpdateList.get(i).get("cartIdx"));	
									commonDAO.cartUpdate(cartMap);
								}
							}
							
							// 장바구니 삭제 리스트
							List<SqlMap> cartDeleteList =commonDAO.getCartDeleteList(cartMap);
								
							if(cartDeleteList != null && cartDeleteList.size() > 0){
								for(int i=0; i < cartDeleteList.size(); i++){
									cartMap.put("CARTIDX", cartDeleteList.get(i).get("cartIdx"));	
									commonDAO.cartDelete(cartMap);
								}
							}
							
							//아이디 저장
							Cookie cookie = null;
							if(vo.getSaveMemberIdFlag().equals("Y")){
								cookie = new Cookie("cookieMemberId", memberInfo.get("memberId").toString());
								cookie.setMaxAge(60*60*24*30);			//1 달 유효 설정
								response.addCookie(cookie);
							}else{
								cookie = new Cookie("cookieAdminId", null);
								cookie.setMaxAge(0);							
								response.addCookie(cookie);
							}
						}
					}
				}			
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return flag;		
	}
	
	/**
	 * @Method : noLoginCheck
	 * @Date: 2017. 6. 22.
	 * @Author :  김  민  수
	 * @Description	:	비로그인 체크	
	 * @HISTORY :
	 *
	 */
	public int noLoginCheck(OrderVO vo)throws Exception{
		int flag = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ORDERCD", vo.getOrderCd());
		map.put("SENDERNM", vo.getSenderNm());
		map.put("SENDERPHONENO", vo.getSenderPhoneNo());	// 주문 테이블에 전화번호 - 없이 저장됨
		try{
			
			SqlMap orderInfo = commonDAO.noLoginCheck(map);
			if(orderInfo !=null){
				HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); 
				HttpSession session = request.getSession(false);
				
				session.removeAttribute("SS_NOMEMBER_ORDER_CD");				// 비회원 ORDER_CD 초기화
				
				// session 정보
				session.setAttribute("SS_MEMBER_FLAG", "N");
				session.setAttribute("SS_NOMEMBER_ORDER_CD", orderInfo.get("orderCd"));	// 비회원 로그인 주문 코드
				session.setAttribute("SS_MEMBER_LOGIN_TYPE", "MEMBER");
				flag = 2;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return flag;
	}
	
	/**
	 * @Method : findMemberId
	 * @Date: 2017. 6. 22.
	 * @Author :  김  민  수
	 * @Description	:	아이디 찾기 결과	
	 * @HISTORY :
	 *
	 */
	public SqlMap findMemberId(MemberVO vo)throws Exception{
		SqlMap info = new SqlMap();
		HashMap<String, Object> map = new HashMap<>();
		map.put("MEMBERNM", vo.getMemberNm());
		map.put("EMAIL", vo.getEmail());
		
		try{
			SqlMap memberInfo = commonDAO.findMemberId(map);
			if(memberInfo !=null){
				info.put("info", memberInfo);
				info.put("flag", "Y");
			}else{
				info.put("flag", "N");
			} 
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return info;
	}
	
	/**
	 * @Method : findMemberPwd
	 * @Date: 2017. 6. 22.
	 * @Author :  김  민  수
	 * @Description	:	비밀번호 찾기 결과
	 * @HISTORY :
	 *
	 */
	public SqlMap findMemberPwd(MemberVO vo)throws Exception{
		SqlMap info = new SqlMap();
		HashMap<String, Object> map = new HashMap<>();
		map.put("MEMBERID", vo.getMemberId());
		map.put("EMAIL", vo.getEmail());
		
		try{
			SqlMap memberInfo = commonDAO.findMemberPwd(map);
			if(memberInfo !=null){
				String findType = vo.getFindType();
				
				if("E".equals(findType)){ // 이메일 발송
					String password=  MethodUtil.randomNumber(12);
					map.put("MEMBERPWD", password);
					commonDAO.changeMemberPwd(map);
					vo.setMemberNm(memberInfo.get("memberNm").toString());
					vo.setMemberPwd(password); 
					MailUtil.sendMemberPassword(vo);					//임시비밀번호 발송
					info.put("info", memberInfo);
					info.put("flag", "Y");
				}else if("P".equals(findType)){ // 휴대폰 발송
					String password=  MethodUtil.randomNumber(12);
					map.put("MEMBERPWD", password);
					commonDAO.changeMemberPwd(map);
					
					vo.setMemberIdx(Integer.parseInt(memberInfo.get("memberIdx").toString()));
					vo.setMemberNm(memberInfo.get("memberNm").toString());
					vo.setMemberPwd(password);
					
					map.put("MEMBERIDX", vo.getMemberIdx());
					map.put("MEMBERCODE", "MEM006");
					map.put("PWD", password);
					commonDAO.callSpSmsSend(map);		// 휴대폰 임시비밀번호 발송
					
					info.put("info", memberInfo);
					info.put("flag", "Y");
				}else{
					info.put("flag", "N");
				}
			}else{
				info.put("flag", "N");
			}
		}catch(Exception e){
			e.printStackTrace();
			info.put("flag", "N");
		}
		
		return info;
	}
	
	
	/**
	 * @Method : getCodeList
	 * @Date		: 2017. 6. 19.
	 * @Author	:  유  준  철 
	 * @Description	:	공통코드(하위) 리스트
	 */	
	public List<SqlMap> getCodeList(String commonCd)throws Exception{
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<>();
		map.put("COMMONCD", commonCd);
		
		try{
			list = commonDAO.getCodeList(map);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}

	/**
	 * @Method : getCate1DepthList
	 * @Date		: 2017. 8. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	1Depth 카테고리 리스트 (select 박스용)
	 */
	public List<SqlMap> getCate1DepthList(int brandIdx)throws Exception{
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<>();
		map.put("BRANDIDX", brandIdx);
		
		try{
			list = commonDAO.getCate1DepthList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}
	
	/**
	 * @Method : getCate2DepthList
	 * @Date		: 2017. 8. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	2Depth 카테고리 리스트 (select 박스용)
	 */
	public List<SqlMap> getCate2DepthList(SchProductVO schProductVo)throws Exception{
		List<SqlMap> list = null;
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("SCHCATEFLAG", schProductVo.getSchCateFlag());
		map.put("SCHCATEIDX", schProductVo.getSchCateIdx());
		map.put("SCH2DEPTHCATEIDX", schProductVo.getSch2depthCateIdx());
		
		try{
			list = commonDAO.getCate2DepthList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}
	
	/**
	 * @Method : getCate3DepthList
	 * @Date		: 2017. 8. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	3Depth 카테고리 리스트 (select 박스용)
	 */
	public List<SqlMap> getCate3DepthList(SchProductVO schProductVo)throws Exception{
		List<SqlMap> list = null;
		
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("SCHCATEIDX", schProductVo.getSchCateIdx());
		
		try{
			list = commonDAO.getCate3DepthList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}
	/**
	 * @Method : getTotalCate1DepthList
	 * @Date		: 2017. 8. 3.
	 * @Author	:  강 병 철
	 * @Description	:	1Depth 통합 카테고리 리스트 (select 박스용)
	 */
	public List<SqlMap> getTotalCate1DepthList()throws Exception{
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<>();
		
		try{
			list = commonDAO.getTotalCate1DepthList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}
	
	/**
	 * @Method : getTotalCate2DepthList
	 * @Date		: 2017. 8. 3.
	 * @Author	:  강 병 철
	 * @Description	:	2Depth 통합 카테고리 리스트 (select 박스용)
	 */
	public List<SqlMap> getTotalCate2DepthList(SchProductVO schProductVo)throws Exception{
		List<SqlMap> list = null;
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("SCHCATEFLAG", schProductVo.getSchCateFlag());
		map.put("SCHCATEIDX", schProductVo.getSchCateIdx());
		map.put("SCH2DEPTHCATEIDX", schProductVo.getSch2depthCateIdx());
		
		try{
			list = commonDAO.getTotalCate2DepthList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}
	
	/**
	 * @Method : getTotalCate3DepthList
	 * @Date		: 2017. 8. 3.
	 * @Author	:강 병 철
	 * @Description	:	3Depth 통합 카테고리 리스트 (select 박스용)
	 */
	public List<SqlMap> getTotalCate3DepthList(SchProductVO schProductVo)throws Exception{
		List<SqlMap> list = null;
		
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("SCHCATEIDX", schProductVo.getSchCateIdx());
		
		try{
			list = commonDAO.getTotalCate3DepthList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}
	
	/**
	 * @Method : getBrandList
	 * @Date: 2017. 5. 24.
	 * @Author :  김  민  수
	 * @Description	:	브랜드 리스트(select 박스용)
	 * @HISTORY :
	 *
	 */
	public List<SqlMap> getBrandList()throws Exception{
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<>();
		
		try{
			list = commonDAO.getBrandList(map);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * @Method : getUserInfo
	 * @Date		: 2017. 6. 22.
	 * @Author	:  유  준  철 
	 * @Description	:	회원 정보
	 */
	public SqlMap getUserInfo(MemberVO vo)throws Exception{
		SqlMap userInfo = null;
		HashMap<String, Object> map = new HashMap<>();
		map.put("MEMBERID", vo.getMemberId());
		
		try{
			userInfo = commonDAO.getMemberInfo(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return userInfo;
	}
	
	/**
	 * @Method : dormancySave
	 * @Date		: 2017. 6. 26.
	 * @Author	:  유  준  철 
	 * @Description	:	휴면 해제
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public int dormancySave(MemberVO vo)throws Exception{
		int flag = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("MEMBERIDX", vo.getMemberIdx());
		
		commonDAO.dormancyUpdate(map);
		
		commonDAO.dormancyDelete(map);
		
		flag = 1;
		
		return flag;  
	}
	
	/**
	 * @Method : nextChangePwd
	 * @Date		: 2017. 6. 27.
	 * @Author	:  유  준  철 
	 * @Description	:	비밀번호 다음에 변경
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public int nextChangePwd(MemberVO vo)throws Exception{
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("MEMBERIDX", vo.getMemberIdx());
		
		return commonDAO.nextChangePwd(map);
	}
	
	/**
	 * @Method : checkPwd
	 * @Date		: 2017. 6. 27.
	 * @Author	:  유  준  철 
	 * @Description	:	비밀번호 체크
	 */
	public int checkPwd(MemberVO vo)throws Exception{
		int flag = 0;
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("MEMBERIDX", vo.getMemberIdx());
		map.put("MEMBERPWD", vo.getMemberPwd());
		
		try{
			flag = commonDAO.checkPwd(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return flag;
	}
	
	/**
	 * @Method : changePwd
	 * @Date		: 2017. 6. 27.
	 * @Author	:  유  준  철 
	 * @Description	:	비밀번호 변경	
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public int changePwd(MemberVO vo)throws Exception{
		int flag = 0;
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("MEMBERIDX", vo.getMemberIdx());
		map.put("MEMBERID", vo.getMemberId());
		map.put("MEMBERPWD", vo.getMemberPwd());
		map.put("JOINTYPE", vo.getJoinType());
		
		 commonDAO.changeMemberPwd(map);
		 commonDAO.nextChangePwd(map);
		 
		 // 로그인 
		 loginCheck(vo);
		 
		 flag = 1;
		
		return flag;
	}
	
	/**
	 * @Method : getCategoryList
	 * @Date		: 2017. 6. 29.
	 * @Author	:  유  준  철 
	 * @Description	:	GNB 카테고리
	 */
	public List<SqlMap> getCategoryList()throws Exception{
		List<SqlMap> list = null;
		
		try{
			list = commonDAO.getCategoryList();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * @Method : getCategory3DepthList
	 * @Date		: 2017. 7. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	3DEPTH 카테고리 리스트
	 */
	public List<SqlMap> getCategory3DepthList() throws Exception {
		List<SqlMap> list = null;
		
		try{
			list = commonDAO.getCategory3DepthList();			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;		
	}
	
	
	/**
	 * @Method : getTotalCateList
	 * @Date		: 2018. 08. 29.
	 * @Author	:  강 병 철
	 * @Description	:	통합 카테고리
	 */
	public List<SqlMap> getTotalCateList()throws Exception{
		List<SqlMap> list = null;
		
		try{
			list = commonDAO.getTotalCateList();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * @Method : getTotalCategory3DepthList
	 * @Date		: 2018. 08. 29.
	 * @Author	:  강 병 철
	 * @Description	: 통합 3DEPTH 카테고리 리스트
	 */
	public List<SqlMap> getTotalCategory3DepthList() throws Exception {
		List<SqlMap> list = null;
		
		try{
			list = commonDAO.getTotalCategory3DepthList();			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;		
	}
	/**
	 * @Method : eventBanner
	 * @Date		: 2017. 7. 10.
	 * @Author	:  유  준  철 
	 * @Description	:	이벤트 배너
	 */
	public SqlMap eventBanner(String bannerPos)throws Exception{
		SqlMap eventBanner = null;
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("BANNERPOS", bannerPos);
		
		try{
			eventBanner = commonDAO.eventBanner(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return eventBanner;
	}
	
	/**
	 * @Method : getHtmlinfoList
	 * @Date		: 2017. 7. 26.
	 * @Author	:  유  준  철 
	 * @Description	:	HTML 정보관리 리스트
	 */
	public List<SqlMap> getHtmlinfoList() throws Exception {
		List<SqlMap> list = null;
		
		try{
			list = commonDAO.getHtmlinfoList();			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;		
	}
	
	/**
	 * @Method : getHtmlinfo
	 * @Date		: 2017. 7. 31.
	 * @Author	:  유  준  철 
	 * @Description	:	HTML 정보관리
	 */
	public SqlMap getHtmlinfo(String gubun)throws Exception{
		SqlMap detail = null;
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("HTMLINFOGUBUN", gubun);
		
		try{
			detail = commonDAO.getHtmlinfo(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return detail;
	}

	/**
	 * @Method : autoCompletResult
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	자동완성 결과
	 */
	public List<SqlMap> autoCompletProductResult(String query)throws Exception{
		List<SqlMap> detail = null;
		HashMap<String, Object> map = new HashMap<>();
		if (!query.trim().isEmpty()) {
			query = query.replaceAll("\'", "");
		}
		map.put("QUERY", query);
		detail = (List<SqlMap>)commonDAO.autoCompletProductResult(map);
		return detail;
	}
	
	/**
	 * @Method : autoCompletHashtagResult
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	자동완성 hashtag 결과
	 */
	public List<SqlMap> autoCompletHashtagResult(String query)throws Exception{
		List<SqlMap> detail = null;
		HashMap<String, Object> map = new HashMap<>();
		if (query.trim().substring(0,1).equals("#")) {
			query = query.trim().substring(1,query.trim().length());
		}
		query = query.replaceAll("\'", "");
		map.put("QUERY", query);
		detail = (List<SqlMap>)commonDAO.autoCompletHashtagResult(map);
		return detail;
	}

	   /**
	    * @Method : getLastestOrderedGoods
	    * @Date: 2017. 8. 4.
	    * @Author :  서 정 길
	    * @Description	:	WING - 최근 구매 상품 1개
	   */
	@Override
	public SqlMap getLastestOrderedGoods(OrderVO vo) throws Exception {
		SqlMap goodsInfo = null;
		
		HashMap<String, Object> map = new HashMap<>();
		if(vo.getMemberIdx() > 0){	// 회원
			map.put("MEMBERIDX", vo.getMemberIdx());	// 회원 일련번호
			map.put("SESSIONID", null);							// 비회원 세션 ID
		}else{							// 비회원
			map.put("MEMBERIDX", null);						// 회원 일련번호
			map.put("SESSIONID", vo.getSessionId());		// 비회원 세션 ID
		}
		
		try{
			goodsInfo = commonDAO.getLastestOrderedGoods(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return goodsInfo;
	}

	/**
	    * @Method : getMainBannerList
	    * @Date: 2017. 8. 11.
	    * @Author :  임  재  형
	    * @Description	:	메인 배너 리스트
	   */
	public List<SqlMap> getMainBannerList(String gubun) throws Exception {
		List<SqlMap> mainBannerList = null;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("BANNERPOS", gubun);
		
		try{
			mainBannerList = commonDAO.getMainBannerList(map); // 메인 배너 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return mainBannerList;
	}

	/**
	    * @Method : getPcMidBanner
	    * @Date: 2017. 8. 11.
	    * @Author :  임  재  형
	    * @Description	:	PC 중간 배너
	   */
	public SqlMap getPcMidBanner(String gubun) throws Exception {
		SqlMap pcMidBanner = null;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("BANNERPOS", gubun);
		
		try{
			pcMidBanner = commonDAO.getPcMidBanner(map); // PC 중간 배너
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return pcMidBanner;
	}

	/**
	    * @Method : getMoMidBanner
	    * @Date: 2017. 8. 11.
	    * @Author :  임  재  형
	    * @Description	:	MO 중간 배너
	   */
	public SqlMap getMoMidBanner(String gubun) throws Exception {
		SqlMap moMidBanner = null;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("BANNERPOS", gubun);
		
		try{
			moMidBanner = commonDAO.getMoMidBanner(map); // MO 중간 배너
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return moMidBanner;
	}
	
	/**
	    * @Method : getMainGoodsListCnt
	    * @Date: 2017. 8. 23.
	    * @Author :  임  재  형
	    * @Description	:	세트, 베스트 리스트 총 개수
	   */
	public int getMainGoodsListCnt(String gubun, int mainGubun) throws Exception {
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("GUBUN", gubun);
		map.put("MAINGUBUN", mainGubun);
		
		try{
			cnt = commonDAO.getMainGoodsListCnt(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}

	/**
	    * @Method : getMainGoodsList
	    * @Date: 2017. 8. 11.
	    * @Author :  임  재  형
	    * @Description	:	세트, 베스트 리스트
	   */
	public List<SqlMap> getMainGoodsList(String gubun, int mainGubun, SearchVO schVO) throws Exception {
		List<SqlMap> mainGoodsList = null;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("GUBUN", gubun);
		map.put("MAINGUBUN", mainGubun);
		map.put("DEVICE", PathUtil.getDevice());
		
		try{
			mainGoodsList = commonDAO.getMainGoodsList(map); // 세트, 베스트 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return mainGoodsList;
	}

	/**
	    * @Method : getTipsList
	    * @Date: 2017. 8. 11.
	    * @Author :  임  재  형
	    * @Description	:	TIPS 리스트
	   */
	public List<SqlMap> getTipsList(int mainGubun) throws Exception {
		List<SqlMap> tipsList = null;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("MAINGUBUN", mainGubun);
		
		try{
			tipsList = commonDAO.getTipsList(map); // TIPS 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return tipsList;
	}

	/**
	    * @Method : getReviewList
	    * @Date: 2017. 8. 11.
	    * @Author :  임  재  형
	    * @Description	:	후기 리스트
	   */
	public List<SqlMap> getReviewList(int mainGubun) throws Exception {
		List<SqlMap> reviewList = null;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("MAINGUBUN", mainGubun);
	
		try{
			reviewList = commonDAO.getReviewList(map); // 후기 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return reviewList;
	}

	/**
	    * @Method : getEventList
	    * @Date: 2017. 8. 11.
	    * @Author :  임  재  형
	    * @Description	:	이벤트 리스트
	   */
	public List<SqlMap> getEventList() throws Exception {
		List<SqlMap> eventList = null;
		HashMap<String, Object> map = new HashMap<>();
		
		if(PathUtil.getDevice().equals("m")){
			map.put("DEVICE", "M");
		}else{
			map.put("DEVICE", "P");
		}
		
		try{
			eventList = commonDAO.getEventList(map); // 이벤트 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return eventList;
	}

	/**
	    * @Method : getSourceBanner
	    * @Date: 2017. 8. 12.
	    * @Author :  임  재  형
	    * @Description	:	제품 소개
	   */
	public SqlMap getSourceBanner(int mainGubun) throws Exception {
		SqlMap sourceBanner = null;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("MAINGUBUN", mainGubun);
		
		try{
			sourceBanner = commonDAO.getSourceBanner(map); // 제품 소개
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return sourceBanner;
	}
	
	/**
	    * @Method : getWingCoupon
	    * @Date: 2017. 8. 12.
	    * @Author : 강 병 철
	    * @Description	:	윙의 쿠폰 정보
	   */
	public List<SqlMap> getWingCouponList(HashMap<String, Object> map) throws Exception {
		List<SqlMap> list = null;
		
		try{
			list = commonDAO.getWingCouponList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	/**
	    * @Method : getBannerWing
	    * @Date: 2017. 8. 28.
	    * @Author :  임  재  형
	    * @Description	:	윙 배너 리스트
	   */
	public List<SqlMap> getBannerWing(String gubun) throws Exception {
		List<SqlMap> bannerWing = null;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("BANNERPOS", gubun);
		
		try{
			bannerWing = commonDAO.getBannerWing(map); // 윙 배너 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return bannerWing;
	}

	/**
	    * @Method : getPopupList
	    * @Date: 2017. 9. 1.
	    * @Author :  임  재  형
	    * @Description	:	팝업 리스트
	   */
	public List<SqlMap> getPopupList() throws Exception {
		List<SqlMap> popupList = null;
		HashMap<String, Object> map = new HashMap<>();
		
		String device = ""; 
		device =  PathUtil.getCtx().replace("/", "");
		if(device.equals("w")){
			device = "P";
		}else{
			device = "M";
		}
		
		map.put("DEVICE",device);
		map.put("DEVICE2", "A");
		
		try{
			popupList = commonDAO.getPopupList(map); // 팝업 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return popupList;
	}

	/**
	    * @Method : getBannerHeader
	    * @Date: 2017. 9. 11.
	    * @Author :  임  재  형
	    * @Description	:	띠배너
	   */
	public SqlMap getBannerHeader(String bannerPos) throws Exception {
		SqlMap bannerHeader = null;
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("BANNERPOS", bannerPos);
		if(PathUtil.getDevice().equals("m")){
			map.put("DEVICEGUBUN", "M");
		}else{
			map.put("DEVICEGUBUN", "P");
		}
		map.put("DEVICEGUBUN2", "A");
		
		try{
			bannerHeader = commonDAO.getBannerHeader(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return bannerHeader;
	}
	
	/**
	 * @Method : getCate1DepthSetList
	 * @Date		: 2018. 2. 9.
	 * @Author	:  유  준  철 
	 * @Description	:	1뎁스 카테고리 세트 리스트
	 */
	public List<SqlMap> getCate1DepthSetList()throws Exception{
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<>();
		
		try{
			list = commonDAO.getCate1DepthSetList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}
	
	/**
	 * @Method : getCate1DepthSetList
	 * @Date		: 2018. 2. 9.
	 * @Author	:  유  준  철 
	 * @Description	:	2뎁스 카테고리 세트 리스트
	 */
	public List<SqlMap> getCate2DepthSetList()throws Exception{
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<>();
		
		try{
			list = commonDAO.getCate2DepthSetList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	/**
	 * @Method : getMainHashtagList
	 * @Date		: 2018. 2. 12.
	 * @Author	:  임  재  형 
	 * @Description	:	메인 해시태그 리스트
	 */
	public List<SqlMap> getMainHashtagList(int mainGubun) throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<>();
		map.put("MAINGUBUN", mainGubun);
		
		try{
			list = commonDAO.getMainHashtagList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	/**
	 * @Method : getNoticeList
	 * @Date		: 2018. 2. 13.
	 * @Author	:  임  재  형 
	 * @Description	:	공지사항 리스트
	 */
	public List<SqlMap> getNoticeList() throws Exception {
		List<SqlMap> list = null;
		
		try{
			list = commonDAO.getNoticeList();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}
	
	/**
	 * @Method : getSrcPathSave
	 * @Date		: 2018. 4. 11.
	 * @Author	:  유  준  철 
	 * @Description	:	유입 경로 저장
	 */
	public void getSrcPathSave(CommonVO vo)throws Exception{
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); 
		HttpSession session = request.getSession(false);
	      
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> param = new HashMap<String, Object>();
		
		String paramStr = "?"+vo.getSrcPathUrl();
		
		List<NameValuePair> parameters;
		
	      try {
	    	  
	    	  parameters = URLEncodedUtils.parse(new URI(paramStr), Charset.defaultCharset());
	    	  
	    	  for (NameValuePair p : parameters) {
	    		  param.put(p.getName(), p.getValue());
	    	  }
	    	  
	      } catch (URISyntaxException e) {
	         e.printStackTrace();
	      }
	      
	      if(param.get("adfrom") != null){
	    	  logger.info("*************** seesionId*************** : " + vo.getSrcPathSessionId());
	    	  logger.info("*************** param *************** : " + param);
	    	  
	    	  map.put("SESSIONID", vo.getSrcPathSessionId());
	    	  map.put("ADFROM", param.get("adfrom"));
	    	  map.put("DEVICE", PathUtil.getDeviceNm());
	    	  
	    	  commonDAO.callSrcPathProcedure(map);			// 유입 경로 프로시저 호출
	    	  logger.info("유입경로 프로시저 저장 결과값 : " + map.get("RESULT"));		
	    	  
	    	  String result = (String) map.get("RESULT");
	    	  
	    	  if (!result.isEmpty()){
	    		  logger.info("*************** adfrom_seesion 생성!! ***************");
	    		  session.setAttribute("adfrom", result);
	    	  }
	      }
	}

	/**
	 * @Method : getBonoYn
	 * @Date		: 2018. 6. 26.
	 * @Author	:  임  재  형 
	 * @Description	:	보노보노 노출 여부
	 */
	public SqlMap getBonoYn() throws Exception {
		SqlMap bono = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("URL", PathUtil.getRefer());	// 현재 URL
		map.put("DEVICE", PathUtil.getDeviceNm());	// 디바이스
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx());	// 회원 일련번호
		
		try{
			bono = commonDAO.callSpBonoLoading(map);	// 보노보노 로딩 프로시저 호출
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return bono;
	}

	/**
	 * @Method : bonoClick
	 * @Date		: 2018. 6. 26.
	 * @Author	:  임  재  형 
	 * @Description	:	보노보노 클릭	
	 */
	public SqlMap bonoClick() throws Exception {
		SqlMap bono = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx());	// 회원 일련번호
		
		try{
			bono = commonDAO.callSpBonoClick(map);	// 보노보노 클릭
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return bono;
	}

	/**
	 * @Method : bonoPointSave
	 * @Date		: 2018. 6. 26.
	 * @Author	:  임  재  형 
	 * @Description	:	보노보노 포인트 지급
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public int bonoPointSave(String reason, int bonoPoint) throws Exception {
		int flag = 0;
		int pointPrice = 0;							// 보유 포인트 금액
		int payDedPrice = bonoPoint;			// 포인트 지급 금액
		int resultVal = 0;							// 포인트 업데이트 값 
		SqlMap detail = null;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx());
		map.put("PAYDEDTYPE", "P");
		map.put("PAYDEDREASON", reason);
		map.put("PAYMENTPRICE", bonoPoint);
		
		// 회원 포인트 정보
		detail = commonDAO.getMemberPoint(map);
		pointPrice = (Integer)detail.get("pointPrice");
		
		memberDAO.memberPointSave(map);			// 포인트 저장
		
		resultVal = pointPrice + payDedPrice;	
		map.put("POINTPRICE", resultVal);
		
		commonDAO.memberPointUpdate(map);		// 회원 포인트 업데이트
		flag = 2;
			
		return flag;
	}
	
	/**
	    * @Method : getBannerOne
	    * @Date: 2018. 8. 22.
	    * @Author :  임  재  형
	    * @Description	:	배너 1개만 가져오기
	   */
	public SqlMap getBannerOne(String bannerPos) throws Exception {
		SqlMap bannerInfo = null;
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("BANNERPOS", bannerPos);
		if(PathUtil.getDevice().equals("m")){
			map.put("DEVICEGUBUN", "M");
		}else{
			map.put("DEVICEGUBUN", "P");
		}
		map.put("DEVICEGUBUN2", "A");
		
		try{
			bannerInfo = commonDAO.getBannerOne(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return bannerInfo;
	}
	
	
	/***************************** 2018 리뉴얼 메인 *****************************/
	
	
	/**
	 * @Method : getMainProductList
	 * @Date		: 2018. 8. 23.
	 * @Author	:  임  재  형 
	 * @Description	:	메인 상품 리스트
	 */
	public List<SqlMap> getMainProductList(String mainGubun, String gubun) throws Exception {
		List<SqlMap> productList = null;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("GUBUN", gubun);
		map.put("MAINGUBUN", mainGubun);
		
		try{
			productList = commonDAO.getProductList(map); // 메인 상품 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return productList;
	}

	/**
	 * @Method : getMainHtml
	 * @Date		: 2018. 8. 23.
	 * @Author	:  임  재  형 
	 * @Description	:	메인 HTML
	 */
	public SqlMap getMainHtml(String mainGubun, String gubun) throws Exception {
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("MAINGUBUN", mainGubun);
		map.put("GUBUN", gubun);
		
		try{
			detail = commonDAO.getMainHtml(map); // 메인 HTML
		}catch(Exception e){
			e.printStackTrace();
			throw  e;
		}
		
		return detail;
	}

	/**
	 * @Method : getMainReviewList
	 * @Date		: 2018. 8. 23.
	 * @Author	:  임  재  형 
	 * @Description	:	메인 후기 리스트
	 */
	public List<SqlMap> getMainReviewList(String mainGubun, String gubun) throws Exception {
		List<SqlMap> reviewList = null;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("MAINGUBUN", mainGubun);
		map.put("GUBUN", gubun);
	
		try{
			reviewList = commonDAO.getMainReviewList(map); // 메인 후기 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return reviewList;
	}

	/**
	 * @Method : getMainSnsList
	 * @Date		: 2018. 8. 23.
	 * @Author	:  임  재  형 
	 * @Description	:	메인 SNS 리스트
	 */
	public List<SqlMap> getMainSnsList(String mainGubun) throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("MAINGUBUN", mainGubun);
		
		try{
			list = commonDAO.getMainSnsList(map); // 메인 SNS 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	/**
	 * @Method : getMainTipList
	 * @Date		: 2018. 8. 23.
	 * @Author	:  임  재  형 
	 * @Description	:	메인 Tip 리스트
	 */
	public List<SqlMap> getMainTipList(String mainGubun, String gubun) throws Exception {
		List<SqlMap> tipsList = null;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("MAINGUBUN", mainGubun);
		map.put("GUBUN", gubun);
		
		try{
			tipsList = commonDAO.getMainTipList(map); // 메인 Tip 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return tipsList;
	}

	/**
	 * @Method : getMainEventList
	 * @Date		: 2018. 8. 23.
	 * @Author	:  임  재  형 
	 * @Description	:	메인 이벤트 리스트	
	 */
	public List<SqlMap> getMainEventList(String mainGubun) throws Exception {
		List<SqlMap> eventList = null;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("MAINGUBUN", mainGubun);
		if(PathUtil.getDevice().equals("m")){
			map.put("DEVICE", "M");
		}else{
			map.put("DEVICE", "P");
		}
		
		try{
			eventList = commonDAO.getMainEventList(map); // 메인 이벤트 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return eventList;
	}

	/**
	 * @Method : getFloatPopup
	 * @Date		: 2018. 9. 17.
	 * @Author	:  임  재  형 
	 * @Description	:	PC 플로팅 팝업
	 */
	public SqlMap getFloatPopup(String popGubun) throws Exception {
		SqlMap floatPopup = null;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("POPGUBUN", popGubun);
		
		try{
			floatPopup = commonDAO.getFloatPopup(map); // PC 플로팅 팝업
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return floatPopup;
	}

	
	
	/**
	 * @Method : getFileInfo
	 * @Date		: 2020. 01. 14.
	 * @Description	: 파일다운로드시 파일 정보 가지고 오기 
	 */
	public SqlMap getFileInfo(FileVO vo) throws Exception {
		
		SqlMap fileInfo = new SqlMap();
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("TABLEIDX", vo.getFileFolder());
		map.put("CSCENTERFILEIDX", vo.getUploadFileNm());
		
		try{
			fileInfo = commonDAO.getFileInfo(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return fileInfo;
	}
	
	
	
	
}
