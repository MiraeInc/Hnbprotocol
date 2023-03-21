package com.gxenSoft.mall.event.event.web;


import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gxenSoft.mall.common.service.CommonService;
import com.gxenSoft.mall.common.vo.JsonResultVO;
import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.common.web.CommonMethod;
import com.gxenSoft.mall.event.event.service.EventService;
import com.gxenSoft.mall.event.event.vo.EventVO;
import com.gxenSoft.method.ConvertUtil;
import com.gxenSoft.method.MethodUtil;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.Page;
import com.gxenSoft.util.page.SearchVO;
import com.gxenSoft.util.pathUtil.PathUtil;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : EventController
    * PACKAGE NM : com.gxenSoft.mall.event.event.web
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 7. 20. 
    * HISTORY :   
    *
    *************************************
    */
@Controller
public class EventController extends CommonMethod{
	
	static final Logger logger = LoggerFactory.getLogger(EventController.class);

	@Autowired
	private EventService eventService;
	@Autowired
	private CommonService commonService;
	
	
	/**
	    * @Method : eventList
	    * @Date: 2017. 7. 20.
	    * @Author : 임  재  형
	    * @Description	:	이벤트 리스트
	   */
	@RequestMapping("/event/event/eventList")
	public String eventList(EventVO vo, SearchVO schVO, HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{
		List<SqlMap> bannerList = eventService.getEventBannerList();	// 이벤트 배너 리스트
		SqlMap cardBenefitInfo = commonService.getHtmlinfo("HTMLINFO20"); // 카드 할인 안내
		
		List<SqlMap> ingEventList = eventService.getIngEventList(vo); // 진행 중 이벤트 리스트
		
		//int endTotalCount = eventService.getEndEventListCnt(); // 종료 된 이벤트 리스트 총 개수
		//List<SqlMap> endEventList = eventService.getEndEventList(schVO); // 종료 된 이벤트 리스트
		
		//Page page = new Page(); 
		//page.pagingInfo(schVO, endTotalCount);
		
		model.addAttribute("bannerList", bannerList);
		model.addAttribute("cardBenefitInfo", cardBenefitInfo);
		model.addAttribute("ingEventList", ingEventList);
		//model.addAttribute("endEventList", endEventList);
		//model.addAttribute("page", page);
		model.addAttribute("VO", vo);
		model.addAttribute("schVO", schVO);
		model.addAttribute("tabType", "1");
		
		return PathUtil.getCtx()+"/event/event/eventList";
	}
	
	/**
	    * @Method : eventView
	    * @Date: 2017. 7. 20.
	    * @Author : 임  재  형
	    * @Description	:	이벤트 상세
	   */
	@RequestMapping("/event/event/eventView")
	public String eventView(EventVO vo, SearchVO schVO, HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{
		SqlMap detail = eventService.getEventDetail(vo); // 이벤트 상세
		
		if(detail==null){
			MethodUtil.alertMsgUrl(request, response, "종료 된 이벤트 입니다.", "/event/event/eventList.do");
		}else{
			//벚꽃이벤트 qr코드때문에 강제로 redirect 예외처리
			if (vo.getEventIdx().equals(165)) {
				PrintWriter printWriter = null;
				
		        response.setCharacterEncoding("utf-8");
		        response.setContentType("text/html;charset=utf-8");
		        
		        printWriter = response.getWriter();
		        printWriter.println("<script type='text/javascript'>");
		        printWriter.println("location.href=\""+request.getContextPath()+"/event/entry/blossomEvent.do\"");
		        printWriter.println("</script>");
		        printWriter.flush();
		        printWriter.close();
			} else {
				List<SqlMap> ingEventList = eventService.getIngEventList(vo); // 진행 중 이벤트 리스트
				
				schVO.setSchCheck("LIST");
				List<SqlMap> eventReplyList = eventService.getEventReplyList(vo, schVO); // 이벤트 댓글 리스트
				
				schVO.setSchCheck("CNT");
				List<SqlMap> eventReplyCnt = eventService.getEventReplyList(vo, schVO); // 이벤트 댓글 개수
				int replyCount = eventReplyCnt.size();
				//int replyCount = eventService.getEventReplyListCnt(vo); // 이벤트 댓글 리스트 총 개수
				
				for(int i=0; i<eventReplyList.size(); i++){
					eventReplyList.get(i).put("replyContents", MethodUtil.repl(eventReplyList.get(i).get("replyContents").toString()));
				}
				
				Page page = new Page(); 
				page.pagingInfo(schVO, replyCount);
				
				model.addAttribute("ingEventList", ingEventList);
				model.addAttribute("detail", detail);
				model.addAttribute("replyCount", replyCount);
				model.addAttribute("eventReplyList", eventReplyList);
				model.addAttribute("page", page);
				model.addAttribute("schVO", schVO);
				model.addAttribute("tabType", "1");
			}
		}
		return PathUtil.getCtx()+"/event/event/eventView";
		
		
	}
	
	/**
	    * @Method : eventReplySave
	    * @Date: 2017. 7. 20.
	    * @Author : 임  재  형
	    * @Description	:	이벤트 댓글 등록
	   */
	@RequestMapping("/ajax/event/event/eventReplySave")
	public @ResponseBody int eventReplySave(EventVO vo, HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{
		int flag = 0;
		
		if (UserInfo.getUserInfo().getMemberIdx() == null) {
			flag = -100;
		} else {
			flag = eventService.eventReplySave(vo); // 이벤트 댓글 등록
		}
		
		return flag;
	}
	
	/**
	    * @Method : eventReplyDeleteAjax
	    * @Date: 2017. 7. 20.
	    * @Author : 임  재  형
	    * @Description	:	이벤트 댓글 삭제
	   */
	@RequestMapping("/ajax/event/event/eventReplyDeleteAjax")
	public @ResponseBody int eventReplyDeleteAjax(EventVO vo, HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{
		int flag = 0;
		
		if (UserInfo.getUserInfo().getMemberIdx() == null) {
			flag = -100;
		} else {
			flag = eventService.eventReplyDeleteAjax(vo, UserInfo.getUserInfo().getMemberIdx()); // 이벤트 댓글 삭제
		}
		
		return flag;
	}

	
	/**
	    * @Method : couponBook
	    * @Date: 2017. 7. 20.
	    * @Author : 강 병 철
	    * @Description	:	쿠폰북
	   */
	@RequestMapping("/event/couponBook")
	public String couponBook(SearchVO schVO, HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{
		if (UserInfo.getUserInfo().getMemberIdx() == null) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
			return null;
		}
		
		List<SqlMap> couponBook = eventService.couponBookList(schVO); // 쿠폰북 목록
		int couponBookCnt = eventService.couponBookListCnt(); // 쿠폰북 목록 총 개수
		
		if(PathUtil.getDevice().equals("m")){
			Page page = new Page(); 
			page.pagingInfo(schVO, couponBookCnt);
			
			model.addAttribute("page", page);
			model.addAttribute("schVO", schVO);
		}
		
		model.addAttribute("couponBook", couponBook);
		model.addAttribute("tabType", "4");
		
		return PathUtil.getCtx()+"/event/couponBook";
	}

	/**
	    * @Method : couponDownloadAjax
	    * @Date: 2017. 7. 20.
	    * @Author : 강 병 철
	    * @Description	:	쿠폰북 다운로드
	   */
	@RequestMapping(value="/ajax/event/couponDownloadAjax", method = RequestMethod.POST)
	public @ResponseBody JsonResultVO couponDownloadAjax(int couponIdx, HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{

		UserInfo userInfo = UserInfo.getUserInfo();
		JsonResultVO resultMap = new JsonResultVO();		
		if(!ConvertUtil.nvl(userInfo.getMemberIdx().toString()).isEmpty()){	// 회원
			int memberIdx = Integer.parseInt(userInfo.getMemberIdx().toString());
			if (couponIdx == 0 ) {
				resultMap.setResult(false);
				resultMap.setMsg("쿠폰번호가 없습니다..");
			}
			else {
				try{
					SqlMap	info = eventService.couponDownloadAjax(memberIdx, couponIdx);

					if(info == null){
						resultMap.setResult(false);
						resultMap.setMsg("쿠폰 다운로드 중 에러가 발생했습니다!");
						return resultMap;
					}else{
						if(!info.get("result").toString().equals("TRUE")){
							resultMap.setResult(false);
							resultMap.setMsg(info.get("result").toString());
							return resultMap;
						}
					}

					resultMap.setResult(true);
					
				}catch(Exception e){
					e.printStackTrace();
					resultMap.setResult(false);
					resultMap.setMsg("쿠폰다운로드 중 에러가 발생했습니다!");
				}
			}
			
		}
		else
		{
			resultMap.setErrorCode("100");
			resultMap.setResult(false);
			resultMap.setMsg("로그인이 필요합니다.");
		}
		return resultMap;
	}
	
	/**
	    * @Method : couponDownloadAjax
	    * @Date: 2017. 7. 20.
	    * @Author : 강 병 철
	    * @Description	:	쿠폰북 다운로드
	   */
	@RequestMapping(value="/ajax/event/couponAllDownAjax", method = RequestMethod.POST)
	public @ResponseBody JsonResultVO couponAllDownAjax(HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{

		UserInfo userInfo = UserInfo.getUserInfo();
		JsonResultVO resultMap = new JsonResultVO();		
		if(!ConvertUtil.nvl(userInfo.getMemberIdx().toString()).isEmpty()){	// 회원
			int memberIdx = Integer.parseInt(userInfo.getMemberIdx().toString());
			
			try{
				SqlMap	info = eventService.couponAllDownAjax(memberIdx);

				if(info == null){
					resultMap.setResult(false);
					resultMap.setMsg("쿠폰 다운로드 중 에러가 발생했습니다!");
					return resultMap;
				}else{
					if(!info.get("result").toString().equals("TRUE")){
						resultMap.setResult(false);
						resultMap.setMsg(info.get("result").toString());
						return resultMap;
					}
				}
				resultMap.setResult(true);
				resultMap.setData1(info.get("downCnt").toString()); //'x'개의 쿠폰이 다운되었습니다.
			}catch(Exception e){
				e.printStackTrace();
				resultMap.setResult(false);
				resultMap.setMsg("쿠폰다운로드 중 에러가 발생했습니다!");
			}
			
		}
		else
		{
			resultMap.setErrorCode("100");
			resultMap.setResult(false);
			resultMap.setMsg("로그인이 필요합니다.");
		}
		return resultMap;
	}
	
	/**
	    * @Method : eventVoteSave
	    * @Date: 2019. 4. 18.
	    * @Author : 임  재  형
	    * @Description	:	이벤트 투표하기
	   */
	@RequestMapping("/ajax/event/event/eventVoteSave")
	public @ResponseBody int eventVoteSave(int eventIdx, int voteNum, HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{
		int flag = 0;
		
		try {
			if (UserInfo.getUserInfo().getMemberIdx() == null) {
				flag = -100;
			} else {
				flag = eventService.eventVoteSave(eventIdx, voteNum); // 이벤트 투표하기
			}
		} catch (Exception e){
			response.sendError(1000);
		}
		
		return flag;
	}
	
	/**
	    * @Method : getVoteNum
	    * @Date: 2019. 4. 18.
	    * @Author : 임  재  형
	    * @Description	:	이벤트 투표 카운트 가져오기
	   */
	@RequestMapping("/ajax/event/event/getVoteNum")
	public @ResponseBody JsonResultVO eventVoteSave(int eventIdx, HttpServletRequest request , HttpServletResponse response , Model model)throws Exception{
		JsonResultVO resultMap = new JsonResultVO();
		
		try {
			SqlMap	info = eventService.getVoteNum(eventIdx);
			
			resultMap.setResult(true);
			resultMap.setData1(info.get("voteSum1").toString());
			resultMap.setData2(info.get("voteSum2").toString());
			resultMap.setData3(info.get("voteSum3").toString());
			resultMap.setData4(info.get("voteSum4").toString());
			resultMap.setData5(info.get("voteSum5").toString());
			resultMap.setData6(info.get("voteSum6").toString());
		} catch (Exception e){
			e.printStackTrace();
			resultMap.setResult(false);
			resultMap.setMsg("투표 정보를 가져오던중 에러가 발생했습니다!");
		}
		
		return resultMap;
	}
}
