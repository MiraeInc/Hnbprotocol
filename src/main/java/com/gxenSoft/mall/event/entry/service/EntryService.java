package com.gxenSoft.mall.event.entry.service;

import java.util.List;

import com.gxenSoft.mall.event.entry.vo.CreatorVO;
import com.gxenSoft.mall.event.entry.vo.EntryPredictVO;
import com.gxenSoft.mall.event.entry.vo.EntryVO;
import com.gxenSoft.mall.event.event.vo.EventVO;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : EntryService
 * PACKAGE NM : com.gxenSoft.gatsbyMall.event.entry.service
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 8. 22. 
 * HISTORY :
 
 *************************************
 */
public interface EntryService {
	
	/**
	 * @Method : entryNoCheck
	 * @Date		: 2017. 8. 22.
	 * @Author	:  유  준  철 
	 * @Description	:	응모 번호 체크
	 */
	public int entryNoCheck(EntryVO vo)throws Exception;
	
	/**
	 * @Method : entryEventSave
	 * @Date		: 2017. 8. 22.
	 * @Author	:  유  준  철 
	 * @Description	:	응모 저장
	 */
	public void entryEventSave(EntryVO vo)throws Exception;
	
	/**
	 * @Method : predictEventSaveAjax
	 * @Date		: 2018. 1. 22.
	 * @Author	:  유  준  철 
	 * @Description	:	토종비결 저장
	 */
	public int predictEventSave(EntryPredictVO vo)throws Exception;

	/**
	 * @Method : procSpPgCheckNaverEvent
	 * @Date		: 2018. 4. 18.
	 * @Author	:  서  정  길 
	 * @Description	:	네이버 타임보드 이벤트 (2018-04-23 회원 가입자 대상)용 - 구매시 유효성 체크
	 */
	public SqlMap procSpPgCheckNaverEvent(String memberIdx) throws Exception;

	/**
	 * @Method : day100ringSave
	 * @Date		: 2018. 5. 31.
	 * @Author	:  임  재  형 
	 * @Description	:	커플링 신청
	 */
	public int day100ringSave(EventVO vo) throws Exception;

	/**
	 * @Method : getEventReplyList
	 * @Date		: 2018. 5. 31.
	 * @Author	:  임  재  형 
	 * @Description	:	100일 이벤트 댓글 리스트 --> 2018 리뉴얼 이벤트
	 */
	public List<SqlMap> get100dayReplyList(EventVO vo, SearchVO schVO) throws Exception;

	/**
	 * @Method : getEventReplyListCnt
	 * @Date		: 2018. 5. 31.
	 * @Author	:  임  재  형 
	 * @Description	:	100일 이벤트 댓글 리스트 총 개수 --> 2018 리뉴얼 이벤트
	 */
	public int get100dayReplyListCnt(EventVO vo, SearchVO schVO) throws Exception;
	
	/**
	 * @Method : renewReplySave
	 * @Date		: 2018. 9. 6.
	 * @Author	:  임  재  형 
	 * @Description	:	2018 리뉴얼 이벤트 댓글 저장
	 */
	int renewReplySave(EventVO vo) throws Exception;

	/**
	 * @Method : renewQuizCheck
	 * @Date		: 2018. 9. 6.
	 * @Author	:  강병철
	 * @Description	:	2018 리뉴얼 퀴즈 이벤트 정답체크
	 */
	int renewQuizCheck(int memberIdx, String quizNo, String answerNo, int eventNo) throws Exception ;
	

	/**
	 * @Method : renewQuizSave
	 * @Date		: 2018. 9. 6.
	 * @Author	:  강병철
	 * @Description	:	2018 리뉴얼 퀴즈 이벤트 참가 저장
	 */
	int renewQuizSave(int memberIdx, String quizNo, String answerNo, int eventNo) throws Exception ;

	/**
	 * @Method : renewQuizList
	 * @Date		: 2018. 5. 31.
	 * @Author	:  강병철
	 * @Description	:	2018 리뉴얼 퀴즈 이벤트 참가 목록
	 */
	List<SqlMap> renewQuizList(int memberIdx, int eventNo)  throws Exception;

	/**
	 * @Method : creatorSave
	 * @Date		: 2019. 6. 17.
	 * @Author	:  임  재  형 
	 * @Description	:	크리에이터 제출
	 */
	public int creatorSave(CreatorVO vo) throws Exception;

	/**
	 * @Method : getGoodsDetail
	 * @Date		: 2019. 10. 30.
	 * @Author	:  임  재  형 
	 * @Description	:	상품 정보
	 */
	public SqlMap getGoodsDetail(String goodsCd) throws Exception;
	

	/**
	 * @Method : entryNo2020Check
	 * @Date		: 2020.02.17
	 * @Author	:  강 병 철
	 * @Description	:	벚꽃 이벤트 응모 번호 체크
	 */
	public int entryNo2020Check(EntryVO vo)throws Exception;
	
	/**
	 * @Method : blossomEventSave
	 * @Date		: 2020. 02. 17.
	 * @Author	:  강 병 철
	 * @Description	:	벚꽃 이벤트 응모 저장
	 */
	public void blossomEventSave(EntryVO vo)throws Exception;
	
}
