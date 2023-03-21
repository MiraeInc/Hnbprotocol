package com.gxenSoft.mall.mypage.review.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gxenSoft.sqlDAO.CommonDefaultDAO;
import com.gxenSoft.sqlMap.SqlMap;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : ReviewDAO
 * PACKAGE NM : com.gxenSoft.mall.mypage.review.dao
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 7. 26. 
 * HISTORY :
 
 *************************************
 */
@Repository("reviewDAO")
public class ReviewDAO extends CommonDefaultDAO{
	
	/**
	 * @Method : getReviewList
	 * @Date		: 2017. 7. 26.
	 * @Author	:  유  준  철 
	 * @Description	:	리뷰 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getReviewList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("reviewDAO.getReviewList", param);
	}
	
	/**
	 * @Method : getReviewCnt
	 * @Date		: 2017. 7. 26.
	 * @Author	:  유  준  철 
	 * @Description	:	리뷰 총 건수
	 */
	public int getReviewCnt(HashMap<String, Object> param)throws Exception{
		return (Integer)selectListTotCnt("reviewDAO.getReviewCnt", param);
	}
	
	/**
	 * @Method : getPhotoCnt
	 * @Date		: 2017. 7. 26.
	 * @Author	:  유  준  철 
	 * @Description	:	포토 리뷰 총 건수
	 */
	public int getPhotoCnt(HashMap<String, Object> param)throws Exception{
		return (Integer)selectListTotCnt("reviewDAO.getPhotoCnt", param);
	}
	
	/**
	 * @Method : getReviewAvg
	 * @Date		: 2017. 8. 17.
	 * @Author	:  유  준  철 
	 * @Description	:	구매 만족도 평균 별표시
	 */
	public int getReviewAvg(HashMap<String, Object> param)throws Exception{
		return (Integer)selectListTotCnt("reviewDAO.getReviewAvg", param);
	}
	
	/**
	 * @Method : getReviewDetail
	 * @Date		: 2017. 7. 31.
	 * @Author	:  유  준  철 
	 * @Description	:	리뷰 상세
	 */
	public SqlMap getReviewDetail(HashMap<String, Object> param)throws Exception{
		return (SqlMap)selectOne("reviewDAO.getReviewDetail", param);
	}

	/**
	    * @Method : reviewSave
	    * @Date: 2017. 8. 7.
	    * @Author : 임  재  형
	    * @Description	:	리뷰 등록
	   */
	public int reviewSave(HashMap<String, Object> param) throws Exception {
		return (Integer)insert("reviewDAO.reviewSave", param);
	}

	/**
	    * @Method : reviewImgInsert
	    * @Date: 2017. 8. 7.
	    * @Author : 임  재  형
	    * @Description	:	리뷰 이미지 등록
	   */
	public int reviewImgInsert(HashMap<String, Object> param) throws Exception {
		return (Integer)update("reviewDAO.reviewImgInsert", param);
	}

	/**
	    * @Method : reviewCheck
	    * @Date: 2017. 8. 7.
	    * @Author : 임  재  형
	    * @Description	:	당첨자 리뷰 중복 체크
	   */
	public int reviewCheck(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("reviewDAO.reviewCheck", param);
	}

