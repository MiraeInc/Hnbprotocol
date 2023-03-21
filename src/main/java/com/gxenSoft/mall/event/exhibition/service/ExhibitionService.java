package com.gxenSoft.mall.event.exhibition.service;

import java.util.List;

import com.gxenSoft.mall.event.exhibition.vo.ExhibitionVO;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : ExhibitionService
    * PACKAGE NM : com.gxenSoft.mall.event.exhibition.service
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 7. 21. 
    * HISTORY :   
    *
    *************************************
    */
public interface ExhibitionService {

	
	/**
	    * @Method : getExhibitionBannerList
	    * @Date: 2017. 7. 21.
	    * @Author : 임  재  형
	    * @Description	:	기획전 배너 리스트
	   */
	List<SqlMap> getExhibitionBannerList() throws Exception;

	/**
	    * @Method : getExhibitionList
	    * @Date: 2017. 7. 21.
	    * @Author : 임  재  형
	    * @Description	:	기획전 리스트
	   */
	List<SqlMap> getExhibitionList(ExhibitionVO vo, SearchVO schVO) throws Exception;

	/**
	    * @Method : getExhibitionDetail
	    * @Date: 2017. 7. 24.
	    * @Author : 임  재  형
	    * @Description	:	기획전 상세
	   */
	SqlMap getExhibitionDetail(ExhibitionVO vo) throws Exception;

	/**
	    * @Method : getHashtagList
	    * @Date: 2017. 7. 24.
	    * @Author : 임  재  형
	    * @Description	:	기획전 해시태그 리스트
	   */
	List<SqlMap> getHashtagList(ExhibitionVO vo) throws Exception;

	/**
	    * @Method : getBestList
	    * @Date: 2017. 7. 24.
	    * @Author : 임  재  형
	    * @Description	:	베스트 기획전 리스트
	   */
	List<SqlMap> getBestList(ExhibitionVO vo) throws Exception;

	/**
	    * @Method : getExhibitionGoodsList
	    * @Date: 2017. 7. 24.
	    * @Author : 임  재  형
	    * @Description	:	기획전 상품 리스트
	   */
	List<SqlMap> getExhibitionGoodsList(ExhibitionVO vo) throws Exception;

	/**
	    * @Method : getExhibitionGroupList
	    * @Date: 2017. 7. 24.
	    * @Author : 임  재  형
	    * @Description	:	기획전 그룹 리스트
	   */
	List<SqlMap> getExhibitionGroupList(ExhibitionVO vo) throws Exception;

}
