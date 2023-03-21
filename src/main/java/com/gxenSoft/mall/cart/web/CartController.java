package com.gxenSoft.mall.cart.web;


import java.net.InetAddress;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gxenSoft.mall.cart.service.CartService;
import com.gxenSoft.mall.cart.vo.CartVO;
import com.gxenSoft.mall.common.service.CommonService;
import com.gxenSoft.mall.common.vo.JsonResultVO;
import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.common.web.CommonMethod;
import com.gxenSoft.method.ConvertUtil;
import com.gxenSoft.method.MethodUtil;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.pathUtil.PathUtil;

   /**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : CartController
    * PACKAGE NM : com.gxenSoft.mall.cart.web
    * AUTHOR	 : 서 정 길
    * CREATED DATE  : 2017. 6. 23. 
    * HISTORY :   
    *
    *************************************
    */	
@Controller
public class CartController extends CommonMethod {

	static final Logger logger = LoggerFactory.getLogger(CartController.class);

	@Autowired
	private CartService cartService;
	@Autowired
	private CommonService commonService;

	   /**
	    * @Method : cart
	    * @Date: 2017. 7. 4.
	    * @Author :  서 정 길
	    * @Description	:	장바구니
	   */
	@RequestMapping(value="/cart") 
	public String cart(CartVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
		
		String returnStr = "";
		
		UserInfo userInfo = UserInfo.getUserInfo();
		
		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
			vo.setRegIdx(userInfo.getMemberIdx());
			vo.setMemberIdx(userInfo.getMemberIdx());
			
			// 회원 포인트
			SqlMap memberInfo = cartService.getMemberPoint(userInfo.getMemberIdx());
			if(memberInfo != null){
				model.addAttribute("point", memberInfo.get("pointPrice"));
			}
			
			int couponCnt = cartService.getMemberCouponCnt(vo);
			model.addAttribute("couponCnt", couponCnt);
			
			List<SqlMap> list = cartService.getCartList(vo);	// 장바구니 리스트
			
			List<SqlMap> freeGiftList = cartService.getGiftList("Y", null);						// 무료 사은품 리스트
			List<SqlMap> priceGiftList = cartService.getGiftList("N", null);						// 금액별 사은품 리스트
			//List<SqlMap> recommendList = cartService.getRecommendProductList(vo);		// 추천상품 리스트
			List<SqlMap> shippingPriceList = cartService.getShippingPriceList();				// 배송비 금액 리스트 (상품 체크/해제시 배송비 동적 계산용)
			
			model.addAttribute("list", list);
			model.addAttribute("freeGiftList", freeGiftList);
			model.addAttribute("priceGiftList", priceGiftList);
			//model.addAttribute("recommendList", recommendList);
			model.addAttribute("shippingPriceList", shippingPriceList);

			returnStr = PathUtil.getCtx()+"/cart/cart";
		}else if(vo.getSessionId() == null || vo.getSessionId().isEmpty()){	// 비회원이고 세션 ID 없을때
			vo.setRegIdx(0);
			
			returnStr = PathUtil.getCtx()+"/cart/noMemberCart";	// 로컬스토리지 맨담세션ID 반환
		}else{	// 비회원이고 세션 ID 있을때
			vo.setRegIdx(0);

			List<SqlMap> list = cartService.getCartList(vo);	// 장바구니 리스트
			
			List<SqlMap> freeGiftList = cartService.getGiftList("Y", null);						// 무료 사은품 리스트
			List<SqlMap> priceGiftList = cartService.getGiftList("N", null);						// 금액별 사은품 리스트
			//List<SqlMap> recommendList = cartService.getRecommendProductList(vo);		// 추천상품 리스트
			List<SqlMap> shippingPriceList = cartService.getShippingPriceList();				// 배송비 금액 리스트 (상품 체크/해제시 배송비 동적 계산용)
			
			model.addAttribute("list", list);
			model.addAttribute("freeGiftList", freeGiftList);
			model.addAttribute("priceGiftList", priceGiftList);
			//model.addAttribute("recommendList", recommendList);
			model.addAttribute("shippingPriceList", shippingPriceList);
			
			returnStr = PathUtil.getCtx()+"/cart/cart";
		}
		
		SqlMap bannerInfo = commonService.getBannerOne("CART_ORDER_BANNER");		// 장바구니 배너
		model.addAttribute("bannerInfo", bannerInfo);
		
