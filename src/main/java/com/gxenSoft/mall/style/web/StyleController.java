package com.gxenSoft.mall.style.web;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gxenSoft.fileUtil.FileUtil;
import com.gxenSoft.fileUtil.FileVO;
import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.common.web.CommonMethod;
import com.gxenSoft.mall.style.service.StyleService;
import com.gxenSoft.mall.style.vo.CounselVO;
import com.gxenSoft.mall.style.vo.HowtouseVO;
import com.gxenSoft.mall.style.vo.SampleVO;
import com.gxenSoft.mall.style.vo.TipVO;
import com.gxenSoft.method.DateUtil;
import com.gxenSoft.method.MethodUtil;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.Page;
import com.gxenSoft.util.page.SearchVO;
import com.gxenSoft.util.pathUtil.PathUtil;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : StyleController
    * PACKAGE NM : com.gxenSoft.mall.style.web
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 7. 13. 
    * HISTORY :   
    *
    *************************************
    */
@Controller
public class StyleController extends CommonMethod{
	
	static final Logger logger = LoggerFactory.getLogger(StyleController.class);

	@Autowired
	private StyleService styleService;
	
	
	/**
	    * @Method : counselList
	    * @Date: 2017. 7. 13.
	    * @Author : 임  재  형
	    * @Description	:	스타일 상담 리스트
	   */
	@RequestMapping("/style/counselList")
	public String counselList(CounselVO vo, SearchVO schVO, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		int totalCount = styleService.getCounselListCnt(vo, schVO); // 스타일 상담 리스트 총 개수
		List<SqlMap> counselList = styleService.getCounselList(vo, schVO); // 스타일 상담 리스트
		
		Page page = new Page(); 
		page.pagingInfo(schVO, totalCount);
		
		model.addAttribute("schVO", schVO);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("counselList", counselList);
		model.addAttribute("page", page);
		model.addAttribute("VO", vo);
		model.addAttribute("tabType", "2");
		
		return PathUtil.getCtx()+"/style/counselList";
	}
	
	/**
	    * @Method : counselWrite
	    * @Date: 2017. 7. 13.
	    * @Author : 임  재  형
	    * @Description	:	스타일 상담 작성 페이지
	   */
	@RequestMapping("/style/counselWrite")
	public String counselWrite(CounselVO vo, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		if (UserInfo.getUserInfo().getMemberIdx() == null) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
			return null;
		} else {
			vo.setRegIdx(UserInfo.getUserInfo().getMemberIdx());			
		}

		
		if("U".equals(vo.getStatusFlag())){
			SqlMap detail = styleService.counselView(vo); // 스타일 상담 상세
			
			if (!StringUtils.isEmpty(detail.get("qImg1"))) {
				String qImg1 = detail.get("qImg1").toString();
				detail.put("qImgPath1", File.separator + "counsel" + File.separator +detail.get("counselIdx").toString()+ File.separator+ FileUtil.getFileName(qImg1)+"_T162"+"."+FileUtil.getFileExt(qImg1));
			}
			if (!StringUtils.isEmpty(detail.get("qImg2"))) {
				String qImg2 = detail.get("qImg2").toString();
				detail.put("qImgPath2", File.separator + "counsel" + File.separator +detail.get("counselIdx").toString()+ File.separator+ FileUtil.getFileName(qImg2)+"_T162"+"."+FileUtil.getFileExt(qImg2));
			}
			if (!StringUtils.isEmpty(detail.get("qImg3"))) {
				String qImg3 = detail.get("qImg3").toString();
				detail.put("qImgPath3", File.separator + "counsel" + File.separator +detail.get("counselIdx").toString()+ File.separator+ FileUtil.getFileName(qImg3)+"_T162"+"."+FileUtil.getFileExt(qImg3));
			}
			if (!StringUtils.isEmpty(detail.get("qImg4"))) {
				String qImg4 = detail.get("qImg4").toString();
				detail.put("qImgPath4", File.separator + "counsel" + File.separator +detail.get("counselIdx").toString()+ File.separator+ FileUtil.getFileName(qImg4)+"_T162"+"."+FileUtil.getFileExt(qImg4));
			}
			
			if (!StringUtils.isEmpty(detail.get("aImg1"))) {
				String aImg1 = detail.get("aImg1").toString();
				detail.put("aImgPath1", File.separator + "counsel" + File.separator +detail.get("counselIdx").toString()+ File.separator+ FileUtil.getFileName(aImg1)+"_T162"+"."+FileUtil.getFileExt(aImg1));
			}
			if (!StringUtils.isEmpty(detail.get("aImg2"))) {
				String aImg2 = detail.get("aImg2").toString();
				detail.put("aImgPath2", File.separator + "counsel" + File.separator +detail.get("counselIdx").toString()+ File.separator+ FileUtil.getFileName(aImg2)+"_T162"+"."+FileUtil.getFileExt(aImg2));
			}
			if (!StringUtils.isEmpty(detail.get("aImg3"))) {
				String aImg3 = detail.get("aImg3").toString();
				detail.put("aImgPath3", File.separator + "counsel" + File.separator +detail.get("counselIdx").toString()+ File.separator+ FileUtil.getFileName(aImg3)+"_T162"+"."+FileUtil.getFileExt(aImg3));
			}
			if (!StringUtils.isEmpty(detail.get("aImg4"))) {
				String aImg4 = detail.get("aImg4").toString();
				detail.put("aImgPath4", File.separator + "counsel" + File.separator +detail.get("counselIdx").toString()+ File.separator+ FileUtil.getFileName(aImg4)+"_T162"+"."+FileUtil.getFileExt(aImg4));
			}
			
			model.addAttribute("detail", detail);
		}
		
