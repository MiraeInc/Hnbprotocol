package com.gxenSoft.mall.brand.service;

import java.util.List;

import com.gxenSoft.mall.brand.vo.BrandVO;
import com.gxenSoft.mall.product.vo.SchProductVO;
import com.gxenSoft.sqlMap.SqlMap;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : BrandService
 * PACKAGE NM : com.gxenSoft.mall.brand.service
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 7. 28. 
 * HISTORY :
 
 *************************************
 */
public interface BrandService {
	
	/**
	 * @Method : getBrandAdList
	 * @Date		: 2017. 7. 28.
	 * @Author	:  유  준  철 
	 * @Description	:	광고(AD) 리스트
	 */
	public List<SqlMap> getBrandAdList(BrandVO vo, SchProductVO schProductVo) throws Exception;
	
	/**
	 * @Method : getBrandAdListCnt
	 * @Date		: 2017. 7. 28.
	 * @Author	:  유  준  철 
	 * @Description	:	광고(AD) 총 건수
	 */
	public int getBrandAdListCnt(BrandVO vo)throws Exception;
	
	/**
	 * @Method : getBrandMagazineList
	 * @Date		: 2017. 7. 28.
	 * @Author	:  유  준  철 
	 * @Description	:	매거진 리스트
	 */
	public List<SqlMap> getBrandMagazineList(BrandVO vo, SchProductVO schProductVo) throws Exception;
	
	/**
	 * @Method : getBrandMagazineListCnt
	 * @Date		: 2017. 7. 28.
	 * @Author	:  유  준  철 
	 * @Description	:	매거진 총 건수
	 */
	public int getBrandMagazineListCnt(BrandVO vo)throws Exception;
	
	/**
	 * @Method : getBrandMagazineDetail
	 * @Date		: 2017. 7. 28.
	 * @Author	:  유  준  철 
	 * @Description	:	매거진 상세
	 */
	public SqlMap getBrandMagazineDetail(BrandVO vo) throws Exception;
	
	/**
	 * @Method : getBrandMagazinePrev
	 * @Date		: 2017. 7. 28.
	 * @Author	:  유  준  철 
	 * @Description	:	매거진 상세 (이전글)
	 */
	public SqlMap getBrandMagazinePrev(String magazineIdx) throws Exception;
	
	/**
	 * @Method : getBrandMagazineNext
	 * @Date		: 2017. 7. 28.
	 * @Author	:  유  준  철 
	 * @Description	:	매거진 상세 (다음글)
	 */
	public SqlMap getBrandMagazineNext(String magazineIdx) throws Exception;

	/**
	 * @Method : getAdBrandCnt
	 * @Date		: 2018. 9. 13.
	 * @Author	:  임  재  형 
	 * @Description	:	광고 브랜드 별 개수
	 */
	public SqlMap getAdBrandCnt(BrandVO vo) throws Exception;

	/**
	 * @Method : getMagBrandCnt
	 * @Date		: 2018. 9. 13.
	 * @Author	:  임  재  형 
	 * @Description	:	매거진 브랜드 별 개수
	 */
	public SqlMap getMagBrandCnt(BrandVO vo) throws Exception;
}
