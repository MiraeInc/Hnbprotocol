package com.gxenSoft.mall.event.event.service;

import java.util.List;

import com.gxenSoft.mall.event.event.vo.EventVO;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : EventService
    * PACKAGE NM : com.gxenSoft.mall.event.event.service
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 7. 20. 
    * HISTORY :   
    *
    *************************************
    */
public interface EventService {

	
	/**
	    * @Method : getEventBannerList
	    * @Date: 2017. 7. 20.
	    * @Author : 임  재  형
	    * @Description	:	이벤트 배너 리스트
	   */
	List<SqlMap> getEventBannerList() throws Exception;

	/**
	    * @Method : getIngEventList
	    * @Date: 2017. 7. 20.
	    * @Author : 임  재  형
	    * @Description	:	진행 중 이벤트 리스트
	   */
	List<SqlMap> getIngEventList(EventVO vo) throws Exception;

	/**
	    * @Method : getEndEventListCnt
	    * @Date: 2017. 7. 20.
	    * @Author : 임  재  형
	    * @Description	:	종료 된 이벤트 리스트 총 개수
	   */
	int getEndEventListCnt() throws Exception;
	
	/**
	    * @Method : getEndEventList
	    * @Date: 2017. 7. 20.
	    * @Author : 임  재  형
	    * @Description	:	종료 된 이벤트 리스트
	   */
	List<SqlMap> getEndEventList(SearchVO schVO) throws Exception;

	/**
	    * @Method : getEventDetail
	    * @Date: 2017. 7. 20.
	    * @Author : 임  재  형
	    * @Description	:	이벤트 상세
	   */
	SqlMap getEventDetail(EventVO vo) throws Exception;

	/**
	    * @Method : getEventReplyListCnt
	    * @Date: 2017. 7. 20.
	    * @Author : 임  재  형
	    * @Description	:	이벤트 댓글 리스트 총 개수
	   */
	int getEventReplyListCnt(EventVO vo) throws Exception;

	/**
	    * @Method : getSampleReplyList
	    * @Date: 2017. 7. 20.
	    * @Author : 임  재  형
	    * @Description	:	이벤트 댓글 리스트
	   */
	List<SqlMap> getEventReplyList(EventVO vo, SearchVO schVO) throws Exception;

	/**
	    * @Method : eventReplySave
	    * @Date: 2017. 7. 20.
	    * @Author : 임  재  형
	    * @Description	:	이벤트 댓글 등록
	   */
	int eventReplySave(EventVO vo) throws Exception;

	/**
	    * @Method : eventReplyDeleteAjax
	    * @Date: 2017. 7. 20.
	    * @Author : 임  재  형
	    * @Description	:	이벤트 댓글 삭제
	   */
	int eventReplyDeleteAjax(EventVO vo, int memberIdx) throws Exception;

	/**
	    * @Method : couponBookList
	    * @Date: 2017. 7. 20.
	    * @Author :  강 병 철
	    * @Description	: 쿠폰북 리스트
	   */
	List<SqlMap> couponBookList(SearchVO schVO) throws Exception;
	
	/**
	 * @Method : couponBookListCnt
	 * @Date		: 2018. 2. 21.
	 * @Author	:  임  재  형 
	 * @Description	:	쿠폰북 리스트 총 개수
	 */
	int couponBookListCnt() throws Exception;

	/**
	    * @Method : couponDownloadAjax
	    * @Date: 2017. 7. 20.
	    * @Author :  강 병 철
	    * @Description	: 쿠폰다운로드
	   */
	SqlMap couponDownloadAjax(int memberIdx, int couponIdx) throws Exception;
	
	/**
	    * @Method : couponAllDownAjax
	    * @Date: 2017. 7. 20.
	    * @Author :  강 병 철
	    * @Description	: 쿠폰 전체 다운로드
	   */
	SqlMap couponAllDownAjax(int memberIdx) throws Exception;

	/**
	 * @Method : eventVoteSave
	 * @Date		: 2019. 4. 18.
	 * @Author	:  임  재  형 
	 * @Description	:	이벤트 투표하기
	 */
	int eventVoteSave(int eventIdx, int voteNum) throws Exception;

	/**
	 * @Method : getVoteNum
	 * @Date		: 2019. 4. 18.
	 * @Author	:  임  재  형 
	 * @Description	:	이벤트 투표 카운트 가져오기
	 */
	SqlMap getVoteNum(int eventIdx) throws Exception;

}
