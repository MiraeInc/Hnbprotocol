package com.gxenSoft.mall.event.entry.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.common.web.CommonMethod;
import com.gxenSoft.mall.event.entry.service.EntryService;
import com.gxenSoft.mall.event.entry.vo.CreatorVO;
import com.gxenSoft.mall.event.entry.vo.EntryPredictVO;
import com.gxenSoft.mall.event.entry.vo.EntryVO;
import com.gxenSoft.mall.event.event.service.EventService;
import com.gxenSoft.mall.event.event.vo.EventVO;
import com.gxenSoft.mall.member.service.MemberService;
import com.gxenSoft.mall.member.vo.MemberVO;
import com.gxenSoft.method.DateUtil;
import com.gxenSoft.method.MethodUtil;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.Page;
import com.gxenSoft.util.page.SearchVO;
import com.gxenSoft.util.pathUtil.PathUtil;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : EntryController
 * PACKAGE NM : com.gxenSoft.gatsbyMall.event.entry.web
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 8. 22. 
 * HISTORY :
 
 *************************************
 */
@Controller
public class EntryController extends CommonMethod {
	
	static final Logger logger = LoggerFactory.getLogger(EntryController.class);
	
	@Autowired
	private EntryService entryService;
	
	@Autowired
	private EventService eventService;

	@Autowired
	private MemberService memberService;
	/**
	 * @Method : entryEvent
	 * @Date		: 2017. 8. 22.
	 * @Author	:  유  준  철 
	 * @Description	:	일회성 응모 이벤트 페이지
	 */
	@RequestMapping("/event/entry/entryEvent")
	public String entryEvent(EntryVO vo, HttpServletRequest request, HttpServletResponse response, Model model)throws Exception{
		model.addAttribute("tabType", "1");
		return PathUtil.getCtx()+"/event/entry/entryEvent";
	}
	
	/**
	 * @Method : entryNoCheckAjax
	 * @Date		: 2017. 8. 22.
	 * @Author	:  유  준  철 
	 * @Description	:	응모 번호 체크
	 */
	@RequestMapping("/ajax/event/entry/entryNoCheckAjax") 
	public @ResponseBody int entryNoCheckAjax(EntryVO vo, HttpServletRequest request, HttpServletResponse response)throws Exception{
		int flag = entryService.entryNoCheck(vo);
		return flag;
	}
	
	/**
	 * @Method : entryEventSave
	 * @Date		: 2017. 8. 22.
	 * @Author	:  유  준  철 
	 * @Description	:	응모 저장
	 */
	@RequestMapping("/event/entry/entryEventSave") 
	public void entryEventSave(EntryVO vo, HttpServletRequest request, HttpServletResponse response, Model model)throws Exception{
		String currentDate = DateUtil.getCurrentDate("yyyy-MM-dd");		
		SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
        Date nowDate = null;
        Date endDate = null;
        
        try {
        	nowDate = format.parse(currentDate);								// 현재 날짜
        	endDate = format.parse( "2017-09-30" );							// 응모하기 종료 날짜
        } catch (ParseException e) {
            e.printStackTrace();
        }
         
        int compare = nowDate.compareTo(endDate);
        
        if(compare > 0){
        	MethodUtil.alertMsgUrl(request, response, "응모하기 기간이 종료 되었습니다.", "/event/entry/entryEvent.do");
        }else{
        	entryService.entryEventSave(vo);
        	MethodUtil.alertMsgUrl(request, response, "응모하기가 완료 되었습니다.", "/event/entry/entryEvent.do");
        }
	}
	
