package com.gxenSoft.mall.product.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gxenSoft.sqlDAO.CommonDefaultDAO;
import com.gxenSoft.sqlMap.SqlMap;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : ProductDAO
 * PACKAGE NM : com.gxenSoft.mall.product.dao
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 6. 29. 
 * HISTORY :
 
 *************************************
 */
@Repository("productDAO")
public class ProductDAO extends CommonDefaultDAO{
	
	/**
	 * @Method : getProductList
	 * @Date		: 2017. 6. 29.
	 * @Author	:  유  준  철 
	 * @Description	:	상품 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getProductList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("productDAO.getProductList", param);
	}
	
	/**
	 * @Method : getProductListCnt
	 * @Date		: 2017. 6. 30.
	 * @Author	:  유  준  철 
	 * @Description	:	상품 리스트 총 건수
	 */
	public int getProductListCnt(HashMap<String, Object> param)throws Exception{
		return (Integer)selectListTotCnt("productDAO.getProductListCnt", param);
	}

	/**
	 * @Method : getTotalProductList
	 * @Date		: 2017. 6. 29.
	 * @Author	:  강 병 철
	 * @Description	:	통합상품 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getTotalProductList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("productDAO.getTotalProductList", param);
	}
	
	/**
	 * @Method : getTotalProductListCnt
	 * @Date		: 2017. 6. 30.
	 * @Author	:  강 병 철
	 * @Description	:	통합상품 리스트 총 건수
	 */
	public int getTotalProductListCnt(HashMap<String, Object> param)throws Exception{
		return (Integer)selectListTotCnt("productDAO.getTotalProductListCnt", param);
	}
	/**
	 * @Method : getCategoryNavi
	 * @Date		: 2017. 6. 30.
	 * @Author	:  유  준  철 
	 * @Description	:	상품 네이게이션
	 */
	public SqlMap getCategoryNavi(HashMap<String, Object> param)throws Exception{
		return (SqlMap)selectOne("productDAO.getCategoryNavi", param);
	}
	/**
	 * @Method : getTotalCategoryNavi
	 * @Date		: 2017. 6. 30.
	 * @Author	:  강병철
	 * @Description	:	상품 통합 네이게이션
	 */
	public SqlMap getTotalCategoryNavi(HashMap<String, Object> param)throws Exception{
		return (SqlMap)selectOne("productDAO.getTotalCategoryNavi", param);
	}
	
	
	/**
	 * @Method : getProductHashList
	 * @Date		: 2017. 7. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	해시태그 리스트 (상품 상세)
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getProductHashList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("productDAO.getProductHashList", param);
	}
	
	/**
	 * @Method : getProductDetail
	 * @Date		: 2017. 7. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	상품 상세
	 */
	public SqlMap getProductDetail(HashMap<String, Object> param)throws Exception{
		return (SqlMap)selectOne("productDAO.getProductDetail", param);
	}
	
	/**
	 * @Method : getBestProductList
	 * @Date		: 2017. 7. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	베스트 상품 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getBestProductList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("productDAO.getBestProductList", param);
	}
	
	/**
	 * @Method : getCategoryBestProduct
	 * @Date		: 2017. 7. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	best (상품 상세)
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getCategoryBestProduct(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("productDAO.getCategoryBestProduct", param);
	}
	
	/**
	 * @Method : getWishProduct
	 * @Date		: 2017. 7. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	wish (상품 상세)
	 */
	public SqlMap getWishProduct(HashMap<String, Object> param)throws Exception{
		return (SqlMap)selectOne("productDAO.getWishProduct", param);
	}
	
	/**
	 * @Method : getRecommendProduct
	 * @Date		: 2017. 7. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	recommend (상품 상세)
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getRecommendProduct(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("productDAO.getRecommendProduct", param);
	}
	
	/**
	 * @Method : getSetProduct
	 * @Date		: 2017. 7. 5.
	 * @Author	:  유  준  철 
	 * @Description	:	set (상품 상세)
	 */
	public SqlMap getSetProduct(HashMap<String, Object> param)throws Exception{
		return (SqlMap)selectOne("productDAO.getSetProduct", param);
	}
	
	/**
	 * @Method : getOptionProductList
	 * @Date		: 2017. 7. 5.
	 * @Author	:  유  준  철 
	 * @Description	:	옵션 상품 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getOptionProductList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("productDAO.getOptionProductList", param);
	}
	
	/**
	 * @Method : getProductImgList
	 * @Date		: 2017. 7. 5.
	 * @Author	:  유  준  철 
	 * @Description	:	상품 이미지 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getProductImgList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("productDAO.getProductImgList", param);
	}
	
	/**
	 * @Method : getProductExhibitionList
	 * @Date		: 2017. 7. 7.
	 * @Author	:  유  준  철 
	 * @Description	:	상품 기획전 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getProductExhibitionList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("productDAO.getProductExhibitionList", param);
	}
	
	/**
	 * @Method : productViewCntUpdate
	 * @Date		: 2017. 7. 17.
	 * @Author	:  유  준  철 
	 * @Description	:	상품 조회 수 업데이트
	 */
	public int productViewCntUpdate(HashMap<String, Object> param)throws Exception{
		return (Integer)update("productDAO.productViewCntUpdate", param);
	}
	
