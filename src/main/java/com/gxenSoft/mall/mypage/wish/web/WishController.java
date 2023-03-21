package com.gxenSoft.mall.mypage.wish.web;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gxenSoft.mall.common.web.CommonMethod;
import com.gxenSoft.mall.mypage.wish.service.WishService;
import com.gxenSoft.mall.mypage.wish.vo.WishVO;
import com.gxenSoft.mall.product.service.ProductService;
import com.gxenSoft.method.MethodUtil;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.Page;
import com.gxenSoft.util.page.SearchVO;
import com.gxenSoft.util.pathUtil.PathUtil;


@Controller
public class WishController extends CommonMethod{
	
	static final Logger logger = LoggerFactory.getLogger(WishController.class);
	
	@Autowired
	private WishService wishService;

	@Autowired
	private ProductService productService;

	/**
	    * @Method : wishList
	    * @Date: 2017. 8. 5.
	    * @Author : 임  재  형
	    * @Description	:	위시 리스트
	   */
	@RequestMapping("/mypage/wish/wishList") 
	public String wishList(WishVO vo, SearchVO schVO, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		if (session.getAttribute("SS_MEMBER_FLAG")  == null) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
		}
		
		if (session.getAttribute("SS_MEMBER_FLAG").equals("N")) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
		}
		
		int totalCount = wishService.getWishListCnt(schVO); // 위시 리스트 총 개수
		List<SqlMap> wishList = wishService.getWishList(schVO); // 위시 리스트
		
		Page page = new Page(); 
		page.pagingInfo(schVO, totalCount);
		
		List<SqlMap> productSubImgList = productService.getProductSubImgList();	// 상품 서브이미지 리스트
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("wishList", wishList);
		model.addAttribute("page", page);
		model.addAttribute("productSubImgList", productSubImgList);
		model.addAttribute("schVO", schVO);
		
		return PathUtil.getCtx()+"/mypage/wish/wishList";
	}
	
	/**
	    * @Method : wishDelete
	    * @Date: 2017. 8. 5.
	    * @Author : 임  재  형
	    * @Description	:	위시 리스트 삭제 (하트 : 1개 상품)
	   */
	@RequestMapping("/mypage/wish/wishDelete")
	public void wishDelete(WishVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception {
		if (session.getAttribute("SS_MEMBER_FLAG")  == null) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
		}
		
		if (session.getAttribute("SS_MEMBER_FLAG").equals("N")) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
		}
		
		wishService.wishDelete(vo); // 위시 삭제
		
		MethodUtil.alertMsgUrl(request, response, "해당 상품이 위시 리스트에서 삭제 되었습니다.", "/mypage/wish/wishList.do");
	}
	
	/**
	    * @Method : wishDeleteAjax
	    * @Date: 2017. 8. 5.
	    * @Author : 임  재  형
	    * @Description	:	위시 리스트 삭제(체크 박스)
	   */
	@RequestMapping(value = "/ajax/mypage/wish/wishDeleteAjax", method = RequestMethod.POST) 
	public @ResponseBody int wishDeleteAjax(WishVO vo, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		if (session.getAttribute("SS_MEMBER_FLAG")  == null) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
		}
		
		if (session.getAttribute("SS_MEMBER_FLAG").equals("N")) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
		}
		
		int flag = 0;
		
		flag = wishService.wishDeleteAjax(vo); // 위시 리스트 삭제(체크 박스)
		
		return flag;
	}
	
}