	/**
	 * @Method : entryDateCheckAjax
	 * @Date		: 2017. 8. 22.
	 * @Author	:  유  준  철 
	 * @Description	:	응모하기 종료 여부 체크
	 */
	@RequestMapping("/ajax/event/entry/entryDateCheckAjax") 
	public @ResponseBody int entryDateCheckAjax(EntryVO vo, HttpServletRequest request, HttpServletResponse response)throws Exception{
		int flag=0;
		
		String currentDate = DateUtil.getCurrentDate("yyyy-MM-dd");		
		SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
        Date nowDate = null;
        Date endDate = null;
        
        try {
        	nowDate = format.parse(currentDate);								// 현재 날짜
        	endDate = format.parse( "2017-09-30" );							// 응모하기 종료 날짜
        } catch (ParseException e) {
            e.printStackTrace();
        }
         
        int compare = nowDate.compareTo(endDate);
        
        if (compare > 0){
        	flag = 1;
        }
		
		return flag;
	}
	
	/**
	 * @Method : predictEvent
	 * @Date		: 2018. 1. 22.
	 * @Author	:  유  준  철 
	 * @Description	:	토종비결 이벤트
	 */
	@RequestMapping("/event/entry/predictEvent")
	public String predictEvent(EntryPredictVO vo, HttpServletRequest request, HttpServletResponse response, Model model)throws Exception{
		model.addAttribute("tabType", "1");
		return PathUtil.getCtx()+"/event/entry/predictEvent";
	}
	
	/**
	 * @Method : predictEventSaveAjax
	 * @Date		: 2018. 1. 22.
	 * @Author	:  유  준  철 
	 * @Description	:	토종비결 저장
	 */
	@RequestMapping("/ajax/event/entry/predictEventSaveAjax") 
	public @ResponseBody int predictEventSaveAjax(EntryPredictVO vo, HttpServletRequest request, HttpServletResponse response)throws Exception{
		int flag = entryService.predictEventSave(vo);
		return flag;
	}
	
	/**
	 * @Method : predictDateCheckAjax
	 * @Date		: 2018. 1. 22.
	 * @Author	:  유  준  철 
	 * @Description	:	토종비결 종료 여부 체크
	 */
	@RequestMapping("/ajax/event/entry/predictDateCheckAjax") 
	public @ResponseBody int predictDateCheckAjax(EntryPredictVO vo, HttpServletRequest request, HttpServletResponse response)throws Exception{
		int flag=0;
		
		String currentDate = DateUtil.getCurrentDate("yyyy-MM-dd");		
		SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
        Date nowDate = null;
        Date endDate = null;
        
        try {
        	nowDate = format.parse(currentDate);								// 현재 날짜
        	endDate = format.parse( "2018-02-28" );							// 종료 날짜
        } catch (ParseException e) {
            e.printStackTrace();
        }
         
        int compare = nowDate.compareTo(endDate);
        
        if (compare > 0){
        	flag = 1;
        }
		
		return flag;
	}
	
	/**
	 * @Method : quizEvent
	 * @Date		: 2018. 3. 14.
	 * @Author	:  유  준  철 
	 * @Description	:	퀴즈 이벤트
	 */
	@RequestMapping("/event/entry/quizEvent")
	public String quizEvent(EventVO vo, SearchVO schVO, HttpServletRequest request, HttpServletResponse response, Model model)throws Exception{
		vo.setEventIdx(74);
		SqlMap detail = eventService.getEventDetail(vo); 
		
		if(detail==null){
			MethodUtil.alertMsgUrl(request, response, "종료 된 이벤트 입니다.", "/event/event/eventList.do");
		}else{
			schVO.setSchCheck("LIST");
			List<SqlMap> quizEventList = eventService.getEventReplyList(vo, schVO); 
			
			schVO.setSchCheck("CNT");
			List<SqlMap> quizEventListCnt = eventService.getEventReplyList(vo, schVO); 
			int totalCount = quizEventListCnt.size();
			
			for(int i=0; i<quizEventList.size(); i++){
				quizEventList.get(i).put("replyContents", MethodUtil.repl(quizEventList.get(i).get("replyContents").toString()));
			}
			
			Page page = new Page(); 
			page.pagingInfo(schVO, totalCount);
			
			model.addAttribute("totalCount", totalCount);
			model.addAttribute("quizEventList", quizEventList);
			model.addAttribute("detail", detail);
			model.addAttribute("page", page);
			model.addAttribute("schVO", schVO);
		}
		
		return PathUtil.getCtx()+"/event/entry/quizEvent";
	}
	
