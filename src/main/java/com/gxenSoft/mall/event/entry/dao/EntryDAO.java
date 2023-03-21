package com.gxenSoft.mall.event.entry.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gxenSoft.sqlDAO.CommonDefaultDAO;
import com.gxenSoft.sqlMap.SqlMap;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : EntryDAO
 * PACKAGE NM : com.gxenSoft.gatsbyMall.event.entry.dao
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 8. 22. 
 * HISTORY :
 
 *************************************
 */
@Repository("entryDAO")
public class EntryDAO extends CommonDefaultDAO {
	
	/**
	 * @Method : entryNoCheck
	 * @Date		: 2017. 8. 22.
	 * @Author	:  유  준  철 
	 * @Description	:	응모 번호 체크
	 */
	public int entryNoCheck(HashMap<String, Object> param)throws Exception{
		return (Integer)selectListTotCnt("entryDAO.entryNoCheck", param);
	}
	
	/**
	 * @Method : entryEventSave
	 * @Date		: 2017. 8. 22.
	 * @Author	:  유  준  철 
	 * @Description	:	응모 저장
	 */
	public int entryEventSave(HashMap<String, Object> param)throws Exception{
		return insert("entryDAO.entryEventSave", param);
	}
	
	public int entryEventUpdate(HashMap<String, Object> param)throws Exception{
		return update("entryDAO.entryEventUpdate", param);
	}
	
	/**
	 * @Method : predictEventSave
	 * @Date		: 2018. 1. 22.
	 * @Author	:  유  준  철 
	 * @Description	:	토종비결 저장
	 */
	public int predictEventSave(HashMap<String, Object> param)throws Exception{
		return insert("entryDAO.predictEventSave", param);
	}

	/**
	 * @Method : procSpPgCheckNaverEvent
	 * @Date		: 2018. 4. 18.
	 * @Author	:  서  정  길 
	 * @Description	:	네이버 타임보드 이벤트 (2018-04-23 회원 가입자 대상)용 - 구매시 유효성 체크
	 */
	public SqlMap procSpPgCheckNaverEvent(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("entryDAO.procSpPgCheckNaverEvent", param);
	}

	/**
	 * @Method : day100ringCheck
	 * @Date		: 2018. 5. 31.
	 * @Author	:  임  재  형 
	 * @Description	:	커플링 신청 중복 체크
	 */
	public int day100ringCheck(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("entryDAO.day100ringCheck", param);
	}

	/**
	 * @Method : day100ringSave
	 * @Date		: 2018. 5. 31.
	 * @Author	:  임  재  형 
	 * @Description	:	커플링 신청
	 */
	public int day100ringSave(HashMap<String, Object> param) throws Exception {
		return (Integer)insert("entryDAO.day100ringSave", param);
	}

	/**
	 * @Method : get100dayReplyList
	 * @Date		: 2018. 5. 31.
	 * @Author	:  임  재  형 
	 * @Description	:	100일 이벤트 댓글 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> get100dayReplyList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("entryDAO.get100dayReplyList", param);
	}

	/**
	 * @Method : get100dayReplyListCnt
	 * @Date		: 2018. 5. 31.
	 * @Author	:  임  재  형 
	 * @Description	:	100일 이벤트 댓글 리스트 총 개수 --> 2018 리뉴얼 이벤트
	 */
	public int get100dayReplyListCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("entryDAO.get100dayReplyListCnt", param);
	}

	/**
	 * @Method : renewReplySave
	 * @Date		: 2018. 9. 6.
	 * @Author	:  임  재  형 
	 * @Description	:	2018 리뉴얼 이벤트 댓글 저장 --> 2018 리뉴얼 이벤트
	 */
	public int renewReplySave(HashMap<String, Object> param) throws Exception {
		return (Integer)insert("entryDAO.renewReplySave", param);
	}
	
	/**
	 * @Method : renewQuizCheck
	 * @Date		:  2018. 9. 6.
	 * @Author	:  강병철
	 * @Description	:	2018 리뉴얼 퀴즈 이벤트 참가여부체크
	 */
	public int renewQuizCheck(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("entryDAO.renewQuizCheck", param);
	}

	/**
	 * @Method : renewQuizSave
	 * @Date		:   2018. 9. 6.
	 * @Author	:  강병철
	 * @Description	:	2018 리뉴얼 퀴즈 이벤트 참가 저장
	 */
	public int renewQuizSave(HashMap<String, Object> param) throws Exception {
		return (Integer)insert("entryDAO.renewQuizSave", param);
	}


	/**
	 * @Method : renewQuizList
	 * @Date		:  2018. 9. 6.
	 * @Author	:  강병철
	 * @Description	:	2018 리뉴얼 퀴즈 이벤트 참가 목록
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> renewQuizList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("entryDAO.renewQuizList", param);
	}

	/**
	 * @Method : creatorSave
	 * @Date		: 2019. 6. 17.
	 * @Author	:  임  재  형 
	 * @Description	:	크리에이터 제출
	 */
	public int creatorSave(HashMap<String, Object> param) throws Exception {
		return (Integer)insert("entryDAO.creatorSave", param);
	}

	/**
	 * @Method : getGoodsDetail
	 * @Date		: 2019. 10. 30.
	 * @Author	:  임  재  형 
	 * @Description	:	상품 정보
	 */
	public SqlMap getGoodsDetail(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("entryDAO.getGoodsDetail", param);
	}
	
	/**
	 * @Method : entryNo2020Check
	 * @Date		: 2020. 02. 17.
	 * @Author	:  강 병 철
	 * @Description	:	응모 번호 체크
	 */
	public int entryNo2020Check(HashMap<String, Object> param)throws Exception{
		return (Integer)selectListTotCnt("entryDAO.entryNo2020Check", param);
	}
	
	/**
	 * @Method : entryEvent2020Save
	 * @Date		: 2020. 02. 17.
	 * @Author	:  강 병 철
	 * @Description	:	벚꽃 이벤트 응모 저장
	 */
	public int entryEvent2020Save(HashMap<String, Object> param)throws Exception{
		return insert("entryDAO.entryEvent2020Save", param);
	}
	
	public int entryNumber2020Update(HashMap<String, Object> param)throws Exception{
		return update("entryDAO.entryNumber2020Update", param);
	}
	
}
