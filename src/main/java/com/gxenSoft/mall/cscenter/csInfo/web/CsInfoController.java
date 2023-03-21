package com.gxenSoft.mall.cscenter.csInfo.web;


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

import com.gxenSoft.mall.common.service.CommonService;
import com.gxenSoft.mall.common.web.CommonMethod;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.pathUtil.PathUtil;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : CsInfoController
    * PACKAGE NM : com.gxenSoft.mall.cscenter.csInfo.web
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 7. 25. 
    * HISTORY :   
    *
    *************************************
    */
@Controller
public class CsInfoController extends CommonMethod{
	
	static final Logger logger = LoggerFactory.getLogger(CsInfoController.class);

	@Autowired
	public CommonService commonService;
	
	
	/**
	    * @Method : benefitInfo
	    * @Date: 2017. 7. 25.
	    * @Author : 임  재  형
	    * @Description	:	혜택 안내
	   */
	@RequestMapping("/cscenter/csInfo/benefitInfo")
	public String benefitInfo(HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		List<SqlMap> eventList = commonService.getEventList();	// 이벤트 리스트
		List<SqlMap> payBanefitBanner = commonService.getMainBannerList("BANNER_PAYBENEFIT"); // 결제혜택배너 리스트
		SqlMap bannerInfo = commonService.getBannerOne("CSINFO_BANNER");		// 결제혜택 배너
		
		model.addAttribute("eventList", eventList);
		model.addAttribute("payBenefitBanner", payBanefitBanner);
		model.addAttribute("bannerInfo", bannerInfo);
		model.addAttribute("tabType", "4");
		
		return PathUtil.getCtx()+"/cscenter/csInfo/benefitInfo";
	}
	
}