	/**
	 * @Method : quizEventSave
	 * @Date		: 2018. 3. 14.
	 * @Author	:  유  준  철 
	 * @Description	:	퀴즈 이벤트 댓글 저장
	 */
	@RequestMapping("/ajax/event/entry/quizEventSaveAjax")
	public @ResponseBody int quizEventSaveAjax(EventVO vo, HttpServletRequest request , HttpServletResponse response)throws Exception{
		int flag = 0;
		vo.setRegIdx(UserInfo.getUserInfo().getMemberIdx());
		
		try {
			flag = eventService.eventReplySave(vo); 	
		} catch (Exception e){
			response.sendError(1000);
		}
		
		return flag;
	}
	
	/**
	 * @Method : quizEventDeleteAjax
	 * @Date		: 2018. 3. 14.
	 * @Author	:  유  준  철 
	 * @Description	:	퀴즈 이벤트 댓글 삭제
	 */
	@RequestMapping("/ajax/event/entry/quizEventDeleteAjax")
	public @ResponseBody int quizEventDeleteAjax(EventVO vo, HttpServletRequest request , HttpServletResponse response)throws Exception{
		int flag = 0;
		
		if (UserInfo.getUserInfo().getMemberIdx() == null) {
			flag = -100;
		}else {
			flag = eventService.eventReplyDeleteAjax(vo, UserInfo.getUserInfo().getMemberIdx()); 
		}
		
		return flag;
	}

	/**
	 * @Method : exhibition201804
	 * @Date		: 2018. 4. 16.
	 * @Author	:  서  정  길 
	 * @Description	:	네이버 타임보드 이벤트 (2018-04-23 회원 가입자 대상)
	 */
/*
	@RequestMapping("/event/entry/exhibition201804")
	public String exhibition201804(ExhibitionVO vo, SearchVO schVO, HttpServletRequest request , HttpServletResponse response , Model model) throws Exception{
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMddHHmmss");
		String dayTimeStr = dayTime.format(new Date(time));
		
		if(dayTimeStr.compareTo("20180423120000") < 0 || dayTimeStr.compareTo("20180423235959") > 0){
			MethodUtil.alertMsgUrl(request, response, "유효하지 않은 이벤트입니다.", "/");
		}else{
			model.addAttribute("schVO", schVO);
		}
		return PathUtil.getCtx()+"/event/entry/exhibition201804";
	}
*/
	
	/**
	 * @Method : event100day
	 * @Date		: 2018. 5. 31.
	 * @Author	:  임  재  형 
	 * @Description	:	6월 100일 이벤트
	 */
	@RequestMapping("/event/entry/event100day")
	public String event100day(EventVO vo, SearchVO schVO, HttpServletRequest request, HttpServletResponse response, Model model)throws Exception{
		vo.setEventIdx(62);
		SqlMap detail = eventService.getEventDetail(vo); 
		
		if(detail==null){
			MethodUtil.alertMsgUrl(request, response, "종료 된 이벤트 입니다.", "/event/event/eventList.do");
		}else{
			List<SqlMap> ingEventList = eventService.getIngEventList(vo); // 진행 중 이벤트 리스트
			
			List<SqlMap> replyList = entryService.get100dayReplyList(vo, schVO); // 100일 이벤트 댓글 리스트
			int totalCount = entryService.get100dayReplyListCnt(vo, schVO); // 100일 이벤트 댓글 리스트 개수
			
			for(int i=0; i<replyList.size(); i++){
				replyList.get(i).put("replyContents", MethodUtil.repl(replyList.get(i).get("replyContents").toString()));
			}
			
			Page page = new Page(); 
			page.pagingInfo(schVO, totalCount);
			
			model.addAttribute("ingEventList", ingEventList);
			model.addAttribute("totalCount", totalCount);
			model.addAttribute("replyList", replyList);
			model.addAttribute("detail", detail);
			model.addAttribute("page", page);
			model.addAttribute("schVO", schVO);
		}
		
		model.addAttribute("tabType", "1");
		model.addAttribute("VO", vo);
		
		return PathUtil.getCtx()+"/event/entry/event100day";
	}
	
