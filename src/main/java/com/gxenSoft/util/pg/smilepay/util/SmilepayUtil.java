package com.gxenSoft.util.pg.smilepay.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;

import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.message.SpringMessage;
import com.gxenSoft.util.pathUtil.PathUtil;
import com.gxenSoft.util.pg.smilepay.vo.SmilepayCancelVO;
import com.gxenSoft.util.pg.smilepay.vo.SmilepayReqVO;
import com.gxenSoft.util.pg.smilepay.vo.SmilepayResultVO;
import com.lgcns.kmpay.dto.DealApproveDto;
import com.lgcns.kmpay.service.CallWebService;

import kr.co.lgcns.module.lite.CnsPayWebConnector;
import net.sf.json.JSONObject;

/**
   *************************************
   * PROJECT   : GatsbyMall
   * PROGRAM ID  : SmilpayUtil
   * PACKAGE NM : com.gxenSoft.mall.lgdacom.util
   * AUTHOR	 : 김 민 수
   * CREATED DATE  : 2017. 11. 25. 
   * HISTORY :   
   *
   *************************************
 **/	
public class SmilepayUtil {

	//message-smilepay.properties 참조
	private static final String SMILEPAY_LOG_HOME = SpringMessage.getMessage("smilepay.log.path");				// 스마일페이 로그 경로 (windows는 \\ 사용, 유닉스와 리눅스는 / 사용에 주의)
	private static final String CNS_PAY_HOME = SpringMessage.getMessage("smilepay.cnsPayHome");					// 스마일 페이 관련 프로퍼티
	private static final String ENCODE_KEY = SpringMessage.getMessage("smilepay.encodekey");	 					// 스마일페이 encodekey (가맹점키 세팅(MID 별로 상이함))
	private static final String MID = SpringMessage.getMessage("smilepay.mid");	 											// 스마일페이 결제요청용 키값	
	private static final String MERCHANTENCKEY = SpringMessage.getMessage("smilepay.merchantenckey");		  	// TXN_ID 호출전용 키값
	private static final String MERCHANTHASHKEY = SpringMessage.getMessage("smilepay.merchanthashkey");		// TXN_ID 호출전용 키값
	
	private static final String MSGNAME = SpringMessage.getMessage("smilepay.msgname");								// smilePay의 INBOUND 전문 URL SETTING
	private static final String WEBPATH = SpringMessage.getMessage("smilepay.webpath");								// smilePay의 INBOUND 전문 URL SETTING
	private static final String EDIDATE = getyyyyMMddHHmmss();																		// 전문 생성일자		 
	
	
	/**
	 * @Method : initInfo
	 * @Date: 2017. 11. 25.
	 * @Author :  김  민  수
	 * @Description	:	스마일 페이 정보 세팅	
	 * @HISTORY :
	 *
	 */
	public static HashMap<String, Object> initInfo(int totalPayPrice , String buyerName)throws Exception{
		
		//결제요청용 키값
		String md_src = EDIDATE + MID + totalPayPrice;
		String hash_String  = SHA256Salt(md_src, ENCODE_KEY);
		
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("smilepayLogHome", SMILEPAY_LOG_HOME);
		resultMap.put("encodekey", ENCODE_KEY);
		resultMap.put("mid", MID);
		resultMap.put("merchantEncKey", MERCHANTENCKEY);									// 가맹점 암호화키
		resultMap.put("merchantHashKey", MERCHANTHASHKEY);								// 가맹점 해쉬키
		resultMap.put("msgName", MSGNAME);
		resultMap.put("webPath", WEBPATH);
		resultMap.put("ediDate", EDIDATE);
		resultMap.put("hashString", hash_String);
		resultMap.put("totalPayPrice", totalPayPrice);
		resultMap.put("goodsCnt", 1);																	// 상품 갯수 	고정
		resultMap.put("authFlg", 10);																	// 인증플래그 	고정
		resultMap.put("merchantTxnNum", System.nanoTime());								// 가맹점 거래번호
		//resultMap.put("merchantTxnNum", EDIDATE);								// 가맹점 거래번호
		if(!UserInfo.getUserInfo().getMemberNm().isEmpty() ){								//회원구매
			resultMap.put("buyerName", UserInfo.getUserInfo().getMemberNm());		// 구매자명
		}else{
			resultMap.put("buyerName", buyerName);												//비회원구매
		}
		
		resultMap.put("payMethod", "SMILEPAY");													// 결제수단
		resultMap.put("certifiedFlag", "CN");															// 인증구분 	CN : 웹결제, N : 인앱결제 고정값으로 사용
		resultMap.put("currency", "KRW");																// 거래통화	고정
		resultMap.put("usePopup", "false");															// true : 팝업  false : 레이어	
		if(PathUtil.getDevice().equals("W")){						
			resultMap.put("prType", "WPM");															// WPM : PC 결제  MPM : Mobile 결제
			resultMap.put("channelType", 4);															//2: 모바일결제, 4: PC결제
		}else{
			resultMap.put("channelType", 2);															//2: 모바일결제, 4: PC결제
			resultMap.put("prType", "MPM");																// WPM : PC 결제  MPM : Mobile 결제
		}
		
		return resultMap;
	}
	
