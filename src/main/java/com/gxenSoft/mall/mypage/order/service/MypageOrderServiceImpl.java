package com.gxenSoft.mall.mypage.order.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.gxenSoft.encrypt.Crypto;
import com.gxenSoft.fileUtil.FileUtil;
import com.gxenSoft.fileUtil.FileVO;
import com.gxenSoft.mall.common.vo.JsonResultVO;
import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.lgdacom.vo.PaycoApprovalVO;
import com.gxenSoft.mall.lgdacom.vo.PaycoReturnVO;
import com.gxenSoft.mall.lgdacom.vo.PaycoSettingVO;
import com.gxenSoft.mall.lgdacom.vo.XPayResultVO;
import com.gxenSoft.mall.member.dao.MemberDAO;
import com.gxenSoft.mall.mypage.order.dao.MypageOrderDAO;
import com.gxenSoft.mall.mypage.order.vo.ClaimVO;
import com.gxenSoft.mall.mypage.order.vo.OrderDetailVO;
import com.gxenSoft.mall.order.dao.OrderDAO;
import com.gxenSoft.mall.order.service.OrderService;
import com.gxenSoft.mall.order.vo.OrderVO;
import com.gxenSoft.message.SpringMessage;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;
import com.gxenSoft.util.pathUtil.PathUtil;
import com.gxenSoft.util.pg.payco.util.PaycoUtil;
import com.gxenSoft.util.pg.smilepay.util.SmilepayUtil;
import com.gxenSoft.util.pg.smilepay.vo.SmilepayCancelVO;
import com.kcp.J_PP_CLI_N;

import lgdacom.XPayClient.XPayClient;

/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : MypageOrderServiceImpl
    * PACKAGE NM : com.gxenSoft.mall.mypage.order.service
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 8. 4. 
    * HISTORY :   
    *
    *************************************
    */
@Service("mypageOrderService")
public class MypageOrderServiceImpl implements MypageOrderService {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private MypageOrderDAO mypageOrderDAO;

	@Autowired
	private OrderDAO orderDAO;
	
	@Autowired
	private MemberDAO memberDAO;
	
	   /**
	    * @Method : nvl
	    * @Date: 2017. 8. 26.
	    * @Author :  서 정 길
	    * @Description	:	null 대신 ""로 반환
	   */
	public String nvl(Object str){
		if(str == null){
			return "";
		}else{
			return String.valueOf(str);
		}
	}	

	   /**
	    * @Method : getMemberCntInfo
	    * @Date: 2017. 8. 6.
	    * @Author :  서 정 길
	    * @Description	:	쿠폰 갯수, 포인트, 상품 후기 건수, 이 달의 샘플 신청 건수
	   */
	@Override
	public SqlMap getMemberCntInfo(OrderVO vo) throws Exception {
		SqlMap cnt = new SqlMap();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			map.put("DEVICE", PathUtil.getDeviceNm());			// 디바이스 (P : PC, M : MOBILE, A : APP)
			map.put("MEMBERIDX", vo.getMemberIdx());			// 회원 일련번호
		
			cnt = mypageOrderDAO.getMemberCntInfo(map); 	// 쿠폰 갯수, 포인트, 상품 후기 건수, 이 달의 샘플 신청 건수
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}