	/**
	 * @Method : event100daySaveAjax
	 * @Date		: 2018. 5. 31.
	 * @Author	:  임  재  형 
	 * @Description	:	커플링 신청
	 */
	@RequestMapping("/ajax/event/entry/day100ringSaveAjax")
	public @ResponseBody int day100ring(EventVO vo, HttpServletRequest request , HttpServletResponse response)throws Exception{
		int flag = 0;
		
		
		// 로그인 체크
		if (UserInfo.getUserInfo().getMemberIdx() == null) {
			flag = -100;
		}else{
			vo.setRegIdx(UserInfo.getUserInfo().getMemberIdx());
			
			try {
				flag = entryService.day100ringSave(vo); 	
			} catch (Exception e){
				response.sendError(1000);
			}
		}
		
		return flag;
	}
	
	/**
	 * @Method : day100replySaveAjax
	 * @Date		: 2018. 5. 31.
	 * @Author	:  임  재  형 
	 * @Description	:	100일 이벤트 댓글 저장
	 */
	@RequestMapping("/ajax/event/entry/day100replySaveAjax")
	public @ResponseBody int day100replySaveAjax(EventVO vo, HttpServletRequest request , HttpServletResponse response)throws Exception{
		int flag = 0;
		// 로그인 체크
		if (UserInfo.getUserInfo().getMemberIdx() == null) {
			flag = -100;
		}else{
			vo.setRegIdx(UserInfo.getUserInfo().getMemberIdx());
			
			try {
				flag = eventService.eventReplySave(vo); 	
			} catch (Exception e){
				response.sendError(1000);
			}
		}
		
		return flag;
	}
	
	/**
	 * @Method : renewEvent
	 * @Date		: 2018. 9. 6.
	 * @Author	:  임  재  형 
	 * @Description	:	2018 리뉴얼 댓글 이벤트
	 */
	@RequestMapping("/event/entry/renewEvent")
	public String renewEvent(EventVO vo, SearchVO schVO, HttpServletRequest request, HttpServletResponse response, Model model)throws Exception{
		vo.setEventIdx(87);
		SqlMap detail = eventService.getEventDetail(vo);
		
		if(detail==null){
			MethodUtil.alertMsgUrl(request, response, "종료 된 이벤트 입니다.", "/event/event/eventList.do");
		}else{
			List<SqlMap> ingEventList = eventService.getIngEventList(vo); // 진행 중 이벤트 리스트
			
			List<SqlMap> replyList = entryService.get100dayReplyList(vo, schVO); // 리뉴얼 이벤트 댓글 리스트
			int totalCount = entryService.get100dayReplyListCnt(vo, schVO); // 리뉴얼 이벤트 댓글 리스트 개수
			
			for(int i=0; i<replyList.size(); i++){
				replyList.get(i).put("replyContents", MethodUtil.repl(replyList.get(i).get("replyContents").toString()));
			}
			
			Page page = new Page(); 
			page.pagingInfo(schVO, totalCount);
			
			model.addAttribute("ingEventList", ingEventList);
			model.addAttribute("totalCount", totalCount);
			model.addAttribute("replyList", replyList);
			model.addAttribute("detail", detail);
			model.addAttribute("page", page);
			model.addAttribute("schVO", schVO);
		}
		
		model.addAttribute("tabType", "1");
		model.addAttribute("VO", vo);
		
		return PathUtil.getCtx()+"/event/entry/renewEvent";
	}
	