	/**
	 * @Method : getTxnid
	 * @Date: 2017. 11. 26.
	 * @Author :  김  민  수
	 * @Description	:	인증정보 결과 리턴
	 * @HISTORY :
	 *
	 */
	public static HashMap<String, Object> getTxnid(SmilepayReqVO smilepayReqVO)throws Exception{
		int totalPayPrice = Integer.parseInt(smilepayReqVO.getAmt());
		HashMap<String, Object> result = initInfo(totalPayPrice,smilepayReqVO.getBuyerName());			// 결제창 다시 띄울 경우 초기화 세팅
		
		CallWebService webService = new CallWebService();
		
		webService.setLogHome(SMILEPAY_LOG_HOME); 						// Log 디렉토리 설정
		webService.setKMPayHome(CNS_PAY_HOME);								// 프로퍼티 위치지정
		
	    DealApproveDto approveDto = new DealApproveDto();
	    // DTO 객체에 인증 PARAMETER 세팅
	    // 기타 approveDto에 추가로 세팅해야하는 값은 문서(Manual) 참고하여 setter로 추가
	    approveDto.setRequestDealApproveUrl(smilepayReqVO.getRequestDealApproveUrl());		 // 결제요청을 위한 URL
	    approveDto.setMerchantEncKey(result.get("merchantEncKey").toString());							 // 가맹점의 EncKey
	    approveDto.setMerchantHashKey(result.get("merchantHashKey").toString());						 // 가맹점의 HashKey
	    
	    approveDto.setCertifiedFlag(smilepayReqVO.getCertifiedFlag()); 									// WEB결제로 신청할시에 필수 'CN'
	    approveDto.setPrType(smilepayReqVO.getPrType());
	    approveDto.setChannelType(smilepayReqVO.getChannelType()); 									// TMS 및 방판 결제시 필수
	    
	    approveDto.setMerchantID(result.get("mid").toString());			
	    approveDto.setMerchantTxnNum(result.get("merchantTxnNum").toString());
	    
	    approveDto.setProductName(new String(smilepayReqVO.getGoodsName().getBytes("UTF-8"),"UTF-8"));
	    
	    approveDto.setAmount(smilepayReqVO.getAmt());
	    approveDto.setServiceAmt(smilepayReqVO.getServiceAmt());
	    approveDto.setSupplyAmt(smilepayReqVO.getSupplyAmt());
	    approveDto.setGoodsVat(smilepayReqVO.getGoodsVat());
	    
	    approveDto.setCurrency(smilepayReqVO.getCurrency());
	    approveDto.setReturnUrl(smilepayReqVO.getReturnUrl());
	    approveDto.setReturnUrl2(smilepayReqVO.getReturnUrl2());
	    
	    approveDto.setOfferPeriod(smilepayReqVO.getOfferPeriod());
	    approveDto.setOfferPeriodFlag(smilepayReqVO.getOfferPeriodFlag());
	    
	    approveDto.setPossiCard(smilepayReqVO.getPossiCard());
	    approveDto.setFixedInt(smilepayReqVO.getFixedInt());
	    approveDto.setMaxInt(smilepayReqVO.getMaxInt());
	    approveDto.setNoIntYN(smilepayReqVO.getNoIntYN());
	    approveDto.setNoIntOpt(smilepayReqVO.getNoIntOpt());
	    approveDto.setPointUseYN(smilepayReqVO.getPointUseYn());
	    approveDto.setBlockCard(smilepayReqVO.getBlockCard());
	    approveDto.setBlockBin(smilepayReqVO.getBlockBin());
	    
	 	approveDto.setOrderCheckYn(smilepayReqVO.getOrderCheckYn());
	    approveDto.setOrderBirthDay(smilepayReqVO.getOrderBirthDay());
	    approveDto.setOrderName(smilepayReqVO.getOrderName());
	    approveDto.setOrderTel(smilepayReqVO.getOrderTel()); 
	 
	    approveDto.setPossiBin(smilepayReqVO.getPossiBin());
	    approveDto.setEtc1(smilepayReqVO.getEtc1());
	    approveDto.setEtc2(smilepayReqVO.getEtc2());
	    approveDto.setEtc3(smilepayReqVO.getEtc3());
	    approveDto.setSubMallName(smilepayReqVO.getCardSubCoNm());
	    approveDto.setDummyPageFlag(smilepayReqVO.getDummyPageFlag());
		
	    // SMILEPAY 추가 파라미터 세팅
	    approveDto.setTaxationAmount(smilepayReqVO.getTaxationAmt());
	    
	    HashMap<String, Object> resultMap = new HashMap<>();
	    JSONObject  resultJSONObject =  new JSONObject();
	    resultJSONObject = webService.requestDealApprove(approveDto);
	    
	    String resultString = resultJSONObject.toString();
	    
	    if( !resultString.equals("{}") ) {
	    	resultMap.put("resultCode", resultJSONObject.getString("RESULT_CODE"));
	    	resultMap.put("resultMsg", resultJSONObject.getString("RESULT_MSG"));
	    	
	    	if( resultJSONObject.getString("RESULT_CODE").equals("00") ) {
	    		resultMap.put("txnId", resultJSONObject.getString("TXN_ID"));
	    		resultMap.put("merchantTxnNum", resultJSONObject.getString("MERCHANT_TXN_NUM"));
	    		resultMap.put("prDt", resultJSONObject.optString("PR_DT"));
	    		
	    	}
	    }
		return resultMap;
	}
	
