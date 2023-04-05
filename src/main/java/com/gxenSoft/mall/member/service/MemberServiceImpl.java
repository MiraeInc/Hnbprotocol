package com.gxenSoft.mall.member.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gxenSoft.encrypt.Crypto;
import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.member.dao.MemberDAO;
import com.gxenSoft.mall.member.vo.MemberVO;
import com.gxenSoft.mall.mypage.order.dao.MypageOrderDAO;
import com.gxenSoft.method.MethodUtil;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.mailUtil.MailUtil;
import com.gxenSoft.util.pathUtil.PathUtil;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : MemberServiceImpl
 * PACKAGE NM : com.gxenSoft.mall.member.service
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 6. 19. 
 * HISTORY :
 
 *************************************
 */
@Service("memberService")
public class MemberServiceImpl implements MemberService {
	
	static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
	
	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private MypageOrderDAO mypageOrderDAO;
	
	/**
	 * @Method : duplicateCheckMemberId
	 * @Date		: 2017. 6. 19.
	 * @Author	:  유  준  철 
	 * @Description	:	아이디 중복 체크
	 */
	public int duplicateCheckMemberId(MemberVO vo)throws Exception{
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<>();
		map.put("MEMBERID", vo.getMemberId());	
		
		try{
			cnt = memberDAO.duplicateCheckMemberId(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return cnt;
	}
	
	/**
	 * @Method : duplicateCheckEmail
	 * @Date		: 2017. 6. 19.
	 * @Author	:  유  준  철 
	 * @Description	:	이메일 중복 체크
	 */
	public int duplicateCheckEmail(MemberVO vo)throws Exception{
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<>();
		map.put("EMAIL", vo.getEmail());	
		map.put("MEMBERIDX", vo.getMemberIdx());
		
		try{
			cnt = memberDAO.duplicateCheckEmail(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return cnt;
	}
	
	/**
	 * @Method : duplicateCheckRecommender
	 * @Date		: 2017. 6. 20.
	 * @Author	:  유  준  철 
	 * @Description	:	추천인 중복 체크
	 */
	public int duplicateCheckRecommender(MemberVO vo)throws Exception{
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<>();
		map.put("RECOMMENDER", vo.getRecommender());	
		
		try{
			SqlMap detail = memberDAO.recommendDetail(map);
			if(detail != null){
				String recommenderIdx = detail.get("memberIdx").toString();
				map.put("RECOMMENDERIDX", recommenderIdx);
				
				int recommendCnt = memberDAO.getMemberRecommendCnt(map);
				if(recommendCnt >= 5){
					return 100;
				}
				
				cnt = memberDAO.duplicateCheckRecommender(map);
			}else{
				return -1;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return cnt;
	}
	
	/**
	 * @Method : memberSave
	 * @Date		: 2017. 6. 20.
	 * @Author	:  유  준  철 
	 * @Description	:	회원가입 저장
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public void memberSave(MemberVO vo)throws Exception{
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(false);
		
		map.put("MEMBERID", MethodUtil.blankAllRemove(vo.getMemberId()));
		map.put("MEMBERPWD", MethodUtil.blankAllRemove(vo.getMemberPwd()));
		map.put("MEMBERNM", MethodUtil.blankAllRemove(vo.getMemberNm()));
		map.put("EMAIL", MethodUtil.blankAllRemove(vo.getEmail()));
		map.put("EMAILYN", vo.getEmailYn());
		map.put("SMSYN", vo.getSmsYn());
		map.put("PHONENO", MethodUtil.makePhoneNumber(vo.getPhoneNo()));
		map.put("BIRTHDATE", vo.getBirthDate());
		map.put("GENDER", vo.getGender());
		map.put("SUBSCRIBETYPE", vo.getSubscribeType());
		map.put("SUBSCRIBE", vo.getSubscribe());
		String marketingYN = (vo.getMarketingYn() == null ?"":vo.getMarketingYn().toString());
		String individualInfoYn = (vo.getIndividualInfoYn() == null ?"":vo.getIndividualInfoYn().toString());
		if (marketingYN.isEmpty()) {
			map.put("MARKETINGYN", "N");
		} else {
			map.put("MARKETINGYN", marketingYN);
		}
		if (individualInfoYn.isEmpty()) {
			map.put("INDIVIDUALINFOYN", "N");
		} else {
			map.put("INDIVIDUALINFOYN", individualInfoYn);
		}
		map.put("MEMBERSTATE", "100");
		map.put("LEVELIDX", "1");
		map.put("JOINTYPE", vo.getJoinType());
		map.put("HTTPUSERAGENT", vo.getHttpUserAgent());
		map.put("DEVICE", PathUtil.getDeviceNm());
		map.put("ADFROM", session.getAttribute("adfrom"));
		
		// 1. 회원 마스터 정보 
		memberDAO.memberMasterSave(map);
		int memberIdx = (Integer)map.get("memberIdx");	
		map.put("MEMBERIDX", memberIdx);
		
		// 추천인 입력시 추천대상 회원 일련번호 가져오기
		if (!vo.getRecommender().isEmpty()){
			map.put("RECOMMENDER", vo.getRecommender());
			SqlMap detail = memberDAO.recommendDetail(map);
			String recommenderIdx = detail.get("memberIdx").toString();
			map.put("RECOMMENDERIDX", recommenderIdx);
		}
		
		// 2. 회원 디테일 정보
		memberDAO.memberDetailSave(map);
		
		// 3. 회원 포인트 지급 2,000P
		String joinPoint = "2000";

		map.put("PAYMENTPRICE", joinPoint);
		map.put("PAYDEDREASON", "POINT_REASON10");				// 공통코드 신규회원가입
		memberDAO.memberPointSave(map);
		
		// 3. 추천인 등록시 포인트 지급 1,000P
		if (!vo.getRecommender().isEmpty()){
			map.put("PAYMENTPRICE", "1000");
			map.put("PAYDEDREASON", "POINT_REASON20");			// 공통코드 추천인 등록
			memberDAO.memberPointSave(map);
			map.put("POINTPRICE", "3000");											// 신규가입 2,000P + 추천인 등록 1,000P
			
			// 추천인 대상 최대 5회만 지급
			int cnt = memberDAO.getMemberRecommendCnt(map);
			
			if(cnt <  5){
				// 추천인 대상 내역 저장
				memberDAO.memberRecommendSave(map);
				
				//추천인 대상 에게 포인트 지급
				if (map.get("RECOMMENDERIDX") != null) {
					HashMap<String, Object> recommendMap = new HashMap<String, Object>();
					recommendMap.put("MEMBERIDX", map.get("RECOMMENDERIDX").toString());
					recommendMap.put("PAYMENTPRICE", "1000");
					recommendMap.put("PAYDEDREASON", "POINT_REASON20");			// 공통코드 추천인 등록
					memberDAO.memberPointSave(recommendMap);

					recommendMap.clear();
					recommendMap.put("MEMBERIDX", map.get("RECOMMENDERIDX").toString());
					recommendMap.put("POINT", "1000");
					mypageOrderDAO.memberPointUpdate(recommendMap);
				}
			}
		}else{
			map.put("POINTPRICE", joinPoint);											// 신규가입 2,000P
		}
		
		//// 네이버 타임보드 이벤트 (2019-11-13 회원 가입자 대상)용 임시 시작-----------------------------------------------------------------
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
		if ( nowcalendar.compareTo(calendar2) ==0 ) {
			System.out.println("네이버이벤트 추가 포인트 지급");
			map.put("PAYMENTPRICE", "3000");											
			map.put("PAYDEDREASON", "POINT_REASON210");				// 회원가입이벤트 추가포인트
			memberDAO.memberPointSave(map);
			map.put("POINTPRICE", "5000");				//memberinfo에 저장할 포인트
		}
		//// 네이버 타임보드 이벤트 (2019-11-13 회원 가입자 대상)용 임시 END-----------------------------------------------------------------
		
		// 4. 회원 정보 
		memberDAO.memberInfoSave(map);
		
		// 5. SNS 정보 (joinType 0 : 회원가입, 1 : SNS가입) 
		if(vo.getJoinType().equals("1")){
			map.put("SNSCD", MethodUtil.blankAllRemove(vo.getMemberId()));
			map.put("SNSTYPE", vo.getSnsType());
			map.put("SNSEMAIL", MethodUtil.blankAllRemove(vo.getEmail()));
			memberDAO.memberSnsSave(map);
			
			// 가입한 SNS명 가져오기
			SqlMap snsDetail = memberDAO.getSnsName(map);
			String snsTypeNm = snsDetail.get("cdNm").toString();
			vo.setSnsTypeNm(snsTypeNm);
		}
		
		// 6. 알림톡 발송
		map.put("MEMBERCODE", "MEM001");
		memberDAO.callSendSmsProcedure(map);
		
		// 7. 쿠폰 발급
		memberDAO.callMemberCouponProcedure(map);
		
		// 8. 메일 발송
		MailUtil.sendMemberJoin(vo);		
	}
	
	/**
	 * @Method : getMemberDetail
	 * @Date		: 2017. 6. 20.
	 * @Author	:  유  준  철 
	 * @Description	:	회원 상세
	 */
	public SqlMap getMemberDetail(MemberVO vo) throws Exception{
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("MEMBERID", vo.getMemberId());
		
		try {
			detail = memberDAO.getMemberDetail(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return detail;
	}
	
	/**
	 * @Method : getTermsList
	 * @Date		: 2017. 7. 7.
	 * @Author	:  유  준  철 
	 * @Description	:	약관/개인정보 리스트
	 */
	public List<SqlMap> getTermsList() throws Exception {
		List<SqlMap> list = null;
		
		try{
			list = memberDAO.getTermsList();	
			
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;		
	}
	
	//================================ 마이페이지 ================================
	
	/**
	 * @Method : getMemberInfo
	 * @Date		: 2017. 8. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	보유포인트, 진행중인 주문 건
	 */
	public SqlMap getMemberInfo(MemberVO vo) throws Exception{
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx());
		
		try {
			detail = memberDAO.getMemberInfo(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return detail;
	}
	
	/**
	 * @Method : memberModify
	 * @Date		: 2017. 8. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	회원정보 수정
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public void memberModify(MemberVO vo)throws Exception{
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx());
		map.put("PHONENO", MethodUtil.makePhoneNumber(vo.getPhoneNo()));
		map.put("GENDER", vo.getGender());
		map.put("EMAILYN", vo.getEmailYn());
		map.put("SMSYN", vo.getSmsYn());
		map.put("EMAILAGREE", "N");
		map.put("SMSAGREE", "N");
		
		// 이메일 수신 N -> Y
		if("N".equals(vo.getEmailYnOrg()) && "Y".equals(vo.getEmailYn())){
			map.put("EMAILAGREE", "Y");
		}
		// SMS 수신 N -> Y
		if("N".equals(vo.getSmsYnOrg()) && "Y".equals(vo.getSmsYn())){
			map.put("SMSAGREE", "Y");
		}
		
		if(vo.getJoinType().equals("0")){
			map.put("EMAIL", MethodUtil.blankAllRemove(vo.getEmail()));
			map.put("MEMBERPWD", MethodUtil.blankAllRemove(vo.getMemberPwd()));
			map.put("BIRTHDATE", vo.getBirthDate());
			map.put("RECOMMENDER", vo.getRecommender());
			
			// 1.회원 정보 마스터 업데이트
			memberDAO.memberMasterUpdate(map);
			// 2.회원 정보 디테일 업데이트
			memberDAO.memberDetailUpdate(map);
		}else{
			map.put("MEMBERNM", vo.getMemberNm());
			memberDAO.memberSnsDetailUpdate(map);
		}
		
		map.put("EMAILREJECT", "N");
		map.put("SMSREJECT", "N");
		// 이메일 수신 Y -> N
		if("Y".equals(vo.getEmailYnOrg()) && "N".equals(vo.getEmailYn())){
			map.put("EMAILREJECT", "Y");
			map.put("SMSCONTS", "메일");
		}
		// SMS 수신 Y -> N
		if("Y".equals(vo.getSmsYnOrg()) && "N".equals(vo.getSmsYn())){
			map.put("SMSREJECT", "Y");
			map.put("SMSCONTS", "SMS");
		}
		
		// 수신동의 철회 알림톡 (EMAIL , SMS 둘중 하나라도 거절 했으면 알림톡 발송)
		if("Y".equals(map.get("EMAILREJECT").toString()) || "Y".equals(map.get("SMSREJECT").toString())){
			if("Y".equals(map.get("EMAILREJECT").toString()) && "Y".equals(map.get("SMSREJECT").toString())){
				map.put("SMSCONTS", "메일/SMS");
			}
			
			map.put("MEMBERCODE", "MEM008");
			memberDAO.callSendSmsSmsRejectProcedure(map);
		}
	}
	
	/**
	 * @Method : memberWithdrawSave
	 * @Date		: 2017. 8. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	회원 탈퇴 저장
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public int memberWithdrawSave(MemberVO vo) throws Exception{
		int flag = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx());
		map.put("MEMBERSTATE", "900");
		map.put("REASON", vo.getReason());
		map.put("ETCREASON", vo.getEtcReason());
		map.put("ISAGREE", "T");
		
		// 1.회원 탈퇴 저장
		memberDAO.memberWithdrawSave(map);
		// 2. 회원 탈퇴 메일 발송
		MailUtil.sendMemberWithdraw(vo);		
		// 3.회원 마스터 업데이트 
		memberDAO.memberWithdrawMasterUpdate(map);		
		// 4.회원 디테일 업데이트
		memberDAO.memberWithdrawDetailUpdate(map);
		// 5.SNS 회원 정보 삭제
		memberDAO.memberWithdrawSnsDelete(map);
		// 6. SMS 알림톡 전송
		map.put("MEMBERCODE", "MEM002");
		memberDAO.callSendSmsProcedure(map);
		
		flag = 1;
		
		return flag;
	}
	
	/**
	 * @Method : getMemberAccount
	 * @Date		: 2017. 8. 7.
	 * @Author	:  유  준  철 
	 * @Description	:	환불계좌 정보
	 */
	public SqlMap getMemberAccount() throws Exception{
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx());
		
		try {
			detail = memberDAO.getMemberAccount(map);
			if(detail != null){
				detail.put("account", Crypto.decrypt((String) detail.get("account")));			// 계좌번호 복호화
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return detail;
	}
	
	/**
	 * @Method : memberAccountSave
	 * @Date		: 2017. 8. 7.
	 * @Author	:  유  준  철 
	 * @Description	:	환불계좌 저장
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public int memberAccountSave(MemberVO vo) throws Exception{
		int flag = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx());
		map.put("BANKCODE", vo.getBankCode());
		map.put("ACCOUNT", Crypto.encrypt(vo.getAccount()));							// 계좌번호 암호화
		map.put("DEPOSITOR", vo.getDepositor());
		
		flag = memberDAO.memberAccountSave(map);							
		
		return flag;
	}
	
	/**
	 * @Method : memberAccountDelete
	 * @Date		: 2017. 8. 7.
	 * @Author	:  유  준  철 
	 * @Description	:	환불계좌 삭제
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public int memberAccountDelete(MemberVO vo) throws Exception{
		int flag = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx());
		
		flag = memberDAO.memberAccountDelete(map);							
		
		return flag;
	}
	
	/**
	 * @Method : getMemberShippingList
	 * @Date		: 2017. 8. 7.
	 * @Author	:  유  준  철 
	 * @Description	:	배송지 리스트
	 */
	public List<SqlMap> getMemberShippingList(MemberVO vo) throws Exception {
		List<SqlMap> list = null;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx());
		
		try{
			list = memberDAO.getMemberShippingList(map);	
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;		
	}
	
	/**
	 * @Method : getShippingDetail
	 * @Date		: 2017. 8. 8.
	 * @Author	:  유  준  철 
	 * @Description	:	배송지 상세
	 */
	public SqlMap getShippingDetail(MemberVO vo) throws Exception{
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("ADDRESSIDX", vo.getAddressIdx());
		
		try {
			detail = memberDAO.getShippingDetail(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return detail;
	}
	
	/**
	 * @Method : memberShippingSave
	 * @Date		: 2017. 8. 8.
	 * @Author	:  유  준  철 
	 * @Description	:	배송지 저장/수정/삭제
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public int memberShippingSave(MemberVO vo) throws Exception{
		int flag = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx());
		map.put("SHIPPINGNM", vo.getShippingNm());
		map.put("RECEIVERNM", vo.getReceiverNm());
		map.put("ADDR", vo.getAddr());
		map.put("ADDRDETAIL", vo.getAddrDetail());
		map.put("ZIPCD", vo.getZipCd());
		map.put("OLDADDR", vo.getOldAddr());
		map.put("OLDZIPCD", vo.getOldZipCd());
		map.put("TELNO", vo.getTelNo());
		map.put("PHONENO", vo.getPhoneNo());
		map.put("DEFAULTYN", vo.getDefaultYn());
		
		if(vo.getStatusFlag().equals("I")){
			if(vo.getDefaultYn().equals("Y")){
				memberDAO.shippingAllDefalutUpdate(map);	
			}
			map.put("REGHTTPUSERAGENT", vo.getRegHttpUserAgent());
			map.put("REGIP", vo.getRegIp());
			
			flag = memberDAO.memberShippingInsert(map);							
		}else if(vo.getStatusFlag().equals("U")){
			if(vo.getDefaultYn().equals("Y")){
				memberDAO.shippingAllDefalutUpdate(map);	
			}
			map.put("ADDRESSIDX", vo.getAddressIdx());
			map.put("EDITHTTPUSERAGENT", vo.getEditHttpUserAgent());
			map.put("EDITIP", vo.getEditIp());
			
			flag = memberDAO.memberShippingUpdate(map);				
		}else if(vo.getStatusFlag().equals("D")){
			map.put("ADDRESSIDX", vo.getAddressIdx());
			
			flag = memberDAO.memberShippingDelete(map);	
		}
		return flag;
	}
	
	/**
	 * @Method : shippingDefalutUpdate
	 * @Date		: 2017. 8. 8.
	 * @Author	:  유  준  철 
	 * @Description	:	기본 배송지 설정
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public int shippingDefalutUpdate(MemberVO vo) throws Exception{
		int flag = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx());
		map.put("EDITHTTPUSERAGENT", vo.getEditHttpUserAgent());
		map.put("EDITIP", vo.getEditIp());
		map.put("ADDRESSIDX", vo.getAddressIdx());
		
		memberDAO.shippingAllDefalutUpdate(map);	
		memberDAO.shippingDefalutUpdate(map);	
		
		flag = 1;
		
		return flag;
	}
	
	/**
	 * @Method : joinCompleteDetail
	 * @Date		: 2018. 3. 8.
	 * @Author	:  유  준  철 
	 * @Description	:	회원 가입 완료 상세
	 */
	public SqlMap joinCompleteDetail(MemberVO vo) throws Exception{
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("MEMBERID", vo.getMemberId());
		
		try {
			detail = memberDAO.joinCompleteDetail(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return detail;
	}
}
