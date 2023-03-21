package com.gxenSoft.mall.cscenter.notice.dao;


import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gxenSoft.sqlDAO.CommonDefaultDAO;
import com.gxenSoft.sqlMap.SqlMap;

/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : NoticeDAO
    * PACKAGE NM : com.gxenSoft.mall.cscenter.notice.dao
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 7. 25. 
    * HISTORY :   
    *
    *************************************
    */
@Repository("noticeDAO")
public class NoticeDAO extends CommonDefaultDAO{

	
	/**
	    * @Method : getNoticeTopListCnt
	    * @Date: 2017. 8. 16.
	    * @Author : 임  재  형
	    * @Description	:	공지사항 Top 리스트 총 개수
	   */
	public int getNoticeTopListCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("noticeDAO.getNoticeTopListCnt", param);
	}
	
	/**
	    * @Method : getNoticeTopList
	    * @Date: 2017. 7. 25.
	    * @Author : 임  재  형
	    * @Description	:	공지사항 Top 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getNoticeTopList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("noticeDAO.getNoticeTopList", param);
	}
	
	/**
	    * @Method : getNoticeListCnt
	    * @Date: 2017. 7. 25.
	    * @Author : 임  재  형
	    * @Description	:	공지사항 리스트 총 개수
	   */
	public int getNoticeListCnt(HashMap<String, Object> param) throws Exception {
		return (Integer)selectListTotCnt("noticeDAO.getNoticeListCnt", param);
	}

	/**
	    * @Method : getNoticeList
	    * @Date: 2017. 7. 25.
	    * @Author : 임  재  형
	    * @Description	:	공지사항 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getNoticeList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("noticeDAO.getNoticeList", param);
	}
	
	/**
	    * @Method : getNoticeDetail
	    * @Date: 2017. 7. 26.
	    * @Author : 임  재  형
	    * @Description	:	공지사항 상세
	   */
	public SqlMap getNoticeDetail(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("noticeDAO.getNoticeDetail", param);
	}

	/**
	    * @Method : getPrevNotice
	    * @Date: 2017. 7. 26.
	    * @Author : 임  재  형
	    * @Description	:	이전 게시글
	   */
	public SqlMap getPrevNotice(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("noticeDAO.getPrevNotice", param);
	}

	/**
	    * @Method : getNextNotice
	    * @Date: 2017. 7. 26.
	    * @Author : 임  재  형
	    * @Description	:	다음 게시글
	   */
	public SqlMap getNextNotice(HashMap<String, Object> param) throws Exception {
		return (SqlMap)selectOne("noticeDAO.getNextNotice", param);
	}

	/**
	    * @Method : addNoticeReadCnt
	    * @Date: 2017. 7. 27.
	    * @Author : 임  재  형
	    * @Description	:	공지사항 조회 수 증가
	   */
	public void addNoticeReadCnt(HashMap<String, Object> param) throws Exception {
		update("noticeDAO.addNoticeReadCnt", param);
	}

	/**
	    * @Method : getNoticeFileList
	    * @Date: 2017. 8. 29.
	    * @Author : 임  재  형
	    * @Description	:	공지사항 파일 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getNoticeFileList(HashMap<String, Object> param) throws Exception {
		return (List<SqlMap>)selectList("noticeDAO.getNoticeFileList", param);
	}

	/**
	    * @Method : getMainNoticeList
	    * @Date: 2017. 8. 30.
	    * @Author : 임  재  형
	    * @Description	:	메인 공지사항 리스트
	   */
	@SuppressWarnings("unchecked")
	public List<SqlMap> getMainNoticeList() throws Exception {
		return (List<SqlMap>)selectList("noticeDAO.getMainNoticeList", null);
	}

	/**
	 * @Method : getFooterNotice
	 * @Date		: 2018. 2. 22.
	 * @Author	:  임  재  형 
	 * @Description	:	푸터 공지사항
	 */
	public SqlMap getFooterNotice() throws Exception {
		return (SqlMap)selectOne("noticeDAO.getFooterNotice", null);
	}

}
