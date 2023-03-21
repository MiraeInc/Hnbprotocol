package com.gxenSoft.mall.sns.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gxenSoft.mall.common.dao.CommonDAO;
import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.member.dao.MemberDAO;
import com.gxenSoft.mall.member.vo.MemberVO;
import com.gxenSoft.mall.sns.dao.SnsDAO;
import com.gxenSoft.method.MethodUtil;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.pathUtil.PathUtil;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : SnsServiceImpl
 * PACKAGE NM : com.gxenSoft.mall.sns.service
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 6. 21. 
 * HISTORY :
 
 *************************************
 */
@Service("snsService")
public class SnsServiceImpl implements SnsService {
	
	static final Logger logger = LoggerFactory.getLogger(SnsServiceImpl.class);
	
	@Autowired
	private SnsDAO snsDAO;
	
	@Autowired
	private CommonDAO commonDAO;
	
	@Autowired
	private MemberDAO memberDAO;
	
	/**
	 * @Method : snsLoginCheck
	 * @Date		: 2017. 6. 22.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS 로그인 체크
	 */
	public int snsLoginCheck(MemberVO vo)throws Exception{
		int flag = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> cartMap = new HashMap<String, Object>();
		
		map.put("MEMBERID", vo.getSnsCd());
		
		try{
			flag = snsDAO.snsLoginCheck(map);
			
			if(flag == 1 && vo.getSnsMode().equals("login")){
				SqlMap memberInfo = commonDAO.getMemberInfo(map);
				
				HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
				HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
				HttpSession session = request.getSession(false);
				
				if(memberInfo.get("inactiveState").equals("Y")){
					vo.setMemberId((String) memberInfo.get("memberId"));
					
					MethodUtil.redirectUrl(request, response, "/login/dormancyForm.do?memberId="+vo.getMemberId());
				}else{
					String memberIdx = String.valueOf(memberInfo.get("memberIdx"));
					
					// session 정보
					session.setAttribute("SS_MEMBER_IDX", Integer.parseInt((String)memberIdx));
					session.setAttribute("SS_MEMBER_ID", memberInfo.get("memberId"));
					session.setAttribute("SS_MEMBER_NM", memberInfo.get("memberNm"));
					session.setAttribute("SS_LEVEL_IDX", memberInfo.get("levelIdx"));
					session.setAttribute("SS_MEMBER_FLAG", "Y");
					session.setAttribute("SS_MEMBER_LOGIN_TYPE", "SNS");
					
					String device = ""; 
					device =  PathUtil.getCtx().replace("/", "");
					if(device.equals("w")){
						device = "P";
					}else{
						device = "M";
					}
					
					map.put("IP", MethodUtil.clientIP(request));				
					map.put("MEMBERIDX", memberIdx);					
					map.put("DEVICE", device);
					map.put("SNSTYPE", vo.getSnsType());
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
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return flag;
	}
	
	/**
	 * @Method : snsConnectLogin
	 * @Date		: 2017. 8. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS 연동 로그인 처리 
	 */
	public int snsConnectLogin(MemberVO vo)throws Exception{
		int flag = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> cartMap = new HashMap<String, Object>();
		
		map.put("SNSCD", vo.getSnsCd());
		SqlMap snsMemberInfo = snsDAO.getSnsMemberInfo(map);
		map.put("MEMBERID", snsMemberInfo.get("memberId"));
		
		try{
			
			SqlMap memberInfo = commonDAO.getMemberInfo(map);
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			HttpSession session = request.getSession(false);
			String memberIdx = String.valueOf(memberInfo.get("memberIdx"));
			
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
			
			commonDAO.memberLogInsert(map);							// 로그인 이력
			commonDAO.memberInfoUpdate(map);						// 회원정보 업데이트 (방문수, 방문IP, 방문일자)
			
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
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return flag;
	}
	
	/**
	 * @Method : snsJoinCheck
	 * @Date		: 2017. 6. 27.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS 가입 체크
	 */
	public int snsJoinCheck(MemberVO vo)throws Exception{
		int flag = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("MEMBERID", vo.getSnsCd());
		
		try{
			flag = snsDAO.snsLoginCheck(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return flag;
	}
	
	/**
	 * @Method : snsConnectSave
	 * @Date		: 2017. 8. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS 연동 저장
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public void snsConnectSave(MemberVO vo)throws Exception{
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx());
		map.put("SNSCD", MethodUtil.blankAllRemove(vo.getSnsCd()));
		map.put("SNSTYPE", vo.getSnsType());
		map.put("SNSEMAIL", vo.getEmail());
		
		memberDAO.memberSnsSave(map);
	}
	
	/**
	 * @Method : getSnsInfo
	 * @Date		: 2017. 8. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS 계정 정보
	 */
	public SqlMap getSnsInfo(MemberVO vo) throws Exception{
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx());
		map.put("SNSTYPE", vo.getSnsType());
		
		try {
			detail = snsDAO.getSnsInfo(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return detail;
	}
	
	/**
	 * @Method : snsConnectDelete
	 * @Date		: 2017. 8. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS 계정 연동 해제
	 */
	public int snsConnectDelete(MemberVO vo)throws Exception{
		int flag = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("SNSCD", vo.getSnsCd());
		
		try{
			flag = snsDAO.snsConnectDelete(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return flag;
	}
	
	/**
	 * @Method : snsConnectCheck
	 * @Date		: 2017. 8. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS 계정 연동 체크
	 */
	public int snsConnectCheck(MemberVO vo)throws Exception{
		int flag = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("SNSCD", vo.getSnsCd());
		
		try{
			flag = snsDAO.snsConnectCheck(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return flag;
	}
	
	/**
	 * @Method : getSnsMemberInfo
	 * @Date		: 2017. 8. 8.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS 연동 회원 정보
	 */
	public SqlMap getSnsMemberInfo(MemberVO vo) throws Exception{
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("SNSCD", vo.getSnsCd());
		
		try {
			detail = snsDAO.getSnsMemberInfo(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return detail;
	}
	
	/**
	 * @Method : snsReJoinCheck
	 * @Date		: 2017. 8. 8.
	 * @Author	:  유  준  철 
	 * @Description	:	SNS 재가입 체크
	 */
	public int snsReJoinCheck(MemberVO vo)throws Exception{
		int flag = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("SNSCD", vo.getSnsCd());
		
		try{
			flag = snsDAO.snsReJoinCheck(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return flag;
	}
}
