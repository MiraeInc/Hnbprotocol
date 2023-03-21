package com.gxenSoft.mall.mypage.inquiry.web;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.util.TextUtils;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gxenSoft.fileUtil.FileUtil;
import com.gxenSoft.fileUtil.FileVO;
import com.gxenSoft.mall.common.service.CommonService;
import com.gxenSoft.mall.common.vo.JsonResultVO;
import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.common.web.CommonMethod;
import com.gxenSoft.mall.mypage.inquiry.service.InquiryService;
import com.gxenSoft.mall.mypage.inquiry.vo.InquiryVO;
import com.gxenSoft.method.DateUtil;
import com.gxenSoft.method.MethodUtil;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.Page;
import com.gxenSoft.util.page.SearchVO;
import com.gxenSoft.util.pathUtil.PathUtil;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : InquiryController
    * PACKAGE NM : com.gxenSoft.mall.mypage.inquiry.web
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 8. 3. 
    * HISTORY :   
    *
    *************************************
    */
@Controller
public class InquiryController extends CommonMethod{
	
	static final Logger logger = LoggerFactory.getLogger(InquiryController.class);
	
	@Autowired
	private InquiryService inquiryService;
	@Autowired
	private CommonService commonService;

	
	/**
	    * @Method : inquiryList
	    * @Date: 2017. 8. 3.
	    * @Author : 임  재  형
	    * @Description	:	1:1 문의 리스트
	   */
	@RequestMapping("/mypage/inquiry/inquiryList") 
	public String inquiryList(InquiryVO vo, SearchVO schVO, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		UserInfo userInfo = UserInfo.getUserInfo();
		
		if(session.getAttribute("SS_MEMBER_FLAG") != null){	// 로그인
			if(session.getAttribute("SS_MEMBER_FLAG").equals("Y")){	// 회원 로그인
				vo.setMemberIdx(userInfo.getMemberIdx());
			}else{	// 비회원 로그인
				vo.setMemberIdx(0);
				vo.setNomemberOrderCd((String)session.getAttribute("SS_NOMEMBER_ORDER_CD"));	// 비회원 로그인 주문 코드
			}
		}else{	// 미로그인
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
		}
		
		// 조회일자 최초 3개월 기본 세팅
		if(TextUtils.isEmpty(schVO.getSchType())){
			// 3달 전
			Calendar mon = Calendar.getInstance();
			mon.add(Calendar.MONTH , -3);
			schVO.setSchStartDt(new java.text.SimpleDateFormat("yyyy-MM-dd").format(mon.getTime()));

			// 오늘
			Date today = new Date();         
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			schVO.setSchEndDt(date.format(today));
		}
		
		List<SqlMap> inquiryType = commonService.getCodeList("INQUIRY_TYPE"); // 문의 유형
		int totalCount = inquiryService.getInquiryListCnt(vo, schVO); 			// 1:1 문의 리스트 총 개수
		List<SqlMap> inquiryList = inquiryService.getInquiryList(vo, schVO); 	// 1:1 문의 리스트
		for(int i=0; i<inquiryList.size(); i++){
			inquiryList.get(i).put("questnDesc", MethodUtil.repl(inquiryList.get(i).get("questnDesc").toString()));
		}
		
		Page page = new Page(); 
		page.pagingInfo(schVO, totalCount);
		
		model.addAttribute("inquiryType", inquiryType);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("inquiryList", inquiryList);
		model.addAttribute("page", page);
		model.addAttribute("schVO", schVO);
		
		return PathUtil.getCtx()+"/mypage/inquiry/inquiryList";
	}
	