	/**
	 * @Method : renewReplySaveAjax
	 * @Date		: 2018. 9. 6.
	 * @Author	:  임  재  형 
	 * @Description	:	2018 리뉴얼 이벤트 댓글 저장
	 */
	@RequestMapping("/ajax/event/entry/renewReplySaveAjax")
	public @ResponseBody int renewReplySaveAjax(EventVO vo, HttpServletRequest request , HttpServletResponse response)throws Exception{
		int flag = 0;
		// 로그인 체크
		if (UserInfo.getUserInfo().getMemberIdx() == null) {
			flag = -100;
		}else{
			vo.setRegIdx(UserInfo.getUserInfo().getMemberIdx());
			
			try {
				flag = entryService.renewReplySave(vo); // 2018 리뉴얼 이벤트 댓글 저장
			} catch (Exception e){
				response.sendError(1000);
			}
		}
		
		return flag;
	}


	/**
	 * @Method : renewQuizEvent
	 * @Date		: 2018. 9. 6.
	 * @Author	:  강 병 철
	 * @Description	:	2018 리뉴얼 퀴즈 이벤트
	 */
	@RequestMapping("/event/entry/renewQuizEvent")
	public String renewQuizEvent(EventVO vo, SearchVO schVO, HttpServletRequest request, HttpServletResponse response, Model model)throws Exception{
		vo.setEventIdx(88);
		SqlMap detail = eventService.getEventDetail(vo);
		
		if(detail==null){
			MethodUtil.alertMsgUrl(request, response, "종료 된 이벤트 입니다.", "/event/event/eventList.do");
		}else{
			List<SqlMap> ingEventList = eventService.getIngEventList(vo); // 진행 중 이벤트 리스트
			
			//참가한 퀴즈 목록 구하기
			if (UserInfo.getUserInfo().getMemberIdx() != null) {
				int memberIdx = UserInfo.getUserInfo().getMemberIdx();
				List<SqlMap> quizList = entryService.renewQuizList(memberIdx, vo.getEventIdx());
				if (quizList != null) {
					for (SqlMap quiz :  quizList) {
						model.addAttribute("joinQuiz"+quiz.get("quizNo").toString(),  quiz.get("quizNo").toString());
					}
				}
			}
			
			model.addAttribute("ingEventList", ingEventList);
			model.addAttribute("detail", detail);
			model.addAttribute("schVO", schVO);
			
		}
		
		model.addAttribute("tabType", "1");
		model.addAttribute("VO", vo);
		
		return PathUtil.getCtx()+"/event/entry/renewQuizEvent";
	}

	

	/**
	 * @Method : renewQuizAnswerAjax
	 * @Date		: 2018. 9. 6.
	 * @Author	:  임  재  형 
	 * @Description	:	2018 리뉴얼 이벤트 댓글 저장
	 */
	@RequestMapping("/ajax/event/entry/renewQuizAnswerAjax")
	public @ResponseBody int renewQuizAnswerAjax(String quizNo, String answerNo, HttpServletRequest request , HttpServletResponse response)throws Exception{

		// chk : -100:로그인체크, -200 : 종료된이벤트,  -300 이미 참여한 퀴즈, 100 : 정답, 200:오답
		int chk = 0;
		
		// 로그인 체크
		if (UserInfo.getUserInfo().getMemberIdx() == null) {
			chk = -100;
		}else{
			EventVO vo = new EventVO();
			vo.setEventIdx(88);
			SqlMap detail = eventService.getEventDetail(vo);
			
			if(detail==null){
				 chk = -200; //종료된 이벤트
			}
			else
			{
				int memberIdx = UserInfo.getUserInfo().getMemberIdx();
				try {
					// chk : -300 이미 참여한 퀴즈, 100 : 정답, 200:오답
					int cnt = entryService.renewQuizCheck(memberIdx, quizNo, answerNo, 88);
					
					if (cnt > 0) {
						chk = -300; //이미참여한 퀴즈
					}
					else {
						if (quizNo.equals("1")) {
							if (answerNo.equals("2")) {
								chk = 100; //정답
							} else {
								chk = 200; //오답
							}
						}
						else if (quizNo.equals("2")) {
							if (answerNo.equals("4")) {
								chk = 100; //정답
							} else {
								chk = 200; //오답
							}
						}
						else if (quizNo.equals("3")) {
							if (answerNo.equals("3")) {
								chk = 100; //정답
							} else {
								chk = 200; //오답
							}
						}
						else if (quizNo.equals("4")) {
							if (answerNo.equals("4")) {
								chk = 100; //정답
							} else {
								chk = 200; //오답
							}
						}
						else if (quizNo.equals("5")) {
							if (answerNo.equals("1")) {
								chk = 100; //정답
							} else {
								chk = 200; //오답
							}
						}
						else if (quizNo.equals("6")) {
							if (answerNo.equals("3")) {
								chk = 100; //정답
							} else {
								chk = 200; //오답
							}
						}
					}
					
					//정답이면 저장
					if (chk == 100) {
						entryService.renewQuizSave(memberIdx, quizNo, answerNo, 88);
					}
					
				} catch (Exception e){
					response.sendError(1000);
				}
			}
		}
		
		return chk;
	}
	
