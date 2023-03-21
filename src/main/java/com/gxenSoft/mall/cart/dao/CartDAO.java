package com.gxenSoft.mall.cart.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gxenSoft.sqlDAO.CommonDefaultDAO;
import com.gxenSoft.sqlMap.SqlMap;

@Repository("cartDAO")
public class CartDAO extends CommonDefaultDAO {

	   /**
	    * @Method : getMemberPoint
	    * @Date: 2017. 6. 27.
	    * @Author :  서 정 길
	    * @Description	:	회원 포인트 정보
	   */
	public SqlMap getMemberPoint(HashMap<String, Object> param) throws Exception{
		return (SqlMap)selectOne("cartDAO.getMemberPoint", param);
	}

	   /**
	    * @Method : getMemberCouponCnt
	    * @Date: 2017. 6. 27.
	    * @Author :  서 정 길
	    * @Description	:	회원 쿠폰 갯수
	   */
	public int getMemberCouponCnt(HashMap<String, Object> param) throws Exception{
		return (Integer)selectListTotCnt("cartDAO.getMemberCouponCnt", param);
	}

	   /**
	    * @Method : getGoodsCartCnt
	    * @Date: 2017. 6. 27.
	    * @Author :  서 정 길
	    * @Description	:	장바구니에 담긴 해당 상품 갯수
	   */
	public int getGoodsCartCnt(HashMap<String, Object> param) throws Exception{
		return (Integer)selectListTotCnt("cartDAO.getGoodsCartCnt", param);
	}
	
	   /**
	    * @Method : insertCart
	    * @Date: 2017. 6. 27.
	    * @Author :  서 정 길
	    * @Description	:	장바구니에 상품 추가
	   */
	public int insertCart(HashMap<String, Object> param) throws Exception{
		return (Integer)insert("cartDAO.insertCart", param);
	}

	   /**
	    * @Method : getCartList
	    * @Date: 2017. 6. 28.
	    * @Author :  서 정 길
	    * @Description	:	장바구니 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getCartList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("cartDAO.getCartList", param);
	}
	
	   /**
	    * @Method : getGoodsWishCnt
	    * @Date: 2017. 6. 29.
	    * @Author :  서 정 길
	    * @Description	:	찜하기에 담긴 해당 상품 갯수
	   */
	public int getGoodsWishCnt(HashMap<String, Object> param) throws Exception{
		return (Integer)selectListTotCnt("cartDAO.getGoodsWishCnt", param);
	}
	
	   /**
	    * @Method : insertWish
	    * @Date: 2017. 6. 29.
	    * @Author :  서 정 길
	    * @Description	:	찜하기에 상품 추가
	   */
	public int insertWish(HashMap<String, Object> param) throws Exception{
		return (Integer)insert("cartDAO.insertWish", param);
	}

	   /**
	    * @Method : deleteCart
	    * @Date: 2017. 6. 29.
	    * @Author :  서 정 길
	    * @Description	:	장바구니 상품 삭제
	   */
	public int deleteCart(HashMap<String, Object> param) throws Exception{
		return (Integer)delete("cartDAO.deleteCart", param);
	}
	
	   /**
	    * @Method : deleteSoldOutCart
	    * @Date: 2017. 6. 29.
	    * @Author :  서 정 길
	    * @Description	:	장바구니 품절 상품 삭제
	   */
	public int deleteSoldOutCart(HashMap<String, Object> param) throws Exception{
		return (Integer)delete("cartDAO.deleteSoldOutCart", param);
	}

	   /**
	    * @Method : changeCnt
	    * @Date: 2017. 7. 4.
	    * @Author :  서 정 길
	    * @Description	:	장바구니 수량 변경
	   */
	public int changeCnt(HashMap<String, Object> param) throws Exception{
		return (Integer)update("cartDAO.changeCnt", param);
	}

	   /**
	    * @Method : getGoodsInfo
	    * @Date: 2017. 6. 29.
	    * @Author :  서 정 길
	    * @Description	:	장바구니/위시리스트에 담길 상품 정보 (유효한 상품인지 보기 위해)
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getGoodsInfoList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("cartDAO.getGoodsInfoList", param);
	}

	   /**
	    * @Method : getGiftList
	    * @Date: 2017. 6. 30.
	    * @Author :  서 정 길
	    * @Description	:	사은품 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getGiftList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("cartDAO.getGiftList", param);
	}

	   /**
	    * @Method : getRecommendProductList
	    * @Date: 2017. 6. 30.
	    * @Author :  서 정 길
	    * @Description	:	추천상품 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getRecommendProductList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("cartDAO.getRecommendProductList", param);
	}
	
	   /**
	    * @Method : getAutoIssueGoodsCouponList
	    * @Date: 2017. 7. 11.
	    * @Author :  서 정 길
	    * @Description	:	상품 자동발급 상품 쿠폰 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getAutoIssueGoodsCouponList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("cartDAO.getAutoIssueGoodsCouponList", param);
	}
	
	/**
	 * @Method : getWishTotalCnt
	 * @Date		: 2017. 7. 18.
	 * @Author	:  유  준  철 
	 * @Description	:	위시 리스트(상품) 총 건수 
	 */
	public int getWishTotalCnt(HashMap<String, Object> param)throws Exception{
		return (Integer)selectListTotCnt("cartDAO.getWishTotalCnt", param);
	}
	
	/**
	 * @Method : wishDelete
	 * @Date		: 2017. 8. 24.
	 * @Author	:  유  준  철 
	 * @Description	:	찜 하기 해제
	 */
	public int wishDelete(HashMap<String, Object> param)throws Exception{
		return (Integer)delete("cartDAO.wishDelete", param);
	}
	
	   /**
	    * @Method : getShippingPriceList
	    * @Date: 2017. 9. 13.
	    * @Author :  서 정 길
	    * @Description	:	배송비 금액 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getShippingPriceList(HashMap<String, Object> param) throws Exception{
		return (List<SqlMap>)selectList("cartDAO.getShippingPriceList", param);
	}

	/**
	 * @Method : getCartRecommendList
	 * @Date		: 2019. 7. 23.
	 * @Author	:  임  재  형 
	 * @Description	:	장바구니 추천상품 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getCartRecommendList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("cartDAO.getCartRecommendList", param);
	}	
}
