package com.gxenSoft.mall.style.dao;


import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gxenSoft.sqlDAO.CommonDefaultDAO;
import com.gxenSoft.sqlMap.SqlMap;

/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : StyleDAO
    * PACKAGE NM : com.gxenSoft.mall.style.dao
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 7. 13. 
    * HISTORY :   
    *
    *************************************
    */
@Repository("styleDAO")
public class StyleDAO extends CommonDefaultDAO{

	/**
	    * @Method : getCounselListCnt
	    * @Date: 2017. 7. 13.
	    * @Author : 임  재  형
	    * @Description	:	상담 신청 리스트 총 개수
	   */
	public int getCounselListCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("styleDAO.getCounselListCnt", param);
	}

	/**
	    * @Method : getCounselList
	    * @Date: 2017. 7. 13.
	    * @Author : 임  재  형
	    * @Description	:	상담 신청 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getCounselList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("styleDAO.getCounselList", param);
	}
	
	/**
	    * @Method : counselSave
	    * @Date: 2017. 7. 13.
	    * @Author : 임  재  형
	    * @Description	:	스타일 상담 신청
	   */
	public int counselSave(HashMap<String, Object> param) throws Exception {
		return (Integer)insert("styleDAO.counselSave", param);
	}
	
	/**
	    * @Method : counselUpdate
	    * @Date: 2017. 7. 14.
	    * @Author : 임  재  형
	    * @Description	:	스타일 상담 수정
	   */
	public int counselUpdate(HashMap<String, Object> param) throws Exception {
		return (Integer)update("styleDAO.counselUpdate", param);
	}

	/**
	    * @Method : counselView
	    * @Date: 2017. 7. 13.
	    * @Author : 임  재  형
	    * @Description	:	스타일 상담 상세
	   */
	public SqlMap counselView(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("styleDAO.counselView", param);
	}
	
	/**
	    * @Method : counselDelete
	    * @Date: 2017. 7. 14.
	    * @Author : 임  재  형
	    * @Description	:	스타일 상담 삭제	
	   */
	public int counselDelete(HashMap<String, Object> param) throws Exception {
		return (Integer)delete("styleDAO.counselDelete", param);
	}

	/**
	    * @Method : getSampleInfo
	    * @Date: 2017. 7. 13.
	    * @Author : 임  재  형
	    * @Description	:	정품신청 정보
	   */
	public SqlMap getSampleInfo(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("styleDAO.getSampleInfo", param);
	}

	/**
	    * @Method : getSampleReplyListCnt
	    * @Date: 2017. 7. 13.
	    * @Author : 임  재  형
	    * @Description	:	정품신청 댓글 리스트 총 개수
	   */
	public int getSampleReplyListCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("styleDAO.getSampleReplyListCnt", param);
	}

	/**
	    * @Method : getSampleReplyList
	    * @Date: 2017. 7. 13.
	    * @Author : 임  재  형
	    * @Description	:	정품신청 댓글 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getSampleReplyList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("styleDAO.getSampleReplyList", param);
	}
	
	/**
	    * @Method : replyCheck
	    * @Date: 2017. 7. 13.
	    * @Author : 임  재  형
	    * @Description	:	중복 댓글 체크
	   */
	public int replyCheck(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("styleDAO.replyCheck", param);
	}

	/**
	    * @Method : sampleReplySave
	    * @Date: 2017. 7. 13.
	    * @Author : 임  재  형
	    * @Description	:	정품신청 댓글 등록
	   */
	public int sampleReplySave(HashMap<String, Object> param) throws Exception {
		return (Integer)insert("styleDAO.sampleReplySave", param);
	}

	/**
	    * @Method : sampleReplyDeleteAjax
	    * @Date: 2017. 7. 20.
	    * @Author : 임  재  형
	    * @Description	:	정품신청 댓글 삭제
	   */
	public int sampleReplyDeleteAjax(HashMap<String, Object> param) throws Exception {
		return (Integer)delete("styleDAO.sampleReplyDeleteAjax", param);
	}
	
	/**
	 * @Method : addSampleReadCnt
	 * @Date		: 2018. 4. 10.
	 * @Author	:  임  재  형 
	 * @Description	:	정품신청 조회 수 증가
	 */
	public int addSampleReadCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)update("styleDAO.addSampleReadCnt", param);
	}

	/**
	    * @Method : getTipListCnt
	    * @Date: 2017. 7. 14.
	    * @Author : 임  재  형
	    * @Description	:	스타일 팁 리스트 총 개수
	   */
	public int getTipListCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("styleDAO.getTipListCnt", param);
	}
	
	/**
	    * @Method : getTipBrandListCnt
	    * @Date: 2018. 2. 14.
	    * @Author : 임  재  형
	    * @Description	:	스타일 팁 브랜드 리스트 총 개수
	   */
	public int getTipBrandListCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("styleDAO.getTipBrandListCnt", param);
	}

	/**
	    * @Method : getTipList
	    * @Date: 2017. 7. 14.
	    * @Author : 임  재  형
	    * @Description	:	스타일 팁 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getTipList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("styleDAO.getTipList", param);
	}
	
	/**
	    * @Method : getTipBrandList
	    * @Date: 2018. 8. 14.
	    * @Author : 임  재  형
	    * @Description	:	스타일 팁 브랜드 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getTipBrandList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("styleDAO.getTipBrandList", param);
	}

	/**
	    * @Method : getTipView
	    * @Date: 2017. 7. 14.
	    * @Author : 임  재  형
	    * @Description	:	스타일 팁 상세
	   */
	public SqlMap getTipView(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("styleDAO.getTipView", param);
	}
	
	/**
	    * @Method : addTipReadCnt
	    * @Date: 2017. 7. 17.
	    * @Author : 임  재  형
	    * @Description	:	스타일 팁 조회 수 증가
	   */
	public void addTipReadCnt(HashMap<String, Object> param) throws Exception {
		update("styleDAO.addTipReadCnt", param);
	}

	/**
	    * @Method : getHowtouseListCnt
	    * @Date: 2017. 7. 14.
	    * @Author : 임  재  형
	    * @Description	:	상품 사용법 리스트 총 개수
	   */
	public int getHowtouseListCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("styleDAO.getHowtouseListCnt", param);
	}
	
	/**
	    * @Method : getHowtouseBrandListCnt
	    * @Date: 2018. 2. 14.
	    * @Author : 임  재  형
	    * @Description	:	상품 사용법 브랜드 리스트 총 개수
	   */
	public int getHowtouseBrandListCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("styleDAO.getHowtouseBrandListCnt", param);
	}

	/**
	    * @Method : getHowtouseList
	    * @Date: 2017. 7. 14.
	    * @Author : 임  재  형
	    * @Description	:	상품 사용법 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getHowtouseList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("styleDAO.getHowtouseList", param);
	}
	
	/**
	    * @Method : getHowtouseBrandList
	    * @Date: 2018. 2. 14.
	    * @Author : 임  재  형
	    * @Description	:	상품 사용법 브랜드 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getHowtouseBrandList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("styleDAO.getHowtouseBrandList", param);
	}
	
	/**
	    * @Method : getHowtouseDetailMaster
	    * @Date: 2017. 7. 17.
	    * @Author : 임  재  형
	    * @Description	:	상품 사용법 마스터
	   */
	public SqlMap getHowtouseDetailMaster(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("styleDAO.getHowtouseDetailMaster", param);
	}

	/**
	    * @Method : getHowtouseDetailList
	    * @Date: 2017. 7. 14.
	    * @Author : 임  재  형
	    * @Description	:	상품 사용법 상세
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getHowtouseDetailList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("styleDAO.getHowtouseDetailList", param);
	}
	
	/**
	    * @Method : addHowReadCnt
	    * @Date: 2017. 7. 17.
	    * @Author : 임  재  형
	    * @Description	:	상품 사용법 조회 수 증가
	   */
	public void addHowReadCnt(HashMap<String, Object> param) throws Exception {
		update("styleDAO.addHowReadCnt", param);
	}

	/**
	 * @Method : getTipBrandCnt
	 * @Date		: 2018. 9. 11.
	 * @Author	:  임  재  형 
	 * @Description	:	스타일 팁 브랜드 별 개수
	 */
	public SqlMap getTipBrandCnt(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("styleDAO.getTipBrandCnt", param);
	}

	/**
	 * @Method : getHowtoBrandCnt
	 * @Date		: 2018. 9. 12.
	 * @Author	:  임  재  형 
	 * @Description	:	상품 사용법 브랜드 별 개수
	 */
	public SqlMap getHowtoBrandCnt(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("styleDAO.getHowtoBrandCnt", param);
	}

	/**
	 * @Method : getSampleListCnt
	 * @Date		: 2019. 4. 22.
	 * @Author	:  임  재  형 
	 * @Description	:	정품신청 리스트 총 개수
	 */
	public int getSampleListCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("styleDAO.getSampleListCnt", param);
	}

	/**
	 * @Method : getSampleList
	 * @Date		: 2019. 4. 22.
	 * @Author	:  임  재  형 
	 * @Description	:	정품신청 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getSampleList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("styleDAO.getSampleList", param);
	}

	/**
	 * @Method : sampleDupCheck
	 * @Date		: 2019. 4. 22.
	 * @Author	:  임  재  형 
	 * @Description	:	정품신청 중복체크
	 */
	public int sampleDupCheck(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("styleDAO.sampleDupCheck", param);
	}

	/**
	 * @Method : sampleAppl
	 * @Date		: 2019. 4. 22.
	 * @Author	:  임  재  형 
	 * @Description	:	정품신청
	 */
	public int sampleAppl(HashMap<String, Object> param) throws Exception {
		return (Integer)insert("styleDAO.sampleAppl", param);
	}

}
