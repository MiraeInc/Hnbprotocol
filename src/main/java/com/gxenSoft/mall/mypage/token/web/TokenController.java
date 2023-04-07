package com.gxenSoft.mall.mypage.token.web;

import com.gxenSoft.mall.common.web.CommonMethod;
import com.gxenSoft.mall.mypage.point.service.PointService;
import com.gxenSoft.mall.mypage.token.service.TokenService;
import com.gxenSoft.method.MethodUtil;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.Page;
import com.gxenSoft.util.page.SearchVO;
import com.gxenSoft.util.pathUtil.PathUtil;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class TokenController extends CommonMethod {

    static final Logger logger = LoggerFactory.getLogger(TokenController.class);

    @Autowired
    private PointService pointService;

    @Autowired
    private TokenService tokenService;

    @RequestMapping("/mypage/token/tokenList")
    public String tokenList(
            SearchVO schVO,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model,
            HttpSession session
    ) throws Exception {
        if (session.getAttribute("SS_MEMBER_FLAG") == null) {
            MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
        }

        if (session.getAttribute("SS_MEMBER_FLAG").equals("N")) {
            MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
        }

        // 조회일자 최초 3개월 기본 세팅
        if (TextUtils.isEmpty(schVO.getSchType())) {
            // 3달 전
            Calendar mon = Calendar.getInstance();
            mon.add(Calendar.MONTH, -3);
            schVO.setSchStartDt(new SimpleDateFormat("yyyy-MM-dd").format(mon.getTime()));

            // 오늘
            Date today = new Date();
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            schVO.setSchEndDt(date.format(today));
        }

        int totalCount = tokenService.getTokenListCnt(schVO);
        List<SqlMap> tokenList = tokenService.getTokenList(schVO);

        int sumPoint = pointService.getSumPoint(schVO, totalCount); // 페이지 적용 포인트 합
        int totalPoint = pointService.getTotalPoint(); // 보유 포인트

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, +1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String nextMonth = format.format(cal.getTime()) + "-01";

        SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM");
        String nextMonthTxt = format2.format(cal.getTime()) + "월 01일";

        SqlMap spPointDeduct = pointService.getSpPointDeduct(nextMonth); // 포인트 조회 프로시져

        Page page = new Page();
        page.pagingInfo(schVO, totalCount);

        model.addAttribute("totalCount", totalCount);
        model.addAttribute("tokenList", tokenList);
        model.addAttribute("sumPoint", sumPoint);
        model.addAttribute("totalPoint", totalPoint);
        model.addAttribute("spPointDeduct", spPointDeduct);
        model.addAttribute("nextMonthTxt", nextMonthTxt);
        model.addAttribute("page", page);
        model.addAttribute("schVO", schVO);

        return PathUtil.getCtx() + "/mypage/token/tokenList";
    }

    @RequestMapping("/mypage/token/tokenWrite")
    public String tokenWrite(
            SearchVO schVO,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model,
            HttpSession session
    ) throws Exception {
        if (session.getAttribute("SS_MEMBER_FLAG") == null) {
            MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
        }

        if (session.getAttribute("SS_MEMBER_FLAG").equals("N")) {
            MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
        }

        // 조회일자 최초 3개월 기본 세팅
        if (TextUtils.isEmpty(schVO.getSchType())) {
            // 3달 전
            Calendar mon = Calendar.getInstance();
            mon.add(Calendar.MONTH, -3);
            schVO.setSchStartDt(new SimpleDateFormat("yyyy-MM-dd").format(mon.getTime()));

            // 오늘
            Date today = new Date();
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            schVO.setSchEndDt(date.format(today));
        }



        int totalCount = 0;
        List<SqlMap> tokenList = new ArrayList<>();




        int sumPoint = pointService.getSumPoint(schVO, totalCount); // 페이지 적용 포인트 합
        int totalPoint = pointService.getTotalPoint(); // 보유 포인트

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, +1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String nextMonth = format.format(cal.getTime()) + "-01";

        SimpleDateFormat format2 = new SimpleDateFormat("yyyy년 MM");
        String nextMonthTxt = format2.format(cal.getTime()) + "월 01일";

        SqlMap spPointDeduct = pointService.getSpPointDeduct(nextMonth); // 포인트 조회 프로시져

        Page page = new Page();
        page.pagingInfo(schVO, totalCount);

        model.addAttribute("totalCount", totalCount);
        model.addAttribute("tokenList", tokenList);
        model.addAttribute("sumPoint", sumPoint);
        model.addAttribute("totalPoint", totalPoint);
        model.addAttribute("spPointDeduct", spPointDeduct);
        model.addAttribute("nextMonthTxt", nextMonthTxt);
        model.addAttribute("page", page);
        model.addAttribute("schVO", schVO);

        return PathUtil.getCtx() + "/mypage/token/tokenWrite";
    }

}