		// 장바구니 추천상품
		List<SqlMap> cartRecommendList = cartService.getCartRecommendList(vo);
		model.addAttribute("cartRecommendList", cartRecommendList);

		return returnStr;
	}
		
	   /**
	    * @Method : addCartAjax
	    * @Date: 2017. 6. 27.
	    * @Author :  서 정 길
	    * @Description	:	장바구니에 담기 처리
	   */
	@RequestMapping(value = "/ajax/cart/addCartAjax", method = RequestMethod.POST) 
	public @ResponseBody JsonResultVO addCartAjax(@RequestBody List<CartVO> vo, HttpServletRequest request) throws Exception{
		
		JsonResultVO resultMap = new JsonResultVO();

		try{
			resultMap = cartService.addCart(vo, request);
		}catch(Exception e){
			e.printStackTrace();
			resultMap.setResult(false);
			resultMap.setMsg("장바구니 담기중 에러가 발생했습니다!");
		}

		return resultMap;
	}
	
	   /**
	    * @Method : addWishAjax
	    * @Date: 2017. 6. 29.
	    * @Author :  서 정 길
	    * @Description	:	찜하기 처리
	   */
	@RequestMapping(value = "/ajax/wish/addWishAjax", method = RequestMethod.POST) 
	public @ResponseBody JsonResultVO addWishAjax(CartVO vo, HttpServletRequest request) throws Exception{
		
		JsonResultVO resultMap = new JsonResultVO();

		UserInfo userInfo = UserInfo.getUserInfo();
		
		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
			vo.setRegIdx(userInfo.getMemberIdx());
			vo.setMemberIdx(userInfo.getMemberIdx());
		}else{	// 로그인 세션이 만료됐으면
			resultMap.setResult(false);
			resultMap.setErrorCode("99");	// 로그인 페이지 이동
			resultMap.setMsg("로그인 세션이 만료됐습니다. 다시 로그인 해주세요.");
			return resultMap;
		}

		vo.setRegHttpUserAgent(request.getHeader("user-agent"));	// HTTP_USER_AGENT
		InetAddress local = InetAddress.getLocalHost();	// LOCAL IP ADDRESS
		vo.setRegIp(local.getHostAddress());
		
		try{
			vo.setGoodsIdxes(vo.getGoodsIdx());

			List<SqlMap> list = cartService.getGoodsInfoList(vo);	// 장바구니/위시리스트에 담길 상품 정보 (유효한 상품인지 보기 위해)
			if(list == null){
				resultMap.setResult(false);
				resultMap.setMsg("상품정보 검색중 에러가 발생했습니다.");
				return resultMap;
			}else if(list.size() == 0){
				resultMap.setResult(false);
				resultMap.setMsg("존재하지 않는 상품입니다.");
				return resultMap;
			}else{
				if(list.get(0).get("saleStatus").equals("P")){
					resultMap.setResult(false);
					resultMap.setMsg("판매전 상태 상품입니다.");
					return resultMap;
				}else if(list.get(0).get("saleStatus").equals("S")){
					resultMap.setResult(false);
					resultMap.setMsg("판매중단 된 상품입니다.");
					return resultMap;
				}
			}
			
			int cnt = cartService.getGoodsWishCnt(vo);		// 찜하기에 담긴 해당 상품 갯수

			if(cnt > 0){
				cartService.wishDelete(vo);
				resultMap.setWishFlag(false);
			}else{
				cartService.addWish(vo);
				resultMap.setWishFlag(true);
			}
			
			resultMap.setResultCnt(cartService.getWishTotalCnt(vo)); 
			
		}catch(Exception e){
			e.printStackTrace();
			
			resultMap.setResult(false);
			resultMap.setMsg("찜하기 중 에러가 발생했습니다!");
			return resultMap;
		} 
		
		resultMap.setResult(true);
		
		return resultMap;
	}

	   /**
	    * @Method : deleteCart
	    * @Date: 2017. 6. 29.
	    * @Author :  서 정 길
	    * @Description	:	장바구니 삭제
	   */
	@RequestMapping(value="/cart/deleteCart")
	public void deleteCart(CartVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
		
		UserInfo userInfo = UserInfo.getUserInfo();
		
		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
			vo.setRegIdx(userInfo.getMemberIdx());
			vo.setMemberIdx(userInfo.getMemberIdx());
		}else{	// 비회원
			vo.setRegIdx(0);
		}

		int cnt = cartService.deleteCart(vo);		// 장바구니 삭제

		String message = "";
		
		if(cnt > 0){
			message = "삭제되었습니다.";
		}else if(cnt == 0){
			message = "삭제할 상품이 존재하지 않습니다!";
		}else{			
			message = "삭제 중 에러가 발생했습니다!";
		}
		
		MethodUtil.alertMsgUrl(request, response, message, "/cart.do");
	}

	   /**
	    * @Method : deleteSoldOutCart
	    * @Date: 2017. 6. 29.
	    * @Author :  서 정 길
	    * @Description	:	장바구니 품절 상품 삭제
	   */
	@RequestMapping(value="/cart/deleteSoldOutCart")
	public void deleteSoldOutCart(CartVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
		
		UserInfo userInfo = UserInfo.getUserInfo();
		
		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
			vo.setRegIdx(userInfo.getMemberIdx());
			vo.setMemberIdx(userInfo.getMemberIdx());
		}else{	// 비회원
			vo.setRegIdx(0);
		}

		int cnt = cartService.deleteSoldOutCart(vo);		// 장바구니 품절 상품 삭제

		String message = "";
		
		if(cnt > 0){
			message = "삭제되었습니다.";
		}else if(cnt == 0){
			message = "삭제할 품절상품이 존재하지 않습니다!";
		}else{			
			message = "품절상품 삭제 중 에러가 발생했습니다!";
		}
		
		MethodUtil.alertMsgUrl(request, response, message, "/cart.do");
	}

	   /**
	    * @Method : changeCnt
	    * @Date: 2017. 7. 4.
	    * @Author :  서 정 길
	    * @Description	:	장바구니 상품 수량 변경
	   */
	@RequestMapping(value="/cart/changeCnt")
	public void changeCnt(CartVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
		
		UserInfo userInfo = UserInfo.getUserInfo();
		
		if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
			vo.setRegIdx(userInfo.getMemberIdx());
			vo.setMemberIdx(userInfo.getMemberIdx());
		}else{	// 비회원
			vo.setRegIdx(0);
		}

		int cnt = cartService.changeCnt(vo);		// 장바구니 수량 변경

		String message = "";
		
		if(cnt > 0){
			message = "수량 변경되었습니다.";
		}else if(cnt == 0){
			message = "변경할 상품이 존재하지 않습니다!";
		}else{			
			message = "수량 변경 중 에러가 발생했습니다!";
		}
		
		MethodUtil.alertMsgUrl(request, response, message, "/cart.do");
	}

	   /**
	    * @Method : getCartListAjax
	    * @Date: 2017. 7. 11.
	    * @Author :  서 정 길
	    * @Description	:	헤더 장바구니 리스트
	   */
	@RequestMapping(value = "/ajax/cart/getCartListAjax", method = RequestMethod.POST) 
	public @ResponseBody JsonResultVO getCartListAjax(CartVO vo, HttpServletRequest request) throws Exception{
		
		JsonResultVO resultMap = new JsonResultVO();

		UserInfo userInfo = UserInfo.getUserInfo();
		
		if(ConvertUtil.nvl(userInfo.getMemberId()).isEmpty() && vo.getSessionId() == null || vo.getSessionId().isEmpty()){	// 비회원이고 세션 ID 없을때
			vo.setRegIdx(0);
			
			resultMap.setResult(false);
			resultMap.setErrorCode("99");	// 로그인 페이지 이동
			resultMap.setMsg("세션 정보가 없습니다!");
			return resultMap;
		}else{
			if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
				vo.setRegIdx(userInfo.getMemberIdx());
				vo.setMemberIdx(userInfo.getMemberIdx());
			}else{	// 비회원
				vo.setRegIdx(0);
			}

			try{
				List<SqlMap> list = cartService.getCartList(vo);	// 장바구니 리스트

				resultMap.setResult(true);
				resultMap.setResultList(list);				
			}catch(Exception e){
				e.printStackTrace();
				
				resultMap.setResult(false);
				resultMap.setMsg("장바구니 상품 목록을 가져오던 중 에러가 발생했습니다!");
				return resultMap;
			}
		}

		return resultMap;
	}

}