	/**
	 * @Method : creatorEvent
	 * @Date		: 2019. 6. 17.
	 * @Author	:  임  재  형 
	 * @Description	:	크리에이터 이벤트
	 */
	@RequestMapping("/event/entry/creatorEvent")
	public String creatorEvent(EventVO vo, CreatorVO cvo, HttpServletRequest request, HttpServletResponse response, Model model)throws Exception{
		vo.setEventIdx(139);
		SqlMap detail = eventService.getEventDetail(vo);
		
		if(detail==null){
			MethodUtil.alertMsgUrl(request, response, "종료 된 이벤트 입니다.", "/event/event/eventList.do");
		}else{
			model.addAttribute("detail", detail);
		}
		
		model.addAttribute("tabType", "1");
		model.addAttribute("VO", vo);
		
		return PathUtil.getCtx()+"/event/entry/creatorEvent";
	}
	
	/**
	 * @Method : creatorSave
	 * @Date: 2019. 6. 17.
	 * @Author :  임  재  형
	 * @Description	:	크리에이터 제출
	 * @HISTORY :
	 *
	 */
	@RequestMapping("/event/entry/creatorSave")
	public void creatorSave(CreatorVO vo, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		int flag = entryService.creatorSave(vo);
		
		if(flag > 0){
			MethodUtil.alertMsgUrl(request, response, "정상적으로 등록되었습니다", "/event/entry/creatorEvent.do?eventIdx=139");
		}else{
			MethodUtil.alertMsgUrl(request, response, "등록에 실패했습니다.", "/event/entry/creatorEvent.do?eventIdx=139");
		}
	}
	
	/**
	 * @Method : signupEvent
	 * @Date		: 2019. 10. 30.
	 * @Author	:  임  재  형
	 * @Description	:	회원가입 이벤트
	 */
	@RequestMapping("/event/entry/signupEvent")
	public String signupEvent(HttpServletRequest request , HttpServletResponse response , Model model) throws Exception{
		// 상품 정보 (갸스비 스타일&케어오일 내츄럴 75ml 정품)
		EventVO evo = new EventVO();
		evo.setEventIdx(162);  //네이버이벤트
		SqlMap event = eventService.getEventDetail(evo); // 이벤트 상세
		
		if(event==null){
			MethodUtil.alertMsgUrl(request, response, "이벤트 기간이 아닙니다.", "/main.do");
			return null;
		}else{
			boolean islogin =  isLogin(request);
			model.addAttribute("islogin", islogin);
			
			if (islogin) {
				MemberVO memVO = new MemberVO();
				memVO.setMemberId(UserInfo.getUserInfo().getMemberId());
				SqlMap member = memberService.getMemberDetail(memVO);
				String regdt = member.get("regDt").toString();
				System.out.println(regdt);
				
				Date now = new Date();
				
				SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
				String nowdt = dt.format(now);
				System.out.println(nowdt);
				if (nowdt.equals(regdt)) {
					model.addAttribute("nowjoin", true);
				} else {
					model.addAttribute("nowjoin", false);
				}
			}
			model.addAttribute("islogin", islogin);
			
			SqlMap detail = entryService.getGoodsDetail("6921439806060SA");
			model.addAttribute("detail", detail);
			
		}

		return PathUtil.getCtx()+"/event/entry/signupEvent";
		
	}

