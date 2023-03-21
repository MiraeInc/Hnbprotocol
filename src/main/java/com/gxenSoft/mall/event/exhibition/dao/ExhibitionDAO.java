package com.gxenSoft.mall.event.exhibition.dao;


import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gxenSoft.sqlDAO.CommonDefaultDAO;
import com.gxenSoft.sqlMap.SqlMap;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : ExhibitionDAO
    * PACKAGE NM : com.gxenSoft.mall.event.exhibition.dao
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 7. 21. 
    * HISTORY :   
    *
    *************************************
    */
@Repository("exhibitionDAO")
public class ExhibitionDAO extends CommonDefaultDAO{

	
	/**
	    * @Method : getExhibitionBannerList
	    * @Date: 2017. 7. 21.
	    * @Author : 임  재  형
	    * @Description	:	기획전 배너 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getExhibitionBannerList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("exhibitionDAO.getExhibitionBannerList", param);
	}

	/**
	    * @Method : getExhibitionList
	    * @Date: 2017. 7. 21.
	    * @Author : 임  재  형
	    * @Description	:	기획전 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getExhibitionList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("exhibitionDAO.getExhibitionList", param);
	}

	/**
	    * @Method : getExhibitionDetail
	    * @Date: 2017. 7. 24.
	    * @Author : 임  재  형
	    * @Description	:	기획전 상세
	   */
	public SqlMap getExhibitionDetail(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("exhibitionDAO.getExhibitionDetail", param);
	}
	
	/**
	    * @Method : addExhibitionReadCnt
	    * @Date: 2017. 7. 24.
	    * @Author : 임  재  형
	    * @Description	:	기획전 조회 수 증가
	   */
	public void addExhibitionReadCnt(HashMap<String, Object> param) throws Exception {
		update("exhibitionDAO.addExhibitionReadCnt", param);
	}

	/**
	    * @Method : getHashtagList
	    * @Date: 2017. 7. 24.
	    * @Author : 임  재  형
	    * @Description	:	기획전 해시태그 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getHashtagList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("exhibitionDAO.getHashtagList", param);
	}

	/**
	    * @Method : getBestList
	    * @Date: 2017. 7. 24.
	    * @Author : 임  재  형
	    * @Description	:	베스트 기획전 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getBestList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("exhibitionDAO.getBestList", param);
	}

	/**
	    * @Method : getExhibitionGoodsList
	    * @Date: 2017. 7. 24.
	    * @Author : 임  재  형
	    * @Description	:	기획전 상품 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getExhibitionGoodsList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("exhibitionDAO.getExhibitionGoodsList", param);
	}

	/**
	    * @Method : getExhibitionGroupList
	    * @Date: 2017. 7. 24.
	    * @Author : 임  재  형
	    * @Description	:	기획전 그룹 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getExhibitionGroupList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("exhibitionDAO.getExhibitionGroupList", param);
	}

}
