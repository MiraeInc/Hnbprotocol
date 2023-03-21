package com.gxenSoft.mall.mypage.wish.service;


import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.mypage.wish.dao.WishDAO;
import com.gxenSoft.mall.mypage.wish.vo.WishVO;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : WishServiceImpl
    * PACKAGE NM : com.gxenSoft.mall.mypage.wish.service
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 8. 5. 
    * HISTORY :   
    *
    *************************************
    */
@Service("wishService")
public class WishServiceImpl implements WishService {
	
	static final Logger logger = LoggerFactory.getLogger(WishServiceImpl.class);
	
	@Autowired
	private WishDAO wishDAO;


	/**
	    * @Method : getWishListCnt
	    * @Date: 2017. 8. 5.
	    * @Author :  임  재  형
	    * @Description	:	위시 리스트 총 개수
	   */
	public int getWishListCnt(SearchVO schVO) throws Exception {
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx()); 	// 회원 일련번호
		
		try{
			cnt = wishDAO.getWishListCnt(map); // 위시 리스트 총 개수
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}

	/**
	    * @Method : getWishList
	    * @Date: 2017. 8. 5.
	    * @Author :  임  재  형
	    * @Description	:	위시 리스트
	   */
	public List<SqlMap> getWishList(SearchVO schVO) throws Exception {
		schVO.setPageBlock(12);
		
		List<SqlMap> wishList = null;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx()); 	// 회원 일련번호
		
		map.put("PAGESTART", ((schVO.getPageNo() - 1) * schVO.getPageBlock()));
		map.put("PAGEBLOCK", schVO.getPageBlock());
		map.put("PAGENO", schVO.getPageNo());
		
		try{
			wishList = wishDAO.getWishList(map); // 위시 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return wishList;
	}
	
	/**
	    * @Method : wishDelete
	    * @Date: 2017. 8. 5.
	    * @Author : 임  재  형
	    * @Description	:	위시 삭제 (하트)
	   */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public int wishDelete(WishVO vo) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("WISHIDX", vo.getWishIdx()); // 위시 일련번호
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx()); // 회원 일련번호
		
		try{
			flag = wishDAO.wishDelete(map); // 위시 삭제
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return flag;
	}

	/**
	    * @Method : wishDeleteAjax
	    * @Date: 2017. 8. 5.
	    * @Author :  임  재  형
	    * @Description	:	위시 삭제 (체크 박스)
	   */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public int wishDeleteAjax(WishVO vo) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx()); // 회원 일련번호
		
		if(!vo.getWishListIdx().isEmpty()){									
			String[] arrayParam = vo.getWishListIdx().split(",");
			for(int i=0; i<arrayParam.length; i++){
				map.put("WISHIDX", arrayParam[i]);
				
				flag = wishDAO.wishDelete(map); // 위시 삭제 (체크 박스)
			}
		}
		
		return flag;
	}

}
