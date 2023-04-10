package com.gxenSoft.mall.mypage.token.web;

import com.gxenSoft.mall.common.vo.JsonResultVO;
import com.gxenSoft.mall.mypage.point.service.PointService;
import com.gxenSoft.mall.mypage.token.service.TokenService;
import com.gxenSoft.mall.mypage.token.vo.TokenRequest;
import com.gxenSoft.method.MethodUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class TokenRestController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PointService pointService;

    @PostMapping("/mypage/token/tokenWriteOk")
    public JsonResultVO tokenWriteOk(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,
            @RequestBody TokenRequest tokenRequest
    ) throws Exception {
        if (session.getAttribute("SS_MEMBER_FLAG") == null) {
            MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
        }

        if (session.getAttribute("SS_MEMBER_FLAG").equals("N")) {
            MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
        }

        JsonResultVO resultMap = new JsonResultVO();
        resultMap.setResult(true);

        try {
            int totalPoint = pointService.getTotalPoint();
            tokenService.tokenWriteOk(tokenRequest, totalPoint);
        } catch (Exception e) {
            e.printStackTrace();

            resultMap.setResult(false);
            resultMap.setMsg(e.getMessage());
        }

        return resultMap;
    }

    @GetMapping("/mypage/token/tokenCancel/{idx}")
    public JsonResultVO tokenCencel(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,
            @PathVariable("idx") Integer idx
    ) throws Exception {
        if (session.getAttribute("SS_MEMBER_FLAG") == null) {
            MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
        }

        if (session.getAttribute("SS_MEMBER_FLAG").equals("N")) {
            MethodUtil.alertMsgUrl(request, response, "로그인이 필요합니다.", "/login/loginPage.do");
        }

        JsonResultVO resultMap = new JsonResultVO();
        resultMap.setResult(true);

        try {

            tokenService.cancel(idx, (Integer) session.getAttribute("SS_MEMBER_IDX"));

        } catch (Exception e) {
            e.printStackTrace();

            resultMap.setResult(false);
            resultMap.setMsg(e.getMessage());
        }

        return resultMap;
    }
}
