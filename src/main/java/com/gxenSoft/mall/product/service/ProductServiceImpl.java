package com.gxenSoft.mall.product.service;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.product.dao.ProductDAO;
import com.gxenSoft.mall.product.vo.ProductVO;
import com.gxenSoft.mall.product.vo.SchProductVO;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.pathUtil.PathUtil;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : ProductServiceImpl
 * PACKAGE NM : com.gxenSoft.mall.product.service
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 6. 29. 
 * HISTORY :
 
 *************************************
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {
	
	static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	private ProductDAO productDAO;
	
	/**
	 * @Method : getProductList
	 * @Date		: 2017. 6. 29.
	 * @Author	:  유  준  철 
	 * @Description	:	상품 리스트
	 */
	public List<SqlMap> getProductList(ProductVO vo , SchProductVO schProductVo) throws Exception {
		List<SqlMap> list = null;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("SCHCATEFLAG", schProductVo.getSchCateFlag());
		map.put("SCHCATEIDX", schProductVo.getSchCateIdx());
		map.put("SCH2DEPTHCATEIDX", schProductVo.getSch2depthCateIdx());
		map.put("SCH3DEPTHCATEIDX", schProductVo.getSch3depthCateIdx());
		map.put("SCHORDERGUBUN", schProductVo.getSchOrderGubun());
		map.put("PAGESTART", ((schProductVo.getPageNo() - 1) * schProductVo.getPageBlock()));					
		map.put("PAGEBLOCK", schProductVo.getPageBlock());																
		map.put("PAGENO", schProductVo.getPageNo());
		map.put("SETFLAG", vo.getSetFlag());
		
		try{
			list = productDAO.getProductList(map);			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;		
	}
	
	/**
	 * @Method : getProductListCnt
	 * @Date		: 2017. 6. 30.
	 * @Author	:  유  준  철 
	 * @Description	:	상품 리스트 총 건수
	 */
	public int getProductListCnt(ProductVO vo , SchProductVO schProductVo)throws Exception{
		int cnt = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("SCHCATEFLAG", schProductVo.getSchCateFlag());
		map.put("SCHCATEIDX", schProductVo.getSchCateIdx());
		map.put("SCH2DEPTHCATEIDX", schProductVo.getSch2depthCateIdx());
		map.put("SCH3DEPTHCATEIDX", schProductVo.getSch3depthCateIdx());
		map.put("SETFLAG", vo.getSetFlag());
		
		try{
			cnt = productDAO.getProductListCnt(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}

	/**
	 * @Method : getTotalProductList
	 * @Date		: 2017. 6. 29.
	 * @Author	:  강 병 철
	 * @Description	:	통합 상품 리스트
	 */
	public List<SqlMap> getTotalProductList(ProductVO vo , SchProductVO schProductVo) throws Exception {
		List<SqlMap> list = null;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("SCHCATEFLAG", schProductVo.getSchCateFlag());
		map.put("SCHCATEIDX", schProductVo.getSchCateIdx());
		map.put("SCH2DEPTHCATEIDX", schProductVo.getSch2depthCateIdx());
		map.put("SCH3DEPTHCATEIDX", schProductVo.getSch3depthCateIdx());
		map.put("SCHORDERGUBUN", schProductVo.getSchOrderGubun());
		map.put("PAGESTART", ((schProductVo.getPageNo() - 1) * schProductVo.getPageBlock()));					
		map.put("PAGEBLOCK", schProductVo.getPageBlock());																
		map.put("PAGENO", schProductVo.getPageNo());
		map.put("SETFLAG", vo.getSetFlag());
		
		try{
			list = productDAO.getTotalProductList(map);			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;		
	}
	
	/**
	 * @Method : getTotalProductListCnt
	 * @Date		: 2017. 6. 30.
	 * @Author	:  강 병 철
	 * @Description	:	통합상품 리스트 총 건수
	 */
	public int getTotalProductListCnt(ProductVO vo , SchProductVO schProductVo)throws Exception{
		int cnt = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("SCHCATEFLAG", schProductVo.getSchCateFlag());
		map.put("SCHCATEIDX", schProductVo.getSchCateIdx());
		map.put("SCH2DEPTHCATEIDX", schProductVo.getSch2depthCateIdx());
		map.put("SCH3DEPTHCATEIDX", schProductVo.getSch3depthCateIdx());
		map.put("SETFLAG", vo.getSetFlag());
		
		try{
			cnt = productDAO.getTotalProductListCnt(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}
	
	/**
	 * @Method : getCategoryNavi
	 * @Date		: 2017. 6. 30.
	 * @Author	:  유  준  철 
	 * @Description	:	상품 네비게이션
	 */
	public SqlMap getCategoryNavi(ProductVO vo , SchProductVO schProductVo)throws Exception{
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<>();
		
		if(vo.getChoiceCateIdx() != null){
			map.put("SCHCATEIDX", vo.getChoiceCateIdx());
		}else{
			map.put("SCHCATEIDX", schProductVo.getSchCateIdx());
		}
		
		map.put("SCH3DEPTHCATEIDX", schProductVo.getSch3depthCateIdx());
		
		try{
			detail = productDAO.getCategoryNavi(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return detail;
	}
	
	/**
	 * @Method : getTotalCategoryNavi
	 * @Date		: 2019.08.29
	 * @Author	:  강병철
	 * @Description	:	통합 네비게이션
	 */
	public SqlMap getTotalCategoryNavi(ProductVO vo , SchProductVO schProductVo)throws Exception{
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<>();
		
		if(vo.getChoiceCateIdx() != null){
			map.put("SCHCATEIDX", vo.getChoiceCateIdx());
		}else{
			map.put("SCHCATEIDX", schProductVo.getSchCateIdx());
		}
		
		map.put("SCH3DEPTHCATEIDX", schProductVo.getSch3depthCateIdx());
		
		try{
			detail = productDAO.getTotalCategoryNavi(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return detail;
	}
	
	
	/**
	 * @Method : getProductHashList
	 * @Date		: 2017. 7. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	해시태그 리스트 (상품 상세)
	 */
	public List<SqlMap> getProductHashList(ProductVO vo , SchProductVO schProductVo) throws Exception {
		List<SqlMap> list = null;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("GOODSIDX", vo.getGoodsIdx());
		
		try{
			list = productDAO.getProductHashList(map);			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;		
	}
	
	/**
	 * @Method : getProductDetail
	 * @Date		: 2017. 7. 3.
	 * @Author	:  유  준  철 
	 * @Description	:	상품 상세
	 */
	public SqlMap getProductDetail(ProductVO vo , SchProductVO schProductVo)throws Exception{
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<>();
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(false);
		
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx());
		map.put("MEMBERGRADE", UserInfo.getUserInfo().getLevelIdx());
		map.put("GOODSCD", vo.getGoodsCd());
		map.put("CHOICECATEIDX", vo.getChoiceCateIdx());								// 카테고리 일련번호
		
		try{
			detail = productDAO.getProductDetail(map);
			if(detail != null && !detail.isEmpty()){
				productDAO.productViewCntUpdate(map);										// 상품 조회 수 업데이트
				
				map.put("GOODSIDX", detail.get("goodsIdx"));								// 상품 IDX
				map.put("SRCPATH", vo.getSrcPath());												// 유입경로
				map.put("SRCINFO", vo.getSrcInfo());												// 유입경로 기타 정보
				map.put("ADFROM", session.getAttribute("adfrom"));					// 광고 경로 TAG
				map.put("SESSIONID", session.getId());												// 세션 ID
				map.put("DEVICE", PathUtil.getDeviceNm());									// 디바이스 정보
				productDAO.productBestViewInsert(map);										// 베스트 상품관리 조회 점수 저장
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return detail;
	}
	
	/**
	 * @Method : getBestProductList
	 * @Date		: 2017. 7. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	베스트 상품 리스트
	 */
	public List<SqlMap> getBestProductList(ProductVO vo) throws Exception {
		List<SqlMap> list = null;
		
		HashMap<String, Object> map = new HashMap<>();
		
		if(vo.getBrandIdx() == null){
			if(vo.getGnbBrand() == null || vo.getGnbBrand().equals("default") || vo.getGnbBrand().equals("gatsby")){
				vo.setBrandIdx("1");
			}else if (vo.getGnbBrand().equals("bifesta")){
				vo.setBrandIdx("3");
			}else if (vo.getGnbBrand().equals("lucidol")){
				vo.setBrandIdx("4");
			}
			map.put("BRANDIDX", vo.getBrandIdx());
		}else{
			map.put("BRANDIDX", vo.getBrandIdx());
		}
		
		try{
			list = productDAO.getBestProductList(map);			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;		
	}
	
	/**
	 * @Method : getCategoryBestProduct
	 * @Date		: 2017. 7. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	best (상품 상세)
	 */
	public List<SqlMap> getCategoryBestProduct(ProductVO vo , SchProductVO schProductVo) throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("GOODSIDX", vo.getGoodsIdx());
		
		try{
			list = productDAO.getCategoryBestProduct(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}
	
	/**
	 * @Method : getWishProduct
	 * @Date		: 2017. 7. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	wish (상품 상세)
	 */
	public SqlMap getWishProduct(ProductVO vo , SchProductVO schProductVo)throws Exception{
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("GOODSIDX", vo.getGoodsIdx());
		
		try{
			detail = productDAO.getWishProduct(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return detail;
	}
	
	/**
	 * @Method : getRecommendProduct
	 * @Date		: 2017. 7. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	recommend (상품 상세)
	 */
	public List<SqlMap> getRecommendProduct(ProductVO vo) throws Exception {
		List<SqlMap> list = null;
		
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("BRANDIDX", vo.getBrandIdx());
		
		try{
			list = productDAO.getRecommendProduct(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}
	
	/**
	 * @Method : getSetProduct
	 * @Date		: 2017. 7. 5.
	 * @Author	:  유  준  철 
	 * @Description	:	set (상품 상세)
	 */
	public SqlMap getSetProduct(ProductVO vo , SchProductVO schProductVo)throws Exception{
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("GOODSIDX", vo.getGoodsIdx());
		
		try{
			detail = productDAO.getSetProduct(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return detail;
	}
	
	/**
	 * @Method : getOptionProductList
	 * @Date		: 2017. 7. 5.
	 * @Author	:  유  준  철 
	 * @Description	:	옵션 상품 리스트
	 */
	public List<SqlMap> getOptionProductList(ProductVO vo , SchProductVO schProductVo) throws Exception {
		List<SqlMap> list = null;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx());
		map.put("MEMBERGRADE", UserInfo.getUserInfo().getLevelIdx());
		map.put("GOODSIDX", vo.getGoodsIdx());
		
		try{
			list = productDAO.getOptionProductList(map);			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;		
	}
	
	/**
	 * @Method : getProductImgList
	 * @Date		: 2017. 7. 5.
	 * @Author	:  유  준  철 
	 * @Description	:	상품 이미지 리스트
	 */
	public List<SqlMap> getProductImgList(ProductVO vo , SchProductVO schProductVo) throws Exception {
		List<SqlMap> list = null;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("GOODSIDX", vo.getGoodsIdx());
		
		try{
			list = productDAO.getProductImgList(map);			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;		
	}
	
	/**
	 * @Method : getProductExhibitionList
	 * @Date		: 2017. 7. 7.
	 * @Author	:  유  준  철 
	 * @Description	:	상품 기획전 리스트
	 */
	public List<SqlMap> getProductExhibitionList(ProductVO vo , SchProductVO schProductVo) throws Exception {
		List<SqlMap> list = null;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("GOODSIDX", vo.getGoodsIdx());
		
		try{
			list = productDAO.getProductExhibitionList(map);			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;		
	}
	
	/**
	 * @Method : getStylingMovieCnt
	 * @Date		: 2017. 7. 17.
	 * @Author	:  유  준  철 
	 * @Description	:	스타일링 무비, 매거진 건수, HOW TO USE, 리뷰 건수 , 리뷰 평균
	 */
	public SqlMap getProductSubInfo(ProductVO vo)throws Exception{
		SqlMap detail = null;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("GOODSIDX", vo.getGoodsIdx());
		
		try{
			detail = productDAO.getProductSubInfo(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return detail;
	}
	
	/**
	 * @Method : getStyleInfo
	 * @Date		: 2017. 7. 31.
	 * @Author	:  유  준  철 
	 * @Description	:	스타일링 정보
	 */
	public SqlMap getStyleInfo(ProductVO vo , SchProductVO schProductVo)throws Exception{
		SqlMap detail = null;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("GOODSIDX", vo.getGoodsIdx());
		
		try{
			detail = productDAO.getStyleInfo(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return detail;
	}
	
	/**
	 * @Method : getMagazineInfo
	 * @Date		: 2017. 7. 31.
	 * @Author	:  유  준  철 
	 * @Description	:	매거진 정보
	 */
	public SqlMap getMagazineInfo(ProductVO vo , SchProductVO schProductVo)throws Exception{
		SqlMap detail = null;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("GOODSIDX", vo.getGoodsIdx());
		
		try{
			detail = productDAO.getMagazineInfo(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return detail;
	}
	
	/**
	 * @Method : getBlogList
	 * @Date		: 2017. 8. 1.
	 * @Author	:  유  준  철 
	 * @Description	:	블로그 리스트
	 */
	public List<SqlMap> getBlogList() throws Exception {
		List<SqlMap> list = null;
		
		try{
			list = productDAO.getBlogList();			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;		
	}
	
	/**
	 * @Method : getHairStylingProductList
	 * @Date		: 2017. 9. 1.
	 * @Author	:  유  준  철 
	 * @Description	:	헤어스타일링 메인 상품 리스트
	 */
	public List<SqlMap> getHairStylingProductList() throws Exception {
		List<SqlMap> list = null;
		
		try{
			list = productDAO.getHairStylingProductList();			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;		
	}
	
	/**
	 * @Method : getHairStylingNoWaxtList
	 * @Date		: 2017. 9. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	헤어스타일링 메인 왁스(핸디 X) 리스트
	 */
	public List<SqlMap> getHairStylingNoWaxtList() throws Exception {
		List<SqlMap> list = null;
		
		try{
			list = productDAO.getHairStylingNoWaxtList();			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;		
	}
	
	/**
	 * @Method : getHairStylingWaxtList
	 * @Date		: 2017. 9. 4.
	 * @Author	:  유  준  철 
	 * @Description	:	헤어스타일링 메인 왁스(핸디 O) 리스트
	 */
	public List<SqlMap> getHairStylingWaxtList() throws Exception {
		List<SqlMap> list = null;
		
		try{
			list = productDAO.getHairStylingWaxtList();			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;		
	}
	
	/**
	 * @Method : getProductSubImgList
	 * @Date		: 2018. 2. 7.
	 * @Author	:  유  준  철 
	 * @Description	:	상품 목록 오버시 이미지 리스트
	 */
	public List<SqlMap> getProductSubImgList() throws Exception {
		List<SqlMap> list = null;
		
		try{
			list = productDAO.getProductSubImgList();			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;		
	}
	
	/**
	 * @Method : getProductOriginDetail
	 * @Date		: 2018. 06.13
	 * @Author	:  강병철
	 * @Description	:	상품 상세 (npay)
	 */
	public SqlMap getProductOriginDetail(String GoodsCd)throws Exception{
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<>();
		map.put("GOODSCD", GoodsCd);
		try{
			detail = productDAO.getProductOriginDetail(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return detail;
	}

	/**
	 * @Method : getProductSalesDetail
	 * @Date		: 2018. 06.13
	 * @Author	:  강병철
	 * @Description	:	상품 상세 (npay)
	 */
	public SqlMap getProductSalesDetail(String goodsIdx)throws Exception{
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<>();
		map.put("GOODSIDX", goodsIdx);
		try{
			detail = productDAO.getProductSalesDetail(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return detail;
	}
	
    /**
     * @Method      : retrieveGoodsOption
     * @Date        : 2021. 09. 27.
     * @Author      : dsjeong
     * @Description : 상품옵션 조회
     * @HISTORY     :
     *
     */
    @Override
    public List<SqlMap> retrieveGoodsOptionList(ProductVO vo) throws Exception {
        List<SqlMap> list = null;
        HashMap<String, Object> map = new HashMap<>();
        map.put("GOODSIDX", vo.getGoodsIdx());

        try {
            list = productDAO.retrieveGoodsOptionList(map);

            HashMap<String, Object> sMap = null;
            
            for (SqlMap optionList : list) {
                sMap = new HashMap<>();
                sMap.put("GOODSIDX", vo.getGoodsIdx());
                sMap.put("OPTIONIDX", optionList.get("optionIdx"));
                
                optionList.put("itemList", productDAO.retrieveGoodsOptionItemList(sMap));
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return list;
    }
}
