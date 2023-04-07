package com.gxenSoft.mall.mypage.token.service;

import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.mypage.token.dao.TokenDAO;
import com.gxenSoft.mall.mypage.token.vo.TokenRequest;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service("tokenService")
public class TokenServiceImpl implements TokenService {
	
	static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);
	
	@Autowired
	private TokenDAO tokenDAO;

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
	public void tokenWriteOk(TokenRequest tokenRequest) {

	}

}
