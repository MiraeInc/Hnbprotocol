package com.gxenSoft.mall.etc.web;


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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.common.web.CommonMethod;
import com.gxenSoft.mall.etc.service.EtcService;
import com.gxenSoft.mall.etc.vo.SearchConditionVO;
import com.gxenSoft.mall.product.service.ProductService;
import com.gxenSoft.method.ConvertUtil;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.pathUtil.PathUtil;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : EtcController
    * PACKAGE NM : com.gxenSoft.mall.etc.web
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 7. 26. 
    * HISTORY :   
    *
    *************************************
    */
@Controller
public class EtcController extends CommonMethod{
	
	static final Logger logger = LoggerFactory.getLogger(EtcController.class);

	@Autowired
	private EtcService etcService;
		
	@Autowired
	private ProductService productService;
	
	/**
	    * @Method : contactUs
	    * @Date: 2017. 7. 26.
	    * @Author : 임  재  형
	    * @Description	:	CONTACT US
	   */
	@RequestMapping("/etc/contactUs")
	public String contactUs(HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{
		
		return PathUtil.getCtx()+"/etc/contactUs";
	}
	
	/**
	    * @Method : terms
	    * @Date: 2017. 7. 26.
	    * @Author : 임  재  형
	    * @Description	:	이용약관
	   */
	@RequestMapping("/etc/terms")
	public String terms(HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{
		
		model.addAttribute("tabType", "1");
		return PathUtil.getCtx()+"/etc/terms";
	}
	
	/**
	    * @Method : privacy
	    * @Date: 2017. 7. 26.
	    * @Author : 임  재  형
	    * @Description	:	개인정보취급방침
	   */
	@RequestMapping("/etc/privacy")
	public String privacy(HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{
		
		model.addAttribute("tabType", "2");
		return PathUtil.getCtx()+"/etc/privacy";
	}
	
	/**
	    * @Method : sitemap
	    * @Date: 2017. 7. 26.
	    * @Author : 임  재  형
	    * @Description	:	사이트맵
	   */
	@RequestMapping("/etc/sitemap")
	public String sitemap(HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{
		
		return PathUtil.getCtx()+"/etc/sitemap";
	}
	/**
	    * @Method : searchResult
	    * @Date: 2017. 7. 26.
	    * @Author : 강 병 철
	    * @Description	:	검색 결과
	   */
	@RequestMapping("/etc/searchResult")
	public String  searchResult(SearchConditionVO searchVo, HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{
	
		String Device = PathUtil.getCtx();
		if (Device.length() > 0) {
			Device = Device.substring(Device.length()-1, Device.length()).toUpperCase();
		}
		
		//검색어 로그 저장
		if (!searchVo.getKeyword().trim().isEmpty()) {
			UserInfo userInfo = UserInfo.getUserInfo();
			Integer memberIdx = 0;
			if(!ConvertUtil.nvl(userInfo.getMemberId()).isEmpty()){	// 회원
				memberIdx = userInfo.getMemberIdx();
			}
			InetAddress local = InetAddress.getLocalHost();	// LOCAL IP ADDRESS		
			String userAgent = request.getHeader("user-agent");	// HTTP_USER_AGENT
			String RegIp = local.getHostAddress();
			
			etcService.insertKeywordLog(searchVo.getKeyword().trim(), memberIdx, userAgent, RegIp, Device);
		}
		

		if (Device.equals("M")) {
			searchVo.setDevice("M");
		} else {
			searchVo.setDevice("P");
		}
		
		//상품검색
		List<SqlMap> productList = etcService.productSearchResult(searchVo);
		int productCnt = etcService.productSearchCnt(searchVo);
		
		List<SqlMap> productSubImgList = productService.getProductSubImgList();
		
		model.addAttribute("productSubImgList", productSubImgList);
		model.addAttribute("keyword", searchVo.getKeyword());
		model.addAttribute("productList", productList);
		model.addAttribute("productCnt", productCnt);
		
		//스타일검색일경우에는 상품검색만 함
		if (!searchVo.getKeyword().isEmpty()) {
			//기획전
			List<SqlMap> exhibitList = etcService.getExhibitList(searchVo);
			int exhibitCnt = etcService.getExhibitListCnt(searchVo);
			
			//스타일팁
			int styleTipCnt = etcService.getTipListCnt(searchVo); // 스타일 팁 리스트 총 개수
			List<SqlMap> styleTipList = etcService.getTipList(searchVo); // 스타일 팁 리스트
			
			model.addAttribute("exhibitList", exhibitList);
			model.addAttribute("exhibitCnt", exhibitCnt);
			model.addAttribute("styleTipList", styleTipList);
			model.addAttribute("styleTipCnt", styleTipCnt);
		}

		List<SqlMap> hashList = etcService.getMainHashtagList(); //추천 해시태그 목록
		model.addAttribute("searchVo", searchVo);
		model.addAttribute("hashList", hashList);
		
		return PathUtil.getCtx()+"/etc/searchResult";
	}
	
	   /**
	    * @Method : productMoreAjax
	    * @Date: 2017. 7. 12.
	    * @Author :  강병철
	    * @Description	:	상품 검색 more
	   */
	@RequestMapping(value = "/ajax/etc/productMoreAjax") 
	public  ModelAndView productMoreAjax(SearchConditionVO searchVo, HttpServletRequest request, HttpSession session) throws Exception{
		ModelAndView mav = new ModelAndView();
		
		String Device = PathUtil.getCtx();
		if (Device.length() > 0) {
			Device = Device.substring(Device.length()-1, Device.length()).toUpperCase();
		}
		
		if (Device.equals("M")) {
			searchVo.setDevice("M");
		} else {
			searchVo.setDevice("P");
		}
		
		List<SqlMap> productList = etcService.productSearchResult(searchVo);
		mav.addObject("productList", productList);
		mav.setViewName(PathUtil.getCtx()+"/etc/productMoreView");		
		return mav;
	}
	
	   /**
	    * @Method : exhibitMoreAjax
	    * @Date: 2017. 7. 12.
	    * @Author :  강병철
	    * @Description	:	top검색
	   */
	@RequestMapping(value = "/ajax/etc/exhibitMoreAjax") 
	public  ModelAndView exhibitMoreAjax(SearchConditionVO searchVo, HttpServletRequest request, HttpSession session) throws Exception{
		ModelAndView mav = new ModelAndView();
		List<SqlMap> exhibitList = etcService.getExhibitList(searchVo);
		mav.addObject("exhibitList", exhibitList);
		mav.setViewName(PathUtil.getCtx()+"/etc/exhibitMoreView");		
		return mav;
	}
	
	   /**
	    * @Method : tipMoreAjax
	    * @Date: 2017. 7. 12.
	    * @Author :  강병철
	    * @Description	:	스타일팁 검색 more
	   */
	@RequestMapping(value = "/ajax/etc/tipMoreAjax") 
	public  ModelAndView tipMoreAjax(SearchConditionVO searchVo, HttpServletRequest request, HttpSession session) throws Exception{
		ModelAndView mav = new ModelAndView();
		List<SqlMap> styleTipList = etcService.getTipList(searchVo);
		mav.addObject("styleTipList", styleTipList);
		mav.setViewName(PathUtil.getCtx()+"/etc/tipMoreView");		
		return mav;
	}

	   /**
	    * @Method : adMoreAjax
	    * @Date: 2017. 7. 12.
	    * @Author :  강병철
	    * @Description	:	스타일팁 검색 more
	   */
	@RequestMapping(value = "/ajax/etc/adMoreAjax") 
	public  ModelAndView adMoreAjax(SearchConditionVO searchVo, HttpServletRequest request, HttpSession session) throws Exception{
		ModelAndView mav = new ModelAndView();
		List<SqlMap> adList = etcService.getBrandAdList(searchVo);
		mav.addObject("adList", adList);
		mav.setViewName(PathUtil.getCtx()+"/etc/adMoreView");		
		return mav;
	}

	   /**
	    * @Method : magazMoreAjax
	    * @Date: 2017. 7. 12.
	    * @Author :  강병철
	    * @Description	:	매거진 검색 more
	   */
	@RequestMapping(value = "/ajax/etc/magazMoreAjax") 
	public  ModelAndView magazMoreAjax(SearchConditionVO searchVo, HttpServletRequest request, HttpSession session) throws Exception{
		ModelAndView mav = new ModelAndView();
		List<SqlMap> magazList = etcService.getMagazineList(searchVo);
		mav.addObject("magazList", magazList);
		mav.setViewName(PathUtil.getCtx()+"/etc/magazMoreView");		
		return mav;
	}
	
	/**
	    * @Method : reviewMoreAjax
	    * @Date: 2017. 8. 12.
	    * @Author : 임  재  형
	    * @Description	:	후기 검색 more
	   */
	@RequestMapping(value = "/ajax/etc/reviewMoreAjax") 
	public  ModelAndView reviewMoreAjax(SearchConditionVO searchVo, HttpServletRequest request, HttpSession session) throws Exception{
		ModelAndView mav = new ModelAndView();
		List<SqlMap> reviewList = etcService.getReviewList(searchVo);
		mav.addObject("reviewList", reviewList);
		mav.setViewName(PathUtil.getCtx()+"/etc/reviewMoreView");		
		return mav;
	}
}
