package com.gxenSoft.mall.brand.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gxenSoft.sqlDAO.CommonDefaultDAO;
import com.gxenSoft.sqlMap.SqlMap;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : BrandDAO
 * PACKAGE NM : com.gxenSoft.mall.brand.dao
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 7. 28. 
 * HISTORY :
 
 *************************************
 */
@Repository("brandDAO")
public class BrandDAO extends CommonDefaultDAO{
	
	/**
	 * @Method : getBrandAdList
	 * @Date		: 2017. 7. 28.
	 * @Author	:  유  준  철 
	 * @Description	:	광고(AD) 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getBrandAdList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("brandDAO.getBrandAdList", param);
	}
	
	/**
	 * @Method : getBrandAdListCnt
	 * @Date		: 2017. 7. 28.
	 * @Author	:  유  준  철 
	 * @Description	:	광고(AD) 총 건수
	 */
	public int getBrandAdListCnt(HashMap<String, Object> param)throws Exception{
		return (Integer)selectListTotCnt("brandDAO.getBrandAdListCnt", param);
	}
	
	/**
	 * @Method : getBrandMagazineList
	 * @Date		: 2017. 7. 28.
	 * @Author	:  유  준  철 
	 * @Description	:	매거진 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getBrandMagazineList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("brandDAO.getBrandMagazineList", param);
	}
	
	/**
	 * @Method : getBrandMagazineListCnt
	 * @Date		: 2017. 7. 28.
	 * @Author	:  유  준  철 
	 * @Description	:	매거진 총 건수
	 */
	public int getBrandMagazineListCnt(HashMap<String, Object> param)throws Exception{
		return (Integer)selectListTotCnt("brandDAO.getBrandMagazineListCnt", param);
	}
	
	/**
	 * @Method : getBrandMagazineDetail
	 * @Date		: 2017. 7. 28.
	 * @Author	:  유  준  철 
	 * @Description	:	매거진 상세
	 */
	public SqlMap getBrandMagazineDetail(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("brandDAO.getBrandMagazineDetail", param);
	}
	
	/**
	 * @Method : getBrandMagazinePrev
	 * @Date		: 2017. 7. 28.
	 * @Author	:  유  준  철 
	 * @Description	:	매거진 상세 (이전글)
	 */
	public SqlMap getBrandMagazinePrev(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("brandDAO.getBrandMagazinePrev", param);
	}
	
	/**
	 * @Method : getBrandMagazineNext
	 * @Date		: 2017. 7. 28.
	 * @Author	:  유  준  철 
	 * @Description	:	매거진 상세 (다음글)
	 */
	public SqlMap getBrandMagazineNext(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("brandDAO.getBrandMagazineNext", param);
	}

	/**
	 * @Method : getAdBrandCnt
	 * @Date		: 2018. 9. 13.
	 * @Author	:  임  재  형 
	 * @Description	:	광고 브랜드 별 개수
	 */
	public SqlMap getAdBrandCnt(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("brandDAO.getAdBrandCnt", param);
	}

	/**
	 * @Method : getMagBrandCnt
	 * @Date		: 2018. 9. 13.
	 * @Author	:  임  재  형 
	 * @Description	:	매거진 브랜드 별 개수
	 */
	public SqlMap getMagBrandCnt(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("brandDAO.getMagBrandCnt", param);
	}
}
