package com.gxenSoft.mall.product.service;

import java.util.List;
import com.gxenSoft.mall.product.vo.ProductVO;
import com.gxenSoft.mall.product.vo.SchProductVO;
import com.gxenSoft.sqlMap.SqlMap;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : ProductService
 * PACKAGE NM : com.gxenSoft.mall.product.service
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 6. 29. 
 * HISTORY :
 
 *************************************
 */
public interface ProductService {
	
	/**
	 * @Method : getProductList
	 * @Date		: 2017. 6. 29.
	 * @Author	:  유  준  철 
	 * @Description	:	상품 리스트
	 */
	public List<SqlMap> getProductList(ProductVO vo , SchProductVO schProductVo) throws Exception;
	
	/**
	 * @Method : getProductListCnt
	 * @Date		: 2017. 6. 30.
	 * @Author	:  유  준  철 
	 * @Description	:	상품 리스트 총 건수
	 */
	public int getProductListCnt(ProductVO vo , SchProductVO schProductVo)throws Exception;
	/**
	 * @Method : getTotalProductList
	 * @Date		: 2017. 6. 29.
	 * @Author	:  유  준  철 
	 * @Description	:	통합상품 리스트
	 */
	public List<SqlMap> getTotalProductList(ProductVO vo , SchProductVO schProductVo) throws Exception;
	
	/**
	 * @Method : getTotalProductListCnt
	 * @Date		: 2017. 6. 30.
	 * @Author	:  유  준  철 
	 * @Description	:	통합상품 리스트 총 건수
	 */
	public int getTotalProductListCnt(ProductVO vo , SchProductVO schProductVo)throws Exception;
	
	/**
	 * @Method : getCategoryNavi
	 * @Date		: 2017. 6. 30.
	 * @Author	:  유  준  철 
	 * @Description	:	상품 네비게이션
	 */
	public SqlMap getCategoryNavi(ProductVO vo , SchProductVO schProductVo)throws Exception;
	/**
	 * @Method : getTotalCategoryNavi
	 * @Date		: 2019.08.29
	 * @Author	:  강병철
	 * @Description	:	통합 네비게이션
	 */
	public SqlMap getTotalCategoryNavi(ProductVO vo , SchProductVO schProductVo)throws Exception;
	
	/**
	 * @Method : getProductHashList
	 * @Date		: 2017. 7. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	해시태그 리스트 (상품 상세)
	 */
	public List<SqlMap> getProductHashList(ProductVO vo , SchProductVO schProductVo) throws Exception;
	
	/**
	 * @Method : getProductDetail
	 * @Date		: 2017. 7. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	상품 상세
	 */
	public SqlMap getProductDetail(ProductVO vo , SchProductVO schProductVo)throws Exception;
	
	/**
	 * @Method : getBestProductList
	 * @Date		: 2017. 7. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	베스트 상품 리스트
	 */
	public List<SqlMap> getBestProductList(ProductVO vo) throws Exception;
	
	/**
	 * @Method : getCategoryBestProduct
	 * @Date		: 2017. 7. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	best (상품 상세)
	 */
	public List<SqlMap> getCategoryBestProduct(ProductVO vo , SchProductVO schProductVo)throws Exception;
	
	/**
	 * @Method : getWishProduct
	 * @Date		: 2017. 7. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	wish (상품 상세)
	 */
	public SqlMap getWishProduct(ProductVO vo , SchProductVO schProductVo)throws Exception;
	
	/**
	 * @Method : getRecommendProduct
	 * @Date		: 2017. 7. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	recommend (상품 상세)
	 */
	public List<SqlMap> getRecommendProduct(ProductVO vo)throws Exception;
	
	/**
	 * @Method : getSetProduct
	 * @Date		: 2017. 7. 5.
	 * @Author	:  유  준  철 
	 * @Description	:	set (상품 상세)
	 */
	public SqlMap getSetProduct(ProductVO vo , SchProductVO schProductVo)throws Exception;
	