	/**
	 * @Method : getSmilepayResult
	 * @Date: 2017. 11. 26.
	 * @Author :  김  민  수
	 * @Description	:	결제 승인 정보 return 	
	 * @HISTORY :
	 *
	 */
	public static SmilepayResultVO getSmilepayResult(HttpServletRequest request)throws Exception{ 

	//    request.setCharacterEncoding("utf-8"); 

		CnsPayWebConnector connector = new CnsPayWebConnector();							// 모듈이 설치되어 있는 경로 설정

		connector.setLogHome(SMILEPAY_LOG_HOME); 													// 환경설정 및 로그 디렉토리 생성
		connector.setCnsPayHome(CNS_PAY_HOME);														// 환경설정 및 로그 디렉토리 생성
		
	    connector.setRequestData(request);																	// 요청 페이지 파라메터 셋팅
	    
	    // 추가 파라메터 셋팅
	    connector.addRequestData("actionType", "PY0");              									// actionType : CL0 취소, PY0 승인
	    connector.addRequestData("MallIP", request.getRemoteAddr());								// 가맹점 고유 ip
	     
	    connector.addRequestData("EncodeKey", ENCODE_KEY);										// 가맹점키 셋팅 (MID 별로 틀림)
	    
	    connector.requestAction();																				// CNSPAY Lite 서버 접속하여 처리
	    
	    SmilepayResultVO result = new SmilepayResultVO();
		HttpSession session = request.getSession(false);
	    ///result.setOrderCd(session.getAttribute("orderCd").toString());
		result.setOrderCd(connector.getResultData("Moid"));  //몰주문번호
	    result.setResultCode(connector.getResultData("ResultCode"));
	    result.setResultMsg(connector.getResultData("ResultMsg"));
	    result.setErrorCD(connector.getResultData("ErrorCD"));
	    result.setErrorMsg(connector.getResultData("ErrorMsg"));
	    result.setAuthDate(connector.getResultData("AuthDate"));
	    result.setAuthCode(connector.getResultData("AuthCode"));
	    result.setBuyerName(connector.getResultData("BuyerName"));
	    result.setGoodsName(connector.getResultData("GoodsName"));
	    result.setPayMethod(connector.getResultData("PayMethod"));
	    result.setMid(connector.getResultData("MID"));
	    result.setTid(connector.getResultData("TID"));
	    result.setMoid(connector.getResultData("Moid"));
	    result.setAmt(connector.getResultData("Amt"));
	    result.setCardCode(connector.getResultData("CardCode"));
	    result.setAcquCardCode(connector.getResultData("AcquCardCode"));
	    result.setCardName(connector.getResultData("CardName"));
	    result.setCardQuota(connector.getResultData("CardQuota"));
	    result.setCardInterest(connector.getResultData("CardInterest"));
	    result.setCardCl(connector.getResultData("CardCl"));
	    result.setCardBin(connector.getResultData("CardBin"));
	    result.setCardPoint(connector.getResultData("CardPoint"));
	    result.setCcPartCl(connector.getResultData("CcPartCl"));
	    result.setPromotionCcPartCl(connector.getResultData("PromotionCcPartCl"));
	    result.setVanCode(connector.getResultData("VanCode"));
	    result.setFnNo(connector.getResultData("FnNo"));
	    result.setCardNo(connector.getResultData("CardNo"));
	    result.setPromotionCd(connector.getResultData("PromotionCd"));
	    result.setDiscountAmt(connector.getResultData("DiscountAmt"));
	    result.setPossiBin(connector.getResultData("possiBin"));
	    result.setBlockBin(connector.getResultData("blockBin"));
	    if("CARD".equals(connector.getResultData("PayMethod"))){  
	        if("3001".equals(connector.getResultData("ResultCode"))){
	        	result.setPaySuccess("Y");
	        }else{
	        	result.setPaySuccess("N");
	        }
	    }
	    
		return result;
		
	}
	
