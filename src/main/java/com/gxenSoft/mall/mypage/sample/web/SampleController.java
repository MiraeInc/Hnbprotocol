package com.gxenSoft.mall.mypage.sample.web;

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

import com.gxenSoft.mall.common.web.CommonMethod;
import com.gxenSoft.mall.mypage.sample.service.SampleService;
import com.gxenSoft.method.MethodUtil;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.Page;
import com.gxenSoft.util.page.SearchVO;
import com.gxenSoft.util.pathUtil.PathUtil;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : SampleController
    * PACKAGE NM : com.gxenSoft.mall.mypage.sample.web
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 8. 2. 
    * HISTORY :   
    *
    *************************************
    */
@Controller
public class SampleController extends CommonMethod{
	
	static final Logger logger = LoggerFactory.getLogger(SampleController.class);
	
	@Autowired
	private SampleService sampleService;

	
	/**
	    * @Method : sampleList
	    * @Date: 2017. 8. 2.
	    * @Author : 임  재  형
	    * @Description	:	마이페이지 - 샘플 리스트
	   */
	@RequestMapping("/mypage/sample/sampleList") 
	public String sampleList(SearchVO schVO, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		if (session.getAttribute("SS_MEMBER_FLAG")  == null) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
		}
		
		if (session.getAttribute("SS_MEMBER_FLAG").equals("N")) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
		}
		
		int totalCount = sampleService.getSampleListCnt(schVO); 				// 샘플 리스트 총 개수
		List<SqlMap> sampleList = sampleService.getSampleList(schVO); 	// 샘플 리스트
		List<SqlMap> sampleReplyList = sampleService.getSampleReplyList(schVO); // 샘플 댓글 리스트
		
		Page page = new Page(); 
		page.pagingInfo(schVO, totalCount);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("sampleList", sampleList);
		model.addAttribute("sampleReplyList", sampleReplyList);
		model.addAttribute("page", page);
		model.addAttribute("schVO", schVO);
		
		return PathUtil.getCtx()+"/mypage/sample/sampleList";
	}

}
