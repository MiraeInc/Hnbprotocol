package com.gxenSoft.mall.mypage.token.service;

import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.mypage.point.dao.PointDAO;
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
	private PointDAO pointDAO;

	public int getPointListCnt(SearchVO schVO) throws Exception {
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("SCHSUBTYPE", schVO.getSchSubType()); 				// 유형
		map.put("SCHSTARTDT", schVO.getSchStartDt());		// 검색 기간 시작일		
		map.put("SCHENDDT", schVO.getSchEndDt());			// 검색 기간 종료일
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx()); 	// 회원 일련번호
		
		try{
			cnt = pointDAO.getPointListCnt(map); // 포인트 리스트 총 개수
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}

	public List<SqlMap> getPointList(SearchVO schVO) throws Exception {
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
			pointList = pointDAO.getPointList(map); // 포인트 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return pointList;
	}

	public int getSumPoint(SearchVO schVO, int totalCount) throws Exception {
		int sumPoint = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("SCHTYPE", schVO.getSchType()); 				// 유형
		map.put("SCHSTARTDT", schVO.getSchStartDt()); 	// 시작 일
		map.put("SCHENDDT", schVO.getSchEndDt()); 		// 종료 일
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx()); 	// 회원 일련번호
		
		map.put("PAGESTART", ((schVO.getPageNo() - 1) * schVO.getPageBlock()));
		map.put("TOTALCOUNT", totalCount);
		
		try{
			sumPoint = pointDAO.getSumPoint(map); // 페이지 적용 포인트 합
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return sumPoint;
	}

	public int getTotalPoint() throws Exception {
		int totalPoint = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx()); 	// 회원 일련번호
		
		try{
			totalPoint = pointDAO.getTotalPoint(map); // 보유 포인트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return totalPoint;
	}

	public SqlMap getSpPointDeduct(String nextMonth) throws Exception {
		SqlMap detail = null;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("DEDUCT_DAY", nextMonth); 	
		map.put("INTERVAL", 24); 	
		map.put("MEMBER_IDX", UserInfo.getUserInfo().getMemberIdx()); 
		map.put("PROCESS_GBN", "V");
		try{
			detail = pointDAO.getSpPointDeduct(map); // 포인트 조회 프로시져
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return detail;
	}

	public int getUsableCouponCnt(SearchVO schVO) throws Exception {
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("MEMBER_IDX", UserInfo.getUserInfo().getMemberIdx()); 
		cnt = pointDAO.getUsableCouponCnt(map); // 포인트 리스트 총 개수
		return cnt;
	}

	public List<SqlMap> getUsableCouponList(SearchVO schVO) throws Exception {
		List<SqlMap> cList = null;
		HashMap<String, Object> map = new HashMap<>();
		map.put("MEMBER_IDX", UserInfo.getUserInfo().getMemberIdx()); 
		map.put("PAGESTART", ((schVO.getPageNo() - 1) * schVO.getPageBlock()));
		map.put("PAGEBLOCK", schVO.getPageBlock());
		map.put("PAGENO", schVO.getPageNo());
		cList = pointDAO.getUsableCouponList(map); 
		return cList;
	}

	public int getSevenExpireCouponCnt(SearchVO schVO) throws Exception {
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("MEMBER_IDX", UserInfo.getUserInfo().getMemberIdx()); 
		cnt = pointDAO.getSevenExpireCouponCnt(map); // 포인트 리스트 총 개수
		return cnt;
	}

	public List<SqlMap> getSevenExpireCouponList(SearchVO schVO) throws Exception {
		List<SqlMap> cList = null;
		HashMap<String, Object> map = new HashMap<>();
		map.put("MEMBER_IDX", UserInfo.getUserInfo().getMemberIdx()); 
		map.put("PAGESTART", ((schVO.getPageNo() - 1) * schVO.getPageBlock()));
		map.put("PAGEBLOCK", schVO.getPageBlock());
		map.put("PAGENO", schVO.getPageNo());
		cList = pointDAO.getSevenExpireCouponList(map); 
		return cList;
	}

	public int getExpireCouponCnt(SearchVO schVO) throws Exception {
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("MEMBER_IDX", UserInfo.getUserInfo().getMemberIdx()); 
		cnt = pointDAO.getExpireCouponCnt(map); // 포인트 리스트 총 개수
		return cnt;
	}

	public List<SqlMap> getExpireCouponList(SearchVO schVO) throws Exception {
		List<SqlMap> cList = null;
		HashMap<String, Object> map = new HashMap<>();
		map.put("MEMBER_IDX", UserInfo.getUserInfo().getMemberIdx()); 
		map.put("PAGESTART", ((schVO.getPageNo() - 1) * schVO.getPageBlock()));
		map.put("PAGEBLOCK", schVO.getPageBlock());
		map.put("PAGENO", schVO.getPageNo());
		cList = pointDAO.getExpireCouponList(map); 
		return cList;
	}
}