	/**
	 * @Method : smilepayCancel
	 * @Date: 2017. 12. 8.
	 * @Author :  김  민  수
	 * @Description	:	스마일 페이 취소 처리
	 * @HISTORY :
	 *
	 */
	public static SmilepayCancelVO  smilepayCancel(SmilepayCancelVO smilepayCancelVO ,HttpServletRequest request)throws Exception{
		String ediDate = getyyyyMMddHHmmss();
		
		////////위변조 처리/////////
		//결제 취소 요청용 키값
		String md_src = ediDate + MID + smilepayCancelVO.getCancelAmt();
		String hash_String = SHA256Salt(md_src, ENCODE_KEY);
		
		CnsPayWebConnector connector = new CnsPayWebConnector();
		
		// 1. 로그 디렉토리 생성
		connector.setLogHome(SMILEPAY_LOG_HOME); 						
		connector.setCnsPayHome(CNS_PAY_HOME);							
		
		// 2. 요청 페이지 파라메터 셋팅
		connector.setRequestData(request);
		
		// 3. 추가 파라메터 셋팅
		connector.addRequestData("actionType", "CL0");				// 서비스코드 설정
		connector.addRequestData("EdiDate", ediDate);
		connector.addRequestData("EncryptData", hash_String);
		connector.addRequestData("CancelIP", request.getRemoteAddr());
		
		//강제 취소시
		if(smilepayCancelVO.getAutoFlag().equals("N")){
			connector.addRequestData("MID", MID);
			connector.addRequestData("TID", smilepayCancelVO.getTid());
			connector.addRequestData("CancelAmt", smilepayCancelVO.getCancelAmt());
			connector.addRequestData("CancelMsg", smilepayCancelVO.getCancelMsg());
			connector.addRequestData("PartialCancelCode", smilepayCancelVO.getPartialCancelCode());
		}
		
		
		
		// 4. CNSPAY Lite 서버 접속하여 취소 요청 처리
		connector.requestAction();
		
		SmilepayCancelVO resultVO = new SmilepayCancelVO();
		resultVO.setSupplyAmt(smilepayCancelVO.getSupplyAmt());
		resultVO.setGoodsVat(smilepayCancelVO.getGoodsVat());
		resultVO.setServiceAmt(smilepayCancelVO.getServiceAmt());
		resultVO.setTaxationAmt(smilepayCancelVO.getTaxationAmt());
		resultVO.setCancelMsg(smilepayCancelVO.getCancelMsg());
		resultVO.setPartialCancelCode(smilepayCancelVO.getPartialCancelCode());
		resultVO.setCancelIP(request.getRemoteAddr());
		resultVO.setCancelNo(smilepayCancelVO.getCancelNo());
		resultVO.setCheckRemainAmt(smilepayCancelVO.getCheckRemainAmt());
		resultVO.setPreCancelCode(smilepayCancelVO.getPreCancelCode());
		
		//응답결과
		resultVO.setResultCode(connector.getResultData("ResultCode"));
		resultVO.setResultMsg(connector.getResultData("ResultMsg"));
		resultVO.setErrorCd(connector.getResultData("ErrorCD"));
		resultVO.setErrorMsg(connector.getResultData("ErrorMsg"));
		resultVO.setCancelAmt(connector.getResultData("CancelAmt"));
		resultVO.setCancelDate(connector.getResultData("CancelDate"));
		resultVO.setCancelTime(connector.getResultData("CancelTime"));
		resultVO.setCancelNum(connector.getResultData("CancelNum"));
		resultVO.setPayMethod(connector.getResultData("PayMethod"));
		resultVO.setMid(connector.getResultData("MID"));
		resultVO.setTid(connector.getResultData("TID"));
		resultVO.setAuthDate(connector.getResultData("CancelDate")+connector.getResultData("CancelTime"));
		resultVO.setStateCd(connector.getResultData("StateCD"));
		resultVO.setVanCode(connector.getResultData("VanCode"));
		
		return resultVO;
	}
	
	
	
	
	/**
	 * 현재날짜를 YYYYMMDDHHMMSS로 리턴
	 */
	public synchronized static String getyyyyMMddHHmmss(){
		/** yyyyMMddHHmmss Date Format */
		SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");

		return yyyyMMddHHmmss.format(new Date());
	}
	
	
	public static String SHA256Salt(String strData, String salt) { 
		  String SHA = "";
		  
		try {
			MessageDigest sh = MessageDigest.getInstance("SHA-256");
			sh.reset();
			sh.update(salt.getBytes());
			byte byteData[] = sh.digest(strData.getBytes());
			
			//Hardening against the attacker's attack
			sh.reset();
			byteData = sh.digest(byteData);
			
			StringBuffer sb = new StringBuffer();
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));

			}
			
			SHA = sb.toString();
			byte[] raw = SHA.getBytes();
			byte[] encodedBytes = Base64.encodeBase64(raw);
			SHA = new String(encodedBytes);
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
			SHA = null;
		}
		
		return SHA;
	}
}