		return cnt;
	}
	
	   /**
	    * @Method : getOrderStatusCnt
	    * @Date: 2017. 8. 4.
	    * @Author :  서 정 길
	    * @Description	:	마이페이지 메인 - 주문 상태별 건수
	   */
	@Override
	public SqlMap getOrderStatusCnt(OrderVO vo) throws Exception {
		SqlMap cnt = new SqlMap();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			map.put("DEVICE", PathUtil.getDeviceNm());				// 디바이스 (P : PC, M : MOBILE, A : APP)
			
			if(vo.getMemberIdx() > 0){	// 회원
				map.put("MEMBERIDX", vo.getMemberIdx());			// 회원 일련번호
			}else{								// 비회원
				map.put("ORDERCD", vo.getNomemberOrderCd());	// 비회원 주문 코드
			}
		
			cnt = mypageOrderDAO.getOrderStatusCnt(map); // 주문 상태별 건수
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}

		return cnt;
	}

	   /**
	    * @Method : getOrderListFor1Month
	    * @Date: 2017. 8. 6.
	    * @Author :  서 정 길
	    * @Description	:	마이페이지 메인 - 주문 리스트(최근 1달 5건)
	   */
	@Override
	public List<SqlMap> getOrderListFor1Month(OrderVO vo) throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();

		try{
			if(vo.getMemberIdx() > 0){	// 회원
				map.put("MEMBERIDX", vo.getMemberIdx());			// 회원 일련번호
			}else{								// 비회원
				map.put("ORDERCD", vo.getNomemberOrderCd());	// 비회원 주문 코드
			}

			list = mypageOrderDAO.getOrderListFor1Month(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	   /**
	    * @Method : getOrderListFor1MonthCnt
	    * @Date: 2017. 8. 6.
	    * @Author :  서 정 길
	    * @Description	:	마이페이지 메인 - 주문 리스트(최근 1달 5건) 건수
	   */
	@Override
	public int getOrderListFor1MonthCnt(OrderVO vo) throws Exception {
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();

		try{
			if(vo.getMemberIdx() > 0){	// 회원
				map.put("MEMBERIDX", vo.getMemberIdx());			// 회원 일련번호
			}else{								// 비회원
				map.put("ORDERCD", vo.getNomemberOrderCd());	// 비회원 주문 코드
			}
			
			String deviceGubun = "";
			if(PathUtil.getDevice().equals("m")){
				deviceGubun = "M";
			}else{
				deviceGubun = "P";
			}
			map.put("DEVICEGUBUN", deviceGubun);

			cnt = mypageOrderDAO.getOrderListFor1MonthCnt(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}

	   /**
	    * @Method : getMyOrderList
	    * @Date: 2017. 8. 6.
	    * @Author :  서 정 길
	    * @Description	:	마이페이지 주문관리 주문 내역 리스트
	   */
	@Override
	public List<SqlMap> getMyOrderList(OrderVO vo, SearchVO searchVO) throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();

		try{
			String[] schOrderStatus = null;
			if(searchVO.getSchStatus() == null || searchVO.getSchStatus().isEmpty()){	// 상태가 전체면 아래 주문 상태들만 표시되게
				String totalStatus = "100,200,300,400,900";	// 전체 주문 상태 코드
				schOrderStatus = totalStatus.split(",");
			}else{
				schOrderStatus = searchVO.getSchStatus().split(",");
			}

			if(vo.getMemberIdx() > 0){	// 회원
				map.put("MEMBERIDX", vo.getMemberIdx());			// 회원 일련번호
			}else{								// 비회원
				map.put("ORDERCD", vo.getNomemberOrderCd());	// 비회원 주문 코드
			}
			if(searchVO.getSchStartDt() != null && !searchVO.getSchStartDt().isEmpty()){
				map.put("SCHSTARTDT", searchVO.getSchStartDt() + " 00:00:00");		// 검색 기간 시작일		
			}
			if(searchVO.getSchEndDt() != null && !searchVO.getSchEndDt().isEmpty()){
				map.put("SCHENDDT", searchVO.getSchEndDt() + " 23:59:59");			// 검색 기간 종료일
			}
			map.put("SCHORDERSTATUS", schOrderStatus);								// 주문 상태		
			map.put("PAGESTART", ((searchVO.getPageNo() - 1) * searchVO.getPageBlock()));					
			map.put("PAGEBLOCK", searchVO.getPageBlock());																
			map.put("PAGENO", searchVO.getPageNo());			

			list = mypageOrderDAO.getMyOrderList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	   /**
	    * @Method : getMyOrderListCnt
	    * @Date: 2017. 8. 6.
	    * @Author :  서 정 길
	    * @Description	:	마이페이지 주문관리 주문 내역 리스트 건수
	   */
	@Override
	public int getMyOrderListCnt(OrderVO vo, SearchVO searchVO) throws Exception {
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();

		try{
			String[] schOrderStatus = null;
			if(searchVO.getSchStatus() == null || searchVO.getSchStatus().isEmpty()){	// 상태가 전체면 아래 주문 상태들만 표시되게
				String totalStatus = "100,200,300,400,900";	// 전체 주문 상태 코드
				schOrderStatus = totalStatus.split(",");
			}else{
				schOrderStatus = searchVO.getSchStatus().split(",");
			}

			if(vo.getMemberIdx() > 0){	// 회원
				map.put("MEMBERIDX", vo.getMemberIdx());			// 회원 일련번호
			}else{								// 비회원
				map.put("ORDERCD", vo.getNomemberOrderCd());	// 비회원 주문 코드
			}
			if(searchVO.getSchStartDt() != null && !searchVO.getSchStartDt().isEmpty()){
				map.put("SCHSTARTDT", searchVO.getSchStartDt() + " 00:00:00");		// 검색 기간 시작일		
			}
			if(searchVO.getSchEndDt() != null && !searchVO.getSchEndDt().isEmpty()){
				map.put("SCHENDDT", searchVO.getSchEndDt() + " 23:59:59");			// 검색 기간 종료일
			}
			map.put("SCHORDERSTATUS", schOrderStatus);								// 주문 상태		

			cnt = mypageOrderDAO.getMyOrderListCnt(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}
	
	   /**
	    * @Method : getMyClaimList
	    * @Date: 2017. 8. 7.
	    * @Author :  서 정 길
	    * @Description	:	주문관리 - 취소, 반품, 교환 리스트
	    * claimType : 클레임 구분 (C : 취소, X : 교환, R : 반품)
	   */
	@Override
	public List<SqlMap> getMyClaimList(String claimType, OrderVO vo, SearchVO searchVO) throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();

		try{
			if(vo.getMemberIdx() > 0){	// 회원
				map.put("MEMBERIDX", vo.getMemberIdx());			// 회원 일련번호
			}else{								// 비회원
				map.put("ORDERCD", vo.getNomemberOrderCd());	// 비회원 주문 코드
			}
			if(searchVO.getSchStartDt() != null && !searchVO.getSchStartDt().isEmpty()){
				map.put("SCHSTARTDT", searchVO.getSchStartDt() + " 00:00:00");		// 검색 기간 시작일		
			}
			if(searchVO.getSchEndDt() != null && !searchVO.getSchEndDt().isEmpty()){
				map.put("SCHENDDT", searchVO.getSchEndDt() + " 23:59:59");			// 검색 기간 종료일
			}
			if(searchVO.getSchStatus() != null && !searchVO.getSchStatus().isEmpty()){
				map.put("SCHSTATUS", searchVO.getSchStatus());		
			}
			
			map.put("CLAIMTYPE", claimType);								// 주문 상태		
			map.put("PAGESTART", ((searchVO.getPageNo() - 1) * searchVO.getPageBlock()));					
			map.put("PAGEBLOCK", searchVO.getPageBlock());																
			map.put("PAGENO", searchVO.getPageNo());			

			list = mypageOrderDAO.getMyClaimList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	   /**
	    * @Method : getMyClaimListCnt
	    * @Date: 2017. 8. 7.
	    * @Author :  서 정 길
	    * @Description	:	주문관리 - 취소, 반품, 교환 리스트 건수
	    * claimType : 클레임 구분 (C : 취소, X : 교환, R : 반품)
	   */
	@Override
	public int getMyClaimListCnt(String claimType, OrderVO vo, SearchVO searchVO) throws Exception {
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();

		try{
			if(vo.getMemberIdx() > 0){	// 회원
				map.put("MEMBERIDX", vo.getMemberIdx());			// 회원 일련번호
			}else{								// 비회원
				map.put("ORDERCD", vo.getNomemberOrderCd());	// 비회원 주문 코드
			}
			if(searchVO.getSchStartDt() != null && !searchVO.getSchStartDt().isEmpty()){
				map.put("SCHSTARTDT", searchVO.getSchStartDt() + " 00:00:00");		// 검색 기간 시작일		
			}
			if(searchVO.getSchEndDt() != null && !searchVO.getSchEndDt().isEmpty()){
				map.put("SCHENDDT", searchVO.getSchEndDt() + " 23:59:59");			// 검색 기간 종료일
			}
			if(searchVO.getSchStatus() != null && !searchVO.getSchStatus().isEmpty()){
				map.put("SCHSTATUS", searchVO.getSchStatus());		
			}
			map.put("CLAIMTYPE", claimType);								// 주문 상태		

			cnt = mypageOrderDAO.getMyClaimListCnt(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}
	
	
	 /**
	    * @Method : getProductList
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	:	주문상품 정보
	   */
	public String getProductList(SqlMap order) throws Exception {
		String product = "";
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ORDERIDX", order.get("orderIdx"));
		List<SqlMap> list = mypageOrderDAO.getProductList(map);
		for(SqlMap p : list) {
			product += "<a href='javascript:void(0);'>";
			product += "<div class='badge-box'>";
			if("Y".equals(p.get("opoYn").toString())){ product += "<span class='badge type1'>1+1</span>"; }
			if("Y".equals(p.get("tpoYn").toString())){ product += "<span class='badge type2'>2+1</span>"; }
			if("Y".equals(p.get("newYn").toString())){ product += "<span class='badge type3'>NEW</span>"; }
			if("Y".equals(p.get("bestYn").toString())){ product += "<span class='badge type3'>BEST</span>"; }
			if("Y".equals(p.get("saleiconYn").toString())){ product += "<span class='badge type3'>SALE</span>"; }
			if("Y".equals(p.get("pointiconYn").toString())){ product += "<span class='badge type3'><i>POINT</i> X2</span>"; }
			product += "</div>";
			if(Integer.valueOf(p.get("brandIdx").toString()) == 1){ product += "<em class='brand-gatsby'>"+p.get("brandNm").toString()+"</em>"; }
			if(Integer.valueOf(p.get("brandIdx").toString()) == 3){ product += "<em class='brand-bifesta'>"+p.get("brandNm").toString()+"</em>"; }
			if(Integer.valueOf(p.get("brandIdx").toString()) == 4){ product += "<em class='brand-lucidol'>"+p.get("brandNm").toString()+"</em>"; }
			product += "<span>"+p.get("goodsNm").toString()+"</span>";
			product += "</a>";
		}
		return product;
	}
	
	
	   /**
	    * @Method : getIssueDocumentList
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	:	증명서류발급 목록
	   */
	@Override
	public List<SqlMap> getIssueDocumentList(OrderVO vo, SearchVO searchVO) throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();

		try{
			String schOrderStatus = null;
			if(searchVO.getSchStatus() == null || searchVO.getSchStatus().isEmpty()){	// 상태가 전체면 아래 주문 상태들만 표시되게
				schOrderStatus = "";
			}else{
				schOrderStatus = searchVO.getSchStatus().toString();
			}

			if(vo.getMemberIdx() > 0){	// 회원
				map.put("MEMBERIDX", vo.getMemberIdx());			// 회원 일련번호
			}else{								// 비회원
				map.put("ORDERCD", vo.getNomemberOrderCd());	// 비회원 주문 코드
			}
			if(searchVO.getSchStartDt() != null && !searchVO.getSchStartDt().isEmpty()){
				map.put("SCHSTARTDT", searchVO.getSchStartDt() + " 00:00:00");		// 검색 기간 시작일		
			}
			if(searchVO.getSchEndDt() != null && !searchVO.getSchEndDt().isEmpty()){
				map.put("SCHENDDT", searchVO.getSchEndDt() + " 23:59:59");			// 검색 기간 종료일
			}
			
			map.put("SCHORDERSTATUS", schOrderStatus);								// 주문 상태		
			map.put("PAGESTART", ((searchVO.getPageNo() - 1) * searchVO.getPageBlock()));					
			map.put("PAGEBLOCK", searchVO.getPageBlock());																
			map.put("PAGENO", searchVO.getPageNo());			

			list = mypageOrderDAO.getIssueDocumentList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}
	

	   /**
	    * @Method : getIssueDocumentCnt
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	:	증명서류발급 목록 개수
	   */
	@Override
	public int getIssueDocumentCnt(OrderVO vo, SearchVO searchVO) throws Exception {
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();

		try{
			String schOrderStatus = null;
			if(searchVO.getSchStatus() == null || searchVO.getSchStatus().isEmpty()){	// 상태가 전체면 아래 주문 상태들만 표시되게
				schOrderStatus = "";
			}else{
				schOrderStatus = searchVO.getSchStatus().toString();
			}

			if(vo.getMemberIdx() > 0){	// 회원
				map.put("MEMBERIDX", vo.getMemberIdx());			// 회원 일련번호
			}else{								// 비회원
				map.put("ORDERCD", vo.getNomemberOrderCd());	// 비회원 주문 코드
			}
			if(searchVO.getSchStartDt() != null && !searchVO.getSchStartDt().isEmpty()){
				map.put("SCHSTARTDT", searchVO.getSchStartDt() + " 00:00:00");		// 검색 기간 시작일		
			}
			if(searchVO.getSchEndDt() != null && !searchVO.getSchEndDt().isEmpty()){
				map.put("SCHENDDT", searchVO.getSchEndDt() + " 23:59:59");			// 검색 기간 종료일
			}
			
			map.put("SCHORDERSTATUS", schOrderStatus);								// 주문 상태		
			map.put("PAGESTART", ((searchVO.getPageNo() - 1) * searchVO.getPageBlock()));					
			map.put("PAGEBLOCK", searchVO.getPageBlock());																
			map.put("PAGENO", searchVO.getPageNo());			

			cnt = mypageOrderDAO.getIssueDocumentCnt(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}
	
	
	 /**
	    * @Method : TaxBillReq
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	:	세금계산서 발급 시넝
	   */
	public void TaxBillReq(String ORDER_IDX,String BUSINESS_NO,String COMPANY_NM,String CEO_NM,String ADDR,String UPTAE,String JONGMOK,String DAMDANG_NM,String DAMDANG_EMAIL, String REG_IDX) throws Exception {	
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ORDER_IDX", ORDER_IDX);	
		map.put("BUSINESS_NO", BUSINESS_NO);	
		map.put("COMPANY_NM", COMPANY_NM);	
		map.put("CEO_NM", CEO_NM);	
		map.put("ADDR", ADDR);	
		map.put("UPTAE", UPTAE);	
		map.put("JONGMOK", JONGMOK);	
		map.put("DAMDANG_NM", DAMDANG_NM);	
		map.put("DAMDANG_EMAIL", DAMDANG_EMAIL);	
		map.put("STATUS", "10"); //10 접수, 20:완료	
		map.put("REG_IDX", REG_IDX);
		mypageOrderDAO.insertTaxBillReq(map);
	}

	/**
	    * @Method : lgTelecomCashReceiptProcess
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	: LGU+ 현금영수증 신청
	    * 
	   */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Boolean lgTelecomCashReceiptProcess( SqlMap orderInfo, String CASH_RECEIPT_GUBUN, String CASH_RECEIPT_NO, SqlMap xpayInfo, JsonResultVO resultMap) throws Exception {
		Boolean result = false;
		SqlMap info = null;
		
		//1. DB   처리 (요청한 현금영수증 정보 저장)
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ORDER_IDX", orderInfo.get("orderIdx").toString());	
			map.put("CASH_RECEIPT_GUBUN", CASH_RECEIPT_GUBUN);	
			map.put("CASH_RECEIPT_NO", CASH_RECEIPT_NO);	
			mypageOrderDAO.updateCashBillReq(map);
			
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			resultMap.setMsg("현금영수증 정보저장중 에러가 발생하였습니다.");
			return false;
		}
		
		
		//2. LGU+결제 현금영수증 신청 모듈 실행
		XPayResultVO vo = new XPayResultVO();
		vo.setLGD_MID(xpayInfo.get("lgdMid").toString());
		vo.setLGD_TID(xpayInfo.get("lgdTid").toString());

	    String ResCode = "";
		try {
			
			final String XPAYCONFIGPATH =  SpringMessage.getMessage("xpay.CONFIGPATH"); 
			final String CST_PLATFORM = SpringMessage.getMessage("xpay.PLATFORM"); //"test";// 테스트여부    service 또는 test
			String configPath = XPAYCONFIGPATH;  //LG유플러스에서 제공한 환경파일("\conf\lgdacom.conf,\conf\mall.conf") 위치 지정.
	
	        XPayClient xpay = new XPayClient();
		    xpay.Init(configPath, CST_PLATFORM);
		    xpay.Init_TX(vo.getLGD_MID());
		    xpay.Set("LGD_TXNAME", "CashReceipt");  //현금영수증 신청
		    xpay.Set("LGD_METHOD", "AUTH");      //현금영수증 메소드(LGD_METHOD)는 AUTH, CANCEL, REAUTH, REGISTER, SEARCH, OMK_AUTH_ETC, OMK_CANCEL_ETC 중에 하나이어야 합니다.
		    xpay.Set("LGD_PAYTYPE", xpayInfo.get("lgdPaytype").toString());
		    xpay.Set("LGD_OID", xpayInfo.get("lgdOid").toString());

		    xpay.Set("LGD_CASHRECEIPTUSE", CASH_RECEIPT_GUBUN); 	//현금영수증 발급용도		소득공제:1, 지출증빙:2
		    xpay.Set("LGD_CASHCARDNUM", CASH_RECEIPT_NO);            	//현금영수증 발급번호		현금영수증카드번호, 주민번호, 휴대폰번호 등                   

		    xpay.Set("LGD_AMOUNT", xpayInfo.get("lgdAmount").toString());
		    xpay.Set("LGD_CUSTOM_BUSINESSNUM", "5098802366");
		    xpay.Set("LGD_CUSTOM_MERTPHONE", "025441191");
		    xpay.Set("LGD_CUSTOM_MERTNAME", "(주)엠와이지비");
		    xpay.Set("LGD_CUSTOM_CEONAME", "문준현");


	    	if (xpayInfo.get("lgdPaytype").toString().equals("SC0030")){  //기결제된 계좌이체건 현금영수증 발급요청시 필수  
			    xpay.Set("LGD_TID", vo.getLGD_TID());
	    	}
	    	else if (xpayInfo.get("lgdPaytype").equals("SC0040")){  //기결제된 가상계좌건 현금영수증 발급요청시 필수  
			    xpay.Set("LGD_TID", vo.getLGD_TID());
	    		xpay.Set("LGD_SEQNO", "001");
	    	}
	    	else {  								//무통장입금 단독건 발급요청  
			    xpay.Set("LGD_PRODUCTINFO", xpayInfo.get("lgdProductinfo").toString());
	        }

		    
		    if (xpay.TX()) {
		    	vo.setLGD_RESPCODE(xpay.m_szResCode);
		    	vo.setLGD_RESPMSG( xpay.m_szResMsg );
		        //1)결제취소결과 화면처리(성공,실패 결과 처리를 하시기 바랍니다.)
		        System.out.println( "TX Response_code = " + xpay.m_szResCode + "<br>");
		        System.out.println( "TX Response_msg = " + xpay.m_szResMsg + "<p>");
		        
		        ResCode = xpay.m_szResCode;
		    }else {
		        //2)API 요청 실패 화면처리
		        System.out.println( "TX Response_code = " + xpay.m_szResCode + "<br>");
		        System.out.println( "TX Response_msg = " + xpay.m_szResMsg + "<p>");
		        ResCode = xpay.m_szResCode;
		    }
		}catch(Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			resultMap.setMsg(info.get("result").toString());
			return false;
		}

		if (! ResCode.equals("0000")  )
		{
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			resultMap.setMsg(vo.getLGD_RESPMSG());
		}
		else {
			resultMap.setResult(true);
			resultMap.setMsg(vo.getLGD_RESPMSG());
			result = true;
		}
	
		return result;
	}
	   /**
	    * @Method : getXpayResult
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	: LGU+ 결제 정보
	   */
	@Override
	public SqlMap getXpayResult(OrderVO vo) throws Exception {
		SqlMap detail = new SqlMap();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			if(vo.getMemberIdx() > 0){	// 회원
				map.put("ORDERCD", vo.getOrderCd());		// 주문 코드ORDERCD
			}
			else {	
				map.put("ORDERCD", vo.getNomemberOrderCd());		// 주문 코드
			}
			detail = mypageOrderDAO.getXpayResult(map); // 주문 상태별 건수
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return detail;
	}
	
	   /**
	    * @Method : getPaycoApprovalResult
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	: PAYCO 결제 정보
	   */
	@Override
	public SqlMap getPaycoApprovalResult(OrderVO vo) throws Exception {
		SqlMap detail = new SqlMap();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			if(vo.getMemberIdx() > 0){	// 회원
				map.put("ORDERCD", vo.getOrderCd());		
			}
			else {	
				map.put("ORDERCD", vo.getNomemberOrderCd());		
			}
			detail = mypageOrderDAO.getPaycoApprovalResult(map); 
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return detail;
	}

	/**
	    * @Method : cancleAllDBModule
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	: 전체 취소 DB처리
	    * 
	   */
	public SqlMap cancleAllDBModule(SqlMap orderInfo, String reason) throws Exception {
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		InetAddress local = InetAddress.getLocalHost();	// LOCAL IP ADDRESS

		HashMap<String, Object> map = new HashMap<String, Object>();
        
		map.put("CLAIM_IDX", "");		
		map.put("ORDER_IDX", orderInfo.get("orderIdx").toString());
		map.put("CLAIM_TYPE", "C");  // C : 취소, X : 교환, R : 반품 
		map.put("DEVICE", PathUtil.getDeviceNm());		// 디바이스 (P : PC, M : MOBILE, A : APP)
		map.put("REST_PAY_PRICE", "0"); //잔여결제금액
		map.put("TOTAL_REFUND_CHARGE", "0"); //총환불수수료
		map.put("REFUND_SUBTRACTION", "0"); //환불차감금액
		map.put("FREE_SHIPPING_COUPON_REFUND_PRICE", orderInfo.get("freeShippingCouponPrice").toString());
		map.put("TOTAL_GIFT_COUPON_REFUND_PRICE", orderInfo.get("totalGiftCouponPrice").toString());
		map.put("TOTAL_CART_COUPON_REFUND_PRICE", orderInfo.get("totalCartCouponPrice").toString());
		map.put("TOTAL_COUPON_REFUND_PRICE", orderInfo.get("totalCouponPrice").toString());
		map.put("SHIPPING_REFUND_PRICE", orderInfo.get("shippingPrice").toString());
		map.put("TOTAL_POINT_REFUND_PRICE", orderInfo.get("totalPointPrice").toString());
		map.put("TOTAL_PRE_POINT_REFUND_PRICE", "0");
		map.put("TOTAL_REFUND_PRICE", orderInfo.get("totalPayPrice").toString());
		map.put("TOTAL_REFUND_POINT", orderInfo.get("totalSavePoint").toString());
		map.put("TOTAL_PROMOTIONCODE_PRICE", orderInfo.get("totalPromotioncodePrice").toString());
		map.put("REFUND_BANK_CODE", nvl(orderInfo.get("refundBankCode")));						// 환불 은행코드
		map.put("REFUND_ACCOUNT", Crypto.encrypt(nvl(orderInfo.get("refundAccount"))));	// 환불 계좌번호 암호화
		map.put("REFUND_DEPOSITOR", nvl(orderInfo.get("refundDepositor")));						// 환불 예금주명
		map.put("REASON_CD", "CANCEL_REASON90"); //사유코드 (기타)
		map.put("REASON", reason); //사유

		map.put("REFUND_METHOD", "P"); // 환불방식 (P : PG사 결제취소, A : 현금환불)
		map.put("REFUND_STATE", "E"); // 환불상태 (E : 처리 완료)
		if (UserInfo.getUserInfo().getMemberId().isEmpty()) {
			map.put("REFUND_MEMBER_IDX", 0); // 환불 처리자 일련번호
			map.put("REQ_MEMBER_IDX", 0);							// 신청자 일련번호
		}
		else {
			map.put("REFUND_MEMBER_IDX", UserInfo.getUserInfo().getMemberIdx()); // 환불 처리자 일련번호
			map.put("REQ_MEMBER_IDX", UserInfo.getUserInfo().getMemberIdx());							// 신청자 일련번호
		}
		map.put("REFUND_HTTP_USER_AGENT", request.getHeader("user-agent")); // 환불 처리자 USER_AGENT
		map.put("REFUND_IP", local.getHostAddress()); 										// 환불 처리자 IP
		map.put("REQ_HTTP_USER_AGENT", request.getHeader("user-agent"));		// 신청자 USER_AGENT
		map.put("REQ_IP", local.getHostAddress());											// 신청자 IP

		map.put("REG_HTTP_USER_AGENT", orderInfo.get("httpUserAgent").toString());
		map.put("REG_IP", orderInfo.get("regIp").toString());
		if(orderInfo.get("memberIdx") == null){
			map.put("REG_IDX", 0);
		}else{
			map.put("REG_IDX", orderInfo.get("memberIdx").toString());
		}
		
		//1. 클레임테이블(마스터,detail)정보 저장
		mypageOrderDAO.insertClaimMaster(map);
		String claimIdx = map.get("CLAIM_IDX").toString();
		
		orderInfo.put("claimIdx", claimIdx); //orderInfo에 claimidx를 저장해줌. 메일/sms보낼때 사용
		
		//1. 클레임 detail 정보 저장
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("CLAIM_IDX", claimIdx);
		map2.put("ORDER_IDX", orderInfo.get("orderIdx").toString());
		map2.put("REG_HTTP_USER_AGENT", orderInfo.get("httpUserAgent").toString());
		map2.put("REG_IP", orderInfo.get("regIp").toString());
		if(orderInfo.get("memberIdx") == null){
			map2.put("REG_IDX", 0);
		}else{
			map2.put("REG_IDX", orderInfo.get("memberIdx").toString());
		}
		mypageOrderDAO.insertClaimDetailAllCancel(map2);
		SqlMap info = new SqlMap();
		
		//2. 주문테이블, 쿠폰, 포인트등 결제 취소 프로시저 호출
		map2.clear();
		map2.put("CLAIM_IDX", claimIdx);
		info = mypageOrderDAO.procSpOrderCancelRefund(map2);
		
		//3. 사은품 상태변경
		map2.clear();
		map2.put("ORDER_IDX", orderInfo.get("orderIdx").toString());
		map2.put("STATUS", "9");  //9: 취소
		mypageOrderDAO.updateOrderGiftStatus(map2);
		
		return info;
	} 
	
	
	/**
	    * @Method : lgTelecomCasNotiCancelProcess
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	: LGU+주문 취소 (가상계좌 연동 URL로 취소 호출시 )
	    * 
	   */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Boolean lgTelecomCasNotiCancelProcess( String orderCd, SqlMap xpayInfo, JsonResultVO resultMap) throws Exception {
		Boolean result = false;
		SqlMap info = null;
		//1. DB  취소 처리
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ORDERCD", orderCd);
			SqlMap orderInfo = mypageOrderDAO.getOrderMasterInfo(map);
			orderInfo.put("httpUserAgent","httpUserAgent");
			orderInfo.put("regIp","0.0.0.0");
			orderInfo.put("memberIdx","0");
			
			info = cancleAllDBModule(orderInfo, "가상계좌 취소 연동");	
			if(info == null){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
				resultMap.setResult(false);
				resultMap.setMsg("취소 DB 처리중 에러가 발생했습니다!");
				return false;
			}else{
				if(!info.get("result").toString().equals("TRUE")){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
					resultMap.setResult(false);
					resultMap.setMsg(info.get("result").toString());
					return false;
				}
				else {
					resultMap.setResult(true);
					result = true;
				}
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			resultMap.setMsg(info.get("result").toString());
			return false;
		}
		
		
		//2. LGU+결제 취소 모듈 실행
		/*
		 * [[[중요]]] 고객사에서 정상취소 처리해야할 응답코드
		 * 1. 신용카드 : 0000, AV11  
		 * 2. 계좌이체 : 0000, RF00  만 성공               RF10, RF09, RF15, RF19, RF23, RF25 (환불진행중 응답건-> 환불결과코드.xls 참고)
		 * 3. 나머지 결제수단의 경우 0000(성공) 만 취소성공 처리
		 */

		/*  가상계좌 취소이기때문에 아직 입금된금액은 없음  취소모듈 돌릴필요없음 (강병철 20170821)
		XPayResultVO vo = new XPayResultVO();
		vo.setLGD_MID(xpayInfo.get("lgdMid").toString());
		vo.setLGD_TID(xpayInfo.get("lgdTid").toString());

		String ResCode = xPayCancel(vo) ;
		
		if (! ( ResCode.equals("0000") || ResCode.equals("AV11")  || ResCode.equals("RF00")) )
		{
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			resultMap.setMsg(vo.getLGD_RESPMSG());
		}
		else {
			resultMap.setResult(true);
			resultMap.setMsg(vo.getLGD_RESPMSG());
			result = true;
		}
		 */
		return result;
	}
	
	/**
	    * @Method : lgTelecomCancelProcess
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	: LGU+주문 취소
	    * 
	   */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Boolean lgTelecomCancelProcess( SqlMap orderInfo, SqlMap xpayInfo, JsonResultVO resultMap) throws Exception {
		Boolean result = false;
		SqlMap info = null;
		//1. DB  취소 처리
		try {
			info = cancleAllDBModule(orderInfo, "주문자 직접 취소");	
			if(info == null){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
				resultMap.setResult(false);
				resultMap.setMsg("취소 DB 처리중 에러가 발생했습니다!");
				return false;
			}else{
				if(!info.get("result").toString().equals("TRUE")){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
					resultMap.setResult(false);
					resultMap.setMsg(info.get("result").toString());
					return false;
				} else {
					resultMap.setResult(true);
					result = true;
				}
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			resultMap.setMsg(info.get("result").toString());
			return false;
		}
		
		
		//2. LGU+결제 취소 모듈 실행
		/*
		 * [[[중요]]] 고객사에서 정상취소 처리해야할 응답코드
		 * 1. 신용카드 : 0000, AV11  
		 * 2. 계좌이체 : 0000, RF00  만 성공               RF10, RF09, RF15, RF19, RF23, RF25 (환불진행중 응답건-> 환불결과코드.xls 참고)
		 * 3. 나머지 결제수단의 경우 0000(성공) 만 취소성공 처리
		 */

		XPayResultVO vo = new XPayResultVO();
		vo.setLGD_MID(xpayInfo.get("lgdMid").toString());
		vo.setLGD_TID(xpayInfo.get("lgdTid").toString());

		// 가상계좌면 환불계좌등 파라미터 추가
		if(orderInfo.get("payType").equals("PAY_TYPE25")){
			vo.setLGD_RFBANKCODE(nvl(orderInfo.get("refundBankCode")));			//환불계좌 은행코드 (가상계좌환불은 필수)
			vo.setLGD_RFACCOUNTNUM(nvl(orderInfo.get("refundAccount")));			//환불계좌 번호 (가상계좌환불은 필수)
			vo.setLGD_RFCUSTOMERNAME(nvl(orderInfo.get("refundDepositor")));	//환불계좌 예금주 (가상계좌환불은 필수)
			vo.setLGD_RFPHONE(nvl(orderInfo.get("senderPhoneNo")));					//요청자 연락처 (가상계좌환불은 필수)
		}

		String ResCode = xPayCancel(vo, nvl(orderInfo.get("payType")));
		
		if (! ( ResCode.equals("0000") || ResCode.equals("AV11")  || ResCode.equals("RF00")) )
		{
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			resultMap.setMsg(vo.getLGD_RESPMSG());
			result = false;
		}
		else {
			resultMap.setResult(true);
			resultMap.setMsg(vo.getLGD_RESPMSG());
			result = true;
		}
	
		return result;
	}
	
	/**
	    * @Method : paycoCancelProcess
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	: PAYCO 주문 취소
	    * @Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	   */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Boolean paycoCancelProcess(SqlMap orderInfo, SqlMap approvalInfo, JsonResultVO resultMap) throws Exception {
		Boolean result = false;
		SqlMap info = null;		
		//1. DB  취소 처리
		try {
			info = cancleAllDBModule(orderInfo, "주문자 직접 취소");	
			if(info == null){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
				resultMap.setResult(false);
				resultMap.setMsg("취소 DB 처리중 에러가 발생했습니다!");
				return false;
			}else{
				if(!info.get("result").toString().equals("TRUE")){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
					resultMap.setResult(false);
					resultMap.setMsg(info.get("result").toString());
					return false;
				}
				else {
					resultMap.setResult(true);
					result = true;					
				}
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			resultMap.setMsg(info.get("result").toString());
			return false;
		}
		
		//2. PAYCO 결제 취소 모듈 실행
		PaycoApprovalVO vo = new PaycoApprovalVO();
		vo.setSellerKey(approvalInfo.get("sellerKey").toString());
		vo.setOrderNo(approvalInfo.get("orderNo").toString());
		vo.setOrderCertifyKey(approvalInfo.get("orderCertifyKey").toString());
		vo.setTotalPaymentAmt(Double.valueOf(approvalInfo.get("totalPaymentAmt").toString()));
		
		/*
		 * [[[중요]]] 고객사에서 정상취소 처리해야할 응답코드
		 * 1. 신용카드 : 0000, AV11  
		 * 2. 계좌이체 : 0000, RF00  만 성공               RF10, RF09, RF15, RF19, RF23, RF25 (환불진행중 응답건-> 환불결과코드.xls 참고)
		 * 3. 나머지 결제수단의 경우 0000(성공) 만 취소성공 처리
		 */
		PaycoReturnVO ResVo = this.paycoCancel(vo) ;
		
		if (! ResVo.getCode().equals("0") )
		{
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			resultMap.setMsg(ResVo.getMessage());
			result = false;
			Exception e = new Exception(ResVo.getMessage());
			throw e;
		}
		else {
			resultMap.setResult(true);
			result = true;
		}
		return result;
	}
	
	/**
	    * @Method : pointCancelProcess
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	:0원 취소
	   */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Boolean pointCancelProcess(SqlMap orderInfo,  JsonResultVO resultMap) throws Exception  {
		Boolean result = false;
		SqlMap info = null;		
		//1. DB  취소 처리
		try {
			info = cancleAllDBModule(orderInfo, "주문자 직접 취소");	
			if(info == null){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
				resultMap.setResult(false);
				resultMap.setMsg("취소 DB 처리중 에러가 발생했습니다!");
				return false;
			}else{
				if(!info.get("result").toString().equals("TRUE")){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
					resultMap.setResult(false);
					resultMap.setMsg(info.get("result").toString());
					return false;
				}
				else {
					result = true;
					resultMap.setResult(true);
				}
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			resultMap.setMsg(info.get("result").toString());
			return false;
		}
		
 		return result;
	}
	
	/**
	    * @Method : xPayCancel
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	: LGU+ 결제 주문 취소
	   */
	public String xPayCancel(XPayResultVO vo, String payType) throws Exception {
		String result = "";
		try {
			
			final String XPAYCONFIGPATH =  SpringMessage.getMessage("xpay.CONFIGPATH"); 
			final String CST_PLATFORM = SpringMessage.getMessage("xpay.PLATFORM"); //"test";// 테스트여부    service 또는 test
			String configPath = XPAYCONFIGPATH;  //LG유플러스에서 제공한 환경파일("\conf\lgdacom.conf,\conf\mall.conf") 위치 지정.
	
	        XPayClient xpay = new XPayClient();
		    xpay.Init(configPath, CST_PLATFORM);
		    xpay.Init_TX(vo.getLGD_MID());
		    xpay.Set("LGD_TXNAME", "Cancel");
		    xpay.Set("LGD_TID", vo.getLGD_TID());
		    
		    // 가상계좌 환불용
		    if(payType.equals("PAY_TYPE25")){
			    xpay.Set("LGD_RFBANKCODE", nvl(vo.getLGD_RFBANKCODE()));
			    xpay.Set("LGD_RFACCOUNTNUM", nvl(vo.getLGD_RFACCOUNTNUM()));
			    xpay.Set("LGD_RFCUSTOMERNAME", nvl(vo.getLGD_RFCUSTOMERNAME()));
			    xpay.Set("LGD_RFPHONE", nvl(vo.getLGD_RFPHONE()));
		    }

		    /*
		     * 1. 결제취소 요청 결과처리
		     *
		     * 취소결과 리턴 파라미터는 연동메뉴얼을 참고하시기 바랍니다.
			 *
			 * [[[중요]]] 고객사에서 정상취소 처리해야할 응답코드
			 * 1. 신용카드 : 0000, AV11  
			 * 2. 계좌이체 : 0000, RF00  만 성공               RF10, RF09, RF15, RF19, RF23, RF25 (환불진행중 응답건-> 환불결과코드.xls 참고)
			 * 3. 나머지 결제수단의 경우 0000(성공) 만 취소성공 처리
			 *
		     */
		    if (xpay.TX()) {
		    	vo.setLGD_RESPCODE(xpay.m_szResCode);
		    	vo.setLGD_RESPMSG( xpay.m_szResMsg );
		        //1)결제취소결과 화면처리(성공,실패 결과 처리를 하시기 바랍니다.)
		        System.out.println("결제 취소요청이 완료되었습니다.");
		        System.out.println( "TX Response_code = " + xpay.m_szResCode + "<br>");
		        System.out.println( "TX Response_msg = " + xpay.m_szResMsg + "<p>");
		        
		        result = xpay.m_szResCode;
		    }else {
		        //2)API 요청 실패 화면처리
		        System.out.println("결제 취소요청이 실패하였습니다.  <br>");
		        System.out.println( "TX Response_code = " + xpay.m_szResCode + "<br>");
		        System.out.println( "TX Response_msg = " + xpay.m_szResMsg + "<p>");
		        result = xpay.m_szResCode;
		    }
		}catch(Exception e){
			result = "AAAA";
			vo.setLGD_RESPCODE(result);
	    	vo.setLGD_RESPMSG( e.getMessage() );
		}

	    return result;
	}
	

	/**
	    * @Method : paycoCancel
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	: PAYCO 결제 주문 취소
	   */
	public PaycoReturnVO paycoCancel(PaycoApprovalVO vo) throws Exception {
		PaycoSettingVO setting = new PaycoSettingVO();
		ObjectMapper mapper = new ObjectMapper(); 		  //jackson json object
		PaycoUtil 	 util 	= new PaycoUtil(setting.getServerType()); //Co
		
		PaycoReturnVO result = new PaycoReturnVO();
		/* 설정한 주문취소 정보로 Json String 을 작성합니다. */
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("sellerKey", setting.getSellerKey());										//[필수]가맹점 코드
		param.put("orderNo", vo.getOrderNo());													//[필수]주문번호
		param.put("orderCertifyKey", vo.getOrderCertifyKey());								//[필수]주문인증 key
		param.put("cancelTotalAmt", Double.valueOf(vo.getTotalPaymentAmt().toString()).intValue());  	//[필수]취소할 총 금액(전체취소, 부분취소 전부다)
		
		param.put("requestMemo", "고객직접취소");
		/*
		if(orderProducts.size() != 0){
			
			param.put("orderProducts", orderProducts);					//[선택]취소할 상품 List(부분취소인 경우 사용, 입력하지 않는 경우 전체 취소)	
		}
		*/
		/* 부분취소 중복을 막기위해 totalCancelPossibleAmt 컬럼을 사용하는 예 
		 * 예를들어 고객이 10만원 주문을 하고 2만원을 부분취소 하고 싶은데 두번눌러서 4만원이 취소 되는 케이스
		 * 또는 어떠한 이유로 PAYCO 에서는 2만원 부분취소가 되었지만 가맹점 에서는 취소가 발생하지 않았을때 등의
		 * 예외상황이 발생했을때를 대비하여 totalCancelPossibleAmt 컬럼의 값을 설정하여 보내주시면 중복취소를 막을 수 있습니다.
		 * 고객이 10만원 결제금액 중 2만원 부분취소시도 -> PAYCO에는 2만원 취소성공, 그러나 어떠한 이유로 고객 화면에는 취소가 안되었음
		 * -> 고객이 다시 2만원 취소 -> 이때 가맹점 에서는 취소된상품이 하나도 없으므로 totalCancelPossibleAmt 값을 10만원으로 보냄 -> 
		 * 가맹점은 totalCancelPossibleAmt 값이 10만원이고 PAYCO 는 2만원을 제외한 8만원이 취소가능금액 이므로 취소가능금액이 일치하지않아 부분취소 불가.
		 */
		//param.put("totalCancelPossibleAmt", totalCancelPossibleAmt);	//[선택]총 취소가능금액(현재기준) : 취소가능금액 검증

		/* 주문 결제 취소 API 호출 */
		String cancelResult = util.payco_cancel(param,setting.getLogYn(),"N");
		JsonNode cancelNode = mapper.readTree(cancelResult);
		
		if(!cancelNode.path("code").toString().equals("0")){
			result.setCode(cancelNode.path("code").textValue());
			String message   = "PAYCO 결제 취소 중 에러가 발생하였습니다.("+cancelNode.path("message").textValue()+")";
			result.setMessage(message);
		}
		else {
			result.setCode(cancelNode.path("code").toString());
		}
		return result;
	}
	
	/**
	    * @Method : setDeliveryChange
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	: 배송지정보수정
	   */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public void setDeliveryChange(OrderVO vo) throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();

		if(vo.getMemberIdx() > 0){	// 회원
			map.put("ORDERCD", vo.getOrderCd());					// 주문 코드
		}else{								// 비회원
			map.put("ORDERCD", vo.getNomemberOrderCd());	// 비회원 주문 코드
		}

		map.put("RECEIVERNM", vo.getReceiverNm());
		map.put("RECEIVERADDR", vo.getReceiverAddr());
		map.put("RECEIVERADDRDETAIL", vo.getReceiverAddrDetail());
		map.put("RECEIVERZIPCD", vo.getReceiverZipCd());
		map.put("RECEIVEROLDADDR", vo.getReceiverOldAddr());
		map.put("RECEIVEROLDZIPCD", vo.getReceiverOldZipCd());
		map.put("RECEIVERPHONENO", vo.getReceiverPhoneNo());
		map.put("RECEIVERTELNO", vo.getReceiverTelNo());
		mypageOrderDAO.updateOrderMasterDelivery(map);

		if(vo.getMemberIdx() > 0){	// 회원
			HashMap<String, Object> map2 = new HashMap<String, Object>();
			//내주소록에 저장
			if (vo.getAddToAddress() != null ) {
					map2.put("SHIPPINGNM", vo.getShippingNm());
					map2.put("RECEIVERNM", vo.getReceiverNm());
					map2.put("ADDR", vo.getReceiverAddr());
					map2.put("ADDRDETAIL", vo.getReceiverAddrDetail());
					map2.put("ZIPCD", vo.getReceiverZipCd());
					map2.put("OLDADDR", vo.getReceiverOldAddr());
					map2.put("OLDZIPCD", vo.getReceiverOldZipCd());
					map2.put("TELNO", vo.getReceiverTelNo());
					map2.put("PHONENO", vo.getReceiverPhoneNo());
					if (vo.getSetAsDefaultAddress()!= null  && !vo.getSetAsDefaultAddress().isEmpty() && vo.getSetAsDefaultAddress().equals("Y")) {
						map2.put("DEFAULTYN", "Y");
					}else{
						map2.put("DEFAULTYN", "N");
					}
					map2.put("EDITHTTPUSERAGENT", vo.getRegHttpUserAgent());
					map2.put("EDITIP", vo.getRegIp());
					map2.put("MEMBERIDX", vo.getMemberIdx());
					
					//기본배송지 변경
					if (vo.getSetAsDefaultAddress()!= null  && !vo.getSetAsDefaultAddress().isEmpty() && vo.getSetAsDefaultAddress().equals("Y")) {
						map2.put("EDITHTTPUSERAGENT", vo.getRegHttpUserAgent());
						map2.put("EDITIP", vo.getRegIp());
				      	memberDAO.shippingAllDefalutUpdate(map2);	
					}
					
					//배송지수정					
					if (vo.getAddressTabId() != null  && !vo.getAddressTabId().isEmpty()  &&Integer.parseInt(vo.getAddressTabId()) > 0) {
						map2.put("ADDRESSIDX", Integer.parseInt(vo.getAddressTabId()) );
						memberDAO.memberShippingUpdate(map2);		
					}
					else {
						//배송지 추가
						map2.put("REGHTTPUSERAGENT", vo.getRegHttpUserAgent());
						map2.put("REGIP", vo.getRegIp());
						memberDAO.memberShippingInsert(map2);
					}
				
			}
		}
	}

   /**
    * @Method : insertOrderStatusLog
    * @Date: 2017. 8. 7.
    * @Author :  강병철
    * @Description	:	주문상세 로그 저장
   */
	public void insertOrderStatusLog(OrderVO vo, int orderDetailIdx, String statusCd , String reason) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ORDERIDX", vo.getOrderIdx());
		map.put("ORDERDETAILIDX", orderDetailIdx);
		map.put("ORDERSTATUSCD", statusCd);
		map.put("REASON", reason);
		map.put("DEVICE", PathUtil.getDeviceNm());		// 디바이스 (P : PC, M : MOBILE, A : APP)
		map.put("REGIDX", vo.getMemberIdx());
		map.put("REGHTTPUSERAGENT", vo.getRegHttpUserAgent());
		map.put("REGIP", vo.getRegIp());
		mypageOrderDAO.insertOrderStatusLog(map); // 주문 상태별 건수
	}
	/**
    * @Method : AllCompletStatus
    * @Date: 2017. 8. 7.
    * @Author :  강병철
    * @Description	:	전체 구매확정
   */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public void AllCompletStatus(OrderVO vo, JsonResultVO resultMap) throws Exception {
		List<SqlMap> detaillist = this.getOrderDetailList(vo, false);	// 주문 상세 리스트, 가상계좌 입금에서 호출 아님 (false)
		HashMap<String, Object> map = new HashMap<String, Object>();
		int Point = 0;
		for(SqlMap detail : detaillist) {
			 String reason = "사용자 전체 구매확정";
			//배송중(발송 완료), 배송 완료, 교환 불가, 교환 완료시 구매 확정 가능
			if (detail.get("detailStatusCd").toString().equals("400") || detail.get("detailStatusCd").toString().equals("500") || detail.get("detailStatusCd").toString().equals("670") || detail.get("detailStatusCd").toString().equals("690"))
			{
				map.clear();
				map.put("ORDERIDX", vo.getOrderIdx());
				map.put("ORDERDETAILIDX",Integer.parseInt(detail.get("orderDetailIdx").toString()));
				mypageOrderDAO.updateOrderDetailStatusTo900(map);
				insertOrderStatusLog(vo, Integer.parseInt(detail.get("orderDetailIdx").toString()) , "900" ,reason);
				
				map.clear();
				map.put("ORDERDETAILIDX", Integer.parseInt(detail.get("orderDetailIdx").toString()));
				Point += mypageOrderDAO.getOrderSavePoint(map);
			}
		}
		
		//master 상태 변경
		map.clear();
		map.put("ORDERIDX", vo.getOrderIdx());
		mypageOrderDAO.updateOrderStatusCdAsDetailMinOrderStatusCd(map);

		//포인트 적립
		if (vo.getMemberIdx() > 0) {
			if (Point > 0) {
				map.clear();
				map.put("MEMBER_IDX", vo.getMemberIdx());
				map.put("ORDER_IDX", vo.getOrderIdx());
				map.put("EVENT_IDX", "");
				map.put("PAY_DED_TYPE", "P"); //지급/차감구분 ( P : 지급, M : 차감 )
				map.put("PAY_DED_REASON", "POINT_REASON50"); //POINT_REASON50 : 구매확정에의한 구매포인트 지급
				map.put("PAYMENT_PRICE", Point);
				map.put("DEDUCTION_PRICE", ""); //차감포인트
				mypageOrderDAO.orderInsertPointHistory(map);
				
				map.clear();
				map.put("MEMBERIDX", vo.getMemberIdx());
				map.put("POINT", Point);
				mypageOrderDAO.memberPointUpdate(map);

/*	// 추천인 일단 주석 처리				
				// 첫 주문이고 추천인이 있으면 추천인에게 포인트 지급
				map.clear();
				map.put("MEMBERIDX", vo.getMemberIdx());
				Integer recommendIdx = mypageOrderDAO.getRecommendIdx(map);	// 추천인 일련번호 (일련번호가 있다는건 현재가 최초 주문이고 추천인을 입력했다라는 뜻)
				if(recommendIdx != null && recommendIdx.intValue() > 0){
					int recommendPoint = 1000;		// 1000 포인트 지급
					
					map.clear();
					map.put("MEMBER_IDX", recommendIdx);	// 추천인 일련번호
					map.put("ORDER_IDX", null);
					map.put("EVENT_IDX", null);
					map.put("PAY_DED_TYPE", "P"); //지급/차감구분 ( P : 지급, M : 차감 )
					map.put("PAY_DED_REASON", "POINT_REASON20"); //POINT_REASON20 : 추천인 등록으로 인한 포인트지급
					map.put("PAYMENT_PRICE", recommendPoint);
					map.put("DEDUCTION_PRICE", ""); //차감포인트
					mypageOrderDAO.orderInsertPointHistory(map);
					
					map.clear();
					map.put("MEMBERIDX", recommendIdx);		// 추천인 일련번호
					map.put("POINT", recommendPoint);
					mypageOrderDAO.memberPointUpdate(map);

					// 추천인 일련번호 삭제
					map.clear();
					map.put("MEMBERIDX", vo.getMemberIdx());	// 추천한 사람 일련번호(주문자/로그인 한 회원)
					mypageOrderDAO.updateRecommendIdxNull(map);					
				}
*/
			}
		}	
	}
	
	   /**
	    * @Method : getOrderInfo
	    * @Date: 2017. 8. 7.
	    * @Author :  서 정 길
	    * @Description	:	주문 상세 - 주문 마스터 정보
	    * isCasNote : 가상계좌 입금완료에서 호출시는 회원/비회원 여부 상관없이 주문 코드로만 조회하기 위해
	   */
	@Override
	public SqlMap getOrderInfo(OrderVO vo, Boolean isCasNote) throws Exception {
		SqlMap detail = new SqlMap();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			// 가상계좌 입금완료에서 호출시는 회원/비회원 여부 상관없이 주문 코드로만 조회하기 위해
			if(isCasNote != null && isCasNote){
				map.put("ORDERCDONLY", "Y");
				map.put("ORDERCD", vo.getOrderCd());					// 주문 코드
			}else{
				if(vo.getMemberIdx() > 0){	// 회원
					map.put("MEMBERIDX", vo.getMemberIdx());			// 회원 일련번호
					map.put("ORDERCD", vo.getOrderCd());					// 주문 코드
				}else{								// 비회원
					map.put("ORDERCD", vo.getNomemberOrderCd());	// 비회원 주문 코드
				}
			}
		
			detail = mypageOrderDAO.getOrderInfo(map); // 주문 마스터 정보
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}

		return detail;
	}

	   /**
	    * @Method : getOrderDetailList
	    * @Date: 2017. 8. 7.
	    * @Author :  서 정 길
	    * @Description	:	주문 상세 - 주문 상세 리스트
	    * isCasNote : 가상계좌 입금완료에서 호출시는 회원/비회원 여부 상관없이 주문 코드로만 조회하기 위해
	   */
	@Override
	public List<SqlMap> getOrderDetailList(OrderVO vo, Boolean isCasNote) throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();

		try{
			// 가상계좌 입금완료에서 호출시는 회원/비회원 여부 상관없이 주문 코드로만 조회하기 위해
			if(isCasNote != null && isCasNote){
				map.put("ORDERCDONLY", "Y");	
				map.put("ORDERCD", vo.getOrderCd());					// 주문 코드
			}else{
				if(vo.getMemberIdx() > 0){	// 회원
					map.put("MEMBERIDX", vo.getMemberIdx());			// 회원 일련번호
					map.put("ORDERCD", vo.getOrderCd());					// 주문 코드
				}else{								// 비회원
					map.put("ORDERCD", vo.getNomemberOrderCd());	// 비회원 주문 코드
				}
			}

			list = mypageOrderDAO.getOrderDetailList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	   /**
	    * @Method : getOrderGiftList
	    * @Date: 2017. 8. 9.
	    * @Author :  서 정 길
	    * @Description	:	주문 상세 - 사은품 리스트
	    * freeYn : 무료구분	Y : 무료사은품, N:구간사은품
	   */
	@Override
	public List<SqlMap> getOrderGiftList(OrderVO vo, String freeYn) throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();

		try{
			if(vo.getMemberIdx() > 0){	// 회원
				map.put("ORDERCD", vo.getOrderCd());					// 주문 코드
			}else{								// 비회원
				map.put("ORDERCD", vo.getNomemberOrderCd());	// 비회원 주문 코드
			}
			
			map.put("FREEYN", freeYn);					// 무료구분	Y : 무료사은품, N:구간사은품

			list = mypageOrderDAO.getOrderGiftList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	   /**
	    * @Method : getPaycoResultList
	    * @Date: 2017. 8. 8.
	    * @Author :  서 정 길
	    * @Description	:	주문 상세 - PAYCO 결제 결과 리스트
	   */
	@Override
	public List<SqlMap> getPaycoResultList(OrderVO vo) throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();

		try{
			if(vo.getMemberIdx() > 0){	// 회원
				map.put("ORDERCD", vo.getOrderCd());					// 주문 코드
			}else{								// 비회원
				map.put("ORDERCD", vo.getNomemberOrderCd());	// 비회원 주문 코드
			}

			list = mypageOrderDAO.getPaycoResultList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	   /**
	    * @Method : getOrderDetailValidStatusCnt
	    * @Date: 2017. 8. 11.
	    * @Author :  서 정 길
	    * @Description	:	주문 상세 - 선택한 상품들 중 해당 상태에 맞는 상품 건 수
	   */
	@Override
	public JsonResultVO getOrderDetailValidStatusCnt(ClaimVO vo, String[] orderStatusCds) throws Exception {
		
		JsonResultVO resultMap = new JsonResultVO();
		
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("ORDERDETAILIDXES", vo.getOrderDetailIdxes());	// 선택한 주문 디테일 일련번호
		map.put("ORDERSTATUSCDS", orderStatusCds);					// 유효한 주문 상태 코드

		if(vo.getMemberIdx() > 0){	// 회원
			map.put("ORDERCD", vo.getOrderCd());					// 주문 코드
		}else{								// 비회원
			map.put("ORDERCD", vo.getNomemberOrderCd());	// 비회원 주문 코드
		}

		try{
			cnt = mypageOrderDAO.getOrderDetailValidStatusCnt(map);
		}catch(Exception e){
			e.printStackTrace();
			resultMap.setResult(false);
			resultMap.setMsg(e.getMessage());
			return resultMap;
		}

		resultMap.setResult(true);
		resultMap.setResultCnt(cnt);
		
		return resultMap;
	}
	
	   /**
	    * @Method : updateOrderStatusCdAsDetailMinOrderStatusCd
	    * @Date: 2017. 8. 11.
	    * @Author :  서 정 길
	    * @Description	:	주문 상세의 주문 상태 코드들 최소값으로 주문 마스터의 주문 상태 코드 변경
	   */
	@Override
	public int updateOrderStatusCdAsDetailMinOrderStatusCd(String orderIdx) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("ORDERIDX", orderIdx);
		
		flag = mypageOrderDAO.updateOrderStatusCdAsDetailMinOrderStatusCd(map);			
		
		return flag;
	}	

	   /**
	    * @Method : insertOrderStatusLog
	    * @Date: 2017. 8. 11.
	    * @Author :  서 정 길
	    * @Description	:	주문 상세 로그 저장
	   */
	@Override
	public int insertOrderStatusLog(OrderDetailVO vo) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("ORDERIDX", vo.getOrderIdx());
		map.put("ORDERDETAILIDX", vo.getOrderDetailIdx());
		map.put("ORDERSTATUSCD", vo.getOrderStatusCd());
		map.put("REASON", vo.getReason());
		map.put("DEVICE", PathUtil.getDeviceNm());		// 디바이스 (P : PC, M : MOBILE, A : APP)
		map.put("REGIDX", vo.getRegIdx());
		map.put("REGHTTPUSERAGENT", vo.getRegHttpUserAgent());
		map.put("REGIP", vo.getRegIp());
		
		flag = mypageOrderDAO.insertOrderStatusLog(map);			
		
		return flag;
	}

	   /**
	    * @Method : requestExchangeOrder
	    * @Date: 2017. 8. 11.
	    * @Author :  서 정 길
	    * @Description	:	교환 신청
	   */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public JsonResultVO requestExchangeOrder(ClaimVO vo) throws Exception {
		int flag = 0;
		
		JsonResultVO resultMap = new JsonResultVO();
		resultMap.setResult(false);
		
		HashMap<String, Object> map = new HashMap<>();
		
		// ORDER_IDX 구함
		map.clear();
		map.put("ORDERCD", vo.getOrderCd());		// 주문 코드
		map.put("MEMBERIDX", vo.getMemberIdx());		// 주문 코드

		SqlMap info = orderDAO.getOrderInfo(map);	// 주문 정보

		if(info == null || info.size() == 0){
			resultMap.setResult(false);
			resultMap.setMsg("주문 일련번호가 없습니다!");
			return resultMap;
		}
		
		String orderIdx = info.get("orderIdx").toString();
		
		// 클레임 마스터 등록
		map.clear();
		map.put("ORDER_IDX", orderIdx);
		map.put("CLAIM_TYPE", "X");													// 클레임 구분 (C : 취소, X : 교환, R : 반품)		
		map.put("DEVICE", PathUtil.getDeviceNm());								// 디바이스 (P : PC, M : MOBILE, A : APP)
		map.put("REST_PAY_PRICE", 0);												// 잔여 결제금액	// TODO 계산해서 넘겨야 함
		map.put("TOTAL_REFUND_CHARGE", 0);										// 총 환불수수료		
		map.put("FREE_SHIPPING_COUPON_REFUND_PRICE", 0);				// 무료배송 쿠폰 환불 금액		
		map.put("TOTAL_GIFT_COUPON_REFUND_PRICE", 0);					// 총 상품 쿠폰 환불 금액
		map.put("TOTAL_CART_COUPON_REFUND_PRICE", 0);					// 총 장바구니 쿠폰 환불 금액
		map.put("TOTAL_COUPON_REFUND_PRICE", 0);							// 총 쿠폰 환불 금액
		map.put("SHIPPING_REFUND_PRICE", 0);									// 배송비 환불 금액
		map.put("TOTAL_POINT_REFUND_PRICE", 0);								// 포인트 결제 환불 금액
		map.put("TOTAL_PRE_POINT_REFUND_PRICE", 0);						// 선포인트 결제 환불 금액
		map.put("TOTAL_REFUND_POINT", 0);										// 총 환불 포인트
		map.put("TOTAL_PROMOTIONCODE_PRICE", 0);							// 총 프로모션 코드 결제 환불 금액		
		map.put("REFUND_SUBTRACTION", 0);										// 환불차감금액
		map.put("TOTAL_REFUND_PRICE", 0);										// 총 환불금액
//		map.put("REFUND_BANK_CODE", null);										// 환불 은행코드
//		map.put("REFUND_ACCOUNT", null);											// 환불 계좌번호
//		map.put("REFUND_DEPOSITOR", null);										// 환불 예금주명
		map.put("REASON_CD", vo.getReasonCd());								// 사유코드
		map.put("REASON", vo.getReason());											// 사유 상세
		map.put("REQ_MEMBER_IDX", vo.getRegIdx());							// 신청자 일련번호
		map.put("REQ_HTTP_USER_AGENT", vo.getRegHttpUserAgent());	// 신청자 USER_AGENT
		map.put("REQ_IP", vo.getRegIp());											// 신청자 IP
		map.put("REG_IDX", vo.getRegIdx());											// 작성자 일련번호
		map.put("REG_HTTP_USER_AGENT", vo.getRegHttpUserAgent());	// 작성자 USER_AGENT
		map.put("REG_IP", vo.getRegIp());											// 작성자 IP				

		flag = mypageOrderDAO.insertClaimMaster(map);
		
		if(flag < 1){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			resultMap.setMsg("클레임 정보 저장 중 에러가 발생했습니다!");
			return resultMap;
		}

		String claimIdx = map.get("CLAIM_IDX").toString();		// 클레임 마스터 일련번호
		vo.setClaimIdx(Integer.parseInt(claimIdx));
		
		// 첨부 파일
		if(vo.getImgFile() !=null && !vo.getImgFile().isEmpty()){
			MultipartFile multipartFile = vo.getImgFile();
			FileVO fvo = FileUtil.multiPart2File(multipartFile, "claim/"+claimIdx);	
			map.clear();
			map.put("IMGFILE", fvo.getUploadFileNm()); 		// 업로드 파일 명
			map.put("REALIMGFILE", fvo.getOrgFileNm()); 	// 실제 파일 명
			map.put("CLAIMIDX", claimIdx); 						// 클레임 마스터 테이블 일련번호
			
			flag = mypageOrderDAO.updateClaimFile(map); // 파일 정보 insert

			if(flag < 1){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
				resultMap.setResult(false);
				resultMap.setMsg("첨부파일 저장 중 에러가 발생했습니다!");
				return resultMap;
			}
		}

		for(int i=0;i<vo.getOrderDetailIdxes().length;i++){
			// 주문 상세 주문 상태 코드 변경
			map.clear();
			map.put("ORDERIDX", orderIdx);
			map.put("ORDERDETAILIDX", vo.getOrderDetailIdxes()[i].toString());
//			map.put("BEFOREORDERSTATUSCD", vo.getBeforeOrderStatusCd());		// 현재 주문 상태
			map.put("BEFOREORDERSTATUSCDS", vo.getBeforeOrderStatusCds());	// 현재 주문 상태 배열
			map.put("ORDERSTATUSCD", "600");													// 바꿀 주문 상태 (600 : 교환신청)
			map.put("EDITIDX", vo.getRegIdx());
			map.put("EDITHTTPUSERAGENT", vo.getRegHttpUserAgent());
			map.put("EDITIP", vo.getRegIp());	
			
			flag = mypageOrderDAO.updateDetailOrderStatusCd(map);

			if(flag < 1){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
				resultMap.setResult(false);
				resultMap.setMsg("주문 상세 상태 변경 중 에러가 발생했습니다!");
				return resultMap;
			}

			// 주문 상세 로그 저장
			OrderDetailVO detailVO = new OrderDetailVO();
			detailVO.setOrderIdx(Integer.parseInt(orderIdx));
			detailVO.setOrderDetailIdx(Integer.parseInt(vo.getOrderDetailIdxes()[i].toString()));
			detailVO.setOrderStatusCd("600");			// 바꿀 주문 상태 (600 : 교환신청)
			detailVO.setReason(vo.getReason());
			detailVO.setRegIdx(vo.getRegIdx());
			detailVO.setRegHttpUserAgent(vo.getRegHttpUserAgent());
			detailVO.setRegIp(vo.getRegIp());
			
			flag = this.insertOrderStatusLog(detailVO);

			if(flag < 1){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
				resultMap.setResult(false);
				resultMap.setMsg("주문 상세 로그 저장 중 에러가 발생했습니다!");
				return resultMap;
			}

			// 교환 신청 등록------------------------------------------------------------------------------------------------------------------------
			// 주문 상세 정보
			map.clear();
			map.put("ORDERDETAILIDX",Integer.parseInt(vo.getOrderDetailIdxes()[i].toString()));	// 주문 디테일 일련번호
			SqlMap detail = mypageOrderDAO.getOrderDetailInfo(map);

			// 클레임 디테일 등록
			map.clear();
			map.put("CLAIMIDX", claimIdx);						// 클레임 마스터 일련번호
			map.put("ORDERDETAILIDX", Integer.parseInt(vo.getOrderDetailIdxes()[i].toString()));	// 주문 디테일 일련번호
			map.put("REFUNDCHARGE", 0);							// 환불수수료
			map.put("GIFTCOUPONREFUNDPRICE", nvl(detail.get("giftCouponPrice")));		// 상품 쿠폰 환불 금액
			map.put("CARTCOUPONREFUNDPRICE", nvl(detail.get("cartCouponPrice")));		// 장바구니 쿠폰 환불 금액
			map.put("POINTREFUNDPRICE", nvl(detail.get("pointPrice")));						// 포인트 결제 환불 금액
			map.put("PROMOTIONCODEPRICE", nvl(detail.get("promotioncodePrice")));		// 프로모션 코드 환불 금액
			map.put("PREPOINTREFUNDPRICE", nvl(detail.get("prePointPrice")));				// 선포인트 결제 환불 금액
			map.put("REFUNDPRICE", nvl(detail.get("payPrice")));									// 환불 금액
			map.put("REFUNDPOINT", nvl(detail.get("savePoint")));								// 환불 포인트
			map.put("APPROVEYN", "B");								// 승인/불가 여부 (B : 승인전, Y : 승인, N : 불가)
			map.put("REJECTREASON", null);						// 불가 사유 상세
			map.put("EXCHANGEDELIVERYCOMPCD", null);	// 교환 배송업체 코드
			map.put("EXCHANGEINVOICENO", null);				// 교환 송장번호
			map.put("EXCHANGESHIPPINGDT", null);			// 교환 발송일
			map.put("EXCHANGEDELIVEREDDT", null);			// 교환 배송완료일
			map.put("REGIDX", vo.getRegIdx());					// 작성자 일련번호
			map.put("REGHTTPUSERAGENT", vo.getRegHttpUserAgent());	// 작성자 USER_AGENT
			map.put("REGIP", vo.getRegIp());						// 작성자 IP				

			flag = mypageOrderDAO.insertClaimDetail(map);
			
			if(flag < 1){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
				resultMap.setResult(false);
				resultMap.setMsg("클레임 상세 정보 저장 중 에러가 발생했습니다!");
				return resultMap;
			}

			String claimDetailIdx = map.get("CLAIM_DETAIL_IDX").toString();	// 클레임 디테일 일련번호
			
			// 클레임 상세 로그 저장
			map.clear();
			map.put("CLAIMDETAILIDX", claimDetailIdx);		// 클레임 디테일 일련번호
			map.put("ORDERIDX", orderIdx);
			map.put("ORDERDETAILIDX", Integer.parseInt(vo.getOrderDetailIdxes()[i].toString()));	// 주문 디테일 일련번호
			map.put("CLAIMTYPE", "X");								// 클레임 구분 (C : 취소, X : 교환, R : 반품)		
			map.put("APPROVEYN", "B");								// 승인/불가 여부 (B : 승인전, Y : 승인, N : 불가)
			map.put("EXCHANGEDELIVERYCOMPCD", null);	// 교환 배송업체 코드(공통코드)
			map.put("EXCHANGEINVOICENO", null);				// 교환 송장번호
			map.put("DEVICE", PathUtil.getDeviceNm());		// 디바이스 (P : PC, M : MOBILE, A : APP)
			map.put("REGIDX", vo.getRegIdx());					// 작성자 일련번호
			map.put("REGHTTPUSERAGENT", vo.getRegHttpUserAgent());	// 작성자 USER_AGENT
			map.put("REGIP", vo.getRegIp());						// 작성자 IP				

			flag = mypageOrderDAO.insertClaimStatusLog(map);
			
			if(flag < 1){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
				resultMap.setResult(false);
				resultMap.setMsg("클레임 상세 로그 저장 중 에러가 발생했습니다!");
				return resultMap;
			}
			// 교환 신청 등록 끝------------------------------------------------------------------------------------------------------------------------
		}
		
		//3. 클레임 마스터 테이블 합계 update
		map.clear();
		map.put("CLAIMIDX", claimIdx);			// 클레임 마스터 일련번호	
		map.put("REFUNDSUBTRACTION", 0);		// 환불 차감금액
		flag = mypageOrderDAO.updateClaimSumDetail(map);

		if(flag < 1){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			resultMap.setMsg("클레임 정보 적용 중 에러가 발생했습니다!");
			return resultMap;
		}

		// 주문 상세의 주문 상태 코드들 최소값으로 주문 마스터의 주문 상태 코드 변경
		flag = this.updateOrderStatusCdAsDetailMinOrderStatusCd(orderIdx);

		if(flag < 1){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			resultMap.setMsg("주문 상태 적용 중 에러가 발생했습니다!");
			return resultMap;
		}

		resultMap.setResult(true);
		return resultMap;
	}

	   /**
	    * @Method : requestReturnOrder
	    * @Date: 2017. 8. 11.
	    * @Author :  서 정 길
	    * @Description	:	반품 신청
	   */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public JsonResultVO requestReturnOrder(ClaimVO vo) throws Exception {
		int flag = 0;
		
		JsonResultVO resultMap = new JsonResultVO();
		resultMap.setResult(false);
		
		HashMap<String, Object> map = new HashMap<>();
		
		// ORDER_IDX 구함
		map.clear();
		map.put("ORDERCD", vo.getOrderCd());		// 주문 코드
		map.put("MEMBERIDX", vo.getMemberIdx());		// 주문 코드

		SqlMap info = mypageOrderDAO.getOrderInfo(map);	// 주문 정보

		if(info == null || info.size() == 0){
			resultMap.setResult(false);
			resultMap.setMsg("주문 일련번호가 없습니다!");
			return resultMap;
		}
		
		String orderIdx = info.get("orderIdx").toString();
		
		// 클레임 마스터 등록
		map.clear();
		map.put("ORDER_IDX", orderIdx);
		map.put("CLAIM_TYPE", "R");													// 클레임 구분 (C : 취소, X : 교환, R : 반품)		
		map.put("DEVICE", PathUtil.getDeviceNm());								// 디바이스 (P : PC, M : MOBILE, A : APP)
		map.put("REST_PAY_PRICE", 0);												// 잔여 결제금액	// TODO 계산해서 넘겨야 함
		map.put("TOTAL_REFUND_CHARGE", 0);										// 총 환불수수료		
		map.put("FREE_SHIPPING_COUPON_REFUND_PRICE", 0);				// 무료배송 쿠폰 환불 금액		
		map.put("TOTAL_GIFT_COUPON_REFUND_PRICE", 0);					// 총 상품 쿠폰 환불 금액
		map.put("TOTAL_CART_COUPON_REFUND_PRICE", 0);					// 총 장바구니 쿠폰 환불 금액
		map.put("TOTAL_COUPON_REFUND_PRICE", 0);							// 총 쿠폰 환불 금액
		map.put("SHIPPING_REFUND_PRICE", 0);									// 배송비 환불 금액
		map.put("TOTAL_POINT_REFUND_PRICE", 0);								// 포인트 결제 환불 금액
		map.put("TOTAL_PRE_POINT_REFUND_PRICE", 0);						// 선포인트 결제 환불 금액
		map.put("TOTAL_REFUND_POINT", 0);										// 총 환불 포인트
		map.put("TOTAL_PROMOTIONCODE_PRICE", 0);							// 총 프로모션 코드 결제 환불 금액		
		map.put("REFUND_SUBTRACTION", 0);										// 환불차감금액
		map.put("TOTAL_REFUND_PRICE", 0);										// 총 환불금액
		map.put("REFUND_BANK_CODE", vo.getRefundBankCode());			// 환불 은행코드
		map.put("REFUND_ACCOUNT", Crypto.encrypt(vo.getRefundAccount()));				// 환불 계좌번호 암호화
		map.put("REFUND_DEPOSITOR", vo.getRefundDepositor());			// 환불 예금주명
		map.put("REASON_CD", vo.getReasonCd());								// 사유코드
		map.put("REASON", vo.getReason());											// 사유 상세
		map.put("REQ_MEMBER_IDX", vo.getRegIdx());							// 신청자 일련번호
		map.put("REQ_HTTP_USER_AGENT", vo.getRegHttpUserAgent());	// 신청자 USER_AGENT
		map.put("REQ_IP", vo.getRegIp());											// 신청자 IP
		map.put("REG_IDX", vo.getRegIdx());											// 작성자 일련번호
		map.put("REG_HTTP_USER_AGENT", vo.getRegHttpUserAgent());	// 작성자 USER_AGENT
		map.put("REG_IP", vo.getRegIp());											// 작성자 IP				

		flag = mypageOrderDAO.insertClaimMaster(map);
		
		if(flag < 1){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			resultMap.setMsg("클레임 정보 저장 중 에러가 발생했습니다!");
			return resultMap;
		}

		String claimIdx = map.get("CLAIM_IDX").toString();		// 클레임 마스터 일련번호
		vo.setClaimIdx(Integer.parseInt(claimIdx));
		// 첨부 파일
		if(vo.getImgFile() !=null && !vo.getImgFile().isEmpty()){
			MultipartFile multipartFile = vo.getImgFile();
			FileVO fvo = FileUtil.multiPart2File(multipartFile, "claim/"+claimIdx);	
			map.clear();
			map.put("IMGFILE", fvo.getUploadFileNm()); 		// 업로드 파일 명
			map.put("REALIMGFILE", fvo.getOrgFileNm()); 	// 실제 파일 명
			map.put("CLAIMIDX", claimIdx); 						// 클레임 마스터 테이블 일련번호
			
			flag = mypageOrderDAO.updateClaimFile(map); // 파일 정보 insert

			if(flag < 1){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
				resultMap.setResult(false);
				resultMap.setMsg("첨부파일 저장 중 에러가 발생했습니다!");
				return resultMap;
			}
		}

		for(int i=0;i<vo.getOrderDetailIdxes().length;i++){
			// 주문 상세 주문 상태 코드 변경
			map.clear();
			map.put("ORDERIDX", orderIdx);
			map.put("ORDERDETAILIDX", vo.getOrderDetailIdxes()[i].toString());
//			map.put("BEFOREORDERSTATUSCD", vo.getBeforeOrderStatusCd());		// 현재 주문 상태
			map.put("BEFOREORDERSTATUSCDS", vo.getBeforeOrderStatusCds());	// 현재 주문 상태 배열
			map.put("ORDERSTATUSCD", "700");													// 바꿀 주문 상태 (700 : 반품신청)
			map.put("EDITIDX", vo.getRegIdx());
			map.put("EDITHTTPUSERAGENT", vo.getRegHttpUserAgent());
			map.put("EDITIP", vo.getRegIp());	
			
			flag = mypageOrderDAO.updateDetailOrderStatusCd(map);

			if(flag < 1){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
				resultMap.setResult(false);
				resultMap.setMsg("주문 상세 상태 변경 중 에러가 발생했습니다!");
				return resultMap;
			}

			// 주문 상세 로그 저장
			OrderDetailVO detailVO = new OrderDetailVO();
			detailVO.setOrderIdx(Integer.parseInt(orderIdx));
			detailVO.setOrderDetailIdx(Integer.parseInt(vo.getOrderDetailIdxes()[i].toString()));
			detailVO.setOrderStatusCd("700");			// 바꿀 주문 상태 (700 : 반품신청)
			detailVO.setReason(vo.getReason());
			detailVO.setRegIdx(vo.getRegIdx());
			detailVO.setRegHttpUserAgent(vo.getRegHttpUserAgent());
			detailVO.setRegIp(vo.getRegIp());
			
			flag = this.insertOrderStatusLog(detailVO);

			if(flag < 1){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
				resultMap.setResult(false);
				resultMap.setMsg("주문 상세 로그 저장 중 에러가 발생했습니다!");
				return resultMap;
			}

			// 반품 신청 등록------------------------------------------------------------------------------------------------------------------------
			// 주문 상세 정보
			map.clear();
			map.put("ORDERDETAILIDX",Integer.parseInt(vo.getOrderDetailIdxes()[i].toString()));	// 주문 디테일 일련번호
			SqlMap detail = mypageOrderDAO.getOrderDetailInfo(map);

			// 클레임 디테일 등록
			map.clear();
			map.put("CLAIMIDX", claimIdx);						// 클레임 마스터 일련번호
			map.put("ORDERDETAILIDX", Integer.parseInt(vo.getOrderDetailIdxes()[i].toString()));	// 주문 디테일 일련번호
			map.put("REFUNDCHARGE", 0);							// 환불수수료
			map.put("GIFTCOUPONREFUNDPRICE", nvl(detail.get("giftCouponPrice")));		// 상품 쿠폰 환불 금액
			map.put("CARTCOUPONREFUNDPRICE", nvl(detail.get("cartCouponPrice")));		// 장바구니 쿠폰 환불 금액
			map.put("POINTREFUNDPRICE", nvl(detail.get("pointPrice")));						// 포인트 결제 환불 금액
			map.put("PROMOTIONCODEPRICE", nvl(detail.get("promotioncodePrice")));		// 프로모션 코드 환불 금액
			map.put("PREPOINTREFUNDPRICE", nvl(detail.get("prePointPrice")));				// 선포인트 결제 환불 금액
			map.put("REFUNDPRICE", nvl(detail.get("payPrice")));									// 환불 금액
			map.put("REFUNDPOINT", nvl(detail.get("savePoint")));								// 환불 포인트
			map.put("APPROVEYN", "B");								// 승인/불가 여부 (B : 승인전, Y : 승인, N : 불가)
			map.put("REJECTREASON", null);						// 불가 사유 상세
			map.put("EXCHANGEDELIVERYCOMPCD", null);	// 교환 배송업체 코드
			map.put("EXCHANGEINVOICENO", null);				// 교환 송장번호
			map.put("EXCHANGESHIPPINGDT", null);			// 교환 발송일
			map.put("EXCHANGEDELIVEREDDT", null);			// 교환 배송완료일
			map.put("REGIDX", vo.getRegIdx());					// 작성자 일련번호
			map.put("REGHTTPUSERAGENT", vo.getRegHttpUserAgent());	// 작성자 USER_AGENT
			map.put("REGIP", vo.getRegIp());						// 작성자 IP				

			flag = mypageOrderDAO.insertClaimDetail(map);
			
			if(flag < 1){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
				resultMap.setResult(false);
				resultMap.setMsg("클레임 상세 정보 저장 중 에러가 발생했습니다!");
				return resultMap;
			}

			String claimDetailIdx = map.get("CLAIM_DETAIL_IDX").toString();	// 클레임 디테일 일련번호
			
			// 클레임 상세 로그 저장
			map.clear();
			map.put("CLAIMDETAILIDX", claimDetailIdx);		// 클레임 디테일 일련번호
			map.put("ORDERIDX", orderIdx);
			map.put("ORDERDETAILIDX", Integer.parseInt(vo.getOrderDetailIdxes()[i].toString()));	// 주문 디테일 일련번호
			map.put("CLAIMTYPE", "R");								// 클레임 구분 (C : 취소, X : 교환, R : 반품)		
			map.put("APPROVEYN", "B");								// 승인/불가 여부 (B : 승인전, Y : 승인, N : 불가)
			map.put("EXCHANGEDELIVERYCOMPCD", null);	// 교환 배송업체 코드(공통코드)
			map.put("EXCHANGEINVOICENO", null);				// 교환 송장번호
			map.put("DEVICE", PathUtil.getDeviceNm());		// 디바이스 (P : PC, M : MOBILE, A : APP)
			map.put("REGIDX", vo.getRegIdx());					// 작성자 일련번호
			map.put("REGHTTPUSERAGENT", vo.getRegHttpUserAgent());	// 작성자 USER_AGENT
			map.put("REGIP", vo.getRegIp());						// 작성자 IP				

			flag = mypageOrderDAO.insertClaimStatusLog(map);
			
			if(flag < 1){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
				resultMap.setResult(false);
				resultMap.setMsg("클레임 상세 로그 저장 중 에러가 발생했습니다!");
				return resultMap;
			}
			// 반품 신청 등록 끝------------------------------------------------------------------------------------------------------------------------
		}
		
		//3. 클레임 마스터 테이블 합계 update
		map.clear();
		map.put("CLAIMIDX", claimIdx);			// 클레임 마스터 일련번호	
		map.put("REFUNDSUBTRACTION", 0);		// 환불 차감금액
		flag = mypageOrderDAO.updateClaimSumDetail(map);

		if(flag < 1){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			resultMap.setMsg("클레임 정보 적용 중 에러가 발생했습니다!");
			return resultMap;
		}

		// 주문 상세의 주문 상태 코드들 최소값으로 주문 마스터의 주문 상태 코드 변경
		flag = this.updateOrderStatusCdAsDetailMinOrderStatusCd(orderIdx);

		if(flag < 1){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			resultMap.setMsg("주문 상태 적용 중 에러가 발생했습니다!");
			return resultMap;
		}

		// 환불 계좌 저장 (회원만)
		if(vo.getMemberIdx() > 0){	// 회원
			if(vo.getSaveRefundAccount() != null && vo.getSaveRefundAccount().equals("Y")){
				map.clear();
				map.put("MEMBERIDX", vo.getMemberIdx());							// 회원 일련번호
				map.put("BANKCODE", vo.getRefundBankCode());					// 환불 은행코드
				map.put("ACCOUNT", Crypto.encrypt(vo.getRefundAccount()));	// 환불 계좌번호 암호화
				map.put("DEPOSITOR", vo.getRefundDepositor());					// 환불 예금주명
				map.put("REGIDX", vo.getRegIdx());										// 작성자 일련번호
				map.put("REGHTTPUSERAGENT", vo.getRegHttpUserAgent());	// 작성자 USER_AGENT
				map.put("REGIP", vo.getRegIp());											// 작성자 IP				

				flag = mypageOrderDAO.insertRefundAccount(map);
				
				// 저장된 건이 없어도 그냥 무시
				if(flag < 1){
					System.out.println("환불 계좌 저장된 건이 없습니다.");
				}
			}
		}
		
		//KCP이고 에스크로이면 지급보류상태로 변경
		if (info.get("escrowYn").toString().equals("Y") ) {
			if (info.get("pgType").toString().equals("KCP")) { 
				
				String reason = "고객 반품 요청 Claim("+claimIdx+")";
				XPayResultVO Pgvo = new XPayResultVO();
				Pgvo.setLGD_MID(info.get("lgdMid").toString());
				Pgvo.setLGD_TID(info.get("lgdTid").toString());
				
				String ResCode = kcpPgStep3(Pgvo, vo.getRegIp(), reason); 
				if(!ResCode.equals("0000")){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
					resultMap.setResult(false);
					resultMap.setMsg(Pgvo.getLGD_RESPMSG());
					return resultMap;
				}
			}
		}
		
		resultMap.setResult(true);
		return resultMap;
	}

	
	   /**
	    * @Method : getClaimDetailList
	    * @Date: 2017. 8. 7.
	    * @Author :  강병철
	    * @Description	:	Claim 상품 정보
	   */
	public List<SqlMap> getClaimDetailList(int claimIdx) throws Exception{
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		try{
			map.put("CLAIMIDX", claimIdx);
			list = mypageOrderDAO.getClaimDetailList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	
	
	/**
	 * @Method : smilepayCancelProcess
	 * @Date: 2017. 12. 10.
	 * @Author :  김  민  수
	 * @Description	:	
	 * @HISTORY :
	 *
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Boolean smilepayCancelProcess(SqlMap orderInfo, HttpServletRequest request ,  JsonResultVO resultMap) throws Exception  {
		Boolean result = false;
		SqlMap info = null;		
		//1. DB  취소 처리
		try {
			info = cancleAllDBModule(orderInfo, "주문자 직접 취소");	
			if(info == null){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
				resultMap.setResult(false);
				resultMap.setMsg("취소 DB 처리중 에러가 발생했습니다!");
				return false;
			}else{
				if(!info.get("result").toString().equals("TRUE")){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
					resultMap.setResult(false);
					resultMap.setMsg(info.get("result").toString());
					return false;
				}
				else {
					
					//스마일 페이 결제 취소 처리
					
					SmilepayCancelVO cancelVO = new  SmilepayCancelVO(); 
					cancelVO.setAutoFlag("N");				
					cancelVO.setTid(orderInfo.get("tid").toString());
					cancelVO.setCancelMsg("전체주문취소");
					double totalPayPrice = Double.valueOf(String.valueOf(orderInfo.get("totalPayPrice")));
					cancelVO.setCancelAmt(String.valueOf((int)totalPayPrice));			// 결제 금액
					cancelVO.setPartialCancelCode("0");													// 0 : 전체 취소  1: 부분 취소
					cancelVO.setOrderCd(orderInfo.get("orderCd").toString());
					
					SmilepayCancelVO cancelResult = SmilepayUtil.smilepayCancel(cancelVO, request);		//결제 취소 처리
					orderService.saveSamilPayCancelResult(cancelResult);													//결제 취소 정보 저장(취소여부 상관없음)
					if(cancelResult.getResultCode().equals("2001")){
						result = true; 
						resultMap.setResult(true);
					}else{
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
						resultMap.setResult(false);
						resultMap.setMsg(cancelResult.getResultMsg());
						result = false; 
						resultMap.setResult(false);	
					} 
				}
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			resultMap.setMsg(info.get("result").toString());
			return false;
		}
		
 		return result;
	}
	
	/**
	 * @Method : getSmilepayResult
	 * @Date: 2017. 12. 17.
	 * @Author :  김  민  수
	 * @Description	:	스마일 페이 결제 결과
	 * @HISTORY :
	 *
	 */
	public SqlMap getSmilepayResult(OrderVO vo) throws Exception {
		SqlMap info = null;
		HashMap<String, Object> map = new HashMap<String, Object>();

		try{
			map.put("ORDERCD", vo.getOrderCd());		// 주문 번호

			info = mypageOrderDAO.getSmilepayResult(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return info;	
	}
	

	/**
	    * @Method : kcpCancelProcess
	    * @Date: 2017. 8. 7.
	    * @Author :  강 병 철
	    * @Description	: KCP 주문 취소
	    * 
	   */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Boolean kcpCancelProcess( SqlMap orderInfo, SqlMap xpayInfo, JsonResultVO resultMap) throws Exception {
		Boolean result = false;
		SqlMap info = null;
		//1. DB  취소 처리
		try {
			info = cancleAllDBModule(orderInfo, "주문자 직접 취소");	
			if(info == null){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
				resultMap.setResult(false);
				resultMap.setMsg("취소 DB 처리중 에러가 발생했습니다!");
				return false;
			}else{
				if(!info.get("result").toString().equals("TRUE")){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
					resultMap.setResult(false);
					resultMap.setMsg(info.get("result").toString());
					return false;
				} else {
					resultMap.setResult(true);
					result = true;
				}
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			resultMap.setMsg(info.get("result").toString());
			return false;
		}
		XPayResultVO vo = new XPayResultVO();
		vo.setLGD_TID(xpayInfo.get("lgdTid").toString());
		vo.setLGD_ESCROWYN(xpayInfo.get("lgdEscrowyn").toString());
		String ResCode = kcpPgCancel(vo,  orderInfo.get("regIp").toString(), nvl(orderInfo.get("payType")));
		
		if (!  ResCode.equals("0000")  )
		{
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			resultMap.setMsg(vo.getLGD_RESPMSG());
			result = false;
		}
		else {
			resultMap.setResult(true);
			resultMap.setMsg(vo.getLGD_RESPMSG());
			result = true;
		}
	
		return result;
		
		
	}
	
	
	/**
	    * @Method : kcpPgCancel
	    * @Date: 2018. 7. 18.
	    * @Author :  강 병 철
	    * @Description	: KCP 결제 주문 취소
	   */
	public String kcpPgCancel(XPayResultVO vo, String regIp, String payType) throws Exception {
		String result = "";
		
		//2. KCP결제 취소 모듈 실행
		final String g_conf_gw_url =  SpringMessage.getMessage("kcp.gwUrl"); 
		final String g_conf_gw_port =  SpringMessage.getMessage("kcp.gwPort");
		final int g_conf_tx_mode =  0;
		final String g_conf_log_dir =  SpringMessage.getMessage("kcp.logPath");
		final String g_conf_site_cd =  SpringMessage.getMessage("kcp.siteCd");
		final String g_conf_site_key =  SpringMessage.getMessage("kcp.siteKey");
		final String g_conf_log_level =  SpringMessage.getMessage("kcp.logLevel");
		
		try {


			/* ============================================================================== */
		    /* =   03. 인스턴스 생성 및 초기화(변경 불가)                                   = */
		    /* = -------------------------------------------------------------------------- = */
		    /* =       결제에 필요한 인스턴스를 생성하고 초기화 합니다.                     = */
		    /* = -------------------------------------------------------------------------- = */
		    J_PP_CLI_N c_PayPlus = new J_PP_CLI_N();
		    c_PayPlus.mf_init( "", g_conf_gw_url, g_conf_gw_port, g_conf_tx_mode, g_conf_log_dir );
		    c_PayPlus.mf_init_set();  
		    /* ============================================================================== */
		    /* =   03. 인스턴스 생성 및 초기화 END                                          = */
		    /* ============================================================================== */


		    /* ============================================================================== */
		    /* =   04. 처리 요청 정보 설정                                                  = */
		    /* = -------------------------------------------------------------------------- = */

		    /* = -------------------------------------------------------------------------- = */
		    /* =   04-1. 취소/매입 요청                                                     = */
		    /* = -------------------------------------------------------------------------- = */
		    int    mod_data_set_no;
		    String tno = vo.getLGD_TID();
		    String mod_type = "STSC";	// 전체취소 STSC / 부분취소 STPC 
		    String cust_ip = regIp;//;  //IP
		    String tran_cd = "00200000";
		    String escw_yn =  vo.getLGD_ESCROWYN().toString();
		    /* ============================================================================== */
		    /* =   07-1.자동취소시 에스크로 거래인 경우                                     = */
		    /* = -------------------------------------------------------------------------- = */

            if ( escw_yn.equals("Y") && payType.equals("PAY_TYPE25") ) //가상계좌
            {
            	mod_type = "STE5"; // 에스크로 가상계좌 건의 경우 가상계좌 발급취소(STE5)
            }
            else if ( escw_yn.equals("Y") )
            {
            	mod_type = "STE2"; // 에스크로 가상계좌 이외 건은 즉시취소(STE2)
            }
            else
            {
            	mod_type = "STSC"; // 에스크로 거래 건이 아닌 경우(일반건)(STSC)
            }
		    /* = ---------------------------------------------------------------------------- = */
		    /* =   07-1. 자동취소시 에스크로 거래인 경우 처리 END                             = */
		    /* = ============================================================================== */

		                
		    mod_data_set_no = c_PayPlus.mf_add_set( "mod_data" );

		    c_PayPlus.mf_set_us( mod_data_set_no, "tno",      tno		  ); 				  						// KCP 원거래 거래번호
		    c_PayPlus.mf_set_us( mod_data_set_no, "mod_type", mod_type); 								// 전체취소 STSC / 부분취소 STPC 
		    c_PayPlus.mf_set_us( mod_data_set_no, "mod_ip",   cust_ip     ); 				  				    // 변경 요청자 IP
		    c_PayPlus.mf_set_us( mod_data_set_no, "mod_desc", "고객직접취소"    ); 
					               
		    if ( mod_type.equals( "STPC" ) ) // 부분취소의 경우
		    {
		        c_PayPlus.mf_set_us( mod_data_set_no, "mod_mny", "" ); // 취소요청금액
		        c_PayPlus.mf_set_us( mod_data_set_no, "rem_mny", "" ); // 취소가능잔액

		        //복합거래 부분 취소시 주석을 풀어 주시기 바랍니다.
		        //c_PayPlus.mf_set_us( mod_data_set_no, "tax_flag",     "TG03"                       ); // 복합과세 구분
		        //c_PayPlus.mf_set_us( mod_data_set_no, "mod_tax_mny",  mod_tax_mny                  ); // 공급가 부분 취소 요청 금액
		        //c_PayPlus.mf_set_us( mod_data_set_no, "mod_vat_mny",  mod_vat_mny                  ); // 부과세 부분 취소 요청 금액
		        //c_PayPlus.mf_set_us( mod_data_set_no, "mod_free_mny", mod_free_mny                 ); // 비과세 부분 취소 요청 금액
		    }
		    /* = ========================================================================== = */
		    /* =   05. 실행                                                                 = */
		    /* = -------------------------------------------------------------------------- = */
		    c_PayPlus.mf_do_tx( g_conf_site_cd, g_conf_site_key, tran_cd, "", "", g_conf_log_level, "0" );

		    String  res_cd  = c_PayPlus.m_res_cd;                      // 결과 코드
		    String  res_msg = c_PayPlus.m_res_msg;                     // 결과 메시지
		        
		    /* = -------------------------------------------------------------------------- = */
		    /* =   05. 실행 END                                                             = */
		    /* ============================================================================== */
		  	vo.setLGD_RESPCODE(res_cd);
	    	vo.setLGD_RESPMSG( res_msg );
	        System.out.println( "res_cd" + res_cd + "<br>");
	        System.out.println( "res_msg = " + res_msg + "<p>");
	        result = res_cd;
			
		}catch(Exception e){
			result = "AAAA";
			vo.setLGD_RESPCODE(result);
	    	vo.setLGD_RESPMSG( e.getMessage() );
		}

	    return result;
	}
	

	/**
	    * @Method : kcpPgStep2
	    * @Date: 2018. 7. 18.
	    * @Author :  강 병 철
	    * @Description	: KCP 정산보류
	   */
	public String kcpPgStep3(XPayResultVO vo, String regIp, String reason) throws Exception{
		String result = "";
		
		//2. KCP결제 취소 모듈 실행
		final String g_conf_gw_url =  SpringMessage.getMessage("kcp.gwUrl"); 
		final String g_conf_gw_port =  SpringMessage.getMessage("kcp.gwPort");
		final int g_conf_tx_mode =  0;
		final String g_conf_log_dir =  SpringMessage.getMessage("kcp.logPath");
		final String g_conf_site_cd =  SpringMessage.getMessage("kcp.siteCd");
		final String g_conf_site_key =  SpringMessage.getMessage("kcp.siteKey");
		final String g_conf_log_level =  SpringMessage.getMessage("kcp.logLevel");
		
		try {
			/* ============================================================================== */
		    /* =   03. 인스턴스 생성 및 초기화(변경 불가)                                   = */
		    /* = -------------------------------------------------------------------------- = */
		    /* =       결제에 필요한 인스턴스를 생성하고 초기화 합니다.                     = */
		    /* = -------------------------------------------------------------------------- = */
		    J_PP_CLI_N c_PayPlus = new J_PP_CLI_N();
		    c_PayPlus.mf_init( "", g_conf_gw_url, g_conf_gw_port, g_conf_tx_mode, g_conf_log_dir );
		    c_PayPlus.mf_init_set();  
		    /* ============================================================================== */
		    /* =   03. 인스턴스 생성 및 초기화 END                                          = */
		    /* ============================================================================== */


		    /* ============================================================================== */
		    /* =   04. 처리 요청 정보 설정                                                  = */
		    /* = -------------------------------------------------------------------------- = */

		    /* = -------------------------------------------------------------------------- = */
		    /* =   04-1. 취소/매입 요청                                                     = */
		    /* = -------------------------------------------------------------------------- = */
		    int    mod_data_set_no;
		    String tno = vo.getLGD_TID();
		    String mod_type = "STE3";	//정산보류 
		    String cust_ip = regIp;//;  //IP
		    String tran_cd = "00200000";      
		    
		    mod_data_set_no = c_PayPlus.mf_add_set( "mod_data" );
		    

		    c_PayPlus.mf_set_us( mod_data_set_no, "tno",  tno); 				  						// KCP 원거래 거래번호
		    c_PayPlus.mf_set_us( mod_data_set_no, "mod_type", mod_type); 								// 전체취소 STSC / 부분취소 STPC 
		    c_PayPlus.mf_set_us( mod_data_set_no, "mod_ip", cust_ip ); 				  				    // 변경 요청자 IP
		    c_PayPlus.mf_set_us( mod_data_set_no, "mod_desc", reason); 
					               
		    /* = ========================================================================== = */
		    /* =   05. 실행                                                                 = */
		    /* = -------------------------------------------------------------------------- = */
		    c_PayPlus.mf_do_tx( g_conf_site_cd, g_conf_site_key, tran_cd, "", "", g_conf_log_level, "0" );

		    String  res_cd  = c_PayPlus.m_res_cd;                      // 결과 코드
		    String  res_msg =   c_PayPlus.getLogRecvMsg();  //c_PayPlus.m_res_msg;                     // 결과 메시지
		   
		  
		    /* = -------------------------------------------------------------------------- = */
		    /* =   05. 실행 END                                                             = */
		    /* ============================================================================== */
		  	vo.setLGD_RESPCODE(res_cd);
	    	vo.setLGD_RESPMSG( res_msg );
	    	System.out.println( "============== KCP 정산보류 요청 ================" + reason);
	        System.out.println( "res_cd" + res_cd + "<br>");
	        System.out.println( "res_msg = " + res_msg + "<p>");
	        result = res_cd;
			
		}catch(Exception e){
			result = "AAAA";
			vo.setLGD_RESPCODE(result);
	    	vo.setLGD_RESPMSG( e.getMessage() );
		}

	    return result;
	}

	/**
	 * @Method : getWonderpayResult
	 * @Date: 2018.11.02
	 * @Author :  강 병 철
	 * @Description	:	원더페이 결제 결과
	 * @HISTORY :
	 *
	 */
	public SqlMap getWonderpayResult(OrderVO vo) throws Exception {
		SqlMap info = null;
		HashMap<String, Object> map = new HashMap<String, Object>();

		try{
			map.put("ORDERCD", vo.getOrderCd());		// 주문 번호

			info = mypageOrderDAO.getWonderpayResult(map);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return info;	
	}
	
	/**
	    * @Method : getSSLConnect
	    * @Date: 2018. 11. 02.
	    * @Author :   강 병 철
	    * @Description	:	원더페이 인증 통신
	   */
	@SuppressWarnings("unchecked")
	public String getSSLConnect(String apiUrl, String body) throws Exception{
		
		URL url 			   = new URL(apiUrl); 	// 요청을 보낸 URL
		HttpsURLConnection con = null;
		StringBuffer buf 	   = new StringBuffer();
		String returnStr 	   = "";
	
		System.out.println(body);
		try {
			con = (HttpsURLConnection)url.openConnection();
			
			con.setConnectTimeout(30000);		//서버통신 timeout 설정. 30초
			con.setReadTimeout(30000);			//스트림읽기 timeout 설정.30초
			
			con.setRequestProperty("Content-Type", "application/json");
			con.setDoOutput(true);
		    con.setRequestMethod("POST");
		    con.connect();
		    
		    // 송신할 데이터 전송.
		    DataOutputStream dos = new DataOutputStream(con.getOutputStream());
		    dos.write(body.getBytes());
		    dos.flush();
		    dos.close();
		    int resCode = con.getResponseCode();
		    if (resCode == HttpsURLConnection.HTTP_OK) {
		    	BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				int c;
			    
			    while ((c = br.read()) != -1) {
			    	buf.append((char)c);
			    }
			    returnStr = buf.toString();
			    br.close();
		    } else {
		    	returnStr = "{ \"res_cd\" : 9999, \"req_msg\" : \"Connection Error\" }";
		    }
		    
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.disconnect();
		}
		
		return returnStr;
	}
	/**
	    * @Method : wonderCancelProcess
	    * @Date: 2018. 11. 04.
	    * @Author :  강 병 철
	    * @Description	: 원더페이 주문 취소
	   */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public Boolean wonderCancelProcess(SqlMap orderInfo, SqlMap approvalInfo, JsonResultVO resultMap) throws Exception {
		Boolean result = false;
		SqlMap info = null;		
		//1. DB  취소 처리
		try {
			info = cancleAllDBModule(orderInfo, "주문자 직접 취소");	
			if(info == null){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
				resultMap.setResult(false);
				resultMap.setMsg("취소 DB 처리중 에러가 발생했습니다!");
				return false;
			}else{
				if(!info.get("result").toString().equals("TRUE")){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
					resultMap.setResult(false);
					resultMap.setMsg(info.get("result").toString());
					return false;
				}
				else {
					resultMap.setResult(true);
					result = true;					
				}
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			resultMap.setMsg(info.get("result").toString());
			return false;
		}
		final String wonderDomain = SpringMessage.getMessage("wonderpay.domain"); //"https://pay-stg.wonder-pay.com"
		final String wonderAppKey = SpringMessage.getMessage("wonderpay.appKey");   // "4toG23FN0cksHA4HSuzdSRK__";

		//2. 원더페이 결제 취소 모듈 실행
		String apiUrl2 = wonderDomain+"/wpay/api/b2/"+wonderAppKey+"/cancel";
		
		JSONObject json2 = new JSONObject();
		String tno	 = approvalInfo.get("tid") != null ? approvalInfo.get("tid").toString() : "";
		String site_cd	 = approvalInfo.get("siteCd") != null ? approvalInfo.get("siteCd").toString() : "";
		String ordr_idxx	 = approvalInfo.get("orderCd") != null ? approvalInfo.get("orderCd").toString() : "";
		String cancel_desc = "구매자 직접 취소";
		String good_mny	 = approvalInfo.get("goodMny") != null ? approvalInfo.get("goodMny").toString() : "";
		json2.put("site_cd", site_cd);
		json2.put("ordr_idxx", ordr_idxx);
		json2.put("tno", tno);
		json2.put("cancel_desc", cancel_desc);
		json2.put("good_mny", good_mny);
		
		String body2 = json2.toString();
		String callresult2 = getSSLConnect(apiUrl2, body2);

		System.out.println("********************WonderPay 취소 호출(구매자직접호출) ***************************************");
		System.out.println(callresult2);
		System.out.println("*************************************************************************************");
		
		Map<String, Object> resultMap2 = null;
		Gson gson2 = new Gson();
		resultMap2 = gson2.fromJson(callresult2, HashMap.class);
		
		if(!resultMap2.get("res_cd").toString().equals("0000")) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	// 수동 롤백
			resultMap.setResult(false);
			String message   = "원더페이 결제 취소 중 에러가 발생하였습니다.("+resultMap2.get("res_msg").toString()+")";
			System.out.println(message);			
			resultMap.setMsg(message);
			result = false;
		}
		else {
			resultMap.setResult(true);
			result = true;
		}
		return result;
	}
	
}
