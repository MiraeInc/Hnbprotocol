package com.gxenSoft.mall.event.timeSale.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gxenSoft.mall.cart.service.CartService;
import com.gxenSoft.mall.cart.vo.CartVO;
import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.common.web.CommonMethod;
import com.gxenSoft.mall.event.timeSale.service.TimeSaleService;
import com.gxenSoft.mall.order.service.OrderService;
import com.gxenSoft.mall.product.service.ProductService;
import com.gxenSoft.mall.product.vo.ProductVO;
import com.gxenSoft.method.ConvertUtil;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.pathUtil.PathUtil;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : TimeSaleController
 * PACKAGE NM : com.gxenSoft.mall.event.timeSale.web
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 7. 20. 
 * HISTORY :
 
 *************************************
 */
@Controller
public class TimeSaleController extends CommonMethod{
	
	static final Logger logger = LoggerFactory.getLogger(TimeSaleController.class);
	
	@Autowired
	private TimeSaleService timeSaleService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping("/event/timeSale/timeSaleList")
	public String timeSaleList(HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{
		SqlMap detail = timeSaleService.getTimeSaleDetail(null);						// 타임세일 상세 (null 이면 brand_idx 조건 주지 않음)
		
		List<SqlMap> list = null;
		SqlMap productSubInfo = null;
		
		if(detail != null){
		
			String timesaleIdx = detail.get("timesaleIdx").toString();
			timeSaleService.getTimeSaleProductList(timesaleIdx);						// 타임 세일 진행 예정 상품 리스트
			
			ProductVO vo = new ProductVO();
			vo.setGoodsIdx(detail.get("goodsIdx").toString());
			productSubInfo = productService.getProductSubInfo(vo);									// 리뷰 건수 , 리뷰 평균
		
		
			CartVO cartVo = new CartVO();
			cartVo.setGoodsIdx(detail.get("goodsIdx").toString());
			int wishTotalCnt = cartService.getWishTotalCnt(cartVo);
			
			String billkeyYn =  "";
			UserInfo userInfo = UserInfo.getUserInfo();
			if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
				SqlMap billkeyInfo = orderService.selectMainBillkey(userInfo.getMemberIdx());	// 대표 빌키 구하기
				if(billkeyInfo == null || billkeyInfo.size() == 0){		//등록된 빌키가 없을경우
					billkeyYn =  "N";
				}else{
					billkeyYn = "Y";
				}
			}		
			model.addAttribute("wishTotalCnt", wishTotalCnt);
			model.addAttribute("billkeyYn", billkeyYn);
		}
		
		model.addAttribute("detail", detail);
		model.addAttribute("list", list);
		model.addAttribute("productSubInfo", productSubInfo);
		model.addAttribute("tabType", "3");
	
		return PathUtil.getCtx()+"/event/timeSale/timeSaleList";
	}

}