	/**
	    * @Method : inquirySave
	    * @Date: 2017. 8. 4.
	    * @Author : 임  재  형
	    * @Description	:	1:1 문의 삭제
	   */
	@RequestMapping("/mypage/inquiry/inquiryDelete")
	public void inquiryDelete(InquiryVO vo, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception {
		UserInfo userInfo = UserInfo.getUserInfo();
		
		if(session.getAttribute("SS_MEMBER_FLAG") != null){	// 로그인
			if(session.getAttribute("SS_MEMBER_FLAG").equals("Y")){	// 회원 로그인
				vo.setMemberIdx(userInfo.getMemberIdx());
			}else{	// 비회원 로그인
				vo.setMemberIdx(0);
			}
		}else{	// 미로그인
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
		}
		
		inquiryService.inquiryDelete(vo); // 1:1 문의 삭제
		
		MethodUtil.alertStatusMsg(request, response, "D", "/mypage/inquiry/inquiryList.do");
	}
	
	/**
	    * @Method : inquiryWrite
	    * @Date: 2017. 8. 6.
	    * @Author : 임  재  형
	    * @Description	:	1:1 문의 작성/수정
	   */
	@RequestMapping("/mypage/inquiry/inquiryWrite")
	public String inquiryWrite(InquiryVO vo, SearchVO searchVO, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception {
		if (session.getAttribute("SS_MEMBER_FLAG")  == null) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
		}
		
		if(session.getAttribute("SS_MEMBER_FLAG").equals("N")){	// 비회원 로그인
			SqlMap orderDetail = inquiryService.getInquiryOrderDetail((String)session.getAttribute("SS_NOMEMBER_ORDER_CD")); // 비회원 주문 정보
			
			if(orderDetail != null && orderDetail.get("senderPhoneNo") != null ){
				String phoneNo = (String) orderDetail.get("senderPhoneNo");
				orderDetail.put("SENDER_PHONE_NO", phoneNo.replaceAll("-", ""));
			}
			
			model.addAttribute("orderDetail", orderDetail);
		}
		
		List<SqlMap> inquiryType = commonService.getCodeList("INQUIRY_TYPE"); // 문의 유형

		if(vo.getStatusFlag().equals("U")){
			SqlMap detail = inquiryService.getInquiryDetail(vo); // 1:1 문의 상세
			
			if(detail != null && detail.get("questnPhone") != null ){
				String phoneNo = (String) detail.get("questnPhone");
				detail.put("QUESTN_PHONE", phoneNo.replaceAll("-", ""));
			}
			
			if (!StringUtils.isEmpty(detail.get("qImg1"))) {
				String qImg1 = detail.get("qImg1").toString();
				detail.put("qImgPath1", File.separator + "inquiry" + File.separator +detail.get("inquiryIdx").toString()+ File.separator+ FileUtil.getFileName(qImg1)+"_T162"+"."+FileUtil.getFileExt(qImg1));
			}
			if (!StringUtils.isEmpty(detail.get("qImg2"))) {
				String qImg2 = detail.get("qImg2").toString();
				detail.put("qImgPath2", File.separator + "inquiry" + File.separator +detail.get("inquiryIdx").toString()+ File.separator+ FileUtil.getFileName(qImg2)+"_T162"+"."+FileUtil.getFileExt(qImg2));
			}
			if (!StringUtils.isEmpty(detail.get("qImg3"))) {
				String qImg3 = detail.get("qImg3").toString();
				detail.put("qImgPath3", File.separator + "inquiry" + File.separator +detail.get("inquiryIdx").toString()+ File.separator+ FileUtil.getFileName(qImg3)+"_T162"+"."+FileUtil.getFileExt(qImg3));
			}
			if (!StringUtils.isEmpty(detail.get("qImg4"))) {
				String qImg4 = detail.get("qImg4").toString();
				detail.put("qImgPath4", File.separator + "inquiry" + File.separator +detail.get("inquiryIdx").toString()+ File.separator+ FileUtil.getFileName(qImg4)+"_T162"+"."+FileUtil.getFileExt(qImg4));
			}
			
			model.addAttribute("detail", detail);
		}
		
		model.addAttribute("MEMBERFLAG", session.getAttribute("SS_MEMBER_FLAG"));
		model.addAttribute("inquiryType", inquiryType);
		model.addAttribute("VO" , vo);
		model.addAttribute("searchHiddenParams" , MethodUtil.searchHiddenParams(searchVO));
		
		return PathUtil.getCtx()+"/mypage/inquiry/inquiryWrite";
	}
	
	/**
	    * @Method : inquirySave
	    * @Date: 2017. 8. 6.
	    * @Author : 임  재  형
	    * @Description	:	1:1 문의 등록, 수정
	   */
	@RequestMapping("/mypage/inquiry/inquirySave")
	public void inquirySave(InquiryVO vo, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		UserInfo userInfo = UserInfo.getUserInfo();
		
		if(session.getAttribute("SS_MEMBER_FLAG") != null){	// 로그인
			if(session.getAttribute("SS_MEMBER_FLAG").equals("Y")){	// 회원 로그인
				vo.setMemberIdx(userInfo.getMemberIdx());
			}else{	// 비회원 로그인
				vo.setMemberIdx(0);
				vo.setNomemberOrderCd((String)session.getAttribute("SS_NOMEMBER_ORDER_CD"));	// 비회원 로그인 주문 코드
			}
		}else{	// 미로그인
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
		}
		
		inquiryService.inquirySave(vo); // 1:1 문의 등록, 수정
		
		String msg = "";
		if(vo.getStatusFlag().equals("I")){
			msg = "문의가 등록 되었습니다.";
		}else if(vo.getStatusFlag().equals("U")){
			msg = "문의가 수정 되었습니다.";
		}
		
		MethodUtil.alertMsgUrl(request, response, msg, "/mypage/inquiry/inquiryList.do");
	}
	
	/**
	    * @Method : inquiryFileupload
	    * @Date: 2017. 8. 6.
	    * @Author : 임  재  형
	    * @Description	:	1:1 문의 파일 업로드
	   */
	@RequestMapping(value = "/mypage/inquiry/inquiryFileupload", produces="application/json")
	@ResponseBody
	public String inquiryFileupload(MultipartFile[] fileData, HttpServletRequest request )throws  Exception{
		HashMap<String, Object> result = new HashMap<String, Object>();
		List<String> arrFile = new ArrayList<String>();		
		List<String> oarrFile = new ArrayList<String>();		
		
		for (MultipartFile file : fileData) { 
			String path = "";
			String opath = ""; 
			FileVO fvo = FileUtil.multiPart2Img(file, "inquiryTemp"+File.separator+DateUtil.dateFormat("yyyyMMdd") );	
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
	    * @Method : inquiryGoodsAjax
	    * @Date: 2017. 8. 7.
	    * @Author : 임  재  형
	    * @Description	:	1:1 상품 검색
	   */
	@RequestMapping(value = "/ajax/mypage/inquiry/inquiryGoodsAjax", method = RequestMethod.POST) 
	public @ResponseBody JsonResultVO inquiryGoodsAjax(InquiryVO vo, SearchVO schVO, HttpServletRequest request) throws Exception{
		JsonResultVO resultMap = new JsonResultVO();

		try{
			int totalCount = inquiryService.getInquiryGoodsListCnt(vo, schVO);	// 1:1 상품 검색 리스트 총 개수
			List<SqlMap> list = inquiryService.getInquiryGoodsList(vo, schVO);	// 1:1 상품 검색 리스트

			Page page = new Page(); 
			page.pagingInfo(schVO, totalCount);
		
			resultMap.setResult(true);
			resultMap.setResultCnt(totalCount);
			resultMap.setResultList(list);		
			resultMap.setPage(page);
		}catch(Exception e){
			e.printStackTrace();
			
			resultMap.setResult(false);
			resultMap.setMsg("상품 목록을 가져오던 중 에러가 발생했습니다!");
			return resultMap;
		}
		
		return resultMap;
	}
	
	/**
	    * @Method : inquiryOrderAjax
	    * @Date: 2017. 8. 8.
	    * @Author : 임  재  형
	    * @Description	:	1:1 주문 검색
	   */
	@RequestMapping(value = "/ajax/mypage/inquiry/inquiryOrderAjax", method = RequestMethod.POST) 
	public @ResponseBody JsonResultVO inquiryOrderAjax(InquiryVO vo, SearchVO schVO, HttpServletRequest request, HttpSession session) throws Exception{
		JsonResultVO resultMap = new JsonResultVO();

		UserInfo userInfo = UserInfo.getUserInfo();
		
		if(session.getAttribute("SS_MEMBER_FLAG").equals("Y")){	// 회원 로그인
			vo.setMemberIdx(userInfo.getMemberIdx()); // 회원 일련번호
		}else{	// 비회원 로그인
			vo.setMemberIdx(0);
			vo.setNomemberOrderCd((String)session.getAttribute("SS_NOMEMBER_ORDER_CD"));	// 비회원 로그인 주문 코드
		}
		
		try{
			int totalCount = inquiryService.getInquiryOrderListCnt(vo, schVO);		// 1:1 문의 주문 검색 리스트 총 개수
			List<SqlMap> list = inquiryService.getInquiryOrderList(vo, schVO);	// 1:1 문의 주문 검색 리스트

			Page page = new Page(); 
			page.pagingInfo(schVO, totalCount);
		
			resultMap.setResult(true);
			resultMap.setResultCnt(totalCount);
			resultMap.setResultList(list);		
			resultMap.setPage(page);
		}catch(Exception e){
			e.printStackTrace();
			
			resultMap.setResult(false);
			resultMap.setMsg("주문 목록을 가져오던 중 에러가 발생했습니다!");
			return resultMap;
		}
		
		return resultMap;
	}

}
