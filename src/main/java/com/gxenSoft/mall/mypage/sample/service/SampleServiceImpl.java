package com.gxenSoft.mall.mypage.sample.service;


import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gxenSoft.mall.common.vo.UserInfo;
import com.gxenSoft.mall.mypage.sample.dao.SampleDAO;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : SampleServiceImpl
    * PACKAGE NM : com.gxenSoft.mall.mypage.sample.service
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 8. 2. 
    * HISTORY :   
    *
    *************************************
    */
@Service("sampleService")
public class SampleServiceImpl implements SampleService {
	
	static final Logger logger = LoggerFactory.getLogger(SampleServiceImpl.class);
	
	@Autowired
	private SampleDAO sampleDAO;

	
	/**
	    * @Method : getSampleListCnt
	    * @Date: 2017. 8. 2.
	    * @Author :  임  재  형
	    * @Description	:	샘플 리스트 총 개수
	   */
	public int getSampleListCnt(SearchVO schVO) throws Exception {
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("REGIDX", UserInfo.getUserInfo().getMemberIdx()); 	// 작성자 일련번호
		
		try{
			cnt = sampleDAO.getSampleListCnt(map); // 샘플 리스트 총 개수
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}

	/**
	    * @Method : getSampleList
	    * @Date: 2017. 8. 2.
	    * @Author :  임  재  형
	    * @Description	:	샘플 리스트
	   */
	public List<SqlMap> getSampleList(SearchVO schVO) throws Exception {
		List<SqlMap> list = null;
		schVO.setPageBlock(10);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("REGIDX", UserInfo.getUserInfo().getMemberIdx());	// 회원 일련번호
		
		map.put("PAGESTART", ((schVO.getPageNo() - 1) * schVO.getPageBlock()));
		map.put("PAGEBLOCK", schVO.getPageBlock());
		map.put("PAGENO", schVO.getPageNo());
		
		try{
			list = sampleDAO.getSampleList(map); // 샘플 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;	
	}

	/**
	    * @Method : getSampleReplyList
	    * @Date: 2017. 8. 2.
	    * @Author :  임  재  형
	    * @Description	:	샘플 댓글 리스트
	   */
	public List<SqlMap> getSampleReplyList(SearchVO schVO) throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("REGIDX", UserInfo.getUserInfo().getMemberIdx());	// 회원 일련번호
		
		try{
			list = sampleDAO.getSampleReplyList(map); // 샘플 댓글 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;	
	}

}
