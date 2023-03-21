package com.gxenSoft.mall.cscenter.faq.web;


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
import org.springframework.web.bind.annotation.ResponseBody;

import com.gxenSoft.mall.common.service.CommonService;
import com.gxenSoft.mall.common.web.CommonMethod;
import com.gxenSoft.mall.cscenter.faq.service.FaqService;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.Page;
import com.gxenSoft.util.page.SearchVO;
import com.gxenSoft.util.pathUtil.PathUtil;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : FaqController
    * PACKAGE NM : com.gxenSoft.mall.cscenter.faq.web
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 7. 26. 
    * HISTORY :   
    *
    *************************************
    */
@Controller
public class FaqController extends CommonMethod{
	
	static final Logger logger = LoggerFactory.getLogger(FaqController.class);

	@Autowired
	private FaqService faqService;
	@Autowired
	private CommonService commonService;
	
	
	/**
	    * @Method : faqList
	    * @Date: 2017. 7. 26.
	    * @Author : 임  재  형
	    * @Description	:	FAQ 리스트
	   */
	@RequestMapping("/cscenter/faq/faqList")
	public String faqList(SearchVO schVO, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		List<SqlMap> faqType = commonService.getCodeList("FAQ_TYPE"); // FAQ 유형 리스트
		int totalCount = faqService.getFaqListCnt(schVO); // FAQ 리스트 총 개수
		List<SqlMap> faqList = faqService.getFaqList(schVO); // FAQ 리스트
		
		Page page = new Page(); 
		page.pagingInfo(schVO, totalCount);
		
		model.addAttribute("faqType", faqType);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("faqList", faqList);
		model.addAttribute("page", page);
		model.addAttribute("schVO", schVO);
		model.addAttribute("tabType", "2");
		
		return PathUtil.getCtx()+"/cscenter/faq/faqList";
	}
	
	/**
	    * @Method : faqReadCntAjax
	    * @Date: 2017. 8. 17.
	    * @Author : 임  재  형
	    * @Description	:	FAQ 조회수 증가
	   */
	@RequestMapping("/ajax/cscenter/faqReadCntAjax")
	public @ResponseBody int faqReadCntAjax(int faqIdx, HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{
		int flag = 0;
		
		try {
			flag = faqService.addFaqReadCnt(faqIdx); // FAQ 조회수 증가
		} catch (Exception e){
			response.sendError(1000);
		}
		
		return flag;
	}
	
}