	/**
	 * @Method : getStylingMovieCnt
	 * @Date		: 2017. 7. 17.
	 * @Author	:  유  준  철 
	 * @Description	:	스타일링 무비, 매거진 건수, HOW TO USE, 리뷰 건수 , 리뷰 평균
	 */
	public SqlMap getProductSubInfo(HashMap<String, Object> param)throws Exception{
		return (SqlMap)selectOne("productDAO.getProductSubInfo", param);
	}
	
	/**
	 * @Method : getStyleInfo
	 * @Date		: 2017. 7. 31.
	 * @Author	:  유  준  철 
	 * @Description	:	스타일링 정보
	 */
	public SqlMap getStyleInfo(HashMap<String, Object> param)throws Exception{
		return (SqlMap)selectOne("productDAO.getStyleInfo", param);
	}
	
	/**
	 * @Method : getMagazineInfo
	 * @Date		: 2017. 7. 31.
	 * @Author	:  유  준  철 
	 * @Description	:	매거진 정보
	 */
	public SqlMap getMagazineInfo(HashMap<String, Object> param)throws Exception{
		return (SqlMap)selectOne("productDAO.getMagazineInfo", param);
	}
	
	/**
	 * @Method : getBlogList
	 * @Date		: 2017. 8. 1.
	 * @Author	:  유  준  철 
	 * @Description	:	블로그 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getBlogList() throws Exception{
		return (List<SqlMap>)selectList("productDAO.getBlogList", null);
	}
	
	/**
	 * @Method : getHairStylingProductList
	 * @Date		: 2017. 9. 1.
	 * @Author	:  유  준  철 
	 * @Description	:	헤어스타일링 메인 상품 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getHairStylingProductList() throws Exception{
		return (List<SqlMap>)selectList("productDAO.getHairStylingProductList", null);
	}
	
	/**
	 * @Method : getHairStylingNoWaxtList
	 * @Date		: 2017. 9. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	헤어스타일링 메인 왁스(핸디 X) 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getHairStylingNoWaxtList() throws Exception{
		return (List<SqlMap>)selectList("productDAO.getHairStylingNoWaxtList", null);
	}
	
	/**
	 * @Method : getHairStylingWaxtList
	 * @Date		: 2017. 9. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	헤어스타일링 메인 왁스(핸디 O) 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getHairStylingWaxtList() throws Exception{
		return (List<SqlMap>)selectList("productDAO.getHairStylingWaxtList", null);
	}
	
	/**
	 * @Method : productBestViewInsert
	 * @Date		: 2017. 9. 7.
	 * @Author	:  유  준  철 
	 * @Description	:	베스트 상품관리 조회 점수 저장
	 */
	public int productBestViewInsert(HashMap<String, Object> param)throws Exception{
		return (Integer)insert("productDAO.productBestViewInsert", param);
	}
	
	/**
	 * @Method : getProductSubImgList
	 * @Date		: 2018. 2. 7.
	 * @Author	:  유  준  철 
	 * @Description	:	상품 목록 오버시 이미지 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getProductSubImgList() throws Exception{
		return (List<SqlMap>)selectList("productDAO.getProductSubImgList", null);
	}
	
	/**
	 * @Method : getProductOriginDetail
	 * @Date		: 2018. 6. 20
	 * @Author	:  강병철
	 * @Description	:	상품 상세 (npay 상품정보요청)
	 */
	public SqlMap getProductOriginDetail(HashMap<String, Object> param)throws Exception{
		return (SqlMap)selectOne("productDAO.getProductOriginDetail", param);
	}
	

	/**
	 * @Method : getProductOriginDetail
	 * @Date		: 2018. 6. 20
	 * @Author	:  강병철
	 * @Description	:	상품 판매중인정보 상세 (npay 상품정보요청)
	 */
	public SqlMap getProductSalesDetail(HashMap<String, Object> param)throws Exception{
		return (SqlMap)selectOne("productDAO.getProductSalesDetail", param);
	}
	
    /**
     * @Method      : retrieveGoodsOptionList
     * @Date        : 2021. 09. 24.
     * @Author      : dsjeong
     * @Description : 상품옵션항목 등록
     * @HISTORY     :
     *
     */
    @SuppressWarnings("unchecked")
    public List<SqlMap> retrieveGoodsOptionList(HashMap<String, Object> param)throws Exception{
        return (List<SqlMap>)selectList("productDAO.retrieveGoodsOptionList", param);
    }
    
    /**
     * @Method      : retrieveGoodsOptionItemList
     * @Date        : 2021. 09. 24.
     * @Author      : dsjeong
     * @Description : 상품옵션항목 등록
     * @HISTORY     :
     *
     */
    @SuppressWarnings("unchecked")
    public List<SqlMap> retrieveGoodsOptionItemList(HashMap<String, Object> param)throws Exception{
        return (List<SqlMap>)selectList("productDAO.retrieveGoodsOptionItemList", param);
    }
}
