package com.gxenSoft.mall.mypage.token.web;

import com.gxenSoft.mall.common.vo.JsonResultVO;
import com.gxenSoft.mall.mypage.token.service.TokenService;
import com.gxenSoft.mall.mypage.token.vo.TokenRequest;
import com.gxenSoft.method.MethodUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class TokenRestController {

    @Autowired
    private TokenService tokenService;

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
            tokenService.tokenWriteOk(tokenRequest);
        } catch (Exception e) {
            e.printStackTrace();

            resultMap.setResult(false);
            resultMap.setMsg("현금영수증 발급중 에러가 발생했습니다! (200)");
        }

        System.out.println("tokenRequest = " + tokenRequest);

        return resultMap;
    }
}
