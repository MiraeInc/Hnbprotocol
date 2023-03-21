package com.gxenSoft.mall.etc.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gxenSoft.mall.etc.dao.EtcDAO;
import com.gxenSoft.mall.etc.vo.SearchConditionVO;
import com.gxenSoft.sqlMap.SqlMap;

/**
 *************************************
 * PROJECT   : GatsbyMall
 * PROGRAM ID  : CommonServiceImpl
 * PACKAGE NM : com.gatsbyMall.common.service
 * AUTHOR	 : 유  준  철
 * CREATED DATE  : 2017. 6. 14. 
 * HISTORY :
 
 *************************************
 */
@Service("EtcService")
public class EtcServiceImpl implements EtcService {
	
	static final Logger logger = LoggerFactory.getLogger(EtcServiceImpl.class);
	
	@Autowired
	private EtcDAO etcDAO;
	

	/**
	 * @Method : insertKeywordLog
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	검색어 로그 저장
	 */
	public void insertKeywordLog(String keyword, Integer memberIdx, String UserAgent, String UserIp, String Device)throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		map.put("KEYWORD", keyword);
		map.put("REG_IDX", memberIdx);
		map.put("HTTP_USER_AGENT", UserAgent);
		map.put("USER_IP", UserIp);
		map.put("DEVICE", Device);
		etcDAO.insertKeywordLog(map);
	}

	
	/**
	 * @Method : productSearchResult(searchVo);
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	상품검색결과
	 */
	public List<SqlMap> productSearchResult(SearchConditionVO vo)throws Exception{
		List<SqlMap> detail = null;
		HashMap<String, Object> map = new HashMap<>();
		map.put("KEYWORD", "");
		map.put("HASHTAGNAME","");
		map.put("HAIRSTYLE10", "");
		map.put("HAIRSTYLE20", "");
		map.put("HAIRSTYLE30", "");
		map.put("HAIRSTYLE40", "");
		map.put("WAXSTRONG", "");
		map.put("WAXGLOSSY", "");
		map.put("HASHTAG", "");
		
		if (!vo.getKeyword().trim().isEmpty()) {
			vo.setKeyword(vo.getKeyword().replaceAll("\'", ""));
			if (vo.getKeyword().trim().substring(0,1).equals("#")) {
				map.put("HASHTAGNAME", vo.getKeyword().trim().substring(1,vo.getKeyword().trim().length()));
			}
			else
			{
				map.put("KEYWORD", "%"+vo.getKeyword().trim()+"%");				
			}
			
		} else {
			if (!vo.getHashtag().trim().isEmpty()) {
				map.put("HASHTAG", vo.getHashtag());
			} else {
				String waxstrong = "";
				String waxglossy = "";
				
				if (vo.getStrong1().equals("Y")) {
					waxstrong += "10,9,8"; //강
				}
				if (vo.getStrong2().equals("Y")) {
					waxstrong += "7,6,5,4"; //중
				}
				if (vo.getStrong3().equals("Y")) {
					waxstrong += "3,2,1"; //약
				}
				
				if (vo.getGlossy1().equals("Y")) {
					waxglossy += "10,9,8"; //강
				}
				if (vo.getGlossy2().equals("Y")) {
					waxglossy += "7,6,5,4"; //중
				}
				if (vo.getGlossy3().equals("Y")) {
					waxglossy += "3,2,1"; //약
				}		
				
				if (vo.getHairstyle10().equals("Y")) {
					map.put("HAIRSTYLE10", "%HAIR_STYLE10%");
				}	
				if (vo.getHairstyle20().equals("Y")) {
					map.put("HAIRSTYLE20", "%HAIR_STYLE20%");
				}
				if (vo.getHairstyle30().equals("Y")) {
					map.put("HAIRSTYLE30", "%HAIR_STYLE30%");
				}
				if (vo.getHairstyle40().equals("Y")) {
					map.put("HAIRSTYLE40", "%HAIR_STYLE40%");
				}
				
				map.put("WAXSTRONG", waxstrong);
				map.put("WAXGLOSSY", waxglossy);
			}
		}

		map.put("PAGESTART", ((vo.getPageNo() - 1) * vo.getPageBlock()));					
		map.put("PAGEBLOCK", vo.getPageBlock());		
		
		detail = (List<SqlMap>)etcDAO.getProductList(map);
		return detail;
	}
	
	/**
	 * @Method : productSearchCnt
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	상품검색결과 개수
	 */
	public int productSearchCnt(SearchConditionVO vo)throws Exception{
		int cnt = 0;
		HashMap<String, Object> map = new HashMap<>();
		map.put("KEYWORD", "");
		map.put("HASHTAGNAME","");
		map.put("HAIRSTYLE10", "");
		map.put("HAIRSTYLE20", "");
		map.put("HAIRSTYLE30", "");
		map.put("HAIRSTYLE40", "");
		map.put("WAXSTRONG", "");
		map.put("WAXGLOSSY", "");
		map.put("HASHTAG", "");
		
		if (!vo.getKeyword().trim().isEmpty()) {
			vo.setKeyword(vo.getKeyword().replaceAll("\'", ""));
			if (vo.getKeyword().trim().substring(0,1).equals("#")) {
				map.put("HASHTAGNAME", vo.getKeyword().trim().substring(1,vo.getKeyword().trim().length()));
			}
			else
			{
				map.put("KEYWORD", "%"+vo.getKeyword().trim()+"%");				
			}
			
		} else {
			if (!vo.getHashtag().trim().isEmpty()) {
				map.put("HASHTAG", vo.getHashtag());
			} else {
				String waxstrong = "";
				String waxglossy = "";
				
				if (vo.getStrong1().equals("Y")) {
					waxstrong += "10,9,8"; //강
				}
				if (vo.getStrong2().equals("Y")) {
					waxstrong += "7,6,5,4"; //중
				}
				if (vo.getStrong3().equals("Y")) {
					waxstrong += "3,2,1"; //약
				}
				
				if (vo.getGlossy1().equals("Y")) {
					waxglossy += "10,9,8"; //강
				}
				if (vo.getGlossy2().equals("Y")) {
					waxglossy += "7,6,5,4"; //중
				}
				if (vo.getGlossy3().equals("Y")) {
					waxglossy += "3,2,1"; //약
				}		
				
				if (vo.getHairstyle10().equals("Y")) {
					map.put("HAIRSTYLE10", "%HAIR_STYLE10%");
				}	
				if (vo.getHairstyle20().equals("Y")) {
					map.put("HAIRSTYLE20", "%HAIR_STYLE20%");
				}
				if (vo.getHairstyle30().equals("Y")) {
					map.put("HAIRSTYLE30", "%HAIR_STYLE30%");
				}
				if (vo.getHairstyle40().equals("Y")) {
					map.put("HAIRSTYLE40", "%HAIR_STYLE40%");
				}
				
				map.put("WAXSTRONG", waxstrong);
				map.put("WAXGLOSSY", waxglossy);
			}
		}
		cnt = etcDAO.getProductListCnt(map);
		return cnt;
	}
	
	/**
	 * @Method : getMainHashtagList
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	main 노출 해시태그
	 */
	public List<SqlMap> getMainHashtagList()throws Exception {
		List<SqlMap> detail = null;
			
		detail = (List<SqlMap>)etcDAO.getMainHashtagList();
		return detail;
	}
	
	/**
	 * @Method : getExhibitList
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	기획전 검색
	 */
	public List<SqlMap> getExhibitList(SearchConditionVO vo)throws Exception	{
		List<SqlMap> detail = null;
		HashMap<String, Object> map = new HashMap<>();
		map.put("KEYWORD", "");
		map.put("HASHTAGNAME","");
		map.put("HAIRSTYLE10", "");
		map.put("HAIRSTYLE20", "");
		map.put("HAIRSTYLE30", "");
		map.put("HAIRSTYLE40", "");
		map.put("WAXSTRONG", "");
		map.put("WAXGLOSSY", "");
		map.put("HASHTAG", "");
		map.put("DEVICE", vo.getDevice());
		
		if (!vo.getKeyword().trim().isEmpty()) {
			vo.setKeyword(vo.getKeyword().replaceAll("\'", ""));
			if (vo.getKeyword().trim().substring(0,1).equals("#")) {
				map.put("HASHTAGNAME", vo.getKeyword().trim().substring(1,vo.getKeyword().trim().length()));
			}
			else
			{
				map.put("KEYWORD", "%"+vo.getKeyword().trim()+"%");				
			}
			
		} else {
			if (!vo.getHashtag().trim().isEmpty()) {
				map.put("HASHTAG", vo.getHashtag());
			} else {
				String waxstrong = "";
				String waxglossy = "";
				
				if (vo.getStrong1().equals("Y")) {
					waxstrong += "10,9,8"; //강
				}
				if (vo.getStrong2().equals("Y")) {
					waxstrong += "7,6,5,4"; //중
				}
				if (vo.getStrong3().equals("Y")) {
					waxstrong += "3,2,1"; //약
				}
				
				if (vo.getGlossy1().equals("Y")) {
					waxglossy += "10,9,8"; //강
				}
				if (vo.getGlossy2().equals("Y")) {
					waxglossy += "7,6,5,4"; //중
				}
				if (vo.getGlossy3().equals("Y")) {
					waxglossy += "3,2,1"; //약
				}		
				
				if (vo.getHairstyle10().equals("Y")) {
					map.put("HAIRSTYLE10", "%HAIR_STYLE10%");
				}	
				if (vo.getHairstyle20().equals("Y")) {
					map.put("HAIRSTYLE20", "%HAIR_STYLE20%");
				}
				if (vo.getHairstyle30().equals("Y")) {
					map.put("HAIRSTYLE30", "%HAIR_STYLE30%");
				}
				if (vo.getHairstyle40().equals("Y")) {
					map.put("HAIRSTYLE40", "%HAIR_STYLE40%");
				}
				
				map.put("WAXSTRONG", waxstrong);
				map.put("WAXGLOSSY", waxglossy);
			}
		}

		map.put("PAGESTART", ((vo.getExhPageNo() - 1) * vo.getExhPageBlock()));					
		map.put("PAGEBLOCK", vo.getExhPageBlock());																
	
		
		detail = (List<SqlMap>)etcDAO.getExhibitList(map);
		return detail;
	}

	/**
	 * @Method : getExhibitListCnt
	 * @Date		: 2017. 7. 31.
	 * @Author	:  강 병 철
	 * @Description	:	기획전 검색 개수
	 */
	public int getExhibitListCnt(SearchConditionVO vo)throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		map.put("KEYWORD", "");
		map.put("HASHTAGNAME","");
		map.put("HAIRSTYLE10", "");
		map.put("HAIRSTYLE20", "");
		map.put("HAIRSTYLE30", "");
		map.put("HAIRSTYLE40", "");
		map.put("WAXSTRONG", "");
		map.put("WAXGLOSSY", "");
		map.put("HASHTAG", "");
		map.put("DEVICE", vo.getDevice());
		
		if (!vo.getKeyword().trim().isEmpty()) {
			vo.setKeyword(vo.getKeyword().replaceAll("\'", ""));
			if (vo.getKeyword().trim().substring(0,1).equals("#")) {
				map.put("HASHTAGNAME", vo.getKeyword().trim().substring(1,vo.getKeyword().trim().length()));
			}
			else
			{
				map.put("KEYWORD", "%"+vo.getKeyword().trim()+"%");				
			}
			
		} else {
			if (!vo.getHashtag().trim().isEmpty()) {
				map.put("HASHTAG", vo.getHashtag());
			} else {
				String waxstrong = "";
				String waxglossy = "";
				
				if (vo.getStrong1().equals("Y")) {
					waxstrong += "10,9,8"; //강
				}
				if (vo.getStrong2().equals("Y")) {
					waxstrong += "7,6,5,4"; //중
				}
				if (vo.getStrong3().equals("Y")) {
					waxstrong += "3,2,1"; //약
				}
				
				if (vo.getGlossy1().equals("Y")) {
					waxglossy += "10,9,8"; //강
				}
				if (vo.getGlossy2().equals("Y")) {
					waxglossy += "7,6,5,4"; //중
				}
				if (vo.getGlossy3().equals("Y")) {
					waxglossy += "3,2,1"; //약
				}		
				
				if (vo.getHairstyle10().equals("Y")) {
					map.put("HAIRSTYLE10", "%HAIR_STYLE10%");
				}	
				if (vo.getHairstyle20().equals("Y")) {
					map.put("HAIRSTYLE20", "%HAIR_STYLE20%");
				}
				if (vo.getHairstyle30().equals("Y")) {
					map.put("HAIRSTYLE30", "%HAIR_STYLE30%");
				}
				if (vo.getHairstyle40().equals("Y")) {
					map.put("HAIRSTYLE40", "%HAIR_STYLE40%");
				}
				
				map.put("WAXSTRONG", waxstrong);
				map.put("WAXGLOSSY", waxglossy);
			}
		}

		map.put("PAGESTART", ((vo.getExhPageNo() - 1) * vo.getExhPageBlock()));					
		map.put("PAGEBLOCK", vo.getExhPageBlock());																
	
		
		 return etcDAO.getExhibitListCnt(map);
	}
	
	/**
	    * @Method : getTipList
	    * @Date: 2017. 7. 14.
	    * @Author :  강 병 철
	    * @Description	:	스타일 팁 검색 리스트
	   */
	public List<SqlMap> getTipList(SearchConditionVO vo) throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<>();

		map.put("KEYWORD", "");
		map.put("HASHTAGNAME","");
		map.put("HAIRSTYLE10", "");
		map.put("HAIRSTYLE20", "");
		map.put("HAIRSTYLE30", "");
		map.put("HAIRSTYLE40", "");
		map.put("WAXSTRONG", "");
		map.put("WAXGLOSSY", "");
		map.put("HASHTAG", "");
		map.put("DEVICE", vo.getDevice());
		
		if (!vo.getKeyword().trim().isEmpty()) {
			vo.setKeyword(vo.getKeyword().replaceAll("\'", ""));
			if (vo.getKeyword().trim().substring(0,1).equals("#")) {
				map.put("HASHTAGNAME", vo.getKeyword().trim().substring(1,vo.getKeyword().trim().length()));
			}
			else
			{
				map.put("KEYWORD", "%"+vo.getKeyword().trim()+"%");				
			}
			
		} else {
			if (!vo.getHashtag().trim().isEmpty()) {
				map.put("HASHTAG", vo.getHashtag());
			} else {
				String waxstrong = "";
				String waxglossy = "";
				
				if (vo.getStrong1().equals("Y")) {
					waxstrong += "10,9,8"; //강
				}
				if (vo.getStrong2().equals("Y")) {
					waxstrong += "7,6,5,4"; //중
				}
				if (vo.getStrong3().equals("Y")) {
					waxstrong += "3,2,1"; //약
				}
				
				if (vo.getGlossy1().equals("Y")) {
					waxglossy += "10,9,8"; //강
				}
				if (vo.getGlossy2().equals("Y")) {
					waxglossy += "7,6,5,4"; //중
				}
				if (vo.getGlossy3().equals("Y")) {
					waxglossy += "3,2,1"; //약
				}		
				
				if (vo.getHairstyle10().equals("Y")) {
					map.put("HAIRSTYLE10", "%HAIR_STYLE10%");
				}	
				if (vo.getHairstyle20().equals("Y")) {
					map.put("HAIRSTYLE20", "%HAIR_STYLE20%");
				}
				if (vo.getHairstyle30().equals("Y")) {
					map.put("HAIRSTYLE30", "%HAIR_STYLE30%");
				}
				if (vo.getHairstyle40().equals("Y")) {
					map.put("HAIRSTYLE40", "%HAIR_STYLE40%");
				}
				
				map.put("WAXSTRONG", waxstrong);
				map.put("WAXGLOSSY", waxglossy);
			}
		}

		map.put("PAGESTART", ((vo.getTipPageNo() - 1) * vo.getTipPageBlock()));					
		map.put("PAGEBLOCK", vo.getTipPageBlock());																
												
	
		
		try{
			list = etcDAO.getTipList(map); // 스타일 팁 리스트
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}
	

	/**
	    * @Method : getTipListCnt
	    * @Date: 2017. 7. 14.
	    * @Author :  강 병 철
	    * @Description	:	스타일 팁  검색 리스트 총 개수
	   */
	public int getTipListCnt(SearchConditionVO vo) throws Exception {
		int cnt = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("KEYWORD", "");
		map.put("HASHTAGNAME","");
		map.put("HAIRSTYLE10", "");
		map.put("HAIRSTYLE20", "");
		map.put("HAIRSTYLE30", "");
		map.put("HAIRSTYLE40", "");
		map.put("WAXSTRONG", "");
		map.put("WAXGLOSSY", "");
		map.put("HASHTAG", "");
		map.put("DEVICE", vo.getDevice());
		
		if (!vo.getKeyword().trim().isEmpty()) {
			vo.setKeyword(vo.getKeyword().replaceAll("\'", ""));
			if (vo.getKeyword().trim().substring(0,1).equals("#")) {
				map.put("HASHTAGNAME", vo.getKeyword().trim().substring(1,vo.getKeyword().trim().length()));
			}
			else
			{
				map.put("KEYWORD", "%"+vo.getKeyword().trim()+"%");				
			}
			
		} else {
			if (!vo.getHashtag().trim().isEmpty()) {
				map.put("HASHTAG", vo.getHashtag());
			} else {
				String waxstrong = "";
				String waxglossy = "";
				
				if (vo.getStrong1().equals("Y")) {
					waxstrong += "10,9,8"; //강
				}
				if (vo.getStrong2().equals("Y")) {
					waxstrong += "7,6,5,4"; //중
				}
				if (vo.getStrong3().equals("Y")) {
					waxstrong += "3,2,1"; //약
				}
				
				if (vo.getGlossy1().equals("Y")) {
					waxglossy += "10,9,8"; //강
				}
				if (vo.getGlossy2().equals("Y")) {
					waxglossy += "7,6,5,4"; //중
				}
				if (vo.getGlossy3().equals("Y")) {
					waxglossy += "3,2,1"; //약
				}		
				
				if (vo.getHairstyle10().equals("Y")) {
					map.put("HAIRSTYLE10", "%HAIR_STYLE10%");
				}	
				if (vo.getHairstyle20().equals("Y")) {
					map.put("HAIRSTYLE20", "%HAIR_STYLE20%");
				}
				if (vo.getHairstyle30().equals("Y")) {
					map.put("HAIRSTYLE30", "%HAIR_STYLE30%");
				}
				if (vo.getHairstyle40().equals("Y")) {
					map.put("HAIRSTYLE40", "%HAIR_STYLE40%");
				}
				
				map.put("WAXSTRONG", waxstrong);
				map.put("WAXGLOSSY", waxglossy);
			}
		}

		map.put("PAGESTART", ((vo.getTipPageNo() - 1) * vo.getTipPageBlock()));					
		map.put("PAGEBLOCK", vo.getTipPageBlock());																
	
		try{
			cnt = etcDAO.getTipListCnt(map); // 스타일 팁 리스트 총 개수
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}
	

	/**
	    * @Method : getBrandAdList
	    * @Date: 2017. 7. 14.
	    * @Author :  강 병 철
	    * @Description	:	광고 검색 리스트
	   */
	public List<SqlMap> getBrandAdList(SearchConditionVO vo) throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<>();

		map.put("KEYWORD", "");
		map.put("HASHTAGNAME","");
		map.put("HAIRSTYLE10", "");
		map.put("HAIRSTYLE20", "");
		map.put("HAIRSTYLE30", "");
		map.put("HAIRSTYLE40", "");
		map.put("WAXSTRONG", "");
		map.put("WAXGLOSSY", "");
		map.put("HASHTAG", "");
		map.put("DEVICE", vo.getDevice());
		
		if (!vo.getKeyword().trim().isEmpty()) {
			vo.setKeyword(vo.getKeyword().replaceAll("\'", ""));
			if (vo.getKeyword().trim().substring(0,1).equals("#")) {
				map.put("HASHTAGNAME", vo.getKeyword().trim().substring(1,vo.getKeyword().trim().length()));
			}
			else
			{
				map.put("KEYWORD", "%"+vo.getKeyword().trim()+"%");				
			}
			
		} else {
			if (!vo.getHashtag().trim().isEmpty()) {
				map.put("HASHTAG", vo.getHashtag());
			} else {
				String waxstrong = "";
				String waxglossy = "";
				
				if (vo.getStrong1().equals("Y")) {
					waxstrong += "10,9,8"; //강
				}
				if (vo.getStrong2().equals("Y")) {
					waxstrong += "7,6,5,4"; //중
				}
				if (vo.getStrong3().equals("Y")) {
					waxstrong += "3,2,1"; //약
				}
				
				if (vo.getGlossy1().equals("Y")) {
					waxglossy += "10,9,8"; //강
				}
				if (vo.getGlossy2().equals("Y")) {
					waxglossy += "7,6,5,4"; //중
				}
				if (vo.getGlossy3().equals("Y")) {
					waxglossy += "3,2,1"; //약
				}		
				
				if (vo.getHairstyle10().equals("Y")) {
					map.put("HAIRSTYLE10", "%HAIR_STYLE10%");
				}	
				if (vo.getHairstyle20().equals("Y")) {
					map.put("HAIRSTYLE20", "%HAIR_STYLE20%");
				}
				if (vo.getHairstyle30().equals("Y")) {
					map.put("HAIRSTYLE30", "%HAIR_STYLE30%");
				}
				if (vo.getHairstyle40().equals("Y")) {
					map.put("HAIRSTYLE40", "%HAIR_STYLE40%");
				}
				
				map.put("WAXSTRONG", waxstrong);
				map.put("WAXGLOSSY", waxglossy);
			}
		}

		map.put("PAGESTART", ((vo.getAdPageNo() - 1) * vo.getAdPageBlock()));					
		map.put("PAGEBLOCK", vo.getAdPageBlock());																
												
		try{
			list = etcDAO.getBrandAdList(map); 
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}
	

	/**
	    * @Method : getBrandAdListCnt
	    * @Date: 2017. 7. 14.
	    * @Author :  강 병 철
	    * @Description	:	광고 리스트 총 개수
	   */
	public int getBrandAdListCnt(SearchConditionVO vo) throws Exception {
		int cnt = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("KEYWORD", "");
		map.put("HASHTAGNAME","");
		map.put("HAIRSTYLE10", "");
		map.put("HAIRSTYLE20", "");
		map.put("HAIRSTYLE30", "");
		map.put("HAIRSTYLE40", "");
		map.put("WAXSTRONG", "");
		map.put("WAXGLOSSY", "");
		map.put("HASHTAG", "");
		map.put("DEVICE", vo.getDevice());
		
		if (!vo.getKeyword().trim().isEmpty()) {
			vo.setKeyword(vo.getKeyword().replaceAll("\'", ""));
			if (vo.getKeyword().trim().substring(0,1).equals("#")) {
				map.put("HASHTAGNAME", vo.getKeyword().trim().substring(1,vo.getKeyword().trim().length()));
			}
			else
			{
				map.put("KEYWORD", "%"+vo.getKeyword().trim()+"%");				
			}
			
		} else {
			if (!vo.getHashtag().trim().isEmpty()) {
				map.put("HASHTAG", vo.getHashtag());
			} else {
				String waxstrong = "";
				String waxglossy = "";
				
				if (vo.getStrong1().equals("Y")) {
					waxstrong += "10,9,8"; //강
				}
				if (vo.getStrong2().equals("Y")) {
					waxstrong += "7,6,5,4"; //중
				}
				if (vo.getStrong3().equals("Y")) {
					waxstrong += "3,2,1"; //약
				}
				
				if (vo.getGlossy1().equals("Y")) {
					waxglossy += "10,9,8"; //강
				}
				if (vo.getGlossy2().equals("Y")) {
					waxglossy += "7,6,5,4"; //중
				}
				if (vo.getGlossy3().equals("Y")) {
					waxglossy += "3,2,1"; //약
				}		
				
				if (vo.getHairstyle10().equals("Y")) {
					map.put("HAIRSTYLE10", "%HAIR_STYLE10%");
				}	
				if (vo.getHairstyle20().equals("Y")) {
					map.put("HAIRSTYLE20", "%HAIR_STYLE20%");
				}
				if (vo.getHairstyle30().equals("Y")) {
					map.put("HAIRSTYLE30", "%HAIR_STYLE30%");
				}
				if (vo.getHairstyle40().equals("Y")) {
					map.put("HAIRSTYLE40", "%HAIR_STYLE40%");
				}
				
				map.put("WAXSTRONG", waxstrong);
				map.put("WAXGLOSSY", waxglossy);
			}
		}

		map.put("PAGESTART", ((vo.getTipPageNo() - 1) * vo.getTipPageBlock()));					
		map.put("PAGEBLOCK", vo.getTipPageBlock());																
	
		try{
			cnt = etcDAO.getBrandAdListCnt(map); 
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}
	

	/**
	    * @Method : getMagazineList
	    * @Date: 2017. 7. 14.
	    * @Author :  강 병 철
	    * @Description	:	매거진 검색 리스트
	   */
	public List<SqlMap> getMagazineList(SearchConditionVO vo) throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<>();

		map.put("KEYWORD", "");
		map.put("HASHTAGNAME","");
		map.put("HAIRSTYLE10", "");
		map.put("HAIRSTYLE20", "");
		map.put("HAIRSTYLE30", "");
		map.put("HAIRSTYLE40", "");
		map.put("WAXSTRONG", "");
		map.put("WAXGLOSSY", "");
		map.put("HASHTAG", "");
		map.put("DEVICE", vo.getDevice());
		
		if (!vo.getKeyword().trim().isEmpty()) {
			vo.setKeyword(vo.getKeyword().replaceAll("\'", ""));
			if (vo.getKeyword().trim().substring(0,1).equals("#")) {
				map.put("HASHTAGNAME", vo.getKeyword().trim().substring(1,vo.getKeyword().trim().length()));
			}
			else
			{
				map.put("KEYWORD", "%"+vo.getKeyword().trim()+"%");				
			}
			
		} else {
			if (!vo.getHashtag().trim().isEmpty()) {
				map.put("HASHTAG", vo.getHashtag());
			} else {
				String waxstrong = "";
				String waxglossy = "";
				
				if (vo.getStrong1().equals("Y")) {
					waxstrong += "10,9,8"; //강
				}
				if (vo.getStrong2().equals("Y")) {
					waxstrong += "7,6,5,4"; //중
				}
				if (vo.getStrong3().equals("Y")) {
					waxstrong += "3,2,1"; //약
				}
				
				if (vo.getGlossy1().equals("Y")) {
					waxglossy += "10,9,8"; //강
				}
				if (vo.getGlossy2().equals("Y")) {
					waxglossy += "7,6,5,4"; //중
				}
				if (vo.getGlossy3().equals("Y")) {
					waxglossy += "3,2,1"; //약
				}		
				
				if (vo.getHairstyle10().equals("Y")) {
					map.put("HAIRSTYLE10", "%HAIR_STYLE10%");
				}	
				if (vo.getHairstyle20().equals("Y")) {
					map.put("HAIRSTYLE20", "%HAIR_STYLE20%");
				}
				if (vo.getHairstyle30().equals("Y")) {
					map.put("HAIRSTYLE30", "%HAIR_STYLE30%");
				}
				if (vo.getHairstyle40().equals("Y")) {
					map.put("HAIRSTYLE40", "%HAIR_STYLE40%");
				}
				
				map.put("WAXSTRONG", waxstrong);
				map.put("WAXGLOSSY", waxglossy);
			}
		}

		map.put("PAGESTART", ((vo.getMagazPageNo() - 1) * vo.getMagazPageBlock()));					
		map.put("PAGEBLOCK", vo.getMagazPageBlock());																
												
		try{
			list = etcDAO.getMagazineList(map); 
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}
	

	/**
	    * @Method : getMagazineListCnt
	    * @Date: 2017. 7. 14.
	    * @Author :  강 병 철
	    * @Description	:	매거진 리스트 총 개수
	   */
	public int getMagazineListCnt(SearchConditionVO vo) throws Exception {
		int cnt = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("KEYWORD", "");
		map.put("HASHTAGNAME","");
		map.put("HAIRSTYLE10", "");
		map.put("HAIRSTYLE20", "");
		map.put("HAIRSTYLE30", "");
		map.put("HAIRSTYLE40", "");
		map.put("WAXSTRONG", "");
		map.put("WAXGLOSSY", "");
		map.put("HASHTAG", "");
		map.put("DEVICE", vo.getDevice());
		
		if (!vo.getKeyword().trim().isEmpty()) {
			vo.setKeyword(vo.getKeyword().replaceAll("\'", ""));
			if (vo.getKeyword().trim().substring(0,1).equals("#")) {
				map.put("HASHTAGNAME", vo.getKeyword().trim().substring(1,vo.getKeyword().trim().length()));
			}
			else
			{
				map.put("KEYWORD", "%"+vo.getKeyword().trim()+"%");				
			}
			
		} else {
			if (!vo.getHashtag().trim().isEmpty()) {
				map.put("HASHTAG", vo.getHashtag());
			} else {
				String waxstrong = "";
				String waxglossy = "";
				
				if (vo.getStrong1().equals("Y")) {
					waxstrong += "10,9,8"; //강
				}
				if (vo.getStrong2().equals("Y")) {
					waxstrong += "7,6,5,4"; //중
				}
				if (vo.getStrong3().equals("Y")) {
					waxstrong += "3,2,1"; //약
				}
				
				if (vo.getGlossy1().equals("Y")) {
					waxglossy += "10,9,8"; //강
				}
				if (vo.getGlossy2().equals("Y")) {
					waxglossy += "7,6,5,4"; //중
				}
				if (vo.getGlossy3().equals("Y")) {
					waxglossy += "3,2,1"; //약
				}		
				
				if (vo.getHairstyle10().equals("Y")) {
					map.put("HAIRSTYLE10", "%HAIR_STYLE10%");
				}	
				if (vo.getHairstyle20().equals("Y")) {
					map.put("HAIRSTYLE20", "%HAIR_STYLE20%");
				}
				if (vo.getHairstyle30().equals("Y")) {
					map.put("HAIRSTYLE30", "%HAIR_STYLE30%");
				}
				if (vo.getHairstyle40().equals("Y")) {
					map.put("HAIRSTYLE40", "%HAIR_STYLE40%");
				}
				
				map.put("WAXSTRONG", waxstrong);
				map.put("WAXGLOSSY", waxglossy);
			}
		}

		map.put("PAGESTART", ((vo.getMagazPageNo() - 1) * vo.getMagazPageBlock()));					
		map.put("PAGEBLOCK", vo.getMagazPageBlock());																
	
		try{
			cnt = etcDAO.getMagazineListCnt(map); 
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}


	/**
	    * @Method : getReviewList
	    * @Date: 2017. 8. 12.
	    * @Author :  임  재  형
	    * @Description	:	후기 검색 리스트
	   */
	public List<SqlMap> getReviewList(SearchConditionVO vo) throws Exception {
		List<SqlMap> list = null;
		HashMap<String, Object> map = new HashMap<>();

		map.put("KEYWORD", "");
		map.put("HASHTAGNAME","");
		map.put("HAIRSTYLE10", "");
		map.put("HAIRSTYLE20", "");
		map.put("HAIRSTYLE30", "");
		map.put("HAIRSTYLE40", "");
		map.put("WAXSTRONG", "");
		map.put("WAXGLOSSY", "");
		map.put("HASHTAG", "");
		map.put("DEVICE", vo.getDevice());
		
		if (!vo.getKeyword().trim().isEmpty()) {
			vo.setKeyword(vo.getKeyword().replaceAll("\'", ""));
			if (vo.getKeyword().trim().substring(0,1).equals("#")) {
				map.put("KEYWORD", "%"+vo.getKeyword().trim().substring(1,vo.getKeyword().trim().length())+"%");
			}
			else
			{
				map.put("KEYWORD", "%"+vo.getKeyword().trim()+"%");				
			}
		} else {
			if (!vo.getHashtag().trim().isEmpty()) {
				map.put("HASHTAG", vo.getHashtag());
			} else {
				String waxstrong = "";
				String waxglossy = "";
				
				if (vo.getStrong1().equals("Y")) {
					waxstrong += "10,9,8"; //강
				}
				if (vo.getStrong2().equals("Y")) {
					waxstrong += "7,6,5,4"; //중
				}
				if (vo.getStrong3().equals("Y")) {
					waxstrong += "3,2,1"; //약
				}
				
				if (vo.getGlossy1().equals("Y")) {
					waxglossy += "10,9,8"; //강
				}
				if (vo.getGlossy2().equals("Y")) {
					waxglossy += "7,6,5,4"; //중
				}
				if (vo.getGlossy3().equals("Y")) {
					waxglossy += "3,2,1"; //약
				}		
				
				if (vo.getHairstyle10().equals("Y")) {
					map.put("HAIRSTYLE10", "%HAIR_STYLE10%");
				}	
				if (vo.getHairstyle20().equals("Y")) {
					map.put("HAIRSTYLE20", "%HAIR_STYLE20%");
				}
				if (vo.getHairstyle30().equals("Y")) {
					map.put("HAIRSTYLE30", "%HAIR_STYLE30%");
				}
				if (vo.getHairstyle40().equals("Y")) {
					map.put("HAIRSTYLE40", "%HAIR_STYLE40%");
				}
				
				map.put("WAXSTRONG", waxstrong);
				map.put("WAXGLOSSY", waxglossy);
			}
		}

		map.put("PAGESTART", ((vo.getMagazPageNo() - 1) * vo.getMagazPageBlock()));					
		map.put("PAGEBLOCK", vo.getMagazPageBlock());																
												
		try{
			list = etcDAO.getReviewList(map); 
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}


	/**
	    * @Method : getReviewCnt
	    * @Date: 2017. 8. 12.
	    * @Author :  임  재  형
	    * @Description	:	후기 리스트 총 개수
	   */
	public int getReviewCnt(SearchConditionVO vo) throws Exception {
		int cnt = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("KEYWORD", "");
		map.put("HASHTAGNAME","");
		map.put("HAIRSTYLE10", "");
		map.put("HAIRSTYLE20", "");
		map.put("HAIRSTYLE30", "");
		map.put("HAIRSTYLE40", "");
		map.put("WAXSTRONG", "");
		map.put("WAXGLOSSY", "");
		map.put("HASHTAG", "");
		map.put("DEVICE", vo.getDevice());
		
		if (!vo.getKeyword().trim().isEmpty()) {
			vo.setKeyword(vo.getKeyword().replaceAll("\'", ""));
			if (vo.getKeyword().trim().substring(0,1).equals("#")) {
				map.put("KEYWORD", "%"+vo.getKeyword().trim().substring(1,vo.getKeyword().trim().length())+"%");
			}
			else
			{
				map.put("KEYWORD", "%"+vo.getKeyword().trim()+"%");				
			}
		} else {
			if (!vo.getHashtag().trim().isEmpty()) {
				map.put("HASHTAG", vo.getHashtag());
			} else {
				String waxstrong = "";
				String waxglossy = "";
				
				if (vo.getStrong1().equals("Y")) {
					waxstrong += "10,9,8"; //강
				}
				if (vo.getStrong2().equals("Y")) {
					waxstrong += "7,6,5,4"; //중
				}
				if (vo.getStrong3().equals("Y")) {
					waxstrong += "3,2,1"; //약
				}
				
				if (vo.getGlossy1().equals("Y")) {
					waxglossy += "10,9,8"; //강
				}
				if (vo.getGlossy2().equals("Y")) {
					waxglossy += "7,6,5,4"; //중
				}
				if (vo.getGlossy3().equals("Y")) {
					waxglossy += "3,2,1"; //약
				}		
				
				if (vo.getHairstyle10().equals("Y")) {
					map.put("HAIRSTYLE10", "%HAIR_STYLE10%");
				}	
				if (vo.getHairstyle20().equals("Y")) {
					map.put("HAIRSTYLE20", "%HAIR_STYLE20%");
				}
				if (vo.getHairstyle30().equals("Y")) {
					map.put("HAIRSTYLE30", "%HAIR_STYLE30%");
				}
				if (vo.getHairstyle40().equals("Y")) {
					map.put("HAIRSTYLE40", "%HAIR_STYLE40%");
				}
				
				map.put("WAXSTRONG", waxstrong);
				map.put("WAXGLOSSY", waxglossy);
			}
		}

		map.put("PAGESTART", ((vo.getMagazPageNo() - 1) * vo.getMagazPageBlock()));					
		map.put("PAGEBLOCK", vo.getMagazPageBlock());																
	
		try{
			cnt = etcDAO.getReviewCnt(map); 
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		return cnt;
	}


	/**
	 * @Method : getGnbHashList
	 * @Date		: 2018. 8. 29.
	 * @Author	:  임  재  형 
	 * @Description	:	GNB 해시태그 리스트
	 */
	public List<SqlMap> getGnbHashList() throws Exception {
		List<SqlMap> list = null;
		
		try{
			list = etcDAO.getGnbHashList();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
}