		model.addAttribute("VO", vo);
		model.addAttribute("tabType", "2");
		
		return PathUtil.getCtx()+"/style/counselWrite";
	}
	
	/**
	    * @Method : counselSave
	    * @Date: 2017. 7. 13.
	    * @Author : 임  재  형
	    * @Description	:	스타일 상담 신청
	   */
	@RequestMapping("/style/counselSave")
	public void counselSave(CounselVO vo, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		if (UserInfo.getUserInfo().getMemberIdx() == null) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
		} else {
			vo.setRegIdx(UserInfo.getUserInfo().getMemberIdx());
			
			styleService.counselSave(vo, UserInfo.getUserInfo().getMemberIdx()); // 스타일 상담 신청
			
			MethodUtil.alertStatusMsg(request, response, "I", "/style/counselList.do");
		}
	}

	
	/**
	    * @Method : counselFileupload
	    * @Date: 2017. 7. 14.
	    * @Author : 임  재  형
	    * @Description	:	파일업로드
	   */
	@RequestMapping(value = "/style/counselFileupload", produces="application/json")
	@ResponseBody
	public String counselFileupload(MultipartFile[] fileData, HttpServletRequest request )throws  Exception{
		HashMap<String, Object> result = new HashMap<String, Object>();
		List<String> arrFile = new ArrayList<String>();		
		List<String> oarrFile = new ArrayList<String>();		
		
		for (MultipartFile file : fileData) { 
			String path = "";
			String opath = ""; 
			FileVO fvo = FileUtil.multiPart2Img(file, "counselTemp"+File.separator+DateUtil.dateFormat("yyyyMMdd") );	
			FileUtil.makeThumnail (fvo.getFileFolder() , fvo.getUploadFileNm() ,162 ,162);
			path = File.separator + fvo.getFileFolder() + File.separator + FileUtil.getFileName(fvo.getUploadFileNm())+"_T162"+"."+FileUtil.getFileExt(fvo.getUploadFileNm());
			opath = File.separator + fvo.getFileFolder() + File.separator + fvo.getUploadFileNm();
			arrFile.add(path);
			oarrFile.add(opath);
		} 
		//Object to JSON in String
		result.put("code", "1");
		result.put("msg", "file upload success");
		result.put("files",arrFile);
		result.put("ofiles",oarrFile);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	/**
	    * @Method : counselViewAjax
	    * @Date: 2017. 7. 13.
	    * @Author : 임  재  형
	    * @Description	:	스타일 상담 상세 [레이어]
	   */
	@RequestMapping(value = "/ajax/style/counselViewAjax") 
	public ModelAndView counselViewAjax(CounselVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		
		vo.setMemberIdx(UserInfo.getUserInfo().getMemberIdx());
				
		SqlMap detail = styleService.counselView(vo); // 스타일 상담 상세
		detail.put("qContentsValue", MethodUtil.repl(detail.get("qContents").toString()));
		
		if (!StringUtils.isEmpty(detail.get("qImg1"))) {
			String qImg1 = detail.get("qImg1").toString();
			detail.put("qImgPath1", File.separator + "counsel" + File.separator +detail.get("counselIdx").toString()+ File.separator+ FileUtil.getFileName(qImg1)+"_T162"+"."+FileUtil.getFileExt(qImg1));
		}
		if (!StringUtils.isEmpty(detail.get("qImg2"))) {
			String qImg2 = detail.get("qImg2").toString();
			detail.put("qImgPath2", File.separator + "counsel" + File.separator +detail.get("counselIdx").toString()+ File.separator+ FileUtil.getFileName(qImg2)+"_T162"+"."+FileUtil.getFileExt(qImg2));
		}
		if (!StringUtils.isEmpty(detail.get("qImg3"))) {
			String qImg3 = detail.get("qImg3").toString();
			detail.put("qImgPath3", File.separator + "counsel" + File.separator +detail.get("counselIdx").toString()+ File.separator+ FileUtil.getFileName(qImg3)+"_T162"+"."+FileUtil.getFileExt(qImg3));
		}
		if (!StringUtils.isEmpty(detail.get("qImg4"))) {
			String qImg4 = detail.get("qImg4").toString();
			detail.put("qImgPath4", File.separator + "counsel" + File.separator +detail.get("counselIdx").toString()+ File.separator+ FileUtil.getFileName(qImg4)+"_T162"+"."+FileUtil.getFileExt(qImg4));
		}
		
		if (!StringUtils.isEmpty(detail.get("aImg1"))) {
			String aImg1 = detail.get("aImg1").toString();
			detail.put("aImgPath1", File.separator + "counsel" + File.separator +detail.get("counselIdx").toString()+ File.separator+ FileUtil.getFileName(aImg1)+"_T162"+"."+FileUtil.getFileExt(aImg1));
		}
		if (!StringUtils.isEmpty(detail.get("aImg2"))) {
			String aImg2 = detail.get("aImg2").toString();
			detail.put("aImgPath2", File.separator + "counsel" + File.separator +detail.get("counselIdx").toString()+ File.separator+ FileUtil.getFileName(aImg2)+"_T162"+"."+FileUtil.getFileExt(aImg2));
		}
		if (!StringUtils.isEmpty(detail.get("aImg3"))) {
			String aImg3 = detail.get("aImg3").toString();
			detail.put("aImgPath3", File.separator + "counsel" + File.separator +detail.get("counselIdx").toString()+ File.separator+ FileUtil.getFileName(aImg3)+"_T162"+"."+FileUtil.getFileExt(aImg3));
		}
		if (!StringUtils.isEmpty(detail.get("aImg4"))) {
			String aImg4 = detail.get("aImg4").toString();
			detail.put("aImgPath4", File.separator + "counsel" + File.separator +detail.get("counselIdx").toString()+ File.separator+ FileUtil.getFileName(aImg4)+"_T162"+"."+FileUtil.getFileExt(aImg4));
		}
		
		
		mav.addObject("detail", detail);
		mav.addObject("memberIdx", vo.getMemberIdx());
		
		mav.setViewName(PathUtil.getCtx()+"/style/counselPop");
		
		return mav;
	}
	
	/**
	    * @Method : counselView
	    * @Date: 2017. 8. 18.
	    * @Author : 임  재  형
	    * @Description	:	스타일 상담 상세
	   */
	@RequestMapping(value = "/style/counselView") 
	public String counselView(CounselVO vo, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session) throws Exception{
		vo.setMemberIdx(UserInfo.getUserInfo().getMemberIdx());
		
		SqlMap detail = styleService.counselView(vo); // 스타일 상담 상세
		detail.put("qContentsValue", MethodUtil.repl(detail.get("qContents").toString()));
		
		if (!StringUtils.isEmpty(detail.get("qImg1"))) {
			String qImg1 = detail.get("qImg1").toString();
			detail.put("qImgPath1", File.separator + "counsel" + File.separator +detail.get("counselIdx").toString()+ File.separator+ FileUtil.getFileName(qImg1)+"_T162"+"."+FileUtil.getFileExt(qImg1));
		}
		if (!StringUtils.isEmpty(detail.get("qImg2"))) {
			String qImg2 = detail.get("qImg2").toString();
			detail.put("qImgPath2", File.separator + "counsel" + File.separator +detail.get("counselIdx").toString()+ File.separator+ FileUtil.getFileName(qImg2)+"_T162"+"."+FileUtil.getFileExt(qImg2));
		}
		if (!StringUtils.isEmpty(detail.get("qImg3"))) {
			String qImg3 = detail.get("qImg3").toString();
			detail.put("qImgPath3", File.separator + "counsel" + File.separator +detail.get("counselIdx").toString()+ File.separator+ FileUtil.getFileName(qImg3)+"_T162"+"."+FileUtil.getFileExt(qImg3));
		}
		if (!StringUtils.isEmpty(detail.get("qImg4"))) {
			String qImg4 = detail.get("qImg4").toString();
			detail.put("qImgPath4", File.separator + "counsel" + File.separator +detail.get("counselIdx").toString()+ File.separator+ FileUtil.getFileName(qImg4)+"_T162"+"."+FileUtil.getFileExt(qImg4));
		}
		
		if (!StringUtils.isEmpty(detail.get("aImg1"))) {
			String aImg1 = detail.get("aImg1").toString();
			detail.put("aImgPath1", File.separator + "counsel" + File.separator +detail.get("counselIdx").toString()+ File.separator+ FileUtil.getFileName(aImg1)+"_T162"+"."+FileUtil.getFileExt(aImg1));
		}
		if (!StringUtils.isEmpty(detail.get("aImg2"))) {
			String aImg2 = detail.get("aImg2").toString();
			detail.put("aImgPath2", File.separator + "counsel" + File.separator +detail.get("counselIdx").toString()+ File.separator+ FileUtil.getFileName(aImg2)+"_T162"+"."+FileUtil.getFileExt(aImg2));
		}
		if (!StringUtils.isEmpty(detail.get("aImg3"))) {
			String aImg3 = detail.get("aImg3").toString();
			detail.put("aImgPath3", File.separator + "counsel" + File.separator +detail.get("counselIdx").toString()+ File.separator+ FileUtil.getFileName(aImg3)+"_T162"+"."+FileUtil.getFileExt(aImg3));
		}
		if (!StringUtils.isEmpty(detail.get("aImg4"))) {
			String aImg4 = detail.get("aImg4").toString();
			detail.put("aImgPath4", File.separator + "counsel" + File.separator +detail.get("counselIdx").toString()+ File.separator+ FileUtil.getFileName(aImg4)+"_T162"+"."+FileUtil.getFileExt(aImg4));
		}
		
		model.addAttribute("detail", detail);
		model.addAttribute("memberIdx", vo.getMemberIdx());
		model.addAttribute("VO", vo);
		model.addAttribute("tabType", "2");
		
		return PathUtil.getCtx()+"/style/counselView";
	}
	
	/**
	    * @Method : counselDeleteAjax
	    * @Date: 2017. 7. 14.
	    * @Author : 임  재  형
	    * @Description	:	스타일 상담 삭제	
	   */
	@RequestMapping("/ajax/style/counselDeleteAjax")
	public @ResponseBody int counselDeleteAjax(CounselVO vo, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		int flag = 0;
		if (UserInfo.getUserInfo().getMemberIdx() == null) {
			flag = -100;
		} else {
			flag = styleService.counselDelete(vo, UserInfo.getUserInfo().getMemberIdx()); // 스타일 상담 삭제
		}
		return flag;		
	}
	
	/**
	    * @Method : sampleList
	    * @Date: 2019. 4. 22.
	    * @Author : 임  재  형
	    * @Description	:	정품신청 리스트
	   */
	@RequestMapping("/style/sampleList")
	public String sampleList(SampleVO vo, SearchVO schVO, HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{
		int totalCount = styleService.getSampleListCnt(vo);	// 정품신청 리스트 총 개수
		List<SqlMap> sampleList = styleService.getSampleList(vo, schVO); // 정품신청 리스트
		
		Page page = new Page(); 
		page.pagingInfo(schVO, totalCount);
		
		model.addAttribute("sampleList", sampleList);
		model.addAttribute("page", page);
		model.addAttribute("VO", vo);
		model.addAttribute("schVO", schVO);
		model.addAttribute("tabType", "5");
		
		return PathUtil.getCtx()+"/style/sampleList";
	}
	
	/**
	    * @Method : sampleView
	    * @Date: 2017. 7. 13.
	    * @Author : 임  재  형
	    * @Description	:	정품신청 신청 페이지
	   */
	@RequestMapping("/style/sampleView")
	public String sampleView(SampleVO vo, SearchVO schVO, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		SqlMap sampleInfo = styleService.getSampleInfo(vo, schVO); // 정품신청 페이지 정보
		
		if(sampleInfo==null){
			MethodUtil.alertMsgUrl(request, response, "존재하지 않는 정품신청 입니다.", "/style/sampleList.do");
		}else{
			String status = String.valueOf(sampleInfo.get("status"));
			String startDt = String.valueOf(sampleInfo.get("startDt"));
			
			if("I".equals(status)){
				vo.setSampleIdx(Integer.parseInt(String.valueOf(sampleInfo.get("sampleIdx"))));
				styleService.addSampleReadCnt(vo); // 정품신청 조회 수 증가
				
				// 댓글 리스트
				schVO.setSchCheck("LIST");
				List<SqlMap> sampleReplyList = styleService.getSampleReplyList(vo, schVO); // 정품신청 댓글 리스트
				for(int i=0; i<sampleReplyList.size(); i++){
					sampleReplyList.get(i).put("replyContents", MethodUtil.repl(sampleReplyList.get(i).get("replyContents").toString()));
				}
				
				// 댓글 리스트 총 개수
				schVO.setSchCheck("CNT");
				List<SqlMap> sampleReplyCnt = styleService.getSampleReplyList(vo, schVO); // 정품신청 댓글 개수
				int replyCount = sampleReplyCnt.size();
				//int replyCount = styleService.getSampleReplyListCnt(vo); // 정품신청 댓글 리스트 총 개수
				
				Page page = new Page(); 
				page.pagingInfo(schVO, replyCount);
				
				model.addAttribute("sampleInfo", sampleInfo);
				model.addAttribute("replyCount", replyCount);
				model.addAttribute("sampleReplyList", sampleReplyList);
				model.addAttribute("page", page);
				model.addAttribute("schVO", schVO);
				model.addAttribute("VO", vo);
				model.addAttribute("tabType", "5");
			}else if("E".equals(status)){
				MethodUtil.alertMsgUrl(request, response, "정품신청 기간이 종료되었습니다. 곧 당첨자 발표 예정입니다.", "/style/sampleList.do");
			}else if("P".equals(status)){
				MethodUtil.alertMsgUrl(request, response, startDt+"일부터 신청이 가능 합니다.", "/style/sampleList.do");
			}else{
				MethodUtil.alertMsgUrl(request, response, "존재하지 않는 정품신청 입니다.", "/style/sampleList.do");
			}
			
		}
		
		return PathUtil.getCtx()+"/style/sampleView";
	}
	
	/**
	    * @Method : sampleReplySave
	    * @Date: 2017. 7. 13.
	    * @Author : 임  재  형
	    * @Description	:	정품신청 댓글 등록
	   */
	@RequestMapping("/ajax/style/sampleReplySave")
	public @ResponseBody int sampleReplySave(SampleVO vo, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		int flag = 0;
		
		if (UserInfo.getUserInfo().getMemberIdx() == null) {
			flag = -100;
		} else {
			flag = styleService.sampleReplySave(vo); // 정품신청 댓글 등록
		}
		
		return flag;
	}
	
	/**
	    * @Method : sampleReplyDeleteAjax
	    * @Date: 2017. 7. 20.
	    * @Author : 임  재  형
	    * @Description	:	정품신청 댓글 삭제
	   */
	@RequestMapping("/ajax/style/sampleReplyDeleteAjax")
	public @ResponseBody int sampleReplyDeleteAjax(SampleVO vo, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		int flag = 0;
		
		if (UserInfo.getUserInfo().getMemberIdx() == null) {
			flag = -100;
		} else {
			flag = styleService.sampleReplyDeleteAjax(vo, UserInfo.getUserInfo().getMemberIdx()); // 정품신청 댓글 삭제
		}
		
		return flag;
	}

	
	/**
	    * @Method : tipList
	    * @Date: 2017. 7. 14.
	    * @Author : 임  재  형
	    * @Description	:	스타일 팁 리스트
	   */
	@RequestMapping("/style/tipList")
	public String tipList(TipVO vo, SearchVO schVO, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		int totalCount = styleService.getTipListCnt(schVO, vo); // 스타일 팁 리스트 총 개수
		List<SqlMap> styleTipList = styleService.getTipList(schVO, vo); // 스타일 팁 리스트
		
		SqlMap tipBrandCnt = styleService.getTipBrandCnt(schVO, vo);	// 스타일 팁 브랜드 별 개수
		
		Page page = new Page(); 
		page.pagingInfo(schVO, totalCount);
		
		model.addAttribute("schVO", schVO);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("styleTipList", styleTipList);
		model.addAttribute("tipBrandCnt", tipBrandCnt);
		model.addAttribute("page", page);
		model.addAttribute("VO", vo);
		model.addAttribute("tabType", "1");
		
		return PathUtil.getCtx()+"/style/tipList";
	}
	
	/**
	    * @Method : tipViewAjax
	    * @Date: 2017. 7. 14.
	    * @Author : 임  재  형
	    * @Description	:	스타일 팁 상세 [레이어]
	   */
	@RequestMapping(value = "/ajax/style/tipViewAjax") 
	public ModelAndView tipViewAjax(TipVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		
		SqlMap detail = styleService.getTipView(vo); // 스타일 팁 상세
		
		mav.addObject("detail", detail);
		
		mav.setViewName(PathUtil.getCtx()+"/style/tipPop");
		
		return mav;
	}
	
	/**
	    * @Method : tipView
	    * @Date: 2017. 8. 18.
	    * @Author : 임  재  형
	    * @Description	:	스타일 팁 상세
	   */
	@RequestMapping("/style/tipView")
	public String tipView(TipVO vo, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		SqlMap detail = styleService.getTipView(vo); // 스타일 팁 상세
		
		model.addAttribute("detail", detail);
		model.addAttribute("VO", vo);
		model.addAttribute("tabType", "1");
		
		return PathUtil.getCtx()+"/style/tipView";
	}
	
	/**
	    * @Method : howtouseList
	    * @Date: 2017. 7. 14.
	    * @Author : 임  재  형
	    * @Description	:	상품 사용법 리스트
	   */
	@RequestMapping(value = "/style/howtouseList")
	public String howtouseList(HowtouseVO vo, SearchVO schVO, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		int totalCount = styleService.getHowtouseListCnt(vo, schVO); // 상품 사용법 리스트 총 개수
		List<SqlMap> howtouseList = styleService.getHowtouseList(vo, schVO); // 상품 사용법 리스트
		
		SqlMap howtoBrandCnt = styleService.getHowtoBrandCnt(schVO, vo);	// 상품 사용법 브랜드 별 개수
		
		Page page = new Page(); 
		page.pagingInfo(schVO, totalCount);
		
		model.addAttribute("schVO", schVO);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("howtouseList", howtouseList);
		model.addAttribute("howtoBrandCnt", howtoBrandCnt);
		model.addAttribute("page", page);
		model.addAttribute("VO", vo);
		model.addAttribute("tabType", "3");
		
		return PathUtil.getCtx()+"/style/howtouseList";
	}
	
	/**
	    * @Method : howtouseView
	    * @Date: 2017. 7. 14.
	    * @Author : 임  재  형
	    * @Description	:	상품 사용법 상세
	   */
	@RequestMapping(value = "/style/howtouseView", method = RequestMethod.GET)
	public String howtouseView(HowtouseVO vo, SearchVO schVO, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		SqlMap howtouseDetailMaster = styleService.getHowtouseDetailMaster(vo, schVO); // 상품 사용법 마스터
		List<SqlMap> howtouseDetailList = styleService.getHowtouseDetailList(vo, schVO); // 상품 사용법 상세
		
		model.addAttribute("VO", vo);
		model.addAttribute("master", howtouseDetailMaster);
		model.addAttribute("howtouseDetailList", howtouseDetailList);
		model.addAttribute("tabType", "3");
		
		return PathUtil.getCtx()+"/style/howtouseView";
	}

	/**
	    * @Method : sampleApplAjax
	    * @Date: 2019. 4. 22.
	    * @Author : 임  재  형
	    * @Description	:	정품신청
	   */
	@RequestMapping("/ajax/style/sampleApplAjax")
	public @ResponseBody int sampleApplAjax(SampleVO vo, HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{
		int flag = 0;
		
		if (UserInfo.getUserInfo().getMemberIdx() == null) {
			flag = -100;
		} else {
			flag = styleService.sampleApplAjax(vo); // 정품신청
		}
		
		return flag;
	}
	
	/**
	    * @Method : winnerCheckAjax
	    * @Date: 2019. 4. 23.
	    * @Author : 임  재  형
	    * @Description	:	당첨여부 체크
	   */
	@RequestMapping("/ajax/style/winnerCheckAjax")
	public @ResponseBody int winnerCheckAjax(SampleVO vo, HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{
		int flag = 0;
		
		if (UserInfo.getUserInfo().getMemberIdx() == null) {
			flag = -100;
		} else {
			flag = styleService.winnerCheckAjax(vo); // 당첨여부 체크
		}
		
		return flag;
	}

}
