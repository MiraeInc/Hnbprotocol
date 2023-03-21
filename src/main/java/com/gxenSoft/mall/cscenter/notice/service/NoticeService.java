package com.gxenSoft.mall.cscenter.notice.service;

import java.util.List;

import com.gxenSoft.mall.cscenter.notice.vo.NoticeVO;
import com.gxenSoft.sqlMap.SqlMap;
import com.gxenSoft.util.page.SearchVO;


/**
    *************************************
    * PROJECT   : GatsbyMall
    * PROGRAM ID  : NoticeService
    * PACKAGE NM : com.gxenSoft.mall.cscenter.notice.service
    * AUTHOR	 : 임  재  형
    * CREATED DATE  : 2017. 7. 25. 
    * HISTORY :   
    *
    *************************************
    */
public interface NoticeService {

	/**
	    * @Method : getNoticeTopListCnt
	    * @Date: 2017. 8. 16.
	    * @Author : 임  재  형
	    * @Description	:	공지사항 Top 리스트 개수
	   */
	int getNoticeTopListCnt(SearchVO schVO) throws Exception;
	
	/**
	    * @Method : getNoticeTopList
	    * @Date: 2017. 7. 25.
	    * @Author : 임  재  형
	    * @Description	:	공지사항 Top 리스트
	   */
	List<SqlMap> getNoticeTopList(SearchVO schVO) throws Exception;
	
	/**
	    * @Method : getNoticeListCnt
	    * @Date: 2017. 7. 25.
	    * @Author : 임  재  형
	    * @Description	:	공지사항 리스트 총 개수
	   */
	int getNoticeListCnt(SearchVO schVO) throws Exception;
	
	/**
	    * @Method : getAllNoticeList
	    * @Date: 2017. 7. 25.
	    * @Author : 임  재  형
	    * @Description	:	공지사항 리스트
	   */
	List<SqlMap> getNoticeList(SearchVO schVO) throws Exception;

	/**
	    * @Method : getNoticeDetail
	    * @Date: 2017. 7. 26.
	    * @Author : 임  재  형
	    * @Description	:	공지사항 상세
	   */
	SqlMap getNoticeDetail(NoticeVO vo) throws Exception;

	/**
	    * @Method : getPrevNotice
	    * @Date: 2017. 7. 26.
	    * @Author : 임  재  형
	    * @Description	:	이전 게시글
	   */
	SqlMap getPrevNotice(NoticeVO vo, SearchVO schVO) throws Exception;

	/**
	    * @Method : getNextNotice
	    * @Date: 2017. 7. 26.
	    * @Author : 임  재  형
	    * @Description	:	다음 게시글
	   */
	SqlMap getNextNotice(NoticeVO vo, SearchVO schVO) throws Exception;

	/**
	    * @Method : getNoticeFileList
	    * @Date: 2017. 8. 29.
	    * @Author : 임  재  형
	    * @Description	:	공지사항 파일 리스트
	   */
	List<SqlMap> getNoticeFileList(NoticeVO vo) throws Exception;
	
	/**
	    * @Method : getMainNoticeList
	    * @Date: 2017. 8. 30.
	    * @Author : 임  재  형
	    * @Description	:	메인 공지사항 리스트
	   */
	List<SqlMap> getMainNoticeList(SearchVO schVO) throws Exception;

	/**
	 * @Method : getFooterNotice
	 * @Date		: 2018. 2. 22.
	 * @Author	:  임  재  형 
	 * @Description	:	푸터 공지사항
	 */
	SqlMap getFooterNotice() throws Exception;

}
