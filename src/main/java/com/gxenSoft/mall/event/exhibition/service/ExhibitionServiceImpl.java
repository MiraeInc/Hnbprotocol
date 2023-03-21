package com.gxenSoft.mall.event.exhibition.service;


import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gxenSoft.mall.event.exhibition.dao.ExhibitionDAO;
import com.gxenSoft.mall.event.exhibition.vo.ExhibitionVO;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;
import com.gxenSoft.util.pathUtil.PathUtil;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : ExhibitionServiceImpl
    * PACKAGE NM : com.gxenSoft.mall.event.exhibition.service
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 7. 21. 
    * HISTORY :   
    *
    *************************************
    */
@Service("exhibitionService")
public class ExhibitionServiceImpl implements ExhibitionService {
	
	static final Logger logger = LoggerFactory.getLogger(ExhibitionServiceImpl.class);
	
	@Autowired
	private ExhibitionDAO exhibitionDAO;

	
	/**
	    * @Method : getExhibitionBannerList
	    * @Date: 2017. 7. 21.
	    * @Author :  임  재  형
	    * @Description	:	기획전 배너 리스트
	   */
	public List<SqlMap> getExhibitionBannerList() throws Exception {
		List<SqlMap> exhibitionBannerList = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try{
			exhibitionBannerList = exhibitionDAO.getExhibitionBannerList(map); // 기획전 배너 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return exhibitionBannerList;
	}

	/**
	    * @Method : getExhibitionList
	    * @Date: 2017. 7. 21.
	    * @Author :  임  재  형
	    * @Description	:	기획전 리스트
	   */
	public List<SqlMap> getExhibitionList(ExhibitionVO vo, SearchVO schVO) throws Exception {
		List<SqlMap> exhibitionList = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("EXHIBITIONIDX", vo.getExhibitionIdx());	// 기획전 일련번호
		//map.put("SCHTYPE", schVO.getSchType());				// 검색 조건   "ALL":전체, "NEW":신규, "END":마감임박, "BEST":베스트
		map.put("BRANDIDX", vo.getBrandIdx());				// 브랜드 일련번호
		map.put("DEVICE", PathUtil.getDevice());				// 디바이스
		
		try{
			exhibitionList = exhibitionDAO.getExhibitionList(map); // 기획전 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return exhibitionList;
	}

	/**
	    * @Method : getExhibitionDetail
	    * @Date: 2017. 7. 24.
	    * @Author :  임  재  형
	    * @Description	:	기획전 상세
	   */
	public SqlMap getExhibitionDetail(ExhibitionVO vo) throws Exception {
		SqlMap detail = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("EXHIBITIONIDX", vo.getExhibitionIdx()); // 기획전 일련번호
		
		try{
			detail = exhibitionDAO.getExhibitionDetail(map); // 기획전 상세
			exhibitionDAO.addExhibitionReadCnt(map);	 // 기획전 조회 수 증가
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return detail;
	}

	/**
	    * @Method : getHashtagList
	    * @Date: 2017. 7. 24.
	    * @Author :  임  재  형
	    * @Description	:	기획전 해시태그 리스트
	   */
	public List<SqlMap> getHashtagList(ExhibitionVO vo) throws Exception {
		List<SqlMap> hashtagList = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("EXHIBITIONIDX", vo.getExhibitionIdx()); // 기획전 일련번호
		
		try{
			hashtagList = exhibitionDAO.getHashtagList(map); // 기획전 해시태그 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return hashtagList;
	}

	/**
	    * @Method : getBestList
	    * @Date: 2017. 7. 24.
	    * @Author :  임  재  형
	    * @Description	:	베스트 기획전 리스트
	   */
	public List<SqlMap> getBestList(ExhibitionVO vo) throws Exception {
		List<SqlMap> bestList = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("EXHIBITIONIDX", vo.getExhibitionIdx()); // 기획전 일련번호
		
		try{
			bestList = exhibitionDAO.getBestList(map); // 베스트 기획전 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return bestList;
	}

	/**
	    * @Method : getExhibitionGoodsList
	    * @Date: 2017. 7. 24.
	    * @Author :  임  재  형
	    * @Description	:	기획전 상품 리스트
	   */
	public List<SqlMap> getExhibitionGoodsList(ExhibitionVO vo) throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("EXHIBITIONIDX", vo.getExhibitionIdx());
		
		try{
			list = exhibitionDAO.getExhibitionGoodsList(map); // 기획전 상품 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	/**
	    * @Method : getExhibitionGroupList
	    * @Date: 2017. 7. 24.
	    * @Author :  임  재  형
	    * @Description	:	기획전 그룹 리스트
	   */
	public List<SqlMap> getExhibitionGroupList(ExhibitionVO vo) throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("EXHIBITIONIDX", vo.getExhibitionIdx());
		
		try{
			list = exhibitionDAO.getExhibitionGroupList(map); // 기획전 그룹 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}
	
}
