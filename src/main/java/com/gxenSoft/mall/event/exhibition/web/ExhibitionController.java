package com.gxenSoft.mall.event.exhibition.web;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gxenSoft.mall.common.web.CommonMethod;
import com.gxenSoft.mall.event.exhibition.service.ExhibitionService;
import com.gxenSoft.mall.event.exhibition.vo.ExhibitionVO;
import com.gxenSoft.mall.product.service.ProductService;
import com.gxenSoft.method.MethodUtil;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;
import com.gxenSoft.util.pathUtil.PathUtil;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : ExhibitionController
    * PACKAGE NM : com.gxenSoft.mall.event.exhibition.web
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 7. 21. 
    * HISTORY :   
    *
    *************************************
    */
@Controller
public class ExhibitionController extends CommonMethod{
	
	static final Logger logger = LoggerFactory.getLogger(ExhibitionController.class);

	@Autowired
	private ExhibitionService exhibitionService;
	
	@Autowired
	private ProductService productService;
	
	
	/**
	    * @Method : exhibitionList
	    * @Date: 2017. 7. 21.
	    * @Author : 임  재  형
	    * @Description	:	기획전 리스트
	   */
	@RequestMapping("/event/exhibition/exhibitionList")
	public String exhibitionList(ExhibitionVO vo, SearchVO schVO, HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{
		List<SqlMap> bannerList = exhibitionService.getExhibitionBannerList();	// 기획전 배너 리스트
		List<SqlMap> exhibitionList = exhibitionService.getExhibitionList(vo, schVO); // 기획전 리스트

		model.addAttribute("bannerList", bannerList);
		model.addAttribute("exhibitionList", exhibitionList);
		model.addAttribute("VO", vo);
		model.addAttribute("schVO", schVO);
		model.addAttribute("tabType", "2");
		
		return PathUtil.getCtx()+"/event/exhibition/exhibitionList";
	}
	
	/**
	    * @Method : exhibitionView
	    * @Date: 2017. 7. 24.
	    * @Author : 임  재  형
	    * @Description	:	기획전 상세
	   */
	@RequestMapping("/event/exhibition/exhibitionView")
	public String exhibitionView(ExhibitionVO vo, SearchVO schVO, HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{
		SqlMap detail = exhibitionService.getExhibitionDetail(vo); // 기획전 상세
		
		if(detail==null){
			MethodUtil.alertMsgUrl(request, response, "존재하지 않는 기획전 입니다.", "/event/exhibition/exhibitionList.do");
		}else{
			List<SqlMap> exhibitionList = exhibitionService.getExhibitionList(vo, schVO); // 기획전 리스트
			
			List<SqlMap> hashtagList = exhibitionService.getHashtagList(vo); // 기획전 해시태그 리스트
			List<SqlMap> bestList = exhibitionService.getBestList(vo); // 베스트 기획전 리스트
			
			List<SqlMap> goodsList = exhibitionService.getExhibitionGoodsList(vo);	// 기획전 상품 리스트
			List<SqlMap> groupList = exhibitionService.getExhibitionGroupList(vo); 	// 기획전 그룹 리스트

			List<SqlMap> productSubImgList = productService.getProductSubImgList();
			
			model.addAttribute("productSubImgList", productSubImgList);
			
			model.addAttribute("exhibitionList", exhibitionList);
			model.addAttribute("detail", detail);
			model.addAttribute("hashtagList", hashtagList);
			model.addAttribute("bestList", bestList);
			model.addAttribute("goodsList", goodsList);
			model.addAttribute("groupList", groupList);
			model.addAttribute("schVO", schVO);
			model.addAttribute("tabType", "2");
		}
		return PathUtil.getCtx()+"/event/exhibition/exhibitionView";
	}
	
}
