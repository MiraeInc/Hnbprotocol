package com.gxenSoft.mall.etc.service;

import java.util.List;

import com.gxenSoft.mall.etc.vo.SearchConditionVO;
import com.gxenSoft.sqlMap.SqlMap;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : CommonService
 * PACKAGE NM : com.gatsbyMall.common.service
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 6. 14. 
 * HISTORY :
 
 *************************************
 */
public interface EtcService {

	/**
	 * @Method : insertKeywordLog
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	검색어 로그 저장
	 */
	public void insertKeywordLog(String keyword, Integer memberIdx, String UserAgent, String UserIp, String Device)throws Exception;
	
	/**
	 * @Method : productSearchResult
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	상품검색 결과
	 */
	public List<SqlMap> productSearchResult(SearchConditionVO vo)throws Exception;
	

	/**
	 * @Method : productSearchCnt
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	상품검색결과 개수
	 */
	public int productSearchCnt(SearchConditionVO vo)throws Exception;
	
	/**
	 * @Method : getMainHashtagList
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	main 노출 해시태그
	 */
	public List<SqlMap> getMainHashtagList()throws Exception;
	
	/**
	 * @Method : getExhibitList
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	기획전 검색
	 */
	public List<SqlMap> getExhibitList(SearchConditionVO vo)throws Exception;
	
	/**
	 * @Method : getExhibitListCnt
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	기획전 검색 개수
	 */
	public int getExhibitListCnt(SearchConditionVO vo)throws Exception;
	

	/**
	    * @Method : getTipList
	    * @Date: 2017. 7. 14.
	    * @Author :  강 병 철
	    * @Description	:	스타일 팁 검색 리스트
	   */
	public List<SqlMap> getTipList(SearchConditionVO schVO) throws Exception;

	/**
	    * @Method : getTipListCnt
	    * @Date: 2017. 7. 14.
	    * @Author :  강 병 철
	    * @Description	:	스타일 팁  검색 리스트 총 개수
	   */
	public int getTipListCnt(SearchConditionVO schVO) throws Exception ;
	
	/**
	    * @Method : getBrandAdList
	    * @Date: 2017. 7. 14.
	    * @Author :  강 병 철
	    * @Description	:	광고 검색 리스트
	   */
	public List<SqlMap> getBrandAdList(SearchConditionVO vo) throws Exception;

	/**
	    * @Method : getBrandAdListCnt
	    * @Date: 2017. 7. 14.
	    * @Author :  강 병 철
	    * @Description	:	광고 검색 리스트 총 개수
	   */
	public int getBrandAdListCnt(SearchConditionVO schVO) throws Exception ;

	/**
	    * @Method : getMagazineList
	    * @Date: 2017. 7. 14.
	    * @Author :  강 병 철
	    * @Description	:	매거진 검색 리스트
	   */
	public List<SqlMap> getMagazineList(SearchConditionVO vo) throws Exception;

	/**
	    * @Method : getMagazineListCnt
	    * @Date: 2017. 7. 14.
	    * @Author :  강 병 철
	    * @Description	:	매거진 리스트 총 개수
	   */
	public int getMagazineListCnt(SearchConditionVO schVO) throws Exception ;

	/**
	    * @Method : getReviewList
	    * @Date: 2017. 8. 12.
	    * @Author : 임  재  형
	    * @Description	:	후기 검색 리스트
	   */
	public List<SqlMap> getReviewList(SearchConditionVO vo) throws Exception;

	/**
	    * @Method : getReviewCnt
	    * @Date: 2017. 8. 12.
	    * @Author : 임  재  형
	    * @Description	:	후기 리스트 총 개수
	   */
	public int getReviewCnt(SearchConditionVO vo) throws Exception;

	/**
	 * @Method : getGnbHashList
	 * @Date		: 2018. 8. 29.
	 * @Author	:  임  재  형 
	 * @Description	:	GNB 해시태그 리스트
	 */
	public List<SqlMap> getGnbHashList() throws Exception;

}
