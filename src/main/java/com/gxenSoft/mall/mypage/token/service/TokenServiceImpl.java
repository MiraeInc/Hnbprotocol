package com.gxenSoft.mall.mypage.token.service;

import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.member.dao.MemberDAO;
import com.gxenSoft.mall.mypage.review.dao.ReviewDAO;
import com.gxenSoft.mall.mypage.token.dao.TokenDAO;
import com.gxenSoft.mall.mypage.token.vo.TokenRequest;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("tokenService")
public class TokenServiceImpl implements TokenService {
	
	static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);
	
	@Autowired
	private TokenDAO tokenDAO;

	@Autowired
	private ReviewDAO reviewDAO;

	public int getTokenListCnt(SearchVO schVO) throws Exception {
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("SCHSUBTYPE", schVO.getSchSubType()); 		// 유형
		map.put("SCHSTARTDT", schVO.getSchStartDt());		// 검색 기간 시작일		
		map.put("SCHENDDT", schVO.getSchEndDt());			// 검색 기간 종료일
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx()); 	// 회원 일련번호
		
		try{
			cnt = tokenDAO.getTokenListCnt(map); // 포인트 리스트 총 개수
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}

	public List<SqlMap> getTokenList(SearchVO schVO) throws Exception {
		schVO.setPageBlock(10);
		
		List<SqlMap> pointList = null;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("SCHSUBTYPE", schVO.getSchSubType()); 				// 유형
		map.put("SCHSTARTDT", schVO.getSchStartDt());		// 검색 기간 시작일		
		map.put("SCHENDDT", schVO.getSchEndDt());			// 검색 기간 종료일
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx()); 	// 회원 일련번호
		
		map.put("PAGESTART", ((schVO.getPageNo() - 1) * schVO.getPageBlock()));
		map.put("PAGEBLOCK", schVO.getPageBlock());
		map.put("PAGENO", schVO.getPageNo());
		
		try{
			pointList = tokenDAO.getTokenList(map); // 포인트 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return pointList;
	}

	@Override
	@Transactional(propagation= Propagation.REQUIRED, rollbackFor={Exception.class})
	public void tokenWriteOk(TokenRequest tokenRequest, int totalPoint) throws Exception {
		Integer requestPoint = tokenRequest.getRequestPoint();
		String walletAddress = tokenRequest.getWalletAddress();

		if (requestPoint == null) {
			throw new IllegalArgumentException("요청 포인트가 없습니다.");
		}

		if (walletAddress == null) {
			throw new IllegalArgumentException("지갑 주소가 없습니다.");
		}

		if (requestPoint % 100 != 0) {
			throw new IllegalArgumentException("포인트는 100포인트 단위로 신청할 수 있습니다.");
		}

		if (totalPoint < 3000) {
			throw new IllegalArgumentException("보유포인트가 3천포인트 이상이 있어야 교환신청이 가능합니다.");
		}

		Integer changeToken = requestPoint / 100;
		Integer memberIdx = UserInfo.getUserInfo().getMemberIdx();

		memberPointDraw(memberIdx, requestPoint);
		tokenDAO.tokenWriteOk(requestPoint, changeToken, walletAddress, memberIdx);
	}

	private void memberPointDraw(Integer memberIdx, Integer requestPoint) throws Exception {
		HashMap<String, Object> memberPointMinusParam = new HashMap<>();
		memberPointMinusParam.put("MEMBERIDX", memberIdx);
		memberPointMinusParam.put("REVIEWPOINT", requestPoint);
		reviewDAO.memberPointMinus(memberPointMinusParam);

		HashMap<String, Object> memberPointAddHistoryParam = new HashMap<>();
		memberPointAddHistoryParam.put("MEMBERIDX", memberIdx);
		memberPointAddHistoryParam.put("PAYDEDREASON", "POINT_REASON240");	// 코인 교환 요청에 따른 포인트 차감
		memberPointAddHistoryParam.put("REVIEWPOINT", requestPoint);
		reviewDAO.memberPointMinusHistory(memberPointAddHistoryParam);
	}

}
