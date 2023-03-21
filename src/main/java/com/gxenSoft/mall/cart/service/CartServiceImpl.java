package com.gxenSoft.mall.cart.service;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gxenSoft.mall.cart.dao.CartDAO;
import com.gxenSoft.mall.cart.vo.CartVO;
import com.gxenSoft.mall.common.vo.JsonResultVO;
import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.method.ConvertUtil;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.pathUtil.PathUtil;

@Service("cartService")
public class CartServiceImpl implements CartService {

	@Autowired
	private CartDAO cartDAO;

	   /**
	    * @Method : getMemberPoint
	    * @Date: 2017. 6. 27.
	    * @Author :  서 정 길
	    * @Description	:	회원 포인트 정보
	   */
	@Override
	public SqlMap getMemberPoint(int memberIdx) throws Exception {
		SqlMap info = new SqlMap();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			map.put("MEMBERIDX", memberIdx);	// 회원 일련번호
		
			info = cartDAO.getMemberPoint(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}

		return info;
	}

	   /**
	    * @Method : getMemberCouponCnt
	    * @Date: 2017. 6. 27.
	    * @Author :  서 정 길
	    * @Description	:	회원 쿠폰 갯수
	   */
	@Override
	public int getMemberCouponCnt(CartVO vo) throws Exception {
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();

		try{
			map.put("MEMBERIDX", vo.getMemberIdx());	// 회원 일련번호
			map.put("DEVICE", PathUtil.getDeviceNm());		// 디바이스 (P : PC, M : MOBILE, A : APP)
			map.put("MEMBERGRADE", UserInfo.getUserInfo().getLevelIdx());		// 회원 등급 코드		
		
			cnt = cartDAO.getMemberCouponCnt(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}
	
	   /**
	    * @Method : getGoodsCartCnt
	    * @Date: 2017. 6. 27.
	    * @Author :  서 정 길
	    * @Description	:	장바구니에 담긴 해당 상품 갯수
	   */
	@Override
	public int getGoodsCartCnt(CartVO vo) throws Exception {
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("GOODSIDX", vo.getGoodsIdx());		// 상품 일련번호
		if(vo.getRegIdx() > 0){
			map.put("MEMBERIDX", vo.getMemberIdx());	// 회원 일련번호
		}else{
			map.put("SESSIONID", vo.getSessionId());		// 비회원 세션 ID
		}
	
		cnt = cartDAO.getGoodsCartCnt(map);
		
		return cnt;
	}

	   /**
	    * @Method : addCart
	    * @Date: 2017. 6. 27.
	    * @Author :  서 정 길
	    * @Description	:	장바구니 담기
	   */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public JsonResultVO addCart(List<CartVO> vo, HttpServletRequest request) throws Exception {
		
		JsonResultVO resultMap = new JsonResultVO();
		
		HashMap<String, Object> map = new HashMap<String, Object>();

		int successCnt = 0;
//		int failCnt = 0;
	
		for(CartVO item : vo){
			if(!ConvertUtil.nvl(UserInfo.getUserInfo().getMemberId()).isEmpty()){	// 회원
				item.setRegIdx(UserInfo.getUserInfo().getMemberIdx());
				item.setMemberIdx(UserInfo.getUserInfo().getMemberIdx());
			}else{	// 비회원
				item.setRegIdx(0);
			}

			item.setRegHttpUserAgent(request.getHeader("user-agent"));	// HTTP_USER_AGENT
			InetAddress local = InetAddress.getLocalHost();	// LOCAL IP ADDRESS
			item.setRegIp(local.getHostAddress());
			
			int cnt = this.getGoodsCartCnt(item);		// 장바구니에 담긴 해당 상품 갯수

			if(cnt > 0){
//					++failCnt;
				resultMap.setMsg("이미 장바구니에 담긴 상품입니다.");
				continue;
			}
			
			item.setGoodsIdxes(item.getGoodsIdx());
			
			List<SqlMap> goodsList = this.getGoodsInfoList(item);	// 장바구니/위시리스트에 담길 상품 정보 (유효한 상품인지 보기 위해)
			if(goodsList == null){
//					++failCnt;
				resultMap.setMsg("상품정보 검색중 에러가 발생했습니다.");
				continue;
			}else if(goodsList.size() == 0){
//					++failCnt;
				resultMap.setMsg("존재하지 않는 상품입니다.");
				continue;
			}else{
				if(goodsList.get(0).get("saleStatus").equals("P")){
//						++failCnt;
					resultMap.setMsg("판매전 상태 상품입니다.");
					continue;
				}else if(goodsList.get(0).get("saleStatus").equals("S")){
//						++failCnt;
					resultMap.setMsg("판매중단 된 상품입니다.");
					continue;
				}
			}
			
			// 상품 자동발급 상품 쿠폰 리스트(회원일때만)
			Integer couponIdx = null;
			if(!ConvertUtil.nvl(UserInfo.getUserInfo().getMemberId()).isEmpty()){	// 회원
				List<SqlMap> couponList = this.getAutoIssueGoodsCouponList(item.getGoodsIdx());	// 상품 자동발급 상품 쿠폰 리스트
				if(couponList != null && couponList.size() > 0){
					couponIdx = Integer.valueOf(couponList.get(0).get("couponIdx").toString());
				}
			}
			
			// 장바구니 테이블에 저장
			if(item.getRegIdx() > 0){	// 회원
				map.put("MEMBERIDX", item.getMemberIdx());		// 회원 일련번호
				map.put("SESSIONID", null);									// 비회원 세션 ID
			}else{							// 비회원
				map.put("MEMBERIDX", null);								// 회원 일련번호
				map.put("SESSIONID", item.getSessionId());			// 비회원 세션 ID
			}
			map.put("GOODSIDX", item.getGoodsIdx());				// 상품 일련번호
			map.put("GOODSCNT", item.getGoodsCnt());				// 상품 갯수
			map.put("COUPONIDX", couponIdx);							// 자동 적용 쿠폰 일련번호		
			map.put("DEVICE", PathUtil.getDeviceNm());				// 디바이스 (P : PC, M : MOBILE, A : APP)
			map.put("CATEIDX", goodsList.get(0).get("cateIdx"));	// 카테고리 일련번호
			map.put("PARTNERIDX", null);									// 파트너 데이타 일련번호		// TODO 확인 필요		
			map.put("REGIDX", item.getRegIdx());
			map.put("REGHTTPUSERAGENT", item.getRegHttpUserAgent());
			map.put("REGIP", item.getRegIp());

			cartDAO.insertCart(map);

			++successCnt;
		}

		if(vo.size() == 1){
			if(successCnt == 1){
				resultMap.setMsg("장바구니에 담았습니다.\n장바구니로 이동하시겠습니까?");
				resultMap.setResult(true);
			}else{
				resultMap.setResult(false);
			}
		}else{
			if(successCnt == 0){
				resultMap.setResult(false);
			}else if(successCnt < vo.size()){
				resultMap.setMsg("선택한 "+vo.size()+"개의 상품 중 주문 가능한 "+successCnt+"개의 상품을 장바구니에 담았습니다.\n장바구니로 이동하시겠습니까?");
			}else{
				resultMap.setMsg("장바구니에 담았습니다.\n장바구니로 이동하시겠습니까?");
				resultMap.setResult(true);
			}
		}		

		return resultMap;
	}	

	   /**
	    * @Method : getCartList
	    * @Date: 2017. 6. 28.
	    * @Author :  서 정 길
	    * @Description	:	장바구니 리스트
	   */
	@Override
	public List<SqlMap> getCartList(CartVO vo) throws Exception{
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();

		if(vo.getRegIdx() > 0){	// 회원
			map.put("MEMBERIDX", vo.getMemberIdx());	// 회원 일련번호
			map.put("SESSIONID", null);							// 비회원 세션 ID
			map.put("MEMBERGRADE", UserInfo.getUserInfo().getLevelIdx());		// 회원 등급 코드
		}else{							// 비회원
			map.put("MEMBERIDX", null);						// 회원 일련번호
			map.put("SESSIONID", vo.getSessionId());		// 비회원 세션 ID
		}
		
		map.put("DEVICE", PathUtil.getDeviceNm());		// 디바이스 (P : PC, M : MOBILE, A : APP)
		
		try{
			list = cartDAO.getCartList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}
	
	   /**
	    * @Method : getGoodsWishCnt
	    * @Date: 2017. 6. 29.
	    * @Author :  서 정 길
	    * @Description	:	찜하기에 담긴 해당 상품 갯수
	   */
	@Override
	public int getGoodsWishCnt(CartVO vo) throws Exception {
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("GOODSIDX", vo.getGoodsIdx());		// 상품 일련번호
		map.put("MEMBERIDX", vo.getMemberIdx());	// 회원 일련번호
	
		cnt = cartDAO.getGoodsWishCnt(map);
		
		return cnt;
	}

	   /**
	    * @Method : addWish
	    * @Date: 2017. 6. 29.
	    * @Author :  서 정 길
	    * @Description	:	찜하기에 담기
	   */
	@Override
	public int addWish(CartVO vo) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("MEMBERIDX", vo.getMemberIdx());	// 회원 일련번호
		map.put("GOODSIDX", vo.getGoodsIdx());		// 상품 일련번호
		map.put("DEVICE", PathUtil.getDeviceNm());	// 디바이스 (P : PC, M : MOBILE, A : APP)
		map.put("CATEIDX", null);							// 카테고리 일련번호			// TODO 확인 필요
		map.put("REGIDX", vo.getRegIdx());
		map.put("REGHTTPUSERAGENT", vo.getRegHttpUserAgent());
		map.put("REGIP", vo.getRegIp());

		flag = cartDAO.insertWish(map);
		
		return flag;
	}

	   /**
	    * @Method : deleteCart
	    * @Date: 2017. 6. 29.
	    * @Author :  서 정 길
	    * @Description	:	장바구니 상품 삭제
	   */
	@Override
	public int deleteCart(CartVO vo) throws Exception {
		int flag = -1;
		HashMap<String, Object> map = new HashMap<String, Object>();

		try{			
			if(vo.getRegIdx() > 0){
				map.put("MEMBERIDX", vo.getMemberIdx());	// 회원 일련번호
			}else{
				map.put("SESSIONID", vo.getSessionId());		// 비회원 세션 ID
			}
			
			String[] cartIdxes = vo.getCartIdxes().split(",");
			map.put("CARTIDXES", cartIdxes);						// 선택한 장바구니 일련번호
	
			flag = cartDAO.deleteCart(map);
		}catch (Exception e) {
			e.printStackTrace();			
		}
		
		return flag;
	}	

	   /**
	    * @Method : deleteSoldOutCart
	    * @Date: 2017. 6. 29.
	    * @Author :  서 정 길
	    * @Description	:	장바구니 품절 상품 삭제
	   */
	@Override
	public int deleteSoldOutCart(CartVO vo) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();

		try{
			if(vo.getRegIdx() > 0){
				map.put("MEMBERIDX", vo.getMemberIdx());	// 회원 일련번호
			}else{
				map.put("SESSIONID", vo.getSessionId());		// 비회원 세션 ID
			}
			
			flag = cartDAO.deleteSoldOutCart(map);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}

	   /**
	    * @Method : changeCnt
	    * @Date: 2017. 7. 4.
	    * @Author :  서 정 길
	    * @Description	:	장바구니 수량 변경
	   */
	@Override
	public int changeCnt(CartVO vo) throws Exception {
		int flag = -1;
		HashMap<String, Object> map = new HashMap<String, Object>();

		try{			
			if(vo.getRegIdx() > 0){
				map.put("MEMBERIDX", vo.getMemberIdx());	// 회원 일련번호
			}else{
				map.put("SESSIONID", vo.getSessionId());		// 비회원 세션 ID
			}
			
			map.put("CARTIDX", vo.getCartIdxes());					// 수량 변경할 장바구니 일련번호
			map.put("GOODSCNT", vo.getChangeGoodsCnt());	// 변경할 수량
	
			flag = cartDAO.changeCnt(map);
		}catch (Exception e) {
			e.printStackTrace();			
		}
		
		return flag;
	}	

	   /**
	    * @Method : getGoodsInfo
	    * @Date: 2017. 6. 29.
	    * @Author :  서 정 길
	    * @Description	:	장바구니/위시리스트에 담길 상품 정보 (유효한 상품인지 보기 위해)
	   */
	@Override
	public List<SqlMap> getGoodsInfoList(CartVO vo) throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();

		String[] goodsIdxes = vo.getGoodsIdxes().split(",");
		map.put("GOODSIDXES", goodsIdxes);		// 상품 일련번호
		
		try{
			list = cartDAO.getGoodsInfoList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}	

	   /**
	    * @Method : getGiftList
	    * @Date: 2017. 6. 30.
	    * @Author :  서 정 길
	    * @Description	:	사은품 리스트
	   */
	@Override
	public List<SqlMap> getGiftList(String freeYn, Double price) throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("FREEYN", freeYn);		// 무료 구분 (Y : 무료사은품, N:구간사은품)
		if(price != null){						// 금액별 사은품 금액
			map.put("PRICE", price);
		}

		try{
			list = cartDAO.getGiftList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}	

	   /**
	    * @Method : getRecommendProductList
	    * @Date: 2017. 6. 30.
	    * @Author :  서 정 길
	    * @Description	:	추천상품 리스트
	   */
	@Override
	public List<SqlMap> getRecommendProductList(CartVO vo) throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();

		if(vo.getRegIdx() > 0){	// 회원
			map.put("MEMBERIDX", vo.getMemberIdx());	// 회원 일련번호
			map.put("SESSIONID", null);							// 비회원 세션 ID
		}else{							// 비회원
			map.put("MEMBERIDX", null);						// 회원 일련번호
			map.put("SESSIONID", vo.getSessionId());		// 비회원 세션 ID
		}

		try{
			list = cartDAO.getRecommendProductList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	   /**
	    * @Method : getAutoIssueGoodsCouponList
	    * @Date: 2017. 7. 11.
	    * @Author :  서 정 길
	    * @Description	:	상품 자동발급 상품 쿠폰 리스트
	   */
	@Override
	public List<SqlMap> getAutoIssueGoodsCouponList(String goodsIdx) throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx());	// 회원 일련번호
		map.put("GOODSIDX", goodsIdx);		// 상품 일련번호
		map.put("MEMBERGRADE", UserInfo.getUserInfo().getLevelIdx());		// 회원 등급 코드		
		map.put("DEVICE", PathUtil.getDeviceNm());		// 디바이스 (P : PC, M : MOBILE, A : APP)
		
		try{
			list = cartDAO.getAutoIssueGoodsCouponList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}	
	
	/**
	 * @Method : getWishTotalCnt
	 * @Date		: 2017. 7. 18.
	 * @Author	:  유  준  철 
	 * @Description	:	위시 리스트(상품) 총 건수 
	 */
	public int getWishTotalCnt(CartVO vo)throws Exception{
		int cnt = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("GOODSIDX", vo.getGoodsIdx());		
		
		try{
			cnt = cartDAO.getWishTotalCnt(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}
	
	/**
	 * @Method : wishDelete
	 * @Date		: 2017. 8. 24.
	 * @Author	:  유  준  철 
	 * @Description	:	찜 하기 해제
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public int wishDelete(CartVO vo) throws Exception{
		int flag = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("MEMBERIDX", vo.getMemberIdx());			// 회원 일련번호
		map.put("GOODSIDX", vo.getGoodsIdx());				// 상품 일련번호
		
		flag = cartDAO.wishDelete(map);							
		
		return flag;
	}

	   /**
	    * @Method : getShippingPriceList
	    * @Date: 2017. 9. 13.
	    * @Author :  서 정 길
	    * @Description	:	배송비 금액 리스트
	   */
	@Override
	public List<SqlMap> getShippingPriceList() throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();

		try{
			list = cartDAO.getShippingPriceList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	/**
	 * @Method : getCartRecommendList
	 * @Date		: 2019. 7. 23.
	 * @Author	:  임  재  형 
	 * @Description	:	장바구니 추천상품 리스트
	 */
	public List<SqlMap> getCartRecommendList(CartVO vo) throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();

		if(vo.getRegIdx() > 0){	// 회원
			map.put("MEMBERIDX", vo.getMemberIdx());	// 회원 일련번호
			map.put("SESSIONID", null);							// 비회원 세션 ID
		}else{							// 비회원
			map.put("MEMBERIDX", null);						// 회원 일련번호
			map.put("SESSIONID", vo.getSessionId());		// 비회원 세션 ID
		}

		try{
			list = cartDAO.getCartRecommendList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

}