	/**
	 * @Method : getOptionProductList
	 * @Date		: 2017. 7. 5.
	 * @Author	:  유  준  철 
	 * @Description	:	옵션 상품 리스트
	 */
	public List<SqlMap> getOptionProductList(ProductVO vo , SchProductVO schProductVo) throws Exception;
	
	/**
	 * @Method : getProductImgList
	 * @Date		: 2017. 7. 5.
	 * @Author	:  유  준  철 
	 * @Description	:	상품 이미지 리스트
	 */
	public List<SqlMap> getProductImgList(ProductVO vo , SchProductVO schProductVo) throws Exception;
	
	/**
	 * @Method : getProductExhibitionList
	 * @Date		: 2017. 7. 7.
	 * @Author	:  유  준  철 
	 * @Description	:	상품 기획전 리스트
	 */
	public List<SqlMap> getProductExhibitionList(ProductVO vo , SchProductVO schProductVo) throws Exception;
	
	/**
	 * @Method : getStylingMovieCnt
	 * @Date		: 2017. 7. 17.
	 * @Author	:  유  준  철 
	 * @Description	:	스타일링 무비, 매거진 건수, HOW TO USE, 리뷰 건수 , 리뷰 평균
	 */
	public SqlMap getProductSubInfo(ProductVO vo)throws Exception;
	
	/**
	 * @Method : getStyleInfo
	 * @Date		: 2017. 7. 31.
	 * @Author	:  유  준  철 
	 * @Description	:	스타일링 정보
	 */
	public SqlMap getStyleInfo(ProductVO vo , SchProductVO schProductVo)throws Exception;
	
	/**
	 * @Method : getMagazineInfo
	 * @Date		: 2017. 7. 31.
	 * @Author	:  유  준  철 
	 * @Description	:	매거진 정보
	 */
	public SqlMap getMagazineInfo(ProductVO vo , SchProductVO schProductVo)throws Exception;
	
	/**
	 * @Method : blogList
	 * @Date		: 2017. 8. 1.
	 * @Author	:  유  준  철 
	 * @Description	:	블로그 리스트
	 */
	public List<SqlMap> getBlogList() throws Exception;
	
	/**
	 * @Method : getHairStylingProductList
	 * @Date		: 2017. 9. 1.
	 * @Author	:  유  준  철 
	 * @Description	:	헤어스타일링 메인 상품 리스트
	 */
	public List<SqlMap> getHairStylingProductList() throws Exception;
	
	/**
	 * @Method : getHairStylingNoWaxtList
	 * @Date		: 2017. 9. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	헤어스타일링 메인 왁스(핸디 X) 리스트
	 */
	public List<SqlMap> getHairStylingNoWaxtList() throws Exception;
	
	/**
	 * @Method : getHairStylingWaxtList
	 * @Date		: 2017. 9. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	헤어스타일링 메인 왁스(핸디 O) 리스트
	 */
	public List<SqlMap> getHairStylingWaxtList() throws Exception;
	
	/**
	 * @Method : getProductSubImgList
	 * @Date		: 2018. 2. 7.
	 * @Author	:  유  준  철 
	 * @Description	:	상품 목록 오버시 이미지 리스트
	 */
	public List<SqlMap> getProductSubImgList() throws Exception;
	

	/**
	 * @Method : getProductOriginDetail
	 * @Date		: 2018. 06.13
	 * @Author	:  강병철
	 * @Description	:	상품 상세
	 */
	public SqlMap getProductOriginDetail(String GoodsCd)throws Exception;
	

	/**
	 * @Method : getProductSalesDetail
	 * @Date		: 2018. 06.13
	 * @Author	:  강병철
	 * @Description	:	상품 상세 판매중인 상품정보 (npay)
	 */
	public SqlMap getProductSalesDetail(String goodsIdx)throws Exception;
	
    /**
     * @Method      : retrieveGoodsOption
     * @Date        : 2021. 09. 27.
     * @Author      : dsjeong
     * @Description : 상품옵션 조회
     * @HISTORY     :
     *
     */
    public List<SqlMap> retrieveGoodsOptionList(ProductVO vo)throws Exception;
}
