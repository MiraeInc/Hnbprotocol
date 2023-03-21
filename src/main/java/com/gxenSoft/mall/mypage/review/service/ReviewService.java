package com.gxenSoft.mall.mypage.review.service;

import java.util.List;

import com.gxenSoft.mall.mypage.review.vo.ReviewVO;
import com.gxenSoft.mall.product.vo.SchProductVO;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : ReviewService
 * PACKAGE NM : com.gxenSoft.mall.mypage.review.service
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 7. 26. 
 * HISTORY :
 
 *************************************
 */
public interface ReviewService {
	
	/**
	 * @Method : getReviewList
	 * @Date		: 2017. 7. 26.
	 * @Author	:  유  준  철 
	 * @Description	:	리뷰 리스트
	 */
	public List<SqlMap> getReviewList(ReviewVO vo, SchProductVO schProductVo) throws Exception;
	
	/**
	 * @Method : getReviewCnt
	 * @Date		: 2017. 7. 26.
	 * @Author	:  유  준  철 
	 * @Description	:	리뷰 총 건수
	 */
	public int getReviewCnt(ReviewVO vo)throws Exception;
	
	/**
	 * @Method : getPhotoCnt
	 * @Date		: 2017. 7. 26.
	 * @Author	:  유  준  철 
	 * @Description	:	포토 리뷰 총 건수
	 */
	public int getPhotoCnt(ReviewVO vo)throws Exception;
	
	/**
	 * @Method : getReviewAvg
	 * @Date		: 2017. 8. 17.
	 * @Author	:  유  준  철 
	 * @Description	:	구매 만족도 평균 별표시
	 */
	public int getReviewAvg(ReviewVO vo)throws Exception;
	
	/**
	 * @Method : getReviewDetail
	 * @Date		: 2017. 7. 31.
	 * @Author	:  유  준  철 
	 * @Description	:	리뷰 상세
	 */
	public SqlMap getReviewDetail(ReviewVO vo)throws Exception;

	/**
	    * @Method : reviewSave
	    * @Date: 2017. 8. 7.
	    * @Author : 임  재  형
	    * @Description	:	리뷰 등록
	   */
	public int reviewSave(ReviewVO vo) throws Exception;

	/**
	    * @Method : getNoWriteReviewCnt
	    * @Date: 2017. 8. 8.
	    * @Author : 임  재  형
	    * @Description	:	작성 가능 한 리뷰 총 개수
	   */
	public int getNoWriteReviewCnt() throws Exception;

	/**
	    * @Method : getWriteListCnt
	    * @Date: 2017. 8. 8.
	    * @Author : 임  재  형
	    * @Description	:	작성 한 리뷰 총 개수
	   */
	public int getWriteListCnt() throws Exception;
	
	/**
	    * @Method : getNoWriteList
	    * @Date: 2017. 8. 9.
	    * @Author : 임  재  형
	    * @Description	:	작성 가능한 리뷰 리스트
	   */
	public List<SqlMap> getNoWriteList(SearchVO schVO) throws Exception;

	/**
	    * @Method : getWriteList
	    * @Date: 2017. 8. 8.
	    * @Author : 임  재  형
	    * @Description	:	작성 한 리뷰 리스트
	   */
	public List<SqlMap> getWriteList(SearchVO schVO) throws Exception;

	/**
	    * @Method : reviewDelete
	    * @Date: 2017. 8. 8.
	    * @Author : 임  재  형
	    * @Description	:	리뷰 삭제
	   */
	public int reviewDelete(ReviewVO vo) throws Exception;

	/**
	    * @Method : getGoodsDetail
	    * @Date: 2017. 8. 9.
	    * @Author : 임  재  형
	    * @Description	:	상품 정보
	   */
	public SqlMap getGoodsDetail(ReviewVO vo) throws Exception;

	/**
	    * @Method : getOrderDetail
	    * @Date: 2017. 8. 25.
	    * @Author : 임  재  형
	    * @Description	:	주문 디테일 정보
	   */
	public SqlMap getOrderDetail(int orderDetailIdx, int memberIdx) throws Exception;

	/**
	    * @Method : getWinnerDetail
	    * @Date: 2017. 8. 25.
	    * @Author : 임  재  형
	    * @Description	:	당첨자 정보
	   */
	public SqlMap getWinnerDetail(int winnerIdx, int memberIdx) throws Exception;
	
}
