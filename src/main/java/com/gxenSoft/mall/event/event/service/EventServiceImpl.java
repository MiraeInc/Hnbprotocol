package com.gxenSoft.mall.event.event.service;


import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.event.event.dao.EventDAO;
import com.gxenSoft.mall.event.event.vo.EventVO;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;
import com.gxenSoft.util.pathUtil.PathUtil;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : EventServiceImpl
    * PACKAGE NM : com.gxenSoft.mall.event.event.service
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 7. 20. 
    * HISTORY :   
    *
    *************************************
    */
@Service("eventService")
public class EventServiceImpl implements EventService {
	
	static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);
	
	@Autowired
	private EventDAO eventDAO;

	
	/**
	    * @Method : getEventBannerList
	    * @Date: 2017. 7. 20.
	    * @Author :  임  재  형
	    * @Description	:	이벤트 배너 리스트
	   */
	public List<SqlMap> getEventBannerList() throws Exception {
		List<SqlMap> eventBannerList = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			eventBannerList = eventDAO.getEventBannerList(map); // 이벤트 배너 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return eventBannerList;
	}

	/**
	    * @Method : getIngEventList
	    * @Date: 2017. 7. 20.
	    * @Author :  임  재  형
	    * @Description	:	진행 중 이벤트 리스트
	   */
	public List<SqlMap> getIngEventList(EventVO vo) throws Exception {
		List<SqlMap> ingEventList = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("EVENTIDX", vo.getEventIdx()); 	// 이벤트 일련번호
		map.put("BRANDIDX", vo.getBrandIdx());	// 브랜드 일련번호
		map.put("DEVICE", PathUtil.getDevice());	// 디바이스
		
		try{
			ingEventList = eventDAO.getIngEventList(map); // 진행 중 이벤트 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return ingEventList;
	}
	
	/**
	    * @Method : getEndEventListCnt
	    * @Date: 2017. 7. 20.
	    * @Author :  임  재  형
	    * @Description	:	종료 된 이벤트 리스트 총 개수
	   */
	public int getEndEventListCnt() throws Exception {
		int cnt = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			cnt = eventDAO.getEndEventListCnt(map); // 종료 된 이벤트 리스트 총 개수
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}

	/**
	    * @Method : getEndEventList
	    * @Date: 2017. 7. 20.
	    * @Author :  임  재  형
	    * @Description	:	종료 된 이벤트 리스트
	   */
	public List<SqlMap> getEndEventList(SearchVO schVO) throws Exception {
		List<SqlMap> endEventList = null;
		schVO.setPageBlock(8);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("PAGESTART", ((schVO.getPageNo() - 1) * schVO.getPageBlock()));
		map.put("PAGEBLOCK", schVO.getPageBlock());
		map.put("PAGENO", schVO.getPageNo());
		
		try{
			endEventList = eventDAO.getEndEventList(map); // 종료 된 이벤트 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return endEventList;
	}

	/**
	    * @Method : getEventDetail
	    * @Date: 2017. 7. 20.
	    * @Author :  임  재  형
	    * @Description	:	이벤트 상세
	   */
	public SqlMap getEventDetail(EventVO vo) throws Exception {
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("EVENTIDX", vo.getEventIdx()); // 이벤트 일련번호
		
		try{
			detail = eventDAO.getEventDetail(map); // 이벤트 상세
			eventDAO.addEventReadCnt(map); // 이벤트 조회 수 증가
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return detail;
	}

	/**
	    * @Method : getEventReplyListCnt
	    * @Date: 2017. 7. 20.
	    * @Author :  임  재  형
	    * @Description	:	이벤트 댓글 리스트 총 개수	
	   */
	public int getEventReplyListCnt(EventVO vo) throws Exception {
		int cnt = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("EVENTIDX", vo.getEventIdx()); // 이벤트 일련번호
		
		try{
			cnt = eventDAO.getEventReplyListCnt(map); // 이벤트 댓글 리스트 총 개수
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}

	/**
	    * @Method : getEventReplyList
	    * @Date: 2017. 7. 20.
	    * @Author :  임  재  형
	    * @Description	:	이벤트 댓글 리스트
	   */
	public List<SqlMap> getEventReplyList(EventVO vo, SearchVO schVO) throws Exception {
		List<SqlMap> list = null;
		schVO.setPageBlock(20);
		
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("EVENTIDX", vo.getEventIdx()); // 이벤트 일련번호
		map.put("SCHCHECK", schVO.getSchCheck());
		
		map.put("PAGESTART", ((schVO.getPageNo() - 1) * schVO.getPageBlock()));
		map.put("PAGEBLOCK", schVO.getPageBlock());
		map.put("PAGENO", schVO.getPageNo());
		
		try{
			list = eventDAO.getEventReplyList(map); // 이벤트 댓글 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	/**
	    * @Method : eventReplySave
	    * @Date: 2017. 7. 20.
	    * @Author :  임  재  형
	    * @Description	:	이벤트 댓글 등록
	   */
	public int eventReplySave(EventVO vo) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("REGIDX", UserInfo.getUserInfo().getMemberIdx());	// 작성자 일련번호
		map.put("REPLYCONTENTS", vo.getReplyContents());	// 내용
		map.put("EVENTIDX", vo.getEventIdx());
		
		try{
			int chkReply = eventDAO.replyCheck(map);	 // 중복 댓글 체크
			if(chkReply!=0){
				flag = -1;
			}else if(chkReply==0){
				flag = eventDAO.eventReplySave(map); // 이벤트 댓글 등록
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return flag;
	}

	/**
	    * @Method : eventReplyDeleteAjax
	    * @Date: 2017. 7. 20.
	    * @Author :  임  재  형
	    * @Description	:	이벤트 댓글 삭제
	   */
	public int eventReplyDeleteAjax(EventVO vo, int memberIdx) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("REPLYIDX", vo.getReplyIdx()); // 댓글 일련번호
		map.put("MEMBERIDX", memberIdx);		// 회원 일련번호
		
		flag = eventDAO.eventReplyDeleteAjax(map); // 이벤트 댓글 삭제
		
		return flag;
	}

	/**
	    * @Method : couponBookList
	    * @Date: 2017. 7. 20.
	    * @Author :  강 병 철
	    * @Description	: 쿠폰북 리스트
	   */
	public List<SqlMap> couponBookList(SearchVO schVO) throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(PathUtil.getDevice().equals("m")){
			map.put("PAGESTART", ((schVO.getPageNo() - 1) * schVO.getPageBlock()));
			map.put("PAGEBLOCK", schVO.getPageBlock());
			map.put("PAGENO", schVO.getPageNo());
			map.put("DEVICE", "M");
		}
		
		list = eventDAO.couponBookList(map);
		
		return list;
	}
	

	/**
	 * @Method : couponBookListCnt
	 * @Date		: 2018. 2. 21.
	 * @Author	:  임  재  형 
	 * @Description	:	쿠폰북 리스트 총 개수
	 */
	public int couponBookListCnt() throws Exception {
		int cnt = 0;
		
		try{
			cnt = eventDAO.couponBookListCnt(); // 쿠폰북 리스트 총 개수
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}
	
	/**
	    * @Method : couponDownloadAjax
	    * @Date: 2017. 7. 20.
	    * @Author :  강 병 철
	    * @Description	: 쿠폰북 > 쿠폰다운로드
	   */
	public SqlMap couponDownloadAjax(int memberIdx, int couponIdx) throws Exception {
		//1. 쿠폰을 다운로드 
		SqlMap info = new SqlMap();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("COUPONIDX", couponIdx); 
		map.put("MEMBERIDX", memberIdx);	
		info = eventDAO.couponDownload(map);
		
		return info;
	}
	
	/**
	    * @Method : couponAllDownAjax
	    * @Date: 2017. 7. 20.
	    * @Author :  강 병 철
	    * @Description	: 쿠폰북 > 쿠폰 전체 다운로드 다운로드
	   */
	public SqlMap couponAllDownAjax(int memberIdx) throws Exception {
		//1. 쿠폰을 다운로드 
		SqlMap info = new SqlMap();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("MEMBERIDX", memberIdx);	
		info = eventDAO.couponAllDownload(map);
		
		return info;
	}

	/**
	 * @Method : eventVoteSave
	 * @Date		: 2019. 4. 18.
	 * @Author	:  임  재  형 
	 * @Description	:	이벤트 투표하기
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public int eventVoteSave(int eventIdx, int voteNum) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("REGIDX", UserInfo.getUserInfo().getMemberIdx()); 	// 작성자 일련번호
		map.put("EVENTIDX", eventIdx);	// 이벤트 일련번호
		map.put("VOTENUM", voteNum);	// 투표번호
		
		try{
			flag = eventDAO.eventVoteSave(map); // 이벤트 투표하기
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return flag;
	}

	/**
	 * @Method : getVoteNum
	 * @Date		: 2019. 4. 18.
	 * @Author	:  임  재  형 
	 * @Description	:	이벤트 투표 카운트 가져오기
	 */
	public SqlMap getVoteNum(int eventIdx) throws Exception {
		SqlMap info = new SqlMap();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("EVENTIDX", eventIdx);	
		info = eventDAO.getVoteNum(map);
		
		return info;
	}

}
