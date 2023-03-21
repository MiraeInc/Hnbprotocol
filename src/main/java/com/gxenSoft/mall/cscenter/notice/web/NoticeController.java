package com.gxenSoft.mall.cscenter.notice.web;


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
import com.gxenSoft.mall.cscenter.faq.service.FaqService;
import com.gxenSoft.mall.cscenter.notice.service.NoticeService;
import com.gxenSoft.mall.cscenter.notice.vo.NoticeVO;
import com.gxenSoft.method.MethodUtil;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.Page;
import com.gxenSoft.util.page.SearchVO;
import com.gxenSoft.util.pathUtil.PathUtil;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : NoticeController
    * PACKAGE NM : com.gxenSoft.mall.cscenter.notice.web
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 7. 25. 
    * HISTORY :   
    *
    *************************************
    */
@Controller
public class NoticeController extends CommonMethod{
	
	static final Logger logger = LoggerFactory.getLogger(NoticeController.class);

	@Autowired
	private NoticeService noticeService;
	@Autowired
	private FaqService faqService;
	@Autowired
	private CommonService commonService;
	
	
	/**
	    * @Method : csMain
	    * @Date: 2017. 7. 25.
	    * @Author : 임  재  형
	    * @Description	:	고객센터 메인
	   */
	@RequestMapping("/cscenter/csMain")
	public String csMain(NoticeVO noticeVO, SearchVO schVO, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		List<SqlMap> faqType = commonService.getCodeList("FAQ_TYPE"); // FAQ 유형 리스트
		List<SqlMap> faqList = faqService.getFaqList(schVO); // 메인 FAQ 리스트
		
		if(PathUtil.getDevice().equals("m")){
			List<SqlMap> faqTop5List = faqService.getFaqTop5List(schVO); // FAQ Top5 리스트
			model.addAttribute("faqTop5List", faqTop5List);
		}
		
		List<SqlMap> noticeList = noticeService.getMainNoticeList(schVO); // 메인 공지사항 리스트
		
		model.addAttribute("faqType", faqType);
		model.addAttribute("faqList", faqList);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("schVO", schVO);
		model.addAttribute("tabType", "0");
		
		return PathUtil.getCtx()+"/cscenter/csMain";
	}
	
	/**
	    * @Method : noticeList
	    * @Date: 2017. 7. 25.
	    * @Author : 임  재  형
	    * @Description	:	공지사항 리스트
	   */
	@RequestMapping("/cscenter/notice/noticeList")
	public String noticeList(NoticeVO vo, SearchVO schVO, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		int topCount = noticeService.getNoticeTopListCnt(schVO); // 공지사항 Top 리스트 개수
		List<SqlMap> noticeTopList = noticeService.getNoticeTopList(schVO); // 공지사항 Top 리스트 [검색 조건이 없을 경우에만 노출] :: 08.16 다시 노출 되도록 수정
		int totalCount = noticeService.getNoticeListCnt(schVO); // 공지사항 리스트 총 개수
		List<SqlMap> noticeList = noticeService.getNoticeList(schVO); // 공지사항 리스트
		
		List<SqlMap> noticeType = commonService.getCodeList("NOTICE_TYPE"); // 공지사항 유형 리스트
		
		Page page = new Page(); 
		page.pagingInfo(schVO, totalCount);
		
		model.addAttribute("topCount", topCount);
		model.addAttribute("noticeTopList", noticeTopList);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("noticeType", noticeType);
		model.addAttribute("page", page);
		model.addAttribute("schVO", schVO);
		model.addAttribute("tabType", "1");
		
		return PathUtil.getCtx()+"/cscenter/notice/noticeList";
	}
	
	/**
	    * @Method : noticeView
	    * @Date: 2017. 7. 26.
	    * @Author : 임  재  형
	    * @Description	:	공지사항 상세
	   */
	@RequestMapping("/cscenter/notice/noticeView")
	public String noticeView(NoticeVO vo, SearchVO schVO, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		SqlMap detail = noticeService.getNoticeDetail(vo); // 공지사항 상세
		List<SqlMap> fileList = noticeService.getNoticeFileList(vo);	// 공지사항 파일 리스트
		SqlMap prevNotice = noticeService.getPrevNotice(vo, schVO); // 이전 게시글
		SqlMap nextNotice = noticeService.getNextNotice(vo, schVO); // 다음 게시글
		
		model.addAttribute("detail", detail);
		model.addAttribute("fileList", fileList);
		model.addAttribute("prevNotice", prevNotice);
		model.addAttribute("nextNotice", nextNotice);
		model.addAttribute("searchHiddenParams" , MethodUtil.searchHiddenParams(schVO));
		model.addAttribute("tabType", "1");
		
		return PathUtil.getCtx()+"/cscenter/notice/noticeView";
	}
	
}
