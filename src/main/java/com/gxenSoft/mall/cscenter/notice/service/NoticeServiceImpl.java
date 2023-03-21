package com.gxenSoft.mall.cscenter.notice.service;


import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gxenSoft.mall.cscenter.notice.dao.NoticeDAO;
import com.gxenSoft.mall.cscenter.notice.vo.NoticeVO;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : NoticeServiceImpl
    * PACKAGE NM : com.gxenSoft.mall.cscenter.notice.service
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 7. 25. 
    * HISTORY :   
    *
    *************************************
    */
@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
	
	static final Logger logger = LoggerFactory.getLogger(NoticeServiceImpl.class);
	
	@Autowired
	private NoticeDAO noticeDAO;
	
	
	/**
	    * @Method : getNoticeTopListCnt
	    * @Date: 2017. 8. 16.
	    * @Author :  임  재  형
	    * @Description	:	공지사항 Top 리스트 개수
	   */
	public int getNoticeTopListCnt(SearchVO schVO) throws Exception {
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("SCHCHECK", schVO.getSchCheck());			// 전체, 공지, 뉴스, 이벤트, 당첨자발표
		map.put("SCHSUBTYPE", schVO.getSchSubType());	// 검색 조건 [전체, 제목, 내용]
		map.put("SCHVALUE", schVO.getSchValue());			// 검색 어
		
		try{
			cnt = noticeDAO.getNoticeTopListCnt(map); // 공지사항 Top 리스트 총 개수
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}

	/**
	    * @Method : getNoticeTopList
	    * @Date: 2017. 7. 25.
	    * @Author :  임  재  형
	    * @Description	:	공지사항 Top 리스트
	   */
	public List<SqlMap> getNoticeTopList(SearchVO schVO) throws Exception {
		List<SqlMap> list = null;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("SCHCHECK", schVO.getSchCheck());			// 전체, 공지, 뉴스, 이벤트, 당첨자발표
		map.put("SCHSUBTYPE", schVO.getSchSubType());	// 검색 조건 [전체, 제목, 내용]
		map.put("SCHVALUE", schVO.getSchValue());			// 검색 어
		
		try{
			list = noticeDAO.getNoticeTopList(map); // 공지사항 Top 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;	
	}
	
	/**
	    * @Method : getNoticeListCnt
	    * @Date: 2017. 7. 25.
	    * @Author :  임  재  형
	    * @Description	:	공지사항 리스트 총 개수
	   */
	public int getNoticeListCnt(SearchVO schVO) throws Exception {
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("SCHCHECK", schVO.getSchCheck());			// 전체, 공지, 뉴스, 이벤트, 당첨자발표
		map.put("SCHSUBTYPE", schVO.getSchSubType());	// 검색 조건 [전체, 제목, 내용]
		map.put("SCHVALUE", schVO.getSchValue());			// 검색 어
		
		try{
			cnt = noticeDAO.getNoticeListCnt(map); // 공지사항 리스트 총 개수
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}
	
	/**
	    * @Method : getNoticeList
	    * @Date: 2017. 7. 25.
	    * @Author :  임  재  형
	    * @Description	:	공지사항 리스트
	   */
	public List<SqlMap> getNoticeList(SearchVO schVO) throws Exception {
		List<SqlMap> list = null;
		schVO.setPageBlock(10);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("SCHCHECK", schVO.getSchCheck()); 			// 전체, 공지, 뉴스, 이벤트, 당첨자발표
		map.put("SCHSUBTYPE", schVO.getSchSubType());	// 검색 조건 [전체, 제목, 내용]
		map.put("SCHVALUE", schVO.getSchValue());			// 검색 어
		
		map.put("PAGESTART", ((schVO.getPageNo() - 1) * schVO.getPageBlock()));
		map.put("PAGEBLOCK", schVO.getPageBlock());
		map.put("PAGENO", schVO.getPageNo());
		
		try{
			list = noticeDAO.getNoticeList(map); // 공지사항 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;	
	}
	
	/**
	    * @Method : getNoticeDetail
	    * @Date: 2017. 7. 26.
	    * @Author :  임  재  형
	    * @Description	:	공지사항 상세
	   */
	public SqlMap getNoticeDetail(NoticeVO vo) throws Exception {
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("NOTICEIDX", vo.getNoticeIdx()); // 공지사항 일련번호
		
		try{
			detail = noticeDAO.getNoticeDetail(map); // 공지사항 상세
			noticeDAO.addNoticeReadCnt(map);	// 공지사항 조회 수 증가
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return detail;
	}

	/**
	    * @Method : getPrevNotice
	    * @Date: 2017. 7. 26.
	    * @Author :  임  재  형
	    * @Description	:	이전 게시글
	   */
	public SqlMap getPrevNotice(NoticeVO vo, SearchVO schVO) throws Exception {
		SqlMap prev = null;	
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("NOTICEIDX", vo.getNoticeIdx());				// 일련번호
		map.put("SCHCHECK", schVO.getSchCheck());			// 게시판 타입 [전체, 공지, 뉴스, 이벤트, 당첨자 발표]
		map.put("SCHSUBTYPE", schVO.getSchSubType());	// 검색 조건 [전체, 제목, 내용]
		map.put("SCHVALUE", schVO.getSchValue());			// 검색 어
		
		try{
			prev = noticeDAO.getPrevNotice(map); // 이전 공지사항 게시글
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return prev;
	}

	/**
	    * @Method : getNextNotice
	    * @Date: 2017. 7. 26.
	    * @Author :  임  재  형
	    * @Description	:	다음 게시글
	   */
	public SqlMap getNextNotice(NoticeVO vo, SearchVO schVO) throws Exception {
		SqlMap next = null;	
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("NOTICEIDX", vo.getNoticeIdx()); 				// 일련번호
		map.put("SCHCHECK", schVO.getSchCheck());			// 게시판 타입 [전체, 공지, 뉴스, 이벤트, 당첨자 발표]
		map.put("SCHSUBTYPE", schVO.getSchSubType());	// 검색 조건 [전체, 제목, 내용]
		map.put("SCHVALUE", schVO.getSchValue());			// 검색 어
		
		try{
			next = noticeDAO.getNextNotice(map); // 다음 공지사항 게시글
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return next;
	}

	/**
	    * @Method : getNoticeFileList
	    * @Date: 2017. 8. 29.
	    * @Author :  임  재  형
	    * @Description	:	공지사항 파일 리스트
	   */
	public List<SqlMap> getNoticeFileList(NoticeVO vo) throws Exception {
		List<SqlMap> list = null;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("NOTICEIDX", vo.getNoticeIdx()); 	// 일련번호
		map.put("TABLEGUBUN", "N");					// 테이블 구분
		
		try{
			list = noticeDAO.getNoticeFileList(map); // 공지사항 파일 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;	
	}

	/**
	    * @Method : getMainNoticeList
	    * @Date: 2017. 8. 30.
	    * @Author :  임  재  형
	    * @Description	:	메인 공지사항 리스트
	   */
	public List<SqlMap> getMainNoticeList(SearchVO schVO) throws Exception {
		List<SqlMap> list = null;

		try{
			list = noticeDAO.getMainNoticeList(); // 메인 공지사항 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;	
	}

	/**
	 * @Method : getFooterNotice
	 * @Date		: 2018. 2. 22.
	 * @Author	:  임  재  형 
	 * @Description	:	푸터 공지사항
	 */
	public SqlMap getFooterNotice() throws Exception {
		SqlMap detail = null;	
		
		try{
			detail = noticeDAO.getFooterNotice(); // 푸터 공지사항
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return detail;
	}

}
