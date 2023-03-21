package com.gxenSoft.mall.lgdacom.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gxenSoft.mall.common.web.CommonMethod;
import com.gxenSoft.mall.lgdacom.vo.PaycoSettingVO;
import com.gxenSoft.mall.order.service.OrderService;
import com.gxenSoft.util.pathUtil.PathUtil;


/**
 *************************************
 * PROJECT   : PaycoController
 * PROGRAM ID  : LgdacomController
 * PACKAGE NM : com.gxenSoft.mall.lgdacom.web
 * AUTHOR	 : 강 병 철
 * CREATED DATE  : 2017. 7. 4. 
 * HISTORY :   
 *
 *************************************
 */	
@Controller
public class PaycoLoginController extends CommonMethod {

	@Autowired
	private OrderService orderService;

	/**
    * @Method : IsMobile
    * @Date: 2017. 7. 4.
    * @Author :   강 병 철
    * @Description	:	모바일여부 체크
   */
	public String IsMobile(HttpServletRequest request) {
		String isMobile;
		String userAgent = request.getHeader("user-agent");
		boolean mobile1 = userAgent.matches(".*(iPhone|iPod|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mni|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson).*");
		boolean mobile2 = userAgent.matches(".*(LG|SAMSUNG|Samsung).*");
		if(mobile1||mobile2){
			isMobile = "true";
		}else{
			isMobile = "false";
		}
		return isMobile;
	}
	
	/**
    * @Method : index
    * @Date: 2017. 7. 4.
    * @Author :   강 병 철
    * @Description	:	payco 테스트
   */
	@RequestMapping(value="/paycoLogin/index") 
	public String index( HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) throws Exception{
		PaycoSettingVO setting = new PaycoSettingVO();
		model.addAttribute("IsMobile",IsMobile(request));
		model.addAttribute("settingVo", setting);
		return PathUtil.getCtx()+"/paycoLogin/index";
	}
	
}
