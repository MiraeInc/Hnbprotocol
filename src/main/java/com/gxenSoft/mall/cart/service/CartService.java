package com.gxenSoft.mall.cart.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gxenSoft.mall.cart.vo.CartVO;
import com.gxenSoft.mall.common.vo.JsonResultVO;
import com.gxenSoft.sqlMap.SqlMap;

public interface CartService {

	   /**
	    * @Method : getMemberPoint
	    * @Date: 2017. 6. 27.
	    * @Author :  서 정 길
	    * @Description	:	회원 포인트 정보
	   */
	public SqlMap getMemberPoint(int memberIdx) throws Exception;
	
	   /**
	    * @Method : getMemberCouponCnt
	    * @Date: 2017. 6. 27.
	    * @Author :  서 정 길
	    * @Description	:	회원 쿠폰 갯수
	   */
	public int getMemberCouponCnt(CartVO vo) throws Exception;
	
	   /**
	    * @Method : getGoodsCartCnt
	    * @Date: 2017. 6. 27.
	    * @Author :  서 정 길
	    * @Description	:	장바구니에 담긴 해당 상품 갯수
	   */
	public int getGoodsCartCnt(CartVO vo) throws Exception;
	
	   /**
	    * @Method : addCart
	    * @Date: 2017. 6. 27.
	    * @Author :  서 정 길
	    * @Description	:	장바구니 담기
	   */
	public JsonResultVO addCart(List<CartVO> vo, HttpServletRequest request) throws Exception;
	
	   /**
	    * @Method : getCartList
	    * @Date: 2017. 6. 28.
	    * @Author :  서 정 길
	    * @Description	:	장바구니 리스트
	   */
	public List<SqlMap> getCartList(CartVO vo) throws Exception;
	
	   /**
	    * @Method : getGoodsWishCnt
	    * @Date: 2017. 6. 29.
	    * @Author :  서 정 길
	    * @Description	:	찜하기에 담긴 해당 상품 갯수
	   */
	public int getGoodsWishCnt(CartVO vo) throws Exception;

	   /**
	    * @Method : addWish
	    * @Date: 2017. 6. 29.
	    * @Author :  서 정 길
	    * @Description	:	찜하기에 담기
	   */
	public int addWish(CartVO vo) throws Exception;
	
	   /**
	    * @Method : deleteCart
	    * @Date: 2017. 6. 29.
	    * @Author :  서 정 길
	    * @Description	:	장바구니 상품 삭제
	   */
	public int deleteCart(CartVO vo) throws Exception;
	
	   /**
	    * @Method : deleteSoldOutCart
	    * @Date: 2017. 6. 29.
	    * @Author :  서 정 길
	    * @Description	:	장바구니 품절 상품 삭제
	   */
	public int deleteSoldOutCart(CartVO vo) throws Exception;
	
	   /**
	    * @Method : changeCnt
	    * @Date: 2017. 7. 4.
	    * @Author :  서 정 길
	    * @Description	:	장바구니 수량 변경
	   */
	public int changeCnt(CartVO vo) throws Exception;

	   /**
	    * @Method : getGoodsInfo
	    * @Date: 2017. 6. 29.
	    * @Author :  서 정 길
	    * @Description	:	장바구니/위시리스트에 담길 상품 정보 (유효한 상품인지 보기 위해)
	   */
	public List<SqlMap> getGoodsInfoList(CartVO vo) throws Exception;

	   /**
	    * @Method : getGiftList
	    * @Date: 2017. 6. 30.
	    * @Author :  서 정 길
	    * @Description	:	사은품 리스트
	   */
	public List<SqlMap> getGiftList(String freeYn, Double price) throws Exception;

	   /**
	    * @Method : getRecommendProductList
	    * @Date: 2017. 6. 30.
	    * @Author :  서 정 길
	    * @Description	:	추천상품 리스트
	   */
	public List<SqlMap> getRecommendProductList(CartVO vo) throws Exception;
	
	   /**
	    * @Method : getAutoIssueGoodsCouponList
	    * @Date: 2017. 7. 11.
	    * @Author :  서 정 길
	    * @Description	:	상품 자동발급 상품 쿠폰 리스트
	   */
	public List<SqlMap> getAutoIssueGoodsCouponList(String goodsIdx) throws Exception;
	
	/**
	 * @Method : getWishTotalCnt
	 * @Date		: 2017. 7. 18.
	 * @Author	:  유  준  철 
	 * @Description	:	위시 리스트(상품) 총 건수 
	 */
	public int getWishTotalCnt(CartVO vo) throws Exception;
	
	/**
	 * @Method : wishDelete
	 * @Date		: 2017. 8. 24.
	 * @Author	:  유  준  철 
	 * @Description	:	찜 하기 해제
	 */
	public int wishDelete(CartVO vo) throws Exception;
	
	   /**
	    * @Method : getShippingPriceList
	    * @Date: 2017. 9. 13.
	    * @Author :  서 정 길
	    * @Description	:	배송비 금액 리스트
	   */
	public List<SqlMap> getShippingPriceList() throws Exception;

	/**
	 * @Method : getCartRecommendList
	 * @Date		: 2019. 7. 23.
	 * @Author	:  임  재  형 
	 * @Description	:	장바구니 추천상품 리스트
	 */
	public List<SqlMap> getCartRecommendList(CartVO vo) throws Exception;
}