	/**
	 * @Method : signupEvent
	 * @Date		: 2019. 10. 30.
	 * @Author	:  임  재  형
	 * @Description	:	회원가입 이벤트
	 */
	@RequestMapping("/event/entry/blossomEvent")
	public String blossomEvent(HttpServletRequest request , HttpServletResponse response , Model model) throws Exception{
		// 상품 정보 (갸스비 스타일&케어오일 내츄럴 75ml 정품)
		EventVO evo = new EventVO();
		evo.setEventIdx(165);  //
		SqlMap event = eventService.getEventDetail(evo); // 이벤트 상세
		
		if(event==null){
			MethodUtil.alertMsgUrl(request, response, "이벤트 기간이 아닙니다.", "/main.do");
			return null;
		}else{
			boolean islogin =  isLogin(request);
			model.addAttribute("islogin", islogin);
			
			if (islogin) {
				MemberVO memVO = new MemberVO();
				memVO.setMemberId(UserInfo.getUserInfo().getMemberId());
				SqlMap member = memberService.getMemberDetail(memVO);
				String regdt = member.get("regDt").toString();
				System.out.println(regdt);
				
				Date now = new Date();
				
				SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
				String nowdt = dt.format(now);
				System.out.println(nowdt);
				if (nowdt.equals(regdt)) {
					model.addAttribute("nowjoin", true);
				} else {
					model.addAttribute("nowjoin", false);
				}
			}
			model.addAttribute("islogin", islogin);
					
		}

		model.addAttribute("tabType", "1");
		return PathUtil.getCtx()+"/event/entry/blossomEvent";
		
	}


	/**
	 * @Method : entryNoCheck2020Ajax
	 * @Date		: 2020. 02. 17.
	 * @Author	:  강병철
	 * @Description	:	벚꽃이벤트응모 번호 체크
	 */
	@RequestMapping("/ajax/event/entry/entryNoCheck2020Ajax") 
	public @ResponseBody int entryNoCheck2020Ajax(EntryVO vo, HttpServletRequest request, HttpServletResponse response)throws Exception{
		int flag = entryService.entryNo2020Check(vo);
		return flag;
	}
	
	/**
	 * @Method : entryEventSave
	 * @Date		: 2017. 8. 22.
	 * @Author	:  유  준  철 
	 * @Description	:	응모 저장
	 */
	@RequestMapping("/event/entry/entryEvent2020Save") 
	public void entryEvent2020Save(EntryVO vo, HttpServletRequest request, HttpServletResponse response, Model model)throws Exception{
		String currentDate = DateUtil.getCurrentDate("yyyy-MM-dd");		
		SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
        Date nowDate = null;
        Date endDate = null;
        
        try {
        	nowDate = format.parse(currentDate);								// 현재 날짜
        	endDate = format.parse( "2020-04-16" );							// 응모하기 종료 날짜
        } catch (ParseException e) {
            e.printStackTrace();
        }
         
        int compare = nowDate.compareTo(endDate);
        
        if(compare > 0){
        	MethodUtil.alertMsgUrl(request, response, "응모하기 기간이 종료 되었습니다.", "/event/entry/blossomEvent.do");
        }else{
        	int flag = entryService.entryNo2020Check(vo);
        	if( flag > 0) {
        		entryService.blossomEventSave(vo);
        		MethodUtil.alertMsgUrl(request, response, "응모하기가 완료 되었습니다.", "/event/entry/blossomEvent.do");
        	} else {
        		MethodUtil.alertMsgUrl(request, response, "응모번호가 유효하지 않거나 이미 응모하기가 되었습니다.", "/event/entry/blossomEvent.do");
        	}
        }
	}
	
}
