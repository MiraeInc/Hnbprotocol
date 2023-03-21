package com.gxenSoft.mall.cscenter.faq.service;


import java.util.HashMap;
import java.util.List;

import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gxenSoft.mall.cscenter.faq.dao.FaqDAO;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;
import com.gxenSoft.util.pathUtil.PathUtil;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : FaqServiceImpl
    * PACKAGE NM : com.gxenSoft.mall.cscenter.faq.service
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 7. 25. 
    * HISTORY :   
    *
    *************************************
    */
@Service("faqService")
public class FaqServiceImpl implements FaqService {
	
	static final Logger logger = LoggerFactory.getLogger(FaqServiceImpl.class);
	
	@Autowired
	private FaqDAO faqDAO;

	
	/**
	    * @Method : getFaqListCnt
	    * @Date: 2017. 7. 26.
	    * @Author :  임  재  형
	    * @Description	:	FAQ 리스트 총 개수
	   */
	public int getFaqListCnt(SearchVO schVO) throws Exception {
		int cnt = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(TextUtils.isEmpty(schVO.getSchSubType())){
			schVO.setSchSubType("ALL");
		}
		
		map.put("SCHTYPE", schVO.getSchType());			// 유형 [전체, 배송관련, 주문결제 ...]
		map.put("SCHSUBTYPE", schVO.getSchSubType());	// 검색 조건 [전체, 제목, 내용]
		map.put("SCHVALUE", schVO.getSchValue());		// 검색 어
		
		try{
			cnt = faqDAO.getFaqListCnt(map); // FAQ 리스트 총 개수
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}

	/**
	    * @Method : getFaqList
	    * @Date: 2017. 7. 26.
	    * @Author :  임  재  형
	    * @Description	:	FAQ 리스트
	   */
	public List<SqlMap> getFaqList(SearchVO schVO) throws Exception {
		List<SqlMap> list = null;
		schVO.setPageBlock(10); // 게시글 10개씩 노출
		//schVO.setPageSize(12);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(TextUtils.isEmpty(schVO.getSchSubType())){
			schVO.setSchSubType("ALL");
		}
		
		map.put("SCHTYPE", schVO.getSchType());			// 유형 [전체, 배송관련, 주문결제 ...]
		map.put("SCHSUBTYPE", schVO.getSchSubType());	// 검색 조건 [전체, 제목, 내용]
		map.put("SCHVALUE", schVO.getSchValue());		// 검색 어
		
		map.put("PAGESTART", ((schVO.getPageNo() - 1) * schVO.getPageBlock()));
		map.put("PAGEBLOCK", schVO.getPageBlock());
		map.put("PAGENO", schVO.getPageNo());
		
		try{
			list = faqDAO.getFaqList(map); // FAQ 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;	
	}

	/**
	    * @Method : addFaqReadCnt
	    * @Date: 2017. 8. 17.
	    * @Author :  임  재  형
	    * @Description	:	FAQ 조회수 증가
	   */
	public int addFaqReadCnt(int faqIdx) throws Exception {
		int flag = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("FAQIDX", faqIdx);
		
		flag = faqDAO.addFaqReadCnt(map); // FAQ 조회수 증가
		
		return flag;
	}

	/**
	    * @Method : getFaqTop5List
	    * @Date: 2017. 8. 17.
	    * @Author :  임  재  형
	    * @Description	:	FAQ Top5 리스트
	   */
	public List<SqlMap> getFaqTop5List(SearchVO schVO) throws Exception {
		List<SqlMap> list = null;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("DEVICE", PathUtil.getDevice());
		map.put("SCHVALUE", schVO.getSchValue());		// 검색 어
		
		map.put("PAGESTART", ((schVO.getPageNo() - 1) * schVO.getPageBlock()));
		map.put("PAGEBLOCK", schVO.getPageBlock());
		map.put("PAGENO", schVO.getPageNo());
		
		try{
			list = faqDAO.getFaqTop5List(map); // FAQ Top5 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;	
	}
	
}
