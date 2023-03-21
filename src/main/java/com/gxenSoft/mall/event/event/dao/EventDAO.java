package com.gxenSoft.mall.event.event.dao;


import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gxenSoft.sqlDAO.CommonDefaultDAO;
import com.gxenSoft.sqlMap.SqlMap;

/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : EventDAO
    * PACKAGE NM : com.gxenSoft.mall.event.event.dao
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 7. 20. 
    * HISTORY :   
    *
    *************************************
    */
@Repository("eventDAO")
public class EventDAO extends CommonDefaultDAO{

	
	/**
	    * @Method : getEventBannerList
	    * @Date: 2017. 7. 20.
	    * @Author : 임  재  형
	    * @Description	:	이벤트 배너 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getEventBannerList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("eventDAO.getEventBannerList", param);
	}
	
	/**
	    * @Method : getIngEventList
	    * @Date: 2017. 7. 20.
	    * @Author : 임  재  형
	    * @Description	:	진행 중 이벤트 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getIngEventList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("eventDAO.getIngEventList", param);
	}
	
	/**
	    * @Method : getEndEventListCnt
	    * @Date: 2017. 7. 20.
	    * @Author : 임  재  형
	    * @Description	:	종료 된 이벤트 리스트 총 개수
	   */
	public int getEndEventListCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("eventDAO.getEndEventListCnt", param);
	}

	/**
	    * @Method : getEndEventList
	    * @Date: 2017. 7. 20.
	    * @Author : 임  재  형
	    * @Description	:	종료 된 이벤트 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getEndEventList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("eventDAO.getEndEventList", param);
	}

	/**
	    * @Method : getEventDetail
	    * @Date: 2017. 7. 20.
	    * @Author : 임  재  형
	    * @Description	:	이벤트 상세
	   */
	public SqlMap getEventDetail(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("eventDAO.getEventDetail", param);
	}
	
	/**
	    * @Method : addEventReadCnt
	    * @Date: 2017. 7. 24.
	    * @Author : 임  재  형
	    * @Description	:	이벤트 조회 수 증가
	   */
	public void addEventReadCnt(HashMap<String, Object> param) throws Exception {
		update("eventDAO.addEventReadCnt", param);
	}

	/**
	    * @Method : getEventReplyListCnt
	    * @Date: 2017. 7. 20.
	    * @Author : 임  재  형
	    * @Description	:	이벤트 댓글 리스트 총 개수
	   */
	public int getEventReplyListCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("eventDAO.getEventReplyListCnt", param);
	}

	/**
	    * @Method : getEventReplyList
	    * @Date: 2017. 7. 20.
	    * @Author : 임  재  형
	    * @Description	:	이벤트 댓글 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getEventReplyList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("eventDAO.getEventReplyList", param);
	}

	/**
	    * @Method : replyCheck
	    * @Date: 2017. 7. 20.
	    * @Author : 임  재  형
	    * @Description	:	댓글 중복 체크
	   */
	public int replyCheck(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("eventDAO.replyCheck", param);
	}

	/**
	    * @Method : eventReplySave
	    * @Date: 2017. 7. 20.
	    * @Author : 임  재  형
	    * @Description	:	이벤트 댓글 등록
	   */
	public int eventReplySave(HashMap<String, Object> param) throws Exception {
		return (Integer)insert("eventDAO.eventReplySave", param);
	}

	/**
	    * @Method : eventReplyDeleteAjax
	    * @Date: 2017. 7. 20.
	    * @Author : 임  재  형
	    * @Description	:	이벤트 댓글 삭제
	   */
	public int eventReplyDeleteAjax(HashMap<String, Object> param) throws Exception {
		return (Integer)delete("eventDAO.eventReplyDeleteAjax", param);
	}
	
	/**
	    * @Method : couponBookList
	    * @Date: 2017. 7. 20.
	    * @Author : 강 병 철
	    * @Description	:	쿠폰북 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> couponBookList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("eventDAO.couponBookList", param);
	}
	
	/**
	 * @Method : couponBookListCnt
	 * @Date		: 2018. 2. 21.
	 * @Author	:  임  재  형 
	 * @Description	:	쿠폰북 리스트 총 개수
	 */
	public int couponBookListCnt() throws Exception {
		return (Integer)selectListTotCnt("eventDAO.couponBookListCnt", null);
	}

	
	/**
	    * @Method : couponDownload
	    * @Date: 2017. 7. 20.
	    * @Author : 강 병 철
	    * @Description	:	쿠폰북 다운로드 1개
	   */
	public SqlMap couponDownload(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("eventDAO.procCouponDownload", param);
	}


	/**
	    * @Method : couponAllDownload
	    * @Date: 2017. 7. 20.
	    * @Author : 강 병 철
	    * @Description	:	쿠폰북 다운로드 전체
	   */
	public SqlMap couponAllDownload(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("eventDAO.procCouponAllDownload", param);
	}

	/**
	 * @Method : eventVoteSave
	 * @Date		: 2019. 4. 18.
	 * @Author	:  임  재  형 
	 * @Description	:	이벤트 투표하기
	 */
	public int eventVoteSave(HashMap<String, Object> param) throws Exception {
		return (Integer)insert("eventDAO.eventVoteSave", param);
	}

	/**
	 * @Method : getVoteNum
	 * @Date		: 2019. 4. 18.
	 * @Author	:  임  재  형 
	 * @Description	:	이벤트 투표 카운트 가져오기
	 */
	public SqlMap getVoteNum(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("eventDAO.getVoteNum", param);
	}

}
