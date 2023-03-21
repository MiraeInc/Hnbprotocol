package com.gxenSoft.mall.mypage.inquiry.service;



import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.apache.http.util.TextUtils;
import org.aspectj.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.mypage.inquiry.dao.InquiryDAO;
import com.gxenSoft.mall.mypage.inquiry.vo.InquiryVO;
import com.gxenSoft.message.SpringMessage;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : InquiryServiceImpl
    * PACKAGE NM : com.gxenSoft.mall.mypage.inquiry.service
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 8. 3. 
    * HISTORY :   
    *
    *************************************
    */
@Service("inquiryService")
public class InquiryServiceImpl implements InquiryService {
	
	static final Logger logger = LoggerFactory.getLogger(InquiryServiceImpl.class);
	
	@Autowired
	private InquiryDAO inquiryDAO;


	/**
	    * @Method : getInquiryListCnt
	    * @Date: 2017. 8. 3.
	    * @Author :  임  재  형
	    * @Description	:	1:1 문의 리스트 총 개수
	   */
	public int getInquiryListCnt(InquiryVO vo, SearchVO schVO) throws Exception {
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("SCHSUBTYPE", schVO.getSchSubType()); 	// 문의 유형
		map.put("SCHSTARTDT", schVO.getSchStartDt());		// 검색 기간 시작일		
		map.put("SCHENDDT", schVO.getSchEndDt());			// 검색 기간 종료일
		if(vo.getMemberIdx() > 0){
			map.put("QUESTNREGIDX", vo.getMemberIdx());	// 작성자 일련번호
			map.put("NOMEMBERORDERCD", "");
		}else{
			map.put("QUESTNREGIDX", "");
			map.put("NOMEMBERORDERCD", vo.getNomemberOrderCd());	// 비회원 주문 코드
		}
		
		try{
			cnt = inquiryDAO.getInquiryListCnt(map); // 1:1 문의 리스트 총 개수
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}

	/**
	    * @Method : getInquiryList
	    * @Date: 2017. 8. 3.
	    * @Author :  임  재  형
	    * @Description	:	1:1 문의 리스트
	   */
	public List<SqlMap> getInquiryList(InquiryVO vo, SearchVO schVO) throws Exception {
		List<SqlMap> list = null;
		schVO.setPageBlock(5);
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("SCHSUBTYPE", schVO.getSchSubType()); 	// 문의 유형
		map.put("SCHSTARTDT", schVO.getSchStartDt());		// 검색 기간 시작일		
		map.put("SCHENDDT", schVO.getSchEndDt());			// 검색 기간 종료일
		if(vo.getMemberIdx() > 0){
			map.put("QUESTNREGIDX", vo.getMemberIdx());	// 작성자 일련번호
			map.put("NOMEMBERORDERCD", "");
		}else{
			map.put("QUESTNREGIDX", "");
			map.put("NOMEMBERORDERCD", vo.getNomemberOrderCd());	// 비회원 주문 코드
		}
		
		// 페이징
		map.put("PAGESTART", ((schVO.getPageNo() - 1) * schVO.getPageBlock()));
		map.put("PAGEBLOCK", schVO.getPageBlock());
		map.put("PAGENO", schVO.getPageNo());
		
		try{
			list = inquiryDAO.getInquiryList(map); // 1:1 문의 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	/**
	    * @Method : inquiryDelete
	    * @Date: 2017. 8. 4.
	    * @Author :  임  재  형
	    * @Description	:	1:1 문의 삭제
	   */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public int inquiryDelete(InquiryVO vo) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("INQUIRYIDX", vo.getInquiryIdx()); // 문의 일련번호
		if(vo.getMemberIdx() > 0){
			map.put("QUESTNREGIDX", vo.getMemberIdx()); // 질문 작성자 일련번호
		}
		
		try{
			flag = inquiryDAO.inquiryDelete(map); // 1:1 문의 삭제
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return flag;
	}

	/**
	    * @Method : getInquiryDetail
	    * @Date: 2017. 8. 6.
	    * @Author :  임  재  형
	    * @Description	:	1:1 문의 상세	
	   */
	public SqlMap getInquiryDetail(InquiryVO vo) throws Exception {
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("INQUIRYIDX", vo.getInquiryIdx()); // 1:1 문의 일련번호
		
		try{
			detail = inquiryDAO.getInquiryDetail(map); // 1:1 문의 상세
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return detail;
	}

	/**
	    * @Method : inquirySave
	    * @Date: 2017. 8. 6.
	    * @Author :  임  재  형
	    * @Description	:	1:1 문의 작성, 수정
	   */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public int inquirySave(InquiryVO vo) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<>();
		String uploadIMGPath = SpringMessage.getMessage("server.imgPath");
		
		map.put("INQUIRYTYPE", vo.getInquiryType());	// 문의 유형
		
		// 회원, 비회원 체크
		if(vo.getMemberIdx() > 0){
			map.put("MEMBERYN", "Y");								// 회원
			map.put("QUESTNREGIDX", vo.getMemberIdx());	// 작성자 일련번호
		}else{
			map.put("MEMBERYN", "N");													// 비회원
			map.put("NOMEMBERORDERCD", vo.getNomemberOrderCd());	// 비회원 주문 코드
		}
		
		// 주문검색
		if(!TextUtils.isEmpty(vo.getOrderCd())){
			map.put("ORDERIDX", vo.getOrderIdx());					// 주문 일련번호 (HIDDEN)
			map.put("ORDERDETAILIDX", vo.getOrderDetailIdx());	// 주문 디테일 일련번호 (HIDDEN)
			map.put("ORDERCD", vo.getOrderCd());						// 주문 코드 (HIDDEN)
			map.put("ORDERGOODSNM", vo.getOrderGoodsNm());	// 주문 상품 명 (HIDDEN)
		}else{
			map.put("ORDERCD", vo.getUserOrderCd());						// 주문 코드 (INPUT)
			map.put("ORDERGOODSNM", vo.getUserOrderGoodsNm());	// 주문 상품 명 (INPUT)
		}
		
		// 상품검색
		if(vo.getQuestnGoodsIdx() != null){
			map.put("QUESTNGOODSIDX", vo.getQuestnGoodsIdx());	// 상품 일련번호 (HIDDEN)
			map.put("QUESTNGOODSNM", vo.getQuestnGoodsNm());	// 상품 명 (HIDDEN)
		}else{
			map.put("QUESTNGOODSNM", vo.getUserQuestnGoodsNm());	// 상품 명 (INPUT)
		}
		
		map.put("PHONESENDYN", vo.getPhoneSendYn());	// 문자 수신 여부
		map.put("QUESTNPHONE", vo.getQuestnPhone());	// 질문자 핸드폰 번호
		map.put("QUESTNEMAIL", vo.getQuestnEmail());		// 질문자 이메일
		map.put("QUESTNTITLE", vo.getQuestnTitle());		// 질문 제목
		map.put("QUESTNDESC", vo.getQuestnDesc());		// 질문 내용
		
		if("I".equals(vo.getStatusFlag())){
			flag = inquiryDAO.inquirySave(map); // 1:1 문의 작성
			
			if (vo.getqImg() != null) 
			{
				int i = 1;
				for (String img : vo.getqImg()) {
					 File tmpFile = new File(img);
					 String fileFolder = img.substring(0,img.lastIndexOf(File.separator));
					
					String Filename = tmpFile.getName();
				    String sourceFile = uploadIMGPath+File.separator+img;
				    String targetFile = uploadIMGPath+File.separator+"inquiry"+File.separator+map.get("INQUIRYIDX")+File.separator+Filename;				    
				    String thumSourceFile = uploadIMGPath+File.separator+fileFolder+File.separator +com.gxenSoft.fileUtil.FileUtil.getFileName(Filename)+"_T162"+"."+com.gxenSoft.fileUtil.FileUtil.getFileExt(Filename);
				    String thumTargetFile = uploadIMGPath+File.separator+"inquiry"+File.separator+map.get("INQUIRYIDX")+File.separator+com.gxenSoft.fileUtil.FileUtil.getFileName(Filename)+"_T162"+"."+com.gxenSoft.fileUtil.FileUtil.getFileExt(Filename);				    

				    //qImg+Integer.toString(i)
				    File source = new File(sourceFile);
				    File target = new File(targetFile);
				    File th_source = new File(thumSourceFile);
				    File th_target = new File(thumTargetFile);
				    FileUtil.copyFile(source, target); //원본복사
				    FileUtil.copyFile(th_source, th_target); //썸네일복사
				    map.put("QIMG"+Integer.toString(i), Filename);
				    i++;
				}
				com.gxenSoft.fileUtil.FileUtil.createPermission(uploadIMGPath+File.separator+"inquiry"+File.separator+map.get("INQUIRYIDX"), "755"); // 폴더, 파일 권한
				if (i > 1) {
					inquiryDAO.inquiryUpdate(map); // 1:1 문의 수정 (이미지정보 수정)
				}
			}
			
		}else if("U".equals(vo.getStatusFlag())){
			map.put("INQUIRYIDX", vo.getInquiryIdx());	// 일련번호
			
			if (!TextUtils.isEmpty(vo.getqImg1())) {
		    	map.put("QIMG1", vo.getqImg1().toString());
		    } 
			if (!TextUtils.isEmpty(vo.getqImg2())) {
		    	map.put("QIMG2", vo.getqImg2().toString());
		    } 
			if (!TextUtils.isEmpty(vo.getqImg3())) {
		    	map.put("QIMG3", vo.getqImg3().toString());
		    } 
			if (!TextUtils.isEmpty(vo.getqImg4())) {
		    	map.put("QIMG4", vo.getqImg4().toString());
		    } 
			
			if (vo.getqImg() != null){
				int i = 1;
				for (String img : vo.getqImg()) {
					File tmpFile = new File(img);
					String fileFolder = img.substring(0,img.lastIndexOf(File.separator));
					
					String Filename = tmpFile.getName();
				    String sourceFile = uploadIMGPath+File.separator+img;
				    String targetFile = uploadIMGPath+File.separator+"inquiry"+File.separator+map.get("INQUIRYLIDX")+File.separator+Filename;				    
				    String thumSourceFile = uploadIMGPath+File.separator+fileFolder+File.separator +com.gxenSoft.fileUtil.FileUtil.getFileName(Filename)+"_T162"+"."+com.gxenSoft.fileUtil.FileUtil.getFileExt(Filename);
				    String thumTargetFile = uploadIMGPath+File.separator+"inquiry"+File.separator+map.get("INQUIRYIDX")+File.separator+com.gxenSoft.fileUtil.FileUtil.getFileName(Filename)+"_T162"+"."+com.gxenSoft.fileUtil.FileUtil.getFileExt(Filename);				    

				    //qImg+Integer.toString(i)
				    File source = new File(sourceFile);
				    File target = new File(targetFile);
				    File th_source = new File(thumSourceFile);
				    File th_target = new File(thumTargetFile);
				    FileUtil.copyFile(source, target); //원본복사
				    FileUtil.copyFile(th_source, th_target); //썸네일복사
				    if (TextUtils.isEmpty(vo.getqImg1()) && StringUtils.isEmpty(map.get("QIMG1"))) {
				    	map.put("QIMG1", Filename);
				    } else {
					    if (TextUtils.isEmpty(vo.getqImg2()) && StringUtils.isEmpty(map.get("QIMG2"))) {
					    	map.put("QIMG2", Filename);
					    } else {
					    	if (TextUtils.isEmpty(vo.getqImg3()) && StringUtils.isEmpty(map.get("QIMG3"))) {
						    	map.put("QIMG3", Filename);
						    } else {
						    	if (TextUtils.isEmpty(vo.getqImg4()) && StringUtils.isEmpty(map.get("QIMG4"))) {
							    	map.put("QIMG4", Filename);
							    } 
						    }		
					    }				    	
				    }
				    i++;
				}
			}
			com.gxenSoft.fileUtil.FileUtil.createPermission(uploadIMGPath+File.separator+"inquiry"+File.separator+map.get("INQUIRYIDX"), "755"); // 폴더, 파일 권한
			flag = inquiryDAO.inquiryUpdate(map); // 1:1 문의 수정
		}
		
		return flag;
	}

	/**
	    * @Method : getInquiryGoodsListCnt
	    * @Date: 2017. 8. 7.
	    * @Author :  임  재  형
	    * @Description	:	1:1 상품 검색 리스트 총 개수
	   */
	public int getInquiryGoodsListCnt(InquiryVO vo, SearchVO schVO) throws Exception {
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("USERQUESTNGOODSNM", vo.getUserQuestnGoodsNm()); 	// 상품 검색 상품 명
		
		try{
			cnt = inquiryDAO.getInquiryGoodsListCnt(map); // 1:1 상품 검색 리스트 총 개수
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}

	/**
	    * @Method : getInquiryGoodsList
	    * @Date: 2017. 8. 7.
	    * @Author :  임  재  형
	    * @Description	:	1:1 상품 검색 리스트
	   */
	public List<SqlMap> getInquiryGoodsList(InquiryVO vo, SearchVO schVO) throws Exception {
		List<SqlMap> list = null;
		schVO.setPageBlock(20);
		
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("USERQUESTNGOODSNM", vo.getUserQuestnGoodsNm()); 	// 상품 검색 상품 명
		
		// 페이징
		map.put("PAGESTART", ((schVO.getPageNo() - 1) * schVO.getPageBlock()));
		map.put("PAGEBLOCK", schVO.getPageBlock());
		map.put("PAGENO", schVO.getPageNo());
		
		try{
			list = inquiryDAO.getInquiryGoodsList(map); // 1:1 상품 검색 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	/**
	    * @Method : getInquiryOrderListCnt
	    * @Date: 2017. 8. 8.
	    * @Author :  임  재  형
	    * @Description	:	1:1 주문 검색 리스트 총 개수
	   */
	public int getInquiryOrderListCnt(InquiryVO vo, SearchVO schVO) throws Exception {
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<>();
		
		if(vo.getMemberIdx() > 0){
			map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx()); // 회원 일련번호
		}else{
			map.put("NOMEMBERORDERCD", vo.getNomemberOrderCd()); // 비회원 주문 코드
		}
		map.put("USERORDERCD", vo.getUserOrderCd()); 	// 주문 검색 주문 코드
		map.put("USERORDERGOODSNM", vo.getUserOrderGoodsNm());	// 주문 검색 상품 명
		
		try{
			cnt = inquiryDAO.getInquiryOrderListCnt(map); // 1:1 주문 검색 리스트 총 개수
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}

	/**
	    * @Method : getInquiryOrderList
	    * @Date: 2017. 8. 8.
	    * @Author :  임  재  형
	    * @Description	:	1:1 주문 검색 리스트
	   */
	public List<SqlMap> getInquiryOrderList(InquiryVO vo, SearchVO schVO) throws Exception {
		List<SqlMap> list = null;
		schVO.setPageBlock(5);
		
		HashMap<String, Object> map = new HashMap<>();
		
		if(vo.getMemberIdx() > 0){
			map.put("MEMBERIDX", UserInfo.getUserInfo().getMemberIdx()); // 회원 일련번호
		}else{
			map.put("NOMEMBERORDERCD", vo.getNomemberOrderCd()); // 비회원 주문 코드
		}
		map.put("USERORDERCD", vo.getUserOrderCd()); 	// 주문 검색 주문 코드
		map.put("USERORDERGOODSNM", vo.getUserOrderGoodsNm());	// 주문 검색 상품 명
		
		// 페이징
		map.put("PAGESTART", ((schVO.getPageNo() - 1) * schVO.getPageBlock()));
		map.put("PAGEBLOCK", schVO.getPageBlock());
		map.put("PAGENO", schVO.getPageNo());
		
		try{
			list = inquiryDAO.getInquiryOrderList(map); // 1:1 주문 검색 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	/**
	    * @Method : getInquiryOrderDetail
	    * @Date: 2017. 8. 28.
	    * @Author :  임  재  형
	    * @Description	:	비회원 주문 정보
	   */
	public SqlMap getInquiryOrderDetail(String orderCd) throws Exception {
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("NOMEMBERORDERCD", orderCd); // 주문코드
		
		try{
			detail = inquiryDAO.getInquiryOrderDetail(map); // 비회원 주문 정보
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return detail;
	}

}
