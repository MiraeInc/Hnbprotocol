package com.gxenSoft.mall.etc.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gxenSoft.sqlDAO.CommonDefaultDAO;
import com.gxenSoft.sqlMap.SqlMap;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : CommonDAO
 * PACKAGE NM : com.gatsbyMall.common.dao
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 6. 14. 
 * HISTORY :
 
 *************************************
 */
@Repository("etcDAO")
public class EtcDAO extends CommonDefaultDAO{
	

	/**
	 * @Method : productSearchResult
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	상품 검색 결과
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getProductList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("etcDAO.getProductList", param);
	}
	
	/**
	 * @Method : getProductListCnt
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	상품 검색 개수 결과
	 */
	public int getProductListCnt(HashMap<String, Object> param)throws Exception{
		return (Integer)selectListTotCnt("etcDAO.getProductListCnt", param);
	}
	/**
	 * @Method : insertKeywordLog
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	상품 검색 개수 결과
	 */
	public int insertKeywordLog(HashMap<String, Object> param)throws Exception{
		return (Integer)insert("etcDAO.insertKeywordLog", param);
	}
	
	/**
	 * @Method : getMainHashtagList
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	main 노출 해시태그
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getMainHashtagList() throws Exception{
		return (List<SqlMap>)selectList("etcDAO.getMainHashtagList", null);
	}
	/**
	 * @Method : getExhibitList
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	기획전 검색 결과
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getExhibitList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("etcDAO.getExhibitList", param);
	}
	
	/**
	 * @Method : getExhibitListCnt
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	기획전 검색 개수 결과
	 */
	public int getExhibitListCnt(HashMap<String, Object> param)throws Exception{
		return (Integer)selectListTotCnt("etcDAO.getExhibitListCnt", param);
	}

	/**
	 * @Method : getTipList
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	스타일팁  검색 결과
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getTipList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("etcDAO.getTipList", param);
	}
	
	/**
	 * @Method : getTipListCnt
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	스타일팁 검색 개수 결과
	 */
	public int getTipListCnt(HashMap<String, Object> param)throws Exception{
		return (Integer)selectListTotCnt("etcDAO.getTipListCnt", param);
	}

	/**
	 * @Method : getBrandAdList
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	광고  검색 결과
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getBrandAdList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("etcDAO.getBrandAdList", param);
	}
	
	/**
	 * @Method : getBrandAdListCnt
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	광고 검색 개수 결과
	 */
	public int getBrandAdListCnt(HashMap<String, Object> param)throws Exception{
		return (Integer)selectListTotCnt("etcDAO.getBrandAdListCnt", param);
	}

	/**
	 * @Method : getMagazineList
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	매거진  검색 결과
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getMagazineList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("etcDAO.getMagazineList", param);
	}
	
	/**
	 * @Method : getMagazineListCnt
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	매거진 검색 개수 결과
	 */
	public int getMagazineListCnt(HashMap<String, Object> param)throws Exception{
		return (Integer)selectListTotCnt("etcDAO.getMagazineListCnt", param);
	}

	/**
	    * @Method : getReviewList
	    * @Date: 2017. 8. 12.
	    * @Author : 임  재  형
	    * @Description	:	후기 검색 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getReviewList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("etcDAO.getReviewList", param);
	}

	/**
	    * @Method : getReviewCnt
	    * @Date: 2017. 8. 12.
	    * @Author : 임  재  형
	    * @Description	:	후기 리스트 총 개수
	   */
	public int getReviewCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("etcDAO.getReviewCnt", param);
	}

	/**
	 * @Method : getGnbHashList
	 * @Date		: 2018. 8. 29.
	 * @Author	:  임  재  형 
	 * @Description	:	GNB 해시태그 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getGnbHashList() throws Exception {
		return (List<SqlMap>)selectList("etcDAO.getGnbHashList", null);
	}
	
}
