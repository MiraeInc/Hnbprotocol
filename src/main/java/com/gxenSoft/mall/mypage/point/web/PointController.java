package com.gxenSoft.mall.mypage.point.web;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestMapping;

import com.gxenSoft.mall.common.web.CommonMethod;
import com.gxenSoft.mall.mypage.point.service.PointService;
import com.gxenSoft.mall.mypage.point.vo.PointVO;
import com.gxenSoft.method.MethodUtil;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.Page;
import com.gxenSoft.util.page.SearchVO;
import com.gxenSoft.util.pathUtil.PathUtil;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : PointController
    * PACKAGE NM : com.gxenSoft.mall.mypage.point.web
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 8. 4. 
    * HISTORY :   포인트 / 쿠폰
    *
    *************************************
    */
@Controller
public class PointController extends CommonMethod{
	
	static final Logger logger = LoggerFactory.getLogger(PointController.class);
	
	@Autowired
	private PointService pointService;
	

	/**
	    * @Method : pointList
	    * @Date: 2017. 8. 4.
	    * @Author : 임  재  형
	    * @Description	:	포인트
	   */
	@RequestMapping("/mypage/point/pointList") 
	public String pointList(PointVO vo, SearchVO schVO, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		if (session.getAttribute("SS_MEMBER_FLAG")  == null) {
			MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
		}
		
		if (session.getAttribute("SS_MEMBER_FLAG").equals("N")) {
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
		
		int totalCount = pointService.getPointListCnt(schVO); // 포인트 리스트 총 개수
		List<SqlMap> pointList = pointService.getPointList(schVO); // 포인트 리스트
		int sumPoint = pointService.getSumPoint(schVO, totalCount); // 페이지 적용 포인트 합
		int totalPoint = pointService.getTotalPoint(); // 보유 포인트
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, +1);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String nextMonth = format.format(cal.getTime())+"-01";
		
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM");
		String nextMonthTxt = format2.format(cal.getTime())+"월 01일";
		
		SqlMap spPointDeduct = pointService.getSpPointDeduct(nextMonth); // 포인트 조회 프로시져
		
		Page page = new Page(); 
		page.pagingInfo(schVO, totalCount);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pointList", pointList);
		model.addAttribute("sumPoint", sumPoint);
		model.addAttribute("totalPoint", totalPoint);
		model.addAttribute("spPointDeduct", spPointDeduct);
		model.addAttribute("nextMonthTxt", nextMonthTxt);
		model.addAttribute("page", page);
		model.addAttribute("schVO", schVO);
		
		return PathUtil.getCtx()+"/mypage/point/pointList";
	}

	/**
	    * @Method : couponList
	    * @Date: 2017. 8. 4.
	    * @Author : 임  재  형
	    * @Description	:	쿠폰목록
	   */
	@RequestMapping("/mypage/point/couponList") 
	public String couponList(SearchVO schVO, HttpServletRequest request , HttpServletResponse response , Model model, HttpSession session)throws Exception{
		//1: 사용가능한 쿠폰, 2:7일이내 만료 쿠폰, 3:사용 만료된 쿠폰
		if (schVO.getSchType().isEmpty() || !(schVO.getSchType().equals("2") || schVO.getSchType().equals("3")) ) {
			schVO.setSchType("1");
		}
		if (schVO.getPageNo() == 0) {
			schVO.setPageNo(1);
		}
		schVO.setPageSize(9);
		schVO.setPageBlock(9);
		
		Page page = new Page(); 
		int type1Count = pointService.getUsableCouponCnt(schVO);
		int type2Count = pointService.getSevenExpireCouponCnt(schVO);
		int type3Count = pointService.getExpireCouponCnt(schVO);
		List<SqlMap> couponList = new ArrayList<SqlMap>(); 
		
		if (schVO.getSchType().equals("2")) {
			page.pagingInfo(schVO, type2Count);
			couponList = pointService.getSevenExpireCouponList(schVO);
		} else if (schVO.getSchType().equals("3")) {
			page.pagingInfo(schVO, type3Count);
			couponList = pointService.getExpireCouponList(schVO);
		} else  {
			page.pagingInfo(schVO, type1Count);
			couponList = pointService.getUsableCouponList(schVO);
		}

		model.addAttribute("type1Count", type1Count);
		model.addAttribute("type2Count", type2Count);
		model.addAttribute("type3Count", type3Count);
		model.addAttribute("couponList", couponList);
		model.addAttribute("page", page);
		model.addAttribute("schVO", schVO);
		
		return PathUtil.getCtx()+"/mypage/point/couponList";
	}
}