	/**
	    * @Method : getNoWriteReviewCnt
	    * @Date: 2017. 8. 8.
	    * @Author : 임  재  형
	    * @Description	:	작성 가능 한 리뷰 총 개수
	   */
	public int getNoWriteReviewCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("reviewDAO.getNoWriteReviewCnt", param);
	}

	/**
	    * @Method : getWriteListCnt
	    * @Date: 2017. 8. 8.
	    * @Author : 임  재  형
	    * @Description	:	작성 한 리뷰 총 개수
	   */
	public int getWriteListCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("reviewDAO.getWriteListCnt", param);
	}

	/**
	    * @Method : getNoWriteList
	    * @Date: 2017. 8. 9.
	    * @Author : 임  재  형
	    * @Description	:	작성 가능한 리뷰 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getNoWriteList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("reviewDAO.getNoWriteList", param);
	}
	
	/**
	    * @Method : getWriteList
	    * @Date: 2017. 8. 8.
	    * @Author : 임  재  형
	    * @Description	:	작성 한 리뷰 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getWriteList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("reviewDAO.getWriteList", param);
	}

	/**
	    * @Method : reviewDelete
	    * @Date: 2017. 8. 8.
	    * @Author : 임  재  형
	    * @Description	:	리뷰 삭제
	   */
	public int reviewDelete(HashMap<String, Object> param) throws Exception {
		return (Integer)delete("reviewDAO.reviewDelete", param);
	}

	/**
	    * @Method : memberPointUpdate
	    * @Date: 2017. 8. 8.
	    * @Author : 임  재  형
	    * @Description	:	회원 보유 포인트 증가
	   */
	public int memberPointAdd(HashMap<String, Object> param) throws Exception {
		return (Integer)update("reviewDAO.memberPointAdd", param);
	}

	/**
	    * @Method : memberPointHistory
	    * @Date: 2017. 8. 8.
	    * @Author : 임  재  형
	    * @Description	:	회원 포인트 증가 히스토리 추가
	   */
	public int memberPointAddHistory(HashMap<String, Object> param) throws Exception {
		return (Integer)insert("reviewDAO.memberPointAddHistory", param);
	}
	
	/**
	    * @Method : memberPointMinus
	    * @Date: 2017. 8. 8.
	    * @Author : 임  재  형
	    * @Description	:	회원 보유 포인트 차감
	   */
	public int memberPointMinus(HashMap<String, Object> param) throws Exception {
		return (Integer)update("reviewDAO.memberPointMinus", param);
	}

	/**
	    * @Method : memberPointMinusHistory
	    * @Date: 2017. 8. 8.
	    * @Author : 임  재  형
	    * @Description	:	회원 포인트 차감 히스토리 추가
	   */
	public int memberPointMinusHistory(HashMap<String, Object> param) throws Exception {
		return (Integer)insert("reviewDAO.memberPointMinusHistory", param);
	}

	/**
	    * @Method : getGoodsDetail
	    * @Date: 2017. 8. 9.
	    * @Author : 임  재  형
	    * @Description	:	상품 정보
	   */
	public SqlMap getGoodsDetail(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("reviewDAO.getGoodsDetail", param);
	}

	/**
	    * @Method : reviewUpdate
	    * @Date: 2017. 8. 9.
	    * @Author : 임  재  형
	    * @Description	:	리뷰 수정
	   */
	public int reviewUpdate(HashMap<String, Object> param) throws Exception {
		return (Integer)update("reviewDAO.reviewUpdate", param);
	}

	/**
	    * @Method : addViewCnt
	    * @Date: 2017. 8. 9.
	    * @Author : 임  재  형
	    * @Description	:	조회 수 증가
	   */
	public int addViewCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)update("reviewDAO.addViewCnt", param);
	}

	/**
	    * @Method : reviewDisplayDelete
	    * @Date: 2017. 8. 14.
	    * @Author : 임  재  형
	    * @Description	:	메인 전시 관리 리뷰 삭제
	   */
	public int reviewDisplayDelete(HashMap<String, Object> param) throws Exception {
		return (Integer)delete("reviewDAO.reviewDisplayDelete", param);
	}

	/**
	    * @Method : getOrderDetail
	    * @Date: 2017. 8. 25.
	    * @Author : 임  재  형
	    * @Description	:	주문 디테일 정보
	   */
	public SqlMap getOrderDetail(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("reviewDAO.getOrderDetail", param);
	}

	/**
	    * @Method : getWinnerDetail
	    * @Date: 2017. 8. 25.
	    * @Author : 임  재  형
	    * @Description	:	당첨자 정보
	   */
	public SqlMap getWinnerDetail(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("reviewDAO.getWinnerDetail", param);
	}

}
