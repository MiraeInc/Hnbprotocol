package com.gxenSoft.mall.nhn.web;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gxenSoft.mall.common.web.CommonMethod;
import com.gxenSoft.mall.nhn.service.NpayService;
import com.nhncorp.platform.checkout.base.ProductOrderChangeType;


@Controller
public class NhnController extends CommonMethod{
	@Autowired
	private NpayService npayService;
	
	
	/**
	 * @Method : GetChangedProductOrderList  
	 * @Date		: 2017. 8. 07.
	 * @Author	:  강 병 철
	 * @Description	: 변경 상품 주문 내역을 조회
	 *  ①  입금대기 ②  결제완료 ③  발송처리 ④  취소요청 ⑤  반품요청 ⑥  교환요청, ⑦  교환재배송준비 ⑧  구매확정보류요청 ⑨  취소완료/반품완료/교환완료 ⑩  구매확정
		type = PAY_WAITING 입금 대기 ,PAYED 결제 완료,	DISPATCHED 발송 처리,	CANCEL_REQUESTED 취소 요청,	RETURN_REQUESTED 반품 요청,	EXCHANGE_REQUESTED 교환 요청,	EXCHANGE_REDELIVERY_READY 교환 재배송 준비, 	HOLDBACK_REQUESTED 구매 확정 보류 요청,	CANCELED 취소, 	RETURNED 반품, 	EXCHANGED 교환, 	PURCHASE_DECIDED 구매 확정
	 */
	@RequestMapping("/nhn/GetChangedProductOrderList")
	public void GetChangedProductOrderList(String TYPE, String date, HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session)throws Exception{
		
		ProductOrderChangeType OrderStatus = null;
		TYPE = TYPE.toUpperCase();
		switch (TYPE) {
		case "PAY_WAITING":
			OrderStatus = ProductOrderChangeType.PAY_WAITING;
			break;
		case "PAYED":
			OrderStatus = ProductOrderChangeType.PAYED;
			break;
		case "DISPATCHED": //발송처리
			OrderStatus = ProductOrderChangeType.DISPATCHED;
			break;
		case "CANCEL_REQUESTED":
			OrderStatus = ProductOrderChangeType.CANCEL_REQUESTED;
			break;
		case "RETURN_REQUESTED":
			OrderStatus = ProductOrderChangeType.RETURN_REQUESTED;
			break;
		case "EXCHANGE_REQUESTED":
			OrderStatus = ProductOrderChangeType.EXCHANGE_REQUESTED;
			break;
		case "HOLDBACK_REQUESTED":
			OrderStatus = ProductOrderChangeType.HOLDBACK_REQUESTED;
			break;	
		case "CANCELED":
			OrderStatus = ProductOrderChangeType.CANCELED;
			break;	
		case "RETURNED":
			OrderStatus = ProductOrderChangeType.RETURNED;
			break;	
		case "EXCHANGED":
			OrderStatus = ProductOrderChangeType.EXCHANGED;
			break;				
		case "PURCHASE_DECIDED":
			OrderStatus = ProductOrderChangeType.PURCHASE_DECIDED;  //구매 확정
			break;				
		}
		
		String result = "";
		if (OrderStatus != null) {
			result = npayService.GetChangedProductOrderList (OrderStatus, date);
			
		}
		else {
			result = "FALSE";
		}
		
		PrintWriter printWriter = null;
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        printWriter = response.getWriter();
        printWriter.println("RESULT="+result);
        printWriter.flush();
        printWriter.close();
		
	}
}